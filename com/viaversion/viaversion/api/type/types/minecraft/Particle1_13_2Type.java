package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.*;
import io.netty.buffer.*;
import java.util.*;

@Deprecated
public class Particle1_13_2Type extends Type
{
    public Particle1_13_2Type() {
        super("Particle", Particle.class);
    }
    
    public void write(final ByteBuf byteBuf, final Particle particle) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, particle.getId());
        for (final Particle.ParticleData particleData : particle.getArguments()) {
            particleData.getType().write(byteBuf, particleData.getValue());
        }
    }
    
    @Override
    public Particle read(final ByteBuf byteBuf) throws Exception {
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        final Particle particle = new Particle(primitive);
        switch (primitive) {
            case 3:
            case 20: {
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf)));
                break;
            }
            case 11: {
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
                break;
            }
            case 27: {
                particle.getArguments().add(new Particle.ParticleData(Type.FLAT_VAR_INT_ITEM, Type.FLAT_VAR_INT_ITEM.read(byteBuf)));
                break;
            }
        }
        return particle;
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Particle)o);
    }
}
