package wdl.api;

import net.minecraft.entity.*;

public interface IEntityEditor extends IWDLMod
{
    boolean shouldEdit(final Entity p0);
    
    void editEntity(final Entity p0);
}
