package net.minecraft.pathfinding;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.world.pathfinder.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class PathNavigateGround extends PathNavigate
{
    protected WalkNodeProcessor field_179695_a;
    private boolean field_179694_f;
    private static final String __OBFID;
    
    public PathNavigateGround(final EntityLiving entityLiving, final World world) {
        super(entityLiving, world);
    }
    
    @Override
    protected PathFinder func_179679_a() {
        (this.field_179695_a = new WalkNodeProcessor()).func_176175_a(true);
        return new PathFinder(this.field_179695_a);
    }
    
    @Override
    protected boolean canNavigate() {
        return this.theEntity.onGround || (this.func_179684_h() && this.isInLiquid()) || (this.theEntity.isRiding() && this.theEntity instanceof EntityZombie && this.theEntity.ridingEntity instanceof EntityChicken);
    }
    
    @Override
    protected Vec3 getEntityPosition() {
        return new Vec3(this.theEntity.posX, this.func_179687_p(), this.theEntity.posZ);
    }
    
    private int func_179687_p() {
        if (!this.theEntity.isInWater() || !this.func_179684_h()) {
            return (int)(this.theEntity.getEntityBoundingBox().minY + 0.5);
        }
        int n = (int)this.theEntity.getEntityBoundingBox().minY;
        final Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), n, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
        if (block != Blocks.flowing_water && block != Blocks.water) {
            return n;
        }
        ++n;
        this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), n, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
        int n2 = 0;
        ++n2;
        return (int)this.theEntity.getEntityBoundingBox().minY;
    }
    
    @Override
    protected void removeSunnyPath() {
        super.removeSunnyPath();
        if (this.field_179694_f) {
            if (this.worldObj.isAgainstSky(new BlockPos(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.getEntityBoundingBox().minY + 0.5), MathHelper.floor_double(this.theEntity.posZ)))) {
                return;
            }
            while (0 < this.currentPath.getCurrentPathLength()) {
                final PathPoint pathPointFromIndex = this.currentPath.getPathPointFromIndex(0);
                if (this.worldObj.isAgainstSky(new BlockPos(pathPointFromIndex.xCoord, pathPointFromIndex.yCoord, pathPointFromIndex.zCoord))) {
                    this.currentPath.setCurrentPathLength(-1);
                    return;
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    protected boolean isDirectPathBetweenPoints(final Vec3 p0, final Vec3 p1, final int p2, final int p3, final int p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/util/Vec3.xCoord:D
        //     4: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //     7: istore          6
        //     9: aload_1        
        //    10: getfield        net/minecraft/util/Vec3.zCoord:D
        //    13: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    16: istore          7
        //    18: aload_2        
        //    19: getfield        net/minecraft/util/Vec3.xCoord:D
        //    22: aload_1        
        //    23: getfield        net/minecraft/util/Vec3.xCoord:D
        //    26: dsub           
        //    27: dstore          8
        //    29: aload_2        
        //    30: getfield        net/minecraft/util/Vec3.zCoord:D
        //    33: aload_1        
        //    34: getfield        net/minecraft/util/Vec3.zCoord:D
        //    37: dsub           
        //    38: dstore          10
        //    40: dload           8
        //    42: dload           8
        //    44: dmul           
        //    45: dload           10
        //    47: dload           10
        //    49: dmul           
        //    50: dadd           
        //    51: dstore          12
        //    53: dload           12
        //    55: ldc2_w          1.0E-8
        //    58: dcmpg          
        //    59: ifge            64
        //    62: iconst_0       
        //    63: ireturn        
        //    64: dconst_1       
        //    65: dload           12
        //    67: invokestatic    java/lang/Math.sqrt:(D)D
        //    70: ddiv           
        //    71: dstore          14
        //    73: dload           8
        //    75: dload           14
        //    77: dmul           
        //    78: dstore          8
        //    80: dload           10
        //    82: dload           14
        //    84: dmul           
        //    85: dstore          10
        //    87: iinc            3, 2
        //    90: iinc            5, 2
        //    93: aload_0        
        //    94: iload           6
        //    96: aload_1        
        //    97: getfield        net/minecraft/util/Vec3.yCoord:D
        //   100: d2i            
        //   101: iload           7
        //   103: iload_3        
        //   104: iload           4
        //   106: iload           5
        //   108: aload_1        
        //   109: dload           8
        //   111: dload           10
        //   113: ifne            118
        //   116: iconst_0       
        //   117: ireturn        
        //   118: iinc            3, -2
        //   121: iinc            5, -2
        //   124: dconst_1       
        //   125: dload           8
        //   127: invokestatic    java/lang/Math.abs:(D)D
        //   130: ddiv           
        //   131: dstore          16
        //   133: dconst_1       
        //   134: dload           10
        //   136: invokestatic    java/lang/Math.abs:(D)D
        //   139: ddiv           
        //   140: dstore          18
        //   142: iload           6
        //   144: iconst_1       
        //   145: imul           
        //   146: i2d            
        //   147: aload_1        
        //   148: getfield        net/minecraft/util/Vec3.xCoord:D
        //   151: dsub           
        //   152: dstore          20
        //   154: iload           7
        //   156: iconst_1       
        //   157: imul           
        //   158: i2d            
        //   159: aload_1        
        //   160: getfield        net/minecraft/util/Vec3.zCoord:D
        //   163: dsub           
        //   164: dstore          22
        //   166: dload           8
        //   168: dconst_0       
        //   169: dcmpl          
        //   170: iflt            179
        //   173: dload           20
        //   175: dconst_1       
        //   176: dadd           
        //   177: dstore          20
        //   179: dload           10
        //   181: dconst_0       
        //   182: dcmpl          
        //   183: iflt            192
        //   186: dload           22
        //   188: dconst_1       
        //   189: dadd           
        //   190: dstore          22
        //   192: dload           20
        //   194: dload           8
        //   196: ddiv           
        //   197: dstore          20
        //   199: dload           22
        //   201: dload           10
        //   203: ddiv           
        //   204: dstore          22
        //   206: dload           8
        //   208: dconst_0       
        //   209: dcmpg          
        //   210: ifge            217
        //   213: iconst_m1      
        //   214: goto            218
        //   217: iconst_1       
        //   218: istore          24
        //   220: dload           10
        //   222: dconst_0       
        //   223: dcmpg          
        //   224: ifge            231
        //   227: iconst_m1      
        //   228: goto            232
        //   231: iconst_1       
        //   232: istore          25
        //   234: aload_2        
        //   235: getfield        net/minecraft/util/Vec3.xCoord:D
        //   238: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   241: istore          26
        //   243: aload_2        
        //   244: getfield        net/minecraft/util/Vec3.zCoord:D
        //   247: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   250: istore          27
        //   252: iload           26
        //   254: iload           6
        //   256: isub           
        //   257: istore          28
        //   259: iload           27
        //   261: iload           7
        //   263: isub           
        //   264: istore          29
        //   266: iload           28
        //   268: iload           24
        //   270: imul           
        //   271: ifgt            284
        //   274: iload           29
        //   276: iload           25
        //   278: imul           
        //   279: ifgt            284
        //   282: iconst_1       
        //   283: ireturn        
        //   284: dload           20
        //   286: dload           22
        //   288: dcmpg          
        //   289: ifge            316
        //   292: dload           20
        //   294: dload           16
        //   296: dadd           
        //   297: dstore          20
        //   299: iload           6
        //   301: iload           24
        //   303: iadd           
        //   304: istore          6
        //   306: iload           26
        //   308: iload           6
        //   310: isub           
        //   311: istore          28
        //   313: goto            337
        //   316: dload           22
        //   318: dload           18
        //   320: dadd           
        //   321: dstore          22
        //   323: iload           7
        //   325: iload           25
        //   327: iadd           
        //   328: istore          7
        //   330: iload           27
        //   332: iload           7
        //   334: isub           
        //   335: istore          29
        //   337: aload_0        
        //   338: iload           6
        //   340: aload_1        
        //   341: getfield        net/minecraft/util/Vec3.yCoord:D
        //   344: d2i            
        //   345: iload           7
        //   347: iload_3        
        //   348: iload           4
        //   350: iload           5
        //   352: aload_1        
        //   353: dload           8
        //   355: dload           10
        //   357: ifne            266
        //   360: iconst_0       
        //   361: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0266 (coming from #0357).
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
    
    public void func_179690_a(final boolean b) {
        this.field_179695_a.func_176176_c(b);
    }
    
    public boolean func_179689_e() {
        return this.field_179695_a.func_176173_e();
    }
    
    public void func_179688_b(final boolean b) {
        this.field_179695_a.func_176172_b(b);
    }
    
    public void func_179691_c(final boolean b) {
        this.field_179695_a.func_176175_a(b);
    }
    
    public boolean func_179686_g() {
        return this.field_179695_a.func_176179_b();
    }
    
    public void func_179693_d(final boolean b) {
        this.field_179695_a.func_176178_d(b);
    }
    
    public boolean func_179684_h() {
        return this.field_179695_a.func_176174_d();
    }
    
    public void func_179685_e(final boolean field_179694_f) {
        this.field_179694_f = field_179694_f;
    }
    
    static {
        __OBFID = "CL_00002246";
    }
}
