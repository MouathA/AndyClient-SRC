package net.minecraft.entity.passive;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
    protected Block field_175506_bl;
    private int inLove;
    private EntityPlayer playerInLove;
    private static final String __OBFID;
    
    public EntityAnimal(final World world) {
        super(world);
        this.field_175506_bl = Blocks.grass;
    }
    
    @Override
    protected void updateAITasks() {
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        super.updateAITasks();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        if (this.inLove > 0) {
            --this.inLove;
            if (this.inLove % 10 == 0) {
                this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        this.inLove = 0;
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public float func_180484_a(final BlockPos blockPos) {
        return (this.worldObj.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.grass) ? 10.0f : (this.worldObj.getLightBrightness(blockPos) - 0.5f);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("InLove", this.inLove);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.inLove = nbtTagCompound.getInteger("InLove");
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY), MathHelper.floor_double(this.posZ));
        return this.worldObj.getBlockState(blockPos.offsetDown()).getBlock() == this.field_175506_bl && this.worldObj.getLight(blockPos) > 8 && super.getCanSpawnHere();
    }
    
    @Override
    public int getTalkInterval() {
        return 120;
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        return 1 + this.worldObj.rand.nextInt(3);
    }
    
    @Override
    public boolean interact(final EntityPlayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //     4: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getCurrentItem:()Lnet/minecraft/item/ItemStack;
        //     7: astore_2       
        //     8: aload_2        
        //     9: ifnull          82
        //    12: aload_0        
        //    13: aload_2        
        //    14: ifnonnull       44
        //    17: aload_0        
        //    18: invokevirtual   net/minecraft/entity/passive/EntityAnimal.getGrowingAge:()I
        //    21: ifne            44
        //    24: aload_0        
        //    25: getfield        net/minecraft/entity/passive/EntityAnimal.inLove:I
        //    28: ifgt            44
        //    31: aload_0        
        //    32: aload_1        
        //    33: aload_2        
        //    34: invokevirtual   net/minecraft/entity/passive/EntityAnimal.func_175505_a:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V
        //    37: aload_0        
        //    38: aload_1        
        //    39: invokevirtual   net/minecraft/entity/passive/EntityAnimal.setInLove:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //    42: iconst_1       
        //    43: ireturn        
        //    44: aload_0        
        //    45: invokevirtual   net/minecraft/entity/passive/EntityAnimal.isChild:()Z
        //    48: ifeq            82
        //    51: aload_0        
        //    52: aload_2        
        //    53: ifnonnull       82
        //    56: aload_0        
        //    57: aload_1        
        //    58: aload_2        
        //    59: invokevirtual   net/minecraft/entity/passive/EntityAnimal.func_175505_a:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V
        //    62: aload_0        
        //    63: aload_0        
        //    64: invokevirtual   net/minecraft/entity/passive/EntityAnimal.getGrowingAge:()I
        //    67: ineg           
        //    68: bipush          20
        //    70: idiv           
        //    71: i2f            
        //    72: ldc             0.1
        //    74: fmul           
        //    75: f2i            
        //    76: iconst_1       
        //    77: invokevirtual   net/minecraft/entity/passive/EntityAnimal.func_175501_a:(IZ)V
        //    80: iconst_1       
        //    81: ireturn        
        //    82: aload_0        
        //    83: aload_1        
        //    84: invokespecial   net/minecraft/entity/EntityAgeable.interact:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //    87: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0082 (coming from #0048).
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
    
    protected void func_175505_a(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            --itemStack.stackSize;
            if (itemStack.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
        }
    }
    
    public void setInLove(final EntityPlayer playerInLove) {
        this.inLove = 600;
        this.playerInLove = playerInLove;
        this.worldObj.setEntityState(this, (byte)18);
    }
    
    public EntityPlayer func_146083_cb() {
        return this.playerInLove;
    }
    
    public void resetInLove() {
        this.inLove = 0;
    }
    
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        return entityAnimal != this && entityAnimal.getClass() == this.getClass() && (this > 0 && entityAnimal > 0);
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b != 18) {
            super.handleHealthUpdate(b);
        }
    }
    
    static {
        __OBFID = "CL_00001638";
    }
}
