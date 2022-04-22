package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

final class HSVLikeImpl implements HSVLike
{
    private final float h;
    private final float s;
    private final float v;
    
    HSVLikeImpl(final float h, final float s, final float v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }
    
    @Override
    public float h() {
        return this.h;
    }
    
    @Override
    public float s() {
        return this.s;
    }
    
    @Override
    public float v() {
        return this.v;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HSVLikeImpl)) {
            return false;
        }
        final HSVLikeImpl hsvLikeImpl = (HSVLikeImpl)other;
        return ShadyPines.equals(hsvLikeImpl.h, this.h) && ShadyPines.equals(hsvLikeImpl.s, this.s) && ShadyPines.equals(hsvLikeImpl.v, this.v);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.h, this.s, this.v);
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
}
