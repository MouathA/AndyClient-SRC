package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

public interface HSVLike extends Examinable
{
    @NotNull
    default HSVLike of(final float h, final float s, final float v) {
        return new HSVLikeImpl(h, s, v);
    }
    
    @NotNull
    default HSVLike fromRGB(final int red, final int green, final int blue) {
        final float n = red / 255.0f;
        final float n2 = green / 255.0f;
        final float n3 = blue / 255.0f;
        final float min = Math.min(n, Math.min(n2, n3));
        final float max = Math.max(n, Math.max(n2, n3));
        final float n4 = max - min;
        float n5;
        if (max != 0.0f) {
            n5 = n4 / max;
        }
        else {
            n5 = 0.0f;
        }
        if (n5 == 0.0f) {
            return new HSVLikeImpl(0.0f, n5, max);
        }
        float n6;
        if (n == max) {
            n6 = (n2 - n3) / n4;
        }
        else if (n2 == max) {
            n6 = 2.0f + (n3 - n) / n4;
        }
        else {
            n6 = 4.0f + (n - n2) / n4;
        }
        float n7 = n6 * 60.0f;
        if (n7 < 0.0f) {
            n7 += 360.0f;
        }
        return new HSVLikeImpl(n7 / 360.0f, n5, max);
    }
    
    float h();
    
    float s();
    
    float v();
    
    @NotNull
    default Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("h", this.h()), ExaminableProperty.of("s", this.s()), ExaminableProperty.of("v", this.v()) });
    }
}
