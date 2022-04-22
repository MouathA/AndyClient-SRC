package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.connection.*;

public class GlassConnectionHandler extends AbstractFenceConnectionHandler
{
    static List init() {
        final ArrayList<ConnectionData.ConnectorInitAction> list = new ArrayList<ConnectionData.ConnectorInitAction>(18);
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:white_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:orange_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:magenta_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:light_blue_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:yellow_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:lime_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:pink_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:gray_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:light_gray_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:cyan_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:purple_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:blue_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:brown_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:green_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:red_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:black_stained_glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:glass_pane"));
        list.add(new GlassConnectionHandler("paneConnections").getInitAction("minecraft:iron_bars"));
        return list;
    }
    
    public GlassConnectionHandler(final String s) {
        super(s);
    }
    
    @Override
    protected byte getStates(final UserConnection userConnection, final Position position, final int n) {
        final byte states = super.getStates(userConnection, position, n);
        if (states != 0) {
            return states;
        }
        final ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        return (byte)((protocolInfo.getServerProtocolVersion() <= 47 && protocolInfo.getServerProtocolVersion() != -1) ? 15 : states);
    }
}
