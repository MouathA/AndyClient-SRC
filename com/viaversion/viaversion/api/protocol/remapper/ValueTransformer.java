package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public abstract class ValueTransformer implements ValueWriter
{
    private final Type inputType;
    private final Type outputType;
    
    protected ValueTransformer(final Type inputType, final Type outputType) {
        this.inputType = inputType;
        this.outputType = outputType;
    }
    
    protected ValueTransformer(final Type type) {
        this(null, type);
    }
    
    public abstract Object transform(final PacketWrapper p0, final Object p1) throws Exception;
    
    @Override
    public void write(final PacketWrapper packetWrapper, final Object o) throws Exception {
        packetWrapper.write(this.outputType, this.transform(packetWrapper, o));
    }
    
    public Type getInputType() {
        return this.inputType;
    }
    
    public Type getOutputType() {
        return this.outputType;
    }
}
