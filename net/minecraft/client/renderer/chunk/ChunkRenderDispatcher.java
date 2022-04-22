package net.minecraft.client.renderer.chunk;

import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import com.google.common.util.concurrent.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;

public class ChunkRenderDispatcher
{
    private static final Logger field_178523_a;
    private static final ThreadFactory field_178521_b;
    private final List field_178522_c;
    private final BlockingQueue field_178519_d;
    private final BlockingQueue field_178520_e;
    private final WorldVertexBufferUploader field_178517_f;
    private final VertexBufferUploader field_178518_g;
    private final Queue field_178524_h;
    private final ChunkRenderWorker field_178525_i;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002463";
        field_178523_a = LogManager.getLogger();
        field_178521_b = new ThreadFactoryBuilder().setNameFormat("Chunk Batcher %d").setDaemon(true).build();
    }
    
    public ChunkRenderDispatcher() {
        this.field_178522_c = Lists.newArrayList();
        this.field_178519_d = Queues.newArrayBlockingQueue(100);
        this.field_178520_e = Queues.newArrayBlockingQueue(5);
        this.field_178517_f = new WorldVertexBufferUploader();
        this.field_178518_g = new VertexBufferUploader();
        this.field_178524_h = Queues.newArrayDeque();
        int n = 0;
        while (0 < 2) {
            final ChunkRenderWorker chunkRenderWorker = new ChunkRenderWorker(this);
            ChunkRenderDispatcher.field_178521_b.newThread(chunkRenderWorker).start();
            this.field_178522_c.add(chunkRenderWorker);
            ++n;
        }
        while (0 < 5) {
            this.field_178520_e.add(new RegionRenderCacheBuilder());
            ++n;
        }
        this.field_178525_i = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
    }
    
    public String func_178504_a() {
        return String.format("pC: %03d, pU: %1d, aB: %1d", this.field_178519_d.size(), this.field_178524_h.size(), this.field_178520_e.size());
    }
    
    public boolean func_178516_a(final long n) {
        long n2;
        do {
            final Queue field_178524_h = this.field_178524_h;
            final Queue field_178524_h2 = this.field_178524_h;
            // monitorenter(field_178524_h3 = this.field_178524_h)
            if (!this.field_178524_h.isEmpty()) {
                this.field_178524_h.poll().run();
            }
            // monitorexit(field_178524_h3)
            if (n == 0L) {
                break;
            }
            if (!true) {
                break;
            }
            n2 = n - System.nanoTime();
        } while (n2 >= 0L && n2 <= 1000000000L);
        return true;
    }
    
    public boolean func_178507_a(final RenderChunk renderChunk) {
        renderChunk.func_178579_c().lock();
        final ChunkCompileTaskGenerator func_178574_d = renderChunk.func_178574_d();
        func_178574_d.func_178539_a(new Runnable(func_178574_d) {
            private static final String __OBFID;
            final ChunkRenderDispatcher this$0;
            private final ChunkCompileTaskGenerator val$var2;
            
            @Override
            public void run() {
                ChunkRenderDispatcher.access$0(this.this$0).remove(this.val$var2);
            }
            
            static {
                __OBFID = "CL_00002462";
            }
        });
        final boolean offer = this.field_178519_d.offer(func_178574_d);
        if (!offer) {
            func_178574_d.func_178542_e();
        }
        final boolean b = offer;
        renderChunk.func_178579_c().unlock();
        return b;
    }
    
    public boolean func_178505_b(final RenderChunk renderChunk) {
        renderChunk.func_178579_c().lock();
        this.field_178525_i.func_178474_a(renderChunk.func_178574_d());
        renderChunk.func_178579_c().unlock();
        return true;
    }
    
    public void func_178514_b() {
        this.func_178513_e();
        while (this.func_178516_a(0L)) {}
        final ArrayList arrayList = Lists.newArrayList();
        while (arrayList.size() != 5) {
            arrayList.add(this.func_178515_c());
        }
        this.field_178520_e.addAll(arrayList);
    }
    
    public void func_178512_a(final RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this.field_178520_e.add(regionRenderCacheBuilder);
    }
    
    public RegionRenderCacheBuilder func_178515_c() throws InterruptedException {
        return this.field_178520_e.take();
    }
    
    public ChunkCompileTaskGenerator func_178511_d() throws InterruptedException {
        return this.field_178519_d.take();
    }
    
    public boolean func_178509_c(final RenderChunk renderChunk) {
        renderChunk.func_178579_c().lock();
        final ChunkCompileTaskGenerator func_178582_e = renderChunk.func_178582_e();
        if (func_178582_e == null) {
            renderChunk.func_178579_c().unlock();
            return true;
        }
        func_178582_e.func_178539_a(new Runnable(func_178582_e) {
            private static final String __OBFID;
            final ChunkRenderDispatcher this$0;
            private final ChunkCompileTaskGenerator val$var2;
            
            @Override
            public void run() {
                ChunkRenderDispatcher.access$0(this.this$0).remove(this.val$var2);
            }
            
            static {
                __OBFID = "CL_00002461";
            }
        });
        this.field_178519_d.offer(func_178582_e);
        renderChunk.func_178579_c().unlock();
        return true;
    }
    
    public ListenableFuture func_178503_a(final EnumWorldBlockLayer enumWorldBlockLayer, final WorldRenderer worldRenderer, final RenderChunk renderChunk, final CompiledChunk compiledChunk) {
        if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
            if (OpenGlHelper.func_176075_f()) {
                this.func_178506_a(worldRenderer, renderChunk.func_178565_b(enumWorldBlockLayer.ordinal()));
            }
            else {
                this.func_178510_a(worldRenderer, ((ListedRenderChunk)renderChunk).func_178600_a(enumWorldBlockLayer, compiledChunk), renderChunk);
            }
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            return Futures.immediateFuture(null);
        }
        final ListenableFutureTask create = ListenableFutureTask.create(new Runnable(enumWorldBlockLayer, worldRenderer, renderChunk, compiledChunk) {
            private static final String __OBFID;
            final ChunkRenderDispatcher this$0;
            private final EnumWorldBlockLayer val$p_178503_1_;
            private final WorldRenderer val$p_178503_2_;
            private final RenderChunk val$p_178503_3_;
            private final CompiledChunk val$p_178503_4_;
            
            @Override
            public void run() {
                this.this$0.func_178503_a(this.val$p_178503_1_, this.val$p_178503_2_, this.val$p_178503_3_, this.val$p_178503_4_);
            }
            
            static {
                __OBFID = "CL_00002460";
            }
        }, null);
        final Queue field_178524_h = this.field_178524_h;
        final Queue field_178524_h2 = this.field_178524_h;
        // monitorenter(field_178524_h3 = this.field_178524_h)
        this.field_178524_h.add(create);
        // monitorexit(field_178524_h3)
        return create;
    }
    
    private void func_178510_a(final WorldRenderer worldRenderer, final int n, final RenderChunk renderChunk) {
        GL11.glNewList(n, 4864);
        renderChunk.func_178572_f();
        this.field_178517_f.draw(worldRenderer, worldRenderer.func_178976_e());
    }
    
    private void func_178506_a(final WorldRenderer worldRenderer, final VertexBuffer vertexBuffer) {
        this.field_178518_g.func_178178_a(vertexBuffer);
        this.field_178518_g.draw(worldRenderer, worldRenderer.func_178976_e());
    }
    
    public void func_178513_e() {
        while (!this.field_178519_d.isEmpty()) {
            final ChunkCompileTaskGenerator chunkCompileTaskGenerator = (ChunkCompileTaskGenerator)this.field_178519_d.poll();
            if (chunkCompileTaskGenerator != null) {
                chunkCompileTaskGenerator.func_178542_e();
            }
        }
    }
    
    static BlockingQueue access$0(final ChunkRenderDispatcher chunkRenderDispatcher) {
        return chunkRenderDispatcher.field_178519_d;
    }
}
