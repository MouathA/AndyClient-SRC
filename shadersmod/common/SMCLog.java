package shadersmod.common;

import java.util.*;
import java.util.logging.*;
import java.io.*;

public abstract class SMCLog
{
    public static final String smcLogName;
    public static final Logger logger;
    public static final Level SMCINFO;
    public static final Level SMCCONFIG;
    public static final Level SMCFINE;
    public static final Level SMCFINER;
    public static final Level SMCFINEST;
    
    static {
        smcLogName = "SMC";
        logger = new SMCLogger("SMC");
        SMCINFO = new SMCLevel("INF", 850, (NamelessClass763038833)null);
        SMCCONFIG = new SMCLevel("CFG", 840, (NamelessClass763038833)null);
        SMCFINE = new SMCLevel("FNE", 830, (NamelessClass763038833)null);
        SMCFINER = new SMCLevel("FNR", 820, (NamelessClass763038833)null);
        SMCFINEST = new SMCLevel("FNT", 810, (NamelessClass763038833)null);
    }
    
    public static void log(final Level level, final String s) {
        if (SMCLog.logger.isLoggable(level)) {
            SMCLog.logger.log(level, s);
        }
    }
    
    public static void severe(final String s) {
        if (SMCLog.logger.isLoggable(Level.SEVERE)) {
            SMCLog.logger.log(Level.SEVERE, s);
        }
    }
    
    public static void warning(final String s) {
        if (SMCLog.logger.isLoggable(Level.WARNING)) {
            SMCLog.logger.log(Level.WARNING, s);
        }
    }
    
    public static void info(final String s) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCINFO)) {
            SMCLog.logger.log(SMCLog.SMCINFO, s);
        }
    }
    
    public static void config(final String s) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCCONFIG)) {
            SMCLog.logger.log(SMCLog.SMCCONFIG, s);
        }
    }
    
    public static void fine(final String s) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCFINE)) {
            SMCLog.logger.log(SMCLog.SMCFINE, s);
        }
    }
    
    public static void finer(final String s) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCFINER)) {
            SMCLog.logger.log(SMCLog.SMCFINER, s);
        }
    }
    
    public static void finest(final String s) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCFINEST)) {
            SMCLog.logger.log(SMCLog.SMCFINEST, s);
        }
    }
    
    public static void log(final Level level, final String s, final Object... array) {
        if (SMCLog.logger.isLoggable(level)) {
            SMCLog.logger.log(level, String.format(s, array));
        }
    }
    
    public static void severe(final String s, final Object... array) {
        if (SMCLog.logger.isLoggable(Level.SEVERE)) {
            SMCLog.logger.log(Level.SEVERE, String.format(s, array));
        }
    }
    
    public static void warning(final String s, final Object... array) {
        if (SMCLog.logger.isLoggable(Level.WARNING)) {
            SMCLog.logger.log(Level.WARNING, String.format(s, array));
        }
    }
    
    public static void info(final String s, final Object... array) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCINFO)) {
            SMCLog.logger.log(SMCLog.SMCINFO, String.format(s, array));
        }
    }
    
    public static void config(final String s, final Object... array) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCCONFIG)) {
            SMCLog.logger.log(SMCLog.SMCCONFIG, String.format(s, array));
        }
    }
    
    public static void fine(final String s, final Object... array) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCFINE)) {
            SMCLog.logger.log(SMCLog.SMCFINE, String.format(s, array));
        }
    }
    
    public static void finer(final String s, final Object... array) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCFINER)) {
            SMCLog.logger.log(SMCLog.SMCFINER, String.format(s, array));
        }
    }
    
    public static void finest(final String s, final Object... array) {
        if (SMCLog.logger.isLoggable(SMCLog.SMCFINEST)) {
            SMCLog.logger.log(SMCLog.SMCFINEST, String.format(s, array));
        }
    }
    
    static class NamelessClass763038833
    {
    }
    
    private static class SMCFormatter extends Formatter
    {
        int tzOffset;
        
        private SMCFormatter() {
            this.tzOffset = Calendar.getInstance().getTimeZone().getRawOffset();
        }
        
        @Override
        public String format(final LogRecord logRecord) {
            final StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append("Shaders").append("]");
            if (logRecord.getLevel() != SMCLog.SMCINFO) {
                sb.append("[").append(logRecord.getLevel()).append("]");
            }
            sb.append(" ");
            sb.append(logRecord.getMessage()).append("\n");
            return sb.toString();
        }
        
        SMCFormatter(final SMCFormatter smcFormatter) {
            this();
        }
    }
    
    private static class SMCLevel extends Level
    {
        private SMCLevel(final String s, final int n) {
            super(s, n);
        }
        
        SMCLevel(final String s, final int n, final NamelessClass763038833 namelessClass763038833) {
            this(s, n);
        }
    }
    
    private static class SMCLogger extends Logger
    {
        SMCLogger(final String s) {
            super(s, null);
            this.setUseParentHandlers(false);
            final SMCFormatter smcFormatter = new SMCFormatter(null);
            final ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(smcFormatter);
            consoleHandler.setLevel(Level.ALL);
            this.addHandler(consoleHandler);
            final StreamHandler streamHandler = new StreamHandler((OutputStream)new FileOutputStream("logs/shadersmod.log", false), (Formatter)smcFormatter) {
                final SMCLogger this$1;
                
                @Override
                public synchronized void publish(final LogRecord logRecord) {
                    super.publish(logRecord);
                    this.flush();
                }
            };
            streamHandler.setFormatter(smcFormatter);
            streamHandler.setLevel(Level.ALL);
            this.addHandler(streamHandler);
            this.setLevel(Level.ALL);
        }
    }
}
