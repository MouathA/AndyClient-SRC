package org.lwjgl.util.mapped;

import org.lwjgl.*;
import java.nio.*;
import java.util.concurrent.*;

final class CacheLineSize
{
    private CacheLineSize() {
    }
    
    static int getCacheLineSize() {
        final int n = LWJGLUtil.getPrivilegedInteger("org.lwjgl.util.mapped.CacheLineMaxSize", 1024) / 4;
        final double n2 = 1.0 + LWJGLUtil.getPrivilegedInteger("org.lwjgl.util.mapped.CacheLineTimeThreshold", 50) / 100.0;
        final ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(Executors.newFixedThreadPool(2));
        final IntBuffer memory = getMemory(n);
        while (true) {
            doTest(2, 100000, 0, memory, executorCompletionService);
            int n3 = 0;
            ++n3;
        }
    }
    
    public static void main(final String[] array) {
        CacheUtil.getCacheLineSize();
    }
    
    static long memoryLoop(final int n, final int n2, final IntBuffer intBuffer, final int n3) {
        final long n4 = MemoryUtil.getAddress(intBuffer) + n * n3 * 4;
        final long nanoTime = System.nanoTime();
        while (0 < n2) {
            MappedHelper.ivput(MappedHelper.ivget(n4) + 1, n4);
            int n5 = 0;
            ++n5;
        }
        return System.nanoTime() - nanoTime;
    }
    
    private static IntBuffer getMemory(final int n) {
        final int pageSize = MappedObjectUnsafe.INSTANCE.pageSize();
        final ByteBuffer order = ByteBuffer.allocateDirect(n * 4 + pageSize).order(ByteOrder.nativeOrder());
        if (MemoryUtil.getAddress(order) % pageSize != 0L) {
            order.position(pageSize - (int)(MemoryUtil.getAddress(order) & (long)(pageSize - 1)));
        }
        return order.asIntBuffer();
    }
    
    private static long doTest(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ExecutorCompletionService executorCompletionService) {
        while (0 < n) {
            submitTest(executorCompletionService, 0, n2, intBuffer, n3);
            int n4 = 0;
            ++n4;
        }
        return waitForResults(n, executorCompletionService);
    }
    
    private static void submitTest(final ExecutorCompletionService executorCompletionService, final int n, final int n2, final IntBuffer intBuffer, final int n3) {
        executorCompletionService.submit(new Callable(n, n2, intBuffer, n3) {
            final int val$index;
            final int val$repeats;
            final IntBuffer val$memory;
            final int val$padding;
            
            public Long call() throws Exception {
                return CacheLineSize.memoryLoop(this.val$index, this.val$repeats, this.val$memory, this.val$padding);
            }
            
            public Object call() throws Exception {
                return this.call();
            }
        });
    }
    
    private static long waitForResults(final int n, final ExecutorCompletionService executorCompletionService) {
        long n2 = 0L;
        while (0 < n) {
            n2 += executorCompletionService.take().get();
            int n3 = 0;
            ++n3;
        }
        return n2;
    }
}
