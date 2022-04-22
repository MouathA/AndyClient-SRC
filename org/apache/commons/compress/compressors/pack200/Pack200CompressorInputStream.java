package org.apache.commons.compress.compressors.pack200;

import org.apache.commons.compress.compressors.*;
import java.util.*;
import java.util.jar.*;
import java.io.*;

public class Pack200CompressorInputStream extends CompressorInputStream
{
    private final InputStream originalInput;
    private final StreamBridge streamBridge;
    private static final byte[] CAFE_DOOD;
    private static final int SIG_LENGTH;
    
    public Pack200CompressorInputStream(final InputStream inputStream) throws IOException {
        this(inputStream, Pack200Strategy.IN_MEMORY);
    }
    
    public Pack200CompressorInputStream(final InputStream inputStream, final Pack200Strategy pack200Strategy) throws IOException {
        this(inputStream, null, pack200Strategy, null);
    }
    
    public Pack200CompressorInputStream(final InputStream inputStream, final Map map) throws IOException {
        this(inputStream, Pack200Strategy.IN_MEMORY, map);
    }
    
    public Pack200CompressorInputStream(final InputStream inputStream, final Pack200Strategy pack200Strategy, final Map map) throws IOException {
        this(inputStream, null, pack200Strategy, map);
    }
    
    public Pack200CompressorInputStream(final File file) throws IOException {
        this(file, Pack200Strategy.IN_MEMORY);
    }
    
    public Pack200CompressorInputStream(final File file, final Pack200Strategy pack200Strategy) throws IOException {
        this(null, file, pack200Strategy, null);
    }
    
    public Pack200CompressorInputStream(final File file, final Map map) throws IOException {
        this(file, Pack200Strategy.IN_MEMORY, map);
    }
    
    public Pack200CompressorInputStream(final File file, final Pack200Strategy pack200Strategy, final Map map) throws IOException {
        this(null, file, pack200Strategy, map);
    }
    
    private Pack200CompressorInputStream(final InputStream originalInput, final File file, final Pack200Strategy pack200Strategy, final Map map) throws IOException {
        this.originalInput = originalInput;
        this.streamBridge = pack200Strategy.newStreamBridge();
        final JarOutputStream jarOutputStream = new JarOutputStream(this.streamBridge);
        final Pack200.Unpacker unpacker = Pack200.newUnpacker();
        if (map != null) {
            unpacker.properties().putAll((Map<?, ?>)map);
        }
        if (file == null) {
            unpacker.unpack(new FilterInputStream(originalInput) {
                final Pack200CompressorInputStream this$0;
                
                @Override
                public void close() {
                }
            }, jarOutputStream);
        }
        else {
            unpacker.unpack(file, jarOutputStream);
        }
        jarOutputStream.close();
    }
    
    @Override
    public int read() throws IOException {
        return this.streamBridge.getInput().read();
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.streamBridge.getInput().read(array);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.streamBridge.getInput().read(array, n, n2);
    }
    
    @Override
    public int available() throws IOException {
        return this.streamBridge.getInput().available();
    }
    
    @Override
    public boolean markSupported() {
        return this.streamBridge.getInput().markSupported();
    }
    
    @Override
    public void mark(final int n) {
        this.streamBridge.getInput().mark(n);
    }
    
    @Override
    public void reset() throws IOException {
        this.streamBridge.getInput().reset();
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return this.streamBridge.getInput().skip(n);
    }
    
    @Override
    public void close() throws IOException {
        this.streamBridge.stop();
        if (this.originalInput != null) {
            this.originalInput.close();
        }
    }
    
    public static boolean matches(final byte[] array, final int n) {
        if (n < Pack200CompressorInputStream.SIG_LENGTH) {
            return false;
        }
        while (0 < Pack200CompressorInputStream.SIG_LENGTH) {
            if (array[0] != Pack200CompressorInputStream.CAFE_DOOD[0]) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    static {
        CAFE_DOOD = new byte[] { -54, -2, -48, 13 };
        SIG_LENGTH = Pack200CompressorInputStream.CAFE_DOOD.length;
    }
}
