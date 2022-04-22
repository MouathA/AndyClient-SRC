package org.apache.commons.compress.compressors.pack200;

import org.apache.commons.compress.compressors.*;
import java.util.*;
import java.util.jar.*;
import org.apache.commons.compress.utils.*;
import java.io.*;

public class Pack200CompressorOutputStream extends CompressorOutputStream
{
    private boolean finished;
    private final OutputStream originalOutput;
    private final StreamBridge streamBridge;
    private final Map properties;
    
    public Pack200CompressorOutputStream(final OutputStream outputStream) throws IOException {
        this(outputStream, Pack200Strategy.IN_MEMORY);
    }
    
    public Pack200CompressorOutputStream(final OutputStream outputStream, final Pack200Strategy pack200Strategy) throws IOException {
        this(outputStream, pack200Strategy, null);
    }
    
    public Pack200CompressorOutputStream(final OutputStream outputStream, final Map map) throws IOException {
        this(outputStream, Pack200Strategy.IN_MEMORY, map);
    }
    
    public Pack200CompressorOutputStream(final OutputStream originalOutput, final Pack200Strategy pack200Strategy, final Map properties) throws IOException {
        this.finished = false;
        this.originalOutput = originalOutput;
        this.streamBridge = pack200Strategy.newStreamBridge();
        this.properties = properties;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.streamBridge.write(n);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.streamBridge.write(array);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.streamBridge.write(array, n, n2);
    }
    
    @Override
    public void close() throws IOException {
        this.finish();
        this.streamBridge.stop();
        this.originalOutput.close();
    }
    
    public void finish() throws IOException {
        if (!this.finished) {
            this.finished = true;
            final Pack200.Packer packer = Pack200.newPacker();
            if (this.properties != null) {
                packer.properties().putAll((Map<?, ?>)this.properties);
            }
            final JarInputStream jarInputStream;
            packer.pack(jarInputStream = new JarInputStream(this.streamBridge.getInput()), this.originalOutput);
            if (!true) {
                IOUtils.closeQuietly(jarInputStream);
            }
        }
    }
}
