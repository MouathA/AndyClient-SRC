package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityMoveHelper
{
    protected EntityLiving entity;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected double speed;
    protected boolean update;
    private static final String __OBFID;
    
    public EntityMoveHelper(final EntityLiving entity) {
        this.entity = entity;
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.posZ = entity.posZ;
    }
    
    public boolean isUpdating() {
        return this.update;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    public void setMoveTo(final double posX, final double posY, final double posZ, final double speed) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.speed = speed;
        this.update = true;
    }
    
    public void onUpdateMoveHelper() {
        this.entity.setMoveForward(0.0f);
        if (this.update) {
            this.update = false;
            final int floor_double = MathHelper.floor_double(this.entity.getEntityBoundingBox().minY + 0.5);
            final double n = this.posX - this.entity.posX;
            final double n2 = this.posZ - this.entity.posZ;
            final double n3 = this.posY - floor_double;
            if (n * n + n3 * n3 + n2 * n2 >= 2.500000277905201E-7) {
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f, 30.0f);
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                if (n3 > 0.0 && n * n + n2 * n2 < 1.0) {
                    this.entity.getJumpHelper().setJumping();
                }
            }
        }
    }
    
    protected float limitAngle(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        float n4 = n + wrapAngleTo180_float;
        if (n4 < 0.0f) {
            n4 += 360.0f;
        }
        else if (n4 > 360.0f) {
            n4 -= 360.0f;
        }
        return n4;
    }
    
    public double func_179917_d() {
        return this.posX;
    }
    
    public double func_179919_e() {
        return this.posY;
    }
    
    public double func_179918_f() {
        return this.posZ;
    }
    
    static {
        __OBFID = "CL_00001573";
    }
}
