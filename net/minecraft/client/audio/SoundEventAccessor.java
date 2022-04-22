package net.minecraft.client.audio;

public class SoundEventAccessor implements ISoundEventAccessor
{
    private final SoundPoolEntry entry;
    private final int weight;
    private static final String __OBFID;
    
    SoundEventAccessor(final SoundPoolEntry entry, final int weight) {
        this.entry = entry;
        this.weight = weight;
    }
    
    @Override
    public int getWeight() {
        return this.weight;
    }
    
    @Override
    public SoundPoolEntry cloneEntry() {
        return new SoundPoolEntry(this.entry);
    }
    
    public Object cloneEntry1() {
        return this.cloneEntry();
    }
    
    @Override
    public Object cloneEntry() {
        return this.cloneEntry();
    }
    
    static {
        __OBFID = "CL_00001153";
    }
}
