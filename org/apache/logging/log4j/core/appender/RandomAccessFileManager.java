package org.apache.logging.log4j.core.appender;

import java.nio.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import java.io.*;

public class RandomAccessFileManager extends OutputStreamManager
{
    static final int DEFAULT_BUFFER_SIZE = 262144;
    private static final RandomAccessFileManagerFactory FACTORY;
    private final boolean isImmediateFlush;
    private final String advertiseURI;
    private final RandomAccessFile randomAccessFile;
    private final ByteBuffer buffer;
    private final ThreadLocal isEndOfBatch;
    
    protected RandomAccessFileManager(final RandomAccessFile randomAccessFile, final String s, final OutputStream outputStream, final boolean isImmediateFlush, final String advertiseURI, final Layout layout) {
        super(outputStream, s, layout);
        this.isEndOfBatch = new ThreadLocal();
        this.isImmediateFlush = isImmediateFlush;
        this.randomAccessFile = randomAccessFile;
        this.advertiseURI = advertiseURI;
        this.isEndOfBatch.set(Boolean.FALSE);
        this.buffer = ByteBuffer.allocate(262144);
    }
    
    public static RandomAccessFileManager getFileManager(final String s, final boolean b, final boolean b2, final String s2, final Layout layout) {
        return (RandomAccessFileManager)OutputStreamManager.getManager(s, new FactoryData(b, b2, s2, layout), RandomAccessFileManager.FACTORY);
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
    public synchronized void flush() {
        this.buffer.flip();
        this.randomAccessFile.write(this.buffer.array(), 0, this.buffer.limit());
        this.buffer.clear();
    }
    
    public synchronized void close() {
        this.flush();
        this.randomAccessFile.close();
    }
    
    public String getFileName() {
        return this.getName();
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("fileURI", this.advertiseURI);
        return hashMap;
    }
    
    static {
        FACTORY = new RandomAccessFileManagerFactory(null);
    }
    
    private static class RandomAccessFileManagerFactory implements ManagerFactory
    {
        private RandomAccessFileManagerFactory() {
        }
        
        public RandomAccessFileManager createManager(final String s, final FactoryData factoryData) {
            final File file = new File(s);
            final File parentFile = file.getParentFile();
            if (null != parentFile && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!FactoryData.access$100(factoryData)) {
                file.delete();
            }
            final DummyOutputStream dummyOutputStream = new DummyOutputStream();
            final RandomAccessFile randomAccessFile = new RandomAccessFile(s, "rw");
            if (FactoryData.access$100(factoryData)) {
                randomAccessFile.seek(randomAccessFile.length());
            }
            else {
                randomAccessFile.setLength(0L);
            }
            return new RandomAccessFileManager(randomAccessFile, s, dummyOutputStream, FactoryData.access$200(factoryData), FactoryData.access$300(factoryData), FactoryData.access$400(factoryData));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        RandomAccessFileManagerFactory(final RandomAccessFileManager$1 object) {
            this();
        }
    }
    
    private static class FactoryData
    {
        private final boolean append;
        private final boolean immediateFlush;
        private final String advertiseURI;
        private final Layout layout;
        
        public FactoryData(final boolean append, final boolean immediateFlush, final String advertiseURI, final Layout layout) {
            this.append = append;
            this.immediateFlush = immediateFlush;
            this.advertiseURI = advertiseURI;
            this.layout = layout;
        }
        
        static boolean access$100(final FactoryData factoryData) {
            return factoryData.append;
        }
        
        static boolean access$200(final FactoryData factoryData) {
            return factoryData.immediateFlush;
        }
        
        static String access$300(final FactoryData factoryData) {
            return factoryData.advertiseURI;
        }
        
        static Layout access$400(final FactoryData factoryData) {
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
}
