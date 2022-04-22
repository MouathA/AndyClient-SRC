package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public abstract class EntityFireball extends Entity
{
    private int field_145795_e;
    private int field_145793_f;
    private int field_145794_g;
    private Block field_145796_h;
    private boolean inGround;
    public EntityLivingBase shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    private static final String __OBFID;
    
    public EntityFireball(final World world) {
        super(world);
        this.field_145795_e = -1;
        this.field_145793_f = -1;
        this.field_145794_g = -1;
        this.setSize(1.0f, 1.0f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        final double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0 * 64.0;
        return n < n2 * n2;
    }
    
    public EntityFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world);
        this.field_145795_e = -1;
        this.field_145793_f = -1;
        this.field_145794_g = -1;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(n, n2, n3, this.rotationYaw, this.rotationPitch);
        this.setPosition(n, n2, n3);
        final double n7 = MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
        this.accelerationX = n4 / n7 * 0.1;
        this.accelerationY = n5 / n7 * 0.1;
        this.accelerationZ = n6 / n7 * 0.1;
    }
    
    public EntityFireball(final World world, final EntityLivingBase shootingEntity, double n, double n2, double n3) {
        super(world);
        this.field_145795_e = -1;
        this.field_145793_f = -1;
        this.field_145794_g = -1;
        this.shootingEntity = shootingEntity;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(shootingEntity.posX, shootingEntity.posY, shootingEntity.posZ, shootingEntity.rotationYaw, shootingEntity.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        n += this.rand.nextGaussian() * 0.4;
        n2 += this.rand.nextGaussian() * 0.4;
        n3 += this.rand.nextGaussian() * 0.4;
        final double n4 = MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
        this.accelerationX = n / n4 * 0.1;
        this.accelerationY = n2 / n4 * 0.1;
        this.accelerationZ = n3 / n4 * 0.1;
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifne            52
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/projectile/EntityFireball.shootingEntity:Lnet/minecraft/entity/EntityLivingBase;
        //    14: ifnull          27
        //    17: aload_0        
        //    18: getfield        net/minecraft/entity/projectile/EntityFireball.shootingEntity:Lnet/minecraft/entity/EntityLivingBase;
        //    21: getfield        net/minecraft/entity/EntityLivingBase.isDead:Z
        //    24: ifne            45
        //    27: aload_0        
        //    28: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //    31: new             Lnet/minecraft/util/BlockPos;
        //    34: dup            
        //    35: aload_0        
        //    36: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //    39: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //    42: ifne            52
        //    45: aload_0        
        //    46: invokevirtual   net/minecraft/entity/projectile/EntityFireball.setDead:()V
        //    49: goto            1072
        //    52: aload_0        
        //    53: invokespecial   net/minecraft/entity/Entity.onUpdate:()V
        //    56: aload_0        
        //    57: iconst_1       
        //    58: invokevirtual   net/minecraft/entity/projectile/EntityFireball.setFire:(I)V
        //    61: aload_0        
        //    62: getfield        net/minecraft/entity/projectile/EntityFireball.inGround:Z
        //    65: ifeq            209
        //    68: aload_0        
        //    69: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //    72: new             Lnet/minecraft/util/BlockPos;
        //    75: dup            
        //    76: aload_0        
        //    77: getfield        net/minecraft/entity/projectile/EntityFireball.field_145795_e:I
        //    80: aload_0        
        //    81: getfield        net/minecraft/entity/projectile/EntityFireball.field_145793_f:I
        //    84: aload_0        
        //    85: getfield        net/minecraft/entity/projectile/EntityFireball.field_145794_g:I
        //    88: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    91: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    94: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    99: aload_0        
        //   100: getfield        net/minecraft/entity/projectile/EntityFireball.field_145796_h:Lnet/minecraft/block/Block;
        //   103: if_acmpne       131
        //   106: aload_0        
        //   107: dup            
        //   108: getfield        net/minecraft/entity/projectile/EntityFireball.ticksAlive:I
        //   111: iconst_1       
        //   112: iadd           
        //   113: putfield        net/minecraft/entity/projectile/EntityFireball.ticksAlive:I
        //   116: aload_0        
        //   117: getfield        net/minecraft/entity/projectile/EntityFireball.ticksAlive:I
        //   120: sipush          600
        //   123: if_icmpne       130
        //   126: aload_0        
        //   127: invokevirtual   net/minecraft/entity/projectile/EntityFireball.setDead:()V
        //   130: return         
        //   131: aload_0        
        //   132: iconst_0       
        //   133: putfield        net/minecraft/entity/projectile/EntityFireball.inGround:Z
        //   136: aload_0        
        //   137: dup            
        //   138: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   141: aload_0        
        //   142: getfield        net/minecraft/entity/projectile/EntityFireball.rand:Ljava/util/Random;
        //   145: invokevirtual   java/util/Random.nextFloat:()F
        //   148: ldc             0.2
        //   150: fmul           
        //   151: f2d            
        //   152: dmul           
        //   153: putfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   156: aload_0        
        //   157: dup            
        //   158: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   161: aload_0        
        //   162: getfield        net/minecraft/entity/projectile/EntityFireball.rand:Ljava/util/Random;
        //   165: invokevirtual   java/util/Random.nextFloat:()F
        //   168: ldc             0.2
        //   170: fmul           
        //   171: f2d            
        //   172: dmul           
        //   173: putfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   176: aload_0        
        //   177: dup            
        //   178: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   181: aload_0        
        //   182: getfield        net/minecraft/entity/projectile/EntityFireball.rand:Ljava/util/Random;
        //   185: invokevirtual   java/util/Random.nextFloat:()F
        //   188: ldc             0.2
        //   190: fmul           
        //   191: f2d            
        //   192: dmul           
        //   193: putfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   196: aload_0        
        //   197: iconst_0       
        //   198: putfield        net/minecraft/entity/projectile/EntityFireball.ticksAlive:I
        //   201: aload_0        
        //   202: iconst_0       
        //   203: putfield        net/minecraft/entity/projectile/EntityFireball.ticksInAir:I
        //   206: goto            219
        //   209: aload_0        
        //   210: dup            
        //   211: getfield        net/minecraft/entity/projectile/EntityFireball.ticksInAir:I
        //   214: iconst_1       
        //   215: iadd           
        //   216: putfield        net/minecraft/entity/projectile/EntityFireball.ticksInAir:I
        //   219: new             Lnet/minecraft/util/Vec3;
        //   222: dup            
        //   223: aload_0        
        //   224: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   227: aload_0        
        //   228: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   231: aload_0        
        //   232: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   235: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   238: astore_1       
        //   239: new             Lnet/minecraft/util/Vec3;
        //   242: dup            
        //   243: aload_0        
        //   244: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   247: aload_0        
        //   248: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   251: dadd           
        //   252: aload_0        
        //   253: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   256: aload_0        
        //   257: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   260: dadd           
        //   261: aload_0        
        //   262: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   265: aload_0        
        //   266: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   269: dadd           
        //   270: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   273: astore_2       
        //   274: aload_0        
        //   275: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //   278: aload_1        
        //   279: aload_2        
        //   280: invokevirtual   net/minecraft/world/World.rayTraceBlocks:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   283: astore_3       
        //   284: new             Lnet/minecraft/util/Vec3;
        //   287: dup            
        //   288: aload_0        
        //   289: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   292: aload_0        
        //   293: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   296: aload_0        
        //   297: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   300: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   303: astore_1       
        //   304: new             Lnet/minecraft/util/Vec3;
        //   307: dup            
        //   308: aload_0        
        //   309: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   312: aload_0        
        //   313: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   316: dadd           
        //   317: aload_0        
        //   318: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   321: aload_0        
        //   322: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   325: dadd           
        //   326: aload_0        
        //   327: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   330: aload_0        
        //   331: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   334: dadd           
        //   335: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   338: astore_2       
        //   339: aload_3        
        //   340: ifnull          372
        //   343: new             Lnet/minecraft/util/Vec3;
        //   346: dup            
        //   347: aload_3        
        //   348: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   351: getfield        net/minecraft/util/Vec3.xCoord:D
        //   354: aload_3        
        //   355: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   358: getfield        net/minecraft/util/Vec3.yCoord:D
        //   361: aload_3        
        //   362: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   365: getfield        net/minecraft/util/Vec3.zCoord:D
        //   368: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   371: astore_2       
        //   372: aconst_null    
        //   373: astore          4
        //   375: aload_0        
        //   376: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //   379: aload_0        
        //   380: aload_0        
        //   381: invokevirtual   net/minecraft/entity/projectile/EntityFireball.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   384: aload_0        
        //   385: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   388: aload_0        
        //   389: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   392: aload_0        
        //   393: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   396: invokevirtual   net/minecraft/util/AxisAlignedBB.addCoord:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   399: dconst_1       
        //   400: dconst_1       
        //   401: dconst_1       
        //   402: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   405: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //   408: astore          5
        //   410: dconst_0       
        //   411: dstore          6
        //   413: goto            532
        //   416: aload           5
        //   418: iconst_0       
        //   419: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   424: checkcast       Lnet/minecraft/entity/Entity;
        //   427: astore          9
        //   429: aload           9
        //   431: invokevirtual   net/minecraft/entity/Entity.canBeCollidedWith:()Z
        //   434: ifeq            529
        //   437: aload           9
        //   439: aload_0        
        //   440: getfield        net/minecraft/entity/projectile/EntityFireball.shootingEntity:Lnet/minecraft/entity/EntityLivingBase;
        //   443: invokevirtual   net/minecraft/entity/Entity.isEntityEqual:(Lnet/minecraft/entity/Entity;)Z
        //   446: ifeq            458
        //   449: aload_0        
        //   450: getfield        net/minecraft/entity/projectile/EntityFireball.ticksInAir:I
        //   453: bipush          25
        //   455: if_icmplt       529
        //   458: ldc             0.3
        //   460: fstore          10
        //   462: aload           9
        //   464: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   467: fload           10
        //   469: f2d            
        //   470: fload           10
        //   472: f2d            
        //   473: fload           10
        //   475: f2d            
        //   476: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   479: astore          11
        //   481: aload           11
        //   483: aload_1        
        //   484: aload_2        
        //   485: invokevirtual   net/minecraft/util/AxisAlignedBB.calculateIntercept:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   488: astore          12
        //   490: aload           12
        //   492: ifnull          529
        //   495: aload_1        
        //   496: aload           12
        //   498: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   501: invokevirtual   net/minecraft/util/Vec3.distanceTo:(Lnet/minecraft/util/Vec3;)D
        //   504: dstore          13
        //   506: dload           13
        //   508: dload           6
        //   510: dcmpg          
        //   511: iflt            521
        //   514: dload           6
        //   516: dconst_0       
        //   517: dcmpl          
        //   518: ifne            529
        //   521: aload           9
        //   523: astore          4
        //   525: dload           13
        //   527: dstore          6
        //   529: iinc            8, 1
        //   532: iconst_0       
        //   533: aload           5
        //   535: invokeinterface java/util/List.size:()I
        //   540: if_icmplt       416
        //   543: aload           4
        //   545: ifnull          558
        //   548: new             Lnet/minecraft/util/MovingObjectPosition;
        //   551: dup            
        //   552: aload           4
        //   554: invokespecial   net/minecraft/util/MovingObjectPosition.<init>:(Lnet/minecraft/entity/Entity;)V
        //   557: astore_3       
        //   558: aload_3        
        //   559: ifnull          567
        //   562: aload_0        
        //   563: aload_3        
        //   564: invokevirtual   net/minecraft/entity/projectile/EntityFireball.onImpact:(Lnet/minecraft/util/MovingObjectPosition;)V
        //   567: aload_0        
        //   568: dup            
        //   569: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   572: aload_0        
        //   573: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   576: dadd           
        //   577: putfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   580: aload_0        
        //   581: dup            
        //   582: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   585: aload_0        
        //   586: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   589: dadd           
        //   590: putfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   593: aload_0        
        //   594: dup            
        //   595: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   598: aload_0        
        //   599: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   602: dadd           
        //   603: putfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   606: aload_0        
        //   607: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   610: aload_0        
        //   611: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   614: dmul           
        //   615: aload_0        
        //   616: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   619: aload_0        
        //   620: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   623: dmul           
        //   624: dadd           
        //   625: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   628: fstore          8
        //   630: aload_0        
        //   631: aload_0        
        //   632: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   635: aload_0        
        //   636: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   639: invokestatic    java/lang/Math.atan2:(DD)D
        //   642: ldc2_w          180.0
        //   645: dmul           
        //   646: ldc2_w          3.141592653589793
        //   649: ddiv           
        //   650: d2f            
        //   651: ldc_w           90.0
        //   654: fadd           
        //   655: putfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   658: aload_0        
        //   659: fload           8
        //   661: f2d            
        //   662: aload_0        
        //   663: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   666: invokestatic    java/lang/Math.atan2:(DD)D
        //   669: ldc2_w          180.0
        //   672: dmul           
        //   673: ldc2_w          3.141592653589793
        //   676: ddiv           
        //   677: d2f            
        //   678: ldc_w           90.0
        //   681: fsub           
        //   682: putfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   685: goto            700
        //   688: aload_0        
        //   689: dup            
        //   690: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   693: ldc_w           360.0
        //   696: fsub           
        //   697: putfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   700: aload_0        
        //   701: getfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   704: aload_0        
        //   705: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   708: fsub           
        //   709: ldc_w           -180.0
        //   712: fcmpg          
        //   713: iflt            688
        //   716: goto            731
        //   719: aload_0        
        //   720: dup            
        //   721: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   724: ldc_w           360.0
        //   727: fadd           
        //   728: putfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   731: aload_0        
        //   732: getfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   735: aload_0        
        //   736: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   739: fsub           
        //   740: ldc_w           180.0
        //   743: fcmpl          
        //   744: ifge            719
        //   747: goto            762
        //   750: aload_0        
        //   751: dup            
        //   752: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   755: ldc_w           360.0
        //   758: fsub           
        //   759: putfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   762: aload_0        
        //   763: getfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   766: aload_0        
        //   767: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   770: fsub           
        //   771: ldc_w           -180.0
        //   774: fcmpg          
        //   775: iflt            750
        //   778: goto            793
        //   781: aload_0        
        //   782: dup            
        //   783: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   786: ldc_w           360.0
        //   789: fadd           
        //   790: putfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   793: aload_0        
        //   794: getfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   797: aload_0        
        //   798: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   801: fsub           
        //   802: ldc_w           180.0
        //   805: fcmpl          
        //   806: ifge            781
        //   809: aload_0        
        //   810: aload_0        
        //   811: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   814: aload_0        
        //   815: getfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   818: aload_0        
        //   819: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   822: fsub           
        //   823: ldc             0.2
        //   825: fmul           
        //   826: fadd           
        //   827: putfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   830: aload_0        
        //   831: aload_0        
        //   832: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   835: aload_0        
        //   836: getfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   839: aload_0        
        //   840: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   843: fsub           
        //   844: ldc             0.2
        //   846: fmul           
        //   847: fadd           
        //   848: putfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   851: aload_0        
        //   852: invokevirtual   net/minecraft/entity/projectile/EntityFireball.getMotionFactor:()F
        //   855: fstore          9
        //   857: aload_0        
        //   858: invokevirtual   net/minecraft/entity/projectile/EntityFireball.isInWater:()Z
        //   861: ifeq            949
        //   864: goto            939
        //   867: ldc_w           0.25
        //   870: fstore          11
        //   872: aload_0        
        //   873: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //   876: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //   879: aload_0        
        //   880: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   883: aload_0        
        //   884: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   887: fload           11
        //   889: f2d            
        //   890: dmul           
        //   891: dsub           
        //   892: aload_0        
        //   893: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   896: aload_0        
        //   897: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   900: fload           11
        //   902: f2d            
        //   903: dmul           
        //   904: dsub           
        //   905: aload_0        
        //   906: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   909: aload_0        
        //   910: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   913: fload           11
        //   915: f2d            
        //   916: dmul           
        //   917: dsub           
        //   918: aload_0        
        //   919: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   922: aload_0        
        //   923: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   926: aload_0        
        //   927: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   930: iconst_0       
        //   931: newarray        I
        //   933: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   936: iinc            10, 1
        //   939: iconst_0       
        //   940: iconst_4       
        //   941: if_icmplt       867
        //   944: ldc_w           0.8
        //   947: fstore          9
        //   949: aload_0        
        //   950: dup            
        //   951: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   954: aload_0        
        //   955: getfield        net/minecraft/entity/projectile/EntityFireball.accelerationX:D
        //   958: dadd           
        //   959: putfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   962: aload_0        
        //   963: dup            
        //   964: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   967: aload_0        
        //   968: getfield        net/minecraft/entity/projectile/EntityFireball.accelerationY:D
        //   971: dadd           
        //   972: putfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   975: aload_0        
        //   976: dup            
        //   977: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   980: aload_0        
        //   981: getfield        net/minecraft/entity/projectile/EntityFireball.accelerationZ:D
        //   984: dadd           
        //   985: putfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   988: aload_0        
        //   989: dup            
        //   990: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   993: fload           9
        //   995: f2d            
        //   996: dmul           
        //   997: putfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //  1000: aload_0        
        //  1001: dup            
        //  1002: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //  1005: fload           9
        //  1007: f2d            
        //  1008: dmul           
        //  1009: putfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //  1012: aload_0        
        //  1013: dup            
        //  1014: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //  1017: fload           9
        //  1019: f2d            
        //  1020: dmul           
        //  1021: putfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //  1024: aload_0        
        //  1025: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //  1028: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_NORMAL:Lnet/minecraft/util/EnumParticleTypes;
        //  1031: aload_0        
        //  1032: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //  1035: aload_0        
        //  1036: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //  1039: ldc2_w          0.5
        //  1042: dadd           
        //  1043: aload_0        
        //  1044: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //  1047: dconst_0       
        //  1048: dconst_0       
        //  1049: dconst_0       
        //  1050: iconst_0       
        //  1051: newarray        I
        //  1053: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //  1056: aload_0        
        //  1057: aload_0        
        //  1058: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //  1061: aload_0        
        //  1062: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //  1065: aload_0        
        //  1066: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //  1069: invokevirtual   net/minecraft/entity/projectile/EntityFireball.setPosition:(DDD)V
        //  1072: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected float getMotionFactor() {
        return 0.95f;
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short)this.field_145795_e);
        nbtTagCompound.setShort("yTile", (short)this.field_145793_f);
        nbtTagCompound.setShort("zTile", (short)this.field_145794_g);
        final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.field_145796_h);
        nbtTagCompound.setString("inTile", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbtTagCompound.setTag("direction", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.field_145795_e = nbtTagCompound.getShort("xTile");
        this.field_145793_f = nbtTagCompound.getShort("yTile");
        this.field_145794_g = nbtTagCompound.getShort("zTile");
        if (nbtTagCompound.hasKey("inTile", 8)) {
            this.field_145796_h = Block.getBlockFromName(nbtTagCompound.getString("inTile"));
        }
        else {
            this.field_145796_h = Block.getBlockById(nbtTagCompound.getByte("inTile") & 0xFF);
        }
        this.inGround = (nbtTagCompound.getByte("inGround") == 1);
        if (nbtTagCompound.hasKey("direction", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("direction", 6);
            this.motionX = tagList.getDouble(0);
            this.motionY = tagList.getDouble(1);
            this.motionZ = tagList.getDouble(2);
        }
        else {
            this.setDead();
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public float getCollisionBorderSize() {
        return 1.0f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        this.setBeenAttacked();
        if (damageSource.getEntity() != null) {
            final Vec3 lookVec = damageSource.getEntity().getLookVec();
            if (lookVec != null) {
                this.motionX = lookVec.xCoord;
                this.motionY = lookVec.yCoord;
                this.motionZ = lookVec.zCoord;
                this.accelerationX = this.motionX * 0.1;
                this.accelerationY = this.motionY * 0.1;
                this.accelerationZ = this.motionZ * 0.1;
            }
            if (damageSource.getEntity() instanceof EntityLivingBase) {
                this.shootingEntity = (EntityLivingBase)damageSource.getEntity();
            }
            return true;
        }
        return false;
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    static {
        __OBFID = "CL_00001717";
    }
}
