package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.*;
import com.viaversion.viabackwards.utils.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;

public abstract class LegacyBlockItemRewriter extends ItemRewriterBase
{
    private static final Map LEGACY_MAPPINGS;
    protected final Int2ObjectMap replacementData;
    
    protected LegacyBlockItemRewriter(final BackwardsProtocol backwardsProtocol) {
        super(backwardsProtocol, false);
        this.replacementData = LegacyBlockItemRewriter.LEGACY_MAPPINGS.get(backwardsProtocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        final MappedLegacyBlockItem mappedLegacyBlockItem = (MappedLegacyBlockItem)this.replacementData.get(item.identifier());
        if (mappedLegacyBlockItem == null) {
            return super.handleItemToClient(item);
        }
        final short data = item.data();
        item.setIdentifier(mappedLegacyBlockItem.getId());
        if (mappedLegacyBlockItem.getData() != -1) {
            item.setData(mappedLegacyBlockItem.getData());
        }
        if (mappedLegacyBlockItem.getName() != null) {
            if (item.tag() == null) {
                item.setTag(new CompoundTag());
            }
            CompoundTag compoundTag = (CompoundTag)item.tag().get("display");
            if (compoundTag == null) {
                item.tag().put("display", compoundTag = new CompoundTag());
            }
            StringTag stringTag = (StringTag)compoundTag.get("Name");
            if (stringTag == null) {
                compoundTag.put("Name", stringTag = new StringTag(mappedLegacyBlockItem.getName()));
                compoundTag.put(this.nbtTagName + "|customName", new ByteTag());
            }
            final String value = stringTag.getValue();
            if (value.contains("%vb_color%")) {
                compoundTag.put("Name", new StringTag(value.replace("%vb_color%", BlockColors.get(data))));
            }
        }
        return item;
    }
    
    public int handleBlockID(final int n) {
        final Block handleBlock = this.handleBlock(n >> 4, n & 0xF);
        if (handleBlock == null) {
            return n;
        }
        return handleBlock.getId() << 4 | (handleBlock.getData() & 0xF);
    }
    
    public Block handleBlock(final int n, final int n2) {
        final MappedLegacyBlockItem mappedLegacyBlockItem = (MappedLegacyBlockItem)this.replacementData.get(n);
        if (mappedLegacyBlockItem == null || !mappedLegacyBlockItem.isBlock()) {
            return null;
        }
        final Block block = mappedLegacyBlockItem.getBlock();
        if (block.getData() == -1) {
            return block.withData(n2);
        }
        return block;
    }
    
    protected void handleChunk(final Chunk p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/HashMap.<init>:()V
        //     7: astore_2       
        //     8: aload_1        
        //     9: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
        //    14: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    19: astore_3       
        //    20: aload_3        
        //    21: invokeinterface java/util/Iterator.hasNext:()Z
        //    26: ifeq            233
        //    29: aload_3        
        //    30: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    35: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //    38: astore          4
        //    40: aload           4
        //    42: astore          5
        //    44: ifnull          20
        //    47: aload           4
        //    49: astore          6
        //    51: ifnull          20
        //    54: aload           4
        //    56: astore          7
        //    58: ifnonnull       64
        //    61: goto            20
        //    64: new             Lcom/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos;
        //    67: dup            
        //    68: aload           5
        //    70: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
        //    73: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
        //    76: bipush          15
        //    78: iand           
        //    79: aload           6
        //    81: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
        //    84: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
        //    87: aload           7
        //    89: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
        //    92: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
        //    95: bipush          15
        //    97: iand           
        //    98: aconst_null    
        //    99: invokespecial   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.<init>:(IIILcom/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$1;)V
        //   102: astore          8
        //   104: aload_2        
        //   105: aload           8
        //   107: aload           4
        //   109: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   114: pop            
        //   115: aload           8
        //   117: invokevirtual   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.getY:()I
        //   120: iflt            20
        //   123: aload           8
        //   125: invokevirtual   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.getY:()I
        //   128: sipush          255
        //   131: if_icmple       137
        //   134: goto            20
        //   137: aload_1        
        //   138: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
        //   143: aload           8
        //   145: invokevirtual   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.getY:()I
        //   148: iconst_4       
        //   149: ishr           
        //   150: aaload         
        //   151: astore          9
        //   153: aload           9
        //   155: ifnonnull       161
        //   158: goto            20
        //   161: aload           9
        //   163: aload           8
        //   165: invokevirtual   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.getX:()I
        //   168: aload           8
        //   170: invokevirtual   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.getY:()I
        //   173: bipush          15
        //   175: iand           
        //   176: aload           8
        //   178: invokevirtual   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.getZ:()I
        //   181: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getFlatBlock:(III)I
        //   186: istore          10
        //   188: aload_0        
        //   189: getfield        com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter.replacementData:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap;
        //   192: iconst_0       
        //   193: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap.get:(I)Ljava/lang/Object;
        //   198: checkcast       Lcom/viaversion/viabackwards/api/data/MappedLegacyBlockItem;
        //   201: astore          12
        //   203: aload           12
        //   205: ifnull          230
        //   208: aload           12
        //   210: invokevirtual   com/viaversion/viabackwards/api/data/MappedLegacyBlockItem.hasBlockEntityHandler:()Z
        //   213: ifeq            230
        //   216: aload           12
        //   218: invokevirtual   com/viaversion/viabackwards/api/data/MappedLegacyBlockItem.getBlockEntityHandler:()Lcom/viaversion/viabackwards/api/data/MappedLegacyBlockItem$BlockEntityHandler;
        //   221: iconst_0       
        //   222: aload           4
        //   224: invokeinterface com/viaversion/viabackwards/api/data/MappedLegacyBlockItem$BlockEntityHandler.handleOrNewCompoundTag:(ILcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   229: pop            
        //   230: goto            20
        //   233: iconst_0       
        //   234: aload_1        
        //   235: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
        //   240: arraylength    
        //   241: if_icmpge       586
        //   244: aload_1        
        //   245: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
        //   250: iconst_0       
        //   251: aaload         
        //   252: astore          4
        //   254: aload           4
        //   256: ifnonnull       262
        //   259: goto            580
        //   262: iconst_0       
        //   263: aload           4
        //   265: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
        //   270: if_icmpge       361
        //   273: aload           4
        //   275: iconst_0       
        //   276: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
        //   281: istore          7
        //   283: aload_0        
        //   284: iconst_0       
        //   285: iconst_0       
        //   286: invokevirtual   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter.handleBlock:(II)Lcom/viaversion/viabackwards/utils/Block;
        //   289: astore          10
        //   291: aload           10
        //   293: ifnull          320
        //   296: aload           4
        //   298: iconst_0       
        //   299: aload           10
        //   301: invokevirtual   com/viaversion/viabackwards/utils/Block.getId:()I
        //   304: iconst_4       
        //   305: ishl           
        //   306: aload           10
        //   308: invokevirtual   com/viaversion/viabackwards/utils/Block.getData:()I
        //   311: bipush          15
        //   313: iand           
        //   314: ior            
        //   315: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setPaletteEntry:(II)V
        //   320: iconst_1       
        //   321: ifeq            327
        //   324: goto            355
        //   327: aload_0        
        //   328: getfield        com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter.replacementData:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap;
        //   331: iconst_0       
        //   332: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap.get:(I)Ljava/lang/Object;
        //   337: checkcast       Lcom/viaversion/viabackwards/api/data/MappedLegacyBlockItem;
        //   340: astore          11
        //   342: aload           11
        //   344: ifnull          355
        //   347: aload           11
        //   349: invokevirtual   com/viaversion/viabackwards/api/data/MappedLegacyBlockItem.hasBlockEntityHandler:()Z
        //   352: ifeq            355
        //   355: iinc            6, 1
        //   358: goto            262
        //   361: iconst_1       
        //   362: ifne            368
        //   365: goto            580
        //   368: iconst_0       
        //   369: bipush          16
        //   371: if_icmpge       580
        //   374: iconst_0       
        //   375: bipush          16
        //   377: if_icmpge       574
        //   380: iconst_0       
        //   381: bipush          16
        //   383: if_icmpge       568
        //   386: aload           4
        //   388: iconst_0       
        //   389: iconst_0       
        //   390: iconst_0       
        //   391: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getFlatBlock:(III)I
        //   396: istore          9
        //   398: aload_0        
        //   399: getfield        com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter.replacementData:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap;
        //   402: iconst_0       
        //   403: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap.get:(I)Ljava/lang/Object;
        //   408: checkcast       Lcom/viaversion/viabackwards/api/data/MappedLegacyBlockItem;
        //   411: astore          12
        //   413: aload           12
        //   415: ifnull          562
        //   418: aload           12
        //   420: invokevirtual   com/viaversion/viabackwards/api/data/MappedLegacyBlockItem.hasBlockEntityHandler:()Z
        //   423: ifne            429
        //   426: goto            562
        //   429: new             Lcom/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos;
        //   432: dup            
        //   433: iconst_0       
        //   434: iconst_0       
        //   435: iconst_0       
        //   436: aconst_null    
        //   437: invokespecial   com/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$Pos.<init>:(IIILcom/viaversion/viabackwards/api/rewriters/LegacyBlockItemRewriter$1;)V
        //   440: astore          13
        //   442: aload_2        
        //   443: aload           13
        //   445: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   450: ifeq            456
        //   453: goto            562
        //   456: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   459: dup            
        //   460: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.<init>:()V
        //   463: astore          14
        //   465: aload           14
        //   467: ldc_w           "x"
        //   470: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag;
        //   473: dup            
        //   474: iconst_0       
        //   475: aload_1        
        //   476: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
        //   481: iconst_4       
        //   482: ishl           
        //   483: iadd           
        //   484: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag.<init>:(I)V
        //   487: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.put:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   490: pop            
        //   491: aload           14
        //   493: ldc_w           "y"
        //   496: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag;
        //   499: dup            
        //   500: iconst_0       
        //   501: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag.<init>:(I)V
        //   504: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.put:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   507: pop            
        //   508: aload           14
        //   510: ldc_w           "z"
        //   513: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag;
        //   516: dup            
        //   517: iconst_0       
        //   518: aload_1        
        //   519: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
        //   524: iconst_4       
        //   525: ishl           
        //   526: iadd           
        //   527: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag.<init>:(I)V
        //   530: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.put:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   533: pop            
        //   534: aload           12
        //   536: invokevirtual   com/viaversion/viabackwards/api/data/MappedLegacyBlockItem.getBlockEntityHandler:()Lcom/viaversion/viabackwards/api/data/MappedLegacyBlockItem$BlockEntityHandler;
        //   539: iconst_0       
        //   540: aload           14
        //   542: invokeinterface com/viaversion/viabackwards/api/data/MappedLegacyBlockItem$BlockEntityHandler.handleOrNewCompoundTag:(ILcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   547: pop            
        //   548: aload_1        
        //   549: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
        //   554: aload           14
        //   556: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   561: pop            
        //   562: iinc            8, 1
        //   565: goto            380
        //   568: iinc            7, 1
        //   571: goto            374
        //   574: iinc            6, 1
        //   577: goto            368
        //   580: iinc            3, 1
        //   583: goto            233
        //   586: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected CompoundTag getNamedTag(String string) {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("display", new CompoundTag());
        string = "§r" + string;
        ((CompoundTag)compoundTag.get("display")).put("Name", new StringTag(this.jsonNameFormat ? ChatRewriter.legacyTextToJsonString(string) : string));
        return compoundTag;
    }
    
    static {
        LEGACY_MAPPINGS = new HashMap();
        for (final Map.Entry<String, V> entry : VBMappingDataLoader.loadFromDataDir("legacy-mappings.json").entrySet()) {
            final Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap(8);
            LegacyBlockItemRewriter.LEGACY_MAPPINGS.put(entry.getKey(), int2ObjectOpenHashMap);
            for (final Map.Entry<K, JsonElement> entry2 : ((JsonElement)entry.getValue()).getAsJsonObject().entrySet()) {
                final JsonObject asJsonObject = entry2.getValue().getAsJsonObject();
                final int asInt = asJsonObject.getAsJsonPrimitive("id").getAsInt();
                final JsonPrimitive asJsonPrimitive = asJsonObject.getAsJsonPrimitive("data");
                final short n = (short)((asJsonPrimitive != null) ? asJsonPrimitive.getAsShort() : 0);
                final String asString = asJsonObject.getAsJsonPrimitive("name").getAsString();
                final JsonPrimitive asJsonPrimitive2 = asJsonObject.getAsJsonPrimitive("block");
                final boolean b = asJsonPrimitive2 != null && asJsonPrimitive2.getAsBoolean();
                if (((String)entry2.getKey()).indexOf(45) != -1) {
                    final String[] split = ((String)entry2.getKey()).split("-", 2);
                    final int int1 = Integer.parseInt(split[0]);
                    final int int2 = Integer.parseInt(split[1]);
                    if (asString.contains("%color%")) {
                        for (int i = int1; i <= int2; ++i) {
                            int2ObjectOpenHashMap.put(i, new MappedLegacyBlockItem(asInt, n, asString.replace("%color%", BlockColors.get(i - int1)), b));
                        }
                    }
                    else {
                        final MappedLegacyBlockItem mappedLegacyBlockItem = new MappedLegacyBlockItem(asInt, n, asString, b);
                        for (int j = int1; j <= int2; ++j) {
                            int2ObjectOpenHashMap.put(j, mappedLegacyBlockItem);
                        }
                    }
                }
                else {
                    int2ObjectOpenHashMap.put(Integer.parseInt((String)entry2.getKey()), new MappedLegacyBlockItem(asInt, n, asString, b));
                }
            }
        }
    }
    
    private static final class Pos
    {
        private final int x;
        private final short y;
        private final int z;
        
        private Pos(final int x, final int n, final int z) {
            this.x = x;
            this.y = (short)n;
            this.z = z;
        }
        
        public int getX() {
            return this.x;
        }
        
        public int getY() {
            return this.y;
        }
        
        public int getZ() {
            return this.z;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final Pos pos = (Pos)o;
            return this.x == pos.x && this.y == pos.y && this.z == pos.z;
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * this.x + this.y) + this.z;
        }
        
        @Override
        public String toString() {
            return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
        }
        
        Pos(final int n, final int n2, final int n3, final LegacyBlockItemRewriter$1 object) {
            this(n, n2, n3);
        }
    }
}
