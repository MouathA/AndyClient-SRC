package Mood.events;

import DTool.events.*;

public class WorldRenderEvent extends Event
{
    private float partialTicks;
    
    public WorldRenderEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
