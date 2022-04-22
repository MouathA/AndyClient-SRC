package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class TypeRemapper implements ValueReader, ValueWriter
{
    private final Type type;
    
    public TypeRemapper(final Type type) {
        this.type = type;
    }
    
    @Override
    public Object read(final PacketWrapper packetWrapper) throws Exception {
        return packetWrapper.read(this.type);
    }
    
    @Override
    public void write(final PacketWrapper packetWrapper, final Object o) {
        packetWrapper.write(this.type, o);
    }
}
