package net.minecraft.client.renderer;

import net.minecraft.world.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;

public class ViewFrustum
{
    protected final RenderGlobal field_178169_a;
    protected final World field_178167_b;
    protected int field_178168_c;
    protected int field_178165_d;
    protected int field_178166_e;
    public RenderChunk[] field_178164_f;
    
    public ViewFrustum(final World field_178167_b, final int n, final RenderGlobal field_178169_a, final IRenderChunkFactory renderChunkFactory) {
        this.field_178169_a = field_178169_a;
        this.field_178167_b = field_178167_b;
        this.func_178159_a(n);
        this.func_178158_a(renderChunkFactory);
    }
    
    protected void func_178158_a(final IRenderChunkFactory renderChunkFactory) {
        this.field_178164_f = new RenderChunk[this.field_178165_d * this.field_178168_c * this.field_178166_e];
        while (0 < this.field_178165_d) {
            while (0 < this.field_178168_c) {
                while (0 < this.field_178166_e) {
                    final int n = (0 * this.field_178168_c + 0) * this.field_178165_d + 0;
                    final BlockPos blockPos = new BlockPos(0, 0, 0);
                    final RenderChunk[] field_178164_f = this.field_178164_f;
                    final int n2 = n;
                    final World field_178167_b = this.field_178167_b;
                    final RenderGlobal field_178169_a = this.field_178169_a;
                    final BlockPos blockPos2 = blockPos;
                    final int n3 = 0;
                    int n4 = 0;
                    ++n4;
                    field_178164_f[n2] = renderChunkFactory.func_178602_a(field_178167_b, field_178169_a, blockPos2, n3);
                    int n5 = 0;
                    ++n5;
                }
                int n6 = 0;
                ++n6;
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    public void func_178160_a() {
        final RenderChunk[] field_178164_f = this.field_178164_f;
        while (0 < field_178164_f.length) {
            field_178164_f[0].func_178566_a();
            int n = 0;
            ++n;
        }
    }
    
    protected void func_178159_a(final int n) {
        final int n2 = n * 2 + 1;
        this.field_178165_d = n2;
        this.field_178168_c = 16;
        this.field_178166_e = n2;
    }
    
    public void func_178163_a(final double n, final double n2) {
        final int n3 = MathHelper.floor_double(n) - 8;
        final int n4 = MathHelper.floor_double(n2) - 8;
        final int n5 = this.field_178165_d * 16;
        while (0 < this.field_178165_d) {
            final int func_178157_a = this.func_178157_a(n3, n5, 0);
            while (0 < this.field_178166_e) {
                final int func_178157_a2 = this.func_178157_a(n4, n5, 0);
                while (0 < this.field_178168_c) {
                    final RenderChunk renderChunk = this.field_178164_f[(0 * this.field_178168_c + 0) * this.field_178165_d + 0];
                    final BlockPos func_178568_j = renderChunk.func_178568_j();
                    if (func_178568_j.getX() != func_178157_a || func_178568_j.getY() != 0 || func_178568_j.getZ() != func_178157_a2) {
                        final BlockPos blockPos = new BlockPos(func_178157_a, 0, func_178157_a2);
                        if (!blockPos.equals(renderChunk.func_178568_j())) {
                            renderChunk.func_178576_a(blockPos);
                        }
                    }
                    int n6 = 0;
                    ++n6;
                }
                int n7 = 0;
                ++n7;
            }
            int n8 = 0;
            ++n8;
        }
    }
    
    private int func_178157_a(final int n, final int n2, final int n3) {
        final int n4 = n3 * 16;
        int n5 = n4 - n + n2 / 2;
        if (n5 < 0) {
            n5 -= n2 - 1;
        }
        return n4 - n5 / n2 * n2;
    }
    
    public void func_178162_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final int bucketInt = MathHelper.bucketInt(n, 16);
        final int bucketInt2 = MathHelper.bucketInt(n2, 16);
        final int bucketInt3 = MathHelper.bucketInt(n3, 16);
        final int bucketInt4 = MathHelper.bucketInt(n4, 16);
        final int bucketInt5 = MathHelper.bucketInt(n5, 16);
        final int bucketInt6 = MathHelper.bucketInt(n6, 16);
        for (int i = bucketInt; i <= bucketInt4; ++i) {
            int n7 = i % this.field_178165_d;
            if (n7 < 0) {
                n7 += this.field_178165_d;
            }
            for (int j = bucketInt2; j <= bucketInt5; ++j) {
                int n8 = j % this.field_178168_c;
                if (n8 < 0) {
                    n8 += this.field_178168_c;
                }
                for (int k = bucketInt3; k <= bucketInt6; ++k) {
                    int n9 = k % this.field_178166_e;
                    if (n9 < 0) {
                        n9 += this.field_178166_e;
                    }
                    this.field_178164_f[(n9 * this.field_178168_c + n8) * this.field_178165_d + n7].func_178575_a(true);
                }
            }
        }
    }
    
    protected RenderChunk func_178161_a(final BlockPos blockPos) {
        final int bucketInt = MathHelper.bucketInt(blockPos.getX(), 16);
        final int bucketInt2 = MathHelper.bucketInt(blockPos.getY(), 16);
        final int bucketInt3 = MathHelper.bucketInt(blockPos.getZ(), 16);
        if (bucketInt2 >= 0 && bucketInt2 < this.field_178168_c) {
            int n = bucketInt % this.field_178165_d;
            if (n < 0) {
                n += this.field_178165_d;
            }
            int n2 = bucketInt3 % this.field_178166_e;
            if (n2 < 0) {
                n2 += this.field_178166_e;
            }
            return this.field_178164_f[(n2 * this.field_178168_c + bucketInt2) * this.field_178165_d + n];
        }
        return null;
    }
}
