package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public abstract class RenderLiving extends RendererLivingEntity
{
    private static final String __OBFID;
    
    public RenderLiving(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    protected boolean canRenderName(final EntityLiving entityLiving) {
        return super.canRenderName((EntityLivingBase)entityLiving) && (entityLiving.getAlwaysRenderNameTagForRender() || (entityLiving.hasCustomName() && entityLiving == this.renderManager.field_147941_i));
    }
    
    public boolean func_177104_a(final EntityLiving entityLiving, final ICamera camera, final double n, final double n2, final double n3) {
        return super.func_177071_a(entityLiving, camera, n, n2, n3) || (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() != null && camera.isBoundingBoxInFrustum(entityLiving.getLeashedToEntity().getEntityBoundingBox()));
    }
    
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        super.doRender(entityLiving, n, n2, n3, n4, n5);
        this.func_110827_b(entityLiving, n, n2, n3, n4, n5);
    }
    
    public void func_177105_a(final EntityLiving entityLiving, final float n) {
        final int brightnessForRender = entityLiving.getBrightnessForRender(n);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
    }
    
    private double func_110828_a(final double n, final double n2, final double n3) {
        return n + (n2 - n) * n3;
    }
    
    protected void func_110827_b(final EntityLiving p0, final double p1, final double p2, final double p3, final float p4, final float p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            12
        //     6: getstatic       shadersmod/client/Shaders.isShadowPass:Z
        //     9: ifne            812
        //    12: aload_1        
        //    13: invokevirtual   net/minecraft/entity/EntityLiving.getLeashedToEntity:()Lnet/minecraft/entity/Entity;
        //    16: astore          10
        //    18: aload           10
        //    20: ifnull          812
        //    23: dload           4
        //    25: ldc2_w          1.6
        //    28: aload_1        
        //    29: getfield        net/minecraft/entity/EntityLiving.height:F
        //    32: f2d            
        //    33: dsub           
        //    34: ldc2_w          0.5
        //    37: dmul           
        //    38: dsub           
        //    39: dstore          4
        //    41: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //    44: astore          11
        //    46: aload           11
        //    48: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //    51: astore          12
        //    53: aload_0        
        //    54: aload           10
        //    56: getfield        net/minecraft/entity/Entity.prevRotationYaw:F
        //    59: f2d            
        //    60: aload           10
        //    62: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //    65: f2d            
        //    66: fload           9
        //    68: ldc             0.5
        //    70: fmul           
        //    71: f2d            
        //    72: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //    75: ldc2_w          0.01745329238474369
        //    78: dmul           
        //    79: dstore          13
        //    81: aload_0        
        //    82: aload           10
        //    84: getfield        net/minecraft/entity/Entity.prevRotationPitch:F
        //    87: f2d            
        //    88: aload           10
        //    90: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //    93: f2d            
        //    94: fload           9
        //    96: ldc             0.5
        //    98: fmul           
        //    99: f2d            
        //   100: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //   103: ldc2_w          0.01745329238474369
        //   106: dmul           
        //   107: dstore          15
        //   109: dload           13
        //   111: invokestatic    java/lang/Math.cos:(D)D
        //   114: dstore          17
        //   116: dload           13
        //   118: invokestatic    java/lang/Math.sin:(D)D
        //   121: dstore          19
        //   123: dload           15
        //   125: invokestatic    java/lang/Math.sin:(D)D
        //   128: dstore          21
        //   130: aload           10
        //   132: instanceof      Lnet/minecraft/entity/EntityHanging;
        //   135: ifeq            149
        //   138: dconst_0       
        //   139: dstore          17
        //   141: dconst_0       
        //   142: dstore          19
        //   144: ldc2_w          -1.0
        //   147: dstore          21
        //   149: dload           15
        //   151: invokestatic    java/lang/Math.cos:(D)D
        //   154: dstore          23
        //   156: aload_0        
        //   157: aload           10
        //   159: getfield        net/minecraft/entity/Entity.prevPosX:D
        //   162: aload           10
        //   164: getfield        net/minecraft/entity/Entity.posX:D
        //   167: fload           9
        //   169: f2d            
        //   170: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //   173: dload           17
        //   175: ldc2_w          0.7
        //   178: dmul           
        //   179: dsub           
        //   180: dload           19
        //   182: ldc2_w          0.5
        //   185: dmul           
        //   186: dload           23
        //   188: dmul           
        //   189: dsub           
        //   190: dstore          25
        //   192: aload_0        
        //   193: aload           10
        //   195: getfield        net/minecraft/entity/Entity.prevPosY:D
        //   198: aload           10
        //   200: invokevirtual   net/minecraft/entity/Entity.getEyeHeight:()F
        //   203: f2d            
        //   204: ldc2_w          0.7
        //   207: dmul           
        //   208: dadd           
        //   209: aload           10
        //   211: getfield        net/minecraft/entity/Entity.posY:D
        //   214: aload           10
        //   216: invokevirtual   net/minecraft/entity/Entity.getEyeHeight:()F
        //   219: f2d            
        //   220: ldc2_w          0.7
        //   223: dmul           
        //   224: dadd           
        //   225: fload           9
        //   227: f2d            
        //   228: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //   231: dload           21
        //   233: ldc2_w          0.5
        //   236: dmul           
        //   237: dsub           
        //   238: ldc2_w          0.25
        //   241: dsub           
        //   242: dstore          27
        //   244: aload_0        
        //   245: aload           10
        //   247: getfield        net/minecraft/entity/Entity.prevPosZ:D
        //   250: aload           10
        //   252: getfield        net/minecraft/entity/Entity.posZ:D
        //   255: fload           9
        //   257: f2d            
        //   258: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //   261: dload           19
        //   263: ldc2_w          0.7
        //   266: dmul           
        //   267: dsub           
        //   268: dload           17
        //   270: ldc2_w          0.5
        //   273: dmul           
        //   274: dload           23
        //   276: dmul           
        //   277: dadd           
        //   278: dstore          29
        //   280: aload_0        
        //   281: aload_1        
        //   282: getfield        net/minecraft/entity/EntityLiving.prevRenderYawOffset:F
        //   285: f2d            
        //   286: aload_1        
        //   287: getfield        net/minecraft/entity/EntityLiving.renderYawOffset:F
        //   290: f2d            
        //   291: fload           9
        //   293: f2d            
        //   294: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //   297: ldc2_w          0.01745329238474369
        //   300: dmul           
        //   301: ldc2_w          1.5707963267948966
        //   304: dadd           
        //   305: dstore          31
        //   307: dload           31
        //   309: invokestatic    java/lang/Math.cos:(D)D
        //   312: aload_1        
        //   313: getfield        net/minecraft/entity/EntityLiving.width:F
        //   316: f2d            
        //   317: dmul           
        //   318: ldc2_w          0.4
        //   321: dmul           
        //   322: dstore          17
        //   324: dload           31
        //   326: invokestatic    java/lang/Math.sin:(D)D
        //   329: aload_1        
        //   330: getfield        net/minecraft/entity/EntityLiving.width:F
        //   333: f2d            
        //   334: dmul           
        //   335: ldc2_w          0.4
        //   338: dmul           
        //   339: dstore          19
        //   341: aload_0        
        //   342: aload_1        
        //   343: getfield        net/minecraft/entity/EntityLiving.prevPosX:D
        //   346: aload_1        
        //   347: getfield        net/minecraft/entity/EntityLiving.posX:D
        //   350: fload           9
        //   352: f2d            
        //   353: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //   356: dload           17
        //   358: dadd           
        //   359: dstore          33
        //   361: aload_0        
        //   362: aload_1        
        //   363: getfield        net/minecraft/entity/EntityLiving.prevPosY:D
        //   366: aload_1        
        //   367: getfield        net/minecraft/entity/EntityLiving.posY:D
        //   370: fload           9
        //   372: f2d            
        //   373: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //   376: dstore          35
        //   378: aload_0        
        //   379: aload_1        
        //   380: getfield        net/minecraft/entity/EntityLiving.prevPosZ:D
        //   383: aload_1        
        //   384: getfield        net/minecraft/entity/EntityLiving.posZ:D
        //   387: fload           9
        //   389: f2d            
        //   390: invokespecial   net/minecraft/client/renderer/entity/RenderLiving.func_110828_a:(DDD)D
        //   393: dload           19
        //   395: dadd           
        //   396: dstore          37
        //   398: dload_2        
        //   399: dload           17
        //   401: dadd           
        //   402: dstore_2       
        //   403: dload           6
        //   405: dload           19
        //   407: dadd           
        //   408: dstore          6
        //   410: dload           25
        //   412: dload           33
        //   414: dsub           
        //   415: d2f            
        //   416: f2d            
        //   417: dstore          39
        //   419: dload           27
        //   421: dload           35
        //   423: dsub           
        //   424: d2f            
        //   425: f2d            
        //   426: dstore          41
        //   428: dload           29
        //   430: dload           37
        //   432: dsub           
        //   433: d2f            
        //   434: f2d            
        //   435: dstore          43
        //   437: invokestatic    optifine/Config.isShaders:()Z
        //   440: ldc2_w          0.025
        //   443: dstore          46
        //   445: aload           12
        //   447: iconst_5       
        //   448: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawing:(I)V
        //   451: goto            613
        //   454: iconst_0       
        //   455: ifne            473
        //   458: aload           12
        //   460: ldc             0.5
        //   462: ldc             0.4
        //   464: ldc             0.3
        //   466: fconst_1       
        //   467: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178960_a:(FFFF)V
        //   470: goto            485
        //   473: aload           12
        //   475: ldc             0.35
        //   477: ldc             0.28
        //   479: ldc             0.21000001
        //   481: fconst_1       
        //   482: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178960_a:(FFFF)V
        //   485: iconst_0       
        //   486: i2f            
        //   487: ldc             24.0
        //   489: fdiv           
        //   490: fstore          49
        //   492: aload           12
        //   494: dload_2        
        //   495: dload           39
        //   497: fload           49
        //   499: f2d            
        //   500: dmul           
        //   501: dadd           
        //   502: dconst_0       
        //   503: dadd           
        //   504: dload           4
        //   506: dload           41
        //   508: fload           49
        //   510: fload           49
        //   512: fmul           
        //   513: fload           49
        //   515: fadd           
        //   516: f2d            
        //   517: dmul           
        //   518: ldc2_w          0.5
        //   521: dmul           
        //   522: dadd           
        //   523: ldc             24.0
        //   525: iconst_0       
        //   526: i2f            
        //   527: fsub           
        //   528: ldc             18.0
        //   530: fdiv           
        //   531: ldc             0.125
        //   533: fadd           
        //   534: f2d            
        //   535: dadd           
        //   536: dload           6
        //   538: dload           43
        //   540: fload           49
        //   542: f2d            
        //   543: dmul           
        //   544: dadd           
        //   545: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   548: aload           12
        //   550: dload_2        
        //   551: dload           39
        //   553: fload           49
        //   555: f2d            
        //   556: dmul           
        //   557: dadd           
        //   558: ldc2_w          0.025
        //   561: dadd           
        //   562: dload           4
        //   564: dload           41
        //   566: fload           49
        //   568: fload           49
        //   570: fmul           
        //   571: fload           49
        //   573: fadd           
        //   574: f2d            
        //   575: dmul           
        //   576: ldc2_w          0.5
        //   579: dmul           
        //   580: dadd           
        //   581: ldc             24.0
        //   583: iconst_0       
        //   584: i2f            
        //   585: fsub           
        //   586: ldc             18.0
        //   588: fdiv           
        //   589: ldc             0.125
        //   591: fadd           
        //   592: f2d            
        //   593: dadd           
        //   594: ldc2_w          0.025
        //   597: dadd           
        //   598: dload           6
        //   600: dload           43
        //   602: fload           49
        //   604: f2d            
        //   605: dmul           
        //   606: dadd           
        //   607: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   610: iinc            48, 1
        //   613: iconst_0       
        //   614: bipush          24
        //   616: if_icmple       454
        //   619: aload           11
        //   621: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //   624: pop            
        //   625: aload           12
        //   627: iconst_5       
        //   628: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawing:(I)V
        //   631: goto            797
        //   634: iconst_0       
        //   635: ifne            653
        //   638: aload           12
        //   640: ldc             0.5
        //   642: ldc             0.4
        //   644: ldc             0.3
        //   646: fconst_1       
        //   647: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178960_a:(FFFF)V
        //   650: goto            665
        //   653: aload           12
        //   655: ldc             0.35
        //   657: ldc             0.28
        //   659: ldc             0.21000001
        //   661: fconst_1       
        //   662: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178960_a:(FFFF)V
        //   665: iconst_0       
        //   666: i2f            
        //   667: ldc             24.0
        //   669: fdiv           
        //   670: fstore          49
        //   672: aload           12
        //   674: dload_2        
        //   675: dload           39
        //   677: fload           49
        //   679: f2d            
        //   680: dmul           
        //   681: dadd           
        //   682: dconst_0       
        //   683: dadd           
        //   684: dload           4
        //   686: dload           41
        //   688: fload           49
        //   690: fload           49
        //   692: fmul           
        //   693: fload           49
        //   695: fadd           
        //   696: f2d            
        //   697: dmul           
        //   698: ldc2_w          0.5
        //   701: dmul           
        //   702: dadd           
        //   703: ldc             24.0
        //   705: iconst_0       
        //   706: i2f            
        //   707: fsub           
        //   708: ldc             18.0
        //   710: fdiv           
        //   711: ldc             0.125
        //   713: fadd           
        //   714: f2d            
        //   715: dadd           
        //   716: ldc2_w          0.025
        //   719: dadd           
        //   720: dload           6
        //   722: dload           43
        //   724: fload           49
        //   726: f2d            
        //   727: dmul           
        //   728: dadd           
        //   729: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   732: aload           12
        //   734: dload_2        
        //   735: dload           39
        //   737: fload           49
        //   739: f2d            
        //   740: dmul           
        //   741: dadd           
        //   742: ldc2_w          0.025
        //   745: dadd           
        //   746: dload           4
        //   748: dload           41
        //   750: fload           49
        //   752: fload           49
        //   754: fmul           
        //   755: fload           49
        //   757: fadd           
        //   758: f2d            
        //   759: dmul           
        //   760: ldc2_w          0.5
        //   763: dmul           
        //   764: dadd           
        //   765: ldc             24.0
        //   767: iconst_0       
        //   768: i2f            
        //   769: fsub           
        //   770: ldc             18.0
        //   772: fdiv           
        //   773: ldc             0.125
        //   775: fadd           
        //   776: f2d            
        //   777: dadd           
        //   778: dload           6
        //   780: dload           43
        //   782: fload           49
        //   784: f2d            
        //   785: dmul           
        //   786: dadd           
        //   787: ldc2_w          0.025
        //   790: dadd           
        //   791: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertex:(DDD)V
        //   794: iinc            48, 1
        //   797: iconst_0       
        //   798: bipush          24
        //   800: if_icmple       634
        //   803: aload           11
        //   805: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //   808: pop            
        //   809: invokestatic    optifine/Config.isShaders:()Z
        //   812: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0812 (coming from #0809).
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
    
    protected boolean canRenderName(final EntityLivingBase entityLivingBase) {
        return this.canRenderName((EntityLiving)entityLivingBase);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLiving)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    protected boolean func_177070_b(final Entity entity) {
        return this.canRenderName((EntityLiving)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLiving)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    public boolean func_177071_a(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        return this.func_177104_a((EntityLiving)entity, camera, n, n2, n3);
    }
    
    static {
        __OBFID = "CL_00001015";
    }
}
