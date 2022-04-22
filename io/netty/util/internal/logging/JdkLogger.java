package io.netty.util.internal.logging;

import java.util.logging.*;

class JdkLogger extends AbstractInternalLogger
{
    private static final long serialVersionUID = -1767272577989225979L;
    final transient Logger logger;
    static final String SELF;
    static final String SUPER;
    
    JdkLogger(final Logger logger) {
        super(logger.getName());
        this.logger = logger;
    }
    
    @Override
    public boolean isTraceEnabled() {
        return this.logger.isLoggable(Level.FINEST);
    }
    
    @Override
    public void trace(final String s) {
        if (this.logger.isLoggable(Level.FINEST)) {
            this.log(JdkLogger.SELF, Level.FINEST, s, null);
        }
    }
    
    @Override
    public void trace(final String s, final Object o) {
        if (this.logger.isLoggable(Level.FINEST)) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.log(JdkLogger.SELF, Level.FINEST, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Object o, final Object o2) {
        if (this.logger.isLoggable(Level.FINEST)) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.log(JdkLogger.SELF, Level.FINEST, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Object... array) {
        if (this.logger.isLoggable(Level.FINEST)) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.log(JdkLogger.SELF, Level.FINEST, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Throwable t) {
        if (this.logger.isLoggable(Level.FINEST)) {
            this.log(JdkLogger.SELF, Level.FINEST, s, t);
        }
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.logger.isLoggable(Level.FINE);
    }
    
    @Override
    public void debug(final String s) {
        if (this.logger.isLoggable(Level.FINE)) {
            this.log(JdkLogger.SELF, Level.FINE, s, null);
        }
    }
    
    @Override
    public void debug(final String s, final Object o) {
        if (this.logger.isLoggable(Level.FINE)) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.log(JdkLogger.SELF, Level.FINE, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Object o, final Object o2) {
        if (this.logger.isLoggable(Level.FINE)) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.log(JdkLogger.SELF, Level.FINE, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Object... array) {
        if (this.logger.isLoggable(Level.FINE)) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.log(JdkLogger.SELF, Level.FINE, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Throwable t) {
        if (this.logger.isLoggable(Level.FINE)) {
            this.log(JdkLogger.SELF, Level.FINE, s, t);
        }
    }
    
    @Override
    public boolean isInfoEnabled() {
        return this.logger.isLoggable(Level.INFO);
    }
    
    @Override
    public void info(final String s) {
        if (this.logger.isLoggable(Level.INFO)) {
            this.log(JdkLogger.SELF, Level.INFO, s, null);
        }
    }
    
    @Override
    public void info(final String s, final Object o) {
        if (this.logger.isLoggable(Level.INFO)) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.log(JdkLogger.SELF, Level.INFO, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Object o, final Object o2) {
        if (this.logger.isLoggable(Level.INFO)) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.log(JdkLogger.SELF, Level.INFO, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Object... array) {
        if (this.logger.isLoggable(Level.INFO)) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.log(JdkLogger.SELF, Level.INFO, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Throwable t) {
        if (this.logger.isLoggable(Level.INFO)) {
            this.log(JdkLogger.SELF, Level.INFO, s, t);
        }
    }
    
    @Override
    public boolean isWarnEnabled() {
        return this.logger.isLoggable(Level.WARNING);
    }
    
    @Override
    public void warn(final String s) {
        if (this.logger.isLoggable(Level.WARNING)) {
            this.log(JdkLogger.SELF, Level.WARNING, s, null);
        }
    }
    
    @Override
    public void warn(final String s, final Object o) {
        if (this.logger.isLoggable(Level.WARNING)) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.log(JdkLogger.SELF, Level.WARNING, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Object o, final Object o2) {
        if (this.logger.isLoggable(Level.WARNING)) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.log(JdkLogger.SELF, Level.WARNING, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Object... array) {
        if (this.logger.isLoggable(Level.WARNING)) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.log(JdkLogger.SELF, Level.WARNING, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Throwable t) {
        if (this.logger.isLoggable(Level.WARNING)) {
            this.log(JdkLogger.SELF, Level.WARNING, s, t);
        }
    }
    
    @Override
    public boolean isErrorEnabled() {
        return this.logger.isLoggable(Level.SEVERE);
    }
    
    @Override
    public void error(final String s) {
        if (this.logger.isLoggable(Level.SEVERE)) {
            this.log(JdkLogger.SELF, Level.SEVERE, s, null);
        }
    }
    
    @Override
    public void error(final String s, final Object o) {
        if (this.logger.isLoggable(Level.SEVERE)) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.log(JdkLogger.SELF, Level.SEVERE, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Object o, final Object o2) {
        if (this.logger.isLoggable(Level.SEVERE)) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.log(JdkLogger.SELF, Level.SEVERE, format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Object... array) {
        if (this.logger.isLoggable(Level.SEVERE)) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.log(JdkLogger.SELF, Level.SEVERE, arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Throwable t) {
        if (this.logger.isLoggable(Level.SEVERE)) {
            this.log(JdkLogger.SELF, Level.SEVERE, s, t);
        }
    }
    
    private void log(final String s, final Level level, final String s2, final Throwable thrown) {
        final LogRecord logRecord = new LogRecord(level, s2);
        logRecord.setLoggerName(this.name());
        logRecord.setThrown(thrown);
        fillCallerData(s, logRecord);
        this.logger.log(logRecord);
    }
    
    private static void fillCallerData(final String p0, final LogRecord p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/lang/Throwable.<init>:()V
        //     7: invokevirtual   java/lang/Throwable.getStackTrace:()[Ljava/lang/StackTraceElement;
        //    10: astore_2       
        //    11: iconst_m1      
        //    12: aload_2        
        //    13: arraylength    
        //    14: if_icmpge       54
        //    17: aload_2        
        //    18: iconst_m1      
        //    19: aaload         
        //    20: invokevirtual   java/lang/StackTraceElement.getClassName:()Ljava/lang/String;
        //    23: astore          5
        //    25: aload           5
        //    27: aload_0        
        //    28: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    31: ifne            45
        //    34: aload           5
        //    36: getstatic       io/netty/util/internal/logging/JdkLogger.SUPER:Ljava/lang/String;
        //    39: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    42: ifeq            48
        //    45: goto            54
        //    48: iinc            4, 1
        //    51: goto            11
        //    54: iconst_0       
        //    55: aload_2        
        //    56: arraylength    
        //    57: if_icmpge       97
        //    60: aload_2        
        //    61: iconst_0       
        //    62: aaload         
        //    63: invokevirtual   java/lang/StackTraceElement.getClassName:()Ljava/lang/String;
        //    66: astore          6
        //    68: aload           6
        //    70: aload_0        
        //    71: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    74: ifne            91
        //    77: aload           6
        //    79: getstatic       io/netty/util/internal/logging/JdkLogger.SUPER:Ljava/lang/String;
        //    82: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    85: ifne            91
        //    88: goto            97
        //    91: iinc            5, 1
        //    94: goto            54
        //    97: iconst_m1      
        //    98: iconst_m1      
        //    99: if_icmpeq       125
        //   102: aload_2        
        //   103: iconst_m1      
        //   104: aaload         
        //   105: astore          5
        //   107: aload_1        
        //   108: aload           5
        //   110: invokevirtual   java/lang/StackTraceElement.getClassName:()Ljava/lang/String;
        //   113: invokevirtual   java/util/logging/LogRecord.setSourceClassName:(Ljava/lang/String;)V
        //   116: aload_1        
        //   117: aload           5
        //   119: invokevirtual   java/lang/StackTraceElement.getMethodName:()Ljava/lang/String;
        //   122: invokevirtual   java/util/logging/LogRecord.setSourceMethodName:(Ljava/lang/String;)V
        //   125: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        SELF = JdkLogger.class.getName();
        SUPER = AbstractInternalLogger.class.getName();
    }
}
