package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import optifine.*;
import java.nio.*;

public class GlStateManager
{
    private static AlphaState alphaState;
    private static BooleanState lightingState;
    private static BooleanState[] field_179159_c;
    private static ColorMaterialState colorMaterialState;
    private static BlendState blendState;
    private static DepthState depthState;
    private static FogState fogState;
    private static CullState cullState;
    private static PolygonOffsetState polygonOffsetState;
    private static ColorLogicState colorLogicState;
    private static TexGenState texGenState;
    private static ClearState clearState;
    private static StencilState stencilState;
    private static BooleanState normalizeState;
    private static TextureState[] textureState;
    private static TextureState[] field_179174_p;
    private static BooleanState rescaleNormalState;
    private static ColorMask colorMaskState;
    private static Color colorState;
    private static Viewport field_179169_u;
    private static final String __OBFID;
    public static boolean clearEnabled;
    public static Color Mizu;
    
    static {
        __OBFID = "CL_00002558";
        GlStateManager.alphaState = new AlphaState(null);
        GlStateManager.lightingState = new BooleanState(2896);
        GlStateManager.field_179159_c = new BooleanState[8];
        GlStateManager.colorMaterialState = new ColorMaterialState(null);
        GlStateManager.blendState = new BlendState(null);
        GlStateManager.depthState = new DepthState(null);
        GlStateManager.fogState = new FogState(null);
        GlStateManager.cullState = new CullState(null);
        GlStateManager.polygonOffsetState = new PolygonOffsetState(null);
        GlStateManager.colorLogicState = new ColorLogicState(null);
        GlStateManager.texGenState = new TexGenState(null);
        GlStateManager.clearState = new ClearState(null);
        GlStateManager.stencilState = new StencilState(null);
        GlStateManager.normalizeState = new BooleanState(2977);
        GlStateManager.textureState = new TextureState[32];
        GlStateManager.field_179174_p = new TextureState[32];
        GlStateManager.rescaleNormalState = new BooleanState(32826);
        GlStateManager.colorMaskState = new ColorMask(null);
        GlStateManager.colorState = new Color();
        GlStateManager.field_179169_u = new Viewport(null);
        GlStateManager.clearEnabled = true;
        GlStateManager.Mizu = new Color();
        while (0 < GlStateManager.field_179174_p.length) {
            GlStateManager.field_179174_p[0] = new TextureState(null);
            int n = 0;
            ++n;
        }
    }
    
    public static void pushAttrib() {
        GL11.glPushAttrib(8256);
    }
    
    public static void popAttrib() {
    }
    
    public static void disableAlpha() {
        GlStateManager.alphaState.field_179208_a.setDisabled();
    }
    
    public static void enableAlpha() {
        GlStateManager.alphaState.field_179208_a.setEnabled();
    }
    
    public static void alphaFunc(final int field_179206_b, final float field_179207_c) {
        if (field_179206_b != GlStateManager.alphaState.field_179206_b || field_179207_c != GlStateManager.alphaState.field_179207_c) {
            GL11.glAlphaFunc(GlStateManager.alphaState.field_179206_b = field_179206_b, GlStateManager.alphaState.field_179207_c = field_179207_c);
        }
    }
    
    public static void enableLighting() {
        GlStateManager.lightingState.setEnabled();
    }
    
    public static void disableLighting() {
        GlStateManager.lightingState.setDisabled();
    }
    
    public static void enableBooleanStateAt(final int n) {
        GlStateManager.field_179159_c[n].setEnabled();
    }
    
    public static void disableBooleanStateAt(final int n) {
        GlStateManager.field_179159_c[n].setDisabled();
    }
    
    public static void enableColorMaterial() {
        GlStateManager.colorMaterialState.field_179191_a.setEnabled();
    }
    
    public static void disableColorMaterial() {
        GlStateManager.colorMaterialState.field_179191_a.setDisabled();
    }
    
    public static void colorMaterial(final int field_179189_b, final int field_179190_c) {
        if (field_179189_b != GlStateManager.colorMaterialState.field_179189_b || field_179190_c != GlStateManager.colorMaterialState.field_179190_c) {
            GL11.glColorMaterial(GlStateManager.colorMaterialState.field_179189_b = field_179189_b, GlStateManager.colorMaterialState.field_179190_c = field_179190_c);
        }
    }
    
    public static void disableDepth() {
        GlStateManager.depthState.field_179052_a.setDisabled();
    }
    
    public static void enableDepth() {
        GlStateManager.depthState.field_179052_a.setEnabled();
    }
    
    public static void depthFunc(final int field_179051_c) {
        if (field_179051_c != GlStateManager.depthState.field_179051_c) {
            GL11.glDepthFunc(GlStateManager.depthState.field_179051_c = field_179051_c);
        }
    }
    
    public static void depthMask(final boolean field_179050_b) {
        if (field_179050_b != GlStateManager.depthState.field_179050_b) {
            GL11.glDepthMask(GlStateManager.depthState.field_179050_b = field_179050_b);
        }
    }
    
    public static void disableBlend() {
        GlStateManager.blendState.field_179213_a.setDisabled();
    }
    
    public static void enableBlend() {
        GlStateManager.blendState.field_179213_a.setEnabled();
    }
    
    public static void blendFunc(final int field_179211_b, final int field_179212_c) {
        if (field_179211_b != GlStateManager.blendState.field_179211_b || field_179212_c != GlStateManager.blendState.field_179212_c) {
            GL11.glBlendFunc(GlStateManager.blendState.field_179211_b = field_179211_b, GlStateManager.blendState.field_179212_c = field_179212_c);
        }
    }
    
    public static void tryBlendFuncSeparate(final int field_179211_b, final int field_179212_c, final int field_179209_d, final int field_179210_e) {
        if (field_179211_b != GlStateManager.blendState.field_179211_b || field_179212_c != GlStateManager.blendState.field_179212_c || field_179209_d != GlStateManager.blendState.field_179209_d || field_179210_e != GlStateManager.blendState.field_179210_e) {
            OpenGlHelper.glBlendFunc(GlStateManager.blendState.field_179211_b = field_179211_b, GlStateManager.blendState.field_179212_c = field_179212_c, GlStateManager.blendState.field_179209_d = field_179209_d, GlStateManager.blendState.field_179210_e = field_179210_e);
        }
    }
    
    public static void enableFog() {
        GlStateManager.fogState.field_179049_a.setEnabled();
    }
    
    public static void disableFog() {
        GlStateManager.fogState.field_179049_a.setDisabled();
    }
    
    public static void setFog(final int field_179047_b) {
        if (field_179047_b != GlStateManager.fogState.field_179047_b) {
            GL11.glFogi(2917, GlStateManager.fogState.field_179047_b = field_179047_b);
        }
    }
    
    public static void setFogDensity(final float field_179048_c) {
        if (field_179048_c != GlStateManager.fogState.field_179048_c) {
            GL11.glFogf(2914, GlStateManager.fogState.field_179048_c = field_179048_c);
        }
    }
    
    public static void setFogStart(final float field_179045_d) {
        if (field_179045_d != GlStateManager.fogState.field_179045_d) {
            GL11.glFogf(2915, GlStateManager.fogState.field_179045_d = field_179045_d);
        }
    }
    
    public static void setFogEnd(final float field_179046_e) {
        if (field_179046_e != GlStateManager.fogState.field_179046_e) {
            GL11.glFogf(2916, GlStateManager.fogState.field_179046_e = field_179046_e);
        }
    }
    
    public static void enableCull() {
        GlStateManager.cullState.field_179054_a.setEnabled();
    }
    
    public static void disableCull() {
        GlStateManager.cullState.field_179054_a.setDisabled();
    }
    
    public static void cullFace(final int field_179053_b) {
        if (field_179053_b != GlStateManager.cullState.field_179053_b) {
            GL11.glCullFace(GlStateManager.cullState.field_179053_b = field_179053_b);
        }
    }
    
    public static void enablePolygonOffset() {
        GlStateManager.polygonOffsetState.field_179044_a.setEnabled();
    }
    
    public static void disablePolygonOffset() {
        GlStateManager.polygonOffsetState.field_179044_a.setDisabled();
    }
    
    public static void doPolygonOffset(final float field_179043_c, final float field_179041_d) {
        if (field_179043_c != GlStateManager.polygonOffsetState.field_179043_c || field_179041_d != GlStateManager.polygonOffsetState.field_179041_d) {
            GL11.glPolygonOffset(GlStateManager.polygonOffsetState.field_179043_c = field_179043_c, GlStateManager.polygonOffsetState.field_179041_d = field_179041_d);
        }
    }
    
    public static void enableColorLogic() {
        GlStateManager.colorLogicState.field_179197_a.setEnabled();
    }
    
    public static void disableColorLogic() {
        GlStateManager.colorLogicState.field_179197_a.setDisabled();
    }
    
    public static void colorLogicOp(final int field_179196_b) {
        if (field_179196_b != GlStateManager.colorLogicState.field_179196_b) {
            GL11.glLogicOp(GlStateManager.colorLogicState.field_179196_b = field_179196_b);
        }
    }
    
    public static void enableTexGenCoord(final TexGen texGen) {
        texGenCoord(texGen).field_179067_a.setEnabled();
    }
    
    public static void disableTexGenCoord(final TexGen texGen) {
        texGenCoord(texGen).field_179067_a.setDisabled();
    }
    
    public static void texGen(final TexGen texGen, final int field_179066_c) {
        final TexGenCoord texGenCoord = texGenCoord(texGen);
        if (field_179066_c != texGenCoord.field_179066_c) {
            texGenCoord.field_179066_c = field_179066_c;
            GL11.glTexGeni(texGenCoord.field_179065_b, 9472, field_179066_c);
        }
    }
    
    public static void func_179105_a(final TexGen texGen, final int n, final FloatBuffer floatBuffer) {
        GL11.glTexGen(texGenCoord(texGen).field_179065_b, n, floatBuffer);
    }
    
    private static TexGenCoord texGenCoord(final TexGen texGen) {
        switch (SwitchTexGen.field_179175_a[texGen.ordinal()]) {
            case 1: {
                return GlStateManager.texGenState.field_179064_a;
            }
            case 2: {
                return GlStateManager.texGenState.field_179062_b;
            }
            case 3: {
                return GlStateManager.texGenState.field_179063_c;
            }
            case 4: {
                return GlStateManager.texGenState.field_179061_d;
            }
            default: {
                return GlStateManager.texGenState.field_179064_a;
            }
        }
    }
    
    public static void setActiveTexture(final int activeTexture) {
        if (0 != activeTexture - OpenGlHelper.defaultTexUnit) {
            GlStateManager.field_179162_o = activeTexture - OpenGlHelper.defaultTexUnit;
            OpenGlHelper.setActiveTexture(activeTexture);
        }
    }
    
    public static void func_179098_w() {
        GlStateManager.field_179174_p[0].field_179060_a.setEnabled();
    }
    
    public static void func_179090_x() {
        GlStateManager.field_179174_p[0].field_179060_a.setDisabled();
    }
    
    public static int func_179146_y() {
        return GL11.glGenTextures();
    }
    
    public static void func_179150_h(final int n) {
        if (n != 0) {
            GL11.glDeleteTextures(n);
            final TextureState[] field_179174_p = GlStateManager.field_179174_p;
            while (0 < field_179174_p.length) {
                final TextureState textureState = field_179174_p[0];
                if (textureState.field_179059_b == n) {
                    textureState.field_179059_b = 0;
                }
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public static void func_179144_i(final int field_179059_b) {
        if (field_179059_b != GlStateManager.field_179174_p[0].field_179059_b) {
            GL11.glBindTexture(3553, GlStateManager.field_179174_p[0].field_179059_b = field_179059_b);
        }
    }
    
    public static void bindTexture(final int n) {
        GL11.glBindTexture(3553, GlStateManager.field_179174_p[0].field_179059_b);
    }
    
    public static void bindCurrentTexture() {
        GL11.glBindTexture(3553, GlStateManager.field_179174_p[0].field_179059_b);
    }
    
    public static void enableNormalize() {
        GlStateManager.normalizeState.setEnabled();
    }
    
    public static void disableNormalize() {
        GlStateManager.normalizeState.setDisabled();
    }
    
    public static void shadeModel(final int field_179173_q) {
        if (field_179173_q != 7425) {
            GL11.glShadeModel(GlStateManager.field_179173_q = field_179173_q);
        }
    }
    
    public static void enableRescaleNormal() {
        GlStateManager.rescaleNormalState.setEnabled();
    }
    
    public static void disableRescaleNormal() {
        GlStateManager.rescaleNormalState.setDisabled();
    }
    
    public static void viewport(final int field_179058_a, final int field_179056_b, final int field_179057_c, final int field_179055_d) {
        if (field_179058_a != GlStateManager.field_179169_u.field_179058_a || field_179056_b != GlStateManager.field_179169_u.field_179056_b || field_179057_c != GlStateManager.field_179169_u.field_179057_c || field_179055_d != GlStateManager.field_179169_u.field_179055_d) {
            GL11.glViewport(GlStateManager.field_179169_u.field_179058_a = field_179058_a, GlStateManager.field_179169_u.field_179056_b = field_179056_b, GlStateManager.field_179169_u.field_179057_c = field_179057_c, GlStateManager.field_179169_u.field_179055_d = field_179055_d);
        }
    }
    
    public static void colorMask(final boolean field_179188_a, final boolean field_179186_b, final boolean field_179187_c, final boolean field_179185_d) {
        if (field_179188_a != GlStateManager.colorMaskState.field_179188_a || field_179186_b != GlStateManager.colorMaskState.field_179186_b || field_179187_c != GlStateManager.colorMaskState.field_179187_c || field_179185_d != GlStateManager.colorMaskState.field_179185_d) {
            GL11.glColorMask(GlStateManager.colorMaskState.field_179188_a = field_179188_a, GlStateManager.colorMaskState.field_179186_b = field_179186_b, GlStateManager.colorMaskState.field_179187_c = field_179187_c, GlStateManager.colorMaskState.field_179185_d = field_179185_d);
        }
    }
    
    public static void clearDepth(final double field_179205_a) {
        if (field_179205_a != GlStateManager.clearState.field_179205_a) {
            GL11.glClearDepth(GlStateManager.clearState.field_179205_a = field_179205_a);
        }
    }
    
    public static void clearColor(final float field_179195_a, final float green, final float blue, final float alpha) {
        if (field_179195_a != GlStateManager.clearState.field_179203_b.field_179195_a || green != GlStateManager.clearState.field_179203_b.green || blue != GlStateManager.clearState.field_179203_b.blue || alpha != GlStateManager.clearState.field_179203_b.alpha) {
            GL11.glClearColor(GlStateManager.clearState.field_179203_b.field_179195_a = field_179195_a, GlStateManager.clearState.field_179203_b.green = green, GlStateManager.clearState.field_179203_b.blue = blue, GlStateManager.clearState.field_179203_b.alpha = alpha);
        }
    }
    
    public static void clear(final int n) {
        if (GlStateManager.clearEnabled) {
            GL11.glClear(n);
        }
    }
    
    public static void matrixMode(final int n) {
        GL11.glMatrixMode(n);
    }
    
    public static void loadIdentity() {
    }
    
    public static void pushMatrix() {
    }
    
    public static void popMatrix() {
    }
    
    public static void getFloat(final int n, final FloatBuffer floatBuffer) {
        GL11.glGetFloat(n, floatBuffer);
    }
    
    public static void ortho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        GL11.glOrtho(n, n2, n3, n4, n5, n6);
    }
    
    public static void rotate(final float n, final float n2, final float n3, final float n4) {
        GL11.glRotatef(n, n2, n3, n4);
    }
    
    public static void scale(final float n, final float n2, final float n3) {
        GL11.glScalef(n, n2, n3);
    }
    
    public static void scale(final double n, final double n2, final double n3) {
        GL11.glScaled(n, n2, n3);
    }
    
    public static void translate(final float n, final float n2, final float n3) {
        GL11.glTranslatef(n, n2, n3);
    }
    
    public static void translate(final double n, final double n2, final double n3) {
        GL11.glTranslated(n, n2, n3);
    }
    
    public static void multMatrix(final FloatBuffer floatBuffer) {
        GL11.glMultMatrix(floatBuffer);
    }
    
    public static void color(final float field_179195_a, final float green, final float blue, final float alpha) {
        if (field_179195_a != GlStateManager.colorState.field_179195_a || green != GlStateManager.colorState.green || blue != GlStateManager.colorState.blue || alpha != GlStateManager.colorState.alpha) {
            GL11.glColor4f(GlStateManager.colorState.field_179195_a = field_179195_a, GlStateManager.colorState.green = green, GlStateManager.colorState.blue = blue, GlStateManager.colorState.alpha = alpha);
        }
    }
    
    public static void color(final float n, final float n2, final float n3) {
        color(n, n2, n3, 1.0f);
    }
    
    public static void func_179117_G() {
        final Color colorState = GlStateManager.colorState;
        final Color colorState2 = GlStateManager.colorState;
        final Color colorState3 = GlStateManager.colorState;
        final Color colorState4 = GlStateManager.colorState;
        final float n = -1.0f;
        colorState4.alpha = n;
        colorState3.blue = n;
        colorState2.green = n;
        colorState.field_179195_a = n;
    }
    
    public static void callList(final int n) {
        GL11.glCallList(n);
    }
    
    public static int getActiveTextureUnit() {
        return OpenGlHelper.defaultTexUnit + 0;
    }
    
    public static int getBoundTexture() {
        return GlStateManager.field_179174_p[0].field_179059_b;
    }
    
    public static void checkBoundTexture() {
        if (Config.isMinecraftThread()) {
            final int glGetInteger = GL11.glGetInteger(34016);
            final int glGetInteger2 = GL11.glGetInteger(32873);
            final int activeTextureUnit = getActiveTextureUnit();
            final int boundTexture = getBoundTexture();
            if (boundTexture > 0 && (glGetInteger != activeTextureUnit || glGetInteger2 != boundTexture)) {
                Config.dbg("checkTexture: act: " + activeTextureUnit + ", glAct: " + glGetInteger + ", tex: " + boundTexture + ", glTex: " + glGetInteger2);
            }
        }
    }
    
    public static void deleteTextures(final IntBuffer intBuffer) {
        intBuffer.rewind();
        while (intBuffer.position() < intBuffer.limit()) {
            func_179150_h(intBuffer.get());
        }
        intBuffer.rewind();
    }
    
    public static void enableTexture2D() {
        GlStateManager.field_179174_p[0].field_179060_a.setEnabled();
    }
    
    public static void disableTexture2D() {
        GlStateManager.field_179174_p[0].field_179060_a.setDisabled();
    }
    
    public static void resetColor() {
        final Color colorState = GlStateManager.colorState;
        final Color colorState2 = GlStateManager.colorState;
        final Color colorState3 = GlStateManager.colorState;
        final Color colorState4 = GlStateManager.colorState;
        final float n = -1.0f;
        colorState4.alpha = n;
        colorState3.blue = n;
        colorState2.green = n;
        colorState.field_179195_a = n;
    }
    
    static class AlphaState
    {
        public BooleanState field_179208_a;
        public int field_179206_b;
        public float field_179207_c;
        private static final String __OBFID;
        
        private AlphaState() {
            this.field_179208_a = new BooleanState(3008);
            this.field_179206_b = 519;
            this.field_179207_c = -1.0f;
        }
        
        AlphaState(final SwitchTexGen switchTexGen) {
            this();
        }
        
        static {
            __OBFID = "CL_00002556";
        }
    }
    
    static class BooleanState
    {
        private final int capability;
        private boolean currentState;
        
        public BooleanState(final int capability) {
            this.currentState = false;
            this.capability = capability;
        }
        
        public void setDisabled() {
            this.setState(false);
        }
        
        public void setEnabled() {
            this.setState(true);
        }
        
        public void setState(final boolean currentState) {
            if (currentState != this.currentState) {
                this.currentState = currentState;
                if (currentState) {
                    GL11.glEnable(this.capability);
                }
                else {
                    GL11.glDisable(this.capability);
                }
            }
        }
    }
    
    static final class SwitchTexGen
    {
        static final int[] field_179175_a;
        
        static {
            field_179175_a = new int[TexGen.values().length];
            try {
                SwitchTexGen.field_179175_a[TexGen.S.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchTexGen.field_179175_a[TexGen.T.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchTexGen.field_179175_a[TexGen.R.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchTexGen.field_179175_a[TexGen.Q.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
    
    public enum TexGen
    {
        S("S", 0, "S", 0, "S", 0), 
        T("T", 1, "T", 1, "T", 1), 
        R("R", 2, "R", 2, "R", 2), 
        Q("Q", 3, "Q", 3, "Q", 3);
        
        private static final TexGen[] $VALUES;
        private static final String __OBFID;
        private static final TexGen[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002542";
            ENUM$VALUES = new TexGen[] { TexGen.S, TexGen.T, TexGen.R, TexGen.Q };
            $VALUES = new TexGen[] { TexGen.S, TexGen.T, TexGen.R, TexGen.Q };
        }
        
        private TexGen(final String s, final int n, final String s2, final int n2, final String s3, final int n3) {
        }
    }
    
    static class BlendState
    {
        public BooleanState field_179213_a;
        public int field_179211_b;
        public int field_179212_c;
        public int field_179209_d;
        public int field_179210_e;
        
        private BlendState() {
            this.field_179213_a = new BooleanState(3042);
            this.field_179211_b = 1;
            this.field_179212_c = 0;
            this.field_179209_d = 1;
            this.field_179210_e = 0;
        }
        
        BlendState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class ClearState
    {
        public double field_179205_a;
        public Color field_179203_b;
        public int field_179204_c;
        
        private ClearState() {
            this.field_179205_a = 1.0;
            this.field_179203_b = new Color(0.0f, 0.0f, 0.0f, 0.0f);
            this.field_179204_c = 0;
        }
        
        ClearState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    public static class Color
    {
        public float field_179195_a;
        public float green;
        public float blue;
        public float alpha;
        
        public Color() {
            this.field_179195_a = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
            this.alpha = 1.0f;
        }
        
        public Color(final float field_179195_a, final float green, final float blue, final float alpha) {
            this.field_179195_a = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
            this.alpha = 1.0f;
            this.field_179195_a = field_179195_a;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
    }
    
    static class ColorLogicState
    {
        public BooleanState field_179197_a;
        public int field_179196_b;
        
        private ColorLogicState() {
            this.field_179197_a = new BooleanState(3058);
            this.field_179196_b = 5379;
        }
        
        ColorLogicState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class ColorMask
    {
        public boolean field_179188_a;
        public boolean field_179186_b;
        public boolean field_179187_c;
        public boolean field_179185_d;
        
        private ColorMask() {
            this.field_179188_a = true;
            this.field_179186_b = true;
            this.field_179187_c = true;
            this.field_179185_d = true;
        }
        
        ColorMask(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class ColorMaterialState
    {
        public BooleanState field_179191_a;
        public int field_179189_b;
        public int field_179190_c;
        
        private ColorMaterialState() {
            this.field_179191_a = new BooleanState(2903);
            this.field_179189_b = 1032;
            this.field_179190_c = 5634;
        }
        
        ColorMaterialState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class CullState
    {
        public BooleanState field_179054_a;
        public int field_179053_b;
        
        private CullState() {
            this.field_179054_a = new BooleanState(2884);
            this.field_179053_b = 1029;
        }
        
        CullState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class DepthState
    {
        public BooleanState field_179052_a;
        public boolean field_179050_b;
        public int field_179051_c;
        
        private DepthState() {
            this.field_179052_a = new BooleanState(2929);
            this.field_179050_b = true;
            this.field_179051_c = 513;
        }
        
        DepthState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class FogState
    {
        public BooleanState field_179049_a;
        public int field_179047_b;
        public float field_179048_c;
        public float field_179045_d;
        public float field_179046_e;
        
        private FogState() {
            this.field_179049_a = new BooleanState(2912);
            this.field_179047_b = 2048;
            this.field_179048_c = 1.0f;
            this.field_179045_d = 0.0f;
            this.field_179046_e = 1.0f;
        }
        
        FogState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class PolygonOffsetState
    {
        public BooleanState field_179044_a;
        public BooleanState field_179042_b;
        public float field_179043_c;
        public float field_179041_d;
        
        private PolygonOffsetState() {
            this.field_179044_a = new BooleanState(32823);
            this.field_179042_b = new BooleanState(10754);
            this.field_179043_c = 0.0f;
            this.field_179041_d = 0.0f;
        }
        
        PolygonOffsetState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class StencilFunc
    {
        public int field_179081_a;
        public int field_179079_b;
        public int field_179080_c;
        
        private StencilFunc() {
            this.field_179081_a = 519;
            this.field_179079_b = 0;
            this.field_179080_c = -1;
        }
        
        StencilFunc(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class StencilState
    {
        public StencilFunc field_179078_a;
        public int field_179076_b;
        public int field_179077_c;
        public int field_179074_d;
        public int field_179075_e;
        
        private StencilState() {
            this.field_179078_a = new StencilFunc(null);
            this.field_179076_b = -1;
            this.field_179077_c = 7680;
            this.field_179074_d = 7680;
            this.field_179075_e = 7680;
        }
        
        StencilState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class TexGenCoord
    {
        public BooleanState field_179067_a;
        public int field_179065_b;
        public int field_179066_c;
        
        public TexGenCoord(final int field_179065_b, final int n) {
            this.field_179066_c = -1;
            this.field_179065_b = field_179065_b;
            this.field_179067_a = new BooleanState(n);
        }
    }
    
    static class TexGenState
    {
        public TexGenCoord field_179064_a;
        public TexGenCoord field_179062_b;
        public TexGenCoord field_179063_c;
        public TexGenCoord field_179061_d;
        
        private TexGenState() {
            this.field_179064_a = new TexGenCoord(8192, 3168);
            this.field_179062_b = new TexGenCoord(8193, 3169);
            this.field_179063_c = new TexGenCoord(8194, 3170);
            this.field_179061_d = new TexGenCoord(8195, 3171);
        }
        
        TexGenState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class TextureState
    {
        public BooleanState field_179060_a;
        public int field_179059_b;
        
        private TextureState() {
            this.field_179060_a = new BooleanState(3553);
            this.field_179059_b = 0;
        }
        
        TextureState(final SwitchTexGen switchTexGen) {
            this();
        }
    }
    
    static class Viewport
    {
        public int field_179058_a;
        public int field_179056_b;
        public int field_179057_c;
        public int field_179055_d;
        
        private Viewport() {
            this.field_179058_a = 0;
            this.field_179056_b = 0;
            this.field_179057_c = 0;
            this.field_179055_d = 0;
        }
        
        Viewport(final SwitchTexGen switchTexGen) {
            this();
        }
    }
}
