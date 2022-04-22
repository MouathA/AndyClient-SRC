package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.*;
import java.io.*;
import java.net.*;
import org.apache.logging.log4j.status.*;

public class DatagramOutputStream extends OutputStream
{
    protected static final Logger LOGGER;
    private static final int SHIFT_1 = 8;
    private static final int SHIFT_2 = 16;
    private static final int SHIFT_3 = 24;
    private DatagramSocket ds;
    private final InetAddress address;
    private final int port;
    private byte[] data;
    private final byte[] header;
    private final byte[] footer;
    
    public DatagramOutputStream(final String s, final int port, final byte[] header, final byte[] footer) {
        this.port = port;
        this.header = header;
        this.footer = footer;
        this.address = InetAddress.getByName(s);
        this.ds = new DatagramSocket();
    }
    
    @Override
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        this.copy(array, n, n2);
    }
    
    @Override
    public synchronized void write(final int n) throws IOException {
        this.copy(new byte[] { (byte)(n >>> 24), (byte)(n >>> 16), (byte)(n >>> 8), (byte)n }, 0, 4);
    }
    
    @Override
    public synchronized void write(final byte[] array) throws IOException {
        this.copy(array, 0, array.length);
    }
    
    @Override
    public synchronized void flush() throws IOException {
        if (this.data != null && this.ds != null && this.address != null) {
            if (this.footer != null) {
                this.copy(this.footer, 0, this.footer.length);
            }
            this.ds.send(new DatagramPacket(this.data, this.data.length, this.address, this.port));
        }
        this.data = null;
        if (this.header != null) {
            this.copy(this.header, 0, this.header.length);
        }
    }
    
    @Override
    public synchronized void close() throws IOException {
        if (this.ds != null) {
            if (this.data != null) {
                this.flush();
            }
            this.ds.close();
            this.ds = null;
        }
    }
    
    private void copy(final byte[] array, final int n, final int n2) {
        final int n3 = (this.data == null) ? 0 : this.data.length;
        final byte[] data = new byte[n2 + n3];
        if (this.data != null) {
            System.arraycopy(this.data, 0, data, 0, this.data.length);
        }
        System.arraycopy(array, n, data, n3, n2);
        this.data = data;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
