package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types;

import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Chunk1_9_3_4Type extends PartialType
{
    public Chunk1_9_3_4Type(final ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }
    
    public Chunk read(final ByteBuf p0, final ClientWorld p1) throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   io/netty/buffer/ByteBuf.readInt:()I
        //     4: istore_3       
        //     5: aload_1        
        //     6: invokevirtual   io/netty/buffer/ByteBuf.readInt:()I
        //     9: istore          4
        //    11: aload_1        
        //    12: invokevirtual   io/netty/buffer/ByteBuf.readBoolean:()Z
        //    15: istore          5
        //    17: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
        //    20: aload_1        
        //    21: invokevirtual   com/viaversion/viaversion/api/type/types/VarIntType.readPrimitive:(Lio/netty/buffer/ByteBuf;)I
        //    24: istore          6
        //    26: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
        //    29: aload_1        
        //    30: invokevirtual   com/viaversion/viaversion/api/type/types/VarIntType.readPrimitive:(Lio/netty/buffer/ByteBuf;)I
        //    33: pop            
        //    34: bipush          16
        //    36: anewarray       Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
        //    39: astore          7
        //    41: iconst_0       
        //    42: bipush          16
        //    44: if_icmpge       117
        //    47: iload           6
        //    49: iconst_1       
        //    50: iand           
        //    51: ifne            57
        //    54: goto            111
        //    57: getstatic       com/viaversion/viaversion/api/type/types/version/Types1_9.CHUNK_SECTION:Lcom/viaversion/viaversion/api/type/Type;
        //    60: aload_1        
        //    61: invokevirtual   com/viaversion/viaversion/api/type/Type.read:(Lio/netty/buffer/ByteBuf;)Ljava/lang/Object;
        //    64: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
        //    67: astore          9
        //    69: aload           7
        //    71: iconst_0       
        //    72: aload           9
        //    74: aastore        
        //    75: aload           9
        //    77: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getLight:()Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight;
        //    82: aload_1        
        //    83: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.readBlockLight:(Lio/netty/buffer/ByteBuf;)V
        //    88: aload_2        
        //    89: invokevirtual   com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld.getEnvironment:()Lcom/viaversion/viaversion/api/minecraft/Environment;
        //    92: getstatic       com/viaversion/viaversion/api/minecraft/Environment.NORMAL:Lcom/viaversion/viaversion/api/minecraft/Environment;
        //    95: if_acmpne       111
        //    98: aload           9
        //   100: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getLight:()Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight;
        //   105: aload_1        
        //   106: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.readSkyLight:(Lio/netty/buffer/ByteBuf;)V
        //   111: iinc            8, 1
        //   114: goto            41
        //   117: iload           5
        //   119: ifeq            130
        //   122: sipush          256
        //   125: newarray        I
        //   127: goto            131
        //   130: aconst_null    
        //   131: astore          8
        //   133: iload           5
        //   135: ifeq            163
        //   138: iconst_0       
        //   139: sipush          256
        //   142: if_icmpge       163
        //   145: aload           8
        //   147: iconst_0       
        //   148: aload_1        
        //   149: invokevirtual   io/netty/buffer/ByteBuf.readByte:()B
        //   152: sipush          255
        //   155: iand           
        //   156: iastore        
        //   157: iinc            9, 1
        //   160: goto            138
        //   163: new             Ljava/util/ArrayList;
        //   166: dup            
        //   167: getstatic       com/viaversion/viaversion/api/type/Type.NBT_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
        //   170: aload_1        
        //   171: invokevirtual   com/viaversion/viaversion/api/type/Type.read:(Lio/netty/buffer/ByteBuf;)Ljava/lang/Object;
        //   174: checkcast       [Ljava/lang/Object;
        //   177: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   180: invokespecial   java/util/ArrayList.<init>:(Ljava/util/Collection;)V
        //   183: astore          9
        //   185: aload_1        
        //   186: invokevirtual   io/netty/buffer/ByteBuf.readableBytes:()I
        //   189: ifle            266
        //   192: getstatic       com/viaversion/viaversion/api/type/Type.REMAINING_BYTES:Lcom/viaversion/viaversion/api/type/Type;
        //   195: aload_1        
        //   196: invokevirtual   com/viaversion/viaversion/api/type/Type.read:(Lio/netty/buffer/ByteBuf;)Ljava/lang/Object;
        //   199: checkcast       [B
        //   202: astore          10
        //   204: invokestatic    com/viaversion/viaversion/api/Via.getManager:()Lcom/viaversion/viaversion/api/ViaManager;
        //   207: invokeinterface com/viaversion/viaversion/api/ViaManager.isDebug:()Z
        //   212: ifeq            266
        //   215: invokestatic    com/viaversion/viaversion/api/Via.getPlatform:()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
        //   218: invokeinterface com/viaversion/viaversion/api/platform/ViaPlatform.getLogger:()Ljava/util/logging/Logger;
        //   223: new             Ljava/lang/StringBuilder;
        //   226: dup            
        //   227: invokespecial   java/lang/StringBuilder.<init>:()V
        //   230: ldc             "Found "
        //   232: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   235: aload           10
        //   237: arraylength    
        //   238: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   241: ldc             " more bytes than expected while reading the chunk: "
        //   243: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   246: iload_3        
        //   247: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   250: ldc             "/"
        //   252: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   255: iload           4
        //   257: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   260: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   263: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
        //   266: new             Lcom/viaversion/viaversion/api/minecraft/chunks/BaseChunk;
        //   269: dup            
        //   270: iload_3        
        //   271: iload           4
        //   273: iload           5
        //   275: iconst_0       
        //   276: iload           6
        //   278: aload           7
        //   280: aload           8
        //   282: aload           9
        //   284: invokespecial   com/viaversion/viaversion/api/minecraft/chunks/BaseChunk.<init>:(IIZZI[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;[ILjava/util/List;)V
        //   287: areturn        
        //    Exceptions:
        //  throws java.lang.Exception
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void write(final ByteBuf byteBuf, final ClientWorld clientWorld, final Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(byteBuf, chunk.getBitmask());
        final ByteBuf buffer = byteBuf.alloc().buffer();
        while (0 < 16) {
            final ChunkSection chunkSection = chunk.getSections()[0];
            if (chunkSection != null) {
                Types1_9.CHUNK_SECTION.write(buffer, chunkSection);
                chunkSection.getLight().writeBlockLight(buffer);
                if (chunkSection.getLight().hasSkyLight()) {
                    chunkSection.getLight().writeSkyLight(buffer);
                }
            }
            int n = 0;
            ++n;
        }
        buffer.readerIndex(0);
        Type.VAR_INT.writePrimitive(byteBuf, buffer.readableBytes() + (chunk.isBiomeData() ? 256 : 0));
        byteBuf.writeBytes(buffer);
        buffer.release();
        if (chunk.isBiomeData()) {
            final int[] biomeData = chunk.getBiomeData();
            while (0 < biomeData.length) {
                byteBuf.writeByte((byte)biomeData[0]);
                int n2 = 0;
                ++n2;
            }
        }
        Type.NBT_ARRAY.write(byteBuf, chunk.getBlockEntities().toArray(new CompoundTag[0]));
    }
    
    @Override
    public Class getBaseClass() {
        return BaseChunkType.class;
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o, final Object o2) throws Exception {
        this.write(byteBuf, (ClientWorld)o, (Chunk)o2);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf, final Object o) throws Exception {
        return this.read(byteBuf, (ClientWorld)o);
    }
}
