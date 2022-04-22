package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL11
{
    public static final int GL_ACCUM = 256;
    public static final int GL_LOAD = 257;
    public static final int GL_RETURN = 258;
    public static final int GL_MULT = 259;
    public static final int GL_ADD = 260;
    public static final int GL_NEVER = 512;
    public static final int GL_LESS = 513;
    public static final int GL_EQUAL = 514;
    public static final int GL_LEQUAL = 515;
    public static final int GL_GREATER = 516;
    public static final int GL_NOTEQUAL = 517;
    public static final int GL_GEQUAL = 518;
    public static final int GL_ALWAYS = 519;
    public static final int GL_CURRENT_BIT = 1;
    public static final int GL_POINT_BIT = 2;
    public static final int GL_LINE_BIT = 4;
    public static final int GL_POLYGON_BIT = 8;
    public static final int GL_POLYGON_STIPPLE_BIT = 16;
    public static final int GL_PIXEL_MODE_BIT = 32;
    public static final int GL_LIGHTING_BIT = 64;
    public static final int GL_FOG_BIT = 128;
    public static final int GL_DEPTH_BUFFER_BIT = 256;
    public static final int GL_ACCUM_BUFFER_BIT = 512;
    public static final int GL_STENCIL_BUFFER_BIT = 1024;
    public static final int GL_VIEWPORT_BIT = 2048;
    public static final int GL_TRANSFORM_BIT = 4096;
    public static final int GL_ENABLE_BIT = 8192;
    public static final int GL_COLOR_BUFFER_BIT = 16384;
    public static final int GL_HINT_BIT = 32768;
    public static final int GL_EVAL_BIT = 65536;
    public static final int GL_LIST_BIT = 131072;
    public static final int GL_TEXTURE_BIT = 262144;
    public static final int GL_SCISSOR_BIT = 524288;
    public static final int GL_ALL_ATTRIB_BITS = 1048575;
    public static final int GL_POINTS = 0;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_LOOP = 2;
    public static final int GL_LINE_STRIP = 3;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_TRIANGLE_STRIP = 5;
    public static final int GL_TRIANGLE_FAN = 6;
    public static final int GL_QUADS = 7;
    public static final int GL_QUAD_STRIP = 8;
    public static final int GL_POLYGON = 9;
    public static final int GL_ZERO = 0;
    public static final int GL_ONE = 1;
    public static final int GL_SRC_COLOR = 768;
    public static final int GL_ONE_MINUS_SRC_COLOR = 769;
    public static final int GL_SRC_ALPHA = 770;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
    public static final int GL_DST_ALPHA = 772;
    public static final int GL_ONE_MINUS_DST_ALPHA = 773;
    public static final int GL_DST_COLOR = 774;
    public static final int GL_ONE_MINUS_DST_COLOR = 775;
    public static final int GL_SRC_ALPHA_SATURATE = 776;
    public static final int GL_CONSTANT_COLOR = 32769;
    public static final int GL_ONE_MINUS_CONSTANT_COLOR = 32770;
    public static final int GL_CONSTANT_ALPHA = 32771;
    public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 32772;
    public static final int GL_TRUE = 1;
    public static final int GL_FALSE = 0;
    public static final int GL_CLIP_PLANE0 = 12288;
    public static final int GL_CLIP_PLANE1 = 12289;
    public static final int GL_CLIP_PLANE2 = 12290;
    public static final int GL_CLIP_PLANE3 = 12291;
    public static final int GL_CLIP_PLANE4 = 12292;
    public static final int GL_CLIP_PLANE5 = 12293;
    public static final int GL_BYTE = 5120;
    public static final int GL_UNSIGNED_BYTE = 5121;
    public static final int GL_SHORT = 5122;
    public static final int GL_UNSIGNED_SHORT = 5123;
    public static final int GL_INT = 5124;
    public static final int GL_UNSIGNED_INT = 5125;
    public static final int GL_FLOAT = 5126;
    public static final int GL_2_BYTES = 5127;
    public static final int GL_3_BYTES = 5128;
    public static final int GL_4_BYTES = 5129;
    public static final int GL_DOUBLE = 5130;
    public static final int GL_NONE = 0;
    public static final int GL_FRONT_LEFT = 1024;
    public static final int GL_FRONT_RIGHT = 1025;
    public static final int GL_BACK_LEFT = 1026;
    public static final int GL_BACK_RIGHT = 1027;
    public static final int GL_FRONT = 1028;
    public static final int GL_BACK = 1029;
    public static final int GL_LEFT = 1030;
    public static final int GL_RIGHT = 1031;
    public static final int GL_FRONT_AND_BACK = 1032;
    public static final int GL_AUX0 = 1033;
    public static final int GL_AUX1 = 1034;
    public static final int GL_AUX2 = 1035;
    public static final int GL_AUX3 = 1036;
    public static final int GL_NO_ERROR = 0;
    public static final int GL_INVALID_ENUM = 1280;
    public static final int GL_INVALID_VALUE = 1281;
    public static final int GL_INVALID_OPERATION = 1282;
    public static final int GL_STACK_OVERFLOW = 1283;
    public static final int GL_STACK_UNDERFLOW = 1284;
    public static final int GL_OUT_OF_MEMORY = 1285;
    public static final int GL_2D = 1536;
    public static final int GL_3D = 1537;
    public static final int GL_3D_COLOR = 1538;
    public static final int GL_3D_COLOR_TEXTURE = 1539;
    public static final int GL_4D_COLOR_TEXTURE = 1540;
    public static final int GL_PASS_THROUGH_TOKEN = 1792;
    public static final int GL_POINT_TOKEN = 1793;
    public static final int GL_LINE_TOKEN = 1794;
    public static final int GL_POLYGON_TOKEN = 1795;
    public static final int GL_BITMAP_TOKEN = 1796;
    public static final int GL_DRAW_PIXEL_TOKEN = 1797;
    public static final int GL_COPY_PIXEL_TOKEN = 1798;
    public static final int GL_LINE_RESET_TOKEN = 1799;
    public static final int GL_EXP = 2048;
    public static final int GL_EXP2 = 2049;
    public static final int GL_CW = 2304;
    public static final int GL_CCW = 2305;
    public static final int GL_COEFF = 2560;
    public static final int GL_ORDER = 2561;
    public static final int GL_DOMAIN = 2562;
    public static final int GL_CURRENT_COLOR = 2816;
    public static final int GL_CURRENT_INDEX = 2817;
    public static final int GL_CURRENT_NORMAL = 2818;
    public static final int GL_CURRENT_TEXTURE_COORDS = 2819;
    public static final int GL_CURRENT_RASTER_COLOR = 2820;
    public static final int GL_CURRENT_RASTER_INDEX = 2821;
    public static final int GL_CURRENT_RASTER_TEXTURE_COORDS = 2822;
    public static final int GL_CURRENT_RASTER_POSITION = 2823;
    public static final int GL_CURRENT_RASTER_POSITION_VALID = 2824;
    public static final int GL_CURRENT_RASTER_DISTANCE = 2825;
    public static final int GL_POINT_SMOOTH = 2832;
    public static final int GL_POINT_SIZE = 2833;
    public static final int GL_POINT_SIZE_RANGE = 2834;
    public static final int GL_POINT_SIZE_GRANULARITY = 2835;
    public static final int GL_LINE_SMOOTH = 2848;
    public static final int GL_LINE_WIDTH = 2849;
    public static final int GL_LINE_WIDTH_RANGE = 2850;
    public static final int GL_LINE_WIDTH_GRANULARITY = 2851;
    public static final int GL_LINE_STIPPLE = 2852;
    public static final int GL_LINE_STIPPLE_PATTERN = 2853;
    public static final int GL_LINE_STIPPLE_REPEAT = 2854;
    public static final int GL_LIST_MODE = 2864;
    public static final int GL_MAX_LIST_NESTING = 2865;
    public static final int GL_LIST_BASE = 2866;
    public static final int GL_LIST_INDEX = 2867;
    public static final int GL_POLYGON_MODE = 2880;
    public static final int GL_POLYGON_SMOOTH = 2881;
    public static final int GL_POLYGON_STIPPLE = 2882;
    public static final int GL_EDGE_FLAG = 2883;
    public static final int GL_CULL_FACE = 2884;
    public static final int GL_CULL_FACE_MODE = 2885;
    public static final int GL_FRONT_FACE = 2886;
    public static final int GL_LIGHTING = 2896;
    public static final int GL_LIGHT_MODEL_LOCAL_VIEWER = 2897;
    public static final int GL_LIGHT_MODEL_TWO_SIDE = 2898;
    public static final int GL_LIGHT_MODEL_AMBIENT = 2899;
    public static final int GL_SHADE_MODEL = 2900;
    public static final int GL_COLOR_MATERIAL_FACE = 2901;
    public static final int GL_COLOR_MATERIAL_PARAMETER = 2902;
    public static final int GL_COLOR_MATERIAL = 2903;
    public static final int GL_FOG = 2912;
    public static final int GL_FOG_INDEX = 2913;
    public static final int GL_FOG_DENSITY = 2914;
    public static final int GL_FOG_START = 2915;
    public static final int GL_FOG_END = 2916;
    public static final int GL_FOG_MODE = 2917;
    public static final int GL_FOG_COLOR = 2918;
    public static final int GL_DEPTH_RANGE = 2928;
    public static final int GL_DEPTH_TEST = 2929;
    public static final int GL_DEPTH_WRITEMASK = 2930;
    public static final int GL_DEPTH_CLEAR_VALUE = 2931;
    public static final int GL_DEPTH_FUNC = 2932;
    public static final int GL_ACCUM_CLEAR_VALUE = 2944;
    public static final int GL_STENCIL_TEST = 2960;
    public static final int GL_STENCIL_CLEAR_VALUE = 2961;
    public static final int GL_STENCIL_FUNC = 2962;
    public static final int GL_STENCIL_VALUE_MASK = 2963;
    public static final int GL_STENCIL_FAIL = 2964;
    public static final int GL_STENCIL_PASS_DEPTH_FAIL = 2965;
    public static final int GL_STENCIL_PASS_DEPTH_PASS = 2966;
    public static final int GL_STENCIL_REF = 2967;
    public static final int GL_STENCIL_WRITEMASK = 2968;
    public static final int GL_MATRIX_MODE = 2976;
    public static final int GL_NORMALIZE = 2977;
    public static final int GL_VIEWPORT = 2978;
    public static final int GL_MODELVIEW_STACK_DEPTH = 2979;
    public static final int GL_PROJECTION_STACK_DEPTH = 2980;
    public static final int GL_TEXTURE_STACK_DEPTH = 2981;
    public static final int GL_MODELVIEW_MATRIX = 2982;
    public static final int GL_PROJECTION_MATRIX = 2983;
    public static final int GL_TEXTURE_MATRIX = 2984;
    public static final int GL_ATTRIB_STACK_DEPTH = 2992;
    public static final int GL_CLIENT_ATTRIB_STACK_DEPTH = 2993;
    public static final int GL_ALPHA_TEST = 3008;
    public static final int GL_ALPHA_TEST_FUNC = 3009;
    public static final int GL_ALPHA_TEST_REF = 3010;
    public static final int GL_DITHER = 3024;
    public static final int GL_BLEND_DST = 3040;
    public static final int GL_BLEND_SRC = 3041;
    public static final int GL_BLEND = 3042;
    public static final int GL_LOGIC_OP_MODE = 3056;
    public static final int GL_INDEX_LOGIC_OP = 3057;
    public static final int GL_COLOR_LOGIC_OP = 3058;
    public static final int GL_AUX_BUFFERS = 3072;
    public static final int GL_DRAW_BUFFER = 3073;
    public static final int GL_READ_BUFFER = 3074;
    public static final int GL_SCISSOR_BOX = 3088;
    public static final int GL_SCISSOR_TEST = 3089;
    public static final int GL_INDEX_CLEAR_VALUE = 3104;
    public static final int GL_INDEX_WRITEMASK = 3105;
    public static final int GL_COLOR_CLEAR_VALUE = 3106;
    public static final int GL_COLOR_WRITEMASK = 3107;
    public static final int GL_INDEX_MODE = 3120;
    public static final int GL_RGBA_MODE = 3121;
    public static final int GL_DOUBLEBUFFER = 3122;
    public static final int GL_STEREO = 3123;
    public static final int GL_RENDER_MODE = 3136;
    public static final int GL_PERSPECTIVE_CORRECTION_HINT = 3152;
    public static final int GL_POINT_SMOOTH_HINT = 3153;
    public static final int GL_LINE_SMOOTH_HINT = 3154;
    public static final int GL_POLYGON_SMOOTH_HINT = 3155;
    public static final int GL_FOG_HINT = 3156;
    public static final int GL_TEXTURE_GEN_S = 3168;
    public static final int GL_TEXTURE_GEN_T = 3169;
    public static final int GL_TEXTURE_GEN_R = 3170;
    public static final int GL_TEXTURE_GEN_Q = 3171;
    public static final int GL_PIXEL_MAP_I_TO_I = 3184;
    public static final int GL_PIXEL_MAP_S_TO_S = 3185;
    public static final int GL_PIXEL_MAP_I_TO_R = 3186;
    public static final int GL_PIXEL_MAP_I_TO_G = 3187;
    public static final int GL_PIXEL_MAP_I_TO_B = 3188;
    public static final int GL_PIXEL_MAP_I_TO_A = 3189;
    public static final int GL_PIXEL_MAP_R_TO_R = 3190;
    public static final int GL_PIXEL_MAP_G_TO_G = 3191;
    public static final int GL_PIXEL_MAP_B_TO_B = 3192;
    public static final int GL_PIXEL_MAP_A_TO_A = 3193;
    public static final int GL_PIXEL_MAP_I_TO_I_SIZE = 3248;
    public static final int GL_PIXEL_MAP_S_TO_S_SIZE = 3249;
    public static final int GL_PIXEL_MAP_I_TO_R_SIZE = 3250;
    public static final int GL_PIXEL_MAP_I_TO_G_SIZE = 3251;
    public static final int GL_PIXEL_MAP_I_TO_B_SIZE = 3252;
    public static final int GL_PIXEL_MAP_I_TO_A_SIZE = 3253;
    public static final int GL_PIXEL_MAP_R_TO_R_SIZE = 3254;
    public static final int GL_PIXEL_MAP_G_TO_G_SIZE = 3255;
    public static final int GL_PIXEL_MAP_B_TO_B_SIZE = 3256;
    public static final int GL_PIXEL_MAP_A_TO_A_SIZE = 3257;
    public static final int GL_UNPACK_SWAP_BYTES = 3312;
    public static final int GL_UNPACK_LSB_FIRST = 3313;
    public static final int GL_UNPACK_ROW_LENGTH = 3314;
    public static final int GL_UNPACK_SKIP_ROWS = 3315;
    public static final int GL_UNPACK_SKIP_PIXELS = 3316;
    public static final int GL_UNPACK_ALIGNMENT = 3317;
    public static final int GL_PACK_SWAP_BYTES = 3328;
    public static final int GL_PACK_LSB_FIRST = 3329;
    public static final int GL_PACK_ROW_LENGTH = 3330;
    public static final int GL_PACK_SKIP_ROWS = 3331;
    public static final int GL_PACK_SKIP_PIXELS = 3332;
    public static final int GL_PACK_ALIGNMENT = 3333;
    public static final int GL_MAP_COLOR = 3344;
    public static final int GL_MAP_STENCIL = 3345;
    public static final int GL_INDEX_SHIFT = 3346;
    public static final int GL_INDEX_OFFSET = 3347;
    public static final int GL_RED_SCALE = 3348;
    public static final int GL_RED_BIAS = 3349;
    public static final int GL_ZOOM_X = 3350;
    public static final int GL_ZOOM_Y = 3351;
    public static final int GL_GREEN_SCALE = 3352;
    public static final int GL_GREEN_BIAS = 3353;
    public static final int GL_BLUE_SCALE = 3354;
    public static final int GL_BLUE_BIAS = 3355;
    public static final int GL_ALPHA_SCALE = 3356;
    public static final int GL_ALPHA_BIAS = 3357;
    public static final int GL_DEPTH_SCALE = 3358;
    public static final int GL_DEPTH_BIAS = 3359;
    public static final int GL_MAX_EVAL_ORDER = 3376;
    public static final int GL_MAX_LIGHTS = 3377;
    public static final int GL_MAX_CLIP_PLANES = 3378;
    public static final int GL_MAX_TEXTURE_SIZE = 3379;
    public static final int GL_MAX_PIXEL_MAP_TABLE = 3380;
    public static final int GL_MAX_ATTRIB_STACK_DEPTH = 3381;
    public static final int GL_MAX_MODELVIEW_STACK_DEPTH = 3382;
    public static final int GL_MAX_NAME_STACK_DEPTH = 3383;
    public static final int GL_MAX_PROJECTION_STACK_DEPTH = 3384;
    public static final int GL_MAX_TEXTURE_STACK_DEPTH = 3385;
    public static final int GL_MAX_VIEWPORT_DIMS = 3386;
    public static final int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 3387;
    public static final int GL_SUBPIXEL_BITS = 3408;
    public static final int GL_INDEX_BITS = 3409;
    public static final int GL_RED_BITS = 3410;
    public static final int GL_GREEN_BITS = 3411;
    public static final int GL_BLUE_BITS = 3412;
    public static final int GL_ALPHA_BITS = 3413;
    public static final int GL_DEPTH_BITS = 3414;
    public static final int GL_STENCIL_BITS = 3415;
    public static final int GL_ACCUM_RED_BITS = 3416;
    public static final int GL_ACCUM_GREEN_BITS = 3417;
    public static final int GL_ACCUM_BLUE_BITS = 3418;
    public static final int GL_ACCUM_ALPHA_BITS = 3419;
    public static final int GL_NAME_STACK_DEPTH = 3440;
    public static final int GL_AUTO_NORMAL = 3456;
    public static final int GL_MAP1_COLOR_4 = 3472;
    public static final int GL_MAP1_INDEX = 3473;
    public static final int GL_MAP1_NORMAL = 3474;
    public static final int GL_MAP1_TEXTURE_COORD_1 = 3475;
    public static final int GL_MAP1_TEXTURE_COORD_2 = 3476;
    public static final int GL_MAP1_TEXTURE_COORD_3 = 3477;
    public static final int GL_MAP1_TEXTURE_COORD_4 = 3478;
    public static final int GL_MAP1_VERTEX_3 = 3479;
    public static final int GL_MAP1_VERTEX_4 = 3480;
    public static final int GL_MAP2_COLOR_4 = 3504;
    public static final int GL_MAP2_INDEX = 3505;
    public static final int GL_MAP2_NORMAL = 3506;
    public static final int GL_MAP2_TEXTURE_COORD_1 = 3507;
    public static final int GL_MAP2_TEXTURE_COORD_2 = 3508;
    public static final int GL_MAP2_TEXTURE_COORD_3 = 3509;
    public static final int GL_MAP2_TEXTURE_COORD_4 = 3510;
    public static final int GL_MAP2_VERTEX_3 = 3511;
    public static final int GL_MAP2_VERTEX_4 = 3512;
    public static final int GL_MAP1_GRID_DOMAIN = 3536;
    public static final int GL_MAP1_GRID_SEGMENTS = 3537;
    public static final int GL_MAP2_GRID_DOMAIN = 3538;
    public static final int GL_MAP2_GRID_SEGMENTS = 3539;
    public static final int GL_TEXTURE_1D = 3552;
    public static final int GL_TEXTURE_2D = 3553;
    public static final int GL_FEEDBACK_BUFFER_POINTER = 3568;
    public static final int GL_FEEDBACK_BUFFER_SIZE = 3569;
    public static final int GL_FEEDBACK_BUFFER_TYPE = 3570;
    public static final int GL_SELECTION_BUFFER_POINTER = 3571;
    public static final int GL_SELECTION_BUFFER_SIZE = 3572;
    public static final int GL_TEXTURE_WIDTH = 4096;
    public static final int GL_TEXTURE_HEIGHT = 4097;
    public static final int GL_TEXTURE_INTERNAL_FORMAT = 4099;
    public static final int GL_TEXTURE_BORDER_COLOR = 4100;
    public static final int GL_TEXTURE_BORDER = 4101;
    public static final int GL_DONT_CARE = 4352;
    public static final int GL_FASTEST = 4353;
    public static final int GL_NICEST = 4354;
    public static final int GL_LIGHT0 = 16384;
    public static final int GL_LIGHT1 = 16385;
    public static final int GL_LIGHT2 = 16386;
    public static final int GL_LIGHT3 = 16387;
    public static final int GL_LIGHT4 = 16388;
    public static final int GL_LIGHT5 = 16389;
    public static final int GL_LIGHT6 = 16390;
    public static final int GL_LIGHT7 = 16391;
    public static final int GL_AMBIENT = 4608;
    public static final int GL_DIFFUSE = 4609;
    public static final int GL_SPECULAR = 4610;
    public static final int GL_POSITION = 4611;
    public static final int GL_SPOT_DIRECTION = 4612;
    public static final int GL_SPOT_EXPONENT = 4613;
    public static final int GL_SPOT_CUTOFF = 4614;
    public static final int GL_CONSTANT_ATTENUATION = 4615;
    public static final int GL_LINEAR_ATTENUATION = 4616;
    public static final int GL_QUADRATIC_ATTENUATION = 4617;
    public static final int GL_COMPILE = 4864;
    public static final int GL_COMPILE_AND_EXECUTE = 4865;
    public static final int GL_CLEAR = 5376;
    public static final int GL_AND = 5377;
    public static final int GL_AND_REVERSE = 5378;
    public static final int GL_COPY = 5379;
    public static final int GL_AND_INVERTED = 5380;
    public static final int GL_NOOP = 5381;
    public static final int GL_XOR = 5382;
    public static final int GL_OR = 5383;
    public static final int GL_NOR = 5384;
    public static final int GL_EQUIV = 5385;
    public static final int GL_INVERT = 5386;
    public static final int GL_OR_REVERSE = 5387;
    public static final int GL_COPY_INVERTED = 5388;
    public static final int GL_OR_INVERTED = 5389;
    public static final int GL_NAND = 5390;
    public static final int GL_SET = 5391;
    public static final int GL_EMISSION = 5632;
    public static final int GL_SHININESS = 5633;
    public static final int GL_AMBIENT_AND_DIFFUSE = 5634;
    public static final int GL_COLOR_INDEXES = 5635;
    public static final int GL_MODELVIEW = 5888;
    public static final int GL_PROJECTION = 5889;
    public static final int GL_TEXTURE = 5890;
    public static final int GL_COLOR = 6144;
    public static final int GL_DEPTH = 6145;
    public static final int GL_STENCIL = 6146;
    public static final int GL_COLOR_INDEX = 6400;
    public static final int GL_STENCIL_INDEX = 6401;
    public static final int GL_DEPTH_COMPONENT = 6402;
    public static final int GL_RED = 6403;
    public static final int GL_GREEN = 6404;
    public static final int GL_BLUE = 6405;
    public static final int GL_ALPHA = 6406;
    public static final int GL_RGB = 6407;
    public static final int GL_RGBA = 6408;
    public static final int GL_LUMINANCE = 6409;
    public static final int GL_LUMINANCE_ALPHA = 6410;
    public static final int GL_BITMAP = 6656;
    public static final int GL_POINT = 6912;
    public static final int GL_LINE = 6913;
    public static final int GL_FILL = 6914;
    public static final int GL_RENDER = 7168;
    public static final int GL_FEEDBACK = 7169;
    public static final int GL_SELECT = 7170;
    public static final int GL_FLAT = 7424;
    public static final int GL_SMOOTH = 7425;
    public static final int GL_KEEP = 7680;
    public static final int GL_REPLACE = 7681;
    public static final int GL_INCR = 7682;
    public static final int GL_DECR = 7683;
    public static final int GL_VENDOR = 7936;
    public static final int GL_RENDERER = 7937;
    public static final int GL_VERSION = 7938;
    public static final int GL_EXTENSIONS = 7939;
    public static final int GL_S = 8192;
    public static final int GL_T = 8193;
    public static final int GL_R = 8194;
    public static final int GL_Q = 8195;
    public static final int GL_MODULATE = 8448;
    public static final int GL_DECAL = 8449;
    public static final int GL_TEXTURE_ENV_MODE = 8704;
    public static final int GL_TEXTURE_ENV_COLOR = 8705;
    public static final int GL_TEXTURE_ENV = 8960;
    public static final int GL_EYE_LINEAR = 9216;
    public static final int GL_OBJECT_LINEAR = 9217;
    public static final int GL_SPHERE_MAP = 9218;
    public static final int GL_TEXTURE_GEN_MODE = 9472;
    public static final int GL_OBJECT_PLANE = 9473;
    public static final int GL_EYE_PLANE = 9474;
    public static final int GL_NEAREST = 9728;
    public static final int GL_LINEAR = 9729;
    public static final int GL_NEAREST_MIPMAP_NEAREST = 9984;
    public static final int GL_LINEAR_MIPMAP_NEAREST = 9985;
    public static final int GL_NEAREST_MIPMAP_LINEAR = 9986;
    public static final int GL_LINEAR_MIPMAP_LINEAR = 9987;
    public static final int GL_TEXTURE_MAG_FILTER = 10240;
    public static final int GL_TEXTURE_MIN_FILTER = 10241;
    public static final int GL_TEXTURE_WRAP_S = 10242;
    public static final int GL_TEXTURE_WRAP_T = 10243;
    public static final int GL_CLAMP = 10496;
    public static final int GL_REPEAT = 10497;
    public static final int GL_CLIENT_PIXEL_STORE_BIT = 1;
    public static final int GL_CLIENT_VERTEX_ARRAY_BIT = 2;
    public static final int GL_ALL_CLIENT_ATTRIB_BITS = -1;
    public static final int GL_POLYGON_OFFSET_FACTOR = 32824;
    public static final int GL_POLYGON_OFFSET_UNITS = 10752;
    public static final int GL_POLYGON_OFFSET_POINT = 10753;
    public static final int GL_POLYGON_OFFSET_LINE = 10754;
    public static final int GL_POLYGON_OFFSET_FILL = 32823;
    public static final int GL_ALPHA4 = 32827;
    public static final int GL_ALPHA8 = 32828;
    public static final int GL_ALPHA12 = 32829;
    public static final int GL_ALPHA16 = 32830;
    public static final int GL_LUMINANCE4 = 32831;
    public static final int GL_LUMINANCE8 = 32832;
    public static final int GL_LUMINANCE12 = 32833;
    public static final int GL_LUMINANCE16 = 32834;
    public static final int GL_LUMINANCE4_ALPHA4 = 32835;
    public static final int GL_LUMINANCE6_ALPHA2 = 32836;
    public static final int GL_LUMINANCE8_ALPHA8 = 32837;
    public static final int GL_LUMINANCE12_ALPHA4 = 32838;
    public static final int GL_LUMINANCE12_ALPHA12 = 32839;
    public static final int GL_LUMINANCE16_ALPHA16 = 32840;
    public static final int GL_INTENSITY = 32841;
    public static final int GL_INTENSITY4 = 32842;
    public static final int GL_INTENSITY8 = 32843;
    public static final int GL_INTENSITY12 = 32844;
    public static final int GL_INTENSITY16 = 32845;
    public static final int GL_R3_G3_B2 = 10768;
    public static final int GL_RGB4 = 32847;
    public static final int GL_RGB5 = 32848;
    public static final int GL_RGB8 = 32849;
    public static final int GL_RGB10 = 32850;
    public static final int GL_RGB12 = 32851;
    public static final int GL_RGB16 = 32852;
    public static final int GL_RGBA2 = 32853;
    public static final int GL_RGBA4 = 32854;
    public static final int GL_RGB5_A1 = 32855;
    public static final int GL_RGBA8 = 32856;
    public static final int GL_RGB10_A2 = 32857;
    public static final int GL_RGBA12 = 32858;
    public static final int GL_RGBA16 = 32859;
    public static final int GL_TEXTURE_RED_SIZE = 32860;
    public static final int GL_TEXTURE_GREEN_SIZE = 32861;
    public static final int GL_TEXTURE_BLUE_SIZE = 32862;
    public static final int GL_TEXTURE_ALPHA_SIZE = 32863;
    public static final int GL_TEXTURE_LUMINANCE_SIZE = 32864;
    public static final int GL_TEXTURE_INTENSITY_SIZE = 32865;
    public static final int GL_PROXY_TEXTURE_1D = 32867;
    public static final int GL_PROXY_TEXTURE_2D = 32868;
    public static final int GL_TEXTURE_PRIORITY = 32870;
    public static final int GL_TEXTURE_RESIDENT = 32871;
    public static final int GL_TEXTURE_BINDING_1D = 32872;
    public static final int GL_TEXTURE_BINDING_2D = 32873;
    public static final int GL_VERTEX_ARRAY = 32884;
    public static final int GL_NORMAL_ARRAY = 32885;
    public static final int GL_COLOR_ARRAY = 32886;
    public static final int GL_INDEX_ARRAY = 32887;
    public static final int GL_TEXTURE_COORD_ARRAY = 32888;
    public static final int GL_EDGE_FLAG_ARRAY = 32889;
    public static final int GL_VERTEX_ARRAY_SIZE = 32890;
    public static final int GL_VERTEX_ARRAY_TYPE = 32891;
    public static final int GL_VERTEX_ARRAY_STRIDE = 32892;
    public static final int GL_NORMAL_ARRAY_TYPE = 32894;
    public static final int GL_NORMAL_ARRAY_STRIDE = 32895;
    public static final int GL_COLOR_ARRAY_SIZE = 32897;
    public static final int GL_COLOR_ARRAY_TYPE = 32898;
    public static final int GL_COLOR_ARRAY_STRIDE = 32899;
    public static final int GL_INDEX_ARRAY_TYPE = 32901;
    public static final int GL_INDEX_ARRAY_STRIDE = 32902;
    public static final int GL_TEXTURE_COORD_ARRAY_SIZE = 32904;
    public static final int GL_TEXTURE_COORD_ARRAY_TYPE = 32905;
    public static final int GL_TEXTURE_COORD_ARRAY_STRIDE = 32906;
    public static final int GL_EDGE_FLAG_ARRAY_STRIDE = 32908;
    public static final int GL_VERTEX_ARRAY_POINTER = 32910;
    public static final int GL_NORMAL_ARRAY_POINTER = 32911;
    public static final int GL_COLOR_ARRAY_POINTER = 32912;
    public static final int GL_INDEX_ARRAY_POINTER = 32913;
    public static final int GL_TEXTURE_COORD_ARRAY_POINTER = 32914;
    public static final int GL_EDGE_FLAG_ARRAY_POINTER = 32915;
    public static final int GL_V2F = 10784;
    public static final int GL_V3F = 10785;
    public static final int GL_C4UB_V2F = 10786;
    public static final int GL_C4UB_V3F = 10787;
    public static final int GL_C3F_V3F = 10788;
    public static final int GL_N3F_V3F = 10789;
    public static final int GL_C4F_N3F_V3F = 10790;
    public static final int GL_T2F_V3F = 10791;
    public static final int GL_T4F_V4F = 10792;
    public static final int GL_T2F_C4UB_V3F = 10793;
    public static final int GL_T2F_C3F_V3F = 10794;
    public static final int GL_T2F_N3F_V3F = 10795;
    public static final int GL_T2F_C4F_N3F_V3F = 10796;
    public static final int GL_T4F_C4F_N3F_V4F = 10797;
    public static final int GL_LOGIC_OP = 3057;
    public static final int GL_TEXTURE_COMPONENTS = 4099;
    
    private GL11() {
    }
    
    public static void glAccum(final int n, final float n2) {
        final long glAccum = GLContext.getCapabilities().glAccum;
        BufferChecks.checkFunctionAddress(glAccum);
        nglAccum(n, n2, glAccum);
    }
    
    static native void nglAccum(final int p0, final float p1, final long p2);
    
    public static void glAlphaFunc(final int n, final float n2) {
        final long glAlphaFunc = GLContext.getCapabilities().glAlphaFunc;
        BufferChecks.checkFunctionAddress(glAlphaFunc);
        nglAlphaFunc(n, n2, glAlphaFunc);
    }
    
    static native void nglAlphaFunc(final int p0, final float p1, final long p2);
    
    public static void glClearColor(final float n, final float n2, final float n3, final float n4) {
        final long glClearColor = GLContext.getCapabilities().glClearColor;
        BufferChecks.checkFunctionAddress(glClearColor);
        nglClearColor(n, n2, n3, n4, glClearColor);
    }
    
    static native void nglClearColor(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glClearAccum(final float n, final float n2, final float n3, final float n4) {
        final long glClearAccum = GLContext.getCapabilities().glClearAccum;
        BufferChecks.checkFunctionAddress(glClearAccum);
        nglClearAccum(n, n2, n3, n4, glClearAccum);
    }
    
    static native void nglClearAccum(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glClear(final int n) {
        final long glClear = GLContext.getCapabilities().glClear;
        BufferChecks.checkFunctionAddress(glClear);
        nglClear(n, glClear);
    }
    
    static native void nglClear(final int p0, final long p1);
    
    public static void glCallLists(final ByteBuffer byteBuffer) {
        final long glCallLists = GLContext.getCapabilities().glCallLists;
        BufferChecks.checkFunctionAddress(glCallLists);
        BufferChecks.checkDirect(byteBuffer);
        nglCallLists(byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), glCallLists);
    }
    
    public static void glCallLists(final IntBuffer intBuffer) {
        final long glCallLists = GLContext.getCapabilities().glCallLists;
        BufferChecks.checkFunctionAddress(glCallLists);
        BufferChecks.checkDirect(intBuffer);
        nglCallLists(intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), glCallLists);
    }
    
    public static void glCallLists(final ShortBuffer shortBuffer) {
        final long glCallLists = GLContext.getCapabilities().glCallLists;
        BufferChecks.checkFunctionAddress(glCallLists);
        BufferChecks.checkDirect(shortBuffer);
        nglCallLists(shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), glCallLists);
    }
    
    static native void nglCallLists(final int p0, final int p1, final long p2, final long p3);
    
    public static void glCallList(final int n) {
        final long glCallList = GLContext.getCapabilities().glCallList;
        BufferChecks.checkFunctionAddress(glCallList);
        nglCallList(n, glCallList);
    }
    
    static native void nglCallList(final int p0, final long p1);
    
    public static void glBlendFunc(final int n, final int n2) {
        final long glBlendFunc = GLContext.getCapabilities().glBlendFunc;
        BufferChecks.checkFunctionAddress(glBlendFunc);
        nglBlendFunc(n, n2, glBlendFunc);
    }
    
    static native void nglBlendFunc(final int p0, final int p1, final long p2);
    
    public static void glBitmap(final int n, final int n2, final float n3, final float n4, final float n5, final float n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBitmap = capabilities.glBitmap;
        BufferChecks.checkFunctionAddress(glBitmap);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, (n + 7) / 8 * n2);
        }
        nglBitmap(n, n2, n3, n4, n5, n6, MemoryUtil.getAddressSafe(byteBuffer), glBitmap);
    }
    
    static native void nglBitmap(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6, final long p7);
    
    public static void glBitmap(final int n, final int n2, final float n3, final float n4, final float n5, final float n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBitmap = capabilities.glBitmap;
        BufferChecks.checkFunctionAddress(glBitmap);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglBitmapBO(n, n2, n3, n4, n5, n6, n7, glBitmap);
    }
    
    static native void nglBitmapBO(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6, final long p7);
    
    public static void glBindTexture(final int n, final int n2) {
        final long glBindTexture = GLContext.getCapabilities().glBindTexture;
        BufferChecks.checkFunctionAddress(glBindTexture);
        nglBindTexture(n, n2, glBindTexture);
    }
    
    static native void nglBindTexture(final int p0, final int p1, final long p2);
    
    public static void glPrioritizeTextures(final IntBuffer intBuffer, final FloatBuffer floatBuffer) {
        final long glPrioritizeTextures = GLContext.getCapabilities().glPrioritizeTextures;
        BufferChecks.checkFunctionAddress(glPrioritizeTextures);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(floatBuffer, intBuffer.remaining());
        nglPrioritizeTextures(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(floatBuffer), glPrioritizeTextures);
    }
    
    static native void nglPrioritizeTextures(final int p0, final long p1, final long p2, final long p3);
    
    public static boolean glAreTexturesResident(final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glAreTexturesResident = GLContext.getCapabilities().glAreTexturesResident;
        BufferChecks.checkFunctionAddress(glAreTexturesResident);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkBuffer(byteBuffer, intBuffer.remaining());
        return nglAreTexturesResident(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glAreTexturesResident);
    }
    
    static native boolean nglAreTexturesResident(final int p0, final long p1, final long p2, final long p3);
    
    public static void glBegin(final int n) {
        final long glBegin = GLContext.getCapabilities().glBegin;
        BufferChecks.checkFunctionAddress(glBegin);
        nglBegin(n, glBegin);
    }
    
    static native void nglBegin(final int p0, final long p1);
    
    public static void glEnd() {
        final long glEnd = GLContext.getCapabilities().glEnd;
        BufferChecks.checkFunctionAddress(glEnd);
        nglEnd(glEnd);
    }
    
    static native void nglEnd(final long p0);
    
    public static void glArrayElement(final int n) {
        final long glArrayElement = GLContext.getCapabilities().glArrayElement;
        BufferChecks.checkFunctionAddress(glArrayElement);
        nglArrayElement(n, glArrayElement);
    }
    
    static native void nglArrayElement(final int p0, final long p1);
    
    public static void glClearDepth(final double n) {
        final long glClearDepth = GLContext.getCapabilities().glClearDepth;
        BufferChecks.checkFunctionAddress(glClearDepth);
        nglClearDepth(n, glClearDepth);
    }
    
    static native void nglClearDepth(final double p0, final long p1);
    
    public static void glDeleteLists(final int n, final int n2) {
        final long glDeleteLists = GLContext.getCapabilities().glDeleteLists;
        BufferChecks.checkFunctionAddress(glDeleteLists);
        nglDeleteLists(n, n2, glDeleteLists);
    }
    
    static native void nglDeleteLists(final int p0, final int p1, final long p2);
    
    public static void glDeleteTextures(final IntBuffer intBuffer) {
        final long glDeleteTextures = GLContext.getCapabilities().glDeleteTextures;
        BufferChecks.checkFunctionAddress(glDeleteTextures);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteTextures(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteTextures);
    }
    
    static native void nglDeleteTextures(final int p0, final long p1, final long p2);
    
    public static void glDeleteTextures(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteTextures = capabilities.glDeleteTextures;
        BufferChecks.checkFunctionAddress(glDeleteTextures);
        nglDeleteTextures(1, APIUtil.getInt(capabilities, n), glDeleteTextures);
    }
    
    public static void glCullFace(final int n) {
        final long glCullFace = GLContext.getCapabilities().glCullFace;
        BufferChecks.checkFunctionAddress(glCullFace);
        nglCullFace(n, glCullFace);
    }
    
    static native void nglCullFace(final int p0, final long p1);
    
    public static void glCopyTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long glCopyTexSubImage2D = GLContext.getCapabilities().glCopyTexSubImage2D;
        BufferChecks.checkFunctionAddress(glCopyTexSubImage2D);
        nglCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, glCopyTexSubImage2D);
    }
    
    static native void nglCopyTexSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    public static void glCopyTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glCopyTexSubImage1D = GLContext.getCapabilities().glCopyTexSubImage1D;
        BufferChecks.checkFunctionAddress(glCopyTexSubImage1D);
        nglCopyTexSubImage1D(n, n2, n3, n4, n5, n6, glCopyTexSubImage1D);
    }
    
    static native void nglCopyTexSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long glCopyTexImage2D = GLContext.getCapabilities().glCopyTexImage2D;
        BufferChecks.checkFunctionAddress(glCopyTexImage2D);
        nglCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, glCopyTexImage2D);
    }
    
    static native void nglCopyTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    public static void glCopyTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long glCopyTexImage1D = GLContext.getCapabilities().glCopyTexImage1D;
        BufferChecks.checkFunctionAddress(glCopyTexImage1D);
        nglCopyTexImage1D(n, n2, n3, n4, n5, n6, n7, glCopyTexImage1D);
    }
    
    static native void nglCopyTexImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glCopyPixels(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glCopyPixels = GLContext.getCapabilities().glCopyPixels;
        BufferChecks.checkFunctionAddress(glCopyPixels);
        nglCopyPixels(n, n2, n3, n4, n5, glCopyPixels);
    }
    
    static native void nglCopyPixels(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glColorPointer(final int n, final int n2, final DoubleBuffer gl11_glColorPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorPointer = capabilities.glColorPointer;
        BufferChecks.checkFunctionAddress(glColorPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glColorPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glColorPointer_pointer = gl11_glColorPointer_pointer;
        }
        nglColorPointer(n, 5130, n2, MemoryUtil.getAddress(gl11_glColorPointer_pointer), glColorPointer);
    }
    
    public static void glColorPointer(final int n, final int n2, final FloatBuffer gl11_glColorPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorPointer = capabilities.glColorPointer;
        BufferChecks.checkFunctionAddress(glColorPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glColorPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glColorPointer_pointer = gl11_glColorPointer_pointer;
        }
        nglColorPointer(n, 5126, n2, MemoryUtil.getAddress(gl11_glColorPointer_pointer), glColorPointer);
    }
    
    public static void glColorPointer(final int n, final boolean b, final int n2, final ByteBuffer gl11_glColorPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorPointer = capabilities.glColorPointer;
        BufferChecks.checkFunctionAddress(glColorPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glColorPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glColorPointer_pointer = gl11_glColorPointer_pointer;
        }
        nglColorPointer(n, b ? 5121 : 5120, n2, MemoryUtil.getAddress(gl11_glColorPointer_pointer), glColorPointer);
    }
    
    static native void nglColorPointer(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glColorPointer(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorPointer = capabilities.glColorPointer;
        BufferChecks.checkFunctionAddress(glColorPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglColorPointerBO(n, n2, n3, n4, glColorPointer);
    }
    
    static native void nglColorPointerBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glColorPointer(final int n, final int n2, final int n3, final ByteBuffer gl11_glColorPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorPointer = capabilities.glColorPointer;
        BufferChecks.checkFunctionAddress(glColorPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glColorPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glColorPointer_pointer = gl11_glColorPointer_pointer;
        }
        nglColorPointer(n, n2, n3, MemoryUtil.getAddress(gl11_glColorPointer_pointer), glColorPointer);
    }
    
    public static void glColorMaterial(final int n, final int n2) {
        final long glColorMaterial = GLContext.getCapabilities().glColorMaterial;
        BufferChecks.checkFunctionAddress(glColorMaterial);
        nglColorMaterial(n, n2, glColorMaterial);
    }
    
    static native void nglColorMaterial(final int p0, final int p1, final long p2);
    
    public static void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final long glColorMask = GLContext.getCapabilities().glColorMask;
        BufferChecks.checkFunctionAddress(glColorMask);
        nglColorMask(b, b2, b3, b4, glColorMask);
    }
    
    static native void nglColorMask(final boolean p0, final boolean p1, final boolean p2, final boolean p3, final long p4);
    
    public static void glColor3b(final byte b, final byte b2, final byte b3) {
        final long glColor3b = GLContext.getCapabilities().glColor3b;
        BufferChecks.checkFunctionAddress(glColor3b);
        nglColor3b(b, b2, b3, glColor3b);
    }
    
    static native void nglColor3b(final byte p0, final byte p1, final byte p2, final long p3);
    
    public static void glColor3f(final float n, final float n2, final float n3) {
        final long glColor3f = GLContext.getCapabilities().glColor3f;
        BufferChecks.checkFunctionAddress(glColor3f);
        nglColor3f(n, n2, n3, glColor3f);
    }
    
    static native void nglColor3f(final float p0, final float p1, final float p2, final long p3);
    
    public static void glColor3d(final double n, final double n2, final double n3) {
        final long glColor3d = GLContext.getCapabilities().glColor3d;
        BufferChecks.checkFunctionAddress(glColor3d);
        nglColor3d(n, n2, n3, glColor3d);
    }
    
    static native void nglColor3d(final double p0, final double p1, final double p2, final long p3);
    
    public static void glColor3ub(final byte b, final byte b2, final byte b3) {
        final long glColor3ub = GLContext.getCapabilities().glColor3ub;
        BufferChecks.checkFunctionAddress(glColor3ub);
        nglColor3ub(b, b2, b3, glColor3ub);
    }
    
    static native void nglColor3ub(final byte p0, final byte p1, final byte p2, final long p3);
    
    public static void glColor4b(final byte b, final byte b2, final byte b3, final byte b4) {
        final long glColor4b = GLContext.getCapabilities().glColor4b;
        BufferChecks.checkFunctionAddress(glColor4b);
        nglColor4b(b, b2, b3, b4, glColor4b);
    }
    
    static native void nglColor4b(final byte p0, final byte p1, final byte p2, final byte p3, final long p4);
    
    public static void glColor4f(final float n, final float n2, final float n3, final float n4) {
        final long glColor4f = GLContext.getCapabilities().glColor4f;
        BufferChecks.checkFunctionAddress(glColor4f);
        nglColor4f(n, n2, n3, n4, glColor4f);
    }
    
    static native void nglColor4f(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glColor4d(final double n, final double n2, final double n3, final double n4) {
        final long glColor4d = GLContext.getCapabilities().glColor4d;
        BufferChecks.checkFunctionAddress(glColor4d);
        nglColor4d(n, n2, n3, n4, glColor4d);
    }
    
    static native void nglColor4d(final double p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glColor4ub(final byte b, final byte b2, final byte b3, final byte b4) {
        final long glColor4ub = GLContext.getCapabilities().glColor4ub;
        BufferChecks.checkFunctionAddress(glColor4ub);
        nglColor4ub(b, b2, b3, b4, glColor4ub);
    }
    
    static native void nglColor4ub(final byte p0, final byte p1, final byte p2, final byte p3, final long p4);
    
    public static void glClipPlane(final int n, final DoubleBuffer doubleBuffer) {
        final long glClipPlane = GLContext.getCapabilities().glClipPlane;
        BufferChecks.checkFunctionAddress(glClipPlane);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglClipPlane(n, MemoryUtil.getAddress(doubleBuffer), glClipPlane);
    }
    
    static native void nglClipPlane(final int p0, final long p1, final long p2);
    
    public static void glClearStencil(final int n) {
        final long glClearStencil = GLContext.getCapabilities().glClearStencil;
        BufferChecks.checkFunctionAddress(glClearStencil);
        nglClearStencil(n, glClearStencil);
    }
    
    static native void nglClearStencil(final int p0, final long p1);
    
    public static void glEvalPoint1(final int n) {
        final long glEvalPoint1 = GLContext.getCapabilities().glEvalPoint1;
        BufferChecks.checkFunctionAddress(glEvalPoint1);
        nglEvalPoint1(n, glEvalPoint1);
    }
    
    static native void nglEvalPoint1(final int p0, final long p1);
    
    public static void glEvalPoint2(final int n, final int n2) {
        final long glEvalPoint2 = GLContext.getCapabilities().glEvalPoint2;
        BufferChecks.checkFunctionAddress(glEvalPoint2);
        nglEvalPoint2(n, n2, glEvalPoint2);
    }
    
    static native void nglEvalPoint2(final int p0, final int p1, final long p2);
    
    public static void glEvalMesh1(final int n, final int n2, final int n3) {
        final long glEvalMesh1 = GLContext.getCapabilities().glEvalMesh1;
        BufferChecks.checkFunctionAddress(glEvalMesh1);
        nglEvalMesh1(n, n2, n3, glEvalMesh1);
    }
    
    static native void nglEvalMesh1(final int p0, final int p1, final int p2, final long p3);
    
    public static void glEvalMesh2(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glEvalMesh2 = GLContext.getCapabilities().glEvalMesh2;
        BufferChecks.checkFunctionAddress(glEvalMesh2);
        nglEvalMesh2(n, n2, n3, n4, n5, glEvalMesh2);
    }
    
    static native void nglEvalMesh2(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glEvalCoord1f(final float n) {
        final long glEvalCoord1f = GLContext.getCapabilities().glEvalCoord1f;
        BufferChecks.checkFunctionAddress(glEvalCoord1f);
        nglEvalCoord1f(n, glEvalCoord1f);
    }
    
    static native void nglEvalCoord1f(final float p0, final long p1);
    
    public static void glEvalCoord1d(final double n) {
        final long glEvalCoord1d = GLContext.getCapabilities().glEvalCoord1d;
        BufferChecks.checkFunctionAddress(glEvalCoord1d);
        nglEvalCoord1d(n, glEvalCoord1d);
    }
    
    static native void nglEvalCoord1d(final double p0, final long p1);
    
    public static void glEvalCoord2f(final float n, final float n2) {
        final long glEvalCoord2f = GLContext.getCapabilities().glEvalCoord2f;
        BufferChecks.checkFunctionAddress(glEvalCoord2f);
        nglEvalCoord2f(n, n2, glEvalCoord2f);
    }
    
    static native void nglEvalCoord2f(final float p0, final float p1, final long p2);
    
    public static void glEvalCoord2d(final double n, final double n2) {
        final long glEvalCoord2d = GLContext.getCapabilities().glEvalCoord2d;
        BufferChecks.checkFunctionAddress(glEvalCoord2d);
        nglEvalCoord2d(n, n2, glEvalCoord2d);
    }
    
    static native void nglEvalCoord2d(final double p0, final double p1, final long p2);
    
    public static void glEnableClientState(final int n) {
        final long glEnableClientState = GLContext.getCapabilities().glEnableClientState;
        BufferChecks.checkFunctionAddress(glEnableClientState);
        nglEnableClientState(n, glEnableClientState);
    }
    
    static native void nglEnableClientState(final int p0, final long p1);
    
    public static void glDisableClientState(final int n) {
        final long glDisableClientState = GLContext.getCapabilities().glDisableClientState;
        BufferChecks.checkFunctionAddress(glDisableClientState);
        nglDisableClientState(n, glDisableClientState);
    }
    
    static native void nglDisableClientState(final int p0, final long p1);
    
    public static void glEnable(final int n) {
        final long glEnable = GLContext.getCapabilities().glEnable;
        BufferChecks.checkFunctionAddress(glEnable);
        nglEnable(n, glEnable);
    }
    
    static native void nglEnable(final int p0, final long p1);
    
    public static void glDisable(final int n) {
        final long glDisable = GLContext.getCapabilities().glDisable;
        BufferChecks.checkFunctionAddress(glDisable);
        nglDisable(n, glDisable);
    }
    
    static native void nglDisable(final int p0, final long p1);
    
    public static void glEdgeFlagPointer(final int n, final ByteBuffer gl11_glEdgeFlagPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glEdgeFlagPointer = capabilities.glEdgeFlagPointer;
        BufferChecks.checkFunctionAddress(glEdgeFlagPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glEdgeFlagPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glEdgeFlagPointer_pointer = gl11_glEdgeFlagPointer_pointer;
        }
        nglEdgeFlagPointer(n, MemoryUtil.getAddress(gl11_glEdgeFlagPointer_pointer), glEdgeFlagPointer);
    }
    
    static native void nglEdgeFlagPointer(final int p0, final long p1, final long p2);
    
    public static void glEdgeFlagPointer(final int n, final long n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glEdgeFlagPointer = capabilities.glEdgeFlagPointer;
        BufferChecks.checkFunctionAddress(glEdgeFlagPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglEdgeFlagPointerBO(n, n2, glEdgeFlagPointer);
    }
    
    static native void nglEdgeFlagPointerBO(final int p0, final long p1, final long p2);
    
    public static void glEdgeFlag(final boolean b) {
        final long glEdgeFlag = GLContext.getCapabilities().glEdgeFlag;
        BufferChecks.checkFunctionAddress(glEdgeFlag);
        nglEdgeFlag(b, glEdgeFlag);
    }
    
    static native void nglEdgeFlag(final boolean p0, final long p1);
    
    public static void glDrawPixels(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawPixels = capabilities.glDrawPixels;
        BufferChecks.checkFunctionAddress(glDrawPixels);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n3, n4, n, n2, 1));
        nglDrawPixels(n, n2, n3, n4, MemoryUtil.getAddress(byteBuffer), glDrawPixels);
    }
    
    public static void glDrawPixels(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawPixels = capabilities.glDrawPixels;
        BufferChecks.checkFunctionAddress(glDrawPixels);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n3, n4, n, n2, 1));
        nglDrawPixels(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glDrawPixels);
    }
    
    public static void glDrawPixels(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawPixels = capabilities.glDrawPixels;
        BufferChecks.checkFunctionAddress(glDrawPixels);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n3, n4, n, n2, 1));
        nglDrawPixels(n, n2, n3, n4, MemoryUtil.getAddress(shortBuffer), glDrawPixels);
    }
    
    static native void nglDrawPixels(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glDrawPixels(final int n, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawPixels = capabilities.glDrawPixels;
        BufferChecks.checkFunctionAddress(glDrawPixels);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglDrawPixelsBO(n, n2, n3, n4, n5, glDrawPixels);
    }
    
    static native void nglDrawPixelsBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glDrawElements(final int n, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElements = capabilities.glDrawElements;
        BufferChecks.checkFunctionAddress(glDrawElements);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglDrawElements(n, byteBuffer.remaining(), 5121, MemoryUtil.getAddress(byteBuffer), glDrawElements);
    }
    
    public static void glDrawElements(final int n, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElements = capabilities.glDrawElements;
        BufferChecks.checkFunctionAddress(glDrawElements);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglDrawElements(n, intBuffer.remaining(), 5125, MemoryUtil.getAddress(intBuffer), glDrawElements);
    }
    
    public static void glDrawElements(final int n, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElements = capabilities.glDrawElements;
        BufferChecks.checkFunctionAddress(glDrawElements);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglDrawElements(n, shortBuffer.remaining(), 5123, MemoryUtil.getAddress(shortBuffer), glDrawElements);
    }
    
    static native void nglDrawElements(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElements = capabilities.glDrawElements;
        BufferChecks.checkFunctionAddress(glDrawElements);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsBO(n, n2, n3, n4, glDrawElements);
    }
    
    static native void nglDrawElementsBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glDrawElements(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDrawElements = capabilities.glDrawElements;
        BufferChecks.checkFunctionAddress(glDrawElements);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, n2);
        nglDrawElements(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glDrawElements);
    }
    
    public static void glDrawBuffer(final int n) {
        final long glDrawBuffer = GLContext.getCapabilities().glDrawBuffer;
        BufferChecks.checkFunctionAddress(glDrawBuffer);
        nglDrawBuffer(n, glDrawBuffer);
    }
    
    static native void nglDrawBuffer(final int p0, final long p1);
    
    public static void glDrawArrays(final int n, final int n2, final int n3) {
        final long glDrawArrays = GLContext.getCapabilities().glDrawArrays;
        BufferChecks.checkFunctionAddress(glDrawArrays);
        nglDrawArrays(n, n2, n3, glDrawArrays);
    }
    
    static native void nglDrawArrays(final int p0, final int p1, final int p2, final long p3);
    
    public static void glDepthRange(final double n, final double n2) {
        final long glDepthRange = GLContext.getCapabilities().glDepthRange;
        BufferChecks.checkFunctionAddress(glDepthRange);
        nglDepthRange(n, n2, glDepthRange);
    }
    
    static native void nglDepthRange(final double p0, final double p1, final long p2);
    
    public static void glDepthMask(final boolean b) {
        final long glDepthMask = GLContext.getCapabilities().glDepthMask;
        BufferChecks.checkFunctionAddress(glDepthMask);
        nglDepthMask(b, glDepthMask);
    }
    
    static native void nglDepthMask(final boolean p0, final long p1);
    
    public static void glDepthFunc(final int n) {
        final long glDepthFunc = GLContext.getCapabilities().glDepthFunc;
        BufferChecks.checkFunctionAddress(glDepthFunc);
        nglDepthFunc(n, glDepthFunc);
    }
    
    static native void nglDepthFunc(final int p0, final long p1);
    
    public static void glFeedbackBuffer(final int n, final FloatBuffer floatBuffer) {
        final long glFeedbackBuffer = GLContext.getCapabilities().glFeedbackBuffer;
        BufferChecks.checkFunctionAddress(glFeedbackBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglFeedbackBuffer(floatBuffer.remaining(), n, MemoryUtil.getAddress(floatBuffer), glFeedbackBuffer);
    }
    
    static native void nglFeedbackBuffer(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetPixelMap(final int n, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPixelMapfv = capabilities.glGetPixelMapfv;
        BufferChecks.checkFunctionAddress(glGetPixelMapfv);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, 256);
        nglGetPixelMapfv(n, MemoryUtil.getAddress(floatBuffer), glGetPixelMapfv);
    }
    
    static native void nglGetPixelMapfv(final int p0, final long p1, final long p2);
    
    public static void glGetPixelMapfv(final int n, final long n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPixelMapfv = capabilities.glGetPixelMapfv;
        BufferChecks.checkFunctionAddress(glGetPixelMapfv);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetPixelMapfvBO(n, n2, glGetPixelMapfv);
    }
    
    static native void nglGetPixelMapfvBO(final int p0, final long p1, final long p2);
    
    public static void glGetPixelMapu(final int n, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPixelMapuiv = capabilities.glGetPixelMapuiv;
        BufferChecks.checkFunctionAddress(glGetPixelMapuiv);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, 256);
        nglGetPixelMapuiv(n, MemoryUtil.getAddress(intBuffer), glGetPixelMapuiv);
    }
    
    static native void nglGetPixelMapuiv(final int p0, final long p1, final long p2);
    
    public static void glGetPixelMapuiv(final int n, final long n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPixelMapuiv = capabilities.glGetPixelMapuiv;
        BufferChecks.checkFunctionAddress(glGetPixelMapuiv);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetPixelMapuivBO(n, n2, glGetPixelMapuiv);
    }
    
    static native void nglGetPixelMapuivBO(final int p0, final long p1, final long p2);
    
    public static void glGetPixelMapu(final int n, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPixelMapusv = capabilities.glGetPixelMapusv;
        BufferChecks.checkFunctionAddress(glGetPixelMapusv);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, 256);
        nglGetPixelMapusv(n, MemoryUtil.getAddress(shortBuffer), glGetPixelMapusv);
    }
    
    static native void nglGetPixelMapusv(final int p0, final long p1, final long p2);
    
    public static void glGetPixelMapusv(final int n, final long n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPixelMapusv = capabilities.glGetPixelMapusv;
        BufferChecks.checkFunctionAddress(glGetPixelMapusv);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetPixelMapusvBO(n, n2, glGetPixelMapusv);
    }
    
    static native void nglGetPixelMapusvBO(final int p0, final long p1, final long p2);
    
    public static void glGetMaterial(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetMaterialfv = GLContext.getCapabilities().glGetMaterialfv;
        BufferChecks.checkFunctionAddress(glGetMaterialfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMaterialfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetMaterialfv);
    }
    
    static native void nglGetMaterialfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetMaterial(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetMaterialiv = GLContext.getCapabilities().glGetMaterialiv;
        BufferChecks.checkFunctionAddress(glGetMaterialiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMaterialiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetMaterialiv);
    }
    
    static native void nglGetMaterialiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetMap(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetMapfv = GLContext.getCapabilities().glGetMapfv;
        BufferChecks.checkFunctionAddress(glGetMapfv);
        BufferChecks.checkBuffer(floatBuffer, 256);
        nglGetMapfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetMapfv);
    }
    
    static native void nglGetMapfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetMap(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetMapdv = GLContext.getCapabilities().glGetMapdv;
        BufferChecks.checkFunctionAddress(glGetMapdv);
        BufferChecks.checkBuffer(doubleBuffer, 256);
        nglGetMapdv(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetMapdv);
    }
    
    static native void nglGetMapdv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetMap(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetMapiv = GLContext.getCapabilities().glGetMapiv;
        BufferChecks.checkFunctionAddress(glGetMapiv);
        BufferChecks.checkBuffer(intBuffer, 256);
        nglGetMapiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetMapiv);
    }
    
    static native void nglGetMapiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetLight(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetLightfv = GLContext.getCapabilities().glGetLightfv;
        BufferChecks.checkFunctionAddress(glGetLightfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetLightfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetLightfv);
    }
    
    static native void nglGetLightfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetLight(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetLightiv = GLContext.getCapabilities().glGetLightiv;
        BufferChecks.checkFunctionAddress(glGetLightiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetLightiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetLightiv);
    }
    
    static native void nglGetLightiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetError() {
        final long glGetError = GLContext.getCapabilities().glGetError;
        BufferChecks.checkFunctionAddress(glGetError);
        return nglGetError(glGetError);
    }
    
    static native int nglGetError(final long p0);
    
    public static void glGetClipPlane(final int n, final DoubleBuffer doubleBuffer) {
        final long glGetClipPlane = GLContext.getCapabilities().glGetClipPlane;
        BufferChecks.checkFunctionAddress(glGetClipPlane);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetClipPlane(n, MemoryUtil.getAddress(doubleBuffer), glGetClipPlane);
    }
    
    static native void nglGetClipPlane(final int p0, final long p1, final long p2);
    
    public static void glGetBoolean(final int n, final ByteBuffer byteBuffer) {
        final long glGetBooleanv = GLContext.getCapabilities().glGetBooleanv;
        BufferChecks.checkFunctionAddress(glGetBooleanv);
        BufferChecks.checkBuffer(byteBuffer, 16);
        nglGetBooleanv(n, MemoryUtil.getAddress(byteBuffer), glGetBooleanv);
    }
    
    static native void nglGetBooleanv(final int p0, final long p1, final long p2);
    
    public static boolean glGetBoolean(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetBooleanv = capabilities.glGetBooleanv;
        BufferChecks.checkFunctionAddress(glGetBooleanv);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, 1);
        nglGetBooleanv(n, MemoryUtil.getAddress(bufferByte), glGetBooleanv);
        return bufferByte.get(0) == 1;
    }
    
    public static void glGetDouble(final int n, final DoubleBuffer doubleBuffer) {
        final long glGetDoublev = GLContext.getCapabilities().glGetDoublev;
        BufferChecks.checkFunctionAddress(glGetDoublev);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglGetDoublev(n, MemoryUtil.getAddress(doubleBuffer), glGetDoublev);
    }
    
    static native void nglGetDoublev(final int p0, final long p1, final long p2);
    
    public static double glGetDouble(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetDoublev = capabilities.glGetDoublev;
        BufferChecks.checkFunctionAddress(glGetDoublev);
        final DoubleBuffer bufferDouble = APIUtil.getBufferDouble(capabilities);
        nglGetDoublev(n, MemoryUtil.getAddress(bufferDouble), glGetDoublev);
        return bufferDouble.get(0);
    }
    
    public static void glGetFloat(final int n, final FloatBuffer floatBuffer) {
        final long glGetFloatv = GLContext.getCapabilities().glGetFloatv;
        BufferChecks.checkFunctionAddress(glGetFloatv);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglGetFloatv(n, MemoryUtil.getAddress(floatBuffer), glGetFloatv);
    }
    
    static native void nglGetFloatv(final int p0, final long p1, final long p2);
    
    public static float glGetFloat(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFloatv = capabilities.glGetFloatv;
        BufferChecks.checkFunctionAddress(glGetFloatv);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetFloatv(n, MemoryUtil.getAddress(bufferFloat), glGetFloatv);
        return bufferFloat.get(0);
    }
    
    public static void glGetInteger(final int n, final IntBuffer intBuffer) {
        final long glGetIntegerv = GLContext.getCapabilities().glGetIntegerv;
        BufferChecks.checkFunctionAddress(glGetIntegerv);
        BufferChecks.checkBuffer(intBuffer, 16);
        nglGetIntegerv(n, MemoryUtil.getAddress(intBuffer), glGetIntegerv);
    }
    
    static native void nglGetIntegerv(final int p0, final long p1, final long p2);
    
    public static int glGetInteger(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetIntegerv = capabilities.glGetIntegerv;
        BufferChecks.checkFunctionAddress(glGetIntegerv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetIntegerv(n, MemoryUtil.getAddress(bufferInt), glGetIntegerv);
        return bufferInt.get(0);
    }
    
    public static void glGenTextures(final IntBuffer intBuffer) {
        final long glGenTextures = GLContext.getCapabilities().glGenTextures;
        BufferChecks.checkFunctionAddress(glGenTextures);
        BufferChecks.checkDirect(intBuffer);
        nglGenTextures(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenTextures);
    }
    
    static native void nglGenTextures(final int p0, final long p1, final long p2);
    
    public static int glGenTextures() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenTextures = capabilities.glGenTextures;
        BufferChecks.checkFunctionAddress(glGenTextures);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenTextures(1, MemoryUtil.getAddress(bufferInt), glGenTextures);
        return bufferInt.get(0);
    }
    
    public static int glGenLists(final int n) {
        final long glGenLists = GLContext.getCapabilities().glGenLists;
        BufferChecks.checkFunctionAddress(glGenLists);
        return nglGenLists(n, glGenLists);
    }
    
    static native int nglGenLists(final int p0, final long p1);
    
    public static void glFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final long glFrustum = GLContext.getCapabilities().glFrustum;
        BufferChecks.checkFunctionAddress(glFrustum);
        nglFrustum(n, n2, n3, n4, n5, n6, glFrustum);
    }
    
    static native void nglFrustum(final double p0, final double p1, final double p2, final double p3, final double p4, final double p5, final long p6);
    
    public static void glFrontFace(final int n) {
        final long glFrontFace = GLContext.getCapabilities().glFrontFace;
        BufferChecks.checkFunctionAddress(glFrontFace);
        nglFrontFace(n, glFrontFace);
    }
    
    static native void nglFrontFace(final int p0, final long p1);
    
    public static void glFogf(final int n, final float n2) {
        final long glFogf = GLContext.getCapabilities().glFogf;
        BufferChecks.checkFunctionAddress(glFogf);
        nglFogf(n, n2, glFogf);
    }
    
    static native void nglFogf(final int p0, final float p1, final long p2);
    
    public static void glFogi(final int n, final int n2) {
        final long glFogi = GLContext.getCapabilities().glFogi;
        BufferChecks.checkFunctionAddress(glFogi);
        nglFogi(n, n2, glFogi);
    }
    
    static native void nglFogi(final int p0, final int p1, final long p2);
    
    public static void glFog(final int n, final FloatBuffer floatBuffer) {
        final long glFogfv = GLContext.getCapabilities().glFogfv;
        BufferChecks.checkFunctionAddress(glFogfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglFogfv(n, MemoryUtil.getAddress(floatBuffer), glFogfv);
    }
    
    static native void nglFogfv(final int p0, final long p1, final long p2);
    
    public static void glFog(final int n, final IntBuffer intBuffer) {
        final long glFogiv = GLContext.getCapabilities().glFogiv;
        BufferChecks.checkFunctionAddress(glFogiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglFogiv(n, MemoryUtil.getAddress(intBuffer), glFogiv);
    }
    
    static native void nglFogiv(final int p0, final long p1, final long p2);
    
    public static void glFlush() {
        final long glFlush = GLContext.getCapabilities().glFlush;
        BufferChecks.checkFunctionAddress(glFlush);
        nglFlush(glFlush);
    }
    
    static native void nglFlush(final long p0);
    
    public static void glFinish() {
        final long glFinish = GLContext.getCapabilities().glFinish;
        BufferChecks.checkFunctionAddress(glFinish);
        nglFinish(glFinish);
    }
    
    static native void nglFinish(final long p0);
    
    public static ByteBuffer glGetPointer(final int n, final long n2) {
        final long glGetPointerv = GLContext.getCapabilities().glGetPointerv;
        BufferChecks.checkFunctionAddress(glGetPointerv);
        final ByteBuffer nglGetPointerv = nglGetPointerv(n, n2, glGetPointerv);
        return (LWJGLUtil.CHECKS && nglGetPointerv == null) ? null : nglGetPointerv.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglGetPointerv(final int p0, final long p1, final long p2);
    
    public static boolean glIsEnabled(final int n) {
        final long glIsEnabled = GLContext.getCapabilities().glIsEnabled;
        BufferChecks.checkFunctionAddress(glIsEnabled);
        return nglIsEnabled(n, glIsEnabled);
    }
    
    static native boolean nglIsEnabled(final int p0, final long p1);
    
    public static void glInterleavedArrays(final int n, final int n2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glInterleavedArrays = capabilities.glInterleavedArrays;
        BufferChecks.checkFunctionAddress(glInterleavedArrays);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglInterleavedArrays(n, n2, MemoryUtil.getAddress(byteBuffer), glInterleavedArrays);
    }
    
    public static void glInterleavedArrays(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glInterleavedArrays = capabilities.glInterleavedArrays;
        BufferChecks.checkFunctionAddress(glInterleavedArrays);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglInterleavedArrays(n, n2, MemoryUtil.getAddress(doubleBuffer), glInterleavedArrays);
    }
    
    public static void glInterleavedArrays(final int n, final int n2, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glInterleavedArrays = capabilities.glInterleavedArrays;
        BufferChecks.checkFunctionAddress(glInterleavedArrays);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglInterleavedArrays(n, n2, MemoryUtil.getAddress(floatBuffer), glInterleavedArrays);
    }
    
    public static void glInterleavedArrays(final int n, final int n2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glInterleavedArrays = capabilities.glInterleavedArrays;
        BufferChecks.checkFunctionAddress(glInterleavedArrays);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglInterleavedArrays(n, n2, MemoryUtil.getAddress(intBuffer), glInterleavedArrays);
    }
    
    public static void glInterleavedArrays(final int n, final int n2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glInterleavedArrays = capabilities.glInterleavedArrays;
        BufferChecks.checkFunctionAddress(glInterleavedArrays);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglInterleavedArrays(n, n2, MemoryUtil.getAddress(shortBuffer), glInterleavedArrays);
    }
    
    static native void nglInterleavedArrays(final int p0, final int p1, final long p2, final long p3);
    
    public static void glInterleavedArrays(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glInterleavedArrays = capabilities.glInterleavedArrays;
        BufferChecks.checkFunctionAddress(glInterleavedArrays);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglInterleavedArraysBO(n, n2, n3, glInterleavedArrays);
    }
    
    static native void nglInterleavedArraysBO(final int p0, final int p1, final long p2, final long p3);
    
    public static void glInitNames() {
        final long glInitNames = GLContext.getCapabilities().glInitNames;
        BufferChecks.checkFunctionAddress(glInitNames);
        nglInitNames(glInitNames);
    }
    
    static native void nglInitNames(final long p0);
    
    public static void glHint(final int n, final int n2) {
        final long glHint = GLContext.getCapabilities().glHint;
        BufferChecks.checkFunctionAddress(glHint);
        nglHint(n, n2, glHint);
    }
    
    static native void nglHint(final int p0, final int p1, final long p2);
    
    public static void glGetTexParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetTexParameterfv = GLContext.getCapabilities().glGetTexParameterfv;
        BufferChecks.checkFunctionAddress(glGetTexParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetTexParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetTexParameterfv);
    }
    
    static native void nglGetTexParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetTexParameterf(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexParameterfv = capabilities.glGetTexParameterfv;
        BufferChecks.checkFunctionAddress(glGetTexParameterfv);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetTexParameterfv(n, n2, MemoryUtil.getAddress(bufferFloat), glGetTexParameterfv);
        return bufferFloat.get(0);
    }
    
    public static void glGetTexParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTexParameteriv = GLContext.getCapabilities().glGetTexParameteriv;
        BufferChecks.checkFunctionAddress(glGetTexParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTexParameteriv);
    }
    
    static native void nglGetTexParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTexParameteri(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexParameteriv = capabilities.glGetTexParameteriv;
        BufferChecks.checkFunctionAddress(glGetTexParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTexParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTexParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glGetTexLevelParameter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetTexLevelParameterfv = GLContext.getCapabilities().glGetTexLevelParameterfv;
        BufferChecks.checkFunctionAddress(glGetTexLevelParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetTexLevelParameterfv(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetTexLevelParameterfv);
    }
    
    static native void nglGetTexLevelParameterfv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static float glGetTexLevelParameterf(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexLevelParameterfv = capabilities.glGetTexLevelParameterfv;
        BufferChecks.checkFunctionAddress(glGetTexLevelParameterfv);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetTexLevelParameterfv(n, n2, n3, MemoryUtil.getAddress(bufferFloat), glGetTexLevelParameterfv);
        return bufferFloat.get(0);
    }
    
    public static void glGetTexLevelParameter(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetTexLevelParameteriv = GLContext.getCapabilities().glGetTexLevelParameteriv;
        BufferChecks.checkFunctionAddress(glGetTexLevelParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexLevelParameteriv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetTexLevelParameteriv);
    }
    
    static native void nglGetTexLevelParameteriv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetTexLevelParameteri(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexLevelParameteriv = capabilities.glGetTexLevelParameteriv;
        BufferChecks.checkFunctionAddress(glGetTexLevelParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTexLevelParameteriv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetTexLevelParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glGetTexImage(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexImage = capabilities.glGetTexImage;
        BufferChecks.checkFunctionAddress(glGetTexImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n3, n4, 1, 1, 1));
        nglGetTexImage(n, n2, n3, n4, MemoryUtil.getAddress(byteBuffer), glGetTexImage);
    }
    
    public static void glGetTexImage(final int n, final int n2, final int n3, final int n4, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexImage = capabilities.glGetTexImage;
        BufferChecks.checkFunctionAddress(glGetTexImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n3, n4, 1, 1, 1));
        nglGetTexImage(n, n2, n3, n4, MemoryUtil.getAddress(doubleBuffer), glGetTexImage);
    }
    
    public static void glGetTexImage(final int n, final int n2, final int n3, final int n4, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexImage = capabilities.glGetTexImage;
        BufferChecks.checkFunctionAddress(glGetTexImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n3, n4, 1, 1, 1));
        nglGetTexImage(n, n2, n3, n4, MemoryUtil.getAddress(floatBuffer), glGetTexImage);
    }
    
    public static void glGetTexImage(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexImage = capabilities.glGetTexImage;
        BufferChecks.checkFunctionAddress(glGetTexImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n3, n4, 1, 1, 1));
        nglGetTexImage(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glGetTexImage);
    }
    
    public static void glGetTexImage(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexImage = capabilities.glGetTexImage;
        BufferChecks.checkFunctionAddress(glGetTexImage);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n3, n4, 1, 1, 1));
        nglGetTexImage(n, n2, n3, n4, MemoryUtil.getAddress(shortBuffer), glGetTexImage);
    }
    
    static native void nglGetTexImage(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetTexImage(final int n, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexImage = capabilities.glGetTexImage;
        BufferChecks.checkFunctionAddress(glGetTexImage);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetTexImageBO(n, n2, n3, n4, n5, glGetTexImage);
    }
    
    static native void nglGetTexImageBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetTexGen(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTexGeniv = GLContext.getCapabilities().glGetTexGeniv;
        BufferChecks.checkFunctionAddress(glGetTexGeniv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexGeniv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTexGeniv);
    }
    
    static native void nglGetTexGeniv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTexGeni(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexGeniv = capabilities.glGetTexGeniv;
        BufferChecks.checkFunctionAddress(glGetTexGeniv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTexGeniv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTexGeniv);
        return bufferInt.get(0);
    }
    
    public static void glGetTexGen(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetTexGenfv = GLContext.getCapabilities().glGetTexGenfv;
        BufferChecks.checkFunctionAddress(glGetTexGenfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetTexGenfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetTexGenfv);
    }
    
    static native void nglGetTexGenfv(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetTexGenf(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexGenfv = capabilities.glGetTexGenfv;
        BufferChecks.checkFunctionAddress(glGetTexGenfv);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetTexGenfv(n, n2, MemoryUtil.getAddress(bufferFloat), glGetTexGenfv);
        return bufferFloat.get(0);
    }
    
    public static void glGetTexGen(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glGetTexGendv = GLContext.getCapabilities().glGetTexGendv;
        BufferChecks.checkFunctionAddress(glGetTexGendv);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetTexGendv(n, n2, MemoryUtil.getAddress(doubleBuffer), glGetTexGendv);
    }
    
    static native void nglGetTexGendv(final int p0, final int p1, final long p2, final long p3);
    
    public static double glGetTexGend(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexGendv = capabilities.glGetTexGendv;
        BufferChecks.checkFunctionAddress(glGetTexGendv);
        final DoubleBuffer bufferDouble = APIUtil.getBufferDouble(capabilities);
        nglGetTexGendv(n, n2, MemoryUtil.getAddress(bufferDouble), glGetTexGendv);
        return bufferDouble.get(0);
    }
    
    public static void glGetTexEnv(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTexEnviv = GLContext.getCapabilities().glGetTexEnviv;
        BufferChecks.checkFunctionAddress(glGetTexEnviv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexEnviv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTexEnviv);
    }
    
    static native void nglGetTexEnviv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTexEnvi(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexEnviv = capabilities.glGetTexEnviv;
        BufferChecks.checkFunctionAddress(glGetTexEnviv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTexEnviv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTexEnviv);
        return bufferInt.get(0);
    }
    
    public static void glGetTexEnv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetTexEnvfv = GLContext.getCapabilities().glGetTexEnvfv;
        BufferChecks.checkFunctionAddress(glGetTexEnvfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetTexEnvfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetTexEnvfv);
    }
    
    static native void nglGetTexEnvfv(final int p0, final int p1, final long p2, final long p3);
    
    public static float glGetTexEnvf(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexEnvfv = capabilities.glGetTexEnvfv;
        BufferChecks.checkFunctionAddress(glGetTexEnvfv);
        final FloatBuffer bufferFloat = APIUtil.getBufferFloat(capabilities);
        nglGetTexEnvfv(n, n2, MemoryUtil.getAddress(bufferFloat), glGetTexEnvfv);
        return bufferFloat.get(0);
    }
    
    public static String glGetString(final int n) {
        final long glGetString = GLContext.getCapabilities().glGetString;
        BufferChecks.checkFunctionAddress(glGetString);
        return nglGetString(n, glGetString);
    }
    
    static native String nglGetString(final int p0, final long p1);
    
    public static void glGetPolygonStipple(final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPolygonStipple = capabilities.glGetPolygonStipple;
        BufferChecks.checkFunctionAddress(glGetPolygonStipple);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, 128);
        nglGetPolygonStipple(MemoryUtil.getAddress(byteBuffer), glGetPolygonStipple);
    }
    
    static native void nglGetPolygonStipple(final long p0, final long p1);
    
    public static void glGetPolygonStipple(final long n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPolygonStipple = capabilities.glGetPolygonStipple;
        BufferChecks.checkFunctionAddress(glGetPolygonStipple);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetPolygonStippleBO(n, glGetPolygonStipple);
    }
    
    static native void nglGetPolygonStippleBO(final long p0, final long p1);
    
    public static boolean glIsList(final int n) {
        final long glIsList = GLContext.getCapabilities().glIsList;
        BufferChecks.checkFunctionAddress(glIsList);
        return nglIsList(n, glIsList);
    }
    
    static native boolean nglIsList(final int p0, final long p1);
    
    public static void glMaterialf(final int n, final int n2, final float n3) {
        final long glMaterialf = GLContext.getCapabilities().glMaterialf;
        BufferChecks.checkFunctionAddress(glMaterialf);
        nglMaterialf(n, n2, n3, glMaterialf);
    }
    
    static native void nglMaterialf(final int p0, final int p1, final float p2, final long p3);
    
    public static void glMateriali(final int n, final int n2, final int n3) {
        final long glMateriali = GLContext.getCapabilities().glMateriali;
        BufferChecks.checkFunctionAddress(glMateriali);
        nglMateriali(n, n2, n3, glMateriali);
    }
    
    static native void nglMateriali(final int p0, final int p1, final int p2, final long p3);
    
    public static void glMaterial(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glMaterialfv = GLContext.getCapabilities().glMaterialfv;
        BufferChecks.checkFunctionAddress(glMaterialfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglMaterialfv(n, n2, MemoryUtil.getAddress(floatBuffer), glMaterialfv);
    }
    
    static native void nglMaterialfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMaterial(final int n, final int n2, final IntBuffer intBuffer) {
        final long glMaterialiv = GLContext.getCapabilities().glMaterialiv;
        BufferChecks.checkFunctionAddress(glMaterialiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglMaterialiv(n, n2, MemoryUtil.getAddress(intBuffer), glMaterialiv);
    }
    
    static native void nglMaterialiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMapGrid1f(final int n, final float n2, final float n3) {
        final long glMapGrid1f = GLContext.getCapabilities().glMapGrid1f;
        BufferChecks.checkFunctionAddress(glMapGrid1f);
        nglMapGrid1f(n, n2, n3, glMapGrid1f);
    }
    
    static native void nglMapGrid1f(final int p0, final float p1, final float p2, final long p3);
    
    public static void glMapGrid1d(final int n, final double n2, final double n3) {
        final long glMapGrid1d = GLContext.getCapabilities().glMapGrid1d;
        BufferChecks.checkFunctionAddress(glMapGrid1d);
        nglMapGrid1d(n, n2, n3, glMapGrid1d);
    }
    
    static native void nglMapGrid1d(final int p0, final double p1, final double p2, final long p3);
    
    public static void glMapGrid2f(final int n, final float n2, final float n3, final int n4, final float n5, final float n6) {
        final long glMapGrid2f = GLContext.getCapabilities().glMapGrid2f;
        BufferChecks.checkFunctionAddress(glMapGrid2f);
        nglMapGrid2f(n, n2, n3, n4, n5, n6, glMapGrid2f);
    }
    
    static native void nglMapGrid2f(final int p0, final float p1, final float p2, final int p3, final float p4, final float p5, final long p6);
    
    public static void glMapGrid2d(final int n, final double n2, final double n3, final int n4, final double n5, final double n6) {
        final long glMapGrid2d = GLContext.getCapabilities().glMapGrid2d;
        BufferChecks.checkFunctionAddress(glMapGrid2d);
        nglMapGrid2d(n, n2, n3, n4, n5, n6, glMapGrid2d);
    }
    
    static native void nglMapGrid2d(final int p0, final double p1, final double p2, final int p3, final double p4, final double p5, final long p6);
    
    public static void glMap2f(final int n, final float n2, final float n3, final int n4, final int n5, final float n6, final float n7, final int n8, final int n9, final FloatBuffer floatBuffer) {
        final long glMap2f = GLContext.getCapabilities().glMap2f;
        BufferChecks.checkFunctionAddress(glMap2f);
        BufferChecks.checkDirect(floatBuffer);
        nglMap2f(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(floatBuffer), glMap2f);
    }
    
    static native void nglMap2f(final int p0, final float p1, final float p2, final int p3, final int p4, final float p5, final float p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glMap2d(final int n, final double n2, final double n3, final int n4, final int n5, final double n6, final double n7, final int n8, final int n9, final DoubleBuffer doubleBuffer) {
        final long glMap2d = GLContext.getCapabilities().glMap2d;
        BufferChecks.checkFunctionAddress(glMap2d);
        BufferChecks.checkDirect(doubleBuffer);
        nglMap2d(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.getAddress(doubleBuffer), glMap2d);
    }
    
    static native void nglMap2d(final int p0, final double p1, final double p2, final int p3, final int p4, final double p5, final double p6, final int p7, final int p8, final long p9, final long p10);
    
    public static void glMap1f(final int n, final float n2, final float n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final long glMap1f = GLContext.getCapabilities().glMap1f;
        BufferChecks.checkFunctionAddress(glMap1f);
        BufferChecks.checkDirect(floatBuffer);
        nglMap1f(n, n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), glMap1f);
    }
    
    static native void nglMap1f(final int p0, final float p1, final float p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glMap1d(final int n, final double n2, final double n3, final int n4, final int n5, final DoubleBuffer doubleBuffer) {
        final long glMap1d = GLContext.getCapabilities().glMap1d;
        BufferChecks.checkFunctionAddress(glMap1d);
        BufferChecks.checkDirect(doubleBuffer);
        nglMap1d(n, n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), glMap1d);
    }
    
    static native void nglMap1d(final int p0, final double p1, final double p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glLogicOp(final int n) {
        final long glLogicOp = GLContext.getCapabilities().glLogicOp;
        BufferChecks.checkFunctionAddress(glLogicOp);
        nglLogicOp(n, glLogicOp);
    }
    
    static native void nglLogicOp(final int p0, final long p1);
    
    public static void glLoadName(final int n) {
        final long glLoadName = GLContext.getCapabilities().glLoadName;
        BufferChecks.checkFunctionAddress(glLoadName);
        nglLoadName(n, glLoadName);
    }
    
    static native void nglLoadName(final int p0, final long p1);
    
    public static void glLoadMatrix(final FloatBuffer floatBuffer) {
        final long glLoadMatrixf = GLContext.getCapabilities().glLoadMatrixf;
        BufferChecks.checkFunctionAddress(glLoadMatrixf);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglLoadMatrixf(MemoryUtil.getAddress(floatBuffer), glLoadMatrixf);
    }
    
    static native void nglLoadMatrixf(final long p0, final long p1);
    
    public static void glLoadMatrix(final DoubleBuffer doubleBuffer) {
        final long glLoadMatrixd = GLContext.getCapabilities().glLoadMatrixd;
        BufferChecks.checkFunctionAddress(glLoadMatrixd);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglLoadMatrixd(MemoryUtil.getAddress(doubleBuffer), glLoadMatrixd);
    }
    
    static native void nglLoadMatrixd(final long p0, final long p1);
    
    public static void glLoadIdentity() {
        final long glLoadIdentity = GLContext.getCapabilities().glLoadIdentity;
        BufferChecks.checkFunctionAddress(glLoadIdentity);
        nglLoadIdentity(glLoadIdentity);
    }
    
    static native void nglLoadIdentity(final long p0);
    
    public static void glListBase(final int n) {
        final long glListBase = GLContext.getCapabilities().glListBase;
        BufferChecks.checkFunctionAddress(glListBase);
        nglListBase(n, glListBase);
    }
    
    static native void nglListBase(final int p0, final long p1);
    
    public static void glLineWidth(final float n) {
        final long glLineWidth = GLContext.getCapabilities().glLineWidth;
        BufferChecks.checkFunctionAddress(glLineWidth);
        nglLineWidth(n, glLineWidth);
    }
    
    static native void nglLineWidth(final float p0, final long p1);
    
    public static void glLineStipple(final int n, final short n2) {
        final long glLineStipple = GLContext.getCapabilities().glLineStipple;
        BufferChecks.checkFunctionAddress(glLineStipple);
        nglLineStipple(n, n2, glLineStipple);
    }
    
    static native void nglLineStipple(final int p0, final short p1, final long p2);
    
    public static void glLightModelf(final int n, final float n2) {
        final long glLightModelf = GLContext.getCapabilities().glLightModelf;
        BufferChecks.checkFunctionAddress(glLightModelf);
        nglLightModelf(n, n2, glLightModelf);
    }
    
    static native void nglLightModelf(final int p0, final float p1, final long p2);
    
    public static void glLightModeli(final int n, final int n2) {
        final long glLightModeli = GLContext.getCapabilities().glLightModeli;
        BufferChecks.checkFunctionAddress(glLightModeli);
        nglLightModeli(n, n2, glLightModeli);
    }
    
    static native void nglLightModeli(final int p0, final int p1, final long p2);
    
    public static void glLightModel(final int n, final FloatBuffer floatBuffer) {
        final long glLightModelfv = GLContext.getCapabilities().glLightModelfv;
        BufferChecks.checkFunctionAddress(glLightModelfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglLightModelfv(n, MemoryUtil.getAddress(floatBuffer), glLightModelfv);
    }
    
    static native void nglLightModelfv(final int p0, final long p1, final long p2);
    
    public static void glLightModel(final int n, final IntBuffer intBuffer) {
        final long glLightModeliv = GLContext.getCapabilities().glLightModeliv;
        BufferChecks.checkFunctionAddress(glLightModeliv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglLightModeliv(n, MemoryUtil.getAddress(intBuffer), glLightModeliv);
    }
    
    static native void nglLightModeliv(final int p0, final long p1, final long p2);
    
    public static void glLightf(final int n, final int n2, final float n3) {
        final long glLightf = GLContext.getCapabilities().glLightf;
        BufferChecks.checkFunctionAddress(glLightf);
        nglLightf(n, n2, n3, glLightf);
    }
    
    static native void nglLightf(final int p0, final int p1, final float p2, final long p3);
    
    public static void glLighti(final int n, final int n2, final int n3) {
        final long glLighti = GLContext.getCapabilities().glLighti;
        BufferChecks.checkFunctionAddress(glLighti);
        nglLighti(n, n2, n3, glLighti);
    }
    
    static native void nglLighti(final int p0, final int p1, final int p2, final long p3);
    
    public static void glLight(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glLightfv = GLContext.getCapabilities().glLightfv;
        BufferChecks.checkFunctionAddress(glLightfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglLightfv(n, n2, MemoryUtil.getAddress(floatBuffer), glLightfv);
    }
    
    static native void nglLightfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glLight(final int n, final int n2, final IntBuffer intBuffer) {
        final long glLightiv = GLContext.getCapabilities().glLightiv;
        BufferChecks.checkFunctionAddress(glLightiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglLightiv(n, n2, MemoryUtil.getAddress(intBuffer), glLightiv);
    }
    
    static native void nglLightiv(final int p0, final int p1, final long p2, final long p3);
    
    public static boolean glIsTexture(final int n) {
        final long glIsTexture = GLContext.getCapabilities().glIsTexture;
        BufferChecks.checkFunctionAddress(glIsTexture);
        return nglIsTexture(n, glIsTexture);
    }
    
    static native boolean nglIsTexture(final int p0, final long p1);
    
    public static void glMatrixMode(final int n) {
        final long glMatrixMode = GLContext.getCapabilities().glMatrixMode;
        BufferChecks.checkFunctionAddress(glMatrixMode);
        nglMatrixMode(n, glMatrixMode);
    }
    
    static native void nglMatrixMode(final int p0, final long p1);
    
    public static void glPolygonStipple(final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPolygonStipple = capabilities.glPolygonStipple;
        BufferChecks.checkFunctionAddress(glPolygonStipple);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, 128);
        nglPolygonStipple(MemoryUtil.getAddress(byteBuffer), glPolygonStipple);
    }
    
    static native void nglPolygonStipple(final long p0, final long p1);
    
    public static void glPolygonStipple(final long n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPolygonStipple = capabilities.glPolygonStipple;
        BufferChecks.checkFunctionAddress(glPolygonStipple);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglPolygonStippleBO(n, glPolygonStipple);
    }
    
    static native void nglPolygonStippleBO(final long p0, final long p1);
    
    public static void glPolygonOffset(final float n, final float n2) {
        final long glPolygonOffset = GLContext.getCapabilities().glPolygonOffset;
        BufferChecks.checkFunctionAddress(glPolygonOffset);
        nglPolygonOffset(n, n2, glPolygonOffset);
    }
    
    static native void nglPolygonOffset(final float p0, final float p1, final long p2);
    
    public static void glPolygonMode(final int n, final int n2) {
        final long glPolygonMode = GLContext.getCapabilities().glPolygonMode;
        BufferChecks.checkFunctionAddress(glPolygonMode);
        nglPolygonMode(n, n2, glPolygonMode);
    }
    
    static native void nglPolygonMode(final int p0, final int p1, final long p2);
    
    public static void glPointSize(final float n) {
        final long glPointSize = GLContext.getCapabilities().glPointSize;
        BufferChecks.checkFunctionAddress(glPointSize);
        nglPointSize(n, glPointSize);
    }
    
    static native void nglPointSize(final float p0, final long p1);
    
    public static void glPixelZoom(final float n, final float n2) {
        final long glPixelZoom = GLContext.getCapabilities().glPixelZoom;
        BufferChecks.checkFunctionAddress(glPixelZoom);
        nglPixelZoom(n, n2, glPixelZoom);
    }
    
    static native void nglPixelZoom(final float p0, final float p1, final long p2);
    
    public static void glPixelTransferf(final int n, final float n2) {
        final long glPixelTransferf = GLContext.getCapabilities().glPixelTransferf;
        BufferChecks.checkFunctionAddress(glPixelTransferf);
        nglPixelTransferf(n, n2, glPixelTransferf);
    }
    
    static native void nglPixelTransferf(final int p0, final float p1, final long p2);
    
    public static void glPixelTransferi(final int n, final int n2) {
        final long glPixelTransferi = GLContext.getCapabilities().glPixelTransferi;
        BufferChecks.checkFunctionAddress(glPixelTransferi);
        nglPixelTransferi(n, n2, glPixelTransferi);
    }
    
    static native void nglPixelTransferi(final int p0, final int p1, final long p2);
    
    public static void glPixelStoref(final int n, final float n2) {
        final long glPixelStoref = GLContext.getCapabilities().glPixelStoref;
        BufferChecks.checkFunctionAddress(glPixelStoref);
        nglPixelStoref(n, n2, glPixelStoref);
    }
    
    static native void nglPixelStoref(final int p0, final float p1, final long p2);
    
    public static void glPixelStorei(final int n, final int n2) {
        final long glPixelStorei = GLContext.getCapabilities().glPixelStorei;
        BufferChecks.checkFunctionAddress(glPixelStorei);
        nglPixelStorei(n, n2, glPixelStorei);
    }
    
    static native void nglPixelStorei(final int p0, final int p1, final long p2);
    
    public static void glPixelMap(final int n, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPixelMapfv = capabilities.glPixelMapfv;
        BufferChecks.checkFunctionAddress(glPixelMapfv);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglPixelMapfv(n, floatBuffer.remaining(), MemoryUtil.getAddress(floatBuffer), glPixelMapfv);
    }
    
    static native void nglPixelMapfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glPixelMapfv(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPixelMapfv = capabilities.glPixelMapfv;
        BufferChecks.checkFunctionAddress(glPixelMapfv);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglPixelMapfvBO(n, n2, n3, glPixelMapfv);
    }
    
    static native void nglPixelMapfvBO(final int p0, final int p1, final long p2, final long p3);
    
    public static void glPixelMapu(final int n, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPixelMapuiv = capabilities.glPixelMapuiv;
        BufferChecks.checkFunctionAddress(glPixelMapuiv);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglPixelMapuiv(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glPixelMapuiv);
    }
    
    static native void nglPixelMapuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glPixelMapuiv(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPixelMapuiv = capabilities.glPixelMapuiv;
        BufferChecks.checkFunctionAddress(glPixelMapuiv);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglPixelMapuivBO(n, n2, n3, glPixelMapuiv);
    }
    
    static native void nglPixelMapuivBO(final int p0, final int p1, final long p2, final long p3);
    
    public static void glPixelMapu(final int n, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPixelMapusv = capabilities.glPixelMapusv;
        BufferChecks.checkFunctionAddress(glPixelMapusv);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglPixelMapusv(n, shortBuffer.remaining(), MemoryUtil.getAddress(shortBuffer), glPixelMapusv);
    }
    
    static native void nglPixelMapusv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glPixelMapusv(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPixelMapusv = capabilities.glPixelMapusv;
        BufferChecks.checkFunctionAddress(glPixelMapusv);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglPixelMapusvBO(n, n2, n3, glPixelMapusv);
    }
    
    static native void nglPixelMapusvBO(final int p0, final int p1, final long p2, final long p3);
    
    public static void glPassThrough(final float n) {
        final long glPassThrough = GLContext.getCapabilities().glPassThrough;
        BufferChecks.checkFunctionAddress(glPassThrough);
        nglPassThrough(n, glPassThrough);
    }
    
    static native void nglPassThrough(final float p0, final long p1);
    
    public static void glOrtho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final long glOrtho = GLContext.getCapabilities().glOrtho;
        BufferChecks.checkFunctionAddress(glOrtho);
        nglOrtho(n, n2, n3, n4, n5, n6, glOrtho);
    }
    
    static native void nglOrtho(final double p0, final double p1, final double p2, final double p3, final double p4, final double p5, final long p6);
    
    public static void glNormalPointer(final int n, final ByteBuffer gl11_glNormalPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glNormalPointer = capabilities.glNormalPointer;
        BufferChecks.checkFunctionAddress(glNormalPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glNormalPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glNormalPointer_pointer = gl11_glNormalPointer_pointer;
        }
        nglNormalPointer(5120, n, MemoryUtil.getAddress(gl11_glNormalPointer_pointer), glNormalPointer);
    }
    
    public static void glNormalPointer(final int n, final DoubleBuffer gl11_glNormalPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glNormalPointer = capabilities.glNormalPointer;
        BufferChecks.checkFunctionAddress(glNormalPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glNormalPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glNormalPointer_pointer = gl11_glNormalPointer_pointer;
        }
        nglNormalPointer(5130, n, MemoryUtil.getAddress(gl11_glNormalPointer_pointer), glNormalPointer);
    }
    
    public static void glNormalPointer(final int n, final FloatBuffer gl11_glNormalPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glNormalPointer = capabilities.glNormalPointer;
        BufferChecks.checkFunctionAddress(glNormalPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glNormalPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glNormalPointer_pointer = gl11_glNormalPointer_pointer;
        }
        nglNormalPointer(5126, n, MemoryUtil.getAddress(gl11_glNormalPointer_pointer), glNormalPointer);
    }
    
    public static void glNormalPointer(final int n, final IntBuffer gl11_glNormalPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glNormalPointer = capabilities.glNormalPointer;
        BufferChecks.checkFunctionAddress(glNormalPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glNormalPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glNormalPointer_pointer = gl11_glNormalPointer_pointer;
        }
        nglNormalPointer(5124, n, MemoryUtil.getAddress(gl11_glNormalPointer_pointer), glNormalPointer);
    }
    
    static native void nglNormalPointer(final int p0, final int p1, final long p2, final long p3);
    
    public static void glNormalPointer(final int n, final int n2, final long n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glNormalPointer = capabilities.glNormalPointer;
        BufferChecks.checkFunctionAddress(glNormalPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglNormalPointerBO(n, n2, n3, glNormalPointer);
    }
    
    static native void nglNormalPointerBO(final int p0, final int p1, final long p2, final long p3);
    
    public static void glNormalPointer(final int n, final int n2, final ByteBuffer gl11_glNormalPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glNormalPointer = capabilities.glNormalPointer;
        BufferChecks.checkFunctionAddress(glNormalPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glNormalPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glNormalPointer_pointer = gl11_glNormalPointer_pointer;
        }
        nglNormalPointer(n, n2, MemoryUtil.getAddress(gl11_glNormalPointer_pointer), glNormalPointer);
    }
    
    public static void glNormal3b(final byte b, final byte b2, final byte b3) {
        final long glNormal3b = GLContext.getCapabilities().glNormal3b;
        BufferChecks.checkFunctionAddress(glNormal3b);
        nglNormal3b(b, b2, b3, glNormal3b);
    }
    
    static native void nglNormal3b(final byte p0, final byte p1, final byte p2, final long p3);
    
    public static void glNormal3f(final float n, final float n2, final float n3) {
        final long glNormal3f = GLContext.getCapabilities().glNormal3f;
        BufferChecks.checkFunctionAddress(glNormal3f);
        nglNormal3f(n, n2, n3, glNormal3f);
    }
    
    static native void nglNormal3f(final float p0, final float p1, final float p2, final long p3);
    
    public static void glNormal3d(final double n, final double n2, final double n3) {
        final long glNormal3d = GLContext.getCapabilities().glNormal3d;
        BufferChecks.checkFunctionAddress(glNormal3d);
        nglNormal3d(n, n2, n3, glNormal3d);
    }
    
    static native void nglNormal3d(final double p0, final double p1, final double p2, final long p3);
    
    public static void glNormal3i(final int n, final int n2, final int n3) {
        final long glNormal3i = GLContext.getCapabilities().glNormal3i;
        BufferChecks.checkFunctionAddress(glNormal3i);
        nglNormal3i(n, n2, n3, glNormal3i);
    }
    
    static native void nglNormal3i(final int p0, final int p1, final int p2, final long p3);
    
    public static void glNewList(final int n, final int n2) {
        final long glNewList = GLContext.getCapabilities().glNewList;
        BufferChecks.checkFunctionAddress(glNewList);
        nglNewList(n, n2, glNewList);
    }
    
    static native void nglNewList(final int p0, final int p1, final long p2);
    
    public static void glEndList() {
        final long glEndList = GLContext.getCapabilities().glEndList;
        BufferChecks.checkFunctionAddress(glEndList);
        nglEndList(glEndList);
    }
    
    static native void nglEndList(final long p0);
    
    public static void glMultMatrix(final FloatBuffer floatBuffer) {
        final long glMultMatrixf = GLContext.getCapabilities().glMultMatrixf;
        BufferChecks.checkFunctionAddress(glMultMatrixf);
        BufferChecks.checkBuffer(floatBuffer, 16);
        nglMultMatrixf(MemoryUtil.getAddress(floatBuffer), glMultMatrixf);
    }
    
    static native void nglMultMatrixf(final long p0, final long p1);
    
    public static void glMultMatrix(final DoubleBuffer doubleBuffer) {
        final long glMultMatrixd = GLContext.getCapabilities().glMultMatrixd;
        BufferChecks.checkFunctionAddress(glMultMatrixd);
        BufferChecks.checkBuffer(doubleBuffer, 16);
        nglMultMatrixd(MemoryUtil.getAddress(doubleBuffer), glMultMatrixd);
    }
    
    static native void nglMultMatrixd(final long p0, final long p1);
    
    public static void glShadeModel(final int n) {
        final long glShadeModel = GLContext.getCapabilities().glShadeModel;
        BufferChecks.checkFunctionAddress(glShadeModel);
        nglShadeModel(n, glShadeModel);
    }
    
    static native void nglShadeModel(final int p0, final long p1);
    
    public static void glSelectBuffer(final IntBuffer intBuffer) {
        final long glSelectBuffer = GLContext.getCapabilities().glSelectBuffer;
        BufferChecks.checkFunctionAddress(glSelectBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglSelectBuffer(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glSelectBuffer);
    }
    
    static native void nglSelectBuffer(final int p0, final long p1, final long p2);
    
    public static void glScissor(final int n, final int n2, final int n3, final int n4) {
        final long glScissor = GLContext.getCapabilities().glScissor;
        BufferChecks.checkFunctionAddress(glScissor);
        nglScissor(n, n2, n3, n4, glScissor);
    }
    
    static native void nglScissor(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glScalef(final float n, final float n2, final float n3) {
        final long glScalef = GLContext.getCapabilities().glScalef;
        BufferChecks.checkFunctionAddress(glScalef);
        nglScalef(n, n2, n3, glScalef);
    }
    
    static native void nglScalef(final float p0, final float p1, final float p2, final long p3);
    
    public static void glScaled(final double n, final double n2, final double n3) {
        final long glScaled = GLContext.getCapabilities().glScaled;
        BufferChecks.checkFunctionAddress(glScaled);
        nglScaled(n, n2, n3, glScaled);
    }
    
    static native void nglScaled(final double p0, final double p1, final double p2, final long p3);
    
    public static void glRotatef(final float n, final float n2, final float n3, final float n4) {
        final long glRotatef = GLContext.getCapabilities().glRotatef;
        BufferChecks.checkFunctionAddress(glRotatef);
        nglRotatef(n, n2, n3, n4, glRotatef);
    }
    
    static native void nglRotatef(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glRotated(final double n, final double n2, final double n3, final double n4) {
        final long glRotated = GLContext.getCapabilities().glRotated;
        BufferChecks.checkFunctionAddress(glRotated);
        nglRotated(n, n2, n3, n4, glRotated);
    }
    
    static native void nglRotated(final double p0, final double p1, final double p2, final double p3, final long p4);
    
    public static int glRenderMode(final int n) {
        final long glRenderMode = GLContext.getCapabilities().glRenderMode;
        BufferChecks.checkFunctionAddress(glRenderMode);
        return nglRenderMode(n, glRenderMode);
    }
    
    static native int nglRenderMode(final int p0, final long p1);
    
    public static void glRectf(final float n, final float n2, final float n3, final float n4) {
        final long glRectf = GLContext.getCapabilities().glRectf;
        BufferChecks.checkFunctionAddress(glRectf);
        nglRectf(n, n2, n3, n4, glRectf);
    }
    
    static native void nglRectf(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glRectd(final double n, final double n2, final double n3, final double n4) {
        final long glRectd = GLContext.getCapabilities().glRectd;
        BufferChecks.checkFunctionAddress(glRectd);
        nglRectd(n, n2, n3, n4, glRectd);
    }
    
    static native void nglRectd(final double p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glRecti(final int n, final int n2, final int n3, final int n4) {
        final long glRecti = GLContext.getCapabilities().glRecti;
        BufferChecks.checkFunctionAddress(glRecti);
        nglRecti(n, n2, n3, n4, glRecti);
    }
    
    static native void nglRecti(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadPixels = capabilities.glReadPixels;
        BufferChecks.checkFunctionAddress(glReadPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n5, n6, n3, n4, 1));
        nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), glReadPixels);
    }
    
    public static void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadPixels = capabilities.glReadPixels;
        BufferChecks.checkFunctionAddress(glReadPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n5, n6, n3, n4, 1));
        nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), glReadPixels);
    }
    
    public static void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadPixels = capabilities.glReadPixels;
        BufferChecks.checkFunctionAddress(glReadPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n5, n6, n3, n4, 1));
        nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), glReadPixels);
    }
    
    public static void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadPixels = capabilities.glReadPixels;
        BufferChecks.checkFunctionAddress(glReadPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n5, n6, n3, n4, 1));
        nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), glReadPixels);
    }
    
    public static void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadPixels = capabilities.glReadPixels;
        BufferChecks.checkFunctionAddress(glReadPixels);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n5, n6, n3, n4, 1));
        nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), glReadPixels);
    }
    
    static native void nglReadPixels(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glReadPixels = capabilities.glReadPixels;
        BufferChecks.checkFunctionAddress(glReadPixels);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglReadPixelsBO(n, n2, n3, n4, n5, n6, n7, glReadPixels);
    }
    
    static native void nglReadPixelsBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glReadBuffer(final int n) {
        final long glReadBuffer = GLContext.getCapabilities().glReadBuffer;
        BufferChecks.checkFunctionAddress(glReadBuffer);
        nglReadBuffer(n, glReadBuffer);
    }
    
    static native void nglReadBuffer(final int p0, final long p1);
    
    public static void glRasterPos2f(final float n, final float n2) {
        final long glRasterPos2f = GLContext.getCapabilities().glRasterPos2f;
        BufferChecks.checkFunctionAddress(glRasterPos2f);
        nglRasterPos2f(n, n2, glRasterPos2f);
    }
    
    static native void nglRasterPos2f(final float p0, final float p1, final long p2);
    
    public static void glRasterPos2d(final double n, final double n2) {
        final long glRasterPos2d = GLContext.getCapabilities().glRasterPos2d;
        BufferChecks.checkFunctionAddress(glRasterPos2d);
        nglRasterPos2d(n, n2, glRasterPos2d);
    }
    
    static native void nglRasterPos2d(final double p0, final double p1, final long p2);
    
    public static void glRasterPos2i(final int n, final int n2) {
        final long glRasterPos2i = GLContext.getCapabilities().glRasterPos2i;
        BufferChecks.checkFunctionAddress(glRasterPos2i);
        nglRasterPos2i(n, n2, glRasterPos2i);
    }
    
    static native void nglRasterPos2i(final int p0, final int p1, final long p2);
    
    public static void glRasterPos3f(final float n, final float n2, final float n3) {
        final long glRasterPos3f = GLContext.getCapabilities().glRasterPos3f;
        BufferChecks.checkFunctionAddress(glRasterPos3f);
        nglRasterPos3f(n, n2, n3, glRasterPos3f);
    }
    
    static native void nglRasterPos3f(final float p0, final float p1, final float p2, final long p3);
    
    public static void glRasterPos3d(final double n, final double n2, final double n3) {
        final long glRasterPos3d = GLContext.getCapabilities().glRasterPos3d;
        BufferChecks.checkFunctionAddress(glRasterPos3d);
        nglRasterPos3d(n, n2, n3, glRasterPos3d);
    }
    
    static native void nglRasterPos3d(final double p0, final double p1, final double p2, final long p3);
    
    public static void glRasterPos3i(final int n, final int n2, final int n3) {
        final long glRasterPos3i = GLContext.getCapabilities().glRasterPos3i;
        BufferChecks.checkFunctionAddress(glRasterPos3i);
        nglRasterPos3i(n, n2, n3, glRasterPos3i);
    }
    
    static native void nglRasterPos3i(final int p0, final int p1, final int p2, final long p3);
    
    public static void glRasterPos4f(final float n, final float n2, final float n3, final float n4) {
        final long glRasterPos4f = GLContext.getCapabilities().glRasterPos4f;
        BufferChecks.checkFunctionAddress(glRasterPos4f);
        nglRasterPos4f(n, n2, n3, n4, glRasterPos4f);
    }
    
    static native void nglRasterPos4f(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glRasterPos4d(final double n, final double n2, final double n3, final double n4) {
        final long glRasterPos4d = GLContext.getCapabilities().glRasterPos4d;
        BufferChecks.checkFunctionAddress(glRasterPos4d);
        nglRasterPos4d(n, n2, n3, n4, glRasterPos4d);
    }
    
    static native void nglRasterPos4d(final double p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glRasterPos4i(final int n, final int n2, final int n3, final int n4) {
        final long glRasterPos4i = GLContext.getCapabilities().glRasterPos4i;
        BufferChecks.checkFunctionAddress(glRasterPos4i);
        nglRasterPos4i(n, n2, n3, n4, glRasterPos4i);
    }
    
    static native void nglRasterPos4i(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glPushName(final int n) {
        final long glPushName = GLContext.getCapabilities().glPushName;
        BufferChecks.checkFunctionAddress(glPushName);
        nglPushName(n, glPushName);
    }
    
    static native void nglPushName(final int p0, final long p1);
    
    public static void glPopName() {
        final long glPopName = GLContext.getCapabilities().glPopName;
        BufferChecks.checkFunctionAddress(glPopName);
        nglPopName(glPopName);
    }
    
    static native void nglPopName(final long p0);
    
    public static void glPushMatrix() {
        final long glPushMatrix = GLContext.getCapabilities().glPushMatrix;
        BufferChecks.checkFunctionAddress(glPushMatrix);
        nglPushMatrix(glPushMatrix);
    }
    
    static native void nglPushMatrix(final long p0);
    
    public static void glPopMatrix() {
        final long glPopMatrix = GLContext.getCapabilities().glPopMatrix;
        BufferChecks.checkFunctionAddress(glPopMatrix);
        nglPopMatrix(glPopMatrix);
    }
    
    static native void nglPopMatrix(final long p0);
    
    public static void glPushClientAttrib(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPushClientAttrib = capabilities.glPushClientAttrib;
        BufferChecks.checkFunctionAddress(glPushClientAttrib);
        StateTracker.pushAttrib(capabilities, n);
        nglPushClientAttrib(n, glPushClientAttrib);
    }
    
    static native void nglPushClientAttrib(final int p0, final long p1);
    
    public static void glPopClientAttrib() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPopClientAttrib = capabilities.glPopClientAttrib;
        BufferChecks.checkFunctionAddress(glPopClientAttrib);
        StateTracker.popAttrib(capabilities);
        nglPopClientAttrib(glPopClientAttrib);
    }
    
    static native void nglPopClientAttrib(final long p0);
    
    public static void glPushAttrib(final int n) {
        final long glPushAttrib = GLContext.getCapabilities().glPushAttrib;
        BufferChecks.checkFunctionAddress(glPushAttrib);
        nglPushAttrib(n, glPushAttrib);
    }
    
    static native void nglPushAttrib(final int p0, final long p1);
    
    public static void glPopAttrib() {
        final long glPopAttrib = GLContext.getCapabilities().glPopAttrib;
        BufferChecks.checkFunctionAddress(glPopAttrib);
        nglPopAttrib(glPopAttrib);
    }
    
    static native void nglPopAttrib(final long p0);
    
    public static void glStencilFunc(final int n, final int n2, final int n3) {
        final long glStencilFunc = GLContext.getCapabilities().glStencilFunc;
        BufferChecks.checkFunctionAddress(glStencilFunc);
        nglStencilFunc(n, n2, n3, glStencilFunc);
    }
    
    static native void nglStencilFunc(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertexPointer(final int n, final int n2, final DoubleBuffer gl11_glVertexPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexPointer = capabilities.glVertexPointer;
        BufferChecks.checkFunctionAddress(glVertexPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glVertexPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glVertexPointer_pointer = gl11_glVertexPointer_pointer;
        }
        nglVertexPointer(n, 5130, n2, MemoryUtil.getAddress(gl11_glVertexPointer_pointer), glVertexPointer);
    }
    
    public static void glVertexPointer(final int n, final int n2, final FloatBuffer gl11_glVertexPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexPointer = capabilities.glVertexPointer;
        BufferChecks.checkFunctionAddress(glVertexPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glVertexPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glVertexPointer_pointer = gl11_glVertexPointer_pointer;
        }
        nglVertexPointer(n, 5126, n2, MemoryUtil.getAddress(gl11_glVertexPointer_pointer), glVertexPointer);
    }
    
    public static void glVertexPointer(final int n, final int n2, final IntBuffer gl11_glVertexPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexPointer = capabilities.glVertexPointer;
        BufferChecks.checkFunctionAddress(glVertexPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glVertexPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glVertexPointer_pointer = gl11_glVertexPointer_pointer;
        }
        nglVertexPointer(n, 5124, n2, MemoryUtil.getAddress(gl11_glVertexPointer_pointer), glVertexPointer);
    }
    
    public static void glVertexPointer(final int n, final int n2, final ShortBuffer gl11_glVertexPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexPointer = capabilities.glVertexPointer;
        BufferChecks.checkFunctionAddress(glVertexPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glVertexPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glVertexPointer_pointer = gl11_glVertexPointer_pointer;
        }
        nglVertexPointer(n, 5122, n2, MemoryUtil.getAddress(gl11_glVertexPointer_pointer), glVertexPointer);
    }
    
    static native void nglVertexPointer(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glVertexPointer(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexPointer = capabilities.glVertexPointer;
        BufferChecks.checkFunctionAddress(glVertexPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexPointerBO(n, n2, n3, n4, glVertexPointer);
    }
    
    static native void nglVertexPointerBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glVertexPointer(final int n, final int n2, final int n3, final ByteBuffer gl11_glVertexPointer_pointer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexPointer = capabilities.glVertexPointer;
        BufferChecks.checkFunctionAddress(glVertexPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(gl11_glVertexPointer_pointer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).GL11_glVertexPointer_pointer = gl11_glVertexPointer_pointer;
        }
        nglVertexPointer(n, n2, n3, MemoryUtil.getAddress(gl11_glVertexPointer_pointer), glVertexPointer);
    }
    
    public static void glVertex2f(final float n, final float n2) {
        final long glVertex2f = GLContext.getCapabilities().glVertex2f;
        BufferChecks.checkFunctionAddress(glVertex2f);
        nglVertex2f(n, n2, glVertex2f);
    }
    
    static native void nglVertex2f(final float p0, final float p1, final long p2);
    
    public static void glVertex2d(final double n, final double n2) {
        final long glVertex2d = GLContext.getCapabilities().glVertex2d;
        BufferChecks.checkFunctionAddress(glVertex2d);
        nglVertex2d(n, n2, glVertex2d);
    }
    
    static native void nglVertex2d(final double p0, final double p1, final long p2);
    
    public static void glVertex2i(final int n, final int n2) {
        final long glVertex2i = GLContext.getCapabilities().glVertex2i;
        BufferChecks.checkFunctionAddress(glVertex2i);
        nglVertex2i(n, n2, glVertex2i);
    }
    
    static native void nglVertex2i(final int p0, final int p1, final long p2);
    
    public static void glVertex3f(final float n, final float n2, final float n3) {
        final long glVertex3f = GLContext.getCapabilities().glVertex3f;
        BufferChecks.checkFunctionAddress(glVertex3f);
        nglVertex3f(n, n2, n3, glVertex3f);
    }
    
    static native void nglVertex3f(final float p0, final float p1, final float p2, final long p3);
    
    public static void glVertex3d(final double n, final double n2, final double n3) {
        final long glVertex3d = GLContext.getCapabilities().glVertex3d;
        BufferChecks.checkFunctionAddress(glVertex3d);
        nglVertex3d(n, n2, n3, glVertex3d);
    }
    
    static native void nglVertex3d(final double p0, final double p1, final double p2, final long p3);
    
    public static void glVertex3i(final int n, final int n2, final int n3) {
        final long glVertex3i = GLContext.getCapabilities().glVertex3i;
        BufferChecks.checkFunctionAddress(glVertex3i);
        nglVertex3i(n, n2, n3, glVertex3i);
    }
    
    static native void nglVertex3i(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertex4f(final float n, final float n2, final float n3, final float n4) {
        final long glVertex4f = GLContext.getCapabilities().glVertex4f;
        BufferChecks.checkFunctionAddress(glVertex4f);
        nglVertex4f(n, n2, n3, n4, glVertex4f);
    }
    
    static native void nglVertex4f(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glVertex4d(final double n, final double n2, final double n3, final double n4) {
        final long glVertex4d = GLContext.getCapabilities().glVertex4d;
        BufferChecks.checkFunctionAddress(glVertex4d);
        nglVertex4d(n, n2, n3, n4, glVertex4d);
    }
    
    static native void nglVertex4d(final double p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glVertex4i(final int n, final int n2, final int n3, final int n4) {
        final long glVertex4i = GLContext.getCapabilities().glVertex4i;
        BufferChecks.checkFunctionAddress(glVertex4i);
        nglVertex4i(n, n2, n3, n4, glVertex4i);
    }
    
    static native void nglVertex4i(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glTranslatef(final float n, final float n2, final float n3) {
        final long glTranslatef = GLContext.getCapabilities().glTranslatef;
        BufferChecks.checkFunctionAddress(glTranslatef);
        nglTranslatef(n, n2, n3, glTranslatef);
    }
    
    static native void nglTranslatef(final float p0, final float p1, final float p2, final long p3);
    
    public static void glTranslated(final double n, final double n2, final double n3) {
        final long glTranslated = GLContext.getCapabilities().glTranslated;
        BufferChecks.checkFunctionAddress(glTranslated);
        nglTranslated(n, n2, n3, glTranslated);
    }
    
    static native void nglTranslated(final double p0, final double p1, final double p2, final long p3);
    
    public static void glTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage1D = capabilities.glTexImage1D;
        BufferChecks.checkFunctionAddress(glTexImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage1DStorage(byteBuffer, n6, n7, n4));
        }
        nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddressSafe(byteBuffer), glTexImage1D);
    }
    
    public static void glTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage1D = capabilities.glTexImage1D;
        BufferChecks.checkFunctionAddress(glTexImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage1DStorage(doubleBuffer, n6, n7, n4));
        }
        nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddressSafe(doubleBuffer), glTexImage1D);
    }
    
    public static void glTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage1D = capabilities.glTexImage1D;
        BufferChecks.checkFunctionAddress(glTexImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage1DStorage(floatBuffer, n6, n7, n4));
        }
        nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddressSafe(floatBuffer), glTexImage1D);
    }
    
    public static void glTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage1D = capabilities.glTexImage1D;
        BufferChecks.checkFunctionAddress(glTexImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage1DStorage(intBuffer, n6, n7, n4));
        }
        nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddressSafe(intBuffer), glTexImage1D);
    }
    
    public static void glTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage1D = capabilities.glTexImage1D;
        BufferChecks.checkFunctionAddress(glTexImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage1DStorage(shortBuffer, n6, n7, n4));
        }
        nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.getAddressSafe(shortBuffer), glTexImage1D);
    }
    
    static native void nglTexImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glTexImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage1D = capabilities.glTexImage1D;
        BufferChecks.checkFunctionAddress(glTexImage1D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTexImage1DBO(n, n2, n3, n4, n5, n6, n7, n8, glTexImage1D);
    }
    
    static native void nglTexImage1DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    public static void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage2D = capabilities.glTexImage2D;
        BufferChecks.checkFunctionAddress(glTexImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (byteBuffer != null) {
            BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateTexImage2DStorage(byteBuffer, n7, n8, n4, n5));
        }
        nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(byteBuffer), glTexImage2D);
    }
    
    public static void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage2D = capabilities.glTexImage2D;
        BufferChecks.checkFunctionAddress(glTexImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (doubleBuffer != null) {
            BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateTexImage2DStorage(doubleBuffer, n7, n8, n4, n5));
        }
        nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(doubleBuffer), glTexImage2D);
    }
    
    public static void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage2D = capabilities.glTexImage2D;
        BufferChecks.checkFunctionAddress(glTexImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateTexImage2DStorage(floatBuffer, n7, n8, n4, n5));
        }
        nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(floatBuffer), glTexImage2D);
    }
    
    public static void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage2D = capabilities.glTexImage2D;
        BufferChecks.checkFunctionAddress(glTexImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, GLChecks.calculateTexImage2DStorage(intBuffer, n7, n8, n4, n5));
        }
        nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(intBuffer), glTexImage2D);
    }
    
    public static void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage2D = capabilities.glTexImage2D;
        BufferChecks.checkFunctionAddress(glTexImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateTexImage2DStorage(shortBuffer, n7, n8, n4, n5));
        }
        nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddressSafe(shortBuffer), glTexImage2D);
    }
    
    static native void nglTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexImage2D = capabilities.glTexImage2D;
        BufferChecks.checkFunctionAddress(glTexImage2D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTexImage2DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glTexImage2D);
    }
    
    static native void nglTexImage2DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage1D = capabilities.glTexSubImage1D;
        BufferChecks.checkFunctionAddress(glTexSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n5, n6, n4, 1, 1));
        nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), glTexSubImage1D);
    }
    
    public static void glTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage1D = capabilities.glTexSubImage1D;
        BufferChecks.checkFunctionAddress(glTexSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n5, n6, n4, 1, 1));
        nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), glTexSubImage1D);
    }
    
    public static void glTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage1D = capabilities.glTexSubImage1D;
        BufferChecks.checkFunctionAddress(glTexSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n5, n6, n4, 1, 1));
        nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), glTexSubImage1D);
    }
    
    public static void glTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage1D = capabilities.glTexSubImage1D;
        BufferChecks.checkFunctionAddress(glTexSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n5, n6, n4, 1, 1));
        nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), glTexSubImage1D);
    }
    
    public static void glTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage1D = capabilities.glTexSubImage1D;
        BufferChecks.checkFunctionAddress(glTexSubImage1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n5, n6, n4, 1, 1));
        nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), glTexSubImage1D);
    }
    
    static native void nglTexSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glTexSubImage1D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage1D = capabilities.glTexSubImage1D;
        BufferChecks.checkFunctionAddress(glTexSubImage1D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTexSubImage1DBO(n, n2, n3, n4, n5, n6, n7, glTexSubImage1D);
    }
    
    static native void nglTexSubImage1DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage2D = capabilities.glTexSubImage2D;
        BufferChecks.checkFunctionAddress(glTexSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n7, n8, n5, n6, 1));
        nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(byteBuffer), glTexSubImage2D);
    }
    
    public static void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage2D = capabilities.glTexSubImage2D;
        BufferChecks.checkFunctionAddress(glTexSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n7, n8, n5, n6, 1));
        nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(doubleBuffer), glTexSubImage2D);
    }
    
    public static void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage2D = capabilities.glTexSubImage2D;
        BufferChecks.checkFunctionAddress(glTexSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n7, n8, n5, n6, 1));
        nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(floatBuffer), glTexSubImage2D);
    }
    
    public static void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage2D = capabilities.glTexSubImage2D;
        BufferChecks.checkFunctionAddress(glTexSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n7, n8, n5, n6, 1));
        nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(intBuffer), glTexSubImage2D);
    }
    
    public static void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage2D = capabilities.glTexSubImage2D;
        BufferChecks.checkFunctionAddress(glTexSubImage2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n7, n8, n5, n6, 1));
        nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.getAddress(shortBuffer), glTexSubImage2D);
    }
    
    static native void nglTexSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexSubImage2D = capabilities.glTexSubImage2D;
        BufferChecks.checkFunctionAddress(glTexSubImage2D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglTexSubImage2DBO(n, n2, n3, n4, n5, n6, n7, n8, n9, glTexSubImage2D);
    }
    
    static native void nglTexSubImage2DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    public static void glTexParameterf(final int n, final int n2, final float n3) {
        final long glTexParameterf = GLContext.getCapabilities().glTexParameterf;
        BufferChecks.checkFunctionAddress(glTexParameterf);
        nglTexParameterf(n, n2, n3, glTexParameterf);
    }
    
    static native void nglTexParameterf(final int p0, final int p1, final float p2, final long p3);
    
    public static void glTexParameteri(final int n, final int n2, final int n3) {
        final long glTexParameteri = GLContext.getCapabilities().glTexParameteri;
        BufferChecks.checkFunctionAddress(glTexParameteri);
        nglTexParameteri(n, n2, n3, glTexParameteri);
    }
    
    static native void nglTexParameteri(final int p0, final int p1, final int p2, final long p3);
    
    public static void glTexParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glTexParameterfv = GLContext.getCapabilities().glTexParameterfv;
        BufferChecks.checkFunctionAddress(glTexParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglTexParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glTexParameterfv);
    }
    
    static native void nglTexParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTexParameteriv = GLContext.getCapabilities().glTexParameteriv;
        BufferChecks.checkFunctionAddress(glTexParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glTexParameteriv);
    }
    
    static native void nglTexParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexGenf(final int n, final int n2, final float n3) {
        final long glTexGenf = GLContext.getCapabilities().glTexGenf;
        BufferChecks.checkFunctionAddress(glTexGenf);
        nglTexGenf(n, n2, n3, glTexGenf);
    }
    
    static native void nglTexGenf(final int p0, final int p1, final float p2, final long p3);
    
    public static void glTexGend(final int n, final int n2, final double n3) {
        final long glTexGend = GLContext.getCapabilities().glTexGend;
        BufferChecks.checkFunctionAddress(glTexGend);
        nglTexGend(n, n2, n3, glTexGend);
    }
    
    static native void nglTexGend(final int p0, final int p1, final double p2, final long p3);
    
    public static void glTexGen(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glTexGenfv = GLContext.getCapabilities().glTexGenfv;
        BufferChecks.checkFunctionAddress(glTexGenfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglTexGenfv(n, n2, MemoryUtil.getAddress(floatBuffer), glTexGenfv);
    }
    
    static native void nglTexGenfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexGen(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final long glTexGendv = GLContext.getCapabilities().glTexGendv;
        BufferChecks.checkFunctionAddress(glTexGendv);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglTexGendv(n, n2, MemoryUtil.getAddress(doubleBuffer), glTexGendv);
    }
    
    static native void nglTexGendv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexGeni(final int n, final int n2, final int n3) {
        final long glTexGeni = GLContext.getCapabilities().glTexGeni;
        BufferChecks.checkFunctionAddress(glTexGeni);
        nglTexGeni(n, n2, n3, glTexGeni);
    }
    
    static native void nglTexGeni(final int p0, final int p1, final int p2, final long p3);
    
    public static void glTexGen(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTexGeniv = GLContext.getCapabilities().glTexGeniv;
        BufferChecks.checkFunctionAddress(glTexGeniv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexGeniv(n, n2, MemoryUtil.getAddress(intBuffer), glTexGeniv);
    }
    
    static native void nglTexGeniv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexEnvf(final int n, final int n2, final float n3) {
        final long glTexEnvf = GLContext.getCapabilities().glTexEnvf;
        BufferChecks.checkFunctionAddress(glTexEnvf);
        nglTexEnvf(n, n2, n3, glTexEnvf);
    }
    
    static native void nglTexEnvf(final int p0, final int p1, final float p2, final long p3);
    
    public static void glTexEnvi(final int n, final int n2, final int n3) {
        final long glTexEnvi = GLContext.getCapabilities().glTexEnvi;
        BufferChecks.checkFunctionAddress(glTexEnvi);
        nglTexEnvi(n, n2, n3, glTexEnvi);
    }
    
    static native void nglTexEnvi(final int p0, final int p1, final int p2, final long p3);
    
    public static void glTexEnv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glTexEnvfv = GLContext.getCapabilities().glTexEnvfv;
        BufferChecks.checkFunctionAddress(glTexEnvfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglTexEnvfv(n, n2, MemoryUtil.getAddress(floatBuffer), glTexEnvfv);
    }
    
    static native void nglTexEnvfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexEnv(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTexEnviv = GLContext.getCapabilities().glTexEnviv;
        BufferChecks.checkFunctionAddress(glTexEnviv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexEnviv(n, n2, MemoryUtil.getAddress(intBuffer), glTexEnviv);
    }
    
    static native void nglTexEnviv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexCoordPointer(final int n, final int n2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexCoordPointer = capabilities.glTexCoordPointer;
        BufferChecks.checkFunctionAddress(glTexCoordPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glTexCoordPointer_buffer[StateTracker.getReferences(capabilities).glClientActiveTexture] = doubleBuffer;
        }
        nglTexCoordPointer(n, 5130, n2, MemoryUtil.getAddress(doubleBuffer), glTexCoordPointer);
    }
    
    public static void glTexCoordPointer(final int n, final int n2, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexCoordPointer = capabilities.glTexCoordPointer;
        BufferChecks.checkFunctionAddress(glTexCoordPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glTexCoordPointer_buffer[StateTracker.getReferences(capabilities).glClientActiveTexture] = floatBuffer;
        }
        nglTexCoordPointer(n, 5126, n2, MemoryUtil.getAddress(floatBuffer), glTexCoordPointer);
    }
    
    public static void glTexCoordPointer(final int n, final int n2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexCoordPointer = capabilities.glTexCoordPointer;
        BufferChecks.checkFunctionAddress(glTexCoordPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glTexCoordPointer_buffer[StateTracker.getReferences(capabilities).glClientActiveTexture] = intBuffer;
        }
        nglTexCoordPointer(n, 5124, n2, MemoryUtil.getAddress(intBuffer), glTexCoordPointer);
    }
    
    public static void glTexCoordPointer(final int n, final int n2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexCoordPointer = capabilities.glTexCoordPointer;
        BufferChecks.checkFunctionAddress(glTexCoordPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glTexCoordPointer_buffer[StateTracker.getReferences(capabilities).glClientActiveTexture] = shortBuffer;
        }
        nglTexCoordPointer(n, 5122, n2, MemoryUtil.getAddress(shortBuffer), glTexCoordPointer);
    }
    
    static native void nglTexCoordPointer(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glTexCoordPointer(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexCoordPointer = capabilities.glTexCoordPointer;
        BufferChecks.checkFunctionAddress(glTexCoordPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglTexCoordPointerBO(n, n2, n3, n4, glTexCoordPointer);
    }
    
    static native void nglTexCoordPointerBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glTexCoordPointer(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexCoordPointer = capabilities.glTexCoordPointer;
        BufferChecks.checkFunctionAddress(glTexCoordPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glTexCoordPointer_buffer[StateTracker.getReferences(capabilities).glClientActiveTexture] = byteBuffer;
        }
        nglTexCoordPointer(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glTexCoordPointer);
    }
    
    public static void glTexCoord1f(final float n) {
        final long glTexCoord1f = GLContext.getCapabilities().glTexCoord1f;
        BufferChecks.checkFunctionAddress(glTexCoord1f);
        nglTexCoord1f(n, glTexCoord1f);
    }
    
    static native void nglTexCoord1f(final float p0, final long p1);
    
    public static void glTexCoord1d(final double n) {
        final long glTexCoord1d = GLContext.getCapabilities().glTexCoord1d;
        BufferChecks.checkFunctionAddress(glTexCoord1d);
        nglTexCoord1d(n, glTexCoord1d);
    }
    
    static native void nglTexCoord1d(final double p0, final long p1);
    
    public static void glTexCoord2f(final float n, final float n2) {
        final long glTexCoord2f = GLContext.getCapabilities().glTexCoord2f;
        BufferChecks.checkFunctionAddress(glTexCoord2f);
        nglTexCoord2f(n, n2, glTexCoord2f);
    }
    
    static native void nglTexCoord2f(final float p0, final float p1, final long p2);
    
    public static void glTexCoord2d(final double n, final double n2) {
        final long glTexCoord2d = GLContext.getCapabilities().glTexCoord2d;
        BufferChecks.checkFunctionAddress(glTexCoord2d);
        nglTexCoord2d(n, n2, glTexCoord2d);
    }
    
    static native void nglTexCoord2d(final double p0, final double p1, final long p2);
    
    public static void glTexCoord3f(final float n, final float n2, final float n3) {
        final long glTexCoord3f = GLContext.getCapabilities().glTexCoord3f;
        BufferChecks.checkFunctionAddress(glTexCoord3f);
        nglTexCoord3f(n, n2, n3, glTexCoord3f);
    }
    
    static native void nglTexCoord3f(final float p0, final float p1, final float p2, final long p3);
    
    public static void glTexCoord3d(final double n, final double n2, final double n3) {
        final long glTexCoord3d = GLContext.getCapabilities().glTexCoord3d;
        BufferChecks.checkFunctionAddress(glTexCoord3d);
        nglTexCoord3d(n, n2, n3, glTexCoord3d);
    }
    
    static native void nglTexCoord3d(final double p0, final double p1, final double p2, final long p3);
    
    public static void glTexCoord4f(final float n, final float n2, final float n3, final float n4) {
        final long glTexCoord4f = GLContext.getCapabilities().glTexCoord4f;
        BufferChecks.checkFunctionAddress(glTexCoord4f);
        nglTexCoord4f(n, n2, n3, n4, glTexCoord4f);
    }
    
    static native void nglTexCoord4f(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    public static void glTexCoord4d(final double n, final double n2, final double n3, final double n4) {
        final long glTexCoord4d = GLContext.getCapabilities().glTexCoord4d;
        BufferChecks.checkFunctionAddress(glTexCoord4d);
        nglTexCoord4d(n, n2, n3, n4, glTexCoord4d);
    }
    
    static native void nglTexCoord4d(final double p0, final double p1, final double p2, final double p3, final long p4);
    
    public static void glStencilOp(final int n, final int n2, final int n3) {
        final long glStencilOp = GLContext.getCapabilities().glStencilOp;
        BufferChecks.checkFunctionAddress(glStencilOp);
        nglStencilOp(n, n2, n3, glStencilOp);
    }
    
    static native void nglStencilOp(final int p0, final int p1, final int p2, final long p3);
    
    public static void glStencilMask(final int n) {
        final long glStencilMask = GLContext.getCapabilities().glStencilMask;
        BufferChecks.checkFunctionAddress(glStencilMask);
        nglStencilMask(n, glStencilMask);
    }
    
    static native void nglStencilMask(final int p0, final long p1);
    
    public static void glViewport(final int n, final int n2, final int n3, final int n4) {
        final long glViewport = GLContext.getCapabilities().glViewport;
        BufferChecks.checkFunctionAddress(glViewport);
        nglViewport(n, n2, n3, n4, glViewport);
    }
    
    static native void nglViewport(final int p0, final int p1, final int p2, final int p3, final long p4);
}
