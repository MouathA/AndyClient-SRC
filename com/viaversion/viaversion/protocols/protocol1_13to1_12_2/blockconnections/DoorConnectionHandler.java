package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;

public class DoorConnectionHandler extends ConnectionHandler
{
    private static final Map doorDataMap;
    private static final Map connectedStates;
    
    static ConnectionData.ConnectorInitAction init() {
        final LinkedList<String> list = new LinkedList<String>();
        list.add("minecraft:oak_door");
        list.add("minecraft:birch_door");
        list.add("minecraft:jungle_door");
        list.add("minecraft:dark_oak_door");
        list.add("minecraft:acacia_door");
        list.add("minecraft:spruce_door");
        list.add("minecraft:iron_door");
        return DoorConnectionHandler::lambda$init$0;
    }
    
    private static short getStates(final DoorData doorData) {
        if (doorData.isLower()) {
            final short n = 1;
        }
        if (doorData.isOpen()) {
            final short n2 = 2;
        }
        if (doorData.isPowered()) {
            final short n3 = 4;
        }
        if (doorData.isRightHinge()) {
            final short n4 = 8;
        }
        final short n5 = (short)(0x0 | doorData.getFacing().ordinal() << 4);
        final short n6 = (short)(0x0 | (doorData.getType() & 0x7) << 6);
        return 0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        final DoorData doorData = DoorConnectionHandler.doorDataMap.get(n);
        if (doorData == null) {
            return n;
        }
        final short n2 = (short)(0x0 | (doorData.getType() & 0x7) << 6);
        if (doorData.isLower()) {
            final DoorData doorData2 = DoorConnectionHandler.doorDataMap.get(this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)));
            if (doorData2 == null) {
                return n;
            }
            final short n3 = 1;
            if (doorData.isOpen()) {
                final short n4 = 2;
            }
            if (doorData2.isPowered()) {
                final short n5 = 4;
            }
            if (doorData2.isRightHinge()) {
                final short n6 = 8;
            }
            final short n7 = (short)(0x0 | doorData.getFacing().ordinal() << 4);
        }
        else {
            final DoorData doorData3 = DoorConnectionHandler.doorDataMap.get(this.getBlockData(userConnection, position.getRelative(BlockFace.BOTTOM)));
            if (doorData3 == null) {
                return n;
            }
            if (doorData3.isOpen()) {
                final short n8 = 2;
            }
            if (doorData.isPowered()) {
                final short n9 = 4;
            }
            if (doorData.isRightHinge()) {
                final short n10 = 8;
            }
            final short n11 = (short)(0x0 | doorData3.getFacing().ordinal() << 4);
        }
        final Integer n12 = DoorConnectionHandler.connectedStates.get(0);
        return (n12 == null) ? n : n12;
    }
    
    private static void lambda$init$0(final List list, final DoorConnectionHandler doorConnectionHandler, final WrappedBlockData wrappedBlockData) {
        final int index = list.indexOf(wrappedBlockData.getMinecraftKey());
        if (index == -1) {
            return;
        }
        final int savedBlockStateId = wrappedBlockData.getSavedBlockStateId();
        final DoorData doorData = new DoorData(wrappedBlockData.getValue("half").equals("lower"), wrappedBlockData.getValue("hinge").equals("right"), wrappedBlockData.getValue("powered").equals("true"), wrappedBlockData.getValue("open").equals("true"), BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)), index, null);
        DoorConnectionHandler.doorDataMap.put(savedBlockStateId, doorData);
        DoorConnectionHandler.connectedStates.put(getStates(doorData), savedBlockStateId);
        ConnectionData.connectionHandlerMap.put(savedBlockStateId, doorConnectionHandler);
    }
    
    static {
        doorDataMap = new HashMap();
        connectedStates = new HashMap();
    }
    
    private static final class DoorData
    {
        private final boolean lower;
        private final boolean rightHinge;
        private final boolean powered;
        private final boolean open;
        private final BlockFace facing;
        private final int type;
        
        private DoorData(final boolean lower, final boolean rightHinge, final boolean powered, final boolean open, final BlockFace facing, final int type) {
            this.lower = lower;
            this.rightHinge = rightHinge;
            this.powered = powered;
            this.open = open;
            this.facing = facing;
            this.type = type;
        }
        
        public boolean isLower() {
            return this.lower;
        }
        
        public boolean isRightHinge() {
            return this.rightHinge;
        }
        
        public boolean isPowered() {
            return this.powered;
        }
        
        public boolean isOpen() {
            return this.open;
        }
        
        public BlockFace getFacing() {
            return this.facing;
        }
        
        public int getType() {
            return this.type;
        }
        
        DoorData(final boolean b, final boolean b2, final boolean b3, final boolean b4, final BlockFace blockFace, final int n, final DoorConnectionHandler$1 object) {
            this(b, b2, b3, b4, blockFace, n);
        }
    }
}
