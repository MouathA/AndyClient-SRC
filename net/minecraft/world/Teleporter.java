package net.minecraft.world;

import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import java.util.*;

public class Teleporter
{
    private final WorldServer worldServerInstance;
    private final Random random;
    private final LongHashMap destinationCoordinateCache;
    private final List destinationCoordinateKeys;
    private static final String __OBFID;
    
    public Teleporter(final WorldServer worldServerInstance) {
        this.destinationCoordinateCache = new LongHashMap();
        this.destinationCoordinateKeys = Lists.newArrayList();
        this.worldServerInstance = worldServerInstance;
        this.random = new Random(worldServerInstance.getSeed());
    }
    
    public void func_180266_a(final Entity p0, final float p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/Teleporter.worldServerInstance:Lnet/minecraft/world/WorldServer;
        //     4: getfield        net/minecraft/world/WorldServer.provider:Lnet/minecraft/world/WorldProvider;
        //     7: invokevirtual   net/minecraft/world/WorldProvider.getDimensionId:()I
        //    10: iconst_1       
        //    11: if_icmpeq       36
        //    14: aload_0        
        //    15: aload_1        
        //    16: fload_2        
        //    17: ifeq            185
        //    20: aload_0        
        //    21: aload_1        
        //    22: invokevirtual   net/minecraft/world/Teleporter.makePortal:(Lnet/minecraft/entity/Entity;)Z
        //    25: pop            
        //    26: aload_0        
        //    27: aload_1        
        //    28: fload_2        
        //    29: invokevirtual   net/minecraft/world/Teleporter.func_180620_b:(Lnet/minecraft/entity/Entity;F)Z
        //    32: pop            
        //    33: goto            185
        //    36: aload_1        
        //    37: getfield        net/minecraft/entity/Entity.posX:D
        //    40: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    43: istore_3       
        //    44: aload_1        
        //    45: getfield        net/minecraft/entity/Entity.posY:D
        //    48: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    51: iconst_1       
        //    52: isub           
        //    53: istore          4
        //    55: aload_1        
        //    56: getfield        net/minecraft/entity/Entity.posZ:D
        //    59: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    62: istore          5
        //    64: goto            153
        //    67: goto            150
        //    70: goto            147
        //    73: iload_3        
        //    74: bipush          -2
        //    76: iadd           
        //    77: iconst_0       
        //    78: iadd           
        //    79: istore          11
        //    81: iload           4
        //    83: iconst_m1      
        //    84: iadd           
        //    85: istore          12
        //    87: iload           5
        //    89: iconst_0       
        //    90: iadd           
        //    91: bipush          -2
        //    93: isub           
        //    94: istore          13
        //    96: iconst_1       
        //    97: goto            101
        //   100: iconst_0       
        //   101: istore          14
        //   103: aload_0        
        //   104: getfield        net/minecraft/world/Teleporter.worldServerInstance:Lnet/minecraft/world/WorldServer;
        //   107: new             Lnet/minecraft/util/BlockPos;
        //   110: dup            
        //   111: iload           11
        //   113: iload           12
        //   115: iload           13
        //   117: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   120: iload           14
        //   122: ifeq            134
        //   125: getstatic       net/minecraft/init/Blocks.obsidian:Lnet/minecraft/block/Block;
        //   128: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   131: goto            140
        //   134: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
        //   137: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   140: invokevirtual   net/minecraft/world/WorldServer.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //   143: pop            
        //   144: iinc            10, 1
        //   147: iinc            9, 1
        //   150: iinc            8, 1
        //   153: aload_1        
        //   154: iload_3        
        //   155: i2d            
        //   156: iload           4
        //   158: i2d            
        //   159: iload           5
        //   161: i2d            
        //   162: aload_1        
        //   163: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   166: fconst_0       
        //   167: invokevirtual   net/minecraft/entity/Entity.setLocationAndAngles:(DDDFF)V
        //   170: aload_1        
        //   171: aload_1        
        //   172: aload_1        
        //   173: dconst_0       
        //   174: dup2_x1        
        //   175: putfield        net/minecraft/entity/Entity.motionZ:D
        //   178: dup2_x1        
        //   179: putfield        net/minecraft/entity/Entity.motionY:D
        //   182: putfield        net/minecraft/entity/Entity.motionX:D
        //   185: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0185 (coming from #0017).
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
    
    private boolean func_180265_a(final BlockPos blockPos) {
        return !this.worldServerInstance.isAirBlock(blockPos) || !this.worldServerInstance.isAirBlock(blockPos.offsetUp());
    }
    
    public boolean makePortal(final Entity entity) {
        final double n = -1.0;
        final int floor_double = MathHelper.floor_double(entity.posX);
        MathHelper.floor_double(entity.posY);
        final int floor_double2 = MathHelper.floor_double(entity.posZ);
        this.random.nextInt(4);
        for (int i = floor_double - 16; i <= floor_double + 16; ++i) {
            final double n2 = i + 0.5 - entity.posX;
            for (int j = floor_double2 - 16; j <= floor_double2 + 16; ++j) {
                final double n3 = j + 0.5 - entity.posZ;
                final int n4 = this.worldServerInstance.getActualHeight() - 1;
            }
        }
        if (n < 0.0) {
            for (int k = floor_double - 16; k <= floor_double + 16; ++k) {
                final double n5 = k + 0.5 - entity.posX;
                for (int l = floor_double2 - 16; l <= floor_double2 + 16; ++l) {
                    final double n6 = l + 0.5 - entity.posZ;
                    final int n7 = this.worldServerInstance.getActualHeight() - 1;
                }
            }
        }
        if (n < 0.0) {
            MathHelper.clamp_int(-1, 70, this.worldServerInstance.getActualHeight() - 10);
        }
        Blocks.portal.getDefaultState().withProperty(BlockPortal.field_176550_a, EnumFacing.Axis.Z);
        return true;
    }
    
    public void removeStalePortalLocations(final long n) {
        if (n % 100L == 0L) {
            final Iterator<Long> iterator = (Iterator<Long>)this.destinationCoordinateKeys.iterator();
            final long n2 = n - 600L;
            while (iterator.hasNext()) {
                final Long n3 = iterator.next();
                final PortalPosition portalPosition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(n3);
                if (portalPosition == null || portalPosition.lastUpdateTime < n2) {
                    iterator.remove();
                    this.destinationCoordinateCache.remove(n3);
                }
            }
        }
    }
    
    static {
        __OBFID = "CL_00000153";
    }
    
    public class PortalPosition extends BlockPos
    {
        public long lastUpdateTime;
        private static final String __OBFID;
        final Teleporter this$0;
        
        public PortalPosition(final Teleporter this$0, final BlockPos blockPos, final long lastUpdateTime) {
            this.this$0 = this$0;
            super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.lastUpdateTime = lastUpdateTime;
        }
        
        static {
            __OBFID = "CL_00000154";
        }
    }
}
