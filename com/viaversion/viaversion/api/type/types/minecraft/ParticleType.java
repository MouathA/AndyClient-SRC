package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.protocol.*;
import io.netty.buffer.*;
import java.util.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.*;

public class ParticleType extends Type
{
    private final Int2ObjectMap readers;
    
    public ParticleType(final Int2ObjectMap readers) {
        super("Particle", Particle.class);
        this.readers = readers;
    }
    
    public ParticleType() {
        this(new Int2ObjectArrayMap());
    }
    
    public ParticleTypeFiller filler(final Protocol protocol) {
        return this.filler(protocol, true);
    }
    
    public ParticleTypeFiller filler(final Protocol protocol, final boolean b) {
        return new ParticleTypeFiller(protocol, b, null);
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
    
    public static ParticleReader itemHandler(final Type type) {
        return ParticleType::lambda$itemHandler$0;
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Particle)o);
    }
    
    private static void lambda$itemHandler$0(final Type type, final ByteBuf byteBuf, final Particle particle) throws Exception {
        particle.add(type, type.read(byteBuf));
    }
    
    static Int2ObjectMap access$100(final ParticleType particleType) {
        return particleType.readers;
    }
    
    @FunctionalInterface
    public interface ParticleReader
    {
        void read(final ByteBuf p0, final Particle p1) throws Exception;
    }
    
    public final class ParticleTypeFiller
    {
        private final ParticleMappings mappings;
        private final boolean useMappedNames;
        final ParticleType this$0;
        
        private ParticleTypeFiller(final ParticleType this$0, final Protocol protocol, final boolean useMappedNames) {
            this.this$0 = this$0;
            this.mappings = protocol.getMappingData().getParticleMappings();
            this.useMappedNames = useMappedNames;
        }
        
        public ParticleTypeFiller reader(final String s, final ParticleReader particleReader) {
            ParticleType.access$100(this.this$0).put(this.useMappedNames ? this.mappings.mappedId(s) : this.mappings.id(s), particleReader);
            return this;
        }
        
        public ParticleTypeFiller reader(final int n, final ParticleReader particleReader) {
            ParticleType.access$100(this.this$0).put(n, particleReader);
            return this;
        }
        
        ParticleTypeFiller(final ParticleType particleType, final Protocol protocol, final boolean b, final ParticleType$1 object) {
            this(particleType, protocol, b);
        }
    }
    
    public static final class Readers
    {
        public static final ParticleReader BLOCK;
        public static final ParticleReader ITEM;
        public static final ParticleReader VAR_INT_ITEM;
        public static final ParticleReader DUST;
        public static final ParticleReader DUST_TRANSITION;
        public static final ParticleReader VIBRATION;
        
        private static void lambda$static$3(final ByteBuf byteBuf, final Particle particle) throws Exception {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(byteBuf));
            String substring = (String)Type.STRING.read(byteBuf);
            particle.add(Type.STRING, substring);
            if (substring.startsWith("minecraft:")) {
                substring = substring.substring(10);
            }
            if (substring.equals("block")) {
                particle.add(Type.POSITION1_14, Type.POSITION1_14.read(byteBuf));
            }
            else if (substring.equals("entity")) {
                particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
            }
            else {
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + substring);
            }
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
        }
        
        private static void lambda$static$2(final ByteBuf byteBuf, final Particle particle) throws Exception {
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
        }
        
        private static void lambda$static$1(final ByteBuf byteBuf, final Particle particle) throws Exception {
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
            particle.add(Type.FLOAT, Type.FLOAT.readPrimitive(byteBuf));
        }
        
        private static void lambda$static$0(final ByteBuf byteBuf, final Particle particle) throws Exception {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
        }
        
        static {
            BLOCK = Readers::lambda$static$0;
            ITEM = ParticleType.itemHandler(Type.FLAT_ITEM);
            VAR_INT_ITEM = ParticleType.itemHandler(Type.FLAT_VAR_INT_ITEM);
            DUST = Readers::lambda$static$1;
            DUST_TRANSITION = Readers::lambda$static$2;
            VIBRATION = Readers::lambda$static$3;
        }
    }
}
