package net.minecraft.client.resources.data;

import com.google.common.collect.*;
import java.util.*;

public class AnimationMetadataSection implements IMetadataSection
{
    private final List animationFrames;
    private final int frameWidth;
    private final int frameHeight;
    private final int frameTime;
    private final boolean field_177220_e;
    private static final String __OBFID;
    
    public AnimationMetadataSection(final List animationFrames, final int frameWidth, final int frameHeight, final int frameTime, final boolean field_177220_e) {
        this.animationFrames = animationFrames;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameTime = frameTime;
        this.field_177220_e = field_177220_e;
    }
    
    public int getFrameHeight() {
        return this.frameHeight;
    }
    
    public int getFrameWidth() {
        return this.frameWidth;
    }
    
    public int getFrameCount() {
        return this.animationFrames.size();
    }
    
    public int getFrameTime() {
        return this.frameTime;
    }
    
    public boolean func_177219_e() {
        return this.field_177220_e;
    }
    
    private AnimationFrame getAnimationFrame(final int n) {
        return this.animationFrames.get(n);
    }
    
    public int getFrameTimeSingle(final int n) {
        final AnimationFrame animationFrame = this.getAnimationFrame(n);
        return animationFrame.hasNoTime() ? this.frameTime : animationFrame.getFrameTime();
    }
    
    public boolean frameHasTime(final int n) {
        return !this.animationFrames.get(n).hasNoTime();
    }
    
    public int getFrameIndex(final int n) {
        return this.animationFrames.get(n).getFrameIndex();
    }
    
    public Set getFrameIndexSet() {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<AnimationFrame> iterator = this.animationFrames.iterator();
        while (iterator.hasNext()) {
            hashSet.add(iterator.next().getFrameIndex());
        }
        return hashSet;
    }
    
    static {
        __OBFID = "CL_00001106";
    }
}
