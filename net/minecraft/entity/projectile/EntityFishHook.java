package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityFishHook extends Entity
{
    private static final List JUNK;
    private static final List VALUABLES;
    private static final List FISH;
    private int xTile;
    private int yTile;
    private int zTile;
    private Block inTile;
    private boolean inGround;
    public int shake;
    public EntityPlayer angler;
    private int ticksInGround;
    private int ticksInAir;
    private int ticksCatchable;
    private int ticksCaughtDelay;
    private int ticksCatchableDelay;
    private float fishApproachAngle;
    public Entity caughtEntity;
    private int fishPosRotationIncrements;
    private double fishX;
    private double fishY;
    private double fishZ;
    private double fishYaw;
    private double fishPitch;
    private double clientMotionX;
    private double clientMotionY;
    private double clientMotionZ;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001663";
        JUNK = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10).setMaxDamagePercent(0.9f), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack(Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2).setMaxDamagePercent(0.9f), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, EnumDyeColor.BLACK.getDyeColorDamage()), 1), new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10));
        VALUABLES = Arrays.asList(new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), new WeightedRandomFishable(new ItemStack(Items.bow), 1).setMaxDamagePercent(0.25f).setEnchantable(), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1).setMaxDamagePercent(0.25f).setEnchantable(), new WeightedRandomFishable(new ItemStack(Items.book), 1).setEnchantable());
        FISH = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getItemDamage()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.getItemDamage()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.getItemDamage()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.getItemDamage()), 13));
    }
    
    public static List func_174855_j() {
        return EntityFishHook.FISH;
    }
    
    public EntityFishHook(final World world) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
    }
    
    public EntityFishHook(final World world, final double n, final double n2, final double n3, final EntityPlayer angler) {
        this(world);
        this.setPosition(n, n2, n3);
        this.ignoreFrustumCheck = true;
        this.angler = angler;
        angler.fishEntity = this;
    }
    
    public EntityFishHook(final World world, final EntityPlayer angler) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.ignoreFrustumCheck = true;
        this.angler = angler;
        (this.angler.fishEntity = this).setSize(0.25f, 0.25f);
        this.setLocationAndAngles(angler.posX, angler.posY + angler.getEyeHeight(), angler.posZ, angler.rotationYaw, angler.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        final float n = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        final double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0 * 64.0;
        return n < n2 * n2;
    }
    
    public void handleHookCasting(double motionX, double motionY, double motionZ, final float n, final float n2) {
        final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= sqrt_double;
        motionY /= sqrt_double;
        motionZ /= sqrt_double;
        motionX += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionY += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionZ += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionX *= n;
        motionY *= n;
        motionZ *= n;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        final float sqrt_double2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        final float n3 = (float)(Math.atan2(motionX, motionZ) * 180.0 / 3.141592653589793);
        this.rotationYaw = n3;
        this.prevRotationYaw = n3;
        final float n4 = (float)(Math.atan2(motionY, sqrt_double2) * 180.0 / 3.141592653589793);
        this.rotationPitch = n4;
        this.prevRotationPitch = n4;
        this.ticksInGround = 0;
    }
    
    @Override
    public void func_180426_a(final double fishX, final double fishY, final double fishZ, final float n, final float n2, final int fishPosRotationIncrements, final boolean b) {
        this.fishX = fishX;
        this.fishY = fishY;
        this.fishZ = fishZ;
        this.fishYaw = n;
        this.fishPitch = n2;
        this.fishPosRotationIncrements = fishPosRotationIncrements;
        this.motionX = this.clientMotionX;
        this.motionY = this.clientMotionY;
        this.motionZ = this.clientMotionZ;
    }
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.clientMotionX = n;
        this.motionY = n2;
        this.clientMotionY = n2;
        this.motionZ = n3;
        this.clientMotionZ = n3;
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/minecraft/entity/Entity.onUpdate:()V
        //     4: aload_0        
        //     5: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //     8: ifle            169
        //    11: aload_0        
        //    12: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //    15: aload_0        
        //    16: getfield        net/minecraft/entity/projectile/EntityFishHook.fishX:D
        //    19: aload_0        
        //    20: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //    23: dsub           
        //    24: aload_0        
        //    25: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //    28: i2d            
        //    29: ddiv           
        //    30: dadd           
        //    31: dstore_1       
        //    32: aload_0        
        //    33: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //    36: aload_0        
        //    37: getfield        net/minecraft/entity/projectile/EntityFishHook.fishY:D
        //    40: aload_0        
        //    41: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //    44: dsub           
        //    45: aload_0        
        //    46: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //    49: i2d            
        //    50: ddiv           
        //    51: dadd           
        //    52: dstore_3       
        //    53: aload_0        
        //    54: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //    57: aload_0        
        //    58: getfield        net/minecraft/entity/projectile/EntityFishHook.fishZ:D
        //    61: aload_0        
        //    62: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //    65: dsub           
        //    66: aload_0        
        //    67: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //    70: i2d            
        //    71: ddiv           
        //    72: dadd           
        //    73: dstore          5
        //    75: aload_0        
        //    76: getfield        net/minecraft/entity/projectile/EntityFishHook.fishYaw:D
        //    79: aload_0        
        //    80: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //    83: f2d            
        //    84: dsub           
        //    85: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_double:(D)D
        //    88: dstore          7
        //    90: aload_0        
        //    91: aload_0        
        //    92: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //    95: f2d            
        //    96: dload           7
        //    98: aload_0        
        //    99: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //   102: i2d            
        //   103: ddiv           
        //   104: dadd           
        //   105: d2f            
        //   106: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //   109: aload_0        
        //   110: aload_0        
        //   111: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   114: f2d            
        //   115: aload_0        
        //   116: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPitch:D
        //   119: aload_0        
        //   120: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   123: f2d            
        //   124: dsub           
        //   125: aload_0        
        //   126: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //   129: i2d            
        //   130: ddiv           
        //   131: dadd           
        //   132: d2f            
        //   133: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   136: aload_0        
        //   137: dup            
        //   138: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //   141: iconst_1       
        //   142: isub           
        //   143: putfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //   146: aload_0        
        //   147: dload_1        
        //   148: dload_3        
        //   149: dload           5
        //   151: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setPosition:(DDD)V
        //   154: aload_0        
        //   155: aload_0        
        //   156: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //   159: aload_0        
        //   160: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   163: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setRotation:(FF)V
        //   166: goto            2314
        //   169: aload_0        
        //   170: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //   173: getfield        net/minecraft/world/World.isRemote:Z
        //   176: ifne            323
        //   179: aload_0        
        //   180: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   183: invokevirtual   net/minecraft/entity/player/EntityPlayer.getCurrentEquippedItem:()Lnet/minecraft/item/ItemStack;
        //   186: astore_1       
        //   187: aload_0        
        //   188: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   191: getfield        net/minecraft/entity/player/EntityPlayer.isDead:Z
        //   194: ifne            236
        //   197: aload_0        
        //   198: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   201: invokevirtual   net/minecraft/entity/player/EntityPlayer.isEntityAlive:()Z
        //   204: ifeq            236
        //   207: aload_1        
        //   208: ifnull          236
        //   211: aload_1        
        //   212: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   215: getstatic       net/minecraft/init/Items.fishing_rod:Lnet/minecraft/item/ItemFishingRod;
        //   218: if_acmpne       236
        //   221: aload_0        
        //   222: aload_0        
        //   223: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   226: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getDistanceSqToEntity:(Lnet/minecraft/entity/Entity;)D
        //   229: ldc2_w          1024.0
        //   232: dcmpl          
        //   233: ifle            249
        //   236: aload_0        
        //   237: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setDead:()V
        //   240: aload_0        
        //   241: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   244: aconst_null    
        //   245: putfield        net/minecraft/entity/player/EntityPlayer.fishEntity:Lnet/minecraft/entity/projectile/EntityFishHook;
        //   248: return         
        //   249: aload_0        
        //   250: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   253: ifnull          323
        //   256: aload_0        
        //   257: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   260: getfield        net/minecraft/entity/Entity.isDead:Z
        //   263: ifne            318
        //   266: aload_0        
        //   267: aload_0        
        //   268: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   271: getfield        net/minecraft/entity/Entity.posX:D
        //   274: putfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   277: aload_0        
        //   278: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   281: getfield        net/minecraft/entity/Entity.height:F
        //   284: f2d            
        //   285: dstore_2       
        //   286: aload_0        
        //   287: aload_0        
        //   288: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   291: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   294: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   297: dload_2        
        //   298: ldc2_w          0.8
        //   301: dmul           
        //   302: dadd           
        //   303: putfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   306: aload_0        
        //   307: aload_0        
        //   308: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   311: getfield        net/minecraft/entity/Entity.posZ:D
        //   314: putfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   317: return         
        //   318: aload_0        
        //   319: aconst_null    
        //   320: putfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   323: aload_0        
        //   324: getfield        net/minecraft/entity/projectile/EntityFishHook.shake:I
        //   327: ifle            340
        //   330: aload_0        
        //   331: dup            
        //   332: getfield        net/minecraft/entity/projectile/EntityFishHook.shake:I
        //   335: iconst_1       
        //   336: isub           
        //   337: putfield        net/minecraft/entity/projectile/EntityFishHook.shake:I
        //   340: aload_0        
        //   341: getfield        net/minecraft/entity/projectile/EntityFishHook.inGround:Z
        //   344: ifeq            491
        //   347: aload_0        
        //   348: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //   351: new             Lnet/minecraft/util/BlockPos;
        //   354: dup            
        //   355: aload_0        
        //   356: getfield        net/minecraft/entity/projectile/EntityFishHook.xTile:I
        //   359: aload_0        
        //   360: getfield        net/minecraft/entity/projectile/EntityFishHook.yTile:I
        //   363: aload_0        
        //   364: getfield        net/minecraft/entity/projectile/EntityFishHook.zTile:I
        //   367: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   370: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   373: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   378: aload_0        
        //   379: getfield        net/minecraft/entity/projectile/EntityFishHook.inTile:Lnet/minecraft/block/Block;
        //   382: if_acmpne       410
        //   385: aload_0        
        //   386: dup            
        //   387: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksInGround:I
        //   390: iconst_1       
        //   391: iadd           
        //   392: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksInGround:I
        //   395: aload_0        
        //   396: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksInGround:I
        //   399: sipush          1200
        //   402: if_icmpne       409
        //   405: aload_0        
        //   406: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setDead:()V
        //   409: return         
        //   410: aload_0        
        //   411: iconst_0       
        //   412: putfield        net/minecraft/entity/projectile/EntityFishHook.inGround:Z
        //   415: aload_0        
        //   416: dup            
        //   417: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   420: aload_0        
        //   421: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //   424: invokevirtual   java/util/Random.nextFloat:()F
        //   427: ldc_w           0.2
        //   430: fmul           
        //   431: f2d            
        //   432: dmul           
        //   433: putfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   436: aload_0        
        //   437: dup            
        //   438: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   441: aload_0        
        //   442: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //   445: invokevirtual   java/util/Random.nextFloat:()F
        //   448: ldc_w           0.2
        //   451: fmul           
        //   452: f2d            
        //   453: dmul           
        //   454: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   457: aload_0        
        //   458: dup            
        //   459: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   462: aload_0        
        //   463: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //   466: invokevirtual   java/util/Random.nextFloat:()F
        //   469: ldc_w           0.2
        //   472: fmul           
        //   473: f2d            
        //   474: dmul           
        //   475: putfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   478: aload_0        
        //   479: iconst_0       
        //   480: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksInGround:I
        //   483: aload_0        
        //   484: iconst_0       
        //   485: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksInAir:I
        //   488: goto            501
        //   491: aload_0        
        //   492: dup            
        //   493: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksInAir:I
        //   496: iconst_1       
        //   497: iadd           
        //   498: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksInAir:I
        //   501: new             Lnet/minecraft/util/Vec3;
        //   504: dup            
        //   505: aload_0        
        //   506: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   509: aload_0        
        //   510: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   513: aload_0        
        //   514: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   517: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   520: astore_1       
        //   521: new             Lnet/minecraft/util/Vec3;
        //   524: dup            
        //   525: aload_0        
        //   526: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   529: aload_0        
        //   530: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   533: dadd           
        //   534: aload_0        
        //   535: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   538: aload_0        
        //   539: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   542: dadd           
        //   543: aload_0        
        //   544: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   547: aload_0        
        //   548: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   551: dadd           
        //   552: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   555: astore_2       
        //   556: aload_0        
        //   557: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //   560: aload_1        
        //   561: aload_2        
        //   562: invokevirtual   net/minecraft/world/World.rayTraceBlocks:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   565: astore_3       
        //   566: new             Lnet/minecraft/util/Vec3;
        //   569: dup            
        //   570: aload_0        
        //   571: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   574: aload_0        
        //   575: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   578: aload_0        
        //   579: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   582: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   585: astore_1       
        //   586: new             Lnet/minecraft/util/Vec3;
        //   589: dup            
        //   590: aload_0        
        //   591: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   594: aload_0        
        //   595: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   598: dadd           
        //   599: aload_0        
        //   600: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   603: aload_0        
        //   604: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   607: dadd           
        //   608: aload_0        
        //   609: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   612: aload_0        
        //   613: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   616: dadd           
        //   617: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   620: astore_2       
        //   621: aload_3        
        //   622: ifnull          654
        //   625: new             Lnet/minecraft/util/Vec3;
        //   628: dup            
        //   629: aload_3        
        //   630: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   633: getfield        net/minecraft/util/Vec3.xCoord:D
        //   636: aload_3        
        //   637: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   640: getfield        net/minecraft/util/Vec3.yCoord:D
        //   643: aload_3        
        //   644: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   647: getfield        net/minecraft/util/Vec3.zCoord:D
        //   650: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   653: astore_2       
        //   654: aconst_null    
        //   655: astore          4
        //   657: aload_0        
        //   658: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //   661: aload_0        
        //   662: aload_0        
        //   663: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   666: aload_0        
        //   667: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   670: aload_0        
        //   671: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   674: aload_0        
        //   675: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   678: invokevirtual   net/minecraft/util/AxisAlignedBB.addCoord:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   681: dconst_1       
        //   682: dconst_1       
        //   683: dconst_1       
        //   684: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   687: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //   690: astore          5
        //   692: dconst_0       
        //   693: dstore          6
        //   695: goto            811
        //   698: aload           5
        //   700: iconst_0       
        //   701: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   706: checkcast       Lnet/minecraft/entity/Entity;
        //   709: astore          11
        //   711: aload           11
        //   713: invokevirtual   net/minecraft/entity/Entity.canBeCollidedWith:()Z
        //   716: ifeq            808
        //   719: aload           11
        //   721: aload_0        
        //   722: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   725: if_acmpne       736
        //   728: aload_0        
        //   729: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksInAir:I
        //   732: iconst_5       
        //   733: if_icmplt       808
        //   736: ldc_w           0.3
        //   739: fstore          12
        //   741: aload           11
        //   743: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   746: fload           12
        //   748: f2d            
        //   749: fload           12
        //   751: f2d            
        //   752: fload           12
        //   754: f2d            
        //   755: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   758: astore          13
        //   760: aload           13
        //   762: aload_1        
        //   763: aload_2        
        //   764: invokevirtual   net/minecraft/util/AxisAlignedBB.calculateIntercept:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   767: astore          14
        //   769: aload           14
        //   771: ifnull          808
        //   774: aload_1        
        //   775: aload           14
        //   777: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   780: invokevirtual   net/minecraft/util/Vec3.distanceTo:(Lnet/minecraft/util/Vec3;)D
        //   783: dstore          8
        //   785: dload           8
        //   787: dload           6
        //   789: dcmpg          
        //   790: iflt            800
        //   793: dload           6
        //   795: dconst_0       
        //   796: dcmpl          
        //   797: ifne            808
        //   800: aload           11
        //   802: astore          4
        //   804: dload           8
        //   806: dstore          6
        //   808: iinc            10, 1
        //   811: iconst_0       
        //   812: aload           5
        //   814: invokeinterface java/util/List.size:()I
        //   819: if_icmplt       698
        //   822: aload           4
        //   824: ifnull          837
        //   827: new             Lnet/minecraft/util/MovingObjectPosition;
        //   830: dup            
        //   831: aload           4
        //   833: invokespecial   net/minecraft/util/MovingObjectPosition.<init>:(Lnet/minecraft/entity/Entity;)V
        //   836: astore_3       
        //   837: aload_3        
        //   838: ifnull          883
        //   841: aload_3        
        //   842: getfield        net/minecraft/util/MovingObjectPosition.entityHit:Lnet/minecraft/entity/Entity;
        //   845: ifnull          878
        //   848: aload_3        
        //   849: getfield        net/minecraft/util/MovingObjectPosition.entityHit:Lnet/minecraft/entity/Entity;
        //   852: aload_0        
        //   853: aload_0        
        //   854: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   857: invokestatic    net/minecraft/util/DamageSource.causeThrownDamage:(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/DamageSource;
        //   860: fconst_0       
        //   861: invokevirtual   net/minecraft/entity/Entity.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //   864: ifeq            883
        //   867: aload_0        
        //   868: aload_3        
        //   869: getfield        net/minecraft/util/MovingObjectPosition.entityHit:Lnet/minecraft/entity/Entity;
        //   872: putfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   875: goto            883
        //   878: aload_0        
        //   879: iconst_1       
        //   880: putfield        net/minecraft/entity/projectile/EntityFishHook.inGround:Z
        //   883: aload_0        
        //   884: getfield        net/minecraft/entity/projectile/EntityFishHook.inGround:Z
        //   887: ifne            2314
        //   890: aload_0        
        //   891: aload_0        
        //   892: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   895: aload_0        
        //   896: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   899: aload_0        
        //   900: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   903: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.moveEntity:(DDD)V
        //   906: aload_0        
        //   907: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   910: aload_0        
        //   911: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   914: dmul           
        //   915: aload_0        
        //   916: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   919: aload_0        
        //   920: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   923: dmul           
        //   924: dadd           
        //   925: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   928: fstore          10
        //   930: aload_0        
        //   931: aload_0        
        //   932: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   935: aload_0        
        //   936: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   939: invokestatic    java/lang/Math.atan2:(DD)D
        //   942: ldc2_w          180.0
        //   945: dmul           
        //   946: ldc2_w          3.141592653589793
        //   949: ddiv           
        //   950: d2f            
        //   951: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //   954: aload_0        
        //   955: aload_0        
        //   956: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   959: fload           10
        //   961: f2d            
        //   962: invokestatic    java/lang/Math.atan2:(DD)D
        //   965: ldc2_w          180.0
        //   968: dmul           
        //   969: ldc2_w          3.141592653589793
        //   972: ddiv           
        //   973: d2f            
        //   974: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   977: goto            992
        //   980: aload_0        
        //   981: dup            
        //   982: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //   985: ldc_w           360.0
        //   988: fsub           
        //   989: putfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //   992: aload_0        
        //   993: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   996: aload_0        
        //   997: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1000: fsub           
        //  1001: ldc_w           -180.0
        //  1004: fcmpg          
        //  1005: iflt            980
        //  1008: goto            1023
        //  1011: aload_0        
        //  1012: dup            
        //  1013: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1016: ldc_w           360.0
        //  1019: fadd           
        //  1020: putfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1023: aload_0        
        //  1024: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //  1027: aload_0        
        //  1028: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1031: fsub           
        //  1032: ldc             180.0
        //  1034: fcmpl          
        //  1035: ifge            1011
        //  1038: goto            1053
        //  1041: aload_0        
        //  1042: dup            
        //  1043: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1046: ldc_w           360.0
        //  1049: fsub           
        //  1050: putfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1053: aload_0        
        //  1054: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //  1057: aload_0        
        //  1058: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1061: fsub           
        //  1062: ldc_w           -180.0
        //  1065: fcmpg          
        //  1066: iflt            1041
        //  1069: goto            1084
        //  1072: aload_0        
        //  1073: dup            
        //  1074: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1077: ldc_w           360.0
        //  1080: fadd           
        //  1081: putfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1084: aload_0        
        //  1085: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //  1088: aload_0        
        //  1089: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1092: fsub           
        //  1093: ldc             180.0
        //  1095: fcmpl          
        //  1096: ifge            1072
        //  1099: aload_0        
        //  1100: aload_0        
        //  1101: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1104: aload_0        
        //  1105: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //  1108: aload_0        
        //  1109: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1112: fsub           
        //  1113: ldc_w           0.2
        //  1116: fmul           
        //  1117: fadd           
        //  1118: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //  1121: aload_0        
        //  1122: aload_0        
        //  1123: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1126: aload_0        
        //  1127: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //  1130: aload_0        
        //  1131: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1134: fsub           
        //  1135: ldc_w           0.2
        //  1138: fmul           
        //  1139: fadd           
        //  1140: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //  1143: ldc_w           0.92
        //  1146: fstore          11
        //  1148: aload_0        
        //  1149: getfield        net/minecraft/entity/projectile/EntityFishHook.onGround:Z
        //  1152: ifne            1162
        //  1155: aload_0        
        //  1156: getfield        net/minecraft/entity/projectile/EntityFishHook.isCollidedHorizontally:Z
        //  1159: ifeq            1167
        //  1162: ldc_w           0.5
        //  1165: fstore          11
        //  1167: dconst_0       
        //  1168: dstore          13
        //  1170: goto            1284
        //  1173: aload_0        
        //  1174: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1177: astore          18
        //  1179: aload           18
        //  1181: getfield        net/minecraft/util/AxisAlignedBB.maxY:D
        //  1184: aload           18
        //  1186: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1189: dsub           
        //  1190: dstore          19
        //  1192: aload           18
        //  1194: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1197: dload           19
        //  1199: iconst_0       
        //  1200: i2d            
        //  1201: dmul           
        //  1202: iconst_5       
        //  1203: i2d            
        //  1204: ddiv           
        //  1205: dadd           
        //  1206: dstore          21
        //  1208: aload           18
        //  1210: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1213: dload           19
        //  1215: iconst_1       
        //  1216: i2d            
        //  1217: dmul           
        //  1218: iconst_5       
        //  1219: i2d            
        //  1220: ddiv           
        //  1221: dadd           
        //  1222: dstore          15
        //  1224: new             Lnet/minecraft/util/AxisAlignedBB;
        //  1227: dup            
        //  1228: aload           18
        //  1230: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //  1233: dload           21
        //  1235: aload           18
        //  1237: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //  1240: aload           18
        //  1242: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //  1245: dload           15
        //  1247: aload           18
        //  1249: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //  1252: invokespecial   net/minecraft/util/AxisAlignedBB.<init>:(DDDDDD)V
        //  1255: astore          23
        //  1257: aload_0        
        //  1258: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1261: aload           23
        //  1263: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //  1266: invokevirtual   net/minecraft/world/World.isAABBInMaterial:(Lnet/minecraft/util/AxisAlignedBB;Lnet/minecraft/block/material/Material;)Z
        //  1269: ifeq            1281
        //  1272: dload           13
        //  1274: dconst_1       
        //  1275: iconst_5       
        //  1276: i2d            
        //  1277: ddiv           
        //  1278: dadd           
        //  1279: dstore          13
        //  1281: iinc            17, 1
        //  1284: iconst_0       
        //  1285: iconst_5       
        //  1286: if_icmplt       1173
        //  1289: aload_0        
        //  1290: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1293: getfield        net/minecraft/world/World.isRemote:Z
        //  1296: ifne            2208
        //  1299: dload           13
        //  1301: dconst_0       
        //  1302: dcmpl          
        //  1303: ifle            2208
        //  1306: aload_0        
        //  1307: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1310: checkcast       Lnet/minecraft/world/WorldServer;
        //  1313: astore          17
        //  1315: new             Lnet/minecraft/util/BlockPos;
        //  1318: dup            
        //  1319: aload_0        
        //  1320: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  1323: invokevirtual   net/minecraft/util/BlockPos.offsetUp:()Lnet/minecraft/util/BlockPos;
        //  1326: astore          19
        //  1328: aload_0        
        //  1329: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1332: invokevirtual   java/util/Random.nextFloat:()F
        //  1335: ldc             0.25
        //  1337: fcmpg          
        //  1338: ifge            1353
        //  1341: aload_0        
        //  1342: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1345: aload           19
        //  1347: invokevirtual   net/minecraft/world/World.func_175727_C:(Lnet/minecraft/util/BlockPos;)Z
        //  1350: ifeq            1353
        //  1353: aload_0        
        //  1354: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1357: invokevirtual   java/util/Random.nextFloat:()F
        //  1360: ldc_w           0.5
        //  1363: fcmpg          
        //  1364: ifge            1382
        //  1367: aload_0        
        //  1368: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1371: aload           19
        //  1373: invokevirtual   net/minecraft/world/World.isAgainstSky:(Lnet/minecraft/util/BlockPos;)Z
        //  1376: ifne            1382
        //  1379: iinc            18, -1
        //  1382: aload_0        
        //  1383: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1386: ifle            1419
        //  1389: aload_0        
        //  1390: dup            
        //  1391: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1394: iconst_1       
        //  1395: isub           
        //  1396: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1399: aload_0        
        //  1400: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1403: ifgt            2164
        //  1406: aload_0        
        //  1407: iconst_0       
        //  1408: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1411: aload_0        
        //  1412: iconst_0       
        //  1413: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1416: goto            2164
        //  1419: aload_0        
        //  1420: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1423: ifle            1839
        //  1426: aload_0        
        //  1427: dup            
        //  1428: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1431: iconst_2       
        //  1432: isub           
        //  1433: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1436: aload_0        
        //  1437: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1440: ifgt            1614
        //  1443: aload_0        
        //  1444: dup            
        //  1445: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  1448: ldc2_w          0.20000000298023224
        //  1451: dsub           
        //  1452: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  1455: aload_0        
        //  1456: ldc_w           "random.splash"
        //  1459: ldc             0.25
        //  1461: fconst_1       
        //  1462: aload_0        
        //  1463: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1466: invokevirtual   java/util/Random.nextFloat:()F
        //  1469: aload_0        
        //  1470: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1473: invokevirtual   java/util/Random.nextFloat:()F
        //  1476: fsub           
        //  1477: ldc_w           0.4
        //  1480: fmul           
        //  1481: fadd           
        //  1482: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.playSound:(Ljava/lang/String;FF)V
        //  1485: aload_0        
        //  1486: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1489: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1492: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  1495: i2f            
        //  1496: fstore          20
        //  1498: aload           17
        //  1500: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //  1503: aload_0        
        //  1504: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  1507: fload           20
        //  1509: fconst_1       
        //  1510: fadd           
        //  1511: f2d            
        //  1512: aload_0        
        //  1513: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  1516: fconst_1       
        //  1517: aload_0        
        //  1518: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1521: ldc_w           20.0
        //  1524: fmul           
        //  1525: fadd           
        //  1526: f2i            
        //  1527: aload_0        
        //  1528: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1531: f2d            
        //  1532: dconst_0       
        //  1533: aload_0        
        //  1534: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1537: f2d            
        //  1538: ldc2_w          0.20000000298023224
        //  1541: iconst_0       
        //  1542: newarray        I
        //  1544: invokevirtual   net/minecraft/world/WorldServer.func_175739_a:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1547: aload           17
        //  1549: getstatic       net/minecraft/util/EnumParticleTypes.WATER_WAKE:Lnet/minecraft/util/EnumParticleTypes;
        //  1552: aload_0        
        //  1553: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  1556: fload           20
        //  1558: fconst_1       
        //  1559: fadd           
        //  1560: f2d            
        //  1561: aload_0        
        //  1562: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  1565: fconst_1       
        //  1566: aload_0        
        //  1567: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1570: ldc_w           20.0
        //  1573: fmul           
        //  1574: fadd           
        //  1575: f2i            
        //  1576: aload_0        
        //  1577: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1580: f2d            
        //  1581: dconst_0       
        //  1582: aload_0        
        //  1583: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1586: f2d            
        //  1587: ldc2_w          0.20000000298023224
        //  1590: iconst_0       
        //  1591: newarray        I
        //  1593: invokevirtual   net/minecraft/world/WorldServer.func_175739_a:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1596: aload_0        
        //  1597: aload_0        
        //  1598: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1601: bipush          10
        //  1603: bipush          30
        //  1605: invokestatic    net/minecraft/util/MathHelper.getRandomIntegerInRange:(Ljava/util/Random;II)I
        //  1608: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1611: goto            2164
        //  1614: aload_0        
        //  1615: aload_0        
        //  1616: getfield        net/minecraft/entity/projectile/EntityFishHook.fishApproachAngle:F
        //  1619: f2d            
        //  1620: aload_0        
        //  1621: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1624: invokevirtual   java/util/Random.nextGaussian:()D
        //  1627: ldc2_w          4.0
        //  1630: dmul           
        //  1631: dadd           
        //  1632: d2f            
        //  1633: putfield        net/minecraft/entity/projectile/EntityFishHook.fishApproachAngle:F
        //  1636: aload_0        
        //  1637: getfield        net/minecraft/entity/projectile/EntityFishHook.fishApproachAngle:F
        //  1640: ldc_w           0.017453292
        //  1643: fmul           
        //  1644: fstore          20
        //  1646: fload           20
        //  1648: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1651: fstore          24
        //  1653: fload           20
        //  1655: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1658: fstore          21
        //  1660: aload_0        
        //  1661: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  1664: fload           24
        //  1666: aload_0        
        //  1667: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1670: i2f            
        //  1671: fmul           
        //  1672: ldc_w           0.1
        //  1675: fmul           
        //  1676: f2d            
        //  1677: dadd           
        //  1678: dstore          15
        //  1680: aload_0        
        //  1681: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1684: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1687: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  1690: i2f            
        //  1691: fconst_1       
        //  1692: fadd           
        //  1693: f2d            
        //  1694: dstore          25
        //  1696: aload_0        
        //  1697: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  1700: fload           21
        //  1702: aload_0        
        //  1703: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1706: i2f            
        //  1707: fmul           
        //  1708: ldc_w           0.1
        //  1711: fmul           
        //  1712: f2d            
        //  1713: dadd           
        //  1714: dstore          22
        //  1716: aload_0        
        //  1717: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1720: invokevirtual   java/util/Random.nextFloat:()F
        //  1723: ldc_w           0.15
        //  1726: fcmpg          
        //  1727: ifge            1762
        //  1730: aload           17
        //  1732: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //  1735: dload           15
        //  1737: dload           25
        //  1739: ldc2_w          0.10000000149011612
        //  1742: dsub           
        //  1743: dload           22
        //  1745: iconst_1       
        //  1746: fload           24
        //  1748: f2d            
        //  1749: ldc2_w          0.1
        //  1752: fload           21
        //  1754: f2d            
        //  1755: dconst_0       
        //  1756: iconst_0       
        //  1757: newarray        I
        //  1759: invokevirtual   net/minecraft/world/WorldServer.func_175739_a:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1762: fload           24
        //  1764: ldc_w           0.04
        //  1767: fmul           
        //  1768: fstore          27
        //  1770: fload           21
        //  1772: ldc_w           0.04
        //  1775: fmul           
        //  1776: fstore          28
        //  1778: aload           17
        //  1780: getstatic       net/minecraft/util/EnumParticleTypes.WATER_WAKE:Lnet/minecraft/util/EnumParticleTypes;
        //  1783: dload           15
        //  1785: dload           25
        //  1787: dload           22
        //  1789: iconst_0       
        //  1790: fload           28
        //  1792: f2d            
        //  1793: ldc2_w          0.01
        //  1796: fload           27
        //  1798: fneg           
        //  1799: f2d            
        //  1800: dconst_1       
        //  1801: iconst_0       
        //  1802: newarray        I
        //  1804: invokevirtual   net/minecraft/world/WorldServer.func_175739_a:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1807: aload           17
        //  1809: getstatic       net/minecraft/util/EnumParticleTypes.WATER_WAKE:Lnet/minecraft/util/EnumParticleTypes;
        //  1812: dload           15
        //  1814: dload           25
        //  1816: dload           22
        //  1818: iconst_0       
        //  1819: fload           28
        //  1821: fneg           
        //  1822: f2d            
        //  1823: ldc2_w          0.01
        //  1826: fload           27
        //  1828: f2d            
        //  1829: dconst_1       
        //  1830: iconst_0       
        //  1831: newarray        I
        //  1833: invokevirtual   net/minecraft/world/WorldServer.func_175739_a:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1836: goto            2164
        //  1839: aload_0        
        //  1840: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1843: ifle            2127
        //  1846: aload_0        
        //  1847: dup            
        //  1848: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1851: iconst_2       
        //  1852: isub           
        //  1853: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1856: ldc_w           0.15
        //  1859: fstore          20
        //  1861: aload_0        
        //  1862: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1865: bipush          20
        //  1867: if_icmpge       1892
        //  1870: fload           20
        //  1872: f2d            
        //  1873: bipush          20
        //  1875: aload_0        
        //  1876: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1879: isub           
        //  1880: i2d            
        //  1881: ldc2_w          0.05
        //  1884: dmul           
        //  1885: dadd           
        //  1886: d2f            
        //  1887: fstore          20
        //  1889: goto            1951
        //  1892: aload_0        
        //  1893: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1896: bipush          40
        //  1898: if_icmpge       1923
        //  1901: fload           20
        //  1903: f2d            
        //  1904: bipush          40
        //  1906: aload_0        
        //  1907: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1910: isub           
        //  1911: i2d            
        //  1912: ldc2_w          0.02
        //  1915: dmul           
        //  1916: dadd           
        //  1917: d2f            
        //  1918: fstore          20
        //  1920: goto            1951
        //  1923: aload_0        
        //  1924: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1927: bipush          60
        //  1929: if_icmpge       1951
        //  1932: fload           20
        //  1934: f2d            
        //  1935: bipush          60
        //  1937: aload_0        
        //  1938: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1941: isub           
        //  1942: i2d            
        //  1943: ldc2_w          0.01
        //  1946: dmul           
        //  1947: dadd           
        //  1948: d2f            
        //  1949: fstore          20
        //  1951: aload_0        
        //  1952: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1955: invokevirtual   java/util/Random.nextFloat:()F
        //  1958: fload           20
        //  1960: fcmpg          
        //  1961: ifge            2087
        //  1964: aload_0        
        //  1965: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1968: fconst_0       
        //  1969: ldc_w           360.0
        //  1972: invokestatic    net/minecraft/util/MathHelper.randomFloatClamp:(Ljava/util/Random;FF)F
        //  1975: ldc_w           0.017453292
        //  1978: fmul           
        //  1979: fstore          24
        //  1981: aload_0        
        //  1982: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1985: ldc_w           25.0
        //  1988: ldc_w           60.0
        //  1991: invokestatic    net/minecraft/util/MathHelper.randomFloatClamp:(Ljava/util/Random;FF)F
        //  1994: fstore          21
        //  1996: aload_0        
        //  1997: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  2000: fload           24
        //  2002: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  2005: fload           21
        //  2007: fmul           
        //  2008: ldc_w           0.1
        //  2011: fmul           
        //  2012: f2d            
        //  2013: dadd           
        //  2014: dstore          15
        //  2016: aload_0        
        //  2017: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  2020: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  2023: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  2026: i2f            
        //  2027: fconst_1       
        //  2028: fadd           
        //  2029: f2d            
        //  2030: dstore          25
        //  2032: aload_0        
        //  2033: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  2036: fload           24
        //  2038: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  2041: fload           21
        //  2043: fmul           
        //  2044: ldc_w           0.1
        //  2047: fmul           
        //  2048: f2d            
        //  2049: dadd           
        //  2050: dstore          22
        //  2052: aload           17
        //  2054: getstatic       net/minecraft/util/EnumParticleTypes.WATER_SPLASH:Lnet/minecraft/util/EnumParticleTypes;
        //  2057: dload           15
        //  2059: dload           25
        //  2061: dload           22
        //  2063: iconst_2       
        //  2064: aload_0        
        //  2065: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2068: iconst_2       
        //  2069: invokevirtual   java/util/Random.nextInt:(I)I
        //  2072: iadd           
        //  2073: ldc2_w          0.10000000149011612
        //  2076: dconst_0       
        //  2077: ldc2_w          0.10000000149011612
        //  2080: dconst_0       
        //  2081: iconst_0       
        //  2082: newarray        I
        //  2084: invokevirtual   net/minecraft/world/WorldServer.func_175739_a:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  2087: aload_0        
        //  2088: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  2091: ifgt            2164
        //  2094: aload_0        
        //  2095: aload_0        
        //  2096: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2099: fconst_0       
        //  2100: ldc_w           360.0
        //  2103: invokestatic    net/minecraft/util/MathHelper.randomFloatClamp:(Ljava/util/Random;FF)F
        //  2106: putfield        net/minecraft/entity/projectile/EntityFishHook.fishApproachAngle:F
        //  2109: aload_0        
        //  2110: aload_0        
        //  2111: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2114: bipush          20
        //  2116: bipush          80
        //  2118: invokestatic    net/minecraft/util/MathHelper.getRandomIntegerInRange:(Ljava/util/Random;II)I
        //  2121: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  2124: goto            2164
        //  2127: aload_0        
        //  2128: aload_0        
        //  2129: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2132: bipush          100
        //  2134: sipush          900
        //  2137: invokestatic    net/minecraft/util/MathHelper.getRandomIntegerInRange:(Ljava/util/Random;II)I
        //  2140: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  2143: aload_0        
        //  2144: dup            
        //  2145: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  2148: aload_0        
        //  2149: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //  2152: invokestatic    net/minecraft/enchantment/EnchantmentHelper.func_151387_h:(Lnet/minecraft/entity/EntityLivingBase;)I
        //  2155: bipush          20
        //  2157: imul           
        //  2158: iconst_5       
        //  2159: imul           
        //  2160: isub           
        //  2161: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  2164: aload_0        
        //  2165: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  2168: ifle            2208
        //  2171: aload_0        
        //  2172: dup            
        //  2173: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2176: aload_0        
        //  2177: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2180: invokevirtual   java/util/Random.nextFloat:()F
        //  2183: aload_0        
        //  2184: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2187: invokevirtual   java/util/Random.nextFloat:()F
        //  2190: fmul           
        //  2191: aload_0        
        //  2192: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2195: invokevirtual   java/util/Random.nextFloat:()F
        //  2198: fmul           
        //  2199: f2d            
        //  2200: ldc2_w          0.2
        //  2203: dmul           
        //  2204: dsub           
        //  2205: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2208: dload           13
        //  2210: ldc2_w          2.0
        //  2213: dmul           
        //  2214: dconst_1       
        //  2215: dsub           
        //  2216: dstore          8
        //  2218: aload_0        
        //  2219: dup            
        //  2220: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2223: ldc2_w          0.03999999910593033
        //  2226: dload           8
        //  2228: dmul           
        //  2229: dadd           
        //  2230: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2233: dload           13
        //  2235: dconst_0       
        //  2236: dcmpl          
        //  2237: ifle            2262
        //  2240: fload           11
        //  2242: f2d            
        //  2243: ldc2_w          0.9
        //  2246: dmul           
        //  2247: d2f            
        //  2248: fstore          11
        //  2250: aload_0        
        //  2251: dup            
        //  2252: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2255: ldc2_w          0.8
        //  2258: dmul           
        //  2259: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2262: aload_0        
        //  2263: dup            
        //  2264: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //  2267: fload           11
        //  2269: f2d            
        //  2270: dmul           
        //  2271: putfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //  2274: aload_0        
        //  2275: dup            
        //  2276: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2279: fload           11
        //  2281: f2d            
        //  2282: dmul           
        //  2283: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2286: aload_0        
        //  2287: dup            
        //  2288: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //  2291: fload           11
        //  2293: f2d            
        //  2294: dmul           
        //  2295: putfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //  2298: aload_0        
        //  2299: aload_0        
        //  2300: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  2303: aload_0        
        //  2304: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //  2307: aload_0        
        //  2308: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  2311: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setPosition:(DDD)V
        //  2314: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short)this.xTile);
        nbtTagCompound.setShort("yTile", (short)this.yTile);
        nbtTagCompound.setShort("zTile", (short)this.zTile);
        final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
        nbtTagCompound.setString("inTile", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setByte("shake", (byte)this.shake);
        nbtTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort("xTile");
        this.yTile = nbtTagCompound.getShort("yTile");
        this.zTile = nbtTagCompound.getShort("zTile");
        if (nbtTagCompound.hasKey("inTile", 8)) {
            this.inTile = Block.getBlockFromName(nbtTagCompound.getString("inTile"));
        }
        else {
            this.inTile = Block.getBlockById(nbtTagCompound.getByte("inTile") & 0xFF);
        }
        this.shake = (nbtTagCompound.getByte("shake") & 0xFF);
        this.inGround = (nbtTagCompound.getByte("inGround") == 1);
    }
    
    public int handleHookRetraction() {
        if (this.worldObj.isRemote) {
            return 0;
        }
        if (this.caughtEntity != null) {
            final double n = this.angler.posX - this.posX;
            final double n2 = this.angler.posY - this.posY;
            final double n3 = this.angler.posZ - this.posZ;
            final double n4 = MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
            final double n5 = 0.1;
            final Entity caughtEntity = this.caughtEntity;
            caughtEntity.motionX += n * n5;
            final Entity caughtEntity2 = this.caughtEntity;
            caughtEntity2.motionY += n2 * n5 + MathHelper.sqrt_double(n4) * 0.08;
            final Entity caughtEntity3 = this.caughtEntity;
            caughtEntity3.motionZ += n3 * n5;
        }
        else if (this.ticksCatchable > 0) {
            final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.func_146033_f());
            final double n6 = this.angler.posX - this.posX;
            final double n7 = this.angler.posY - this.posY;
            final double n8 = this.angler.posZ - this.posZ;
            final double n9 = MathHelper.sqrt_double(n6 * n6 + n7 * n7 + n8 * n8);
            final double n10 = 0.1;
            entityItem.motionX = n6 * n10;
            entityItem.motionY = n7 * n10 + MathHelper.sqrt_double(n9) * 0.08;
            entityItem.motionZ = n8 * n10;
            this.worldObj.spawnEntityInWorld(entityItem);
            this.angler.worldObj.spawnEntityInWorld(new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5, this.angler.posZ + 0.5, this.rand.nextInt(6) + 1));
        }
        if (this.inGround) {}
        this.setDead();
        this.angler.fishEntity = null;
        return 2;
    }
    
    private ItemStack func_146033_f() {
        final float nextFloat = this.worldObj.rand.nextFloat();
        final int func_151386_g = EnchantmentHelper.func_151386_g(this.angler);
        final int func_151387_h = EnchantmentHelper.func_151387_h(this.angler);
        final float n = 0.1f - func_151386_g * 0.025f - func_151387_h * 0.01f;
        final float n2 = 0.05f + func_151386_g * 0.01f - func_151387_h * 0.01f;
        final float clamp_float = MathHelper.clamp_float(n, 0.0f, 1.0f);
        final float clamp_float2 = MathHelper.clamp_float(n2, 0.0f, 1.0f);
        if (nextFloat < clamp_float) {
            this.angler.triggerAchievement(StatList.junkFishedStat);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.JUNK)).getItemStack(this.rand);
        }
        if (nextFloat - clamp_float < clamp_float2) {
            this.angler.triggerAchievement(StatList.treasureFishedStat);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.VALUABLES)).getItemStack(this.rand);
        }
        this.angler.triggerAchievement(StatList.fishCaughtStat);
        return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.FISH)).getItemStack(this.rand);
    }
    
    @Override
    public void setDead() {
        super.setDead();
        if (this.angler != null) {
            this.angler.fishEntity = null;
        }
    }
}
