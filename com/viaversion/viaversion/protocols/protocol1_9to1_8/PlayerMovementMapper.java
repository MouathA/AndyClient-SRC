package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.type.*;

public class PlayerMovementMapper implements PacketHandler
{
    @Override
    public void handle(final PacketWrapper packetWrapper) throws Exception {
        final MovementTracker movementTracker = (MovementTracker)packetWrapper.user().get(MovementTracker.class);
        movementTracker.incrementIdlePacket();
        if (packetWrapper.is(Type.BOOLEAN, 0)) {
            movementTracker.setGround((boolean)packetWrapper.get(Type.BOOLEAN, 0));
        }
    }
}
