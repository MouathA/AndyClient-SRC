package io.netty.util.internal;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.security.*;
import java.util.concurrent.*;
import io.netty.util.internal.logging.*;

public final class ThreadLocalRandom extends Random
{
    private static final InternalLogger logger;
    private static final AtomicLong seedUniquifier;
    private static long initialSeedUniquifier;
    private static final long multiplier = 25214903917L;
    private static final long addend = 11L;
    private static final long mask = 281474976710655L;
    private long rnd;
    boolean initialized;
    private long pad0;
    private long pad1;
    private long pad2;
    private long pad3;
    private long pad4;
    private long pad5;
    private long pad6;
    private long pad7;
    private static final long serialVersionUID = -5851777807851030925L;
    
    public static void setInitialSeedUniquifier(final long initialSeedUniquifier) {
        ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
    }
    
    public static synchronized long getInitialSeedUniquifier() {
        long initialSeedUniquifier = ThreadLocalRandom.initialSeedUniquifier;
        if (initialSeedUniquifier == 0L) {
            initialSeedUniquifier = (ThreadLocalRandom.initialSeedUniquifier = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L));
        }
        if (initialSeedUniquifier == 0L) {
            final LinkedBlockingQueue<byte[]> linkedBlockingQueue = new LinkedBlockingQueue<byte[]>();
            final Thread thread = new Thread("initialSeedUniquifierGenerator", linkedBlockingQueue) {
                final BlockingQueue val$queue;
                
                @Override
                public void run() {
                    this.val$queue.add(new SecureRandom().generateSeed(8));
                }
            };
            thread.setDaemon(true);
            thread.start();
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(final Thread thread, final Throwable t) {
                    ThreadLocalRandom.access$000().debug("An exception has been raised by {}", thread.getName(), t);
                }
            });
            final long n = System.nanoTime() + TimeUnit.SECONDS.toNanos(3L);
            while (true) {
                final long n2 = n - System.nanoTime();
                if (n2 <= 0L) {
                    thread.interrupt();
                    ThreadLocalRandom.logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entrophy?", (Object)3L);
                    break;
                }
                final byte[] array = linkedBlockingQueue.poll(n2, TimeUnit.NANOSECONDS);
                if (array != null) {
                    initialSeedUniquifier = (((long)array[0] & 0xFFL) << 56 | ((long)array[1] & 0xFFL) << 48 | ((long)array[2] & 0xFFL) << 40 | ((long)array[3] & 0xFFL) << 32 | ((long)array[4] & 0xFFL) << 24 | ((long)array[5] & 0xFFL) << 16 | ((long)array[6] & 0xFFL) << 8 | ((long)array[7] & 0xFFL));
                    break;
                }
            }
            initialSeedUniquifier = (ThreadLocalRandom.initialSeedUniquifier = (initialSeedUniquifier ^ 0x3255ECDC33BAE119L ^ Long.reverse(System.nanoTime())));
            if (true) {
                Thread.currentThread().interrupt();
                thread.interrupt();
            }
        }
        return initialSeedUniquifier;
    }
    
    private static long newSeed() {
        final long nanoTime = System.nanoTime();
        long value;
        long n;
        long n2;
        do {
            value = ThreadLocalRandom.seedUniquifier.get();
            n = ((value != 0L) ? value : getInitialSeedUniquifier());
            n2 = n * 181783497276652981L;
        } while (!ThreadLocalRandom.seedUniquifier.compareAndSet(value, n2));
        if (value == 0L && ThreadLocalRandom.logger.isDebugEnabled()) {
            ThreadLocalRandom.logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", n, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime)));
        }
        return n2 ^ System.nanoTime();
    }
    
    ThreadLocalRandom() {
        super(newSeed());
        this.initialized = true;
    }
    
    public static ThreadLocalRandom current() {
        return InternalThreadLocalMap.get().random();
    }
    
    @Override
    public void setSeed(final long n) {
        if (this.initialized) {
            throw new UnsupportedOperationException();
        }
        this.rnd = ((n ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL);
    }
    
    @Override
    protected int next(final int n) {
        this.rnd = (this.rnd * 25214903917L + 11L & 0xFFFFFFFFFFFFL);
        return (int)(this.rnd >>> 48 - n);
    }
    
    public int nextInt(final int n, final int n2) {
        if (n >= n2) {
            throw new IllegalArgumentException();
        }
        return this.nextInt(n2 - n) + n;
    }
    
    public long nextLong(long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("n must be positive");
        }
        long n2 = 0L;
        while (n >= 2147483647L) {
            final int next = this.next(2);
            final long n3 = n >>> 1;
            final long n4 = ((next & 0x2) == 0x0) ? n3 : (n - n3);
            if ((next & 0x1) == 0x0) {
                n2 += n - n4;
            }
            n = n4;
        }
        return n2 + this.nextInt((int)n);
    }
    
    public long nextLong(final long n, final long n2) {
        if (n >= n2) {
            throw new IllegalArgumentException();
        }
        return this.nextLong(n2 - n) + n;
    }
    
    public double nextDouble(final double n) {
        if (n <= 0.0) {
            throw new IllegalArgumentException("n must be positive");
        }
        return this.nextDouble() * n;
    }
    
    public double nextDouble(final double n, final double n2) {
        if (n >= n2) {
            throw new IllegalArgumentException();
        }
        return this.nextDouble() * (n2 - n) + n;
    }
    
    static InternalLogger access$000() {
        return ThreadLocalRandom.logger;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
        seedUniquifier = new AtomicLong();
    }
}
