package optifine;

import java.net.*;
import net.minecraft.client.*;
import java.util.*;
import java.io.*;

public class HttpUtils
{
    public static final String SERVER_URL;
    public static final String POST_URL;
    
    public static byte[] get(final String s) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s).openConnection(Minecraft.getMinecraft().getProxy());
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(false);
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() / 100 != 2) {
            if (httpURLConnection.getErrorStream() != null) {
                Config.readAll(httpURLConnection.getErrorStream());
            }
            throw new IOException("HTTP response: " + httpURLConnection.getResponseCode());
        }
        final InputStream inputStream = httpURLConnection.getInputStream();
        final byte[] array = new byte[httpURLConnection.getContentLength()];
        while (inputStream.read(array, 0, array.length - 0) >= 0) {
            if (0 >= array.length) {
                final byte[] array2 = array;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return array2;
            }
        }
        throw new IOException("Input stream closed: " + s);
    }
    
    public static String post(final String s, final Map map, final byte[] array) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s).openConnection(Minecraft.getMinecraft().getProxy());
        httpURLConnection.setRequestMethod("POST");
        if (map != null) {
            for (final String s2 : map.keySet()) {
                httpURLConnection.setRequestProperty(s2, new StringBuilder().append(map.get(s2)).toString());
            }
        }
        httpURLConnection.setRequestProperty("Content-Type", "text/plain");
        httpURLConnection.setRequestProperty("Content-Length", new StringBuilder().append(array.length).toString());
        httpURLConnection.setRequestProperty("Content-Language", "en-US");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        final OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(array);
        outputStream.flush();
        outputStream.close();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "ASCII"));
        final StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append('\r');
        }
        bufferedReader.close();
        final String string = sb.toString();
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        return string;
    }
    
    static {
        POST_URL = "http://optifine.net";
        SERVER_URL = "http://s.optifine.net";
    }
}
