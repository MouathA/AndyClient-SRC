package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

abstract class InfoUtilAbstract implements InfoUtil
{
    protected InfoUtilAbstract() {
    }
    
    protected abstract int getInfo(final CLObject p0, final int p1, final ByteBuffer p2, final PointerBuffer p3);
    
    protected int getInfoSizeArraySize(final CLObject clObject, final int n) {
        throw new UnsupportedOperationException();
    }
    
    protected PointerBuffer getSizesBuffer(final CLObject clObject, final int n) {
        final int infoSizeArraySize = this.getInfoSizeArraySize(clObject, n);
        final PointerBuffer bufferPointer = APIUtil.getBufferPointer(infoSizeArraySize);
        bufferPointer.limit(infoSizeArraySize);
        this.getInfo(clObject, n, bufferPointer.getBuffer(), null);
        return bufferPointer;
    }
    
    public int getInfoInt(final CLObject clObject, final int n) {
        clObject.checkValid();
        final ByteBuffer bufferByte = APIUtil.getBufferByte(4);
        this.getInfo(clObject, n, bufferByte, null);
        return bufferByte.getInt(0);
    }
    
    public long getInfoSize(final CLObject clObject, final int n) {
        clObject.checkValid();
        final PointerBuffer bufferPointer = APIUtil.getBufferPointer();
        this.getInfo(clObject, n, bufferPointer.getBuffer(), null);
        return bufferPointer.get(0);
    }
    
    public long[] getInfoSizeArray(final CLObject clObject, final int n) {
        clObject.checkValid();
        final int infoSizeArraySize = this.getInfoSizeArraySize(clObject, n);
        final PointerBuffer bufferPointer = APIUtil.getBufferPointer(infoSizeArraySize);
        this.getInfo(clObject, n, bufferPointer.getBuffer(), null);
        final long[] array = new long[infoSizeArraySize];
        while (0 < infoSizeArraySize) {
            array[0] = bufferPointer.get(0);
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    public long getInfoLong(final CLObject clObject, final int n) {
        clObject.checkValid();
        final ByteBuffer bufferByte = APIUtil.getBufferByte(8);
        this.getInfo(clObject, n, bufferByte, null);
        return bufferByte.getLong(0);
    }
    
    public String getInfoString(final CLObject clObject, final int n) {
        clObject.checkValid();
        final int sizeRet = this.getSizeRet(clObject, n);
        if (sizeRet <= 1) {
            return null;
        }
        final ByteBuffer bufferByte = APIUtil.getBufferByte(sizeRet);
        this.getInfo(clObject, n, bufferByte, null);
        bufferByte.limit(sizeRet - 1);
        return APIUtil.getString(bufferByte);
    }
    
    protected final int getSizeRet(final CLObject clObject, final int n) {
        final PointerBuffer bufferPointer = APIUtil.getBufferPointer();
        if (this.getInfo(clObject, n, null, bufferPointer) != 0) {
            throw new IllegalArgumentException("Invalid parameter specified: " + LWJGLUtil.toHexString(n));
        }
        return (int)bufferPointer.get(0);
    }
}
