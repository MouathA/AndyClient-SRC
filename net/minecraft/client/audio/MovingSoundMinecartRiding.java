package net.minecraft.client.audio;

import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;

public class MovingSoundMinecartRiding extends MovingSound
{
    private final EntityPlayer player;
    private final EntityMinecart minecart;
    private static final String __OBFID;
    
    public MovingSoundMinecartRiding(final EntityPlayer player, final EntityMinecart minecart) {
        super(new ResourceLocation("minecraft:minecart.inside"));
        this.player = player;
        this.minecart = minecart;
        this.attenuationType = ISound.AttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
    }
    
    @Override
    public void update() {
        if (!this.minecart.isDead && this.player.isRiding() && this.player.ridingEntity == this.minecart) {
            final float sqrt_double = MathHelper.sqrt_double(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
            if (sqrt_double >= 0.01) {
                this.volume = 0.0f + MathHelper.clamp_float(sqrt_double, 0.0f, 1.0f) * 0.75f;
            }
            else {
                this.volume = 0.0f;
            }
        }
        else {
            this.donePlaying = true;
        }
    }
    
    static {
        __OBFID = "CL_00001119";
    }
}
