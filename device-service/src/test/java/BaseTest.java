import com.bn.gpb.envelope.Envelope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbose on 23/4/23.
 */
public abstract class BaseTest {
    public static final String BASE_URL = "http://localhost:9002/";
//    public static final String BASE_URL =  "https://nook-apim-dev-eastus.azure-api.net/bncloud/ServiceD";


    public static Envelope.EnvelopeResponseV1 postRequest(byte[] data, String command, String apiEndpoint,
            Integer... expectedCodes ) throws IOException {
        String url = BASE_URL + apiEndpoint;
//        url = BASE_URL;
        OutputStream out = null;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/octet-stream");

        Map<String, String> headers = new HashMap();
        headers.put("BN_COMMAND", command);
        headers.put("x-dynaTrace", "NA="+command);
        for(Map.Entry<String, String> header : headers.entrySet()) {
            conn.setRequestProperty(header.getKey(), header.getValue());
        }

        out = conn.getOutputStream();
        if (data != null) {
            out.write(data);
            out.flush();
        }
        if (expectedCodes == null || expectedCodes.length == 0) {
            expectedCodes = new Integer[]{200};
        }
        conn.connect();
        InputStream in =conn.getInputStream();
        Envelope.EnvelopeResponseV1 envelopeResponseV1= Envelope.EnvelopeResponseV1.parseFrom(in);
        return envelopeResponseV1;
    }
}
