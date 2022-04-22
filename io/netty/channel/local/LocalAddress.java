package io.netty.channel.local;

import java.net.*;
import io.netty.channel.*;

public final class LocalAddress extends SocketAddress implements Comparable
{
    private static final long serialVersionUID = 4644331421130916435L;
    public static final LocalAddress ANY;
    private final String id;
    private final String strVal;
    
    LocalAddress(final Channel channel) {
        final StringBuilder sb = new StringBuilder(16);
        sb.append("local:E");
        sb.append(Long.toHexString(((long)channel.hashCode() & 0xFFFFFFFFL) | 0x100000000L));
        sb.setCharAt(7, ':');
        this.id = sb.substring(6);
        this.strVal = sb.toString();
    }
    
    public LocalAddress(String lowerCase) {
        if (lowerCase == null) {
            throw new NullPointerException("id");
        }
        lowerCase = lowerCase.trim().toLowerCase();
        if (lowerCase.isEmpty()) {
            throw new IllegalArgumentException("empty id");
        }
        this.id = lowerCase;
        this.strVal = "local:" + lowerCase;
    }
    
    public String id() {
        return this.id;
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof LocalAddress && this.id.equals(((LocalAddress)o).id);
    }
    
    public int compareTo(final LocalAddress localAddress) {
        return this.id.compareTo(localAddress.id);
    }
    
    @Override
    public String toString() {
        return this.strVal;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((LocalAddress)o);
    }
    
    static {
        ANY = new LocalAddress("ANY");
    }
}
