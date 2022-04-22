package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;

@NotThreadSafe
public class SerializableEntity extends AbstractHttpEntity
{
    private byte[] objSer;
    private Serializable objRef;
    
    public SerializableEntity(final Serializable objRef, final boolean b) throws IOException {
        Args.notNull(objRef, "Source object");
        if (b) {
            this.createBytes(objRef);
        }
        else {
            this.objRef = objRef;
        }
    }
    
    public SerializableEntity(final Serializable objRef) {
        Args.notNull(objRef, "Source object");
        this.objRef = objRef;
    }
    
    private void createBytes(final Serializable s) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(s);
        objectOutputStream.flush();
        this.objSer = byteArrayOutputStream.toByteArray();
    }
    
    public InputStream getContent() throws IOException, IllegalStateException {
        if (this.objSer == null) {
            this.createBytes(this.objRef);
        }
        return new ByteArrayInputStream(this.objSer);
    }
    
    public long getContentLength() {
        if (this.objSer == null) {
            return -1L;
        }
        return this.objSer.length;
    }
    
    public boolean isRepeatable() {
        return true;
    }
    
    public boolean isStreaming() {
        return this.objSer == null;
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        if (this.objSer == null) {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this.objRef);
            objectOutputStream.flush();
        }
        else {
            outputStream.write(this.objSer);
            outputStream.flush();
        }
    }
}
