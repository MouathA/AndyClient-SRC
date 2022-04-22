package wdl.api;

import java.util.*;

public interface IEntityAdder extends IWDLMod
{
    List getModEntities();
    
    int getDefaultEntityTrackDistance(final String p0);
    
    String getEntityCategory(final String p0);
}
