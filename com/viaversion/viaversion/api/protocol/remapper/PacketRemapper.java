package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.type.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;

public abstract class PacketRemapper
{
    private final List valueRemappers;
    
    protected PacketRemapper() {
        this.valueRemappers = new ArrayList();
        this.registerMap();
    }
    
    public void map(final Type type) {
        this.handler(PacketRemapper::lambda$map$0);
    }
    
    public void map(final Type type, final Type type2) {
        this.handler(PacketRemapper::lambda$map$1);
    }
    
    public void map(final Type type, final Type type2, final Function function) {
        this.map(type, new ValueTransformer(type2, function) {
            final Function val$transformer;
            final PacketRemapper this$0;
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.val$transformer.apply(o);
            }
        });
    }
    
    public void map(final ValueTransformer valueTransformer) {
        if (valueTransformer.getInputType() == null) {
            throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
        }
        this.map(valueTransformer.getInputType(), valueTransformer);
    }
    
    public void map(final Type type, final ValueTransformer valueTransformer) {
        this.map(new TypeRemapper(type), valueTransformer);
    }
    
    public void map(final ValueReader valueReader, final ValueWriter valueWriter) {
        this.handler(PacketRemapper::lambda$map$2);
    }
    
    public void handler(final PacketHandler packetHandler) {
        this.valueRemappers.add(packetHandler);
    }
    
    public void create(final Type type, final Object o) {
        this.handler(PacketRemapper::lambda$create$3);
    }
    
    public void read(final Type type) {
        this.handler(PacketRemapper::lambda$read$4);
    }
    
    public abstract void registerMap();
    
    public void remap(final PacketWrapper packetWrapper) throws Exception {
        final Iterator<PacketHandler> iterator = this.valueRemappers.iterator();
        while (iterator.hasNext()) {
            iterator.next().handle(packetWrapper);
        }
    }
    
    private static void lambda$read$4(final Type type, final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.read(type);
    }
    
    private static void lambda$create$3(final Type type, final Object o, final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(type, o);
    }
    
    private static void lambda$map$2(final ValueWriter valueWriter, final ValueReader valueReader, final PacketWrapper packetWrapper) throws Exception {
        valueWriter.write(packetWrapper, valueReader.read(packetWrapper));
    }
    
    private static void lambda$map$1(final Type type, final Type type2, final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(type, packetWrapper.read(type2));
    }
    
    private static void lambda$map$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(type, packetWrapper.read(type));
    }
}
