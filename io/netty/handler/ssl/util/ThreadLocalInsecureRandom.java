package io.netty.handler.ssl.util;

import java.security.*;
import java.util.*;
import io.netty.util.internal.*;

final class ThreadLocalInsecureRandom extends SecureRandom
{
    private static final long serialVersionUID = -8209473337192526191L;
    private static final SecureRandom INSTANCE;
    
    static SecureRandom current() {
        return ThreadLocalInsecureRandom.INSTANCE;
    }
    
    private ThreadLocalInsecureRandom() {
    }
    
    @Override
    public String getAlgorithm() {
        return "insecure";
    }
    
    @Override
    public void setSeed(final byte[] array) {
    }
    
    @Override
    public void setSeed(final long n) {
    }
    
    @Override
    public void nextBytes(final byte[] array) {
        random().nextBytes(array);
    }
    
    @Override
    public byte[] generateSeed(final int n) {
        final byte[] array = new byte[n];
        random().nextBytes(array);
        return array;
    }
    
    @Override
    public int nextInt() {
        return random().nextInt();
    }
    
    @Override
    public int nextInt(final int n) {
        return random().nextInt(n);
    }
    
    @Override
    public boolean nextBoolean() {
        return random().nextBoolean();
    }
    
    @Override
    public long nextLong() {
        return random().nextLong();
    }
    
    @Override
    public float nextFloat() {
        return random().nextFloat();
    }
    
    @Override
    public double nextDouble() {
        return random().nextDouble();
    }
    
    @Override
    public double nextGaussian() {
        return random().nextGaussian();
    }
    
    private static Random random() {
        return ThreadLocalRandom.current();
    }
    
    static {
        INSTANCE = new ThreadLocalInsecureRandom();
    }
}
