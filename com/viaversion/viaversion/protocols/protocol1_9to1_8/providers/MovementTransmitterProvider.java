package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import io.netty.channel.*;

public abstract class MovementTransmitterProvider implements Provider
{
    public abstract Object getFlyingPacket();
    
    public abstract Object getGroundPacket();
    
    public void sendPlayer(final UserConnection userConnection) {
        final ChannelHandlerContext contextBefore = PipelineUtil.getContextBefore("decoder", userConnection.getChannel().pipeline());
        if (contextBefore != null) {
            if (((MovementTracker)userConnection.get(MovementTracker.class)).isGround()) {
                contextBefore.fireChannelRead(this.getGroundPacket());
            }
            else {
                contextBefore.fireChannelRead(this.getFlyingPacket());
            }
            ((MovementTracker)userConnection.get(MovementTracker.class)).incrementIdlePacket();
        }
    }
}
