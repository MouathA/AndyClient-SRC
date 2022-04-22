package org.apache.commons.io.output;

import java.io.*;

public class CloseShieldOutputStream extends ProxyOutputStream
{
    public CloseShieldOutputStream(final OutputStream outputStream) {
        super(outputStream);
    }
    
    @Override
    public void close() {
        this.out = new ClosedOutputStream();
    }
}
