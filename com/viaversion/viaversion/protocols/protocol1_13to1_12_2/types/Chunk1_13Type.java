package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types;

import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Chunk1_13Type extends PartialType
{
    public Chunk1_13Type(final ClientWorld clientWorld) {
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
        //    26: aload_1        
        //    27: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
        //    30: aload_1        
        //    31: invokevirtual   com/viaversion/viaversion/api/type/types/VarIntType.readPrimitive:(Lio/netty/buffer/ByteBuf;)I
        //    34: invokevirtual   io/netty/buffer/ByteBuf.readSlice:(I)Lio/netty/buffer/ByteBuf;
        //    37: astore          7
        //    39: bipush          16
        //    41: anewarray       Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
        //    44: astore          8
        //    46: iconst_0       
        //    47: bipush          16
        //    49: if_icmpge       125
        //    52: iload           6
        //    54: iconst_1       
        //    55: iand           
        //    56: ifne            62
        //    59: goto            119
        //    62: getstatic       com/viaversion/viaversion/api/type/types/version/Types1_13.CHUNK_SECTION:Lcom/viaversion/viaversion/api/type/Type;
        //    65: aload           7
        //    67: invokevirtual   com/viaversion/viaversion/api/type/Type.read:(Lio/netty/buffer/ByteBuf;)Ljava/lang/Object;
        //    70: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
        //    73: astore          10
        //    75: aload           8
        //    77: iconst_0       
        //    78: aload           10
        //    80: aastore        
        //    81: aload           10
        //    83: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getLight:()Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight;
        //    88: aload           7
        //    90: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.readBlockLight:(Lio/netty/buffer/ByteBuf;)V
        //    95: aload_2        
        //    96: invokevirtual   com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld.getEnvironment:()Lcom/viaversion/viaversion/api/minecraft/Environment;
        //    99: getstatic       com/viaversion/viaversion/api/minecraft/Environment.NORMAL:Lcom/viaversion/viaversion/api/minecraft/Environment;
        //   102: if_acmpne       119
        //   105: aload           10
        //   107: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getLight:()Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight;
        //   112: aload           7
        //   114: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.readSkyLight:(Lio/netty/buffer/ByteBuf;)V
        //   119: iinc            9, 1
        //   122: goto            46
        //   125: iload           5
        //   127: ifeq            138
        //   130: sipush          256
        //   133: newarray        I
        //   135: goto            139
        //   138: aconst_null    
        //   139: astore          9
        //   141: iload           5
        //   143: ifeq            230
        //   146: aload           7
        //   148: invokevirtual   io/netty/buffer/ByteBuf.readableBytes:()I
        //   151: sipush          1024
        //   154: if_icmplt       182
        //   157: iconst_0       
        //   158: sipush          256
        //   161: if_icmpge       179
        //   164: aload           9
        //   166: iconst_0       
        //   167: aload           7
        //   169: invokevirtual   io/netty/buffer/ByteBuf.readInt:()I
        //   172: iastore        
        //   173: iinc            10, 1
        //   176: goto            157
        //   179: goto            230
        //   182: invokestatic    com/viaversion/viaversion/api/Via.getPlatform:()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
        //   185: invokeinterface com/viaversion/viaversion/api/platform/ViaPlatform.getLogger:()Ljava/util/logging/Logger;
        //   190: getstatic       java/util/logging/Level.WARNING:Ljava/util/logging/Level;
        //   193: new             Ljava/lang/StringBuilder;
        //   196: dup            
        //   197: invokespecial   java/lang/StringBuilder.<init>:()V
        //   200: ldc             "Chunk x="
        //   202: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   205: iload_3        
        //   206: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   209: ldc             " z="
        //   211: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   214: iload           4
        //   216: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   219: ldc             " doesn't have biome data!"
        //   221: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   224: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   227: invokevirtual   java/util/logging/Logger.log:(Ljava/util/logging/Level;Ljava/lang/String;)V
        //   230: new             Ljava/util/ArrayList;
        //   233: dup            
        //   234: getstatic       com/viaversion/viaversion/api/type/Type.NBT_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
        //   237: aload_1        
        //   238: invokevirtual   com/viaversion/viaversion/api/type/Type.read:(Lio/netty/buffer/ByteBuf;)Ljava/lang/Object;
        //   241: checkcast       [Ljava/lang/Object;
        //   244: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   247: invokespecial   java/util/ArrayList.<init>:(Ljava/util/Collection;)V
        //   250: astore          10
        //   252: aload_1        
        //   253: invokevirtual   io/netty/buffer/ByteBuf.readableBytes:()I
        //   256: ifle            333
        //   259: getstatic       com/viaversion/viaversion/api/type/Type.REMAINING_BYTES:Lcom/viaversion/viaversion/api/type/Type;
        //   262: aload_1        
        //   263: invokevirtual   com/viaversion/viaversion/api/type/Type.read:(Lio/netty/buffer/ByteBuf;)Ljava/lang/Object;
        //   266: checkcast       [B
        //   269: astore          11
        //   271: invokestatic    com/viaversion/viaversion/api/Via.getManager:()Lcom/viaversion/viaversion/api/ViaManager;
        //   274: invokeinterface com/viaversion/viaversion/api/ViaManager.isDebug:()Z
        //   279: ifeq            333
        //   282: invokestatic    com/viaversion/viaversion/api/Via.getPlatform:()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
        //   285: invokeinterface com/viaversion/viaversion/api/platform/ViaPlatform.getLogger:()Ljava/util/logging/Logger;
        //   290: new             Ljava/lang/StringBuilder;
        //   293: dup            
        //   294: invokespecial   java/lang/StringBuilder.<init>:()V
        //   297: ldc             "Found "
        //   299: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   302: aload           11
        //   304: arraylength    
        //   305: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   308: ldc             " more bytes than expected while reading the chunk: "
        //   310: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   313: iload_3        
        //   314: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   317: ldc             "/"
        //   319: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   322: iload           4
        //   324: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   327: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   330: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
        //   333: new             Lcom/viaversion/viaversion/api/minecraft/chunks/BaseChunk;
        //   336: dup            
        //   337: iload_3        
        //   338: iload           4
        //   340: iload           5
        //   342: iconst_0       
        //   343: iload           6
        //   345: aload           8
        //   347: aload           9
        //   349: aload           10
        //   351: invokespecial   com/viaversion/viaversion/api/minecraft/chunks/BaseChunk.<init>:(IIZZI[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;[ILjava/util/List;)V
        //   354: areturn        
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
                Types1_13.CHUNK_SECTION.write(buffer, chunkSection);
                chunkSection.getLight().writeBlockLight(buffer);
                if (chunkSection.getLight().hasSkyLight()) {
                    chunkSection.getLight().writeSkyLight(buffer);
                }
            }
            int n = 0;
            ++n;
        }
        buffer.readerIndex(0);
        Type.VAR_INT.writePrimitive(byteBuf, buffer.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
        byteBuf.writeBytes(buffer);
        buffer.release();
        if (chunk.isBiomeData()) {
            final int[] biomeData = chunk.getBiomeData();
            while (0 < biomeData.length) {
                byteBuf.writeInt(biomeData[0]);
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
