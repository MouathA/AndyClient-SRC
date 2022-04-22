package net.minecraft.client.audio;

import java.util.*;
import com.google.common.collect.*;

public class SoundList
{
    private final List soundList;
    private boolean replaceExisting;
    private SoundCategory category;
    private static final String __OBFID;
    
    public SoundList() {
        this.soundList = Lists.newArrayList();
    }
    
    public List getSoundList() {
        return this.soundList;
    }
    
    public boolean canReplaceExisting() {
        return this.replaceExisting;
    }
    
    public void setReplaceExisting(final boolean replaceExisting) {
        this.replaceExisting = replaceExisting;
    }
    
    public SoundCategory getSoundCategory() {
        return this.category;
    }
    
    public void setSoundCategory(final SoundCategory category) {
        this.category = category;
    }
    
    static {
        __OBFID = "CL_00001121";
    }
    
    public static class SoundEntry
    {
        private String name;
        private float volume;
        private float pitch;
        private int field_148565_d;
        private Type field_148566_e;
        private boolean field_148564_f;
        private static final String __OBFID;
        
        public SoundEntry() {
            this.volume = 1.0f;
            this.pitch = 1.0f;
            this.field_148565_d = 1;
            this.field_148566_e = Type.FILE;
            this.field_148564_f = false;
        }
        
        public String getSoundEntryName() {
            return this.name;
        }
        
        public void setSoundEntryName(final String name) {
            this.name = name;
        }
        
        public float getSoundEntryVolume() {
            return this.volume;
        }
        
        public void setSoundEntryVolume(final float volume) {
            this.volume = volume;
        }
        
        public float getSoundEntryPitch() {
            return this.pitch;
        }
        
        public void setSoundEntryPitch(final float pitch) {
            this.pitch = pitch;
        }
        
        public int getSoundEntryWeight() {
            return this.field_148565_d;
        }
        
        public void setSoundEntryWeight(final int field_148565_d) {
            this.field_148565_d = field_148565_d;
        }
        
        public Type getSoundEntryType() {
            return this.field_148566_e;
        }
        
        public void setSoundEntryType(final Type field_148566_e) {
            this.field_148566_e = field_148566_e;
        }
        
        public boolean isStreaming() {
            return this.field_148564_f;
        }
        
        public void setStreaming(final boolean field_148564_f) {
            this.field_148564_f = field_148564_f;
        }
        
        static {
            __OBFID = "CL_00001122";
        }
        
        public enum Type
        {
            FILE("FILE", 0, "FILE", 0, "file"), 
            SOUND_EVENT("SOUND_EVENT", 1, "SOUND_EVENT", 1, "event");
            
            private final String field_148583_c;
            private static final Type[] $VALUES;
            private static final String __OBFID;
            private static final Type[] ENUM$VALUES;
            
            static {
                __OBFID = "CL_00001123";
                ENUM$VALUES = new Type[] { Type.FILE, Type.SOUND_EVENT };
                $VALUES = new Type[] { Type.FILE, Type.SOUND_EVENT };
            }
            
            private Type(final String s, final int n, final String s2, final int n2, final String field_148583_c) {
                this.field_148583_c = field_148583_c;
            }
            
            public static Type getType(final String s) {
                final Type[] values = values();
                while (0 < values.length) {
                    final Type type = values[0];
                    if (type.field_148583_c.equals(s)) {
                        return type;
                    }
                    int n = 0;
                    ++n;
                }
                return null;
            }
        }
    }
}
