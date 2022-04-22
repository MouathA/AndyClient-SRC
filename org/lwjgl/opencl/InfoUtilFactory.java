package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.opencl.api.*;
import org.lwjgl.opengl.*;
import java.util.*;
import org.lwjgl.*;

final class InfoUtilFactory
{
    static final InfoUtil CL_COMMAND_QUEUE_UTIL;
    static final CLContext.CLContextUtil CL_CONTEXT_UTIL;
    static final InfoUtil CL_DEVICE_UTIL;
    static final CLEvent.CLEventUtil CL_EVENT_UTIL;
    static final CLKernel.CLKernelUtil CL_KERNEL_UTIL;
    static final CLMem.CLMemUtil CL_MEM_UTIL;
    static final CLPlatform.CLPlatformUtil CL_PLATFORM_UTIL;
    static final CLProgram.CLProgramUtil CL_PROGRAM_UTIL;
    static final InfoUtil CL_SAMPLER_UTIL;
    
    private InfoUtilFactory() {
    }
    
    static {
        CL_COMMAND_QUEUE_UTIL = new InfoUtilAbstract() {
            protected int getInfo(final CLCommandQueue clCommandQueue, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
                return CL10.clGetCommandQueueInfo(clCommandQueue, n, byteBuffer, null);
            }
            
            @Override
            protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
                return this.getInfo((CLCommandQueue)clObject, n, byteBuffer, pointerBuffer);
            }
        };
        CL_CONTEXT_UTIL = new CLContextUtil(null);
        CL_DEVICE_UTIL = new CLDeviceUtil(null);
        CL_EVENT_UTIL = new CLEventUtil(null);
        CL_KERNEL_UTIL = new CLKernelUtil(null);
        CL_MEM_UTIL = new CLMemUtil(null);
        CL_PLATFORM_UTIL = new CLPlatformUtil(null);
        CL_PROGRAM_UTIL = new CLProgramUtil(null);
        CL_SAMPLER_UTIL = new InfoUtilAbstract() {
            protected int getInfo(final CLSampler clSampler, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
                return CL10.clGetSamplerInfo(clSampler, n, byteBuffer, pointerBuffer);
            }
            
            @Override
            protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
                return this.getInfo((CLSampler)clObject, n, byteBuffer, pointerBuffer);
            }
        };
    }
    
    private static final class CLProgramUtil extends InfoUtilAbstract implements CLProgram.CLProgramUtil
    {
        private CLProgramUtil() {
        }
        
        protected int getInfo(final CLProgram clProgram, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return CL10.clGetProgramInfo(clProgram, n, byteBuffer, pointerBuffer);
        }
        
        protected int getInfoSizeArraySize(final CLProgram clProgram, final int n) {
            switch (n) {
                case 4453: {
                    return this.getInfoInt(clProgram, 4450);
                }
                default: {
                    throw new IllegalArgumentException("Unsupported parameter: " + LWJGLUtil.toHexString(n));
                }
            }
        }
        
        public CLKernel[] createKernelsInProgram(final CLProgram clProgram) {
            final IntBuffer bufferInt = APIUtil.getBufferInt();
            CL10.clCreateKernelsInProgram(clProgram, null, bufferInt);
            final int value = bufferInt.get(0);
            if (value == 0) {
                return null;
            }
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer(value);
            CL10.clCreateKernelsInProgram(clProgram, bufferPointer, null);
            final CLKernel[] array = new CLKernel[value];
            while (0 < value) {
                array[0] = clProgram.getCLKernel(bufferPointer.get(0));
                int n = 0;
                ++n;
            }
            return array;
        }
        
        public CLDevice[] getInfoDevices(final CLProgram clProgram) {
            clProgram.checkValid();
            final int infoInt = this.getInfoInt(clProgram, 4450);
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer(infoInt);
            CL10.clGetProgramInfo(clProgram, 4451, bufferPointer.getBuffer(), null);
            final CLPlatform clPlatform = (CLPlatform)((CLContext)clProgram.getParent()).getParent();
            final CLDevice[] array = new CLDevice[infoInt];
            while (0 < infoInt) {
                array[0] = clPlatform.getCLDevice(bufferPointer.get(0));
                int n = 0;
                ++n;
            }
            return array;
        }
        
        public ByteBuffer getInfoBinaries(final CLProgram clProgram, ByteBuffer byteBuffer) {
            clProgram.checkValid();
            final PointerBuffer sizesBuffer = this.getSizesBuffer(clProgram, 4453);
            while (0 < sizesBuffer.limit()) {
                final int n = (int)(0 + sizesBuffer.get(0));
                int n2 = 0;
                ++n2;
            }
            if (byteBuffer == null) {
                byteBuffer = BufferUtils.createByteBuffer(0);
            }
            else if (LWJGLUtil.DEBUG) {
                BufferChecks.checkBuffer(byteBuffer, 0);
            }
            CL10.clGetProgramInfo(clProgram, sizesBuffer, byteBuffer, null);
            return byteBuffer;
        }
        
        public ByteBuffer[] getInfoBinaries(final CLProgram clProgram, ByteBuffer[] array) {
            clProgram.checkValid();
            if (array == null) {
                final PointerBuffer sizesBuffer = this.getSizesBuffer(clProgram, 4453);
                array = new ByteBuffer[sizesBuffer.remaining()];
                while (0 < sizesBuffer.remaining()) {
                    array[0] = BufferUtils.createByteBuffer((int)sizesBuffer.get(0));
                    int n = 0;
                    ++n;
                }
            }
            else if (LWJGLUtil.DEBUG) {
                final PointerBuffer sizesBuffer2 = this.getSizesBuffer(clProgram, 4453);
                if (array.length < sizesBuffer2.remaining()) {
                    throw new IllegalArgumentException("The target array is not big enough: " + sizesBuffer2.remaining() + " buffers are required.");
                }
                while (0 < array.length) {
                    BufferChecks.checkBuffer(array[0], (int)sizesBuffer2.get(0));
                    int n = 0;
                    ++n;
                }
            }
            CL10.clGetProgramInfo(clProgram, array, null);
            return array;
        }
        
        public String getBuildInfoString(final CLProgram clProgram, final CLDevice clDevice, final int n) {
            clProgram.checkValid();
            final int buildSizeRet = getBuildSizeRet(clProgram, clDevice, n);
            if (buildSizeRet <= 1) {
                return null;
            }
            final ByteBuffer bufferByte = APIUtil.getBufferByte(buildSizeRet);
            CL10.clGetProgramBuildInfo(clProgram, clDevice, n, bufferByte, null);
            bufferByte.limit(buildSizeRet - 1);
            return APIUtil.getString(bufferByte);
        }
        
        public int getBuildInfoInt(final CLProgram clProgram, final CLDevice clDevice, final int n) {
            clProgram.checkValid();
            final ByteBuffer bufferByte = APIUtil.getBufferByte(4);
            CL10.clGetProgramBuildInfo(clProgram, clDevice, n, bufferByte, null);
            return bufferByte.getInt(0);
        }
        
        private static int getBuildSizeRet(final CLProgram clProgram, final CLDevice clDevice, final int n) {
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer();
            if (CL10.clGetProgramBuildInfo(clProgram, clDevice, n, null, bufferPointer) != 0) {
                throw new IllegalArgumentException("Invalid parameter specified: " + LWJGLUtil.toHexString(n));
            }
            return (int)bufferPointer.get(0);
        }
        
        @Override
        protected int getInfoSizeArraySize(final CLObject clObject, final int n) {
            return this.getInfoSizeArraySize((CLProgram)clObject, n);
        }
        
        @Override
        protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return this.getInfo((CLProgram)clObject, n, byteBuffer, pointerBuffer);
        }
        
        CLProgramUtil(final InfoUtilFactory$1 infoUtilAbstract) {
            this();
        }
    }
    
    private static final class CLPlatformUtil extends InfoUtilAbstract implements CLPlatform.CLPlatformUtil
    {
        private CLPlatformUtil() {
        }
        
        protected int getInfo(final CLPlatform clPlatform, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return CL10.clGetPlatformInfo(clPlatform, n, byteBuffer, pointerBuffer);
        }
        
        public List getPlatforms(final Filter filter) {
            final IntBuffer bufferInt = APIUtil.getBufferInt();
            CL10.clGetPlatformIDs(null, bufferInt);
            final int value = bufferInt.get(0);
            if (value == 0) {
                return null;
            }
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer(value);
            CL10.clGetPlatformIDs(bufferPointer, null);
            final ArrayList list = new ArrayList<CLPlatform>(value);
            while (0 < value) {
                final CLPlatform clPlatform = CLPlatform.getCLPlatform(bufferPointer.get(0));
                if (filter == null || filter.accept(clPlatform)) {
                    list.add(clPlatform);
                }
                int n = 0;
                ++n;
            }
            return (list.size() == 0) ? null : list;
        }
        
        public List getDevices(final CLPlatform clPlatform, final int n, final Filter filter) {
            clPlatform.checkValid();
            final IntBuffer bufferInt = APIUtil.getBufferInt();
            CL10.clGetDeviceIDs(clPlatform, n, null, bufferInt);
            final int value = bufferInt.get(0);
            if (value == 0) {
                return null;
            }
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer(value);
            CL10.clGetDeviceIDs(clPlatform, n, bufferPointer, null);
            final ArrayList list = new ArrayList<CLDevice>(value);
            while (0 < value) {
                final CLDevice clDevice = clPlatform.getCLDevice(bufferPointer.get(0));
                if (filter == null || filter.accept(clDevice)) {
                    list.add(clDevice);
                }
                int n2 = 0;
                ++n2;
            }
            return (list.size() == 0) ? null : list;
        }
        
        @Override
        protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return this.getInfo((CLPlatform)clObject, n, byteBuffer, pointerBuffer);
        }
        
        CLPlatformUtil(final InfoUtilFactory$1 infoUtilAbstract) {
            this();
        }
    }
    
    private static final class CLMemUtil extends InfoUtilAbstract implements CLMem.CLMemUtil
    {
        private CLMemUtil() {
        }
        
        protected int getInfo(final CLMem clMem, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return CL10.clGetMemObjectInfo(clMem, n, byteBuffer, pointerBuffer);
        }
        
        public CLMem createImage2D(final CLContext clContext, final long n, final CLImageFormat clImageFormat, final long n2, final long n3, final long n4, final Buffer buffer, IntBuffer bufferInt) {
            final ByteBuffer bufferByte = APIUtil.getBufferByte(8);
            bufferByte.putInt(0, clImageFormat.getChannelOrder());
            bufferByte.putInt(4, clImageFormat.getChannelType());
            final long clCreateImage2D = CLCapabilities.clCreateImage2D;
            BufferChecks.checkFunctionAddress(clCreateImage2D);
            if (bufferInt != null) {
                BufferChecks.checkBuffer(bufferInt, 1);
            }
            else if (LWJGLUtil.DEBUG) {
                bufferInt = APIUtil.getBufferInt();
            }
            final CLMem clMem = new CLMem(CL10.nclCreateImage2D(clContext.getPointer(), n, MemoryUtil.getAddress(bufferByte, 0), n2, n3, n4, MemoryUtil.getAddress0Safe(buffer) + ((buffer != null) ? BufferChecks.checkBuffer(buffer, CLChecks.calculateImage2DSize(bufferByte, n2, n3, n4)) : 0), MemoryUtil.getAddressSafe(bufferInt), clCreateImage2D), clContext);
            if (LWJGLUtil.DEBUG) {
                Util.checkCLError(bufferInt.get(0));
            }
            return clMem;
        }
        
        public CLMem createImage3D(final CLContext clContext, final long n, final CLImageFormat clImageFormat, final long n2, final long n3, final long n4, final long n5, final long n6, final Buffer buffer, IntBuffer bufferInt) {
            final ByteBuffer bufferByte = APIUtil.getBufferByte(8);
            bufferByte.putInt(0, clImageFormat.getChannelOrder());
            bufferByte.putInt(4, clImageFormat.getChannelType());
            final long clCreateImage3D = CLCapabilities.clCreateImage3D;
            BufferChecks.checkFunctionAddress(clCreateImage3D);
            if (bufferInt != null) {
                BufferChecks.checkBuffer(bufferInt, 1);
            }
            else if (LWJGLUtil.DEBUG) {
                bufferInt = APIUtil.getBufferInt();
            }
            final CLMem clMem = new CLMem(CL10.nclCreateImage3D(clContext.getPointer(), n, MemoryUtil.getAddress(bufferByte, 0), n2, n3, n4, n5, n6, MemoryUtil.getAddress0Safe(buffer) + ((buffer != null) ? BufferChecks.checkBuffer(buffer, CLChecks.calculateImage3DSize(bufferByte, n2, n3, n4, n5, n6)) : 0), MemoryUtil.getAddressSafe(bufferInt), clCreateImage3D), clContext);
            if (LWJGLUtil.DEBUG) {
                Util.checkCLError(bufferInt.get(0));
            }
            return clMem;
        }
        
        public CLMem createSubBuffer(final CLMem clMem, final long n, final int n2, final CLBufferRegion clBufferRegion, final IntBuffer intBuffer) {
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer(2);
            bufferPointer.put(clBufferRegion.getOrigin());
            bufferPointer.put(clBufferRegion.getSize());
            return CL11.clCreateSubBuffer(clMem, n, n2, bufferPointer.getBuffer(), intBuffer);
        }
        
        public ByteBuffer getInfoHostBuffer(final CLMem clMem) {
            clMem.checkValid();
            if (LWJGLUtil.DEBUG && (this.getInfoLong(clMem, 4353) & 0x8L) != 0x8L) {
                throw new IllegalArgumentException("The specified CLMem object does not use host memory.");
            }
            final long infoSize = this.getInfoSize(clMem, 4354);
            if (infoSize == 0L) {
                return null;
            }
            return CL.getHostBuffer(this.getInfoSize(clMem, 4355), (int)infoSize);
        }
        
        public long getImageInfoSize(final CLMem clMem, final int n) {
            clMem.checkValid();
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer();
            CL10.clGetImageInfo(clMem, n, bufferPointer.getBuffer(), null);
            return bufferPointer.get(0);
        }
        
        public CLImageFormat getImageInfoFormat(final CLMem clMem) {
            clMem.checkValid();
            final ByteBuffer bufferByte = APIUtil.getBufferByte(8);
            CL10.clGetImageInfo(clMem, 4368, bufferByte, null);
            return new CLImageFormat(bufferByte.getInt(0), bufferByte.getInt(4));
        }
        
        public int getImageInfoFormat(final CLMem clMem, final int n) {
            clMem.checkValid();
            final ByteBuffer bufferByte = APIUtil.getBufferByte(8);
            CL10.clGetImageInfo(clMem, 4368, bufferByte, null);
            return bufferByte.getInt(n << 2);
        }
        
        public int getGLObjectType(final CLMem clMem) {
            clMem.checkValid();
            final IntBuffer bufferInt = APIUtil.getBufferInt();
            CL10GL.clGetGLObjectInfo(clMem, bufferInt, null);
            return bufferInt.get(0);
        }
        
        public int getGLObjectName(final CLMem clMem) {
            clMem.checkValid();
            final IntBuffer bufferInt = APIUtil.getBufferInt();
            CL10GL.clGetGLObjectInfo(clMem, null, bufferInt);
            return bufferInt.get(0);
        }
        
        public int getGLTextureInfoInt(final CLMem clMem, final int n) {
            clMem.checkValid();
            final ByteBuffer bufferByte = APIUtil.getBufferByte(4);
            CL10GL.clGetGLTextureInfo(clMem, n, bufferByte, null);
            return bufferByte.getInt(0);
        }
        
        @Override
        protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return this.getInfo((CLMem)clObject, n, byteBuffer, pointerBuffer);
        }
        
        CLMemUtil(final InfoUtilFactory$1 infoUtilAbstract) {
            this();
        }
    }
    
    private static final class CLKernelUtil extends InfoUtilAbstract implements CLKernel.CLKernelUtil
    {
        private CLKernelUtil() {
        }
        
        public void setArg(final CLKernel clKernel, final int n, final byte b) {
            CL10.clSetKernelArg(clKernel, n, 1L, APIUtil.getBufferByte(1).put(0, b));
        }
        
        public void setArg(final CLKernel clKernel, final int n, final short n2) {
            CL10.clSetKernelArg(clKernel, n, 2L, APIUtil.getBufferShort().put(0, n2));
        }
        
        public void setArg(final CLKernel clKernel, final int n, final int n2) {
            CL10.clSetKernelArg(clKernel, n, 4L, APIUtil.getBufferInt().put(0, n2));
        }
        
        public void setArg(final CLKernel clKernel, final int n, final long n2) {
            CL10.clSetKernelArg(clKernel, n, 8L, APIUtil.getBufferLong().put(0, n2));
        }
        
        public void setArg(final CLKernel clKernel, final int n, final float n2) {
            CL10.clSetKernelArg(clKernel, n, 4L, APIUtil.getBufferFloat().put(0, n2));
        }
        
        public void setArg(final CLKernel clKernel, final int n, final double n2) {
            CL10.clSetKernelArg(clKernel, n, 8L, APIUtil.getBufferDouble().put(0, n2));
        }
        
        public void setArg(final CLKernel clKernel, final int n, final CLObject clObject) {
            CL10.clSetKernelArg(clKernel, n, clObject);
        }
        
        public void setArgSize(final CLKernel clKernel, final int n, final long n2) {
            CL10.clSetKernelArg(clKernel, n, n2);
        }
        
        protected int getInfo(final CLKernel clKernel, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return CL10.clGetKernelInfo(clKernel, n, byteBuffer, pointerBuffer);
        }
        
        public long getWorkGroupInfoSize(final CLKernel clKernel, final CLDevice clDevice, final int n) {
            clDevice.checkValid();
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer();
            CL10.clGetKernelWorkGroupInfo(clKernel, clDevice, n, bufferPointer.getBuffer(), null);
            return bufferPointer.get(0);
        }
        
        public long[] getWorkGroupInfoSizeArray(final CLKernel clKernel, final CLDevice clDevice, final int n) {
            clDevice.checkValid();
            switch (n) {
                case 4529: {
                    final PointerBuffer bufferPointer = APIUtil.getBufferPointer(3);
                    CL10.clGetKernelWorkGroupInfo(clKernel, clDevice, n, bufferPointer.getBuffer(), null);
                    final long[] array = new long[3];
                    while (0 < 3) {
                        array[0] = bufferPointer.get(0);
                        int n2 = 0;
                        ++n2;
                    }
                    return array;
                }
                default: {
                    throw new IllegalArgumentException("Unsupported parameter: " + LWJGLUtil.toHexString(n));
                }
            }
        }
        
        public long getWorkGroupInfoLong(final CLKernel clKernel, final CLDevice clDevice, final int n) {
            clDevice.checkValid();
            final ByteBuffer bufferByte = APIUtil.getBufferByte(8);
            CL10.clGetKernelWorkGroupInfo(clKernel, clDevice, n, bufferByte, null);
            return bufferByte.getLong(0);
        }
        
        @Override
        protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return this.getInfo((CLKernel)clObject, n, byteBuffer, pointerBuffer);
        }
        
        CLKernelUtil(final InfoUtilFactory$1 infoUtilAbstract) {
            this();
        }
    }
    
    private static final class CLEventUtil extends InfoUtilAbstract implements CLEvent.CLEventUtil
    {
        private CLEventUtil() {
        }
        
        protected int getInfo(final CLEvent clEvent, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return CL10.clGetEventInfo(clEvent, n, byteBuffer, pointerBuffer);
        }
        
        public long getProfilingInfoLong(final CLEvent clEvent, final int n) {
            clEvent.checkValid();
            final ByteBuffer bufferByte = APIUtil.getBufferByte(8);
            CL10.clGetEventProfilingInfo(clEvent, n, bufferByte, null);
            return bufferByte.getLong(0);
        }
        
        @Override
        protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return this.getInfo((CLEvent)clObject, n, byteBuffer, pointerBuffer);
        }
        
        CLEventUtil(final InfoUtilFactory$1 infoUtilAbstract) {
            this();
        }
    }
    
    private static final class CLDeviceUtil extends InfoUtilAbstract
    {
        private CLDeviceUtil() {
        }
        
        protected int getInfo(final CLDevice clDevice, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return CL10.clGetDeviceInfo(clDevice, n, byteBuffer, pointerBuffer);
        }
        
        protected int getInfoSizeArraySize(final CLDevice clDevice, final int n) {
            switch (n) {
                case 4101: {
                    return this.getInfoInt(clDevice, 4099);
                }
                default: {
                    throw new IllegalArgumentException("Unsupported parameter: " + LWJGLUtil.toHexString(n));
                }
            }
        }
        
        @Override
        protected int getInfoSizeArraySize(final CLObject clObject, final int n) {
            return this.getInfoSizeArraySize((CLDevice)clObject, n);
        }
        
        @Override
        protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return this.getInfo((CLDevice)clObject, n, byteBuffer, pointerBuffer);
        }
        
        CLDeviceUtil(final InfoUtilFactory$1 infoUtilAbstract) {
            this();
        }
    }
    
    private static final class CLContextUtil extends InfoUtilAbstract implements CLContext.CLContextUtil
    {
        private CLContextUtil() {
        }
        
        protected int getInfo(final CLContext clContext, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return CL10.clGetContextInfo(clContext, n, byteBuffer, pointerBuffer);
        }
        
        public List getInfoDevices(final CLContext clContext) {
            clContext.checkValid();
            int infoInt;
            if (CLCapabilities.getPlatformCapabilities((CLPlatform)clContext.getParent()).OpenCL11) {
                infoInt = this.getInfoInt(clContext, 4227);
            }
            else {
                final PointerBuffer bufferPointer = APIUtil.getBufferPointer();
                CL10.clGetContextInfo(clContext, 4225, null, bufferPointer);
                infoInt = (int)(bufferPointer.get(0) / PointerBuffer.getPointerSize());
            }
            final PointerBuffer bufferPointer2 = APIUtil.getBufferPointer(infoInt);
            CL10.clGetContextInfo(clContext, 4225, bufferPointer2.getBuffer(), null);
            final ArrayList list = new ArrayList<CLDevice>(infoInt);
            while (0 < infoInt) {
                list.add(((CLPlatform)clContext.getParent()).getCLDevice(bufferPointer2.get(0)));
                int n = 0;
                ++n;
            }
            return (list.size() == 0) ? null : list;
        }
        
        public CLContext create(final CLPlatform clPlatform, final List list, final CLContextCallback clContextCallback, final Drawable drawable, IntBuffer bufferInt) throws LWJGLException {
            final int n = 2 + ((drawable == null) ? 0 : 4) + 1;
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer(n + list.size());
            bufferPointer.put(4228L).put(clPlatform);
            if (drawable != null) {
                drawable.setCLSharingProperties(bufferPointer);
            }
            bufferPointer.put(0L);
            bufferPointer.position(n);
            final Iterator<CLDevice> iterator = list.iterator();
            while (iterator.hasNext()) {
                bufferPointer.put(iterator.next());
            }
            final long clCreateContext = CLCapabilities.clCreateContext;
            BufferChecks.checkFunctionAddress(clCreateContext);
            if (bufferInt != null) {
                BufferChecks.checkBuffer(bufferInt, 1);
            }
            else if (LWJGLUtil.DEBUG) {
                bufferInt = APIUtil.getBufferInt();
            }
            final long contextCallback = (clContextCallback == null || clContextCallback.isCustom()) ? 0L : CallbackUtil.createGlobalRef(clContextCallback);
            final CLContext clContext = new CLContext(CL10.nclCreateContext(MemoryUtil.getAddress0(bufferPointer.getBuffer()), list.size(), MemoryUtil.getAddress(bufferPointer, n), (clContextCallback == null) ? 0L : clContextCallback.getPointer(), contextCallback, MemoryUtil.getAddressSafe(bufferInt), clCreateContext), clPlatform);
            if (LWJGLUtil.DEBUG) {
                Util.checkCLError(bufferInt.get(0));
            }
            final CLContext clContext2;
            if ((clContext2 = clContext) != null) {
                clContext.setContextCallback(contextCallback);
            }
            return clContext2;
        }
        
        public CLContext createFromType(final CLPlatform clPlatform, final long n, final CLContextCallback clContextCallback, final Drawable drawable, final IntBuffer intBuffer) throws LWJGLException {
            final PointerBuffer bufferPointer = APIUtil.getBufferPointer(2 + ((drawable == null) ? 0 : 4) + 1);
            bufferPointer.put(4228L).put(clPlatform);
            if (drawable != null) {
                drawable.setCLSharingProperties(bufferPointer);
            }
            bufferPointer.put(0L);
            bufferPointer.flip();
            return CL10.clCreateContextFromType(bufferPointer, n, clContextCallback, intBuffer);
        }
        
        public List getSupportedImageFormats(final CLContext clContext, final long n, final int n2, final Filter filter) {
            final IntBuffer bufferInt = APIUtil.getBufferInt();
            CL10.clGetSupportedImageFormats(clContext, n, n2, null, bufferInt);
            final int value = bufferInt.get(0);
            if (value == 0) {
                return null;
            }
            final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(value * 8);
            CL10.clGetSupportedImageFormats(clContext, n, n2, byteBuffer, null);
            final ArrayList list = new ArrayList<CLImageFormat>(value);
            while (0 < value) {
                final int n3 = value * 8;
                final CLImageFormat clImageFormat = new CLImageFormat(byteBuffer.getInt(n3), byteBuffer.getInt(n3 + 4));
                if (filter == null || filter.accept(clImageFormat)) {
                    list.add(clImageFormat);
                }
                int n4 = 0;
                ++n4;
            }
            return (list.size() == 0) ? null : list;
        }
        
        @Override
        protected int getInfo(final CLObject clObject, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
            return this.getInfo((CLContext)clObject, n, byteBuffer, pointerBuffer);
        }
        
        CLContextUtil(final InfoUtilFactory$1 infoUtilAbstract) {
            this();
        }
    }
}
