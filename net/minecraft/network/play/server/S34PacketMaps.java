package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.storage.*;
import net.minecraft.network.*;

public class S34PacketMaps implements Packet
{
    private int mapId;
    private byte field_179739_b;
    private Vec4b[] field_179740_c;
    private int field_179737_d;
    private int field_179738_e;
    private int field_179735_f;
    private int field_179736_g;
    private byte[] field_179741_h;
    private static final String __OBFID;
    
    public S34PacketMaps() {
    }
    
    public S34PacketMaps(final int mapId, final byte field_179739_b, final Collection collection, final byte[] array, final int field_179737_d, final int field_179738_e, final int field_179735_f, final int field_179736_g) {
        this.mapId = mapId;
        this.field_179739_b = field_179739_b;
        this.field_179740_c = collection.toArray(new Vec4b[collection.size()]);
        this.field_179737_d = field_179737_d;
        this.field_179738_e = field_179738_e;
        this.field_179735_f = field_179735_f;
        this.field_179736_g = field_179736_g;
        this.field_179741_h = new byte[field_179735_f * field_179736_g];
        while (0 < field_179735_f) {
            while (0 < field_179736_g) {
                this.field_179741_h[0 + 0 * field_179735_f] = array[field_179737_d + 0 + (field_179738_e + 0) * 128];
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.mapId = packetBuffer.readVarIntFromBuffer();
        this.field_179739_b = packetBuffer.readByte();
        this.field_179740_c = new Vec4b[packetBuffer.readVarIntFromBuffer()];
        while (0 < this.field_179740_c.length) {
            final short n = packetBuffer.readByte();
            this.field_179740_c[0] = new Vec4b((byte)(n >> 4 & 0xF), packetBuffer.readByte(), packetBuffer.readByte(), (byte)(n & 0xF));
            int n2 = 0;
            ++n2;
        }
        this.field_179735_f = packetBuffer.readUnsignedByte();
        if (this.field_179735_f > 0) {
            this.field_179736_g = packetBuffer.readUnsignedByte();
            this.field_179737_d = packetBuffer.readUnsignedByte();
            this.field_179738_e = packetBuffer.readUnsignedByte();
            this.field_179741_h = packetBuffer.readByteArray();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.mapId);
        packetBuffer.writeByte(this.field_179739_b);
        packetBuffer.writeVarIntToBuffer(this.field_179740_c.length);
        final Vec4b[] field_179740_c = this.field_179740_c;
        while (0 < field_179740_c.length) {
            final Vec4b vec4b = field_179740_c[0];
            packetBuffer.writeByte((vec4b.func_176110_a() & 0xF) << 4 | (vec4b.func_176111_d() & 0xF));
            packetBuffer.writeByte(vec4b.func_176112_b());
            packetBuffer.writeByte(vec4b.func_176113_c());
            int n = 0;
            ++n;
        }
        packetBuffer.writeByte(this.field_179735_f);
        if (this.field_179735_f > 0) {
            packetBuffer.writeByte(this.field_179736_g);
            packetBuffer.writeByte(this.field_179737_d);
            packetBuffer.writeByte(this.field_179738_e);
            packetBuffer.writeByteArray(this.field_179741_h);
        }
    }
    
    public void func_180741_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMaps(this);
    }
    
    public int getMapId() {
        return this.mapId;
    }
    
    public void func_179734_a(final MapData p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179739_b:B
        //     5: putfield        net/minecraft/world/storage/MapData.scale:B
        //     8: aload_1        
        //     9: getfield        net/minecraft/world/storage/MapData.playersVisibleOnMap:Ljava/util/Map;
        //    12: invokeinterface java/util/Map.clear:()V
        //    17: goto            57
        //    20: aload_0        
        //    21: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179740_c:[Lnet/minecraft/util/Vec4b;
        //    24: iconst_0       
        //    25: aaload         
        //    26: astore_3       
        //    27: aload_1        
        //    28: getfield        net/minecraft/world/storage/MapData.playersVisibleOnMap:Ljava/util/Map;
        //    31: new             Ljava/lang/StringBuilder;
        //    34: dup            
        //    35: ldc             "icon-"
        //    37: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    40: iconst_0       
        //    41: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    44: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    47: aload_3        
        //    48: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    53: pop            
        //    54: iinc            2, 1
        //    57: iconst_0       
        //    58: aload_0        
        //    59: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179740_c:[Lnet/minecraft/util/Vec4b;
        //    62: arraylength    
        //    63: if_icmplt       20
        //    66: goto            121
        //    69: goto            110
        //    72: aload_1        
        //    73: getfield        net/minecraft/world/storage/MapData.colors:[B
        //    76: aload_0        
        //    77: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179737_d:I
        //    80: iconst_0       
        //    81: iadd           
        //    82: aload_0        
        //    83: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179738_e:I
        //    86: iconst_0       
        //    87: iadd           
        //    88: sipush          128
        //    91: imul           
        //    92: iadd           
        //    93: aload_0        
        //    94: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179741_h:[B
        //    97: iconst_0       
        //    98: iconst_0       
        //    99: aload_0        
        //   100: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179735_f:I
        //   103: imul           
        //   104: iadd           
        //   105: baload         
        //   106: bastore        
        //   107: iinc            3, 1
        //   110: iconst_0       
        //   111: aload_0        
        //   112: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179736_g:I
        //   115: if_icmplt       72
        //   118: iinc            2, 1
        //   121: iconst_0       
        //   122: aload_0        
        //   123: getfield        net/minecraft/network/play/server/S34PacketMaps.field_179735_f:I
        //   126: if_icmplt       69
        //   129: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180741_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001311";
    }
}
