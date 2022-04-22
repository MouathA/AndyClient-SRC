package com.viaversion.viaversion.libs.kyori.examination;

import java.util.stream.*;
import org.jetbrains.annotations.*;

public interface Examiner
{
    @NotNull
    default Object examine(@NotNull final Examinable examinable) {
        return this.examine(examinable.examinableName(), examinable.examinableProperties());
    }
    
    @NotNull
    Object examine(@NotNull final String name, @NotNull final Stream properties);
    
    @NotNull
    Object examine(@Nullable final Object value);
    
    @NotNull
    Object examine(final boolean value);
    
    @NotNull
    Object examine(final boolean[] values);
    
    @NotNull
    Object examine(final byte value);
    
    @NotNull
    Object examine(final byte[] values);
    
    @NotNull
    Object examine(final char value);
    
    @NotNull
    Object examine(final char[] values);
    
    @NotNull
    Object examine(final double value);
    
    @NotNull
    Object examine(final double[] values);
    
    @NotNull
    Object examine(final float value);
    
    @NotNull
    Object examine(final float[] values);
    
    @NotNull
    Object examine(final int value);
    
    @NotNull
    Object examine(final int[] values);
    
    @NotNull
    Object examine(final long value);
    
    @NotNull
    Object examine(final long[] values);
    
    @NotNull
    Object examine(final short value);
    
    @NotNull
    Object examine(final short[] values);
    
    @NotNull
    Object examine(@Nullable final String value);
}
