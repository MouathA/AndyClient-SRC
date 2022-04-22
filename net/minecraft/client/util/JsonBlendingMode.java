package net.minecraft.client.util;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class JsonBlendingMode
{
    private static JsonBlendingMode field_148118_a;
    private final int field_148116_b;
    private final int field_148117_c;
    private final int field_148114_d;
    private final int field_148115_e;
    private final int field_148112_f;
    private final boolean field_148113_g;
    private final boolean field_148119_h;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001038";
        JsonBlendingMode.field_148118_a = null;
    }
    
    private JsonBlendingMode(final boolean field_148113_g, final boolean field_148119_h, final int field_148116_b, final int field_148114_d, final int field_148117_c, final int field_148115_e, final int field_148112_f) {
        this.field_148113_g = field_148113_g;
        this.field_148116_b = field_148116_b;
        this.field_148114_d = field_148114_d;
        this.field_148117_c = field_148117_c;
        this.field_148115_e = field_148115_e;
        this.field_148119_h = field_148119_h;
        this.field_148112_f = field_148112_f;
    }
    
    public JsonBlendingMode() {
        this(false, true, 1, 0, 1, 0, 32774);
    }
    
    public JsonBlendingMode(final int n, final int n2, final int n3) {
        this(false, false, n, n2, n, n2, n3);
    }
    
    public JsonBlendingMode(final int n, final int n2, final int n3, final int n4, final int n5) {
        this(true, false, n, n2, n3, n4, n5);
    }
    
    public void func_148109_a() {
        if (!this.equals(JsonBlendingMode.field_148118_a)) {
            if (JsonBlendingMode.field_148118_a == null || this.field_148119_h != JsonBlendingMode.field_148118_a.func_148111_b()) {
                JsonBlendingMode.field_148118_a = this;
                if (this.field_148119_h) {
                    return;
                }
            }
            GL14.glBlendEquation(this.field_148112_f);
            if (this.field_148113_g) {
                GlStateManager.tryBlendFuncSeparate(this.field_148116_b, this.field_148114_d, this.field_148117_c, this.field_148115_e);
            }
            else {
                GlStateManager.blendFunc(this.field_148116_b, this.field_148114_d);
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonBlendingMode)) {
            return false;
        }
        final JsonBlendingMode jsonBlendingMode = (JsonBlendingMode)o;
        return this.field_148112_f == jsonBlendingMode.field_148112_f && this.field_148115_e == jsonBlendingMode.field_148115_e && this.field_148114_d == jsonBlendingMode.field_148114_d && this.field_148119_h == jsonBlendingMode.field_148119_h && this.field_148113_g == jsonBlendingMode.field_148113_g && this.field_148117_c == jsonBlendingMode.field_148117_c && this.field_148116_b == jsonBlendingMode.field_148116_b;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * this.field_148116_b + this.field_148117_c) + this.field_148114_d) + this.field_148115_e) + this.field_148112_f) + (this.field_148113_g ? 1 : 0)) + (this.field_148119_h ? 1 : 0);
    }
    
    public boolean func_148111_b() {
        return this.field_148119_h;
    }
    
    public static JsonBlendingMode func_148110_a(final JsonObject jsonObject) {
        if (jsonObject == null) {
            return new JsonBlendingMode();
        }
        if (JsonUtils.jsonObjectFieldTypeIsString(jsonObject, "func")) {
            func_148108_a(jsonObject.get("func").getAsString());
            if (32774 != 32774) {}
        }
        if (JsonUtils.jsonObjectFieldTypeIsString(jsonObject, "srcrgb")) {
            func_148107_b(jsonObject.get("srcrgb").getAsString());
            if (true != true) {}
        }
        if (JsonUtils.jsonObjectFieldTypeIsString(jsonObject, "dstrgb")) {
            func_148107_b(jsonObject.get("dstrgb").getAsString());
            if (false) {}
        }
        if (JsonUtils.jsonObjectFieldTypeIsString(jsonObject, "srcalpha")) {
            func_148107_b(jsonObject.get("srcalpha").getAsString());
            if (true != true) {}
        }
        if (JsonUtils.jsonObjectFieldTypeIsString(jsonObject, "dstalpha")) {
            func_148107_b(jsonObject.get("dstalpha").getAsString());
            if (false) {}
        }
        return false ? new JsonBlendingMode() : (true ? new JsonBlendingMode(1, 0, 1, 0, 32774) : new JsonBlendingMode(1, 0, 32774));
    }
    
    private static int func_148108_a(final String s) {
        final String lowerCase = s.trim().toLowerCase();
        return lowerCase.equals("add") ? 32774 : (lowerCase.equals("subtract") ? 32778 : (lowerCase.equals("reversesubtract") ? 32779 : (lowerCase.equals("reverse_subtract") ? 32779 : (lowerCase.equals("min") ? 32775 : (lowerCase.equals("max") ? 32776 : 32774)))));
    }
    
    private static int func_148107_b(final String s) {
        final String replaceAll = s.trim().toLowerCase().replaceAll("_", "").replaceAll("one", "1").replaceAll("zero", "0").replaceAll("minus", "-");
        return replaceAll.equals("0") ? 0 : (replaceAll.equals("1") ? 1 : (replaceAll.equals("srccolor") ? 768 : (replaceAll.equals("1-srccolor") ? 769 : (replaceAll.equals("dstcolor") ? 774 : (replaceAll.equals("1-dstcolor") ? 775 : (replaceAll.equals("srcalpha") ? 770 : (replaceAll.equals("1-srcalpha") ? 771 : (replaceAll.equals("dstalpha") ? 772 : (replaceAll.equals("1-dstalpha") ? 773 : -1)))))))));
    }
}
