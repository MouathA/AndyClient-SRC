package net.minecraft.util;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.server.*;
import com.google.common.util.concurrent.*;
import org.apache.commons.io.*;
import java.io.*;
import java.net.*;

public class HttpUtil
{
    public static final ListeningExecutorService field_180193_a;
    private static final AtomicInteger downloadThreadsStarted;
    private static final Logger logger;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001485";
        field_180193_a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Downloader %d").build()));
        downloadThreadsStarted = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    public static String buildPostString(final Map map) {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            if (entry.getValue() != null) {
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            }
        }
        return sb.toString();
    }
    
    public static String postMap(final URL url, final Map map, final boolean b) {
        return post(url, buildPostString(map), b);
    }
    
    private static String post(final URL url, final String s, final boolean b) {
        Proxy no_PROXY = (MinecraftServer.getServer() == null) ? null : MinecraftServer.getServer().getServerProxy();
        if (no_PROXY == null) {
            no_PROXY = Proxy.NO_PROXY;
        }
        final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection(no_PROXY);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", new StringBuilder().append(s.getBytes().length).toString());
        httpURLConnection.setRequestProperty("Content-Language", "en-US");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.writeBytes(s);
        dataOutputStream.flush();
        dataOutputStream.close();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        final StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append('\r');
        }
        bufferedReader.close();
        return sb.toString();
    }
    
    public static ListenableFuture func_180192_a(final File file, final String s, final Map map, final int n, final IProgressUpdate progressUpdate, final Proxy proxy) {
        return HttpUtil.field_180193_a.submit((Runnable)new Runnable(s, proxy, map, file, n) {
            private static final String __OBFID;
            private final IProgressUpdate val$p_180192_4_;
            private final String val$p_180192_1_;
            private final Proxy val$p_180192_5_;
            private final Map val$p_180192_2_;
            private final File val$p_180192_0_;
            private final int val$p_180192_3_;
            
            @Override
            public void run() {
                final OutputStream outputStream = null;
                if (this.val$p_180192_4_ != null) {
                    this.val$p_180192_4_.resetProgressAndMessage("Downloading Resource Pack");
                    this.val$p_180192_4_.displayLoadingString("Making Request...");
                }
                final byte[] array = new byte[4096];
                final URLConnection openConnection = new URL(this.val$p_180192_1_).openConnection(this.val$p_180192_5_);
                float n = 0.0f;
                final float n2 = (float)this.val$p_180192_2_.entrySet().size();
                for (final Map.Entry<String, V> entry : this.val$p_180192_2_.entrySet()) {
                    openConnection.setRequestProperty(entry.getKey(), (String)entry.getValue());
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setLoadingProgress((int)(++n / n2 * 100.0f));
                    }
                }
                final InputStream inputStream = openConnection.getInputStream();
                final float n3 = (float)openConnection.getContentLength();
                final int contentLength = openConnection.getContentLength();
                if (this.val$p_180192_4_ != null) {
                    this.val$p_180192_4_.displayLoadingString(String.format("Downloading file (%.2f MB)...", n3 / 1000.0f / 1000.0f));
                }
                if (this.val$p_180192_0_.exists()) {
                    final long length = this.val$p_180192_0_.length();
                    if (length == contentLength) {
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setDoneWorking();
                        }
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly(outputStream);
                        return;
                    }
                    HttpUtil.access$0().warn("Deleting " + this.val$p_180192_0_ + " as it does not match what we currently have (" + contentLength + " vs our " + length + ").");
                    FileUtils.deleteQuietly(this.val$p_180192_0_);
                }
                else if (this.val$p_180192_0_.getParentFile() != null) {
                    this.val$p_180192_0_.getParentFile().mkdirs();
                }
                final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(this.val$p_180192_0_));
                if (this.val$p_180192_3_ > 0 && n3 > this.val$p_180192_3_) {
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setDoneWorking();
                    }
                    throw new IOException("Filesize is bigger than maximum allowed (file is " + n + ", limit is " + this.val$p_180192_3_ + ")");
                }
                int read;
                while ((read = inputStream.read(array)) >= 0) {
                    n += read;
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setLoadingProgress((int)(n / n3 * 100.0f));
                    }
                    if (this.val$p_180192_3_ > 0 && n > this.val$p_180192_3_) {
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setDoneWorking();
                        }
                        throw new IOException("Filesize was bigger than maximum allowed (got >= " + n + ", limit was " + this.val$p_180192_3_ + ")");
                    }
                    dataOutputStream.write(array, 0, read);
                }
                if (this.val$p_180192_4_ != null) {
                    this.val$p_180192_4_.setDoneWorking();
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(dataOutputStream);
                    return;
                }
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(dataOutputStream);
            }
            
            static {
                __OBFID = "CL_00001486";
            }
        });
    }
    
    public static int getSuitableLanPort() throws IOException {
        final ServerSocket serverSocket = new ServerSocket(0);
        final int localPort = serverSocket.getLocalPort();
        if (serverSocket != null) {
            serverSocket.close();
        }
        return localPort;
    }
    
    public static String get(final URL url) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append('\r');
        }
        bufferedReader.close();
        return sb.toString();
    }
    
    static Logger access$0() {
        return HttpUtil.logger;
    }
}
