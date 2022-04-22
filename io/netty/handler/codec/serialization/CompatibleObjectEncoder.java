package io.netty.handler.codec.serialization;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.io.*;
import io.netty.buffer.*;
import io.netty.util.*;

public class CompatibleObjectEncoder extends MessageToByteEncoder
{
    private static final AttributeKey OOS;
    private final int resetInterval;
    private int writtenObjects;
    
    public CompatibleObjectEncoder() {
        this(16);
    }
    
    public CompatibleObjectEncoder(final int resetInterval) {
        if (resetInterval < 0) {
            throw new IllegalArgumentException("resetInterval: " + resetInterval);
        }
        this.resetInterval = resetInterval;
    }
    
    protected ObjectOutputStream newObjectOutputStream(final OutputStream outputStream) throws Exception {
        return new ObjectOutputStream(outputStream);
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Serializable s, final ByteBuf byteBuf) throws Exception {
        final Attribute attr = channelHandlerContext.attr(CompatibleObjectEncoder.OOS);
        ObjectOutputStream objectOutputStream = (ObjectOutputStream)attr.get();
        if (objectOutputStream == null) {
            objectOutputStream = this.newObjectOutputStream(new ByteBufOutputStream(byteBuf));
            final ObjectOutputStream objectOutputStream2 = (ObjectOutputStream)attr.setIfAbsent(objectOutputStream);
            if (objectOutputStream2 != null) {
                objectOutputStream = objectOutputStream2;
            }
        }
        // monitorenter(objectOutputStream3 = objectOutputStream)
        if (this.resetInterval != 0) {
            ++this.writtenObjects;
            if (this.writtenObjects % this.resetInterval == 0) {
                objectOutputStream.reset();
            }
        }
        objectOutputStream.writeObject(s);
        objectOutputStream.flush();
    }
    // monitorexit(objectOutputStream3)
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (Serializable)o, byteBuf);
    }
    
    static {
        OOS = AttributeKey.valueOf(CompatibleObjectEncoder.class.getName() + ".OOS");
    }
}
