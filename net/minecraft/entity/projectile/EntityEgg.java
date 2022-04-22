package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityEgg extends EntityThrowable
{
    private static final String __OBFID;
    
    public EntityEgg(final World world) {
        super(world);
    }
    
    public EntityEgg(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
    
    public EntityEgg(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/util/MovingObjectPosition.entityHit:Lnet/minecraft/entity/Entity;
        //     4: ifnull          24
        //     7: aload_1        
        //     8: getfield        net/minecraft/util/MovingObjectPosition.entityHit:Lnet/minecraft/entity/Entity;
        //    11: aload_0        
        //    12: aload_0        
        //    13: invokevirtual   net/minecraft/entity/projectile/EntityEgg.getThrower:()Lnet/minecraft/entity/EntityLivingBase;
        //    16: invokestatic    net/minecraft/util/DamageSource.causeThrownDamage:(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/DamageSource;
        //    19: fconst_0       
        //    20: invokevirtual   net/minecraft/entity/Entity.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //    23: pop            
        //    24: aload_0        
        //    25: getfield        net/minecraft/entity/projectile/EntityEgg.worldObj:Lnet/minecraft/world/World;
        //    28: getfield        net/minecraft/world/World.isRemote:Z
        //    31: ifne            122
        //    34: aload_0        
        //    35: getfield        net/minecraft/entity/projectile/EntityEgg.rand:Ljava/util/Random;
        //    38: bipush          8
        //    40: invokevirtual   java/util/Random.nextInt:(I)I
        //    43: ifne            122
        //    46: aload_0        
        //    47: getfield        net/minecraft/entity/projectile/EntityEgg.rand:Ljava/util/Random;
        //    50: bipush          32
        //    52: invokevirtual   java/util/Random.nextInt:(I)I
        //    55: ifne            58
        //    58: goto            117
        //    61: new             Lnet/minecraft/entity/passive/EntityChicken;
        //    64: dup            
        //    65: aload_0        
        //    66: getfield        net/minecraft/entity/projectile/EntityEgg.worldObj:Lnet/minecraft/world/World;
        //    69: invokespecial   net/minecraft/entity/passive/EntityChicken.<init>:(Lnet/minecraft/world/World;)V
        //    72: astore          4
        //    74: aload           4
        //    76: sipush          -24000
        //    79: invokevirtual   net/minecraft/entity/passive/EntityChicken.setGrowingAge:(I)V
        //    82: aload           4
        //    84: aload_0        
        //    85: getfield        net/minecraft/entity/projectile/EntityEgg.posX:D
        //    88: aload_0        
        //    89: getfield        net/minecraft/entity/projectile/EntityEgg.posY:D
        //    92: aload_0        
        //    93: getfield        net/minecraft/entity/projectile/EntityEgg.posZ:D
        //    96: aload_0        
        //    97: getfield        net/minecraft/entity/projectile/EntityEgg.rotationYaw:F
        //   100: fconst_0       
        //   101: invokevirtual   net/minecraft/entity/passive/EntityChicken.setLocationAndAngles:(DDDFF)V
        //   104: aload_0        
        //   105: getfield        net/minecraft/entity/projectile/EntityEgg.worldObj:Lnet/minecraft/world/World;
        //   108: aload           4
        //   110: invokevirtual   net/minecraft/world/World.spawnEntityInWorld:(Lnet/minecraft/entity/Entity;)Z
        //   113: pop            
        //   114: iinc            3, 1
        //   117: iconst_0       
        //   118: iconst_4       
        //   119: if_icmplt       61
        //   122: ldc2_w          0.08
        //   125: dstore_2       
        //   126: goto            214
        //   129: aload_0        
        //   130: getfield        net/minecraft/entity/projectile/EntityEgg.worldObj:Lnet/minecraft/world/World;
        //   133: getstatic       net/minecraft/util/EnumParticleTypes.ITEM_CRACK:Lnet/minecraft/util/EnumParticleTypes;
        //   136: aload_0        
        //   137: getfield        net/minecraft/entity/projectile/EntityEgg.posX:D
        //   140: aload_0        
        //   141: getfield        net/minecraft/entity/projectile/EntityEgg.posY:D
        //   144: aload_0        
        //   145: getfield        net/minecraft/entity/projectile/EntityEgg.posZ:D
        //   148: aload_0        
        //   149: getfield        net/minecraft/entity/projectile/EntityEgg.rand:Ljava/util/Random;
        //   152: invokevirtual   java/util/Random.nextFloat:()F
        //   155: f2d            
        //   156: ldc2_w          0.5
        //   159: dsub           
        //   160: ldc2_w          0.08
        //   163: dmul           
        //   164: aload_0        
        //   165: getfield        net/minecraft/entity/projectile/EntityEgg.rand:Ljava/util/Random;
        //   168: invokevirtual   java/util/Random.nextFloat:()F
        //   171: f2d            
        //   172: ldc2_w          0.5
        //   175: dsub           
        //   176: ldc2_w          0.08
        //   179: dmul           
        //   180: aload_0        
        //   181: getfield        net/minecraft/entity/projectile/EntityEgg.rand:Ljava/util/Random;
        //   184: invokevirtual   java/util/Random.nextFloat:()F
        //   187: f2d            
        //   188: ldc2_w          0.5
        //   191: dsub           
        //   192: ldc2_w          0.08
        //   195: dmul           
        //   196: iconst_1       
        //   197: newarray        I
        //   199: dup            
        //   200: iconst_0       
        //   201: getstatic       net/minecraft/init/Items.egg:Lnet/minecraft/item/Item;
        //   204: invokestatic    net/minecraft/item/Item.getIdFromItem:(Lnet/minecraft/item/Item;)I
        //   207: iastore        
        //   208: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   211: iinc            4, 1
        //   214: iconst_0       
        //   215: bipush          8
        //   217: if_icmplt       129
        //   220: aload_0        
        //   221: getfield        net/minecraft/entity/projectile/EntityEgg.worldObj:Lnet/minecraft/world/World;
        //   224: getfield        net/minecraft/world/World.isRemote:Z
        //   227: ifne            234
        //   230: aload_0        
        //   231: invokevirtual   net/minecraft/entity/projectile/EntityEgg.setDead:()V
        //   234: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        __OBFID = "CL_00001724";
    }
}
