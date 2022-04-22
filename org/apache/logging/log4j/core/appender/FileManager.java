package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.*;
import java.nio.channels.*;
import java.util.*;
import java.io.*;

public class FileManager extends OutputStreamManager
{
    private static final FileManagerFactory FACTORY;
    private final boolean isAppend;
    private final boolean isLocking;
    private final String advertiseURI;
    
    protected FileManager(final String s, final OutputStream outputStream, final boolean isAppend, final boolean isLocking, final String advertiseURI, final Layout layout) {
        super(outputStream, s, layout);
        this.isAppend = isAppend;
        this.isLocking = isLocking;
        this.advertiseURI = advertiseURI;
    }
    
    public static FileManager getFileManager(final String s, final boolean b, final boolean b2, final boolean b3, final String s2, final Layout layout) {
        if (!false || b3) {}
        return (FileManager)OutputStreamManager.getManager(s, new FactoryData(b, false, b3, s2, layout), FileManager.FACTORY);
    }
    
    @Override
    protected synchronized void write(final byte[] array, final int n, final int n2) {
        if (this.isLocking) {
            final FileLock lock = ((FileOutputStream)this.getOutputStream()).getChannel().lock(0L, Long.MAX_VALUE, false);
            super.write(array, n, n2);
            lock.release();
        }
        else {
            super.write(array, n, n2);
        }
    }
    
    public String getFileName() {
        return this.getName();
    }
    
    public boolean isAppend() {
        return this.isAppend;
    }
    
    public boolean isLocking() {
        return this.isLocking;
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("fileURI", this.advertiseURI);
        return hashMap;
    }
    
    static {
        FACTORY = new FileManagerFactory(null);
    }
    
    private static class FileManagerFactory implements ManagerFactory
    {
        private FileManagerFactory() {
        }
        
        public FileManager createManager(final String s, final FactoryData factoryData) {
            final File parentFile = new File(s).getParentFile();
            if (null != parentFile && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(s, FactoryData.access$100(factoryData));
            if (FactoryData.access$200(factoryData)) {
                outputStream = new BufferedOutputStream(outputStream);
            }
            return new FileManager(s, outputStream, FactoryData.access$100(factoryData), FactoryData.access$300(factoryData), FactoryData.access$400(factoryData), FactoryData.access$500(factoryData));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        FileManagerFactory(final FileManager$1 object) {
            this();
        }
    }
    
    private static class FactoryData
    {
        private final boolean append;
        private final boolean locking;
        private final boolean bufferedIO;
        private final String advertiseURI;
        private final Layout layout;
        
        public FactoryData(final boolean append, final boolean locking, final boolean bufferedIO, final String advertiseURI, final Layout layout) {
            this.append = append;
            this.locking = locking;
            this.bufferedIO = bufferedIO;
            this.advertiseURI = advertiseURI;
            this.layout = layout;
        }
        
        static boolean access$100(final FactoryData factoryData) {
            return factoryData.append;
        }
        
        static boolean access$200(final FactoryData factoryData) {
            return factoryData.bufferedIO;
        }
        
        static boolean access$300(final FactoryData factoryData) {
            return factoryData.locking;
        }
        
        static String access$400(final FactoryData factoryData) {
            return factoryData.advertiseURI;
        }
        
        static Layout access$500(final FactoryData factoryData) {
            return factoryData.layout;
        }
    }
}
