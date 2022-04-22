package com.mojang.realmsclient.client;

import org.apache.http.client.config.*;
import com.mojang.realmsclient.dto.*;
import com.mojang.realmsclient.*;
import org.apache.http.*;
import com.google.gson.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.methods.*;
import org.apache.logging.log4j.*;
import org.apache.http.entity.*;
import org.apache.http.util.*;
import java.io.*;

public class FileUpload
{
    private static final Logger LOGGER;
    private static final String UPLOAD_PATH = "/upload";
    private static final String PORT = "8080";
    private boolean cancelled;
    private boolean finished;
    private HttpPost request;
    private int statusCode;
    private String errorMessage;
    private RequestConfig requestConfig;
    private Thread currentThread;
    
    public FileUpload() {
        this.cancelled = false;
        this.finished = false;
        this.statusCode = -1;
        this.requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
    }
    
    public void upload(final File file, final long n, final int n2, final UploadInfo uploadInfo, final String s, final String s2, final String s3, final UploadStatus uploadStatus) {
        if (this.currentThread != null) {
            return;
        }
        (this.currentThread = new Thread(uploadInfo, n, n2, s, s2, s3, uploadStatus, file) {
            final UploadInfo val$uploadInfo;
            final long val$worldId;
            final int val$slotId;
            final String val$sessionId;
            final String val$username;
            final String val$clientVersion;
            final UploadStatus val$uploadStatus;
            final File val$file;
            final FileUpload this$0;
            
            @Override
            public void run() {
                FileUpload.access$002(this.this$0, new HttpPost("http://" + this.val$uploadInfo.getUploadEndpoint() + ":" + "8080" + "/upload" + "/" + String.valueOf(this.val$worldId) + "/" + String.valueOf(this.val$slotId)));
                final CloseableHttpClient build = HttpClientBuilder.create().setDefaultRequestConfig(FileUpload.access$100(this.this$0)).build();
                final String version = RealmsVersion.getVersion();
                if (version != null) {
                    FileUpload.access$000(this.this$0).setHeader("Cookie", "sid=" + this.val$sessionId + ";token=" + this.val$uploadInfo.getToken() + ";user=" + this.val$username + ";version=" + this.val$clientVersion + ";realms_version=" + version);
                }
                else {
                    FileUpload.access$000(this.this$0).setHeader("Cookie", "sid=" + this.val$sessionId + ";token=" + this.val$uploadInfo.getToken() + ";user=" + this.val$username + ";version=" + this.val$clientVersion);
                }
                this.val$uploadStatus.totalBytes = this.val$file.length();
                final CustomInputStreamEntity entity = new CustomInputStreamEntity(new FileInputStream(this.val$file), this.val$file.length(), this.val$uploadStatus);
                entity.setContentType("application/octet-stream");
                FileUpload.access$000(this.this$0).setEntity(entity);
                final CloseableHttpResponse execute = build.execute((HttpUriRequest)FileUpload.access$000(this.this$0));
                final int statusCode = execute.getStatusLine().getStatusCode();
                if (statusCode == 401) {
                    FileUpload.access$200().debug("Realms server returned 401: " + execute.getFirstHeader("WWW-Authenticate"));
                }
                FileUpload.access$302(this.this$0, statusCode);
                final String string = EntityUtils.toString(execute.getEntity(), "UTF-8");
                if (string != null) {
                    FileUpload.access$402(this.this$0, new JsonParser().parse(string).getAsJsonObject().get("errorMsg").getAsString());
                }
                FileUpload.access$000(this.this$0).releaseConnection();
                FileUpload.access$502(this.this$0, true);
                if (build != null) {
                    build.close();
                }
            }
        }).start();
    }
    
    public void cancel() {
        this.cancelled = true;
        if (this.request != null) {
            this.request.abort();
        }
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public int getStatusCode() {
        return this.statusCode;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }
    
    static HttpPost access$002(final FileUpload fileUpload, final HttpPost request) {
        return fileUpload.request = request;
    }
    
    static RequestConfig access$100(final FileUpload fileUpload) {
        return fileUpload.requestConfig;
    }
    
    static HttpPost access$000(final FileUpload fileUpload) {
        return fileUpload.request;
    }
    
    static Logger access$200() {
        return FileUpload.LOGGER;
    }
    
    static int access$302(final FileUpload fileUpload, final int statusCode) {
        return fileUpload.statusCode = statusCode;
    }
    
    static String access$402(final FileUpload fileUpload, final String errorMessage) {
        return fileUpload.errorMessage = errorMessage;
    }
    
    static boolean access$502(final FileUpload fileUpload, final boolean finished) {
        return fileUpload.finished = finished;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    private static class CustomInputStreamEntity extends InputStreamEntity
    {
        private final long length;
        private final InputStream content;
        private final UploadStatus uploadStatus;
        
        public CustomInputStreamEntity(final InputStream content, final long length, final UploadStatus uploadStatus) {
            super(content);
            this.content = content;
            this.length = length;
            this.uploadStatus = uploadStatus;
        }
        
        @Override
        public void writeTo(final OutputStream outputStream) throws IOException {
            Args.notNull(outputStream, "Output stream");
            final InputStream content = this.content;
            final byte[] array = new byte[4096];
            if (this.length < 0L) {
                int read;
                while ((read = content.read(array)) != -1) {
                    outputStream.write(array, 0, read);
                    final UploadStatus uploadStatus = this.uploadStatus;
                    uploadStatus.bytesWritten += (Long)read;
                }
            }
            else {
                long length = this.length;
                while (length > 0L) {
                    final int read2 = content.read(array, 0, (int)Math.min(4096L, length));
                    if (read2 == -1) {
                        break;
                    }
                    outputStream.write(array, 0, read2);
                    final UploadStatus uploadStatus2 = this.uploadStatus;
                    uploadStatus2.bytesWritten += (Long)read2;
                    length -= read2;
                    outputStream.flush();
                }
            }
            content.close();
        }
    }
}
