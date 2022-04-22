package org.apache.logging.log4j.core.appender.rolling;

import java.util.concurrent.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import java.io.*;
import org.apache.logging.log4j.core.appender.rolling.helper.*;

public class RollingFileManager extends FileManager
{
    private static RollingFileManagerFactory factory;
    private long size;
    private long initialTime;
    private final PatternProcessor patternProcessor;
    private final Semaphore semaphore;
    private final TriggeringPolicy policy;
    private final RolloverStrategy strategy;
    
    protected RollingFileManager(final String s, final String s2, final OutputStream outputStream, final boolean b, final long size, final long initialTime, final TriggeringPolicy policy, final RolloverStrategy strategy, final String s3, final Layout layout) {
        super(s, outputStream, b, false, s3, layout);
        this.semaphore = new Semaphore(1);
        this.size = size;
        this.initialTime = initialTime;
        this.policy = policy;
        this.strategy = strategy;
        this.patternProcessor = new PatternProcessor(s2);
        policy.initialize(this);
    }
    
    public static RollingFileManager getFileManager(final String s, final String s2, final boolean b, final boolean b2, final TriggeringPolicy triggeringPolicy, final RolloverStrategy rolloverStrategy, final String s3, final Layout layout) {
        return (RollingFileManager)OutputStreamManager.getManager(s, new FactoryData(s2, b, b2, triggeringPolicy, rolloverStrategy, s3, layout), RollingFileManager.factory);
    }
    
    @Override
    protected synchronized void write(final byte[] array, final int n, final int n2) {
        this.size += n2;
        super.write(array, n, n2);
    }
    
    public long getFileSize() {
        return this.size;
    }
    
    public long getFileTime() {
        return this.initialTime;
    }
    
    public synchronized void checkRollover(final LogEvent logEvent) {
        if (this.policy.isTriggeringEvent(logEvent) && this.rollover(this.strategy)) {
            this.size = 0L;
            this.initialTime = System.currentTimeMillis();
            this.createFileAfterRollover();
        }
    }
    
    protected void createFileAfterRollover() throws IOException {
        this.setOutputStream(new FileOutputStream(this.getFileName(), this.isAppend()));
    }
    
    public PatternProcessor getPatternProcessor() {
        return this.patternProcessor;
    }
    
    private boolean rollover(final RolloverStrategy rolloverStrategy) {
        this.semaphore.acquire();
        Thread thread = null;
        final RolloverDescription rollover = rolloverStrategy.rollover(this);
        if (rollover != null) {
            this.close();
            if (rollover.getSynchronous() != null) {
                rollover.getSynchronous().execute();
            }
            if (false && rollover.getAsynchronous() != null) {
                thread = new Thread(new AsyncAction(rollover.getAsynchronous(), this));
                thread.start();
            }
            if (thread == null) {
                this.semaphore.release();
            }
            return false;
        }
        if (thread == null) {
            this.semaphore.release();
        }
        return false;
    }
    
    static Semaphore access$100(final RollingFileManager rollingFileManager) {
        return rollingFileManager.semaphore;
    }
    
    static Logger access$200() {
        return RollingFileManager.LOGGER;
    }
    
    static Logger access$1000() {
        return RollingFileManager.LOGGER;
    }
    
    static {
        RollingFileManager.factory = new RollingFileManagerFactory(null);
    }
    
    private static class RollingFileManagerFactory implements ManagerFactory
    {
        private RollingFileManagerFactory() {
        }
        
        public RollingFileManager createManager(final String s, final FactoryData factoryData) {
            final File file = new File(s);
            final File parentFile = file.getParentFile();
            if (null != parentFile && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
            final long n = FactoryData.access$300(factoryData) ? file.length() : 0L;
            final long lastModified = file.lastModified();
            OutputStream outputStream = new FileOutputStream(s, FactoryData.access$300(factoryData));
            if (FactoryData.access$400(factoryData)) {
                outputStream = new BufferedOutputStream(outputStream);
            }
            return new RollingFileManager(s, FactoryData.access$500(factoryData), outputStream, FactoryData.access$300(factoryData), n, lastModified, FactoryData.access$600(factoryData), FactoryData.access$700(factoryData), FactoryData.access$800(factoryData), FactoryData.access$900(factoryData));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        RollingFileManagerFactory(final RollingFileManager$1 object) {
            this();
        }
    }
    
    private static class FactoryData
    {
        private final String pattern;
        private final boolean append;
        private final boolean bufferedIO;
        private final TriggeringPolicy policy;
        private final RolloverStrategy strategy;
        private final String advertiseURI;
        private final Layout layout;
        
        public FactoryData(final String pattern, final boolean append, final boolean bufferedIO, final TriggeringPolicy policy, final RolloverStrategy strategy, final String advertiseURI, final Layout layout) {
            this.pattern = pattern;
            this.append = append;
            this.bufferedIO = bufferedIO;
            this.policy = policy;
            this.strategy = strategy;
            this.advertiseURI = advertiseURI;
            this.layout = layout;
        }
        
        static boolean access$300(final FactoryData factoryData) {
            return factoryData.append;
        }
        
        static boolean access$400(final FactoryData factoryData) {
            return factoryData.bufferedIO;
        }
        
        static String access$500(final FactoryData factoryData) {
            return factoryData.pattern;
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
    
    private static class AsyncAction extends AbstractAction
    {
        private final Action action;
        private final RollingFileManager manager;
        
        public AsyncAction(final Action action, final RollingFileManager manager) {
            this.action = action;
            this.manager = manager;
        }
        
        @Override
        public boolean execute() throws IOException {
            final boolean execute = this.action.execute();
            RollingFileManager.access$100(this.manager).release();
            return execute;
        }
        
        @Override
        public void close() {
            this.action.close();
        }
        
        @Override
        public boolean isComplete() {
            return this.action.isComplete();
        }
    }
}
