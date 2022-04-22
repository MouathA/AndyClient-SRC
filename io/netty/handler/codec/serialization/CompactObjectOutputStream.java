package io.netty.handler.codec.serialization;

import java.io.*;

class CompactObjectOutputStream extends ObjectOutputStream
{
    static final int TYPE_FAT_DESCRIPTOR = 0;
    static final int TYPE_THIN_DESCRIPTOR = 1;
    
    CompactObjectOutputStream(final OutputStream outputStream) throws IOException {
        super(outputStream);
    }
    
    @Override
    protected void writeStreamHeader() throws IOException {
        this.writeByte(5);
    }
    
    @Override
    protected void writeClassDescriptor(final ObjectStreamClass objectStreamClass) throws IOException {
        final Class<?> forClass = objectStreamClass.forClass();
        if (forClass.isPrimitive() || forClass.isArray() || forClass.isInterface() || objectStreamClass.getSerialVersionUID() == 0L) {
            this.write(0);
            super.writeClassDescriptor(objectStreamClass);
        }
        else {
            this.write(1);
            this.writeUTF(objectStreamClass.getName());
        }
    }
}
