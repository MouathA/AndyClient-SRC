package org.apache.commons.compress.compressors.pack200;

import java.io.*;

abstract class StreamBridge extends FilterOutputStream
{
    private InputStream input;
    private final Object INPUT_LOCK;
    
    protected StreamBridge(final OutputStream outputStream) {
        super(outputStream);
        this.INPUT_LOCK = new Object();
    }
    
    protected StreamBridge() {
        this(null);
    }
    
    InputStream getInput() throws IOException {
        // monitorenter(input_LOCK = this.INPUT_LOCK)
        if (this.input == null) {
            this.input = this.getInputView();
        }
        // monitorexit(input_LOCK)
        return this.input;
    }
    
    abstract InputStream getInputView() throws IOException;
    
    void stop() throws IOException {
        this.close();
        // monitorenter(input_LOCK = this.INPUT_LOCK)
        if (this.input != null) {
            this.input.close();
            this.input = null;
        }
    }
    // monitorexit(input_LOCK)
}
