package org.apache.logging.log4j.message;

import java.lang.reflect.*;
import java.lang.management.*;
import java.util.*;
import java.io.*;

public class ThreadDumpMessage implements Message
{
    private static final long serialVersionUID = -1103400781608841088L;
    private static final ThreadInfoFactory FACTORY;
    private Map threads;
    private final String title;
    private String formattedMessage;
    
    public ThreadDumpMessage(final String s) {
        this.title = ((s == null) ? "" : s);
        this.threads = ThreadDumpMessage.FACTORY.createThreadInfo();
    }
    
    private ThreadDumpMessage(final String formattedMessage, final String s) {
        this.formattedMessage = formattedMessage;
        this.title = ((s == null) ? "" : s);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ThreadDumpMessage[");
        if (this.title.length() > 0) {
            sb.append("Title=\"").append(this.title).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage != null) {
            return this.formattedMessage;
        }
        final StringBuilder sb = new StringBuilder(this.title);
        if (this.title.length() > 0) {
            sb.append("\n");
        }
        for (final Map.Entry<ThreadInformation, V> entry : this.threads.entrySet()) {
            final ThreadInformation threadInformation = entry.getKey();
            threadInformation.printThreadInfo(sb);
            threadInformation.printStack(sb, (StackTraceElement[])(Object)entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public String getFormat() {
        return (this.title == null) ? "" : this.title;
    }
    
    @Override
    public Object[] getParameters() {
        return null;
    }
    
    protected Object writeReplace() {
        return new ThreadDumpMessageProxy(this);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
    
    @Override
    public Throwable getThrowable() {
        return null;
    }
    
    static String access$200(final ThreadDumpMessage threadDumpMessage) {
        return threadDumpMessage.title;
    }
    
    ThreadDumpMessage(final String s, final String s2, final ThreadDumpMessage$1 object) {
        this(s, s2);
    }
    
    static {
        final Method[] methods = ThreadInfo.class.getMethods();
        while (0 < methods.length && !methods[0].getName().equals("getLockInfo")) {
            int n = 0;
            ++n;
        }
        FACTORY = (false ? new BasicThreadInfoFactory(null) : new ExtendedThreadInfoFactory(null));
    }
    
    private static class ExtendedThreadInfoFactory implements ThreadInfoFactory
    {
        private ExtendedThreadInfoFactory() {
        }
        
        @Override
        public Map createThreadInfo() {
            final ThreadInfo[] dumpAllThreads = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
            final HashMap hashMap = new HashMap<ExtendedThreadInformation, StackTraceElement[]>(dumpAllThreads.length);
            final ThreadInfo[] array = dumpAllThreads;
            while (0 < array.length) {
                final ThreadInfo threadInfo = array[0];
                hashMap.put(new ExtendedThreadInformation(threadInfo), threadInfo.getStackTrace());
                int n = 0;
                ++n;
            }
            return hashMap;
        }
        
        ExtendedThreadInfoFactory(final ThreadDumpMessage$1 object) {
            this();
        }
    }
    
    private interface ThreadInfoFactory
    {
        Map createThreadInfo();
    }
    
    private static class BasicThreadInfoFactory implements ThreadInfoFactory
    {
        private BasicThreadInfoFactory() {
        }
        
        @Override
        public Map createThreadInfo() {
            final Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            final HashMap hashMap = new HashMap<BasicThreadInformation, Object>(allStackTraces.size());
            for (final Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
                hashMap.put(new BasicThreadInformation(entry.getKey()), (V)(Object)entry.getValue());
            }
            return hashMap;
        }
        
        BasicThreadInfoFactory(final ThreadDumpMessage$1 object) {
            this();
        }
    }
    
    private static class ThreadDumpMessageProxy implements Serializable
    {
        private static final long serialVersionUID = -3476620450287648269L;
        private final String formattedMsg;
        private final String title;
        
        public ThreadDumpMessageProxy(final ThreadDumpMessage threadDumpMessage) {
            this.formattedMsg = threadDumpMessage.getFormattedMessage();
            this.title = ThreadDumpMessage.access$200(threadDumpMessage);
        }
        
        protected Object readResolve() {
            return new ThreadDumpMessage(this.formattedMsg, this.title, null);
        }
    }
}
