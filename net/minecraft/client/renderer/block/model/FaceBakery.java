package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraftforge.client.model.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.client.renderer.*;
import javax.vecmath.*;
import net.minecraft.util.*;
import java.util.*;
import java.nio.charset.*;

public class FaceBakery
{
    private static final double field_178418_a;
    private static final double field_178417_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002490";
        field_178418_a = 1.0 / Math.cos(0.39269908169872414) - 1.0;
        field_178417_b = 1.0 / Math.cos(0.7853981633974483) - 1.0;
    }
    
    public BakedQuad func_178414_a(final Vector3f vector3f, final Vector3f vector3f2, final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final ModelRotation modelRotation, final BlockPartRotation blockPartRotation, final boolean b, final boolean b2) {
        return this.makeBakedQuad(vector3f, vector3f2, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPartRotation, b, b2);
    }
    
    public BakedQuad makeBakedQuad(final Vector3f vector3f, final Vector3f vector3f2, final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final ITransformation transformation, final BlockPartRotation blockPartRotation, final boolean b, final boolean b2) {
        final int[] quadVertexData = this.makeQuadVertexData(blockPartFace, textureAtlasSprite, enumFacing, this.func_178403_a(vector3f, vector3f2), transformation, blockPartRotation, b, b2);
        final EnumFacing func_178410_a = func_178410_a(quadVertexData);
        if (b) {
            this.func_178409_a(quadVertexData, func_178410_a, blockPartFace.field_178243_e, textureAtlasSprite);
        }
        if (blockPartRotation == null) {
            this.func_178408_a(quadVertexData, func_178410_a);
        }
        if (Reflector.ForgeHooksClient_fillNormal.exists()) {
            Reflector.callVoid(Reflector.ForgeHooksClient_fillNormal, quadVertexData, func_178410_a);
        }
        return new BakedQuad(quadVertexData, blockPartFace.field_178245_c, func_178410_a, textureAtlasSprite);
    }
    
    private int[] makeQuadVertexData(final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final float[] array, final ITransformation transformation, final BlockPartRotation blockPartRotation, final boolean b, final boolean b2) {
        if (Config.isShaders()) {}
        final int[] array2 = new int[56];
        while (0 < 4) {
            this.fillVertexData(array2, 0, enumFacing, blockPartFace, array, textureAtlasSprite, transformation, blockPartRotation, b, b2);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    private int func_178413_a(final EnumFacing enumFacing) {
        final int clamp_int = MathHelper.clamp_int((int)(this.func_178412_b(enumFacing) * 255.0f), 0, 255);
        return 0xFF000000 | clamp_int << 16 | clamp_int << 8 | clamp_int;
    }
    
    private float func_178412_b(final EnumFacing enumFacing) {
        switch (SwitchEnumFacing.field_178400_a[enumFacing.ordinal()]) {
            case 1: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel05;
                }
                return 0.5f;
            }
            case 2: {
                return 1.0f;
            }
            case 3:
            case 4: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel08;
                }
                return 0.8f;
            }
            case 5:
            case 6: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel06;
                }
                return 0.6f;
            }
            default: {
                return 1.0f;
            }
        }
    }
    
    private float[] func_178403_a(final Vector3f vector3f, final Vector3f vector3f2) {
        final float[] array = new float[EnumFacing.values().length];
        array[EnumFaceing.Constants.field_179176_f] = vector3f.x / 16.0f;
        array[EnumFaceing.Constants.field_179178_e] = vector3f.y / 16.0f;
        array[EnumFaceing.Constants.field_179177_d] = vector3f.z / 16.0f;
        array[EnumFaceing.Constants.field_179180_c] = vector3f2.x / 16.0f;
        array[EnumFaceing.Constants.field_179179_b] = vector3f2.y / 16.0f;
        array[EnumFaceing.Constants.field_179181_a] = vector3f2.z / 16.0f;
        return array;
    }
    
    private void fillVertexData(final int[] array, final int n, final EnumFacing enumFacing, final BlockPartFace blockPartFace, final float[] array2, final TextureAtlasSprite textureAtlasSprite, final ITransformation transformation, final BlockPartRotation blockPartRotation, final boolean b, final boolean b2) {
        final EnumFacing rotate = transformation.rotate(enumFacing);
        final int n2 = b2 ? this.func_178413_a(rotate) : -1;
        final EnumFaceing.VertexInformation func_179025_a = EnumFaceing.func_179027_a(enumFacing).func_179025_a(n);
        final Vector3d vector3d = new Vector3d(array2[func_179025_a.field_179184_a], array2[func_179025_a.field_179182_b], array2[func_179025_a.field_179183_c]);
        this.func_178407_a(vector3d, blockPartRotation);
        this.func_178404_a(array, this.rotateVertex(vector3d, enumFacing, n, transformation, b), n, vector3d, n2, textureAtlasSprite, blockPartFace.field_178243_e);
    }
    
    private void func_178404_a(final int[] array, final int n, final int n2, final Vector3d vector3d, final int n3, final TextureAtlasSprite textureAtlasSprite, final BlockFaceUV blockFaceUV) {
        final int n4 = n * (array.length / 4);
        array[n4] = Float.floatToRawIntBits((float)vector3d.x);
        array[n4 + 1] = Float.floatToRawIntBits((float)vector3d.y);
        array[n4 + 2] = Float.floatToRawIntBits((float)vector3d.z);
        array[n4 + 3] = n3;
        array[n4 + 4] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedU(blockFaceUV.func_178348_a(n2)));
        array[n4 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedV(blockFaceUV.func_178346_b(n2)));
    }
    
    private void func_178407_a(final Vector3d vector3d, final BlockPartRotation blockPartRotation) {
        if (blockPartRotation != null) {
            final Matrix4d func_178411_a = this.func_178411_a();
            final Vector3d vector3d2 = new Vector3d(0.0, 0.0, 0.0);
            switch (SwitchEnumFacing.field_178399_b[blockPartRotation.field_178342_b.ordinal()]) {
                case 1: {
                    func_178411_a.mul(this.func_178416_a(new AxisAngle4d(1.0, 0.0, 0.0, blockPartRotation.field_178343_c * 0.017453292519943295)));
                    vector3d2.set(0.0, 1.0, 1.0);
                    break;
                }
                case 2: {
                    func_178411_a.mul(this.func_178416_a(new AxisAngle4d(0.0, 1.0, 0.0, blockPartRotation.field_178343_c * 0.017453292519943295)));
                    vector3d2.set(1.0, 0.0, 1.0);
                    break;
                }
                case 3: {
                    func_178411_a.mul(this.func_178416_a(new AxisAngle4d(0.0, 0.0, 1.0, blockPartRotation.field_178343_c * 0.017453292519943295)));
                    vector3d2.set(1.0, 1.0, 0.0);
                    break;
                }
            }
            if (blockPartRotation.field_178341_d) {
                if (Math.abs(blockPartRotation.field_178343_c) == 22.5f) {
                    vector3d2.scale(FaceBakery.field_178418_a);
                }
                else {
                    vector3d2.scale(FaceBakery.field_178417_b);
                }
                vector3d2.add(new Vector3d(1.0, 1.0, 1.0));
            }
            else {
                vector3d2.set(new Vector3d(1.0, 1.0, 1.0));
            }
            this.func_178406_a(vector3d, new Vector3d(blockPartRotation.field_178344_a), func_178411_a, vector3d2);
        }
    }
    
    public int func_178415_a(final Vector3d vector3d, final EnumFacing enumFacing, final int n, final ModelRotation modelRotation, final boolean b) {
        return this.rotateVertex(vector3d, enumFacing, n, modelRotation, b);
    }
    
    public int rotateVertex(final Vector3d vector3d, final EnumFacing enumFacing, final int n, final ITransformation transformation, final boolean b) {
        if (transformation == ModelRotation.X0_Y0) {
            return n;
        }
        if (Reflector.ForgeHooksClient_transform.exists()) {
            Reflector.call(Reflector.ForgeHooksClient_transform, vector3d, transformation.getMatrix());
        }
        else {
            this.func_178406_a(vector3d, new Vector3d(0.5, 0.5, 0.5), new Matrix4d(transformation.getMatrix()), new Vector3d(1.0, 1.0, 1.0));
        }
        return transformation.rotate(enumFacing, n);
    }
    
    private void func_178406_a(final Vector3d vector3d, final Vector3d vector3d2, final Matrix4d matrix4d, final Vector3d vector3d3) {
        vector3d.sub(vector3d2);
        matrix4d.transform(vector3d);
        vector3d.x *= vector3d3.x;
        vector3d.y *= vector3d3.y;
        vector3d.z *= vector3d3.z;
        vector3d.add(vector3d2);
    }
    
    private Matrix4d func_178416_a(final AxisAngle4d rotation) {
        final Matrix4d func_178411_a = this.func_178411_a();
        func_178411_a.setRotation(rotation);
        return func_178411_a;
    }
    
    private Matrix4d func_178411_a() {
        final Matrix4d matrix4d = new Matrix4d();
        matrix4d.setIdentity();
        return matrix4d;
    }
    
    public static EnumFacing func_178410_a(final int[] array) {
        final int n = array.length / 4;
        final int n2 = n * 2;
        final Vector3f vector3f = new Vector3f(Float.intBitsToFloat(array[0]), Float.intBitsToFloat(array[1]), Float.intBitsToFloat(array[2]));
        final Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(array[n]), Float.intBitsToFloat(array[n + 1]), Float.intBitsToFloat(array[n + 2]));
        final Vector3f vector3f3 = new Vector3f(Float.intBitsToFloat(array[n2]), Float.intBitsToFloat(array[n2 + 1]), Float.intBitsToFloat(array[n2 + 2]));
        final Vector3f vector3f4 = new Vector3f();
        final Vector3f vector3f5 = new Vector3f();
        final Vector3f vector3f6 = new Vector3f();
        vector3f4.sub(vector3f, vector3f2);
        vector3f5.sub(vector3f3, vector3f2);
        vector3f6.cross(vector3f5, vector3f4);
        vector3f6.normalize();
        EnumFacing enumFacing = null;
        float n3 = 0.0f;
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final EnumFacing enumFacing2 = values[0];
            final Vec3i directionVec = enumFacing2.getDirectionVec();
            final float dot = vector3f6.dot(new Vector3f((float)directionVec.getX(), (float)directionVec.getY(), (float)directionVec.getZ()));
            if (dot >= 0.0f && dot > n3) {
                n3 = dot;
                enumFacing = enumFacing2;
            }
            int n4 = 0;
            ++n4;
        }
        if (n3 < 0.719f) {
            if (enumFacing != EnumFacing.EAST && enumFacing != EnumFacing.WEST && enumFacing != EnumFacing.NORTH && enumFacing != EnumFacing.SOUTH) {
                enumFacing = EnumFacing.UP;
            }
            else {
                enumFacing = EnumFacing.NORTH;
            }
        }
        return (enumFacing == null) ? EnumFacing.UP : enumFacing;
    }
    
    public void func_178409_a(final int[] array, final EnumFacing enumFacing, final BlockFaceUV blockFaceUV, final TextureAtlasSprite textureAtlasSprite) {
        while (0 < 4) {
            this.func_178401_a(0, array, enumFacing, blockFaceUV, textureAtlasSprite);
            int n = 0;
            ++n;
        }
    }
    
    private void func_178408_a(final int[] array, final EnumFacing enumFacing) {
        final int[] array2 = new int[array.length];
        System.arraycopy(array, 0, array2, 0, array.length);
        final float[] array3 = new float[EnumFacing.values().length];
        array3[EnumFaceing.Constants.field_179176_f] = 999.0f;
        array3[EnumFaceing.Constants.field_179178_e] = 999.0f;
        array3[EnumFaceing.Constants.field_179177_d] = 999.0f;
        array3[EnumFaceing.Constants.field_179180_c] = -999.0f;
        array3[EnumFaceing.Constants.field_179179_b] = -999.0f;
        array3[EnumFaceing.Constants.field_179181_a] = -999.0f;
        final int n = array.length / 4;
        int n2 = 0;
        while (0 < 4) {
            n2 = n * 0;
            final float intBitsToFloat = Float.intBitsToFloat(array2[0]);
            final float intBitsToFloat2 = Float.intBitsToFloat(array2[1]);
            final float intBitsToFloat3 = Float.intBitsToFloat(array2[2]);
            if (intBitsToFloat < array3[EnumFaceing.Constants.field_179176_f]) {
                array3[EnumFaceing.Constants.field_179176_f] = intBitsToFloat;
            }
            if (intBitsToFloat2 < array3[EnumFaceing.Constants.field_179178_e]) {
                array3[EnumFaceing.Constants.field_179178_e] = intBitsToFloat2;
            }
            if (intBitsToFloat3 < array3[EnumFaceing.Constants.field_179177_d]) {
                array3[EnumFaceing.Constants.field_179177_d] = intBitsToFloat3;
            }
            if (intBitsToFloat > array3[EnumFaceing.Constants.field_179180_c]) {
                array3[EnumFaceing.Constants.field_179180_c] = intBitsToFloat;
            }
            if (intBitsToFloat2 > array3[EnumFaceing.Constants.field_179179_b]) {
                array3[EnumFaceing.Constants.field_179179_b] = intBitsToFloat2;
            }
            if (intBitsToFloat3 > array3[EnumFaceing.Constants.field_179181_a]) {
                array3[EnumFaceing.Constants.field_179181_a] = intBitsToFloat3;
            }
            int n3 = 0;
            ++n3;
        }
        final EnumFaceing func_179027_a = EnumFaceing.func_179027_a(enumFacing);
        while (0 < 4) {
            final int n4 = n * 0;
            final EnumFaceing.VertexInformation func_179025_a = func_179027_a.func_179025_a(0);
            final float n5 = array3[func_179025_a.field_179184_a];
            final float n6 = array3[func_179025_a.field_179182_b];
            final float n7 = array3[func_179025_a.field_179183_c];
            array[n4] = Float.floatToRawIntBits(n5);
            array[n4 + 1] = Float.floatToRawIntBits(n6);
            array[n4 + 2] = Float.floatToRawIntBits(n7);
            while (0 < 4) {
                final int n8 = n * 0;
                final float intBitsToFloat4 = Float.intBitsToFloat(array2[n8]);
                final float intBitsToFloat5 = Float.intBitsToFloat(array2[n8 + 1]);
                final float intBitsToFloat6 = Float.intBitsToFloat(array2[n8 + 2]);
                if (MathHelper.func_180185_a(n5, intBitsToFloat4) && MathHelper.func_180185_a(n6, intBitsToFloat5) && MathHelper.func_180185_a(n7, intBitsToFloat6)) {
                    array[n4 + 4] = array2[n8 + 4];
                    array[n4 + 4 + 1] = array2[n8 + 4 + 1];
                }
                int n9 = 0;
                ++n9;
            }
            ++n2;
        }
    }
    
    private void func_178401_a(final int n, final int[] array, final EnumFacing enumFacing, final BlockFaceUV blockFaceUV, final TextureAtlasSprite textureAtlasSprite) {
        final int n2 = array.length / 4;
        final int n3 = n2 * n;
        float intBitsToFloat = Float.intBitsToFloat(array[n3]);
        float intBitsToFloat2 = Float.intBitsToFloat(array[n3 + 1]);
        float intBitsToFloat3 = Float.intBitsToFloat(array[n3 + 2]);
        if (intBitsToFloat < -0.1f || intBitsToFloat >= 1.1f) {
            intBitsToFloat -= MathHelper.floor_float(intBitsToFloat);
        }
        if (intBitsToFloat2 < -0.1f || intBitsToFloat2 >= 1.1f) {
            intBitsToFloat2 -= MathHelper.floor_float(intBitsToFloat2);
        }
        if (intBitsToFloat3 < -0.1f || intBitsToFloat3 >= 1.1f) {
            intBitsToFloat3 -= MathHelper.floor_float(intBitsToFloat3);
        }
        float n4 = 0.0f;
        float n5 = 0.0f;
        switch (SwitchEnumFacing.field_178400_a[enumFacing.ordinal()]) {
            case 1: {
                n4 = intBitsToFloat * 16.0f;
                n5 = (1.0f - intBitsToFloat3) * 16.0f;
                break;
            }
            case 2: {
                n4 = intBitsToFloat * 16.0f;
                n5 = intBitsToFloat3 * 16.0f;
                break;
            }
            case 3: {
                n4 = (1.0f - intBitsToFloat) * 16.0f;
                n5 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 4: {
                n4 = intBitsToFloat * 16.0f;
                n5 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 5: {
                n4 = intBitsToFloat3 * 16.0f;
                n5 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 6: {
                n4 = (1.0f - intBitsToFloat3) * 16.0f;
                n5 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
        }
        final int n6 = blockFaceUV.func_178345_c(n) * n2;
        array[n6 + 4] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedU(n4));
        array[n6 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedV(n5));
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_178400_a;
        static final int[] field_178399_b;
        private static final String __OBFID;
        private static final String[] lIlIlIIIlllllIIl;
        private static String[] lIlIlIIIlllllIlI;
        
        static {
            lllllIIIlllIllII();
            lllllIIIlllIlIll();
            __OBFID = SwitchEnumFacing.lIlIlIIIlllllIIl[0];
            field_178399_b = new int[EnumFacing.Axis.values().length];
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.Y.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.Z.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            field_178400_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
        }
        
        private static void lllllIIIlllIlIll() {
            (lIlIlIIIlllllIIl = new String[1])[0] = lllllIIIlllIlIlI(SwitchEnumFacing.lIlIlIIIlllllIlI[0], SwitchEnumFacing.lIlIlIIIlllllIlI[1]);
            SwitchEnumFacing.lIlIlIIIlllllIlI = null;
        }
        
        private static void lllllIIIlllIllII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIlIlIIIlllllIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lllllIIIlllIlIlI(String s, final String s2) {
            s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int n = 0;
            final char[] charArray2 = s.toCharArray();
            for (int length = charArray2.length, i = 0; i < length; ++i) {
                sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
                ++n;
            }
            return sb.toString();
        }
    }
}
