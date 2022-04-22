package net.minecraft.client.audio;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class SoundEventAccessorComposite implements ISoundEventAccessor
{
    private final List soundPool;
    private final Random rnd;
    private final ResourceLocation soundLocation;
    private final SoundCategory category;
    private double eventPitch;
    private double eventVolume;
    private static final String __OBFID;
    
    public SoundEventAccessorComposite(final ResourceLocation soundLocation, final double eventPitch, final double eventVolume, final SoundCategory category) {
        this.soundPool = Lists.newArrayList();
        this.rnd = new Random();
        this.soundLocation = soundLocation;
        this.eventVolume = eventVolume;
        this.eventPitch = eventPitch;
        this.category = category;
    }
    
    @Override
    public int getWeight() {
        final Iterator<ISoundEventAccessor> iterator = this.soundPool.iterator();
        while (iterator.hasNext()) {
            final int n = 0 + iterator.next().getWeight();
        }
        return 0;
    }
    
    public SoundPoolEntry cloneEntry1() {
        final int weight = this.getWeight();
        if (!this.soundPool.isEmpty() && weight != 0) {
            int nextInt = this.rnd.nextInt(weight);
            for (final ISoundEventAccessor soundEventAccessor : this.soundPool) {
                nextInt -= soundEventAccessor.getWeight();
                if (nextInt < 0) {
                    final SoundPoolEntry soundPoolEntry = (SoundPoolEntry)soundEventAccessor.cloneEntry();
                    soundPoolEntry.setPitch(soundPoolEntry.getPitch() * this.eventPitch);
                    soundPoolEntry.setVolume(soundPoolEntry.getVolume() * this.eventVolume);
                    return soundPoolEntry;
                }
            }
            return SoundHandler.missing_sound;
        }
        return SoundHandler.missing_sound;
    }
    
    public void addSoundToEventPool(final ISoundEventAccessor soundEventAccessor) {
        this.soundPool.add(soundEventAccessor);
    }
    
    public ResourceLocation getSoundEventLocation() {
        return this.soundLocation;
    }
    
    public SoundCategory getSoundCategory() {
        return this.category;
    }
    
    @Override
    public Object cloneEntry() {
        return this.cloneEntry1();
    }
    
    static {
        __OBFID = "CL_00001146";
    }
}
