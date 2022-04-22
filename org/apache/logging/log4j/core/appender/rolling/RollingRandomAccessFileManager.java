package org.apache.logging.log4j.core.appender.rolling;

import java.nio.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.*;
import java.io.*;

public class RollingRandomAccessFileManager extends RollingFileManager
{
    static final int DEFAULT_BUFFER_SIZE = 262144;
    private static final RollingRandomAccessFileManagerFactory FACTORY;
    private final boolean isImmediateFlush;
    private RandomAccessFile randomAccessFile;
    private final ByteBuffer buffer;
    private final ThreadLocal isEndOfBatch;
    
    public RollingRandomAccessFileManager(final RandomAccessFile randomAccessFile, final String s, final String s2, final OutputStream outputStream, final boolean b, final boolean isImmediateFlush, final long n, final long n2, final TriggeringPolicy triggeringPolicy, final RolloverStrategy rolloverStrategy, final String s3, final Layout layout) {
        super(s, s2, outputStream, b, n, n2, triggeringPolicy, rolloverStrategy, s3, layout);
        this.isEndOfBatch = new ThreadLocal();
        this.isImmediateFlush = isImmediateFlush;
        this.randomAccessFile = randomAccessFile;
        this.isEndOfBatch.set(Boolean.FALSE);
        this.buffer = ByteBuffer.allocate(262144);
    }
    
    public static RollingRandomAccessFileManager getRollingRandomAccessFileManager(final String s, final String s2, final boolean b, final boolean b2, final TriggeringPolicy triggeringPolicy, final RolloverStrategy rolloverStrategy, final String s3, final Layout layout) {
        return (RollingRandomAccessFileManager)OutputStreamManager.getManager(s, new FactoryData(s2, b, b2, triggeringPolicy, rolloverStrategy, s3, layout), RollingRandomAccessFileManager.FACTORY);
    }
    
    public Boolean isEndOfBatch() {
        return this.isEndOfBatch.get();
    }
    
    public void setEndOfBatch(final boolean b) {
        this.isEndOfBatch.set(b);
    }
    
    @Override
    protected synchronized void write(final byte[] array, int n, int i) {
        super.write(array, n, i);
        do {
            if (i > this.buffer.remaining()) {
                this.flush();
            }
            Math.min(i, this.buffer.remaining());
            this.buffer.put(array, n, 0);
            n += 0;
            i -= 0;
        } while (i > 0);
        if (this.isImmediateFlush || this.isEndOfBatch.get() == Boolean.TRUE) {
            this.flush();
        }
    }
    
    @Override
    protected void createFileAfterRollover() throws IOException {
        this.randomAccessFile = new RandomAccessFile(this.getFileName(), "rw");
        if (this.isAppend()) {
            this.randomAccessFile.seek(this.randomAccessFile.length());
        }
    }
    
    @Override
    public synchronized void flush() {
        this.buffer.flip();
        this.randomAccessFile.write(this.buffer.array(), 0, this.buffer.limit());
        this.buffer.clear();
    }
    
    public synchronized void close() {
        this.flush();
        this.randomAccessFile.close();
    }
    
    static Logger access$200() {
        return RollingRandomAccessFileManager.LOGGER;
    }
    
    static Logger access$300() {
        return RollingRandomAccessFileManager.LOGGER;
    }
    
    static Logger access$1000() {
        return RollingRandomAccessFileManager.LOGGER;
    }
    
    static Logger access$1100() {
        return RollingRandomAccessFileManager.LOGGER;
    }
    
    static {
        FACTORY = new RollingRandomAccessFileManagerFactory(null);
    }
    
    private static class FactoryData
    {
        private final String pattern;
        private final boolean append;
        private final boolean immediateFlush;
        private final TriggeringPolicy policy;
        private final RolloverStrategy strategy;
        private final String advertiseURI;
        private final Layout layout;
        
        public FactoryData(final String pattern, final boolean append, final boolean immediateFlush, final TriggeringPolicy policy, final RolloverStrategy strategy, final String advertiseURI, final Layout layout) {
            this.pattern = pattern;
            this.append = append;
            this.immediateFlush = immediateFlush;
            this.policy = policy;
            this.strategy = strategy;
            this.advertiseURI = advertiseURI;
            this.layout = layout;
        }
        
        static boolean access$100(final FactoryData factoryData) {
            return factoryData.append;
        }
        
        static String access$400(final FactoryData factoryData) {
            return factoryData.pattern;
        }
        
        static boolean access$500(final FactoryData factoryData) {
            return factoryData.immediateFlush;
        }
        
        static TriggeringPolicy access$600(final FactoryData factoryData) {
            return factoryData.policy;
        }
        
        static RolloverStrategy access$700(final FactoryData factoryData) {
            return factoryData.strategy;
        }
        
        static String access$800(final FactoryData factoryData) {
            return factoryData.advertiseURI;
        }
        
        static Layout access$900(final FactoryData factoryData) {
            return factoryData.layout;
        }
    }
    
    static class DummyOutputStream extends OutputStream
    {
        @Override
        public void write(final int n) throws IOException {
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
        }
    }
    
    private static class RollingRandomAccessFileManagerFactory implements ManagerFactory
    {
        private RollingRandomAccessFileManagerFactory() {
        }
        
        public RollingRandomAccessFileManager createManager(final String s, final FactoryData factoryData) {
            final File file = new File(s);
            final File parentFile = file.getParentFile();
            if (null != parentFile && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!FactoryData.access$100(factoryData)) {
                file.delete();
            }
            final long n = FactoryData.access$100(factoryData) ? file.length() : 0L;
            final long n2 = file.exists() ? file.lastModified() : System.currentTimeMillis();
            final RandomAccessFile randomAccessFile = new RandomAccessFile(s, "rw");
            if (FactoryData.access$100(factoryData)) {
                final long length = randomAccessFile.length();
                RollingRandomAccessFileManager.access$200().trace("RandomAccessFile {} seek to {}", s, length);
                randomAccessFile.seek(length);
            }
            else {
                RollingRandomAccessFileManager.access$300().trace("RandomAccessFile {} set length to 0", s);
                randomAccessFile.setLength(0L);
            }
            return new RollingRandomAccessFileManager(randomAccessFile, s, FactoryData.access$400(factoryData), new DummyOutputStream(), FactoryData.access$100(factoryData), FactoryData.access$500(factoryData), n, n2, FactoryData.access$600(factoryData), FactoryData.access$700(factoryData), FactoryData.access$800(factoryData), FactoryData.access$900(factoryData));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        RollingRandomAccessFileManagerFactory(final RollingRandomAccessFileManager$1 object) {
            this();
        }
    }
}
