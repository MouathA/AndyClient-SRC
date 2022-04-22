package org.apache.logging.log4j.core.layout;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;
import java.io.*;

@Plugin(name = "SerializedLayout", category = "Core", elementType = "layout", printObject = true)
public final class SerializedLayout extends AbstractLayout
{
    private static byte[] header;
    
    private SerializedLayout() {
    }
    
    @Override
    public byte[] toByteArray(final LogEvent logEvent) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrivateObjectOutputStream privateObjectOutputStream = new PrivateObjectOutputStream(byteArrayOutputStream);
        privateObjectOutputStream.writeObject(logEvent);
        privateObjectOutputStream.reset();
        privateObjectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    @Override
    public LogEvent toSerializable(final LogEvent logEvent) {
        return logEvent;
    }
    
    @PluginFactory
    public static SerializedLayout createLayout() {
        return new SerializedLayout();
    }
    
    @Override
    public byte[] getHeader() {
        return SerializedLayout.header;
    }
    
    @Override
    public Map getContentFormat() {
        return new HashMap();
    }
    
    @Override
    public String getContentType() {
        return "application/octet-stream";
    }
    
    @Override
    public Serializable toSerializable(final LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }
    
    static {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new ObjectOutputStream(byteArrayOutputStream).close();
        SerializedLayout.header = byteArrayOutputStream.toByteArray();
    }
    
    private class PrivateObjectOutputStream extends ObjectOutputStream
    {
        final SerializedLayout this$0;
        
        public PrivateObjectOutputStream(final SerializedLayout this$0, final OutputStream outputStream) throws IOException {
            this.this$0 = this$0;
            super(outputStream);
        }
        
        @Override
        protected void writeStreamHeader() {
        }
    }
}
