package io.netty.channel.epoll;

import java.io.*;
import java.nio.*;
import io.netty.channel.*;
import java.net.*;
import java.util.*;
import io.netty.util.internal.*;

final class Native
{
    private static final byte[] IPV4_MAPPED_IPV6_PREFIX;
    public static final int EPOLLIN = 1;
    public static final int EPOLLOUT = 2;
    public static final int EPOLLACCEPT = 4;
    public static final int EPOLLRDHUP = 8;
    public static final int IOV_MAX;
    
    public static native int eventFd();
    
    public static native void eventFdWrite(final int p0, final long p1);
    
    public static native void eventFdRead(final int p0);
    
    public static native int epollCreate();
    
    public static native int epollWait(final int p0, final long[] p1, final int p2);
    
    public static native void epollCtlAdd(final int p0, final int p1, final int p2, final int p3);
    
    public static native void epollCtlMod(final int p0, final int p1, final int p2, final int p3);
    
    public static native void epollCtlDel(final int p0, final int p1);
    
    public static native void close(final int p0) throws IOException;
    
    public static native int write(final int p0, final ByteBuffer p1, final int p2, final int p3) throws IOException;
    
    public static native int writeAddress(final int p0, final long p1, final int p2, final int p3) throws IOException;
    
    public static native long writev(final int p0, final ByteBuffer[] p1, final int p2, final int p3) throws IOException;
    
    public static native long writevAddresses(final int p0, final long p1, final int p2) throws IOException;
    
    public static native int read(final int p0, final ByteBuffer p1, final int p2, final int p3) throws IOException;
    
    public static native int readAddress(final int p0, final long p1, final int p2, final int p3) throws IOException;
    
    public static native long sendfile(final int p0, final DefaultFileRegion p1, final long p2, final long p3, final long p4) throws IOException;
    
    public static int sendTo(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final InetAddress inetAddress, final int n4) throws IOException {
        byte[] array;
        if (inetAddress instanceof Inet6Address) {
            array = inetAddress.getAddress();
            ((Inet6Address)inetAddress).getScopeId();
        }
        else {
            array = ipv4MappedIpv6Address(inetAddress.getAddress());
        }
        return sendTo(n, byteBuffer, n2, n3, array, 0, n4);
    }
    
    private static native int sendTo(final int p0, final ByteBuffer p1, final int p2, final int p3, final byte[] p4, final int p5, final int p6) throws IOException;
    
    public static int sendToAddress(final int n, final long n2, final int n3, final int n4, final InetAddress inetAddress, final int n5) throws IOException {
        byte[] array;
        if (inetAddress instanceof Inet6Address) {
            array = inetAddress.getAddress();
            ((Inet6Address)inetAddress).getScopeId();
        }
        else {
            array = ipv4MappedIpv6Address(inetAddress.getAddress());
        }
        return sendToAddress(n, n2, n3, n4, array, 0, n5);
    }
    
    private static native int sendToAddress(final int p0, final long p1, final int p2, final int p3, final byte[] p4, final int p5, final int p6) throws IOException;
    
    public static native EpollDatagramChannel.DatagramSocketAddress recvFrom(final int p0, final ByteBuffer p1, final int p2, final int p3) throws IOException;
    
    public static native EpollDatagramChannel.DatagramSocketAddress recvFromAddress(final int p0, final long p1, final int p2, final int p3) throws IOException;
    
    public static int socketStreamFd() {
        return socketStream();
    }
    
    public static int socketDgramFd() {
        return socketDgram();
    }
    
    private static native int socketStream() throws IOException;
    
    private static native int socketDgram() throws IOException;
    
    public static void bind(final int n, final InetAddress inetAddress, final int n2) throws IOException {
        final NativeInetAddress nativeInetAddress = toNativeInetAddress(inetAddress);
        bind(n, nativeInetAddress.address, nativeInetAddress.scopeId, n2);
    }
    
    private static byte[] ipv4MappedIpv6Address(final byte[] array) {
        final byte[] array2 = new byte[16];
        System.arraycopy(Native.IPV4_MAPPED_IPV6_PREFIX, 0, array2, 0, Native.IPV4_MAPPED_IPV6_PREFIX.length);
        System.arraycopy(array, 0, array2, 12, array.length);
        return array2;
    }
    
    public static native void bind(final int p0, final byte[] p1, final int p2, final int p3) throws IOException;
    
    public static native void listen(final int p0, final int p1) throws IOException;
    
    public static boolean connect(final int n, final InetAddress inetAddress, final int n2) throws IOException {
        final NativeInetAddress nativeInetAddress = toNativeInetAddress(inetAddress);
        return connect(n, nativeInetAddress.address, nativeInetAddress.scopeId, n2);
    }
    
    public static native boolean connect(final int p0, final byte[] p1, final int p2, final int p3) throws IOException;
    
    public static native boolean finishConnect(final int p0) throws IOException;
    
    public static native InetSocketAddress remoteAddress(final int p0);
    
    public static native InetSocketAddress localAddress(final int p0);
    
    public static native int accept(final int p0) throws IOException;
    
    public static native void shutdown(final int p0, final boolean p1, final boolean p2) throws IOException;
    
    public static native int getReceiveBufferSize(final int p0);
    
    public static native int getSendBufferSize(final int p0);
    
    public static native int isKeepAlive(final int p0);
    
    public static native int isReuseAddress(final int p0);
    
    public static native int isReusePort(final int p0);
    
    public static native int isTcpNoDelay(final int p0);
    
    public static native int isTcpCork(final int p0);
    
    public static native int getSoLinger(final int p0);
    
    public static native int getTrafficClass(final int p0);
    
    public static native int isBroadcast(final int p0);
    
    public static native int getTcpKeepIdle(final int p0);
    
    public static native int getTcpKeepIntvl(final int p0);
    
    public static native int getTcpKeepCnt(final int p0);
    
    public static native void setKeepAlive(final int p0, final int p1);
    
    public static native void setReceiveBufferSize(final int p0, final int p1);
    
    public static native void setReuseAddress(final int p0, final int p1);
    
    public static native void setReusePort(final int p0, final int p1);
    
    public static native void setSendBufferSize(final int p0, final int p1);
    
    public static native void setTcpNoDelay(final int p0, final int p1);
    
    public static native void setTcpCork(final int p0, final int p1);
    
    public static native void setSoLinger(final int p0, final int p1);
    
    public static native void setTrafficClass(final int p0, final int p1);
    
    public static native void setBroadcast(final int p0, final int p1);
    
    public static native void setTcpKeepIdle(final int p0, final int p1);
    
    public static native void setTcpKeepIntvl(final int p0, final int p1);
    
    public static native void setTcpKeepCnt(final int p0, final int p1);
    
    private static NativeInetAddress toNativeInetAddress(final InetAddress inetAddress) {
        final byte[] address = inetAddress.getAddress();
        if (inetAddress instanceof Inet6Address) {
            return new NativeInetAddress(address, ((Inet6Address)inetAddress).getScopeId());
        }
        return new NativeInetAddress(ipv4MappedIpv6Address(address));
    }
    
    public static native String kernelVersion();
    
    private static native int iovMax();
    
    private Native() {
    }
    
    static {
        IPV4_MAPPED_IPV6_PREFIX = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1 };
        if (!SystemPropertyUtil.get("os.name").toLowerCase(Locale.UK).trim().startsWith("linux")) {
            throw new IllegalStateException("Only supported on Linux");
        }
        NativeLibraryLoader.load("netty-transport-native-epoll", PlatformDependent.getClassLoader(Native.class));
        IOV_MAX = iovMax();
    }
    
    private static class NativeInetAddress
    {
        final byte[] address;
        final int scopeId;
        
        NativeInetAddress(final byte[] address, final int scopeId) {
            this.address = address;
            this.scopeId = scopeId;
        }
        
        NativeInetAddress(final byte[] array) {
            this(array, 0);
        }
    }
}
