package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;

public class Levitation extends StoredObject implements Tickable
{
    private int amplifier;
    private boolean active;
    
    public Levitation(final UserConnection userConnection) {
        super(userConnection);
        this.active = false;
    }
    
    @Override
    public void tick() {
        if (!this.active) {
            return;
        }
        final int n = (this.amplifier + 1) * 360;
        final PacketWrapper create = PacketWrapper.create(18, null, this.getUser());
        create.write(Type.VAR_INT, ((EntityTracker)this.getUser().get(EntityTracker.class)).getPlayerId());
        create.write(Type.SHORT, 0);
        create.write(Type.SHORT, (short)n);
        create.write(Type.SHORT, 0);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
    }
    
    public void setActive(final boolean active) {
        this.active = active;
    }
    
    public void setAmplifier(final int amplifier) {
        this.amplifier = amplifier;
    }
}
