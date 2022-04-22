package DTool.events.listeners;

import DTool.events.*;
import net.minecraft.network.*;

public class PacketReceiveEvent extends EventCancellable
{
    private Packet packet;
    
    public PacketReceiveEvent(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
