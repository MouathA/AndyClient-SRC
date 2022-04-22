package wdl.chan;

import org.apache.logging.log4j.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import io.netty.buffer.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import wdl.*;
import wdl.api.*;
import com.google.common.collect.*;
import com.google.common.io.*;

public class WDLPluginChannels
{
    private static Logger logger;
    private static HashSet receivedPackets;
    private static boolean canUseFunctionsUnknownToServer;
    private static boolean canDownloadInGeneral;
    private static boolean canCacheChunks;
    private static boolean canSaveEntities;
    private static boolean canSaveTileEntities;
    private static boolean canSaveContainers;
    private static Map entityRanges;
    private static boolean canRequestPermissions;
    private static String requestMessage;
    private static Map chunkOverrides;
    private static Map requests;
    public static final List BOOLEAN_REQUEST_FIELDS;
    public static final List INTEGER_REQUEST_FIELDS;
    private static List chunkOverrideRequests;
    private static Set registeredChannels;
    
    static {
        WDLPluginChannels.logger = LogManager.getLogger();
        WDLPluginChannels.receivedPackets = new HashSet();
        WDLPluginChannels.canUseFunctionsUnknownToServer = true;
        WDLPluginChannels.canDownloadInGeneral = true;
        WDLPluginChannels.canCacheChunks = true;
        WDLPluginChannels.canSaveEntities = true;
        WDLPluginChannels.canSaveTileEntities = true;
        WDLPluginChannels.canSaveContainers = true;
        WDLPluginChannels.entityRanges = new HashMap();
        WDLPluginChannels.canRequestPermissions = false;
        WDLPluginChannels.requestMessage = "";
        WDLPluginChannels.chunkOverrides = new HashMap();
        WDLPluginChannels.requests = new HashMap();
        BOOLEAN_REQUEST_FIELDS = Arrays.asList("downloadInGeneral", "cacheChunks", "saveEntities", "saveTileEntities", "saveContainers", "getEntityRanges");
        INTEGER_REQUEST_FIELDS = Arrays.asList("saveRadius");
        WDLPluginChannels.chunkOverrideRequests = new ArrayList();
        WDLPluginChannels.registeredChannels = new HashSet();
    }
    
    public static boolean canUseFunctionsUnknownToServer() {
        return !WDLPluginChannels.receivedPackets.contains(0) || WDLPluginChannels.canUseFunctionsUnknownToServer;
    }
    
    public static boolean canDownloadAtAll() {
        return hasChunkOverrides() || canDownloadInGeneral();
    }
    
    public static boolean canDownloadInGeneral() {
        if (WDLPluginChannels.receivedPackets.contains(1)) {
            return WDLPluginChannels.canDownloadInGeneral;
        }
        return canUseFunctionsUnknownToServer();
    }
    
    public static boolean canSaveChunk(final Chunk chunk) {
        if (isChunkOverridden(chunk)) {
            return true;
        }
        if (!canDownloadInGeneral()) {
            return false;
        }
        if (WDLPluginChannels.receivedPackets.contains(1)) {
            if (!WDLPluginChannels.canCacheChunks && -1 >= 0) {
                final int n = chunk.xPosition - WDL.thePlayer.chunkCoordX;
                final int n2 = chunk.zPosition - WDL.thePlayer.chunkCoordZ;
                if (Math.abs(n) > -1 || Math.abs(n2) > -1) {
                    return false;
                }
            }
            return true;
        }
        return canUseFunctionsUnknownToServer();
    }
    
    public static boolean canSaveEntities() {
        if (!canDownloadInGeneral()) {
            return false;
        }
        if (WDLPluginChannels.receivedPackets.contains(1)) {
            return WDLPluginChannels.canSaveEntities;
        }
        return canUseFunctionsUnknownToServer();
    }
    
    public static boolean canSaveEntities(final Chunk chunk) {
        return isChunkOverridden(chunk) || canSaveEntities();
    }
    
    public static boolean canSaveEntities(final int n, final int n2) {
        return isChunkOverridden(n, n2) || canSaveEntities();
    }
    
    public static boolean canSaveTileEntities() {
        if (!canDownloadInGeneral()) {
            return false;
        }
        if (WDLPluginChannels.receivedPackets.contains(1)) {
            return WDLPluginChannels.canSaveTileEntities;
        }
        return canUseFunctionsUnknownToServer();
    }
    
    public static boolean canSaveTileEntities(final Chunk chunk) {
        return isChunkOverridden(chunk) || canSaveTileEntities();
    }
    
    public static boolean canSaveTileEntities(final int n, final int n2) {
        return isChunkOverridden(n, n2) || canSaveTileEntities();
    }
    
    public static boolean canSaveContainers() {
        if (!canDownloadInGeneral()) {
            return false;
        }
        if (!canSaveTileEntities()) {
            return false;
        }
        if (WDLPluginChannels.receivedPackets.contains(1)) {
            return WDLPluginChannels.canSaveContainers;
        }
        return canUseFunctionsUnknownToServer();
    }
    
    public static boolean canSaveContainers(final Chunk chunk) {
        return isChunkOverridden(chunk) || canSaveContainers();
    }
    
    public static boolean canSaveContainers(final int n, final int n2) {
        return isChunkOverridden(n, n2) || canSaveContainers();
    }
    
    public static boolean canSaveMaps() {
        if (!canDownloadInGeneral()) {
            return false;
        }
        if (WDLPluginChannels.receivedPackets.contains(1)) {
            return WDLPluginChannels.canSaveTileEntities;
        }
        return canUseFunctionsUnknownToServer();
    }
    
    public static int getEntityRange(final String s) {
        if (!canSaveEntities(null)) {
            return -1;
        }
        if (!WDLPluginChannels.receivedPackets.contains(2)) {
            return -1;
        }
        if (WDLPluginChannels.entityRanges.containsKey(s)) {
            return WDLPluginChannels.entityRanges.get(s);
        }
        return -1;
    }
    
    public static int getSaveRadius() {
        return -1;
    }
    
    public static boolean canCacheChunks() {
        return WDLPluginChannels.canCacheChunks;
    }
    
    public static boolean hasServerEntityRange() {
        return WDLPluginChannels.receivedPackets.contains(2) && WDLPluginChannels.entityRanges.size() > 0;
    }
    
    public static Map getEntityRanges() {
        return new HashMap(WDLPluginChannels.entityRanges);
    }
    
    public static boolean hasPermissions() {
        return WDLPluginChannels.receivedPackets != null && !WDLPluginChannels.receivedPackets.isEmpty();
    }
    
    public static boolean canRequestPermissions() {
        return WDLPluginChannels.registeredChannels.contains("WDL|REQUEST") && WDLPluginChannels.receivedPackets.contains(3) && WDLPluginChannels.canRequestPermissions;
    }
    
    public static String getRequestMessage() {
        if (WDLPluginChannels.receivedPackets.contains(3)) {
            return WDLPluginChannels.requestMessage;
        }
        return null;
    }
    
    public static boolean isChunkOverridden(final Chunk chunk) {
        return chunk != null && isChunkOverridden(chunk.xPosition, chunk.zPosition);
    }
    
    public static boolean isChunkOverridden(final int n, final int n2) {
        final Iterator<Multimap> iterator = WDLPluginChannels.chunkOverrides.values().iterator();
        while (iterator.hasNext()) {
            for (final ChunkRange chunkRange : iterator.next().values()) {
                if (n >= chunkRange.x1 && n <= chunkRange.x2 && n2 >= chunkRange.z1 && n2 <= chunkRange.z2) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean hasChunkOverrides() {
        if (!WDLPluginChannels.receivedPackets.contains(4)) {
            return false;
        }
        if (WDLPluginChannels.chunkOverrides == null || WDLPluginChannels.chunkOverrides.isEmpty()) {
            return false;
        }
        final Iterator<Multimap> iterator = WDLPluginChannels.chunkOverrides.values().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public static Map getChunkOverrides() {
        final HashMap<String, ImmutableMultimap> hashMap = new HashMap<String, ImmutableMultimap>();
        for (final Map.Entry<K, Multimap> entry : WDLPluginChannels.chunkOverrides.entrySet()) {
            hashMap.put((String)entry.getKey(), ImmutableMultimap.copyOf(entry.getValue()));
        }
        return ImmutableMap.copyOf(hashMap);
    }
    
    public static void addRequest(final String s, final String s2) {
        if (!isValidRequest(s, s2)) {
            return;
        }
        WDLPluginChannels.requests.put(s, s2);
    }
    
    public static void removeRequest(final String s) {
        WDLPluginChannels.requests.remove(s);
    }
    
    public static String getRequest(final String s) {
        return WDLPluginChannels.requests.get(s);
    }
    
    public static Map getRequests() {
        return ImmutableMap.copyOf(WDLPluginChannels.requests);
    }
    
    public static boolean isValidRequest(final String s, final String s2) {
        if (s == null || s2 == null) {
            return false;
        }
        if (WDLPluginChannels.BOOLEAN_REQUEST_FIELDS.contains(s)) {
            return s2.equals("true") || s2.equals("false");
        }
        if (WDLPluginChannels.INTEGER_REQUEST_FIELDS.contains(s)) {
            Integer.parseInt(s2);
            return true;
        }
        return false;
    }
    
    public static List getChunkOverrideRequests() {
        return ImmutableList.copyOf(WDLPluginChannels.chunkOverrideRequests);
    }
    
    public static void addChunkOverrideRequest(final ChunkRange chunkRange) {
        WDLPluginChannels.chunkOverrideRequests.add(chunkRange);
    }
    
    public static void sendRequests(final String s) {
        if (WDLPluginChannels.requests.isEmpty() && WDLPluginChannels.chunkOverrideRequests.isEmpty()) {
            return;
        }
        final ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF(s);
        dataOutput.writeInt(WDLPluginChannels.requests.size());
        for (final Map.Entry<String, V> entry : WDLPluginChannels.requests.entrySet()) {
            dataOutput.writeUTF(entry.getKey());
            dataOutput.writeUTF((String)entry.getValue());
        }
        dataOutput.writeInt(WDLPluginChannels.chunkOverrideRequests.size());
        final Iterator<ChunkRange> iterator2 = WDLPluginChannels.chunkOverrideRequests.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().writeToOutput(dataOutput);
        }
        final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeBytes(dataOutput.toByteArray());
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C17PacketCustomPayload("WDL|REQUEST", packetBuffer));
    }
    
    public static void onWorldLoad() {
        final Minecraft instance = Minecraft.getInstance();
        WDLPluginChannels.receivedPackets = new HashSet();
        WDLPluginChannels.requests = new HashMap();
        WDLPluginChannels.chunkOverrideRequests = new ArrayList();
        WDLPluginChannels.canUseFunctionsUnknownToServer = true;
        WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.init", new Object[0]);
        final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeBytes(new byte[] { 87, 68, 76, 124, 73, 78, 73, 84, 0, 87, 68, 76, 124, 67, 79, 78, 84, 82, 79, 76, 0, 87, 68, 76, 124, 82, 69, 81, 85, 69, 83, 84, 0 });
        instance.getNetHandler().addToSendQueue(new C17PacketCustomPayload("REGISTER", packetBuffer));
        final C17PacketCustomPayload c17PacketCustomPayload = new C17PacketCustomPayload("WDL|INIT", new PacketBuffer(Unpooled.copiedBuffer("1.8.9a-beta2".getBytes("UTF-8"))));
    }
    
    public static void onPluginChannelPacket(final String s, final byte[] array) {
        if ("WDL|CONTROL".equals(s)) {
            final ByteArrayDataInput dataInput = ByteStreams.newDataInput(array);
            final int int1 = dataInput.readInt();
            WDLPluginChannels.receivedPackets.add(int1);
            switch (int1) {
                case 0: {
                    WDLPluginChannels.canUseFunctionsUnknownToServer = dataInput.readBoolean();
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet0", WDLPluginChannels.canUseFunctionsUnknownToServer);
                    break;
                }
                case 1: {
                    WDLPluginChannels.canDownloadInGeneral = dataInput.readBoolean();
                    WDLPluginChannels.saveRadius = dataInput.readInt();
                    WDLPluginChannels.canCacheChunks = dataInput.readBoolean();
                    WDLPluginChannels.canSaveEntities = dataInput.readBoolean();
                    WDLPluginChannels.canSaveTileEntities = dataInput.readBoolean();
                    WDLPluginChannels.canSaveContainers = dataInput.readBoolean();
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet1", WDLPluginChannels.canDownloadInGeneral, -1, WDLPluginChannels.canCacheChunks, WDLPluginChannels.canSaveEntities, WDLPluginChannels.canSaveTileEntities, WDLPluginChannels.canSaveContainers);
                    if (!WDLPluginChannels.canDownloadInGeneral && WDL.downloading) {
                        WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, "wdl.messages.generalError.forbidden", new Object[0]);
                        break;
                    }
                    break;
                }
                case 2: {
                    WDLPluginChannels.entityRanges.clear();
                    while (0 < dataInput.readInt()) {
                        final String utf = dataInput.readUTF();
                        final int int2 = dataInput.readInt();
                        WDLPluginChannels.entityRanges.put(utf, 0);
                        int int3 = 0;
                        ++int3;
                    }
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet2", WDLPluginChannels.entityRanges.size());
                    break;
                }
                case 3: {
                    WDLPluginChannels.canRequestPermissions = dataInput.readBoolean();
                    WDLPluginChannels.requestMessage = dataInput.readUTF();
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet3", WDLPluginChannels.canRequestPermissions, WDLPluginChannels.requestMessage.length(), Integer.toHexString(WDLPluginChannels.requestMessage.hashCode()));
                    break;
                }
                case 4: {
                    WDLPluginChannels.chunkOverrides.clear();
                    final int int3 = dataInput.readInt();
                    while (0 < 0) {
                        final String utf2 = dataInput.readUTF();
                        final int int4 = dataInput.readInt();
                        final HashMultimap create = HashMultimap.create();
                        while (0 < int4) {
                            final ChunkRange fromInput = ChunkRange.readFromInput(dataInput);
                            create.put(fromInput.tag, fromInput);
                            int n = 0;
                            ++n;
                        }
                        WDLPluginChannels.chunkOverrides.put(utf2, create);
                        int int2 = 0;
                        ++int2;
                    }
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet4", 0, 0);
                    break;
                }
                case 5: {
                    final String utf3 = dataInput.readUTF();
                    final boolean boolean1 = dataInput.readBoolean();
                    final int int5 = dataInput.readInt();
                    final HashMultimap create2 = HashMultimap.create();
                    if (!boolean1) {
                        create2.putAll(WDLPluginChannels.chunkOverrides.get(utf3));
                    }
                    while (0 < int5) {
                        final ChunkRange fromInput2 = ChunkRange.readFromInput(dataInput);
                        create2.put(fromInput2.tag, fromInput2);
                        int n = 0;
                        ++n;
                    }
                    WDLPluginChannels.chunkOverrides.put(utf3, create2);
                    if (boolean1) {
                        WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet5.set", int5, utf3);
                        break;
                    }
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet5.added", int5, utf3);
                    break;
                }
                case 6: {
                    final String utf4 = dataInput.readUTF();
                    final int int6 = dataInput.readInt();
                    final String[] array2 = new String[int6];
                    while (0 < int6) {
                        array2[0] = dataInput.readUTF();
                        int n2 = 0;
                        ++n2;
                    }
                    String[] array3;
                    while (0 < (array3 = array2).length) {
                        final String s2 = array3[0];
                        final int n2 = 0 + WDLPluginChannels.chunkOverrides.get(utf4).get(s2).size();
                        WDLPluginChannels.chunkOverrides.get(utf4).removeAll(s2);
                        int n3 = 0;
                        ++n3;
                    }
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet6", 0, utf4, Arrays.toString(array2));
                    break;
                }
                case 7: {
                    final String utf5 = dataInput.readUTF();
                    final String utf6 = dataInput.readUTF();
                    final int int7 = dataInput.readInt();
                    final int size = WDLPluginChannels.chunkOverrides.get(utf5).removeAll(utf6).size();
                    while (0 < int7) {
                        WDLPluginChannels.chunkOverrides.get(utf5).put(utf6, ChunkRange.readFromInput(dataInput));
                        int n4 = 0;
                        ++n4;
                    }
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.packet7", size, utf5, utf6, int7);
                    break;
                }
                default: {
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, "wdl.messages.permissions.unknownPacket", int1);
                    final StringBuilder sb = new StringBuilder();
                    while (0 < array.length) {
                        sb.append(array[0]).append(' ');
                        int n5 = 0;
                        ++n5;
                    }
                    WDLPluginChannels.logger.info(sb.toString());
                    break;
                }
            }
        }
        else if ("REGISTER".equals(s)) {
            WDLPluginChannels.registeredChannels.addAll(Arrays.asList(new String(array, "UTF-8").split("\u0000")));
        }
        else if ("UNREGISTER".equals(s)) {
            WDLPluginChannels.registeredChannels.removeAll(Arrays.asList(new String(array, "UTF-8").split("\u0000")));
        }
    }
    
    public static class ChunkRange
    {
        public final String tag;
        public final int x1;
        public final int z1;
        public final int x2;
        public final int z2;
        
        public ChunkRange(final String tag, final int n, final int n2, final int n3, final int n4) {
            this.tag = tag;
            if (n > n3) {
                this.x1 = n3;
                this.x2 = n;
            }
            else {
                this.x1 = n;
                this.x2 = n3;
            }
            if (n2 > n4) {
                this.z1 = n4;
                this.z2 = n2;
            }
            else {
                this.z1 = n2;
                this.z2 = n4;
            }
        }
        
        public static ChunkRange readFromInput(final ByteArrayDataInput byteArrayDataInput) {
            return new ChunkRange(byteArrayDataInput.readUTF(), byteArrayDataInput.readInt(), byteArrayDataInput.readInt(), byteArrayDataInput.readInt(), byteArrayDataInput.readInt());
        }
        
        public void writeToOutput(final ByteArrayDataOutput byteArrayDataOutput) {
            byteArrayDataOutput.writeUTF(this.tag);
            byteArrayDataOutput.writeInt(this.x1);
            byteArrayDataOutput.writeInt(this.z1);
            byteArrayDataOutput.writeInt(this.x2);
            byteArrayDataOutput.writeInt(this.z2);
        }
        
        @Override
        public String toString() {
            return "ChunkRange [tag=" + this.tag + ", x1=" + this.x1 + ", z1=" + this.z1 + ", x2=" + this.x2 + ", z2=" + this.z2 + "]";
        }
    }
}
