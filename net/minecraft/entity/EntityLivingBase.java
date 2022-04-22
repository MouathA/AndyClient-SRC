package net.minecraft.entity;

import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import Mood.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.scoreboard.*;

public abstract class EntityLivingBase extends Entity
{
    private static final UUID sprintingSpeedBoostModifierUUID;
    private static final AttributeModifier sprintingSpeedBoostModifier;
    private BaseAttributeMap attributeMap;
    private final CombatTracker _combatTracker;
    private final Map activePotionsMap;
    private final ItemStack[] previousEquipment;
    public boolean isSwingInProgress;
    public int swingProgressInt;
    public int arrowHitTimer;
    public int hurtTime;
    public int maxHurtTime;
    public float attackedAtYaw;
    public int deathTime;
    public float prevSwingProgress;
    public float swingProgress;
    public float prevLimbSwingAmount;
    public float limbSwingAmount;
    public float limbSwing;
    public int maxHurtResistantTime;
    public float prevCameraPitch;
    public float cameraPitch;
    public float field_70769_ao;
    public float field_70770_ap;
    public float renderYawOffset;
    public float prevRenderYawOffset;
    public float rotationYawHead;
    public float prevRotationYawHead;
    public float jumpMovementFactor;
    protected EntityPlayer attackingPlayer;
    protected int recentlyHit;
    protected boolean dead;
    protected int entityAge;
    protected float field_70768_au;
    protected float field_110154_aX;
    protected float field_70764_aw;
    protected float field_70763_ax;
    protected float field_70741_aB;
    protected int scoreValue;
    protected float lastDamage;
    protected boolean isJumping;
    public float moveStrafing;
    public float moveForward;
    protected float randomYawVelocity;
    protected int newPosRotationIncrements;
    protected double newPosX;
    protected double newPosY;
    protected double newPosZ;
    protected double newRotationYaw;
    protected double newRotationPitch;
    private boolean potionsNeedUpdate;
    private EntityLivingBase entityLivingToAttack;
    private int revengeTimer;
    private EntityLivingBase lastAttacker;
    private int lastAttackerTime;
    private float landMovementFactor;
    private int jumpTicks;
    private float field_110151_bq;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001549";
        sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
        sprintingSpeedBoostModifier = new AttributeModifier(EntityLivingBase.sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.30000001192092896, 2).setSaved(false);
    }
    
    @Override
    public void func_174812_G() {
        this.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
    }
    
    public EntityLivingBase(final World world) {
        super(world);
        this._combatTracker = new CombatTracker(this);
        this.activePotionsMap = Maps.newHashMap();
        this.previousEquipment = new ItemStack[5];
        this.maxHurtResistantTime = 20;
        this.jumpMovementFactor = 0.02f;
        this.potionsNeedUpdate = true;
        this.applyEntityAttributes();
        this.setHealth(this.getMaxHealth());
        this.preventEntitySpawning = true;
        this.field_70770_ap = (float)((Math.random() + 1.0) * 0.009999999776482582);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_70769_ao = (float)Math.random() * 12398.0f;
        this.rotationYaw = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.rotationYawHead = this.rotationYaw;
        this.stepHeight = 0.6f;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(7, 0);
        this.dataWatcher.addObject(8, 0);
        this.dataWatcher.addObject(9, 0);
        this.dataWatcher.addObject(6, 1.0f);
    }
    
    protected void applyEntityAttributes() {
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
    }
    
    @Override
    protected void func_180433_a(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (!this.isInWater()) {
            this.handleWaterMovement();
        }
        if (!this.worldObj.isRemote && this.fallDistance > 3.0f && b) {
            final IBlockState blockState = this.worldObj.getBlockState(blockPos);
            final Block block2 = blockState.getBlock();
            final float n2 = (float)MathHelper.ceiling_float_int(this.fallDistance - 3.0f);
            if (block2.getMaterial() != Material.air) {
                double n3 = Math.min(0.2f + n2 / 15.0f, 10.0f);
                if (n3 > 2.5) {
                    n3 = 2.5;
                }
                ((WorldServer)this.worldObj).func_175739_a(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, (int)(150.0 * n3), 0.0, 0.0, 0.0, 0.15000000596046448, Block.getStateId(blockState));
            }
        }
        super.func_180433_a(n, b, block, blockPos);
    }
    
    public boolean canBreatheUnderwater() {
        return false;
    }
    
    @Override
    public void onEntityUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/entity/EntityLivingBase.swingProgress:F
        //     5: putfield        net/minecraft/entity/EntityLivingBase.prevSwingProgress:F
        //     8: aload_0        
        //     9: invokespecial   net/minecraft/entity/Entity.onEntityUpdate:()V
        //    12: aload_0        
        //    13: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    16: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //    19: ldc_w           "livingEntityBaseTick"
        //    22: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    25: aload_0        
        //    26: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //    29: istore_1       
        //    30: aload_0        
        //    31: invokevirtual   net/minecraft/entity/EntityLivingBase.isEntityAlive:()Z
        //    34: ifeq            135
        //    37: aload_0        
        //    38: invokevirtual   net/minecraft/entity/EntityLivingBase.isEntityInsideOpaqueBlock:()Z
        //    41: ifeq            56
        //    44: aload_0        
        //    45: getstatic       net/minecraft/util/DamageSource.inWall:Lnet/minecraft/util/DamageSource;
        //    48: fconst_1       
        //    49: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //    52: pop            
        //    53: goto            135
        //    56: iload_1        
        //    57: ifeq            135
        //    60: aload_0        
        //    61: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    64: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //    67: aload_0        
        //    68: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //    71: invokevirtual   net/minecraft/world/border/WorldBorder.contains:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //    74: ifne            135
        //    77: aload_0        
        //    78: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    81: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //    84: aload_0        
        //    85: invokevirtual   net/minecraft/world/border/WorldBorder.getClosestDistance:(Lnet/minecraft/entity/Entity;)D
        //    88: aload_0        
        //    89: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    92: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //    95: invokevirtual   net/minecraft/world/border/WorldBorder.getDamageBuffer:()D
        //    98: dadd           
        //    99: dstore_2       
        //   100: dload_2        
        //   101: dconst_0       
        //   102: dcmpg          
        //   103: ifge            135
        //   106: aload_0        
        //   107: getstatic       net/minecraft/util/DamageSource.inWall:Lnet/minecraft/util/DamageSource;
        //   110: iconst_1       
        //   111: dload_2        
        //   112: dneg           
        //   113: aload_0        
        //   114: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   117: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //   120: invokevirtual   net/minecraft/world/border/WorldBorder.func_177727_n:()D
        //   123: dmul           
        //   124: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   127: invokestatic    java/lang/Math.max:(II)I
        //   130: i2f            
        //   131: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //   134: pop            
        //   135: aload_0        
        //   136: invokevirtual   net/minecraft/entity/EntityLivingBase.isImmuneToFire:()Z
        //   139: ifne            152
        //   142: aload_0        
        //   143: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   146: getfield        net/minecraft/world/World.isRemote:Z
        //   149: ifeq            156
        //   152: aload_0        
        //   153: invokevirtual   net/minecraft/entity/EntityLivingBase.extinguish:()V
        //   156: iload_1        
        //   157: ifeq            177
        //   160: aload_0        
        //   161: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   164: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   167: getfield        net/minecraft/entity/player/PlayerCapabilities.disableDamage:Z
        //   170: ifeq            177
        //   173: iconst_1       
        //   174: goto            178
        //   177: iconst_0       
        //   178: istore_2       
        //   179: aload_0        
        //   180: invokevirtual   net/minecraft/entity/EntityLivingBase.isEntityAlive:()Z
        //   183: ifeq            402
        //   186: aload_0        
        //   187: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //   190: invokevirtual   net/minecraft/entity/EntityLivingBase.isInsideOfMaterial:(Lnet/minecraft/block/material/Material;)Z
        //   193: ifeq            402
        //   196: aload_0        
        //   197: invokevirtual   net/minecraft/entity/EntityLivingBase.canBreatheUnderwater:()Z
        //   200: ifne            367
        //   203: aload_0        
        //   204: getstatic       net/minecraft/potion/Potion.waterBreathing:Lnet/minecraft/potion/Potion;
        //   207: getfield        net/minecraft/potion/Potion.id:I
        //   210: invokevirtual   net/minecraft/entity/EntityLivingBase.isPotionActive:(I)Z
        //   213: ifne            367
        //   216: iload_2        
        //   217: ifne            367
        //   220: aload_0        
        //   221: aload_0        
        //   222: aload_0        
        //   223: invokevirtual   net/minecraft/entity/EntityLivingBase.getAir:()I
        //   226: invokevirtual   net/minecraft/entity/EntityLivingBase.decreaseAirSupply:(I)I
        //   229: invokevirtual   net/minecraft/entity/EntityLivingBase.setAir:(I)V
        //   232: aload_0        
        //   233: invokevirtual   net/minecraft/entity/EntityLivingBase.getAir:()I
        //   236: bipush          -20
        //   238: if_icmpne       367
        //   241: aload_0        
        //   242: iconst_0       
        //   243: invokevirtual   net/minecraft/entity/EntityLivingBase.setAir:(I)V
        //   246: goto            352
        //   249: aload_0        
        //   250: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   253: invokevirtual   java/util/Random.nextFloat:()F
        //   256: aload_0        
        //   257: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   260: invokevirtual   java/util/Random.nextFloat:()F
        //   263: fsub           
        //   264: fstore          4
        //   266: aload_0        
        //   267: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   270: invokevirtual   java/util/Random.nextFloat:()F
        //   273: aload_0        
        //   274: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   277: invokevirtual   java/util/Random.nextFloat:()F
        //   280: fsub           
        //   281: fstore          5
        //   283: aload_0        
        //   284: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   287: invokevirtual   java/util/Random.nextFloat:()F
        //   290: aload_0        
        //   291: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   294: invokevirtual   java/util/Random.nextFloat:()F
        //   297: fsub           
        //   298: fstore          6
        //   300: aload_0        
        //   301: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   304: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //   307: aload_0        
        //   308: getfield        net/minecraft/entity/EntityLivingBase.posX:D
        //   311: fload           4
        //   313: f2d            
        //   314: dadd           
        //   315: aload_0        
        //   316: getfield        net/minecraft/entity/EntityLivingBase.posY:D
        //   319: fload           5
        //   321: f2d            
        //   322: dadd           
        //   323: aload_0        
        //   324: getfield        net/minecraft/entity/EntityLivingBase.posZ:D
        //   327: fload           6
        //   329: f2d            
        //   330: dadd           
        //   331: aload_0        
        //   332: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   335: aload_0        
        //   336: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   339: aload_0        
        //   340: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   343: iconst_0       
        //   344: newarray        I
        //   346: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   349: iinc            3, 1
        //   352: iconst_0       
        //   353: bipush          8
        //   355: if_icmplt       249
        //   358: aload_0        
        //   359: getstatic       net/minecraft/util/DamageSource.drown:Lnet/minecraft/util/DamageSource;
        //   362: fconst_2       
        //   363: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //   366: pop            
        //   367: aload_0        
        //   368: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   371: getfield        net/minecraft/world/World.isRemote:Z
        //   374: ifne            409
        //   377: aload_0        
        //   378: invokevirtual   net/minecraft/entity/EntityLivingBase.isRiding:()Z
        //   381: ifeq            409
        //   384: aload_0        
        //   385: getfield        net/minecraft/entity/EntityLivingBase.ridingEntity:Lnet/minecraft/entity/Entity;
        //   388: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   391: ifeq            409
        //   394: aload_0        
        //   395: aconst_null    
        //   396: invokevirtual   net/minecraft/entity/EntityLivingBase.mountEntity:(Lnet/minecraft/entity/Entity;)V
        //   399: goto            409
        //   402: aload_0        
        //   403: sipush          300
        //   406: invokevirtual   net/minecraft/entity/EntityLivingBase.setAir:(I)V
        //   409: aload_0        
        //   410: invokevirtual   net/minecraft/entity/EntityLivingBase.isEntityAlive:()Z
        //   413: ifeq            427
        //   416: aload_0        
        //   417: invokevirtual   net/minecraft/entity/EntityLivingBase.isWet:()Z
        //   420: ifeq            427
        //   423: aload_0        
        //   424: invokevirtual   net/minecraft/entity/EntityLivingBase.extinguish:()V
        //   427: aload_0        
        //   428: aload_0        
        //   429: getfield        net/minecraft/entity/EntityLivingBase.cameraPitch:F
        //   432: putfield        net/minecraft/entity/EntityLivingBase.prevCameraPitch:F
        //   435: aload_0        
        //   436: getfield        net/minecraft/entity/EntityLivingBase.hurtTime:I
        //   439: ifle            452
        //   442: aload_0        
        //   443: dup            
        //   444: getfield        net/minecraft/entity/EntityLivingBase.hurtTime:I
        //   447: iconst_1       
        //   448: isub           
        //   449: putfield        net/minecraft/entity/EntityLivingBase.hurtTime:I
        //   452: aload_0        
        //   453: getfield        net/minecraft/entity/EntityLivingBase.hurtResistantTime:I
        //   456: ifle            476
        //   459: aload_0        
        //   460: instanceof      Lnet/minecraft/entity/player/EntityPlayerMP;
        //   463: ifne            476
        //   466: aload_0        
        //   467: dup            
        //   468: getfield        net/minecraft/entity/EntityLivingBase.hurtResistantTime:I
        //   471: iconst_1       
        //   472: isub           
        //   473: putfield        net/minecraft/entity/EntityLivingBase.hurtResistantTime:I
        //   476: aload_0        
        //   477: invokevirtual   net/minecraft/entity/EntityLivingBase.getHealth:()F
        //   480: fconst_0       
        //   481: fcmpg          
        //   482: ifgt            489
        //   485: aload_0        
        //   486: invokevirtual   net/minecraft/entity/EntityLivingBase.onDeathUpdate:()V
        //   489: aload_0        
        //   490: getfield        net/minecraft/entity/EntityLivingBase.recentlyHit:I
        //   493: ifle            509
        //   496: aload_0        
        //   497: dup            
        //   498: getfield        net/minecraft/entity/EntityLivingBase.recentlyHit:I
        //   501: iconst_1       
        //   502: isub           
        //   503: putfield        net/minecraft/entity/EntityLivingBase.recentlyHit:I
        //   506: goto            514
        //   509: aload_0        
        //   510: aconst_null    
        //   511: putfield        net/minecraft/entity/EntityLivingBase.attackingPlayer:Lnet/minecraft/entity/player/EntityPlayer;
        //   514: aload_0        
        //   515: getfield        net/minecraft/entity/EntityLivingBase.lastAttacker:Lnet/minecraft/entity/EntityLivingBase;
        //   518: ifnull          536
        //   521: aload_0        
        //   522: getfield        net/minecraft/entity/EntityLivingBase.lastAttacker:Lnet/minecraft/entity/EntityLivingBase;
        //   525: invokevirtual   net/minecraft/entity/EntityLivingBase.isEntityAlive:()Z
        //   528: ifne            536
        //   531: aload_0        
        //   532: aconst_null    
        //   533: putfield        net/minecraft/entity/EntityLivingBase.lastAttacker:Lnet/minecraft/entity/EntityLivingBase;
        //   536: aload_0        
        //   537: getfield        net/minecraft/entity/EntityLivingBase.entityLivingToAttack:Lnet/minecraft/entity/EntityLivingBase;
        //   540: ifnull          580
        //   543: aload_0        
        //   544: getfield        net/minecraft/entity/EntityLivingBase.entityLivingToAttack:Lnet/minecraft/entity/EntityLivingBase;
        //   547: invokevirtual   net/minecraft/entity/EntityLivingBase.isEntityAlive:()Z
        //   550: ifne            561
        //   553: aload_0        
        //   554: aconst_null    
        //   555: invokevirtual   net/minecraft/entity/EntityLivingBase.setRevengeTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
        //   558: goto            580
        //   561: aload_0        
        //   562: getfield        net/minecraft/entity/EntityLivingBase.ticksExisted:I
        //   565: aload_0        
        //   566: getfield        net/minecraft/entity/EntityLivingBase.revengeTimer:I
        //   569: isub           
        //   570: bipush          100
        //   572: if_icmple       580
        //   575: aload_0        
        //   576: aconst_null    
        //   577: invokevirtual   net/minecraft/entity/EntityLivingBase.setRevengeTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
        //   580: aload_0        
        //   581: invokevirtual   net/minecraft/entity/EntityLivingBase.updatePotionEffects:()V
        //   584: aload_0        
        //   585: aload_0        
        //   586: getfield        net/minecraft/entity/EntityLivingBase.field_70764_aw:F
        //   589: putfield        net/minecraft/entity/EntityLivingBase.field_70763_ax:F
        //   592: aload_0        
        //   593: aload_0        
        //   594: getfield        net/minecraft/entity/EntityLivingBase.renderYawOffset:F
        //   597: putfield        net/minecraft/entity/EntityLivingBase.prevRenderYawOffset:F
        //   600: aload_0        
        //   601: aload_0        
        //   602: getfield        net/minecraft/entity/EntityLivingBase.rotationYawHead:F
        //   605: putfield        net/minecraft/entity/EntityLivingBase.prevRotationYawHead:F
        //   608: aload_0        
        //   609: aload_0        
        //   610: getfield        net/minecraft/entity/EntityLivingBase.rotationYaw:F
        //   613: putfield        net/minecraft/entity/EntityLivingBase.prevRotationYaw:F
        //   616: aload_0        
        //   617: aload_0        
        //   618: getfield        net/minecraft/entity/EntityLivingBase.rotationPitch:F
        //   621: putfield        net/minecraft/entity/EntityLivingBase.prevRotationPitch:F
        //   624: aload_0        
        //   625: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   628: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   631: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   634: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean isChild() {
        return false;
    }
    
    protected void onDeathUpdate() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            int experiencePoints = 0;
            if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                experiencePoints = this.getExperiencePoints(this.attackingPlayer);
                while (0 > 0) {
                    final int xpSplit = EntityXPOrb.getXPSplit(0);
                    experiencePoints = 0 - xpSplit;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, xpSplit));
                }
            }
            this.setDead();
            while (0 < 20) {
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
                ++experiencePoints;
            }
        }
    }
    
    protected boolean func_146066_aG() {
        return !this.isChild();
    }
    
    protected int decreaseAirSupply(final int n) {
        final int func_180319_a = EnchantmentHelper.func_180319_a(this);
        return (func_180319_a > 0 && this.rand.nextInt(func_180319_a + 1) > 0) ? n : (n - 1);
    }
    
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        return 0;
    }
    
    protected boolean isPlayer() {
        return false;
    }
    
    public Random getRNG() {
        return this.rand;
    }
    
    public EntityLivingBase getAITarget() {
        return this.entityLivingToAttack;
    }
    
    public int getRevengeTimer() {
        return this.revengeTimer;
    }
    
    public void setRevengeTarget(final EntityLivingBase entityLivingToAttack) {
        this.entityLivingToAttack = entityLivingToAttack;
        this.revengeTimer = this.ticksExisted;
    }
    
    public EntityLivingBase getLastAttacker() {
        return this.lastAttacker;
    }
    
    public int getLastAttackerTime() {
        return this.lastAttackerTime;
    }
    
    public void setLastAttacker(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.lastAttacker = (EntityLivingBase)entity;
        }
        else {
            this.lastAttacker = null;
        }
        this.lastAttackerTime = this.ticksExisted;
    }
    
    public int getAge() {
        return this.entityAge;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setFloat("HealF", this.getHealth());
        nbtTagCompound.setShort("Health", (short)Math.ceil(this.getHealth()));
        nbtTagCompound.setShort("HurtTime", (short)this.hurtTime);
        nbtTagCompound.setInteger("HurtByTimestamp", this.revengeTimer);
        nbtTagCompound.setShort("DeathTime", (short)this.deathTime);
        nbtTagCompound.setFloat("AbsorptionAmount", this.getAbsorptionAmount());
        final ItemStack[] inventory = this.getInventory();
        int n = 0;
        while (0 < inventory.length) {
            final ItemStack itemStack = inventory[0];
            if (itemStack != null) {
                this.attributeMap.removeAttributeModifiers(itemStack.getAttributeModifiers());
            }
            ++n;
        }
        nbtTagCompound.setTag("Attributes", SharedMonsterAttributes.writeBaseAttributeMapToNBT(this.getAttributeMap()));
        final ItemStack[] inventory2 = this.getInventory();
        while (0 < inventory2.length) {
            final ItemStack itemStack2 = inventory2[0];
            if (itemStack2 != null) {
                this.attributeMap.applyAttributeModifiers(itemStack2.getAttributeModifiers());
            }
            ++n;
        }
        if (!this.activePotionsMap.isEmpty()) {
            final NBTTagList list = new NBTTagList();
            final Iterator<PotionEffect> iterator = this.activePotionsMap.values().iterator();
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }
            nbtTagCompound.setTag("ActiveEffects", list);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.setAbsorptionAmount(nbtTagCompound.getFloat("AbsorptionAmount"));
        if (nbtTagCompound.hasKey("Attributes", 9) && this.worldObj != null && !this.worldObj.isRemote) {
            SharedMonsterAttributes.func_151475_a(this.getAttributeMap(), nbtTagCompound.getTagList("Attributes", 10));
        }
        if (nbtTagCompound.hasKey("ActiveEffects", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("ActiveEffects", 10);
            while (0 < tagList.tagCount()) {
                final PotionEffect customPotionEffectFromNBT = PotionEffect.readCustomPotionEffectFromNBT(tagList.getCompoundTagAt(0));
                if (customPotionEffectFromNBT != null) {
                    this.activePotionsMap.put(customPotionEffectFromNBT.getPotionID(), customPotionEffectFromNBT);
                }
                int n = 0;
                ++n;
            }
        }
        if (nbtTagCompound.hasKey("HealF", 99)) {
            this.setHealth(nbtTagCompound.getFloat("HealF"));
        }
        else {
            final NBTBase tag = nbtTagCompound.getTag("Health");
            if (tag == null) {
                this.setHealth(this.getMaxHealth());
            }
            else if (tag.getId() == 5) {
                this.setHealth(((NBTTagFloat)tag).getFloat());
            }
            else if (tag.getId() == 2) {
                this.setHealth(((NBTTagShort)tag).getShort());
            }
        }
        this.hurtTime = nbtTagCompound.getShort("HurtTime");
        this.deathTime = nbtTagCompound.getShort("DeathTime");
        this.revengeTimer = nbtTagCompound.getInteger("HurtByTimestamp");
    }
    
    protected void updatePotionEffects() {
        final Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
        while (iterator.hasNext()) {
            final PotionEffect potionEffect = this.activePotionsMap.get(iterator.next());
            if (!potionEffect.onUpdate(this)) {
                if (this.worldObj.isRemote) {
                    continue;
                }
                iterator.remove();
                this.onFinishedPotionEffect(potionEffect);
            }
            else {
                if (potionEffect.getDuration() % 600 != 0) {
                    continue;
                }
                this.onChangedPotionEffect(potionEffect, false);
            }
        }
        if (this.potionsNeedUpdate) {
            if (!this.worldObj.isRemote) {
                this.func_175135_B();
            }
            this.potionsNeedUpdate = false;
        }
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(7);
        final boolean b = this.dataWatcher.getWatchableObjectByte(8) > 0;
        if (watchableObjectInt > 0) {
            if (!this.isInvisible()) {
                this.rand.nextBoolean();
            }
            else {
                final boolean b2 = this.rand.nextInt(15) == 0;
            }
            if (b) {
                final boolean b3 = false & this.rand.nextInt(5) == 0;
            }
            if (false && watchableObjectInt > 0) {
                this.worldObj.spawnParticle(b ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, (watchableObjectInt >> 16 & 0xFF) / 255.0, (watchableObjectInt >> 8 & 0xFF) / 255.0, (watchableObjectInt >> 0 & 0xFF) / 255.0, new int[0]);
            }
        }
    }
    
    protected void func_175135_B() {
        if (this.activePotionsMap.isEmpty()) {
            this.func_175133_bi();
            this.setInvisible(false);
        }
        else {
            final int calcPotionLiquidColor = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
            this.dataWatcher.updateObject(8, (byte)(byte)(PotionHelper.func_82817_b(this.activePotionsMap.values()) ? 1 : 0));
            this.dataWatcher.updateObject(7, calcPotionLiquidColor);
            this.setInvisible(this.isPotionActive(Potion.invisibility.id));
        }
    }
    
    protected void func_175133_bi() {
        this.dataWatcher.updateObject(8, 0);
        this.dataWatcher.updateObject(7, 0);
    }
    
    public void clearActivePotions() {
        final Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
        while (iterator.hasNext()) {
            final PotionEffect potionEffect = this.activePotionsMap.get(iterator.next());
            if (!this.worldObj.isRemote) {
                iterator.remove();
                this.onFinishedPotionEffect(potionEffect);
            }
        }
    }
    
    public Collection getActivePotionEffects() {
        return this.activePotionsMap.values();
    }
    
    public boolean isPotionActive(final int n) {
        return this.activePotionsMap.containsKey(n);
    }
    
    public boolean isPotionActive(final Potion potion) {
        return this.activePotionsMap.containsKey(potion.id);
    }
    
    public PotionEffect getActivePotionEffect(final Potion potion) {
        return this.activePotionsMap.get(potion.id);
    }
    
    public void addPotionEffect(final PotionEffect potionEffect) {
        if (this.isPotionApplicable(potionEffect)) {
            if (this.activePotionsMap.containsKey(potionEffect.getPotionID())) {
                this.activePotionsMap.get(potionEffect.getPotionID()).combine(potionEffect);
                this.onChangedPotionEffect(this.activePotionsMap.get(potionEffect.getPotionID()), true);
            }
            else {
                this.activePotionsMap.put(potionEffect.getPotionID(), potionEffect);
                this.onNewPotionEffect(potionEffect);
            }
        }
    }
    
    public boolean isPotionApplicable(final PotionEffect potionEffect) {
        if (this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            final int potionID = potionEffect.getPotionID();
            if (potionID == Potion.regeneration.id || potionID == Potion.poison.id) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isEntityUndead() {
        return this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
    }
    
    public void removePotionEffectClient(final int n) {
        this.activePotionsMap.remove(n);
    }
    
    public void removePotionEffect(final int n) {
        final PotionEffect potionEffect = this.activePotionsMap.remove(n);
        if (potionEffect != null) {
            this.onFinishedPotionEffect(potionEffect);
        }
    }
    
    protected void onNewPotionEffect(final PotionEffect potionEffect) {
        this.potionsNeedUpdate = true;
        if (!this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    protected void onChangedPotionEffect(final PotionEffect potionEffect, final boolean b) {
        this.potionsNeedUpdate = true;
        if (b && !this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
            Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    protected void onFinishedPotionEffect(final PotionEffect potionEffect) {
        this.potionsNeedUpdate = true;
        if (!this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    public void heal(final float n) {
        final float health = this.getHealth();
        if (health > 0.0f) {
            this.setHealth(health + n);
        }
    }
    
    public final float getHealth() {
        return this.dataWatcher.getWatchableObjectFloat(6);
    }
    
    public void setHealth(final float n) {
        this.dataWatcher.updateObject(6, MathHelper.clamp_float(n, 0.0f, this.getMaxHealth()));
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (this.worldObj.isRemote) {
            return false;
        }
        this.entityAge = 0;
        if (this.getHealth() <= 0.0f) {
            return false;
        }
        if (damageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
            return false;
        }
        if ((damageSource == DamageSource.anvil || damageSource == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null) {
            this.getEquipmentInSlot(4).damageItem((int)(n * 4.0f + this.rand.nextFloat() * n * 2.0f), this);
            n *= 0.75f;
        }
        this.limbSwingAmount = 1.5f;
        if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0f) {
            if (n <= this.lastDamage) {
                return false;
            }
            this.damageEntity(damageSource, n - this.lastDamage);
            this.lastDamage = n;
        }
        else {
            this.lastDamage = n;
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(damageSource, n);
            final int n2 = 10;
            this.maxHurtTime = n2;
            this.hurtTime = n2;
        }
        this.attackedAtYaw = 0.0f;
        final Entity entity = damageSource.getEntity();
        if (entity != null) {
            if (entity instanceof EntityLivingBase) {
                this.setRevengeTarget((EntityLivingBase)entity);
            }
            if (entity instanceof EntityPlayer) {
                this.recentlyHit = 100;
                this.attackingPlayer = (EntityPlayer)entity;
            }
            else if (entity instanceof EntityWolf && ((EntityWolf)entity).isTamed()) {
                this.recentlyHit = 100;
                this.attackingPlayer = null;
            }
        }
        if (false) {
            this.worldObj.setEntityState(this, (byte)2);
            if (damageSource != DamageSource.drown) {
                this.setBeenAttacked();
            }
            if (entity != null) {
                double n3;
                double n4;
                for (n3 = entity.posX - this.posX, n4 = entity.posZ - this.posZ; n3 * n3 + n4 * n4 < 1.0E-4; n3 = (Math.random() - Math.random()) * 0.01, n4 = (Math.random() - Math.random()) * 0.01) {}
                this.attackedAtYaw = (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793 - this.rotationYaw);
                this.knockBack(entity, n, n3, n4);
            }
            else {
                this.attackedAtYaw = (float)((int)(Math.random() * 2.0) * 180);
            }
        }
        if (this.getHealth() <= 0.0f) {
            final String deathSound = this.getDeathSound();
            if (false && deathSound != null) {
                this.playSound(deathSound, this.getSoundVolume(), this.getSoundPitch());
            }
            this.onDeath(damageSource);
        }
        else {
            final String hurtSound = this.getHurtSound();
            if (false && hurtSound != null) {
                this.playSound(hurtSound, this.getSoundVolume(), this.getSoundPitch());
            }
        }
        return true;
    }
    
    public void renderBrokenItemStack(final ItemStack itemStack) {
        this.playSound("random.break", 0.8f, 0.8f + this.worldObj.rand.nextFloat() * 0.4f);
        while (0 < 5) {
            final Vec3 rotateYaw = new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f);
            final Vec3 addVector = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f).addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
            this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, addVector.xCoord, addVector.yCoord, addVector.zCoord, rotateYaw.xCoord, rotateYaw.yCoord + 0.05, rotateYaw.zCoord, Item.getIdFromItem(itemStack.getItem()));
            int n = 0;
            ++n;
        }
    }
    
    public void onDeath(final DamageSource damageSource) {
        final Entity entity = damageSource.getEntity();
        final EntityLivingBase func_94060_bK = this.func_94060_bK();
        if (this.scoreValue >= 0 && func_94060_bK != null) {
            func_94060_bK.addToPlayerScore(this, this.scoreValue);
        }
        if (entity != null) {
            entity.onKillEntity(this);
        }
        this.dead = true;
        this.getCombatTracker().func_94549_h();
        if (!this.worldObj.isRemote) {
            if (entity instanceof EntityPlayer) {
                EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
            }
            if (this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                this.dropFewItems(this.recentlyHit > 0, 0);
                this.dropEquipment(this.recentlyHit > 0, 0);
                if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025f + 0 * 0.01f) {
                    this.addRandomArmor();
                }
            }
        }
        this.worldObj.setEntityState(this, (byte)3);
    }
    
    protected void dropEquipment(final boolean b, final int n) {
    }
    
    public void knockBack(final Entity entity, final float n, final double n2, final double n3) {
        if (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
            this.isAirBorne = true;
            final float sqrt_double = MathHelper.sqrt_double(n2 * n2 + n3 * n3);
            final float n4 = 0.4f;
            this.motionX /= 2.0;
            this.motionY /= 2.0;
            this.motionZ /= 2.0;
            this.motionX -= n2 / sqrt_double * n4;
            this.motionY += n4;
            this.motionZ -= n3 / sqrt_double * n4;
            if (this.motionY > 0.4000000059604645) {
                this.motionY = 0.4000000059604645;
            }
        }
    }
    
    protected String getHurtSound() {
        return "game.neutral.hurt";
    }
    
    protected String getDeathSound() {
        return "game.neutral.die";
    }
    
    protected void addRandomArmor() {
    }
    
    protected void dropFewItems(final boolean b, final int n) {
    }
    
    public boolean isOnLadder() {
        final Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY), MathHelper.floor_double(this.posZ))).getBlock();
        return (block == Blocks.ladder || block == Blocks.vine) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).func_175149_v());
    }
    
    @Override
    public boolean isEntityAlive() {
        return !this.isDead && this.getHealth() > 0.0f;
    }
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        final PotionEffect activePotionEffect = this.getActivePotionEffect(Potion.jump);
        final int ceiling_float_int = MathHelper.ceiling_float_int((n - 3.0f - ((activePotionEffect != null) ? ((float)(activePotionEffect.getAmplifier() + 1)) : 0.0f)) * n2);
        if (ceiling_float_int > 0) {
            this.playSound(this.func_146067_o(ceiling_float_int), 1.0f, 1.0f);
            this.attackEntityFrom(DamageSource.fall, (float)ceiling_float_int);
            final Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ))).getBlock();
            if (block.getMaterial() != Material.air) {
                final Block.SoundType stepSound = block.stepSound;
                this.playSound(stepSound.getStepSound(), stepSound.getVolume() * 0.5f, stepSound.getFrequency() * 0.75f);
            }
        }
    }
    
    protected String func_146067_o(final int n) {
        return (n > 4) ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
    }
    
    @Override
    public void performHurtAnimation() {
        final int n = 10;
        this.maxHurtTime = n;
        this.hurtTime = n;
        this.attackedAtYaw = 0.0f;
    }
    
    public int getTotalArmorValue() {
        final ItemStack[] inventory = this.getInventory();
        while (0 < inventory.length) {
            final ItemStack itemStack = inventory[0];
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                final int n = 0 + ((ItemArmor)itemStack.getItem()).damageReduceAmount;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    protected void damageArmor(final float n) {
    }
    
    protected float applyArmorCalculations(final DamageSource damageSource, float n) {
        if (!damageSource.isUnblockable()) {
            final float n2 = n * (25 - this.getTotalArmorValue());
            this.damageArmor(n);
            n = n2 / 25.0f;
        }
        return n;
    }
    
    protected float applyPotionDamageCalculations(final DamageSource damageSource, float n) {
        if (damageSource.isDamageAbsolute()) {
            return n;
        }
        if (this.isPotionActive(Potion.resistance) && damageSource != DamageSource.outOfWorld) {
            final int n2 = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
            n = n * 5 / 25.0f;
        }
        if (n <= 0.0f) {
            return 0.0f;
        }
        EnchantmentHelper.getEnchantmentModifierDamage(this.getInventory(), damageSource);
        if (20 > 20) {}
        if (20 > 0 && 20 <= 20) {
            n = n * 5 / 25.0f;
        }
        return n;
    }
    
    protected void damageEntity(final DamageSource damageSource, float n) {
        if (!this.func_180431_b(damageSource)) {
            n = this.applyArmorCalculations(damageSource, n);
            final float applyPotionDamageCalculations;
            n = (applyPotionDamageCalculations = this.applyPotionDamageCalculations(damageSource, n));
            n = Math.max(n - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (applyPotionDamageCalculations - n));
            if (n != 0.0f) {
                final float health = this.getHealth();
                this.setHealth(health - n);
                this.getCombatTracker().func_94547_a(damageSource, health, n);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - n);
            }
        }
    }
    
    public CombatTracker getCombatTracker() {
        return this._combatTracker;
    }
    
    public EntityLivingBase func_94060_bK() {
        return (this._combatTracker.func_94550_c() != null) ? this._combatTracker.func_94550_c() : ((this.attackingPlayer != null) ? this.attackingPlayer : ((this.entityLivingToAttack != null) ? this.entityLivingToAttack : null));
    }
    
    public final float getMaxHealth() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
    }
    
    public final int getArrowCountInEntity() {
        return this.dataWatcher.getWatchableObjectByte(9);
    }
    
    public final void setArrowCountInEntity(final int n) {
        this.dataWatcher.updateObject(9, (byte)n);
    }
    
    private int getArmSwingAnimationEnd() {
        return this.isPotionActive(Potion.digSpeed) ? (6 - (1 + this.getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1) : (this.isPotionActive(Potion.digSlowdown) ? (6 + (1 + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
    }
    
    public void swingItem() {
        if (!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
            this.swingProgressInt = -1;
            this.isSwingInProgress = true;
            if (this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S0BPacketAnimation(this, 0));
            }
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 2) {
            this.limbSwingAmount = 1.5f;
            this.hurtResistantTime = this.maxHurtResistantTime;
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
            this.attackedAtYaw = 0.0f;
            if (this.getHurtSound() != null) {
                this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.attackEntityFrom(DamageSource.generic, 0.0f);
        }
        else if (b == 3) {
            if (this.getDeathSound() != null) {
                this.playSound(this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.setHealth(0.0f);
            this.onDeath(DamageSource.generic);
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    protected void kill() {
        this.attackEntityFrom(DamageSource.outOfWorld, 4.0f);
    }
    
    protected void updateArmSwingProgress() {
        final int armSwingAnimationEnd = this.getArmSwingAnimationEnd();
        if (this.isSwingInProgress) {
            ++this.swingProgressInt;
            if (this.swingProgressInt >= armSwingAnimationEnd) {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        }
        else {
            this.swingProgressInt = 0;
        }
        this.swingProgress = this.swingProgressInt / (float)armSwingAnimationEnd;
    }
    
    public IAttributeInstance getEntityAttribute(final IAttribute attribute) {
        return this.getAttributeMap().getAttributeInstance(attribute);
    }
    
    public BaseAttributeMap getAttributeMap() {
        if (this.attributeMap == null) {
            this.attributeMap = new ServersideAttributeMap();
        }
        return this.attributeMap;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public abstract ItemStack getHeldItem();
    
    public abstract ItemStack getEquipmentInSlot(final int p0);
    
    public abstract ItemStack getCurrentArmor(final int p0);
    
    @Override
    public abstract void setCurrentItemOrArmor(final int p0, final ItemStack p1);
    
    @Override
    public void setSprinting(final boolean sprinting) {
        super.setSprinting(sprinting);
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (entityAttribute.getModifier(EntityLivingBase.sprintingSpeedBoostModifierUUID) != null) {
            entityAttribute.removeModifier(EntityLivingBase.sprintingSpeedBoostModifier);
        }
        if (sprinting) {
            entityAttribute.applyModifier(EntityLivingBase.sprintingSpeedBoostModifier);
        }
    }
    
    @Override
    public abstract ItemStack[] getInventory();
    
    protected float getSoundVolume() {
        return 1.0f;
    }
    
    protected float getSoundPitch() {
        return this.isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.5f) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
    }
    
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0f;
    }
    
    public void dismountEntity(final Entity entity) {
        double posX = entity.posX;
        double n = entity.getEntityBoundingBox().minY + entity.height;
        double posZ = entity.posZ;
        while (-1 <= 1) {
            while (-1 < 1) {
                if (-1 != 0 || -1 != 0) {
                    final int n2 = (int)(this.posX - 1);
                    final int n3 = (int)(this.posZ - 1);
                    if (this.worldObj.func_147461_a(this.getEntityBoundingBox().offset(-1, 1.0, -1)).isEmpty()) {
                        if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, (int)this.posY, n3))) {
                            this.setPositionAndUpdate(this.posX - 1, this.posY + 1.0, this.posZ - 1);
                            return;
                        }
                        if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, (int)this.posY - 1, n3)) || this.worldObj.getBlockState(new BlockPos(n2, (int)this.posY - 1, n3)).getBlock().getMaterial() == Material.water) {
                            posX = this.posX - 1;
                            n = this.posY + 1.0;
                            posZ = this.posZ - 1;
                        }
                    }
                }
                int n4 = 0;
                ++n4;
            }
            int n5 = 0;
            ++n5;
        }
        this.setPositionAndUpdate(posX, n, posZ);
    }
    
    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return false;
    }
    
    protected float func_175134_bD() {
        return 0.42f;
    }
    
    protected void jump() {
        this.motionY = this.func_175134_bD();
        if (this instanceof EntityPlayer) {
            final double motionY = this.motionY;
            final Client instance = Client.INSTANCE;
            this.motionY = motionY + Client.getModuleByName("Highjump").getAdditionalJumpMotion();
        }
        if (this.isPotionActive(Potion.jump)) {
            this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        if (this.isSprinting()) {
            final float n = this.rotationYaw * 0.017453292f;
            this.motionX -= MathHelper.sin(n) * 0.2f;
            this.motionZ += MathHelper.cos(n) * 0.2f;
        }
        this.isAirBorne = true;
    }
    
    protected void updateAITick() {
        this.motionY += 0.03999999910593033;
    }
    
    protected void func_180466_bG() {
        this.motionY += 0.03999999910593033;
    }
    
    public void moveEntityWithHeading(final float n, final float n2) {
        if (this.isServerWorld()) {
            if (this.isInWater() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).capabilities.isFlying)) {
                final double posY = this.posY;
                float n3 = 0.8f;
                float n4 = 0.02f;
                float n5 = (float)EnchantmentHelper.func_180318_b(this);
                if (n5 > 3.0f) {
                    n5 = 3.0f;
                }
                if (!this.onGround) {
                    n5 *= 0.5f;
                }
                if (n5 > 0.0f) {
                    n3 += (0.54600006f - n3) * n5 / 3.0f;
                    n4 += (this.getAIMoveSpeed() * 1.0f - n4) * n5 / 3.0f;
                }
                this.moveFlying(n, n2, n4);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= n3;
                this.motionY *= 0.800000011920929;
                this.motionZ *= n3;
                this.motionY -= 0.02;
                if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + posY, this.motionZ)) {
                    this.motionY = 0.30000001192092896;
                }
            }
            else if (this.func_180799_ab() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).capabilities.isFlying)) {
                final double posY2 = this.posY;
                this.moveFlying(n, n2, 0.02f);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
                this.motionY -= 0.02;
                if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + posY2, this.motionZ)) {
                    this.motionY = 0.30000001192092896;
                }
            }
            else {
                float n6 = 0.91f;
                if (this.onGround) {
                    n6 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91f;
                }
                final float n7 = 0.16277136f / (n6 * n6 * n6);
                float jumpMovementFactor;
                if (this.onGround) {
                    jumpMovementFactor = this.getAIMoveSpeed() * n7;
                }
                else {
                    jumpMovementFactor = this.jumpMovementFactor;
                }
                this.moveFlying(n, n2, jumpMovementFactor);
                float n8 = 0.91f;
                if (this.onGround) {
                    n8 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91f;
                }
                if (this.isOnLadder()) {
                    final float n9 = 0.15f;
                    this.motionX = MathHelper.clamp_double(this.motionX, -n9, n9);
                    this.motionZ = MathHelper.clamp_double(this.motionZ, -n9, n9);
                    this.fallDistance = 0.0f;
                    if (this.motionY < -0.15) {
                        this.motionY = -0.15;
                    }
                    if (this.isSneaking() && this instanceof EntityPlayer && this.motionY < 0.0) {
                        this.motionY = 0.0;
                    }
                }
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                if (this.isCollidedHorizontally && this.isOnLadder()) {
                    this.motionY = 0.2;
                }
                if (this.worldObj.isRemote && (!this.worldObj.isBlockLoaded(new BlockPos((int)this.posX, 0, (int)this.posZ)) || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, 0, (int)this.posZ)).isLoaded())) {
                    if (this.posY > 0.0) {
                        this.motionY = -0.1;
                    }
                    else {
                        this.motionY = 0.0;
                    }
                }
                else {
                    this.motionY -= 0.08;
                }
                this.motionY *= 0.9800000190734863;
                this.motionX *= n8;
                this.motionZ *= n8;
            }
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        final double n10 = this.posX - this.prevPosX;
        final double n11 = this.posZ - this.prevPosZ;
        float n12 = MathHelper.sqrt_double(n10 * n10 + n11 * n11) * 4.0f;
        if (n12 > 1.0f) {
            n12 = 1.0f;
        }
        this.limbSwingAmount += (n12 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
    }
    
    public float getAIMoveSpeed() {
        return this.landMovementFactor;
    }
    
    public void setAIMoveSpeed(final float landMovementFactor) {
        this.landMovementFactor = landMovementFactor;
    }
    
    public boolean attackEntityAsMob(final Entity lastAttacker) {
        this.setLastAttacker(lastAttacker);
        return false;
    }
    
    public boolean isPlayerSleeping() {
        return false;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            final int arrowCountInEntity = this.getArrowCountInEntity();
            if (arrowCountInEntity > 0) {
                if (this.arrowHitTimer <= 0) {
                    this.arrowHitTimer = 20 * (30 - arrowCountInEntity);
                }
                --this.arrowHitTimer;
                if (this.arrowHitTimer <= 0) {
                    this.setArrowCountInEntity(arrowCountInEntity - 1);
                }
            }
            while (0 < 5) {
                final ItemStack itemStack = this.previousEquipment[0];
                final ItemStack equipmentInSlot = this.getEquipmentInSlot(0);
                if (!ItemStack.areItemStacksEqual(equipmentInSlot, itemStack)) {
                    ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S04PacketEntityEquipment(this.getEntityId(), 0, equipmentInSlot));
                    if (itemStack != null) {
                        this.attributeMap.removeAttributeModifiers(itemStack.getAttributeModifiers());
                    }
                    if (equipmentInSlot != null) {
                        this.attributeMap.applyAttributeModifiers(equipmentInSlot.getAttributeModifiers());
                    }
                    this.previousEquipment[0] = ((equipmentInSlot == null) ? null : equipmentInSlot.copy());
                }
                int n = 0;
                ++n;
            }
            if (this.ticksExisted % 20 == 0) {
                this.getCombatTracker().func_94549_h();
            }
        }
        this.onLivingUpdate();
        final double n2 = this.posX - this.prevPosX;
        final double n3 = this.posZ - this.prevPosZ;
        final float n4 = (float)(n2 * n2 + n3 * n3);
        float n5 = this.renderYawOffset;
        float n6 = 0.0f;
        this.field_70768_au = this.field_110154_aX;
        float n7 = 0.0f;
        if (n4 > 0.0025000002f) {
            n7 = 1.0f;
            n6 = (float)Math.sqrt(n4) * 3.0f;
            n5 = (float)Math.atan2(n3, n2) * 180.0f / 3.1415927f - 90.0f;
        }
        if (this.swingProgress > 0.0f) {
            n5 = this.rotationYaw;
        }
        if (!this.onGround) {
            n7 = 0.0f;
        }
        this.field_110154_aX += (n7 - this.field_110154_aX) * 0.3f;
        this.worldObj.theProfiler.startSection("headTurn");
        final float func_110146_f = this.func_110146_f(n5, n6);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("rangeChecks");
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
            this.prevRenderYawOffset -= 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
            this.prevRenderYawOffset += 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYawHead - this.prevRotationYawHead < -180.0f) {
            this.prevRotationYawHead -= 360.0f;
        }
        while (this.rotationYawHead - this.prevRotationYawHead >= 180.0f) {
            this.prevRotationYawHead += 360.0f;
        }
        this.worldObj.theProfiler.endSection();
        this.field_70764_aw += func_110146_f;
    }
    
    protected float func_110146_f(final float n, float n2) {
        this.renderYawOffset += MathHelper.wrapAngleTo180_float(n - this.renderYawOffset) * 0.3f;
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
        final boolean b = wrapAngleTo180_float < -90.0f || wrapAngleTo180_float >= 90.0f;
        if (wrapAngleTo180_float < -75.0f) {
            wrapAngleTo180_float = -75.0f;
        }
        if (wrapAngleTo180_float >= 75.0f) {
            wrapAngleTo180_float = 75.0f;
        }
        this.renderYawOffset = this.rotationYaw - wrapAngleTo180_float;
        if (wrapAngleTo180_float * wrapAngleTo180_float > 2500.0f) {
            this.renderYawOffset += wrapAngleTo180_float * 0.2f;
        }
        if (b) {
            n2 *= -1.0f;
        }
        return n2;
    }
    
    public void onLivingUpdate() {
        if (this.jumpTicks > 0) {
            --this.jumpTicks;
        }
        if (this.newPosRotationIncrements > 0) {
            final double n = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
            final double n2 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
            final double n3 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
            this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw) / this.newPosRotationIncrements);
            this.rotationPitch += (float)((this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(n, n2, n3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else if (!this.isServerWorld()) {
            this.motionX *= 0.98;
            this.motionY *= 0.98;
            this.motionZ *= 0.98;
        }
        if (Math.abs(this.motionX) < 0.005) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) < 0.005) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) < 0.005) {
            this.motionZ = 0.0;
        }
        this.worldObj.theProfiler.startSection("ai");
        if (this.isMovementBlocked()) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
            this.randomYawVelocity = 0.0f;
        }
        else if (this.isServerWorld()) {
            this.worldObj.theProfiler.startSection("newAi");
            this.updateEntityActionState();
            this.worldObj.theProfiler.endSection();
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");
        if (this.isJumping) {
            if (this.isInWater()) {
                this.updateAITick();
            }
            else if (this.func_180799_ab()) {
                this.func_180466_bG();
            }
            else if (this.onGround && this.jumpTicks == 0) {
                this.jump();
                this.jumpTicks = 10;
            }
        }
        else {
            this.jumpTicks = 0;
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.randomYawVelocity *= 0.9f;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("push");
        if (!this.worldObj.isRemote) {
            this.collideWithNearbyEntities();
        }
        this.worldObj.theProfiler.endSection();
    }
    
    protected void updateEntityActionState() {
    }
    
    protected void collideWithNearbyEntities() {
        final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224, 0.0, 0.20000000298023224));
        if (entitiesWithinAABBExcludingEntity != null && !entitiesWithinAABBExcludingEntity.isEmpty()) {
            while (0 < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity = entitiesWithinAABBExcludingEntity.get(0);
                if (entity.canBePushed()) {
                    this.collideWithEntity(entity);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    protected void collideWithEntity(final Entity entity) {
        entity.applyEntityCollision(this);
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        if (this.ridingEntity != null && entity == null) {
            if (!this.worldObj.isRemote) {
                this.dismountEntity(this.ridingEntity);
            }
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        }
        else {
            super.mountEntity(entity);
        }
    }
    
    @Override
    public void updateRidden() {
        super.updateRidden();
        this.field_70768_au = this.field_110154_aX;
        this.field_110154_aX = 0.0f;
        this.fallDistance = 0.0f;
    }
    
    @Override
    public void func_180426_a(final double newPosX, final double newPosY, final double newPosZ, final float n, final float n2, final int newPosRotationIncrements, final boolean b) {
        this.newPosX = newPosX;
        this.newPosY = newPosY;
        this.newPosZ = newPosZ;
        this.newRotationYaw = n;
        this.newRotationPitch = n2;
        this.newPosRotationIncrements = newPosRotationIncrements;
    }
    
    public void setJumping(final boolean isJumping) {
        this.isJumping = isJumping;
    }
    
    public void onItemPickup(final Entity entity, final int n) {
        if (!entity.isDead && !this.worldObj.isRemote) {
            final EntityTracker entityTracker = ((WorldServer)this.worldObj).getEntityTracker();
            if (entity instanceof EntityItem) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
            if (entity instanceof EntityArrow) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
            if (entity instanceof EntityXPOrb) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
        }
    }
    
    public boolean canEntityBeSeen(final Entity entity) {
        return this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ), new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ)) == null;
    }
    
    @Override
    public Vec3 getLookVec() {
        return this.getLook(1.0f);
    }
    
    @Override
    public Vec3 getLook(final float n) {
        if (n == 1.0f) {
            return this.func_174806_f(this.rotationPitch, this.rotationYawHead);
        }
        return this.func_174806_f(this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * n, this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * n);
    }
    
    public float getSwingProgress(final float n) {
        float n2 = this.swingProgress - this.prevSwingProgress;
        if (n2 < 0.0f) {
            ++n2;
        }
        return this.prevSwingProgress + n2 * n;
    }
    
    public boolean isServerWorld() {
        return !this.worldObj.isRemote;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }
    
    @Override
    protected void setBeenAttacked() {
        this.velocityChanged = (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
    }
    
    @Override
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }
    
    @Override
    public void setRotationYawHead(final float rotationYawHead) {
        this.rotationYawHead = rotationYawHead;
    }
    
    public float getAbsorptionAmount() {
        return this.field_110151_bq;
    }
    
    public void setAbsorptionAmount(float field_110151_bq) {
        if (field_110151_bq < 0.0f) {
            field_110151_bq = 0.0f;
        }
        this.field_110151_bq = field_110151_bq;
    }
    
    public Team getTeam() {
        return this.worldObj.getScoreboard().getPlayersTeam(this.getUniqueID().toString());
    }
    
    public boolean isOnSameTeam(final EntityLivingBase entityLivingBase) {
        return this.isOnTeam(entityLivingBase.getTeam());
    }
    
    public boolean isOnTeam(final Team team) {
        return this.getTeam() != null && this.getTeam().isSameTeam(team);
    }
    
    public void func_152111_bt() {
    }
    
    public void func_152112_bu() {
    }
    
    protected void func_175136_bO() {
        this.potionsNeedUpdate = true;
    }
}
