package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public abstract class BaseItemType extends Type
{
    protected BaseItemType() {
        super(Item.class);
    }
    
    protected BaseItemType(final String s) {
        super(s, Item.class);
    }
    
    @Override
    public Class getBaseClass() {
        return BaseItemType.class;
    }
}
