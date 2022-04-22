package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import io.netty.buffer.*;
import java.util.*;
import com.viaversion.viaversion.api.*;

@Deprecated
public abstract class AbstractParticleType extends Type
{
    protected final Int2ObjectMap readers;
    
    protected AbstractParticleType() {
        super("Particle", Particle.class);
        this.readers = new Int2ObjectOpenHashMap();
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
        final ParticleReader particleReader = (ParticleReader)this.readers.get(primitive);
        if (particleReader != null) {
            particleReader.read(byteBuf, particle);
        }
        return particle;
    }
    
    protected ParticleReader blockHandler() {
        return AbstractParticleType::lambda$blockHandler$0;
    }
    
    protected ParticleReader itemHandler(final Type type) {
        return AbstractParticleType::lambda$itemHandler$1;
    }
    
    protected ParticleReader dustHandler() {
        return AbstractParticleType::lambda$dustHandler$2;
    }
    
    protected ParticleReader dustTransitionHandler() {
        return AbstractParticleType::lambda$dustTransitionHandler$3;
    }
    
    protected ParticleReader vibrationHandler(final Type type) {
        return AbstractParticleType::lambda$vibrationHandler$4;
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Particle)o);
    }
    
    private static void lambda$vibrationHandler$4(final Type type, final ByteBuf byteBuf, final Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(type, type.read(byteBuf)));
        final String s = (String)Type.STRING.read(byteBuf);
        if (s.equals("block")) {
            particle.getArguments().add(new Particle.ParticleData(type, type.read(byteBuf)));
        }
        else if (s.equals("entity")) {
            particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf)));
        }
        else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + s);
        }
        particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf)));
    }
    
    private static void lambda$dustTransitionHandler$3(final ByteBuf byteBuf, final Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
    }
    
    private static void lambda$dustHandler$2(final ByteBuf byteBuf, final Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf)));
    }
    
    private static void lambda$itemHandler$1(final Type type, final ByteBuf byteBuf, final Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(type, type.read(byteBuf)));
    }
    
    private static void lambda$blockHandler$0(final ByteBuf byteBuf, final Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf)));
    }
    
    @FunctionalInterface
    public interface ParticleReader
    {
        void read(final ByteBuf p0, final Particle p1) throws Exception;
    }
}
