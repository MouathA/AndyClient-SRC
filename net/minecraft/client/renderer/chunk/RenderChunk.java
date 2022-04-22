package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import java.util.concurrent.locks.*;
import java.nio.*;
import net.minecraft.util.*;
import optifine.*;
import net.minecraft.client.renderer.vertex.*;
import Mood.*;
import lumien.chunkanimator.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.*;

public class RenderChunk
{
    private World field_178588_d;
    private final RenderGlobal field_178589_e;
    public static int field_178592_a;
    private BlockPos field_178586_f;
    public CompiledChunk field_178590_b;
    private final ReentrantLock field_178587_g;
    private final ReentrantLock field_178598_h;
    private ChunkCompileTaskGenerator field_178599_i;
    private final int field_178596_j;
    private final FloatBuffer field_178597_k;
    private final VertexBuffer[] field_178594_l;
    public AxisAlignedBB field_178591_c;
    private int field_178595_m;
    private boolean field_178593_n;
    private static final String __OBFID;
    private BlockPos[] positionOffsets16;
    private static EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS;
    private EnumWorldBlockLayer[] blockLayersSingle;
    private boolean isMipmaps;
    private boolean fixBlockLayer;
    private boolean playerUpdate;
    
    static {
        __OBFID = "CL_00002452";
        RenderChunk.ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
    }
    
    public RenderChunk(final World field_178588_d, final RenderGlobal field_178589_e, final BlockPos blockPos, final int field_178596_j) {
        this.positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
        this.blockLayersSingle = new EnumWorldBlockLayer[1];
        this.isMipmaps = Config.isMipmaps();
        this.fixBlockLayer = !Reflector.BetterFoliageClient.exists();
        this.playerUpdate = false;
        this.field_178590_b = CompiledChunk.field_178502_a;
        this.field_178587_g = new ReentrantLock();
        this.field_178598_h = new ReentrantLock();
        this.field_178599_i = null;
        this.field_178597_k = GLAllocation.createDirectFloatBuffer(16);
        this.field_178594_l = new VertexBuffer[EnumWorldBlockLayer.values().length];
        this.field_178595_m = -1;
        this.field_178593_n = true;
        this.field_178588_d = field_178588_d;
        this.field_178589_e = field_178589_e;
        this.field_178596_j = field_178596_j;
        if (!blockPos.equals(this.func_178568_j())) {
            this.func_178576_a(blockPos);
        }
        if (OpenGlHelper.func_176075_f()) {
            while (0 < EnumWorldBlockLayer.values().length) {
                this.field_178594_l[0] = new VertexBuffer(DefaultVertexFormats.field_176600_a);
                int n = 0;
                ++n;
            }
        }
    }
    
    public boolean func_178577_a(final int field_178595_m) {
        if (this.field_178595_m == field_178595_m) {
            return false;
        }
        this.field_178595_m = field_178595_m;
        return true;
    }
    
    public VertexBuffer func_178565_b(final int n) {
        return this.field_178594_l[n];
    }
    
    public void func_178576_a(final BlockPos field_178586_f) {
        this.func_178585_h();
        this.field_178586_f = field_178586_f;
        this.field_178591_c = new AxisAlignedBB(field_178586_f, field_178586_f.add(16, 16, 16));
        this.func_178567_n();
        while (0 < this.positionOffsets16.length) {
            this.positionOffsets16[0] = null;
            final Client instance = Client.INSTANCE;
            if (Client.getModuleByName("BetterChunkLoader").toggled) {
                ChunkAnimator.INSTANCE.animationHandler.setOrigin(this, field_178586_f);
            }
            int n = 0;
            ++n;
        }
    }
    
    public void func_178570_a(final float n, final float n2, final float n3, final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        final CompiledChunk func_178544_c = chunkCompileTaskGenerator.func_178544_c();
        if (func_178544_c.func_178487_c() != null && !func_178544_c.func_178491_b(EnumWorldBlockLayer.TRANSLUCENT)) {
            final WorldRenderer func_179038_a = chunkCompileTaskGenerator.func_178545_d().func_179038_a(EnumWorldBlockLayer.TRANSLUCENT);
            this.func_178573_a(func_179038_a, this.field_178586_f);
            func_179038_a.setVertexState(func_178544_c.func_178487_c());
            this.func_178584_a(EnumWorldBlockLayer.TRANSLUCENT, n, n2, n3, func_179038_a, func_178544_c);
        }
    }
    
    public void func_178581_b(final float p0, final float p1, final float p2, final ChunkCompileTaskGenerator p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   net/minecraft/client/renderer/chunk/CompiledChunk.<init>:()V
        //     7: astore          5
        //     9: aload_0        
        //    10: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178586_f:Lnet/minecraft/util/BlockPos;
        //    13: astore          7
        //    15: aload           7
        //    17: bipush          15
        //    19: bipush          15
        //    21: bipush          15
        //    23: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    26: astore          8
        //    28: aload           4
        //    30: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178540_f:()Ljava/util/concurrent/locks/ReentrantLock;
        //    33: invokevirtual   java/util/concurrent/locks/ReentrantLock.lock:()V
        //    36: aload           4
        //    38: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178546_a:()Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
        //    41: getstatic       net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status.COMPILING:Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
        //    44: if_acmpeq       56
        //    47: aload           4
        //    49: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178540_f:()Ljava/util/concurrent/locks/ReentrantLock;
        //    52: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //    55: return         
        //    56: aload_0        
        //    57: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178588_d:Lnet/minecraft/world/World;
        //    60: ifnull          160
        //    63: aload_0        
        //    64: aload_0        
        //    65: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178588_d:Lnet/minecraft/world/World;
        //    68: aload           7
        //    70: iconst_m1      
        //    71: iconst_m1      
        //    72: iconst_m1      
        //    73: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    76: aload           8
        //    78: iconst_1       
        //    79: iconst_1       
        //    80: iconst_1       
        //    81: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    84: iconst_1       
        //    85: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.createRegionRenderCache:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;I)Lnet/minecraft/client/renderer/RegionRenderCache;
        //    88: astore          9
        //    90: getstatic       optifine/Reflector.MinecraftForgeClient_onRebuildChunk:Loptifine/ReflectorMethod;
        //    93: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //    96: ifeq            129
        //    99: getstatic       optifine/Reflector.MinecraftForgeClient_onRebuildChunk:Loptifine/ReflectorMethod;
        //   102: iconst_3       
        //   103: anewarray       Ljava/lang/Object;
        //   106: dup            
        //   107: iconst_0       
        //   108: aload_0        
        //   109: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178588_d:Lnet/minecraft/world/World;
        //   112: aastore        
        //   113: dup            
        //   114: iconst_1       
        //   115: aload_0        
        //   116: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178586_f:Lnet/minecraft/util/BlockPos;
        //   119: aastore        
        //   120: dup            
        //   121: iconst_2       
        //   122: aload           9
        //   124: aastore        
        //   125: invokestatic    optifine/Reflector.call:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //   128: pop            
        //   129: aload           4
        //   131: aload           5
        //   133: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178543_a:(Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
        //   136: aload           4
        //   138: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178540_f:()Ljava/util/concurrent/locks/ReentrantLock;
        //   141: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   144: goto            169
        //   147: astore          10
        //   149: aload           4
        //   151: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178540_f:()Ljava/util/concurrent/locks/ReentrantLock;
        //   154: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   157: aload           10
        //   159: athrow         
        //   160: aload           4
        //   162: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178540_f:()Ljava/util/concurrent/locks/ReentrantLock;
        //   165: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   168: return         
        //   169: new             Lnet/minecraft/client/renderer/chunk/VisGraph;
        //   172: dup            
        //   173: invokespecial   net/minecraft/client/renderer/chunk/VisGraph.<init>:()V
        //   176: astore          10
        //   178: aload           9
        //   180: invokevirtual   net/minecraft/client/renderer/RegionRenderCache.extendedLevelsInChunkCache:()Z
        //   183: ifne            618
        //   186: getstatic       net/minecraft/client/renderer/chunk/RenderChunk.field_178592_a:I
        //   189: iconst_1       
        //   190: iadd           
        //   191: putstatic       net/minecraft/client/renderer/chunk/RenderChunk.field_178592_a:I
        //   194: aload           7
        //   196: aload           8
        //   198: invokestatic    optifine/BlockPosM.getAllInBoxMutable:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Ljava/lang/Iterable;
        //   201: invokeinterface java/lang/Iterable.iterator:()Ljava/util/Iterator;
        //   206: astore          11
        //   208: getstatic       optifine/Reflector.ForgeBlock_hasTileEntity:Loptifine/ReflectorMethod;
        //   211: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   214: istore          12
        //   216: getstatic       optifine/Reflector.ForgeBlock_canRenderInLayer:Loptifine/ReflectorMethod;
        //   219: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   222: istore          13
        //   224: getstatic       optifine/Reflector.ForgeHooksClient_setRenderLayer:Loptifine/ReflectorMethod;
        //   227: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   230: istore          14
        //   232: goto            530
        //   235: aload           11
        //   237: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   242: checkcast       Loptifine/BlockPosM;
        //   245: astore          15
        //   247: aload           9
        //   249: aload           15
        //   251: invokevirtual   net/minecraft/client/renderer/RegionRenderCache.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   254: astore          16
        //   256: aload           16
        //   258: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   263: astore          17
        //   265: aload           17
        //   267: invokevirtual   net/minecraft/block/Block.isOpaqueCube:()Z
        //   270: ifeq            280
        //   273: aload           10
        //   275: aload           15
        //   277: invokevirtual   net/minecraft/client/renderer/chunk/VisGraph.func_178606_a:(Lnet/minecraft/util/BlockPos;)V
        //   280: aload           16
        //   282: invokestatic    optifine/ReflectorForge.blockHasTileEntity:(Lnet/minecraft/block/state/IBlockState;)Z
        //   285: ifeq            327
        //   288: aload           9
        //   290: new             Lnet/minecraft/util/BlockPos;
        //   293: dup            
        //   294: aload           15
        //   296: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/util/Vec3i;)V
        //   299: invokevirtual   net/minecraft/client/renderer/RegionRenderCache.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //   302: astore          18
        //   304: aload           18
        //   306: ifnull          327
        //   309: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //   312: aload           18
        //   314: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.hasSpecialRenderer:(Lnet/minecraft/tileentity/TileEntity;)Z
        //   317: ifeq            327
        //   320: aload           5
        //   322: aload           18
        //   324: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178490_a:(Lnet/minecraft/tileentity/TileEntity;)V
        //   327: iload           13
        //   329: ifeq            340
        //   332: getstatic       net/minecraft/client/renderer/chunk/RenderChunk.ENUM_WORLD_BLOCK_LAYERS:[Lnet/minecraft/util/EnumWorldBlockLayer;
        //   335: astore          18
        //   337: goto            355
        //   340: aload_0        
        //   341: getfield        net/minecraft/client/renderer/chunk/RenderChunk.blockLayersSingle:[Lnet/minecraft/util/EnumWorldBlockLayer;
        //   344: astore          18
        //   346: aload           18
        //   348: iconst_0       
        //   349: aload           17
        //   351: invokevirtual   net/minecraft/block/Block.getBlockLayer:()Lnet/minecraft/util/EnumWorldBlockLayer;
        //   354: aastore        
        //   355: goto            523
        //   358: aload           18
        //   360: iconst_0       
        //   361: aaload         
        //   362: astore          20
        //   364: iload           13
        //   366: ifeq            396
        //   369: aload           17
        //   371: getstatic       optifine/Reflector.ForgeBlock_canRenderInLayer:Loptifine/ReflectorMethod;
        //   374: iconst_1       
        //   375: anewarray       Ljava/lang/Object;
        //   378: dup            
        //   379: iconst_0       
        //   380: aload           20
        //   382: aastore        
        //   383: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   386: istore          21
        //   388: iload           21
        //   390: ifne            396
        //   393: goto            520
        //   396: iload           14
        //   398: ifeq            416
        //   401: getstatic       optifine/Reflector.ForgeHooksClient_setRenderLayer:Loptifine/ReflectorMethod;
        //   404: iconst_1       
        //   405: anewarray       Ljava/lang/Object;
        //   408: dup            
        //   409: iconst_0       
        //   410: aload           20
        //   412: aastore        
        //   413: invokestatic    optifine/Reflector.callVoid:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //   416: aload_0        
        //   417: getfield        net/minecraft/client/renderer/chunk/RenderChunk.fixBlockLayer:Z
        //   420: ifeq            433
        //   423: aload_0        
        //   424: aload           17
        //   426: aload           20
        //   428: invokespecial   net/minecraft/client/renderer/chunk/RenderChunk.fixBlockLayer:(Lnet/minecraft/block/Block;Lnet/minecraft/util/EnumWorldBlockLayer;)Lnet/minecraft/util/EnumWorldBlockLayer;
        //   431: astore          20
        //   433: aload           20
        //   435: invokevirtual   net/minecraft/util/EnumWorldBlockLayer.ordinal:()I
        //   438: istore          21
        //   440: aload           17
        //   442: invokevirtual   net/minecraft/block/Block.getRenderType:()I
        //   445: iconst_m1      
        //   446: if_icmpeq       520
        //   449: aload           4
        //   451: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178545_d:()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
        //   454: iload           21
        //   456: invokevirtual   net/minecraft/client/renderer/RegionRenderCacheBuilder.func_179039_a:(I)Lnet/minecraft/client/renderer/WorldRenderer;
        //   459: astore          22
        //   461: aload           22
        //   463: aload           20
        //   465: invokevirtual   net/minecraft/client/renderer/WorldRenderer.setBlockLayer:(Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   468: aload           5
        //   470: aload           20
        //   472: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178492_d:(Lnet/minecraft/util/EnumWorldBlockLayer;)Z
        //   475: ifne            493
        //   478: aload           5
        //   480: aload           20
        //   482: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178493_c:(Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   485: aload_0        
        //   486: aload           22
        //   488: aload           7
        //   490: invokespecial   net/minecraft/client/renderer/chunk/RenderChunk.func_178573_a:(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/util/BlockPos;)V
        //   493: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   496: invokevirtual   net/minecraft/client/Minecraft.getBlockRendererDispatcher:()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
        //   499: aload           16
        //   501: aload           15
        //   503: aload           9
        //   505: aload           22
        //   507: invokevirtual   net/minecraft/client/renderer/BlockRendererDispatcher.func_175018_a:(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/WorldRenderer;)Z
        //   510: ifeq            520
        //   513: aload           5
        //   515: aload           20
        //   517: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178486_a:(Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   520: iinc            19, 1
        //   523: iconst_0       
        //   524: aload           18
        //   526: arraylength    
        //   527: if_icmplt       358
        //   530: aload           11
        //   532: invokeinterface java/util/Iterator.hasNext:()Z
        //   537: ifne            235
        //   540: getstatic       net/minecraft/client/renderer/chunk/RenderChunk.ENUM_WORLD_BLOCK_LAYERS:[Lnet/minecraft/util/EnumWorldBlockLayer;
        //   543: astore          15
        //   545: aload           15
        //   547: arraylength    
        //   548: istore          16
        //   550: goto            612
        //   553: aload           15
        //   555: iconst_0       
        //   556: aaload         
        //   557: astore          18
        //   559: aload           5
        //   561: aload           18
        //   563: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178492_d:(Lnet/minecraft/util/EnumWorldBlockLayer;)Z
        //   566: ifeq            609
        //   569: invokestatic    optifine/Config.isShaders:()Z
        //   572: ifeq            588
        //   575: aload           4
        //   577: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178545_d:()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
        //   580: aload           18
        //   582: invokevirtual   net/minecraft/client/renderer/RegionRenderCacheBuilder.func_179038_a:(Lnet/minecraft/util/EnumWorldBlockLayer;)Lnet/minecraft/client/renderer/WorldRenderer;
        //   585: invokestatic    shadersmod/client/SVertexBuilder.calcNormalChunkLayer:(Lnet/minecraft/client/renderer/WorldRenderer;)V
        //   588: aload_0        
        //   589: aload           18
        //   591: fload_1        
        //   592: fload_2        
        //   593: fload_3        
        //   594: aload           4
        //   596: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.func_178545_d:()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
        //   599: aload           18
        //   601: invokevirtual   net/minecraft/client/renderer/RegionRenderCacheBuilder.func_179038_a:(Lnet/minecraft/util/EnumWorldBlockLayer;)Lnet/minecraft/client/renderer/WorldRenderer;
        //   604: aload           5
        //   606: invokespecial   net/minecraft/client/renderer/chunk/RenderChunk.func_178584_a:(Lnet/minecraft/util/EnumWorldBlockLayer;FFFLnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
        //   609: iinc            17, 1
        //   612: iconst_0       
        //   613: iload           16
        //   615: if_icmplt       553
        //   618: aload           5
        //   620: aload           10
        //   622: invokevirtual   net/minecraft/client/renderer/chunk/VisGraph.func_178607_a:()Lnet/minecraft/client/renderer/chunk/SetVisibility;
        //   625: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178488_a:(Lnet/minecraft/client/renderer/chunk/SetVisibility;)V
        //   628: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void func_178578_b() {
        this.field_178587_g.lock();
        if (this.field_178599_i != null && this.field_178599_i.func_178546_a() != ChunkCompileTaskGenerator.Status.DONE) {
            this.field_178599_i.func_178542_e();
            this.field_178599_i = null;
        }
        this.field_178587_g.unlock();
    }
    
    public ReentrantLock func_178579_c() {
        return this.field_178587_g;
    }
    
    public ChunkCompileTaskGenerator func_178574_d() {
        this.field_178587_g.lock();
        this.func_178578_b();
        this.field_178599_i = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
        final ChunkCompileTaskGenerator field_178599_i = this.field_178599_i;
        this.field_178587_g.unlock();
        return field_178599_i;
    }
    
    public ChunkCompileTaskGenerator func_178582_e() {
        this.field_178587_g.lock();
        if (this.field_178599_i != null && this.field_178599_i.func_178546_a() == ChunkCompileTaskGenerator.Status.PENDING) {
            final ChunkCompileTaskGenerator chunkCompileTaskGenerator = null;
            this.field_178587_g.unlock();
            return chunkCompileTaskGenerator;
        }
        if (this.field_178599_i != null && this.field_178599_i.func_178546_a() != ChunkCompileTaskGenerator.Status.DONE) {
            this.field_178599_i.func_178542_e();
            this.field_178599_i = null;
        }
        (this.field_178599_i = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY)).func_178543_a(this.field_178590_b);
        final ChunkCompileTaskGenerator field_178599_i = this.field_178599_i;
        this.field_178587_g.unlock();
        return field_178599_i;
    }
    
    private void func_178573_a(final WorldRenderer worldRenderer, final BlockPos blockPos) {
        worldRenderer.startDrawing(7);
        worldRenderer.setVertexFormat(DefaultVertexFormats.field_176600_a);
        worldRenderer.setTranslation(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
    }
    
    private void func_178584_a(final EnumWorldBlockLayer enumWorldBlockLayer, final float n, final float n2, final float n3, final WorldRenderer worldRenderer, final CompiledChunk compiledChunk) {
        if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunk.func_178491_b(enumWorldBlockLayer)) {
            compiledChunk.func_178494_a(worldRenderer.getVertexState(n, n2, n3));
        }
        worldRenderer.draw();
    }
    
    private void func_178567_n() {
        final float n = 1.000001f;
        GlStateManager.translate(-8.0f, -8.0f, -8.0f);
        GlStateManager.scale(n, n, n);
        GlStateManager.translate(8.0f, 8.0f, 8.0f);
        GlStateManager.getFloat(2982, this.field_178597_k);
    }
    
    public void func_178572_f() {
        GlStateManager.multMatrix(this.field_178597_k);
    }
    
    public CompiledChunk func_178571_g() {
        return this.field_178590_b;
    }
    
    public void func_178580_a(final CompiledChunk field_178590_b) {
        this.field_178598_h.lock();
        this.field_178590_b = field_178590_b;
        this.field_178598_h.unlock();
    }
    
    public void func_178585_h() {
        this.func_178578_b();
        this.field_178590_b = CompiledChunk.field_178502_a;
    }
    
    public void func_178566_a() {
        this.func_178585_h();
        this.field_178588_d = null;
        while (0 < EnumWorldBlockLayer.values().length) {
            if (this.field_178594_l[0] != null) {
                this.field_178594_l[0].func_177362_c();
            }
            int n = 0;
            ++n;
        }
    }
    
    public BlockPos func_178568_j() {
        return this.field_178586_f;
    }
    
    public boolean func_178583_l() {
        this.field_178587_g.lock();
        final boolean b = this.field_178599_i == null || this.field_178599_i.func_178546_a() == ChunkCompileTaskGenerator.Status.PENDING;
        this.field_178587_g.unlock();
        return b;
    }
    
    public void func_178575_a(final boolean field_178593_n) {
        this.field_178593_n = field_178593_n;
        if (this.field_178593_n) {
            if (this.isWorldPlayerUpdate()) {
                this.playerUpdate = true;
            }
        }
        else {
            this.playerUpdate = false;
        }
    }
    
    public boolean func_178569_m() {
        return this.field_178593_n;
    }
    
    public BlockPos getPositionOffset16(final EnumFacing enumFacing) {
        final int index = enumFacing.getIndex();
        BlockPos offset = this.positionOffsets16[index];
        if (offset == null) {
            offset = this.func_178568_j().offset(enumFacing, 16);
            this.positionOffsets16[index] = offset;
        }
        return offset;
    }
    
    private boolean isWorldPlayerUpdate() {
        return this.field_178588_d instanceof WorldClient && ((WorldClient)this.field_178588_d).isPlayerUpdate();
    }
    
    public boolean isPlayerUpdate() {
        return this.playerUpdate;
    }
    
    protected RegionRenderCache createRegionRenderCache(final World world, final BlockPos blockPos, final BlockPos blockPos2, final int n) {
        return new RegionRenderCache(world, blockPos, blockPos2, n);
    }
    
    private EnumWorldBlockLayer fixBlockLayer(final Block block, final EnumWorldBlockLayer enumWorldBlockLayer) {
        if (this.isMipmaps) {
            if (enumWorldBlockLayer == EnumWorldBlockLayer.CUTOUT) {
                if (block instanceof BlockRedstoneWire) {
                    return enumWorldBlockLayer;
                }
                if (block instanceof BlockCactus) {
                    return enumWorldBlockLayer;
                }
                return EnumWorldBlockLayer.CUTOUT_MIPPED;
            }
        }
        else if (enumWorldBlockLayer == EnumWorldBlockLayer.CUTOUT_MIPPED) {
            return EnumWorldBlockLayer.CUTOUT;
        }
        return enumWorldBlockLayer;
    }
    
    public BlockPos getPosition() {
        return this.field_178586_f;
    }
}
