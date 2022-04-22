package net.minecraft.entity.ai;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;

public class EntitySenses
{
    EntityLiving entityObj;
    List seenEntities;
    List unseenEntities;
    private static final String __OBFID;
    
    public EntitySenses(final EntityLiving entityObj) {
        this.seenEntities = Lists.newArrayList();
        this.unseenEntities = Lists.newArrayList();
        this.entityObj = entityObj;
    }
    
    public void clearSensingCache() {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }
    
    public boolean canSee(final Entity entity) {
        if (this.seenEntities.contains(entity)) {
            return true;
        }
        if (this.unseenEntities.contains(entity)) {
            return false;
        }
        this.entityObj.worldObj.theProfiler.startSection("canSee");
        final boolean canEntityBeSeen = this.entityObj.canEntityBeSeen(entity);
        this.entityObj.worldObj.theProfiler.endSection();
        if (canEntityBeSeen) {
            this.seenEntities.add(entity);
        }
        else {
            this.unseenEntities.add(entity);
        }
        return canEntityBeSeen;
    }
    
    static {
        __OBFID = "CL_00001628";
    }
}
