package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;

import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.libs.gson.*;

public class MappingData extends MappingDataBase
{
    private IntSet motionBlocking;
    private IntSet nonFullBlocks;
    
    public MappingData() {
        super("1.13.2", "1.14");
    }
    
    public void loadExtras(final JsonObject p0, final JsonObject p1, final JsonObject p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "blockstates"
        //     3: invokevirtual   com/viaversion/viaversion/libs/gson/JsonObject.getAsJsonObject:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/JsonObject;
        //     6: astore          4
        //     8: new             Ljava/util/HashMap;
        //    11: dup            
        //    12: aload           4
        //    14: invokevirtual   com/viaversion/viaversion/libs/gson/JsonObject.entrySet:()Ljava/util/Set;
        //    17: invokeinterface java/util/Set.size:()I
        //    22: invokespecial   java/util/HashMap.<init>:(I)V
        //    25: astore          5
        //    27: aload           4
        //    29: invokevirtual   com/viaversion/viaversion/libs/gson/JsonObject.entrySet:()Ljava/util/Set;
        //    32: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    37: astore          6
        //    39: aload           6
        //    41: invokeinterface java/util/Iterator.hasNext:()Z
        //    46: ifeq            101
        //    49: aload           6
        //    51: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    56: checkcast       Ljava/util/Map$Entry;
        //    59: astore          7
        //    61: aload           5
        //    63: aload           7
        //    65: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //    70: checkcast       Lcom/viaversion/viaversion/libs/gson/JsonElement;
        //    73: invokevirtual   com/viaversion/viaversion/libs/gson/JsonElement.getAsString:()Ljava/lang/String;
        //    76: aload           7
        //    78: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //    83: checkcast       Ljava/lang/String;
        //    86: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //    89: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    92: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    97: pop            
        //    98: goto            39
        //   101: ldc             "heightMapData-1.14.json"
        //   103: invokestatic    com/viaversion/viaversion/api/data/MappingDataLoader.loadData:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/JsonObject;
        //   106: astore          6
        //   108: aload           6
        //   110: ldc             "MOTION_BLOCKING"
        //   112: invokevirtual   com/viaversion/viaversion/libs/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/JsonArray;
        //   115: astore          7
        //   117: aload_0        
        //   118: new             Lcom/viaversion/viaversion/libs/fastutil/ints/IntOpenHashSet;
        //   121: dup            
        //   122: aload           7
        //   124: invokevirtual   com/viaversion/viaversion/libs/gson/JsonArray.size:()I
        //   127: ldc             0.99
        //   129: invokespecial   com/viaversion/viaversion/libs/fastutil/ints/IntOpenHashSet.<init>:(IF)V
        //   132: putfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.motionBlocking:Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
        //   135: aload           7
        //   137: invokevirtual   com/viaversion/viaversion/libs/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   140: astore          8
        //   142: aload           8
        //   144: invokeinterface java/util/Iterator.hasNext:()Z
        //   149: ifeq            247
        //   152: aload           8
        //   154: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   159: checkcast       Lcom/viaversion/viaversion/libs/gson/JsonElement;
        //   162: astore          9
        //   164: aload           9
        //   166: invokevirtual   com/viaversion/viaversion/libs/gson/JsonElement.getAsString:()Ljava/lang/String;
        //   169: astore          10
        //   171: aload           5
        //   173: aload           10
        //   175: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   180: checkcast       Ljava/lang/Integer;
        //   183: astore          11
        //   185: aload           11
        //   187: ifnonnull       229
        //   190: invokestatic    com/viaversion/viaversion/api/Via.getPlatform:()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
        //   193: invokeinterface com/viaversion/viaversion/api/platform/ViaPlatform.getLogger:()Ljava/util/logging/Logger;
        //   198: new             Ljava/lang/StringBuilder;
        //   201: dup            
        //   202: invokespecial   java/lang/StringBuilder.<init>:()V
        //   205: ldc             "Unknown blockstate "
        //   207: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   210: aload           10
        //   212: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   215: ldc             " :("
        //   217: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   220: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   223: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
        //   226: goto            244
        //   229: aload_0        
        //   230: getfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.motionBlocking:Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
        //   233: aload           11
        //   235: invokevirtual   java/lang/Integer.intValue:()I
        //   238: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/IntSet.add:(I)Z
        //   243: pop            
        //   244: goto            142
        //   247: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
        //   250: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.isNonFullBlockLightFix:()Z
        //   255: ifeq            451
        //   258: aload_0        
        //   259: new             Lcom/viaversion/viaversion/libs/fastutil/ints/IntOpenHashSet;
        //   262: dup            
        //   263: sipush          1611
        //   266: ldc             0.99
        //   268: invokespecial   com/viaversion/viaversion/libs/fastutil/ints/IntOpenHashSet.<init>:(IF)V
        //   271: putfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.nonFullBlocks:Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
        //   274: aload_1        
        //   275: ldc             "blockstates"
        //   277: invokevirtual   com/viaversion/viaversion/libs/gson/JsonObject.getAsJsonObject:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/JsonObject;
        //   280: invokevirtual   com/viaversion/viaversion/libs/gson/JsonObject.entrySet:()Ljava/util/Set;
        //   283: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   288: astore          8
        //   290: aload           8
        //   292: invokeinterface java/util/Iterator.hasNext:()Z
        //   297: ifeq            392
        //   300: aload           8
        //   302: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   307: checkcast       Ljava/util/Map$Entry;
        //   310: astore          9
        //   312: aload           9
        //   314: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //   319: checkcast       Lcom/viaversion/viaversion/libs/gson/JsonElement;
        //   322: invokevirtual   com/viaversion/viaversion/libs/gson/JsonElement.getAsString:()Ljava/lang/String;
        //   325: astore          10
        //   327: aload           10
        //   329: ldc             "_slab"
        //   331: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   334: ifne            357
        //   337: aload           10
        //   339: ldc             "_stairs"
        //   341: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   344: ifne            357
        //   347: aload           10
        //   349: ldc             "_wall["
        //   351: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   354: ifeq            389
        //   357: aload_0        
        //   358: getfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.nonFullBlocks:Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
        //   361: aload_0        
        //   362: getfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.blockStateMappings:Lcom/viaversion/viaversion/api/data/Mappings;
        //   365: aload           9
        //   367: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //   372: checkcast       Ljava/lang/String;
        //   375: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   378: invokeinterface com/viaversion/viaversion/api/data/Mappings.getNewId:(I)I
        //   383: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/IntSet.add:(I)Z
        //   388: pop            
        //   389: goto            290
        //   392: aload_0        
        //   393: getfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.nonFullBlocks:Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
        //   396: aload_0        
        //   397: getfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.blockStateMappings:Lcom/viaversion/viaversion/api/data/Mappings;
        //   400: sipush          8163
        //   403: invokeinterface com/viaversion/viaversion/api/data/Mappings.getNewId:(I)I
        //   408: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/IntSet.add:(I)Z
        //   413: pop            
        //   414: sipush          3060
        //   417: sipush          3067
        //   420: if_icmpgt       451
        //   423: aload_0        
        //   424: getfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.nonFullBlocks:Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
        //   427: aload_0        
        //   428: getfield        com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.blockStateMappings:Lcom/viaversion/viaversion/api/data/Mappings;
        //   431: sipush          3060
        //   434: invokeinterface com/viaversion/viaversion/api/data/Mappings.getNewId:(I)I
        //   439: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/IntSet.add:(I)Z
        //   444: pop            
        //   445: iinc            8, 1
        //   448: goto            414
        //   451: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public IntSet getMotionBlocking() {
        return this.motionBlocking;
    }
    
    public IntSet getNonFullBlocks() {
        return this.nonFullBlocks;
    }
}
