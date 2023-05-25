import com.bn.gpb.GpbCommons;
import com.bn.gpb.device.GpbDevice;
import com.bn.gpb.envelope.Envelope;
import com.bn.gpb.util.GPBConstants;
import com.bn.common.dto.security.SecretKey;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by sbose on 3/4/23.
 */
public class EndpointRegisterTest extends BaseTest {

    public static void main(String[] args) throws Exception {

        Envelope.EnvelopeRequestV1.Builder envelopeRequestV1 =  Envelope.EnvelopeRequestV1.newBuilder();
        envelopeRequestV1.setCommand(GPBConstants.ENDPOINT_REGISTER).setVersion(1);
        GpbCommons.LocaleV1.Builder locale = GpbCommons.LocaleV1.newBuilder().setCountry("US").setLanguage("en");
        envelopeRequestV1.setUserLocale(locale.build());
        envelopeRequestV1.setProviderCountry("US");
        GpbDevice.DeviceAuthRequestV1.Builder req =  GpbDevice.DeviceAuthRequestV1.newBuilder();
        String deviceModelName = "ANDMBL";
        String serialNumber = "Android-NOOK-4e68ec9f-c26b-39b3-aad2-d7b5b081f090";
        String randNum = String.format("%s", "bZQXOAkEmWqRbTyA2U");//"ailbCXc59RVaWLTQv"
        //Set Device
        GpbCommons.DeviceV1.Builder deviceInfo = GpbCommons.DeviceV1.newBuilder();
        deviceInfo.setBuildNumber("5.5");
        deviceInfo.setModel(deviceModelName);
        deviceInfo.setSerialNum(serialNumber);
        deviceInfo.setSoftwareVersion("5.5.1.7");
        deviceInfo.setEndpointType(deviceModelName);
        req.setDevice(deviceInfo.build()) ;
        SecretKey sk = new SecretKey();
        String passPhrase = String.format("%s", randNum);
        String hashKey = sk.getDigest(passPhrase, "SHA1", 1);
        req.setDeviceRand(hashKey);
        String data = String.format("%s|%s|%s", serialNumber, deviceModelName, hashKey);
        byte[] secretKey2 = Base64.decodeBase64(hashKey.getBytes());
        String hashData = sk.encryptData(data, secretKey2);
        req.setDeviceHash(hashData);
        envelopeRequestV1.setPayload(req.build().toByteString());
        Envelope.EnvelopeResponseV1 envelopeResponseV1 = postRequest(envelopeRequestV1.build().toByteArray(),
                GPBConstants.ENDPOINT_REGISTER,
                "api/device/endpointRegister", null);
        GpbDevice.DeviceAuthResponseV1 resp = GpbDevice.DeviceAuthResponseV1.parseFrom(envelopeResponseV1.getPayload());
        String deviceToken = resp.getDeviceToken().getToken();
        String accountToken = resp.getAccountToken() !=null ? resp.getAccountToken().getToken() : null;
        System.out.println("deviceToken : " + deviceToken);
    }
}
