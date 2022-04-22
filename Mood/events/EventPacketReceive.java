package Mood.events;

import com.darkmagician6.eventapi.events.callables.*;
import net.minecraft.network.*;

public class EventPacketReceive extends EventCancellable
{
    private boolean cancel;
    public Packet packet;
    private String string;
    
    public EventPacketReceive(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
