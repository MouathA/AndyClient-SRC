package com.mojang.realmsclient.client;

import org.apache.http.client.config.*;
import org.apache.http.impl.client.*;
import com.mojang.realmsclient.gui.screens.*;
import org.apache.commons.io.*;
import org.apache.http.client.methods.*;
import org.apache.commons.lang3.*;
import net.minecraft.realms.*;
import org.apache.commons.compress.compressors.gzip.*;
import java.util.*;
import java.util.regex.*;
import org.apache.commons.compress.archivers.tar.*;
import java.io.*;
import org.apache.logging.log4j.*;
import org.apache.commons.io.output.*;
import java.awt.event.*;

public class FileDownload
{
    private static final Logger LOGGER;
    private boolean cancelled;
    private boolean finished;
    private boolean error;
    private boolean extracting;
    private File tempFile;
    private HttpGet request;
    private Thread currentThread;
    private RequestConfig requestConfig;
    
    public FileDownload() {
        this.cancelled = false;
        this.finished = false;
        this.error = false;
        this.extracting = false;
        this.requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
    }
    
    public long contentLength(final String s) {
        final HttpGet httpGet = new HttpGet(s);
        final CloseableHttpClient build = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
        final long long1 = Long.parseLong(build.execute((HttpUriRequest)httpGet).getFirstHeader("Content-Length").getValue());
        if (httpGet != null) {
            httpGet.releaseConnection();
        }
        if (build != null) {
            build.close();
        }
        return long1;
    }
    
    public void download(final String s, final String s2, final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus, final RealmsAnvilLevelStorageSource realmsAnvilLevelStorageSource) {
        if (this.currentThread != null) {
            return;
        }
        (this.currentThread = new Thread(s, downloadStatus, s2, realmsAnvilLevelStorageSource) {
            final String val$downloadLink;
            final RealmsDownloadLatestWorldScreen.DownloadStatus val$downloadStatus;
            final String val$worldName;
            final RealmsAnvilLevelStorageSource val$levelStorageSource;
            final FileDownload this$0;
            
            @Override
            public void run() {
                FileDownload.access$002(this.this$0, File.createTempFile("backup", ".tar.gz"));
                FileDownload.access$102(this.this$0, new HttpGet(this.val$downloadLink));
                final CloseableHttpClient build = HttpClientBuilder.create().setDefaultRequestConfig(FileDownload.access$200(this.this$0)).build();
                final CloseableHttpResponse execute = build.execute((HttpUriRequest)FileDownload.access$100(this.this$0));
                this.val$downloadStatus.totalBytes = Long.parseLong(execute.getFirstHeader("Content-Length").getValue());
                if (execute.getStatusLine().getStatusCode() != 200) {
                    FileDownload.access$302(this.this$0, true);
                    FileDownload.access$100(this.this$0).abort();
                    FileDownload.access$100(this.this$0).releaseConnection();
                    if (FileDownload.access$000(this.this$0) != null) {
                        FileDownload.access$000(this.this$0).delete();
                    }
                    if (build != null) {
                        build.close();
                    }
                    return;
                }
                final FileOutputStream fileOutputStream = new FileOutputStream(FileDownload.access$000(this.this$0));
                final ProgressListener listener = this.this$0.new ProgressListener(this.val$worldName.trim(), FileDownload.access$000(this.this$0), this.val$levelStorageSource, this.val$downloadStatus, null);
                final DownloadCountingOutputStream downloadCountingOutputStream = this.this$0.new DownloadCountingOutputStream(fileOutputStream);
                downloadCountingOutputStream.setListener(listener);
                IOUtils.copy(execute.getEntity().getContent(), downloadCountingOutputStream);
                FileDownload.access$100(this.this$0).releaseConnection();
                if (FileDownload.access$000(this.this$0) != null) {
                    FileDownload.access$000(this.this$0).delete();
                }
                if (build != null) {
                    build.close();
                }
            }
        }).start();
    }
    
    public void cancel() {
        if (this.request != null) {
            this.request.abort();
        }
        if (this.tempFile != null) {
            this.tempFile.delete();
        }
        this.cancelled = true;
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public boolean isError() {
        return this.error;
    }
    
    public boolean isExtracting() {
        return this.extracting;
    }
    
    public static String findAvailableFolderName(String s) {
        s = s.replaceAll("[\\./\"]", "_");
        final String[] invalid_FILE_NAMES = FileDownload.INVALID_FILE_NAMES;
        while (0 < invalid_FILE_NAMES.length) {
            if (s.equalsIgnoreCase(invalid_FILE_NAMES[0])) {
                s = "_" + s + "_";
            }
            int n = 0;
            ++n;
        }
        return s;
    }
    
    private void untarGzipArchive(String s, final File file, final RealmsAnvilLevelStorageSource realmsAnvilLevelStorageSource) throws IOException {
        final Pattern compile = Pattern.compile(".*-([0-9]+)$");
        final char[] illegal_FILE_CHARACTERS = RealmsSharedConstants.ILLEGAL_FILE_CHARACTERS;
        while (0 < illegal_FILE_CHARACTERS.length) {
            s = s.replace(illegal_FILE_CHARACTERS[0], '_');
            int n = 0;
            ++n;
        }
        if (StringUtils.isEmpty(s)) {
            s = "Realm";
        }
        s = findAvailableFolderName(s);
        int intValue = 0;
        for (final RealmsLevelSummary realmsLevelSummary : realmsAnvilLevelStorageSource.getLevelList()) {
            if (realmsLevelSummary.getLevelId().toLowerCase().startsWith(s.toLowerCase())) {
                final Matcher matcher = compile.matcher(realmsLevelSummary.getLevelId());
                if (matcher.matches()) {
                    if (Integer.valueOf(matcher.group(1)) <= 1) {
                        continue;
                    }
                    intValue = Integer.valueOf(matcher.group(1));
                }
                else {
                    ++intValue;
                }
            }
        }
        String s2;
        if (!realmsAnvilLevelStorageSource.isNewLevelIdAcceptable(s) || 1 > 1) {
            s2 = s + ((true == true) ? "" : ("-" + 1));
            if (!realmsAnvilLevelStorageSource.isNewLevelIdAcceptable(s2)) {
                while (!true) {
                    ++intValue;
                    s2 = s + ((true == true) ? "" : ("-" + 1));
                    if (realmsAnvilLevelStorageSource.isNewLevelIdAcceptable(s2)) {
                        continue;
                    }
                }
            }
        }
        else {
            s2 = s;
        }
        final File file2 = new File(Realms.getGameDirectoryPath(), "saves");
        file2.mkdir();
        final TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(file))));
        for (TarArchiveEntry tarArchiveEntry = tarArchiveInputStream.getNextTarEntry(); tarArchiveEntry != null; tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()) {
            final File file3 = new File(file2, tarArchiveEntry.getName().replace("world", s2));
            if (tarArchiveEntry.isDirectory()) {
                file3.mkdirs();
            }
            else {
                file3.createNewFile();
                final byte[] array = new byte[1024];
                final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file3));
                while (tarArchiveInputStream.read(array) != -1) {
                    bufferedOutputStream.write(array, 0, 0);
                }
                bufferedOutputStream.close();
            }
        }
        if (tarArchiveInputStream != null) {
            tarArchiveInputStream.close();
        }
        if (file != null) {
            file.delete();
        }
        realmsAnvilLevelStorageSource.renameLevel(s2, s2.trim());
        this.finished = true;
    }
    
    static File access$002(final FileDownload fileDownload, final File tempFile) {
        return fileDownload.tempFile = tempFile;
    }
    
    static HttpGet access$102(final FileDownload fileDownload, final HttpGet request) {
        return fileDownload.request = request;
    }
    
    static RequestConfig access$200(final FileDownload fileDownload) {
        return fileDownload.requestConfig;
    }
    
    static HttpGet access$100(final FileDownload fileDownload) {
        return fileDownload.request;
    }
    
    static boolean access$302(final FileDownload fileDownload, final boolean error) {
        return fileDownload.error = error;
    }
    
    static File access$000(final FileDownload fileDownload) {
        return fileDownload.tempFile;
    }
    
    static Logger access$500() {
        return FileDownload.LOGGER;
    }
    
    static boolean access$600(final FileDownload fileDownload) {
        return fileDownload.cancelled;
    }
    
    static boolean access$702(final FileDownload fileDownload, final boolean extracting) {
        return fileDownload.extracting = extracting;
    }
    
    static void access$800(final FileDownload fileDownload, final String s, final File file, final RealmsAnvilLevelStorageSource realmsAnvilLevelStorageSource) throws IOException {
        fileDownload.untarGzipArchive(s, file, realmsAnvilLevelStorageSource);
    }
    
    static {
        LOGGER = LogManager.getLogger();
        FileDownload.INVALID_FILE_NAMES = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
    }
    
    private class DownloadCountingOutputStream extends CountingOutputStream
    {
        private ActionListener listener;
        final FileDownload this$0;
        
        public DownloadCountingOutputStream(final FileDownload this$0, final OutputStream outputStream) {
            this.this$0 = this$0;
            super(outputStream);
            this.listener = null;
        }
        
        public void setListener(final ActionListener listener) {
            this.listener = listener;
        }
        
        @Override
        protected void afterWrite(final int n) throws IOException {
            super.afterWrite(n);
            if (this.listener != null) {
                this.listener.actionPerformed(new ActionEvent(this, 0, null));
            }
        }
    }
    
    private class ProgressListener implements ActionListener
    {
        private String worldName;
        private File tempFile;
        private RealmsAnvilLevelStorageSource levelStorageSource;
        private RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;
        final FileDownload this$0;
        
        private ProgressListener(final FileDownload this$0, final String worldName, final File tempFile, final RealmsAnvilLevelStorageSource levelStorageSource, final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus) {
            this.this$0 = this$0;
            this.worldName = worldName;
            this.tempFile = tempFile;
            this.levelStorageSource = levelStorageSource;
            this.downloadStatus = downloadStatus;
        }
        
        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            this.downloadStatus.bytesWritten = ((DownloadCountingOutputStream)actionEvent.getSource()).getByteCount();
            if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.access$600(this.this$0)) {
                FileDownload.access$702(this.this$0, true);
                FileDownload.access$800(this.this$0, this.worldName, this.tempFile, this.levelStorageSource);
            }
        }
        
        ProgressListener(final FileDownload fileDownload, final String s, final File file, final RealmsAnvilLevelStorageSource realmsAnvilLevelStorageSource, final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus, final FileDownload$1 thread) {
            this(fileDownload, s, file, realmsAnvilLevelStorageSource, downloadStatus);
        }
    }
}
