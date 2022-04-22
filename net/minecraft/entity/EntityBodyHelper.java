package net.minecraft.entity;

import net.minecraft.util.*;

public class EntityBodyHelper
{
    private EntityLivingBase theLiving;
    private int rotationTickCounter;
    private float prevRenderYawHead;
    private static final String __OBFID;
    
    public EntityBodyHelper(final EntityLivingBase theLiving) {
        this.theLiving = theLiving;
    }
    
    public void updateRenderAngles() {
        final double n = this.theLiving.posX - this.theLiving.prevPosX;
        final double n2 = this.theLiving.posZ - this.theLiving.prevPosZ;
        if (n * n + n2 * n2 > 2.500000277905201E-7) {
            this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
            this.theLiving.rotationYawHead = this.computeAngleWithBound(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0f);
            this.prevRenderYawHead = this.theLiving.rotationYawHead;
            this.rotationTickCounter = 0;
        }
        else {
            float n3 = 75.0f;
            if (Math.abs(this.theLiving.rotationYawHead - this.prevRenderYawHead) > 15.0f) {
                this.rotationTickCounter = 0;
                this.prevRenderYawHead = this.theLiving.rotationYawHead;
            }
            else {
                ++this.rotationTickCounter;
                if (this.rotationTickCounter > 10) {
                    n3 = Math.max(1.0f - (this.rotationTickCounter - 10) / 10.0f, 0.0f) * 75.0f;
                }
            }
            this.theLiving.renderYawOffset = this.computeAngleWithBound(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, n3);
        }
    }
    
    private float computeAngleWithBound(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n - n2);
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        if (wrapAngleTo180_float >= n3) {
            wrapAngleTo180_float = n3;
        }
        return n - wrapAngleTo180_float;
    }
    
    static {
        __OBFID = "CL_00001570";
    }
}
