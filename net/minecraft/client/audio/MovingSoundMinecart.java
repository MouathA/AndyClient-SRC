package net.minecraft.client.audio;

import net.minecraft.entity.item.*;
import net.minecraft.util.*;

public class MovingSoundMinecart extends MovingSound
{
    private final EntityMinecart minecart;
    private float field_147669_l;
    private static final String __OBFID;
    
    public MovingSoundMinecart(final EntityMinecart minecart) {
        super(new ResourceLocation("minecraft:minecart.base"));
        this.field_147669_l = 0.0f;
        this.minecart = minecart;
        this.repeat = true;
        this.repeatDelay = 0;
    }
    
    @Override
    public void update() {
        if (this.minecart.isDead) {
            this.donePlaying = true;
        }
        else {
            this.xPosF = (float)this.minecart.posX;
            this.yPosF = (float)this.minecart.posY;
            this.zPosF = (float)this.minecart.posZ;
            final float sqrt_double = MathHelper.sqrt_double(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
            if (sqrt_double >= 0.01) {
                this.field_147669_l = MathHelper.clamp_float(this.field_147669_l + 0.0025f, 0.0f, 1.0f);
                this.volume = 0.0f + MathHelper.clamp_float(sqrt_double, 0.0f, 0.5f) * 0.7f;
            }
            else {
                this.field_147669_l = 0.0f;
                this.volume = 0.0f;
            }
        }
    }
    
    static {
        __OBFID = "CL_00001118";
    }
}
