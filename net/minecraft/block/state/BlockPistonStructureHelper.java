package net.minecraft.block.state;

import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;

public class BlockPistonStructureHelper
{
    private final World field_177261_a;
    private final BlockPos field_177259_b;
    private final BlockPos field_177260_c;
    private final EnumFacing field_177257_d;
    private final List field_177258_e;
    private final List field_177256_f;
    private static final String __OBFID;
    
    public BlockPistonStructureHelper(final World field_177261_a, final BlockPos field_177259_b, final EnumFacing field_177257_d, final boolean b) {
        this.field_177258_e = Lists.newArrayList();
        this.field_177256_f = Lists.newArrayList();
        this.field_177261_a = field_177261_a;
        this.field_177259_b = field_177259_b;
        if (b) {
            this.field_177257_d = field_177257_d;
            this.field_177260_c = field_177259_b.offset(field_177257_d);
        }
        else {
            this.field_177257_d = field_177257_d.getOpposite();
            this.field_177260_c = field_177259_b.offset(field_177257_d, 2);
        }
    }
    
    public boolean func_177253_a() {
        this.field_177258_e.clear();
        this.field_177256_f.clear();
        final Block block = this.field_177261_a.getBlockState(this.field_177260_c).getBlock();
        if (!BlockPistonBase.func_180696_a(block, this.field_177261_a, this.field_177260_c, this.field_177257_d, false)) {
            if (block.getMobilityFlag() != 1) {
                return false;
            }
            this.field_177256_f.add(this.field_177260_c);
            return true;
        }
        else {
            if (!this.func_177251_a(this.field_177260_c)) {
                return false;
            }
            while (0 < this.field_177258_e.size()) {
                final BlockPos blockPos = this.field_177258_e.get(0);
                if (this.field_177261_a.getBlockState(blockPos).getBlock() == Blocks.slime_block && !this.func_177250_b(blockPos)) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
    }
    
    private boolean func_177251_a(final BlockPos p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177261_a:Lnet/minecraft/world/World;
        //     4: aload_1        
        //     5: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //     8: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    13: astore_2       
        //    14: aload_2        
        //    15: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    18: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //    21: if_acmpne       26
        //    24: iconst_1       
        //    25: ireturn        
        //    26: aload_2        
        //    27: aload_0        
        //    28: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177261_a:Lnet/minecraft/world/World;
        //    31: aload_1        
        //    32: aload_0        
        //    33: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177257_d:Lnet/minecraft/util/EnumFacing;
        //    36: iconst_0       
        //    37: invokestatic    net/minecraft/block/BlockPistonBase.func_180696_a:(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Z)Z
        //    40: ifne            45
        //    43: iconst_1       
        //    44: ireturn        
        //    45: aload_1        
        //    46: aload_0        
        //    47: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177259_b:Lnet/minecraft/util/BlockPos;
        //    50: invokevirtual   net/minecraft/util/BlockPos.equals:(Ljava/lang/Object;)Z
        //    53: ifeq            58
        //    56: iconst_1       
        //    57: ireturn        
        //    58: aload_0        
        //    59: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177258_e:Ljava/util/List;
        //    62: aload_1        
        //    63: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //    68: ifeq            73
        //    71: iconst_1       
        //    72: ireturn        
        //    73: iconst_1       
        //    74: aload_0        
        //    75: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177258_e:Ljava/util/List;
        //    78: invokeinterface java/util/List.size:()I
        //    83: iadd           
        //    84: bipush          12
        //    86: if_icmple       184
        //    89: iconst_0       
        //    90: ireturn        
        //    91: aload_1        
        //    92: aload_0        
        //    93: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177257_d:Lnet/minecraft/util/EnumFacing;
        //    96: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //    99: iconst_1       
        //   100: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   103: astore          4
        //   105: aload_0        
        //   106: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177261_a:Lnet/minecraft/world/World;
        //   109: aload           4
        //   111: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   114: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   119: astore_2       
        //   120: aload_2        
        //   121: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   124: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   127: if_acmpeq       191
        //   130: aload_2        
        //   131: aload_0        
        //   132: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177261_a:Lnet/minecraft/world/World;
        //   135: aload           4
        //   137: aload_0        
        //   138: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177257_d:Lnet/minecraft/util/EnumFacing;
        //   141: iconst_0       
        //   142: invokestatic    net/minecraft/block/BlockPistonBase.func_180696_a:(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Z)Z
        //   145: ifeq            191
        //   148: aload           4
        //   150: aload_0        
        //   151: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177259_b:Lnet/minecraft/util/BlockPos;
        //   154: invokevirtual   net/minecraft/util/BlockPos.equals:(Ljava/lang/Object;)Z
        //   157: ifeq            163
        //   160: goto            191
        //   163: iinc            3, 1
        //   166: iconst_1       
        //   167: aload_0        
        //   168: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177258_e:Ljava/util/List;
        //   171: invokeinterface java/util/List.size:()I
        //   176: iadd           
        //   177: bipush          12
        //   179: if_icmple       184
        //   182: iconst_0       
        //   183: ireturn        
        //   184: aload_2        
        //   185: getstatic       net/minecraft/init/Blocks.slime_block:Lnet/minecraft/block/Block;
        //   188: if_acmpeq       91
        //   191: goto            222
        //   194: aload_0        
        //   195: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177258_e:Ljava/util/List;
        //   198: aload_1        
        //   199: aload_0        
        //   200: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177257_d:Lnet/minecraft/util/EnumFacing;
        //   203: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //   206: iconst_1       
        //   207: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   210: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   215: pop            
        //   216: iinc            4, 1
        //   219: iinc            5, -1
        //   222: iconst_1       
        //   223: ifge            194
        //   226: aload_1        
        //   227: aload_0        
        //   228: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177257_d:Lnet/minecraft/util/EnumFacing;
        //   231: iconst_1       
        //   232: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   235: astore          6
        //   237: aload_0        
        //   238: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177258_e:Ljava/util/List;
        //   241: aload           6
        //   243: invokeinterface java/util/List.indexOf:(Ljava/lang/Object;)I
        //   248: istore          7
        //   250: iload           7
        //   252: iconst_m1      
        //   253: if_icmple       325
        //   256: aload_0        
        //   257: iconst_0       
        //   258: iload           7
        //   260: invokespecial   net/minecraft/block/state/BlockPistonStructureHelper.func_177255_a:(II)V
        //   263: goto            315
        //   266: aload_0        
        //   267: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177258_e:Ljava/util/List;
        //   270: iconst_0       
        //   271: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   276: checkcast       Lnet/minecraft/util/BlockPos;
        //   279: astore          9
        //   281: aload_0        
        //   282: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177261_a:Lnet/minecraft/world/World;
        //   285: aload           9
        //   287: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   290: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   295: getstatic       net/minecraft/init/Blocks.slime_block:Lnet/minecraft/block/Block;
        //   298: if_acmpne       312
        //   301: aload_0        
        //   302: aload           9
        //   304: invokespecial   net/minecraft/block/state/BlockPistonStructureHelper.func_177250_b:(Lnet/minecraft/util/BlockPos;)Z
        //   307: ifne            312
        //   310: iconst_0       
        //   311: ireturn        
        //   312: iinc            8, 1
        //   315: iconst_0       
        //   316: iload           7
        //   318: iconst_0       
        //   319: iadd           
        //   320: if_icmple       266
        //   323: iconst_1       
        //   324: ireturn        
        //   325: aload_0        
        //   326: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177261_a:Lnet/minecraft/world/World;
        //   329: aload           6
        //   331: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   334: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   339: astore_2       
        //   340: aload_2        
        //   341: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   344: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   347: if_acmpne       352
        //   350: iconst_1       
        //   351: ireturn        
        //   352: aload_2        
        //   353: aload_0        
        //   354: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177261_a:Lnet/minecraft/world/World;
        //   357: aload           6
        //   359: aload_0        
        //   360: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177257_d:Lnet/minecraft/util/EnumFacing;
        //   363: iconst_1       
        //   364: invokestatic    net/minecraft/block/BlockPistonBase.func_180696_a:(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Z)Z
        //   367: ifeq            382
        //   370: aload           6
        //   372: aload_0        
        //   373: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177259_b:Lnet/minecraft/util/BlockPos;
        //   376: invokevirtual   net/minecraft/util/BlockPos.equals:(Ljava/lang/Object;)Z
        //   379: ifeq            384
        //   382: iconst_0       
        //   383: ireturn        
        //   384: aload_2        
        //   385: invokevirtual   net/minecraft/block/Block.getMobilityFlag:()I
        //   388: iconst_1       
        //   389: if_icmpne       406
        //   392: aload_0        
        //   393: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177256_f:Ljava/util/List;
        //   396: aload           6
        //   398: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   403: pop            
        //   404: iconst_1       
        //   405: ireturn        
        //   406: aload_0        
        //   407: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177258_e:Ljava/util/List;
        //   410: invokeinterface java/util/List.size:()I
        //   415: bipush          12
        //   417: if_icmplt       422
        //   420: iconst_0       
        //   421: ireturn        
        //   422: aload_0        
        //   423: getfield        net/minecraft/block/state/BlockPistonStructureHelper.field_177258_e:Ljava/util/List;
        //   426: aload           6
        //   428: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   433: pop            
        //   434: iinc            4, 1
        //   437: iinc            5, 1
        //   440: goto            226
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void func_177255_a(final int n, final int n2) {
        final ArrayList arrayList = Lists.newArrayList();
        final ArrayList arrayList2 = Lists.newArrayList();
        final ArrayList arrayList3 = Lists.newArrayList();
        arrayList.addAll(this.field_177258_e.subList(0, n2));
        arrayList2.addAll(this.field_177258_e.subList(this.field_177258_e.size() - n, this.field_177258_e.size()));
        arrayList3.addAll(this.field_177258_e.subList(n2, this.field_177258_e.size() - n));
        this.field_177258_e.clear();
        this.field_177258_e.addAll(arrayList);
        this.field_177258_e.addAll(arrayList2);
        this.field_177258_e.addAll(arrayList3);
    }
    
    private boolean func_177250_b(final BlockPos blockPos) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            if (enumFacing.getAxis() != this.field_177257_d.getAxis() && !this.func_177251_a(blockPos.offset(enumFacing))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public List func_177254_c() {
        return this.field_177258_e;
    }
    
    public List func_177252_d() {
        return this.field_177256_f;
    }
    
    static {
        __OBFID = "CL_00002033";
    }
}
