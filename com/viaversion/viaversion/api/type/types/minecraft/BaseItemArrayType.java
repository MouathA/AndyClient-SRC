package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public abstract class BaseItemArrayType extends Type
{
    protected BaseItemArrayType() {
        super(Item[].class);
    }
    
    protected BaseItemArrayType(final String s) {
        super(s, Item[].class);
    }
    
    @Override
    public Class getBaseClass() {
        return BaseItemArrayType.class;
    }
}
