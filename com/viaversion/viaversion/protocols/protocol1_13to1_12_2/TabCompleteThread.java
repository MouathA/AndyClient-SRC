package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.*;
import java.util.*;

public class TabCompleteThread implements Runnable
{
    @Override
    public void run() {
        for (final UserConnection userConnection : Via.getManager().getConnectionManager().getConnections()) {
            if (userConnection.getProtocolInfo() == null) {
                continue;
            }
            if (!userConnection.getProtocolInfo().getPipeline().contains(Protocol1_13To1_12_2.class) || !userConnection.getChannel().isOpen()) {
                continue;
            }
            ((TabCompleteTracker)userConnection.get(TabCompleteTracker.class)).sendPacketToServer(userConnection);
        }
    }
}
