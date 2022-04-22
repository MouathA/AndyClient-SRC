package org.lwjgl.opencl;

import org.lwjgl.*;
import java.nio.*;

final class CLChecks
{
    private CLChecks() {
    }
    
    static int calculateBufferRectSize(final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, long n, long n2) {
        if (!LWJGLUtil.CHECKS) {
            return 0;
        }
        final long value = pointerBuffer.get(0);
        final long value2 = pointerBuffer.get(1);
        final long value3 = pointerBuffer.get(2);
        if (LWJGLUtil.DEBUG && (value < 0L || value2 < 0L || value3 < 0L)) {
            throw new IllegalArgumentException("Invalid cl_mem host offset: " + value + ", " + value2 + ", " + value3);
        }
        final long value4 = pointerBuffer2.get(0);
        final long value5 = pointerBuffer2.get(1);
        final long value6 = pointerBuffer2.get(2);
        if (LWJGLUtil.DEBUG && (value4 < 1L || value5 < 1L || value6 < 1L)) {
            throw new IllegalArgumentException("Invalid cl_mem rectangle region dimensions: " + value4 + " x " + value5 + " x " + value6);
        }
        if (n == 0L) {
            n = value4;
        }
        else if (LWJGLUtil.DEBUG && n < value4) {
            throw new IllegalArgumentException("Invalid host row pitch specified: " + n);
        }
        if (n2 == 0L) {
            n2 = n * value5;
        }
        else if (LWJGLUtil.DEBUG && n2 < n * value5) {
            throw new IllegalArgumentException("Invalid host slice pitch specified: " + n2);
        }
        return (int)(value3 * n2 + value2 * n + value + value4 * value5 * value6);
    }
    
    static int calculateImageSize(final PointerBuffer pointerBuffer, long n, long n2) {
        if (!LWJGLUtil.CHECKS) {
            return 0;
        }
        final long value = pointerBuffer.get(0);
        final long value2 = pointerBuffer.get(1);
        final long value3 = pointerBuffer.get(2);
        if (LWJGLUtil.DEBUG && (value < 1L || value2 < 1L || value3 < 1L)) {
            throw new IllegalArgumentException("Invalid cl_mem image region dimensions: " + value + " x " + value2 + " x " + value3);
        }
        if (n == 0L) {
            n = value;
        }
        else if (LWJGLUtil.DEBUG && n < value) {
            throw new IllegalArgumentException("Invalid row pitch specified: " + n);
        }
        if (n2 == 0L) {
            n2 = n * value2;
        }
        else if (LWJGLUtil.DEBUG && n2 < n * value2) {
            throw new IllegalArgumentException("Invalid slice pitch specified: " + n2);
        }
        return (int)(n2 * value3);
    }
    
    static int calculateImage2DSize(final ByteBuffer byteBuffer, final long n, final long n2, long n3) {
        if (!LWJGLUtil.CHECKS) {
            return 0;
        }
        if (LWJGLUtil.DEBUG && (n < 1L || n2 < 1L)) {
            throw new IllegalArgumentException("Invalid 2D image dimensions: " + n + " x " + n2);
        }
        final int elementSize = getElementSize(byteBuffer);
        if (n3 == 0L) {
            n3 = n * elementSize;
        }
        else if (LWJGLUtil.DEBUG && (n3 < n * elementSize || n3 % elementSize != 0L)) {
            throw new IllegalArgumentException("Invalid image_row_pitch specified: " + n3);
        }
        return (int)(n3 * n2);
    }
    
    static int calculateImage3DSize(final ByteBuffer byteBuffer, final long n, final long n2, final long n3, long n4, long n5) {
        if (!LWJGLUtil.CHECKS) {
            return 0;
        }
        if (LWJGLUtil.DEBUG && (n < 1L || n2 < 1L || n3 < 2L)) {
            throw new IllegalArgumentException("Invalid 3D image dimensions: " + n + " x " + n2 + " x " + n3);
        }
        final int elementSize = getElementSize(byteBuffer);
        if (n4 == 0L) {
            n4 = n * elementSize;
        }
        else if (LWJGLUtil.DEBUG && (n4 < n * elementSize || n4 % elementSize != 0L)) {
            throw new IllegalArgumentException("Invalid image_row_pitch specified: " + n4);
        }
        if (n5 == 0L) {
            n5 = n4 * n2;
        }
        else if (LWJGLUtil.DEBUG && (n4 < n4 * n2 || n5 % n4 != 0L)) {
            throw new IllegalArgumentException("Invalid image_slice_pitch specified: " + n4);
        }
        return (int)(n5 * n3);
    }
    
    private static int getElementSize(final ByteBuffer byteBuffer) {
        return getChannelCount(byteBuffer.getInt(byteBuffer.position() + 0)) * getChannelSize(byteBuffer.getInt(byteBuffer.position() + 4));
    }
    
    private static int getChannelCount(final int n) {
        switch (n) {
            case 4272:
            case 4273:
            case 4280:
            case 4281:
            case 4282: {
                return 1;
            }
            case 4274:
            case 4275:
            case 4283: {
                return 2;
            }
            case 4276:
            case 4284: {
                return 3;
            }
            case 4277:
            case 4278:
            case 4279: {
                return 4;
            }
            default: {
                throw new IllegalArgumentException("Invalid cl_channel_order specified: " + LWJGLUtil.toHexString(n));
            }
        }
    }
    
    private static int getChannelSize(final int n) {
        switch (n) {
            case 4304:
            case 4306:
            case 4311:
            case 4314: {
                return 1;
            }
            case 4305:
            case 4307:
            case 4308:
            case 4309:
            case 4312:
            case 4315:
            case 4317: {
                return 2;
            }
            case 4310:
            case 4313:
            case 4316:
            case 4318: {
                return 4;
            }
            default: {
                throw new IllegalArgumentException("Invalid cl_channel_type specified: " + LWJGLUtil.toHexString(n));
            }
        }
    }
}
