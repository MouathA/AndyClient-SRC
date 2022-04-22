package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class VillagerDataType extends Type
{
    public VillagerDataType() {
        super(VillagerData.class);
    }
    
    @Override
    public VillagerData read(final ByteBuf byteBuf) throws Exception {
        return new VillagerData(Type.VAR_INT.readPrimitive(byteBuf), Type.VAR_INT.readPrimitive(byteBuf), Type.VAR_INT.readPrimitive(byteBuf));
    }
    
    public void write(final ByteBuf byteBuf, final VillagerData villagerData) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, villagerData.type());
        Type.VAR_INT.writePrimitive(byteBuf, villagerData.profession());
        Type.VAR_INT.writePrimitive(byteBuf, villagerData.level());
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (VillagerData)o);
    }
}
