package io.netty.channel;

import io.netty.util.*;
import java.util.concurrent.*;
import io.netty.util.internal.*;

public class ChannelOption extends UniqueName
{
    private static final ConcurrentMap names;
    public static final ChannelOption ALLOCATOR;
    public static final ChannelOption RCVBUF_ALLOCATOR;
    public static final ChannelOption MESSAGE_SIZE_ESTIMATOR;
    public static final ChannelOption CONNECT_TIMEOUT_MILLIS;
    public static final ChannelOption MAX_MESSAGES_PER_READ;
    public static final ChannelOption WRITE_SPIN_COUNT;
    public static final ChannelOption WRITE_BUFFER_HIGH_WATER_MARK;
    public static final ChannelOption WRITE_BUFFER_LOW_WATER_MARK;
    public static final ChannelOption ALLOW_HALF_CLOSURE;
    public static final ChannelOption AUTO_READ;
    @Deprecated
    public static final ChannelOption AUTO_CLOSE;
    public static final ChannelOption SO_BROADCAST;
    public static final ChannelOption SO_KEEPALIVE;
    public static final ChannelOption SO_SNDBUF;
    public static final ChannelOption SO_RCVBUF;
    public static final ChannelOption SO_REUSEADDR;
    public static final ChannelOption SO_LINGER;
    public static final ChannelOption SO_BACKLOG;
    public static final ChannelOption SO_TIMEOUT;
    public static final ChannelOption IP_TOS;
    public static final ChannelOption IP_MULTICAST_ADDR;
    public static final ChannelOption IP_MULTICAST_IF;
    public static final ChannelOption IP_MULTICAST_TTL;
    public static final ChannelOption IP_MULTICAST_LOOP_DISABLED;
    public static final ChannelOption TCP_NODELAY;
    @Deprecated
    public static final ChannelOption AIO_READ_TIMEOUT;
    @Deprecated
    public static final ChannelOption AIO_WRITE_TIMEOUT;
    @Deprecated
    public static final ChannelOption DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION;
    
    public static ChannelOption valueOf(final String s) {
        return new ChannelOption(s);
    }
    
    @Deprecated
    protected ChannelOption(final String s) {
        super(ChannelOption.names, s, new Object[0]);
    }
    
    public void validate(final Object o) {
        if (o == null) {
            throw new NullPointerException("value");
        }
    }
    
    static {
        names = PlatformDependent.newConcurrentHashMap();
        ALLOCATOR = valueOf("ALLOCATOR");
        RCVBUF_ALLOCATOR = valueOf("RCVBUF_ALLOCATOR");
        MESSAGE_SIZE_ESTIMATOR = valueOf("MESSAGE_SIZE_ESTIMATOR");
        CONNECT_TIMEOUT_MILLIS = valueOf("CONNECT_TIMEOUT_MILLIS");
        MAX_MESSAGES_PER_READ = valueOf("MAX_MESSAGES_PER_READ");
        WRITE_SPIN_COUNT = valueOf("WRITE_SPIN_COUNT");
        WRITE_BUFFER_HIGH_WATER_MARK = valueOf("WRITE_BUFFER_HIGH_WATER_MARK");
        WRITE_BUFFER_LOW_WATER_MARK = valueOf("WRITE_BUFFER_LOW_WATER_MARK");
        ALLOW_HALF_CLOSURE = valueOf("ALLOW_HALF_CLOSURE");
        AUTO_READ = valueOf("AUTO_READ");
        AUTO_CLOSE = valueOf("AUTO_CLOSE");
        SO_BROADCAST = valueOf("SO_BROADCAST");
        SO_KEEPALIVE = valueOf("SO_KEEPALIVE");
        SO_SNDBUF = valueOf("SO_SNDBUF");
        SO_RCVBUF = valueOf("SO_RCVBUF");
        SO_REUSEADDR = valueOf("SO_REUSEADDR");
        SO_LINGER = valueOf("SO_LINGER");
        SO_BACKLOG = valueOf("SO_BACKLOG");
        SO_TIMEOUT = valueOf("SO_TIMEOUT");
        IP_TOS = valueOf("IP_TOS");
        IP_MULTICAST_ADDR = valueOf("IP_MULTICAST_ADDR");
        IP_MULTICAST_IF = valueOf("IP_MULTICAST_IF");
        IP_MULTICAST_TTL = valueOf("IP_MULTICAST_TTL");
        IP_MULTICAST_LOOP_DISABLED = valueOf("IP_MULTICAST_LOOP_DISABLED");
        TCP_NODELAY = valueOf("TCP_NODELAY");
        AIO_READ_TIMEOUT = valueOf("AIO_READ_TIMEOUT");
        AIO_WRITE_TIMEOUT = valueOf("AIO_WRITE_TIMEOUT");
        DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION = valueOf("DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION");
    }
}
