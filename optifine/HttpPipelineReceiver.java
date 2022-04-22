package optifine;

import java.nio.charset.*;
import java.util.*;
import java.io.*;

public class HttpPipelineReceiver extends Thread
{
    private HttpPipelineConnection httpPipelineConnection;
    private static final Charset ASCII;
    private static final String HEADER_CONTENT_LENGTH;
    private static final char CR;
    private static final char LF;
    
    static {
        LF = '\n';
        CR = '\r';
        HEADER_CONTENT_LENGTH = "Content-Length";
        ASCII = Charset.forName("ASCII");
    }
    
    public HttpPipelineReceiver(final HttpPipelineConnection httpPipelineConnection) {
        super("HttpPipelineReceiver");
        this.httpPipelineConnection = null;
        this.httpPipelineConnection = httpPipelineConnection;
    }
    
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            this.httpPipelineConnection.onResponseReceived(this.httpPipelineConnection.getNextRequestReceive(), this.readResponse(this.httpPipelineConnection.getInputStream()));
        }
    }
    
    private HttpResponse readResponse(final InputStream inputStream) throws IOException {
        final String line = this.readLine(inputStream);
        final String[] tokenize = Config.tokenize(line, " ");
        if (tokenize.length < 3) {
            throw new IOException("Invalid status line: " + line);
        }
        final String s = tokenize[0];
        final int int1 = Config.parseInt(tokenize[1], 0);
        final String s2 = tokenize[2];
        final LinkedHashMap<Object, String> linkedHashMap = (LinkedHashMap<Object, String>)new LinkedHashMap<String, String>();
        while (true) {
            final String line2 = this.readLine(inputStream);
            if (line2.length() <= 0) {
                break;
            }
            final int index = line2.indexOf(":");
            if (index <= 0) {
                continue;
            }
            linkedHashMap.put(line2.substring(0, index).trim(), line2.substring(index + 1).trim());
        }
        byte[] contentChunked = null;
        final String s3 = linkedHashMap.get("Content-Length");
        if (s3 != null) {
            final int int2 = Config.parseInt(s3, -1);
            if (int2 > 0) {
                contentChunked = new byte[int2];
                this.readFull(contentChunked, inputStream);
            }
        }
        else if (Config.equals(linkedHashMap.get("Transfer-Encoding"), "chunked")) {
            contentChunked = this.readContentChunked(inputStream);
        }
        return new HttpResponse(int1, line, linkedHashMap, contentChunked);
    }
    
    private byte[] readContentChunked(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        do {
            i = Integer.parseInt(Config.tokenize(this.readLine(inputStream), "; ")[0], 16);
            final byte[] array = new byte[i];
            this.readFull(array, inputStream);
            byteArrayOutputStream.write(array);
            this.readLine(inputStream);
        } while (i != 0);
        return byteArrayOutputStream.toByteArray();
    }
    
    private void readFull(final byte[] array, final InputStream inputStream) throws IOException {
        while (0 < array.length) {
            if (inputStream.read(array, 0, array.length - 0) < 0) {
                throw new EOFException();
            }
        }
    }
    
    private String readLine(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read;
        do {
            read = inputStream.read();
            if (read < 0) {
                break;
            }
            byteArrayOutputStream.write(read);
        } while (-1 != 13 || read != 10);
        String substring = new String(byteArrayOutputStream.toByteArray(), HttpPipelineReceiver.ASCII);
        if (true) {
            substring = substring.substring(0, substring.length() - 2);
        }
        return substring;
    }
}
