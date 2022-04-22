package Mood.events;

import com.darkmagician6.eventapi.events.callables.*;
import net.minecraft.util.*;

public class EventBlockBreak extends EventCancellable
{
    private int[] coords;
    private EnumFacing facing;
    
    public EventBlockBreak(final int n, final int n2, final int n3, final EnumFacing facing) {
        this.facing = facing;
        (this.coords = new int[3])[0] = n;
        this.coords[1] = n2;
        this.coords[2] = n3;
    }
    
    public int[] getCoords() {
        return this.coords;
    }
    
    public int getX() {
        return this.coords[0];
    }
    
    public int getY() {
        return this.coords[1];
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public int getZ() {
        return this.coords[2];
    }
}
