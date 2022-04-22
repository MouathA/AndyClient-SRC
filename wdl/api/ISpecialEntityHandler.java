package wdl.api;

import com.google.common.collect.*;
import net.minecraft.entity.*;

public interface ISpecialEntityHandler extends IWDLMod
{
    Multimap getSpecialEntities();
    
    String getSpecialEntityName(final Entity p0);
    
    String getSpecialEntityCategory(final String p0);
    
    int getSpecialEntityTrackDistance(final String p0);
}
