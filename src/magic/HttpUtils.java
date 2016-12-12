package magic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
    public static String get(String dataUrl) throws IOException {

        URL url = new URL(dataUrl);
        String response = null;

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.addRequestProperty("User-Agent" , "Mozilla-4.76");
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = readStream(in);
        } finally {
            urlConnection.disconnect();
        }
        return response;
    }

    private static String readStream(InputStream in) throws IOException {

        // Read stdin
        BufferedReader rd = new BufferedReader( new InputStreamReader(in));

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }
}
