package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityLookHelper
{
    private EntityLiving entity;
    private float deltaLookYaw;
    private float deltaLookPitch;
    private boolean isLooking;
    private double posX;
    private double posY;
    private double posZ;
    private static final String __OBFID;
    
    public EntityLookHelper(final EntityLiving entity) {
        this.entity = entity;
    }
    
    public void setLookPositionWithEntity(final Entity entity, final float deltaLookYaw, final float deltaLookPitch) {
        this.posX = entity.posX;
        if (entity instanceof EntityLivingBase) {
            this.posY = entity.posY + entity.getEyeHeight();
        }
        else {
            this.posY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0;
        }
        this.posZ = entity.posZ;
        this.deltaLookYaw = deltaLookYaw;
        this.deltaLookPitch = deltaLookPitch;
        this.isLooking = true;
    }
    
    public void setLookPosition(final double posX, final double posY, final double posZ, final float deltaLookYaw, final float deltaLookPitch) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.deltaLookYaw = deltaLookYaw;
        this.deltaLookPitch = deltaLookPitch;
        this.isLooking = true;
    }
    
    public void onUpdateLook() {
        this.entity.rotationPitch = 0.0f;
        if (this.isLooking) {
            this.isLooking = false;
            final double n = this.posX - this.entity.posX;
            final double n2 = this.posY - (this.entity.posY + this.entity.getEyeHeight());
            final double n3 = this.posZ - this.entity.posZ;
            final double n4 = MathHelper.sqrt_double(n * n + n3 * n3);
            final float n5 = (float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f;
            this.entity.rotationPitch = this.updateRotation(this.entity.rotationPitch, (float)(-(Math.atan2(n2, n4) * 180.0 / 3.141592653589793)), this.deltaLookPitch);
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, n5, this.deltaLookYaw);
        }
        else {
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0f);
        }
        final float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(this.entity.rotationYawHead - this.entity.renderYawOffset);
        if (!this.entity.getNavigator().noPath()) {
            if (wrapAngleTo180_float < -75.0f) {
                this.entity.rotationYawHead = this.entity.renderYawOffset - 75.0f;
            }
            if (wrapAngleTo180_float > 75.0f) {
                this.entity.rotationYawHead = this.entity.renderYawOffset + 75.0f;
            }
        }
    }
    
    private float updateRotation(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    public boolean func_180424_b() {
        return this.isLooking;
    }
    
    public double func_180423_e() {
        return this.posX;
    }
    
    public double func_180422_f() {
        return this.posY;
    }
    
    public double func_180421_g() {
        return this.posZ;
    }
    
    static {
        __OBFID = "CL_00001572";
    }
}
