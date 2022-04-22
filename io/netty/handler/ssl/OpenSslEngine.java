package io.netty.handler.ssl;

import java.security.cert.*;
import javax.security.cert.*;
import java.util.concurrent.atomic.*;
import org.apache.tomcat.jni.*;
import io.netty.buffer.*;
import java.nio.*;
import io.netty.util.internal.*;
import javax.net.ssl.*;
import java.security.*;
import io.netty.util.internal.logging.*;

public final class OpenSslEngine extends SSLEngine
{
    private static final InternalLogger logger;
    private static final Certificate[] EMPTY_CERTIFICATES;
    private static final X509Certificate[] EMPTY_X509_CERTIFICATES;
    private static final SSLException ENGINE_CLOSED;
    private static final SSLException RENEGOTIATION_UNSUPPORTED;
    private static final SSLException ENCRYPTED_PACKET_OVERSIZED;
    private static final int MAX_PLAINTEXT_LENGTH = 16384;
    private static final int MAX_COMPRESSED_LENGTH = 17408;
    private static final int MAX_CIPHERTEXT_LENGTH = 18432;
    static final int MAX_ENCRYPTED_PACKET_LENGTH = 18713;
    static final int MAX_ENCRYPTION_OVERHEAD_LENGTH = 2329;
    private static final AtomicIntegerFieldUpdater DESTROYED_UPDATER;
    private long ssl;
    private long networkBIO;
    private int accepted;
    private boolean handshakeFinished;
    private boolean receivedShutdown;
    private int destroyed;
    private String cipher;
    private String applicationProtocol;
    private boolean isInboundDone;
    private boolean isOutboundDone;
    private boolean engineClosed;
    private int lastPrimingReadResult;
    private final ByteBufAllocator alloc;
    private final String fallbackApplicationProtocol;
    private SSLSession session;
    
    public OpenSslEngine(final long n, final ByteBufAllocator alloc, final String fallbackApplicationProtocol) {
        if (n == 0L) {
            throw new NullPointerException("sslContext");
        }
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        this.alloc = alloc;
        this.ssl = SSL.newSSL(n, true);
        this.networkBIO = SSL.makeNetworkBIO(this.ssl);
        this.fallbackApplicationProtocol = fallbackApplicationProtocol;
    }
    
    public synchronized void shutdown() {
        if (OpenSslEngine.DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
            SSL.freeSSL(this.ssl);
            SSL.freeBIO(this.networkBIO);
            final long n = 0L;
            this.networkBIO = n;
            this.ssl = n;
            final boolean isInboundDone = true;
            this.engineClosed = isInboundDone;
            this.isOutboundDone = isInboundDone;
            this.isInboundDone = isInboundDone;
        }
    }
    
    private int writePlaintextData(final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        final int limit = byteBuffer.limit();
        final int min = Math.min(limit - position, 16384);
        int n;
        if (byteBuffer.isDirect()) {
            n = SSL.writeToSSL(this.ssl, Buffer.address(byteBuffer) + position, min);
            if (n > 0) {
                byteBuffer.position(position + n);
                return n;
            }
        }
        else {
            final ByteBuf directBuffer = this.alloc.directBuffer(min);
            long n2;
            if (directBuffer.hasMemoryAddress()) {
                n2 = directBuffer.memoryAddress();
            }
            else {
                n2 = Buffer.address(directBuffer.nioBuffer());
            }
            byteBuffer.limit(position + min);
            directBuffer.setBytes(0, byteBuffer);
            byteBuffer.limit(limit);
            n = SSL.writeToSSL(this.ssl, n2, min);
            if (n > 0) {
                byteBuffer.position(position + n);
                final int n3 = n;
                directBuffer.release();
                return n3;
            }
            byteBuffer.position(position);
            directBuffer.release();
        }
        throw new IllegalStateException("SSL.writeToSSL() returned a non-positive value: " + n);
    }
    
    private int writeEncryptedData(final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        final int remaining = byteBuffer.remaining();
        if (byteBuffer.isDirect()) {
            final long n = Buffer.address(byteBuffer) + position;
            final int writeToBIO = SSL.writeToBIO(this.networkBIO, n, remaining);
            if (writeToBIO >= 0) {
                byteBuffer.position(position + writeToBIO);
                this.lastPrimingReadResult = SSL.readFromSSL(this.ssl, n, 0);
                return writeToBIO;
            }
        }
        else {
            final ByteBuf directBuffer = this.alloc.directBuffer(remaining);
            long n2;
            if (directBuffer.hasMemoryAddress()) {
                n2 = directBuffer.memoryAddress();
            }
            else {
                n2 = Buffer.address(directBuffer.nioBuffer());
            }
            directBuffer.setBytes(0, byteBuffer);
            final int writeToBIO2 = SSL.writeToBIO(this.networkBIO, n2, remaining);
            if (writeToBIO2 >= 0) {
                byteBuffer.position(position + writeToBIO2);
                this.lastPrimingReadResult = SSL.readFromSSL(this.ssl, n2, 0);
                final int n3 = writeToBIO2;
                directBuffer.release();
                return n3;
            }
            byteBuffer.position(position);
            directBuffer.release();
        }
        return 0;
    }
    
    private int readPlaintextData(final ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            final int position = byteBuffer.position();
            final int fromSSL = SSL.readFromSSL(this.ssl, Buffer.address(byteBuffer) + position, byteBuffer.limit() - position);
            if (fromSSL > 0) {
                byteBuffer.position(position + fromSSL);
                return fromSSL;
            }
        }
        else {
            final int position2 = byteBuffer.position();
            final int limit = byteBuffer.limit();
            final int min = Math.min(18713, limit - position2);
            final ByteBuf directBuffer = this.alloc.directBuffer(min);
            long n;
            if (directBuffer.hasMemoryAddress()) {
                n = directBuffer.memoryAddress();
            }
            else {
                n = Buffer.address(directBuffer.nioBuffer());
            }
            final int fromSSL2 = SSL.readFromSSL(this.ssl, n, min);
            if (fromSSL2 > 0) {
                byteBuffer.limit(position2 + fromSSL2);
                directBuffer.getBytes(0, byteBuffer);
                byteBuffer.limit(limit);
                final int n2 = fromSSL2;
                directBuffer.release();
                return n2;
            }
            directBuffer.release();
        }
        return 0;
    }
    
    private int readEncryptedData(final ByteBuffer byteBuffer, final int n) {
        if (byteBuffer.isDirect() && byteBuffer.remaining() >= n) {
            final int position = byteBuffer.position();
            final int fromBIO = SSL.readFromBIO(this.networkBIO, Buffer.address(byteBuffer) + position, n);
            if (fromBIO > 0) {
                byteBuffer.position(position + fromBIO);
                return fromBIO;
            }
        }
        else {
            final ByteBuf directBuffer = this.alloc.directBuffer(n);
            long n2;
            if (directBuffer.hasMemoryAddress()) {
                n2 = directBuffer.memoryAddress();
            }
            else {
                n2 = Buffer.address(directBuffer.nioBuffer());
            }
            final int fromBIO2 = SSL.readFromBIO(this.networkBIO, n2, n);
            if (fromBIO2 > 0) {
                final int limit = byteBuffer.limit();
                byteBuffer.limit(byteBuffer.position() + fromBIO2);
                directBuffer.getBytes(0, byteBuffer);
                byteBuffer.limit(limit);
                final int n3 = fromBIO2;
                directBuffer.release();
                return n3;
            }
            directBuffer.release();
        }
        return 0;
    }
    
    @Override
    public synchronized SSLEngineResult wrap(final ByteBuffer[] array, final int n, final int n2, final ByteBuffer byteBuffer) throws SSLException {
        if (this.destroyed != 0) {
            return new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
        }
        if (array == null) {
            throw new NullPointerException("srcs");
        }
        if (byteBuffer == null) {
            throw new NullPointerException("dst");
        }
        if (n >= array.length || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("offset: " + n + ", length: " + n2 + " (expected: offset <= offset + length <= srcs.length (" + array.length + "))");
        }
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        if (this.accepted == 0) {
            this.beginHandshakeImplicitly();
        }
        final SSLEngineResult.HandshakeStatus handshakeStatus = this.getHandshakeStatus();
        if ((!this.handshakeFinished || this.engineClosed) && handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
            return new SSLEngineResult(this.getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
        }
        final int pendingWrittenBytesInBIO = SSL.pendingWrittenBytesInBIO(this.networkBIO);
        if (pendingWrittenBytesInBIO <= 0) {
            for (int i = n; i < n2; ++i) {
                final ByteBuffer byteBuffer2 = array[i];
                while (byteBuffer2.hasRemaining()) {
                    final int n3 = 0 + this.writePlaintextData(byteBuffer2);
                    final int pendingWrittenBytesInBIO2 = SSL.pendingWrittenBytesInBIO(this.networkBIO);
                    if (pendingWrittenBytesInBIO2 > 0) {
                        if (byteBuffer.remaining() < pendingWrittenBytesInBIO2) {
                            return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.getHandshakeStatus(), 0, 0);
                        }
                        final int n4 = 0 + this.readEncryptedData(byteBuffer, pendingWrittenBytesInBIO2);
                        return new SSLEngineResult(this.getEngineStatus(), this.getHandshakeStatus(), 0, 0);
                    }
                }
            }
            return new SSLEngineResult(this.getEngineStatus(), this.getHandshakeStatus(), 0, 0);
        }
        byteBuffer.remaining();
        if (0 < pendingWrittenBytesInBIO) {
            return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, handshakeStatus, 0, 0);
        }
        final int n5 = 0 + this.readEncryptedData(byteBuffer, pendingWrittenBytesInBIO);
        if (this.isOutboundDone) {
            this.shutdown();
        }
        return new SSLEngineResult(this.getEngineStatus(), this.getHandshakeStatus(), 0, 0);
    }
    
    @Override
    public synchronized SSLEngineResult unwrap(final ByteBuffer byteBuffer, final ByteBuffer[] array, final int n, final int n2) throws SSLException {
        if (this.destroyed != 0) {
            return new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
        }
        if (byteBuffer == null) {
            throw new NullPointerException("src");
        }
        if (array == null) {
            throw new NullPointerException("dsts");
        }
        if (n >= array.length || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("offset: " + n + ", length: " + n2 + " (expected: offset <= offset + length <= dsts.length (" + array.length + "))");
        }
        final int n3 = n + n2;
        for (int i = n; i < n3; ++i) {
            final ByteBuffer byteBuffer2 = array[i];
            if (byteBuffer2 == null) {
                throw new IllegalArgumentException();
            }
            if (byteBuffer2.isReadOnly()) {
                throw new ReadOnlyBufferException();
            }
            final int n4 = 0 + byteBuffer2.remaining();
        }
        if (this.accepted == 0) {
            this.beginHandshakeImplicitly();
        }
        final SSLEngineResult.HandshakeStatus handshakeStatus = this.getHandshakeStatus();
        if ((!this.handshakeFinished || this.engineClosed) && handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
            return new SSLEngineResult(this.getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
        }
        if (byteBuffer.remaining() > 18713) {
            this.isInboundDone = true;
            this.isOutboundDone = true;
            this.engineClosed = true;
            this.shutdown();
            throw OpenSslEngine.ENCRYPTED_PACKET_OVERSIZED;
        }
        this.lastPrimingReadResult = 0;
        final int n5 = 0 + this.writeEncryptedData(byteBuffer);
        final String lastError = SSL.getLastError();
        if (lastError != null && !lastError.startsWith("error:00000000:")) {
            if (OpenSslEngine.logger.isInfoEnabled()) {
                OpenSslEngine.logger.info("SSL_read failed: primingReadResult: " + this.lastPrimingReadResult + "; OpenSSL error: '" + lastError + '\'');
            }
            this.shutdown();
            throw new SSLException(lastError);
        }
        int n6 = (SSL.isInInit(this.ssl) == 0) ? SSL.pendingReadableBytesInSSL(this.ssl) : 0;
        if (0 < n6) {
            return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.getHandshakeStatus(), 0, 0);
        }
        int j = n;
        while (j < n3) {
            final ByteBuffer byteBuffer3 = array[j];
            if (!byteBuffer3.hasRemaining()) {
                ++j;
            }
            else {
                if (n6 <= 0) {
                    break;
                }
                final int plaintextData = this.readPlaintextData(byteBuffer3);
                if (plaintextData == 0) {
                    break;
                }
                n6 -= plaintextData;
                if (byteBuffer3.hasRemaining()) {
                    continue;
                }
                ++j;
            }
        }
        if (!this.receivedShutdown && (SSL.getShutdown(this.ssl) & 0x2) == 0x2) {
            this.receivedShutdown = true;
            this.closeOutbound();
            this.closeInbound();
        }
        return new SSLEngineResult(this.getEngineStatus(), this.getHandshakeStatus(), 0, 0);
    }
    
    @Override
    public Runnable getDelegatedTask() {
        return null;
    }
    
    @Override
    public synchronized void closeInbound() throws SSLException {
        if (this.isInboundDone) {
            return;
        }
        this.isInboundDone = true;
        this.engineClosed = true;
        if (this.accepted != 0) {
            if (!this.receivedShutdown) {
                this.shutdown();
                throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
            }
        }
        else {
            this.shutdown();
        }
    }
    
    @Override
    public synchronized boolean isInboundDone() {
        return this.isInboundDone || this.engineClosed;
    }
    
    @Override
    public synchronized void closeOutbound() {
        if (this.isOutboundDone) {
            return;
        }
        this.isOutboundDone = true;
        this.engineClosed = true;
        if (this.accepted != 0 && this.destroyed == 0) {
            if ((SSL.getShutdown(this.ssl) & 0x1) != 0x1) {
                SSL.shutdownSSL(this.ssl);
            }
        }
        else {
            this.shutdown();
        }
    }
    
    @Override
    public synchronized boolean isOutboundDone() {
        return this.isOutboundDone;
    }
    
    @Override
    public String[] getSupportedCipherSuites() {
        return EmptyArrays.EMPTY_STRINGS;
    }
    
    @Override
    public String[] getEnabledCipherSuites() {
        return EmptyArrays.EMPTY_STRINGS;
    }
    
    @Override
    public void setEnabledCipherSuites(final String[] array) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String[] getSupportedProtocols() {
        return EmptyArrays.EMPTY_STRINGS;
    }
    
    @Override
    public String[] getEnabledProtocols() {
        return EmptyArrays.EMPTY_STRINGS;
    }
    
    @Override
    public void setEnabledProtocols(final String[] array) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public SSLSession getSession() {
        SSLSession session = this.session;
        if (session == null) {
            session = (this.session = new SSLSession() {
                final OpenSslEngine this$0;
                
                @Override
                public byte[] getId() {
                    return String.valueOf(OpenSslEngine.access$000(this.this$0)).getBytes();
                }
                
                @Override
                public SSLSessionContext getSessionContext() {
                    return null;
                }
                
                @Override
                public long getCreationTime() {
                    return 0L;
                }
                
                @Override
                public long getLastAccessedTime() {
                    return 0L;
                }
                
                @Override
                public void invalidate() {
                }
                
                @Override
                public boolean isValid() {
                    return false;
                }
                
                @Override
                public void putValue(final String s, final Object o) {
                }
                
                @Override
                public Object getValue(final String s) {
                    return null;
                }
                
                @Override
                public void removeValue(final String s) {
                }
                
                @Override
                public String[] getValueNames() {
                    return EmptyArrays.EMPTY_STRINGS;
                }
                
                @Override
                public Certificate[] getPeerCertificates() {
                    return OpenSslEngine.access$100();
                }
                
                @Override
                public Certificate[] getLocalCertificates() {
                    return OpenSslEngine.access$100();
                }
                
                @Override
                public X509Certificate[] getPeerCertificateChain() {
                    return OpenSslEngine.access$200();
                }
                
                @Override
                public Principal getPeerPrincipal() {
                    return null;
                }
                
                @Override
                public Principal getLocalPrincipal() {
                    return null;
                }
                
                @Override
                public String getCipherSuite() {
                    return OpenSslEngine.access$300(this.this$0);
                }
                
                @Override
                public String getProtocol() {
                    final String access$400 = OpenSslEngine.access$400(this.this$0);
                    if (access$400 == null) {
                        return "unknown";
                    }
                    return "unknown:" + access$400;
                }
                
                @Override
                public String getPeerHost() {
                    return null;
                }
                
                @Override
                public int getPeerPort() {
                    return 0;
                }
                
                @Override
                public int getPacketBufferSize() {
                    return 18713;
                }
                
                @Override
                public int getApplicationBufferSize() {
                    return 16384;
                }
            });
        }
        return session;
    }
    
    @Override
    public synchronized void beginHandshake() throws SSLException {
        if (this.engineClosed) {
            throw OpenSslEngine.ENGINE_CLOSED;
        }
        switch (this.accepted) {
            case 0: {
                SSL.doHandshake(this.ssl);
                this.accepted = 2;
                break;
            }
            case 1: {
                this.accepted = 2;
                break;
            }
            case 2: {
                throw OpenSslEngine.RENEGOTIATION_UNSUPPORTED;
            }
            default: {
                throw new Error();
            }
        }
    }
    
    private synchronized void beginHandshakeImplicitly() throws SSLException {
        if (this.engineClosed) {
            throw OpenSslEngine.ENGINE_CLOSED;
        }
        if (this.accepted == 0) {
            SSL.doHandshake(this.ssl);
            this.accepted = 1;
        }
    }
    
    private SSLEngineResult.Status getEngineStatus() {
        return this.engineClosed ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK;
    }
    
    @Override
    public synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        if (this.accepted == 0 || this.destroyed != 0) {
            return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        }
        if (!this.handshakeFinished) {
            if (SSL.pendingWrittenBytesInBIO(this.networkBIO) != 0) {
                return SSLEngineResult.HandshakeStatus.NEED_WRAP;
            }
            if (SSL.isInInit(this.ssl) == 0) {
                this.handshakeFinished = true;
                this.cipher = SSL.getCipherForSSL(this.ssl);
                String s = SSL.getNextProtoNegotiated(this.ssl);
                if (s == null) {
                    s = this.fallbackApplicationProtocol;
                }
                if (s != null) {
                    this.applicationProtocol = s.replace(':', '_');
                }
                else {
                    this.applicationProtocol = null;
                }
                return SSLEngineResult.HandshakeStatus.FINISHED;
            }
            return SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
        }
        else {
            if (!this.engineClosed) {
                return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            }
            if (SSL.pendingWrittenBytesInBIO(this.networkBIO) != 0) {
                return SSLEngineResult.HandshakeStatus.NEED_WRAP;
            }
            return SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
        }
    }
    
    @Override
    public void setUseClientMode(final boolean b) {
        if (b) {
            throw new UnsupportedOperationException();
        }
    }
    
    @Override
    public boolean getUseClientMode() {
        return false;
    }
    
    @Override
    public void setNeedClientAuth(final boolean b) {
        if (b) {
            throw new UnsupportedOperationException();
        }
    }
    
    @Override
    public boolean getNeedClientAuth() {
        return false;
    }
    
    @Override
    public void setWantClientAuth(final boolean b) {
        if (b) {
            throw new UnsupportedOperationException();
        }
    }
    
    @Override
    public boolean getWantClientAuth() {
        return false;
    }
    
    @Override
    public void setEnableSessionCreation(final boolean b) {
        if (b) {
            throw new UnsupportedOperationException();
        }
    }
    
    @Override
    public boolean getEnableSessionCreation() {
        return false;
    }
    
    static long access$000(final OpenSslEngine openSslEngine) {
        return openSslEngine.ssl;
    }
    
    static Certificate[] access$100() {
        return OpenSslEngine.EMPTY_CERTIFICATES;
    }
    
    static X509Certificate[] access$200() {
        return OpenSslEngine.EMPTY_X509_CERTIFICATES;
    }
    
    static String access$300(final OpenSslEngine openSslEngine) {
        return openSslEngine.cipher;
    }
    
    static String access$400(final OpenSslEngine openSslEngine) {
        return openSslEngine.applicationProtocol;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(OpenSslEngine.class);
        EMPTY_CERTIFICATES = new Certificate[0];
        EMPTY_X509_CERTIFICATES = new X509Certificate[0];
        ENGINE_CLOSED = new SSLException("engine closed");
        RENEGOTIATION_UNSUPPORTED = new SSLException("renegotiation unsupported");
        ENCRYPTED_PACKET_OVERSIZED = new SSLException("encrypted packet oversized");
        OpenSslEngine.ENGINE_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        OpenSslEngine.RENEGOTIATION_UNSUPPORTED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        OpenSslEngine.ENCRYPTED_PACKET_OVERSIZED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(OpenSslEngine.class, "destroyed");
    }
}
