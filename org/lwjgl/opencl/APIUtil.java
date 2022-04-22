package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;
import java.util.*;

final class APIUtil
{
    private static final int INITIAL_BUFFER_SIZE = 256;
    private static final int INITIAL_LENGTHS_SIZE = 4;
    private static final int BUFFERS_SIZE = 32;
    private static final ThreadLocal arrayTL;
    private static final ThreadLocal bufferByteTL;
    private static final ThreadLocal bufferPointerTL;
    private static final ThreadLocal lengthsTL;
    private static final ThreadLocal buffersTL;
    private static final ObjectDestructor DESTRUCTOR_CLSubDevice;
    private static final ObjectDestructor DESTRUCTOR_CLMem;
    private static final ObjectDestructor DESTRUCTOR_CLCommandQueue;
    private static final ObjectDestructor DESTRUCTOR_CLSampler;
    private static final ObjectDestructor DESTRUCTOR_CLProgram;
    private static final ObjectDestructor DESTRUCTOR_CLKernel;
    private static final ObjectDestructor DESTRUCTOR_CLEvent;
    
    private APIUtil() {
    }
    
    private static char[] getArray(final int n) {
        char[] array = APIUtil.arrayTL.get();
        if (array.length < n) {
            for (int i = array.length << 1; i < n; i <<= 1) {}
            array = new char[n];
            APIUtil.arrayTL.set(array);
        }
        return array;
    }
    
    static ByteBuffer getBufferByte(final int n) {
        ByteBuffer byteBuffer = APIUtil.bufferByteTL.get();
        if (byteBuffer.capacity() < n) {
            for (int i = byteBuffer.capacity() << 1; i < n; i <<= 1) {}
            byteBuffer = BufferUtils.createByteBuffer(n);
            APIUtil.bufferByteTL.set(byteBuffer);
        }
        else {
            byteBuffer.clear();
        }
        return byteBuffer;
    }
    
    private static ByteBuffer getBufferByteOffset(final int n) {
        ByteBuffer byteBuffer = APIUtil.bufferByteTL.get();
        if (byteBuffer.capacity() < n) {
            for (int i = byteBuffer.capacity() << 1; i < n; i <<= 1) {}
            final ByteBuffer byteBuffer2 = BufferUtils.createByteBuffer(n);
            byteBuffer2.put(byteBuffer);
            APIUtil.bufferByteTL.set(byteBuffer = byteBuffer2);
        }
        else {
            byteBuffer.position(byteBuffer.limit());
            byteBuffer.limit(byteBuffer.capacity());
        }
        return byteBuffer;
    }
    
    static PointerBuffer getBufferPointer(final int n) {
        PointerBuffer pointerBuffer = APIUtil.bufferPointerTL.get();
        if (pointerBuffer.capacity() < n) {
            for (int i = pointerBuffer.capacity() << 1; i < n; i <<= 1) {}
            pointerBuffer = BufferUtils.createPointerBuffer(n);
            APIUtil.bufferPointerTL.set(pointerBuffer);
        }
        else {
            pointerBuffer.clear();
        }
        return pointerBuffer;
    }
    
    static ShortBuffer getBufferShort() {
        return APIUtil.buffersTL.get().shorts;
    }
    
    static IntBuffer getBufferInt() {
        return APIUtil.buffersTL.get().ints;
    }
    
    static IntBuffer getBufferIntDebug() {
        return APIUtil.buffersTL.get().intsDebug;
    }
    
    static LongBuffer getBufferLong() {
        return APIUtil.buffersTL.get().longs;
    }
    
    static FloatBuffer getBufferFloat() {
        return APIUtil.buffersTL.get().floats;
    }
    
    static DoubleBuffer getBufferDouble() {
        return APIUtil.buffersTL.get().doubles;
    }
    
    static PointerBuffer getBufferPointer() {
        return APIUtil.buffersTL.get().pointers;
    }
    
    static PointerBuffer getLengths() {
        return getLengths(1);
    }
    
    static PointerBuffer getLengths(final int n) {
        PointerBuffer pointerBuffer = APIUtil.lengthsTL.get();
        if (pointerBuffer.capacity() < n) {
            for (int i = pointerBuffer.capacity(); i < n; i <<= 1) {}
            pointerBuffer = BufferUtils.createPointerBuffer(n);
            APIUtil.lengthsTL.set(pointerBuffer);
        }
        else {
            pointerBuffer.clear();
        }
        return pointerBuffer;
    }
    
    private static ByteBuffer encode(final ByteBuffer byteBuffer, final CharSequence charSequence) {
        while (0 < charSequence.length()) {
            final char char1 = charSequence.charAt(0);
            if (LWJGLUtil.DEBUG && '\u0080' <= char1) {
                byteBuffer.put((byte)26);
            }
            else {
                byteBuffer.put((byte)char1);
            }
            int n = 0;
            ++n;
        }
        return byteBuffer;
    }
    
    static String getString(final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        final char[] array = getArray(remaining);
        for (int i = byteBuffer.position(); i < byteBuffer.limit(); ++i) {
            array[i - byteBuffer.position()] = (char)byteBuffer.get(i);
        }
        return new String(array, 0, remaining);
    }
    
    static long getBuffer(final CharSequence charSequence) {
        final ByteBuffer encode = encode(getBufferByte(charSequence.length()), charSequence);
        encode.flip();
        return MemoryUtil.getAddress0(encode);
    }
    
    static long getBuffer(final CharSequence charSequence, final int n) {
        final ByteBuffer encode = encode(getBufferByteOffset(n + charSequence.length()), charSequence);
        encode.flip();
        return MemoryUtil.getAddress(encode);
    }
    
    static long getBufferNT(final CharSequence charSequence) {
        final ByteBuffer encode = encode(getBufferByte(charSequence.length() + 1), charSequence);
        encode.put((byte)0);
        encode.flip();
        return MemoryUtil.getAddress0(encode);
    }
    
    static int getTotalLength(final CharSequence[] array) {
        while (0 < array.length) {
            final int n = 0 + array[0].length();
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    static long getBuffer(final CharSequence[] array) {
        final ByteBuffer bufferByte = getBufferByte(getTotalLength(array));
        while (0 < array.length) {
            encode(bufferByte, array[0]);
            int n = 0;
            ++n;
        }
        bufferByte.flip();
        return MemoryUtil.getAddress0(bufferByte);
    }
    
    static long getBufferNT(final CharSequence[] array) {
        final ByteBuffer bufferByte = getBufferByte(getTotalLength(array) + array.length);
        while (0 < array.length) {
            encode(bufferByte, array[0]);
            bufferByte.put((byte)0);
            int n = 0;
            ++n;
        }
        bufferByte.flip();
        return MemoryUtil.getAddress0(bufferByte);
    }
    
    static long getLengths(final CharSequence[] array) {
        final PointerBuffer lengths = getLengths(array.length);
        while (0 < array.length) {
            lengths.put(array[0].length());
            int n = 0;
            ++n;
        }
        lengths.flip();
        return MemoryUtil.getAddress0(lengths);
    }
    
    static long getLengths(final ByteBuffer[] array) {
        final PointerBuffer lengths = getLengths(array.length);
        while (0 < array.length) {
            lengths.put(array[0].remaining());
            int n = 0;
            ++n;
        }
        lengths.flip();
        return MemoryUtil.getAddress0(lengths);
    }
    
    static int getSize(final PointerBuffer pointerBuffer) {
        long n = 0L;
        for (int i = pointerBuffer.position(); i < pointerBuffer.limit(); ++i) {
            n += pointerBuffer.get(i);
        }
        return (int)n;
    }
    
    static long getPointer(final PointerWrapper pointerWrapper) {
        return MemoryUtil.getAddress0(getBufferPointer().put(0, pointerWrapper));
    }
    
    static long getPointerSafe(final PointerWrapper pointerWrapper) {
        return MemoryUtil.getAddress0(getBufferPointer().put(0, (pointerWrapper == null) ? 0L : pointerWrapper.getPointer()));
    }
    
    static Set getExtensions(final String s) {
        final HashSet<String> set = new HashSet<String>();
        if (s != null) {
            final StringTokenizer stringTokenizer = new StringTokenizer(s);
            while (stringTokenizer.hasMoreTokens()) {
                set.add(stringTokenizer.nextToken());
            }
        }
        return set;
    }
    
    static boolean isDevicesParam(final int n) {
        switch (n) {
            case 4225:
            case 8198:
            case 8199:
            case 268435458:
            case 268435459: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    static CLPlatform getCLPlatform(final PointerBuffer pointerBuffer) {
        long value = 0L;
        while (0 < pointerBuffer.remaining() / 2) {
            final long value2 = pointerBuffer.get(0);
            if (value2 == 0L) {
                break;
            }
            if (value2 == 4228L) {
                value = pointerBuffer.get(1);
                break;
            }
            int n = 0;
            ++n;
        }
        if (value == 0L) {
            throw new IllegalArgumentException("Could not find CL_CONTEXT_PLATFORM in cl_context_properties.");
        }
        final CLPlatform clPlatform = CLPlatform.getCLPlatform(value);
        if (clPlatform == null) {
            throw new IllegalStateException("Could not find a valid CLPlatform. Make sure clGetPlatformIDs has been used before.");
        }
        return clPlatform;
    }
    
    static ByteBuffer getNativeKernelArgs(final long n, final CLMem[] array, final long[] array2) {
        final ByteBuffer bufferByte = getBufferByte(12 + ((array == null) ? 0 : (array.length * (4 + PointerBuffer.getPointerSize()))));
        bufferByte.putLong(0, n);
        if (array == null) {
            bufferByte.putInt(8, 0);
        }
        else {
            bufferByte.putInt(8, array.length);
            while (0 < array.length) {
                if (LWJGLUtil.DEBUG && !array[0].isValid()) {
                    throw new IllegalArgumentException("An invalid CLMem object was specified.");
                }
                bufferByte.putInt(12, (int)array2[0]);
                final int n2 = 12 + (4 + PointerBuffer.getPointerSize());
                int n3 = 0;
                ++n3;
            }
        }
        return bufferByte;
    }
    
    static void releaseObjects(final CLDevice clDevice) {
        if (!clDevice.isValid() || clDevice.getReferenceCount() > 1) {
            return;
        }
        releaseObjects(clDevice.getSubCLDeviceRegistry(), APIUtil.DESTRUCTOR_CLSubDevice);
    }
    
    static void releaseObjects(final CLContext clContext) {
        if (!clContext.isValid() || clContext.getReferenceCount() > 1) {
            return;
        }
        releaseObjects(clContext.getCLEventRegistry(), APIUtil.DESTRUCTOR_CLEvent);
        releaseObjects(clContext.getCLProgramRegistry(), APIUtil.DESTRUCTOR_CLProgram);
        releaseObjects(clContext.getCLSamplerRegistry(), APIUtil.DESTRUCTOR_CLSampler);
        releaseObjects(clContext.getCLMemRegistry(), APIUtil.DESTRUCTOR_CLMem);
        releaseObjects(clContext.getCLCommandQueueRegistry(), APIUtil.DESTRUCTOR_CLCommandQueue);
    }
    
    static void releaseObjects(final CLProgram clProgram) {
        if (!clProgram.isValid() || clProgram.getReferenceCount() > 1) {
            return;
        }
        releaseObjects(clProgram.getCLKernelRegistry(), APIUtil.DESTRUCTOR_CLKernel);
    }
    
    static void releaseObjects(final CLCommandQueue clCommandQueue) {
        if (!clCommandQueue.isValid() || clCommandQueue.getReferenceCount() > 1) {
            return;
        }
        releaseObjects(clCommandQueue.getCLEventRegistry(), APIUtil.DESTRUCTOR_CLEvent);
    }
    
    private static void releaseObjects(final CLObjectRegistry clObjectRegistry, final ObjectDestructor objectDestructor) {
        if (clObjectRegistry.isEmpty()) {
            return;
        }
        final Iterator<FastLongMap.Entry> iterator = clObjectRegistry.getAll().iterator();
        while (iterator.hasNext()) {
            final CLObjectChild clObjectChild = (CLObjectChild)iterator.next().value;
            while (clObjectChild.isValid()) {
                objectDestructor.release(clObjectChild);
            }
        }
    }
    
    static {
        arrayTL = new ThreadLocal() {
            @Override
            protected char[] initialValue() {
                return new char[256];
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        bufferByteTL = new ThreadLocal() {
            @Override
            protected ByteBuffer initialValue() {
                return BufferUtils.createByteBuffer(256);
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        bufferPointerTL = new ThreadLocal() {
            @Override
            protected PointerBuffer initialValue() {
                return BufferUtils.createPointerBuffer(256);
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        lengthsTL = new ThreadLocal() {
            @Override
            protected PointerBuffer initialValue() {
                return BufferUtils.createPointerBuffer(4);
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        buffersTL = new ThreadLocal() {
            @Override
            protected Buffers initialValue() {
                return new Buffers();
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        DESTRUCTOR_CLSubDevice = new ObjectDestructor() {
            public void release(final CLDevice clDevice) {
                EXTDeviceFission.clReleaseDeviceEXT(clDevice);
            }
            
            public void release(final CLObjectChild clObjectChild) {
                this.release((CLDevice)clObjectChild);
            }
        };
        DESTRUCTOR_CLMem = new ObjectDestructor() {
            public void release(final CLMem clMem) {
                CL10.clReleaseMemObject(clMem);
            }
            
            public void release(final CLObjectChild clObjectChild) {
                this.release((CLMem)clObjectChild);
            }
        };
        DESTRUCTOR_CLCommandQueue = new ObjectDestructor() {
            public void release(final CLCommandQueue clCommandQueue) {
                CL10.clReleaseCommandQueue(clCommandQueue);
            }
            
            public void release(final CLObjectChild clObjectChild) {
                this.release((CLCommandQueue)clObjectChild);
            }
        };
        DESTRUCTOR_CLSampler = new ObjectDestructor() {
            public void release(final CLSampler clSampler) {
                CL10.clReleaseSampler(clSampler);
            }
            
            public void release(final CLObjectChild clObjectChild) {
                this.release((CLSampler)clObjectChild);
            }
        };
        DESTRUCTOR_CLProgram = new ObjectDestructor() {
            public void release(final CLProgram clProgram) {
                CL10.clReleaseProgram(clProgram);
            }
            
            public void release(final CLObjectChild clObjectChild) {
                this.release((CLProgram)clObjectChild);
            }
        };
        DESTRUCTOR_CLKernel = new ObjectDestructor() {
            public void release(final CLKernel clKernel) {
                CL10.clReleaseKernel(clKernel);
            }
            
            public void release(final CLObjectChild clObjectChild) {
                this.release((CLKernel)clObjectChild);
            }
        };
        DESTRUCTOR_CLEvent = new ObjectDestructor() {
            public void release(final CLEvent clEvent) {
                CL10.clReleaseEvent(clEvent);
            }
            
            public void release(final CLObjectChild clObjectChild) {
                this.release((CLEvent)clObjectChild);
            }
        };
    }
    
    private interface ObjectDestructor
    {
        void release(final CLObjectChild p0);
    }
    
    private static class Buffers
    {
        final ShortBuffer shorts;
        final IntBuffer ints;
        final IntBuffer intsDebug;
        final LongBuffer longs;
        final FloatBuffer floats;
        final DoubleBuffer doubles;
        final PointerBuffer pointers;
        
        Buffers() {
            this.shorts = BufferUtils.createShortBuffer(32);
            this.ints = BufferUtils.createIntBuffer(32);
            this.intsDebug = BufferUtils.createIntBuffer(1);
            this.longs = BufferUtils.createLongBuffer(32);
            this.floats = BufferUtils.createFloatBuffer(32);
            this.doubles = BufferUtils.createDoubleBuffer(32);
            this.pointers = BufferUtils.createPointerBuffer(32);
        }
    }
}
