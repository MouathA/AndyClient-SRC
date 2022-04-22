package net.minecraft.client.resources.model;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import javax.vecmath.*;
import optifine.*;

public enum ModelRotation implements IModelState, ITransformation
{
    X0_Y0("X0_Y0", 0, "X0_Y0", 0, 0, 0), 
    X0_Y90("X0_Y90", 1, "X0_Y90", 1, 0, 90), 
    X0_Y180("X0_Y180", 2, "X0_Y180", 2, 0, 180), 
    X0_Y270("X0_Y270", 3, "X0_Y270", 3, 0, 270), 
    X90_Y0("X90_Y0", 4, "X90_Y0", 4, 90, 0), 
    X90_Y90("X90_Y90", 5, "X90_Y90", 5, 90, 90), 
    X90_Y180("X90_Y180", 6, "X90_Y180", 6, 90, 180), 
    X90_Y270("X90_Y270", 7, "X90_Y270", 7, 90, 270), 
    X180_Y0("X180_Y0", 8, "X180_Y0", 8, 180, 0), 
    X180_Y90("X180_Y90", 9, "X180_Y90", 9, 180, 90), 
    X180_Y180("X180_Y180", 10, "X180_Y180", 10, 180, 180), 
    X180_Y270("X180_Y270", 11, "X180_Y270", 11, 180, 270), 
    X270_Y0("X270_Y0", 12, "X270_Y0", 12, 270, 0), 
    X270_Y90("X270_Y90", 13, "X270_Y90", 13, 270, 90), 
    X270_Y180("X270_Y180", 14, "X270_Y180", 14, 270, 180), 
    X270_Y270("X270_Y270", 15, "X270_Y270", 15, 270, 270);
    
    private static final Map field_177546_q;
    private final int field_177545_r;
    private final Matrix4d field_177544_s;
    private final int field_177543_t;
    private final int field_177542_u;
    private static final String __OBFID;
    private static final ModelRotation[] $VALUES;
    private static final ModelRotation[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00002393";
        ENUM$VALUES = new ModelRotation[] { ModelRotation.X0_Y0, ModelRotation.X0_Y90, ModelRotation.X0_Y180, ModelRotation.X0_Y270, ModelRotation.X90_Y0, ModelRotation.X90_Y90, ModelRotation.X90_Y180, ModelRotation.X90_Y270, ModelRotation.X180_Y0, ModelRotation.X180_Y90, ModelRotation.X180_Y180, ModelRotation.X180_Y270, ModelRotation.X270_Y0, ModelRotation.X270_Y90, ModelRotation.X270_Y180, ModelRotation.X270_Y270 };
        field_177546_q = Maps.newHashMap();
        $VALUES = new ModelRotation[] { ModelRotation.X0_Y0, ModelRotation.X0_Y90, ModelRotation.X0_Y180, ModelRotation.X0_Y270, ModelRotation.X90_Y0, ModelRotation.X90_Y90, ModelRotation.X90_Y180, ModelRotation.X90_Y270, ModelRotation.X180_Y0, ModelRotation.X180_Y90, ModelRotation.X180_Y180, ModelRotation.X180_Y270, ModelRotation.X270_Y0, ModelRotation.X270_Y90, ModelRotation.X270_Y180, ModelRotation.X270_Y270 };
        final ModelRotation[] values = values();
        while (0 < values.length) {
            final ModelRotation modelRotation = values[0];
            ModelRotation.field_177546_q.put(modelRotation.field_177545_r, modelRotation);
            int n = 0;
            ++n;
        }
    }
    
    private static int func_177521_b(final int n, final int n2) {
        return n * 360 + n2;
    }
    
    private ModelRotation(final String s, final int n, final String s2, final int n2, final int n3, final int n4) {
        this.field_177545_r = func_177521_b(n3, n4);
        this.field_177544_s = new Matrix4d();
        final Matrix4d matrix4d = new Matrix4d();
        matrix4d.setIdentity();
        matrix4d.setRotation(new AxisAngle4d(1.0, 0.0, 0.0, -n3 * 0.017453292f));
        this.field_177543_t = MathHelper.abs_int(n3 / 90);
        final Matrix4d matrix4d2 = new Matrix4d();
        matrix4d2.setIdentity();
        matrix4d2.setRotation(new AxisAngle4d(0.0, 1.0, 0.0, -n4 * 0.017453292f));
        this.field_177542_u = MathHelper.abs_int(n4 / 90);
        this.field_177544_s.mul(matrix4d2, matrix4d);
    }
    
    public Matrix4d func_177525_a() {
        return this.field_177544_s;
    }
    
    public EnumFacing func_177523_a(final EnumFacing enumFacing) {
        EnumFacing enumFacing2 = enumFacing;
        int n = 0;
        while (0 < this.field_177543_t) {
            enumFacing2 = enumFacing2.rotateAround(EnumFacing.Axis.X);
            ++n;
        }
        if (enumFacing2.getAxis() != EnumFacing.Axis.Y) {
            while (0 < this.field_177542_u) {
                enumFacing2 = enumFacing2.rotateAround(EnumFacing.Axis.Y);
                ++n;
            }
        }
        return enumFacing2;
    }
    
    public int func_177520_a(final EnumFacing enumFacing, final int n) {
        int n2 = n;
        if (enumFacing.getAxis() == EnumFacing.Axis.X) {
            n2 = (n + this.field_177543_t) % 4;
        }
        EnumFacing rotateAround = enumFacing;
        while (0 < this.field_177543_t) {
            rotateAround = rotateAround.rotateAround(EnumFacing.Axis.X);
            int n3 = 0;
            ++n3;
        }
        if (rotateAround.getAxis() == EnumFacing.Axis.Y) {
            n2 = (n2 + this.field_177542_u) % 4;
        }
        return n2;
    }
    
    public static ModelRotation func_177524_a(final int n, final int n2) {
        return ModelRotation.field_177546_q.get(func_177521_b(MathHelper.func_180184_b(n, 360), MathHelper.func_180184_b(n2, 360)));
    }
    
    @Override
    public TRSRTransformation apply(final IModelPart modelPart) {
        return new TRSRTransformation(this.getMatrix());
    }
    
    @Override
    public Matrix4f getMatrix() {
        return (Matrix4f)(Reflector.ForgeHooksClient_getMatrix.exists() ? Reflector.call(Reflector.ForgeHooksClient_getMatrix, this) : new Matrix4f(this.func_177525_a()));
    }
    
    @Override
    public EnumFacing rotate(final EnumFacing enumFacing) {
        return this.func_177523_a(enumFacing);
    }
    
    @Override
    public int rotate(final EnumFacing enumFacing, final int n) {
        return this.func_177520_a(enumFacing, n);
    }
    
    @Override
    public Object apply(final Object o) {
        return this.apply((IModelPart)o);
    }
}
