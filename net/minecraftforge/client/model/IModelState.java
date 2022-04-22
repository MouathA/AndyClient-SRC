package net.minecraftforge.client.model;

import com.google.common.base.*;

public interface IModelState extends Function
{
    TRSRTransformation apply(final IModelPart p0);
    
    default Object apply(final Object o) {
        return this.apply((IModelPart)o);
    }
}
