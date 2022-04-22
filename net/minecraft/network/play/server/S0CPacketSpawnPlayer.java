package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0CPacketSpawnPlayer implements Packet
{
    private int field_148957_a;
    private UUID field_179820_b;
    private int field_148956_c;
    private int field_148953_d;
    private int field_148954_e;
    private byte field_148951_f;
    private byte field_148952_g;
    private int field_148959_h;
    private DataWatcher field_148960_i;
    private List field_148958_j;
    private static final String __OBFID;
    
    public S0CPacketSpawnPlayer() {
    }
    
    public S0CPacketSpawnPlayer(final EntityPlayer entityPlayer) {
        this.field_148957_a = entityPlayer.getEntityId();
        this.field_179820_b = entityPlayer.getGameProfile().getId();
        this.field_148956_c = MathHelper.floor_double(entityPlayer.posX * 32.0);
        this.field_148953_d = MathHelper.floor_double(entityPlayer.posY * 32.0);
        this.field_148954_e = MathHelper.floor_double(entityPlayer.posZ * 32.0);
        this.field_148951_f = (byte)(entityPlayer.rotationYaw * 256.0f / 360.0f);
        this.field_148952_g = (byte)(entityPlayer.rotationPitch * 256.0f / 360.0f);
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        this.field_148959_h = ((currentItem == null) ? 0 : Item.getIdFromItem(currentItem.getItem()));
        this.field_148960_i = entityPlayer.getDataWatcher();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_148957_a = packetBuffer.readVarIntFromBuffer();
        this.field_179820_b = packetBuffer.readUuid();
        this.field_148956_c = packetBuffer.readInt();
        this.field_148953_d = packetBuffer.readInt();
        this.field_148954_e = packetBuffer.readInt();
        this.field_148951_f = packetBuffer.readByte();
        this.field_148952_g = packetBuffer.readByte();
        this.field_148959_h = packetBuffer.readShort();
        this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_148957_a);
        packetBuffer.writeUuid(this.field_179820_b);
        packetBuffer.writeInt(this.field_148956_c);
        packetBuffer.writeInt(this.field_148953_d);
        packetBuffer.writeInt(this.field_148954_e);
        packetBuffer.writeByte(this.field_148951_f);
        packetBuffer.writeByte(this.field_148952_g);
        packetBuffer.writeShort(this.field_148959_h);
        this.field_148960_i.writeTo(packetBuffer);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPlayer(this);
    }
    
    public List func_148944_c() {
        if (this.field_148958_j == null) {
            this.field_148958_j = this.field_148960_i.getAllWatched();
        }
        return this.field_148958_j;
    }
    
    public int func_148943_d() {
        return this.field_148957_a;
    }
    
    public UUID func_179819_c() {
        return this.field_179820_b;
    }
    
    public int func_148942_f() {
        return this.field_148956_c;
    }
    
    public int func_148949_g() {
        return this.field_148953_d;
    }
    
    public int func_148946_h() {
        return this.field_148954_e;
    }
    
    public byte func_148941_i() {
        return this.field_148951_f;
    }
    
    public byte func_148945_j() {
        return this.field_148952_g;
    }
    
    public int func_148947_k() {
        return this.field_148959_h;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001281";
    }
}
