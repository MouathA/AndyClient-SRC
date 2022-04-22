package io.netty.channel.socket.nio;

import java.lang.reflect.*;
import io.netty.channel.socket.*;
import java.net.*;
import java.util.*;
import io.netty.util.internal.*;
import io.netty.channel.*;
import java.nio.channels.*;

class NioDatagramChannelConfig extends DefaultDatagramChannelConfig
{
    private static final Object IP_MULTICAST_TTL;
    private static final Object IP_MULTICAST_IF;
    private static final Object IP_MULTICAST_LOOP;
    private static final Method GET_OPTION;
    private static final Method SET_OPTION;
    private final DatagramChannel javaChannel;
    
    NioDatagramChannelConfig(final NioDatagramChannel nioDatagramChannel, final DatagramChannel javaChannel) {
        super(nioDatagramChannel, javaChannel.socket());
        this.javaChannel = javaChannel;
    }
    
    @Override
    public int getTimeToLive() {
        return (int)this.getOption0(NioDatagramChannelConfig.IP_MULTICAST_TTL);
    }
    
    @Override
    public DatagramChannelConfig setTimeToLive(final int n) {
        this.setOption0(NioDatagramChannelConfig.IP_MULTICAST_TTL, n);
        return this;
    }
    
    @Override
    public InetAddress getInterface() {
        final NetworkInterface networkInterface = this.getNetworkInterface();
        if (networkInterface == null) {
            return null;
        }
        final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        if (inetAddresses.hasMoreElements()) {
            return inetAddresses.nextElement();
        }
        return null;
    }
    
    @Override
    public DatagramChannelConfig setInterface(final InetAddress inetAddress) {
        this.setNetworkInterface(NetworkInterface.getByInetAddress(inetAddress));
        return this;
    }
    
    @Override
    public NetworkInterface getNetworkInterface() {
        return (NetworkInterface)this.getOption0(NioDatagramChannelConfig.IP_MULTICAST_IF);
    }
    
    @Override
    public DatagramChannelConfig setNetworkInterface(final NetworkInterface networkInterface) {
        this.setOption0(NioDatagramChannelConfig.IP_MULTICAST_IF, networkInterface);
        return this;
    }
    
    @Override
    public boolean isLoopbackModeDisabled() {
        return (boolean)this.getOption0(NioDatagramChannelConfig.IP_MULTICAST_LOOP);
    }
    
    @Override
    public DatagramChannelConfig setLoopbackModeDisabled(final boolean b) {
        this.setOption0(NioDatagramChannelConfig.IP_MULTICAST_LOOP, b);
        return this;
    }
    
    @Override
    public DatagramChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    protected void autoReadCleared() {
        ((NioDatagramChannel)this.channel).setReadPending(false);
    }
    
    private Object getOption0(final Object o) {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException();
        }
        return NioDatagramChannelConfig.GET_OPTION.invoke(this.javaChannel, o);
    }
    
    private void setOption0(final Object o, final Object o2) {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException();
        }
        NioDatagramChannelConfig.SET_OPTION.invoke(this.javaChannel, o, o2);
    }
    
    @Override
    public ChannelConfig setAutoRead(final boolean autoRead) {
        return this.setAutoRead(autoRead);
    }
    
    static {
        final ClassLoader classLoader = PlatformDependent.getClassLoader(DatagramChannel.class);
        final Class<?> forName = Class.forName("java.net.SocketOption", true, classLoader);
        final Class<?> forName2 = Class.forName("java.net.StandardSocketOptions", true, classLoader);
        Object value = null;
        Object value2 = null;
        Object value3 = null;
        Method declaredMethod = null;
        Method declaredMethod2 = null;
        if (forName != null) {
            value = forName2.getDeclaredField("IP_MULTICAST_TTL").get(null);
            value2 = forName2.getDeclaredField("IP_MULTICAST_IF").get(null);
            value3 = forName2.getDeclaredField("IP_MULTICAST_LOOP").get(null);
            declaredMethod = NetworkChannel.class.getDeclaredMethod("getOption", forName);
            declaredMethod2 = NetworkChannel.class.getDeclaredMethod("setOption", forName, Object.class);
        }
        IP_MULTICAST_TTL = value;
        IP_MULTICAST_IF = value2;
        IP_MULTICAST_LOOP = value3;
        GET_OPTION = declaredMethod;
        SET_OPTION = declaredMethod2;
    }
}
