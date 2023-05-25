import com.bn.gpb.GpbCommons;
import com.bn.gpb.device.GpbDevice;
import com.bn.gpb.envelope.Envelope;
import com.bn.gpb.util.GPBConstants;
import com.sample.gpb.person.GpbPerson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbose on 3/4/23.
 */
public class EndpointGetHashTest extends BaseTest {

    public static void main(String[] args) throws Exception {

        Envelope.EnvelopeRequestV1.Builder envelopeRequestV1 =  Envelope.EnvelopeRequestV1.newBuilder();
        envelopeRequestV1.setCommand(GPBConstants.ENDPOINT_GETPASSPHRASE).setVersion(1);
        GpbCommons.LocaleV1.Builder locale = GpbCommons.LocaleV1.newBuilder().setCountry("US").setLanguage("en");
        envelopeRequestV1.setUserLocale(locale.build());
        envelopeRequestV1.setProviderCountry("US");
        
        GpbDevice.EndpointGetPassPhraseRequestV1.Builder endpointGetPassPhraseRequestV1 =
                GpbDevice.EndpointGetPassPhraseRequestV1.newBuilder();
        endpointGetPassPhraseRequestV1.setModelid("ANDMBL");
        endpointGetPassPhraseRequestV1.setUniqueid("Android-NOOK-4e68ec9f-c26b-39b3-aad2-d7b5b081f090");  //Android
        // temp device

        envelopeRequestV1.setPayload(endpointGetPassPhraseRequestV1.build().toByteString());

        Envelope.EnvelopeResponseV1 envelopeResponseV1 = postRequest(envelopeRequestV1.build().toByteArray(),
                GPBConstants.ENDPOINT_GETPASSPHRASE,
                "api/device/endpointGetPassPhrase", null);
        GpbDevice.EndpointGetPassPhraseResponseV1 resp = GpbDevice.EndpointGetPassPhraseResponseV1.parseFrom(envelopeResponseV1.getPayload());
        String responseString = resp.getPassphrase();
        System.out.println("response : " + resp.getPassphrase());
    }
}
