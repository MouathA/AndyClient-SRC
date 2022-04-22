package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.*;
import com.viaversion.viaversion.api.platform.providers.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class ConnectionData
{
    private static final BlockChangeRecord1_8[] EMPTY_RECORDS;
    public static BlockConnectionProvider blockConnectionProvider;
    static Int2ObjectMap idToKey;
    static Object2IntMap keyToId;
    static Int2ObjectMap connectionHandlerMap;
    static Int2ObjectMap blockConnectionData;
    static IntSet occludingStates;
    
    public static void update(final UserConnection userConnection, final Position position) {
        final BlockFace[] values = BlockFace.values();
        while (0 < values.length) {
            final Position relative = position.getRelative(values[0]);
            final int blockData = ConnectionData.blockConnectionProvider.getBlockData(userConnection, relative.x(), relative.y(), relative.z());
            final ConnectionHandler connectionHandler = (ConnectionHandler)ConnectionData.connectionHandlerMap.get(blockData);
            if (connectionHandler != null) {
                final int connect = connectionHandler.connect(userConnection, relative, blockData);
                final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_13.BLOCK_CHANGE, null, userConnection);
                create.write(Type.POSITION, relative);
                create.write(Type.VAR_INT, connect);
                create.send(Protocol1_13To1_12_2.class);
            }
            int n = 0;
            ++n;
        }
    }
    
    public static void updateChunkSectionNeighbours(final UserConnection userConnection, final int n, final int n2, final int n3) {
        ArrayList list;
        int n4;
        while (true) {
            if (Math.abs(-1) + Math.abs(-1) != 0) {
                list = new ArrayList();
                if (Math.abs(-1) + Math.abs(-1) == 2) {
                    for (int i = n3 * 16; i < n3 * 16 + 16; ++i) {
                        updateBlock(userConnection, new Position((n - 1 << 4) + 0, (short)i, (n2 - 1 << 4) + 16), list);
                    }
                }
                else {
                    n4 = n3 * 16;
                    if (n4 < n3 * 16 + 16) {
                        break;
                    }
                }
                if (!list.isEmpty()) {
                    final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_13.MULTI_BLOCK_CHANGE, null, userConnection);
                    create.write(Type.INT, n - 1);
                    create.write(Type.INT, n2 - 1);
                    create.write(Type.BLOCK_CHANGE_RECORD_ARRAY, list.toArray(ConnectionData.EMPTY_RECORDS));
                    create.send(Protocol1_13To1_12_2.class);
                }
            }
            int n5 = 0;
            ++n5;
        }
        while (true) {
            updateBlock(userConnection, new Position((n - 1 << 4) + 0, (short)n4, (n2 - 1 << 4) + 14), list);
            int n6 = 0;
            ++n6;
        }
    }
    
    public static void updateBlock(final UserConnection userConnection, final Position position, final List list) {
        final int blockData = ConnectionData.blockConnectionProvider.getBlockData(userConnection, position.x(), position.y(), position.z());
        final ConnectionHandler connectionHandler = getConnectionHandler(blockData);
        if (connectionHandler == null) {
            return;
        }
        list.add(new BlockChangeRecord1_8(position.x() & 0xF, position.y(), position.z() & 0xF, connectionHandler.connect(userConnection, position, blockData)));
    }
    
    public static void updateBlockStorage(final UserConnection userConnection, final int n, final int n2, final int n3, final int n4) {
        if (!needStoreBlocks()) {
            return;
        }
        if (n4 == 0) {
            ConnectionData.blockConnectionProvider.storeBlock(userConnection, n, n2, n3, n4);
        }
        else {
            ConnectionData.blockConnectionProvider.removeBlock(userConnection, n, n2, n3);
        }
    }
    
    public static void clearBlockStorage(final UserConnection userConnection) {
        if (!needStoreBlocks()) {
            return;
        }
        ConnectionData.blockConnectionProvider.clearStorage(userConnection);
    }
    
    public static boolean needStoreBlocks() {
        return ConnectionData.blockConnectionProvider.storesBlocks();
    }
    
    public static void connectBlocks(final UserConnection userConnection, final Chunk chunk) {
        final long n = chunk.getX() << 4;
        final long n2 = chunk.getZ() << 4;
        while (0 < chunk.getSections().length) {
            final ChunkSection chunkSection = chunk.getSections()[0];
            if (chunkSection == null) {
                int n3 = 0;
                ++n3;
            }
            else {
                while (0 < chunkSection.getPaletteSize() && !connects(chunkSection.getPaletteEntry(0))) {
                    int n4 = 0;
                    ++n4;
                }
                final long n5 = 0;
                while (true) {
                    final int flatBlock = chunkSection.getFlatBlock(0, 0, 0);
                    final ConnectionHandler connectionHandler = getConnectionHandler(flatBlock);
                    if (connectionHandler != null) {
                        chunkSection.setFlatBlock(0, 0, 0, connectionHandler.connect(userConnection, new Position((int)(n + 0), (short)(n5 + 0), (int)(n2 + 0)), flatBlock));
                    }
                    int n6 = 0;
                    ++n6;
                }
            }
        }
    }
    
    public static void init() {
        if (!Via.getConfig().isServersideBlockConnections()) {
            return;
        }
        Via.getPlatform().getLogger().info("Loading block connection mappings ...");
        for (final Map.Entry<String, V> entry : MappingDataLoader.loadData("mapping-1.13.json", true).getAsJsonObject("blockstates").entrySet()) {
            final int int1 = Integer.parseInt(entry.getKey());
            final String asString = ((JsonElement)entry.getValue()).getAsString();
            ConnectionData.idToKey.put(int1, asString);
            ConnectionData.keyToId.put(asString, int1);
        }
        ConnectionData.connectionHandlerMap = new Int2ObjectOpenHashMap(3650, 0.99f);
        if (!Via.getConfig().isReduceBlockStorageMemory()) {
            ConnectionData.blockConnectionData = new Int2ObjectOpenHashMap(1146, 0.99f);
            for (final Map.Entry<Object, V> entry2 : MappingDataLoader.loadData("blockConnections.json").entrySet()) {
                final int intValue = ConnectionData.keyToId.get(entry2.getKey());
                final BlockData blockData = new BlockData();
                for (final Map.Entry<String, V> entry3 : ((JsonElement)entry2.getValue()).getAsJsonObject().entrySet()) {
                    final String s = entry3.getKey();
                    final JsonObject asJsonObject = ((JsonElement)entry3.getValue()).getAsJsonObject();
                    final boolean[] array = new boolean[6];
                    final BlockFace[] values = BlockFace.values();
                    while (0 < values.length) {
                        final BlockFace blockFace = values[0];
                        final String lowerCase = blockFace.toString().toLowerCase(Locale.ROOT);
                        if (asJsonObject.has(lowerCase)) {
                            array[blockFace.ordinal()] = asJsonObject.getAsJsonPrimitive(lowerCase).getAsBoolean();
                        }
                        int n = 0;
                        ++n;
                    }
                    blockData.put(s, array);
                }
                if (entry2.getKey().contains("stairs")) {
                    blockData.put("allFalseIfStairPre1_12", new boolean[6]);
                }
                ConnectionData.blockConnectionData.put(intValue, blockData);
            }
        }
        final Iterator iterator4 = MappingDataLoader.loadData("blockData.json").getAsJsonArray("occluding").iterator();
        while (iterator4.hasNext()) {
            ConnectionData.occludingStates.add((int)ConnectionData.keyToId.get((Object)iterator4.next().getAsString()));
        }
        final ArrayList<ConnectorInitAction> list = new ArrayList<ConnectorInitAction>();
        list.add(PumpkinConnectionHandler.init());
        list.addAll((Collection<?>)BasicFenceConnectionHandler.init());
        list.add(NetherFenceConnectionHandler.init());
        list.addAll((Collection<?>)WallConnectionHandler.init());
        list.add(MelonConnectionHandler.init());
        list.addAll((Collection<?>)GlassConnectionHandler.init());
        list.add(ChestConnectionHandler.init());
        list.add(DoorConnectionHandler.init());
        list.add(RedstoneConnectionHandler.init());
        list.add(StairConnectionHandler.init());
        list.add(FlowerConnectionHandler.init());
        list.addAll((Collection<?>)ChorusPlantConnectionHandler.init());
        list.add(TripwireConnectionHandler.init());
        list.add(SnowyGrassConnectionHandler.init());
        list.add(FireConnectionHandler.init());
        if (Via.getConfig().isVineClimbFix()) {
            list.add(VineConnectionHandler.init());
        }
        final ObjectIterator iterator5 = ConnectionData.keyToId.keySet().iterator();
        while (iterator5.hasNext()) {
            final WrappedBlockData fromString = WrappedBlockData.fromString(iterator5.next());
            final Iterator<Object> iterator6 = list.iterator();
            while (iterator6.hasNext()) {
                iterator6.next().check(fromString);
            }
        }
        if (Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("packet")) {
            ConnectionData.blockConnectionProvider = new PacketBlockConnectionProvider();
            Via.getManager().getProviders().register(BlockConnectionProvider.class, ConnectionData.blockConnectionProvider);
        }
    }
    
    public static boolean connects(final int n) {
        return ConnectionData.connectionHandlerMap.containsKey(n);
    }
    
    public static int connect(final UserConnection userConnection, final Position position, final int n) {
        final ConnectionHandler connectionHandler = (ConnectionHandler)ConnectionData.connectionHandlerMap.get(n);
        return (connectionHandler != null) ? connectionHandler.connect(userConnection, position, n) : n;
    }
    
    public static ConnectionHandler getConnectionHandler(final int n) {
        return (ConnectionHandler)ConnectionData.connectionHandlerMap.get(n);
    }
    
    public static int getId(final String s) {
        return ConnectionData.keyToId.getOrDefault(s, -1);
    }
    
    public static String getKey(final int n) {
        return (String)ConnectionData.idToKey.get(n);
    }
    
    static {
        EMPTY_RECORDS = new BlockChangeRecord1_8[0];
        ConnectionData.idToKey = new Int2ObjectOpenHashMap(8582, 0.99f);
        ConnectionData.keyToId = new Object2IntOpenHashMap(8582, 0.99f);
        ConnectionData.connectionHandlerMap = new Int2ObjectOpenHashMap(1);
        ConnectionData.blockConnectionData = new Int2ObjectOpenHashMap(1);
        ConnectionData.occludingStates = new IntOpenHashSet(377, 0.99f);
    }
    
    @FunctionalInterface
    interface ConnectorInitAction
    {
        void check(final WrappedBlockData p0);
    }
}
