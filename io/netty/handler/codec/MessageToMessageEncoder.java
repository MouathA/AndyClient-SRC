package io.netty.handler.codec;

import io.netty.channel.*;
import io.netty.util.*;
import io.netty.util.internal.*;
import java.util.*;

public abstract class MessageToMessageEncoder extends ChannelOutboundHandlerAdapter
{
    private final TypeParameterMatcher matcher;
    
    protected MessageToMessageEncoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
    }
    
    protected MessageToMessageEncoder(final Class clazz) {
        this.matcher = TypeParameterMatcher.get(clazz);
    }
    
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return this.matcher.match(o);
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        ArrayList<Object> instance = null;
        if (this.acceptOutboundMessage(o)) {
            instance = (ArrayList<Object>)RecyclableArrayList.newInstance();
            this.encode(channelHandlerContext, o, instance);
            ReferenceCountUtil.release(o);
            if (instance.isEmpty()) {
                ((RecyclableArrayList)instance).recycle();
                throw new EncoderException(StringUtil.simpleClassName(this) + " must produce at least one message.");
            }
        }
        else {
            channelHandlerContext.write(o, channelPromise);
        }
        if (instance != null) {
            final int n = instance.size() - 1;
            if (n == 0) {
                channelHandlerContext.write(instance.get(0), channelPromise);
            }
            else if (n > 0) {
                final ChannelPromise voidPromise = channelHandlerContext.voidPromise();
                final boolean b = channelPromise == voidPromise;
                while (0 < n) {
                    ChannelPromise promise;
                    if (b) {
                        promise = voidPromise;
                    }
                    else {
                        promise = channelHandlerContext.newPromise();
                    }
                    channelHandlerContext.write(instance.get(0), promise);
                    int n2 = 0;
                    ++n2;
                }
                channelHandlerContext.write(instance.get(n), channelPromise);
            }
            ((RecyclableArrayList)instance).recycle();
        }
    }
    
    protected abstract void encode(final ChannelHandlerContext p0, final Object p1, final List p2) throws Exception;
}
