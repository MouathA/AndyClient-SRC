package org.lwjgl.util.glu;

import java.nio.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.tessellation.*;

public class GLU
{
    static final float PI = 3.1415927f;
    public static final int GLU_INVALID_ENUM = 100900;
    public static final int GLU_INVALID_VALUE = 100901;
    public static final int GLU_OUT_OF_MEMORY = 100902;
    public static final int GLU_INCOMPATIBLE_GL_VERSION = 100903;
    public static final int GLU_VERSION = 100800;
    public static final int GLU_EXTENSIONS = 100801;
    public static final boolean GLU_TRUE = true;
    public static final boolean GLU_FALSE = false;
    public static final int GLU_SMOOTH = 100000;
    public static final int GLU_FLAT = 100001;
    public static final int GLU_NONE = 100002;
    public static final int GLU_POINT = 100010;
    public static final int GLU_LINE = 100011;
    public static final int GLU_FILL = 100012;
    public static final int GLU_SILHOUETTE = 100013;
    public static final int GLU_OUTSIDE = 100020;
    public static final int GLU_INSIDE = 100021;
    public static final double GLU_TESS_MAX_COORD = 1.0E150;
    public static final double TESS_MAX_COORD = 1.0E150;
    public static final int GLU_TESS_WINDING_RULE = 100140;
    public static final int GLU_TESS_BOUNDARY_ONLY = 100141;
    public static final int GLU_TESS_TOLERANCE = 100142;
    public static final int GLU_TESS_WINDING_ODD = 100130;
    public static final int GLU_TESS_WINDING_NONZERO = 100131;
    public static final int GLU_TESS_WINDING_POSITIVE = 100132;
    public static final int GLU_TESS_WINDING_NEGATIVE = 100133;
    public static final int GLU_TESS_WINDING_ABS_GEQ_TWO = 100134;
    public static final int GLU_TESS_BEGIN = 100100;
    public static final int GLU_TESS_VERTEX = 100101;
    public static final int GLU_TESS_END = 100102;
    public static final int GLU_TESS_ERROR = 100103;
    public static final int GLU_TESS_EDGE_FLAG = 100104;
    public static final int GLU_TESS_COMBINE = 100105;
    public static final int GLU_TESS_BEGIN_DATA = 100106;
    public static final int GLU_TESS_VERTEX_DATA = 100107;
    public static final int GLU_TESS_END_DATA = 100108;
    public static final int GLU_TESS_ERROR_DATA = 100109;
    public static final int GLU_TESS_EDGE_FLAG_DATA = 100110;
    public static final int GLU_TESS_COMBINE_DATA = 100111;
    public static final int GLU_TESS_ERROR1 = 100151;
    public static final int GLU_TESS_ERROR2 = 100152;
    public static final int GLU_TESS_ERROR3 = 100153;
    public static final int GLU_TESS_ERROR4 = 100154;
    public static final int GLU_TESS_ERROR5 = 100155;
    public static final int GLU_TESS_ERROR6 = 100156;
    public static final int GLU_TESS_ERROR7 = 100157;
    public static final int GLU_TESS_ERROR8 = 100158;
    public static final int GLU_TESS_MISSING_BEGIN_POLYGON = 100151;
    public static final int GLU_TESS_MISSING_BEGIN_CONTOUR = 100152;
    public static final int GLU_TESS_MISSING_END_POLYGON = 100153;
    public static final int GLU_TESS_MISSING_END_CONTOUR = 100154;
    public static final int GLU_TESS_COORD_TOO_LARGE = 100155;
    public static final int GLU_TESS_NEED_COMBINE_CALLBACK = 100156;
    public static final int GLU_AUTO_LOAD_MATRIX = 100200;
    public static final int GLU_CULLING = 100201;
    public static final int GLU_SAMPLING_TOLERANCE = 100203;
    public static final int GLU_DISPLAY_MODE = 100204;
    public static final int GLU_PARAMETRIC_TOLERANCE = 100202;
    public static final int GLU_SAMPLING_METHOD = 100205;
    public static final int GLU_U_STEP = 100206;
    public static final int GLU_V_STEP = 100207;
    public static final int GLU_PATH_LENGTH = 100215;
    public static final int GLU_PARAMETRIC_ERROR = 100216;
    public static final int GLU_DOMAIN_DISTANCE = 100217;
    public static final int GLU_MAP1_TRIM_2 = 100210;
    public static final int GLU_MAP1_TRIM_3 = 100211;
    public static final int GLU_OUTLINE_POLYGON = 100240;
    public static final int GLU_OUTLINE_PATCH = 100241;
    public static final int GLU_NURBS_ERROR1 = 100251;
    public static final int GLU_NURBS_ERROR2 = 100252;
    public static final int GLU_NURBS_ERROR3 = 100253;
    public static final int GLU_NURBS_ERROR4 = 100254;
    public static final int GLU_NURBS_ERROR5 = 100255;
    public static final int GLU_NURBS_ERROR6 = 100256;
    public static final int GLU_NURBS_ERROR7 = 100257;
    public static final int GLU_NURBS_ERROR8 = 100258;
    public static final int GLU_NURBS_ERROR9 = 100259;
    public static final int GLU_NURBS_ERROR10 = 100260;
    public static final int GLU_NURBS_ERROR11 = 100261;
    public static final int GLU_NURBS_ERROR12 = 100262;
    public static final int GLU_NURBS_ERROR13 = 100263;
    public static final int GLU_NURBS_ERROR14 = 100264;
    public static final int GLU_NURBS_ERROR15 = 100265;
    public static final int GLU_NURBS_ERROR16 = 100266;
    public static final int GLU_NURBS_ERROR17 = 100267;
    public static final int GLU_NURBS_ERROR18 = 100268;
    public static final int GLU_NURBS_ERROR19 = 100269;
    public static final int GLU_NURBS_ERROR20 = 100270;
    public static final int GLU_NURBS_ERROR21 = 100271;
    public static final int GLU_NURBS_ERROR22 = 100272;
    public static final int GLU_NURBS_ERROR23 = 100273;
    public static final int GLU_NURBS_ERROR24 = 100274;
    public static final int GLU_NURBS_ERROR25 = 100275;
    public static final int GLU_NURBS_ERROR26 = 100276;
    public static final int GLU_NURBS_ERROR27 = 100277;
    public static final int GLU_NURBS_ERROR28 = 100278;
    public static final int GLU_NURBS_ERROR29 = 100279;
    public static final int GLU_NURBS_ERROR30 = 100280;
    public static final int GLU_NURBS_ERROR31 = 100281;
    public static final int GLU_NURBS_ERROR32 = 100282;
    public static final int GLU_NURBS_ERROR33 = 100283;
    public static final int GLU_NURBS_ERROR34 = 100284;
    public static final int GLU_NURBS_ERROR35 = 100285;
    public static final int GLU_NURBS_ERROR36 = 100286;
    public static final int GLU_NURBS_ERROR37 = 100287;
    public static final int GLU_CW = 100120;
    public static final int GLU_CCW = 100121;
    public static final int GLU_INTERIOR = 100122;
    public static final int GLU_EXTERIOR = 100123;
    public static final int GLU_UNKNOWN = 100124;
    public static final int GLU_BEGIN = 100100;
    public static final int GLU_VERTEX = 100101;
    public static final int GLU_END = 100102;
    public static final int GLU_ERROR = 100103;
    public static final int GLU_EDGE_FLAG = 100104;
    
    public static void gluLookAt(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        Project.gluLookAt(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    public static void gluOrtho2D(final float n, final float n2, final float n3, final float n4) {
        GL11.glOrtho(n, n2, n3, n4, -1.0, 1.0);
    }
    
    public static void gluPerspective(final float n, final float n2, final float n3, final float n4) {
        Project.gluPerspective(n, n2, n3, n4);
    }
    
    public static boolean gluProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final FloatBuffer floatBuffer3) {
        return Project.gluProject(n, n2, n3, floatBuffer, floatBuffer2, intBuffer, floatBuffer3);
    }
    
    public static boolean gluUnProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final FloatBuffer floatBuffer3) {
        return Project.gluUnProject(n, n2, n3, floatBuffer, floatBuffer2, intBuffer, floatBuffer3);
    }
    
    public static void gluPickMatrix(final float n, final float n2, final float n3, final float n4, final IntBuffer intBuffer) {
        Project.gluPickMatrix(n, n2, n3, n4, intBuffer);
    }
    
    public static String gluGetString(final int n) {
        return Registry.gluGetString(n);
    }
    
    public static boolean gluCheckExtension(final String s, final String s2) {
        return Registry.gluCheckExtension(s, s2);
    }
    
    public static int gluBuild2DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        return MipMap.gluBuild2DMipmaps(n, n2, n3, n4, n5, n6, byteBuffer);
    }
    
    public static int gluScaleImage(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer2) {
        return MipMap.gluScaleImage(n, n2, n3, n4, byteBuffer, n5, n6, n7, byteBuffer2);
    }
    
    public static String gluErrorString(final int n) {
        switch (n) {
            case 100900: {
                return "Invalid enum (glu)";
            }
            case 100901: {
                return "Invalid value (glu)";
            }
            case 100902: {
                return "Out of memory (glu)";
            }
            default: {
                return Util.translateGLErrorString(n);
            }
        }
    }
    
    public static GLUtessellator gluNewTess() {
        return new GLUtessellatorImpl();
    }
}
