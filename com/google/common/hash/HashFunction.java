package com.google.common.hash;

import com.google.common.annotations.*;
import java.nio.charset.*;

@Beta
public interface HashFunction
{
    Hasher newHasher();
    
    Hasher newHasher(final int p0);
    
    HashCode hashInt(final int p0);
    
    HashCode hashLong(final long p0);
    
    HashCode hashBytes(final byte[] p0);
    
    HashCode hashBytes(final byte[] p0, final int p1, final int p2);
    
    HashCode hashUnencodedChars(final CharSequence p0);
    
    HashCode hashString(final CharSequence p0, final Charset p1);
    
    HashCode hashObject(final Object p0, final Funnel p1);
    
    int bits();
}
