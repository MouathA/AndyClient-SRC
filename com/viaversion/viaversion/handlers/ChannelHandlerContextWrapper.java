package com.viaversion.viaversion.handlers;

import io.netty.util.concurrent.*;
import java.net.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.util.*;

public class ChannelHandlerContextWrapper implements ChannelHandlerContext
{
    private final ChannelHandlerContext base;
    private final ViaCodecHandler handler;
    
    public ChannelHandlerContextWrapper(final ChannelHandlerContext base, final ViaCodecHandler handler) {
        this.base = base;
        this.handler = handler;
    }
    
    @Override
    public Channel channel() {
        return this.base.channel();
    }
    
    @Override
    public EventExecutor executor() {
        return this.base.executor();
    }
    
    @Override
    public String name() {
        return this.base.name();
    }
    
    @Override
    public ChannelHandler handler() {
        return this.base.handler();
    }
    
    @Override
    public boolean isRemoved() {
        return this.base.isRemoved();
    }
    
    @Override
    public ChannelHandlerContext fireChannelRegistered() {
        this.base.fireChannelRegistered();
        return this;
    }
    
    @Override
    public ChannelHandlerContext fireChannelUnregistered() {
        this.base.fireChannelUnregistered();
        return this;
    }
    
    @Override
    public ChannelHandlerContext fireChannelActive() {
        this.base.fireChannelActive();
        return this;
    }
    
    @Override
    public ChannelHandlerContext fireChannelInactive() {
        this.base.fireChannelInactive();
        return this;
    }
    
    @Override
    public ChannelHandlerContext fireExceptionCaught(final Throwable t) {
        this.base.fireExceptionCaught(t);
        return this;
    }
    
    @Override
    public ChannelHandlerContext fireUserEventTriggered(final Object o) {
        this.base.fireUserEventTriggered(o);
        return this;
    }
    
    @Override
    public ChannelHandlerContext fireChannelRead(final Object o) {
        this.base.fireChannelRead(o);
        return this;
    }
    
    @Override
    public ChannelHandlerContext fireChannelReadComplete() {
        this.base.fireChannelReadComplete();
        return this;
    }
    
    @Override
    public ChannelHandlerContext fireChannelWritabilityChanged() {
        this.base.fireChannelWritabilityChanged();
        return this;
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress socketAddress) {
        return this.base.bind(socketAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress) {
        return this.base.connect(socketAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2) {
        return this.base.connect(socketAddress, socketAddress2);
    }
    
    @Override
    public ChannelFuture disconnect() {
        return this.base.disconnect();
    }
    
    @Override
    public ChannelFuture close() {
        return this.base.close();
    }
    
    @Override
    public ChannelFuture deregister() {
        return this.base.deregister();
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        return this.base.bind(socketAddress, channelPromise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        return this.base.connect(socketAddress, channelPromise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        return this.base.connect(socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public ChannelFuture disconnect(final ChannelPromise channelPromise) {
        return this.base.disconnect(channelPromise);
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise channelPromise) {
        return this.base.close(channelPromise);
    }
    
    @Override
    public ChannelFuture deregister(final ChannelPromise channelPromise) {
        return this.base.deregister(channelPromise);
    }
    
    @Override
    public ChannelHandlerContext read() {
        this.base.read();
        return this;
    }
    
    @Override
    public ChannelFuture write(final Object o) {
        if (o instanceof ByteBuf && this.transform((ByteBuf)o)) {
            return this.base.newFailedFuture(new Throwable());
        }
        return this.base.write(o);
    }
    
    @Override
    public ChannelFuture write(final Object o, final ChannelPromise channelPromise) {
        if (o instanceof ByteBuf && this.transform((ByteBuf)o)) {
            return this.base.newFailedFuture(new Throwable());
        }
        return this.base.write(o, channelPromise);
    }
    
    public boolean transform(final ByteBuf byteBuf) {
        this.handler.transform(byteBuf);
        return false;
    }
    
    @Override
    public ChannelHandlerContext flush() {
        this.base.flush();
        return this;
    }
    
    @Override
    public ChannelFuture writeAndFlush(final Object o, final ChannelPromise channelPromise) {
        final ChannelFuture write = this.write(o, channelPromise);
        this.flush();
        return write;
    }
    
    @Override
    public ChannelFuture writeAndFlush(final Object o) {
        final ChannelFuture write = this.write(o);
        this.flush();
        return write;
    }
    
    @Override
    public ChannelPipeline pipeline() {
        return this.base.pipeline();
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.base.alloc();
    }
    
    @Override
    public ChannelPromise newPromise() {
        return this.base.newPromise();
    }
    
    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return this.base.newProgressivePromise();
    }
    
    @Override
    public ChannelFuture newSucceededFuture() {
        return this.base.newSucceededFuture();
    }
    
    @Override
    public ChannelFuture newFailedFuture(final Throwable t) {
        return this.base.newFailedFuture(t);
    }
    
    @Override
    public ChannelPromise voidPromise() {
        return this.base.voidPromise();
    }
    
    @Override
    public Attribute attr(final AttributeKey attributeKey) {
        return this.base.attr(attributeKey);
    }
}
