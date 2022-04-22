package io.netty.handler.ssl;

import io.netty.handler.codec.*;
import java.util.regex.*;
import java.net.*;
import javax.net.ssl.*;
import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import io.netty.util.concurrent.*;

public class SslHandler extends ByteToMessageDecoder implements ChannelOutboundHandler
{
    private static final InternalLogger logger;
    private static final Pattern IGNORABLE_CLASS_IN_STACK;
    private static final Pattern IGNORABLE_ERROR_MESSAGE;
    private static final SSLException SSLENGINE_CLOSED;
    private static final SSLException HANDSHAKE_TIMED_OUT;
    private static final ClosedChannelException CHANNEL_CLOSED;
    private ChannelHandlerContext ctx;
    private final SSLEngine engine;
    private final int maxPacketBufferSize;
    private final Executor delegatedTaskExecutor;
    private final boolean wantsDirectBuffer;
    private final boolean wantsLargeOutboundNetworkBuffer;
    private boolean wantsInboundHeapBuffer;
    private final boolean startTls;
    private boolean sentFirstMessage;
    private boolean flushedBeforeHandshakeDone;
    private PendingWriteQueue pendingUnencryptedWrites;
    private final LazyChannelPromise handshakePromise;
    private final LazyChannelPromise sslCloseFuture;
    private boolean needsFlush;
    private int packetLength;
    private long handshakeTimeoutMillis;
    private long closeNotifyTimeoutMillis;
    static final boolean $assertionsDisabled;
    
    public SslHandler(final SSLEngine sslEngine) {
        this(sslEngine, false);
    }
    
    public SslHandler(final SSLEngine sslEngine, final boolean b) {
        this(sslEngine, b, ImmediateExecutor.INSTANCE);
    }
    
    @Deprecated
    public SslHandler(final SSLEngine sslEngine, final Executor executor) {
        this(sslEngine, false, executor);
    }
    
    @Deprecated
    public SslHandler(final SSLEngine engine, final boolean startTls, final Executor delegatedTaskExecutor) {
        this.handshakePromise = new LazyChannelPromise(null);
        this.sslCloseFuture = new LazyChannelPromise(null);
        this.handshakeTimeoutMillis = 10000L;
        this.closeNotifyTimeoutMillis = 3000L;
        if (engine == null) {
            throw new NullPointerException("engine");
        }
        if (delegatedTaskExecutor == null) {
            throw new NullPointerException("delegatedTaskExecutor");
        }
        this.engine = engine;
        this.delegatedTaskExecutor = delegatedTaskExecutor;
        this.startTls = startTls;
        this.maxPacketBufferSize = engine.getSession().getPacketBufferSize();
        this.wantsDirectBuffer = (engine instanceof OpenSslEngine);
        this.wantsLargeOutboundNetworkBuffer = !(engine instanceof OpenSslEngine);
    }
    
    public long getHandshakeTimeoutMillis() {
        return this.handshakeTimeoutMillis;
    }
    
    public void setHandshakeTimeout(final long n, final TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        this.setHandshakeTimeoutMillis(timeUnit.toMillis(n));
    }
    
    public void setHandshakeTimeoutMillis(final long handshakeTimeoutMillis) {
        if (handshakeTimeoutMillis < 0L) {
            throw new IllegalArgumentException("handshakeTimeoutMillis: " + handshakeTimeoutMillis + " (expected: >= 0)");
        }
        this.handshakeTimeoutMillis = handshakeTimeoutMillis;
    }
    
    public long getCloseNotifyTimeoutMillis() {
        return this.closeNotifyTimeoutMillis;
    }
    
    public void setCloseNotifyTimeout(final long n, final TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        this.setCloseNotifyTimeoutMillis(timeUnit.toMillis(n));
    }
    
    public void setCloseNotifyTimeoutMillis(final long closeNotifyTimeoutMillis) {
        if (closeNotifyTimeoutMillis < 0L) {
            throw new IllegalArgumentException("closeNotifyTimeoutMillis: " + closeNotifyTimeoutMillis + " (expected: >= 0)");
        }
        this.closeNotifyTimeoutMillis = closeNotifyTimeoutMillis;
    }
    
    public SSLEngine engine() {
        return this.engine;
    }
    
    public Future handshakeFuture() {
        return this.handshakePromise;
    }
    
    public ChannelFuture close() {
        return this.close(this.ctx.newPromise());
    }
    
    public ChannelFuture close(final ChannelPromise channelPromise) {
        final ChannelHandlerContext ctx = this.ctx;
        ctx.executor().execute(new Runnable(ctx, channelPromise) {
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$future;
            final SslHandler this$0;
            
            @Override
            public void run() {
                SslHandler.access$100(this.this$0).closeOutbound();
                this.this$0.write(this.val$ctx, Unpooled.EMPTY_BUFFER, this.val$future);
                this.this$0.flush(this.val$ctx);
            }
        });
        return channelPromise;
    }
    
    public Future sslCloseFuture() {
        return this.sslCloseFuture;
    }
    
    public void handlerRemoved0(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.removeAndFailAll(new ChannelException("Pending write on removal of SslHandler"));
        }
    }
    
    @Override
    public void bind(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }
    
    @Override
    public void connect(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        this.closeOutboundAndChannel(channelHandlerContext, channelPromise, true);
    }
    
    @Override
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        this.closeOutboundAndChannel(channelHandlerContext, channelPromise, false);
    }
    
    @Override
    public void read(final ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.read();
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        this.pendingUnencryptedWrites.add(o, channelPromise);
    }
    
    @Override
    public void flush(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.startTls && !this.sentFirstMessage) {
            this.sentFirstMessage = true;
            this.pendingUnencryptedWrites.removeAndWriteAll();
            channelHandlerContext.flush();
            return;
        }
        if (this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, channelHandlerContext.voidPromise());
        }
        if (!this.handshakePromise.isDone()) {
            this.flushedBeforeHandshakeDone = true;
        }
        this.wrap(channelHandlerContext, false);
        channelHandlerContext.flush();
    }
    
    private void wrap(final ChannelHandlerContext channelHandlerContext, final boolean b) throws SSLException {
        ByteBuf allocateOutNetBuf = null;
        ChannelPromise remove = null;
        while (true) {
            final Object current = this.pendingUnencryptedWrites.current();
            if (current == null) {
                this.finishWrap(channelHandlerContext, allocateOutNetBuf, remove, b);
                return;
            }
            if (!(current instanceof ByteBuf)) {
                this.pendingUnencryptedWrites.removeAndWrite();
            }
            else {
                final ByteBuf byteBuf = (ByteBuf)current;
                if (allocateOutNetBuf == null) {
                    allocateOutNetBuf = this.allocateOutNetBuf(channelHandlerContext, byteBuf.readableBytes());
                }
                final SSLEngineResult wrap = this.wrap(this.engine, byteBuf, allocateOutNetBuf);
                if (!byteBuf.isReadable()) {
                    remove = this.pendingUnencryptedWrites.remove();
                }
                else {
                    remove = null;
                }
                if (wrap.getStatus() == SSLEngineResult.Status.CLOSED) {
                    this.pendingUnencryptedWrites.removeAndFailAll(SslHandler.SSLENGINE_CLOSED);
                    this.finishWrap(channelHandlerContext, allocateOutNetBuf, remove, b);
                    return;
                }
                switch (wrap.getHandshakeStatus()) {
                    case NEED_TASK: {
                        this.runDelegatedTasks();
                        continue;
                    }
                    case FINISHED: {
                        this.setHandshakeSuccess();
                    }
                    case NOT_HANDSHAKING: {
                        this.setHandshakeSuccessIfStillHandshaking();
                    }
                    case NEED_WRAP: {
                        this.finishWrap(channelHandlerContext, allocateOutNetBuf, remove, b);
                        remove = null;
                        allocateOutNetBuf = null;
                        continue;
                    }
                    case NEED_UNWRAP: {
                        this.finishWrap(channelHandlerContext, allocateOutNetBuf, remove, b);
                    }
                    default: {
                        throw new IllegalStateException("Unknown handshake status: " + wrap.getHandshakeStatus());
                    }
                }
            }
        }
    }
    
    private void finishWrap(final ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, final ChannelPromise channelPromise, final boolean b) {
        if (byteBuf == null) {
            byteBuf = Unpooled.EMPTY_BUFFER;
        }
        else if (!byteBuf.isReadable()) {
            byteBuf.release();
            byteBuf = Unpooled.EMPTY_BUFFER;
        }
        if (channelPromise != null) {
            channelHandlerContext.write(byteBuf, channelPromise);
        }
        else {
            channelHandlerContext.write(byteBuf);
        }
        if (b) {
            this.needsFlush = true;
        }
    }
    
    private void wrapNonAppData(final ChannelHandlerContext channelHandlerContext, final boolean b) throws SSLException {
        ByteBuf allocateOutNetBuf = null;
        while (true) {
            if (allocateOutNetBuf == null) {
                allocateOutNetBuf = this.allocateOutNetBuf(channelHandlerContext, 0);
            }
            final SSLEngineResult wrap = this.wrap(this.engine, Unpooled.EMPTY_BUFFER, allocateOutNetBuf);
            if (wrap.bytesProduced() > 0) {
                channelHandlerContext.write(allocateOutNetBuf);
                if (b) {
                    this.needsFlush = true;
                }
                allocateOutNetBuf = null;
            }
            switch (wrap.getHandshakeStatus()) {
                case FINISHED: {
                    this.setHandshakeSuccess();
                    break;
                }
                case NEED_TASK: {
                    this.runDelegatedTasks();
                    break;
                }
                case NEED_UNWRAP: {
                    if (!b) {
                        this.unwrapNonAppData(channelHandlerContext);
                        break;
                    }
                    break;
                }
                case NEED_WRAP: {
                    break;
                }
                case NOT_HANDSHAKING: {
                    this.setHandshakeSuccessIfStillHandshaking();
                    if (!b) {
                        this.unwrapNonAppData(channelHandlerContext);
                        break;
                    }
                    break;
                }
                default: {
                    throw new IllegalStateException("Unknown handshake status: " + wrap.getHandshakeStatus());
                }
            }
            if (wrap.bytesProduced() == 0) {
                if (allocateOutNetBuf != null) {
                    allocateOutNetBuf.release();
                }
            }
        }
    }
    
    private SSLEngineResult wrap(final SSLEngine sslEngine, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws SSLException {
        ByteBuffer nioBuffer = byteBuf.nioBuffer();
        if (!nioBuffer.isDirect()) {
            final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(nioBuffer.remaining());
            allocateDirect.put(nioBuffer).flip();
            nioBuffer = allocateDirect;
        }
        while (true) {
            final SSLEngineResult wrap = sslEngine.wrap(nioBuffer, byteBuf2.nioBuffer(byteBuf2.writerIndex(), byteBuf2.writableBytes()));
            byteBuf.skipBytes(wrap.bytesConsumed());
            byteBuf2.writerIndex(byteBuf2.writerIndex() + wrap.bytesProduced());
            switch (wrap.getStatus()) {
                case BUFFER_OVERFLOW: {
                    byteBuf2.ensureWritable(this.maxPacketBufferSize);
                    continue;
                }
                default: {
                    return wrap;
                }
            }
        }
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.setHandshakeFailure(SslHandler.CHANNEL_CLOSED);
        super.channelInactive(channelHandlerContext);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (this.ignoreException(t)) {
            if (SslHandler.logger.isDebugEnabled()) {
                SslHandler.logger.debug("Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", t);
            }
            if (channelHandlerContext.channel().isActive()) {
                channelHandlerContext.close();
            }
        }
        else {
            channelHandlerContext.fireExceptionCaught(t);
        }
    }
    
    private boolean ignoreException(final Throwable t) {
        if (!(t instanceof SSLException) && t instanceof IOException && this.sslCloseFuture.isDone()) {
            if (SslHandler.IGNORABLE_ERROR_MESSAGE.matcher(String.valueOf(t.getMessage()).toLowerCase()).matches()) {
                return true;
            }
            final StackTraceElement[] stackTrace = t.getStackTrace();
            while (0 < stackTrace.length) {
                final StackTraceElement stackTraceElement = stackTrace[0];
                final String className = stackTraceElement.getClassName();
                final String methodName = stackTraceElement.getMethodName();
                if (!className.startsWith("io.netty.")) {
                    if ("read".equals(methodName)) {
                        if (SslHandler.IGNORABLE_CLASS_IN_STACK.matcher(className).matches()) {
                            return true;
                        }
                        final Class<?> loadClass = PlatformDependent.getClassLoader(this.getClass()).loadClass(className);
                        if (SocketChannel.class.isAssignableFrom(loadClass) || DatagramChannel.class.isAssignableFrom(loadClass)) {
                            return true;
                        }
                        if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(loadClass.getSuperclass().getName())) {
                            return true;
                        }
                    }
                }
                int n = 0;
                ++n;
            }
        }
        return false;
    }
    
    public static boolean isEncrypted(final ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 5) {
            throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
        }
        return getEncryptedPacketLength(byteBuf, byteBuf.readerIndex()) != -1;
    }
    
    private static int getEncryptedPacketLength(final ByteBuf byteBuf, final int n) {
        switch (byteBuf.getUnsignedByte(n)) {
        }
        if (false) {
            byteBuf.getUnsignedByte(n + 1);
            if (0 == 3) {
                final int n2 = byteBuf.getUnsignedShort(n + 3) + 5;
                if (0 <= 5) {}
            }
        }
        if (!false) {
            final int n3 = ((byteBuf.getUnsignedByte(n) & 0x80) != 0x0) ? 2 : 3;
            final short unsignedByte = byteBuf.getUnsignedByte(n + n3 + 1);
            if (unsignedByte == 2 || unsignedByte == 3) {
                if (n3 == 2) {
                    final int n4 = (byteBuf.getShort(n) & 0x7FFF) + 2;
                }
                else {
                    final int n5 = (byteBuf.getShort(n) & 0x3FFF) + 3;
                }
                if (0 <= n3) {}
            }
            if (!false) {
                return -1;
            }
        }
        return 0;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws SSLException {
        final int readerIndex = byteBuf.readerIndex();
        final int writerIndex = byteBuf.writerIndex();
        int n = readerIndex;
        if (this.packetLength > 0) {
            if (writerIndex - readerIndex < this.packetLength) {
                return;
            }
            n += this.packetLength;
            final int packetLength = this.packetLength;
            this.packetLength = 0;
        }
        while (0 < 18713) {
            final int n2 = writerIndex - n;
            if (n2 < 5) {
                break;
            }
            final int encryptedPacketLength = getEncryptedPacketLength(byteBuf, n);
            if (encryptedPacketLength == -1) {
                break;
            }
            assert encryptedPacketLength > 0;
            if (encryptedPacketLength > n2) {
                this.packetLength = encryptedPacketLength;
                break;
            }
            if (0 + encryptedPacketLength > 18713) {
                break;
            }
            n += encryptedPacketLength;
        }
        if (0 > 0) {
            byteBuf.skipBytes(0);
            final ByteBuffer nioBuffer = byteBuf.nioBuffer(readerIndex, 0);
            this.unwrap(channelHandlerContext, nioBuffer, 0);
            assert !(!this.engine.isInboundDone());
        }
        if (true) {
            final NotSslRecordException handshakeFailure = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(byteBuf));
            byteBuf.skipBytes(byteBuf.readableBytes());
            channelHandlerContext.fireExceptionCaught(handshakeFailure);
            this.setHandshakeFailure(handshakeFailure);
        }
    }
    
    @Override
    public void channelReadComplete(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.needsFlush) {
            this.needsFlush = false;
            channelHandlerContext.flush();
        }
        super.channelReadComplete(channelHandlerContext);
    }
    
    private void unwrapNonAppData(final ChannelHandlerContext channelHandlerContext) throws SSLException {
        this.unwrap(channelHandlerContext, Unpooled.EMPTY_BUFFER.nioBuffer(), 0);
    }
    
    private void unwrap(final ChannelHandlerContext channelHandlerContext, ByteBuffer nioBuffer, final int n) throws SSLException {
        final int position = nioBuffer.position();
        ByteBuf heapBuffer;
        ByteBuffer byteBuffer;
        if (this.wantsInboundHeapBuffer && nioBuffer.isDirect()) {
            heapBuffer = channelHandlerContext.alloc().heapBuffer(nioBuffer.limit() - position);
            heapBuffer.writeBytes(nioBuffer);
            byteBuffer = nioBuffer;
            nioBuffer = heapBuffer.nioBuffer();
        }
        else {
            byteBuffer = null;
            heapBuffer = null;
        }
        final ByteBuf allocate = this.allocate(channelHandlerContext, n);
        while (true) {
            final SSLEngineResult unwrap = unwrap(this.engine, nioBuffer, allocate);
            final SSLEngineResult.Status status = unwrap.getStatus();
            final SSLEngineResult.HandshakeStatus handshakeStatus = unwrap.getHandshakeStatus();
            final int bytesProduced = unwrap.bytesProduced();
            final int bytesConsumed = unwrap.bytesConsumed();
            if (status == SSLEngineResult.Status.CLOSED) {
                this.sslCloseFuture.trySuccess(channelHandlerContext.channel());
                break;
            }
            switch (handshakeStatus) {
                case NEED_UNWRAP: {
                    break;
                }
                case NEED_WRAP: {
                    this.wrapNonAppData(channelHandlerContext, true);
                    break;
                }
                case NEED_TASK: {
                    this.runDelegatedTasks();
                    break;
                }
                case FINISHED: {
                    this.setHandshakeSuccess();
                    continue;
                }
                case NOT_HANDSHAKING: {
                    if (this.setHandshakeSuccessIfStillHandshaking()) {
                        continue;
                    }
                    if (this.flushedBeforeHandshakeDone) {
                        this.flushedBeforeHandshakeDone = false;
                        break;
                    }
                    break;
                }
                default: {
                    throw new IllegalStateException("Unknown handshake status: " + handshakeStatus);
                }
            }
            if (status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                break;
            }
            if (bytesConsumed == 0 && bytesProduced == 0) {
                break;
            }
        }
        if (true) {
            this.wrap(channelHandlerContext, true);
        }
        if (heapBuffer != null) {
            byteBuffer.position(position + nioBuffer.position());
            heapBuffer.release();
        }
        if (allocate.isReadable()) {
            channelHandlerContext.fireChannelRead(allocate);
        }
        else {
            allocate.release();
        }
    }
    
    private static SSLEngineResult unwrap(final SSLEngine sslEngine, final ByteBuffer byteBuffer, final ByteBuf byteBuf) throws SSLException {
        while (true) {
            final SSLEngineResult unwrap = sslEngine.unwrap(byteBuffer, byteBuf.nioBuffer(byteBuf.writerIndex(), byteBuf.writableBytes()));
            byteBuf.writerIndex(byteBuf.writerIndex() + unwrap.bytesProduced());
            switch (unwrap.getStatus()) {
                case BUFFER_OVERFLOW: {
                    final int applicationBufferSize = sslEngine.getSession().getApplicationBufferSize();
                    final boolean b = false;
                    int n = 0;
                    ++n;
                    switch (b) {
                        case 0: {
                            byteBuf.ensureWritable(Math.min(applicationBufferSize, byteBuffer.remaining()));
                            continue;
                        }
                        default: {
                            byteBuf.ensureWritable(applicationBufferSize);
                            continue;
                        }
                    }
                    continue;
                }
                default: {
                    return unwrap;
                }
            }
        }
    }
    
    private void runDelegatedTasks() {
        if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE) {
            while (true) {
                final Runnable delegatedTask = this.engine.getDelegatedTask();
                if (delegatedTask == null) {
                    break;
                }
                delegatedTask.run();
            }
        }
        else {
            final ArrayList<Runnable> list = new ArrayList<Runnable>(2);
            while (true) {
                final Runnable delegatedTask2 = this.engine.getDelegatedTask();
                if (delegatedTask2 == null) {
                    break;
                }
                list.add(delegatedTask2);
            }
            if (list.isEmpty()) {
                return;
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            this.delegatedTaskExecutor.execute(new Runnable((List)list, countDownLatch) {
                final List val$tasks;
                final CountDownLatch val$latch;
                final SslHandler this$0;
                
                @Override
                public void run() {
                    final Iterator<Runnable> iterator = this.val$tasks.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().run();
                    }
                    this.val$latch.countDown();
                }
            });
            while (countDownLatch.getCount() != 0L) {
                countDownLatch.await();
            }
            if (true) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private boolean setHandshakeSuccessIfStillHandshaking() {
        if (!this.handshakePromise.isDone()) {
            this.setHandshakeSuccess();
            return true;
        }
        return false;
    }
    
    private void setHandshakeSuccess() {
        final String value = String.valueOf(this.engine.getSession().getCipherSuite());
        if (!this.wantsDirectBuffer && (value.contains("_GCM_") || value.contains("-GCM-"))) {
            this.wantsInboundHeapBuffer = true;
        }
        if (this.handshakePromise.trySuccess(this.ctx.channel())) {
            if (SslHandler.logger.isDebugEnabled()) {
                SslHandler.logger.debug(this.ctx.channel() + " HANDSHAKEN: " + this.engine.getSession().getCipherSuite());
            }
            this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
        }
    }
    
    private void setHandshakeFailure(final Throwable t) {
        this.engine.closeOutbound();
        this.engine.closeInbound();
        this.notifyHandshakeFailure(t);
        this.pendingUnencryptedWrites.removeAndFailAll(t);
    }
    
    private void notifyHandshakeFailure(final Throwable t) {
        if (this.handshakePromise.tryFailure(t)) {
            this.ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(t));
            this.ctx.close();
        }
    }
    
    private void closeOutboundAndChannel(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise, final boolean b) throws Exception {
        if (!channelHandlerContext.channel().isActive()) {
            if (b) {
                channelHandlerContext.disconnect(channelPromise);
            }
            else {
                channelHandlerContext.close(channelPromise);
            }
            return;
        }
        this.engine.closeOutbound();
        final ChannelPromise promise = channelHandlerContext.newPromise();
        this.write(channelHandlerContext, Unpooled.EMPTY_BUFFER, promise);
        this.flush(channelHandlerContext);
        this.safeClose(channelHandlerContext, promise, channelPromise);
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        this.pendingUnencryptedWrites = new PendingWriteQueue(ctx);
        if (ctx.channel().isActive() && this.engine.getUseClientMode()) {
            this.handshake();
        }
    }
    
    private Future handshake() {
        io.netty.util.concurrent.ScheduledFuture schedule;
        if (this.handshakeTimeoutMillis > 0L) {
            schedule = this.ctx.executor().schedule((Runnable)new Runnable() {
                final SslHandler this$0;
                
                @Override
                public void run() {
                    if (SslHandler.access$400(this.this$0).isDone()) {
                        return;
                    }
                    SslHandler.access$600(this.this$0, SslHandler.access$500());
                }
            }, this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        else {
            schedule = null;
        }
        this.handshakePromise.addListener((GenericFutureListener)new GenericFutureListener((ScheduledFuture)schedule) {
            final ScheduledFuture val$timeoutFuture;
            final SslHandler this$0;
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                if (this.val$timeoutFuture != null) {
                    this.val$timeoutFuture.cancel(false);
                }
            }
        });
        this.engine.beginHandshake();
        this.wrapNonAppData(this.ctx, false);
        this.ctx.flush();
        return this.handshakePromise;
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.startTls && this.engine.getUseClientMode()) {
            this.handshake().addListener(new GenericFutureListener(channelHandlerContext) {
                final ChannelHandlerContext val$ctx;
                final SslHandler this$0;
                
                @Override
                public void operationComplete(final Future future) throws Exception {
                    if (!future.isSuccess()) {
                        SslHandler.access$200().debug("Failed to complete handshake", future.cause());
                        this.val$ctx.close();
                    }
                }
            });
        }
        channelHandlerContext.fireChannelActive();
    }
    
    private void safeClose(final ChannelHandlerContext channelHandlerContext, final ChannelFuture channelFuture, final ChannelPromise channelPromise) {
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        io.netty.util.concurrent.ScheduledFuture schedule;
        if (this.closeNotifyTimeoutMillis > 0L) {
            schedule = channelHandlerContext.executor().schedule((Runnable)new Runnable(channelHandlerContext, channelPromise) {
                final ChannelHandlerContext val$ctx;
                final ChannelPromise val$promise;
                final SslHandler this$0;
                
                @Override
                public void run() {
                    SslHandler.access$200().warn(this.val$ctx.channel() + " last write attempt timed out." + " Force-closing the connection.");
                    this.val$ctx.close(this.val$promise);
                }
            }, this.closeNotifyTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        else {
            schedule = null;
        }
        channelFuture.addListener((GenericFutureListener)new ChannelFutureListener((ScheduledFuture)schedule, channelHandlerContext, channelPromise) {
            final ScheduledFuture val$timeoutFuture;
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$promise;
            final SslHandler this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (this.val$timeoutFuture != null) {
                    this.val$timeoutFuture.cancel(false);
                }
                this.val$ctx.close(this.val$promise);
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }
    
    private ByteBuf allocate(final ChannelHandlerContext channelHandlerContext, final int n) {
        final ByteBufAllocator alloc = channelHandlerContext.alloc();
        if (this.wantsDirectBuffer) {
            return alloc.directBuffer(n);
        }
        return alloc.buffer(n);
    }
    
    private ByteBuf allocateOutNetBuf(final ChannelHandlerContext channelHandlerContext, final int n) {
        if (this.wantsLargeOutboundNetworkBuffer) {
            return this.allocate(channelHandlerContext, this.maxPacketBufferSize);
        }
        return this.allocate(channelHandlerContext, Math.min(n + 2329, this.maxPacketBufferSize));
    }
    
    static SSLEngine access$100(final SslHandler sslHandler) {
        return sslHandler.engine;
    }
    
    static InternalLogger access$200() {
        return SslHandler.logger;
    }
    
    static ChannelHandlerContext access$300(final SslHandler sslHandler) {
        return sslHandler.ctx;
    }
    
    static LazyChannelPromise access$400(final SslHandler sslHandler) {
        return sslHandler.handshakePromise;
    }
    
    static SSLException access$500() {
        return SslHandler.HANDSHAKE_TIMED_OUT;
    }
    
    static void access$600(final SslHandler sslHandler, final Throwable t) {
        sslHandler.notifyHandshakeFailure(t);
    }
    
    static {
        $assertionsDisabled = !SslHandler.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(SslHandler.class);
        IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
        IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
        SSLENGINE_CLOSED = new SSLException("SSLEngine closed already");
        HANDSHAKE_TIMED_OUT = new SSLException("handshake timed out");
        CHANNEL_CLOSED = new ClosedChannelException();
        SslHandler.SSLENGINE_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        SslHandler.HANDSHAKE_TIMED_OUT.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        SslHandler.CHANNEL_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }
    
    private final class LazyChannelPromise extends DefaultPromise
    {
        final SslHandler this$0;
        
        private LazyChannelPromise(final SslHandler this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        protected EventExecutor executor() {
            if (SslHandler.access$300(this.this$0) == null) {
                throw new IllegalStateException();
            }
            return SslHandler.access$300(this.this$0).executor();
        }
        
        LazyChannelPromise(final SslHandler sslHandler, final SslHandler$1 runnable) {
            this(sslHandler);
        }
    }
}
