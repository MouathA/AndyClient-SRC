package net.minecraft.entity.boss;

import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;

public class EntityDragon extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob
{
    public double targetX;
    public double targetY;
    public double targetZ;
    public double[][] ringBuffer;
    public int ringBufferIndex;
    public EntityDragonPart[] dragonPartArray;
    public EntityDragonPart dragonPartHead;
    public EntityDragonPart dragonPartBody;
    public EntityDragonPart dragonPartTail1;
    public EntityDragonPart dragonPartTail2;
    public EntityDragonPart dragonPartTail3;
    public EntityDragonPart dragonPartWing1;
    public EntityDragonPart dragonPartWing2;
    public float prevAnimTime;
    public float animTime;
    public boolean forceNewTarget;
    public boolean slowed;
    private Entity target;
    public int deathTicks;
    public EntityEnderCrystal healingEnderCrystal;
    private static final String __OBFID;
    
    public EntityDragon(final World world) {
        super(world);
        this.ringBuffer = new double[64][3];
        this.ringBufferIndex = -1;
        this.dragonPartArray = new EntityDragonPart[] { this.dragonPartHead = new EntityDragonPart(this, "head", 6.0f, 6.0f), this.dragonPartBody = new EntityDragonPart(this, "body", 8.0f, 8.0f), this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0f, 4.0f), this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0f, 4.0f) };
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0f, 8.0f);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.targetY = 100.0;
        this.ignoreFrustumCheck = true;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }
    
    public double[] getMovementOffsets(final int n, float n2) {
        if (this.getHealth() <= 0.0f) {
            n2 = 0.0f;
        }
        n2 = 1.0f - n2;
        final int n3 = this.ringBufferIndex - n * 1 & 0x3F;
        final int n4 = this.ringBufferIndex - n * 1 - 1 & 0x3F;
        final double[] array = new double[3];
        final double n5 = this.ringBuffer[n3][0];
        array[0] = n5 + MathHelper.wrapAngleTo180_double(this.ringBuffer[n4][0] - n5) * n2;
        final double n6 = this.ringBuffer[n3][1];
        array[1] = n6 + (this.ringBuffer[n4][1] - n6) * n2;
        array[2] = this.ringBuffer[n3][2] + (this.ringBuffer[n4][2] - this.ringBuffer[n3][2]) * n2;
        return array;
    }
    
    @Override
    public void onLivingUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifeq            94
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //    14: ldc             3.1415927
        //    16: fmul           
        //    17: fconst_2       
        //    18: fmul           
        //    19: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //    22: fstore_1       
        //    23: aload_0        
        //    24: getfield        net/minecraft/entity/boss/EntityDragon.prevAnimTime:F
        //    27: ldc             3.1415927
        //    29: fmul           
        //    30: fconst_2       
        //    31: fmul           
        //    32: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //    35: fstore_2       
        //    36: fload_2        
        //    37: ldc             -0.3
        //    39: fcmpg          
        //    40: ifgt            94
        //    43: fload_1        
        //    44: ldc             -0.3
        //    46: fcmpl          
        //    47: iflt            94
        //    50: aload_0        
        //    51: invokevirtual   net/minecraft/entity/boss/EntityDragon.isSlient:()Z
        //    54: ifne            94
        //    57: aload_0        
        //    58: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //    61: aload_0        
        //    62: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //    65: aload_0        
        //    66: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //    69: aload_0        
        //    70: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //    73: ldc             "mob.enderdragon.wings"
        //    75: ldc             5.0
        //    77: ldc             0.8
        //    79: aload_0        
        //    80: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //    83: invokevirtual   java/util/Random.nextFloat:()F
        //    86: ldc             0.3
        //    88: fmul           
        //    89: fadd           
        //    90: iconst_0       
        //    91: invokevirtual   net/minecraft/world/World.playSound:(DDDLjava/lang/String;FFZ)V
        //    94: aload_0        
        //    95: aload_0        
        //    96: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //    99: putfield        net/minecraft/entity/boss/EntityDragon.prevAnimTime:F
        //   102: aload_0        
        //   103: invokevirtual   net/minecraft/entity/boss/EntityDragon.getHealth:()F
        //   106: fconst_0       
        //   107: fcmpg          
        //   108: ifgt            197
        //   111: aload_0        
        //   112: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   115: invokevirtual   java/util/Random.nextFloat:()F
        //   118: ldc             0.5
        //   120: fsub           
        //   121: ldc             8.0
        //   123: fmul           
        //   124: fstore_1       
        //   125: aload_0        
        //   126: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   129: invokevirtual   java/util/Random.nextFloat:()F
        //   132: ldc             0.5
        //   134: fsub           
        //   135: ldc             4.0
        //   137: fmul           
        //   138: fstore_2       
        //   139: aload_0        
        //   140: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   143: invokevirtual   java/util/Random.nextFloat:()F
        //   146: ldc             0.5
        //   148: fsub           
        //   149: ldc             8.0
        //   151: fmul           
        //   152: fstore_3       
        //   153: aload_0        
        //   154: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //   157: getstatic       net/minecraft/util/EnumParticleTypes.EXPLOSION_LARGE:Lnet/minecraft/util/EnumParticleTypes;
        //   160: aload_0        
        //   161: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   164: fload_1        
        //   165: f2d            
        //   166: dadd           
        //   167: aload_0        
        //   168: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   171: ldc2_w          2.0
        //   174: dadd           
        //   175: fload_2        
        //   176: f2d            
        //   177: dadd           
        //   178: aload_0        
        //   179: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   182: fload_3        
        //   183: f2d            
        //   184: dadd           
        //   185: dconst_0       
        //   186: dconst_0       
        //   187: dconst_0       
        //   188: iconst_0       
        //   189: newarray        I
        //   191: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   194: goto            2186
        //   197: aload_0        
        //   198: invokespecial   net/minecraft/entity/boss/EntityDragon.updateDragonEnderCrystal:()V
        //   201: ldc             0.2
        //   203: aload_0        
        //   204: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //   207: aload_0        
        //   208: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //   211: dmul           
        //   212: aload_0        
        //   213: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //   216: aload_0        
        //   217: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //   220: dmul           
        //   221: dadd           
        //   222: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   225: ldc             10.0
        //   227: fmul           
        //   228: fconst_1       
        //   229: fadd           
        //   230: fdiv           
        //   231: fstore_1       
        //   232: fload_1        
        //   233: ldc2_w          2.0
        //   236: aload_0        
        //   237: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //   240: invokestatic    java/lang/Math.pow:(DD)D
        //   243: d2f            
        //   244: fmul           
        //   245: fstore_1       
        //   246: aload_0        
        //   247: getfield        net/minecraft/entity/boss/EntityDragon.slowed:Z
        //   250: ifeq            269
        //   253: aload_0        
        //   254: dup            
        //   255: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   258: fload_1        
        //   259: ldc             0.5
        //   261: fmul           
        //   262: fadd           
        //   263: putfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   266: goto            279
        //   269: aload_0        
        //   270: dup            
        //   271: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   274: fload_1        
        //   275: fadd           
        //   276: putfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   279: aload_0        
        //   280: aload_0        
        //   281: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   284: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_float:(F)F
        //   287: putfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   290: aload_0        
        //   291: getfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   294: ifge            337
        //   297: goto            328
        //   300: aload_0        
        //   301: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   304: iconst_0       
        //   305: aaload         
        //   306: iconst_0       
        //   307: aload_0        
        //   308: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   311: f2d            
        //   312: dastore        
        //   313: aload_0        
        //   314: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   317: iconst_0       
        //   318: aaload         
        //   319: iconst_1       
        //   320: aload_0        
        //   321: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   324: dastore        
        //   325: iinc            4, 1
        //   328: iconst_0       
        //   329: aload_0        
        //   330: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   333: arraylength    
        //   334: if_icmplt       300
        //   337: aload_0        
        //   338: dup            
        //   339: getfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   342: iconst_1       
        //   343: iadd           
        //   344: dup_x1         
        //   345: putfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   348: aload_0        
        //   349: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   352: arraylength    
        //   353: if_icmpne       361
        //   356: aload_0        
        //   357: iconst_0       
        //   358: putfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   361: aload_0        
        //   362: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   365: aload_0        
        //   366: getfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   369: aaload         
        //   370: iconst_0       
        //   371: aload_0        
        //   372: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   375: f2d            
        //   376: dastore        
        //   377: aload_0        
        //   378: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   381: aload_0        
        //   382: getfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   385: aaload         
        //   386: iconst_1       
        //   387: aload_0        
        //   388: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   391: dastore        
        //   392: aload_0        
        //   393: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //   396: getfield        net/minecraft/world/World.isRemote:Z
        //   399: ifeq            571
        //   402: aload_0        
        //   403: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   406: ifle            1363
        //   409: aload_0        
        //   410: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   413: aload_0        
        //   414: getfield        net/minecraft/entity/boss/EntityDragon.newPosX:D
        //   417: aload_0        
        //   418: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   421: dsub           
        //   422: aload_0        
        //   423: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   426: i2d            
        //   427: ddiv           
        //   428: dadd           
        //   429: dstore          10
        //   431: aload_0        
        //   432: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   435: aload_0        
        //   436: getfield        net/minecraft/entity/boss/EntityDragon.newPosY:D
        //   439: aload_0        
        //   440: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   443: dsub           
        //   444: aload_0        
        //   445: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   448: i2d            
        //   449: ddiv           
        //   450: dadd           
        //   451: dstore          4
        //   453: aload_0        
        //   454: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   457: aload_0        
        //   458: getfield        net/minecraft/entity/boss/EntityDragon.newPosZ:D
        //   461: aload_0        
        //   462: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   465: dsub           
        //   466: aload_0        
        //   467: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   470: i2d            
        //   471: ddiv           
        //   472: dadd           
        //   473: dstore          6
        //   475: aload_0        
        //   476: getfield        net/minecraft/entity/boss/EntityDragon.newRotationYaw:D
        //   479: aload_0        
        //   480: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   483: f2d            
        //   484: dsub           
        //   485: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_double:(D)D
        //   488: dstore          8
        //   490: aload_0        
        //   491: aload_0        
        //   492: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   495: f2d            
        //   496: dload           8
        //   498: aload_0        
        //   499: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   502: i2d            
        //   503: ddiv           
        //   504: dadd           
        //   505: d2f            
        //   506: putfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   509: aload_0        
        //   510: aload_0        
        //   511: getfield        net/minecraft/entity/boss/EntityDragon.rotationPitch:F
        //   514: f2d            
        //   515: aload_0        
        //   516: getfield        net/minecraft/entity/boss/EntityDragon.newRotationPitch:D
        //   519: aload_0        
        //   520: getfield        net/minecraft/entity/boss/EntityDragon.rotationPitch:F
        //   523: f2d            
        //   524: dsub           
        //   525: aload_0        
        //   526: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   529: i2d            
        //   530: ddiv           
        //   531: dadd           
        //   532: d2f            
        //   533: putfield        net/minecraft/entity/boss/EntityDragon.rotationPitch:F
        //   536: aload_0        
        //   537: dup            
        //   538: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   541: iconst_1       
        //   542: isub           
        //   543: putfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   546: aload_0        
        //   547: dload           10
        //   549: dload           4
        //   551: dload           6
        //   553: invokevirtual   net/minecraft/entity/boss/EntityDragon.setPosition:(DDD)V
        //   556: aload_0        
        //   557: aload_0        
        //   558: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   561: aload_0        
        //   562: getfield        net/minecraft/entity/boss/EntityDragon.rotationPitch:F
        //   565: invokevirtual   net/minecraft/entity/boss/EntityDragon.setRotation:(FF)V
        //   568: goto            1363
        //   571: aload_0        
        //   572: getfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   575: aload_0        
        //   576: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   579: dsub           
        //   580: dstore          10
        //   582: aload_0        
        //   583: getfield        net/minecraft/entity/boss/EntityDragon.targetY:D
        //   586: aload_0        
        //   587: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   590: dsub           
        //   591: dstore          4
        //   593: aload_0        
        //   594: getfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   597: aload_0        
        //   598: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   601: dsub           
        //   602: dstore          6
        //   604: dload           10
        //   606: dload           10
        //   608: dmul           
        //   609: dload           4
        //   611: dload           4
        //   613: dmul           
        //   614: dadd           
        //   615: dload           6
        //   617: dload           6
        //   619: dmul           
        //   620: dadd           
        //   621: dstore          8
        //   623: aload_0        
        //   624: getfield        net/minecraft/entity/boss/EntityDragon.target:Lnet/minecraft/entity/Entity;
        //   627: ifnull          738
        //   630: aload_0        
        //   631: aload_0        
        //   632: getfield        net/minecraft/entity/boss/EntityDragon.target:Lnet/minecraft/entity/Entity;
        //   635: getfield        net/minecraft/entity/Entity.posX:D
        //   638: putfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   641: aload_0        
        //   642: aload_0        
        //   643: getfield        net/minecraft/entity/boss/EntityDragon.target:Lnet/minecraft/entity/Entity;
        //   646: getfield        net/minecraft/entity/Entity.posZ:D
        //   649: putfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   652: aload_0        
        //   653: getfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   656: aload_0        
        //   657: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   660: dsub           
        //   661: dstore          15
        //   663: aload_0        
        //   664: getfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   667: aload_0        
        //   668: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   671: dsub           
        //   672: dstore          17
        //   674: dload           15
        //   676: dload           15
        //   678: dmul           
        //   679: dload           17
        //   681: dload           17
        //   683: dmul           
        //   684: dadd           
        //   685: invokestatic    java/lang/Math.sqrt:(D)D
        //   688: dstore          19
        //   690: ldc2_w          0.4000000059604645
        //   693: dload           19
        //   695: ldc2_w          80.0
        //   698: ddiv           
        //   699: dadd           
        //   700: dconst_1       
        //   701: dsub           
        //   702: dstore          13
        //   704: dload           13
        //   706: ldc2_w          10.0
        //   709: dcmpl          
        //   710: ifle            718
        //   713: ldc2_w          10.0
        //   716: dstore          13
        //   718: aload_0        
        //   719: aload_0        
        //   720: getfield        net/minecraft/entity/boss/EntityDragon.target:Lnet/minecraft/entity/Entity;
        //   723: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   726: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   729: dload           13
        //   731: dadd           
        //   732: putfield        net/minecraft/entity/boss/EntityDragon.targetY:D
        //   735: goto            778
        //   738: aload_0        
        //   739: dup            
        //   740: getfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   743: aload_0        
        //   744: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   747: invokevirtual   java/util/Random.nextGaussian:()D
        //   750: ldc2_w          2.0
        //   753: dmul           
        //   754: dadd           
        //   755: putfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   758: aload_0        
        //   759: dup            
        //   760: getfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   763: aload_0        
        //   764: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   767: invokevirtual   java/util/Random.nextGaussian:()D
        //   770: ldc2_w          2.0
        //   773: dmul           
        //   774: dadd           
        //   775: putfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   778: aload_0        
        //   779: getfield        net/minecraft/entity/boss/EntityDragon.forceNewTarget:Z
        //   782: ifne            817
        //   785: dload           8
        //   787: ldc2_w          100.0
        //   790: dcmpg          
        //   791: iflt            817
        //   794: dload           8
        //   796: ldc2_w          22500.0
        //   799: dcmpl          
        //   800: ifgt            817
        //   803: aload_0        
        //   804: getfield        net/minecraft/entity/boss/EntityDragon.isCollidedHorizontally:Z
        //   807: ifne            817
        //   810: aload_0        
        //   811: getfield        net/minecraft/entity/boss/EntityDragon.isCollidedVertically:Z
        //   814: ifeq            821
        //   817: aload_0        
        //   818: invokespecial   net/minecraft/entity/boss/EntityDragon.setNewTarget:()V
        //   821: dload           4
        //   823: dload           10
        //   825: dload           10
        //   827: dmul           
        //   828: dload           6
        //   830: dload           6
        //   832: dmul           
        //   833: dadd           
        //   834: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   837: f2d            
        //   838: ddiv           
        //   839: dstore          4
        //   841: ldc_w           0.6
        //   844: fstore          12
        //   846: dload           4
        //   848: fload           12
        //   850: fneg           
        //   851: f2d            
        //   852: fload           12
        //   854: f2d            
        //   855: invokestatic    net/minecraft/util/MathHelper.clamp_double:(DDD)D
        //   858: dstore          4
        //   860: aload_0        
        //   861: dup            
        //   862: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //   865: dload           4
        //   867: ldc2_w          0.10000000149011612
        //   870: dmul           
        //   871: dadd           
        //   872: putfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //   875: aload_0        
        //   876: aload_0        
        //   877: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   880: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_float:(F)F
        //   883: putfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   886: ldc2_w          180.0
        //   889: dload           10
        //   891: dload           6
        //   893: invokestatic    java/lang/Math.atan2:(DD)D
        //   896: ldc2_w          180.0
        //   899: dmul           
        //   900: ldc2_w          3.141592653589793
        //   903: ddiv           
        //   904: dsub           
        //   905: dstore          15
        //   907: dload           15
        //   909: aload_0        
        //   910: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   913: f2d            
        //   914: dsub           
        //   915: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_double:(D)D
        //   918: dstore          17
        //   920: dload           17
        //   922: ldc2_w          50.0
        //   925: dcmpl          
        //   926: ifle            934
        //   929: ldc2_w          50.0
        //   932: dstore          17
        //   934: dload           17
        //   936: ldc2_w          -50.0
        //   939: dcmpg          
        //   940: ifge            948
        //   943: ldc2_w          -50.0
        //   946: dstore          17
        //   948: new             Lnet/minecraft/util/Vec3;
        //   951: dup            
        //   952: aload_0        
        //   953: getfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   956: aload_0        
        //   957: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   960: dsub           
        //   961: aload_0        
        //   962: getfield        net/minecraft/entity/boss/EntityDragon.targetY:D
        //   965: aload_0        
        //   966: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   969: dsub           
        //   970: aload_0        
        //   971: getfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   974: aload_0        
        //   975: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   978: dsub           
        //   979: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   982: invokevirtual   net/minecraft/util/Vec3.normalize:()Lnet/minecraft/util/Vec3;
        //   985: astore          19
        //   987: aload_0        
        //   988: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   991: ldc             3.1415927
        //   993: fmul           
        //   994: ldc_w           180.0
        //   997: fdiv           
        //   998: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1001: fneg           
        //  1002: f2d            
        //  1003: dstore          13
        //  1005: new             Lnet/minecraft/util/Vec3;
        //  1008: dup            
        //  1009: aload_0        
        //  1010: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1013: ldc             3.1415927
        //  1015: fmul           
        //  1016: ldc_w           180.0
        //  1019: fdiv           
        //  1020: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1023: f2d            
        //  1024: aload_0        
        //  1025: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1028: dload           13
        //  1030: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //  1033: invokevirtual   net/minecraft/util/Vec3.normalize:()Lnet/minecraft/util/Vec3;
        //  1036: astore          20
        //  1038: aload           20
        //  1040: aload           19
        //  1042: invokevirtual   net/minecraft/util/Vec3.dotProduct:(Lnet/minecraft/util/Vec3;)D
        //  1045: d2f            
        //  1046: ldc             0.5
        //  1048: fadd           
        //  1049: ldc_w           1.5
        //  1052: fdiv           
        //  1053: fstore          21
        //  1055: fload           21
        //  1057: fconst_0       
        //  1058: fcmpg          
        //  1059: ifge            1065
        //  1062: fconst_0       
        //  1063: fstore          21
        //  1065: aload_0        
        //  1066: dup            
        //  1067: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1070: ldc             0.8
        //  1072: fmul           
        //  1073: putfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1076: aload_0        
        //  1077: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1080: aload_0        
        //  1081: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1084: dmul           
        //  1085: aload_0        
        //  1086: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1089: aload_0        
        //  1090: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1093: dmul           
        //  1094: dadd           
        //  1095: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //  1098: fconst_1       
        //  1099: fmul           
        //  1100: fconst_1       
        //  1101: fadd           
        //  1102: fstore          22
        //  1104: aload_0        
        //  1105: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1108: aload_0        
        //  1109: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1112: dmul           
        //  1113: aload_0        
        //  1114: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1117: aload_0        
        //  1118: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1121: dmul           
        //  1122: dadd           
        //  1123: invokestatic    java/lang/Math.sqrt:(D)D
        //  1126: dconst_1       
        //  1127: dmul           
        //  1128: dconst_1       
        //  1129: dadd           
        //  1130: dstore          23
        //  1132: dload           23
        //  1134: ldc2_w          40.0
        //  1137: dcmpl          
        //  1138: ifle            1146
        //  1141: ldc2_w          40.0
        //  1144: dstore          23
        //  1146: aload_0        
        //  1147: aload_0        
        //  1148: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1151: f2d            
        //  1152: dload           17
        //  1154: ldc2_w          0.699999988079071
        //  1157: dload           23
        //  1159: ddiv           
        //  1160: fload           22
        //  1162: f2d            
        //  1163: ddiv           
        //  1164: dmul           
        //  1165: dadd           
        //  1166: d2f            
        //  1167: putfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1170: aload_0        
        //  1171: dup            
        //  1172: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1175: aload_0        
        //  1176: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1179: ldc_w           0.1
        //  1182: fmul           
        //  1183: fadd           
        //  1184: putfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1187: ldc2_w          2.0
        //  1190: dload           23
        //  1192: dconst_1       
        //  1193: dadd           
        //  1194: ddiv           
        //  1195: d2f            
        //  1196: fstore          25
        //  1198: ldc_w           0.06
        //  1201: fstore          26
        //  1203: aload_0        
        //  1204: fconst_0       
        //  1205: ldc_w           -1.0
        //  1208: fload           26
        //  1210: fload           21
        //  1212: fload           25
        //  1214: fmul           
        //  1215: fconst_1       
        //  1216: fload           25
        //  1218: fsub           
        //  1219: fadd           
        //  1220: fmul           
        //  1221: invokevirtual   net/minecraft/entity/boss/EntityDragon.moveFlying:(FFF)V
        //  1224: aload_0        
        //  1225: getfield        net/minecraft/entity/boss/EntityDragon.slowed:Z
        //  1228: ifeq            1262
        //  1231: aload_0        
        //  1232: aload_0        
        //  1233: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1236: ldc2_w          0.800000011920929
        //  1239: dmul           
        //  1240: aload_0        
        //  1241: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1244: ldc2_w          0.800000011920929
        //  1247: dmul           
        //  1248: aload_0        
        //  1249: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1252: ldc2_w          0.800000011920929
        //  1255: dmul           
        //  1256: invokevirtual   net/minecraft/entity/boss/EntityDragon.moveEntity:(DDD)V
        //  1259: goto            1278
        //  1262: aload_0        
        //  1263: aload_0        
        //  1264: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1267: aload_0        
        //  1268: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1271: aload_0        
        //  1272: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1275: invokevirtual   net/minecraft/entity/boss/EntityDragon.moveEntity:(DDD)V
        //  1278: new             Lnet/minecraft/util/Vec3;
        //  1281: dup            
        //  1282: aload_0        
        //  1283: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1286: aload_0        
        //  1287: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1290: aload_0        
        //  1291: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1294: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //  1297: invokevirtual   net/minecraft/util/Vec3.normalize:()Lnet/minecraft/util/Vec3;
        //  1300: astore          27
        //  1302: aload           27
        //  1304: aload           20
        //  1306: invokevirtual   net/minecraft/util/Vec3.dotProduct:(Lnet/minecraft/util/Vec3;)D
        //  1309: d2f            
        //  1310: fconst_1       
        //  1311: fadd           
        //  1312: fconst_2       
        //  1313: fdiv           
        //  1314: fstore          28
        //  1316: ldc             0.8
        //  1318: ldc_w           0.15
        //  1321: fload           28
        //  1323: fmul           
        //  1324: fadd           
        //  1325: fstore          28
        //  1327: aload_0        
        //  1328: dup            
        //  1329: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1332: fload           28
        //  1334: f2d            
        //  1335: dmul           
        //  1336: putfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1339: aload_0        
        //  1340: dup            
        //  1341: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1344: fload           28
        //  1346: f2d            
        //  1347: dmul           
        //  1348: putfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1351: aload_0        
        //  1352: dup            
        //  1353: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1356: ldc2_w          0.9100000262260437
        //  1359: dmul           
        //  1360: putfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1363: aload_0        
        //  1364: aload_0        
        //  1365: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1368: putfield        net/minecraft/entity/boss/EntityDragon.renderYawOffset:F
        //  1371: aload_0        
        //  1372: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1375: aload_0        
        //  1376: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1379: ldc_w           3.0
        //  1382: dup_x1         
        //  1383: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1386: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1389: aload_0        
        //  1390: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1393: aload_0        
        //  1394: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1397: fconst_2       
        //  1398: dup_x1         
        //  1399: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1402: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1405: aload_0        
        //  1406: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1409: aload_0        
        //  1410: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1413: fconst_2       
        //  1414: dup_x1         
        //  1415: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1418: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1421: aload_0        
        //  1422: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail3:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1425: aload_0        
        //  1426: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail3:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1429: fconst_2       
        //  1430: dup_x1         
        //  1431: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1434: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1437: aload_0        
        //  1438: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1441: ldc_w           3.0
        //  1444: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1447: aload_0        
        //  1448: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1451: ldc             5.0
        //  1453: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1456: aload_0        
        //  1457: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1460: fconst_2       
        //  1461: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1464: aload_0        
        //  1465: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1468: ldc             4.0
        //  1470: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1473: aload_0        
        //  1474: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1477: ldc_w           3.0
        //  1480: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1483: aload_0        
        //  1484: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1487: ldc             4.0
        //  1489: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1492: aload_0        
        //  1493: iconst_5       
        //  1494: fconst_1       
        //  1495: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1498: iconst_1       
        //  1499: daload         
        //  1500: aload_0        
        //  1501: bipush          10
        //  1503: fconst_1       
        //  1504: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1507: iconst_1       
        //  1508: daload         
        //  1509: dsub           
        //  1510: d2f            
        //  1511: ldc             10.0
        //  1513: fmul           
        //  1514: ldc_w           180.0
        //  1517: fdiv           
        //  1518: ldc             3.1415927
        //  1520: fmul           
        //  1521: fstore_2       
        //  1522: fload_2        
        //  1523: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1526: fstore_3       
        //  1527: fload_2        
        //  1528: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1531: fneg           
        //  1532: fstore          13
        //  1534: aload_0        
        //  1535: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1538: ldc             3.1415927
        //  1540: fmul           
        //  1541: ldc_w           180.0
        //  1544: fdiv           
        //  1545: fstore          14
        //  1547: fload           14
        //  1549: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1552: fstore          15
        //  1554: fload           14
        //  1556: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1559: fstore          16
        //  1561: aload_0        
        //  1562: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1565: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  1568: aload_0        
        //  1569: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1572: aload_0        
        //  1573: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  1576: fload           15
        //  1578: ldc             0.5
        //  1580: fmul           
        //  1581: f2d            
        //  1582: dadd           
        //  1583: aload_0        
        //  1584: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  1587: aload_0        
        //  1588: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1591: fload           16
        //  1593: ldc             0.5
        //  1595: fmul           
        //  1596: f2d            
        //  1597: dsub           
        //  1598: fconst_0       
        //  1599: fconst_0       
        //  1600: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  1603: aload_0        
        //  1604: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1607: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  1610: aload_0        
        //  1611: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1614: aload_0        
        //  1615: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  1618: fload           16
        //  1620: ldc_w           4.5
        //  1623: fmul           
        //  1624: f2d            
        //  1625: dadd           
        //  1626: aload_0        
        //  1627: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  1630: ldc2_w          2.0
        //  1633: dadd           
        //  1634: aload_0        
        //  1635: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1638: fload           15
        //  1640: ldc_w           4.5
        //  1643: fmul           
        //  1644: f2d            
        //  1645: dadd           
        //  1646: fconst_0       
        //  1647: fconst_0       
        //  1648: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  1651: aload_0        
        //  1652: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1655: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  1658: aload_0        
        //  1659: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1662: aload_0        
        //  1663: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  1666: fload           16
        //  1668: ldc_w           4.5
        //  1671: fmul           
        //  1672: f2d            
        //  1673: dsub           
        //  1674: aload_0        
        //  1675: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  1678: ldc2_w          2.0
        //  1681: dadd           
        //  1682: aload_0        
        //  1683: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1686: fload           15
        //  1688: ldc_w           4.5
        //  1691: fmul           
        //  1692: f2d            
        //  1693: dsub           
        //  1694: fconst_0       
        //  1695: fconst_0       
        //  1696: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  1699: aload_0        
        //  1700: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  1703: getfield        net/minecraft/world/World.isRemote:Z
        //  1706: ifne            1819
        //  1709: aload_0        
        //  1710: getfield        net/minecraft/entity/boss/EntityDragon.hurtTime:I
        //  1713: ifne            1819
        //  1716: aload_0        
        //  1717: aload_0        
        //  1718: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  1721: aload_0        
        //  1722: aload_0        
        //  1723: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1726: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1729: ldc2_w          4.0
        //  1732: ldc2_w          2.0
        //  1735: ldc2_w          4.0
        //  1738: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1741: dconst_0       
        //  1742: ldc2_w          -2.0
        //  1745: dconst_0       
        //  1746: invokevirtual   net/minecraft/util/AxisAlignedBB.offset:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1749: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //  1752: invokespecial   net/minecraft/entity/boss/EntityDragon.collideWithEntities:(Ljava/util/List;)V
        //  1755: aload_0        
        //  1756: aload_0        
        //  1757: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  1760: aload_0        
        //  1761: aload_0        
        //  1762: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1765: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1768: ldc2_w          4.0
        //  1771: ldc2_w          2.0
        //  1774: ldc2_w          4.0
        //  1777: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1780: dconst_0       
        //  1781: ldc2_w          -2.0
        //  1784: dconst_0       
        //  1785: invokevirtual   net/minecraft/util/AxisAlignedBB.offset:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1788: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //  1791: invokespecial   net/minecraft/entity/boss/EntityDragon.collideWithEntities:(Ljava/util/List;)V
        //  1794: aload_0        
        //  1795: aload_0        
        //  1796: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  1799: aload_0        
        //  1800: aload_0        
        //  1801: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1804: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1807: dconst_1       
        //  1808: dconst_1       
        //  1809: dconst_1       
        //  1810: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1813: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //  1816: invokespecial   net/minecraft/entity/boss/EntityDragon.attackEntitiesInList:(Ljava/util/List;)V
        //  1819: aload_0        
        //  1820: iconst_5       
        //  1821: fconst_1       
        //  1822: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1825: astore          17
        //  1827: aload_0        
        //  1828: iconst_0       
        //  1829: fconst_1       
        //  1830: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1833: astore          18
        //  1835: aload_0        
        //  1836: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1839: ldc             3.1415927
        //  1841: fmul           
        //  1842: ldc_w           180.0
        //  1845: fdiv           
        //  1846: aload_0        
        //  1847: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1850: ldc_w           0.01
        //  1853: fmul           
        //  1854: fsub           
        //  1855: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1858: fstore          12
        //  1860: aload_0        
        //  1861: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1864: ldc             3.1415927
        //  1866: fmul           
        //  1867: ldc_w           180.0
        //  1870: fdiv           
        //  1871: aload_0        
        //  1872: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1875: ldc_w           0.01
        //  1878: fmul           
        //  1879: fsub           
        //  1880: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1883: fstore          19
        //  1885: aload_0        
        //  1886: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1889: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  1892: aload_0        
        //  1893: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1896: aload_0        
        //  1897: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  1900: fload           12
        //  1902: ldc_w           5.5
        //  1905: fmul           
        //  1906: fload_3        
        //  1907: fmul           
        //  1908: f2d            
        //  1909: dadd           
        //  1910: aload_0        
        //  1911: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  1914: aload           18
        //  1916: iconst_1       
        //  1917: daload         
        //  1918: aload           17
        //  1920: iconst_1       
        //  1921: daload         
        //  1922: dsub           
        //  1923: dconst_1       
        //  1924: dmul           
        //  1925: dadd           
        //  1926: fload           13
        //  1928: ldc_w           5.5
        //  1931: fmul           
        //  1932: f2d            
        //  1933: dadd           
        //  1934: aload_0        
        //  1935: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1938: fload           19
        //  1940: ldc_w           5.5
        //  1943: fmul           
        //  1944: fload_3        
        //  1945: fmul           
        //  1946: f2d            
        //  1947: dsub           
        //  1948: fconst_0       
        //  1949: fconst_0       
        //  1950: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  1953: goto            2144
        //  1956: aconst_null    
        //  1957: astore          21
        //  1959: iconst_0       
        //  1960: ifne            1969
        //  1963: aload_0        
        //  1964: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1967: astore          21
        //  1969: iconst_0       
        //  1970: iconst_1       
        //  1971: if_icmpne       1980
        //  1974: aload_0        
        //  1975: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1978: astore          21
        //  1980: iconst_0       
        //  1981: iconst_2       
        //  1982: if_icmpne       1991
        //  1985: aload_0        
        //  1986: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail3:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1989: astore          21
        //  1991: aload_0        
        //  1992: bipush          12
        //  1994: fconst_1       
        //  1995: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1998: astore          22
        //  2000: aload_0        
        //  2001: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  2004: ldc             3.1415927
        //  2006: fmul           
        //  2007: ldc_w           180.0
        //  2010: fdiv           
        //  2011: aload_0        
        //  2012: aload           22
        //  2014: iconst_0       
        //  2015: daload         
        //  2016: aload           17
        //  2018: iconst_0       
        //  2019: daload         
        //  2020: dsub           
        //  2021: invokespecial   net/minecraft/entity/boss/EntityDragon.simplifyAngle:(D)F
        //  2024: ldc             3.1415927
        //  2026: fmul           
        //  2027: ldc_w           180.0
        //  2030: fdiv           
        //  2031: fconst_1       
        //  2032: fmul           
        //  2033: fadd           
        //  2034: fstore          23
        //  2036: fload           23
        //  2038: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  2041: fstore          24
        //  2043: fload           23
        //  2045: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  2048: fstore          25
        //  2050: ldc_w           1.5
        //  2053: fstore          26
        //  2055: iconst_1       
        //  2056: i2f            
        //  2057: fconst_2       
        //  2058: fmul           
        //  2059: fstore          27
        //  2061: aload           21
        //  2063: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  2066: aload           21
        //  2068: aload_0        
        //  2069: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  2072: fload           15
        //  2074: fload           26
        //  2076: fmul           
        //  2077: fload           24
        //  2079: fload           27
        //  2081: fmul           
        //  2082: fadd           
        //  2083: fload_3        
        //  2084: fmul           
        //  2085: f2d            
        //  2086: dsub           
        //  2087: aload_0        
        //  2088: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  2091: aload           22
        //  2093: iconst_1       
        //  2094: daload         
        //  2095: aload           17
        //  2097: iconst_1       
        //  2098: daload         
        //  2099: dsub           
        //  2100: dconst_1       
        //  2101: dmul           
        //  2102: dadd           
        //  2103: fload           27
        //  2105: fload           26
        //  2107: fadd           
        //  2108: fload           13
        //  2110: fmul           
        //  2111: f2d            
        //  2112: dsub           
        //  2113: ldc2_w          1.5
        //  2116: dadd           
        //  2117: aload_0        
        //  2118: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  2121: fload           16
        //  2123: fload           26
        //  2125: fmul           
        //  2126: fload           25
        //  2128: fload           27
        //  2130: fmul           
        //  2131: fadd           
        //  2132: fload_3        
        //  2133: fmul           
        //  2134: f2d            
        //  2135: dadd           
        //  2136: fconst_0       
        //  2137: fconst_0       
        //  2138: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  2141: iinc            20, 1
        //  2144: iconst_0       
        //  2145: iconst_3       
        //  2146: if_icmplt       1956
        //  2149: aload_0        
        //  2150: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  2153: getfield        net/minecraft/world/World.isRemote:Z
        //  2156: ifne            2186
        //  2159: aload_0        
        //  2160: aload_0        
        //  2161: aload_0        
        //  2162: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  2165: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  2168: invokespecial   net/minecraft/entity/boss/EntityDragon.destroyBlocksInAABB:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  2171: aload_0        
        //  2172: aload_0        
        //  2173: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  2176: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  2179: invokespecial   net/minecraft/entity/boss/EntityDragon.destroyBlocksInAABB:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  2182: ior            
        //  2183: putfield        net/minecraft/entity/boss/EntityDragon.slowed:Z
        //  2186: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    private void updateDragonEnderCrystal() {
        if (this.healingEnderCrystal != null) {
            if (this.healingEnderCrystal.isDead) {
                if (!this.worldObj.isRemote) {
                    this.attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource(null), 10.0f);
                }
                this.healingEnderCrystal = null;
            }
            else if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0f);
            }
        }
        if (this.rand.nextInt(10) == 0) {
            final float n = 32.0f;
            final List entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, this.getEntityBoundingBox().expand(n, n, n));
            EntityEnderCrystal healingEnderCrystal = null;
            double n2 = Double.MAX_VALUE;
            for (final EntityEnderCrystal entityEnderCrystal : entitiesWithinAABB) {
                final double distanceSqToEntity = entityEnderCrystal.getDistanceSqToEntity(this);
                if (distanceSqToEntity < n2) {
                    n2 = distanceSqToEntity;
                    healingEnderCrystal = entityEnderCrystal;
                }
            }
            this.healingEnderCrystal = healingEnderCrystal;
        }
    }
    
    private void collideWithEntities(final List list) {
        final double n = (this.dragonPartBody.getEntityBoundingBox().minX + this.dragonPartBody.getEntityBoundingBox().maxX) / 2.0;
        final double n2 = (this.dragonPartBody.getEntityBoundingBox().minZ + this.dragonPartBody.getEntityBoundingBox().maxZ) / 2.0;
        for (final Entity entity : list) {
            if (entity instanceof EntityLivingBase) {
                final double n3 = entity.posX - n;
                final double n4 = entity.posZ - n2;
                final double n5 = n3 * n3 + n4 * n4;
                entity.addVelocity(n3 / n5 * 4.0, 0.20000000298023224, n4 / n5 * 4.0);
            }
        }
    }
    
    private void attackEntitiesInList(final List list) {
        while (0 < list.size()) {
            final Entity entity = list.get(0);
            if (entity instanceof EntityLivingBase) {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0f);
                this.func_174815_a(this, entity);
            }
            int n = 0;
            ++n;
        }
    }
    
    private void setNewTarget() {
        this.forceNewTarget = false;
        final ArrayList arrayList = Lists.newArrayList(this.worldObj.playerEntities);
        final Iterator<EntityPlayer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().func_175149_v()) {
                iterator.remove();
            }
        }
        if (this.rand.nextInt(2) == 0 && !arrayList.isEmpty()) {
            this.target = arrayList.get(this.rand.nextInt(arrayList.size()));
        }
        else {
            double n;
            double n2;
            double n3;
            do {
                this.targetX = 0.0;
                this.targetY = 70.0f + this.rand.nextFloat() * 50.0f;
                this.targetZ = 0.0;
                this.targetX += this.rand.nextFloat() * 120.0f - 60.0f;
                this.targetZ += this.rand.nextFloat() * 120.0f - 60.0f;
                n = this.posX - this.targetX;
                n2 = this.posY - this.targetY;
                n3 = this.posZ - this.targetZ;
            } while (n * n + n2 * n2 + n3 * n3 <= 100.0);
            this.target = null;
        }
    }
    
    private float simplifyAngle(final double n) {
        return (float)MathHelper.wrapAngleTo180_double(n);
    }
    
    private boolean destroyBlocksInAABB(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        for (int i = floor_double; i <= floor_double4; ++i) {
            for (int j = floor_double2; j <= floor_double5; ++j) {
                for (int k = floor_double3; k <= floor_double6; ++k) {
                    final Block block = this.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock();
                    if (block.getMaterial() != Material.air && block != Blocks.barrier && block != Blocks.obsidian && block != Blocks.end_stone && block != Blocks.bedrock && block != Blocks.command_block && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                        final boolean b = this.worldObj.setBlockToAir(new BlockPos(i, j, k)) || false;
                    }
                }
            }
        }
        if (false) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * this.rand.nextFloat(), axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * this.rand.nextFloat(), axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * this.rand.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
        }
        return true;
    }
    
    @Override
    public boolean attackEntityFromPart(final EntityDragonPart entityDragonPart, final DamageSource damageSource, float n) {
        if (entityDragonPart != this.dragonPartHead) {
            n = n / 4.0f + 1.0f;
        }
        final float n2 = this.rotationYaw * 3.1415927f / 180.0f;
        final float sin = MathHelper.sin(n2);
        final float cos = MathHelper.cos(n2);
        this.targetX = this.posX + sin * 5.0f + (this.rand.nextFloat() - 0.5f) * 2.0f;
        this.targetY = this.posY + this.rand.nextFloat() * 3.0f + 1.0;
        this.targetZ = this.posZ - cos * 5.0f + (this.rand.nextFloat() - 0.5f) * 2.0f;
        this.target = null;
        if (damageSource.getEntity() instanceof EntityPlayer || damageSource.isExplosion()) {
            this.func_82195_e(damageSource, n);
        }
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (damageSource instanceof EntityDamageSource && ((EntityDamageSource)damageSource).func_180139_w()) {
            this.func_82195_e(damageSource, n);
        }
        return false;
    }
    
    protected boolean func_82195_e(final DamageSource damageSource, final float n) {
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public void func_174812_G() {
        this.setDead();
    }
    
    @Override
    protected void onDeathUpdate() {
        ++this.deathTicks;
        if (this.deathTicks >= 180 && this.deathTicks <= 200) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (this.rand.nextFloat() - 0.5f) * 8.0f, this.posY + 2.0 + (this.rand.nextFloat() - 0.5f) * 4.0f, this.posZ + (this.rand.nextFloat() - 0.5f) * 8.0f, 0.0, 0.0, 0.0, new int[0]);
        }
        if (!this.worldObj.isRemote) {
            if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                while (2000 > 0) {
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, EntityXPOrb.getXPSplit(2000)));
                }
            }
            if (this.deathTicks == 1) {
                this.worldObj.func_175669_a(1018, new BlockPos(this), 0);
            }
        }
        this.moveEntity(0.0, 0.10000000149011612, 0.0);
        final float n = this.rotationYaw + 20.0f;
        this.rotationYaw = n;
        this.renderYawOffset = n;
        if (this.deathTicks == 200 && !this.worldObj.isRemote) {
            while (2000 > 0) {
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, EntityXPOrb.getXPSplit(2000)));
            }
            this.func_175499_a(new BlockPos(this.posX, 64.0, this.posZ));
            this.setDead();
        }
    }
    
    private void func_175499_a(final BlockPos blockPos) {
        while (-1 <= 32) {
            while (-4 <= 4) {
                while (-4 <= 4) {
                    final double n = 32;
                    if (n <= 12.25) {
                        final BlockPos add = blockPos.add(-4, -1, -4);
                        if (-1 < 0) {
                            if (n <= 6.25) {
                                this.worldObj.setBlockState(add, Blocks.bedrock.getDefaultState());
                            }
                        }
                        else if (-1 > 0) {
                            this.worldObj.setBlockState(add, Blocks.air.getDefaultState());
                        }
                        else if (n > 6.25) {
                            this.worldObj.setBlockState(add, Blocks.bedrock.getDefaultState());
                        }
                        else {
                            this.worldObj.setBlockState(add, Blocks.end_portal.getDefaultState());
                        }
                    }
                    int n2 = 0;
                    ++n2;
                }
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        this.worldObj.setBlockState(blockPos, Blocks.bedrock.getDefaultState());
        this.worldObj.setBlockState(blockPos.offsetUp(), Blocks.bedrock.getDefaultState());
        final BlockPos offsetUp = blockPos.offsetUp(2);
        this.worldObj.setBlockState(offsetUp, Blocks.bedrock.getDefaultState());
        this.worldObj.setBlockState(offsetUp.offsetWest(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.EAST));
        this.worldObj.setBlockState(offsetUp.offsetEast(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.WEST));
        this.worldObj.setBlockState(offsetUp.offsetNorth(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.SOUTH));
        this.worldObj.setBlockState(offsetUp.offsetSouth(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.NORTH));
        this.worldObj.setBlockState(blockPos.offsetUp(3), Blocks.bedrock.getDefaultState());
        this.worldObj.setBlockState(blockPos.offsetUp(4), Blocks.dragon_egg.getDefaultState());
    }
    
    @Override
    protected void despawnEntity() {
    }
    
    @Override
    public Entity[] getParts() {
        return this.dragonPartArray;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public World func_82194_d() {
        return this.worldObj;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.enderdragon.growl";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.enderdragon.hit";
    }
    
    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }
    
    static {
        __OBFID = "CL_00001659";
    }
}
