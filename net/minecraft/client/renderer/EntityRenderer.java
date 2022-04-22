package net.minecraft.client.renderer;

import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import java.nio.*;
import net.minecraft.client.shader.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.settings.*;
import net.minecraft.block.material.*;
import Mood.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.potion.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import shadersmod.client.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.enchantment.*;
import org.lwjgl.opengl.*;
import net.minecraft.server.integrated.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import optifine.*;
import org.lwjgl.util.glu.*;
import java.util.*;
import java.lang.reflect.*;
import DTool.util.*;

public class EntityRenderer implements IResourceManagerReloadListener
{
    private static final Logger logger;
    private static final ResourceLocation locationRainPng;
    private static final ResourceLocation locationSnowPng;
    public static boolean anaglyphEnable;
    public static int anaglyphField;
    private Minecraft mc;
    private final IResourceManager resourceManager;
    private Random random;
    private float farPlaneDistance;
    public ItemRenderer itemRenderer;
    private final MapItemRenderer theMapItemRenderer;
    private int rendererUpdateCount;
    private Entity pointedEntity;
    private MouseFilter mouseFilterXAxis;
    private MouseFilter mouseFilterYAxis;
    private float thirdPersonDistance;
    private float thirdPersonDistanceTemp;
    private float smoothCamYaw;
    private float smoothCamPitch;
    private float smoothCamFilterX;
    private float smoothCamFilterY;
    private float smoothCamPartialTicks;
    private float fovModifierHand;
    private float fovModifierHandPrev;
    private float bossColorModifier;
    private float bossColorModifierPrev;
    private boolean cloudFog;
    private boolean field_175074_C;
    private boolean field_175073_D;
    private long prevFrameTime;
    private long renderEndNanoTime;
    private final DynamicTexture lightmapTexture;
    private final int[] lightmapColors;
    private final ResourceLocation locationLightMap;
    private boolean lightmapUpdateNeeded;
    private float torchFlickerX;
    private float field_175075_L;
    private int rainSoundCounter;
    private float[] field_175076_N;
    private float[] field_175077_O;
    private FloatBuffer fogColorBuffer;
    public float field_175080_Q;
    public float field_175082_R;
    public float field_175081_S;
    private float fogColor2;
    private float fogColor1;
    private int field_175079_V;
    private boolean field_175078_W;
    private double cameraZoom;
    private double cameraYaw;
    private double cameraPitch;
    private ShaderGroup theShaderGroup;
    private static final ResourceLocation[] shaderResourceLocations;
    public static final int shaderCount;
    private int shaderIndex;
    private boolean field_175083_ad;
    public int field_175084_ae;
    private static final String __OBFID;
    private boolean initialized;
    private World updatedWorld;
    private boolean showDebugInfo;
    public boolean fogStandard;
    private float clipDistance;
    private long lastServerTime;
    private int lastServerTicks;
    private int serverWaitTime;
    private int serverWaitTimeCurrent;
    private float avgServerTimeDiff;
    private float avgServerTickDiff;
    private long lastErrorCheckTimeMs;
    private ShaderGroup[] fxaaShaders;
    
    static {
        __OBFID = "CL_00000947";
        logger = LogManager.getLogger();
        locationRainPng = new ResourceLocation("textures/environment/rain.png");
        locationSnowPng = new ResourceLocation("textures/environment/snow.png");
        shaderResourceLocations = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
        shaderCount = EntityRenderer.shaderResourceLocations.length;
    }
    
    public EntityRenderer(final Minecraft mc, final IResourceManager resourceManager) {
        this.random = new Random();
        this.mouseFilterXAxis = new MouseFilter();
        this.mouseFilterYAxis = new MouseFilter();
        this.thirdPersonDistance = 4.0f;
        this.thirdPersonDistanceTemp = 4.0f;
        this.field_175074_C = true;
        this.field_175073_D = true;
        this.prevFrameTime = Minecraft.getSystemTime();
        this.field_175076_N = new float[1024];
        this.field_175077_O = new float[1024];
        this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.field_175079_V = 0;
        this.field_175078_W = false;
        this.cameraZoom = 1.0;
        this.initialized = false;
        this.updatedWorld = null;
        this.showDebugInfo = false;
        this.fogStandard = false;
        this.clipDistance = 128.0f;
        this.lastServerTime = 0L;
        this.lastServerTicks = 0;
        this.serverWaitTime = 0;
        this.serverWaitTimeCurrent = 0;
        this.avgServerTimeDiff = 0.0f;
        this.avgServerTickDiff = 0.0f;
        this.lastErrorCheckTimeMs = 0L;
        this.fxaaShaders = new ShaderGroup[10];
        this.shaderIndex = EntityRenderer.shaderCount;
        this.field_175083_ad = false;
        this.field_175084_ae = 0;
        this.mc = mc;
        this.resourceManager = resourceManager;
        this.itemRenderer = mc.getItemRenderer();
        this.theMapItemRenderer = new MapItemRenderer(mc.getTextureManager());
        this.lightmapTexture = new DynamicTexture(16, 16);
        this.locationLightMap = mc.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;
        while (0 < 32) {
            while (0 < 32) {
                final float n = -16;
                final float n2 = -16;
                final float sqrt_float = MathHelper.sqrt_float(n * n + n2 * n2);
                this.field_175076_N[0] = -n2 / sqrt_float;
                this.field_175077_O[0] = n / sqrt_float;
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
    }
    
    public boolean isShaderActive() {
        return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
    }
    
    public void stopUseShader() {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        this.shaderIndex = EntityRenderer.shaderCount;
    }
    
    public void func_175071_c() {
        this.field_175083_ad = !this.field_175083_ad;
    }
    
    public void func_175066_a(final Entity entity) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.theShaderGroup = null;
            if (entity instanceof EntityCreeper) {
                this.func_175069_a(new ResourceLocation("shaders/post/creeper.json"));
            }
            else if (entity instanceof EntitySpider) {
                this.func_175069_a(new ResourceLocation("shaders/post/spider.json"));
            }
            else if (entity instanceof EntityEnderman) {
                this.func_175069_a(new ResourceLocation("shaders/post/invert.json"));
            }
            else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
                Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, entity, this);
            }
        }
    }
    
    public void activateNextShader() {
        if (OpenGlHelper.shadersSupported && this.mc.func_175606_aa() instanceof EntityPlayer) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.shaderIndex = (this.shaderIndex + 1) % (EntityRenderer.shaderResourceLocations.length + 1);
            if (this.shaderIndex != EntityRenderer.shaderCount) {
                this.func_175069_a(EntityRenderer.shaderResourceLocations[this.shaderIndex]);
            }
            else {
                this.theShaderGroup = null;
            }
        }
    }
    
    private void func_175069_a(final ResourceLocation resourceLocation) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            (this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocation)).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.field_175083_ad = true;
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        if (this.shaderIndex != EntityRenderer.shaderCount) {
            this.func_175069_a(EntityRenderer.shaderResourceLocations[this.shaderIndex]);
        }
        else {
            this.func_175066_a(this.mc.func_175606_aa());
        }
    }
    
    public void updateRenderer() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            9
        //     6: invokestatic    net/minecraft/client/shader/ShaderLinkHelper.getStaticShaderLinkHelper:()Lnet/minecraft/client/shader/ShaderLinkHelper;
        //     9: aload_0        
        //    10: invokespecial   net/minecraft/client/renderer/EntityRenderer.updateFovModifierHand:()V
        //    13: aload_0        
        //    14: invokespecial   net/minecraft/client/renderer/EntityRenderer.updateTorchFlicker:()V
        //    17: aload_0        
        //    18: aload_0        
        //    19: getfield        net/minecraft/client/renderer/EntityRenderer.fogColor1:F
        //    22: putfield        net/minecraft/client/renderer/EntityRenderer.fogColor2:F
        //    25: aload_0        
        //    26: aload_0        
        //    27: getfield        net/minecraft/client/renderer/EntityRenderer.thirdPersonDistance:F
        //    30: putfield        net/minecraft/client/renderer/EntityRenderer.thirdPersonDistanceTemp:F
        //    33: aload_0        
        //    34: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    37: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    40: getfield        net/minecraft/client/settings/GameSettings.smoothCamera:Z
        //    43: ifeq            133
        //    46: aload_0        
        //    47: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    50: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    53: getfield        net/minecraft/client/settings/GameSettings.mouseSensitivity:F
        //    56: ldc_w           0.6
        //    59: fmul           
        //    60: ldc_w           0.2
        //    63: fadd           
        //    64: fstore_1       
        //    65: fload_1        
        //    66: fload_1        
        //    67: fmul           
        //    68: fload_1        
        //    69: fmul           
        //    70: ldc_w           8.0
        //    73: fmul           
        //    74: fstore_2       
        //    75: aload_0        
        //    76: aload_0        
        //    77: getfield        net/minecraft/client/renderer/EntityRenderer.mouseFilterXAxis:Lnet/minecraft/util/MouseFilter;
        //    80: aload_0        
        //    81: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //    84: ldc_w           0.05
        //    87: fload_2        
        //    88: fmul           
        //    89: invokevirtual   net/minecraft/util/MouseFilter.smooth:(FF)F
        //    92: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterX:F
        //    95: aload_0        
        //    96: aload_0        
        //    97: getfield        net/minecraft/client/renderer/EntityRenderer.mouseFilterYAxis:Lnet/minecraft/util/MouseFilter;
        //   100: aload_0        
        //   101: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   104: ldc_w           0.05
        //   107: fload_2        
        //   108: fmul           
        //   109: invokevirtual   net/minecraft/util/MouseFilter.smooth:(FF)F
        //   112: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterY:F
        //   115: aload_0        
        //   116: fconst_0       
        //   117: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPartialTicks:F
        //   120: aload_0        
        //   121: fconst_0       
        //   122: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //   125: aload_0        
        //   126: fconst_0       
        //   127: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   130: goto            157
        //   133: aload_0        
        //   134: fconst_0       
        //   135: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterX:F
        //   138: aload_0        
        //   139: fconst_0       
        //   140: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterY:F
        //   143: aload_0        
        //   144: getfield        net/minecraft/client/renderer/EntityRenderer.mouseFilterXAxis:Lnet/minecraft/util/MouseFilter;
        //   147: invokevirtual   net/minecraft/util/MouseFilter.func_180179_a:()V
        //   150: aload_0        
        //   151: getfield        net/minecraft/client/renderer/EntityRenderer.mouseFilterYAxis:Lnet/minecraft/util/MouseFilter;
        //   154: invokevirtual   net/minecraft/util/MouseFilter.func_180179_a:()V
        //   157: aload_0        
        //   158: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   161: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   164: ifnonnull       177
        //   167: aload_0        
        //   168: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   171: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   174: invokevirtual   net/minecraft/client/Minecraft.func_175607_a:(Lnet/minecraft/entity/Entity;)V
        //   177: aload_0        
        //   178: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   181: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   184: astore_3       
        //   185: aload_3        
        //   186: getfield        net/minecraft/entity/Entity.posX:D
        //   189: dstore          4
        //   191: aload_3        
        //   192: getfield        net/minecraft/entity/Entity.posY:D
        //   195: aload_3        
        //   196: invokevirtual   net/minecraft/entity/Entity.getEyeHeight:()F
        //   199: f2d            
        //   200: dadd           
        //   201: dstore          6
        //   203: aload_3        
        //   204: getfield        net/minecraft/entity/Entity.posZ:D
        //   207: dstore          8
        //   209: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   212: new             Lnet/minecraft/util/BlockPos;
        //   215: dup            
        //   216: dload           4
        //   218: dload           6
        //   220: dload           8
        //   222: invokespecial   net/minecraft/util/BlockPos.<init>:(DDD)V
        //   225: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getLightBrightness:(Lnet/minecraft/util/BlockPos;)F
        //   228: fstore_1       
        //   229: aload_0        
        //   230: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   233: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   236: pop            
        //   237: getstatic       net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //   240: i2f            
        //   241: ldc_w           16.0
        //   244: fdiv           
        //   245: fstore_2       
        //   246: fload_2        
        //   247: fconst_0       
        //   248: fconst_1       
        //   249: invokestatic    net/minecraft/util/MathHelper.clamp_float:(FFF)F
        //   252: fstore_2       
        //   253: fload_1        
        //   254: fconst_1       
        //   255: fload_2        
        //   256: fsub           
        //   257: fmul           
        //   258: fload_2        
        //   259: fadd           
        //   260: fstore          10
        //   262: aload_0        
        //   263: dup            
        //   264: getfield        net/minecraft/client/renderer/EntityRenderer.fogColor1:F
        //   267: fload           10
        //   269: aload_0        
        //   270: getfield        net/minecraft/client/renderer/EntityRenderer.fogColor1:F
        //   273: fsub           
        //   274: ldc_w           0.1
        //   277: fmul           
        //   278: fadd           
        //   279: putfield        net/minecraft/client/renderer/EntityRenderer.fogColor1:F
        //   282: aload_0        
        //   283: dup            
        //   284: getfield        net/minecraft/client/renderer/EntityRenderer.rendererUpdateCount:I
        //   287: iconst_1       
        //   288: iadd           
        //   289: putfield        net/minecraft/client/renderer/EntityRenderer.rendererUpdateCount:I
        //   292: aload_0        
        //   293: getfield        net/minecraft/client/renderer/EntityRenderer.itemRenderer:Lnet/minecraft/client/renderer/ItemRenderer;
        //   296: invokevirtual   net/minecraft/client/renderer/ItemRenderer.updateEquippedItem:()V
        //   299: aload_0        
        //   300: invokespecial   net/minecraft/client/renderer/EntityRenderer.addRainParticles:()V
        //   303: aload_0        
        //   304: aload_0        
        //   305: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   308: putfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifierPrev:F
        //   311: getstatic       net/minecraft/entity/boss/BossStatus.hasColorModifier:Z
        //   314: ifeq            350
        //   317: aload_0        
        //   318: dup            
        //   319: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   322: ldc_w           0.05
        //   325: fadd           
        //   326: putfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   329: aload_0        
        //   330: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   333: fconst_1       
        //   334: fcmpl          
        //   335: ifle            343
        //   338: aload_0        
        //   339: fconst_1       
        //   340: putfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   343: iconst_0       
        //   344: putstatic       net/minecraft/entity/boss/BossStatus.hasColorModifier:Z
        //   347: goto            371
        //   350: aload_0        
        //   351: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   354: fconst_0       
        //   355: fcmpl          
        //   356: ifle            371
        //   359: aload_0        
        //   360: dup            
        //   361: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   364: ldc_w           0.0125
        //   367: fsub           
        //   368: putfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   371: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0009 (coming from #0006).
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
    
    public ShaderGroup getShaderGroup() {
        return this.theShaderGroup;
    }
    
    public void updateShaderGroupSize(final int n, final int n2) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.createBindFramebuffers(n, n2);
            }
            this.mc.renderGlobal.checkOcclusionQueryResult(n, n2);
        }
    }
    
    public void getMouseOver(final float n) {
        final Entity func_175606_aa = this.mc.func_175606_aa();
        if (func_175606_aa != null && Minecraft.theWorld != null) {
            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;
            final double n2 = Minecraft.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = func_175606_aa.func_174822_a(n2, n);
            double distanceTo = n2;
            final Vec3 func_174824_e = func_175606_aa.func_174824_e(n);
            double n3;
            if (Minecraft.playerController.extendedReach()) {
                n3 = 6.0;
                distanceTo = 6.0;
            }
            else {
                if (n2 > 3.0) {
                    distanceTo = 3.0;
                }
                n3 = distanceTo;
            }
            if (this.mc.objectMouseOver != null) {
                distanceTo = this.mc.objectMouseOver.hitVec.distanceTo(func_174824_e);
            }
            final Vec3 look = func_175606_aa.getLook(n);
            final Vec3 addVector = func_174824_e.addVector(look.xCoord * n3, look.yCoord * n3, look.zCoord * n3);
            this.pointedEntity = null;
            Vec3 vec3 = null;
            final float n4 = 1.0f;
            final List entitiesWithinAABBExcludingEntity = Minecraft.theWorld.getEntitiesWithinAABBExcludingEntity(func_175606_aa, func_175606_aa.getEntityBoundingBox().addCoord(look.xCoord * n3, look.yCoord * n3, look.zCoord * n3).expand(n4, n4, n4));
            double n5 = distanceTo;
            while (0 < entitiesWithinAABBExcludingEntity.size()) {
                final Entity pointedEntity = entitiesWithinAABBExcludingEntity.get(0);
                if (pointedEntity.canBeCollidedWith()) {
                    final float collisionBorderSize = pointedEntity.getCollisionBorderSize();
                    final AxisAlignedBB expand = pointedEntity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                    final MovingObjectPosition calculateIntercept = expand.calculateIntercept(func_174824_e, addVector);
                    if (expand.isVecInside(func_174824_e)) {
                        if (0.0 < n5 || n5 == 0.0) {
                            this.pointedEntity = pointedEntity;
                            vec3 = ((calculateIntercept == null) ? func_174824_e : calculateIntercept.hitVec);
                            n5 = 0.0;
                        }
                    }
                    else if (calculateIntercept != null) {
                        final double distanceTo2 = func_174824_e.distanceTo(calculateIntercept.hitVec);
                        if (distanceTo2 < n5 || n5 == 0.0) {
                            if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                                Reflector.callBoolean(pointedEntity, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                            }
                            if (pointedEntity == func_175606_aa.ridingEntity && !false) {
                                if (n5 == 0.0) {
                                    this.pointedEntity = pointedEntity;
                                    vec3 = calculateIntercept.hitVec;
                                }
                            }
                            else {
                                this.pointedEntity = pointedEntity;
                                vec3 = calculateIntercept.hitVec;
                                n5 = distanceTo2;
                            }
                        }
                    }
                }
                int n6 = 0;
                ++n6;
            }
            if (this.pointedEntity != null && (n5 < distanceTo || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec3);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }
    
    private void updateFovModifierHand() {
        float func_175156_o = 1.0f;
        if (this.mc.func_175606_aa() instanceof AbstractClientPlayer) {
            func_175156_o = ((AbstractClientPlayer)this.mc.func_175606_aa()).func_175156_o();
        }
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (func_175156_o - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }
    
    private float getFOVModifier(final float n, final boolean b) {
        if (this.field_175078_W) {
            return 90.0f;
        }
        final Entity func_175606_aa = this.mc.func_175606_aa();
        float fovSetting = 70.0f;
        if (b) {
            fovSetting = this.mc.gameSettings.fovSetting;
            if (Config.isDynamicFov()) {
                fovSetting *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * n;
            }
        }
        if (this.mc.currentScreen == null) {
            final GameSettings gameSettings = this.mc.gameSettings;
            GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
        }
        if (false) {
            if (!Config.zoomMode) {
                Config.zoomMode = true;
                this.mc.gameSettings.smoothCamera = true;
            }
            if (Config.zoomMode) {
                fovSetting /= 4.0f;
            }
        }
        else if (Config.zoomMode) {
            Config.zoomMode = false;
            this.mc.gameSettings.smoothCamera = false;
            this.mouseFilterXAxis = new MouseFilter();
            this.mouseFilterYAxis = new MouseFilter();
            this.mc.renderGlobal.displayListEntitiesDirty = true;
        }
        if (func_175606_aa instanceof EntityLivingBase && ((EntityLivingBase)func_175606_aa).getHealth() <= 0.0f) {
            fovSetting /= (1.0f - 500.0f / (((EntityLivingBase)func_175606_aa).deathTime + n + 500.0f)) * 2.0f + 1.0f;
        }
        if (ActiveRenderInfo.func_180786_a(Minecraft.theWorld, func_175606_aa, n).getMaterial() == Material.water) {
            fovSetting = fovSetting * 60.0f / 70.0f;
        }
        return fovSetting;
    }
    
    private void hurtCameraEffect(final float n) {
        if (this.mc.func_175606_aa() instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)this.mc.func_175606_aa();
            final float n2 = entityLivingBase.hurtTime - n;
            if (entityLivingBase.getHealth() <= 0.0f) {
                GlStateManager.rotate(40.0f - 8000.0f / (entityLivingBase.deathTime + n + 200.0f), 0.0f, 0.0f, 1.0f);
            }
            if (n2 < 0.0f) {
                return;
            }
            final float n3 = n2 / entityLivingBase.maxHurtTime;
            final float sin = MathHelper.sin(n3 * n3 * n3 * n3 * 3.1415927f);
            final float attackedAtYaw = entityLivingBase.attackedAtYaw;
            GlStateManager.rotate(-attackedAtYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-sin * 14.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(attackedAtYaw, 0.0f, 1.0f, 0.0f);
        }
    }
    
    private void setupViewBobbing(final float n) {
        if (this.mc.func_175606_aa() instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)this.mc.func_175606_aa();
            final float n2 = -(entityPlayer.distanceWalkedModified + (entityPlayer.distanceWalkedModified - entityPlayer.prevDistanceWalkedModified) * n);
            final float n3 = entityPlayer.prevCameraYaw + (entityPlayer.cameraYaw - entityPlayer.prevCameraYaw) * n;
            final float n4 = entityPlayer.prevCameraPitch + (entityPlayer.cameraPitch - entityPlayer.prevCameraPitch) * n;
            GlStateManager.translate(MathHelper.sin(n2 * 3.1415927f) * n3 * 0.5f, -Math.abs(MathHelper.cos(n2 * 3.1415927f) * n3), 0.0f);
            GlStateManager.rotate(MathHelper.sin(n2 * 3.1415927f) * n3 * 3.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(Math.abs(MathHelper.cos(n2 * 3.1415927f - 0.2f) * n3) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n4, 1.0f, 0.0f, 0.0f);
            final Client instance = Client.INSTANCE;
            if (!Client.getModuleByName("Drugs").toggled) {
                GlStateManager.rotate(MathHelper.sin(n2 * 3.1415927f) * n3 * 3.0f, 0.0f, 0.0f, 1.0f);
            }
            else {
                GlStateManager.rotate(MathHelper.sin(n2 * 3.1415927f) * n3 * 180.0f, MathHelper.sin(n2 * 3.1415927f), 1.5f, n3);
            }
            GlStateManager.rotate(Math.abs(MathHelper.cos(n2 * 3.1415927f - 0.2f) * n3) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n4, 1.0f, 0.0f, 0.0f);
        }
    }
    
    private void orientCamera(final float n) {
        final Entity func_175606_aa = this.mc.func_175606_aa();
        float eyeHeight = func_175606_aa.getEyeHeight();
        final double n2 = func_175606_aa.prevPosX + (func_175606_aa.posX - func_175606_aa.prevPosX) * n;
        final double n3 = func_175606_aa.prevPosY + (func_175606_aa.posY - func_175606_aa.prevPosY) * n + eyeHeight;
        final double n4 = func_175606_aa.prevPosZ + (func_175606_aa.posZ - func_175606_aa.prevPosZ) * n;
        if (func_175606_aa instanceof EntityLivingBase && ((EntityLivingBase)func_175606_aa).isPlayerSleeping()) {
            ++eyeHeight;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                final BlockPos blockPos = new BlockPos(func_175606_aa);
                final IBlockState blockState = Minecraft.theWorld.getBlockState(blockPos);
                final Block block = blockState.getBlock();
                if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, Minecraft.theWorld, blockPos, blockState, func_175606_aa);
                }
                else if (block == Blocks.bed) {
                    GlStateManager.rotate((float)(((EnumFacing)blockState.getValue(BlockBed.AGE)).getHorizontalIndex() * 90), 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(func_175606_aa.prevRotationYaw + (func_175606_aa.rotationYaw - func_175606_aa.prevRotationYaw) * n + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(func_175606_aa.prevRotationPitch + (func_175606_aa.rotationPitch - func_175606_aa.prevRotationPitch) * n, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0) {
            double n5 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * n;
            if (this.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-n5));
            }
            else {
                final float rotationYaw = func_175606_aa.rotationYaw;
                float rotationPitch = func_175606_aa.rotationPitch;
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    rotationPitch += 180.0f;
                }
                final double n6 = -MathHelper.sin(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n5;
                final double n7 = MathHelper.cos(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n5;
                final double n8 = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f) * n5;
                while (0 < 8) {
                    final float n9 = -1;
                    final float n10 = -1;
                    final float n11 = -1;
                    final float n12 = n9 * 0.1f;
                    final float n13 = n10 * 0.1f;
                    final float n14 = n11 * 0.1f;
                    final MovingObjectPosition rayTraceBlocks = Minecraft.theWorld.rayTraceBlocks(new Vec3(n2 + n12, n3 + n13, n4 + n14), new Vec3(n2 - n6 + n12 + n14, n3 - n8 + n13, n4 - n7 + n14));
                    if (rayTraceBlocks != null) {
                        final double distanceTo = rayTraceBlocks.hitVec.distanceTo(new Vec3(n2, n3, n4));
                        if (distanceTo < n5) {
                            n5 = distanceTo;
                        }
                    }
                    int n15 = 0;
                    ++n15;
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(func_175606_aa.rotationPitch - rotationPitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(func_175606_aa.rotationYaw - rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float)(-n5));
                GlStateManager.rotate(rotationYaw - func_175606_aa.rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(rotationPitch - func_175606_aa.rotationPitch, 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GlStateManager.translate(0.0f, 0.0f, -0.1f);
        }
        if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
            if (!this.mc.gameSettings.debugCamEnable) {
                float n16 = func_175606_aa.prevRotationYaw + (func_175606_aa.rotationYaw - func_175606_aa.prevRotationYaw) * n + 180.0f;
                final float n17 = func_175606_aa.prevRotationPitch + (func_175606_aa.rotationPitch - func_175606_aa.prevRotationPitch) * n;
                final float n18 = 0.0f;
                if (func_175606_aa instanceof EntityAnimal) {
                    final EntityAnimal entityAnimal = (EntityAnimal)func_175606_aa;
                    n16 = entityAnimal.prevRotationYawHead + (entityAnimal.rotationYawHead - entityAnimal.prevRotationYawHead) * n + 180.0f;
                }
                final Object instance = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor, this, func_175606_aa, ActiveRenderInfo.func_180786_a(Minecraft.theWorld, func_175606_aa, n), n, n16, n17, n18);
                Reflector.postForgeBusEvent(instance);
                final float fieldValueFloat = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_CameraSetup_roll, n18);
                final float fieldValueFloat2 = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_CameraSetup_pitch, n17);
                final float fieldValueFloat3 = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_CameraSetup_yaw, n16);
                GlStateManager.rotate(fieldValueFloat, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(fieldValueFloat2, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(fieldValueFloat3, 0.0f, 1.0f, 0.0f);
            }
        }
        else if (!this.mc.gameSettings.debugCamEnable) {
            GlStateManager.rotate(func_175606_aa.prevRotationPitch + (func_175606_aa.rotationPitch - func_175606_aa.prevRotationPitch) * n, 1.0f, 0.0f, 0.0f);
            if (func_175606_aa instanceof EntityAnimal) {
                final EntityAnimal entityAnimal2 = (EntityAnimal)func_175606_aa;
                GlStateManager.rotate(entityAnimal2.prevRotationYawHead + (entityAnimal2.rotationYawHead - entityAnimal2.prevRotationYawHead) * n + 180.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GlStateManager.rotate(func_175606_aa.prevRotationYaw + (func_175606_aa.rotationYaw - func_175606_aa.prevRotationYaw) * n + 180.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        GlStateManager.translate(0.0f, -eyeHeight, 0.0f);
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(func_175606_aa.prevPosX + (func_175606_aa.posX - func_175606_aa.prevPosX) * n, func_175606_aa.prevPosY + (func_175606_aa.posY - func_175606_aa.prevPosY) * n + eyeHeight, func_175606_aa.prevPosZ + (func_175606_aa.posZ - func_175606_aa.prevPosZ) * n, n);
    }
    
    public void setupCameraTransform(final float n, final int n2) {
        final GameSettings gameSettings = this.mc.gameSettings;
        this.farPlaneDistance = (float)(GameSettings.renderDistanceChunks * 16);
        if (Config.isFogFancy()) {
            this.farPlaneDistance *= 0.95f;
        }
        if (Config.isFogFast()) {
            this.farPlaneDistance *= 0.83f;
        }
        GlStateManager.matrixMode(5889);
        final float n3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate(-(n2 * 2 - 1) * n3, 0.0f, 0.0f);
        }
        this.clipDistance = this.farPlaneDistance * 2.0f;
        if (this.clipDistance < 173.0f) {
            this.clipDistance = 173.0f;
        }
        if (Minecraft.theWorld.provider.getDimensionId() == 1) {
            this.clipDistance = 256.0f;
        }
        if (this.cameraZoom != 1.0) {
            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0);
        }
        Project.gluPerspective(this.getFOVModifier(n, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
        GlStateManager.matrixMode(5888);
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((n2 * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(n);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(n);
        }
        final float n4 = Minecraft.thePlayer.prevTimeInPortal + (Minecraft.thePlayer.timeInPortal - Minecraft.thePlayer.prevTimeInPortal) * n;
        if (n4 > 0.0f) {
            if (Minecraft.thePlayer.isPotionActive(Potion.confusion)) {}
            final float n5 = 5.0f / (n4 * n4 + 5.0f) - n4 * 0.04f;
            final float n6 = n5 * n5;
            GlStateManager.rotate((this.rendererUpdateCount + n) * 7, 0.0f, 1.0f, 1.0f);
            GlStateManager.scale(1.0f / n6, 1.0f, 1.0f);
            GlStateManager.rotate(-(this.rendererUpdateCount + n) * 7, 0.0f, 1.0f, 1.0f);
        }
        this.orientCamera(n);
        if (this.field_175078_W) {
            switch (this.field_175079_V) {
                case 0: {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 1: {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 2: {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
                case 4: {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
            }
        }
    }
    
    public void renderHand(final float p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/EntityRenderer.field_175078_W:Z
        //     4: ifne            324
        //     7: sipush          5889
        //    10: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //    13: ldc_w           0.07
        //    16: fstore_3       
        //    17: aload_0        
        //    18: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    21: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    24: getfield        net/minecraft/client/settings/GameSettings.anaglyph:Z
        //    27: ifeq            44
        //    30: iload_2        
        //    31: iconst_2       
        //    32: imul           
        //    33: iconst_1       
        //    34: isub           
        //    35: ineg           
        //    36: i2f            
        //    37: fload_3        
        //    38: fmul           
        //    39: fconst_0       
        //    40: fconst_0       
        //    41: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    44: invokestatic    optifine/Config.isShaders:()Z
        //    47: aload_0        
        //    48: fload_1        
        //    49: iconst_0       
        //    50: invokespecial   net/minecraft/client/renderer/EntityRenderer.getFOVModifier:(FZ)F
        //    53: aload_0        
        //    54: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    57: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //    60: i2f            
        //    61: aload_0        
        //    62: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    65: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //    68: i2f            
        //    69: fdiv           
        //    70: ldc_w           0.05
        //    73: aload_0        
        //    74: getfield        net/minecraft/client/renderer/EntityRenderer.farPlaneDistance:F
        //    77: fconst_2       
        //    78: fmul           
        //    79: invokestatic    org/lwjgl/util/glu/Project.gluPerspective:(FFFF)V
        //    82: sipush          5888
        //    85: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //    88: aload_0        
        //    89: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    92: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    95: getfield        net/minecraft/client/settings/GameSettings.anaglyph:Z
        //    98: ifeq            116
        //   101: iload_2        
        //   102: iconst_2       
        //   103: imul           
        //   104: iconst_1       
        //   105: isub           
        //   106: i2f            
        //   107: ldc_w           0.1
        //   110: fmul           
        //   111: fconst_0       
        //   112: fconst_0       
        //   113: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   116: invokestatic    optifine/Config.isShaders:()Z
        //   119: ifeq            128
        //   122: getstatic       shadersmod/client/Shaders.isHandRendered:Z
        //   125: ifne            259
        //   128: aload_0        
        //   129: fload_1        
        //   130: invokespecial   net/minecraft/client/renderer/EntityRenderer.hurtCameraEffect:(F)V
        //   133: aload_0        
        //   134: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   137: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   140: getfield        net/minecraft/client/settings/GameSettings.viewBobbing:Z
        //   143: ifeq            151
        //   146: aload_0        
        //   147: fload_1        
        //   148: invokespecial   net/minecraft/client/renderer/EntityRenderer.setupViewBobbing:(F)V
        //   151: aload_0        
        //   152: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   155: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   158: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   161: ifeq            184
        //   164: aload_0        
        //   165: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   168: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   171: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   174: invokevirtual   net/minecraft/entity/EntityLivingBase.isPlayerSleeping:()Z
        //   177: ifeq            184
        //   180: iconst_1       
        //   181: goto            185
        //   184: iconst_0       
        //   185: istore          4
        //   187: aload_0        
        //   188: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   191: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   194: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //   197: ifne            259
        //   200: iconst_0       
        //   201: ifne            259
        //   204: aload_0        
        //   205: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   208: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   211: getfield        net/minecraft/client/settings/GameSettings.hideGUI:Z
        //   214: ifne            259
        //   217: getstatic       net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //   220: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.enableEverythingIsScrewedUpMode:()Z
        //   223: ifne            259
        //   226: aload_0        
        //   227: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_180436_i:()V
        //   230: invokestatic    optifine/Config.isShaders:()Z
        //   233: ifeq            247
        //   236: aload_0        
        //   237: getfield        net/minecraft/client/renderer/EntityRenderer.itemRenderer:Lnet/minecraft/client/renderer/ItemRenderer;
        //   240: fload_1        
        //   241: invokestatic    shadersmod/client/ShadersRender.renderItemFP:(Lnet/minecraft/client/renderer/ItemRenderer;F)V
        //   244: goto            255
        //   247: aload_0        
        //   248: getfield        net/minecraft/client/renderer/EntityRenderer.itemRenderer:Lnet/minecraft/client/renderer/ItemRenderer;
        //   251: fload_1        
        //   252: invokevirtual   net/minecraft/client/renderer/ItemRenderer.renderItemInFirstPerson:(F)V
        //   255: aload_0        
        //   256: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_175072_h:()V
        //   259: invokestatic    optifine/Config.isShaders:()Z
        //   262: ifeq            272
        //   265: getstatic       shadersmod/client/Shaders.isCompositeRendered:Z
        //   268: ifne            272
        //   271: return         
        //   272: aload_0        
        //   273: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_175072_h:()V
        //   276: aload_0        
        //   277: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   280: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   283: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //   286: ifne            306
        //   289: iconst_0       
        //   290: ifne            306
        //   293: aload_0        
        //   294: getfield        net/minecraft/client/renderer/EntityRenderer.itemRenderer:Lnet/minecraft/client/renderer/ItemRenderer;
        //   297: fload_1        
        //   298: invokevirtual   net/minecraft/client/renderer/ItemRenderer.renderOverlays:(F)V
        //   301: aload_0        
        //   302: fload_1        
        //   303: invokespecial   net/minecraft/client/renderer/EntityRenderer.hurtCameraEffect:(F)V
        //   306: aload_0        
        //   307: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   310: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   313: getfield        net/minecraft/client/settings/GameSettings.viewBobbing:Z
        //   316: ifeq            324
        //   319: aload_0        
        //   320: fload_1        
        //   321: invokespecial   net/minecraft/client/renderer/EntityRenderer.setupViewBobbing:(F)V
        //   324: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0324 (coming from #0316).
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
    
    public void func_175072_h() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        Config.isShaders();
    }
    
    public void func_180436_i() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.matrixMode(5890);
        final float n = 0.00390625f;
        GlStateManager.scale(n, n, n);
        GlStateManager.translate(8.0f, 8.0f, 8.0f);
        GlStateManager.matrixMode(5888);
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        Config.isShaders();
    }
    
    private void updateTorchFlicker() {
        this.field_175075_L += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.field_175075_L *= (float)0.9;
        this.torchFlickerX += (this.field_175075_L - this.torchFlickerX) * 1.0f;
        this.lightmapUpdateNeeded = true;
    }
    
    private void updateLightmap(final float n) {
        if (this.lightmapUpdateNeeded) {
            this.mc.mcProfiler.startSection("lightTex");
            final WorldClient theWorld = Minecraft.theWorld;
            if (theWorld != null) {
                if (Config.isCustomColors() && CustomColors.updateLightmap(theWorld, this.torchFlickerX, this.lightmapColors, Minecraft.thePlayer.isPotionActive(Potion.nightVision))) {
                    this.lightmapTexture.updateDynamicTexture();
                    this.lightmapUpdateNeeded = false;
                    this.mc.mcProfiler.endSection();
                    return;
                }
                while (0 < 256) {
                    float n2 = theWorld.provider.getLightBrightnessTable()[0] * (theWorld.getSunBrightness(1.0f) * 0.95f + 0.05f);
                    final float n3 = theWorld.provider.getLightBrightnessTable()[0] * (this.torchFlickerX * 0.1f + 1.5f);
                    if (theWorld.func_175658_ac() > 0) {
                        n2 = theWorld.provider.getLightBrightnessTable()[0];
                    }
                    final float n4 = n2 * (theWorld.getSunBrightness(1.0f) * 0.65f + 0.35f);
                    final float n5 = n2 * (theWorld.getSunBrightness(1.0f) * 0.65f + 0.35f);
                    final float n6 = n3 * ((n3 * 0.6f + 0.4f) * 0.6f + 0.4f);
                    final float n7 = n3 * (n3 * n3 * 0.6f + 0.4f);
                    final float n8 = n4 + n3;
                    final float n9 = n5 + n6;
                    final float n10 = n2 + n7;
                    float n11 = n8 * 0.96f + 0.03f;
                    float n12 = n9 * 0.96f + 0.03f;
                    float n13 = n10 * 0.96f + 0.03f;
                    if (this.bossColorModifier > 0.0f) {
                        final float n14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * n;
                        n11 = n11 * (1.0f - n14) + n11 * 0.7f * n14;
                        n12 = n12 * (1.0f - n14) + n12 * 0.6f * n14;
                        n13 = n13 * (1.0f - n14) + n13 * 0.6f * n14;
                    }
                    if (theWorld.provider.getDimensionId() == 1) {
                        n11 = 0.22f + n3 * 0.75f;
                        n12 = 0.28f + n6 * 0.75f;
                        n13 = 0.25f + n7 * 0.75f;
                    }
                    if (Minecraft.thePlayer.isPotionActive(Potion.nightVision)) {
                        final float func_180438_a = this.func_180438_a(Minecraft.thePlayer, n);
                        float n15 = 1.0f / n11;
                        if (n15 > 1.0f / n12) {
                            n15 = 1.0f / n12;
                        }
                        if (n15 > 1.0f / n13) {
                            n15 = 1.0f / n13;
                        }
                        n11 = n11 * (1.0f - func_180438_a) + n11 * n15 * func_180438_a;
                        n12 = n12 * (1.0f - func_180438_a) + n12 * n15 * func_180438_a;
                        n13 = n13 * (1.0f - func_180438_a) + n13 * n15 * func_180438_a;
                    }
                    if (n11 > 1.0f) {
                        n11 = 1.0f;
                    }
                    if (n12 > 1.0f) {
                        n12 = 1.0f;
                    }
                    if (n13 > 1.0f) {
                        n13 = 1.0f;
                    }
                    final float gammaSetting = this.mc.gameSettings.gammaSetting;
                    final float n16 = 1.0f - n11;
                    final float n17 = 1.0f - n12;
                    final float n18 = 1.0f - n13;
                    final float n19 = 1.0f - n16 * n16 * n16 * n16;
                    final float n20 = 1.0f - n17 * n17 * n17 * n17;
                    final float n21 = 1.0f - n18 * n18 * n18 * n18;
                    final float n22 = n11 * (1.0f - gammaSetting) + n19 * gammaSetting;
                    final float n23 = n12 * (1.0f - gammaSetting) + n20 * gammaSetting;
                    final float n24 = n13 * (1.0f - gammaSetting) + n21 * gammaSetting;
                    float n25 = n22 * 0.96f + 0.03f;
                    float n26 = n23 * 0.96f + 0.03f;
                    float n27 = n24 * 0.96f + 0.03f;
                    if (n25 > 1.0f) {
                        n25 = 1.0f;
                    }
                    if (n26 > 1.0f) {
                        n26 = 1.0f;
                    }
                    if (n27 > 1.0f) {
                        n27 = 1.0f;
                    }
                    if (n25 < 0.0f) {
                        n25 = 0.0f;
                    }
                    if (n26 < 0.0f) {
                        n26 = 0.0f;
                    }
                    if (n27 < 0.0f) {
                        n27 = 0.0f;
                    }
                    this.lightmapColors[0] = (0xFF000000 | (int)(n25 * 255.0f) << 16 | (int)(n26 * 255.0f) << 8 | (int)(n27 * 255.0f));
                    int n28 = 0;
                    ++n28;
                }
                this.lightmapTexture.updateDynamicTexture();
                this.lightmapUpdateNeeded = false;
                this.mc.mcProfiler.endSection();
            }
        }
    }
    
    private float func_180438_a(final EntityLivingBase entityLivingBase, final float n) {
        final int duration = entityLivingBase.getActivePotionEffect(Potion.nightVision).getDuration();
        return (duration > 200) ? 1.0f : (0.7f + MathHelper.sin((duration - n) * 3.1415927f * 0.2f) * 0.3f);
    }
    
    public void updateCameraAndRender(final float p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/minecraft/client/renderer/EntityRenderer.frameInit:()V
        //     4: invokestatic    org/lwjgl/opengl/Display.isActive:()Z
        //     7: istore_2       
        //     8: iload_2        
        //     9: ifne            70
        //    12: aload_0        
        //    13: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    16: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    19: getfield        net/minecraft/client/settings/GameSettings.pauseOnLostFocus:Z
        //    22: ifeq            70
        //    25: aload_0        
        //    26: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    29: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    32: getfield        net/minecraft/client/settings/GameSettings.touchscreen:Z
        //    35: ifeq            45
        //    38: iconst_1       
        //    39: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //    42: ifne            70
        //    45: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //    48: aload_0        
        //    49: getfield        net/minecraft/client/renderer/EntityRenderer.prevFrameTime:J
        //    52: lsub           
        //    53: ldc2_w          500
        //    56: lcmp           
        //    57: ifle            77
        //    60: aload_0        
        //    61: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    64: invokevirtual   net/minecraft/client/Minecraft.displayInGameMenu:()V
        //    67: goto            77
        //    70: aload_0        
        //    71: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //    74: putfield        net/minecraft/client/renderer/EntityRenderer.prevFrameTime:J
        //    77: aload_0        
        //    78: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    81: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    84: ldc_w           "mouse"
        //    87: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    90: iload_2        
        //    91: ifeq            137
        //    94: getstatic       net/minecraft/client/Minecraft.isRunningOnMac:Z
        //    97: ifeq            137
        //   100: aload_0        
        //   101: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   104: getfield        net/minecraft/client/Minecraft.inGameHasFocus:Z
        //   107: ifeq            137
        //   110: invokestatic    org/lwjgl/input/Mouse.isInsideWindow:()Z
        //   113: ifne            137
        //   116: iconst_0       
        //   117: invokestatic    org/lwjgl/input/Mouse.setGrabbed:(Z)V
        //   120: invokestatic    org/lwjgl/opengl/Display.getWidth:()I
        //   123: iconst_2       
        //   124: idiv           
        //   125: invokestatic    org/lwjgl/opengl/Display.getHeight:()I
        //   128: iconst_2       
        //   129: idiv           
        //   130: invokestatic    org/lwjgl/input/Mouse.setCursorPosition:(II)V
        //   133: iconst_1       
        //   134: invokestatic    org/lwjgl/input/Mouse.setGrabbed:(Z)V
        //   137: aload_0        
        //   138: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   141: getfield        net/minecraft/client/Minecraft.inGameHasFocus:Z
        //   144: ifeq            341
        //   147: iload_2        
        //   148: ifeq            341
        //   151: aload_0        
        //   152: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   155: getfield        net/minecraft/client/Minecraft.mouseHelper:Lnet/minecraft/util/MouseHelper;
        //   158: invokevirtual   net/minecraft/util/MouseHelper.mouseXYChange:()V
        //   161: aload_0        
        //   162: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   165: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   168: getfield        net/minecraft/client/settings/GameSettings.mouseSensitivity:F
        //   171: ldc_w           0.6
        //   174: fmul           
        //   175: ldc_w           0.2
        //   178: fadd           
        //   179: fstore_3       
        //   180: fload_3        
        //   181: fload_3        
        //   182: fmul           
        //   183: fload_3        
        //   184: fmul           
        //   185: ldc_w           8.0
        //   188: fmul           
        //   189: fstore          4
        //   191: aload_0        
        //   192: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   195: getfield        net/minecraft/client/Minecraft.mouseHelper:Lnet/minecraft/util/MouseHelper;
        //   198: getfield        net/minecraft/util/MouseHelper.deltaX:I
        //   201: i2f            
        //   202: fload           4
        //   204: fmul           
        //   205: fstore          5
        //   207: aload_0        
        //   208: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   211: getfield        net/minecraft/client/Minecraft.mouseHelper:Lnet/minecraft/util/MouseHelper;
        //   214: getfield        net/minecraft/util/MouseHelper.deltaY:I
        //   217: i2f            
        //   218: fload           4
        //   220: fmul           
        //   221: fstore          6
        //   223: aload_0        
        //   224: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   227: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   230: getfield        net/minecraft/client/settings/GameSettings.invertMouse:Z
        //   233: ifeq            236
        //   236: aload_0        
        //   237: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   240: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   243: getfield        net/minecraft/client/settings/GameSettings.smoothCamera:Z
        //   246: ifeq            318
        //   249: aload_0        
        //   250: dup            
        //   251: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //   254: fload           5
        //   256: fadd           
        //   257: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //   260: aload_0        
        //   261: dup            
        //   262: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   265: fload           6
        //   267: fadd           
        //   268: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   271: fload_1        
        //   272: aload_0        
        //   273: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPartialTicks:F
        //   276: fsub           
        //   277: fstore          8
        //   279: aload_0        
        //   280: fload_1        
        //   281: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPartialTicks:F
        //   284: aload_0        
        //   285: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterX:F
        //   288: fload           8
        //   290: fmul           
        //   291: fstore          5
        //   293: aload_0        
        //   294: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterY:F
        //   297: fload           8
        //   299: fmul           
        //   300: fstore          6
        //   302: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   305: fload           5
        //   307: fload           6
        //   309: iconst_m1      
        //   310: i2f            
        //   311: fmul           
        //   312: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.setAngles:(FF)V
        //   315: goto            341
        //   318: aload_0        
        //   319: fconst_0       
        //   320: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //   323: aload_0        
        //   324: fconst_0       
        //   325: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   328: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   331: fload           5
        //   333: fload           6
        //   335: iconst_m1      
        //   336: i2f            
        //   337: fmul           
        //   338: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.setAngles:(FF)V
        //   341: aload_0        
        //   342: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   345: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   348: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   351: aload_0        
        //   352: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   355: getfield        net/minecraft/client/Minecraft.skipRenderWorld:Z
        //   358: ifne            885
        //   361: aload_0        
        //   362: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   365: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   368: getfield        net/minecraft/client/settings/GameSettings.anaglyph:Z
        //   371: putstatic       net/minecraft/client/renderer/EntityRenderer.anaglyphEnable:Z
        //   374: new             Lnet/minecraft/client/gui/ScaledResolution;
        //   377: dup            
        //   378: aload_0        
        //   379: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   382: aload_0        
        //   383: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   386: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //   389: aload_0        
        //   390: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   393: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //   396: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;II)V
        //   399: astore_3       
        //   400: invokestatic    net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   403: istore          4
        //   405: invokestatic    net/minecraft/client/gui/ScaledResolution.getScaledHeight:()I
        //   408: istore          5
        //   410: invokestatic    org/lwjgl/input/Mouse.getX:()I
        //   413: iload           4
        //   415: imul           
        //   416: aload_0        
        //   417: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   420: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //   423: idiv           
        //   424: istore          6
        //   426: iload           5
        //   428: invokestatic    org/lwjgl/input/Mouse.getY:()I
        //   431: iload           5
        //   433: imul           
        //   434: aload_0        
        //   435: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   438: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //   441: idiv           
        //   442: isub           
        //   443: iconst_1       
        //   444: isub           
        //   445: istore          7
        //   447: aload_0        
        //   448: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   451: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   454: getfield        net/minecraft/client/settings/GameSettings.limitFramerate:I
        //   457: istore          8
        //   459: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   462: ifnull          672
        //   465: aload_0        
        //   466: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   469: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   472: ldc_w           "level"
        //   475: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   478: invokestatic    net/minecraft/client/Minecraft.func_175610_ah:()I
        //   481: bipush          30
        //   483: invokestatic    java/lang/Math.max:(II)I
        //   486: istore          9
        //   488: aload_0        
        //   489: fload_1        
        //   490: aload_0        
        //   491: getfield        net/minecraft/client/renderer/EntityRenderer.renderEndNanoTime:J
        //   494: ldc_w           1000000000
        //   497: iload           9
        //   499: idiv           
        //   500: i2l            
        //   501: ladd           
        //   502: invokevirtual   net/minecraft/client/renderer/EntityRenderer.renderWorld:(FJ)V
        //   505: getstatic       net/minecraft/client/renderer/OpenGlHelper.shadersSupported:Z
        //   508: ifeq            560
        //   511: aload_0        
        //   512: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   515: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //   518: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174975_c:()V
        //   521: aload_0        
        //   522: getfield        net/minecraft/client/renderer/EntityRenderer.theShaderGroup:Lnet/minecraft/client/shader/ShaderGroup;
        //   525: ifnull          549
        //   528: aload_0        
        //   529: getfield        net/minecraft/client/renderer/EntityRenderer.field_175083_ad:Z
        //   532: ifeq            549
        //   535: sipush          5890
        //   538: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   541: aload_0        
        //   542: getfield        net/minecraft/client/renderer/EntityRenderer.theShaderGroup:Lnet/minecraft/client/shader/ShaderGroup;
        //   545: fload_1        
        //   546: invokevirtual   net/minecraft/client/shader/ShaderGroup.loadShaderGroup:(F)V
        //   549: aload_0        
        //   550: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   553: invokevirtual   net/minecraft/client/Minecraft.getFramebuffer:()Lnet/minecraft/client/shader/Framebuffer;
        //   556: iconst_1       
        //   557: invokevirtual   net/minecraft/client/shader/Framebuffer.bindFramebuffer:(Z)V
        //   560: aload_0        
        //   561: invokestatic    java/lang/System.nanoTime:()J
        //   564: putfield        net/minecraft/client/renderer/EntityRenderer.renderEndNanoTime:J
        //   567: aload_0        
        //   568: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   571: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   574: ldc_w           "gui"
        //   577: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   580: aload_0        
        //   581: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   584: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   587: getfield        net/minecraft/client/settings/GameSettings.hideGUI:Z
        //   590: ifeq            603
        //   593: aload_0        
        //   594: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   597: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   600: ifnull          659
        //   603: sipush          516
        //   606: ldc_w           0.1
        //   609: invokestatic    net/minecraft/client/renderer/GlStateManager.alphaFunc:(IF)V
        //   612: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   615: fload_1        
        //   616: invokevirtual   net/minecraft/client/gui/GuiIngame.func_175180_a:(F)V
        //   619: aload_0        
        //   620: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   623: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   626: getfield        net/minecraft/client/settings/GameSettings.ofShowFps:Z
        //   629: ifeq            642
        //   632: aload_0        
        //   633: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   636: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   639: getfield        net/minecraft/client/settings/GameSettings.showDebugInfo:Z
        //   642: aload_0        
        //   643: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   646: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   649: getfield        net/minecraft/client/settings/GameSettings.showDebugInfo:Z
        //   652: ifeq            659
        //   655: aload_3        
        //   656: invokestatic    optifine/Lagometer.showLagometer:(Lnet/minecraft/client/gui/ScaledResolution;)V
        //   659: aload_0        
        //   660: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   663: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   666: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   669: goto            714
        //   672: iconst_0       
        //   673: iconst_0       
        //   674: aload_0        
        //   675: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   678: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //   681: aload_0        
        //   682: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   685: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //   688: invokestatic    net/minecraft/client/renderer/GlStateManager.viewport:(IIII)V
        //   691: sipush          5889
        //   694: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   697: sipush          5888
        //   700: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   703: aload_0        
        //   704: invokevirtual   net/minecraft/client/renderer/EntityRenderer.setupOverlayRendering:()V
        //   707: aload_0        
        //   708: invokestatic    java/lang/System.nanoTime:()J
        //   711: putfield        net/minecraft/client/renderer/EntityRenderer.renderEndNanoTime:J
        //   714: aload_0        
        //   715: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   718: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   721: ifnull          885
        //   724: sipush          256
        //   727: invokestatic    net/minecraft/client/renderer/GlStateManager.clear:(I)V
        //   730: getstatic       optifine/Reflector.ForgeHooksClient_drawScreen:Loptifine/ReflectorMethod;
        //   733: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   736: ifeq            784
        //   739: getstatic       optifine/Reflector.ForgeHooksClient_drawScreen:Loptifine/ReflectorMethod;
        //   742: iconst_4       
        //   743: anewarray       Ljava/lang/Object;
        //   746: dup            
        //   747: iconst_0       
        //   748: aload_0        
        //   749: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   752: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   755: aastore        
        //   756: dup            
        //   757: iconst_1       
        //   758: iload           6
        //   760: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   763: aastore        
        //   764: dup            
        //   765: iconst_2       
        //   766: iconst_m1      
        //   767: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   770: aastore        
        //   771: dup            
        //   772: iconst_3       
        //   773: fload_1        
        //   774: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   777: aastore        
        //   778: invokestatic    optifine/Reflector.callVoid:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //   781: goto            885
        //   784: aload_0        
        //   785: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   788: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   791: iload           6
        //   793: iconst_m1      
        //   794: fload_1        
        //   795: invokevirtual   net/minecraft/client/gui/GuiScreen.drawScreen:(IIF)V
        //   798: goto            885
        //   801: astore          9
        //   803: aload           9
        //   805: ldc_w           "Rendering screen"
        //   808: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   811: astore          10
        //   813: aload           10
        //   815: ldc_w           "Screen render details"
        //   818: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   821: astore          11
        //   823: aload           11
        //   825: ldc_w           "Screen name"
        //   828: new             Lnet/minecraft/client/renderer/EntityRenderer$1;
        //   831: dup            
        //   832: aload_0        
        //   833: invokespecial   net/minecraft/client/renderer/EntityRenderer$1.<init>:(Lnet/minecraft/client/renderer/EntityRenderer;)V
        //   836: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSectionCallable:(Ljava/lang/String;Ljava/util/concurrent/Callable;)V
        //   839: aload           11
        //   841: ldc_w           "Mouse location"
        //   844: new             Lnet/minecraft/client/renderer/EntityRenderer$2;
        //   847: dup            
        //   848: aload_0        
        //   849: iload           6
        //   851: iconst_m1      
        //   852: invokespecial   net/minecraft/client/renderer/EntityRenderer$2.<init>:(Lnet/minecraft/client/renderer/EntityRenderer;II)V
        //   855: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSectionCallable:(Ljava/lang/String;Ljava/util/concurrent/Callable;)V
        //   858: aload           11
        //   860: ldc_w           "Screen size"
        //   863: new             Lnet/minecraft/client/renderer/EntityRenderer$3;
        //   866: dup            
        //   867: aload_0        
        //   868: aload_3        
        //   869: invokespecial   net/minecraft/client/renderer/EntityRenderer$3.<init>:(Lnet/minecraft/client/renderer/EntityRenderer;Lnet/minecraft/client/gui/ScaledResolution;)V
        //   872: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSectionCallable:(Ljava/lang/String;Ljava/util/concurrent/Callable;)V
        //   875: new             Lnet/minecraft/util/ReportedException;
        //   878: dup            
        //   879: aload           10
        //   881: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   884: athrow         
        //   885: aload_0        
        //   886: invokespecial   net/minecraft/client/renderer/EntityRenderer.frameFinish:()V
        //   889: aload_0        
        //   890: invokespecial   net/minecraft/client/renderer/EntityRenderer.waitForServerThread:()V
        //   893: aload_0        
        //   894: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   897: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   900: getfield        net/minecraft/client/settings/GameSettings.ofProfiler:Z
        //   903: ifeq            917
        //   906: aload_0        
        //   907: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   910: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   913: iconst_1       
        //   914: putfield        net/minecraft/client/settings/GameSettings.showDebugProfilerChart:Z
        //   917: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0642 (coming from #0639).
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
    
    public void func_152430_c(final float n) {
        this.setupOverlayRendering();
        Minecraft.ingameGUI.func_180478_c(new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight));
    }
    
    private boolean func_175070_n() {
        if (!this.field_175073_D) {
            return false;
        }
        final Entity func_175606_aa = this.mc.func_175606_aa();
        boolean b = func_175606_aa instanceof EntityPlayer && !this.mc.gameSettings.hideGUI;
        if (b && !((EntityPlayer)func_175606_aa).capabilities.allowEdit) {
            final ItemStack currentEquippedItem = ((EntityPlayer)func_175606_aa).getCurrentEquippedItem();
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos func_178782_a = this.mc.objectMouseOver.func_178782_a();
                final IBlockState blockState = Minecraft.theWorld.getBlockState(func_178782_a);
                final Block block = blockState.getBlock();
                if (Minecraft.playerController.func_178889_l() == WorldSettings.GameType.SPECTATOR) {
                    b = (ReflectorForge.blockHasTileEntity(blockState) && Minecraft.theWorld.getTileEntity(func_178782_a) instanceof IInventory);
                }
                else {
                    b = (currentEquippedItem != null && (currentEquippedItem.canDestroy(block) || currentEquippedItem.canPlaceOn(block)));
                }
            }
        }
        return b;
    }
    
    private void func_175067_i(final float n) {
        if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !Minecraft.thePlayer.func_175140_cp() && !this.mc.gameSettings.field_178879_v) {
            final Entity func_175606_aa = this.mc.func_175606_aa();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glLineWidth(1.0f);
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5888);
            this.orientCamera(n);
            GlStateManager.translate(0.0f, func_175606_aa.getEyeHeight(), 0.0f);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), -65536);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), -16776961);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), -16711936);
            GlStateManager.depthMask(true);
        }
    }
    
    public void renderWorld(final float n, final long n2) {
        this.updateLightmap(n);
        if (this.mc.func_175606_aa() == null) {
            this.mc.func_175607_a(Minecraft.thePlayer);
        }
        this.getMouseOver(n);
        if (Config.isShaders()) {
            Shaders.beginRender(this.mc, n, n2);
        }
        GlStateManager.alphaFunc(516, 0.1f);
        this.mc.mcProfiler.startSection("center");
        if (this.mc.gameSettings.anaglyph) {
            EntityRenderer.anaglyphField = 0;
            GlStateManager.colorMask(false, true, true, false);
            this.func_175068_a(0, n, n2);
            EntityRenderer.anaglyphField = 1;
            GlStateManager.colorMask(true, false, false, false);
            this.func_175068_a(1, n, n2);
            GlStateManager.colorMask(true, true, true, false);
        }
        else {
            this.func_175068_a(2, n, n2);
        }
        this.mc.mcProfiler.endSection();
    }
    
    private void func_175068_a(final int p0, final float p1, final long p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore          5
        //     5: iload           5
        //     7: ifeq            16
        //    10: iload_1        
        //    11: fload_2        
        //    12: lload_3        
        //    13: invokestatic    shadersmod/client/Shaders.beginRenderPass:(IFJ)V
        //    16: aload_0        
        //    17: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    20: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //    23: astore          6
        //    25: aload_0        
        //    26: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    29: getfield        net/minecraft/client/Minecraft.effectRenderer:Lnet/minecraft/client/particle/EffectRenderer;
        //    32: astore          7
        //    34: aload_0        
        //    35: invokespecial   net/minecraft/client/renderer/EntityRenderer.func_175070_n:()Z
        //    38: istore          8
        //    40: aload_0        
        //    41: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    44: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    47: ldc_w           "clear"
        //    50: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //    53: iload           5
        //    55: ifeq            80
        //    58: iconst_0       
        //    59: iconst_0       
        //    60: aload_0        
        //    61: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    64: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //    67: aload_0        
        //    68: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    71: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //    74: invokestatic    shadersmod/client/Shaders.setViewport:(IIII)V
        //    77: goto            99
        //    80: iconst_0       
        //    81: iconst_0       
        //    82: aload_0        
        //    83: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    86: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //    89: aload_0        
        //    90: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    93: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //    96: invokestatic    net/minecraft/client/renderer/GlStateManager.viewport:(IIII)V
        //    99: aload_0        
        //   100: fload_2        
        //   101: invokespecial   net/minecraft/client/renderer/EntityRenderer.updateFogColor:(F)V
        //   104: sipush          16640
        //   107: invokestatic    net/minecraft/client/renderer/GlStateManager.clear:(I)V
        //   110: iload           5
        //   112: aload_0        
        //   113: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   116: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   119: ldc_w           "camera"
        //   122: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   125: aload_0        
        //   126: fload_2        
        //   127: iload_1        
        //   128: invokevirtual   net/minecraft/client/renderer/EntityRenderer.setupCameraTransform:(FI)V
        //   131: iload           5
        //   133: ifeq            140
        //   136: fload_2        
        //   137: invokestatic    shadersmod/client/Shaders.setCamera:(F)V
        //   140: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   143: aload_0        
        //   144: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   147: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   150: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //   153: iconst_2       
        //   154: if_icmpne       161
        //   157: iconst_1       
        //   158: goto            162
        //   161: iconst_0       
        //   162: invokestatic    net/minecraft/client/renderer/ActiveRenderInfo.updateRenderInfo:(Lnet/minecraft/entity/player/EntityPlayer;Z)V
        //   165: aload_0        
        //   166: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   169: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   172: ldc_w           "frustum"
        //   175: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   178: invokestatic    net/minecraft/client/renderer/culling/ClippingHelperImpl.getInstance:()Lnet/minecraft/client/renderer/culling/ClippingHelper;
        //   181: pop            
        //   182: aload_0        
        //   183: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   186: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   189: ldc_w           "culling"
        //   192: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   195: new             Lnet/minecraft/client/renderer/culling/Frustrum;
        //   198: dup            
        //   199: invokespecial   net/minecraft/client/renderer/culling/Frustrum.<init>:()V
        //   202: astore          9
        //   204: aload_0        
        //   205: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   208: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //   211: astore          10
        //   213: aload           10
        //   215: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   218: aload           10
        //   220: getfield        net/minecraft/entity/Entity.posX:D
        //   223: aload           10
        //   225: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   228: dsub           
        //   229: fload_2        
        //   230: f2d            
        //   231: dmul           
        //   232: dadd           
        //   233: dstore          11
        //   235: aload           10
        //   237: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   240: aload           10
        //   242: getfield        net/minecraft/entity/Entity.posY:D
        //   245: aload           10
        //   247: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   250: dsub           
        //   251: fload_2        
        //   252: f2d            
        //   253: dmul           
        //   254: dadd           
        //   255: dstore          13
        //   257: aload           10
        //   259: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   262: aload           10
        //   264: getfield        net/minecraft/entity/Entity.posZ:D
        //   267: aload           10
        //   269: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   272: dsub           
        //   273: fload_2        
        //   274: f2d            
        //   275: dmul           
        //   276: dadd           
        //   277: dstore          15
        //   279: iload           5
        //   281: ifeq            298
        //   284: aload           9
        //   286: dload           11
        //   288: dload           13
        //   290: dload           15
        //   292: invokestatic    shadersmod/client/ShadersRender.setFrustrumPosition:(Lnet/minecraft/client/renderer/culling/Frustrum;DDD)V
        //   295: goto            309
        //   298: aload           9
        //   300: dload           11
        //   302: dload           13
        //   304: dload           15
        //   306: invokevirtual   net/minecraft/client/renderer/culling/Frustrum.setPosition:(DDD)V
        //   309: invokestatic    optifine/Config.isSkyEnabled:()Z
        //   312: ifne            327
        //   315: invokestatic    optifine/Config.isSunMoonEnabled:()Z
        //   318: ifne            327
        //   321: invokestatic    optifine/Config.isStarsEnabled:()Z
        //   324: ifeq            456
        //   327: getstatic       shadersmod/client/Shaders.isShadowPass:Z
        //   330: ifne            456
        //   333: aload_0        
        //   334: iconst_m1      
        //   335: fload_2        
        //   336: invokespecial   net/minecraft/client/renderer/EntityRenderer.setupFog:(IF)V
        //   339: aload_0        
        //   340: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   343: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   346: ldc_w           "sky"
        //   349: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   352: sipush          5889
        //   355: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   358: aload_0        
        //   359: fload_2        
        //   360: iconst_1       
        //   361: invokespecial   net/minecraft/client/renderer/EntityRenderer.getFOVModifier:(FZ)F
        //   364: aload_0        
        //   365: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   368: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //   371: i2f            
        //   372: aload_0        
        //   373: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   376: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //   379: i2f            
        //   380: fdiv           
        //   381: ldc_w           0.05
        //   384: aload_0        
        //   385: getfield        net/minecraft/client/renderer/EntityRenderer.clipDistance:F
        //   388: invokestatic    org/lwjgl/util/glu/Project.gluPerspective:(FFFF)V
        //   391: sipush          5888
        //   394: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   397: iload           5
        //   399: aload           6
        //   401: fload_2        
        //   402: iload_1        
        //   403: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174976_a:(FI)V
        //   406: iload           5
        //   408: sipush          5889
        //   411: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   414: aload_0        
        //   415: fload_2        
        //   416: iconst_1       
        //   417: invokespecial   net/minecraft/client/renderer/EntityRenderer.getFOVModifier:(FZ)F
        //   420: aload_0        
        //   421: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   424: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //   427: i2f            
        //   428: aload_0        
        //   429: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   432: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //   435: i2f            
        //   436: fdiv           
        //   437: ldc_w           0.05
        //   440: aload_0        
        //   441: getfield        net/minecraft/client/renderer/EntityRenderer.clipDistance:F
        //   444: invokestatic    org/lwjgl/util/glu/Project.gluPerspective:(FFFF)V
        //   447: sipush          5888
        //   450: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   453: goto            456
        //   456: aload_0        
        //   457: iconst_0       
        //   458: fload_2        
        //   459: invokespecial   net/minecraft/client/renderer/EntityRenderer.setupFog:(IF)V
        //   462: sipush          7425
        //   465: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //   468: aload           10
        //   470: getfield        net/minecraft/entity/Entity.posY:D
        //   473: aload           10
        //   475: invokevirtual   net/minecraft/entity/Entity.getEyeHeight:()F
        //   478: f2d            
        //   479: dadd           
        //   480: ldc2_w          128.0
        //   483: aload_0        
        //   484: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   487: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   490: getfield        net/minecraft/client/settings/GameSettings.ofCloudsHeight:F
        //   493: ldc_w           128.0
        //   496: fmul           
        //   497: f2d            
        //   498: dadd           
        //   499: dcmpg          
        //   500: ifge            511
        //   503: aload_0        
        //   504: aload           6
        //   506: fload_2        
        //   507: iload_1        
        //   508: invokespecial   net/minecraft/client/renderer/EntityRenderer.func_180437_a:(Lnet/minecraft/client/renderer/RenderGlobal;FI)V
        //   511: aload_0        
        //   512: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   515: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   518: ldc_w           "prepareterrain"
        //   521: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   524: aload_0        
        //   525: iconst_0       
        //   526: fload_2        
        //   527: invokespecial   net/minecraft/client/renderer/EntityRenderer.setupFog:(IF)V
        //   530: aload_0        
        //   531: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   534: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //   537: getstatic       net/minecraft/client/renderer/texture/TextureMap.locationBlocksTexture:Lnet/minecraft/util/ResourceLocation;
        //   540: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   543: aload_0        
        //   544: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   547: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   550: ldc_w           "terrain_setup"
        //   553: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   556: iload           5
        //   558: ifeq            592
        //   561: aload           6
        //   563: aload           10
        //   565: fload_2        
        //   566: f2d            
        //   567: aload           9
        //   569: aload_0        
        //   570: dup            
        //   571: getfield        net/minecraft/client/renderer/EntityRenderer.field_175084_ae:I
        //   574: dup_x1         
        //   575: iconst_1       
        //   576: iadd           
        //   577: putfield        net/minecraft/client/renderer/EntityRenderer.field_175084_ae:I
        //   580: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   583: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_175149_v:()Z
        //   586: invokestatic    shadersmod/client/ShadersRender.setupTerrain:(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/entity/Entity;DLnet/minecraft/client/renderer/culling/ICamera;IZ)V
        //   589: goto            620
        //   592: aload           6
        //   594: aload           10
        //   596: fload_2        
        //   597: f2d            
        //   598: aload           9
        //   600: aload_0        
        //   601: dup            
        //   602: getfield        net/minecraft/client/renderer/EntityRenderer.field_175084_ae:I
        //   605: dup_x1         
        //   606: iconst_1       
        //   607: iadd           
        //   608: putfield        net/minecraft/client/renderer/EntityRenderer.field_175084_ae:I
        //   611: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   614: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_175149_v:()Z
        //   617: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174970_a:(Lnet/minecraft/entity/Entity;DLnet/minecraft/client/renderer/culling/ICamera;IZ)V
        //   620: iload_1        
        //   621: ifeq            629
        //   624: iload_1        
        //   625: iconst_2       
        //   626: if_icmpne       679
        //   629: aload_0        
        //   630: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   633: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   636: ldc_w           "updatechunks"
        //   639: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   642: getstatic       optifine/Lagometer.timerChunkUpload:Loptifine/Lagometer$TimerNano;
        //   645: invokevirtual   optifine/Lagometer$TimerNano.start:()V
        //   648: iload           5
        //   650: ifeq            662
        //   653: aload           6
        //   655: lload_3        
        //   656: invokestatic    shadersmod/client/ShadersRender.updateChunks:(Lnet/minecraft/client/renderer/RenderGlobal;J)V
        //   659: goto            673
        //   662: aload_0        
        //   663: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   666: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //   669: lload_3        
        //   670: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174967_a:(J)V
        //   673: getstatic       optifine/Lagometer.timerChunkUpload:Loptifine/Lagometer$TimerNano;
        //   676: invokevirtual   optifine/Lagometer$TimerNano.end:()V
        //   679: aload_0        
        //   680: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   683: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   686: ldc_w           "terrain"
        //   689: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   692: getstatic       optifine/Lagometer.timerTerrain:Loptifine/Lagometer$TimerNano;
        //   695: invokevirtual   optifine/Lagometer$TimerNano.start:()V
        //   698: aload_0        
        //   699: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   702: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   705: getfield        net/minecraft/client/settings/GameSettings.ofSmoothFps:Z
        //   708: ifeq            741
        //   711: iload_1        
        //   712: ifle            741
        //   715: aload_0        
        //   716: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   719: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   722: ldc_w           "finish"
        //   725: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   728: aload_0        
        //   729: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   732: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   735: ldc_w           "terrain"
        //   738: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   741: sipush          5888
        //   744: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   747: iload           5
        //   749: aload           6
        //   751: getstatic       net/minecraft/util/EnumWorldBlockLayer.SOLID:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   754: fload_2        
        //   755: f2d            
        //   756: iload_1        
        //   757: aload           10
        //   759: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174977_a:(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I
        //   762: pop            
        //   763: iload           5
        //   765: aload           6
        //   767: getstatic       net/minecraft/util/EnumWorldBlockLayer.CUTOUT_MIPPED:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   770: fload_2        
        //   771: f2d            
        //   772: iload_1        
        //   773: aload           10
        //   775: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174977_a:(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I
        //   778: pop            
        //   779: aload_0        
        //   780: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   783: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //   786: getstatic       net/minecraft/client/renderer/texture/TextureMap.locationBlocksTexture:Lnet/minecraft/util/ResourceLocation;
        //   789: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.getTexture:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/renderer/texture/ITextureObject;
        //   792: iconst_0       
        //   793: iconst_0       
        //   794: invokeinterface net/minecraft/client/renderer/texture/ITextureObject.func_174936_b:(ZZ)V
        //   799: iload           5
        //   801: aload           6
        //   803: getstatic       net/minecraft/util/EnumWorldBlockLayer.CUTOUT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   806: fload_2        
        //   807: f2d            
        //   808: iload_1        
        //   809: aload           10
        //   811: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174977_a:(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I
        //   814: pop            
        //   815: aload_0        
        //   816: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   819: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //   822: getstatic       net/minecraft/client/renderer/texture/TextureMap.locationBlocksTexture:Lnet/minecraft/util/ResourceLocation;
        //   825: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.getTexture:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/renderer/texture/ITextureObject;
        //   828: invokeinterface net/minecraft/client/renderer/texture/ITextureObject.func_174935_a:()V
        //   833: iload           5
        //   835: getstatic       optifine/Lagometer.timerTerrain:Loptifine/Lagometer$TimerNano;
        //   838: invokevirtual   optifine/Lagometer$TimerNano.end:()V
        //   841: sipush          7424
        //   844: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //   847: sipush          516
        //   850: ldc_w           0.1
        //   853: invokestatic    net/minecraft/client/renderer/GlStateManager.alphaFunc:(IF)V
        //   856: aload_0        
        //   857: getfield        net/minecraft/client/renderer/EntityRenderer.field_175078_W:Z
        //   860: ifne            1094
        //   863: sipush          5888
        //   866: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   869: aload_0        
        //   870: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   873: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   876: ldc_w           "entities"
        //   879: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   882: getstatic       optifine/Reflector.ForgeHooksClient_setRenderPass:Loptifine/ReflectorMethod;
        //   885: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   888: ifeq            908
        //   891: getstatic       optifine/Reflector.ForgeHooksClient_setRenderPass:Loptifine/ReflectorMethod;
        //   894: iconst_1       
        //   895: anewarray       Ljava/lang/Object;
        //   898: dup            
        //   899: iconst_0       
        //   900: iconst_0       
        //   901: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   904: aastore        
        //   905: invokestatic    optifine/Reflector.callVoid:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //   908: aload           6
        //   910: aload           10
        //   912: aload           9
        //   914: fload_2        
        //   915: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_180446_a:(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;F)V
        //   918: getstatic       optifine/Reflector.ForgeHooksClient_setRenderPass:Loptifine/ReflectorMethod;
        //   921: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   924: ifeq            944
        //   927: getstatic       optifine/Reflector.ForgeHooksClient_setRenderPass:Loptifine/ReflectorMethod;
        //   930: iconst_1       
        //   931: anewarray       Ljava/lang/Object;
        //   934: dup            
        //   935: iconst_0       
        //   936: iconst_m1      
        //   937: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   940: aastore        
        //   941: invokestatic    optifine/Reflector.callVoid:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //   944: aload_0        
        //   945: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_175072_h:()V
        //   948: sipush          5888
        //   951: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   954: aload_0        
        //   955: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   958: getfield        net/minecraft/client/Minecraft.objectMouseOver:Lnet/minecraft/util/MovingObjectPosition;
        //   961: ifnull          1094
        //   964: aload           10
        //   966: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //   969: invokevirtual   net/minecraft/entity/Entity.isInsideOfMaterial:(Lnet/minecraft/block/material/Material;)Z
        //   972: ifeq            1094
        //   975: iload           8
        //   977: ifeq            1094
        //   980: aload           10
        //   982: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   985: astore          17
        //   987: aload_0        
        //   988: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   991: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   994: ldc_w           "outline"
        //   997: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1000: getstatic       optifine/Reflector.ForgeHooksClient_onDrawBlockHighlight:Loptifine/ReflectorMethod;
        //  1003: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //  1006: ifeq            1065
        //  1009: getstatic       optifine/Reflector.ForgeHooksClient_onDrawBlockHighlight:Loptifine/ReflectorMethod;
        //  1012: bipush          6
        //  1014: anewarray       Ljava/lang/Object;
        //  1017: dup            
        //  1018: iconst_0       
        //  1019: aload           6
        //  1021: aastore        
        //  1022: dup            
        //  1023: iconst_1       
        //  1024: aload           17
        //  1026: aastore        
        //  1027: dup            
        //  1028: iconst_2       
        //  1029: aload_0        
        //  1030: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1033: getfield        net/minecraft/client/Minecraft.objectMouseOver:Lnet/minecraft/util/MovingObjectPosition;
        //  1036: aastore        
        //  1037: dup            
        //  1038: iconst_3       
        //  1039: iconst_0       
        //  1040: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1043: aastore        
        //  1044: dup            
        //  1045: iconst_4       
        //  1046: aload           17
        //  1048: invokevirtual   net/minecraft/entity/player/EntityPlayer.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //  1051: aastore        
        //  1052: dup            
        //  1053: iconst_5       
        //  1054: fload_2        
        //  1055: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //  1058: aastore        
        //  1059: invokestatic    optifine/Reflector.callBoolean:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1062: ifne            1094
        //  1065: aload_0        
        //  1066: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1069: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1072: getfield        net/minecraft/client/settings/GameSettings.hideGUI:Z
        //  1075: ifne            1094
        //  1078: aload           6
        //  1080: aload           17
        //  1082: aload_0        
        //  1083: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1086: getfield        net/minecraft/client/Minecraft.objectMouseOver:Lnet/minecraft/util/MovingObjectPosition;
        //  1089: iconst_0       
        //  1090: fload_2        
        //  1091: invokevirtual   net/minecraft/client/renderer/RenderGlobal.drawSelectionBox:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/MovingObjectPosition;IF)V
        //  1094: sipush          5888
        //  1097: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //  1100: iload           8
        //  1102: ifeq            1240
        //  1105: aload_0        
        //  1106: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1109: getfield        net/minecraft/client/Minecraft.objectMouseOver:Lnet/minecraft/util/MovingObjectPosition;
        //  1112: ifnull          1240
        //  1115: aload           10
        //  1117: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //  1120: invokevirtual   net/minecraft/entity/Entity.isInsideOfMaterial:(Lnet/minecraft/block/material/Material;)Z
        //  1123: ifne            1240
        //  1126: aload           10
        //  1128: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //  1131: astore          17
        //  1133: aload_0        
        //  1134: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1137: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1140: ldc_w           "outline"
        //  1143: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1146: getstatic       optifine/Reflector.ForgeHooksClient_onDrawBlockHighlight:Loptifine/ReflectorMethod;
        //  1149: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //  1152: ifeq            1211
        //  1155: getstatic       optifine/Reflector.ForgeHooksClient_onDrawBlockHighlight:Loptifine/ReflectorMethod;
        //  1158: bipush          6
        //  1160: anewarray       Ljava/lang/Object;
        //  1163: dup            
        //  1164: iconst_0       
        //  1165: aload           6
        //  1167: aastore        
        //  1168: dup            
        //  1169: iconst_1       
        //  1170: aload           17
        //  1172: aastore        
        //  1173: dup            
        //  1174: iconst_2       
        //  1175: aload_0        
        //  1176: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1179: getfield        net/minecraft/client/Minecraft.objectMouseOver:Lnet/minecraft/util/MovingObjectPosition;
        //  1182: aastore        
        //  1183: dup            
        //  1184: iconst_3       
        //  1185: iconst_0       
        //  1186: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1189: aastore        
        //  1190: dup            
        //  1191: iconst_4       
        //  1192: aload           17
        //  1194: invokevirtual   net/minecraft/entity/player/EntityPlayer.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //  1197: aastore        
        //  1198: dup            
        //  1199: iconst_5       
        //  1200: fload_2        
        //  1201: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //  1204: aastore        
        //  1205: invokestatic    optifine/Reflector.callBoolean:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1208: ifne            1240
        //  1211: aload_0        
        //  1212: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1215: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1218: getfield        net/minecraft/client/settings/GameSettings.hideGUI:Z
        //  1221: ifne            1240
        //  1224: aload           6
        //  1226: aload           17
        //  1228: aload_0        
        //  1229: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1232: getfield        net/minecraft/client/Minecraft.objectMouseOver:Lnet/minecraft/util/MovingObjectPosition;
        //  1235: iconst_0       
        //  1236: fload_2        
        //  1237: invokevirtual   net/minecraft/client/renderer/RenderGlobal.drawSelectionBox:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/MovingObjectPosition;IF)V
        //  1240: aload           6
        //  1242: getfield        net/minecraft/client/renderer/RenderGlobal.damagedBlocks:Ljava/util/Map;
        //  1245: invokeinterface java/util/Map.isEmpty:()Z
        //  1250: ifne            1292
        //  1253: aload_0        
        //  1254: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1257: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1260: ldc_w           "destroyProgress"
        //  1263: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1266: sipush          770
        //  1269: iconst_1       
        //  1270: iconst_1       
        //  1271: iconst_0       
        //  1272: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //  1275: aload           6
        //  1277: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //  1280: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //  1283: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //  1286: aload           10
        //  1288: fload_2        
        //  1289: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174981_a:(Lnet/minecraft/client/renderer/Tessellator;Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;F)V
        //  1292: sipush          770
        //  1295: sipush          771
        //  1298: iconst_1       
        //  1299: iconst_0       
        //  1300: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //  1303: aload_0        
        //  1304: getfield        net/minecraft/client/renderer/EntityRenderer.field_175078_W:Z
        //  1307: ifne            1372
        //  1310: aload_0        
        //  1311: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_180436_i:()V
        //  1314: aload_0        
        //  1315: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1318: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1321: ldc_w           "litParticles"
        //  1324: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1327: iload           5
        //  1329: aload           7
        //  1331: aload           10
        //  1333: fload_2        
        //  1334: invokevirtual   net/minecraft/client/particle/EffectRenderer.renderLitParticles:(Lnet/minecraft/entity/Entity;F)V
        //  1337: aload_0        
        //  1338: iconst_0       
        //  1339: fload_2        
        //  1340: invokespecial   net/minecraft/client/renderer/EntityRenderer.setupFog:(IF)V
        //  1343: aload_0        
        //  1344: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1347: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1350: ldc_w           "particles"
        //  1353: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1356: iload           5
        //  1358: aload           7
        //  1360: aload           10
        //  1362: fload_2        
        //  1363: invokevirtual   net/minecraft/client/particle/EffectRenderer.renderParticles:(Lnet/minecraft/entity/Entity;F)V
        //  1366: iload           5
        //  1368: aload_0        
        //  1369: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_175072_h:()V
        //  1372: iconst_0       
        //  1373: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //  1376: aload_0        
        //  1377: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1380: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1383: ldc_w           "weather"
        //  1386: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1389: iload           5
        //  1391: aload_0        
        //  1392: fload_2        
        //  1393: invokevirtual   net/minecraft/client/renderer/EntityRenderer.renderRainSnow:(F)V
        //  1396: iload           5
        //  1398: iconst_1       
        //  1399: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //  1402: aload           6
        //  1404: aload           10
        //  1406: fload_2        
        //  1407: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_180449_a:(Lnet/minecraft/entity/Entity;F)V
        //  1410: iload           5
        //  1412: ifeq            1421
        //  1415: aload_0        
        //  1416: fload_2        
        //  1417: iload_1        
        //  1418: invokestatic    shadersmod/client/ShadersRender.renderHand0:(Lnet/minecraft/client/renderer/EntityRenderer;FI)V
        //  1421: sipush          770
        //  1424: sipush          771
        //  1427: iconst_1       
        //  1428: iconst_0       
        //  1429: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //  1432: sipush          516
        //  1435: ldc_w           0.1
        //  1438: invokestatic    net/minecraft/client/renderer/GlStateManager.alphaFunc:(IF)V
        //  1441: aload_0        
        //  1442: iconst_0       
        //  1443: fload_2        
        //  1444: invokespecial   net/minecraft/client/renderer/EntityRenderer.setupFog:(IF)V
        //  1447: iconst_0       
        //  1448: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //  1451: aload_0        
        //  1452: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1455: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //  1458: getstatic       net/minecraft/client/renderer/texture/TextureMap.locationBlocksTexture:Lnet/minecraft/util/ResourceLocation;
        //  1461: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //  1464: sipush          7425
        //  1467: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //  1470: invokestatic    optifine/Config.isTranslucentBlocksFancy:()Z
        //  1473: ifeq            1521
        //  1476: aload_0        
        //  1477: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1480: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1483: ldc_w           "translucent"
        //  1486: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1489: sipush          770
        //  1492: sipush          771
        //  1495: iconst_1       
        //  1496: iconst_0       
        //  1497: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //  1500: iload           5
        //  1502: aload           6
        //  1504: getstatic       net/minecraft/util/EnumWorldBlockLayer.TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //  1507: fload_2        
        //  1508: f2d            
        //  1509: iload_1        
        //  1510: aload           10
        //  1512: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174977_a:(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I
        //  1515: pop            
        //  1516: iload           5
        //  1518: goto            1552
        //  1521: aload_0        
        //  1522: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1525: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1528: ldc_w           "translucent"
        //  1531: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1534: iload           5
        //  1536: aload           6
        //  1538: getstatic       net/minecraft/util/EnumWorldBlockLayer.TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //  1541: fload_2        
        //  1542: f2d            
        //  1543: iload_1        
        //  1544: aload           10
        //  1546: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174977_a:(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I
        //  1549: pop            
        //  1550: iload           5
        //  1552: getstatic       optifine/Reflector.ForgeHooksClient_setRenderPass:Loptifine/ReflectorMethod;
        //  1555: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //  1558: ifeq            1641
        //  1561: aload_0        
        //  1562: getfield        net/minecraft/client/renderer/EntityRenderer.field_175078_W:Z
        //  1565: ifne            1641
        //  1568: aload_0        
        //  1569: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1572: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1575: ldc_w           "entities"
        //  1578: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1581: getstatic       optifine/Reflector.ForgeHooksClient_setRenderPass:Loptifine/ReflectorMethod;
        //  1584: iconst_1       
        //  1585: anewarray       Ljava/lang/Object;
        //  1588: dup            
        //  1589: iconst_0       
        //  1590: iconst_1       
        //  1591: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1594: aastore        
        //  1595: invokestatic    optifine/Reflector.callVoid:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //  1598: aload_0        
        //  1599: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1602: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //  1605: aload           10
        //  1607: aload           9
        //  1609: fload_2        
        //  1610: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_180446_a:(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;F)V
        //  1613: sipush          770
        //  1616: sipush          771
        //  1619: iconst_1       
        //  1620: iconst_0       
        //  1621: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //  1624: getstatic       optifine/Reflector.ForgeHooksClient_setRenderPass:Loptifine/ReflectorMethod;
        //  1627: iconst_1       
        //  1628: anewarray       Ljava/lang/Object;
        //  1631: dup            
        //  1632: iconst_0       
        //  1633: iconst_m1      
        //  1634: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1637: aastore        
        //  1638: invokestatic    optifine/Reflector.callVoid:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //  1641: sipush          7424
        //  1644: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //  1647: iconst_1       
        //  1648: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //  1651: aload           10
        //  1653: getfield        net/minecraft/entity/Entity.posY:D
        //  1656: aload           10
        //  1658: invokevirtual   net/minecraft/entity/Entity.getEyeHeight:()F
        //  1661: f2d            
        //  1662: dadd           
        //  1663: ldc2_w          128.0
        //  1666: aload_0        
        //  1667: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1670: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1673: getfield        net/minecraft/client/settings/GameSettings.ofCloudsHeight:F
        //  1676: ldc_w           128.0
        //  1679: fmul           
        //  1680: f2d            
        //  1681: dadd           
        //  1682: dcmpl          
        //  1683: iflt            1707
        //  1686: aload_0        
        //  1687: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1690: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1693: ldc_w           "aboveClouds"
        //  1696: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1699: aload_0        
        //  1700: aload           6
        //  1702: fload_2        
        //  1703: iload_1        
        //  1704: invokespecial   net/minecraft/client/renderer/EntityRenderer.func_180437_a:(Lnet/minecraft/client/renderer/RenderGlobal;FI)V
        //  1707: getstatic       optifine/Reflector.ForgeHooksClient_dispatchRenderLast:Loptifine/ReflectorMethod;
        //  1710: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //  1713: ifeq            1751
        //  1716: aload_0        
        //  1717: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1720: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1723: ldc_w           "forge_render_last"
        //  1726: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1729: getstatic       optifine/Reflector.ForgeHooksClient_dispatchRenderLast:Loptifine/ReflectorMethod;
        //  1732: iconst_2       
        //  1733: anewarray       Ljava/lang/Object;
        //  1736: dup            
        //  1737: iconst_0       
        //  1738: aload           6
        //  1740: aastore        
        //  1741: dup            
        //  1742: iconst_1       
        //  1743: fload_2        
        //  1744: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //  1747: aastore        
        //  1748: invokestatic    optifine/Reflector.callVoid:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)V
        //  1751: aload_0        
        //  1752: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1755: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1758: ldc_w           "hand"
        //  1761: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1764: getstatic       Mood/Client.theClient:LMood/Client;
        //  1767: pop            
        //  1768: getstatic       Mood/Client.modules:Ljava/util/concurrent/CopyOnWriteArrayList;
        //  1771: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.iterator:()Ljava/util/Iterator;
        //  1774: astore          19
        //  1776: goto            1796
        //  1779: aload           19
        //  1781: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1786: checkcast       LDTool/modules/Module;
        //  1789: astore          18
        //  1791: aload           18
        //  1793: invokevirtual   DTool/modules/Module.onRender:()V
        //  1796: aload           19
        //  1798: invokeinterface java/util/Iterator.hasNext:()Z
        //  1803: ifne            1779
        //  1806: getstatic       optifine/Reflector.ForgeHooksClient_renderFirstPersonHand:Loptifine/ReflectorMethod;
        //  1809: iconst_3       
        //  1810: anewarray       Ljava/lang/Object;
        //  1813: dup            
        //  1814: iconst_0       
        //  1815: aload_0        
        //  1816: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //  1819: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //  1822: aastore        
        //  1823: dup            
        //  1824: iconst_1       
        //  1825: fload_2        
        //  1826: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //  1829: aastore        
        //  1830: dup            
        //  1831: iconst_2       
        //  1832: iload_1        
        //  1833: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1836: aastore        
        //  1837: invokestatic    optifine/Reflector.callBoolean:(Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1840: istore          18
        //  1842: iload           18
        //  1844: ifne            1902
        //  1847: aload_0        
        //  1848: getfield        net/minecraft/client/renderer/EntityRenderer.field_175074_C:Z
        //  1851: ifeq            1902
        //  1854: getstatic       shadersmod/client/Shaders.isShadowPass:Z
        //  1857: ifne            1902
        //  1860: iload           5
        //  1862: ifeq            1871
        //  1865: aload_0        
        //  1866: fload_2        
        //  1867: iload_1        
        //  1868: invokestatic    shadersmod/client/ShadersRender.renderHand1:(Lnet/minecraft/client/renderer/EntityRenderer;FI)V
        //  1871: sipush          256
        //  1874: invokestatic    net/minecraft/client/renderer/GlStateManager.clear:(I)V
        //  1877: iload           5
        //  1879: ifeq            1891
        //  1882: aload_0        
        //  1883: fload_2        
        //  1884: iload_1        
        //  1885: invokestatic    shadersmod/client/ShadersRender.renderFPOverlay:(Lnet/minecraft/client/renderer/EntityRenderer;FI)V
        //  1888: goto            1897
        //  1891: aload_0        
        //  1892: fload_2        
        //  1893: iload_1        
        //  1894: invokevirtual   net/minecraft/client/renderer/EntityRenderer.renderHand:(FI)V
        //  1897: aload_0        
        //  1898: fload_2        
        //  1899: invokespecial   net/minecraft/client/renderer/EntityRenderer.func_175067_i:(F)V
        //  1902: iload           5
        //  1904: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #1372 (coming from #1369).
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
    
    private void func_180437_a(final RenderGlobal renderGlobal, final float n, final int n2) {
        final GameSettings gameSettings = this.mc.gameSettings;
        if (GameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
            this.mc.mcProfiler.endStartSection("clouds");
            GlStateManager.matrixMode(5889);
            Project.gluPerspective(this.getFOVModifier(n, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance * 4.0f);
            GlStateManager.matrixMode(5888);
            this.setupFog(0, n);
            renderGlobal.func_180447_b(n, n2);
            GlStateManager.matrixMode(5889);
            Project.gluPerspective(this.getFOVModifier(n, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(5888);
        }
    }
    
    private void addRainParticles() {
        float rainStrength = Minecraft.theWorld.getRainStrength(1.0f);
        if (!Config.isRainFancy()) {
            rainStrength /= 2.0f;
        }
        if (rainStrength != 0.0f && Config.isRainSplash()) {
            this.random.setSeed(this.rendererUpdateCount * 312987231L);
            final Entity func_175606_aa = this.mc.func_175606_aa();
            final WorldClient theWorld = Minecraft.theWorld;
            final BlockPos blockPos = new BlockPos(func_175606_aa);
            double n = 0.0;
            double n2 = 0.0;
            double n3 = 0.0;
            final int n4 = (int)(100.0f * rainStrength * rainStrength);
            if (this.mc.gameSettings.particleSetting != 1) {
                if (this.mc.gameSettings.particleSetting == 2) {}
            }
            while (0 < 0) {
                final BlockPos func_175725_q = theWorld.func_175725_q(blockPos.add(this.random.nextInt(10) - this.random.nextInt(10), 0, this.random.nextInt(10) - this.random.nextInt(10)));
                final BiomeGenBase biomeGenForCoords = theWorld.getBiomeGenForCoords(func_175725_q);
                final BlockPos offsetDown = func_175725_q.offsetDown();
                final Block block = theWorld.getBlockState(offsetDown).getBlock();
                if (func_175725_q.getY() <= blockPos.getY() + 10 && func_175725_q.getY() >= blockPos.getY() - 10 && biomeGenForCoords.canSpawnLightningBolt() && biomeGenForCoords.func_180626_a(func_175725_q) >= 0.15f) {
                    final float nextFloat = this.random.nextFloat();
                    final float nextFloat2 = this.random.nextFloat();
                    if (block.getMaterial() == Material.lava) {
                        Minecraft.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, func_175725_q.getX() + nextFloat, func_175725_q.getY() + 0.1f - block.getBlockBoundsMinY(), func_175725_q.getZ() + nextFloat2, 0.0, 0.0, 0.0, new int[0]);
                    }
                    else if (block.getMaterial() != Material.air) {
                        block.setBlockBoundsBasedOnState(theWorld, offsetDown);
                        int n5 = 0;
                        ++n5;
                        if (this.random.nextInt(0) == 0) {
                            n = offsetDown.getX() + nextFloat;
                            n2 = offsetDown.getY() + 0.1f + block.getBlockBoundsMaxY() - 1.0;
                            n3 = offsetDown.getZ() + nextFloat2;
                        }
                        Minecraft.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, offsetDown.getX() + nextFloat, offsetDown.getY() + 0.1f + block.getBlockBoundsMaxY(), offsetDown.getZ() + nextFloat2, 0.0, 0.0, 0.0, new int[0]);
                    }
                }
                int n6 = 0;
                ++n6;
            }
            if (0 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
                this.rainSoundCounter = 0;
                if (n2 > blockPos.getY() + 1 && theWorld.func_175725_q(blockPos).getY() > MathHelper.floor_float((float)blockPos.getY())) {
                    Minecraft.theWorld.playSound(n, n2, n3, "ambient.weather.rain", 0.1f, 0.5f, false);
                }
                else {
                    Minecraft.theWorld.playSound(n, n2, n3, "ambient.weather.rain", 0.2f, 1.0f, false);
                }
            }
        }
    }
    
    protected void renderRainSnow(final float n) {
        if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
            final Object call = Reflector.call(Minecraft.theWorld.provider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
            if (call != null) {
                Reflector.callVoid(call, Reflector.IRenderHandler_render, n, Minecraft.theWorld, this.mc);
                return;
            }
        }
        final float rainStrength = Minecraft.theWorld.getRainStrength(n);
        if (rainStrength > 0.0f) {
            if (Config.isRainOff()) {
                return;
            }
            this.func_180436_i();
            final Entity func_175606_aa = this.mc.func_175606_aa();
            final WorldClient theWorld = Minecraft.theWorld;
            final int floor_double = MathHelper.floor_double(func_175606_aa.posX);
            final int floor_double2 = MathHelper.floor_double(func_175606_aa.posY);
            final int floor_double3 = MathHelper.floor_double(func_175606_aa.posZ);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1f);
            final double n2 = func_175606_aa.lastTickPosX + (func_175606_aa.posX - func_175606_aa.lastTickPosX) * n;
            final double n3 = func_175606_aa.lastTickPosY + (func_175606_aa.posY - func_175606_aa.lastTickPosY) * n;
            final double n4 = func_175606_aa.lastTickPosZ + (func_175606_aa.posZ - func_175606_aa.lastTickPosZ) * n;
            final int floor_double4 = MathHelper.floor_double(n3);
            if (Config.isRainFancy()) {}
            final float n5 = this.rendererUpdateCount + n;
            if (Config.isRainFancy()) {}
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            for (int i = floor_double3 - 10; i <= floor_double3 + 10; ++i) {
                for (int j = floor_double - 10; j <= floor_double + 10; ++j) {
                    final int n6 = (i - floor_double3 + 16) * 32 + j - floor_double + 16;
                    final float n7 = this.field_175076_N[n6] * 0.5f;
                    final float n8 = this.field_175077_O[n6] * 0.5f;
                    final BlockPos blockPos = new BlockPos(j, 0, i);
                    final BiomeGenBase biomeGenForCoords = theWorld.getBiomeGenForCoords(blockPos);
                    if (biomeGenForCoords.canSpawnLightningBolt() || biomeGenForCoords.getEnableSnow()) {
                        final int y = theWorld.func_175725_q(blockPos).getY();
                        int n9 = floor_double2 - 10;
                        int n10 = floor_double2 + 10;
                        if (n9 < y) {
                            n9 = y;
                        }
                        if (n10 < y) {
                            n10 = y;
                        }
                        final float n11 = 1.0f;
                        int n12;
                        if ((n12 = y) < floor_double4) {
                            n12 = floor_double4;
                        }
                        if (n9 != n10) {
                            this.random.setSeed(j * j * 3121 + j * 45238971 ^ i * i * 418711 + i * 13761);
                            if (theWorld.getWorldChunkManager().getTemperatureAtHeight(biomeGenForCoords.func_180626_a(new BlockPos(j, n9, i)), y) >= 0.15f) {
                                if (true) {
                                    if (1 >= 0) {
                                        instance.draw();
                                    }
                                    this.mc.getTextureManager().bindTexture(EntityRenderer.locationRainPng);
                                    worldRenderer.startDrawingQuads();
                                }
                                final float n13 = ((this.rendererUpdateCount + j * j * 3121 + j * 45238971 + i * i * 418711 + i * 13761 & 0x1F) + n) / 32.0f * (3.0f + this.random.nextFloat());
                                final double n14 = j + 0.5f - func_175606_aa.posX;
                                final double n15 = i + 0.5f - func_175606_aa.posZ;
                                final float n16 = MathHelper.sqrt_double(n14 * n14 + n15 * n15) / 10;
                                final float n17 = 1.0f;
                                worldRenderer.func_178963_b(theWorld.getCombinedLight(new BlockPos(j, n12, i), 0));
                                worldRenderer.func_178960_a(n17, n17, n17, ((1.0f - n16 * n16) * 0.5f + 0.5f) * rainStrength);
                                worldRenderer.setTranslation(-n2 * 1.0, -n3 * 1.0, -n4 * 1.0);
                                worldRenderer.addVertexWithUV(j - n7 + 0.5, n9, i - n8 + 0.5, 0.0f * n11, n9 * n11 / 4.0f + n13 * n11);
                                worldRenderer.addVertexWithUV(j + n7 + 0.5, n9, i + n8 + 0.5, 1.0f * n11, n9 * n11 / 4.0f + n13 * n11);
                                worldRenderer.addVertexWithUV(j + n7 + 0.5, n10, i + n8 + 0.5, 1.0f * n11, n10 * n11 / 4.0f + n13 * n11);
                                worldRenderer.addVertexWithUV(j - n7 + 0.5, n10, i - n8 + 0.5, 0.0f * n11, n10 * n11 / 4.0f + n13 * n11);
                                worldRenderer.setTranslation(0.0, 0.0, 0.0);
                            }
                            else {
                                if (true != true) {
                                    if (1 >= 0) {
                                        instance.draw();
                                    }
                                    this.mc.getTextureManager().bindTexture(EntityRenderer.locationSnowPng);
                                    worldRenderer.startDrawingQuads();
                                }
                                final float n18 = ((this.rendererUpdateCount & 0x1FF) + n) / 512.0f;
                                final float n19 = this.random.nextFloat() + n5 * 0.01f * (float)this.random.nextGaussian();
                                final float n20 = this.random.nextFloat() + n5 * (float)this.random.nextGaussian() * 0.001f;
                                final double n21 = j + 0.5f - func_175606_aa.posX;
                                final double n22 = i + 0.5f - func_175606_aa.posZ;
                                final float n23 = MathHelper.sqrt_double(n21 * n21 + n22 * n22) / 10;
                                final float n24 = 1.0f;
                                worldRenderer.func_178963_b((theWorld.getCombinedLight(new BlockPos(j, n12, i), 0) * 3 + 15728880) / 4);
                                worldRenderer.func_178960_a(n24, n24, n24, ((1.0f - n23 * n23) * 0.3f + 0.5f) * rainStrength);
                                worldRenderer.setTranslation(-n2 * 1.0, -n3 * 1.0, -n4 * 1.0);
                                worldRenderer.addVertexWithUV(j - n7 + 0.5, n9, i - n8 + 0.5, 0.0f * n11 + n19, n9 * n11 / 4.0f + n18 * n11 + n20);
                                worldRenderer.addVertexWithUV(j + n7 + 0.5, n9, i + n8 + 0.5, 1.0f * n11 + n19, n9 * n11 / 4.0f + n18 * n11 + n20);
                                worldRenderer.addVertexWithUV(j + n7 + 0.5, n10, i + n8 + 0.5, 1.0f * n11 + n19, n10 * n11 / 4.0f + n18 * n11 + n20);
                                worldRenderer.addVertexWithUV(j - n7 + 0.5, n10, i - n8 + 0.5, 0.0f * n11 + n19, n10 * n11 / 4.0f + n18 * n11 + n20);
                                worldRenderer.setTranslation(0.0, 0.0, 0.0);
                            }
                        }
                    }
                }
            }
            if (1 >= 0) {
                instance.draw();
            }
            GlStateManager.alphaFunc(516, 0.1f);
            this.func_175072_h();
        }
    }
    
    public void setupOverlayRendering() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }
    
    private void updateFogColor(final float n) {
        final WorldClient theWorld = Minecraft.theWorld;
        final Entity func_175606_aa = this.mc.func_175606_aa();
        final float n2 = 0.25f;
        final float n3 = 0.75f;
        final GameSettings gameSettings = this.mc.gameSettings;
        final float n4 = 1.0f - (float)Math.pow(n2 + n3 * GameSettings.renderDistanceChunks / 32.0f, 0.25);
        final Vec3 worldSkyColor = CustomColors.getWorldSkyColor(theWorld.getSkyColor(this.mc.func_175606_aa(), n), theWorld, this.mc.func_175606_aa(), n);
        final float n5 = (float)worldSkyColor.xCoord;
        final float n6 = (float)worldSkyColor.yCoord;
        final float n7 = (float)worldSkyColor.zCoord;
        final Vec3 worldFogColor = CustomColors.getWorldFogColor(theWorld.getFogColor(n), theWorld, this.mc.func_175606_aa(), n);
        this.field_175080_Q = (float)worldFogColor.xCoord;
        this.field_175082_R = (float)worldFogColor.yCoord;
        this.field_175081_S = (float)worldFogColor.zCoord;
        final GameSettings gameSettings2 = this.mc.gameSettings;
        if (GameSettings.renderDistanceChunks >= 4) {
            final double n8 = -1.0;
            float n9 = (float)func_175606_aa.getLook(n).dotProduct((MathHelper.sin(theWorld.getCelestialAngleRadians(n)) > 0.0f) ? new Vec3(n8, 0.0, 0.0) : new Vec3(1.0, 0.0, 0.0));
            if (n9 < 0.0f) {
                n9 = 0.0f;
            }
            if (n9 > 0.0f) {
                final float[] calcSunriseSunsetColors = theWorld.provider.calcSunriseSunsetColors(theWorld.getCelestialAngle(n), n);
                if (calcSunriseSunsetColors != null) {
                    final float n10 = n9 * calcSunriseSunsetColors[3];
                    this.field_175080_Q = this.field_175080_Q * (1.0f - n10) + calcSunriseSunsetColors[0] * n10;
                    this.field_175082_R = this.field_175082_R * (1.0f - n10) + calcSunriseSunsetColors[1] * n10;
                    this.field_175081_S = this.field_175081_S * (1.0f - n10) + calcSunriseSunsetColors[2] * n10;
                }
            }
        }
        this.field_175080_Q += (n5 - this.field_175080_Q) * n4;
        this.field_175082_R += (n6 - this.field_175082_R) * n4;
        this.field_175081_S += (n7 - this.field_175081_S) * n4;
        final float rainStrength = theWorld.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n11 = 1.0f - rainStrength * 0.5f;
            final float n12 = 1.0f - rainStrength * 0.4f;
            this.field_175080_Q *= n11;
            this.field_175082_R *= n11;
            this.field_175081_S *= n12;
        }
        final float weightedThunderStrength = theWorld.getWeightedThunderStrength(n);
        if (weightedThunderStrength > 0.0f) {
            final float n13 = 1.0f - weightedThunderStrength * 0.5f;
            this.field_175080_Q *= n13;
            this.field_175082_R *= n13;
            this.field_175081_S *= n13;
        }
        final Block func_180786_a = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, func_175606_aa, n);
        if (this.cloudFog) {
            final Vec3 cloudColour = theWorld.getCloudColour(n);
            this.field_175080_Q = (float)cloudColour.xCoord;
            this.field_175082_R = (float)cloudColour.yCoord;
            this.field_175081_S = (float)cloudColour.zCoord;
        }
        else if (func_180786_a.getMaterial() == Material.water) {
            float n14 = EnchantmentHelper.func_180319_a(func_175606_aa) * 0.2f;
            if (func_175606_aa instanceof EntityLivingBase && ((EntityLivingBase)func_175606_aa).isPotionActive(Potion.waterBreathing)) {
                n14 = n14 * 0.3f + 0.6f;
            }
            this.field_175080_Q = 0.02f + n14;
            this.field_175082_R = 0.02f + n14;
            this.field_175081_S = 0.2f + n14;
            final Vec3 underwaterColor = CustomColors.getUnderwaterColor(Minecraft.theWorld, this.mc.func_175606_aa().posX, this.mc.func_175606_aa().posY + 1.0, this.mc.func_175606_aa().posZ);
            if (underwaterColor != null) {
                this.field_175080_Q = (float)underwaterColor.xCoord;
                this.field_175082_R = (float)underwaterColor.yCoord;
                this.field_175081_S = (float)underwaterColor.zCoord;
            }
        }
        else if (func_180786_a.getMaterial() == Material.lava) {
            this.field_175080_Q = 0.6f;
            this.field_175082_R = 0.1f;
            this.field_175081_S = 0.0f;
        }
        final float n15 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * n;
        this.field_175080_Q *= n15;
        this.field_175082_R *= n15;
        this.field_175081_S *= n15;
        double n16 = (func_175606_aa.lastTickPosY + (func_175606_aa.posY - func_175606_aa.lastTickPosY) * n) * theWorld.provider.getVoidFogYFactor();
        if (func_175606_aa instanceof EntityLivingBase && ((EntityLivingBase)func_175606_aa).isPotionActive(Potion.blindness)) {
            final Client instance = Client.INSTANCE;
            if (!Client.getModuleByName("NoTroll").toggled) {
                final int duration = ((EntityLivingBase)func_175606_aa).getActivePotionEffect(Potion.blindness).getDuration();
                if (duration < 20) {
                    n16 *= 1.0f - duration / 20.0f;
                }
                else {
                    n16 = 0.0;
                }
            }
        }
        if (n16 < 1.0) {
            if (n16 < 0.0) {
                n16 = 0.0;
            }
            final double n17 = n16 * n16;
            this.field_175080_Q *= (float)n17;
            this.field_175082_R *= (float)n17;
            this.field_175081_S *= (float)n17;
        }
        if (this.bossColorModifier > 0.0f) {
            final float n18 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * n;
            this.field_175080_Q = this.field_175080_Q * (1.0f - n18) + this.field_175080_Q * 0.7f * n18;
            this.field_175082_R = this.field_175082_R * (1.0f - n18) + this.field_175082_R * 0.6f * n18;
            this.field_175081_S = this.field_175081_S * (1.0f - n18) + this.field_175081_S * 0.6f * n18;
        }
        if (func_175606_aa instanceof EntityLivingBase && ((EntityLivingBase)func_175606_aa).isPotionActive(Potion.nightVision)) {
            final float func_180438_a = this.func_180438_a((EntityLivingBase)func_175606_aa, n);
            float n19 = 1.0f / this.field_175080_Q;
            if (n19 > 1.0f / this.field_175082_R) {
                n19 = 1.0f / this.field_175082_R;
            }
            if (n19 > 1.0f / this.field_175081_S) {
                n19 = 1.0f / this.field_175081_S;
            }
            this.field_175080_Q = this.field_175080_Q * (1.0f - func_180438_a) + this.field_175080_Q * n19 * func_180438_a;
            this.field_175082_R = this.field_175082_R * (1.0f - func_180438_a) + this.field_175082_R * n19 * func_180438_a;
            this.field_175081_S = this.field_175081_S * (1.0f - func_180438_a) + this.field_175081_S * n19 * func_180438_a;
        }
        if (this.mc.gameSettings.anaglyph) {
            final float field_175080_Q = (this.field_175080_Q * 30.0f + this.field_175082_R * 59.0f + this.field_175081_S * 11.0f) / 100.0f;
            final float field_175082_R = (this.field_175080_Q * 30.0f + this.field_175082_R * 70.0f) / 100.0f;
            final float field_175081_S = (this.field_175080_Q * 30.0f + this.field_175081_S * 70.0f) / 100.0f;
            this.field_175080_Q = field_175080_Q;
            this.field_175082_R = field_175082_R;
            this.field_175081_S = field_175081_S;
        }
        if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
            final Object instance2 = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, this, func_175606_aa, func_180786_a, n, this.field_175080_Q, this.field_175082_R, this.field_175081_S);
            Reflector.postForgeBusEvent(instance2);
            this.field_175080_Q = Reflector.getFieldValueFloat(instance2, Reflector.EntityViewRenderEvent_FogColors_red, this.field_175080_Q);
            this.field_175082_R = Reflector.getFieldValueFloat(instance2, Reflector.EntityViewRenderEvent_FogColors_green, this.field_175082_R);
            this.field_175081_S = Reflector.getFieldValueFloat(instance2, Reflector.EntityViewRenderEvent_FogColors_blue, this.field_175081_S);
        }
        Shaders.setClearColor(this.field_175080_Q, this.field_175082_R, this.field_175081_S, 0.0f);
    }
    
    private void setupFog(final int n, final float n2) {
        final Entity func_175606_aa = this.mc.func_175606_aa();
        this.fogStandard = false;
        if (func_175606_aa instanceof EntityPlayer) {
            final boolean isCreativeMode = ((EntityPlayer)func_175606_aa).capabilities.isCreativeMode;
        }
        GL11.glFog(2918, this.setFogColorBuffer(this.field_175080_Q, this.field_175082_R, this.field_175081_S, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final Block func_180786_a = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, func_175606_aa, n2);
        float callFloat = -1.0f;
        if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
            callFloat = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, this, func_175606_aa, func_180786_a, n2, 0.1f);
        }
        if (callFloat >= 0.0f) {
            GlStateManager.setFogDensity(callFloat);
        }
        else if (func_175606_aa instanceof EntityLivingBase && ((EntityPlayer)func_175606_aa).isPotionActive(Potion.blindness)) {
            float fogEnd = 5.0f;
            final int duration = ((EntityPlayer)func_175606_aa).getActivePotionEffect(Potion.blindness).getDuration();
            if (duration < 20) {
                fogEnd = 5.0f + (this.farPlaneDistance - 5.0f) * (1.0f - duration / 20.0f);
            }
            if (Config.isShaders()) {
                Shaders.setFog(9729);
            }
            else {
                GlStateManager.setFog(9729);
            }
            if (n == -1) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(fogEnd * 0.8f);
            }
            else {
                GlStateManager.setFogStart(fogEnd * 0.25f);
                GlStateManager.setFogEnd(fogEnd);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
                GL11.glFogi(34138, 34139);
            }
        }
        else if (this.cloudFog) {
            if (Config.isShaders()) {
                Shaders.setFog(2048);
            }
            else {
                GlStateManager.setFog(2048);
            }
            GlStateManager.setFogDensity(0.1f);
        }
        else if (func_180786_a.getMaterial() == Material.water) {
            if (Config.isShaders()) {
                Shaders.setFog(2048);
            }
            else {
                GlStateManager.setFog(2048);
            }
            if (func_175606_aa instanceof EntityLivingBase && ((EntityPlayer)func_175606_aa).isPotionActive(Potion.waterBreathing)) {
                GlStateManager.setFogDensity(0.01f);
            }
            else {
                GlStateManager.setFogDensity(0.1f - EnchantmentHelper.func_180319_a(func_175606_aa) * 0.03f);
            }
            if (Config.isClearWater()) {
                GlStateManager.setFogDensity(0.02f);
            }
        }
        else if (func_180786_a.getMaterial() == Material.lava) {
            if (Config.isShaders()) {
                Shaders.setFog(2048);
            }
            else {
                GlStateManager.setFog(2048);
            }
            GlStateManager.setFogDensity(2.0f);
        }
        else {
            final float farPlaneDistance = this.farPlaneDistance;
            this.fogStandard = true;
            if (Config.isShaders()) {
                Shaders.setFog(9729);
            }
            else {
                GlStateManager.setFog(9729);
            }
            if (n == -1) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(farPlaneDistance);
            }
            else {
                GlStateManager.setFogStart(farPlaneDistance * Config.getFogStart());
                GlStateManager.setFogEnd(farPlaneDistance);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                if (Config.isFogFancy()) {
                    GL11.glFogi(34138, 34139);
                }
                if (Config.isFogFast()) {
                    GL11.glFogi(34138, 34140);
                }
            }
            if (Minecraft.theWorld.provider.doesXZShowFog((int)func_175606_aa.posX, (int)func_175606_aa.posZ)) {
                GlStateManager.setFogStart(farPlaneDistance * 0.05f);
                GlStateManager.setFogEnd(farPlaneDistance);
            }
            if (Reflector.ForgeHooksClient_onFogRender.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, this, func_175606_aa, func_180786_a, n2, n, farPlaneDistance);
            }
        }
        GlStateManager.colorMaterial(1028, 4608);
    }
    
    private FloatBuffer setFogColorBuffer(final float n, final float n2, final float n3, final float n4) {
        if (Config.isShaders()) {
            Shaders.setFogColor(n, n2, n3);
        }
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(n).put(n2).put(n3).put(n4);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }
    
    public MapItemRenderer getMapItemRenderer() {
        return this.theMapItemRenderer;
    }
    
    private void waitForServerThread() {
        this.serverWaitTimeCurrent = 0;
        if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
            if (this.mc.isIntegratedServerRunning()) {
                final IntegratedServer integratedServer = this.mc.getIntegratedServer();
                if (integratedServer != null) {
                    if (!this.mc.isGamePaused() && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
                        if (this.serverWaitTime > 0) {
                            Lagometer.timerServer.start();
                            Config.sleep(this.serverWaitTime);
                            Lagometer.timerServer.end();
                            this.serverWaitTimeCurrent = this.serverWaitTime;
                        }
                        final long lastServerTime = System.nanoTime() / 1000000L;
                        if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                            long n = lastServerTime - this.lastServerTime;
                            if (n < 0L) {
                                this.lastServerTime = lastServerTime;
                                n = 0L;
                            }
                            if (n >= 50L) {
                                this.lastServerTime = lastServerTime;
                                final int tickCounter = integratedServer.getTickCounter();
                                final int n2 = tickCounter - this.lastServerTicks;
                                if (0 < 0) {
                                    this.lastServerTicks = tickCounter;
                                }
                                if (0 < 1 && this.serverWaitTime < 100) {
                                    this.serverWaitTime += 2;
                                }
                                if (0 > 1 && this.serverWaitTime > 0) {
                                    --this.serverWaitTime;
                                }
                                this.lastServerTicks = tickCounter;
                            }
                        }
                        else {
                            this.lastServerTime = lastServerTime;
                            this.lastServerTicks = integratedServer.getTickCounter();
                            this.avgServerTickDiff = 1.0f;
                            this.avgServerTimeDiff = 50.0f;
                        }
                    }
                    else {
                        if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
                            Config.sleep(20L);
                        }
                        this.lastServerTime = 0L;
                        this.lastServerTicks = 0;
                    }
                }
            }
        }
        else {
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
        }
    }
    
    private void frameInit() {
        if (!this.initialized) {
            if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
                Config.setNotify64BitJava(true);
            }
            this.initialized = true;
        }
        final WorldClient theWorld = Minecraft.theWorld;
        if (theWorld != null) {
            if (Config.getNewRelease() != null) {
                Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(I18n.format("of.message.newVersion", String.valueOf("HD_U".replace("HD_U", "HD Ultra").replace("L", "Light")) + " " + Config.getNewRelease())));
                Config.setNewRelease(null);
            }
            if (Config.isNotify64BitJava()) {
                Config.setNotify64BitJava(false);
                Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(I18n.format("of.message.java64Bit", new Object[0])));
            }
        }
        if (this.mc.currentScreen instanceof GuiMainMenu) {
            this.updateMainMenu((GuiMainMenu)this.mc.currentScreen);
        }
        if (this.updatedWorld != theWorld) {
            RandomMobs.worldChanged(this.updatedWorld, theWorld);
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
            this.updatedWorld = theWorld;
        }
        if (!this.setFxaaShader(Shaders.configAntialiasingLevel)) {
            Shaders.configAntialiasingLevel = 0;
        }
    }
    
    private void frameFinish() {
        if (Minecraft.theWorld != null) {
            final long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis > this.lastErrorCheckTimeMs + 10000L) {
                this.lastErrorCheckTimeMs = currentTimeMillis;
                final int glGetError = GL11.glGetError();
                if (glGetError != 0) {
                    Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(I18n.format("of.message.openglError", glGetError, GLU.gluErrorString(glGetError))));
                }
            }
        }
    }
    
    private void updateMainMenu(final GuiMainMenu guiMainMenu) {
        Object o = null;
        final Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        final int value = instance.get(5);
        final int n = instance.get(2) + 1;
        if (value == 8 && n == 4) {
            o = "Happy birthday, OptiFine!";
        }
        if (value == 14 && n == 8) {
            o = "Happy birthday, sp614x!";
        }
        if (o == null) {
            return;
        }
        final Field[] declaredFields = GuiMainMenu.class.getDeclaredFields();
        while (0 < declaredFields.length) {
            if (declaredFields[0].getType() == String.class) {
                declaredFields[0].setAccessible(true);
                declaredFields[0].set(guiMainMenu, o);
                break;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public boolean setFxaaShader(final int n) {
        if (!OpenGlHelper.isFramebufferEnabled()) {
            return false;
        }
        if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && this.theShaderGroup != this.fxaaShaders[4]) {
            return true;
        }
        if (n != 2 && n != 4) {
            if (this.theShaderGroup == null) {
                return true;
            }
            this.theShaderGroup.deleteShaderGroup();
            this.theShaderGroup = null;
            return true;
        }
        else {
            if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[n]) {
                return true;
            }
            if (Minecraft.theWorld == null) {
                return true;
            }
            this.func_175069_a(new ResourceLocation("shaders/post/fxaa_of_" + n + "x.json"));
            this.fxaaShaders[n] = this.theShaderGroup;
            return this.field_175083_ad;
        }
    }
    
    public void setupOverlayRendering(final ScaledResolution scaledResolution, final LockedResolution lockedResolution, final boolean b) {
        GlStateManager.clear(256);
        GL11.glMatrixMode(5889);
        if (b) {
            GL11.glOrtho(0.0, lockedResolution.getWidth(), lockedResolution.getHeight(), 0.0, 1000.0, 3000.0);
        }
        else {
            GL11.glOrtho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        }
        GL11.glMatrixMode(5888);
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }
    
    static Minecraft access$0(final EntityRenderer entityRenderer) {
        return entityRenderer.mc;
    }
}
