package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityJumpHelper
{
    private EntityLiving entity;
    protected boolean isJumping;
    private static final String __OBFID;
    
    public EntityJumpHelper(final EntityLiving entity) {
        this.entity = entity;
    }
    
    public void setJumping() {
        this.isJumping = true;
    }
    
    public void doJump() {
        this.entity.setJumping(this.isJumping);
        this.isJumping = false;
    }
    
    static {
        __OBFID = "CL_00001571";
    }
}
