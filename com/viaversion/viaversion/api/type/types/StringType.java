package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.google.common.base.*;
import java.nio.charset.*;

public class StringType extends Type
{
    private static final int maxJavaCharUtf8Length;
    private final int maxLength;
    
    public StringType() {
        this(32767);
    }
    
    public StringType(final int maxLength) {
        super(String.class);
        this.maxLength = maxLength;
    }
    
    @Override
    public String read(final ByteBuf byteBuf) throws Exception {
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        Preconditions.checkArgument(primitive <= this.maxLength * StringType.maxJavaCharUtf8Length, "Cannot receive string longer than Short.MAX_VALUE * " + StringType.maxJavaCharUtf8Length + " bytes (got %s bytes)", primitive);
        final String string = byteBuf.toString(byteBuf.readerIndex(), primitive, StandardCharsets.UTF_8);
        byteBuf.skipBytes(primitive);
        Preconditions.checkArgument(string.length() <= this.maxLength, "Cannot receive string longer than Short.MAX_VALUE characters (got %s bytes)", string.length());
        return string;
    }
    
    public void write(final ByteBuf byteBuf, final String s) throws Exception {
        Preconditions.checkArgument(s.length() <= this.maxLength, "Cannot send string longer than Short.MAX_VALUE (got %s characters)", s.length());
        final byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        Type.VAR_INT.writePrimitive(byteBuf, bytes.length);
        byteBuf.writeBytes(bytes);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (String)o);
    }
    
    static {
        maxJavaCharUtf8Length = Character.toString('\uffff').getBytes(StandardCharsets.UTF_8).length;
    }
}
