package net.minecraft.network.status.server;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;
import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class S00PacketServerInfo implements Packet
{
    private static final Gson GSON;
    private ServerStatusResponse response;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001384";
        GSON = new GsonBuilder().registerTypeAdapter(ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter(ServerStatusResponse.PlayerCountData.class, new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
    }
    
    public S00PacketServerInfo() {
    }
    
    public S00PacketServerInfo(final ServerStatusResponse response) {
        this.response = response;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.response = (ServerStatusResponse)S00PacketServerInfo.GSON.fromJson(packetBuffer.readStringFromBuffer(32767), ServerStatusResponse.class);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(S00PacketServerInfo.GSON.toJson(this.response));
    }
    
    public void processPacket(final INetHandlerStatusClient netHandlerStatusClient) {
        netHandlerStatusClient.handleServerInfo(this);
    }
    
    public ServerStatusResponse func_149294_c() {
        return this.response;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerStatusClient)netHandler);
    }
    
    public ServerStatusResponse getResponse() {
        return this.response;
    }
}
