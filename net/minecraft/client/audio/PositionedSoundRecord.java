package net.minecraft.client.audio;

import net.minecraft.util.*;

public class PositionedSoundRecord extends PositionedSound
{
    private static final String __OBFID;
    
    public static PositionedSoundRecord createPositionedSoundRecord(final ResourceLocation resourceLocation, final float n) {
        return new PositionedSoundRecord(resourceLocation, 0.25f, n, false, 0, ISound.AttenuationType.NONE, 0.0f, 0.0f, 0.0f);
    }
    
    public static PositionedSoundRecord createPositionedSoundRecord(final ResourceLocation resourceLocation) {
        return new PositionedSoundRecord(resourceLocation, 1.0f, 1.0f, false, 0, ISound.AttenuationType.NONE, 0.0f, 0.0f, 0.0f);
    }
    
    public static PositionedSoundRecord createRecordSoundAtPosition(final ResourceLocation resourceLocation, final float n, final float n2, final float n3) {
        return new PositionedSoundRecord(resourceLocation, 4.0f, 1.0f, false, 0, ISound.AttenuationType.LINEAR, n, n2, n3);
    }
    
    public PositionedSoundRecord(final ResourceLocation resourceLocation, final float n, final float n2, final float n3, final float n4, final float n5) {
        this(resourceLocation, n, n2, false, 0, ISound.AttenuationType.LINEAR, n3, n4, n5);
    }
    
    private PositionedSoundRecord(final ResourceLocation resourceLocation, final float volume, final float pitch, final boolean repeat, final int repeatDelay, final ISound.AttenuationType attenuationType, final float xPosF, final float yPosF, final float zPosF) {
        super(resourceLocation);
        this.volume = volume;
        this.pitch = pitch;
        this.xPosF = xPosF;
        this.yPosF = yPosF;
        this.zPosF = zPosF;
        this.repeat = repeat;
        this.repeatDelay = repeatDelay;
        this.attenuationType = attenuationType;
    }
    
    static {
        __OBFID = "CL_00001120";
    }
}
