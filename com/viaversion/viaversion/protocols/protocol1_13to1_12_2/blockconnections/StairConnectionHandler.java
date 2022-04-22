package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;

public class StairConnectionHandler extends ConnectionHandler
{
    private static final Map stairDataMap;
    private static final Map connectedBlocks;
    
    static ConnectionData.ConnectorInitAction init() {
        final LinkedList<String> list = new LinkedList<String>();
        list.add("minecraft:oak_stairs");
        list.add("minecraft:cobblestone_stairs");
        list.add("minecraft:brick_stairs");
        list.add("minecraft:stone_brick_stairs");
        list.add("minecraft:nether_brick_stairs");
        list.add("minecraft:sandstone_stairs");
        list.add("minecraft:spruce_stairs");
        list.add("minecraft:birch_stairs");
        list.add("minecraft:jungle_stairs");
        list.add("minecraft:quartz_stairs");
        list.add("minecraft:acacia_stairs");
        list.add("minecraft:dark_oak_stairs");
        list.add("minecraft:red_sandstone_stairs");
        list.add("minecraft:purpur_stairs");
        list.add("minecraft:prismarine_stairs");
        list.add("minecraft:prismarine_brick_stairs");
        list.add("minecraft:dark_prismarine_stairs");
        return StairConnectionHandler::lambda$init$0;
    }
    
    private static short getStates(final StairData stairData) {
        if (stairData.isBottom()) {
            final short n = 1;
        }
        final short n2 = (short)(0x0 | stairData.getShape() << 1);
        final short n3 = (short)(0x0 | stairData.getType() << 4);
        final short n4 = (short)(0x0 | stairData.getFacing().ordinal() << 9);
        return 0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        final StairData stairData = StairConnectionHandler.stairDataMap.get(n);
        if (stairData == null) {
            return n;
        }
        if (stairData.isBottom()) {
            final short n2 = 1;
        }
        final short n3 = (short)(0x0 | this.getShape(userConnection, position, stairData) << 1);
        final short n4 = (short)(0x0 | stairData.getType() << 4);
        final short n5 = (short)(0x0 | stairData.getFacing().ordinal() << 9);
        final Integer n6 = StairConnectionHandler.connectedBlocks.get(0);
        return (n6 == null) ? n : n6;
    }
    
    private int getShape(final UserConnection p0, final Position p1, final StairData p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData.getFacing:()Lcom/viaversion/viaversion/api/minecraft/BlockFace;
        //     4: astore          4
        //     6: getstatic       com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler.stairDataMap:Ljava/util/Map;
        //     9: aload_0        
        //    10: aload_1        
        //    11: aload_2        
        //    12: aload           4
        //    14: invokevirtual   com/viaversion/viaversion/api/minecraft/Position.getRelative:(Lcom/viaversion/viaversion/api/minecraft/BlockFace;)Lcom/viaversion/viaversion/api/minecraft/Position;
        //    17: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler.getBlockData:(Lcom/viaversion/viaversion/api/connection/UserConnection;Lcom/viaversion/viaversion/api/minecraft/Position;)I
        //    20: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    23: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    28: checkcast       Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData;
        //    31: astore          5
        //    33: aload           5
        //    35: ifnull          99
        //    38: aload           5
        //    40: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData.isBottom:()Z
        //    43: aload_3        
        //    44: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData.isBottom:()Z
        //    47: if_icmpne       99
        //    50: aload           5
        //    52: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData.getFacing:()Lcom/viaversion/viaversion/api/minecraft/BlockFace;
        //    55: astore          6
        //    57: aload           4
        //    59: invokevirtual   com/viaversion/viaversion/api/minecraft/BlockFace.axis:()Lcom/viaversion/viaversion/api/minecraft/BlockFace$EnumAxis;
        //    62: aload           6
        //    64: invokevirtual   com/viaversion/viaversion/api/minecraft/BlockFace.axis:()Lcom/viaversion/viaversion/api/minecraft/BlockFace$EnumAxis;
        //    67: if_acmpeq       99
        //    70: aload_0        
        //    71: aload_1        
        //    72: aload_3        
        //    73: aload_2        
        //    74: aload           6
        //    76: invokevirtual   com/viaversion/viaversion/api/minecraft/BlockFace.opposite:()Lcom/viaversion/viaversion/api/minecraft/BlockFace;
        //    79: ifnull          99
        //    82: aload           6
        //    84: aload_0        
        //    85: aload           4
        //    87: invokespecial   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler.rotateAntiClockwise:(Lcom/viaversion/viaversion/api/minecraft/BlockFace;)Lcom/viaversion/viaversion/api/minecraft/BlockFace;
        //    90: if_acmpne       97
        //    93: iconst_3       
        //    94: goto            98
        //    97: iconst_4       
        //    98: ireturn        
        //    99: getstatic       com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler.stairDataMap:Ljava/util/Map;
        //   102: aload_0        
        //   103: aload_1        
        //   104: aload_2        
        //   105: aload           4
        //   107: invokevirtual   com/viaversion/viaversion/api/minecraft/BlockFace.opposite:()Lcom/viaversion/viaversion/api/minecraft/BlockFace;
        //   110: invokevirtual   com/viaversion/viaversion/api/minecraft/Position.getRelative:(Lcom/viaversion/viaversion/api/minecraft/BlockFace;)Lcom/viaversion/viaversion/api/minecraft/Position;
        //   113: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler.getBlockData:(Lcom/viaversion/viaversion/api/connection/UserConnection;Lcom/viaversion/viaversion/api/minecraft/Position;)I
        //   116: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   119: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   124: checkcast       Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData;
        //   127: astore          5
        //   129: aload           5
        //   131: ifnull          192
        //   134: aload           5
        //   136: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData.isBottom:()Z
        //   139: aload_3        
        //   140: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData.isBottom:()Z
        //   143: if_icmpne       192
        //   146: aload           5
        //   148: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData.getFacing:()Lcom/viaversion/viaversion/api/minecraft/BlockFace;
        //   151: astore          6
        //   153: aload           4
        //   155: invokevirtual   com/viaversion/viaversion/api/minecraft/BlockFace.axis:()Lcom/viaversion/viaversion/api/minecraft/BlockFace$EnumAxis;
        //   158: aload           6
        //   160: invokevirtual   com/viaversion/viaversion/api/minecraft/BlockFace.axis:()Lcom/viaversion/viaversion/api/minecraft/BlockFace$EnumAxis;
        //   163: if_acmpeq       192
        //   166: aload_0        
        //   167: aload_1        
        //   168: aload_3        
        //   169: aload_2        
        //   170: aload           6
        //   172: ifnull          192
        //   175: aload           6
        //   177: aload_0        
        //   178: aload           4
        //   180: invokespecial   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler.rotateAntiClockwise:(Lcom/viaversion/viaversion/api/minecraft/BlockFace;)Lcom/viaversion/viaversion/api/minecraft/BlockFace;
        //   183: if_acmpne       190
        //   186: iconst_1       
        //   187: goto            191
        //   190: iconst_2       
        //   191: ireturn        
        //   192: iconst_0       
        //   193: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0192 (coming from #0172).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private BlockFace rotateAntiClockwise(final BlockFace blockFace) {
        switch (blockFace) {
            case NORTH: {
                return BlockFace.WEST;
            }
            case SOUTH: {
                return BlockFace.EAST;
            }
            case EAST: {
                return BlockFace.NORTH;
            }
            case WEST: {
                return BlockFace.SOUTH;
            }
            default: {
                return blockFace;
            }
        }
    }
    
    private static void lambda$init$0(final List list, final StairConnectionHandler stairConnectionHandler, final WrappedBlockData wrappedBlockData) {
        final int index = list.indexOf(wrappedBlockData.getMinecraftKey());
        if (index == -1) {
            return;
        }
        if (wrappedBlockData.getValue("waterlogged").equals("true")) {
            return;
        }
        final String value = wrappedBlockData.getValue("shape");
        switch (value.hashCode()) {
            case 1787472634: {
                if (value.equals("straight")) {
                    break;
                }
                break;
            }
            case 823365712: {
                if (value.equals("inner_left")) {
                    break;
                }
                break;
            }
            case -239805709: {
                if (value.equals("inner_right")) {
                    break;
                }
                break;
            }
            case 1743932747: {
                if (value.equals("outer_left")) {
                    break;
                }
                break;
            }
            case -1766998696: {
                if (value.equals("outer_right")) {}
                break;
            }
        }
        switch (4) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            default: {
                return;
            }
        }
        final StairData stairData = new StairData(wrappedBlockData.getValue("half").equals("bottom"), (byte)4, (byte)index, BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)), null);
        StairConnectionHandler.stairDataMap.put(wrappedBlockData.getSavedBlockStateId(), stairData);
        StairConnectionHandler.connectedBlocks.put(getStates(stairData), wrappedBlockData.getSavedBlockStateId());
        ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), stairConnectionHandler);
    }
    
    static {
        stairDataMap = new HashMap();
        connectedBlocks = new HashMap();
    }
    
    private static final class StairData
    {
        private final boolean bottom;
        private final byte shape;
        private final byte type;
        private final BlockFace facing;
        
        private StairData(final boolean bottom, final byte shape, final byte type, final BlockFace facing) {
            this.bottom = bottom;
            this.shape = shape;
            this.type = type;
            this.facing = facing;
        }
        
        public boolean isBottom() {
            return this.bottom;
        }
        
        public byte getShape() {
            return this.shape;
        }
        
        public byte getType() {
            return this.type;
        }
        
        public BlockFace getFacing() {
            return this.facing;
        }
        
        StairData(final boolean b, final byte b2, final byte b3, final BlockFace blockFace, final StairConnectionHandler$1 object) {
            this(b, b2, b3, blockFace);
        }
    }
}
