package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.*;

public class BasicFenceConnectionHandler extends AbstractFenceConnectionHandler
{
    static List init() {
        final ArrayList<ConnectionData.ConnectorInitAction> list = new ArrayList<ConnectionData.ConnectorInitAction>();
        list.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:oak_fence"));
        list.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:birch_fence"));
        list.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:jungle_fence"));
        list.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:dark_oak_fence"));
        list.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:acacia_fence"));
        list.add(new BasicFenceConnectionHandler("fenceConnections").getInitAction("minecraft:spruce_fence"));
        return list;
    }
    
    public BasicFenceConnectionHandler(final String s) {
        super(s);
    }
}
