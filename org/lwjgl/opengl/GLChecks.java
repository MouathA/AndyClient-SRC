package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

class GLChecks
{
    private GLChecks() {
    }
    
    static void ensureArrayVBOdisabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getReferences(contextCapabilities).arrayBuffer != 0) {
            throw new OpenGLException("Cannot use Buffers when Array Buffer Object is enabled");
        }
    }
    
    static void ensureArrayVBOenabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getReferences(contextCapabilities).arrayBuffer == 0) {
            throw new OpenGLException("Cannot use offsets when Array Buffer Object is disabled");
        }
    }
    
    static void ensureElementVBOdisabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getElementArrayBufferBound(contextCapabilities) != 0) {
            throw new OpenGLException("Cannot use Buffers when Element Array Buffer Object is enabled");
        }
    }
    
    static void ensureElementVBOenabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getElementArrayBufferBound(contextCapabilities) == 0) {
            throw new OpenGLException("Cannot use offsets when Element Array Buffer Object is disabled");
        }
    }
    
    static void ensureIndirectBOdisabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getReferences(contextCapabilities).indirectBuffer != 0) {
            throw new OpenGLException("Cannot use Buffers when Draw Indirect Object is enabled");
        }
    }
    
    static void ensureIndirectBOenabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getReferences(contextCapabilities).indirectBuffer == 0) {
            throw new OpenGLException("Cannot use offsets when Draw Indirect Object is disabled");
        }
    }
    
    static void ensurePackPBOdisabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getReferences(contextCapabilities).pixelPackBuffer != 0) {
            throw new OpenGLException("Cannot use Buffers when Pixel Pack Buffer Object is enabled");
        }
    }
    
    static void ensurePackPBOenabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getReferences(contextCapabilities).pixelPackBuffer == 0) {
            throw new OpenGLException("Cannot use offsets when Pixel Pack Buffer Object is disabled");
        }
    }
    
    static void ensureUnpackPBOdisabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getReferences(contextCapabilities).pixelUnpackBuffer != 0) {
            throw new OpenGLException("Cannot use Buffers when Pixel Unpack Buffer Object is enabled");
        }
    }
    
    static void ensureUnpackPBOenabled(final ContextCapabilities contextCapabilities) {
        if (LWJGLUtil.CHECKS && StateTracker.getReferences(contextCapabilities).pixelUnpackBuffer == 0) {
            throw new OpenGLException("Cannot use offsets when Pixel Unpack Buffer Object is disabled");
        }
    }
    
    static int calculateImageStorage(final Buffer buffer, final int n, final int n2, final int n3, final int n4, final int n5) {
        return LWJGLUtil.CHECKS ? (calculateImageStorage(n, n2, n3, n4, n5) >> BufferUtils.getElementSizeExponent(buffer)) : 0;
    }
    
    static int calculateTexImage1DStorage(final Buffer buffer, final int n, final int n2, final int n3) {
        return LWJGLUtil.CHECKS ? (calculateTexImage1DStorage(n, n2, n3) >> BufferUtils.getElementSizeExponent(buffer)) : 0;
    }
    
    static int calculateTexImage2DStorage(final Buffer buffer, final int n, final int n2, final int n3, final int n4) {
        return LWJGLUtil.CHECKS ? (calculateTexImage2DStorage(n, n2, n3, n4) >> BufferUtils.getElementSizeExponent(buffer)) : 0;
    }
    
    static int calculateTexImage3DStorage(final Buffer buffer, final int n, final int n2, final int n3, final int n4, final int n5) {
        return LWJGLUtil.CHECKS ? (calculateTexImage3DStorage(n, n2, n3, n4, n5) >> BufferUtils.getElementSizeExponent(buffer)) : 0;
    }
    
    private static int calculateImageStorage(final int n, final int n2, final int n3, final int n4, final int n5) {
        return calculateBytesPerPixel(n, n2) * n3 * n4 * n5;
    }
    
    private static int calculateTexImage1DStorage(final int n, final int n2, final int n3) {
        return calculateBytesPerPixel(n, n2) * n3;
    }
    
    private static int calculateTexImage2DStorage(final int n, final int n2, final int n3, final int n4) {
        return calculateTexImage1DStorage(n, n2, n3) * n4;
    }
    
    private static int calculateTexImage3DStorage(final int n, final int n2, final int n3, final int n4, final int n5) {
        return calculateTexImage2DStorage(n, n2, n3, n4) * n5;
    }
    
    private static int calculateBytesPerPixel(final int n, final int n2) {
        switch (n2) {
            case 5120:
            case 5121: {
                break;
            }
            case 5122:
            case 5123: {
                break;
            }
            case 5124:
            case 5125:
            case 5126: {
                break;
            }
            default: {
                return 0;
            }
        }
        switch (n) {
            case 6406:
            case 6409: {
                break;
            }
            case 6410: {
                break;
            }
            case 6407:
            case 32992: {
                break;
            }
            case 6408:
            case 32768:
            case 32993: {
                break;
            }
            default: {
                return 0;
            }
        }
        return 16;
    }
    
    static int calculateBytesPerCharCode(final int n) {
        switch (n) {
            case 5121:
            case 37018: {
                return 1;
            }
            case 5123:
            case 5127:
            case 37019: {
                return 2;
            }
            case 5128: {
                return 3;
            }
            case 5129: {
                return 4;
            }
            default: {
                throw new IllegalArgumentException("Unsupported charcode type: " + n);
            }
        }
    }
    
    static int calculateBytesPerPathName(final int n) {
        switch (n) {
            case 5120:
            case 5121:
            case 37018: {
                return 1;
            }
            case 5122:
            case 5123:
            case 5127:
            case 37019: {
                return 2;
            }
            case 5128: {
                return 3;
            }
            case 5124:
            case 5125:
            case 5126:
            case 5129: {
                return 4;
            }
            default: {
                throw new IllegalArgumentException("Unsupported path name type: " + n);
            }
        }
    }
    
    static int calculateTransformPathValues(final int n) {
        switch (n) {
            case 0: {
                return 0;
            }
            case 37006:
            case 37007: {
                return 1;
            }
            case 37008: {
                return 2;
            }
            case 37009: {
                return 3;
            }
            case 37010:
            case 37014: {
                return 6;
            }
            case 37012:
            case 37016: {
                return 12;
            }
            default: {
                throw new IllegalArgumentException("Unsupported transform type: " + n);
            }
        }
    }
    
    static int calculatePathColorGenCoeffsCount(final int n, final int n2) {
        final int calculatePathGenCoeffsPerComponent = calculatePathGenCoeffsPerComponent(n);
        switch (n2) {
            case 6407: {
                return 3 * calculatePathGenCoeffsPerComponent;
            }
            case 6408: {
                return 4 * calculatePathGenCoeffsPerComponent;
            }
            default: {
                return calculatePathGenCoeffsPerComponent;
            }
        }
    }
    
    static int calculatePathTextGenCoeffsPerComponent(final FloatBuffer floatBuffer, final int n) {
        if (n == 0) {
            return 0;
        }
        return floatBuffer.remaining() / calculatePathGenCoeffsPerComponent(n);
    }
    
    private static int calculatePathGenCoeffsPerComponent(final int n) {
        switch (n) {
            case 0: {
                return 0;
            }
            case 9217:
            case 37002: {
                return 3;
            }
            case 9216: {
                return 4;
            }
            default: {
                throw new IllegalArgumentException("Unsupported gen mode: " + n);
            }
        }
    }
    
    static int calculateMetricsSize(final int n, final int n2) {
        if (LWJGLUtil.DEBUG && (n2 < 0 || n2 % 4 != 0)) {
            throw new IllegalArgumentException("Invalid stride value: " + n2);
        }
        final int bitCount = Integer.bitCount(n);
        if (LWJGLUtil.DEBUG && n2 >> 2 < bitCount) {
            throw new IllegalArgumentException("The queried metrics do not fit in the specified stride: " + n2);
        }
        return (n2 == 0) ? bitCount : (n2 >> 2);
    }
}
