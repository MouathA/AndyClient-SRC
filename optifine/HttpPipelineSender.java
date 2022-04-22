package optifine;

import java.nio.charset.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class HttpPipelineSender extends Thread
{
    private HttpPipelineConnection httpPipelineConnection;
    private static final String CRLF;
    private static Charset ASCII;
    
    static {
        CRLF = "\r\n";
        HttpPipelineSender.ASCII = Charset.forName("ASCII");
    }
    
    public HttpPipelineSender(final HttpPipelineConnection httpPipelineConnection) {
        super("HttpPipelineSender");
        this.httpPipelineConnection = null;
        this.httpPipelineConnection = httpPipelineConnection;
    }
    
    @Override
    public void run() {
        this.connect();
        while (!Thread.interrupted()) {
            final HttpPipelineRequest nextRequestSend = this.httpPipelineConnection.getNextRequestSend();
            this.writeRequest(nextRequestSend.getHttpRequest(), this.httpPipelineConnection.getOutputStream());
            this.httpPipelineConnection.onRequestSent(nextRequestSend);
        }
    }
    
    private void connect() throws IOException {
        final String host = this.httpPipelineConnection.getHost();
        final int port = this.httpPipelineConnection.getPort();
        final Socket socket = new Socket(this.httpPipelineConnection.getProxy());
        socket.connect(new InetSocketAddress(host, port), 5000);
        this.httpPipelineConnection.setSocket(socket);
    }
    
    private void writeRequest(final HttpRequest httpRequest, final OutputStream outputStream) throws IOException {
        this.write(outputStream, String.valueOf(httpRequest.getMethod()) + " " + httpRequest.getFile() + " " + httpRequest.getHttp() + "\r\n");
        for (final String s : httpRequest.getHeaders().keySet()) {
            this.write(outputStream, String.valueOf(s) + ": " + (String)httpRequest.getHeaders().get(s) + "\r\n");
        }
        this.write(outputStream, "\r\n");
    }
    
    private void write(final OutputStream outputStream, final String s) throws IOException {
        outputStream.write(s.getBytes(HttpPipelineSender.ASCII));
    }
}
