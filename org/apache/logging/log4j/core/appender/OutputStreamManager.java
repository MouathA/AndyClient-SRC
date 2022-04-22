package org.apache.logging.log4j.core.appender;

import java.io.*;
import org.apache.logging.log4j.core.*;

public class OutputStreamManager extends AbstractManager
{
    private OutputStream os;
    private final byte[] footer;
    private final byte[] header;
    
    protected OutputStreamManager(final OutputStream os, final String s, final Layout layout) {
        super(s);
        this.os = os;
        if (layout != null) {
            this.footer = layout.getFooter();
            this.header = layout.getHeader();
            if (this.header != null) {
                this.os.write(this.header, 0, this.header.length);
            }
        }
        else {
            this.footer = null;
            this.header = null;
        }
    }
    
    public static OutputStreamManager getManager(final String s, final Object o, final ManagerFactory managerFactory) {
        return (OutputStreamManager)AbstractManager.getManager(s, managerFactory, o);
    }
    
    public void releaseSub() {
        if (this.footer != null) {
            this.write(this.footer);
        }
        this.close();
    }
    
    public boolean isOpen() {
        return this.getCount() > 0;
    }
    
    protected OutputStream getOutputStream() {
        return this.os;
    }
    
    protected void setOutputStream(final OutputStream outputStream) {
        if (this.header != null) {
            outputStream.write(this.header, 0, this.header.length);
            this.os = outputStream;
        }
        else {
            this.os = outputStream;
        }
    }
    
    protected synchronized void write(final byte[] array, final int n, final int n2) {
        this.os.write(array, n, n2);
    }
    
    protected void write(final byte[] array) {
        this.write(array, 0, array.length);
    }
    
    protected synchronized void close() {
        final OutputStream os = this.os;
        if (os == System.out || os == System.err) {
            return;
        }
        os.close();
    }
    
    public synchronized void flush() {
        this.os.flush();
    }
}
