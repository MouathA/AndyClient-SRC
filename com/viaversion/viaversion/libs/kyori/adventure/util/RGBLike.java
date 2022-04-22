package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;

public interface RGBLike
{
    int red();
    
    int green();
    
    int blue();
    
    @NotNull
    default HSVLike asHSV() {
        return HSVLike.fromRGB(this.red(), this.green(), this.blue());
    }
}
