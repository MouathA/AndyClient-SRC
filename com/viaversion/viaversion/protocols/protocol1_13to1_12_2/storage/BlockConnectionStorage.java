package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;

public class BlockConnectionStorage implements StorableObject
{
    private static final short[] REVERSE_BLOCK_MAPPINGS;
    private static Constructor fastUtilLongObjectHashMap;
    private final Map blockStorage;
    
    public BlockConnectionStorage() {
        this.blockStorage = this.createLongObjectMap();
    }
    
    public void store(final int n, final int n2, final int n3, int n4) {
        final int n5 = BlockConnectionStorage.REVERSE_BLOCK_MAPPINGS[n4];
        if (n5 == -1) {
            return;
        }
        n4 = n5;
        final Pair chunkSection = this.getChunkSection(this.getChunkSectionIndex(n, n2, n3), (n4 & 0xF) != 0x0);
        final short encodeBlockPos = this.encodeBlockPos(n, n2, n3);
        ((byte[])chunkSection.key())[encodeBlockPos] = (byte)(n4 >> 4);
        final NibbleArray nibbleArray = (NibbleArray)chunkSection.value();
        if (nibbleArray != null) {
            nibbleArray.set(encodeBlockPos, n4);
        }
    }
    
    public int get(final int n, final int n2, final int n3) {
        final Pair pair = this.blockStorage.get(this.getChunkSectionIndex(n, n2, n3));
        if (pair == null) {
            return 0;
        }
        final short encodeBlockPos = this.encodeBlockPos(n, n2, n3);
        final NibbleArray nibbleArray = (NibbleArray)pair.value();
        return WorldPackets.toNewId((((byte[])pair.key())[encodeBlockPos] & 0xFF) << 4 | ((nibbleArray == null) ? 0 : nibbleArray.get(encodeBlockPos)));
    }
    
    public void remove(final int n, final int n2, final int n3) {
        final long chunkSectionIndex = this.getChunkSectionIndex(n, n2, n3);
        final Pair pair = this.blockStorage.get(chunkSectionIndex);
        if (pair == null) {
            return;
        }
        final short encodeBlockPos = this.encodeBlockPos(n, n2, n3);
        final NibbleArray nibbleArray = (NibbleArray)pair.value();
        if (nibbleArray != null) {
            nibbleArray.set(encodeBlockPos, 0);
            while (0 < 4096 && nibbleArray.get(0) == 0) {
                int length = 0;
                ++length;
            }
            if (false) {
                pair.setValue(null);
            }
        }
        ((byte[])pair.key())[encodeBlockPos] = 0;
        final byte[] array = (byte[])pair.key();
        int length = array.length;
        while (0 < 0) {
            if (array[0] != 0) {
                return;
            }
            int n4 = 0;
            ++n4;
        }
        this.blockStorage.remove(chunkSectionIndex);
    }
    
    public void clear() {
        this.blockStorage.clear();
    }
    
    public void unloadChunk(final int n, final int n2) {
        while (0 < 256) {
            this.blockStorage.remove(this.getChunkSectionIndex(n << 4, 0, n2 << 4));
            final int n3;
            n3 += 16;
        }
    }
    
    private Pair getChunkSection(final long n, final boolean b) {
        Pair pair = this.blockStorage.get(n);
        if (pair == null) {
            pair = new Pair(new byte[4096], null);
            this.blockStorage.put(n, pair);
        }
        if (pair.value() == null && b) {
            pair.setValue(new NibbleArray(4096));
        }
        return pair;
    }
    
    private long getChunkSectionIndex(final int n, final int n2, final int n3) {
        return ((long)(n >> 4) & 0x3FFFFFFL) << 38 | ((long)(n2 >> 4) & 0xFFFL) << 26 | ((long)(n3 >> 4) & 0x3FFFFFFL);
    }
    
    private long getChunkSectionIndex(final Position position) {
        return this.getChunkSectionIndex(position.x(), position.y(), position.z());
    }
    
    private short encodeBlockPos(final int n, final int n2, final int n3) {
        return (short)((n2 & 0xF) << 8 | (n & 0xF) << 4 | (n3 & 0xF));
    }
    
    private short encodeBlockPos(final Position position) {
        return this.encodeBlockPos(position.x(), position.y(), position.z());
    }
    
    private Map createLongObjectMap() {
        if (BlockConnectionStorage.fastUtilLongObjectHashMap != null) {
            return BlockConnectionStorage.fastUtilLongObjectHashMap.newInstance(new Object[0]);
        }
        return new HashMap();
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: newarray        S
        //     5: putstatic       com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockConnectionStorage.REVERSE_BLOCK_MAPPINGS:[S
        //     8: new             Ljava/lang/StringBuilder;
        //    11: dup            
        //    12: ldc             "it"
        //    14: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    17: ldc             ".unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap"
        //    19: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    22: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    25: astore_0       
        //    26: aload_0        
        //    27: invokestatic    java/lang/Class.forName:(Ljava/lang/String;)Ljava/lang/Class;
        //    30: iconst_0       
        //    31: anewarray       Ljava/lang/Class;
        //    34: invokevirtual   java/lang/Class.getConstructor:([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
        //    37: putstatic       com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockConnectionStorage.fastUtilLongObjectHashMap:Ljava/lang/reflect/Constructor;
        //    40: invokestatic    com/viaversion/viaversion/api/Via.getPlatform:()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
        //    43: invokeinterface com/viaversion/viaversion/api/platform/ViaPlatform.getLogger:()Ljava/util/logging/Logger;
        //    48: ldc             "Using FastUtil Long2ObjectOpenHashMap for block connections"
        //    50: invokevirtual   java/util/logging/Logger.info:(Ljava/lang/String;)V
        //    53: goto            57
        //    56: astore_0       
        //    57: getstatic       com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockConnectionStorage.REVERSE_BLOCK_MAPPINGS:[S
        //    60: iconst_m1      
        //    61: invokestatic    java/util/Arrays.fill:([SS)V
        //    64: iconst_0       
        //    65: sipush          4096
        //    68: if_icmpge       102
        //    71: getstatic       com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2.MAPPINGS:Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/MappingData;
        //    74: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/MappingData.getBlockMappings:()Lcom/viaversion/viaversion/api/data/Mappings;
        //    77: iconst_0       
        //    78: invokeinterface com/viaversion/viaversion/api/data/Mappings.getNewId:(I)I
        //    83: istore_1       
        //    84: iload_1        
        //    85: iconst_m1      
        //    86: if_icmpeq       96
        //    89: getstatic       com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockConnectionStorage.REVERSE_BLOCK_MAPPINGS:[S
        //    92: iload_1        
        //    93: iconst_0       
        //    94: i2s            
        //    95: sastore        
        //    96: iinc            0, 1
        //    99: goto            64
        //   102: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
