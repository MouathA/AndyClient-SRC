package io.netty.handler.codec.serialization;

import java.io.*;

class CompactObjectInputStream extends ObjectInputStream
{
    private final ClassResolver classResolver;
    
    CompactObjectInputStream(final InputStream inputStream, final ClassResolver classResolver) throws IOException {
        super(inputStream);
        this.classResolver = classResolver;
    }
    
    @Override
    protected void readStreamHeader() throws IOException {
        final int n = this.readByte() & 0xFF;
        if (n != 5) {
            throw new StreamCorruptedException("Unsupported version: " + n);
        }
    }
    
    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        final int read = this.read();
        if (read < 0) {
            throw new EOFException();
        }
        switch (read) {
            case 0: {
                return super.readClassDescriptor();
            }
            case 1: {
                return ObjectStreamClass.lookupAny(this.classResolver.resolve(this.readUTF()));
            }
            default: {
                throw new StreamCorruptedException("Unexpected class descriptor type: " + read);
            }
        }
    }
    
    @Override
    protected Class resolveClass(final ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        return this.classResolver.resolve(objectStreamClass.getName());
    }
}
