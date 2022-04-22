package org.lwjgl.opengl;

import java.nio.*;

public final class ARBProgramInterfaceQuery
{
    public static final int GL_UNIFORM = 37601;
    public static final int GL_UNIFORM_BLOCK = 37602;
    public static final int GL_PROGRAM_INPUT = 37603;
    public static final int GL_PROGRAM_OUTPUT = 37604;
    public static final int GL_BUFFER_VARIABLE = 37605;
    public static final int GL_SHADER_STORAGE_BLOCK = 37606;
    public static final int GL_VERTEX_SUBROUTINE = 37608;
    public static final int GL_TESS_CONTROL_SUBROUTINE = 37609;
    public static final int GL_TESS_EVALUATION_SUBROUTINE = 37610;
    public static final int GL_GEOMETRY_SUBROUTINE = 37611;
    public static final int GL_FRAGMENT_SUBROUTINE = 37612;
    public static final int GL_COMPUTE_SUBROUTINE = 37613;
    public static final int GL_VERTEX_SUBROUTINE_UNIFORM = 37614;
    public static final int GL_TESS_CONTROL_SUBROUTINE_UNIFORM = 37615;
    public static final int GL_TESS_EVALUATION_SUBROUTINE_UNIFORM = 37616;
    public static final int GL_GEOMETRY_SUBROUTINE_UNIFORM = 37617;
    public static final int GL_FRAGMENT_SUBROUTINE_UNIFORM = 37618;
    public static final int GL_COMPUTE_SUBROUTINE_UNIFORM = 37619;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING = 37620;
    public static final int GL_ACTIVE_RESOURCES = 37621;
    public static final int GL_MAX_NAME_LENGTH = 37622;
    public static final int GL_MAX_NUM_ACTIVE_VARIABLES = 37623;
    public static final int GL_MAX_NUM_COMPATIBLE_SUBROUTINES = 37624;
    public static final int GL_NAME_LENGTH = 37625;
    public static final int GL_TYPE = 37626;
    public static final int GL_ARRAY_SIZE = 37627;
    public static final int GL_OFFSET = 37628;
    public static final int GL_BLOCK_INDEX = 37629;
    public static final int GL_ARRAY_STRIDE = 37630;
    public static final int GL_MATRIX_STRIDE = 37631;
    public static final int GL_IS_ROW_MAJOR = 37632;
    public static final int GL_ATOMIC_COUNTER_BUFFER_INDEX = 37633;
    public static final int GL_BUFFER_BINDING = 37634;
    public static final int GL_BUFFER_DATA_SIZE = 37635;
    public static final int GL_NUM_ACTIVE_VARIABLES = 37636;
    public static final int GL_ACTIVE_VARIABLES = 37637;
    public static final int GL_REFERENCED_BY_VERTEX_SHADER = 37638;
    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER = 37639;
    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER = 37640;
    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER = 37641;
    public static final int GL_REFERENCED_BY_FRAGMENT_SHADER = 37642;
    public static final int GL_REFERENCED_BY_COMPUTE_SHADER = 37643;
    public static final int GL_TOP_LEVEL_ARRAY_SIZE = 37644;
    public static final int GL_TOP_LEVEL_ARRAY_STRIDE = 37645;
    public static final int GL_LOCATION = 37646;
    public static final int GL_LOCATION_INDEX = 37647;
    public static final int GL_IS_PER_PATCH = 37607;
    
    private ARBProgramInterfaceQuery() {
    }
    
    public static void glGetProgramInterface(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        GL43.glGetProgramInterface(n, n2, n3, intBuffer);
    }
    
    public static int glGetProgramInterfacei(final int n, final int n2, final int n3) {
        return GL43.glGetProgramInterfacei(n, n2, n3);
    }
    
    public static int glGetProgramResourceIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        return GL43.glGetProgramResourceIndex(n, n2, byteBuffer);
    }
    
    public static int glGetProgramResourceIndex(final int n, final int n2, final CharSequence charSequence) {
        return GL43.glGetProgramResourceIndex(n, n2, charSequence);
    }
    
    public static void glGetProgramResourceName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        GL43.glGetProgramResourceName(n, n2, n3, intBuffer, byteBuffer);
    }
    
    public static String glGetProgramResourceName(final int n, final int n2, final int n3, final int n4) {
        return GL43.glGetProgramResourceName(n, n2, n3, n4);
    }
    
    public static void glGetProgramResource(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        GL43.glGetProgramResource(n, n2, n3, intBuffer, intBuffer2, intBuffer3);
    }
    
    public static int glGetProgramResourceLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        return GL43.glGetProgramResourceLocation(n, n2, byteBuffer);
    }
    
    public static int glGetProgramResourceLocation(final int n, final int n2, final CharSequence charSequence) {
        return GL43.glGetProgramResourceLocation(n, n2, charSequence);
    }
    
    public static int glGetProgramResourceLocationIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        return GL43.glGetProgramResourceLocationIndex(n, n2, byteBuffer);
    }
    
    public static int glGetProgramResourceLocationIndex(final int n, final int n2, final CharSequence charSequence) {
        return GL43.glGetProgramResourceLocationIndex(n, n2, charSequence);
    }
}
