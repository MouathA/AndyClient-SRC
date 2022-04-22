package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityVillager theMerchant;
    private static final String __OBFID;
    
    public EntityAILookAtTradePlayer(final EntityVillager theMerchant) {
        super(theMerchant, EntityPlayer.class, 8.0f);
        this.theMerchant = theMerchant;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theMerchant.isTrading()) {
            this.closestEntity = this.theMerchant.getCustomer();
            return true;
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00001593";
    }
}
