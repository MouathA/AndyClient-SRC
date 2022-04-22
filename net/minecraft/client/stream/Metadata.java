package net.minecraft.client.stream;

import com.google.gson.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public class Metadata
{
    private static final Gson field_152811_a;
    private final String field_152812_b;
    private String field_152813_c;
    private Map field_152814_d;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001823";
        field_152811_a = new Gson();
    }
    
    public Metadata(final String field_152812_b, final String field_152813_c) {
        this.field_152812_b = field_152812_b;
        this.field_152813_c = field_152813_c;
    }
    
    public Metadata(final String s) {
        this(s, null);
    }
    
    public void func_152807_a(final String field_152813_c) {
        this.field_152813_c = field_152813_c;
    }
    
    public String func_152809_a() {
        return (this.field_152813_c == null) ? this.field_152812_b : this.field_152813_c;
    }
    
    public void func_152808_a(final String s, final String s2) {
        if (this.field_152814_d == null) {
            this.field_152814_d = Maps.newHashMap();
        }
        if (this.field_152814_d.size() > 50) {
            throw new IllegalArgumentException("Metadata payload is full, cannot add more to it!");
        }
        if (s == null) {
            throw new IllegalArgumentException("Metadata payload key cannot be null!");
        }
        if (s.length() > 255) {
            throw new IllegalArgumentException("Metadata payload key is too long!");
        }
        if (s2 == null) {
            throw new IllegalArgumentException("Metadata payload value cannot be null!");
        }
        if (s2.length() > 255) {
            throw new IllegalArgumentException("Metadata payload value is too long!");
        }
        this.field_152814_d.put(s, s2);
    }
    
    public String func_152806_b() {
        return (this.field_152814_d != null && !this.field_152814_d.isEmpty()) ? Metadata.field_152811_a.toJson(this.field_152814_d) : null;
    }
    
    public String func_152810_c() {
        return this.field_152812_b;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name", this.field_152812_b).add("description", this.field_152813_c).add("data", this.func_152806_b()).toString();
    }
}
