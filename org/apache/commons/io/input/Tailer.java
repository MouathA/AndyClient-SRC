package org.apache.commons.io.input;

import org.apache.commons.io.*;
import java.io.*;

public class Tailer implements Runnable
{
    private static final int DEFAULT_DELAY_MILLIS = 1000;
    private static final String RAF_MODE = "r";
    private static final int DEFAULT_BUFSIZE = 4096;
    private final byte[] inbuf;
    private final File file;
    private final long delayMillis;
    private final boolean end;
    private final TailerListener listener;
    private final boolean reOpen;
    private boolean run;
    
    public Tailer(final File file, final TailerListener tailerListener) {
        this(file, tailerListener, 1000L);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n) {
        this(file, tailerListener, n, false);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n, final boolean b) {
        this(file, tailerListener, n, b, 4096);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n, final boolean b, final boolean b2) {
        this(file, tailerListener, n, b, b2, 4096);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n, final boolean b, final int n2) {
        this(file, tailerListener, n, b, false, n2);
    }
    
    public Tailer(final File file, final TailerListener listener, final long delayMillis, final boolean end, final boolean reOpen, final int n) {
        this.run = true;
        this.file = file;
        this.delayMillis = delayMillis;
        this.end = end;
        this.inbuf = new byte[n];
        (this.listener = listener).init(this);
        this.reOpen = reOpen;
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n, final boolean b, final int n2) {
        final Tailer tailer = new Tailer(file, tailerListener, n, b, n2);
        final Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n, final boolean b, final boolean b2, final int n2) {
        final Tailer tailer = new Tailer(file, tailerListener, n, b, b2, n2);
        final Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n, final boolean b) {
        return create(file, tailerListener, n, b, 4096);
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n, final boolean b, final boolean b2) {
        return create(file, tailerListener, n, b, b2, 4096);
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n) {
        return create(file, tailerListener, n, false);
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener) {
        return create(file, tailerListener, 1000L, false);
    }
    
    public File getFile() {
        return this.file;
    }
    
    public long getDelay() {
        return this.delayMillis;
    }
    
    @Override
    public void run() {
        RandomAccessFile randomAccessFile = null;
        long n = 0L;
        long n2 = 0L;
        while (this.run && randomAccessFile == null) {
            randomAccessFile = new RandomAccessFile(this.file, "r");
            if (randomAccessFile == null) {
                Thread.sleep(this.delayMillis);
            }
            else {
                n2 = (this.end ? this.file.length() : 0L);
                n = System.currentTimeMillis();
                randomAccessFile.seek(n2);
            }
        }
        while (this.run) {
            final boolean fileNewer = FileUtils.isFileNewer(this.file, n);
            final long length = this.file.length();
            if (length < n2) {
                this.listener.fileRotated();
                final RandomAccessFile randomAccessFile2 = randomAccessFile;
                randomAccessFile = new RandomAccessFile(this.file, "r");
                n2 = 0L;
                IOUtils.closeQuietly(randomAccessFile2);
            }
            else {
                if (length > n2) {
                    n2 = this.readLines(randomAccessFile);
                    n = System.currentTimeMillis();
                }
                else if (fileNewer) {
                    randomAccessFile.seek(0L);
                    n2 = this.readLines(randomAccessFile);
                    n = System.currentTimeMillis();
                }
                if (this.reOpen) {
                    IOUtils.closeQuietly(randomAccessFile);
                }
                Thread.sleep(this.delayMillis);
                if (!this.run || !this.reOpen) {
                    continue;
                }
                randomAccessFile = new RandomAccessFile(this.file, "r");
                randomAccessFile.seek(n2);
            }
        }
        IOUtils.closeQuietly(randomAccessFile);
    }
    
    public void stop() {
        this.run = false;
    }
    
    private long readLines(final RandomAccessFile randomAccessFile) throws IOException {
        final StringBuilder sb = new StringBuilder();
        long filePointer2;
        long filePointer = filePointer2 = randomAccessFile.getFilePointer();
        int read;
        while (this.run && (read = randomAccessFile.read(this.inbuf)) != -1) {
            while (0 < read) {
                final byte b = this.inbuf[0];
                switch (b) {
                    case 10: {
                        this.listener.handle(sb.toString());
                        sb.setLength(0);
                        filePointer2 = filePointer + 0 + 1L;
                        break;
                    }
                    case 13: {
                        if (false) {
                            sb.append('\r');
                        }
                        break;
                    }
                    default: {
                        if (false) {
                            this.listener.handle(sb.toString());
                            sb.setLength(0);
                            filePointer2 = filePointer + 0 + 1L;
                        }
                        sb.append((char)b);
                        break;
                    }
                }
                int n = 0;
                ++n;
            }
            filePointer = randomAccessFile.getFilePointer();
        }
        randomAccessFile.seek(filePointer2);
        return filePointer2;
    }
}
