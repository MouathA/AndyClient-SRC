package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.*;

final class IOStreamUtil
{
    private IOStreamUtil() {
    }
    
    static InputStream closeShield(final InputStream stream) {
        return new InputStream() {
            final InputStream val$stream;
            
            @Override
            public int read() throws IOException {
                return this.val$stream.read();
            }
            
            @Override
            public int read(final byte[] b) throws IOException {
                return this.val$stream.read(b);
            }
            
            @Override
            public int read(final byte[] b, final int off, final int len) throws IOException {
                return this.val$stream.read(b, off, len);
            }
        };
    }
    
    static OutputStream closeShield(final OutputStream stream) {
        return new OutputStream() {
            final OutputStream val$stream;
            
            @Override
            public void write(final int b) throws IOException {
                this.val$stream.write(b);
            }
            
            @Override
            public void write(final byte[] b) throws IOException {
                this.val$stream.write(b);
            }
            
            @Override
            public void write(final byte[] b, final int off, final int len) throws IOException {
                this.val$stream.write(b, off, len);
            }
        };
    }
}
