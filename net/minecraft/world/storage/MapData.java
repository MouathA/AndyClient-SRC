package net.minecraft.world.storage;

import com.google.common.collect.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import java.util.*;

public class MapData extends WorldSavedData
{
    public int xCenter;
    public int zCenter;
    public byte dimension;
    public byte scale;
    public byte[] colors;
    public List playersArrayList;
    private Map playersHashMap;
    public Map playersVisibleOnMap;
    private static final String __OBFID;
    
    public MapData(final String s) {
        super(s);
        this.colors = new byte[16384];
        this.playersArrayList = Lists.newArrayList();
        this.playersHashMap = Maps.newHashMap();
        this.playersVisibleOnMap = Maps.newLinkedHashMap();
    }
    
    public void func_176054_a(final double n, final double n2, final int n3) {
        final int n4 = 128 * (1 << n3);
        final int floor_double = MathHelper.floor_double((n + 64.0) / n4);
        final int floor_double2 = MathHelper.floor_double((n2 + 64.0) / n4);
        this.xCenter = floor_double * n4 + n4 / 2 - 64;
        this.zCenter = floor_double2 * n4 + n4 / 2 - 64;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.dimension = nbtTagCompound.getByte("dimension");
        this.xCenter = nbtTagCompound.getInteger("xCenter");
        this.zCenter = nbtTagCompound.getInteger("zCenter");
        this.scale = nbtTagCompound.getByte("scale");
        this.scale = (byte)MathHelper.clamp_int(this.scale, 0, 4);
        final short short1 = nbtTagCompound.getShort("width");
        final short short2 = nbtTagCompound.getShort("height");
        if (short1 == 128 && short2 == 128) {
            this.colors = nbtTagCompound.getByteArray("colors");
        }
        else {
            final byte[] byteArray = nbtTagCompound.getByteArray("colors");
            this.colors = new byte[16384];
            final int n = (128 - short1) / 2;
            final int n2 = (128 - short2) / 2;
            while (0 < short2) {
                final int n3 = 0 + n2;
                if (n3 >= 0 || n3 < 128) {
                    while (0 < short1) {
                        final int n4 = 0 + n;
                        if (n4 >= 0 || n4 < 128) {
                            this.colors[n4 + n3 * 128] = byteArray[0 + 0 * short1];
                        }
                        int n5 = 0;
                        ++n5;
                    }
                }
                int n6 = 0;
                ++n6;
            }
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("dimension", this.dimension);
        nbtTagCompound.setInteger("xCenter", this.xCenter);
        nbtTagCompound.setInteger("zCenter", this.zCenter);
        nbtTagCompound.setByte("scale", this.scale);
        nbtTagCompound.setShort("width", (short)128);
        nbtTagCompound.setShort("height", (short)128);
        nbtTagCompound.setByteArray("colors", this.colors);
    }
    
    public void updateVisiblePlayers(final EntityPlayer p0, final ItemStack p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/storage/MapData.playersHashMap:Ljava/util/Map;
        //     4: aload_1        
        //     5: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //    10: ifne            46
        //    13: new             Lnet/minecraft/world/storage/MapData$MapInfo;
        //    16: dup            
        //    17: aload_0        
        //    18: aload_1        
        //    19: invokespecial   net/minecraft/world/storage/MapData$MapInfo.<init>:(Lnet/minecraft/world/storage/MapData;Lnet/minecraft/entity/player/EntityPlayer;)V
        //    22: astore_3       
        //    23: aload_0        
        //    24: getfield        net/minecraft/world/storage/MapData.playersHashMap:Ljava/util/Map;
        //    27: aload_1        
        //    28: aload_3        
        //    29: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    34: pop            
        //    35: aload_0        
        //    36: getfield        net/minecraft/world/storage/MapData.playersArrayList:Ljava/util/List;
        //    39: aload_3        
        //    40: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    45: pop            
        //    46: aload_1        
        //    47: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //    50: aload_2        
        //    51: invokevirtual   net/minecraft/entity/player/InventoryPlayer.hasItemStack:(Lnet/minecraft/item/ItemStack;)Z
        //    54: ifne            71
        //    57: aload_0        
        //    58: getfield        net/minecraft/world/storage/MapData.playersVisibleOnMap:Ljava/util/Map;
        //    61: aload_1        
        //    62: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //    65: invokeinterface java/util/Map.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //    70: pop            
        //    71: goto            223
        //    74: aload_0        
        //    75: getfield        net/minecraft/world/storage/MapData.playersArrayList:Ljava/util/List;
        //    78: iconst_0       
        //    79: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    84: checkcast       Lnet/minecraft/world/storage/MapData$MapInfo;
        //    87: astore          4
        //    89: aload           4
        //    91: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //    94: getfield        net/minecraft/entity/player/EntityPlayer.isDead:Z
        //    97: ifne            193
        //   100: aload           4
        //   102: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   105: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   108: aload_2        
        //   109: invokevirtual   net/minecraft/entity/player/InventoryPlayer.hasItemStack:(Lnet/minecraft/item/ItemStack;)Z
        //   112: ifne            122
        //   115: aload_2        
        //   116: invokevirtual   net/minecraft/item/ItemStack.isOnItemFrame:()Z
        //   119: ifeq            193
        //   122: aload_2        
        //   123: invokevirtual   net/minecraft/item/ItemStack.isOnItemFrame:()Z
        //   126: ifne            220
        //   129: aload           4
        //   131: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   134: getfield        net/minecraft/entity/player/EntityPlayer.dimension:I
        //   137: aload_0        
        //   138: getfield        net/minecraft/world/storage/MapData.dimension:B
        //   141: if_icmpne       220
        //   144: aload_0        
        //   145: iconst_0       
        //   146: aload           4
        //   148: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   151: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   154: aload           4
        //   156: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   159: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //   162: aload           4
        //   164: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   167: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   170: aload           4
        //   172: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   175: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   178: aload           4
        //   180: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   183: getfield        net/minecraft/entity/player/EntityPlayer.rotationYaw:F
        //   186: f2d            
        //   187: invokespecial   net/minecraft/world/storage/MapData.func_82567_a:(ILnet/minecraft/world/World;Ljava/lang/String;DDD)V
        //   190: goto            220
        //   193: aload_0        
        //   194: getfield        net/minecraft/world/storage/MapData.playersHashMap:Ljava/util/Map;
        //   197: aload           4
        //   199: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   202: invokeinterface java/util/Map.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //   207: pop            
        //   208: aload_0        
        //   209: getfield        net/minecraft/world/storage/MapData.playersArrayList:Ljava/util/List;
        //   212: aload           4
        //   214: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //   219: pop            
        //   220: iinc            3, 1
        //   223: iconst_0       
        //   224: aload_0        
        //   225: getfield        net/minecraft/world/storage/MapData.playersArrayList:Ljava/util/List;
        //   228: invokeinterface java/util/List.size:()I
        //   233: if_icmplt       74
        //   236: aload_2        
        //   237: invokevirtual   net/minecraft/item/ItemStack.isOnItemFrame:()Z
        //   240: ifeq            305
        //   243: aload_2        
        //   244: invokevirtual   net/minecraft/item/ItemStack.getItemFrame:()Lnet/minecraft/entity/item/EntityItemFrame;
        //   247: astore_3       
        //   248: aload_3        
        //   249: invokevirtual   net/minecraft/entity/item/EntityItemFrame.func_174857_n:()Lnet/minecraft/util/BlockPos;
        //   252: astore          4
        //   254: aload_0        
        //   255: iconst_1       
        //   256: aload_1        
        //   257: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   260: new             Ljava/lang/StringBuilder;
        //   263: dup            
        //   264: ldc             "frame-"
        //   266: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   269: aload_3        
        //   270: invokevirtual   net/minecraft/entity/item/EntityItemFrame.getEntityId:()I
        //   273: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   276: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   279: aload           4
        //   281: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   284: i2d            
        //   285: aload           4
        //   287: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   290: i2d            
        //   291: aload_3        
        //   292: getfield        net/minecraft/entity/item/EntityItemFrame.field_174860_b:Lnet/minecraft/util/EnumFacing;
        //   295: invokevirtual   net/minecraft/util/EnumFacing.getHorizontalIndex:()I
        //   298: bipush          90
        //   300: imul           
        //   301: i2d            
        //   302: invokespecial   net/minecraft/world/storage/MapData.func_82567_a:(ILnet/minecraft/world/World;Ljava/lang/String;DDD)V
        //   305: aload_2        
        //   306: invokevirtual   net/minecraft/item/ItemStack.hasTagCompound:()Z
        //   309: ifeq            427
        //   312: aload_2        
        //   313: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   316: ldc             "Decorations"
        //   318: bipush          9
        //   320: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   323: ifeq            427
        //   326: aload_2        
        //   327: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   330: ldc             "Decorations"
        //   332: bipush          10
        //   334: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   337: astore_3       
        //   338: goto            419
        //   341: aload_3        
        //   342: iconst_0       
        //   343: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   346: astore          5
        //   348: aload_0        
        //   349: getfield        net/minecraft/world/storage/MapData.playersVisibleOnMap:Ljava/util/Map;
        //   352: aload           5
        //   354: ldc_w           "id"
        //   357: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   360: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   365: ifne            416
        //   368: aload_0        
        //   369: aload           5
        //   371: ldc_w           "type"
        //   374: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByte:(Ljava/lang/String;)B
        //   377: aload_1        
        //   378: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   381: aload           5
        //   383: ldc_w           "id"
        //   386: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   389: aload           5
        //   391: ldc_w           "x"
        //   394: invokevirtual   net/minecraft/nbt/NBTTagCompound.getDouble:(Ljava/lang/String;)D
        //   397: aload           5
        //   399: ldc_w           "z"
        //   402: invokevirtual   net/minecraft/nbt/NBTTagCompound.getDouble:(Ljava/lang/String;)D
        //   405: aload           5
        //   407: ldc_w           "rot"
        //   410: invokevirtual   net/minecraft/nbt/NBTTagCompound.getDouble:(Ljava/lang/String;)D
        //   413: invokespecial   net/minecraft/world/storage/MapData.func_82567_a:(ILnet/minecraft/world/World;Ljava/lang/String;DDD)V
        //   416: iinc            4, 1
        //   419: iconst_0       
        //   420: aload_3        
        //   421: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   424: if_icmplt       341
        //   427: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void func_82567_a(final int n, final World world, final String s, final double n2, final double n3, double n4) {
        final int n5 = 1 << this.scale;
        final float n6 = (float)(n2 - this.xCenter) / n5;
        final float n7 = (float)(n3 - this.zCenter) / n5;
        byte b = (byte)(n6 * 2.0f + 0.5);
        byte b2 = (byte)(n7 * 2.0f + 0.5);
        if (n6 >= -63 && n7 >= -63 && n6 <= 63 && n7 <= 63) {
            n4 += ((n4 < 0.0) ? -8.0 : 8.0);
            final byte b3 = (byte)(n4 * 16.0 / 360.0);
            if (this.dimension < 0) {
                final int n8 = (int)(world.getWorldInfo().getWorldTime() / 10L);
                final byte b4 = (byte)(n8 * n8 * 34187121 + n8 * 121 >> 15 & 0xF);
            }
        }
        else {
            if (Math.abs(n6) >= 320.0f || Math.abs(n7) >= 320.0f) {
                this.playersVisibleOnMap.remove(s);
                return;
            }
            if (n6 <= -63) {
                b = (byte)(126 + 2.5);
            }
            if (n7 <= -63) {
                b2 = (byte)(126 + 2.5);
            }
            if (n6 >= 63) {
                b = 127;
            }
            if (n7 >= 63) {
                b2 = 127;
            }
        }
        this.playersVisibleOnMap.put(s, new Vec4b((byte)6, b, b2, (byte)0));
    }
    
    public Packet func_176052_a(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MapInfo mapInfo = this.playersHashMap.get(entityPlayer);
        return (mapInfo == null) ? null : mapInfo.func_176101_a(itemStack);
    }
    
    public void func_176053_a(final int n, final int n2) {
        super.markDirty();
        final Iterator<MapInfo> iterator = this.playersArrayList.iterator();
        while (iterator.hasNext()) {
            iterator.next().func_176102_a(n, n2);
        }
    }
    
    public MapInfo func_82568_a(final EntityPlayer entityPlayer) {
        MapInfo mapInfo = this.playersHashMap.get(entityPlayer);
        if (mapInfo == null) {
            mapInfo = new MapInfo(entityPlayer);
            this.playersHashMap.put(entityPlayer, mapInfo);
            this.playersArrayList.add(mapInfo);
        }
        return mapInfo;
    }
    
    static {
        __OBFID = "CL_00000577";
    }
    
    public class MapInfo
    {
        public final EntityPlayer entityplayerObj;
        private boolean field_176105_d;
        private int field_176106_e;
        private int field_176103_f;
        private int field_176104_g;
        private int field_176108_h;
        private int field_176109_i;
        public int field_82569_d;
        private static final String __OBFID;
        final MapData this$0;
        
        public MapInfo(final MapData this$0, final EntityPlayer entityplayerObj) {
            this.this$0 = this$0;
            this.field_176105_d = true;
            this.field_176106_e = 0;
            this.field_176103_f = 0;
            this.field_176104_g = 127;
            this.field_176108_h = 127;
            this.entityplayerObj = entityplayerObj;
        }
        
        public Packet func_176101_a(final ItemStack itemStack) {
            if (this.field_176105_d) {
                this.field_176105_d = false;
                return new S34PacketMaps(itemStack.getMetadata(), this.this$0.scale, this.this$0.playersVisibleOnMap.values(), this.this$0.colors, this.field_176106_e, this.field_176103_f, this.field_176104_g + 1 - this.field_176106_e, this.field_176108_h + 1 - this.field_176103_f);
            }
            return (this.field_176109_i++ % 5 == 0) ? new S34PacketMaps(itemStack.getMetadata(), this.this$0.scale, this.this$0.playersVisibleOnMap.values(), this.this$0.colors, 0, 0, 0, 0) : null;
        }
        
        public void func_176102_a(final int n, final int n2) {
            if (this.field_176105_d) {
                this.field_176106_e = Math.min(this.field_176106_e, n);
                this.field_176103_f = Math.min(this.field_176103_f, n2);
                this.field_176104_g = Math.max(this.field_176104_g, n);
                this.field_176108_h = Math.max(this.field_176108_h, n2);
            }
            else {
                this.field_176105_d = true;
                this.field_176106_e = n;
                this.field_176103_f = n2;
                this.field_176104_g = n;
                this.field_176108_h = n2;
            }
        }
        
        static {
            __OBFID = "CL_00000578";
        }
    }
}
