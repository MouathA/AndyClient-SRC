package Mood.Cosmetics.impl;

import net.minecraft.util.*;

public class AnimatedResourceLocation
{
    private final String folder;
    protected final int frames;
    protected final int fpt;
    protected int currentTick;
    protected int currentFrame;
    protected ResourceLocation[] textures;
    
    public AnimatedResourceLocation(final String s, final int n, final int n2) {
        this(s, n, n2, false);
    }
    
    public AnimatedResourceLocation(final String folder, final int frames, final int fpt, final boolean b) {
        this.currentTick = 0;
        this.currentFrame = 0;
        this.folder = folder;
        this.frames = frames;
        this.fpt = fpt;
        this.textures = new ResourceLocation[frames];
        while (0 < frames) {
            if (b) {
                this.textures[0] = new ResourceLocation(String.valueOf(String.valueOf(folder)) + "/" + (this.textures.length - 0) + ".png");
            }
            else {
                this.textures[0] = new ResourceLocation(String.valueOf(String.valueOf(folder)) + "/" + 0 + ".png");
            }
            int n = 0;
            ++n;
        }
    }
    
    public ResourceLocation getTexture() {
        return this.textures[this.currentFrame];
    }
    
    public int getCurrentFrame() {
        return this.currentFrame;
    }
    
    public void update() {
        if (this.currentTick > this.fpt) {
            this.currentTick = 0;
            ++this.currentFrame;
            if (this.currentFrame > this.frames - 1) {
                this.currentFrame = 0;
            }
        }
        ++this.currentTick;
    }
    
    public void setCurrentFrame(final int currentFrame) {
        this.currentFrame = currentFrame;
    }
    
    public int getFrames() {
        return this.frames;
    }
}
