package io.netty.util.internal.logging;

import java.io.*;
import org.slf4j.helpers.*;
import org.slf4j.*;

public class Slf4JLoggerFactory extends InternalLoggerFactory
{
    static final boolean $assertionsDisabled;
    
    public Slf4JLoggerFactory() {
    }
    
    Slf4JLoggerFactory(final boolean b) {
        assert b;
        final StringBuffer sb = new StringBuffer();
        final PrintStream err = System.err;
        System.setErr(new PrintStream(new OutputStream(sb) {
            final StringBuffer val$buf;
            final Slf4JLoggerFactory this$0;
            
            @Override
            public void write(final int n) {
                this.val$buf.append((char)n);
            }
        }, true, "US-ASCII"));
        if (LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory) {
            throw new NoClassDefFoundError(sb.toString());
        }
        err.print(sb);
        err.flush();
        System.setErr(err);
    }
    
    public InternalLogger newInstance(final String s) {
        return new Slf4JLogger(LoggerFactory.getLogger(s));
    }
    
    static {
        $assertionsDisabled = !Slf4JLoggerFactory.class.desiredAssertionStatus();
    }
}
