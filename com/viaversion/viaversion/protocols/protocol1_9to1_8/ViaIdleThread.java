package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;

public class ViaIdleThread implements Runnable
{
    @Override
    public void run() {
        for (final UserConnection userConnection : Via.getManager().getConnectionManager().getConnections()) {
            final ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
            if (protocolInfo != null) {
                if (!protocolInfo.getPipeline().contains(Protocol1_9To1_8.class)) {
                    continue;
                }
                final MovementTracker movementTracker = (MovementTracker)userConnection.get(MovementTracker.class);
                if (movementTracker == null) {
                    continue;
                }
                if (movementTracker.getNextIdlePacket() > System.currentTimeMillis() || !userConnection.getChannel().isOpen()) {
                    continue;
                }
                ((MovementTransmitterProvider)Via.getManager().getProviders().get(MovementTransmitterProvider.class)).sendPlayer(userConnection);
            }
        }
    }
}
