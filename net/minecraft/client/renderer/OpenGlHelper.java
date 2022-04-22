package net.minecraft.client.renderer;

import java.nio.*;
import optifine.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class OpenGlHelper
{
    public static boolean field_153197_d;
    public static int field_153198_e;
    public static int field_153199_f;
    public static int field_153200_g;
    public static int field_153201_h;
    public static int field_153202_i;
    public static int field_153203_j;
    public static int field_153204_k;
    public static int field_153205_l;
    public static int field_153206_m;
    private static int field_153212_w;
    public static boolean framebufferSupported;
    private static boolean field_153213_x;
    private static boolean field_153214_y;
    public static int GL_LINK_STATUS;
    public static int GL_COMPILE_STATUS;
    public static int GL_VERTEX_SHADER;
    public static int GL_FRAGMENT_SHADER;
    private static boolean field_153215_z;
    public static int defaultTexUnit;
    public static int lightmapTexUnit;
    public static int field_176096_r;
    private static boolean field_176088_V;
    public static int field_176095_s;
    public static int field_176094_t;
    public static int field_176093_u;
    public static int field_176092_v;
    public static int field_176091_w;
    public static int field_176099_x;
    public static int field_176098_y;
    public static int field_176097_z;
    public static int field_176080_A;
    public static int field_176081_B;
    public static int field_176082_C;
    public static int field_176076_D;
    public static int field_176077_E;
    public static int field_176078_F;
    public static int field_176079_G;
    public static int field_176084_H;
    public static int field_176085_I;
    public static int field_176086_J;
    public static int field_176087_K;
    private static boolean openGL14;
    public static boolean field_153211_u;
    public static boolean openGL21;
    public static boolean shadersSupported;
    private static String field_153196_B;
    public static boolean field_176083_O;
    private static boolean field_176090_Y;
    public static int field_176089_P;
    public static int anisotropicFilteringMax;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001179";
        OpenGlHelper.field_153196_B = "";
    }
    
    public static void initializeTextures() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        OpenGlHelper.field_153215_z = (capabilities.GL_ARB_multitexture && !capabilities.OpenGL13);
        OpenGlHelper.field_176088_V = (capabilities.GL_ARB_texture_env_combine && !capabilities.OpenGL13);
        if (OpenGlHelper.field_153215_z) {
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "Using ARB_multitexture.\n";
            OpenGlHelper.defaultTexUnit = 33984;
            OpenGlHelper.lightmapTexUnit = 33985;
            OpenGlHelper.field_176096_r = 33986;
        }
        else {
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "Using GL 1.3 multitexturing.\n";
            OpenGlHelper.defaultTexUnit = 33984;
            OpenGlHelper.lightmapTexUnit = 33985;
            OpenGlHelper.field_176096_r = 33986;
        }
        if (OpenGlHelper.field_176088_V) {
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "Using ARB_texture_env_combine.\n";
            OpenGlHelper.field_176095_s = 34160;
            OpenGlHelper.field_176094_t = 34165;
            OpenGlHelper.field_176093_u = 34167;
            OpenGlHelper.field_176092_v = 34166;
            OpenGlHelper.field_176091_w = 34168;
            OpenGlHelper.field_176099_x = 34161;
            OpenGlHelper.field_176098_y = 34176;
            OpenGlHelper.field_176097_z = 34177;
            OpenGlHelper.field_176080_A = 34178;
            OpenGlHelper.field_176081_B = 34192;
            OpenGlHelper.field_176082_C = 34193;
            OpenGlHelper.field_176076_D = 34194;
            OpenGlHelper.field_176077_E = 34162;
            OpenGlHelper.field_176078_F = 34184;
            OpenGlHelper.field_176079_G = 34185;
            OpenGlHelper.field_176084_H = 34186;
            OpenGlHelper.field_176085_I = 34200;
            OpenGlHelper.field_176086_J = 34201;
            OpenGlHelper.field_176087_K = 34202;
        }
        else {
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "Using GL 1.3 texture combiners.\n";
            OpenGlHelper.field_176095_s = 34160;
            OpenGlHelper.field_176094_t = 34165;
            OpenGlHelper.field_176093_u = 34167;
            OpenGlHelper.field_176092_v = 34166;
            OpenGlHelper.field_176091_w = 34168;
            OpenGlHelper.field_176099_x = 34161;
            OpenGlHelper.field_176098_y = 34176;
            OpenGlHelper.field_176097_z = 34177;
            OpenGlHelper.field_176080_A = 34178;
            OpenGlHelper.field_176081_B = 34192;
            OpenGlHelper.field_176082_C = 34193;
            OpenGlHelper.field_176076_D = 34194;
            OpenGlHelper.field_176077_E = 34162;
            OpenGlHelper.field_176078_F = 34184;
            OpenGlHelper.field_176079_G = 34185;
            OpenGlHelper.field_176084_H = 34186;
            OpenGlHelper.field_176085_I = 34200;
            OpenGlHelper.field_176086_J = 34201;
            OpenGlHelper.field_176087_K = 34202;
        }
        OpenGlHelper.field_153211_u = (capabilities.GL_EXT_blend_func_separate && !capabilities.OpenGL14);
        OpenGlHelper.openGL14 = (capabilities.OpenGL14 || capabilities.GL_EXT_blend_func_separate);
        OpenGlHelper.framebufferSupported = (OpenGlHelper.openGL14 && (capabilities.GL_ARB_framebuffer_object || capabilities.GL_EXT_framebuffer_object || capabilities.OpenGL30));
        if (OpenGlHelper.framebufferSupported) {
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "Using framebuffer objects because ";
            if (capabilities.OpenGL30) {
                OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "OpenGL 3.0 is supported and separate blending is supported.\n";
                OpenGlHelper.field_153212_w = 0;
                OpenGlHelper.field_153198_e = 36160;
                OpenGlHelper.field_153199_f = 36161;
                OpenGlHelper.field_153200_g = 36064;
                OpenGlHelper.field_153201_h = 36096;
                OpenGlHelper.field_153202_i = 36053;
                OpenGlHelper.field_153203_j = 36054;
                OpenGlHelper.field_153204_k = 36055;
                OpenGlHelper.field_153205_l = 36059;
                OpenGlHelper.field_153206_m = 36060;
            }
            else if (capabilities.GL_ARB_framebuffer_object) {
                OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "ARB_framebuffer_object is supported and separate blending is supported.\n";
                OpenGlHelper.field_153212_w = 1;
                OpenGlHelper.field_153198_e = 36160;
                OpenGlHelper.field_153199_f = 36161;
                OpenGlHelper.field_153200_g = 36064;
                OpenGlHelper.field_153201_h = 36096;
                OpenGlHelper.field_153202_i = 36053;
                OpenGlHelper.field_153204_k = 36055;
                OpenGlHelper.field_153203_j = 36054;
                OpenGlHelper.field_153205_l = 36059;
                OpenGlHelper.field_153206_m = 36060;
            }
            else if (capabilities.GL_EXT_framebuffer_object) {
                OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "EXT_framebuffer_object is supported.\n";
                OpenGlHelper.field_153212_w = 2;
                OpenGlHelper.field_153198_e = 36160;
                OpenGlHelper.field_153199_f = 36161;
                OpenGlHelper.field_153200_g = 36064;
                OpenGlHelper.field_153201_h = 36096;
                OpenGlHelper.field_153202_i = 36053;
                OpenGlHelper.field_153204_k = 36055;
                OpenGlHelper.field_153203_j = 36054;
                OpenGlHelper.field_153205_l = 36059;
                OpenGlHelper.field_153206_m = 36060;
            }
        }
        else {
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "Not using framebuffer objects because ";
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "OpenGL 1.4 is " + (capabilities.OpenGL14 ? "" : "not ") + "supported, ";
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "EXT_blend_func_separate is " + (capabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "OpenGL 3.0 is " + (capabilities.OpenGL30 ? "" : "not ") + "supported, ";
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "ARB_framebuffer_object is " + (capabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "EXT_framebuffer_object is " + (capabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
        }
        OpenGlHelper.openGL21 = capabilities.OpenGL21;
        OpenGlHelper.field_153213_x = (OpenGlHelper.openGL21 || (capabilities.GL_ARB_vertex_shader && capabilities.GL_ARB_fragment_shader && capabilities.GL_ARB_shader_objects));
        OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "Shaders are " + (OpenGlHelper.field_153213_x ? "" : "not ") + "available because ";
        if (OpenGlHelper.field_153213_x) {
            if (capabilities.OpenGL21) {
                OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "OpenGL 2.1 is supported.\n";
                OpenGlHelper.field_153214_y = false;
                OpenGlHelper.GL_LINK_STATUS = 35714;
                OpenGlHelper.GL_COMPILE_STATUS = 35713;
                OpenGlHelper.GL_VERTEX_SHADER = 35633;
                OpenGlHelper.GL_FRAGMENT_SHADER = 35632;
            }
            else {
                OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
                OpenGlHelper.field_153214_y = true;
                OpenGlHelper.GL_LINK_STATUS = 35714;
                OpenGlHelper.GL_COMPILE_STATUS = 35713;
                OpenGlHelper.GL_VERTEX_SHADER = 35633;
                OpenGlHelper.GL_FRAGMENT_SHADER = 35632;
            }
        }
        else {
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "OpenGL 2.1 is " + (capabilities.OpenGL21 ? "" : "not ") + "supported, ";
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "ARB_shader_objects is " + (capabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "ARB_vertex_shader is " + (capabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
            OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "ARB_fragment_shader is " + (capabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
        }
        OpenGlHelper.shadersSupported = (OpenGlHelper.framebufferSupported && OpenGlHelper.field_153213_x);
        OpenGlHelper.field_153197_d = GL11.glGetString(7936).toLowerCase().contains("nvidia");
        OpenGlHelper.field_176090_Y = (!capabilities.OpenGL15 && capabilities.GL_ARB_vertex_buffer_object);
        OpenGlHelper.field_176083_O = (capabilities.OpenGL15 || OpenGlHelper.field_176090_Y);
        OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "VBOs are " + (OpenGlHelper.field_176083_O ? "" : "not ") + "available because ";
        if (OpenGlHelper.field_176083_O) {
            if (OpenGlHelper.field_176090_Y) {
                OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "ARB_vertex_buffer_object is supported.\n";
                OpenGlHelper.anisotropicFilteringMax = 35044;
                OpenGlHelper.field_176089_P = 34962;
            }
            else {
                OpenGlHelper.field_153196_B = String.valueOf(OpenGlHelper.field_153196_B) + "OpenGL 1.5 is supported.\n";
                OpenGlHelper.anisotropicFilteringMax = 35044;
                OpenGlHelper.field_176089_P = 34962;
            }
        }
    }
    
    public static boolean areShadersSupported() {
        return OpenGlHelper.shadersSupported;
    }
    
    public static String func_153172_c() {
        return OpenGlHelper.field_153196_B;
    }
    
    public static int glGetProgrami(final int n, final int n2) {
        return OpenGlHelper.field_153214_y ? ARBShaderObjects.glGetObjectParameteriARB(n, n2) : GL20.glGetProgrami(n, n2);
    }
    
    public static void glAttachShader(final int n, final int n2) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glAttachObjectARB(n, n2);
        }
        else {
            GL20.glAttachShader(n, n2);
        }
    }
    
    public static void glDeleteShader(final int n) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glDeleteObjectARB(n);
        }
        else {
            GL20.glDeleteShader(n);
        }
    }
    
    public static int glCreateShader(final int n) {
        return OpenGlHelper.field_153214_y ? ARBShaderObjects.glCreateShaderObjectARB(n) : GL20.glCreateShader(n);
    }
    
    public static void glShaderSource(final int n, final ByteBuffer byteBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glShaderSourceARB(n, byteBuffer);
        }
        else {
            GL20.glShaderSource(n, byteBuffer);
        }
    }
    
    public static void glCompileShader(final int n) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glCompileShaderARB(n);
        }
        else {
            GL20.glCompileShader(n);
        }
    }
    
    public static int glGetShaderi(final int n, final int n2) {
        return OpenGlHelper.field_153214_y ? ARBShaderObjects.glGetObjectParameteriARB(n, n2) : GL20.glGetShaderi(n, n2);
    }
    
    public static String glGetShaderInfoLog(final int n, final int n2) {
        return OpenGlHelper.field_153214_y ? ARBShaderObjects.glGetInfoLogARB(n, n2) : GL20.glGetShaderInfoLog(n, n2);
    }
    
    public static String glGetProgramInfoLog(final int n, final int n2) {
        return OpenGlHelper.field_153214_y ? ARBShaderObjects.glGetInfoLogARB(n, n2) : GL20.glGetProgramInfoLog(n, n2);
    }
    
    public static void glUseProgram(final int n) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUseProgramObjectARB(n);
        }
        else {
            GL20.glUseProgram(n);
        }
    }
    
    public static int glCreateProgram() {
        return OpenGlHelper.field_153214_y ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
    }
    
    public static void glDeleteProgram(final int n) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glDeleteObjectARB(n);
        }
        else {
            GL20.glDeleteProgram(n);
        }
    }
    
    public static void glLinkProgram(final int n) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glLinkProgramARB(n);
        }
        else {
            GL20.glLinkProgram(n);
        }
    }
    
    public static int glGetUniformLocation(final int n, final CharSequence charSequence) {
        return OpenGlHelper.field_153214_y ? ARBShaderObjects.glGetUniformLocationARB(n, charSequence) : GL20.glGetUniformLocation(n, charSequence);
    }
    
    public static void glUniform1(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform1ARB(n, intBuffer);
        }
        else {
            GL20.glUniform1(n, intBuffer);
        }
    }
    
    public static void glUniform1i(final int n, final int n2) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform1iARB(n, n2);
        }
        else {
            GL20.glUniform1i(n, n2);
        }
    }
    
    public static void glUniform1(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform1ARB(n, floatBuffer);
        }
        else {
            GL20.glUniform1(n, floatBuffer);
        }
    }
    
    public static void glUniform2(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform2ARB(n, intBuffer);
        }
        else {
            GL20.glUniform2(n, intBuffer);
        }
    }
    
    public static void glUniform2(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform2ARB(n, floatBuffer);
        }
        else {
            GL20.glUniform2(n, floatBuffer);
        }
    }
    
    public static void glUniform3(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform3ARB(n, intBuffer);
        }
        else {
            GL20.glUniform3(n, intBuffer);
        }
    }
    
    public static void glUniform3(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform3ARB(n, floatBuffer);
        }
        else {
            GL20.glUniform3(n, floatBuffer);
        }
    }
    
    public static void glUniform4(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform4ARB(n, intBuffer);
        }
        else {
            GL20.glUniform4(n, intBuffer);
        }
    }
    
    public static void glUniform4(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniform4ARB(n, floatBuffer);
        }
        else {
            GL20.glUniform4(n, floatBuffer);
        }
    }
    
    public static void glUniformMatrix2(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniformMatrix2ARB(n, b, floatBuffer);
        }
        else {
            GL20.glUniformMatrix2(n, b, floatBuffer);
        }
    }
    
    public static void glUniformMatrix3(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniformMatrix3ARB(n, b, floatBuffer);
        }
        else {
            GL20.glUniformMatrix3(n, b, floatBuffer);
        }
    }
    
    public static void glUniformMatrix4(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.field_153214_y) {
            ARBShaderObjects.glUniformMatrix4ARB(n, b, floatBuffer);
        }
        else {
            GL20.glUniformMatrix4(n, b, floatBuffer);
        }
    }
    
    public static int glGetAttribLocation(final int n, final CharSequence charSequence) {
        return OpenGlHelper.field_153214_y ? ARBVertexShader.glGetAttribLocationARB(n, charSequence) : GL20.glGetAttribLocation(n, charSequence);
    }
    
    public static int func_176073_e() {
        return OpenGlHelper.field_176090_Y ? ARBBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
    }
    
    public static void func_176072_g(final int n, final int n2) {
        if (OpenGlHelper.field_176090_Y) {
            ARBBufferObject.glBindBufferARB(n, n2);
        }
        else {
            GL15.glBindBuffer(n, n2);
        }
    }
    
    public static void func_176071_a(final int n, final ByteBuffer byteBuffer, final int n2) {
        if (OpenGlHelper.field_176090_Y) {
            ARBBufferObject.glBufferDataARB(n, byteBuffer, n2);
        }
        else {
            GL15.glBufferData(n, byteBuffer, n2);
        }
    }
    
    public static void func_176074_g(final int n) {
        if (OpenGlHelper.field_176090_Y) {
            ARBBufferObject.glDeleteBuffersARB(n);
        }
        else {
            GL15.glDeleteBuffers(n);
        }
    }
    
    public static boolean func_176075_f() {
        return !Config.isMultiTexture() && (OpenGlHelper.field_176083_O && Minecraft.getMinecraft().gameSettings.field_178881_t);
    }
    
    public static void func_153171_g(final int n, final int n2) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.field_153212_w) {
                case 0: {
                    GL30.glBindFramebuffer(n, n2);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindFramebuffer(n, n2);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindFramebufferEXT(n, n2);
                    break;
                }
            }
        }
    }
    
    public static void func_153176_h(final int n, final int n2) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.field_153212_w) {
                case 0: {
                    GL30.glBindRenderbuffer(n, n2);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindRenderbuffer(n, n2);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindRenderbufferEXT(n, n2);
                    break;
                }
            }
        }
    }
    
    public static void func_153184_g(final int n) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.field_153212_w) {
                case 0: {
                    GL30.glDeleteRenderbuffers(n);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteRenderbuffers(n);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteRenderbuffersEXT(n);
                    break;
                }
            }
        }
    }
    
    public static void func_153174_h(final int n) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.field_153212_w) {
                case 0: {
                    GL30.glDeleteFramebuffers(n);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteFramebuffers(n);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteFramebuffersEXT(n);
                    break;
                }
            }
        }
    }
    
    public static int func_153165_e() {
        if (!OpenGlHelper.framebufferSupported) {
            return -1;
        }
        switch (OpenGlHelper.field_153212_w) {
            case 0: {
                return GL30.glGenFramebuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenFramebuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenFramebuffersEXT();
            }
            default: {
                return -1;
            }
        }
    }
    
    public static int func_153185_f() {
        if (!OpenGlHelper.framebufferSupported) {
            return -1;
        }
        switch (OpenGlHelper.field_153212_w) {
            case 0: {
                return GL30.glGenRenderbuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenRenderbuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenRenderbuffersEXT();
            }
            default: {
                return -1;
            }
        }
    }
    
    public static void func_153186_a(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.field_153212_w) {
                case 0: {
                    GL30.glRenderbufferStorage(n, n2, n3, n4);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glRenderbufferStorage(n, n2, n3, n4);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glRenderbufferStorageEXT(n, n2, n3, n4);
                    break;
                }
            }
        }
    }
    
    public static void func_153190_b(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.field_153212_w) {
                case 0: {
                    GL30.glFramebufferRenderbuffer(n, n2, n3, n4);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferRenderbuffer(n, n2, n3, n4);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferRenderbufferEXT(n, n2, n3, n4);
                    break;
                }
            }
        }
    }
    
    public static int func_153167_i(final int n) {
        if (!OpenGlHelper.framebufferSupported) {
            return -1;
        }
        switch (OpenGlHelper.field_153212_w) {
            case 0: {
                return GL30.glCheckFramebufferStatus(n);
            }
            case 1: {
                return ARBFramebufferObject.glCheckFramebufferStatus(n);
            }
            case 2: {
                return EXTFramebufferObject.glCheckFramebufferStatusEXT(n);
            }
            default: {
                return -1;
            }
        }
    }
    
    public static void func_153188_a(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.field_153212_w) {
                case 0: {
                    GL30.glFramebufferTexture2D(n, n2, n3, n4, n5);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferTexture2D(n, n2, n3, n4, n5);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferTexture2DEXT(n, n2, n3, n4, n5);
                    break;
                }
            }
        }
    }
    
    public static void setActiveTexture(final int n) {
        if (OpenGlHelper.field_153215_z) {
            ARBMultitexture.glActiveTextureARB(n);
        }
        else {
            GL13.glActiveTexture(n);
        }
    }
    
    public static void setClientActiveTexture(final int n) {
        if (OpenGlHelper.field_153215_z) {
            ARBMultitexture.glClientActiveTextureARB(n);
        }
        else {
            GL13.glClientActiveTexture(n);
        }
    }
    
    public static void setLightmapTextureCoords(final int n, final float lastBrightnessX, final float lastBrightnessY) {
        if (OpenGlHelper.field_153215_z) {
            ARBMultitexture.glMultiTexCoord2fARB(n, lastBrightnessX, lastBrightnessY);
        }
        else {
            GL13.glMultiTexCoord2f(n, lastBrightnessX, lastBrightnessY);
        }
        if (n == OpenGlHelper.lightmapTexUnit) {
            OpenGlHelper.lastBrightnessX = lastBrightnessX;
            OpenGlHelper.lastBrightnessY = lastBrightnessY;
        }
    }
    
    public static void glBlendFunc(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.openGL14) {
            if (OpenGlHelper.field_153211_u) {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT(n, n2, n3, n4);
            }
            else {
                GL14.glBlendFuncSeparate(n, n2, n3, n4);
            }
        }
        else {
            GL11.glBlendFunc(n, n2);
        }
    }
    
    public static boolean isFramebufferEnabled() {
        return !Config.isFastRender() && !Config.isAntialiasing() && (OpenGlHelper.framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable);
    }
}
