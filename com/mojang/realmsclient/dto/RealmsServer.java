package com.mojang.realmsclient.dto;

import net.minecraft.realms.*;
import com.mojang.realmsclient.util.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;
import org.apache.commons.lang3.builder.*;
import org.apache.logging.log4j.*;

public class RealmsServer extends ValueObject
{
    private static final Logger LOGGER;
    public long id;
    public String remoteSubscriptionId;
    public String name;
    public String motd;
    public State state;
    public String owner;
    public String ownerUUID;
    public List players;
    public Map slots;
    public String ip;
    public boolean expired;
    public int daysLeft;
    public WorldType worldType;
    public int activeSlot;
    public String minigameName;
    public int minigameId;
    public int protocol;
    public String status;
    public String minigameImage;
    public String resourcePackUrl;
    public String resourcePackHash;
    public RealmsServerPing serverPing;
    
    public RealmsServer() {
        this.status = "";
        this.serverPing = new RealmsServerPing();
    }
    
    public String getDescription() {
        return this.motd;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getMinigameName() {
        return this.minigameName;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setDescription(final String motd) {
        this.motd = motd;
    }
    
    public void latestStatFrom(final RealmsServer realmsServer) {
        this.status = realmsServer.status;
        this.protocol = realmsServer.protocol;
        this.serverPing.nrOfPlayers = realmsServer.serverPing.nrOfPlayers;
        this.serverPing.lastPingSnapshot = realmsServer.serverPing.lastPingSnapshot;
        this.serverPing.playerList = realmsServer.serverPing.playerList;
    }
    
    public static RealmsServer parse(final JsonObject jsonObject) {
        final RealmsServer realmsServer = new RealmsServer();
        realmsServer.id = JsonUtils.getLongOr("id", jsonObject, -1L);
        realmsServer.remoteSubscriptionId = JsonUtils.getStringOr("remoteSubscriptionId", jsonObject, null);
        realmsServer.name = JsonUtils.getStringOr("name", jsonObject, null);
        realmsServer.motd = JsonUtils.getStringOr("motd", jsonObject, null);
        realmsServer.state = getState(JsonUtils.getStringOr("state", jsonObject, State.CLOSED.name()));
        realmsServer.owner = JsonUtils.getStringOr("owner", jsonObject, null);
        if (jsonObject.get("players") != null && jsonObject.get("players").isJsonArray()) {
            realmsServer.players = parseInvited(jsonObject.get("players").getAsJsonArray());
            sortInvited(realmsServer);
        }
        else {
            realmsServer.players = new ArrayList();
        }
        realmsServer.daysLeft = JsonUtils.getIntOr("daysLeft", jsonObject, 0);
        realmsServer.ip = JsonUtils.getStringOr("ip", jsonObject, null);
        realmsServer.expired = JsonUtils.getBooleanOr("expired", jsonObject, false);
        realmsServer.worldType = getWorldType(JsonUtils.getStringOr("worldType", jsonObject, WorldType.NORMAL.name()));
        realmsServer.ownerUUID = JsonUtils.getStringOr("ownerUUID", jsonObject, "");
        if (jsonObject.get("slots") != null && jsonObject.get("slots").isJsonArray()) {
            realmsServer.slots = parseSlots(jsonObject.get("slots").getAsJsonArray());
        }
        else {
            realmsServer.slots = getEmptySlots();
        }
        realmsServer.minigameName = JsonUtils.getStringOr("minigameName", jsonObject, null);
        realmsServer.activeSlot = JsonUtils.getIntOr("activeSlot", jsonObject, -1);
        realmsServer.minigameId = JsonUtils.getIntOr("minigameId", jsonObject, -1);
        realmsServer.minigameImage = JsonUtils.getStringOr("minigameImage", jsonObject, null);
        realmsServer.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", jsonObject, null);
        realmsServer.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", jsonObject, null);
        return realmsServer;
    }
    
    private static void sortInvited(final RealmsServer realmsServer) {
        Collections.sort((List<Object>)realmsServer.players, new Comparator() {
            public int compare(final PlayerInfo playerInfo, final PlayerInfo playerInfo2) {
                return ComparisonChain.start().compare(playerInfo2.getAccepted(), playerInfo.getAccepted()).compare(playerInfo.getName().toLowerCase(), playerInfo2.getName().toLowerCase()).result();
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((PlayerInfo)o, (PlayerInfo)o2);
            }
        });
    }
    
    private static List parseInvited(final JsonArray jsonArray) {
        final ArrayList<PlayerInfo> list = new ArrayList<PlayerInfo>();
        final Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            final JsonObject asJsonObject = iterator.next().getAsJsonObject();
            final PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setName(JsonUtils.getStringOr("name", asJsonObject, null));
            playerInfo.setUuid(JsonUtils.getStringOr("uuid", asJsonObject, null));
            playerInfo.setOperator(JsonUtils.getBooleanOr("operator", asJsonObject, false));
            playerInfo.setAccepted(JsonUtils.getBooleanOr("accepted", asJsonObject, false));
            list.add(playerInfo);
        }
        return list;
    }
    
    private static Map parseSlots(final JsonArray p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/HashMap.<init>:()V
        //     7: astore_1       
        //     8: aload_0        
        //     9: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //    12: astore_2       
        //    13: aload_2        
        //    14: invokeinterface java/util/Iterator.hasNext:()Z
        //    19: ifeq            121
        //    22: aload_2        
        //    23: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    28: checkcast       Lcom/google/gson/JsonElement;
        //    31: astore_3       
        //    32: aload_3        
        //    33: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //    36: astore          5
        //    38: new             Lcom/google/gson/JsonParser;
        //    41: dup            
        //    42: invokespecial   com/google/gson/JsonParser.<init>:()V
        //    45: astore          6
        //    47: aload           6
        //    49: aload           5
        //    51: ldc_w           "options"
        //    54: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //    57: invokevirtual   com/google/gson/JsonElement.getAsString:()Ljava/lang/String;
        //    60: invokevirtual   com/google/gson/JsonParser.parse:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //    63: astore          7
        //    65: aload           7
        //    67: ifnonnull       78
        //    70: invokestatic    com/mojang/realmsclient/dto/RealmsOptions.getDefaults:()Lcom/mojang/realmsclient/dto/RealmsOptions;
        //    73: astore          4
        //    75: goto            88
        //    78: aload           7
        //    80: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //    83: invokestatic    com/mojang/realmsclient/dto/RealmsOptions.parse:(Lcom/google/gson/JsonObject;)Lcom/mojang/realmsclient/dto/RealmsOptions;
        //    86: astore          4
        //    88: ldc_w           "slotId"
        //    91: aload           5
        //    93: iconst_m1      
        //    94: invokestatic    com/mojang/realmsclient/util/JsonUtils.getIntOr:(Ljava/lang/String;Lcom/google/gson/JsonObject;I)I
        //    97: istore          8
        //    99: aload_1        
        //   100: iload           8
        //   102: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   105: aload           4
        //   107: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   112: pop            
        //   113: goto            118
        //   116: astore          4
        //   118: goto            13
        //   121: iconst_1       
        //   122: iconst_3       
        //   123: if_icmpgt       159
        //   126: aload_1        
        //   127: iconst_1       
        //   128: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   131: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   136: ifne            153
        //   139: aload_1        
        //   140: iconst_1       
        //   141: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   144: invokestatic    com/mojang/realmsclient/dto/RealmsOptions.getEmptyDefaults:()Lcom/mojang/realmsclient/dto/RealmsOptions;
        //   147: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   152: pop            
        //   153: iinc            2, 1
        //   156: goto            121
        //   159: aload_1        
        //   160: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static Map getEmptySlots() {
        final HashMap<Integer, RealmsOptions> hashMap = new HashMap<Integer, RealmsOptions>();
        hashMap.put(1, RealmsOptions.getEmptyDefaults());
        hashMap.put(2, RealmsOptions.getEmptyDefaults());
        hashMap.put(3, RealmsOptions.getEmptyDefaults());
        return hashMap;
    }
    
    public static RealmsServer parse(final String s) {
        final RealmsServer realmsServer = new RealmsServer();
        return parse(new JsonParser().parse(s).getAsJsonObject());
    }
    
    private static State getState(final String s) {
        return State.valueOf(s);
    }
    
    private static WorldType getWorldType(final String s) {
        return WorldType.valueOf(s);
    }
    
    public boolean shouldPing(final long n) {
        return n - this.serverPing.lastPingSnapshot >= 6000L;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.id).append(this.name).append(this.motd).append(this.state).append(this.owner).append(this.expired).toHashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        final RealmsServer realmsServer = (RealmsServer)o;
        return new EqualsBuilder().append(this.id, realmsServer.id).append(this.name, realmsServer.name).append(this.motd, realmsServer.motd).append(this.state, realmsServer.state).append(this.owner, realmsServer.owner).append(this.expired, realmsServer.expired).append(this.worldType, this.worldType).isEquals();
    }
    
    public RealmsServer clone() {
        final RealmsServer realmsServer = new RealmsServer();
        realmsServer.id = this.id;
        realmsServer.remoteSubscriptionId = this.remoteSubscriptionId;
        realmsServer.name = this.name;
        realmsServer.motd = this.motd;
        realmsServer.state = this.state;
        realmsServer.owner = this.owner;
        realmsServer.players = this.players;
        realmsServer.slots = this.cloneSlots(this.slots);
        realmsServer.ip = this.ip;
        realmsServer.expired = this.expired;
        realmsServer.daysLeft = this.daysLeft;
        realmsServer.protocol = this.protocol;
        realmsServer.status = this.status;
        realmsServer.serverPing = new RealmsServerPing();
        realmsServer.serverPing.nrOfPlayers = this.serverPing.nrOfPlayers;
        realmsServer.serverPing.lastPingSnapshot = this.serverPing.lastPingSnapshot;
        realmsServer.serverPing.playerList = this.serverPing.playerList;
        realmsServer.worldType = this.worldType;
        realmsServer.ownerUUID = this.ownerUUID;
        realmsServer.minigameName = this.minigameName;
        realmsServer.activeSlot = this.activeSlot;
        realmsServer.minigameId = this.minigameId;
        realmsServer.minigameImage = this.minigameImage;
        realmsServer.resourcePackUrl = this.resourcePackUrl;
        realmsServer.resourcePackHash = this.resourcePackHash;
        return realmsServer;
    }
    
    public Map cloneSlots(final Map map) {
        final HashMap<Object, RealmsOptions> hashMap = new HashMap<Object, RealmsOptions>();
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), ((RealmsOptions)entry.getValue()).clone());
        }
        return hashMap;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public enum WorldType
    {
        NORMAL("NORMAL", 0), 
        MINIGAME("MINIGAME", 1), 
        ADVENTUREMAP("ADVENTUREMAP", 2);
        
        private static final WorldType[] $VALUES;
        
        private WorldType(final String s, final int n) {
        }
        
        static {
            $VALUES = new WorldType[] { WorldType.NORMAL, WorldType.MINIGAME, WorldType.ADVENTUREMAP };
        }
    }
    
    public enum State
    {
        CLOSED("CLOSED", 0), 
        OPEN("OPEN", 1), 
        ADMIN_LOCK("ADMIN_LOCK", 2), 
        UNINITIALIZED("UNINITIALIZED", 3);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.CLOSED, State.OPEN, State.ADMIN_LOCK, State.UNINITIALIZED };
        }
    }
    
    public static class McoServerComparator implements Comparator
    {
        private final String refOwner;
        
        public McoServerComparator(final String refOwner) {
            this.refOwner = refOwner;
        }
        
        public int compare(final RealmsServer realmsServer, final RealmsServer realmsServer2) {
            return ComparisonChain.start().compareTrueFirst(realmsServer.state.equals(State.UNINITIALIZED), realmsServer2.state.equals(State.UNINITIALIZED)).compareFalseFirst(realmsServer.expired, realmsServer2.expired).compareTrueFirst(realmsServer.owner.equals(this.refOwner), realmsServer2.owner.equals(this.refOwner)).compareTrueFirst(realmsServer.state.equals(State.OPEN), realmsServer2.state.equals(State.OPEN)).compare(realmsServer.id, realmsServer2.id).result();
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((RealmsServer)o, (RealmsServer)o2);
        }
    }
}
