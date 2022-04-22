package net.minecraft.client.audio;

import net.minecraft.util.*;

public abstract class MovingSound extends PositionedSound implements ITickableSound
{
    protected boolean donePlaying;
    private static final String __OBFID;
    
    protected MovingSound(final ResourceLocation resourceLocation) {
        super(resourceLocation);
        this.donePlaying = false;
    }
    
    @Override
    public boolean isDonePlaying() {
        return this.donePlaying;
    }
    
    static {
        __OBFID = "CL_00001117";
    }
}
