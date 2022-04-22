package net.minecraft.client.audio;

import net.minecraft.util.*;

public interface ISound
{
    ResourceLocation getSoundLocation();
    
    boolean canRepeat();
    
    int getRepeatDelay();
    
    float getVolume();
    
    float getPitch();
    
    float getXPosF();
    
    float getYPosF();
    
    float getZPosF();
    
    AttenuationType getAttenuationType();
    
    public enum AttenuationType
    {
        NONE("NONE", 0, "NONE", 0, 0), 
        LINEAR("LINEAR", 1, "LINEAR", 1, 2);
        
        private final int type;
        private static final AttenuationType[] $VALUES;
        private static final String __OBFID;
        private static final AttenuationType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001126";
            ENUM$VALUES = new AttenuationType[] { AttenuationType.NONE, AttenuationType.LINEAR };
            $VALUES = new AttenuationType[] { AttenuationType.NONE, AttenuationType.LINEAR };
        }
        
        private AttenuationType(final String s, final int n, final String s2, final int n2, final int type) {
            this.type = type;
        }
        
        public int getTypeInt() {
            return this.type;
        }
    }
}
