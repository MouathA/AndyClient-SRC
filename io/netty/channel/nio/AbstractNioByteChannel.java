package io.netty.channel.nio;

import io.netty.util.internal.*;
import java.nio.channels.*;
import io.netty.channel.socket.*;
import java.io.*;
import io.netty.channel.*;
import io.netty.buffer.*;

public abstract class AbstractNioByteChannel extends AbstractNioChannel
{
    private static final String EXPECTED_TYPES;
    private Runnable flushTask;
    
    protected AbstractNioByteChannel(final Channel channel, final SelectableChannel selectableChannel) {
        super(channel, selectableChannel, 1);
    }
    
    @Override
    protected AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe(null);
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer p0) throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   io/netty/channel/ChannelOutboundBuffer.current:()Ljava/lang/Object;
        //     4: astore_3       
        //     5: aload_3        
        //     6: ifnonnull       16
        //     9: aload_0        
        //    10: invokevirtual   io/netty/channel/nio/AbstractNioByteChannel.clearOpWrite:()V
        //    13: goto            269
        //    16: aload_3        
        //    17: instanceof      Lio/netty/buffer/ByteBuf;
        //    20: ifeq            141
        //    23: aload_3        
        //    24: checkcast       Lio/netty/buffer/ByteBuf;
        //    27: astore          4
        //    29: aload           4
        //    31: invokevirtual   io/netty/buffer/ByteBuf.readableBytes:()I
        //    34: istore          5
        //    36: iconst_1       
        //    37: ifne            48
        //    40: aload_1        
        //    41: invokevirtual   io/netty/channel/ChannelOutboundBuffer.remove:()Z
        //    44: pop            
        //    45: goto            0
        //    48: lconst_0       
        //    49: lstore          8
        //    51: iconst_m1      
        //    52: iconst_m1      
        //    53: if_icmpne       66
        //    56: aload_0        
        //    57: invokevirtual   io/netty/channel/nio/AbstractNioByteChannel.config:()Lio/netty/channel/ChannelConfig;
        //    60: invokeinterface io/netty/channel/ChannelConfig.getWriteSpinCount:()I
        //    65: istore_2       
        //    66: bipush          -2
        //    68: iflt            112
        //    71: aload_0        
        //    72: aload           4
        //    74: invokevirtual   io/netty/channel/nio/AbstractNioByteChannel.doWriteBytes:(Lio/netty/buffer/ByteBuf;)I
        //    77: istore          11
        //    79: iload           11
        //    81: ifne            87
        //    84: goto            112
        //    87: lload           8
        //    89: iload           11
        //    91: i2l            
        //    92: ladd           
        //    93: lstore          8
        //    95: aload           4
        //    97: invokevirtual   io/netty/buffer/ByteBuf.isReadable:()Z
        //   100: ifne            106
        //   103: goto            112
        //   106: iinc            10, -1
        //   109: goto            66
        //   112: aload_1        
        //   113: lload           8
        //   115: invokevirtual   io/netty/channel/ChannelOutboundBuffer.progress:(J)V
        //   118: iconst_1       
        //   119: ifeq            130
        //   122: aload_1        
        //   123: invokevirtual   io/netty/channel/ChannelOutboundBuffer.remove:()Z
        //   126: pop            
        //   127: goto            138
        //   130: aload_0        
        //   131: iconst_1       
        //   132: invokevirtual   io/netty/channel/nio/AbstractNioByteChannel.incompleteWrite:(Z)V
        //   135: goto            269
        //   138: goto            266
        //   141: aload_3        
        //   142: instanceof      Lio/netty/channel/FileRegion;
        //   145: ifeq            258
        //   148: aload_3        
        //   149: checkcast       Lio/netty/channel/FileRegion;
        //   152: astore          4
        //   154: lconst_0       
        //   155: lstore          7
        //   157: iconst_m1      
        //   158: iconst_m1      
        //   159: if_icmpne       172
        //   162: aload_0        
        //   163: invokevirtual   io/netty/channel/nio/AbstractNioByteChannel.config:()Lio/netty/channel/ChannelConfig;
        //   166: invokeinterface io/netty/channel/ChannelConfig.getWriteSpinCount:()I
        //   171: istore_2       
        //   172: bipush          -2
        //   174: iflt            229
        //   177: aload_0        
        //   178: aload           4
        //   180: invokevirtual   io/netty/channel/nio/AbstractNioByteChannel.doWriteFileRegion:(Lio/netty/channel/FileRegion;)J
        //   183: lstore          10
        //   185: lload           10
        //   187: lconst_0       
        //   188: lcmp           
        //   189: ifne            195
        //   192: goto            229
        //   195: lload           7
        //   197: lload           10
        //   199: ladd           
        //   200: lstore          7
        //   202: aload           4
        //   204: invokeinterface io/netty/channel/FileRegion.transfered:()J
        //   209: aload           4
        //   211: invokeinterface io/netty/channel/FileRegion.count:()J
        //   216: lcmp           
        //   217: iflt            223
        //   220: goto            229
        //   223: iinc            9, -1
        //   226: goto            172
        //   229: aload_1        
        //   230: lload           7
        //   232: invokevirtual   io/netty/channel/ChannelOutboundBuffer.progress:(J)V
        //   235: iconst_1       
        //   236: ifeq            247
        //   239: aload_1        
        //   240: invokevirtual   io/netty/channel/ChannelOutboundBuffer.remove:()Z
        //   243: pop            
        //   244: goto            255
        //   247: aload_0        
        //   248: iconst_1       
        //   249: invokevirtual   io/netty/channel/nio/AbstractNioByteChannel.incompleteWrite:(Z)V
        //   252: goto            269
        //   255: goto            266
        //   258: new             Ljava/lang/Error;
        //   261: dup            
        //   262: invokespecial   java/lang/Error.<init>:()V
        //   265: athrow         
        //   266: goto            0
        //   269: return         
        //    Exceptions:
        //  throws java.lang.Exception
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected final Object filterOutboundMessage(final Object o) {
        if (o instanceof ByteBuf) {
            final ByteBuf byteBuf = (ByteBuf)o;
            if (byteBuf.isDirect()) {
                return o;
            }
            return this.newDirectBuffer(byteBuf);
        }
        else {
            if (o instanceof FileRegion) {
                return o;
            }
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + AbstractNioByteChannel.EXPECTED_TYPES);
        }
    }
    
    protected final void incompleteWrite(final boolean b) {
        if (b) {
            this.setOpWrite();
        }
        else {
            Runnable flushTask = this.flushTask;
            if (flushTask == null) {
                final Runnable flushTask2 = new Runnable() {
                    final AbstractNioByteChannel this$0;
                    
                    @Override
                    public void run() {
                        this.this$0.flush();
                    }
                };
                this.flushTask = flushTask2;
                flushTask = flushTask2;
            }
            this.eventLoop().execute(flushTask);
        }
    }
    
    protected abstract long doWriteFileRegion(final FileRegion p0) throws Exception;
    
    protected abstract int doReadBytes(final ByteBuf p0) throws Exception;
    
    protected abstract int doWriteBytes(final ByteBuf p0) throws Exception;
    
    protected final void setOpWrite() {
        final SelectionKey selectionKey = this.selectionKey();
        if (!selectionKey.isValid()) {
            return;
        }
        final int interestOps = selectionKey.interestOps();
        if ((interestOps & 0x4) == 0x0) {
            selectionKey.interestOps(interestOps | 0x4);
        }
    }
    
    protected final void clearOpWrite() {
        final SelectionKey selectionKey = this.selectionKey();
        if (!selectionKey.isValid()) {
            return;
        }
        final int interestOps = selectionKey.interestOps();
        if ((interestOps & 0x4) != 0x0) {
            selectionKey.interestOps(interestOps & 0xFFFFFFFB);
        }
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }
    
    static {
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';
    }
    
    private final class NioByteUnsafe extends AbstractNioUnsafe
    {
        private RecvByteBufAllocator.Handle allocHandle;
        final AbstractNioByteChannel this$0;
        
        private NioByteUnsafe(final AbstractNioByteChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        private void closeOnRead(final ChannelPipeline channelPipeline) {
            final SelectionKey selectionKey = this.this$0.selectionKey();
            this.this$0.setInputShutdown();
            if (this.this$0.isOpen()) {
                if (Boolean.TRUE.equals(this.this$0.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                    selectionKey.interestOps(selectionKey.interestOps() & ~this.this$0.readInterestOp);
                    channelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                }
                else {
                    this.close(this.voidPromise());
                }
            }
        }
        
        private void handleReadException(final ChannelPipeline channelPipeline, final ByteBuf byteBuf, final Throwable t, final boolean b) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.this$0.setReadPending(false);
                    channelPipeline.fireChannelRead(byteBuf);
                }
                else {
                    byteBuf.release();
                }
            }
            channelPipeline.fireChannelReadComplete();
            channelPipeline.fireExceptionCaught(t);
            if (b || t instanceof IOException) {
                this.closeOnRead(channelPipeline);
            }
        }
        
        @Override
        public void read() {
            final ChannelConfig config = this.this$0.config();
            if (!config.isAutoRead() && !this.this$0.isReadPending()) {
                this.removeReadOp();
                return;
            }
            final ChannelPipeline pipeline = this.this$0.pipeline();
            final ByteBufAllocator allocator = config.getAllocator();
            final int maxMessagesPerRead = config.getMaxMessagesPerRead();
            RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
            if (allocHandle == null) {
                allocHandle = (this.allocHandle = config.getRecvByteBufAllocator().newHandle());
            }
            do {
                final ByteBuf allocate = allocHandle.allocate(allocator);
                final int writableBytes = allocate.writableBytes();
                final int doReadBytes = this.this$0.doReadBytes(allocate);
                if (doReadBytes <= 0) {
                    allocate.release();
                    final boolean b = doReadBytes < 0;
                    break;
                }
                pipeline.fireChannelRead(allocate);
                if (Integer.MAX_VALUE >= Integer.MAX_VALUE - doReadBytes) {
                    break;
                }
                if (!config.isAutoRead()) {
                    break;
                }
                if (doReadBytes < writableBytes) {
                    break;
                }
                int n = 0;
                ++n;
            } while (0 < maxMessagesPerRead);
            pipeline.fireChannelReadComplete();
            allocHandle.record(Integer.MAX_VALUE);
            if (!config.isAutoRead() && !this.this$0.isReadPending()) {
                this.removeReadOp();
            }
        }
        
        NioByteUnsafe(final AbstractNioByteChannel abstractNioByteChannel, final AbstractNioByteChannel$1 runnable) {
            this(abstractNioByteChannel);
        }
    }
}
