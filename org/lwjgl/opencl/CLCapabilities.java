package org.lwjgl.opencl;

public final class CLCapabilities
{
    static final boolean CL_APPLE_ContextLoggingFunctions;
    static final long clLogMessagesToSystemLogAPPLE;
    static final long clLogMessagesToStdoutAPPLE;
    static final long clLogMessagesToStderrAPPLE;
    static final boolean CL_APPLE_SetMemObjectDestructor;
    static final long clSetMemObjectDestructorAPPLE;
    static final boolean CL_APPLE_gl_sharing;
    static final long clGetGLContextInfoAPPLE;
    static final boolean OpenCL10;
    static final long clGetPlatformIDs;
    static final long clGetPlatformInfo;
    static final long clGetDeviceIDs;
    static final long clGetDeviceInfo;
    static final long clCreateContext;
    static final long clCreateContextFromType;
    static final long clRetainContext;
    static final long clReleaseContext;
    static final long clGetContextInfo;
    static final long clCreateCommandQueue;
    static final long clRetainCommandQueue;
    static final long clReleaseCommandQueue;
    static final long clGetCommandQueueInfo;
    static final long clCreateBuffer;
    static final long clEnqueueReadBuffer;
    static final long clEnqueueWriteBuffer;
    static final long clEnqueueCopyBuffer;
    static final long clEnqueueMapBuffer;
    static final long clCreateImage2D;
    static final long clCreateImage3D;
    static final long clGetSupportedImageFormats;
    static final long clEnqueueReadImage;
    static final long clEnqueueWriteImage;
    static final long clEnqueueCopyImage;
    static final long clEnqueueCopyImageToBuffer;
    static final long clEnqueueCopyBufferToImage;
    static final long clEnqueueMapImage;
    static final long clGetImageInfo;
    static final long clRetainMemObject;
    static final long clReleaseMemObject;
    static final long clEnqueueUnmapMemObject;
    static final long clGetMemObjectInfo;
    static final long clCreateSampler;
    static final long clRetainSampler;
    static final long clReleaseSampler;
    static final long clGetSamplerInfo;
    static final long clCreateProgramWithSource;
    static final long clCreateProgramWithBinary;
    static final long clRetainProgram;
    static final long clReleaseProgram;
    static final long clBuildProgram;
    static final long clUnloadCompiler;
    static final long clGetProgramInfo;
    static final long clGetProgramBuildInfo;
    static final long clCreateKernel;
    static final long clCreateKernelsInProgram;
    static final long clRetainKernel;
    static final long clReleaseKernel;
    static final long clSetKernelArg;
    static final long clGetKernelInfo;
    static final long clGetKernelWorkGroupInfo;
    static final long clEnqueueNDRangeKernel;
    static final long clEnqueueTask;
    static final long clEnqueueNativeKernel;
    static final long clWaitForEvents;
    static final long clGetEventInfo;
    static final long clRetainEvent;
    static final long clReleaseEvent;
    static final long clEnqueueMarker;
    static final long clEnqueueBarrier;
    static final long clEnqueueWaitForEvents;
    static final long clGetEventProfilingInfo;
    static final long clFlush;
    static final long clFinish;
    static final long clGetExtensionFunctionAddress;
    static final boolean OpenCL10GL;
    static final long clCreateFromGLBuffer;
    static final long clCreateFromGLTexture2D;
    static final long clCreateFromGLTexture3D;
    static final long clCreateFromGLRenderbuffer;
    static final long clGetGLObjectInfo;
    static final long clGetGLTextureInfo;
    static final long clEnqueueAcquireGLObjects;
    static final long clEnqueueReleaseGLObjects;
    static final boolean OpenCL11;
    static final long clCreateSubBuffer;
    static final long clSetMemObjectDestructorCallback;
    static final long clEnqueueReadBufferRect;
    static final long clEnqueueWriteBufferRect;
    static final long clEnqueueCopyBufferRect;
    static final long clCreateUserEvent;
    static final long clSetUserEventStatus;
    static final long clSetEventCallback;
    static final boolean OpenCL12;
    static final long clRetainDevice;
    static final long clReleaseDevice;
    static final long clCreateSubDevices;
    static final long clCreateImage;
    static final long clCreateProgramWithBuiltInKernels;
    static final long clCompileProgram;
    static final long clLinkProgram;
    static final long clUnloadPlatformCompiler;
    static final long clGetKernelArgInfo;
    static final long clEnqueueFillBuffer;
    static final long clEnqueueFillImage;
    static final long clEnqueueMigrateMemObjects;
    static final long clEnqueueMarkerWithWaitList;
    static final long clEnqueueBarrierWithWaitList;
    static final long clSetPrintfCallback;
    static final long clGetExtensionFunctionAddressForPlatform;
    static final boolean OpenCL12GL;
    static final long clCreateFromGLTexture;
    static final boolean CL_EXT_device_fission;
    static final long clRetainDeviceEXT;
    static final long clReleaseDeviceEXT;
    static final long clCreateSubDevicesEXT;
    static final boolean CL_EXT_migrate_memobject;
    static final long clEnqueueMigrateMemObjectEXT;
    static final boolean CL_KHR_gl_event;
    static final long clCreateEventFromGLsyncKHR;
    static final boolean CL_KHR_gl_sharing;
    static final long clGetGLContextInfoKHR;
    static final boolean CL_KHR_icd;
    static final long clIcdGetPlatformIDsKHR;
    static final boolean CL_KHR_subgroups;
    static final long clGetKernelSubGroupInfoKHR;
    static final boolean CL_KHR_terminate_context;
    static final long clTerminateContextKHR;
    
    private CLCapabilities() {
    }
    
    public static CLPlatformCapabilities getPlatformCapabilities(final CLPlatform clPlatform) {
        clPlatform.checkValid();
        CLPlatformCapabilities clPlatformCapabilities = (CLPlatformCapabilities)clPlatform.getCapabilities();
        if (clPlatformCapabilities == null) {
            clPlatform.setCapabilities(clPlatformCapabilities = new CLPlatformCapabilities(clPlatform));
        }
        return clPlatformCapabilities;
    }
    
    public static CLDeviceCapabilities getDeviceCapabilities(final CLDevice clDevice) {
        clDevice.checkValid();
        CLDeviceCapabilities clDeviceCapabilities = (CLDeviceCapabilities)clDevice.getCapabilities();
        if (clDeviceCapabilities == null) {
            clDevice.setCapabilities(clDeviceCapabilities = new CLDeviceCapabilities(clDevice));
        }
        return clDeviceCapabilities;
    }
    
    private static boolean isAPPLE_ContextLoggingFunctionsSupported() {
        return false & false & false;
    }
    
    private static boolean isAPPLE_SetMemObjectDestructorSupported() {
        return false;
    }
    
    private static boolean isAPPLE_gl_sharingSupported() {
        return false;
    }
    
    private static boolean isCL10Supported() {
        return false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false & false;
    }
    
    private static boolean isCL10GLSupported() {
        return false & false & false & false & false & false & false & false;
    }
    
    private static boolean isCL11Supported() {
        return false & false & false & false & false & false & false & false;
    }
    
    private static boolean isCL12Supported() {
        false & false & false & false & false & false & false & false & false & false & false & false & false & false;
        return false & true & false;
    }
    
    private static boolean isCL12GLSupported() {
        return false;
    }
    
    private static boolean isEXT_device_fissionSupported() {
        return false & false & false;
    }
    
    private static boolean isEXT_migrate_memobjectSupported() {
        return false;
    }
    
    private static boolean isKHR_gl_eventSupported() {
        return false;
    }
    
    private static boolean isKHR_gl_sharingSupported() {
        return false;
    }
    
    private static boolean isKHR_icdSupported() {
        false;
        return true;
    }
    
    private static boolean isKHR_subgroupsSupported() {
        return false;
    }
    
    private static boolean isKHR_terminate_contextSupported() {
        return false;
    }
    
    static {
        clLogMessagesToSystemLogAPPLE = CL.getFunctionAddress("clLogMessagesToSystemLogAPPLE");
        clLogMessagesToStdoutAPPLE = CL.getFunctionAddress("clLogMessagesToStdoutAPPLE");
        clLogMessagesToStderrAPPLE = CL.getFunctionAddress("clLogMessagesToStderrAPPLE");
        clSetMemObjectDestructorAPPLE = CL.getFunctionAddress("clSetMemObjectDestructorAPPLE");
        clGetGLContextInfoAPPLE = CL.getFunctionAddress("clGetGLContextInfoAPPLE");
        clGetPlatformIDs = CL.getFunctionAddress("clGetPlatformIDs");
        clGetPlatformInfo = CL.getFunctionAddress("clGetPlatformInfo");
        clGetDeviceIDs = CL.getFunctionAddress("clGetDeviceIDs");
        clGetDeviceInfo = CL.getFunctionAddress("clGetDeviceInfo");
        clCreateContext = CL.getFunctionAddress("clCreateContext");
        clCreateContextFromType = CL.getFunctionAddress("clCreateContextFromType");
        clRetainContext = CL.getFunctionAddress("clRetainContext");
        clReleaseContext = CL.getFunctionAddress("clReleaseContext");
        clGetContextInfo = CL.getFunctionAddress("clGetContextInfo");
        clCreateCommandQueue = CL.getFunctionAddress("clCreateCommandQueue");
        clRetainCommandQueue = CL.getFunctionAddress("clRetainCommandQueue");
        clReleaseCommandQueue = CL.getFunctionAddress("clReleaseCommandQueue");
        clGetCommandQueueInfo = CL.getFunctionAddress("clGetCommandQueueInfo");
        clCreateBuffer = CL.getFunctionAddress("clCreateBuffer");
        clEnqueueReadBuffer = CL.getFunctionAddress("clEnqueueReadBuffer");
        clEnqueueWriteBuffer = CL.getFunctionAddress("clEnqueueWriteBuffer");
        clEnqueueCopyBuffer = CL.getFunctionAddress("clEnqueueCopyBuffer");
        clEnqueueMapBuffer = CL.getFunctionAddress("clEnqueueMapBuffer");
        clCreateImage2D = CL.getFunctionAddress("clCreateImage2D");
        clCreateImage3D = CL.getFunctionAddress("clCreateImage3D");
        clGetSupportedImageFormats = CL.getFunctionAddress("clGetSupportedImageFormats");
        clEnqueueReadImage = CL.getFunctionAddress("clEnqueueReadImage");
        clEnqueueWriteImage = CL.getFunctionAddress("clEnqueueWriteImage");
        clEnqueueCopyImage = CL.getFunctionAddress("clEnqueueCopyImage");
        clEnqueueCopyImageToBuffer = CL.getFunctionAddress("clEnqueueCopyImageToBuffer");
        clEnqueueCopyBufferToImage = CL.getFunctionAddress("clEnqueueCopyBufferToImage");
        clEnqueueMapImage = CL.getFunctionAddress("clEnqueueMapImage");
        clGetImageInfo = CL.getFunctionAddress("clGetImageInfo");
        clRetainMemObject = CL.getFunctionAddress("clRetainMemObject");
        clReleaseMemObject = CL.getFunctionAddress("clReleaseMemObject");
        clEnqueueUnmapMemObject = CL.getFunctionAddress("clEnqueueUnmapMemObject");
        clGetMemObjectInfo = CL.getFunctionAddress("clGetMemObjectInfo");
        clCreateSampler = CL.getFunctionAddress("clCreateSampler");
        clRetainSampler = CL.getFunctionAddress("clRetainSampler");
        clReleaseSampler = CL.getFunctionAddress("clReleaseSampler");
        clGetSamplerInfo = CL.getFunctionAddress("clGetSamplerInfo");
        clCreateProgramWithSource = CL.getFunctionAddress("clCreateProgramWithSource");
        clCreateProgramWithBinary = CL.getFunctionAddress("clCreateProgramWithBinary");
        clRetainProgram = CL.getFunctionAddress("clRetainProgram");
        clReleaseProgram = CL.getFunctionAddress("clReleaseProgram");
        clBuildProgram = CL.getFunctionAddress("clBuildProgram");
        clUnloadCompiler = CL.getFunctionAddress("clUnloadCompiler");
        clGetProgramInfo = CL.getFunctionAddress("clGetProgramInfo");
        clGetProgramBuildInfo = CL.getFunctionAddress("clGetProgramBuildInfo");
        clCreateKernel = CL.getFunctionAddress("clCreateKernel");
        clCreateKernelsInProgram = CL.getFunctionAddress("clCreateKernelsInProgram");
        clRetainKernel = CL.getFunctionAddress("clRetainKernel");
        clReleaseKernel = CL.getFunctionAddress("clReleaseKernel");
        clSetKernelArg = CL.getFunctionAddress("clSetKernelArg");
        clGetKernelInfo = CL.getFunctionAddress("clGetKernelInfo");
        clGetKernelWorkGroupInfo = CL.getFunctionAddress("clGetKernelWorkGroupInfo");
        clEnqueueNDRangeKernel = CL.getFunctionAddress("clEnqueueNDRangeKernel");
        clEnqueueTask = CL.getFunctionAddress("clEnqueueTask");
        clEnqueueNativeKernel = CL.getFunctionAddress("clEnqueueNativeKernel");
        clWaitForEvents = CL.getFunctionAddress("clWaitForEvents");
        clGetEventInfo = CL.getFunctionAddress("clGetEventInfo");
        clRetainEvent = CL.getFunctionAddress("clRetainEvent");
        clReleaseEvent = CL.getFunctionAddress("clReleaseEvent");
        clEnqueueMarker = CL.getFunctionAddress("clEnqueueMarker");
        clEnqueueBarrier = CL.getFunctionAddress("clEnqueueBarrier");
        clEnqueueWaitForEvents = CL.getFunctionAddress("clEnqueueWaitForEvents");
        clGetEventProfilingInfo = CL.getFunctionAddress("clGetEventProfilingInfo");
        clFlush = CL.getFunctionAddress("clFlush");
        clFinish = CL.getFunctionAddress("clFinish");
        clGetExtensionFunctionAddress = CL.getFunctionAddress("clGetExtensionFunctionAddress");
        clCreateFromGLBuffer = CL.getFunctionAddress("clCreateFromGLBuffer");
        clCreateFromGLTexture2D = CL.getFunctionAddress("clCreateFromGLTexture2D");
        clCreateFromGLTexture3D = CL.getFunctionAddress("clCreateFromGLTexture3D");
        clCreateFromGLRenderbuffer = CL.getFunctionAddress("clCreateFromGLRenderbuffer");
        clGetGLObjectInfo = CL.getFunctionAddress("clGetGLObjectInfo");
        clGetGLTextureInfo = CL.getFunctionAddress("clGetGLTextureInfo");
        clEnqueueAcquireGLObjects = CL.getFunctionAddress("clEnqueueAcquireGLObjects");
        clEnqueueReleaseGLObjects = CL.getFunctionAddress("clEnqueueReleaseGLObjects");
        clCreateSubBuffer = CL.getFunctionAddress("clCreateSubBuffer");
        clSetMemObjectDestructorCallback = CL.getFunctionAddress("clSetMemObjectDestructorCallback");
        clEnqueueReadBufferRect = CL.getFunctionAddress("clEnqueueReadBufferRect");
        clEnqueueWriteBufferRect = CL.getFunctionAddress("clEnqueueWriteBufferRect");
        clEnqueueCopyBufferRect = CL.getFunctionAddress("clEnqueueCopyBufferRect");
        clCreateUserEvent = CL.getFunctionAddress("clCreateUserEvent");
        clSetUserEventStatus = CL.getFunctionAddress("clSetUserEventStatus");
        clSetEventCallback = CL.getFunctionAddress("clSetEventCallback");
        clRetainDevice = CL.getFunctionAddress("clRetainDevice");
        clReleaseDevice = CL.getFunctionAddress("clReleaseDevice");
        clCreateSubDevices = CL.getFunctionAddress("clCreateSubDevices");
        clCreateImage = CL.getFunctionAddress("clCreateImage");
        clCreateProgramWithBuiltInKernels = CL.getFunctionAddress("clCreateProgramWithBuiltInKernels");
        clCompileProgram = CL.getFunctionAddress("clCompileProgram");
        clLinkProgram = CL.getFunctionAddress("clLinkProgram");
        clUnloadPlatformCompiler = CL.getFunctionAddress("clUnloadPlatformCompiler");
        clGetKernelArgInfo = CL.getFunctionAddress("clGetKernelArgInfo");
        clEnqueueFillBuffer = CL.getFunctionAddress("clEnqueueFillBuffer");
        clEnqueueFillImage = CL.getFunctionAddress("clEnqueueFillImage");
        clEnqueueMigrateMemObjects = CL.getFunctionAddress("clEnqueueMigrateMemObjects");
        clEnqueueMarkerWithWaitList = CL.getFunctionAddress("clEnqueueMarkerWithWaitList");
        clEnqueueBarrierWithWaitList = CL.getFunctionAddress("clEnqueueBarrierWithWaitList");
        clSetPrintfCallback = CL.getFunctionAddress("clSetPrintfCallback");
        clGetExtensionFunctionAddressForPlatform = CL.getFunctionAddress("clGetExtensionFunctionAddressForPlatform");
        clCreateFromGLTexture = CL.getFunctionAddress("clCreateFromGLTexture");
        clRetainDeviceEXT = CL.getFunctionAddress("clRetainDeviceEXT");
        clReleaseDeviceEXT = CL.getFunctionAddress("clReleaseDeviceEXT");
        clCreateSubDevicesEXT = CL.getFunctionAddress("clCreateSubDevicesEXT");
        clEnqueueMigrateMemObjectEXT = CL.getFunctionAddress("clEnqueueMigrateMemObjectEXT");
        clCreateEventFromGLsyncKHR = CL.getFunctionAddress("clCreateEventFromGLsyncKHR");
        clGetGLContextInfoKHR = CL.getFunctionAddress("clGetGLContextInfoKHR");
        clIcdGetPlatformIDsKHR = CL.getFunctionAddress("clIcdGetPlatformIDsKHR");
        clGetKernelSubGroupInfoKHR = CL.getFunctionAddress("clGetKernelSubGroupInfoKHR");
        clTerminateContextKHR = CL.getFunctionAddress("clTerminateContextKHR");
        CL_APPLE_ContextLoggingFunctions = isAPPLE_ContextLoggingFunctionsSupported();
        CL_APPLE_SetMemObjectDestructor = isAPPLE_SetMemObjectDestructorSupported();
        CL_APPLE_gl_sharing = isAPPLE_gl_sharingSupported();
        OpenCL10 = isCL10Supported();
        OpenCL10GL = isCL10GLSupported();
        OpenCL11 = isCL11Supported();
        OpenCL12 = isCL12Supported();
        OpenCL12GL = isCL12GLSupported();
        CL_EXT_device_fission = isEXT_device_fissionSupported();
        CL_EXT_migrate_memobject = isEXT_migrate_memobjectSupported();
        CL_KHR_gl_event = isKHR_gl_eventSupported();
        CL_KHR_gl_sharing = isKHR_gl_sharingSupported();
        CL_KHR_icd = isKHR_icdSupported();
        CL_KHR_subgroups = isKHR_subgroupsSupported();
        CL_KHR_terminate_context = isKHR_terminate_contextSupported();
    }
}
