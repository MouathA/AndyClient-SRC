package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

@Deprecated
public abstract class LegacySoundRewriter extends RewriterBase
{
    protected final Int2ObjectMap soundRewrites;
    
    protected LegacySoundRewriter(final BackwardsProtocol backwardsProtocol) {
        super(backwardsProtocol);
        this.soundRewrites = new Int2ObjectOpenHashMap(64);
    }
    
    public SoundData added(final int n, final int n2) {
        return this.added(n, n2, -1.0f);
    }
    
    public SoundData added(final int n, final int n2, final float n3) {
        final SoundData soundData = new SoundData(n2, true, n3, true);
        this.soundRewrites.put(n, soundData);
        return soundData;
    }
    
    public SoundData removed(final int n) {
        final SoundData soundData = new SoundData(-1, false, -1.0f, false);
        this.soundRewrites.put(n, soundData);
        return soundData;
    }
    
    public int handleSounds(final int n) {
        int n2 = n;
        final SoundData soundData = (SoundData)this.soundRewrites.get(n);
        if (soundData != null) {
            return soundData.getReplacementSound();
        }
        for (final Int2ObjectMap.Entry entry : this.soundRewrites.int2ObjectEntrySet()) {
            if (n > entry.getIntKey()) {
                if (entry.getValue().isAdded()) {
                    --n2;
                }
                else {
                    ++n2;
                }
            }
        }
        return n2;
    }
    
    public boolean hasPitch(final int n) {
        final SoundData soundData = (SoundData)this.soundRewrites.get(n);
        return soundData != null && soundData.isChangePitch();
    }
    
    public float handlePitch(final int n) {
        final SoundData soundData = (SoundData)this.soundRewrites.get(n);
        return (soundData != null) ? soundData.getNewPitch() : 1.0f;
    }
    
    public static final class SoundData
    {
        private final int replacementSound;
        private final boolean changePitch;
        private final float newPitch;
        private final boolean added;
        
        public SoundData(final int replacementSound, final boolean changePitch, final float newPitch, final boolean added) {
            this.replacementSound = replacementSound;
            this.changePitch = changePitch;
            this.newPitch = newPitch;
            this.added = added;
        }
        
        public int getReplacementSound() {
            return this.replacementSound;
        }
        
        public boolean isChangePitch() {
            return this.changePitch;
        }
        
        public float getNewPitch() {
            return this.newPitch;
        }
        
        public boolean isAdded() {
            return this.added;
        }
    }
}
