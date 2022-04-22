package net.minecraft.pathfinding;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.world.pathfinder.*;
import net.minecraft.util.*;

public class PathNavigateSwimmer extends PathNavigate
{
    private static final String __OBFID;
    
    public PathNavigateSwimmer(final EntityLiving entityLiving, final World world) {
        super(entityLiving, world);
    }
    
    @Override
    protected PathFinder func_179679_a() {
        return new PathFinder(new SwimNodeProcessor());
    }
    
    @Override
    protected boolean canNavigate() {
        return this.isInLiquid();
    }
    
    @Override
    protected Vec3 getEntityPosition() {
        return new Vec3(this.theEntity.posX, this.theEntity.posY + this.theEntity.height * 0.5, this.theEntity.posZ);
    }
    
    @Override
    protected void pathFollow() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/pathfinding/PathNavigateSwimmer.getEntityPosition:()Lnet/minecraft/util/Vec3;
        //     4: astore_1       
        //     5: aload_0        
        //     6: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.theEntity:Lnet/minecraft/entity/EntityLiving;
        //     9: getfield        net/minecraft/entity/EntityLiving.width:F
        //    12: aload_0        
        //    13: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.theEntity:Lnet/minecraft/entity/EntityLiving;
        //    16: getfield        net/minecraft/entity/EntityLiving.width:F
        //    19: fmul           
        //    20: fstore_2       
        //    21: aload_1        
        //    22: aload_0        
        //    23: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.currentPath:Lnet/minecraft/pathfinding/PathEntity;
        //    26: aload_0        
        //    27: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.theEntity:Lnet/minecraft/entity/EntityLiving;
        //    30: aload_0        
        //    31: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.currentPath:Lnet/minecraft/pathfinding/PathEntity;
        //    34: invokevirtual   net/minecraft/pathfinding/PathEntity.getCurrentPathIndex:()I
        //    37: invokevirtual   net/minecraft/pathfinding/PathEntity.getVectorFromIndex:(Lnet/minecraft/entity/Entity;I)Lnet/minecraft/util/Vec3;
        //    40: invokevirtual   net/minecraft/util/Vec3.squareDistanceTo:(Lnet/minecraft/util/Vec3;)D
        //    43: fload_2        
        //    44: f2d            
        //    45: dcmpg          
        //    46: ifge            56
        //    49: aload_0        
        //    50: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.currentPath:Lnet/minecraft/pathfinding/PathEntity;
        //    53: invokevirtual   net/minecraft/pathfinding/PathEntity.incrementPathIndex:()V
        //    56: aload_0        
        //    57: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.currentPath:Lnet/minecraft/pathfinding/PathEntity;
        //    60: invokevirtual   net/minecraft/pathfinding/PathEntity.getCurrentPathIndex:()I
        //    63: bipush          6
        //    65: iadd           
        //    66: aload_0        
        //    67: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.currentPath:Lnet/minecraft/pathfinding/PathEntity;
        //    70: invokevirtual   net/minecraft/pathfinding/PathEntity.getCurrentPathLength:()I
        //    73: iconst_1       
        //    74: isub           
        //    75: invokestatic    java/lang/Math.min:(II)I
        //    78: istore          4
        //    80: goto            136
        //    83: aload_0        
        //    84: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.currentPath:Lnet/minecraft/pathfinding/PathEntity;
        //    87: aload_0        
        //    88: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.theEntity:Lnet/minecraft/entity/EntityLiving;
        //    91: iload           4
        //    93: invokevirtual   net/minecraft/pathfinding/PathEntity.getVectorFromIndex:(Lnet/minecraft/entity/Entity;I)Lnet/minecraft/util/Vec3;
        //    96: astore          5
        //    98: aload           5
        //   100: aload_1        
        //   101: invokevirtual   net/minecraft/util/Vec3.squareDistanceTo:(Lnet/minecraft/util/Vec3;)D
        //   104: ldc2_w          36.0
        //   107: dcmpg          
        //   108: ifgt            133
        //   111: aload_0        
        //   112: aload_1        
        //   113: aload           5
        //   115: iconst_0       
        //   116: iconst_0       
        //   117: iconst_0       
        //   118: ifnull          133
        //   121: aload_0        
        //   122: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.currentPath:Lnet/minecraft/pathfinding/PathEntity;
        //   125: iload           4
        //   127: invokevirtual   net/minecraft/pathfinding/PathEntity.setCurrentPathIndex:(I)V
        //   130: goto            148
        //   133: iinc            4, -1
        //   136: iload           4
        //   138: aload_0        
        //   139: getfield        net/minecraft/pathfinding/PathNavigateSwimmer.currentPath:Lnet/minecraft/pathfinding/PathEntity;
        //   142: invokevirtual   net/minecraft/pathfinding/PathEntity.getCurrentPathIndex:()I
        //   145: if_icmpgt       83
        //   148: aload_0        
        //   149: aload_1        
        //   150: invokevirtual   net/minecraft/pathfinding/PathNavigateSwimmer.func_179677_a:(Lnet/minecraft/util/Vec3;)V
        //   153: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0133 (coming from #0118).
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
    protected void removeSunnyPath() {
        super.removeSunnyPath();
    }
    
    static {
        __OBFID = "CL_00002244";
    }
}
