package net.minecraft.client.audio;

import net.minecraft.util.*;

public class SoundPoolEntry
{
    private final ResourceLocation field_148656_a;
    private final boolean field_148654_b;
    private double field_148655_c;
    private double field_148653_d;
    private static final String __OBFID;
    
    public SoundPoolEntry(final ResourceLocation field_148656_a, final double field_148655_c, final double field_148653_d, final boolean field_148654_b) {
        this.field_148656_a = field_148656_a;
        this.field_148655_c = field_148655_c;
        this.field_148653_d = field_148653_d;
        this.field_148654_b = field_148654_b;
    }
    
    public SoundPoolEntry(final SoundPoolEntry soundPoolEntry) {
        this.field_148656_a = soundPoolEntry.field_148656_a;
        this.field_148655_c = soundPoolEntry.field_148655_c;
        this.field_148653_d = soundPoolEntry.field_148653_d;
        this.field_148654_b = soundPoolEntry.field_148654_b;
    }
    
    public ResourceLocation getSoundPoolEntryLocation() {
        return this.field_148656_a;
    }
    
    public double getPitch() {
        return this.field_148655_c;
    }
    
    public void setPitch(final double field_148655_c) {
        this.field_148655_c = field_148655_c;
    }
    
    public double getVolume() {
        return this.field_148653_d;
    }
    
    public void setVolume(final double field_148653_d) {
        this.field_148653_d = field_148653_d;
    }
    
    public boolean isStreamingSound() {
        return this.field_148654_b;
    }
    
    static {
        __OBFID = "CL_00001140";
    }
}
