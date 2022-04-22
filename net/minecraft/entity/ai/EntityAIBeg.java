package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityAIBeg extends EntityAIBase
{
    private EntityWolf theWolf;
    private EntityPlayer thePlayer;
    private World worldObject;
    private float minPlayerDistance;
    private int field_75384_e;
    private static final String __OBFID;
    
    public EntityAIBeg(final EntityWolf theWolf, final float minPlayerDistance) {
        this.theWolf = theWolf;
        this.worldObject = theWolf.worldObj;
        this.minPlayerDistance = minPlayerDistance;
        this.setMutexBits(2);
    }
    
    @Override
    public boolean shouldExecute() {
        this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, this.minPlayerDistance);
        return this.thePlayer != null && this.hasPlayerGotBoneInHand(this.thePlayer);
    }
    
    @Override
    public boolean continueExecuting() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/ai/EntityAIBeg.thePlayer:Lnet/minecraft/entity/player/EntityPlayer;
        //     4: invokevirtual   net/minecraft/entity/player/EntityPlayer.isEntityAlive:()Z
        //     7: ifne            14
        //    10: iconst_0       
        //    11: goto            63
        //    14: aload_0        
        //    15: getfield        net/minecraft/entity/ai/EntityAIBeg.theWolf:Lnet/minecraft/entity/passive/EntityWolf;
        //    18: aload_0        
        //    19: getfield        net/minecraft/entity/ai/EntityAIBeg.thePlayer:Lnet/minecraft/entity/player/EntityPlayer;
        //    22: invokevirtual   net/minecraft/entity/passive/EntityWolf.getDistanceSqToEntity:(Lnet/minecraft/entity/Entity;)D
        //    25: aload_0        
        //    26: getfield        net/minecraft/entity/ai/EntityAIBeg.minPlayerDistance:F
        //    29: aload_0        
        //    30: getfield        net/minecraft/entity/ai/EntityAIBeg.minPlayerDistance:F
        //    33: fmul           
        //    34: f2d            
        //    35: dcmpl          
        //    36: ifle            43
        //    39: iconst_0       
        //    40: goto            63
        //    43: aload_0        
        //    44: getfield        net/minecraft/entity/ai/EntityAIBeg.field_75384_e:I
        //    47: ifle            62
        //    50: aload_0        
        //    51: aload_0        
        //    52: getfield        net/minecraft/entity/ai/EntityAIBeg.thePlayer:Lnet/minecraft/entity/player/EntityPlayer;
        //    55: ifnonnull       62
        //    58: iconst_1       
        //    59: goto            63
        //    62: iconst_0       
        //    63: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0062 (coming from #0055).
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
    
    @Override
    public void startExecuting() {
        this.theWolf.func_70918_i(true);
        this.field_75384_e = 40 + this.theWolf.getRNG().nextInt(40);
    }
    
    @Override
    public void resetTask() {
        this.theWolf.func_70918_i(false);
        this.thePlayer = null;
    }
    
    @Override
    public void updateTask() {
        this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0f, (float)this.theWolf.getVerticalFaceSpeed());
        --this.field_75384_e;
    }
    
    static {
        __OBFID = "CL_00001576";
    }
}
