package net.minecraft.client.renderer;

import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.shader.*;
import net.minecraft.entity.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.world.chunk.*;
import net.minecraft.client.renderer.culling.*;
import javax.vecmath.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.settings.*;
import net.minecraft.world.border.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.*;
import net.minecraft.client.particle.*;
import optifine.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;

public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener
{
    private static final Logger logger;
    private static final ResourceLocation locationMoonPhasesPng;
    private static final ResourceLocation locationSunPng;
    private static final ResourceLocation locationCloudsPng;
    private static final ResourceLocation locationEndSkyPng;
    private static final ResourceLocation field_175006_g;
    public final Minecraft mc;
    private final TextureManager renderEngine;
    private final RenderManager field_175010_j;
    private WorldClient theWorld;
    private Set field_175009_l;
    private List glRenderLists;
    private ViewFrustum field_175008_n;
    private int starGLCallList;
    private int glSkyList;
    private int glSkyList2;
    private VertexFormat field_175014_r;
    private VertexBuffer field_175013_s;
    private VertexBuffer field_175012_t;
    private VertexBuffer field_175011_u;
    private int cloudTickCounter;
    public final Map damagedBlocks;
    private final Map mapSoundPositions;
    private final TextureAtlasSprite[] destroyBlockIcons;
    private Framebuffer field_175015_z;
    private ShaderGroup field_174991_A;
    private double field_174992_B;
    private double field_174993_C;
    private double field_174987_D;
    private int field_174988_E;
    private int field_174989_F;
    private int field_174990_G;
    private double field_174997_H;
    private double field_174998_I;
    private double field_174999_J;
    private double field_175000_K;
    private double field_174994_L;
    private final ChunkRenderDispatcher field_174995_M;
    private ChunkRenderContainer field_174996_N;
    private int renderDistanceChunks;
    private int renderEntitiesStartupCounter;
    private int countEntitiesTotal;
    private int countEntitiesRendered;
    private int countEntitiesHidden;
    private boolean field_175002_T;
    private ClippingHelper field_175001_U;
    private final Vector4f[] field_175004_V;
    private final Vector3d field_175003_W;
    private boolean field_175005_X;
    IRenderChunkFactory field_175007_a;
    private double prevRenderSortX;
    private double prevRenderSortY;
    private double prevRenderSortZ;
    public boolean displayListEntitiesDirty;
    private static final String __OBFID;
    private CloudRenderer cloudRenderer;
    public Entity renderedEntity;
    public Set chunksToResortTransparency;
    public Set chunksToUpdateForced;
    private Deque visibilityDeque;
    private List renderInfosEntities;
    private List renderInfosTileEntities;
    private List renderInfosNormal;
    private List renderInfosEntitiesNormal;
    private List renderInfosTileEntitiesNormal;
    private List renderInfosShadow;
    private List renderInfosEntitiesShadow;
    private List renderInfosTileEntitiesShadow;
    private int renderDistance;
    private int renderDistanceSq;
    private static final Set SET_ALL_FACINGS;
    private int countTileEntitiesRendered;
    
    static {
        __OBFID = "CL_00000954";
        logger = LogManager.getLogger();
        locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
        locationSunPng = new ResourceLocation("textures/environment/sun.png");
        locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
        locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
        field_175006_g = new ResourceLocation("textures/misc/forcefield.png");
        SET_ALL_FACINGS = Collections.unmodifiableSet((Set<?>)new HashSet<Object>(Arrays.asList(EnumFacing.VALUES)));
    }
    
    public RenderGlobal(final Minecraft mc) {
        this.field_175009_l = Sets.newLinkedHashSet();
        this.glRenderLists = Lists.newArrayListWithCapacity(69696);
        this.starGLCallList = -1;
        this.glSkyList = -1;
        this.glSkyList2 = -1;
        this.damagedBlocks = Maps.newHashMap();
        this.mapSoundPositions = Maps.newHashMap();
        this.destroyBlockIcons = new TextureAtlasSprite[10];
        this.field_174992_B = Double.MIN_VALUE;
        this.field_174993_C = Double.MIN_VALUE;
        this.field_174987_D = Double.MIN_VALUE;
        this.field_174988_E = Integer.MIN_VALUE;
        this.field_174989_F = Integer.MIN_VALUE;
        this.field_174990_G = Integer.MIN_VALUE;
        this.field_174997_H = Double.MIN_VALUE;
        this.field_174998_I = Double.MIN_VALUE;
        this.field_174999_J = Double.MIN_VALUE;
        this.field_175000_K = Double.MIN_VALUE;
        this.field_174994_L = Double.MIN_VALUE;
        this.field_174995_M = new ChunkRenderDispatcher();
        this.renderDistanceChunks = -1;
        this.renderEntitiesStartupCounter = 2;
        this.field_175002_T = false;
        this.field_175004_V = new Vector4f[8];
        this.field_175003_W = new Vector3d();
        this.field_175005_X = false;
        this.displayListEntitiesDirty = true;
        this.chunksToResortTransparency = new LinkedHashSet();
        this.chunksToUpdateForced = new LinkedHashSet();
        this.visibilityDeque = new ArrayDeque();
        this.renderInfosEntities = new ArrayList(1024);
        this.renderInfosTileEntities = new ArrayList(1024);
        this.renderInfosNormal = new ArrayList(1024);
        this.renderInfosEntitiesNormal = new ArrayList(1024);
        this.renderInfosTileEntitiesNormal = new ArrayList(1024);
        this.renderInfosShadow = new ArrayList(1024);
        this.renderInfosEntitiesShadow = new ArrayList(1024);
        this.renderInfosTileEntitiesShadow = new ArrayList(1024);
        this.renderDistance = 0;
        this.renderDistanceSq = 0;
        this.cloudRenderer = new CloudRenderer(mc);
        this.mc = mc;
        this.field_175010_j = mc.getRenderManager();
        (this.renderEngine = mc.getTextureManager()).bindTexture(RenderGlobal.field_175006_g);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GlStateManager.func_179144_i(0);
        this.func_174971_n();
        this.field_175005_X = OpenGlHelper.func_176075_f();
        if (this.field_175005_X) {
            this.field_174996_N = new VboRenderList();
            this.field_175007_a = new VboChunkFactory();
        }
        else {
            this.field_174996_N = new RenderList();
            this.field_175007_a = new ListChunkFactory();
        }
        (this.field_175014_r = new VertexFormat()).func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        this.func_174963_q();
        this.func_174980_p();
        this.func_174964_o();
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.func_174971_n();
    }
    
    private void func_174971_n() {
        final TextureMap textureMapBlocks = this.mc.getTextureMapBlocks();
        while (0 < this.destroyBlockIcons.length) {
            this.destroyBlockIcons[0] = textureMapBlocks.getAtlasSprite("minecraft:blocks/destroy_stage_" + 0);
            int n = 0;
            ++n;
        }
    }
    
    public void func_174966_b() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            171
        //     6: invokestatic    net/minecraft/client/shader/ShaderLinkHelper.getStaticShaderLinkHelper:()Lnet/minecraft/client/shader/ShaderLinkHelper;
        //     9: new             Lnet/minecraft/util/ResourceLocation;
        //    12: dup            
        //    13: ldc_w           "shaders/post/entity_outline.json"
        //    16: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    19: astore_1       
        //    20: aload_0        
        //    21: new             Lnet/minecraft/client/shader/ShaderGroup;
        //    24: dup            
        //    25: aload_0        
        //    26: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    29: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //    32: aload_0        
        //    33: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    36: invokevirtual   net/minecraft/client/Minecraft.getResourceManager:()Lnet/minecraft/client/resources/IResourceManager;
        //    39: aload_0        
        //    40: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    43: invokevirtual   net/minecraft/client/Minecraft.getFramebuffer:()Lnet/minecraft/client/shader/Framebuffer;
        //    46: aload_1        
        //    47: invokespecial   net/minecraft/client/shader/ShaderGroup.<init>:(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/resources/IResourceManager;Lnet/minecraft/client/shader/Framebuffer;Lnet/minecraft/util/ResourceLocation;)V
        //    50: putfield        net/minecraft/client/renderer/RenderGlobal.field_174991_A:Lnet/minecraft/client/shader/ShaderGroup;
        //    53: aload_0        
        //    54: getfield        net/minecraft/client/renderer/RenderGlobal.field_174991_A:Lnet/minecraft/client/shader/ShaderGroup;
        //    57: aload_0        
        //    58: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    61: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //    64: aload_0        
        //    65: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    68: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //    71: invokevirtual   net/minecraft/client/shader/ShaderGroup.createBindFramebuffers:(II)V
        //    74: aload_0        
        //    75: aload_0        
        //    76: getfield        net/minecraft/client/renderer/RenderGlobal.field_174991_A:Lnet/minecraft/client/shader/ShaderGroup;
        //    79: ldc_w           "final"
        //    82: invokevirtual   net/minecraft/client/shader/ShaderGroup.func_177066_a:(Ljava/lang/String;)Lnet/minecraft/client/shader/Framebuffer;
        //    85: putfield        net/minecraft/client/renderer/RenderGlobal.field_175015_z:Lnet/minecraft/client/shader/Framebuffer;
        //    88: goto            181
        //    91: astore_2       
        //    92: getstatic       net/minecraft/client/renderer/RenderGlobal.logger:Lorg/apache/logging/log4j/Logger;
        //    95: new             Ljava/lang/StringBuilder;
        //    98: dup            
        //    99: ldc_w           "Failed to load shader: "
        //   102: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   105: aload_1        
        //   106: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   109: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   112: aload_2        
        //   113: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   118: aload_0        
        //   119: aconst_null    
        //   120: putfield        net/minecraft/client/renderer/RenderGlobal.field_174991_A:Lnet/minecraft/client/shader/ShaderGroup;
        //   123: aload_0        
        //   124: aconst_null    
        //   125: putfield        net/minecraft/client/renderer/RenderGlobal.field_175015_z:Lnet/minecraft/client/shader/Framebuffer;
        //   128: goto            181
        //   131: astore_2       
        //   132: getstatic       net/minecraft/client/renderer/RenderGlobal.logger:Lorg/apache/logging/log4j/Logger;
        //   135: new             Ljava/lang/StringBuilder;
        //   138: dup            
        //   139: ldc_w           "Failed to load shader: "
        //   142: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   145: aload_1        
        //   146: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   149: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   152: aload_2        
        //   153: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   158: aload_0        
        //   159: aconst_null    
        //   160: putfield        net/minecraft/client/renderer/RenderGlobal.field_174991_A:Lnet/minecraft/client/shader/ShaderGroup;
        //   163: aload_0        
        //   164: aconst_null    
        //   165: putfield        net/minecraft/client/renderer/RenderGlobal.field_175015_z:Lnet/minecraft/client/shader/Framebuffer;
        //   168: goto            181
        //   171: aload_0        
        //   172: aconst_null    
        //   173: putfield        net/minecraft/client/renderer/RenderGlobal.field_174991_A:Lnet/minecraft/client/shader/ShaderGroup;
        //   176: aload_0        
        //   177: aconst_null    
        //   178: putfield        net/minecraft/client/renderer/RenderGlobal.field_175015_z:Lnet/minecraft/client/shader/Framebuffer;
        //   181: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0181 (coming from #0088).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void func_174975_c() {
        if (this.func_174985_d()) {
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            this.field_175015_z.func_178038_a(this.mc.displayWidth, this.mc.displayHeight, false);
        }
    }
    
    protected boolean func_174985_d() {
        return !Config.isFastRender() && !Config.isShaders() && !Config.isAntialiasing() && (this.field_175015_z != null && this.field_174991_A != null && Minecraft.thePlayer != null && Minecraft.thePlayer.func_175149_v() && this.mc.gameSettings.field_178883_an.getIsKeyPressed());
    }
    
    private void func_174964_o() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.field_175011_u != null) {
            this.field_175011_u.func_177362_c();
        }
        if (this.glSkyList2 >= 0) {
            GLAllocation.deleteDisplayLists(this.glSkyList2);
            this.glSkyList2 = -1;
        }
        if (this.field_175005_X) {
            this.field_175011_u = new VertexBuffer(this.field_175014_r);
            this.func_174968_a(worldRenderer, -16.0f, true);
            worldRenderer.draw();
            worldRenderer.reset();
            this.field_175011_u.func_177360_a(worldRenderer.func_178966_f(), worldRenderer.func_178976_e());
        }
        else {
            GL11.glNewList(this.glSkyList2 = GLAllocation.generateDisplayLists(1), 4864);
            this.func_174968_a(worldRenderer, -16.0f, true);
            instance.draw();
        }
    }
    
    private void func_174980_p() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.field_175012_t != null) {
            this.field_175012_t.func_177362_c();
        }
        if (this.glSkyList >= 0) {
            GLAllocation.deleteDisplayLists(this.glSkyList);
            this.glSkyList = -1;
        }
        if (this.field_175005_X) {
            this.field_175012_t = new VertexBuffer(this.field_175014_r);
            this.func_174968_a(worldRenderer, 16.0f, false);
            worldRenderer.draw();
            worldRenderer.reset();
            this.field_175012_t.func_177360_a(worldRenderer.func_178966_f(), worldRenderer.func_178976_e());
        }
        else {
            GL11.glNewList(this.glSkyList = GLAllocation.generateDisplayLists(1), 4864);
            this.func_174968_a(worldRenderer, 16.0f, false);
            instance.draw();
        }
    }
    
    private void func_174968_a(final WorldRenderer worldRenderer, final float n, final boolean b) {
        worldRenderer.startDrawingQuads();
        while (-384 <= 384) {
            while (-384 <= 384) {
                float n2 = -384;
                float n3 = -320;
                if (b) {
                    n3 = -384;
                    n2 = -320;
                }
                worldRenderer.addVertex(n2, n, -384);
                worldRenderer.addVertex(n3, n, -384);
                worldRenderer.addVertex(n3, n, -320);
                worldRenderer.addVertex(n2, n, -320);
                final int n4;
                n4 += 64;
            }
            final int n5;
            n5 += 64;
        }
    }
    
    private void func_174963_q() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.field_175013_s != null) {
            this.field_175013_s.func_177362_c();
        }
        if (this.starGLCallList >= 0) {
            GLAllocation.deleteDisplayLists(this.starGLCallList);
            this.starGLCallList = -1;
        }
        if (this.field_175005_X) {
            this.field_175013_s = new VertexBuffer(this.field_175014_r);
            this.func_180444_a(worldRenderer);
            worldRenderer.draw();
            worldRenderer.reset();
            this.field_175013_s.func_177360_a(worldRenderer.func_178966_f(), worldRenderer.func_178976_e());
        }
        else {
            GL11.glNewList(this.starGLCallList = GLAllocation.generateDisplayLists(1), 4864);
            this.func_180444_a(worldRenderer);
            instance.draw();
        }
    }
    
    private void func_180444_a(final WorldRenderer worldRenderer) {
        final Random random = new Random(10842L);
        worldRenderer.startDrawingQuads();
        while (0 < 1500) {
            final double n = random.nextFloat() * 2.0f - 1.0f;
            final double n2 = random.nextFloat() * 2.0f - 1.0f;
            final double n3 = random.nextFloat() * 2.0f - 1.0f;
            final double n4 = 0.15f + random.nextFloat() * 0.1f;
            final double n5 = n * n + n2 * n2 + n3 * n3;
            if (n5 < 1.0 && n5 > 0.01) {
                final double n6 = 1.0 / Math.sqrt(n5);
                final double n7 = n * n6;
                final double n8 = n2 * n6;
                final double n9 = n3 * n6;
                final double n10 = n7 * 100.0;
                final double n11 = n8 * 100.0;
                final double n12 = n9 * 100.0;
                final double atan2 = Math.atan2(n7, n9);
                final double sin = Math.sin(atan2);
                final double cos = Math.cos(atan2);
                final double atan3 = Math.atan2(Math.sqrt(n7 * n7 + n9 * n9), n8);
                final double sin2 = Math.sin(atan3);
                final double cos2 = Math.cos(atan3);
                final double n13 = random.nextDouble() * 3.141592653589793 * 2.0;
                final double sin3 = Math.sin(n13);
                final double cos3 = Math.cos(n13);
                while (0 < 4) {
                    final double n14 = -1 * n4;
                    final double n15 = -1 * n4;
                    final double n16 = n14 * cos3 - n15 * sin3;
                    final double n17 = n15 * cos3 + n14 * sin3;
                    final double n18 = n16 * sin2 + 0.0 * cos2;
                    final double n19 = 0.0 * sin2 - n16 * cos2;
                    worldRenderer.addVertex(n10 + (n19 * sin - n17 * cos), n11 + n18, n12 + (n17 * sin + n19 * cos));
                    int n20 = 0;
                    ++n20;
                }
            }
            int n21 = 0;
            ++n21;
        }
    }
    
    public void setWorldAndLoadRenderers(final WorldClient theWorld) {
        if (this.theWorld != null) {
            this.theWorld.removeWorldAccess(this);
        }
        this.field_174992_B = Double.MIN_VALUE;
        this.field_174993_C = Double.MIN_VALUE;
        this.field_174987_D = Double.MIN_VALUE;
        this.field_174988_E = Integer.MIN_VALUE;
        this.field_174989_F = Integer.MIN_VALUE;
        this.field_174990_G = Integer.MIN_VALUE;
        this.field_175010_j.set(theWorld);
        this.theWorld = theWorld;
        Config.isDynamicLights();
        if (theWorld != null) {
            theWorld.addWorldAccess(this);
            this.loadRenderers();
        }
    }
    
    public void loadRenderers() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //     4: ifnull          261
        //     7: aload_0        
        //     8: iconst_1       
        //     9: putfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //    12: getstatic       net/minecraft/init/Blocks.leaves:Lnet/minecraft/block/BlockLeaves;
        //    15: invokestatic    optifine/Config.isTreesFancy:()Z
        //    18: invokevirtual   net/minecraft/block/BlockLeaves.setGraphicsLevel:(Z)V
        //    21: getstatic       net/minecraft/init/Blocks.leaves2:Lnet/minecraft/block/BlockLeaves;
        //    24: invokestatic    optifine/Config.isTreesFancy:()Z
        //    27: invokevirtual   net/minecraft/block/BlockLeaves.setGraphicsLevel:(Z)V
        //    30: invokestatic    optifine/Config.isDynamicLights:()Z
        //    33: aload_0        
        //    34: aload_0        
        //    35: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    38: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    41: pop            
        //    42: getstatic       net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //    45: putfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //    48: aload_0        
        //    49: aload_0        
        //    50: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //    53: bipush          16
        //    55: imul           
        //    56: putfield        net/minecraft/client/renderer/RenderGlobal.renderDistance:I
        //    59: aload_0        
        //    60: aload_0        
        //    61: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistance:I
        //    64: aload_0        
        //    65: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistance:I
        //    68: imul           
        //    69: putfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceSq:I
        //    72: aload_0        
        //    73: getfield        net/minecraft/client/renderer/RenderGlobal.field_175005_X:Z
        //    76: istore_1       
        //    77: aload_0        
        //    78: invokestatic    net/minecraft/client/renderer/OpenGlHelper.func_176075_f:()Z
        //    81: putfield        net/minecraft/client/renderer/RenderGlobal.field_175005_X:Z
        //    84: iload_1        
        //    85: ifeq            120
        //    88: aload_0        
        //    89: getfield        net/minecraft/client/renderer/RenderGlobal.field_175005_X:Z
        //    92: ifne            120
        //    95: aload_0        
        //    96: new             Lnet/minecraft/client/renderer/RenderList;
        //    99: dup            
        //   100: invokespecial   net/minecraft/client/renderer/RenderList.<init>:()V
        //   103: putfield        net/minecraft/client/renderer/RenderGlobal.field_174996_N:Lnet/minecraft/client/renderer/ChunkRenderContainer;
        //   106: aload_0        
        //   107: new             Lnet/minecraft/client/renderer/chunk/ListChunkFactory;
        //   110: dup            
        //   111: invokespecial   net/minecraft/client/renderer/chunk/ListChunkFactory.<init>:()V
        //   114: putfield        net/minecraft/client/renderer/RenderGlobal.field_175007_a:Lnet/minecraft/client/renderer/chunk/IRenderChunkFactory;
        //   117: goto            153
        //   120: iload_1        
        //   121: ifne            153
        //   124: aload_0        
        //   125: getfield        net/minecraft/client/renderer/RenderGlobal.field_175005_X:Z
        //   128: ifeq            153
        //   131: aload_0        
        //   132: new             Lnet/minecraft/client/renderer/VboRenderList;
        //   135: dup            
        //   136: invokespecial   net/minecraft/client/renderer/VboRenderList.<init>:()V
        //   139: putfield        net/minecraft/client/renderer/RenderGlobal.field_174996_N:Lnet/minecraft/client/renderer/ChunkRenderContainer;
        //   142: aload_0        
        //   143: new             Lnet/minecraft/client/renderer/chunk/VboChunkFactory;
        //   146: dup            
        //   147: invokespecial   net/minecraft/client/renderer/chunk/VboChunkFactory.<init>:()V
        //   150: putfield        net/minecraft/client/renderer/RenderGlobal.field_175007_a:Lnet/minecraft/client/renderer/chunk/IRenderChunkFactory;
        //   153: iload_1        
        //   154: aload_0        
        //   155: getfield        net/minecraft/client/renderer/RenderGlobal.field_175005_X:Z
        //   158: if_icmpeq       173
        //   161: aload_0        
        //   162: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_174963_q:()V
        //   165: aload_0        
        //   166: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_174980_p:()V
        //   169: aload_0        
        //   170: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_174964_o:()V
        //   173: aload_0        
        //   174: getfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   177: ifnull          187
        //   180: aload_0        
        //   181: getfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   184: invokevirtual   net/minecraft/client/renderer/ViewFrustum.func_178160_a:()V
        //   187: aload_0        
        //   188: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174986_e:()V
        //   191: aload_0        
        //   192: new             Lnet/minecraft/client/renderer/ViewFrustum;
        //   195: dup            
        //   196: aload_0        
        //   197: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   200: aload_0        
        //   201: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   204: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   207: pop            
        //   208: getstatic       net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //   211: aload_0        
        //   212: aload_0        
        //   213: getfield        net/minecraft/client/renderer/RenderGlobal.field_175007_a:Lnet/minecraft/client/renderer/chunk/IRenderChunkFactory;
        //   216: invokespecial   net/minecraft/client/renderer/ViewFrustum.<init>:(Lnet/minecraft/world/World;ILnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/client/renderer/chunk/IRenderChunkFactory;)V
        //   219: putfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   222: aload_0        
        //   223: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   226: ifnull          256
        //   229: aload_0        
        //   230: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   233: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   236: astore_2       
        //   237: aload_2        
        //   238: ifnull          256
        //   241: aload_0        
        //   242: getfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   245: aload_2        
        //   246: getfield        net/minecraft/entity/Entity.posX:D
        //   249: aload_2        
        //   250: getfield        net/minecraft/entity/Entity.posZ:D
        //   253: invokevirtual   net/minecraft/client/renderer/ViewFrustum.func_178163_a:(DD)V
        //   256: aload_0        
        //   257: iconst_2       
        //   258: putfield        net/minecraft/client/renderer/RenderGlobal.renderEntitiesStartupCounter:I
        //   261: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0261 (coming from #0258).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void func_174986_e() {
        this.field_175009_l.clear();
        this.field_174995_M.func_178514_b();
    }
    
    public void checkOcclusionQueryResult(final int n, final int n2) {
        if (OpenGlHelper.shadersSupported && this.field_174991_A != null) {
            this.field_174991_A.createBindFramebuffers(n, n2);
        }
    }
    
    public void func_180446_a(final Entity p0, final ICamera p1, final float p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //     6: ifeq            21
        //     9: getstatic       optifine/Reflector.MinecraftForgeClient_getRenderPass:Loptifine/ReflectorMethod;
        //    12: iconst_0       
        //    13: anewarray       Ljava/lang/Object;
        //    16: invokestatic    optifine/Reflector.callInt:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)I
        //    19: istore          4
        //    21: aload_0        
        //    22: getfield        net/minecraft/client/renderer/RenderGlobal.renderEntitiesStartupCounter:I
        //    25: ifle            46
        //    28: iconst_0       
        //    29: ifle            33
        //    32: return         
        //    33: aload_0        
        //    34: dup            
        //    35: getfield        net/minecraft/client/renderer/RenderGlobal.renderEntitiesStartupCounter:I
        //    38: iconst_1       
        //    39: isub           
        //    40: putfield        net/minecraft/client/renderer/RenderGlobal.renderEntitiesStartupCounter:I
        //    43: goto            1864
        //    46: aload_1        
        //    47: getfield        net/minecraft/entity/Entity.prevPosX:D
        //    50: aload_1        
        //    51: getfield        net/minecraft/entity/Entity.posX:D
        //    54: aload_1        
        //    55: getfield        net/minecraft/entity/Entity.prevPosX:D
        //    58: dsub           
        //    59: fload_3        
        //    60: f2d            
        //    61: dmul           
        //    62: dadd           
        //    63: dstore          5
        //    65: aload_1        
        //    66: getfield        net/minecraft/entity/Entity.prevPosY:D
        //    69: aload_1        
        //    70: getfield        net/minecraft/entity/Entity.posY:D
        //    73: aload_1        
        //    74: getfield        net/minecraft/entity/Entity.prevPosY:D
        //    77: dsub           
        //    78: fload_3        
        //    79: f2d            
        //    80: dmul           
        //    81: dadd           
        //    82: dstore          7
        //    84: aload_1        
        //    85: getfield        net/minecraft/entity/Entity.prevPosZ:D
        //    88: aload_1        
        //    89: getfield        net/minecraft/entity/Entity.posZ:D
        //    92: aload_1        
        //    93: getfield        net/minecraft/entity/Entity.prevPosZ:D
        //    96: dsub           
        //    97: fload_3        
        //    98: f2d            
        //    99: dmul           
        //   100: dadd           
        //   101: dstore          9
        //   103: aload_0        
        //   104: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   107: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   110: ldc_w           "prepare"
        //   113: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   116: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //   119: aload_0        
        //   120: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   123: aload_0        
        //   124: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   127: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //   130: getstatic       net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   133: aload_0        
        //   134: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   137: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   140: fload_3        
        //   141: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.func_178470_a:(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;F)V
        //   144: aload_0        
        //   145: getfield        net/minecraft/client/renderer/RenderGlobal.field_175010_j:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   148: aload_0        
        //   149: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   152: getstatic       net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   155: aload_0        
        //   156: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   159: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   162: aload_0        
        //   163: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   166: getfield        net/minecraft/client/Minecraft.pointedEntity:Lnet/minecraft/entity/Entity;
        //   169: aload_0        
        //   170: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   173: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   176: fload_3        
        //   177: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.func_180597_a:(Lnet/minecraft/world/World;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/settings/GameSettings;F)V
        //   180: iconst_0       
        //   181: ifne            204
        //   184: aload_0        
        //   185: iconst_0       
        //   186: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesTotal:I
        //   189: aload_0        
        //   190: iconst_0       
        //   191: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //   194: aload_0        
        //   195: iconst_0       
        //   196: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesHidden:I
        //   199: aload_0        
        //   200: iconst_0       
        //   201: putfield        net/minecraft/client/renderer/RenderGlobal.countTileEntitiesRendered:I
        //   204: aload_0        
        //   205: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   208: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   211: astore          11
        //   213: aload           11
        //   215: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   218: aload           11
        //   220: getfield        net/minecraft/entity/Entity.posX:D
        //   223: aload           11
        //   225: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   228: dsub           
        //   229: fload_3        
        //   230: f2d            
        //   231: dmul           
        //   232: dadd           
        //   233: dstore          12
        //   235: aload           11
        //   237: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   240: aload           11
        //   242: getfield        net/minecraft/entity/Entity.posY:D
        //   245: aload           11
        //   247: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   250: dsub           
        //   251: fload_3        
        //   252: f2d            
        //   253: dmul           
        //   254: dadd           
        //   255: dstore          14
        //   257: aload           11
        //   259: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   262: aload           11
        //   264: getfield        net/minecraft/entity/Entity.posZ:D
        //   267: aload           11
        //   269: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   272: dsub           
        //   273: fload_3        
        //   274: f2d            
        //   275: dmul           
        //   276: dadd           
        //   277: dstore          16
        //   279: dload           12
        //   281: putstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerX:D
        //   284: dload           14
        //   286: putstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerY:D
        //   289: dload           16
        //   291: putstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerZ:D
        //   294: aload_0        
        //   295: getfield        net/minecraft/client/renderer/RenderGlobal.field_175010_j:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   298: dload           12
        //   300: dload           14
        //   302: dload           16
        //   304: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.func_178628_a:(DDD)V
        //   307: aload_0        
        //   308: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   311: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //   314: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_180436_i:()V
        //   317: aload_0        
        //   318: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   321: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   324: ldc_w           "global"
        //   327: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   330: aload_0        
        //   331: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   334: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getLoadedEntityList:()Ljava/util/List;
        //   337: astore          18
        //   339: iconst_0       
        //   340: ifne            354
        //   343: aload_0        
        //   344: aload           18
        //   346: invokeinterface java/util/List.size:()I
        //   351: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesTotal:I
        //   354: invokestatic    optifine/Config.isFogOff:()Z
        //   357: ifeq            370
        //   360: aload_0        
        //   361: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   364: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //   367: getfield        net/minecraft/client/renderer/EntityRenderer.fogStandard:Z
        //   370: getstatic       optifine/Reflector.ForgeEntity_shouldRenderInPass:Loptifine/ReflectorMethod;
        //   373: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   376: istore          19
        //   378: getstatic       optifine/Reflector.ForgeTileEntity_shouldRenderInPass:Loptifine/ReflectorMethod;
        //   381: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   384: istore          20
        //   386: goto            472
        //   389: aload_0        
        //   390: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   393: getfield        net/minecraft/client/multiplayer/WorldClient.weatherEffects:Ljava/util/List;
        //   396: iconst_0       
        //   397: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   402: checkcast       Lnet/minecraft/entity/Entity;
        //   405: astore          22
        //   407: iload           19
        //   409: ifeq            434
        //   412: aload           22
        //   414: getstatic       optifine/Reflector.ForgeEntity_shouldRenderInPass:Loptifine/ReflectorMethod;
        //   417: iconst_1       
        //   418: anewarray       Ljava/lang/Object;
        //   421: dup            
        //   422: iconst_0       
        //   423: iconst_0       
        //   424: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   427: aastore        
        //   428: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   431: ifeq            469
        //   434: aload_0        
        //   435: dup            
        //   436: getfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //   439: iconst_1       
        //   440: iadd           
        //   441: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //   444: aload           22
        //   446: dload           5
        //   448: dload           7
        //   450: dload           9
        //   452: invokevirtual   net/minecraft/entity/Entity.isInRangeToRender3d:(DDD)Z
        //   455: ifeq            469
        //   458: aload_0        
        //   459: getfield        net/minecraft/client/renderer/RenderGlobal.field_175010_j:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   462: aload           22
        //   464: fload_3        
        //   465: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.renderEntitySimple:(Lnet/minecraft/entity/Entity;F)Z
        //   468: pop            
        //   469: iinc            21, 1
        //   472: iconst_0       
        //   473: aload_0        
        //   474: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   477: getfield        net/minecraft/client/multiplayer/WorldClient.weatherEffects:Ljava/util/List;
        //   480: invokeinterface java/util/List.size:()I
        //   485: if_icmplt       389
        //   488: aload_0        
        //   489: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174985_d:()Z
        //   492: ifeq            779
        //   495: sipush          519
        //   498: invokestatic    net/minecraft/client/renderer/GlStateManager.depthFunc:(I)V
        //   501: aload_0        
        //   502: getfield        net/minecraft/client/renderer/RenderGlobal.field_175015_z:Lnet/minecraft/client/shader/Framebuffer;
        //   505: invokevirtual   net/minecraft/client/shader/Framebuffer.framebufferClear:()V
        //   508: aload_0        
        //   509: getfield        net/minecraft/client/renderer/RenderGlobal.field_175015_z:Lnet/minecraft/client/shader/Framebuffer;
        //   512: iconst_0       
        //   513: invokevirtual   net/minecraft/client/shader/Framebuffer.bindFramebuffer:(Z)V
        //   516: aload_0        
        //   517: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   520: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   523: ldc_w           "entityOutlines"
        //   526: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   529: aload_0        
        //   530: getfield        net/minecraft/client/renderer/RenderGlobal.field_175010_j:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   533: iconst_1       
        //   534: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.func_178632_c:(Z)V
        //   537: goto            727
        //   540: aload           18
        //   542: iconst_0       
        //   543: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   548: checkcast       Lnet/minecraft/entity/Entity;
        //   551: astore          22
        //   553: iload           19
        //   555: ifeq            580
        //   558: aload           22
        //   560: getstatic       optifine/Reflector.ForgeEntity_shouldRenderInPass:Loptifine/ReflectorMethod;
        //   563: iconst_1       
        //   564: anewarray       Ljava/lang/Object;
        //   567: dup            
        //   568: iconst_0       
        //   569: iconst_0       
        //   570: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   573: aastore        
        //   574: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   577: ifeq            724
        //   580: aload_0        
        //   581: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   584: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   587: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   590: ifeq            613
        //   593: aload_0        
        //   594: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   597: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   600: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   603: invokevirtual   net/minecraft/entity/EntityLivingBase.isPlayerSleeping:()Z
        //   606: ifeq            613
        //   609: iconst_1       
        //   610: goto            614
        //   613: iconst_0       
        //   614: istore          23
        //   616: aload           22
        //   618: dload           5
        //   620: dload           7
        //   622: dload           9
        //   624: invokevirtual   net/minecraft/entity/Entity.isInRangeToRender3d:(DDD)Z
        //   627: ifeq            675
        //   630: aload           22
        //   632: getfield        net/minecraft/entity/Entity.ignoreFrustumCheck:Z
        //   635: ifne            663
        //   638: aload_2        
        //   639: aload           22
        //   641: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   644: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //   649: ifne            663
        //   652: aload           22
        //   654: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   657: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   660: if_acmpne       675
        //   663: aload           22
        //   665: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   668: ifeq            675
        //   671: iconst_1       
        //   672: goto            676
        //   675: iconst_0       
        //   676: istore          24
        //   678: aload           22
        //   680: aload_0        
        //   681: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   684: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   687: if_acmpne       708
        //   690: aload_0        
        //   691: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   694: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   697: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //   700: ifne            708
        //   703: iload           23
        //   705: ifeq            724
        //   708: iload           24
        //   710: ifeq            724
        //   713: aload_0        
        //   714: getfield        net/minecraft/client/renderer/RenderGlobal.field_175010_j:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   717: aload           22
        //   719: fload_3        
        //   720: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.renderEntitySimple:(Lnet/minecraft/entity/Entity;F)Z
        //   723: pop            
        //   724: iinc            21, 1
        //   727: iconst_0       
        //   728: aload           18
        //   730: invokeinterface java/util/List.size:()I
        //   735: if_icmplt       540
        //   738: aload_0        
        //   739: getfield        net/minecraft/client/renderer/RenderGlobal.field_175010_j:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   742: iconst_0       
        //   743: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.func_178632_c:(Z)V
        //   746: iconst_0       
        //   747: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   750: aload_0        
        //   751: getfield        net/minecraft/client/renderer/RenderGlobal.field_174991_A:Lnet/minecraft/client/shader/ShaderGroup;
        //   754: fload_3        
        //   755: invokevirtual   net/minecraft/client/shader/ShaderGroup.loadShaderGroup:(F)V
        //   758: iconst_1       
        //   759: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   762: aload_0        
        //   763: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   766: invokevirtual   net/minecraft/client/Minecraft.getFramebuffer:()Lnet/minecraft/client/shader/Framebuffer;
        //   769: iconst_0       
        //   770: invokevirtual   net/minecraft/client/shader/Framebuffer.bindFramebuffer:(Z)V
        //   773: sipush          515
        //   776: invokestatic    net/minecraft/client/renderer/GlStateManager.depthFunc:(I)V
        //   779: aload_0        
        //   780: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   783: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   786: ldc_w           "entities"
        //   789: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   792: invokestatic    optifine/Config.isShaders:()Z
        //   795: istore          23
        //   797: iload           23
        //   799: aload_0        
        //   800: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //   803: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   808: astore          24
        //   810: aload_0        
        //   811: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   814: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   817: getfield        net/minecraft/client/settings/GameSettings.fancyGraphics:Z
        //   820: istore          25
        //   822: aload_0        
        //   823: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   826: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   829: invokestatic    optifine/Config.isDroppedItemsFancy:()Z
        //   832: putfield        net/minecraft/client/settings/GameSettings.fancyGraphics:Z
        //   835: goto            1189
        //   838: aload           24
        //   840: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   845: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   848: astore          26
        //   850: aload_0        
        //   851: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   854: aload           26
        //   856: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   859: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178568_j:()Lnet/minecraft/util/BlockPos;
        //   862: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   865: astore          27
        //   867: aload           27
        //   869: invokevirtual   net/minecraft/world/chunk/Chunk.getEntityLists:()[Lnet/minecraft/util/ClassInheratanceMultiMap;
        //   872: aload           26
        //   874: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   877: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178568_j:()Lnet/minecraft/util/BlockPos;
        //   880: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   883: bipush          16
        //   885: idiv           
        //   886: aaload         
        //   887: invokevirtual   net/minecraft/util/ClassInheratanceMultiMap.iterator:()Ljava/util/Iterator;
        //   890: astore          28
        //   892: goto            1179
        //   895: aload           28
        //   897: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   902: checkcast       Lnet/minecraft/entity/Entity;
        //   905: astore          29
        //   907: iload           19
        //   909: ifeq            934
        //   912: aload           29
        //   914: getstatic       optifine/Reflector.ForgeEntity_shouldRenderInPass:Loptifine/ReflectorMethod;
        //   917: iconst_1       
        //   918: anewarray       Ljava/lang/Object;
        //   921: dup            
        //   922: iconst_0       
        //   923: iconst_0       
        //   924: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   927: aastore        
        //   928: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   931: ifeq            1179
        //   934: aload_0        
        //   935: getfield        net/minecraft/client/renderer/RenderGlobal.field_175010_j:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   938: aload           29
        //   940: aload_2        
        //   941: dload           5
        //   943: dload           7
        //   945: dload           9
        //   947: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.func_178635_a:(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z
        //   950: ifne            968
        //   953: aload           29
        //   955: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   958: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   961: if_acmpeq       968
        //   964: iconst_0       
        //   965: goto            969
        //   968: iconst_1       
        //   969: istore          30
        //   971: iload           30
        //   973: ifeq            1143
        //   976: aload_0        
        //   977: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   980: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   983: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   986: ifeq            1005
        //   989: aload_0        
        //   990: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   993: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   996: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   999: invokevirtual   net/minecraft/entity/EntityLivingBase.isPlayerSleeping:()Z
        //  1002: goto            1006
        //  1005: iconst_0       
        //  1006: istore          31
        //  1008: aload           29
        //  1010: aload_0        
        //  1011: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1014: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //  1017: if_acmpne       1038
        //  1020: aload_0        
        //  1021: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1024: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1027: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //  1030: ifne            1038
        //  1033: iload           31
        //  1035: ifeq            1179
        //  1038: aload           29
        //  1040: getfield        net/minecraft/entity/Entity.posY:D
        //  1043: dconst_0       
        //  1044: dcmpl          
        //  1045: iflt            1082
        //  1048: aload           29
        //  1050: getfield        net/minecraft/entity/Entity.posY:D
        //  1053: ldc2_w          256.0
        //  1056: dcmpg          
        //  1057: ifge            1082
        //  1060: aload_0        
        //  1061: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1064: new             Lnet/minecraft/util/BlockPos;
        //  1067: dup            
        //  1068: aload           29
        //  1070: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  1073: invokevirtual   net/minecraft/client/multiplayer/WorldClient.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //  1076: ifne            1082
        //  1079: goto            1179
        //  1082: aload_0        
        //  1083: dup            
        //  1084: getfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //  1087: iconst_1       
        //  1088: iadd           
        //  1089: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //  1092: aload           29
        //  1094: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //  1097: ldc_w           Lnet/minecraft/entity/item/EntityItemFrame;.class
        //  1100: if_acmpne       1111
        //  1103: aload           29
        //  1105: ldc2_w          0.06
        //  1108: putfield        net/minecraft/entity/Entity.renderDistanceWeight:D
        //  1111: aload_0        
        //  1112: aload           29
        //  1114: putfield        net/minecraft/client/renderer/RenderGlobal.renderedEntity:Lnet/minecraft/entity/Entity;
        //  1117: iload           23
        //  1119: ifeq            1127
        //  1122: aload           29
        //  1124: invokestatic    shadersmod/client/Shaders.nextEntity:(Lnet/minecraft/entity/Entity;)V
        //  1127: aload_0        
        //  1128: getfield        net/minecraft/client/renderer/RenderGlobal.field_175010_j:Lnet/minecraft/client/renderer/entity/RenderManager;
        //  1131: aload           29
        //  1133: fload_3        
        //  1134: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.renderEntitySimple:(Lnet/minecraft/entity/Entity;F)Z
        //  1137: pop            
        //  1138: aload_0        
        //  1139: aconst_null    
        //  1140: putfield        net/minecraft/client/renderer/RenderGlobal.renderedEntity:Lnet/minecraft/entity/Entity;
        //  1143: iload           30
        //  1145: ifne            1179
        //  1148: aload           29
        //  1150: instanceof      Lnet/minecraft/entity/projectile/EntityWitherSkull;
        //  1153: ifeq            1179
        //  1156: iload           23
        //  1158: ifeq            1166
        //  1161: aload           29
        //  1163: invokestatic    shadersmod/client/Shaders.nextEntity:(Lnet/minecraft/entity/Entity;)V
        //  1166: aload_0        
        //  1167: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1170: invokevirtual   net/minecraft/client/Minecraft.getRenderManager:()Lnet/minecraft/client/renderer/entity/RenderManager;
        //  1173: aload           29
        //  1175: fload_3        
        //  1176: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.func_178630_b:(Lnet/minecraft/entity/Entity;F)V
        //  1179: aload           28
        //  1181: invokeinterface java/util/Iterator.hasNext:()Z
        //  1186: ifne            895
        //  1189: aload           24
        //  1191: invokeinterface java/util/Iterator.hasNext:()Z
        //  1196: ifne            838
        //  1199: aload_0        
        //  1200: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1203: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1206: iload           25
        //  1208: putfield        net/minecraft/client/settings/GameSettings.fancyGraphics:Z
        //  1211: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1214: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.getFontRenderer:()Lnet/minecraft/client/gui/FontRenderer;
        //  1217: astore          27
        //  1219: iload           23
        //  1221: aload_0        
        //  1222: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1225: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //  1228: ldc_w           "blockentities"
        //  1231: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1234: getstatic       optifine/Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch:Loptifine/ReflectorMethod;
        //  1237: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //  1240: ifeq            1257
        //  1243: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1246: getstatic       optifine/Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch:Loptifine/ReflectorMethod;
        //  1249: iconst_0       
        //  1250: anewarray       Ljava/lang/Object;
        //  1253: invokestatic    optifine/Reflector.call:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //  1256: pop            
        //  1257: aload_0        
        //  1258: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //  1261: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1266: astore          24
        //  1268: goto            1491
        //  1271: aload           24
        //  1273: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1278: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1281: astore          26
        //  1283: aload           26
        //  1285: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1288: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178571_g:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //  1291: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178485_b:()Ljava/util/List;
        //  1294: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1299: astore          29
        //  1301: goto            1481
        //  1304: aload           29
        //  1306: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1311: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //  1314: astore          28
        //  1316: iload           20
        //  1318: ifeq            1382
        //  1321: aload           28
        //  1323: getstatic       optifine/Reflector.ForgeTileEntity_shouldRenderInPass:Loptifine/ReflectorMethod;
        //  1326: iconst_1       
        //  1327: anewarray       Ljava/lang/Object;
        //  1330: dup            
        //  1331: iconst_0       
        //  1332: iconst_0       
        //  1333: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1336: aastore        
        //  1337: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1340: ifne            1346
        //  1343: goto            1481
        //  1346: aload           28
        //  1348: getstatic       optifine/Reflector.ForgeTileEntity_getRenderBoundingBox:Loptifine/ReflectorMethod;
        //  1351: iconst_0       
        //  1352: anewarray       Ljava/lang/Object;
        //  1355: invokestatic    optifine/Reflector.call:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //  1358: checkcast       Lnet/minecraft/util/AxisAlignedBB;
        //  1361: astore          30
        //  1363: aload           30
        //  1365: ifnull          1382
        //  1368: aload_2        
        //  1369: aload           30
        //  1371: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  1376: ifne            1382
        //  1379: goto            1481
        //  1382: aload           28
        //  1384: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //  1387: astore          30
        //  1389: aload           30
        //  1391: ldc_w           Lnet/minecraft/tileentity/TileEntitySign;.class
        //  1394: if_acmpne       1445
        //  1397: getstatic       optifine/Config.zoomMode:Z
        //  1400: ifne            1445
        //  1403: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1406: astore          31
        //  1408: aload           28
        //  1410: aload           31
        //  1412: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //  1415: aload           31
        //  1417: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //  1420: aload           31
        //  1422: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //  1425: invokevirtual   net/minecraft/tileentity/TileEntity.getDistanceSq:(DDD)D
        //  1428: dstore          32
        //  1430: dload           32
        //  1432: ldc2_w          256.0
        //  1435: dcmpl          
        //  1436: ifle            1445
        //  1439: aload           27
        //  1441: iconst_0       
        //  1442: putfield        net/minecraft/client/gui/FontRenderer.enabled:Z
        //  1445: iload           23
        //  1447: ifeq            1455
        //  1450: aload           28
        //  1452: invokestatic    shadersmod/client/Shaders.nextBlockEntity:(Lnet/minecraft/tileentity/TileEntity;)V
        //  1455: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1458: aload           28
        //  1460: fload_3        
        //  1461: iconst_m1      
        //  1462: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.func_180546_a:(Lnet/minecraft/tileentity/TileEntity;FI)V
        //  1465: aload_0        
        //  1466: dup            
        //  1467: getfield        net/minecraft/client/renderer/RenderGlobal.countTileEntitiesRendered:I
        //  1470: iconst_1       
        //  1471: iadd           
        //  1472: putfield        net/minecraft/client/renderer/RenderGlobal.countTileEntitiesRendered:I
        //  1475: aload           27
        //  1477: iconst_1       
        //  1478: putfield        net/minecraft/client/gui/FontRenderer.enabled:Z
        //  1481: aload           29
        //  1483: invokeinterface java/util/Iterator.hasNext:()Z
        //  1488: ifne            1304
        //  1491: aload           24
        //  1493: invokeinterface java/util/Iterator.hasNext:()Z
        //  1498: ifne            1271
        //  1501: getstatic       optifine/Reflector.ForgeTileEntityRendererDispatcher_drawBatch:Loptifine/ReflectorMethod;
        //  1504: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //  1507: ifeq            1531
        //  1510: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1513: getstatic       optifine/Reflector.ForgeTileEntityRendererDispatcher_drawBatch:Loptifine/ReflectorMethod;
        //  1516: iconst_1       
        //  1517: anewarray       Ljava/lang/Object;
        //  1520: dup            
        //  1521: iconst_0       
        //  1522: iconst_0       
        //  1523: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1526: aastore        
        //  1527: invokestatic    optifine/Reflector.call:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //  1530: pop            
        //  1531: aload_0        
        //  1532: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_180443_s:()V
        //  1535: aload_0        
        //  1536: getfield        net/minecraft/client/renderer/RenderGlobal.damagedBlocks:Ljava/util/Map;
        //  1539: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //  1544: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //  1549: astore          24
        //  1551: goto            1830
        //  1554: aload           24
        //  1556: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1561: checkcast       Lnet/minecraft/client/renderer/DestroyBlockProgress;
        //  1564: astore          29
        //  1566: aload           29
        //  1568: invokevirtual   net/minecraft/client/renderer/DestroyBlockProgress.func_180246_b:()Lnet/minecraft/util/BlockPos;
        //  1571: astore          30
        //  1573: aload_0        
        //  1574: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1577: aload           30
        //  1579: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //  1582: astore          28
        //  1584: aload           28
        //  1586: instanceof      Lnet/minecraft/tileentity/TileEntityChest;
        //  1589: ifeq            1660
        //  1592: aload           28
        //  1594: checkcast       Lnet/minecraft/tileentity/TileEntityChest;
        //  1597: astore          31
        //  1599: aload           31
        //  1601: getfield        net/minecraft/tileentity/TileEntityChest.adjacentChestXNeg:Lnet/minecraft/tileentity/TileEntityChest;
        //  1604: ifnull          1631
        //  1607: aload           30
        //  1609: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //  1612: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //  1615: astore          30
        //  1617: aload_0        
        //  1618: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1621: aload           30
        //  1623: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //  1626: astore          28
        //  1628: goto            1660
        //  1631: aload           31
        //  1633: getfield        net/minecraft/tileentity/TileEntityChest.adjacentChestZNeg:Lnet/minecraft/tileentity/TileEntityChest;
        //  1636: ifnull          1660
        //  1639: aload           30
        //  1641: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //  1644: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //  1647: astore          30
        //  1649: aload_0        
        //  1650: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1653: aload           30
        //  1655: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //  1658: astore          28
        //  1660: aload_0        
        //  1661: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1664: aload           30
        //  1666: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  1669: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  1674: astore          31
        //  1676: iload           20
        //  1678: ifeq            1758
        //  1681: aload           28
        //  1683: ifnull          1802
        //  1686: aload           28
        //  1688: getstatic       optifine/Reflector.ForgeTileEntity_shouldRenderInPass:Loptifine/ReflectorMethod;
        //  1691: iconst_1       
        //  1692: anewarray       Ljava/lang/Object;
        //  1695: dup            
        //  1696: iconst_0       
        //  1697: iconst_0       
        //  1698: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1701: aastore        
        //  1702: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1705: ifeq            1802
        //  1708: aload           28
        //  1710: getstatic       optifine/Reflector.ForgeTileEntity_canRenderBreaking:Loptifine/ReflectorMethod;
        //  1713: iconst_0       
        //  1714: anewarray       Ljava/lang/Object;
        //  1717: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1720: ifeq            1802
        //  1723: aload           28
        //  1725: getstatic       optifine/Reflector.ForgeTileEntity_getRenderBoundingBox:Loptifine/ReflectorMethod;
        //  1728: iconst_0       
        //  1729: anewarray       Ljava/lang/Object;
        //  1732: invokestatic    optifine/Reflector.call:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //  1735: checkcast       Lnet/minecraft/util/AxisAlignedBB;
        //  1738: astore          33
        //  1740: aload           33
        //  1742: ifnull          1802
        //  1745: aload_2        
        //  1746: aload           33
        //  1748: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  1753: istore          32
        //  1755: goto            1802
        //  1758: aload           28
        //  1760: ifnull          1799
        //  1763: aload           31
        //  1765: instanceof      Lnet/minecraft/block/BlockChest;
        //  1768: ifne            1795
        //  1771: aload           31
        //  1773: instanceof      Lnet/minecraft/block/BlockEnderChest;
        //  1776: ifne            1795
        //  1779: aload           31
        //  1781: instanceof      Lnet/minecraft/block/BlockSign;
        //  1784: ifne            1795
        //  1787: aload           31
        //  1789: instanceof      Lnet/minecraft/block/BlockSkull;
        //  1792: ifeq            1799
        //  1795: iconst_1       
        //  1796: goto            1800
        //  1799: iconst_0       
        //  1800: istore          32
        //  1802: iconst_0       
        //  1803: ifeq            1830
        //  1806: iload           23
        //  1808: ifeq            1816
        //  1811: aload           28
        //  1813: invokestatic    shadersmod/client/Shaders.nextBlockEntity:(Lnet/minecraft/tileentity/TileEntity;)V
        //  1816: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1819: aload           28
        //  1821: fload_3        
        //  1822: aload           29
        //  1824: invokevirtual   net/minecraft/client/renderer/DestroyBlockProgress.getPartialBlockDamage:()I
        //  1827: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.func_180546_a:(Lnet/minecraft/tileentity/TileEntity;FI)V
        //  1830: aload           24
        //  1832: invokeinterface java/util/Iterator.hasNext:()Z
        //  1837: ifne            1554
        //  1840: aload_0        
        //  1841: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_174969_t:()V
        //  1844: aload_0        
        //  1845: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1848: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1851: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_175072_h:()V
        //  1854: aload_0        
        //  1855: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1858: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1861: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //  1864: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0370 (coming from #0367).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public String getDebugInfoRenders() {
        final int length = this.field_175008_n.field_178164_f.length;
        final Iterator<ContainerLocalRenderInformation> iterator = this.glRenderLists.iterator();
        while (iterator.hasNext()) {
            final CompiledChunk field_178590_b = iterator.next().field_178036_a.field_178590_b;
            if (field_178590_b != CompiledChunk.field_178502_a && !field_178590_b.func_178489_a()) {
                int n = 0;
                ++n;
            }
        }
        return String.format("C: %d/%d %sD: %d, %s", 0, length, this.mc.field_175612_E ? "(s) " : "", this.renderDistanceChunks, this.field_174995_M.func_178504_a());
    }
    
    public String getDebugInfoEntities() {
        return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersionDebug();
    }
    
    public void func_174970_a(final Entity p0, final double p1, final ICamera p2, final int p3, final boolean p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //     7: pop            
        //     8: getstatic       net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //    11: aload_0        
        //    12: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //    15: if_icmpeq       22
        //    18: aload_0        
        //    19: invokevirtual   net/minecraft/client/renderer/RenderGlobal.loadRenderers:()V
        //    22: aload_0        
        //    23: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    26: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //    29: ldc_w           "camera"
        //    32: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    35: aload_1        
        //    36: getfield        net/minecraft/entity/Entity.posX:D
        //    39: aload_0        
        //    40: getfield        net/minecraft/client/renderer/RenderGlobal.field_174992_B:D
        //    43: dsub           
        //    44: dstore          7
        //    46: aload_1        
        //    47: getfield        net/minecraft/entity/Entity.posY:D
        //    50: aload_0        
        //    51: getfield        net/minecraft/client/renderer/RenderGlobal.field_174993_C:D
        //    54: dsub           
        //    55: dstore          9
        //    57: aload_1        
        //    58: getfield        net/minecraft/entity/Entity.posZ:D
        //    61: aload_0        
        //    62: getfield        net/minecraft/client/renderer/RenderGlobal.field_174987_D:D
        //    65: dsub           
        //    66: dstore          11
        //    68: aload_0        
        //    69: getfield        net/minecraft/client/renderer/RenderGlobal.field_174988_E:I
        //    72: aload_1        
        //    73: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //    76: if_icmpne       125
        //    79: aload_0        
        //    80: getfield        net/minecraft/client/renderer/RenderGlobal.field_174989_F:I
        //    83: aload_1        
        //    84: getfield        net/minecraft/entity/Entity.chunkCoordY:I
        //    87: if_icmpne       125
        //    90: aload_0        
        //    91: getfield        net/minecraft/client/renderer/RenderGlobal.field_174990_G:I
        //    94: aload_1        
        //    95: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //    98: if_icmpne       125
        //   101: dload           7
        //   103: dload           7
        //   105: dmul           
        //   106: dload           9
        //   108: dload           9
        //   110: dmul           
        //   111: dadd           
        //   112: dload           11
        //   114: dload           11
        //   116: dmul           
        //   117: dadd           
        //   118: ldc2_w          16.0
        //   121: dcmpl          
        //   122: ifle            188
        //   125: aload_0        
        //   126: aload_1        
        //   127: getfield        net/minecraft/entity/Entity.posX:D
        //   130: putfield        net/minecraft/client/renderer/RenderGlobal.field_174992_B:D
        //   133: aload_0        
        //   134: aload_1        
        //   135: getfield        net/minecraft/entity/Entity.posY:D
        //   138: putfield        net/minecraft/client/renderer/RenderGlobal.field_174993_C:D
        //   141: aload_0        
        //   142: aload_1        
        //   143: getfield        net/minecraft/entity/Entity.posZ:D
        //   146: putfield        net/minecraft/client/renderer/RenderGlobal.field_174987_D:D
        //   149: aload_0        
        //   150: aload_1        
        //   151: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   154: putfield        net/minecraft/client/renderer/RenderGlobal.field_174988_E:I
        //   157: aload_0        
        //   158: aload_1        
        //   159: getfield        net/minecraft/entity/Entity.chunkCoordY:I
        //   162: putfield        net/minecraft/client/renderer/RenderGlobal.field_174989_F:I
        //   165: aload_0        
        //   166: aload_1        
        //   167: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   170: putfield        net/minecraft/client/renderer/RenderGlobal.field_174990_G:I
        //   173: aload_0        
        //   174: getfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   177: aload_1        
        //   178: getfield        net/minecraft/entity/Entity.posX:D
        //   181: aload_1        
        //   182: getfield        net/minecraft/entity/Entity.posZ:D
        //   185: invokevirtual   net/minecraft/client/renderer/ViewFrustum.func_178163_a:(DD)V
        //   188: invokestatic    optifine/Config.isDynamicLights:()Z
        //   191: ifeq            198
        //   194: aload_0        
        //   195: invokestatic    optifine/DynamicLights.update:(Lnet/minecraft/client/renderer/RenderGlobal;)V
        //   198: aload_0        
        //   199: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   202: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   205: ldc_w           "renderlistcamera"
        //   208: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   211: aload_1        
        //   212: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   215: aload_1        
        //   216: getfield        net/minecraft/entity/Entity.posX:D
        //   219: aload_1        
        //   220: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   223: dsub           
        //   224: dload_2        
        //   225: dmul           
        //   226: dadd           
        //   227: dstore          13
        //   229: aload_1        
        //   230: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   233: aload_1        
        //   234: getfield        net/minecraft/entity/Entity.posY:D
        //   237: aload_1        
        //   238: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   241: dsub           
        //   242: dload_2        
        //   243: dmul           
        //   244: dadd           
        //   245: dstore          15
        //   247: aload_1        
        //   248: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   251: aload_1        
        //   252: getfield        net/minecraft/entity/Entity.posZ:D
        //   255: aload_1        
        //   256: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   259: dsub           
        //   260: dload_2        
        //   261: dmul           
        //   262: dadd           
        //   263: dstore          17
        //   265: aload_0        
        //   266: getfield        net/minecraft/client/renderer/RenderGlobal.field_174996_N:Lnet/minecraft/client/renderer/ChunkRenderContainer;
        //   269: dload           13
        //   271: dload           15
        //   273: dload           17
        //   275: invokevirtual   net/minecraft/client/renderer/ChunkRenderContainer.func_178004_a:(DDD)V
        //   278: aload_0        
        //   279: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   282: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   285: ldc_w           "cull"
        //   288: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   291: aload_0        
        //   292: getfield        net/minecraft/client/renderer/RenderGlobal.field_175001_U:Lnet/minecraft/client/renderer/culling/ClippingHelper;
        //   295: ifnull          341
        //   298: new             Lnet/minecraft/client/renderer/culling/Frustrum;
        //   301: dup            
        //   302: aload_0        
        //   303: getfield        net/minecraft/client/renderer/RenderGlobal.field_175001_U:Lnet/minecraft/client/renderer/culling/ClippingHelper;
        //   306: invokespecial   net/minecraft/client/renderer/culling/Frustrum.<init>:(Lnet/minecraft/client/renderer/culling/ClippingHelper;)V
        //   309: astore          19
        //   311: aload           19
        //   313: aload_0        
        //   314: getfield        net/minecraft/client/renderer/RenderGlobal.field_175003_W:Ljavax/vecmath/Vector3d;
        //   317: getfield        javax/vecmath/Vector3d.x:D
        //   320: aload_0        
        //   321: getfield        net/minecraft/client/renderer/RenderGlobal.field_175003_W:Ljavax/vecmath/Vector3d;
        //   324: getfield        javax/vecmath/Vector3d.y:D
        //   327: aload_0        
        //   328: getfield        net/minecraft/client/renderer/RenderGlobal.field_175003_W:Ljavax/vecmath/Vector3d;
        //   331: getfield        javax/vecmath/Vector3d.z:D
        //   334: invokevirtual   net/minecraft/client/renderer/culling/Frustrum.setPosition:(DDD)V
        //   337: aload           19
        //   339: astore          4
        //   341: aload_0        
        //   342: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   345: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   348: ldc_w           "culling"
        //   351: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   354: new             Lnet/minecraft/util/BlockPos;
        //   357: dup            
        //   358: dload           13
        //   360: dload           15
        //   362: aload_1        
        //   363: invokevirtual   net/minecraft/entity/Entity.getEyeHeight:()F
        //   366: f2d            
        //   367: dadd           
        //   368: dload           17
        //   370: invokespecial   net/minecraft/util/BlockPos.<init>:(DDD)V
        //   373: astore          19
        //   375: aload_0        
        //   376: getfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   379: aload           19
        //   381: invokevirtual   net/minecraft/client/renderer/ViewFrustum.func_178161_a:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   384: astore          20
        //   386: new             Lnet/minecraft/util/BlockPos;
        //   389: dup            
        //   390: dload           13
        //   392: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   395: bipush          16
        //   397: idiv           
        //   398: bipush          16
        //   400: imul           
        //   401: dload           15
        //   403: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   406: bipush          16
        //   408: idiv           
        //   409: bipush          16
        //   411: imul           
        //   412: dload           17
        //   414: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   417: bipush          16
        //   419: idiv           
        //   420: bipush          16
        //   422: imul           
        //   423: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   426: astore          21
        //   428: aload_0        
        //   429: aload_0        
        //   430: getfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   433: ifne            514
        //   436: aload_0        
        //   437: getfield        net/minecraft/client/renderer/RenderGlobal.field_175009_l:Ljava/util/Set;
        //   440: invokeinterface java/util/Set.isEmpty:()Z
        //   445: ifeq            514
        //   448: aload_1        
        //   449: getfield        net/minecraft/entity/Entity.posX:D
        //   452: aload_0        
        //   453: getfield        net/minecraft/client/renderer/RenderGlobal.field_174997_H:D
        //   456: dcmpl          
        //   457: ifne            514
        //   460: aload_1        
        //   461: getfield        net/minecraft/entity/Entity.posY:D
        //   464: aload_0        
        //   465: getfield        net/minecraft/client/renderer/RenderGlobal.field_174998_I:D
        //   468: dcmpl          
        //   469: ifne            514
        //   472: aload_1        
        //   473: getfield        net/minecraft/entity/Entity.posZ:D
        //   476: aload_0        
        //   477: getfield        net/minecraft/client/renderer/RenderGlobal.field_174999_J:D
        //   480: dcmpl          
        //   481: ifne            514
        //   484: aload_1        
        //   485: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //   488: f2d            
        //   489: aload_0        
        //   490: getfield        net/minecraft/client/renderer/RenderGlobal.field_175000_K:D
        //   493: dcmpl          
        //   494: ifne            514
        //   497: aload_1        
        //   498: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   501: f2d            
        //   502: aload_0        
        //   503: getfield        net/minecraft/client/renderer/RenderGlobal.field_174994_L:D
        //   506: dcmpl          
        //   507: ifne            514
        //   510: iconst_0       
        //   511: goto            515
        //   514: iconst_1       
        //   515: putfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   518: aload_0        
        //   519: aload_1        
        //   520: getfield        net/minecraft/entity/Entity.posX:D
        //   523: putfield        net/minecraft/client/renderer/RenderGlobal.field_174997_H:D
        //   526: aload_0        
        //   527: aload_1        
        //   528: getfield        net/minecraft/entity/Entity.posY:D
        //   531: putfield        net/minecraft/client/renderer/RenderGlobal.field_174998_I:D
        //   534: aload_0        
        //   535: aload_1        
        //   536: getfield        net/minecraft/entity/Entity.posZ:D
        //   539: putfield        net/minecraft/client/renderer/RenderGlobal.field_174999_J:D
        //   542: aload_0        
        //   543: aload_1        
        //   544: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //   547: f2d            
        //   548: putfield        net/minecraft/client/renderer/RenderGlobal.field_175000_K:D
        //   551: aload_0        
        //   552: aload_1        
        //   553: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   556: f2d            
        //   557: putfield        net/minecraft/client/renderer/RenderGlobal.field_174994_L:D
        //   560: aload_0        
        //   561: getfield        net/minecraft/client/renderer/RenderGlobal.field_175001_U:Lnet/minecraft/client/renderer/culling/ClippingHelper;
        //   564: ifnull          571
        //   567: iconst_1       
        //   568: goto            572
        //   571: iconst_0       
        //   572: istore          22
        //   574: getstatic       optifine/Lagometer.timerVisibility:Loptifine/Lagometer$TimerNano;
        //   577: invokevirtual   optifine/Lagometer$TimerNano.start:()V
        //   580: getstatic       shadersmod/client/Shaders.isShadowPass:Z
        //   583: ifeq            799
        //   586: aload_0        
        //   587: aload_0        
        //   588: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosShadow:Ljava/util/List;
        //   591: putfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   594: aload_0        
        //   595: aload_0        
        //   596: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntitiesShadow:Ljava/util/List;
        //   599: putfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //   602: aload_0        
        //   603: aload_0        
        //   604: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntitiesShadow:Ljava/util/List;
        //   607: putfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //   610: iload           22
        //   612: ifne            823
        //   615: aload_0        
        //   616: getfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   619: ifeq            823
        //   622: aload_0        
        //   623: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   626: invokeinterface java/util/List.clear:()V
        //   631: aload_0        
        //   632: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //   635: invokeinterface java/util/List.clear:()V
        //   640: aload_0        
        //   641: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //   644: invokeinterface java/util/List.clear:()V
        //   649: new             Loptifine/RenderInfoLazy;
        //   652: dup            
        //   653: invokespecial   optifine/RenderInfoLazy.<init>:()V
        //   656: astore          23
        //   658: goto            784
        //   661: aload_0        
        //   662: getfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   665: getfield        net/minecraft/client/renderer/ViewFrustum.field_178164_f:[Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   668: iconst_0       
        //   669: aaload         
        //   670: astore          25
        //   672: aload           23
        //   674: aload           25
        //   676: invokevirtual   optifine/RenderInfoLazy.setRenderChunk:(Lnet/minecraft/client/renderer/chunk/RenderChunk;)V
        //   679: aload           25
        //   681: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178590_b:Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //   684: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178489_a:()Z
        //   687: ifeq            698
        //   690: aload           25
        //   692: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178569_m:()Z
        //   695: ifeq            713
        //   698: aload_0        
        //   699: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   702: aload           23
        //   704: invokevirtual   optifine/RenderInfoLazy.getRenderInfo:()Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   707: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   712: pop            
        //   713: aload           25
        //   715: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178568_j:()Lnet/minecraft/util/BlockPos;
        //   718: astore          26
        //   720: aload_0        
        //   721: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   724: aload           26
        //   726: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   729: invokestatic    optifine/ChunkUtils.hasEntities:(Lnet/minecraft/world/chunk/Chunk;)Z
        //   732: ifeq            750
        //   735: aload_0        
        //   736: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //   739: aload           23
        //   741: invokevirtual   optifine/RenderInfoLazy.getRenderInfo:()Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   744: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   749: pop            
        //   750: aload           25
        //   752: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178571_g:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //   755: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178485_b:()Ljava/util/List;
        //   758: invokeinterface java/util/List.size:()I
        //   763: ifle            781
        //   766: aload_0        
        //   767: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //   770: aload           23
        //   772: invokevirtual   optifine/RenderInfoLazy.getRenderInfo:()Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   775: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   780: pop            
        //   781: iinc            24, 1
        //   784: iconst_0       
        //   785: aload_0        
        //   786: getfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   789: getfield        net/minecraft/client/renderer/ViewFrustum.field_178164_f:[Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   792: arraylength    
        //   793: if_icmplt       661
        //   796: goto            823
        //   799: aload_0        
        //   800: aload_0        
        //   801: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosNormal:Ljava/util/List;
        //   804: putfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   807: aload_0        
        //   808: aload_0        
        //   809: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntitiesNormal:Ljava/util/List;
        //   812: putfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //   815: aload_0        
        //   816: aload_0        
        //   817: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntitiesNormal:Ljava/util/List;
        //   820: putfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //   823: iload           22
        //   825: ifne            1515
        //   828: aload_0        
        //   829: getfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   832: ifeq            1515
        //   835: getstatic       shadersmod/client/Shaders.isShadowPass:Z
        //   838: ifne            1515
        //   841: aload_0        
        //   842: iconst_0       
        //   843: putfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   846: aload_0        
        //   847: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   850: invokeinterface java/util/List.clear:()V
        //   855: aload_0        
        //   856: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //   859: invokeinterface java/util/List.clear:()V
        //   864: aload_0        
        //   865: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //   868: invokeinterface java/util/List.clear:()V
        //   873: aload_0        
        //   874: getfield        net/minecraft/client/renderer/RenderGlobal.visibilityDeque:Ljava/util/Deque;
        //   877: invokeinterface java/util/Deque.clear:()V
        //   882: aload_0        
        //   883: getfield        net/minecraft/client/renderer/RenderGlobal.visibilityDeque:Ljava/util/Deque;
        //   886: astore          25
        //   888: aload_0        
        //   889: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   892: getfield        net/minecraft/client/Minecraft.field_175612_E:Z
        //   895: istore          26
        //   897: aload           20
        //   899: ifnonnull       1046
        //   902: aload           19
        //   904: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   907: ifle            916
        //   910: sipush          248
        //   913: goto            918
        //   916: bipush          8
        //   918: istore          28
        //   920: aload_0        
        //   921: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //   924: ineg           
        //   925: istore          27
        //   927: goto            1034
        //   930: aload_0        
        //   931: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //   934: ineg           
        //   935: istore          29
        //   937: goto            1022
        //   940: aload_0        
        //   941: getfield        net/minecraft/client/renderer/RenderGlobal.field_175008_n:Lnet/minecraft/client/renderer/ViewFrustum;
        //   944: new             Lnet/minecraft/util/BlockPos;
        //   947: dup            
        //   948: iload           27
        //   950: iconst_4       
        //   951: ishl           
        //   952: bipush          8
        //   954: iadd           
        //   955: iconst_1       
        //   956: iload           29
        //   958: iconst_4       
        //   959: ishl           
        //   960: bipush          8
        //   962: iadd           
        //   963: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   966: invokevirtual   net/minecraft/client/renderer/ViewFrustum.func_178161_a:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   969: astore          30
        //   971: aload           30
        //   973: ifnull          1019
        //   976: aload           4
        //   978: aload           30
        //   980: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178591_c:Lnet/minecraft/util/AxisAlignedBB;
        //   983: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //   988: ifeq            1019
        //   991: aload           30
        //   993: iload           5
        //   995: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178577_a:(I)Z
        //   998: pop            
        //   999: aload           25
        //  1001: new             Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1004: dup            
        //  1005: aload           30
        //  1007: aconst_null    
        //  1008: iconst_0       
        //  1009: aconst_null    
        //  1010: invokespecial   net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.<init>:(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;ILjava/lang/Object;)V
        //  1013: invokeinterface java/util/Deque.add:(Ljava/lang/Object;)Z
        //  1018: pop            
        //  1019: iinc            29, 1
        //  1022: iload           29
        //  1024: aload_0        
        //  1025: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //  1028: if_icmple       940
        //  1031: iinc            27, 1
        //  1034: iload           27
        //  1036: aload_0        
        //  1037: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //  1040: if_icmple       930
        //  1043: goto            1204
        //  1046: new             Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1049: dup            
        //  1050: aload           20
        //  1052: aconst_null    
        //  1053: iconst_0       
        //  1054: aconst_null    
        //  1055: invokespecial   net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.<init>:(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;ILjava/lang/Object;)V
        //  1058: astore          29
        //  1060: getstatic       net/minecraft/client/renderer/RenderGlobal.SET_ALL_FACINGS:Ljava/util/Set;
        //  1063: astore          30
        //  1065: aload           30
        //  1067: invokeinterface java/util/Set.isEmpty:()Z
        //  1072: ifne            1127
        //  1075: aload           30
        //  1077: invokeinterface java/util/Set.size:()I
        //  1082: iconst_1       
        //  1083: if_icmpne       1127
        //  1086: aload_0        
        //  1087: aload_1        
        //  1088: dload_2        
        //  1089: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174962_a:(Lnet/minecraft/entity/Entity;D)Ljavax/vecmath/Vector3f;
        //  1092: astore          31
        //  1094: aload           31
        //  1096: getfield        javax/vecmath/Vector3f.x:F
        //  1099: aload           31
        //  1101: getfield        javax/vecmath/Vector3f.y:F
        //  1104: aload           31
        //  1106: getfield        javax/vecmath/Vector3f.z:F
        //  1109: invokestatic    net/minecraft/util/EnumFacing.func_176737_a:(FFF)Lnet/minecraft/util/EnumFacing;
        //  1112: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  1115: astore          32
        //  1117: aload           30
        //  1119: aload           32
        //  1121: invokeinterface java/util/Set.remove:(Ljava/lang/Object;)Z
        //  1126: pop            
        //  1127: aload           30
        //  1129: invokeinterface java/util/Set.isEmpty:()Z
        //  1134: ifeq            1137
        //  1137: iconst_1       
        //  1138: ifeq            1161
        //  1141: iload           6
        //  1143: ifne            1161
        //  1146: aload_0        
        //  1147: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //  1150: aload           29
        //  1152: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1157: pop            
        //  1158: goto            1204
        //  1161: iload           6
        //  1163: ifeq            1186
        //  1166: aload_0        
        //  1167: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1170: aload           19
        //  1172: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  1175: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  1180: invokevirtual   net/minecraft/block/Block.isOpaqueCube:()Z
        //  1183: ifeq            1186
        //  1186: aload           20
        //  1188: iload           5
        //  1190: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178577_a:(I)Z
        //  1193: pop            
        //  1194: aload           25
        //  1196: aload           29
        //  1198: invokeinterface java/util/Deque.add:(Ljava/lang/Object;)Z
        //  1203: pop            
        //  1204: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //  1207: astore          28
        //  1209: aload           28
        //  1211: arraylength    
        //  1212: istore          27
        //  1214: goto            1505
        //  1217: aload           25
        //  1219: invokeinterface java/util/Deque.poll:()Ljava/lang/Object;
        //  1224: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1227: astore          23
        //  1229: aload           23
        //  1231: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1234: astore          24
        //  1236: aload           23
        //  1238: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178034_b:Lnet/minecraft/util/EnumFacing;
        //  1241: astore          29
        //  1243: aload           24
        //  1245: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178568_j:()Lnet/minecraft/util/BlockPos;
        //  1248: astore          30
        //  1250: aload           24
        //  1252: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178590_b:Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //  1255: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178489_a:()Z
        //  1258: ifeq            1269
        //  1261: aload           24
        //  1263: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178569_m:()Z
        //  1266: ifeq            1281
        //  1269: aload_0        
        //  1270: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //  1273: aload           23
        //  1275: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1280: pop            
        //  1281: aload_0        
        //  1282: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1285: aload           30
        //  1287: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //  1290: invokestatic    optifine/ChunkUtils.hasEntities:(Lnet/minecraft/world/chunk/Chunk;)Z
        //  1293: ifeq            1308
        //  1296: aload_0        
        //  1297: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //  1300: aload           23
        //  1302: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1307: pop            
        //  1308: aload           24
        //  1310: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178571_g:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //  1313: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178485_b:()Ljava/util/List;
        //  1316: invokeinterface java/util/List.size:()I
        //  1321: ifle            1336
        //  1324: aload_0        
        //  1325: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //  1328: aload           23
        //  1330: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1335: pop            
        //  1336: goto            1499
        //  1339: aload           28
        //  1341: iconst_0       
        //  1342: aaload         
        //  1343: astore          32
        //  1345: iconst_0       
        //  1346: ifeq            1367
        //  1349: aload           23
        //  1351: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178035_c:Ljava/util/Set;
        //  1354: aload           32
        //  1356: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  1359: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1364: ifne            1496
        //  1367: iconst_0       
        //  1368: ifeq            1394
        //  1371: aload           29
        //  1373: ifnull          1394
        //  1376: aload           24
        //  1378: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178571_g:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //  1381: aload           29
        //  1383: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  1386: aload           32
        //  1388: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178495_a:(Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;)Z
        //  1391: ifeq            1496
        //  1394: aload_0        
        //  1395: aload           19
        //  1397: aload           24
        //  1399: aload           32
        //  1401: invokespecial   net/minecraft/client/renderer/RenderGlobal.getRenderChunkOffset:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1404: astore          33
        //  1406: aload           33
        //  1408: ifnull          1496
        //  1411: aload           33
        //  1413: iload           5
        //  1415: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178577_a:(I)Z
        //  1418: ifeq            1496
        //  1421: aload           4
        //  1423: aload           33
        //  1425: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178591_c:Lnet/minecraft/util/AxisAlignedBB;
        //  1428: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  1433: ifeq            1496
        //  1436: new             Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1439: dup            
        //  1440: aload           33
        //  1442: aload           32
        //  1444: aload           23
        //  1446: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178032_d:I
        //  1449: iconst_1       
        //  1450: iadd           
        //  1451: aconst_null    
        //  1452: invokespecial   net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.<init>:(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;ILjava/lang/Object;)V
        //  1455: astore          34
        //  1457: aload           34
        //  1459: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178035_c:Ljava/util/Set;
        //  1462: aload           23
        //  1464: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178035_c:Ljava/util/Set;
        //  1467: invokeinterface java/util/Set.addAll:(Ljava/util/Collection;)Z
        //  1472: pop            
        //  1473: aload           34
        //  1475: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178035_c:Ljava/util/Set;
        //  1478: aload           32
        //  1480: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //  1485: pop            
        //  1486: aload           25
        //  1488: aload           34
        //  1490: invokeinterface java/util/Deque.add:(Ljava/lang/Object;)Z
        //  1495: pop            
        //  1496: iinc            31, 1
        //  1499: iconst_0       
        //  1500: iload           27
        //  1502: if_icmplt       1339
        //  1505: aload           25
        //  1507: invokeinterface java/util/Deque.isEmpty:()Z
        //  1512: ifeq            1217
        //  1515: aload_0        
        //  1516: getfield        net/minecraft/client/renderer/RenderGlobal.field_175002_T:Z
        //  1519: ifeq            1537
        //  1522: aload_0        
        //  1523: dload           13
        //  1525: dload           15
        //  1527: dload           17
        //  1529: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_174984_a:(DDD)V
        //  1532: aload_0        
        //  1533: iconst_0       
        //  1534: putfield        net/minecraft/client/renderer/RenderGlobal.field_175002_T:Z
        //  1537: getstatic       optifine/Lagometer.timerVisibility:Loptifine/Lagometer$TimerNano;
        //  1540: invokevirtual   optifine/Lagometer$TimerNano.end:()V
        //  1543: getstatic       shadersmod/client/Shaders.isShadowPass:Z
        //  1546: ifeq            1552
        //  1549: goto            1765
        //  1552: aload_0        
        //  1553: getfield        net/minecraft/client/renderer/RenderGlobal.field_174995_M:Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher;
        //  1556: invokevirtual   net/minecraft/client/renderer/chunk/ChunkRenderDispatcher.func_178513_e:()V
        //  1559: aload_0        
        //  1560: getfield        net/minecraft/client/renderer/RenderGlobal.field_175009_l:Ljava/util/Set;
        //  1563: astore          25
        //  1565: aload_0        
        //  1566: invokestatic    com/google/common/collect/Sets.newLinkedHashSet:()Ljava/util/LinkedHashSet;
        //  1569: putfield        net/minecraft/client/renderer/RenderGlobal.field_175009_l:Ljava/util/Set;
        //  1572: aload_0        
        //  1573: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //  1576: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1581: astore          26
        //  1583: getstatic       optifine/Lagometer.timerChunkUpdate:Loptifine/Lagometer$TimerNano;
        //  1586: invokevirtual   optifine/Lagometer$TimerNano.start:()V
        //  1589: goto            1727
        //  1592: aload           26
        //  1594: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1599: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1602: astore          23
        //  1604: aload           23
        //  1606: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1609: astore          24
        //  1611: aload           24
        //  1613: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178569_m:()Z
        //  1616: ifne            1631
        //  1619: aload           25
        //  1621: aload           24
        //  1623: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1628: ifeq            1727
        //  1631: aload_0        
        //  1632: iconst_1       
        //  1633: putfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //  1636: aload_0        
        //  1637: aload           21
        //  1639: aload           23
        //  1641: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1644: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_174983_a:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/client/renderer/chunk/RenderChunk;)Z
        //  1647: ifeq            1715
        //  1650: aload           24
        //  1652: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.isPlayerUpdate:()Z
        //  1655: ifne            1673
        //  1658: aload_0        
        //  1659: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToUpdateForced:Ljava/util/Set;
        //  1662: aload           24
        //  1664: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //  1669: pop            
        //  1670: goto            1727
        //  1673: aload_0        
        //  1674: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1677: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1680: ldc_w           "build near"
        //  1683: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //  1686: aload_0        
        //  1687: getfield        net/minecraft/client/renderer/RenderGlobal.field_174995_M:Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher;
        //  1690: aload           24
        //  1692: invokevirtual   net/minecraft/client/renderer/chunk/ChunkRenderDispatcher.func_178505_b:(Lnet/minecraft/client/renderer/chunk/RenderChunk;)Z
        //  1695: pop            
        //  1696: aload           24
        //  1698: iconst_0       
        //  1699: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178575_a:(Z)V
        //  1702: aload_0        
        //  1703: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1706: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1709: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //  1712: goto            1727
        //  1715: aload_0        
        //  1716: getfield        net/minecraft/client/renderer/RenderGlobal.field_175009_l:Ljava/util/Set;
        //  1719: aload           24
        //  1721: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //  1726: pop            
        //  1727: aload           26
        //  1729: invokeinterface java/util/Iterator.hasNext:()Z
        //  1734: ifne            1592
        //  1737: getstatic       optifine/Lagometer.timerChunkUpdate:Loptifine/Lagometer$TimerNano;
        //  1740: invokevirtual   optifine/Lagometer$TimerNano.end:()V
        //  1743: aload_0        
        //  1744: getfield        net/minecraft/client/renderer/RenderGlobal.field_175009_l:Ljava/util/Set;
        //  1747: aload           25
        //  1749: invokeinterface java/util/Set.addAll:(Ljava/util/Collection;)Z
        //  1754: pop            
        //  1755: aload_0        
        //  1756: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1759: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1762: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //  1765: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean func_174983_a(final BlockPos blockPos, final RenderChunk renderChunk) {
        final BlockPos func_178568_j = renderChunk.func_178568_j();
        return MathHelper.abs_int(blockPos.getX() - func_178568_j.getX()) <= 16 && MathHelper.abs_int(blockPos.getY() - func_178568_j.getY()) <= 16 && MathHelper.abs_int(blockPos.getZ() - func_178568_j.getZ()) <= 16;
    }
    
    private Set func_174978_c(final BlockPos blockPos) {
        final VisGraph visGraph = new VisGraph();
        final BlockPos blockPos2 = new BlockPos(blockPos.getX() >> 4 << 4, blockPos.getY() >> 4 << 4, blockPos.getZ() >> 4 << 4);
        final Chunk chunkFromBlockCoords = this.theWorld.getChunkFromBlockCoords(blockPos2);
        for (final BlockPos.MutableBlockPos mutableBlockPos : BlockPos.getAllInBoxMutable(blockPos2, blockPos2.add(15, 15, 15))) {
            if (chunkFromBlockCoords.getBlock(mutableBlockPos).isOpaqueCube()) {
                visGraph.func_178606_a(mutableBlockPos);
            }
        }
        return visGraph.func_178609_b(blockPos);
    }
    
    private RenderChunk getRenderChunkOffset(final BlockPos blockPos, final RenderChunk renderChunk, final EnumFacing enumFacing) {
        final BlockPos positionOffset16 = renderChunk.getPositionOffset16(enumFacing);
        if (positionOffset16.getY() >= 0 && positionOffset16.getY() < 256) {
            final int abs_int = MathHelper.abs_int(blockPos.getX() - positionOffset16.getX());
            final int abs_int2 = MathHelper.abs_int(blockPos.getZ() - positionOffset16.getZ());
            if (Config.isFogOff()) {
                if (abs_int > this.renderDistance || abs_int2 > this.renderDistance) {
                    return null;
                }
            }
            else if (abs_int * abs_int + abs_int2 * abs_int2 > this.renderDistanceSq) {
                return null;
            }
            return this.field_175008_n.func_178161_a(positionOffset16);
        }
        return null;
    }
    
    private void func_174984_a(final double x, final double y, final double z) {
        this.field_175001_U = new ClippingHelperImpl();
        ((ClippingHelperImpl)this.field_175001_U).init();
        final Matrix4f matrix4f = new Matrix4f(this.field_175001_U.field_178626_c);
        matrix4f.transpose();
        final Matrix4f matrix4f2 = new Matrix4f(this.field_175001_U.field_178625_b);
        matrix4f2.transpose();
        final Matrix4f matrix4f3 = new Matrix4f();
        matrix4f3.mul(matrix4f2, matrix4f);
        matrix4f3.invert();
        this.field_175003_W.x = x;
        this.field_175003_W.y = y;
        this.field_175003_W.z = z;
        this.field_175004_V[0] = new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f);
        this.field_175004_V[1] = new Vector4f(1.0f, -1.0f, -1.0f, 1.0f);
        this.field_175004_V[2] = new Vector4f(1.0f, 1.0f, -1.0f, 1.0f);
        this.field_175004_V[3] = new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f);
        this.field_175004_V[4] = new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f);
        this.field_175004_V[5] = new Vector4f(1.0f, -1.0f, 1.0f, 1.0f);
        this.field_175004_V[6] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_175004_V[7] = new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f);
        while (0 < 8) {
            matrix4f3.transform(this.field_175004_V[0]);
            final Vector4f vector4f = this.field_175004_V[0];
            vector4f.x /= this.field_175004_V[0].w;
            final Vector4f vector4f2 = this.field_175004_V[0];
            vector4f2.y /= this.field_175004_V[0].w;
            final Vector4f vector4f3 = this.field_175004_V[0];
            vector4f3.z /= this.field_175004_V[0].w;
            this.field_175004_V[0].w = 1.0f;
            int n = 0;
            ++n;
        }
    }
    
    protected Vector3f func_174962_a(final Entity entity, final double n) {
        float n2 = (float)(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * n);
        final float n3 = (float)(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * n);
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
            n2 += 180.0f;
        }
        final float cos = MathHelper.cos(-n3 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n3 * 0.017453292f - 3.1415927f);
        final float n4 = -MathHelper.cos(-n2 * 0.017453292f);
        return new Vector3f(sin * n4, MathHelper.sin(-n2 * 0.017453292f), cos * n4);
    }
    
    public int func_174977_a(final EnumWorldBlockLayer p0, final double p1, final int p2, final Entity p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       net/minecraft/util/EnumWorldBlockLayer.TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //     4: if_acmpne       199
        //     7: aload_0        
        //     8: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    11: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    14: ldc_w           "translucent_sort"
        //    17: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    20: aload           5
        //    22: getfield        net/minecraft/entity/Entity.posX:D
        //    25: aload_0        
        //    26: getfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortX:D
        //    29: dsub           
        //    30: dstore          6
        //    32: aload           5
        //    34: getfield        net/minecraft/entity/Entity.posY:D
        //    37: aload_0        
        //    38: getfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortY:D
        //    41: dsub           
        //    42: dstore          8
        //    44: aload           5
        //    46: getfield        net/minecraft/entity/Entity.posZ:D
        //    49: aload_0        
        //    50: getfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortZ:D
        //    53: dsub           
        //    54: dstore          10
        //    56: dload           6
        //    58: dload           6
        //    60: dmul           
        //    61: dload           8
        //    63: dload           8
        //    65: dmul           
        //    66: dadd           
        //    67: dload           10
        //    69: dload           10
        //    71: dmul           
        //    72: dadd           
        //    73: dconst_1       
        //    74: dcmpl          
        //    75: ifle            189
        //    78: aload_0        
        //    79: aload           5
        //    81: getfield        net/minecraft/entity/Entity.posX:D
        //    84: putfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortX:D
        //    87: aload_0        
        //    88: aload           5
        //    90: getfield        net/minecraft/entity/Entity.posY:D
        //    93: putfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortY:D
        //    96: aload_0        
        //    97: aload           5
        //    99: getfield        net/minecraft/entity/Entity.posZ:D
        //   102: putfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortZ:D
        //   105: aload_0        
        //   106: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   109: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   114: astore          13
        //   116: aload_0        
        //   117: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToResortTransparency:Ljava/util/Set;
        //   120: invokeinterface java/util/Set.clear:()V
        //   125: goto            179
        //   128: aload           13
        //   130: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   135: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   138: astore          14
        //   140: aload           14
        //   142: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   145: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_178590_b:Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //   148: aload_1        
        //   149: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178492_d:(Lnet/minecraft/util/EnumWorldBlockLayer;)Z
        //   152: ifeq            179
        //   155: iconst_0       
        //   156: iinc            12, 1
        //   159: bipush          15
        //   161: if_icmpge       179
        //   164: aload_0        
        //   165: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToResortTransparency:Ljava/util/Set;
        //   168: aload           14
        //   170: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   173: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   178: pop            
        //   179: aload           13
        //   181: invokeinterface java/util/Iterator.hasNext:()Z
        //   186: ifne            128
        //   189: aload_0        
        //   190: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   193: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   196: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   199: aload_0        
        //   200: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   203: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   206: ldc_w           "filterempty"
        //   209: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   212: aload_1        
        //   213: getstatic       net/minecraft/util/EnumWorldBlockLayer.TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   216: if_acmpne       223
        //   219: iconst_1       
        //   220: goto            224
        //   223: iconst_0       
        //   224: istore          7
        //   226: iload           7
        //   228: ifeq            245
        //   231: aload_0        
        //   232: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   235: invokeinterface java/util/List.size:()I
        //   240: iconst_1       
        //   241: isub           
        //   242: goto            246
        //   245: iconst_0       
        //   246: istore          8
        //   248: iload           7
        //   250: ifeq            257
        //   253: iconst_m1      
        //   254: goto            266
        //   257: aload_0        
        //   258: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   261: invokeinterface java/util/List.size:()I
        //   266: istore          9
        //   268: iload           7
        //   270: ifeq            277
        //   273: iconst_m1      
        //   274: goto            278
        //   277: iconst_1       
        //   278: istore          10
        //   280: iload           8
        //   282: istore          11
        //   284: goto            338
        //   287: aload_0        
        //   288: getfield        net/minecraft/client/renderer/RenderGlobal.glRenderLists:Ljava/util/List;
        //   291: iload           11
        //   293: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   298: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   301: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.field_178036_a:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   304: astore          12
        //   306: aload           12
        //   308: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.func_178571_g:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //   311: aload_1        
        //   312: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.func_178491_b:(Lnet/minecraft/util/EnumWorldBlockLayer;)Z
        //   315: ifne            331
        //   318: iinc            6, 1
        //   321: aload_0        
        //   322: getfield        net/minecraft/client/renderer/RenderGlobal.field_174996_N:Lnet/minecraft/client/renderer/ChunkRenderContainer;
        //   325: aload           12
        //   327: aload_1        
        //   328: invokevirtual   net/minecraft/client/renderer/ChunkRenderContainer.func_178002_a:(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   331: iload           11
        //   333: iload           10
        //   335: iadd           
        //   336: istore          11
        //   338: iload           11
        //   340: iload           9
        //   342: if_icmpne       287
        //   345: iconst_0       
        //   346: ifne            361
        //   349: aload_0        
        //   350: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   353: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   356: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   359: iconst_0       
        //   360: ireturn        
        //   361: invokestatic    optifine/Config.isFogOff:()Z
        //   364: ifeq            377
        //   367: aload_0        
        //   368: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   371: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //   374: getfield        net/minecraft/client/renderer/EntityRenderer.fogStandard:Z
        //   377: aload_0        
        //   378: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   381: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   384: new             Ljava/lang/StringBuilder;
        //   387: dup            
        //   388: ldc_w           "render_"
        //   391: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   394: aload_1        
        //   395: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   398: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   401: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   404: aload_0        
        //   405: aload_1        
        //   406: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_174982_a:(Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   409: aload_0        
        //   410: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   413: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   416: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   419: iconst_0       
        //   420: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0377 (coming from #0374).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void func_174982_a(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.mc.entityRenderer.func_180436_i();
        if (OpenGlHelper.func_176075_f()) {
            GL11.glEnableClientState(32884);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState(32886);
        }
        Config.isShaders();
        this.field_174996_N.func_178001_a(enumWorldBlockLayer);
        Config.isShaders();
        if (OpenGlHelper.func_176075_f()) {
            for (final VertexFormatElement vertexFormatElement : DefaultVertexFormats.field_176600_a.func_177343_g()) {
                final VertexFormatElement.EnumUseage func_177375_c = vertexFormatElement.func_177375_c();
                final int func_177369_e = vertexFormatElement.func_177369_e();
                switch (SwitchEnumUseage.field_178037_a[func_177375_c.ordinal()]) {
                    default: {
                        continue;
                    }
                    case 1: {
                        GL11.glDisableClientState(32884);
                        continue;
                    }
                    case 2: {
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + func_177369_e);
                        GL11.glDisableClientState(32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        continue;
                    }
                    case 3: {
                        GL11.glDisableClientState(32886);
                        continue;
                    }
                }
            }
        }
        this.mc.entityRenderer.func_175072_h();
    }
    
    private void func_174965_a(final Iterator iterator) {
        while (iterator.hasNext()) {
            if (this.cloudTickCounter - iterator.next().getCreationCloudUpdateTick() > 400) {
                iterator.remove();
            }
        }
    }
    
    public void updateClouds() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            19
        //     6: bipush          61
        //     8: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //    11: ifeq            19
        //    14: bipush          19
        //    16: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //    19: aload_0        
        //    20: dup            
        //    21: getfield        net/minecraft/client/renderer/RenderGlobal.cloudTickCounter:I
        //    24: iconst_1       
        //    25: iadd           
        //    26: putfield        net/minecraft/client/renderer/RenderGlobal.cloudTickCounter:I
        //    29: aload_0        
        //    30: getfield        net/minecraft/client/renderer/RenderGlobal.cloudTickCounter:I
        //    33: bipush          20
        //    35: irem           
        //    36: ifne            57
        //    39: aload_0        
        //    40: aload_0        
        //    41: getfield        net/minecraft/client/renderer/RenderGlobal.damagedBlocks:Ljava/util/Map;
        //    44: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //    49: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    54: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_174965_a:(Ljava/util/Iterator;)V
        //    57: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0019 (coming from #0016).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void func_180448_r() {
        if (Config.isSkyEnabled()) {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.depthMask(false);
            this.renderEngine.bindTexture(RenderGlobal.locationEndSkyPng);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            while (0 < 6) {
                if (false == true) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (0 == 2) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (0 == 3) {
                    GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                }
                if (0 == 4) {
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                }
                if (0 == 5) {
                    GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f);
                }
                worldRenderer.startDrawingQuads();
                worldRenderer.func_178991_c(2631720);
                worldRenderer.addVertexWithUV(-100.0, -100.0, -100.0, 0.0, 0.0);
                worldRenderer.addVertexWithUV(-100.0, -100.0, 100.0, 0.0, 16.0);
                worldRenderer.addVertexWithUV(100.0, -100.0, 100.0, 16.0, 16.0);
                worldRenderer.addVertexWithUV(100.0, -100.0, -100.0, 16.0, 0.0);
                instance.draw();
                int n = 0;
                ++n;
            }
            GlStateManager.depthMask(true);
        }
    }
    
    public void func_174976_a(final float p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //     6: ifeq            68
        //     9: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    12: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //    15: astore_3       
        //    16: aload_3        
        //    17: getstatic       optifine/Reflector.ForgeWorldProvider_getSkyRenderer:Loptifine/ReflectorMethod;
        //    20: iconst_0       
        //    21: anewarray       Ljava/lang/Object;
        //    24: invokestatic    optifine/Reflector.call:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //    27: astore          4
        //    29: aload           4
        //    31: ifnull          68
        //    34: aload           4
        //    36: getstatic       optifine/Reflector.IRenderHandler_render:Loptifine/ReflectorMethod;
        //    39: iconst_3       
        //    40: anewarray       Ljava/lang/Object;
        //    43: dup            
        //    44: iconst_0       
        //    45: fload_1        
        //    46: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    49: aastore        
        //    50: dup            
        //    51: iconst_1       
        //    52: aload_0        
        //    53: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    56: aastore        
        //    57: dup            
        //    58: iconst_2       
        //    59: aload_0        
        //    60: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    63: aastore        
        //    64: invokestatic    optifine/Reflector.callVoid:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //    67: return         
        //    68: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    71: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //    74: invokevirtual   net/minecraft/world/WorldProvider.getDimensionId:()I
        //    77: iconst_1       
        //    78: if_icmpne       88
        //    81: aload_0        
        //    82: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_180448_r:()V
        //    85: goto            1692
        //    88: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    91: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //    94: invokevirtual   net/minecraft/world/WorldProvider.isSurfaceWorld:()Z
        //    97: ifeq            1692
        //   100: invokestatic    optifine/Config.isShaders:()Z
        //   103: istore_3       
        //   104: iload_3        
        //   105: aload_0        
        //   106: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   109: aload_0        
        //   110: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   113: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   116: fload_1        
        //   117: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getSkyColor:(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/util/Vec3;
        //   120: astore          4
        //   122: aload           4
        //   124: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   127: aload_0        
        //   128: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   131: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   134: getfield        net/minecraft/entity/Entity.posX:D
        //   137: aload_0        
        //   138: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   141: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   144: getfield        net/minecraft/entity/Entity.posY:D
        //   147: dconst_1       
        //   148: dadd           
        //   149: aload_0        
        //   150: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   153: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   156: getfield        net/minecraft/entity/Entity.posZ:D
        //   159: invokestatic    optifine/CustomColors.getSkyColor:(Lnet/minecraft/util/Vec3;Lnet/minecraft/world/IBlockAccess;DDD)Lnet/minecraft/util/Vec3;
        //   162: astore          4
        //   164: iload_3        
        //   165: ifeq            173
        //   168: aload           4
        //   170: invokestatic    shadersmod/client/Shaders.setSkyColor:(Lnet/minecraft/util/Vec3;)V
        //   173: aload           4
        //   175: getfield        net/minecraft/util/Vec3.xCoord:D
        //   178: d2f            
        //   179: fstore          5
        //   181: aload           4
        //   183: getfield        net/minecraft/util/Vec3.yCoord:D
        //   186: d2f            
        //   187: fstore          6
        //   189: aload           4
        //   191: getfield        net/minecraft/util/Vec3.zCoord:D
        //   194: d2f            
        //   195: fstore          7
        //   197: iload_2        
        //   198: iconst_2       
        //   199: if_icmpeq       278
        //   202: fload           5
        //   204: ldc_w           30.0
        //   207: fmul           
        //   208: fload           6
        //   210: ldc_w           59.0
        //   213: fmul           
        //   214: fadd           
        //   215: fload           7
        //   217: ldc_w           11.0
        //   220: fmul           
        //   221: fadd           
        //   222: ldc_w           100.0
        //   225: fdiv           
        //   226: fstore          8
        //   228: fload           5
        //   230: ldc_w           30.0
        //   233: fmul           
        //   234: fload           6
        //   236: ldc_w           70.0
        //   239: fmul           
        //   240: fadd           
        //   241: ldc_w           100.0
        //   244: fdiv           
        //   245: fstore          9
        //   247: fload           5
        //   249: ldc_w           30.0
        //   252: fmul           
        //   253: fload           7
        //   255: ldc_w           70.0
        //   258: fmul           
        //   259: fadd           
        //   260: ldc_w           100.0
        //   263: fdiv           
        //   264: fstore          10
        //   266: fload           8
        //   268: fstore          5
        //   270: fload           9
        //   272: fstore          6
        //   274: fload           10
        //   276: fstore          7
        //   278: fload           5
        //   280: fload           6
        //   282: fload           7
        //   284: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //   287: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //   290: astore          8
        //   292: aload           8
        //   294: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //   297: astore          9
        //   299: iconst_0       
        //   300: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   303: iload_3        
        //   304: fload           5
        //   306: fload           6
        //   308: fload           7
        //   310: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //   313: iload_3        
        //   314: invokestatic    optifine/Config.isSkyEnabled:()Z
        //   317: ifeq            382
        //   320: aload_0        
        //   321: getfield        net/minecraft/client/renderer/RenderGlobal.field_175005_X:Z
        //   324: ifeq            375
        //   327: aload_0        
        //   328: getfield        net/minecraft/client/renderer/RenderGlobal.field_175012_t:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //   331: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177359_a:()V
        //   334: ldc_w           32884
        //   337: invokestatic    org/lwjgl/opengl/GL11.glEnableClientState:(I)V
        //   340: iconst_3       
        //   341: sipush          5126
        //   344: bipush          12
        //   346: lconst_0       
        //   347: invokestatic    org/lwjgl/opengl/GL11.glVertexPointer:(IIIJ)V
        //   350: aload_0        
        //   351: getfield        net/minecraft/client/renderer/RenderGlobal.field_175012_t:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //   354: bipush          7
        //   356: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177358_a:(I)V
        //   359: aload_0        
        //   360: getfield        net/minecraft/client/renderer/RenderGlobal.field_175012_t:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //   363: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177361_b:()V
        //   366: ldc_w           32884
        //   369: invokestatic    org/lwjgl/opengl/GL11.glDisableClientState:(I)V
        //   372: goto            382
        //   375: aload_0        
        //   376: getfield        net/minecraft/client/renderer/RenderGlobal.glSkyList:I
        //   379: invokestatic    net/minecraft/client/renderer/GlStateManager.callList:(I)V
        //   382: iload_3        
        //   383: sipush          770
        //   386: sipush          771
        //   389: iconst_1       
        //   390: iconst_0       
        //   391: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //   394: aload_0        
        //   395: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   398: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //   401: aload_0        
        //   402: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   405: fload_1        
        //   406: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCelestialAngle:(F)F
        //   409: fload_1        
        //   410: invokevirtual   net/minecraft/world/WorldProvider.calcSunriseSunsetColors:(FF)[F
        //   413: astore          10
        //   415: aload           10
        //   417: ifnull          713
        //   420: invokestatic    optifine/Config.isSunMoonEnabled:()Z
        //   423: ifeq            713
        //   426: iload_3        
        //   427: sipush          7425
        //   430: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //   433: ldc_w           90.0
        //   436: fconst_1       
        //   437: fconst_0       
        //   438: fconst_0       
        //   439: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   442: aload_0        
        //   443: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   446: fload_1        
        //   447: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCelestialAngleRadians:(F)F
        //   450: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   453: fconst_0       
        //   454: fcmpg          
        //   455: ifge            464
        //   458: ldc_w           180.0
        //   461: goto            465
        //   464: fconst_0       
        //   465: fconst_0       
        //   466: fconst_0       
        //   467: fconst_1       
        //   468: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   471: ldc_w           90.0
        //   474: fconst_0       
        //   475: fconst_0       
        //   476: fconst_1       
        //   477: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   480: aload           10
        //   482: iconst_0       
        //   483: faload         
        //   484: fstore          11
        //   486: aload           10
        //   488: iconst_1       
        //   489: faload         
        //   490: fstore          12
        //   492: aload           10
        //   494: iconst_2       
        //   495: faload         
        //   496: fstore          13
        //   498: iload_2        
        //   499: iconst_2       
        //   500: if_icmpeq       579
        //   503: fload           11
        //   505: ldc_w           30.0
        //   508: fmul           
        //   509: fload           12
        //   511: ldc_w           59.0
        //   514: fmul           
        //   515: fadd           
        //   516: fload           13
        //   518: ldc_w           11.0
        //   521: fmul           
        //   522: fadd           
        //   523: ldc_w           100.0
        //   526: fdiv           
        //   527: fstore          14
        //   529: fload           11
        //   531: ldc_w           30.0
        //   534: fmul           
        //   535: fload           12
        //   537: ldc_w           70.0
        //   540: fmul           
        //   541: fadd           
        //   542: ldc_w           100.0
        //   545: fdiv           
        //   546: fstore          15
        //   548: fload           11
        //   550: ldc_w           30.0
        //   553: fmul           
        //   554: fload           13
        //   556: ldc_w           70.0
        //   559: fmul           
        //   560: fadd           
        //   561: ldc_w           100.0
        //   564: fdiv           
        //   565: fstore          16
        //   567: fload           14
        //   569: fstore          11
        //   571: fload           15
        //   573: fstore          12
        //   575: fload           16
        //   577: fstore          13
        //   579: aload           9
        //   581: bipush          6
        //   583: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawing:(I)V
        //   586: aload           9
        //   588: fload           11
        //   590: fload           12
        //   592: fload           13
        //   594: aload           10
        //   596: iconst_3       
        //   597: faload         
        //   598: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178960_a:(FFFF)V
        //   601: aload           9
        //   603: dconst_0       
        //   604: ldc2_w          100.0
        //   607: dconst_0       
        //   608: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   611: aload           9
        //   613: aload           10
        //   615: iconst_0       
        //   616: faload         
        //   617: aload           10
        //   619: iconst_1       
        //   620: faload         
        //   621: aload           10
        //   623: iconst_2       
        //   624: faload         
        //   625: fconst_0       
        //   626: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178960_a:(FFFF)V
        //   629: goto            695
        //   632: iconst_0       
        //   633: i2f            
        //   634: ldc_w           3.1415927
        //   637: fmul           
        //   638: fconst_2       
        //   639: fmul           
        //   640: ldc_w           16.0
        //   643: fdiv           
        //   644: fstore          16
        //   646: fload           16
        //   648: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   651: fstore          18
        //   653: fload           16
        //   655: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //   658: fstore          19
        //   660: aload           9
        //   662: fload           18
        //   664: ldc_w           120.0
        //   667: fmul           
        //   668: f2d            
        //   669: fload           19
        //   671: ldc_w           120.0
        //   674: fmul           
        //   675: f2d            
        //   676: fload           19
        //   678: fneg           
        //   679: ldc_w           40.0
        //   682: fmul           
        //   683: aload           10
        //   685: iconst_3       
        //   686: faload         
        //   687: fmul           
        //   688: f2d            
        //   689: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   692: iinc            17, 1
        //   695: iconst_0       
        //   696: bipush          16
        //   698: if_icmple       632
        //   701: aload           8
        //   703: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //   706: pop            
        //   707: sipush          7424
        //   710: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //   713: iload_3        
        //   714: sipush          770
        //   717: iconst_1       
        //   718: iconst_1       
        //   719: iconst_0       
        //   720: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //   723: fconst_1       
        //   724: aload_0        
        //   725: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   728: fload_1        
        //   729: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getRainStrength:(F)F
        //   732: fsub           
        //   733: fstore          11
        //   735: fconst_0       
        //   736: fstore          12
        //   738: fconst_0       
        //   739: fstore          13
        //   741: fconst_0       
        //   742: fstore          14
        //   744: fconst_1       
        //   745: fconst_1       
        //   746: fconst_1       
        //   747: fload           11
        //   749: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //   752: fconst_0       
        //   753: fconst_0       
        //   754: fconst_0       
        //   755: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   758: ldc_w           -90.0
        //   761: fconst_0       
        //   762: fconst_1       
        //   763: fconst_0       
        //   764: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   767: aload_0        
        //   768: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   771: aload_0        
        //   772: getfield        net/minecraft/client/renderer/RenderGlobal.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   775: aload_0        
        //   776: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   779: fload_1        
        //   780: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCelestialAngle:(F)F
        //   783: fload           11
        //   785: invokestatic    optifine/CustomSky.renderSky:(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;FF)V
        //   788: iload_3        
        //   789: aload_0        
        //   790: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   793: fload_1        
        //   794: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCelestialAngle:(F)F
        //   797: ldc_w           360.0
        //   800: fmul           
        //   801: fconst_1       
        //   802: fconst_0       
        //   803: fconst_0       
        //   804: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   807: iload_3        
        //   808: invokestatic    optifine/Config.isSunMoonEnabled:()Z
        //   811: ifeq            1055
        //   814: ldc_w           30.0
        //   817: fstore          15
        //   819: aload_0        
        //   820: getfield        net/minecraft/client/renderer/RenderGlobal.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   823: getstatic       net/minecraft/client/renderer/RenderGlobal.locationSunPng:Lnet/minecraft/util/ResourceLocation;
        //   826: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   829: aload           9
        //   831: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawingQuads:()V
        //   834: aload           9
        //   836: fload           15
        //   838: fneg           
        //   839: f2d            
        //   840: ldc2_w          100.0
        //   843: fload           15
        //   845: fneg           
        //   846: f2d            
        //   847: dconst_0       
        //   848: dconst_0       
        //   849: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   852: aload           9
        //   854: fload           15
        //   856: f2d            
        //   857: ldc2_w          100.0
        //   860: fload           15
        //   862: fneg           
        //   863: f2d            
        //   864: dconst_1       
        //   865: dconst_0       
        //   866: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   869: aload           9
        //   871: fload           15
        //   873: f2d            
        //   874: ldc2_w          100.0
        //   877: fload           15
        //   879: f2d            
        //   880: dconst_1       
        //   881: dconst_1       
        //   882: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   885: aload           9
        //   887: fload           15
        //   889: fneg           
        //   890: f2d            
        //   891: ldc2_w          100.0
        //   894: fload           15
        //   896: f2d            
        //   897: dconst_0       
        //   898: dconst_1       
        //   899: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   902: aload           8
        //   904: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //   907: pop            
        //   908: ldc_w           20.0
        //   911: fstore          15
        //   913: aload_0        
        //   914: getfield        net/minecraft/client/renderer/RenderGlobal.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   917: getstatic       net/minecraft/client/renderer/RenderGlobal.locationMoonPhasesPng:Lnet/minecraft/util/ResourceLocation;
        //   920: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   923: aload_0        
        //   924: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   927: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getMoonPhase:()I
        //   930: istore          20
        //   932: iconst_1       
        //   933: i2f            
        //   934: ldc_w           4.0
        //   937: fdiv           
        //   938: fstore          18
        //   940: iconst_0       
        //   941: i2f            
        //   942: fconst_2       
        //   943: fdiv           
        //   944: fstore          19
        //   946: iconst_2       
        //   947: i2f            
        //   948: ldc_w           4.0
        //   951: fdiv           
        //   952: fstore          22
        //   954: iconst_1       
        //   955: i2f            
        //   956: fconst_2       
        //   957: fdiv           
        //   958: fstore          23
        //   960: aload           9
        //   962: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawingQuads:()V
        //   965: aload           9
        //   967: fload           15
        //   969: fneg           
        //   970: f2d            
        //   971: ldc2_w          -100.0
        //   974: fload           15
        //   976: f2d            
        //   977: fload           22
        //   979: f2d            
        //   980: fload           23
        //   982: f2d            
        //   983: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   986: aload           9
        //   988: fload           15
        //   990: f2d            
        //   991: ldc2_w          -100.0
        //   994: fload           15
        //   996: f2d            
        //   997: fload           18
        //   999: f2d            
        //  1000: fload           23
        //  1002: f2d            
        //  1003: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //  1006: aload           9
        //  1008: fload           15
        //  1010: f2d            
        //  1011: ldc2_w          -100.0
        //  1014: fload           15
        //  1016: fneg           
        //  1017: f2d            
        //  1018: fload           18
        //  1020: f2d            
        //  1021: fload           19
        //  1023: f2d            
        //  1024: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //  1027: aload           9
        //  1029: fload           15
        //  1031: fneg           
        //  1032: f2d            
        //  1033: ldc2_w          -100.0
        //  1036: fload           15
        //  1038: fneg           
        //  1039: f2d            
        //  1040: fload           22
        //  1042: f2d            
        //  1043: fload           19
        //  1045: f2d            
        //  1046: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //  1049: aload           8
        //  1051: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //  1054: pop            
        //  1055: iload_3        
        //  1056: aload_0        
        //  1057: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1060: fload_1        
        //  1061: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getStarBrightness:(F)F
        //  1064: fload           11
        //  1066: fmul           
        //  1067: fstore          16
        //  1069: fload           16
        //  1071: fconst_0       
        //  1072: fcmpl          
        //  1073: ifle            1165
        //  1076: invokestatic    optifine/Config.isStarsEnabled:()Z
        //  1079: ifeq            1165
        //  1082: aload_0        
        //  1083: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1086: invokestatic    optifine/CustomSky.hasSkyLayers:(Lnet/minecraft/world/World;)Z
        //  1089: ifne            1165
        //  1092: fload           16
        //  1094: fload           16
        //  1096: fload           16
        //  1098: fload           16
        //  1100: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //  1103: aload_0        
        //  1104: getfield        net/minecraft/client/renderer/RenderGlobal.field_175005_X:Z
        //  1107: ifeq            1158
        //  1110: aload_0        
        //  1111: getfield        net/minecraft/client/renderer/RenderGlobal.field_175013_s:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1114: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177359_a:()V
        //  1117: ldc_w           32884
        //  1120: invokestatic    org/lwjgl/opengl/GL11.glEnableClientState:(I)V
        //  1123: iconst_3       
        //  1124: sipush          5126
        //  1127: bipush          12
        //  1129: lconst_0       
        //  1130: invokestatic    org/lwjgl/opengl/GL11.glVertexPointer:(IIIJ)V
        //  1133: aload_0        
        //  1134: getfield        net/minecraft/client/renderer/RenderGlobal.field_175013_s:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1137: bipush          7
        //  1139: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177358_a:(I)V
        //  1142: aload_0        
        //  1143: getfield        net/minecraft/client/renderer/RenderGlobal.field_175013_s:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1146: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177361_b:()V
        //  1149: ldc_w           32884
        //  1152: invokestatic    org/lwjgl/opengl/GL11.glDisableClientState:(I)V
        //  1155: goto            1165
        //  1158: aload_0        
        //  1159: getfield        net/minecraft/client/renderer/RenderGlobal.starGLCallList:I
        //  1162: invokestatic    net/minecraft/client/renderer/GlStateManager.callList:(I)V
        //  1165: fconst_1       
        //  1166: fconst_1       
        //  1167: fconst_1       
        //  1168: fconst_1       
        //  1169: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //  1172: iload_3        
        //  1173: iload_3        
        //  1174: fconst_0       
        //  1175: fconst_0       
        //  1176: fconst_0       
        //  1177: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //  1180: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1183: fload_1        
        //  1184: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_174824_e:(F)Lnet/minecraft/util/Vec3;
        //  1187: getfield        net/minecraft/util/Vec3.yCoord:D
        //  1190: aload_0        
        //  1191: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1194: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getHorizon:()D
        //  1197: dsub           
        //  1198: dstore          20
        //  1200: dload           20
        //  1202: dconst_0       
        //  1203: dcmpg          
        //  1204: ifge            1555
        //  1207: fconst_0       
        //  1208: ldc_w           12.0
        //  1211: fconst_0       
        //  1212: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //  1215: aload_0        
        //  1216: getfield        net/minecraft/client/renderer/RenderGlobal.field_175005_X:Z
        //  1219: ifeq            1270
        //  1222: aload_0        
        //  1223: getfield        net/minecraft/client/renderer/RenderGlobal.field_175011_u:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1226: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177359_a:()V
        //  1229: ldc_w           32884
        //  1232: invokestatic    org/lwjgl/opengl/GL11.glEnableClientState:(I)V
        //  1235: iconst_3       
        //  1236: sipush          5126
        //  1239: bipush          12
        //  1241: lconst_0       
        //  1242: invokestatic    org/lwjgl/opengl/GL11.glVertexPointer:(IIIJ)V
        //  1245: aload_0        
        //  1246: getfield        net/minecraft/client/renderer/RenderGlobal.field_175011_u:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1249: bipush          7
        //  1251: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177358_a:(I)V
        //  1254: aload_0        
        //  1255: getfield        net/minecraft/client/renderer/RenderGlobal.field_175011_u:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1258: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.func_177361_b:()V
        //  1261: ldc_w           32884
        //  1264: invokestatic    org/lwjgl/opengl/GL11.glDisableClientState:(I)V
        //  1267: goto            1277
        //  1270: aload_0        
        //  1271: getfield        net/minecraft/client/renderer/RenderGlobal.glSkyList2:I
        //  1274: invokestatic    net/minecraft/client/renderer/GlStateManager.callList:(I)V
        //  1277: fconst_1       
        //  1278: fstore          13
        //  1280: dload           20
        //  1282: ldc2_w          65.0
        //  1285: dadd           
        //  1286: d2f            
        //  1287: fneg           
        //  1288: fstore          14
        //  1290: ldc_w           -1.0
        //  1293: fstore          15
        //  1295: aload           9
        //  1297: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawingQuads:()V
        //  1300: aload           9
        //  1302: iconst_0       
        //  1303: sipush          255
        //  1306: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178974_a:(II)V
        //  1309: aload           9
        //  1311: ldc2_w          -1.0
        //  1314: fload           14
        //  1316: f2d            
        //  1317: dconst_1       
        //  1318: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1321: aload           9
        //  1323: dconst_1       
        //  1324: fload           14
        //  1326: f2d            
        //  1327: dconst_1       
        //  1328: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1331: aload           9
        //  1333: dconst_1       
        //  1334: ldc2_w          -1.0
        //  1337: dconst_1       
        //  1338: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1341: aload           9
        //  1343: ldc2_w          -1.0
        //  1346: ldc2_w          -1.0
        //  1349: dconst_1       
        //  1350: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1353: aload           9
        //  1355: ldc2_w          -1.0
        //  1358: ldc2_w          -1.0
        //  1361: ldc2_w          -1.0
        //  1364: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1367: aload           9
        //  1369: dconst_1       
        //  1370: ldc2_w          -1.0
        //  1373: ldc2_w          -1.0
        //  1376: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1379: aload           9
        //  1381: dconst_1       
        //  1382: fload           14
        //  1384: f2d            
        //  1385: ldc2_w          -1.0
        //  1388: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1391: aload           9
        //  1393: ldc2_w          -1.0
        //  1396: fload           14
        //  1398: f2d            
        //  1399: ldc2_w          -1.0
        //  1402: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1405: aload           9
        //  1407: dconst_1       
        //  1408: ldc2_w          -1.0
        //  1411: ldc2_w          -1.0
        //  1414: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1417: aload           9
        //  1419: dconst_1       
        //  1420: ldc2_w          -1.0
        //  1423: dconst_1       
        //  1424: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1427: aload           9
        //  1429: dconst_1       
        //  1430: fload           14
        //  1432: f2d            
        //  1433: dconst_1       
        //  1434: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1437: aload           9
        //  1439: dconst_1       
        //  1440: fload           14
        //  1442: f2d            
        //  1443: ldc2_w          -1.0
        //  1446: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1449: aload           9
        //  1451: ldc2_w          -1.0
        //  1454: fload           14
        //  1456: f2d            
        //  1457: ldc2_w          -1.0
        //  1460: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1463: aload           9
        //  1465: ldc2_w          -1.0
        //  1468: fload           14
        //  1470: f2d            
        //  1471: dconst_1       
        //  1472: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1475: aload           9
        //  1477: ldc2_w          -1.0
        //  1480: ldc2_w          -1.0
        //  1483: dconst_1       
        //  1484: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1487: aload           9
        //  1489: ldc2_w          -1.0
        //  1492: ldc2_w          -1.0
        //  1495: ldc2_w          -1.0
        //  1498: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1501: aload           9
        //  1503: ldc2_w          -1.0
        //  1506: ldc2_w          -1.0
        //  1509: ldc2_w          -1.0
        //  1512: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1515: aload           9
        //  1517: ldc2_w          -1.0
        //  1520: ldc2_w          -1.0
        //  1523: dconst_1       
        //  1524: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1527: aload           9
        //  1529: dconst_1       
        //  1530: ldc2_w          -1.0
        //  1533: dconst_1       
        //  1534: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1537: aload           9
        //  1539: dconst_1       
        //  1540: ldc2_w          -1.0
        //  1543: ldc2_w          -1.0
        //  1546: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //  1549: aload           8
        //  1551: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //  1554: pop            
        //  1555: aload_0        
        //  1556: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1559: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //  1562: invokevirtual   net/minecraft/world/WorldProvider.isSkyColored:()Z
        //  1565: ifeq            1604
        //  1568: fload           5
        //  1570: ldc_w           0.2
        //  1573: fmul           
        //  1574: ldc_w           0.04
        //  1577: fadd           
        //  1578: fload           6
        //  1580: ldc_w           0.2
        //  1583: fmul           
        //  1584: ldc_w           0.04
        //  1587: fadd           
        //  1588: fload           7
        //  1590: ldc_w           0.6
        //  1593: fmul           
        //  1594: ldc_w           0.1
        //  1597: fadd           
        //  1598: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //  1601: goto            1613
        //  1604: fload           5
        //  1606: fload           6
        //  1608: fload           7
        //  1610: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //  1613: aload_0        
        //  1614: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1617: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1620: pop            
        //  1621: getstatic       net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //  1624: iconst_4       
        //  1625: if_icmpgt       1661
        //  1628: aload_0        
        //  1629: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1632: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1635: getfield        net/minecraft/client/renderer/EntityRenderer.field_175080_Q:F
        //  1638: aload_0        
        //  1639: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1642: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1645: getfield        net/minecraft/client/renderer/EntityRenderer.field_175082_R:F
        //  1648: aload_0        
        //  1649: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1652: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1655: getfield        net/minecraft/client/renderer/EntityRenderer.field_175081_S:F
        //  1658: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //  1661: fconst_0       
        //  1662: dload           20
        //  1664: ldc2_w          16.0
        //  1667: dsub           
        //  1668: d2f            
        //  1669: fneg           
        //  1670: fconst_0       
        //  1671: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //  1674: invokestatic    optifine/Config.isSkyEnabled:()Z
        //  1677: ifeq            1687
        //  1680: aload_0        
        //  1681: getfield        net/minecraft/client/renderer/RenderGlobal.glSkyList2:I
        //  1684: invokestatic    net/minecraft/client/renderer/GlStateManager.callList:(I)V
        //  1687: iload_3        
        //  1688: iconst_1       
        //  1689: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //  1692: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #1692 (coming from #1689).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void func_180447_b(final float p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifne            757
        //     6: getstatic       optifine/Reflector.ForgeWorldProvider_getCloudRenderer:Loptifine/ReflectorMethod;
        //     9: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //    12: ifeq            74
        //    15: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    18: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //    21: astore_3       
        //    22: aload_3        
        //    23: getstatic       optifine/Reflector.ForgeWorldProvider_getCloudRenderer:Loptifine/ReflectorMethod;
        //    26: iconst_0       
        //    27: anewarray       Ljava/lang/Object;
        //    30: invokestatic    optifine/Reflector.call:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //    33: astore          4
        //    35: aload           4
        //    37: ifnull          74
        //    40: aload           4
        //    42: getstatic       optifine/Reflector.IRenderHandler_render:Loptifine/ReflectorMethod;
        //    45: iconst_3       
        //    46: anewarray       Ljava/lang/Object;
        //    49: dup            
        //    50: iconst_0       
        //    51: fload_1        
        //    52: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    55: aastore        
        //    56: dup            
        //    57: iconst_1       
        //    58: aload_0        
        //    59: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    62: aastore        
        //    63: dup            
        //    64: iconst_2       
        //    65: aload_0        
        //    66: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    69: aastore        
        //    70: invokestatic    optifine/Reflector.callVoid:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //    73: return         
        //    74: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    77: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //    80: invokevirtual   net/minecraft/world/WorldProvider.isSurfaceWorld:()Z
        //    83: ifeq            757
        //    86: invokestatic    optifine/Config.isShaders:()Z
        //    89: invokestatic    optifine/Config.isCloudsFancy:()Z
        //    92: ifeq            104
        //    95: aload_0        
        //    96: fload_1        
        //    97: iload_2        
        //    98: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_180445_c:(FI)V
        //   101: goto            754
        //   104: aload_0        
        //   105: getfield        net/minecraft/client/renderer/RenderGlobal.cloudRenderer:Loptifine/CloudRenderer;
        //   108: iconst_0       
        //   109: aload_0        
        //   110: getfield        net/minecraft/client/renderer/RenderGlobal.cloudTickCounter:I
        //   113: fload_1        
        //   114: invokevirtual   optifine/CloudRenderer.prepareToRender:(ZIF)V
        //   117: fconst_0       
        //   118: fstore_1       
        //   119: aload_0        
        //   120: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   123: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   126: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   129: aload_0        
        //   130: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   133: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   136: getfield        net/minecraft/entity/Entity.posY:D
        //   139: aload_0        
        //   140: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   143: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   146: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   149: dsub           
        //   150: fload_1        
        //   151: f2d            
        //   152: dmul           
        //   153: dadd           
        //   154: d2f            
        //   155: fstore_3       
        //   156: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //   159: astore          6
        //   161: aload           6
        //   163: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //   166: astore          7
        //   168: aload_0        
        //   169: getfield        net/minecraft/client/renderer/RenderGlobal.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   172: getstatic       net/minecraft/client/renderer/RenderGlobal.locationCloudsPng:Lnet/minecraft/util/ResourceLocation;
        //   175: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   178: sipush          770
        //   181: sipush          771
        //   184: iconst_1       
        //   185: iconst_0       
        //   186: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //   189: aload_0        
        //   190: getfield        net/minecraft/client/renderer/RenderGlobal.cloudRenderer:Loptifine/CloudRenderer;
        //   193: invokevirtual   optifine/CloudRenderer.shouldUpdateGlList:()Z
        //   196: ifeq            740
        //   199: aload_0        
        //   200: getfield        net/minecraft/client/renderer/RenderGlobal.cloudRenderer:Loptifine/CloudRenderer;
        //   203: invokevirtual   optifine/CloudRenderer.startUpdateGlList:()V
        //   206: aload_0        
        //   207: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   210: fload_1        
        //   211: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCloudColour:(F)Lnet/minecraft/util/Vec3;
        //   214: astore          8
        //   216: aload           8
        //   218: getfield        net/minecraft/util/Vec3.xCoord:D
        //   221: d2f            
        //   222: fstore          9
        //   224: aload           8
        //   226: getfield        net/minecraft/util/Vec3.yCoord:D
        //   229: d2f            
        //   230: fstore          10
        //   232: aload           8
        //   234: getfield        net/minecraft/util/Vec3.zCoord:D
        //   237: d2f            
        //   238: fstore          11
        //   240: iload_2        
        //   241: iconst_2       
        //   242: if_icmpeq       321
        //   245: fload           9
        //   247: ldc_w           30.0
        //   250: fmul           
        //   251: fload           10
        //   253: ldc_w           59.0
        //   256: fmul           
        //   257: fadd           
        //   258: fload           11
        //   260: ldc_w           11.0
        //   263: fmul           
        //   264: fadd           
        //   265: ldc_w           100.0
        //   268: fdiv           
        //   269: fstore          12
        //   271: fload           9
        //   273: ldc_w           30.0
        //   276: fmul           
        //   277: fload           10
        //   279: ldc_w           70.0
        //   282: fmul           
        //   283: fadd           
        //   284: ldc_w           100.0
        //   287: fdiv           
        //   288: fstore          13
        //   290: fload           9
        //   292: ldc_w           30.0
        //   295: fmul           
        //   296: fload           11
        //   298: ldc_w           70.0
        //   301: fmul           
        //   302: fadd           
        //   303: ldc_w           100.0
        //   306: fdiv           
        //   307: fstore          14
        //   309: fload           12
        //   311: fstore          9
        //   313: fload           13
        //   315: fstore          10
        //   317: fload           14
        //   319: fstore          11
        //   321: ldc_w           4.8828125E-4
        //   324: fstore          12
        //   326: aload_0        
        //   327: getfield        net/minecraft/client/renderer/RenderGlobal.cloudTickCounter:I
        //   330: i2f            
        //   331: fload_1        
        //   332: fadd           
        //   333: f2d            
        //   334: dstore          13
        //   336: aload_0        
        //   337: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   340: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   343: getfield        net/minecraft/entity/Entity.prevPosX:D
        //   346: aload_0        
        //   347: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   350: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   353: getfield        net/minecraft/entity/Entity.posX:D
        //   356: aload_0        
        //   357: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   360: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   363: getfield        net/minecraft/entity/Entity.prevPosX:D
        //   366: dsub           
        //   367: fload_1        
        //   368: f2d            
        //   369: dmul           
        //   370: dadd           
        //   371: dload           13
        //   373: ldc2_w          0.029999999329447746
        //   376: dmul           
        //   377: dadd           
        //   378: dstore          15
        //   380: aload_0        
        //   381: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   384: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   387: getfield        net/minecraft/entity/Entity.prevPosZ:D
        //   390: aload_0        
        //   391: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   394: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   397: getfield        net/minecraft/entity/Entity.posZ:D
        //   400: aload_0        
        //   401: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   404: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   407: getfield        net/minecraft/entity/Entity.prevPosZ:D
        //   410: dsub           
        //   411: fload_1        
        //   412: f2d            
        //   413: dmul           
        //   414: dadd           
        //   415: dstore          17
        //   417: dload           15
        //   419: ldc2_w          2048.0
        //   422: ddiv           
        //   423: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   426: istore          19
        //   428: dload           17
        //   430: ldc2_w          2048.0
        //   433: ddiv           
        //   434: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   437: istore          20
        //   439: dload           15
        //   441: iload           19
        //   443: sipush          2048
        //   446: imul           
        //   447: i2d            
        //   448: dsub           
        //   449: dstore          15
        //   451: dload           17
        //   453: iload           20
        //   455: sipush          2048
        //   458: imul           
        //   459: i2d            
        //   460: dsub           
        //   461: dstore          17
        //   463: aload_0        
        //   464: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   467: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //   470: invokevirtual   net/minecraft/world/WorldProvider.getCloudHeight:()F
        //   473: fload_3        
        //   474: fsub           
        //   475: ldc_w           0.33
        //   478: fadd           
        //   479: fstore          21
        //   481: fload           21
        //   483: aload_0        
        //   484: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   487: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   490: getfield        net/minecraft/client/settings/GameSettings.ofCloudsHeight:F
        //   493: ldc_w           128.0
        //   496: fmul           
        //   497: fadd           
        //   498: fstore          21
        //   500: dload           15
        //   502: ldc2_w          4.8828125E-4
        //   505: dmul           
        //   506: d2f            
        //   507: fstore          22
        //   509: dload           17
        //   511: ldc2_w          4.8828125E-4
        //   514: dmul           
        //   515: d2f            
        //   516: fstore          23
        //   518: aload           7
        //   520: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawingQuads:()V
        //   523: aload           7
        //   525: fload           9
        //   527: fload           10
        //   529: fload           11
        //   531: ldc_w           0.8
        //   534: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178960_a:(FFFF)V
        //   537: goto            718
        //   540: goto            706
        //   543: aload           7
        //   545: sipush          -256
        //   548: i2d            
        //   549: fload           21
        //   551: f2d            
        //   552: sipush          -224
        //   555: i2d            
        //   556: sipush          -256
        //   559: i2f            
        //   560: ldc_w           4.8828125E-4
        //   563: fmul           
        //   564: fload           22
        //   566: fadd           
        //   567: f2d            
        //   568: sipush          -224
        //   571: i2f            
        //   572: ldc_w           4.8828125E-4
        //   575: fmul           
        //   576: fload           23
        //   578: fadd           
        //   579: f2d            
        //   580: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   583: aload           7
        //   585: sipush          -224
        //   588: i2d            
        //   589: fload           21
        //   591: f2d            
        //   592: sipush          -224
        //   595: i2d            
        //   596: sipush          -224
        //   599: i2f            
        //   600: ldc_w           4.8828125E-4
        //   603: fmul           
        //   604: fload           22
        //   606: fadd           
        //   607: f2d            
        //   608: sipush          -224
        //   611: i2f            
        //   612: ldc_w           4.8828125E-4
        //   615: fmul           
        //   616: fload           23
        //   618: fadd           
        //   619: f2d            
        //   620: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   623: aload           7
        //   625: sipush          -224
        //   628: i2d            
        //   629: fload           21
        //   631: f2d            
        //   632: sipush          -256
        //   635: i2d            
        //   636: sipush          -224
        //   639: i2f            
        //   640: ldc_w           4.8828125E-4
        //   643: fmul           
        //   644: fload           22
        //   646: fadd           
        //   647: f2d            
        //   648: sipush          -256
        //   651: i2f            
        //   652: ldc_w           4.8828125E-4
        //   655: fmul           
        //   656: fload           23
        //   658: fadd           
        //   659: f2d            
        //   660: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   663: aload           7
        //   665: sipush          -256
        //   668: i2d            
        //   669: fload           21
        //   671: f2d            
        //   672: sipush          -256
        //   675: i2d            
        //   676: sipush          -256
        //   679: i2f            
        //   680: ldc_w           4.8828125E-4
        //   683: fmul           
        //   684: fload           22
        //   686: fadd           
        //   687: f2d            
        //   688: sipush          -256
        //   691: i2f            
        //   692: ldc_w           4.8828125E-4
        //   695: fmul           
        //   696: fload           23
        //   698: fadd           
        //   699: f2d            
        //   700: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   703: iinc            25, 32
        //   706: sipush          -256
        //   709: sipush          256
        //   712: if_icmplt       543
        //   715: iinc            24, 32
        //   718: sipush          -256
        //   721: sipush          256
        //   724: if_icmplt       540
        //   727: aload           6
        //   729: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //   732: pop            
        //   733: aload_0        
        //   734: getfield        net/minecraft/client/renderer/RenderGlobal.cloudRenderer:Loptifine/CloudRenderer;
        //   737: invokevirtual   optifine/CloudRenderer.endUpdateGlList:()V
        //   740: aload_0        
        //   741: getfield        net/minecraft/client/renderer/RenderGlobal.cloudRenderer:Loptifine/CloudRenderer;
        //   744: invokevirtual   optifine/CloudRenderer.renderGlList:()V
        //   747: fconst_1       
        //   748: fconst_1       
        //   749: fconst_1       
        //   750: fconst_1       
        //   751: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //   754: invokestatic    optifine/Config.isShaders:()Z
        //   757: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0757 (coming from #0754).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean hasCloudFog(final double n, final double n2, final double n3, final float n4) {
        return false;
    }
    
    private void func_180445_c(float n, final int n2) {
        this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, n);
        n = 0.0f;
        final float n3 = (float)(this.mc.func_175606_aa().lastTickPosY + (this.mc.func_175606_aa().posY - this.mc.func_175606_aa().lastTickPosY) * n);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final double n4 = (this.mc.func_175606_aa().prevPosX + (this.mc.func_175606_aa().posX - this.mc.func_175606_aa().prevPosX) * n + (this.cloudTickCounter + n) * 0.029999999329447746) / 12.0;
        final double n5 = (this.mc.func_175606_aa().prevPosZ + (this.mc.func_175606_aa().posZ - this.mc.func_175606_aa().prevPosZ) * n) / 12.0 + 0.33000001311302185;
        final float n6 = this.theWorld.provider.getCloudHeight() - n3 + 0.33f + this.mc.gameSettings.ofCloudsHeight * 128.0f;
        final int floor_double = MathHelper.floor_double(n4 / 2048.0);
        final int floor_double2 = MathHelper.floor_double(n5 / 2048.0);
        final double n7 = n4 - floor_double * 2048;
        final double n8 = n5 - floor_double2 * 2048;
        this.renderEngine.bindTexture(RenderGlobal.locationCloudsPng);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final Vec3 cloudColour = this.theWorld.getCloudColour(n);
        float n9 = (float)cloudColour.xCoord;
        float n10 = (float)cloudColour.yCoord;
        float n11 = (float)cloudColour.zCoord;
        if (n2 != 2) {
            final float n12 = (n9 * 30.0f + n10 * 59.0f + n11 * 11.0f) / 100.0f;
            final float n13 = (n9 * 30.0f + n10 * 70.0f) / 100.0f;
            final float n14 = (n9 * 30.0f + n11 * 70.0f) / 100.0f;
            n9 = n12;
            n10 = n13;
            n11 = n14;
        }
        final float n15 = MathHelper.floor_double(n7) * 0.00390625f;
        final float n16 = MathHelper.floor_double(n8) * 0.00390625f;
        final float n17 = (float)(n7 - MathHelper.floor_double(n7));
        final float n18 = (float)(n8 - MathHelper.floor_double(n8));
        GlStateManager.scale(12.0f, 1.0f, 12.0f);
        int n19 = 0;
        while (-3 < 2) {
            if (-3 == 0) {
                GlStateManager.colorMask(false, false, false, false);
            }
            else {
                switch (n2) {
                    case 0: {
                        GlStateManager.colorMask(false, true, true, true);
                        break;
                    }
                    case 1: {
                        GlStateManager.colorMask(true, false, false, true);
                        break;
                    }
                    case 2: {
                        GlStateManager.colorMask(true, true, true, true);
                        break;
                    }
                }
            }
            this.cloudRenderer.renderGlList();
            ++n19;
        }
        if (this.cloudRenderer.shouldUpdateGlList()) {
            this.cloudRenderer.startUpdateGlList();
            while (-3 <= 4) {
                while (-3 <= 4) {
                    worldRenderer.startDrawingQuads();
                    final float n20 = -24;
                    final float n21 = -24;
                    final float n22 = n20 - n17;
                    final float n23 = n21 - n18;
                    if (n6 > -5.0f) {
                        worldRenderer.func_178960_a(n9 * 0.7f, n10 * 0.7f, n11 * 0.7f, 0.8f);
                        worldRenderer.func_178980_d(0.0f, -1.0f, 0.0f);
                        worldRenderer.addVertexWithUV(n22 + 0.0f, n6 + 0.0f, n23 + 8.0f, (n20 + 0.0f) * 0.00390625f + n15, (n21 + 8.0f) * 0.00390625f + n16);
                        worldRenderer.addVertexWithUV(n22 + 8.0f, n6 + 0.0f, n23 + 8.0f, (n20 + 8.0f) * 0.00390625f + n15, (n21 + 8.0f) * 0.00390625f + n16);
                        worldRenderer.addVertexWithUV(n22 + 8.0f, n6 + 0.0f, n23 + 0.0f, (n20 + 8.0f) * 0.00390625f + n15, (n21 + 0.0f) * 0.00390625f + n16);
                        worldRenderer.addVertexWithUV(n22 + 0.0f, n6 + 0.0f, n23 + 0.0f, (n20 + 0.0f) * 0.00390625f + n15, (n21 + 0.0f) * 0.00390625f + n16);
                    }
                    if (n6 <= 5.0f) {
                        worldRenderer.func_178960_a(n9, n10, n11, 0.8f);
                        worldRenderer.func_178980_d(0.0f, 1.0f, 0.0f);
                        worldRenderer.addVertexWithUV(n22 + 0.0f, n6 + 4.0f - 9.765625E-4f, n23 + 8.0f, (n20 + 0.0f) * 0.00390625f + n15, (n21 + 8.0f) * 0.00390625f + n16);
                        worldRenderer.addVertexWithUV(n22 + 8.0f, n6 + 4.0f - 9.765625E-4f, n23 + 8.0f, (n20 + 8.0f) * 0.00390625f + n15, (n21 + 8.0f) * 0.00390625f + n16);
                        worldRenderer.addVertexWithUV(n22 + 8.0f, n6 + 4.0f - 9.765625E-4f, n23 + 0.0f, (n20 + 8.0f) * 0.00390625f + n15, (n21 + 0.0f) * 0.00390625f + n16);
                        worldRenderer.addVertexWithUV(n22 + 0.0f, n6 + 4.0f - 9.765625E-4f, n23 + 0.0f, (n20 + 0.0f) * 0.00390625f + n15, (n21 + 0.0f) * 0.00390625f + n16);
                    }
                    worldRenderer.func_178960_a(n9 * 0.9f, n10 * 0.9f, n11 * 0.9f, 0.8f);
                    int n24 = 0;
                    if (-3 > -1) {
                        worldRenderer.func_178980_d(-1.0f, 0.0f, 0.0f);
                        while (0 < 8) {
                            worldRenderer.addVertexWithUV(n22 + 0 + 0.0f, n6 + 0.0f, n23 + 8.0f, (n20 + 0 + 0.5f) * 0.00390625f + n15, (n21 + 8.0f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 0 + 0.0f, n6 + 4.0f, n23 + 8.0f, (n20 + 0 + 0.5f) * 0.00390625f + n15, (n21 + 8.0f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 0 + 0.0f, n6 + 4.0f, n23 + 0.0f, (n20 + 0 + 0.5f) * 0.00390625f + n15, (n21 + 0.0f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 0 + 0.0f, n6 + 0.0f, n23 + 0.0f, (n20 + 0 + 0.5f) * 0.00390625f + n15, (n21 + 0.0f) * 0.00390625f + n16);
                            ++n24;
                        }
                    }
                    if (-3 <= 1) {
                        worldRenderer.func_178980_d(1.0f, 0.0f, 0.0f);
                        while (0 < 8) {
                            worldRenderer.addVertexWithUV(n22 + 0 + 1.0f - 9.765625E-4f, n6 + 0.0f, n23 + 8.0f, (n20 + 0 + 0.5f) * 0.00390625f + n15, (n21 + 8.0f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 0 + 1.0f - 9.765625E-4f, n6 + 4.0f, n23 + 8.0f, (n20 + 0 + 0.5f) * 0.00390625f + n15, (n21 + 8.0f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 0 + 1.0f - 9.765625E-4f, n6 + 4.0f, n23 + 0.0f, (n20 + 0 + 0.5f) * 0.00390625f + n15, (n21 + 0.0f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 0 + 1.0f - 9.765625E-4f, n6 + 0.0f, n23 + 0.0f, (n20 + 0 + 0.5f) * 0.00390625f + n15, (n21 + 0.0f) * 0.00390625f + n16);
                            ++n24;
                        }
                    }
                    worldRenderer.func_178960_a(n9 * 0.8f, n10 * 0.8f, n11 * 0.8f, 0.8f);
                    if (-3 > -1) {
                        worldRenderer.func_178980_d(0.0f, 0.0f, -1.0f);
                        while (0 < 8) {
                            worldRenderer.addVertexWithUV(n22 + 0.0f, n6 + 4.0f, n23 + 0 + 0.0f, (n20 + 0.0f) * 0.00390625f + n15, (n21 + 0 + 0.5f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 8.0f, n6 + 4.0f, n23 + 0 + 0.0f, (n20 + 8.0f) * 0.00390625f + n15, (n21 + 0 + 0.5f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 8.0f, n6 + 0.0f, n23 + 0 + 0.0f, (n20 + 8.0f) * 0.00390625f + n15, (n21 + 0 + 0.5f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 0.0f, n6 + 0.0f, n23 + 0 + 0.0f, (n20 + 0.0f) * 0.00390625f + n15, (n21 + 0 + 0.5f) * 0.00390625f + n16);
                            ++n24;
                        }
                    }
                    if (-3 <= 1) {
                        worldRenderer.func_178980_d(0.0f, 0.0f, 1.0f);
                        while (0 < 8) {
                            worldRenderer.addVertexWithUV(n22 + 0.0f, n6 + 4.0f, n23 + 0 + 1.0f - 9.765625E-4f, (n20 + 0.0f) * 0.00390625f + n15, (n21 + 0 + 0.5f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 8.0f, n6 + 4.0f, n23 + 0 + 1.0f - 9.765625E-4f, (n20 + 8.0f) * 0.00390625f + n15, (n21 + 0 + 0.5f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 8.0f, n6 + 0.0f, n23 + 0 + 1.0f - 9.765625E-4f, (n20 + 8.0f) * 0.00390625f + n15, (n21 + 0 + 0.5f) * 0.00390625f + n16);
                            worldRenderer.addVertexWithUV(n22 + 0.0f, n6 + 0.0f, n23 + 0 + 1.0f - 9.765625E-4f, (n20 + 0.0f) * 0.00390625f + n15, (n21 + 0 + 0.5f) * 0.00390625f + n16);
                            ++n24;
                        }
                    }
                    instance.draw();
                    int n25 = 0;
                    ++n25;
                }
                ++n19;
            }
            this.cloudRenderer.endUpdateGlList();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void func_174967_a(final long n) {
        this.displayListEntitiesDirty |= this.field_174995_M.func_178516_a(n);
        if (this.chunksToUpdateForced.size() > 0) {
            final Iterator<RenderChunk> iterator = (Iterator<RenderChunk>)this.chunksToUpdateForced.iterator();
            while (iterator.hasNext()) {
                final RenderChunk renderChunk = iterator.next();
                if (!this.field_174995_M.func_178507_a(renderChunk)) {
                    break;
                }
                renderChunk.func_178575_a(false);
                iterator.remove();
                this.field_175009_l.remove(renderChunk);
                this.chunksToResortTransparency.remove(renderChunk);
            }
        }
        if (this.chunksToResortTransparency.size() > 0) {
            final Iterator<RenderChunk> iterator2 = (Iterator<RenderChunk>)this.chunksToResortTransparency.iterator();
            if (iterator2.hasNext() && this.field_174995_M.func_178509_c(iterator2.next())) {
                iterator2.remove();
            }
        }
        int updatesPerFrame = Config.getUpdatesPerFrame();
        final int n2 = updatesPerFrame * 2;
        final Iterator<RenderChunk> iterator3 = (Iterator<RenderChunk>)this.field_175009_l.iterator();
        while (iterator3.hasNext()) {
            final RenderChunk renderChunk2 = iterator3.next();
            if (!this.field_174995_M.func_178507_a(renderChunk2)) {
                break;
            }
            renderChunk2.func_178575_a(false);
            iterator3.remove();
            if (renderChunk2.func_178571_g().func_178489_a() && updatesPerFrame < n2) {
                ++updatesPerFrame;
            }
            int n3 = 0;
            ++n3;
            if (0 >= updatesPerFrame) {
                break;
            }
        }
    }
    
    public void func_180449_a(final Entity entity, final float n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final WorldBorder worldBorder = this.theWorld.getWorldBorder();
        final GameSettings gameSettings = this.mc.gameSettings;
        final double n2 = GameSettings.renderDistanceChunks * 16;
        if (entity.posX >= worldBorder.maxX() - n2 || entity.posX <= worldBorder.minX() + n2 || entity.posZ >= worldBorder.maxZ() - n2 || entity.posZ <= worldBorder.minZ() + n2) {
            final double pow = Math.pow(1.0 - worldBorder.getClosestDistance(entity) / n2, 4.0);
            final double n3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
            final double n4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
            final double n5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            this.renderEngine.bindTexture(RenderGlobal.field_175006_g);
            GlStateManager.depthMask(false);
            final int func_177766_a = worldBorder.getStatus().func_177766_a();
            GlStateManager.color((func_177766_a >> 16 & 0xFF) / 255.0f, (func_177766_a >> 8 & 0xFF) / 255.0f, (func_177766_a & 0xFF) / 255.0f, (float)pow);
            GlStateManager.doPolygonOffset(-3.0f, -3.0f);
            GlStateManager.alphaFunc(516, 0.1f);
            final float n6 = Minecraft.getSystemTime() % 3000L / 3000.0f;
            worldRenderer.startDrawingQuads();
            worldRenderer.setTranslation(-n3, -n4, -n5);
            worldRenderer.markDirty();
            final double max = Math.max(MathHelper.floor_double(n5 - n2), worldBorder.minZ());
            final double min = Math.min(MathHelper.ceiling_double_int(n5 + n2), worldBorder.maxZ());
            if (n3 > worldBorder.maxX() - n2) {
                float n7 = 0.0f;
                for (double n8 = max; n8 < min; ++n8, n7 += 0.5f) {
                    final double min2 = Math.min(1.0, min - n8);
                    final float n9 = (float)min2 * 0.5f;
                    worldRenderer.addVertexWithUV(worldBorder.maxX(), 256.0, n8, n6 + n7, n6 + 0.0f);
                    worldRenderer.addVertexWithUV(worldBorder.maxX(), 256.0, n8 + min2, n6 + n9 + n7, n6 + 0.0f);
                    worldRenderer.addVertexWithUV(worldBorder.maxX(), 0.0, n8 + min2, n6 + n9 + n7, n6 + 128.0f);
                    worldRenderer.addVertexWithUV(worldBorder.maxX(), 0.0, n8, n6 + n7, n6 + 128.0f);
                }
            }
            if (n3 < worldBorder.minX() + n2) {
                float n10 = 0.0f;
                for (double n11 = max; n11 < min; ++n11, n10 += 0.5f) {
                    final double min3 = Math.min(1.0, min - n11);
                    final float n12 = (float)min3 * 0.5f;
                    worldRenderer.addVertexWithUV(worldBorder.minX(), 256.0, n11, n6 + n10, n6 + 0.0f);
                    worldRenderer.addVertexWithUV(worldBorder.minX(), 256.0, n11 + min3, n6 + n12 + n10, n6 + 0.0f);
                    worldRenderer.addVertexWithUV(worldBorder.minX(), 0.0, n11 + min3, n6 + n12 + n10, n6 + 128.0f);
                    worldRenderer.addVertexWithUV(worldBorder.minX(), 0.0, n11, n6 + n10, n6 + 128.0f);
                }
            }
            final double max2 = Math.max(MathHelper.floor_double(n3 - n2), worldBorder.minX());
            final double min4 = Math.min(MathHelper.ceiling_double_int(n3 + n2), worldBorder.maxX());
            if (n5 > worldBorder.maxZ() - n2) {
                float n13 = 0.0f;
                for (double n14 = max2; n14 < min4; ++n14, n13 += 0.5f) {
                    final double min5 = Math.min(1.0, min4 - n14);
                    final float n15 = (float)min5 * 0.5f;
                    worldRenderer.addVertexWithUV(n14, 256.0, worldBorder.maxZ(), n6 + n13, n6 + 0.0f);
                    worldRenderer.addVertexWithUV(n14 + min5, 256.0, worldBorder.maxZ(), n6 + n15 + n13, n6 + 0.0f);
                    worldRenderer.addVertexWithUV(n14 + min5, 0.0, worldBorder.maxZ(), n6 + n15 + n13, n6 + 128.0f);
                    worldRenderer.addVertexWithUV(n14, 0.0, worldBorder.maxZ(), n6 + n13, n6 + 128.0f);
                }
            }
            if (n5 < worldBorder.minZ() + n2) {
                float n16 = 0.0f;
                for (double n17 = max2; n17 < min4; ++n17, n16 += 0.5f) {
                    final double min6 = Math.min(1.0, min4 - n17);
                    final float n18 = (float)min6 * 0.5f;
                    worldRenderer.addVertexWithUV(n17, 256.0, worldBorder.minZ(), n6 + n16, n6 + 0.0f);
                    worldRenderer.addVertexWithUV(n17 + min6, 256.0, worldBorder.minZ(), n6 + n18 + n16, n6 + 0.0f);
                    worldRenderer.addVertexWithUV(n17 + min6, 0.0, worldBorder.minZ(), n6 + n18 + n16, n6 + 128.0f);
                    worldRenderer.addVertexWithUV(n17, 0.0, worldBorder.minZ(), n6 + n16, n6 + 128.0f);
                }
            }
            instance.draw();
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            GlStateManager.doPolygonOffset(0.0f, 0.0f);
            GlStateManager.depthMask(true);
        }
    }
    
    private void func_180443_s() {
        GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.5f);
        GlStateManager.doPolygonOffset(-3.0f, -3.0f);
        GlStateManager.alphaFunc(516, 0.1f);
        Config.isShaders();
    }
    
    private void func_174969_t() {
        GlStateManager.doPolygonOffset(0.0f, 0.0f);
        GlStateManager.depthMask(true);
        Config.isShaders();
    }
    
    public void func_174981_a(final Tessellator tessellator, final WorldRenderer worldRenderer, final Entity entity, final float n) {
        final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        if (!this.damagedBlocks.isEmpty()) {
            this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            this.func_180443_s();
            worldRenderer.startDrawingQuads();
            worldRenderer.setVertexFormat(DefaultVertexFormats.field_176600_a);
            worldRenderer.setTranslation(-n2, -n3, -n4);
            worldRenderer.markDirty();
            final Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();
            while (iterator.hasNext()) {
                final DestroyBlockProgress destroyBlockProgress = iterator.next();
                final BlockPos func_180246_b = destroyBlockProgress.func_180246_b();
                final double n5 = func_180246_b.getX() - n2;
                final double n6 = func_180246_b.getY() - n3;
                final double n7 = func_180246_b.getZ() - n4;
                final Block block = this.theWorld.getBlockState(func_180246_b).getBlock();
                boolean b;
                if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
                    boolean callBoolean = block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull;
                    if (!callBoolean) {
                        final TileEntity tileEntity = this.theWorld.getTileEntity(func_180246_b);
                        if (tileEntity != null) {
                            callBoolean = Reflector.callBoolean(tileEntity, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]);
                        }
                    }
                    b = !callBoolean;
                }
                else {
                    b = (!(block instanceof BlockChest) && !(block instanceof BlockEnderChest) && !(block instanceof BlockSign) && !(block instanceof BlockSkull));
                }
                if (b) {
                    if (n5 * n5 + n6 * n6 + n7 * n7 > 1024.0) {
                        iterator.remove();
                    }
                    else {
                        final IBlockState blockState = this.theWorld.getBlockState(func_180246_b);
                        if (blockState.getBlock().getMaterial() == Material.air) {
                            continue;
                        }
                        this.mc.getBlockRendererDispatcher().func_175020_a(blockState, func_180246_b, this.destroyBlockIcons[destroyBlockProgress.getPartialBlockDamage()], this.theWorld);
                    }
                }
            }
            tessellator.draw();
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            this.func_174969_t();
        }
    }
    
    public void drawSelectionBox(final EntityPlayer p0, final MovingObjectPosition p1, final int p2, final float p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifne            215
        //     4: aload_2        
        //     5: getfield        net/minecraft/util/MovingObjectPosition.typeOfHit:Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;
        //     8: getstatic       net/minecraft/util/MovingObjectPosition$MovingObjectType.BLOCK:Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;
        //    11: if_acmpne       215
        //    14: sipush          770
        //    17: sipush          771
        //    20: iconst_1       
        //    21: iconst_0       
        //    22: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //    25: fconst_0       
        //    26: fconst_0       
        //    27: fconst_0       
        //    28: ldc_w           0.4
        //    31: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //    34: fconst_2       
        //    35: invokestatic    org/lwjgl/opengl/GL11.glLineWidth:(F)V
        //    38: invokestatic    optifine/Config.isShaders:()Z
        //    41: iconst_0       
        //    42: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //    45: ldc_w           0.002
        //    48: fstore          5
        //    50: aload_2        
        //    51: invokevirtual   net/minecraft/util/MovingObjectPosition.func_178782_a:()Lnet/minecraft/util/BlockPos;
        //    54: astore          6
        //    56: aload_0        
        //    57: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    60: aload           6
        //    62: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    65: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    70: astore          7
        //    72: aload           7
        //    74: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    77: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //    80: if_acmpeq       208
        //    83: aload_0        
        //    84: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    87: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //    90: aload           6
        //    92: invokevirtual   net/minecraft/world/border/WorldBorder.contains:(Lnet/minecraft/util/BlockPos;)Z
        //    95: ifeq            208
        //    98: aload           7
        //   100: aload_0        
        //   101: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   104: aload           6
        //   106: invokevirtual   net/minecraft/block/Block.setBlockBoundsBasedOnState:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)V
        //   109: aload_1        
        //   110: getfield        net/minecraft/entity/player/EntityPlayer.lastTickPosX:D
        //   113: aload_1        
        //   114: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   117: aload_1        
        //   118: getfield        net/minecraft/entity/player/EntityPlayer.lastTickPosX:D
        //   121: dsub           
        //   122: fload           4
        //   124: f2d            
        //   125: dmul           
        //   126: dadd           
        //   127: dstore          8
        //   129: aload_1        
        //   130: getfield        net/minecraft/entity/player/EntityPlayer.lastTickPosY:D
        //   133: aload_1        
        //   134: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //   137: aload_1        
        //   138: getfield        net/minecraft/entity/player/EntityPlayer.lastTickPosY:D
        //   141: dsub           
        //   142: fload           4
        //   144: f2d            
        //   145: dmul           
        //   146: dadd           
        //   147: dstore          10
        //   149: aload_1        
        //   150: getfield        net/minecraft/entity/player/EntityPlayer.lastTickPosZ:D
        //   153: aload_1        
        //   154: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   157: aload_1        
        //   158: getfield        net/minecraft/entity/player/EntityPlayer.lastTickPosZ:D
        //   161: dsub           
        //   162: fload           4
        //   164: f2d            
        //   165: dmul           
        //   166: dadd           
        //   167: dstore          12
        //   169: aload           7
        //   171: aload_0        
        //   172: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   175: aload           6
        //   177: invokevirtual   net/minecraft/block/Block.getSelectedBoundingBox:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/AxisAlignedBB;
        //   180: ldc2_w          0.0020000000949949026
        //   183: ldc2_w          0.0020000000949949026
        //   186: ldc2_w          0.0020000000949949026
        //   189: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   192: dload           8
        //   194: dneg           
        //   195: dload           10
        //   197: dneg           
        //   198: dload           12
        //   200: dneg           
        //   201: invokevirtual   net/minecraft/util/AxisAlignedBB.offset:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   204: iconst_m1      
        //   205: invokestatic    net/minecraft/client/renderer/RenderGlobal.drawOutlinedBoundingBox:(Lnet/minecraft/util/AxisAlignedBB;I)V
        //   208: iconst_1       
        //   209: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   212: invokestatic    optifine/Config.isShaders:()Z
        //   215: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0215 (coming from #0212).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB axisAlignedBB, final int n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawing(3);
        if (n != -1) {
            worldRenderer.func_178991_c(n);
        }
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        instance.draw();
        worldRenderer.startDrawing(3);
        if (n != -1) {
            worldRenderer.func_178991_c(n);
        }
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        instance.draw();
        worldRenderer.startDrawing(1);
        if (n != -1) {
            worldRenderer.func_178991_c(n);
        }
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        instance.draw();
    }
    
    private void markBlocksForUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_175008_n.func_178162_a(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void markBlockForUpdate(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.markBlocksForUpdate(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
    }
    
    @Override
    public void notifyLightSet(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.markBlocksForUpdate(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
    }
    
    @Override
    public void markBlockRangeForRenderUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.markBlocksForUpdate(n - 1, n2 - 1, n3 - 1, n4 + 1, n5 + 1, n6 + 1);
    }
    
    @Override
    public void func_174961_a(final String s, final BlockPos blockPos) {
        final ISound sound = this.mapSoundPositions.get(blockPos);
        if (sound != null) {
            Minecraft.getSoundHandler().stopSound(sound);
            this.mapSoundPositions.remove(blockPos);
        }
        if (s != null) {
            final ItemRecord record = ItemRecord.getRecord(s);
            if (record != null) {
                Minecraft.ingameGUI.setRecordPlayingMessage(record.getRecordNameLocal());
            }
            ResourceLocation resourceLocation = null;
            if (Reflector.ForgeItemRecord_getRecordResource.exists() && record != null) {
                resourceLocation = (ResourceLocation)Reflector.call(record, Reflector.ForgeItemRecord_getRecordResource, s);
            }
            if (resourceLocation == null) {
                resourceLocation = new ResourceLocation(s);
            }
            final PositionedSoundRecord recordSoundAtPosition = PositionedSoundRecord.createRecordSoundAtPosition(resourceLocation, (float)blockPos.getX(), (float)blockPos.getY(), (float)blockPos.getZ());
            this.mapSoundPositions.put(blockPos, recordSoundAtPosition);
            Minecraft.getSoundHandler().playSound(recordSoundAtPosition);
        }
    }
    
    @Override
    public void playSound(final String s, final double n, final double n2, final double n3, final float n4, final float n5) {
    }
    
    @Override
    public void playSoundToNearExcept(final EntityPlayer entityPlayer, final String s, final double n, final double n2, final double n3, final float n4, final float n5) {
    }
    
    @Override
    public void func_180442_a(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        this.func_174974_b(n, b, n2, n3, n4, n5, n6, n7, array);
    }
    
    private void func_174972_a(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.func_180442_a(enumParticleTypes.func_179348_c(), enumParticleTypes.func_179344_e(), n, n2, n3, n4, n5, n6, array);
    }
    
    private EntityFX func_174974_b(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        if (this.mc == null || this.mc.func_175606_aa() == null || this.mc.effectRenderer == null) {
            return null;
        }
        final int particleSetting = this.mc.gameSettings.particleSetting;
        if (2 != 1 || this.theWorld.rand.nextInt(3) == 0) {}
        final double n8 = this.mc.func_175606_aa().posX - n2;
        final double n9 = this.mc.func_175606_aa().posY - n3;
        final double n10 = this.mc.func_175606_aa().posZ - n4;
        if (n == EnumParticleTypes.EXPLOSION_HUGE.func_179348_c() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.EXPLOSION_LARGE.func_179348_c() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.EXPLOSION_NORMAL.func_179348_c() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.SUSPENDED.func_179348_c() && !Config.isWaterParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SUSPENDED_DEPTH.func_179348_c() && !Config.isVoidParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SMOKE_NORMAL.func_179348_c() && !Config.isAnimatedSmoke()) {
            return null;
        }
        if (n == EnumParticleTypes.SMOKE_LARGE.func_179348_c() && !Config.isAnimatedSmoke()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_MOB.func_179348_c() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_MOB_AMBIENT.func_179348_c() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL.func_179348_c() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_INSTANT.func_179348_c() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_WITCH.func_179348_c() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.PORTAL.func_179348_c() && !Config.isAnimatedPortal()) {
            return null;
        }
        if (n == EnumParticleTypes.FLAME.func_179348_c() && !Config.isAnimatedFlame()) {
            return null;
        }
        if (n == EnumParticleTypes.REDSTONE.func_179348_c() && !Config.isAnimatedRedstone()) {
            return null;
        }
        if (n == EnumParticleTypes.DRIP_WATER.func_179348_c() && !Config.isDrippingWaterLava()) {
            return null;
        }
        if (n == EnumParticleTypes.DRIP_LAVA.func_179348_c() && !Config.isDrippingWaterLava()) {
            return null;
        }
        if (n == EnumParticleTypes.FIREWORKS_SPARK.func_179348_c() && !Config.isFireworkParticles()) {
            return null;
        }
        if (b) {
            return this.mc.effectRenderer.func_178927_a(n, n2, n3, n4, n5, n6, n7, array);
        }
        double n11 = 256.0;
        if (n == EnumParticleTypes.CRIT.func_179348_c()) {
            n11 = 38416.0;
        }
        if (n8 * n8 + n9 * n9 + n10 * n10 > n11) {
            return null;
        }
        if (2 > 1) {
            return null;
        }
        final EntityFX func_178927_a = this.mc.effectRenderer.func_178927_a(n, n2, n3, n4, n5, n6, n7, array);
        if (n == EnumParticleTypes.WATER_BUBBLE.func_179348_c()) {
            CustomColors.updateWaterFX(func_178927_a, this.theWorld, n2, n3, n4);
        }
        if (n == EnumParticleTypes.WATER_SPLASH.func_179348_c()) {
            CustomColors.updateWaterFX(func_178927_a, this.theWorld, n2, n3, n4);
        }
        if (n == EnumParticleTypes.WATER_DROP.func_179348_c()) {
            CustomColors.updateWaterFX(func_178927_a, this.theWorld, n2, n3, n4);
        }
        if (n == EnumParticleTypes.TOWN_AURA.func_179348_c()) {
            CustomColors.updateMyceliumFX(func_178927_a);
        }
        if (n == EnumParticleTypes.PORTAL.func_179348_c()) {
            CustomColors.updatePortalFX(func_178927_a);
        }
        if (n == EnumParticleTypes.REDSTONE.func_179348_c()) {
            CustomColors.updateReddustFX(func_178927_a, this.theWorld, n2, n3, n4);
        }
        return func_178927_a;
    }
    
    @Override
    public void onEntityAdded(final Entity entity) {
        RandomMobs.entityLoaded(entity, this.theWorld);
        if (Config.isDynamicLights()) {
            DynamicLights.entityAdded(entity, this);
        }
    }
    
    @Override
    public void onEntityRemoved(final Entity entity) {
        if (Config.isDynamicLights()) {
            DynamicLights.entityRemoved(entity, this);
        }
    }
    
    public void deleteAllDisplayLists() {
    }
    
    @Override
    public void func_180440_a(final int n, final BlockPos blockPos, final int n2) {
        switch (n) {
            case 1013:
            case 1018: {
                if (this.mc.func_175606_aa() == null) {
                    break;
                }
                final double n3 = blockPos.getX() - this.mc.func_175606_aa().posX;
                final double n4 = blockPos.getY() - this.mc.func_175606_aa().posY;
                final double n5 = blockPos.getZ() - this.mc.func_175606_aa().posZ;
                final double sqrt = Math.sqrt(n3 * n3 + n4 * n4 + n5 * n5);
                double posX = this.mc.func_175606_aa().posX;
                double posY = this.mc.func_175606_aa().posY;
                double posZ = this.mc.func_175606_aa().posZ;
                if (sqrt > 0.0) {
                    posX += n3 / sqrt * 2.0;
                    posY += n4 / sqrt * 2.0;
                    posZ += n5 / sqrt * 2.0;
                }
                if (n == 1013) {
                    this.theWorld.playSound(posX, posY, posZ, "mob.wither.spawn", 1.0f, 1.0f, false);
                    break;
                }
                this.theWorld.playSound(posX, posY, posZ, "mob.enderdragon.end", 5.0f, 1.0f, false);
                break;
            }
        }
    }
    
    @Override
    public void func_180439_a(final EntityPlayer entityPlayer, final int n, final BlockPos blockPos, final int n2) {
        final Random rand = this.theWorld.rand;
        switch (n) {
            case 1000: {
                this.theWorld.func_175731_a(blockPos, "random.click", 1.0f, 1.0f, false);
                break;
            }
            case 1001: {
                this.theWorld.func_175731_a(blockPos, "random.click", 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.theWorld.func_175731_a(blockPos, "random.bow", 1.0f, 1.2f, false);
                break;
            }
            case 1003: {
                this.theWorld.func_175731_a(blockPos, "random.door_open", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1004: {
                this.theWorld.func_175731_a(blockPos, "random.fizz", 0.5f, 2.6f + (rand.nextFloat() - rand.nextFloat()) * 0.8f, false);
                break;
            }
            case 1005: {
                if (Item.getItemById(n2) instanceof ItemRecord) {
                    this.theWorld.func_175717_a(blockPos, "records." + ((ItemRecord)Item.getItemById(n2)).recordName);
                    break;
                }
                this.theWorld.func_175717_a(blockPos, null);
                break;
            }
            case 1006: {
                this.theWorld.func_175731_a(blockPos, "random.door_close", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1007: {
                this.theWorld.func_175731_a(blockPos, "mob.ghast.charge", 10.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1008: {
                this.theWorld.func_175731_a(blockPos, "mob.ghast.fireball", 10.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1009: {
                this.theWorld.func_175731_a(blockPos, "mob.ghast.fireball", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1010: {
                this.theWorld.func_175731_a(blockPos, "mob.zombie.wood", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1011: {
                this.theWorld.func_175731_a(blockPos, "mob.zombie.metal", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1012: {
                this.theWorld.func_175731_a(blockPos, "mob.zombie.woodbreak", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1014: {
                this.theWorld.func_175731_a(blockPos, "mob.wither.shoot", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1015: {
                this.theWorld.func_175731_a(blockPos, "mob.bat.takeoff", 0.05f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.theWorld.func_175731_a(blockPos, "mob.zombie.infect", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.theWorld.func_175731_a(blockPos, "mob.zombie.unfect", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.theWorld.func_175731_a(blockPos, "random.anvil_break", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1021: {
                this.theWorld.func_175731_a(blockPos, "random.anvil_use", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1022: {
                this.theWorld.func_175731_a(blockPos, "random.anvil_land", 0.3f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2000: {
                final int n3 = n2 % 3 - 1;
                final int n4 = n2 / 3 % 3 - 1;
                final double n5 = blockPos.getX() + n3 * 0.6 + 0.5;
                final double n6 = blockPos.getY() + 0.5;
                final double n7 = blockPos.getZ() + n4 * 0.6 + 0.5;
                while (0 < 10) {
                    final double n8 = rand.nextDouble() * 0.2 + 0.01;
                    this.func_174972_a(EnumParticleTypes.SMOKE_NORMAL, n5 + n3 * 0.01 + (rand.nextDouble() - 0.5) * n4 * 0.5, n6 + (rand.nextDouble() - 0.5) * 0.5, n7 + n4 * 0.01 + (rand.nextDouble() - 0.5) * n3 * 0.5, n3 * n8 + rand.nextGaussian() * 0.01, -0.03 + rand.nextGaussian() * 0.01, n4 * n8 + rand.nextGaussian() * 0.01, new int[0]);
                    int n9 = 0;
                    ++n9;
                }
            }
            case 2001: {
                final Block blockById = Block.getBlockById(n2 & 0xFFF);
                if (blockById.getMaterial() != Material.air) {
                    Minecraft.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(blockById.stepSound.getBreakSound()), (blockById.stepSound.getVolume() + 1.0f) / 2.0f, blockById.stepSound.getFrequency() * 0.8f, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f));
                }
                this.mc.effectRenderer.func_180533_a(blockPos, blockById.getStateFromMeta(n2 >> 12 & 0xFF));
                break;
            }
            case 2002: {
                final double n10 = blockPos.getX();
                final double n11 = blockPos.getY();
                final double n12 = blockPos.getZ();
                while (0 < 8) {
                    this.func_174972_a(EnumParticleTypes.ITEM_CRACK, n10, n11, n12, rand.nextGaussian() * 0.15, rand.nextDouble() * 0.2, rand.nextGaussian() * 0.15, Item.getIdFromItem(Items.potionitem), n2);
                    int colorFromDamage = 0;
                    ++colorFromDamage;
                }
                int colorFromDamage = Items.potionitem.getColorFromDamage(n2);
                final float n13 = 0 / 255.0f;
                final float n14 = 0 / 255.0f;
                final float n15 = 0 / 255.0f;
                EnumParticleTypes enumParticleTypes = EnumParticleTypes.SPELL;
                if (Items.potionitem.isEffectInstant(n2)) {
                    enumParticleTypes = EnumParticleTypes.SPELL_INSTANT;
                }
                while (0 < 100) {
                    final double n16 = rand.nextDouble() * 4.0;
                    final double n17 = rand.nextDouble() * 3.141592653589793 * 2.0;
                    final double n18 = Math.cos(n17) * n16;
                    final double n19 = 0.01 + rand.nextDouble() * 0.5;
                    final double n20 = Math.sin(n17) * n16;
                    final EntityFX func_174974_b = this.func_174974_b(enumParticleTypes.func_179348_c(), enumParticleTypes.func_179344_e(), n10 + n18 * 0.1, n11 + 0.3, n12 + n20 * 0.1, n18, n19, n20, new int[0]);
                    if (func_174974_b != null) {
                        final float n21 = 0.75f + rand.nextFloat() * 0.25f;
                        func_174974_b.setRBGColorF(n13 * n21, n14 * n21, n15 * n21);
                        func_174974_b.multiplyVelocity((float)n16);
                    }
                    int n22 = 0;
                    ++n22;
                }
                this.theWorld.func_175731_a(blockPos, "game.potion.smash", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2003: {
                final double n23 = blockPos.getX() + 0.5;
                final double n24 = blockPos.getY();
                final double n25 = blockPos.getZ() + 0.5;
                while (0 < 8) {
                    this.func_174972_a(EnumParticleTypes.ITEM_CRACK, n23, n24, n25, rand.nextGaussian() * 0.15, rand.nextDouble() * 0.2, rand.nextGaussian() * 0.15, Item.getIdFromItem(Items.ender_eye));
                    int colorFromDamage = 0;
                    ++colorFromDamage;
                }
                for (double n26 = 0.0; n26 < 6.283185307179586; n26 += 0.15707963267948966) {
                    this.func_174972_a(EnumParticleTypes.PORTAL, n23 + Math.cos(n26) * 5.0, n24 - 0.4, n25 + Math.sin(n26) * 5.0, Math.cos(n26) * -5.0, 0.0, Math.sin(n26) * -5.0, new int[0]);
                    this.func_174972_a(EnumParticleTypes.PORTAL, n23 + Math.cos(n26) * 5.0, n24 - 0.4, n25 + Math.sin(n26) * 5.0, Math.cos(n26) * -7.0, 0.0, Math.sin(n26) * -7.0, new int[0]);
                }
            }
            case 2004: {
                while (0 < 20) {
                    final double n27 = blockPos.getX() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double n28 = blockPos.getY() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double n29 = blockPos.getZ() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n27, n28, n29, 0.0, 0.0, 0.0, new int[0]);
                    this.theWorld.spawnParticle(EnumParticleTypes.FLAME, n27, n28, n29, 0.0, 0.0, 0.0, new int[0]);
                    int n22 = 0;
                    ++n22;
                }
            }
            case 2005: {
                ItemDye.func_180617_a(this.theWorld, blockPos, n2);
                break;
            }
        }
    }
    
    @Override
    public void sendBlockBreakProgress(final int n, final BlockPos blockPos, final int partialBlockDamage) {
        if (partialBlockDamage >= 0 && partialBlockDamage < 10) {
            DestroyBlockProgress destroyBlockProgress = this.damagedBlocks.get(n);
            if (destroyBlockProgress == null || destroyBlockProgress.func_180246_b().getX() != blockPos.getX() || destroyBlockProgress.func_180246_b().getY() != blockPos.getY() || destroyBlockProgress.func_180246_b().getZ() != blockPos.getZ()) {
                destroyBlockProgress = new DestroyBlockProgress(n, blockPos);
                this.damagedBlocks.put(n, destroyBlockProgress);
            }
            destroyBlockProgress.setPartialBlockDamage(partialBlockDamage);
            destroyBlockProgress.setCloudUpdateTick(this.cloudTickCounter);
        }
        else {
            this.damagedBlocks.remove(n);
        }
    }
    
    public void func_174979_m() {
        this.displayListEntitiesDirty = true;
    }
    
    public void resetClouds() {
        this.cloudRenderer.reset();
    }
    
    public int getCountRenderers() {
        return this.field_175008_n.field_178164_f.length;
    }
    
    public int getCountActiveRenderers() {
        return this.glRenderLists.size();
    }
    
    public int getCountEntitiesRendered() {
        return this.countEntitiesRendered;
    }
    
    public int getCountTileEntitiesRendered() {
        return this.countTileEntitiesRendered;
    }
    
    public RenderChunk getRenderChunk(final BlockPos blockPos) {
        return this.field_175008_n.func_178161_a(blockPos);
    }
    
    public RenderChunk getRenderChunk(final RenderChunk renderChunk, final EnumFacing enumFacing) {
        if (renderChunk == null) {
            return null;
        }
        return this.field_175008_n.func_178161_a(renderChunk.getPositionOffset16(enumFacing));
    }
    
    public WorldClient getWorld() {
        return this.theWorld;
    }
    
    public static class ContainerLocalRenderInformation
    {
        final RenderChunk field_178036_a;
        final EnumFacing field_178034_b;
        final Set field_178035_c;
        final int field_178032_d;
        
        public ContainerLocalRenderInformation(final RenderChunk field_178036_a, final EnumFacing field_178034_b, final int field_178032_d) {
            this.field_178035_c = EnumSet.noneOf(EnumFacing.class);
            this.field_178036_a = field_178036_a;
            this.field_178034_b = field_178034_b;
            this.field_178032_d = field_178032_d;
        }
        
        ContainerLocalRenderInformation(final RenderChunk renderChunk, final EnumFacing enumFacing, final int n, final Object o) {
            this(renderChunk, enumFacing, n);
        }
    }
    
    static final class SwitchEnumUseage
    {
        static final int[] field_178037_a;
        
        static {
            field_178037_a = new int[VertexFormatElement.EnumUseage.values().length];
            try {
                SwitchEnumUseage.field_178037_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumUseage.field_178037_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumUseage.field_178037_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
