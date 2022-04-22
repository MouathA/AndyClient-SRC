package net.minecraft.client.audio;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;

public class GuardianSound extends MovingSound
{
    private final EntityGuardian guardian;
    private static final String __OBFID;
    
    public GuardianSound(final EntityGuardian guardian) {
        super(new ResourceLocation("minecraft:mob.guardian.attack"));
        this.guardian = guardian;
        this.attenuationType = ISound.AttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
    }
    
    @Override
    public void update() {
        if (!this.guardian.isDead && this.guardian.func_175474_cn()) {
            this.xPosF = (float)this.guardian.posX;
            this.yPosF = (float)this.guardian.posY;
            this.zPosF = (float)this.guardian.posZ;
            final float func_175477_p = this.guardian.func_175477_p(0.0f);
            this.volume = 0.0f + 1.0f * func_175477_p * func_175477_p;
            this.pitch = 0.7f + 0.5f * func_175477_p;
        }
        else {
            this.donePlaying = true;
        }
    }
    
    static {
        __OBFID = "CL_00002381";
    }
}
