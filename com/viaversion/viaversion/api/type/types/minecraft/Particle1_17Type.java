package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;

@Deprecated
public class Particle1_17Type extends AbstractParticleType
{
    public Particle1_17Type() {
        this.readers.put(4, this.blockHandler());
        this.readers.put(25, this.blockHandler());
        this.readers.put(15, this.dustHandler());
        this.readers.put(16, this.dustTransitionHandler());
        this.readers.put(36, this.itemHandler(Type.FLAT_VAR_INT_ITEM));
        this.readers.put(37, this.vibrationHandler(Type.POSITION1_14));
    }
}
