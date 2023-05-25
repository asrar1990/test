import com.bn.gpb.GpbCommons;
import com.bn.gpb.device.GpbDevice;
import com.bn.gpb.envelope.Envelope;
import com.bn.gpb.util.GPBConstants;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;

/**
 * Created by sbose on 3/4/23.
 */
public class EndpointGetHashIT extends BaseTest {

    public static void main(String[] args) throws Exception {

        Envelope.EnvelopeRequestV1.Builder envelopeRequestV1 =  Envelope.EnvelopeRequestV1.newBuilder();
        envelopeRequestV1.setCommand(GPBConstants.ENDPOINT_GETPASSPHRASE).setVersion(1);
        GpbCommons.LocaleV1.Builder locale = GpbCommons.LocaleV1.newBuilder().setCountry("US").setLanguage("en");
        envelopeRequestV1.setUserLocale(locale.build());
        envelopeRequestV1.setProviderCountry("US");
        
        GpbDevice.EndpointGetPassPhraseRequestV1.Builder endpointGetPassPhraseRequestV1 =
                GpbDevice.EndpointGetPassPhraseRequestV1.newBuilder();
        endpointGetPassPhraseRequestV1.setModelid("ANDMBL");
        endpointGetPassPhraseRequestV1.setUniqueid("Android-NOOK-4e68ec9f-c26b-39b3-aad2-d7b5b081f090");  //Android temp device

        envelopeRequestV1.setPayload(endpointGetPassPhraseRequestV1.build().toByteString());

        // Convert the message to JSON
        JsonFormat.Printer printer = JsonFormat.printer();
//        String json = printer.print(envelopeRequestV1.build());


        Envelope.EnvelopeResponseV1 envelopeResponseV1 = postRequest(envelopeRequestV1.build().toByteArray(),
                GPBConstants.ENDPOINT_GETPASSPHRASE,
                "api/device/endpointGetPassPhrase", null);
        GpbDevice.EndpointGetPassPhraseResponseV1 resp = GpbDevice.EndpointGetPassPhraseResponseV1.parseFrom(envelopeResponseV1.getPayload());
        String responseString = resp.getPassphrase();
        System.out.println("response : " + resp.getPassphrase());
    }

    public static String toJson(MessageOrBuilder messageOrBuilder) throws IOException {
        return JsonFormat.printer().print(messageOrBuilder);
    }

    /*public static Message fromJson(String json) throws IOException {
        Message.Builder structBuilder = Struct.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(json, structBuilder);
        return structBuilder.build();
    }*/
}
