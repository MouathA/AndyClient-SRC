package net.minecraft.client.renderer.chunk;

import java.util.concurrent.locks.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import java.util.*;

public class ChunkCompileTaskGenerator
{
    private final RenderChunk field_178553_a;
    private final ReentrantLock field_178551_b;
    private final List field_178552_c;
    private final Type field_178549_d;
    private RegionRenderCacheBuilder field_178550_e;
    private CompiledChunk field_178547_f;
    private Status field_178548_g;
    private boolean field_178554_h;
    private static final String __OBFID;
    
    public ChunkCompileTaskGenerator(final RenderChunk field_178553_a, final Type field_178549_d) {
        this.field_178551_b = new ReentrantLock();
        this.field_178552_c = Lists.newArrayList();
        this.field_178548_g = Status.PENDING;
        this.field_178553_a = field_178553_a;
        this.field_178549_d = field_178549_d;
    }
    
    public Status func_178546_a() {
        return this.field_178548_g;
    }
    
    public RenderChunk func_178536_b() {
        return this.field_178553_a;
    }
    
    public CompiledChunk func_178544_c() {
        return this.field_178547_f;
    }
    
    public void func_178543_a(final CompiledChunk field_178547_f) {
        this.field_178547_f = field_178547_f;
    }
    
    public RegionRenderCacheBuilder func_178545_d() {
        return this.field_178550_e;
    }
    
    public void func_178541_a(final RegionRenderCacheBuilder field_178550_e) {
        this.field_178550_e = field_178550_e;
    }
    
    public void func_178535_a(final Status field_178548_g) {
        this.field_178551_b.lock();
        this.field_178548_g = field_178548_g;
        this.field_178551_b.unlock();
    }
    
    public void func_178542_e() {
        this.field_178551_b.lock();
        if (this.field_178549_d == Type.REBUILD_CHUNK && this.field_178548_g != Status.DONE) {
            this.field_178553_a.func_178575_a(true);
        }
        this.field_178554_h = true;
        this.field_178548_g = Status.DONE;
        final Iterator<Runnable> iterator = this.field_178552_c.iterator();
        while (iterator.hasNext()) {
            iterator.next().run();
        }
        this.field_178551_b.unlock();
    }
    
    public void func_178539_a(final Runnable runnable) {
        this.field_178551_b.lock();
        this.field_178552_c.add(runnable);
        if (this.field_178554_h) {
            runnable.run();
        }
        this.field_178551_b.unlock();
    }
    
    public ReentrantLock func_178540_f() {
        return this.field_178551_b;
    }
    
    public Type func_178538_g() {
        return this.field_178549_d;
    }
    
    public boolean func_178537_h() {
        return this.field_178554_h;
    }
    
    static {
        __OBFID = "CL_00002466";
    }
    
    public enum Status
    {
        PENDING("PENDING", 0, "PENDING", 0, "PENDING", 0), 
        COMPILING("COMPILING", 1, "COMPILING", 1, "COMPILING", 1), 
        UPLOADING("UPLOADING", 2, "UPLOADING", 2, "UPLOADING", 2), 
        DONE("DONE", 3, "DONE", 3, "DONE", 3);
        
        private static final Status[] $VALUES;
        private static final String __OBFID;
        private static final Status[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002465";
            ENUM$VALUES = new Status[] { Status.PENDING, Status.COMPILING, Status.UPLOADING, Status.DONE };
            $VALUES = new Status[] { Status.PENDING, Status.COMPILING, Status.UPLOADING, Status.DONE };
        }
        
        private Status(final String s, final int n, final String s2, final int n2, final String s3, final int n3) {
        }
    }
    
    public enum Type
    {
        REBUILD_CHUNK("REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0), 
        RESORT_TRANSPARENCY("RESORT_TRANSPARENCY", 1, "RESORT_TRANSPARENCY", 1, "RESORT_TRANSPARENCY", 1);
        
        private static final Type[] $VALUES;
        private static final String __OBFID;
        private static final Type[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002464";
            ENUM$VALUES = new Type[] { Type.REBUILD_CHUNK, Type.RESORT_TRANSPARENCY };
            $VALUES = new Type[] { Type.REBUILD_CHUNK, Type.RESORT_TRANSPARENCY };
        }
        
        private Type(final String s, final int n, final String s2, final int n2, final String s3, final int n3) {
        }
    }
}
