package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.entity.*;
import java.util.*;

public class ChunkRenderWorker implements Runnable
{
    private static final Logger LOGGER;
    private final ChunkRenderDispatcher field_178477_b;
    private final RegionRenderCacheBuilder field_178478_c;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002459";
        LOGGER = LogManager.getLogger();
    }
    
    public ChunkRenderWorker(final ChunkRenderDispatcher chunkRenderDispatcher) {
        this(chunkRenderDispatcher, null);
    }
    
    public ChunkRenderWorker(final ChunkRenderDispatcher field_178477_b, final RegionRenderCacheBuilder field_178478_c) {
        this.field_178477_b = field_178477_b;
        this.field_178478_c = field_178478_c;
    }
    
    @Override
    public void run() {
        while (true) {
            this.func_178474_a(this.field_178477_b.func_178511_d());
        }
    }
    
    protected void func_178474_a(final ChunkCompileTaskGenerator chunkCompileTaskGenerator) throws InterruptedException {
        chunkCompileTaskGenerator.func_178540_f().lock();
        if (chunkCompileTaskGenerator.func_178546_a() == ChunkCompileTaskGenerator.Status.PENDING) {
            chunkCompileTaskGenerator.func_178535_a(ChunkCompileTaskGenerator.Status.COMPILING);
            chunkCompileTaskGenerator.func_178540_f().unlock();
            final Entity func_175606_aa = Minecraft.getMinecraft().func_175606_aa();
            if (func_175606_aa == null) {
                chunkCompileTaskGenerator.func_178542_e();
            }
            else {
                chunkCompileTaskGenerator.func_178541_a(this.func_178475_b());
                final float n = (float)func_175606_aa.posX;
                final float n2 = (float)func_175606_aa.posY + func_175606_aa.getEyeHeight();
                final float n3 = (float)func_175606_aa.posZ;
                final ChunkCompileTaskGenerator.Type func_178538_g = chunkCompileTaskGenerator.func_178538_g();
                if (func_178538_g == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
                    chunkCompileTaskGenerator.func_178536_b().func_178581_b(n, n2, n3, chunkCompileTaskGenerator);
                }
                else if (func_178538_g == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
                    chunkCompileTaskGenerator.func_178536_b().func_178570_a(n, n2, n3, chunkCompileTaskGenerator);
                }
                chunkCompileTaskGenerator.func_178540_f().lock();
                if (chunkCompileTaskGenerator.func_178546_a() != ChunkCompileTaskGenerator.Status.COMPILING) {
                    if (!chunkCompileTaskGenerator.func_178537_h()) {
                        ChunkRenderWorker.LOGGER.warn("Chunk render task was " + chunkCompileTaskGenerator.func_178546_a() + " when I expected it to be compiling; aborting task");
                    }
                    this.func_178473_b(chunkCompileTaskGenerator);
                    chunkCompileTaskGenerator.func_178540_f().unlock();
                    return;
                }
                chunkCompileTaskGenerator.func_178535_a(ChunkCompileTaskGenerator.Status.UPLOADING);
                chunkCompileTaskGenerator.func_178540_f().unlock();
                final CompiledChunk func_178544_c = chunkCompileTaskGenerator.func_178544_c();
                final ArrayList arrayList = Lists.newArrayList();
                if (func_178538_g == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
                    final EnumWorldBlockLayer[] values = EnumWorldBlockLayer.values();
                    while (0 < values.length) {
                        final EnumWorldBlockLayer enumWorldBlockLayer = values[0];
                        if (func_178544_c.func_178492_d(enumWorldBlockLayer)) {
                            arrayList.add(this.field_178477_b.func_178503_a(enumWorldBlockLayer, chunkCompileTaskGenerator.func_178545_d().func_179038_a(enumWorldBlockLayer), chunkCompileTaskGenerator.func_178536_b(), func_178544_c));
                        }
                        int n4 = 0;
                        ++n4;
                    }
                }
                else if (func_178538_g == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
                    arrayList.add(this.field_178477_b.func_178503_a(EnumWorldBlockLayer.TRANSLUCENT, chunkCompileTaskGenerator.func_178545_d().func_179038_a(EnumWorldBlockLayer.TRANSLUCENT), chunkCompileTaskGenerator.func_178536_b(), func_178544_c));
                }
                final ListenableFuture allAsList = Futures.allAsList(arrayList);
                chunkCompileTaskGenerator.func_178539_a(new Runnable(allAsList) {
                    private static final String __OBFID;
                    final ChunkRenderWorker this$0;
                    private final ListenableFuture val$var19;
                    
                    @Override
                    public void run() {
                        this.val$var19.cancel(false);
                    }
                    
                    static {
                        __OBFID = "CL_00002458";
                    }
                });
                Futures.addCallback(allAsList, new FutureCallback(chunkCompileTaskGenerator, func_178544_c) {
                    private static final String __OBFID;
                    final ChunkRenderWorker this$0;
                    private final ChunkCompileTaskGenerator val$p_178474_1_;
                    private final CompiledChunk val$var7;
                    
                    public void func_178481_a(final List list) {
                        ChunkRenderWorker.access$0(this.this$0, this.val$p_178474_1_);
                        this.val$p_178474_1_.func_178540_f().lock();
                        if (this.val$p_178474_1_.func_178546_a() != ChunkCompileTaskGenerator.Status.UPLOADING) {
                            if (!this.val$p_178474_1_.func_178537_h()) {
                                ChunkRenderWorker.access$1().warn("Chunk render task was " + this.val$p_178474_1_.func_178546_a() + " when I expected it to be uploading; aborting task");
                            }
                            this.val$p_178474_1_.func_178540_f().unlock();
                            return;
                        }
                        this.val$p_178474_1_.func_178535_a(ChunkCompileTaskGenerator.Status.DONE);
                        this.val$p_178474_1_.func_178540_f().unlock();
                        this.val$p_178474_1_.func_178536_b().func_178580_a(this.val$var7);
                    }
                    
                    @Override
                    public void onFailure(final Throwable t) {
                        ChunkRenderWorker.access$0(this.this$0, this.val$p_178474_1_);
                        if (!(t instanceof CancellationException) && !(t instanceof InterruptedException)) {
                            Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(t, "Rendering chunk"));
                        }
                    }
                    
                    @Override
                    public void onSuccess(final Object o) {
                        this.func_178481_a((List)o);
                    }
                    
                    static {
                        __OBFID = "CL_00002457";
                    }
                });
            }
            return;
        }
        if (!chunkCompileTaskGenerator.func_178537_h()) {
            ChunkRenderWorker.LOGGER.warn("Chunk render task was " + chunkCompileTaskGenerator.func_178546_a() + " when I expected it to be pending; ignoring task");
        }
        chunkCompileTaskGenerator.func_178540_f().unlock();
    }
    
    private RegionRenderCacheBuilder func_178475_b() throws InterruptedException {
        return (this.field_178478_c != null) ? this.field_178478_c : this.field_178477_b.func_178515_c();
    }
    
    private void func_178473_b(final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        if (this.field_178478_c == null) {
            this.field_178477_b.func_178512_a(chunkCompileTaskGenerator.func_178545_d());
        }
    }
    
    static void access$0(final ChunkRenderWorker chunkRenderWorker, final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        chunkRenderWorker.func_178473_b(chunkCompileTaskGenerator);
    }
    
    static Logger access$1() {
        return ChunkRenderWorker.LOGGER;
    }
}
