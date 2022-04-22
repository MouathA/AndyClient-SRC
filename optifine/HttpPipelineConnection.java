package optifine;

import java.net.*;
import java.util.regex.*;
import java.util.*;
import java.io.*;

public class HttpPipelineConnection
{
    private String host;
    private int port;
    private Proxy proxy;
    private List listRequests;
    private List listRequestsSend;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private HttpPipelineSender httpPipelineSender;
    private HttpPipelineReceiver httpPipelineReceiver;
    private int countRequests;
    private boolean responseReceived;
    private long keepaliveTimeoutMs;
    private int keepaliveMaxCount;
    private long timeLastActivityMs;
    private boolean terminated;
    private static final String LF;
    private static final Pattern patternFullUrl;
    
    static {
        LF = "\n";
        patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
    }
    
    public HttpPipelineConnection(final String s, final int n) {
        this(s, n, Proxy.NO_PROXY);
    }
    
    public HttpPipelineConnection(final String host, final int port, final Proxy proxy) {
        this.host = null;
        this.port = 0;
        this.proxy = Proxy.NO_PROXY;
        this.listRequests = new LinkedList();
        this.listRequestsSend = new LinkedList();
        this.socket = null;
        this.inputStream = null;
        this.outputStream = null;
        this.httpPipelineSender = null;
        this.httpPipelineReceiver = null;
        this.countRequests = 0;
        this.responseReceived = false;
        this.keepaliveTimeoutMs = 5000L;
        this.keepaliveMaxCount = 1000;
        this.timeLastActivityMs = System.currentTimeMillis();
        this.terminated = false;
        this.host = host;
        this.port = port;
        this.proxy = proxy;
        (this.httpPipelineSender = new HttpPipelineSender(this)).start();
        (this.httpPipelineReceiver = new HttpPipelineReceiver(this)).start();
    }
    
    public synchronized boolean addRequest(final HttpPipelineRequest httpPipelineRequest) {
        if (this != 0) {
            return false;
        }
        this.addRequest(httpPipelineRequest, this.listRequests);
        this.addRequest(httpPipelineRequest, this.listRequestsSend);
        ++this.countRequests;
        return true;
    }
    
    private void addRequest(final HttpPipelineRequest httpPipelineRequest, final List list) {
        list.add(httpPipelineRequest);
        this.notifyAll();
    }
    
    public synchronized void setSocket(final Socket socket) throws IOException {
        if (!this.terminated) {
            if (this.socket != null) {
                throw new IllegalArgumentException("Already connected");
            }
            (this.socket = socket).setTcpNoDelay(true);
            this.inputStream = this.socket.getInputStream();
            this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
            this.onActivity();
            this.notifyAll();
        }
    }
    
    public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
        while (this.outputStream == null) {
            this.checkTimeout();
            this.wait(1000L);
        }
        return this.outputStream;
    }
    
    public synchronized InputStream getInputStream() throws IOException, InterruptedException {
        while (this.inputStream == null) {
            this.checkTimeout();
            this.wait(1000L);
        }
        return this.inputStream;
    }
    
    public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
        if (this.listRequestsSend.size() <= 0 && this.outputStream != null) {
            this.outputStream.flush();
        }
        return this.getNextRequest(this.listRequestsSend, true);
    }
    
    public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
        return this.getNextRequest(this.listRequests, false);
    }
    
    private HttpPipelineRequest getNextRequest(final List list, final boolean b) throws InterruptedException {
        while (list.size() <= 0) {
            this.checkTimeout();
            this.wait(1000L);
        }
        this.onActivity();
        if (b) {
            return list.remove(0);
        }
        return list.get(0);
    }
    
    private void checkTimeout() {
        if (this.socket != null) {
            long keepaliveTimeoutMs = this.keepaliveTimeoutMs;
            if (this.listRequests.size() > 0) {
                keepaliveTimeoutMs = 5000L;
            }
            if (System.currentTimeMillis() > this.timeLastActivityMs + keepaliveTimeoutMs) {
                this.terminate(new InterruptedException("Timeout " + keepaliveTimeoutMs));
            }
        }
    }
    
    private void onActivity() {
        this.timeLastActivityMs = System.currentTimeMillis();
    }
    
    public synchronized void onRequestSent(final HttpPipelineRequest httpPipelineRequest) {
        if (!this.terminated) {
            this.onActivity();
        }
    }
    
    public synchronized void onResponseReceived(final HttpPipelineRequest httpPipelineRequest, final HttpResponse httpResponse) {
        if (!this.terminated) {
            this.responseReceived = true;
            this.onActivity();
            if (this.listRequests.size() <= 0 || this.listRequests.get(0) != httpPipelineRequest) {
                throw new IllegalArgumentException("Response out of order: " + httpPipelineRequest);
            }
            this.listRequests.remove(0);
            httpPipelineRequest.setClosed(true);
            final String header = httpResponse.getHeader("Location");
            if (httpResponse.getStatus() / 100 == 3 && header != null && httpPipelineRequest.getHttpRequest().getRedirects() < 5) {
                final HttpRequest request = HttpPipeline.makeRequest(this.normalizeUrl(header, httpPipelineRequest.getHttpRequest()), httpPipelineRequest.getHttpRequest().getProxy());
                request.setRedirects(httpPipelineRequest.getHttpRequest().getRedirects() + 1);
                HttpPipeline.addRequest(new HttpPipelineRequest(request, httpPipelineRequest.getHttpListener()));
            }
            else {
                httpPipelineRequest.getHttpListener().finished(httpPipelineRequest.getHttpRequest(), httpResponse);
            }
            this.checkResponseHeader(httpResponse);
        }
    }
    
    private String normalizeUrl(final String s, final HttpRequest httpRequest) {
        if (HttpPipelineConnection.patternFullUrl.matcher(s).matches()) {
            return s;
        }
        if (s.startsWith("//")) {
            return "http:" + s;
        }
        String s2 = httpRequest.getHost();
        if (httpRequest.getPort() != 80) {
            s2 = String.valueOf(s2) + ":" + httpRequest.getPort();
        }
        if (s.startsWith("/")) {
            return "http://" + s2 + s;
        }
        final String file = httpRequest.getFile();
        final int lastIndex = file.lastIndexOf("/");
        return (lastIndex >= 0) ? ("http://" + s2 + file.substring(0, lastIndex + 1) + s) : ("http://" + s2 + "/" + s);
    }
    
    private void checkResponseHeader(final HttpResponse httpResponse) {
        final String header = httpResponse.getHeader("Connection");
        if (header != null && !header.toLowerCase().equals("keep-alive")) {
            this.terminate(new EOFException("Connection not keep-alive"));
        }
        final String header2 = httpResponse.getHeader("Keep-Alive");
        if (header2 != null) {
            final String[] tokenize = Config.tokenize(header2, ",;");
            while (0 < tokenize.length) {
                final String[] split = this.split(tokenize[0], '=');
                if (split.length >= 2) {
                    if (split[0].equals("timeout")) {
                        final int int1 = Config.parseInt(split[1], -1);
                        if (int1 > 0) {
                            this.keepaliveTimeoutMs = int1 * 1000;
                        }
                    }
                    if (split[0].equals("max")) {
                        final int int2 = Config.parseInt(split[1], -1);
                        if (int2 > 0) {
                            this.keepaliveMaxCount = int2;
                        }
                    }
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    private String[] split(final String s, final char c) {
        final int index = s.indexOf(c);
        if (index < 0) {
            return new String[] { s };
        }
        return new String[] { s.substring(0, index), s.substring(index + 1) };
    }
    
    public synchronized void onExceptionSend(final HttpPipelineRequest httpPipelineRequest, final Exception ex) {
        this.terminate(ex);
    }
    
    public synchronized void onExceptionReceive(final HttpPipelineRequest httpPipelineRequest, final Exception ex) {
        this.terminate(ex);
    }
    
    private synchronized void terminate(final Exception ex) {
        if (!this.terminated) {
            this.terminated = true;
            this.terminateRequests(ex);
            if (this.httpPipelineSender != null) {
                this.httpPipelineSender.interrupt();
            }
            if (this.httpPipelineReceiver != null) {
                this.httpPipelineReceiver.interrupt();
            }
            if (this.socket != null) {
                this.socket.close();
            }
            this.socket = null;
            this.inputStream = null;
            this.outputStream = null;
        }
    }
    
    private void terminateRequests(final Exception ex) {
        if (this.listRequests.size() > 0) {
            if (!this.responseReceived) {
                final HttpPipelineRequest httpPipelineRequest = this.listRequests.remove(0);
                httpPipelineRequest.getHttpListener().failed(httpPipelineRequest.getHttpRequest(), ex);
                httpPipelineRequest.setClosed(true);
            }
            while (this.listRequests.size() > 0) {
                HttpPipeline.addRequest(this.listRequests.remove(0));
            }
        }
    }
    
    public int getCountRequests() {
        return this.countRequests;
    }
    
    public synchronized boolean hasActiveRequests() {
        return this.listRequests.size() > 0;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public Proxy getProxy() {
        return this.proxy;
    }
}
