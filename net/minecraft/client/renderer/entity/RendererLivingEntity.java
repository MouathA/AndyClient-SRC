package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.model.*;
import java.nio.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import Mood.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.scoreboard.*;

public abstract class RendererLivingEntity extends Render
{
    private static final Logger logger;
    private static final DynamicTexture field_177096_e;
    protected ModelBase mainModel;
    protected FloatBuffer field_177095_g;
    protected List field_177097_h;
    protected boolean field_177098_i;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001012";
        logger = LogManager.getLogger();
        (field_177096_e = new DynamicTexture(16, 16)).getTextureData();
        RendererLivingEntity.field_177096_e.updateDynamicTexture();
    }
    
    public RendererLivingEntity(final RenderManager renderManager, final ModelBase mainModel, final float shadowSize) {
        super(renderManager);
        this.field_177095_g = GLAllocation.createDirectFloatBuffer(4);
        this.field_177097_h = Lists.newArrayList();
        this.field_177098_i = false;
        this.mainModel = mainModel;
        this.shadowSize = shadowSize;
    }
    
    public boolean addLayer(final LayerRenderer layerRenderer) {
        return this.field_177097_h.add(layerRenderer);
    }
    
    protected boolean func_177089_b(final LayerRenderer layerRenderer) {
        return this.field_177097_h.remove(layerRenderer);
    }
    
    public ModelBase getMainModel() {
        return this.mainModel;
    }
    
    protected float interpolateRotation(final float n, final float n2, final float n3) {
        float n4;
        for (n4 = n2 - n; n4 < -180.0f; n4 += 360.0f) {}
        while (n4 >= 180.0f) {
            n4 -= 360.0f;
        }
        return n + n3 * n4;
    }
    
    public void func_82422_c() {
    }
    
    public void doRender(final EntityLivingBase p0, final double p1, final double p2, final double p3, final float p4, final float p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   optifine/ReflectorConstructor.exists:()Z
        //     6: ifeq            53
        //     9: getstatic       optifine/Reflector.RenderLivingEvent_Pre_Constructor:Loptifine/ReflectorConstructor;
        //    12: iconst_5       
        //    13: anewarray       Ljava/lang/Object;
        //    16: dup            
        //    17: iconst_0       
        //    18: aload_1        
        //    19: aastore        
        //    20: dup            
        //    21: iconst_1       
        //    22: aload_0        
        //    23: aastore        
        //    24: dup            
        //    25: iconst_2       
        //    26: dload_2        
        //    27: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    30: aastore        
        //    31: dup            
        //    32: iconst_3       
        //    33: dload           4
        //    35: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    38: aastore        
        //    39: dup            
        //    40: iconst_4       
        //    41: dload           6
        //    43: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    46: aastore        
        //    47: invokestatic    optifine/Reflector.postForgeBusEvent:(Loptifine/ReflectorConstructor;[Ljava/lang/Object;)Z
        //    50: ifne            680
        //    53: aload_0        
        //    54: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.mainModel:Lnet/minecraft/client/model/ModelBase;
        //    57: aload_0        
        //    58: aload_1        
        //    59: fload           9
        //    61: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.getSwingProgress:(Lnet/minecraft/entity/EntityLivingBase;F)F
        //    64: putfield        net/minecraft/client/model/ModelBase.swingProgress:F
        //    67: aload_0        
        //    68: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.mainModel:Lnet/minecraft/client/model/ModelBase;
        //    71: aload_1        
        //    72: invokevirtual   net/minecraft/entity/EntityLivingBase.isRiding:()Z
        //    75: putfield        net/minecraft/client/model/ModelBase.isRiding:Z
        //    78: getstatic       optifine/Reflector.ForgeEntity_shouldRiderSit:Loptifine/ReflectorMethod;
        //    81: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //    84: ifeq            130
        //    87: aload_0        
        //    88: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.mainModel:Lnet/minecraft/client/model/ModelBase;
        //    91: aload_1        
        //    92: invokevirtual   net/minecraft/entity/EntityLivingBase.isRiding:()Z
        //    95: ifeq            126
        //    98: aload_1        
        //    99: getfield        net/minecraft/entity/EntityLivingBase.ridingEntity:Lnet/minecraft/entity/Entity;
        //   102: ifnull          126
        //   105: aload_1        
        //   106: getfield        net/minecraft/entity/EntityLivingBase.ridingEntity:Lnet/minecraft/entity/Entity;
        //   109: getstatic       optifine/Reflector.ForgeEntity_shouldRiderSit:Loptifine/ReflectorMethod;
        //   112: iconst_0       
        //   113: anewarray       Ljava/lang/Object;
        //   116: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   119: ifeq            126
        //   122: iconst_1       
        //   123: goto            127
        //   126: iconst_0       
        //   127: putfield        net/minecraft/client/model/ModelBase.isRiding:Z
        //   130: aload_0        
        //   131: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.mainModel:Lnet/minecraft/client/model/ModelBase;
        //   134: aload_1        
        //   135: invokevirtual   net/minecraft/entity/EntityLivingBase.isChild:()Z
        //   138: putfield        net/minecraft/client/model/ModelBase.isChild:Z
        //   141: aload_0        
        //   142: aload_1        
        //   143: getfield        net/minecraft/entity/EntityLivingBase.prevRenderYawOffset:F
        //   146: aload_1        
        //   147: getfield        net/minecraft/entity/EntityLivingBase.renderYawOffset:F
        //   150: fload           9
        //   152: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.interpolateRotation:(FFF)F
        //   155: fstore          10
        //   157: aload_0        
        //   158: aload_1        
        //   159: getfield        net/minecraft/entity/EntityLivingBase.prevRotationYawHead:F
        //   162: aload_1        
        //   163: getfield        net/minecraft/entity/EntityLivingBase.rotationYawHead:F
        //   166: fload           9
        //   168: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.interpolateRotation:(FFF)F
        //   171: fstore          11
        //   173: fload           11
        //   175: fload           10
        //   177: fsub           
        //   178: fstore          12
        //   180: aload_0        
        //   181: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.mainModel:Lnet/minecraft/client/model/ModelBase;
        //   184: getfield        net/minecraft/client/model/ModelBase.isRiding:Z
        //   187: ifeq            293
        //   190: aload_1        
        //   191: getfield        net/minecraft/entity/EntityLivingBase.ridingEntity:Lnet/minecraft/entity/Entity;
        //   194: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   197: ifeq            293
        //   200: aload_1        
        //   201: getfield        net/minecraft/entity/EntityLivingBase.ridingEntity:Lnet/minecraft/entity/Entity;
        //   204: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   207: astore          14
        //   209: aload_0        
        //   210: aload           14
        //   212: getfield        net/minecraft/entity/EntityLivingBase.prevRenderYawOffset:F
        //   215: aload           14
        //   217: getfield        net/minecraft/entity/EntityLivingBase.renderYawOffset:F
        //   220: fload           9
        //   222: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.interpolateRotation:(FFF)F
        //   225: fstore          10
        //   227: fload           11
        //   229: fload           10
        //   231: fsub           
        //   232: fstore          12
        //   234: fload           12
        //   236: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_float:(F)F
        //   239: fstore          13
        //   241: fload           13
        //   243: ldc             -85.0
        //   245: fcmpg          
        //   246: ifge            253
        //   249: ldc             -85.0
        //   251: fstore          13
        //   253: fload           13
        //   255: ldc             85.0
        //   257: fcmpl          
        //   258: iflt            265
        //   261: ldc             85.0
        //   263: fstore          13
        //   265: fload           11
        //   267: fload           13
        //   269: fsub           
        //   270: fstore          10
        //   272: fload           13
        //   274: fload           13
        //   276: fmul           
        //   277: ldc             2500.0
        //   279: fcmpl          
        //   280: ifle            293
        //   283: fload           10
        //   285: fload           13
        //   287: ldc             0.2
        //   289: fmul           
        //   290: fadd           
        //   291: fstore          10
        //   293: aload_1        
        //   294: getfield        net/minecraft/entity/EntityLivingBase.prevRotationPitch:F
        //   297: aload_1        
        //   298: getfield        net/minecraft/entity/EntityLivingBase.rotationPitch:F
        //   301: aload_1        
        //   302: getfield        net/minecraft/entity/EntityLivingBase.prevRotationPitch:F
        //   305: fsub           
        //   306: fload           9
        //   308: fmul           
        //   309: fadd           
        //   310: fstore          14
        //   312: aload_0        
        //   313: aload_1        
        //   314: dload_2        
        //   315: dload           4
        //   317: dload           6
        //   319: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.renderLivingAt:(Lnet/minecraft/entity/EntityLivingBase;DDD)V
        //   322: aload_0        
        //   323: aload_1        
        //   324: fload           9
        //   326: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.handleRotationFloat:(Lnet/minecraft/entity/EntityLivingBase;F)F
        //   329: fstore          13
        //   331: aload_0        
        //   332: aload_1        
        //   333: fload           13
        //   335: fload           10
        //   337: fload           9
        //   339: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.rotateCorpse:(Lnet/minecraft/entity/EntityLivingBase;FFF)V
        //   342: ldc             -1.0
        //   344: ldc             -1.0
        //   346: fconst_1       
        //   347: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //   350: aload_0        
        //   351: aload_1        
        //   352: fload           9
        //   354: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.preRenderCallback:(Lnet/minecraft/entity/EntityLivingBase;F)V
        //   357: ldc             0.0625
        //   359: fstore          15
        //   361: fconst_0       
        //   362: ldc             -1.5078125
        //   364: fconst_0       
        //   365: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   368: aload_1        
        //   369: getfield        net/minecraft/entity/EntityLivingBase.prevLimbSwingAmount:F
        //   372: aload_1        
        //   373: getfield        net/minecraft/entity/EntityLivingBase.limbSwingAmount:F
        //   376: aload_1        
        //   377: getfield        net/minecraft/entity/EntityLivingBase.prevLimbSwingAmount:F
        //   380: fsub           
        //   381: fload           9
        //   383: fmul           
        //   384: fadd           
        //   385: fstore          16
        //   387: aload_1        
        //   388: getfield        net/minecraft/entity/EntityLivingBase.limbSwing:F
        //   391: aload_1        
        //   392: getfield        net/minecraft/entity/EntityLivingBase.limbSwingAmount:F
        //   395: fconst_1       
        //   396: fload           9
        //   398: fsub           
        //   399: fmul           
        //   400: fsub           
        //   401: fstore          17
        //   403: aload_1        
        //   404: invokevirtual   net/minecraft/entity/EntityLivingBase.isChild:()Z
        //   407: ifeq            417
        //   410: fload           17
        //   412: ldc             3.0
        //   414: fmul           
        //   415: fstore          17
        //   417: fload           16
        //   419: fconst_1       
        //   420: fcmpl          
        //   421: ifle            427
        //   424: fconst_1       
        //   425: fstore          16
        //   427: aload_0        
        //   428: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.mainModel:Lnet/minecraft/client/model/ModelBase;
        //   431: aload_1        
        //   432: fload           17
        //   434: fload           16
        //   436: fload           9
        //   438: invokevirtual   net/minecraft/client/model/ModelBase.setLivingAnimations:(Lnet/minecraft/entity/EntityLivingBase;FFF)V
        //   441: aload_0        
        //   442: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.mainModel:Lnet/minecraft/client/model/ModelBase;
        //   445: fload           17
        //   447: fload           16
        //   449: fload           13
        //   451: fload           12
        //   453: fload           14
        //   455: ldc             0.0625
        //   457: aload_1        
        //   458: invokevirtual   net/minecraft/client/model/ModelBase.setRotationAngles:(FFFFFFLnet/minecraft/entity/Entity;)V
        //   461: aload_0        
        //   462: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.field_177098_i:Z
        //   465: ifeq            504
        //   468: aload_0        
        //   469: aload_1        
        //   470: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.func_177088_c:(Lnet/minecraft/entity/EntityLivingBase;)Z
        //   473: istore          18
        //   475: aload_0        
        //   476: aload_1        
        //   477: fload           17
        //   479: fload           16
        //   481: fload           13
        //   483: fload           12
        //   485: fload           14
        //   487: ldc             0.0625
        //   489: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.renderModel:(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V
        //   492: iload           18
        //   494: ifeq            579
        //   497: aload_0        
        //   498: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.func_180565_e:()V
        //   501: goto            579
        //   504: aload_0        
        //   505: aload_1        
        //   506: fload           9
        //   508: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.func_177090_c:(Lnet/minecraft/entity/EntityLivingBase;F)Z
        //   511: istore          18
        //   513: aload_0        
        //   514: aload_1        
        //   515: fload           17
        //   517: fload           16
        //   519: fload           13
        //   521: fload           12
        //   523: fload           14
        //   525: ldc             0.0625
        //   527: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.renderModel:(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V
        //   530: iload           18
        //   532: ifeq            539
        //   535: aload_0        
        //   536: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.func_177091_f:()V
        //   539: iconst_1       
        //   540: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   543: aload_1        
        //   544: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   547: ifeq            560
        //   550: aload_1        
        //   551: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   554: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_175149_v:()Z
        //   557: ifne            579
        //   560: aload_0        
        //   561: aload_1        
        //   562: fload           17
        //   564: fload           16
        //   566: fload           9
        //   568: fload           13
        //   570: fload           12
        //   572: fload           14
        //   574: ldc             0.0625
        //   576: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.func_177093_a:(Lnet/minecraft/entity/EntityLivingBase;FFFFFFF)V
        //   579: goto            597
        //   582: astore          10
        //   584: getstatic       net/minecraft/client/renderer/entity/RendererLivingEntity.logger:Lorg/apache/logging/log4j/Logger;
        //   587: ldc_w           "Couldn't render entity"
        //   590: aload           10
        //   592: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   597: getstatic       net/minecraft/client/renderer/OpenGlHelper.lightmapTexUnit:I
        //   600: invokestatic    net/minecraft/client/renderer/GlStateManager.setActiveTexture:(I)V
        //   603: getstatic       net/minecraft/client/renderer/OpenGlHelper.defaultTexUnit:I
        //   606: invokestatic    net/minecraft/client/renderer/GlStateManager.setActiveTexture:(I)V
        //   609: aload_0        
        //   610: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.field_177098_i:Z
        //   613: ifne            630
        //   616: aload_0        
        //   617: aload_1        
        //   618: dload_2        
        //   619: dload           4
        //   621: dload           6
        //   623: fload           8
        //   625: fload           9
        //   627: invokespecial   net/minecraft/client/renderer/entity/Render.doRender:(Lnet/minecraft/entity/Entity;DDDFF)V
        //   630: getstatic       optifine/Reflector.RenderLivingEvent_Post_Constructor:Loptifine/ReflectorConstructor;
        //   633: invokevirtual   optifine/ReflectorConstructor.exists:()Z
        //   636: ifeq            680
        //   639: getstatic       optifine/Reflector.RenderLivingEvent_Post_Constructor:Loptifine/ReflectorConstructor;
        //   642: iconst_5       
        //   643: anewarray       Ljava/lang/Object;
        //   646: dup            
        //   647: iconst_0       
        //   648: aload_1        
        //   649: aastore        
        //   650: dup            
        //   651: iconst_1       
        //   652: aload_0        
        //   653: aastore        
        //   654: dup            
        //   655: iconst_2       
        //   656: dload_2        
        //   657: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   660: aastore        
        //   661: dup            
        //   662: iconst_3       
        //   663: dload           4
        //   665: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   668: aastore        
        //   669: dup            
        //   670: iconst_4       
        //   671: dload           6
        //   673: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   676: aastore        
        //   677: invokestatic    optifine/Reflector.postForgeBusEvent:(Loptifine/ReflectorConstructor;[Ljava/lang/Object;)Z
        //   680: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0680 (coming from #0677).
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
    
    protected boolean func_177088_c(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntityPlayer) {
            final ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam)entityLivingBase.getTeam();
            if (scorePlayerTeam != null) {
                final String formatFromString = FontRenderer.getFormatFromString(scorePlayerTeam.getColorPrefix());
                if (formatFromString.length() >= 2) {
                    this.getFontRendererFromRenderManager().func_175064_b(formatFromString.charAt(1));
                }
            }
        }
        final float n = 255 / 255.0f;
        final float n2 = 255 / 255.0f;
        final float n3 = 255 / 255.0f;
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(n, n2, n3, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }
    
    protected void func_180565_e() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    protected void renderModel(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final boolean b = !entityLivingBase.isInvisible();
        boolean b2 = false;
        Label_0054: {
            Label_0053: {
                if (!b) {
                    Minecraft.getMinecraft();
                    if (entityLivingBase.isInvisibleToPlayer(Minecraft.thePlayer)) {
                        final Client instance = Client.INSTANCE;
                        if (!Client.getModuleByName("TrueSight").toggled) {
                            break Label_0053;
                        }
                    }
                    b2 = true;
                    break Label_0054;
                }
            }
            b2 = false;
        }
        final boolean b3 = b2;
        if (b || b3) {
            if (!this.bindEntityTexture(entityLivingBase)) {
                return;
            }
            if (b3) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 0.15f);
                GlStateManager.depthMask(false);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.003921569f);
            }
            this.mainModel.render(entityLivingBase, n, n2, n3, n4, n5, n6);
            if (b3) {
                GlStateManager.alphaFunc(516, 0.1f);
                GlStateManager.depthMask(true);
            }
        }
    }
    
    protected boolean func_177090_c(final EntityLivingBase entityLivingBase, final float n) {
        return this.func_177092_a(entityLivingBase, n, true);
    }
    
    protected boolean func_177092_a(final EntityLivingBase entityLivingBase, final float n, final boolean b) {
        final int colorMultiplier = this.getColorMultiplier(entityLivingBase, entityLivingBase.getBrightness(n), n);
        final boolean b2 = (colorMultiplier >> 24 & 0xFF) > 0;
        final boolean b3 = entityLivingBase.hurtTime > 0 || entityLivingBase.deathTime > 0;
        if (!b2 && !b3) {
            return false;
        }
        if (!b2 && !b) {
            return false;
        }
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, OpenGlHelper.field_176094_t);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176092_v);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176080_A, OpenGlHelper.field_176092_v);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176076_D, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
        this.field_177095_g.position(0);
        if (b3) {
            this.field_177095_g.put(1.0f);
            this.field_177095_g.put(0.0f);
            this.field_177095_g.put(0.0f);
            this.field_177095_g.put(0.3f);
            if (Config.isShaders()) {
                Shaders.setEntityColor(1.0f, 0.0f, 0.0f, 0.3f);
            }
        }
        else {
            final float n2 = (colorMultiplier >> 24 & 0xFF) / 255.0f;
            final float n3 = (colorMultiplier >> 16 & 0xFF) / 255.0f;
            final float n4 = (colorMultiplier >> 8 & 0xFF) / 255.0f;
            final float n5 = (colorMultiplier & 0xFF) / 255.0f;
            this.field_177095_g.put(n3);
            this.field_177095_g.put(n4);
            this.field_177095_g.put(n5);
            this.field_177095_g.put(1.0f - n2);
            if (Config.isShaders()) {
                Shaders.setEntityColor(n3, n4, n5, 1.0f - n2);
            }
        }
        this.field_177095_g.flip();
        GL11.glTexEnv(8960, 8705, this.field_177095_g);
        GlStateManager.setActiveTexture(OpenGlHelper.field_176096_r);
        GlStateManager.func_179144_i(RendererLivingEntity.field_177096_e.getGlTextureId());
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176091_w);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }
    
    protected void func_177091_f() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176079_G, OpenGlHelper.field_176093_u);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176086_J, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, 5890);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.field_176096_r);
        GlStateManager.func_179144_i(0);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, 5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (Config.isShaders()) {
            Shaders.setEntityColor(0.0f, 0.0f, 0.0f, 0.0f);
        }
    }
    
    protected void renderLivingAt(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
    }
    
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        GlStateManager.rotate(180.0f - n2, 0.0f, 1.0f, 0.0f);
        if (entityLivingBase.deathTime > 0) {
            float sqrt_float = MathHelper.sqrt_float((entityLivingBase.deathTime + n3 - 1.0f) / 20.0f * 1.6f);
            if (sqrt_float > 1.0f) {
                sqrt_float = 1.0f;
            }
            GlStateManager.rotate(sqrt_float * this.getDeathMaxRotation(entityLivingBase), 0.0f, 0.0f, 1.0f);
        }
        else {
            final String textWithoutFormattingCodes = EnumChatFormatting.getTextWithoutFormattingCodes(entityLivingBase.getName());
            if (textWithoutFormattingCodes != null && (textWithoutFormattingCodes.equals("Dinnerbone") || textWithoutFormattingCodes.equals("Grumm")) && (!(entityLivingBase instanceof EntityPlayer) || ((EntityPlayer)entityLivingBase).func_175148_a(EnumPlayerModelParts.CAPE))) {
                GlStateManager.translate(0.0f, entityLivingBase.height + 0.1f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }
    
    protected float getSwingProgress(final EntityLivingBase entityLivingBase, final float n) {
        return entityLivingBase.getSwingProgress(n);
    }
    
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return entityLivingBase.ticksExisted + n;
    }
    
    protected void func_177093_a(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        for (final LayerRenderer layerRenderer : this.field_177097_h) {
            final boolean func_177092_a = this.func_177092_a(entityLivingBase, n3, layerRenderer.shouldCombineTextures());
            layerRenderer.doRenderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7);
            if (func_177092_a) {
                this.func_177091_f();
            }
        }
    }
    
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return 90.0f;
    }
    
    protected int getColorMultiplier(final EntityLivingBase entityLivingBase, final float n, final float n2) {
        return 0;
    }
    
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
    }
    
    public void passSpecialRender(final EntityLivingBase p0, final double p1, final double p2, final double p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   optifine/ReflectorConstructor.exists:()Z
        //     6: ifeq            53
        //     9: getstatic       optifine/Reflector.RenderLivingEvent_Specials_Pre_Constructor:Loptifine/ReflectorConstructor;
        //    12: iconst_5       
        //    13: anewarray       Ljava/lang/Object;
        //    16: dup            
        //    17: iconst_0       
        //    18: aload_1        
        //    19: aastore        
        //    20: dup            
        //    21: iconst_1       
        //    22: aload_0        
        //    23: aastore        
        //    24: dup            
        //    25: iconst_2       
        //    26: dload_2        
        //    27: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    30: aastore        
        //    31: dup            
        //    32: iconst_3       
        //    33: dload           4
        //    35: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    38: aastore        
        //    39: dup            
        //    40: iconst_4       
        //    41: dload           6
        //    43: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    46: aastore        
        //    47: invokestatic    optifine/Reflector.postForgeBusEvent:(Loptifine/ReflectorConstructor;[Ljava/lang/Object;)Z
        //    50: ifne            462
        //    53: aload_0        
        //    54: aload_1        
        //    55: ifeq            412
        //    58: aload_1        
        //    59: aload_0        
        //    60: getfield        net/minecraft/client/renderer/entity/RendererLivingEntity.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //    63: getfield        net/minecraft/client/renderer/entity/RenderManager.livingPlayer:Lnet/minecraft/entity/Entity;
        //    66: invokevirtual   net/minecraft/entity/EntityLivingBase.getDistanceSqToEntity:(Lnet/minecraft/entity/Entity;)D
        //    69: dstore          8
        //    71: aload_1        
        //    72: invokevirtual   net/minecraft/entity/EntityLivingBase.isSneaking:()Z
        //    75: ifeq            84
        //    78: ldc_w           32.0
        //    81: goto            87
        //    84: ldc_w           64.0
        //    87: fstore          10
        //    89: dload           8
        //    91: fload           10
        //    93: fload           10
        //    95: fmul           
        //    96: f2d            
        //    97: dcmpg          
        //    98: ifge            412
        //   101: aload_1        
        //   102: invokevirtual   net/minecraft/entity/EntityLivingBase.getDisplayName:()Lnet/minecraft/util/IChatComponent;
        //   105: invokeinterface net/minecraft/util/IChatComponent.getFormattedText:()Ljava/lang/String;
        //   110: astore          11
        //   112: ldc_w           0.02666667
        //   115: fstore          12
        //   117: sipush          516
        //   120: ldc_w           0.1
        //   123: invokestatic    net/minecraft/client/renderer/GlStateManager.alphaFunc:(IF)V
        //   126: aload_1        
        //   127: invokevirtual   net/minecraft/entity/EntityLivingBase.isSneaking:()Z
        //   130: ifeq            376
        //   133: aload_0        
        //   134: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.getFontRendererFromRenderManager:()Lnet/minecraft/client/gui/FontRenderer;
        //   137: astore          13
        //   139: dload_2        
        //   140: d2f            
        //   141: dload           4
        //   143: d2f            
        //   144: aload_1        
        //   145: getfield        net/minecraft/entity/EntityLivingBase.height:F
        //   148: fadd           
        //   149: ldc_w           0.5
        //   152: fadd           
        //   153: aload_1        
        //   154: invokevirtual   net/minecraft/entity/EntityLivingBase.isChild:()Z
        //   157: ifeq            169
        //   160: aload_1        
        //   161: getfield        net/minecraft/entity/EntityLivingBase.height:F
        //   164: fconst_2       
        //   165: fdiv           
        //   166: goto            170
        //   169: fconst_0       
        //   170: fsub           
        //   171: dload           6
        //   173: d2f            
        //   174: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   177: fconst_0       
        //   178: fconst_1       
        //   179: fconst_0       
        //   180: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   183: getstatic       net/minecraft/client/renderer/entity/RenderManager.playerViewY:F
        //   186: fneg           
        //   187: fconst_0       
        //   188: fconst_1       
        //   189: fconst_0       
        //   190: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   193: getstatic       net/minecraft/client/renderer/entity/RenderManager.playerViewX:F
        //   196: fconst_1       
        //   197: fconst_0       
        //   198: fconst_0       
        //   199: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   202: ldc_w           -0.02666667
        //   205: ldc_w           -0.02666667
        //   208: ldc_w           0.02666667
        //   211: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //   214: fconst_0       
        //   215: ldc_w           9.374999
        //   218: fconst_0       
        //   219: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   222: iconst_0       
        //   223: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   226: sipush          770
        //   229: sipush          771
        //   232: iconst_1       
        //   233: iconst_0       
        //   234: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //   237: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //   240: astore          14
        //   242: aload           14
        //   244: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //   247: astore          15
        //   249: aload           15
        //   251: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawingQuads:()V
        //   254: aload           13
        //   256: aload           11
        //   258: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //   261: iconst_2       
        //   262: idiv           
        //   263: istore          16
        //   265: aload           15
        //   267: fconst_0       
        //   268: fconst_0       
        //   269: fconst_0       
        //   270: ldc_w           0.25
        //   273: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178960_a:(FFFF)V
        //   276: aload           15
        //   278: iload           16
        //   280: ineg           
        //   281: iconst_1       
        //   282: isub           
        //   283: i2d            
        //   284: ldc2_w          -1.0
        //   287: dconst_0       
        //   288: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   291: aload           15
        //   293: iload           16
        //   295: ineg           
        //   296: iconst_1       
        //   297: isub           
        //   298: i2d            
        //   299: ldc2_w          8.0
        //   302: dconst_0       
        //   303: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   306: aload           15
        //   308: iload           16
        //   310: iconst_1       
        //   311: iadd           
        //   312: i2d            
        //   313: ldc2_w          8.0
        //   316: dconst_0       
        //   317: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   320: aload           15
        //   322: iload           16
        //   324: iconst_1       
        //   325: iadd           
        //   326: i2d            
        //   327: ldc2_w          -1.0
        //   330: dconst_0       
        //   331: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   334: aload           14
        //   336: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //   339: pop            
        //   340: iconst_1       
        //   341: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   344: aload           13
        //   346: aload           11
        //   348: aload           13
        //   350: aload           11
        //   352: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //   355: ineg           
        //   356: iconst_2       
        //   357: idiv           
        //   358: iconst_0       
        //   359: ldc_w           553648127
        //   362: invokevirtual   net/minecraft/client/gui/FontRenderer.drawString:(Ljava/lang/String;III)I
        //   365: pop            
        //   366: fconst_1       
        //   367: fconst_1       
        //   368: fconst_1       
        //   369: fconst_1       
        //   370: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //   373: goto            412
        //   376: aload_0        
        //   377: aload_1        
        //   378: dload_2        
        //   379: dload           4
        //   381: aload_1        
        //   382: invokevirtual   net/minecraft/entity/EntityLivingBase.isChild:()Z
        //   385: ifeq            398
        //   388: aload_1        
        //   389: getfield        net/minecraft/entity/EntityLivingBase.height:F
        //   392: fconst_2       
        //   393: fdiv           
        //   394: f2d            
        //   395: goto            399
        //   398: dconst_0       
        //   399: dsub           
        //   400: dload           6
        //   402: aload           11
        //   404: ldc_w           0.02666667
        //   407: dload           8
        //   409: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.func_177069_a:(Lnet/minecraft/entity/Entity;DDDLjava/lang/String;FD)V
        //   412: getstatic       optifine/Reflector.RenderLivingEvent_Specials_Post_Constructor:Loptifine/ReflectorConstructor;
        //   415: invokevirtual   optifine/ReflectorConstructor.exists:()Z
        //   418: ifeq            462
        //   421: getstatic       optifine/Reflector.RenderLivingEvent_Specials_Post_Constructor:Loptifine/ReflectorConstructor;
        //   424: iconst_5       
        //   425: anewarray       Ljava/lang/Object;
        //   428: dup            
        //   429: iconst_0       
        //   430: aload_1        
        //   431: aastore        
        //   432: dup            
        //   433: iconst_1       
        //   434: aload_0        
        //   435: aastore        
        //   436: dup            
        //   437: iconst_2       
        //   438: dload_2        
        //   439: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   442: aastore        
        //   443: dup            
        //   444: iconst_3       
        //   445: dload           4
        //   447: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   450: aastore        
        //   451: dup            
        //   452: iconst_4       
        //   453: dload           6
        //   455: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   458: aastore        
        //   459: invokestatic    optifine/Reflector.postForgeBusEvent:(Loptifine/ReflectorConstructor;[Ljava/lang/Object;)Z
        //   462: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0462 (coming from #0459).
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
    
    public void func_177086_a(final boolean field_177098_i) {
        this.field_177098_i = field_177098_i;
    }
    
    protected boolean func_177070_b(final Entity entity) {
        return this.canRenderName((EntityLivingBase)entity);
    }
    
    public void func_177067_a(final Entity entity, final double n, final double n2, final double n3) {
        this.passSpecialRender((EntityLivingBase)entity, n, n2, n3);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLivingBase)entity, n, n2, n3, n4, n5);
    }
    
    static final class SwitchEnumVisible
    {
        static final int[] field_178679_a;
        
        static {
            field_178679_a = new int[Team.EnumVisible.values().length];
            try {
                SwitchEnumVisible.field_178679_a[Team.EnumVisible.ALWAYS.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumVisible.field_178679_a[Team.EnumVisible.NEVER.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumVisible.field_178679_a[Team.EnumVisible.HIDE_FOR_OTHER_TEAMS.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumVisible.field_178679_a[Team.EnumVisible.HIDE_FOR_OWN_TEAM.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
