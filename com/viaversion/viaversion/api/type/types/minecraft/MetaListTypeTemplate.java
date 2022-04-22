package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import java.util.*;

public abstract class MetaListTypeTemplate extends Type
{
    protected MetaListTypeTemplate() {
        super("MetaData List", List.class);
    }
    
    @Override
    public Class getBaseClass() {
        return MetaListTypeTemplate.class;
    }
}
