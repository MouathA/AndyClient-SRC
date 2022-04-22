package io.netty.channel;

import io.netty.util.*;
import java.nio.channels.*;
import java.io.*;
import io.netty.util.internal.logging.*;

public class DefaultFileRegion extends AbstractReferenceCounted implements FileRegion
{
    private static final InternalLogger logger;
    private final FileChannel file;
    private final long position;
    private final long count;
    private long transfered;
    
    public DefaultFileRegion(final FileChannel file, final long position, final long count) {
        if (file == null) {
            throw new NullPointerException("file");
        }
        if (position < 0L) {
            throw new IllegalArgumentException("position must be >= 0 but was " + position);
        }
        if (count < 0L) {
            throw new IllegalArgumentException("count must be >= 0 but was " + count);
        }
        this.file = file;
        this.position = position;
        this.count = count;
    }
    
    @Override
    public long position() {
        return this.position;
    }
    
    @Override
    public long count() {
        return this.count;
    }
    
    @Override
    public long transfered() {
        return this.transfered;
    }
    
    @Override
    public long transferTo(final WritableByteChannel writableByteChannel, final long n) throws IOException {
        final long n2 = this.count - n;
        if (n2 < 0L || n < 0L) {
            throw new IllegalArgumentException("position out of range: " + n + " (expected: 0 - " + (this.count - 1L) + ')');
        }
        if (n2 == 0L) {
            return 0L;
        }
        final long transferTo = this.file.transferTo(this.position + n, n2, writableByteChannel);
        if (transferTo > 0L) {
            this.transfered += transferTo;
        }
        return transferTo;
    }
    
    @Override
    protected void deallocate() {
        this.file.close();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
    }
}
