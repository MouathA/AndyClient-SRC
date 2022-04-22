package org.lwjgl.opencl;

import java.util.*;

public class CLPlatformCapabilities
{
    public final int majorVersion;
    public final int minorVersion;
    public final boolean OpenCL11;
    public final boolean OpenCL12;
    final boolean CL_APPLE_ContextLoggingFunctions;
    public final boolean CL_APPLE_SetMemObjectDestructor;
    public final boolean CL_APPLE_gl_sharing;
    public final boolean CL_KHR_d3d10_sharing;
    public final boolean CL_KHR_gl_event;
    public final boolean CL_KHR_gl_sharing;
    public final boolean CL_KHR_icd;
    
    public CLPlatformCapabilities(final CLPlatform clPlatform) {
        final String infoString = clPlatform.getInfoString(2308);
        final String infoString2 = clPlatform.getInfoString(2305);
        if (!infoString2.startsWith("OpenCL ")) {
            throw new RuntimeException("Invalid OpenCL version string: " + infoString2);
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(infoString2.substring(7), ". ");
        this.majorVersion = Integer.parseInt(stringTokenizer.nextToken());
        this.minorVersion = Integer.parseInt(stringTokenizer.nextToken());
        this.OpenCL11 = (1 < this.majorVersion || (1 == this.majorVersion && 1 <= this.minorVersion));
        this.OpenCL12 = (1 < this.majorVersion || (1 == this.majorVersion && 2 <= this.minorVersion));
        final Set extensions = APIUtil.getExtensions(infoString);
        this.CL_APPLE_ContextLoggingFunctions = (extensions.contains("cl_APPLE_ContextLoggingFunctions") && CLCapabilities.CL_APPLE_ContextLoggingFunctions);
        this.CL_APPLE_SetMemObjectDestructor = (extensions.contains("cl_APPLE_SetMemObjectDestructor") && CLCapabilities.CL_APPLE_SetMemObjectDestructor);
        this.CL_APPLE_gl_sharing = (extensions.contains("cl_APPLE_gl_sharing") && CLCapabilities.CL_APPLE_gl_sharing);
        this.CL_KHR_d3d10_sharing = extensions.contains("cl_khr_d3d10_sharing");
        this.CL_KHR_gl_event = (extensions.contains("cl_khr_gl_event") && CLCapabilities.CL_KHR_gl_event);
        this.CL_KHR_gl_sharing = (extensions.contains("cl_khr_gl_sharing") && CLCapabilities.CL_KHR_gl_sharing);
        this.CL_KHR_icd = (extensions.contains("cl_khr_icd") && CLCapabilities.CL_KHR_icd);
    }
    
    public int getMajorVersion() {
        return this.majorVersion;
    }
    
    public int getMinorVersion() {
        return this.minorVersion;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OpenCL ").append(this.majorVersion).append('.').append(this.minorVersion);
        sb.append(" - Extensions: ");
        if (this.CL_APPLE_ContextLoggingFunctions) {
            sb.append("cl_apple_contextloggingfunctions ");
        }
        if (this.CL_APPLE_SetMemObjectDestructor) {
            sb.append("cl_apple_setmemobjectdestructor ");
        }
        if (this.CL_APPLE_gl_sharing) {
            sb.append("cl_apple_gl_sharing ");
        }
        if (this.CL_KHR_d3d10_sharing) {
            sb.append("cl_khr_d3d10_sharing ");
        }
        if (this.CL_KHR_gl_event) {
            sb.append("cl_khr_gl_event ");
        }
        if (this.CL_KHR_gl_sharing) {
            sb.append("cl_khr_gl_sharing ");
        }
        if (this.CL_KHR_icd) {
            sb.append("cl_khr_icd ");
        }
        return sb.toString();
    }
}
