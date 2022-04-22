package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelDragon extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer spine;
    private ModelRenderer jaw;
    private ModelRenderer body;
    private ModelRenderer rearLeg;
    private ModelRenderer frontLeg;
    private ModelRenderer rearLegTip;
    private ModelRenderer frontLegTip;
    private ModelRenderer rearFoot;
    private ModelRenderer frontFoot;
    private ModelRenderer wing;
    private ModelRenderer wingTip;
    private float partialTicks;
    private static final String __OBFID;
    
    public ModelDragon(final float n) {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.setTextureOffset("body.body", 0, 0);
        this.setTextureOffset("wing.skin", -56, 88);
        this.setTextureOffset("wingtip.skin", -56, 144);
        this.setTextureOffset("rearleg.main", 0, 0);
        this.setTextureOffset("rearfoot.main", 112, 0);
        this.setTextureOffset("rearlegtip.main", 196, 0);
        this.setTextureOffset("head.upperhead", 112, 30);
        this.setTextureOffset("wing.bone", 112, 88);
        this.setTextureOffset("head.upperlip", 176, 44);
        this.setTextureOffset("jaw.jaw", 176, 65);
        this.setTextureOffset("frontleg.main", 112, 104);
        this.setTextureOffset("wingtip.bone", 112, 136);
        this.setTextureOffset("frontfoot.main", 144, 104);
        this.setTextureOffset("neck.box", 192, 104);
        this.setTextureOffset("frontlegtip.main", 226, 138);
        this.setTextureOffset("body.scale", 220, 53);
        this.setTextureOffset("head.scale", 0, 0);
        this.setTextureOffset("neck.scale", 48, 0);
        this.setTextureOffset("head.nostril", 112, 0);
        final float n2 = -16.0f;
        (this.head = new ModelRenderer(this, "head")).addBox("upperlip", -6.0f, -1.0f, -8.0f + n2, 12, 5, 16);
        this.head.addBox("upperhead", -8.0f, -8.0f, 6.0f + n2, 16, 16, 16);
        this.head.mirror = true;
        this.head.addBox("scale", -5.0f, -12.0f, 12.0f + n2, 2, 4, 6);
        this.head.addBox("nostril", -5.0f, -3.0f, -6.0f + n2, 2, 2, 4);
        this.head.mirror = false;
        this.head.addBox("scale", 3.0f, -12.0f, 12.0f + n2, 2, 4, 6);
        this.head.addBox("nostril", 3.0f, -3.0f, -6.0f + n2, 2, 2, 4);
        (this.jaw = new ModelRenderer(this, "jaw")).setRotationPoint(0.0f, 4.0f, 8.0f + n2);
        this.jaw.addBox("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16);
        this.head.addChild(this.jaw);
        (this.spine = new ModelRenderer(this, "neck")).addBox("box", -5.0f, -5.0f, -5.0f, 10, 10, 10);
        this.spine.addBox("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6);
        (this.body = new ModelRenderer(this, "body")).setRotationPoint(0.0f, 4.0f, 8.0f);
        this.body.addBox("body", -12.0f, 0.0f, -16.0f, 24, 24, 64);
        this.body.addBox("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12);
        this.body.addBox("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12);
        this.body.addBox("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12);
        (this.wing = new ModelRenderer(this, "wing")).setRotationPoint(-12.0f, 5.0f, 2.0f);
        this.wing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        this.wing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        (this.wingTip = new ModelRenderer(this, "wingtip")).setRotationPoint(-56.0f, 0.0f, 0.0f);
        this.wingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        this.wingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        this.wing.addChild(this.wingTip);
        (this.frontLeg = new ModelRenderer(this, "frontleg")).setRotationPoint(-12.0f, 20.0f, 2.0f);
        this.frontLeg.addBox("main", -4.0f, -4.0f, -4.0f, 8, 24, 8);
        (this.frontLegTip = new ModelRenderer(this, "frontlegtip")).setRotationPoint(0.0f, 20.0f, -1.0f);
        this.frontLegTip.addBox("main", -3.0f, -1.0f, -3.0f, 6, 24, 6);
        this.frontLeg.addChild(this.frontLegTip);
        (this.frontFoot = new ModelRenderer(this, "frontfoot")).setRotationPoint(0.0f, 23.0f, 0.0f);
        this.frontFoot.addBox("main", -4.0f, 0.0f, -12.0f, 8, 4, 16);
        this.frontLegTip.addChild(this.frontFoot);
        (this.rearLeg = new ModelRenderer(this, "rearleg")).setRotationPoint(-16.0f, 16.0f, 42.0f);
        this.rearLeg.addBox("main", -8.0f, -4.0f, -8.0f, 16, 32, 16);
        (this.rearLegTip = new ModelRenderer(this, "rearlegtip")).setRotationPoint(0.0f, 32.0f, -4.0f);
        this.rearLegTip.addBox("main", -6.0f, -2.0f, 0.0f, 12, 32, 12);
        this.rearLeg.addChild(this.rearLegTip);
        (this.rearFoot = new ModelRenderer(this, "rearfoot")).setRotationPoint(0.0f, 31.0f, 4.0f);
        this.rearFoot.addBox("main", -9.0f, 0.0f, -20.0f, 18, 6, 24);
        this.rearLegTip.addChild(this.rearFoot);
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    @Override
    public void render(final Entity p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: checkcast       Lnet/minecraft/entity/boss/EntityDragon;
        //     4: astore          8
        //     6: aload           8
        //     8: getfield        net/minecraft/entity/boss/EntityDragon.prevAnimTime:F
        //    11: aload           8
        //    13: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //    16: aload           8
        //    18: getfield        net/minecraft/entity/boss/EntityDragon.prevAnimTime:F
        //    21: fsub           
        //    22: aload_0        
        //    23: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //    26: fmul           
        //    27: fadd           
        //    28: fstore          9
        //    30: aload_0        
        //    31: getfield        net/minecraft/client/model/ModelDragon.jaw:Lnet/minecraft/client/model/ModelRenderer;
        //    34: fload           9
        //    36: ldc             3.1415927
        //    38: fmul           
        //    39: fconst_2       
        //    40: fmul           
        //    41: f2d            
        //    42: invokestatic    java/lang/Math.sin:(D)D
        //    45: dconst_1       
        //    46: dadd           
        //    47: d2f            
        //    48: ldc             0.2
        //    50: fmul           
        //    51: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //    54: fload           9
        //    56: ldc             3.1415927
        //    58: fmul           
        //    59: fconst_2       
        //    60: fmul           
        //    61: fconst_1       
        //    62: fsub           
        //    63: f2d            
        //    64: invokestatic    java/lang/Math.sin:(D)D
        //    67: dconst_1       
        //    68: dadd           
        //    69: d2f            
        //    70: fstore          10
        //    72: fload           10
        //    74: fload           10
        //    76: fmul           
        //    77: fconst_1       
        //    78: fmul           
        //    79: fload           10
        //    81: fconst_2       
        //    82: fmul           
        //    83: fadd           
        //    84: ldc             0.05
        //    86: fmul           
        //    87: fstore          10
        //    89: fconst_0       
        //    90: fload           10
        //    92: fconst_2       
        //    93: fsub           
        //    94: ldc             -3.0
        //    96: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    99: fload           10
        //   101: fconst_2       
        //   102: fmul           
        //   103: fconst_1       
        //   104: fconst_0       
        //   105: fconst_0       
        //   106: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   109: ldc             -30.0
        //   111: fstore          11
        //   113: fconst_0       
        //   114: fstore          12
        //   116: ldc             1.5
        //   118: fstore          13
        //   120: aload           8
        //   122: bipush          6
        //   124: aload_0        
        //   125: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //   128: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //   131: astore          14
        //   133: aload_0        
        //   134: aload           8
        //   136: iconst_5       
        //   137: aload_0        
        //   138: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //   141: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //   144: iconst_0       
        //   145: daload         
        //   146: aload           8
        //   148: bipush          10
        //   150: aload_0        
        //   151: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //   154: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //   157: iconst_0       
        //   158: daload         
        //   159: dsub           
        //   160: invokespecial   net/minecraft/client/model/ModelDragon.updateRotations:(D)F
        //   163: fstore          15
        //   165: aload_0        
        //   166: aload           8
        //   168: iconst_5       
        //   169: aload_0        
        //   170: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //   173: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //   176: iconst_0       
        //   177: daload         
        //   178: fload           15
        //   180: fconst_2       
        //   181: fdiv           
        //   182: f2d            
        //   183: dadd           
        //   184: invokespecial   net/minecraft/client/model/ModelDragon.updateRotations:(D)F
        //   187: fstore          16
        //   189: fload           11
        //   191: fconst_2       
        //   192: fadd           
        //   193: fstore          11
        //   195: fload           9
        //   197: ldc             3.1415927
        //   199: fmul           
        //   200: fconst_2       
        //   201: fmul           
        //   202: fstore          17
        //   204: ldc             20.0
        //   206: fstore          11
        //   208: ldc             -12.0
        //   210: fstore          18
        //   212: goto            464
        //   215: aload           8
        //   217: iconst_5       
        //   218: aload_0        
        //   219: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //   222: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //   225: astore          21
        //   227: iconst_0       
        //   228: i2f            
        //   229: ldc             0.45
        //   231: fmul           
        //   232: fload           17
        //   234: fadd           
        //   235: f2d            
        //   236: invokestatic    java/lang/Math.cos:(D)D
        //   239: d2f            
        //   240: ldc             0.15
        //   242: fmul           
        //   243: fstore          19
        //   245: aload_0        
        //   246: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   249: aload_0        
        //   250: aload           21
        //   252: iconst_0       
        //   253: daload         
        //   254: aload           14
        //   256: iconst_0       
        //   257: daload         
        //   258: dsub           
        //   259: invokespecial   net/minecraft/client/model/ModelDragon.updateRotations:(D)F
        //   262: ldc             3.1415927
        //   264: fmul           
        //   265: ldc             180.0
        //   267: fdiv           
        //   268: fload           13
        //   270: fmul           
        //   271: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //   274: aload_0        
        //   275: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   278: fload           19
        //   280: aload           21
        //   282: iconst_1       
        //   283: daload         
        //   284: aload           14
        //   286: iconst_1       
        //   287: daload         
        //   288: dsub           
        //   289: d2f            
        //   290: ldc             3.1415927
        //   292: fmul           
        //   293: ldc             180.0
        //   295: fdiv           
        //   296: fload           13
        //   298: fmul           
        //   299: ldc             5.0
        //   301: fmul           
        //   302: fadd           
        //   303: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   306: aload_0        
        //   307: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   310: aload_0        
        //   311: aload           21
        //   313: iconst_0       
        //   314: daload         
        //   315: fload           16
        //   317: f2d            
        //   318: dsub           
        //   319: invokespecial   net/minecraft/client/model/ModelDragon.updateRotations:(D)F
        //   322: fneg           
        //   323: ldc             3.1415927
        //   325: fmul           
        //   326: ldc             180.0
        //   328: fdiv           
        //   329: fload           13
        //   331: fmul           
        //   332: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleZ:F
        //   335: aload_0        
        //   336: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   339: fload           11
        //   341: putfield        net/minecraft/client/model/ModelRenderer.rotationPointY:F
        //   344: aload_0        
        //   345: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   348: fload           18
        //   350: putfield        net/minecraft/client/model/ModelRenderer.rotationPointZ:F
        //   353: aload_0        
        //   354: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   357: fload           12
        //   359: putfield        net/minecraft/client/model/ModelRenderer.rotationPointX:F
        //   362: fload           11
        //   364: f2d            
        //   365: aload_0        
        //   366: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   369: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   372: f2d            
        //   373: invokestatic    java/lang/Math.sin:(D)D
        //   376: ldc2_w          10.0
        //   379: dmul           
        //   380: dadd           
        //   381: d2f            
        //   382: fstore          11
        //   384: fload           18
        //   386: f2d            
        //   387: aload_0        
        //   388: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   391: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //   394: f2d            
        //   395: invokestatic    java/lang/Math.cos:(D)D
        //   398: aload_0        
        //   399: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   402: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   405: f2d            
        //   406: invokestatic    java/lang/Math.cos:(D)D
        //   409: dmul           
        //   410: ldc2_w          10.0
        //   413: dmul           
        //   414: dsub           
        //   415: d2f            
        //   416: fstore          18
        //   418: fload           12
        //   420: f2d            
        //   421: aload_0        
        //   422: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   425: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //   428: f2d            
        //   429: invokestatic    java/lang/Math.sin:(D)D
        //   432: aload_0        
        //   433: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   436: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   439: f2d            
        //   440: invokestatic    java/lang/Math.cos:(D)D
        //   443: dmul           
        //   444: ldc2_w          10.0
        //   447: dmul           
        //   448: dsub           
        //   449: d2f            
        //   450: fstore          12
        //   452: aload_0        
        //   453: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   456: fload           7
        //   458: invokevirtual   net/minecraft/client/model/ModelRenderer.render:(F)V
        //   461: iinc            20, 1
        //   464: iconst_0       
        //   465: iconst_5       
        //   466: if_icmplt       215
        //   469: aload_0        
        //   470: getfield        net/minecraft/client/model/ModelDragon.head:Lnet/minecraft/client/model/ModelRenderer;
        //   473: fload           11
        //   475: putfield        net/minecraft/client/model/ModelRenderer.rotationPointY:F
        //   478: aload_0        
        //   479: getfield        net/minecraft/client/model/ModelDragon.head:Lnet/minecraft/client/model/ModelRenderer;
        //   482: fload           18
        //   484: putfield        net/minecraft/client/model/ModelRenderer.rotationPointZ:F
        //   487: aload_0        
        //   488: getfield        net/minecraft/client/model/ModelDragon.head:Lnet/minecraft/client/model/ModelRenderer;
        //   491: fload           12
        //   493: putfield        net/minecraft/client/model/ModelRenderer.rotationPointX:F
        //   496: aload           8
        //   498: iconst_0       
        //   499: aload_0        
        //   500: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //   503: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //   506: astore          20
        //   508: aload_0        
        //   509: getfield        net/minecraft/client/model/ModelDragon.head:Lnet/minecraft/client/model/ModelRenderer;
        //   512: aload_0        
        //   513: aload           20
        //   515: iconst_0       
        //   516: daload         
        //   517: aload           14
        //   519: iconst_0       
        //   520: daload         
        //   521: dsub           
        //   522: invokespecial   net/minecraft/client/model/ModelDragon.updateRotations:(D)F
        //   525: ldc             3.1415927
        //   527: fmul           
        //   528: ldc             180.0
        //   530: fdiv           
        //   531: fconst_1       
        //   532: fmul           
        //   533: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //   536: aload_0        
        //   537: getfield        net/minecraft/client/model/ModelDragon.head:Lnet/minecraft/client/model/ModelRenderer;
        //   540: aload_0        
        //   541: aload           20
        //   543: iconst_0       
        //   544: daload         
        //   545: fload           16
        //   547: f2d            
        //   548: dsub           
        //   549: invokespecial   net/minecraft/client/model/ModelDragon.updateRotations:(D)F
        //   552: fneg           
        //   553: ldc             3.1415927
        //   555: fmul           
        //   556: ldc             180.0
        //   558: fdiv           
        //   559: fconst_1       
        //   560: fmul           
        //   561: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleZ:F
        //   564: aload_0        
        //   565: getfield        net/minecraft/client/model/ModelDragon.head:Lnet/minecraft/client/model/ModelRenderer;
        //   568: fload           7
        //   570: invokevirtual   net/minecraft/client/model/ModelRenderer.render:(F)V
        //   573: fconst_0       
        //   574: fconst_1       
        //   575: fconst_0       
        //   576: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   579: fload           15
        //   581: fneg           
        //   582: fload           13
        //   584: fmul           
        //   585: fconst_1       
        //   586: fmul           
        //   587: fconst_0       
        //   588: fconst_0       
        //   589: fconst_1       
        //   590: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   593: fconst_0       
        //   594: ldc             -1.0
        //   596: fconst_0       
        //   597: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   600: aload_0        
        //   601: getfield        net/minecraft/client/model/ModelDragon.body:Lnet/minecraft/client/model/ModelRenderer;
        //   604: fconst_0       
        //   605: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleZ:F
        //   608: aload_0        
        //   609: getfield        net/minecraft/client/model/ModelDragon.body:Lnet/minecraft/client/model/ModelRenderer;
        //   612: fload           7
        //   614: invokevirtual   net/minecraft/client/model/ModelRenderer.render:(F)V
        //   617: goto            852
        //   620: fload           9
        //   622: ldc             3.1415927
        //   624: fmul           
        //   625: fconst_2       
        //   626: fmul           
        //   627: fstore          19
        //   629: aload_0        
        //   630: getfield        net/minecraft/client/model/ModelDragon.wing:Lnet/minecraft/client/model/ModelRenderer;
        //   633: ldc             0.125
        //   635: fload           19
        //   637: f2d            
        //   638: invokestatic    java/lang/Math.cos:(D)D
        //   641: d2f            
        //   642: ldc             0.2
        //   644: fmul           
        //   645: fsub           
        //   646: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   649: aload_0        
        //   650: getfield        net/minecraft/client/model/ModelDragon.wing:Lnet/minecraft/client/model/ModelRenderer;
        //   653: ldc             0.25
        //   655: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //   658: aload_0        
        //   659: getfield        net/minecraft/client/model/ModelDragon.wing:Lnet/minecraft/client/model/ModelRenderer;
        //   662: fload           19
        //   664: f2d            
        //   665: invokestatic    java/lang/Math.sin:(D)D
        //   668: ldc2_w          0.125
        //   671: dadd           
        //   672: d2f            
        //   673: ldc_w           0.8
        //   676: fmul           
        //   677: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleZ:F
        //   680: aload_0        
        //   681: getfield        net/minecraft/client/model/ModelDragon.wingTip:Lnet/minecraft/client/model/ModelRenderer;
        //   684: fload           19
        //   686: fconst_2       
        //   687: fadd           
        //   688: f2d            
        //   689: invokestatic    java/lang/Math.sin:(D)D
        //   692: ldc2_w          0.5
        //   695: dadd           
        //   696: d2f            
        //   697: fneg           
        //   698: ldc_w           0.75
        //   701: fmul           
        //   702: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleZ:F
        //   705: aload_0        
        //   706: getfield        net/minecraft/client/model/ModelDragon.rearLeg:Lnet/minecraft/client/model/ModelRenderer;
        //   709: fconst_1       
        //   710: fload           10
        //   712: ldc_w           0.1
        //   715: fmul           
        //   716: fadd           
        //   717: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   720: aload_0        
        //   721: getfield        net/minecraft/client/model/ModelDragon.rearLegTip:Lnet/minecraft/client/model/ModelRenderer;
        //   724: ldc_w           0.5
        //   727: fload           10
        //   729: ldc_w           0.1
        //   732: fmul           
        //   733: fadd           
        //   734: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   737: aload_0        
        //   738: getfield        net/minecraft/client/model/ModelDragon.rearFoot:Lnet/minecraft/client/model/ModelRenderer;
        //   741: ldc_w           0.75
        //   744: fload           10
        //   746: ldc_w           0.1
        //   749: fmul           
        //   750: fadd           
        //   751: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   754: aload_0        
        //   755: getfield        net/minecraft/client/model/ModelDragon.frontLeg:Lnet/minecraft/client/model/ModelRenderer;
        //   758: ldc_w           1.3
        //   761: fload           10
        //   763: ldc_w           0.1
        //   766: fmul           
        //   767: fadd           
        //   768: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   771: aload_0        
        //   772: getfield        net/minecraft/client/model/ModelDragon.frontLegTip:Lnet/minecraft/client/model/ModelRenderer;
        //   775: ldc_w           -0.5
        //   778: fload           10
        //   780: ldc_w           0.1
        //   783: fmul           
        //   784: fsub           
        //   785: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   788: aload_0        
        //   789: getfield        net/minecraft/client/model/ModelDragon.frontFoot:Lnet/minecraft/client/model/ModelRenderer;
        //   792: ldc_w           0.75
        //   795: fload           10
        //   797: ldc_w           0.1
        //   800: fmul           
        //   801: fadd           
        //   802: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   805: aload_0        
        //   806: getfield        net/minecraft/client/model/ModelDragon.wing:Lnet/minecraft/client/model/ModelRenderer;
        //   809: fload           7
        //   811: invokevirtual   net/minecraft/client/model/ModelRenderer.render:(F)V
        //   814: aload_0        
        //   815: getfield        net/minecraft/client/model/ModelDragon.frontLeg:Lnet/minecraft/client/model/ModelRenderer;
        //   818: fload           7
        //   820: invokevirtual   net/minecraft/client/model/ModelRenderer.render:(F)V
        //   823: aload_0        
        //   824: getfield        net/minecraft/client/model/ModelDragon.rearLeg:Lnet/minecraft/client/model/ModelRenderer;
        //   827: fload           7
        //   829: invokevirtual   net/minecraft/client/model/ModelRenderer.render:(F)V
        //   832: ldc             -1.0
        //   834: fconst_1       
        //   835: fconst_1       
        //   836: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //   839: iconst_0       
        //   840: ifne            849
        //   843: sipush          1028
        //   846: invokestatic    net/minecraft/client/renderer/GlStateManager.cullFace:(I)V
        //   849: iinc            21, 1
        //   852: iconst_0       
        //   853: iconst_2       
        //   854: if_icmplt       620
        //   857: sipush          1029
        //   860: invokestatic    net/minecraft/client/renderer/GlStateManager.cullFace:(I)V
        //   863: fload           9
        //   865: ldc             3.1415927
        //   867: fmul           
        //   868: fconst_2       
        //   869: fmul           
        //   870: f2d            
        //   871: invokestatic    java/lang/Math.sin:(D)D
        //   874: d2f            
        //   875: fneg           
        //   876: fconst_0       
        //   877: fmul           
        //   878: fstore          21
        //   880: fload           9
        //   882: ldc             3.1415927
        //   884: fmul           
        //   885: fconst_2       
        //   886: fmul           
        //   887: fstore          17
        //   889: ldc             10.0
        //   891: fstore          11
        //   893: ldc_w           60.0
        //   896: fstore          18
        //   898: fconst_0       
        //   899: fstore          12
        //   901: aload           8
        //   903: bipush          11
        //   905: aload_0        
        //   906: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //   909: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //   912: astore          14
        //   914: goto            1174
        //   917: aload           8
        //   919: bipush          12
        //   921: aload_0        
        //   922: getfield        net/minecraft/client/model/ModelDragon.partialTicks:F
        //   925: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //   928: astore          20
        //   930: fload           21
        //   932: f2d            
        //   933: iconst_0       
        //   934: i2f            
        //   935: ldc             0.45
        //   937: fmul           
        //   938: fload           17
        //   940: fadd           
        //   941: f2d            
        //   942: invokestatic    java/lang/Math.sin:(D)D
        //   945: ldc2_w          0.05000000074505806
        //   948: dmul           
        //   949: dadd           
        //   950: d2f            
        //   951: fstore          21
        //   953: aload_0        
        //   954: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   957: aload_0        
        //   958: aload           20
        //   960: iconst_0       
        //   961: daload         
        //   962: aload           14
        //   964: iconst_0       
        //   965: daload         
        //   966: dsub           
        //   967: invokespecial   net/minecraft/client/model/ModelDragon.updateRotations:(D)F
        //   970: fload           13
        //   972: fmul           
        //   973: ldc             180.0
        //   975: fadd           
        //   976: ldc             3.1415927
        //   978: fmul           
        //   979: ldc             180.0
        //   981: fdiv           
        //   982: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //   985: aload_0        
        //   986: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //   989: fload           21
        //   991: aload           20
        //   993: iconst_1       
        //   994: daload         
        //   995: aload           14
        //   997: iconst_1       
        //   998: daload         
        //   999: dsub           
        //  1000: d2f            
        //  1001: ldc             3.1415927
        //  1003: fmul           
        //  1004: ldc             180.0
        //  1006: fdiv           
        //  1007: fload           13
        //  1009: fmul           
        //  1010: ldc             5.0
        //  1012: fmul           
        //  1013: fadd           
        //  1014: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //  1017: aload_0        
        //  1018: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1021: aload_0        
        //  1022: aload           20
        //  1024: iconst_0       
        //  1025: daload         
        //  1026: fload           16
        //  1028: f2d            
        //  1029: dsub           
        //  1030: invokespecial   net/minecraft/client/model/ModelDragon.updateRotations:(D)F
        //  1033: ldc             3.1415927
        //  1035: fmul           
        //  1036: ldc             180.0
        //  1038: fdiv           
        //  1039: fload           13
        //  1041: fmul           
        //  1042: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleZ:F
        //  1045: aload_0        
        //  1046: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1049: fload           11
        //  1051: putfield        net/minecraft/client/model/ModelRenderer.rotationPointY:F
        //  1054: aload_0        
        //  1055: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1058: fload           18
        //  1060: putfield        net/minecraft/client/model/ModelRenderer.rotationPointZ:F
        //  1063: aload_0        
        //  1064: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1067: fload           12
        //  1069: putfield        net/minecraft/client/model/ModelRenderer.rotationPointX:F
        //  1072: fload           11
        //  1074: f2d            
        //  1075: aload_0        
        //  1076: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1079: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //  1082: f2d            
        //  1083: invokestatic    java/lang/Math.sin:(D)D
        //  1086: ldc2_w          10.0
        //  1089: dmul           
        //  1090: dadd           
        //  1091: d2f            
        //  1092: fstore          11
        //  1094: fload           18
        //  1096: f2d            
        //  1097: aload_0        
        //  1098: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1101: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //  1104: f2d            
        //  1105: invokestatic    java/lang/Math.cos:(D)D
        //  1108: aload_0        
        //  1109: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1112: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //  1115: f2d            
        //  1116: invokestatic    java/lang/Math.cos:(D)D
        //  1119: dmul           
        //  1120: ldc2_w          10.0
        //  1123: dmul           
        //  1124: dsub           
        //  1125: d2f            
        //  1126: fstore          18
        //  1128: fload           12
        //  1130: f2d            
        //  1131: aload_0        
        //  1132: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1135: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //  1138: f2d            
        //  1139: invokestatic    java/lang/Math.sin:(D)D
        //  1142: aload_0        
        //  1143: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1146: getfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //  1149: f2d            
        //  1150: invokestatic    java/lang/Math.cos:(D)D
        //  1153: dmul           
        //  1154: ldc2_w          10.0
        //  1157: dmul           
        //  1158: dsub           
        //  1159: d2f            
        //  1160: fstore          12
        //  1162: aload_0        
        //  1163: getfield        net/minecraft/client/model/ModelDragon.spine:Lnet/minecraft/client/model/ModelRenderer;
        //  1166: fload           7
        //  1168: invokevirtual   net/minecraft/client/model/ModelRenderer.render:(F)V
        //  1171: iinc            22, 1
        //  1174: iconst_0       
        //  1175: bipush          12
        //  1177: if_icmplt       917
        //  1180: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private float updateRotations(double n) {
        while (n >= 180.0) {
            n -= 360.0;
        }
        while (n < -180.0) {
            n += 360.0;
        }
        return (float)n;
    }
    
    static {
        __OBFID = "CL_00000870";
    }
}
