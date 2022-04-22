package net.minecraft.block.state.pattern;

import com.google.common.collect.*;
import org.apache.commons.lang3.*;
import com.google.common.base.*;
import java.lang.reflect.*;
import java.util.*;

public class FactoryBlockPattern
{
    private static final Joiner field_177667_a;
    private final List field_177665_b;
    private final Map field_177666_c;
    private int field_177663_d;
    private int field_177664_e;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002021";
        field_177667_a = Joiner.on(",");
    }
    
    private FactoryBlockPattern() {
        this.field_177665_b = Lists.newArrayList();
        (this.field_177666_c = Maps.newHashMap()).put(' ', Predicates.alwaysTrue());
    }
    
    public FactoryBlockPattern aisle(final String... array) {
        if (ArrayUtils.isEmpty(array) || StringUtils.isEmpty(array[0])) {
            throw new IllegalArgumentException("Empty pattern for aisle");
        }
        if (this.field_177665_b.isEmpty()) {
            this.field_177663_d = array.length;
            this.field_177664_e = array[0].length();
        }
        if (array.length != this.field_177663_d) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.field_177663_d + ", but was given one with a height of " + array.length + ")");
        }
        while (0 < array.length) {
            final String s = array[0];
            if (s.length() != this.field_177664_e) {
                throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.field_177664_e + ", found one with " + s.length() + ")");
            }
            final char[] charArray = s.toCharArray();
            while (0 < charArray.length) {
                final char c = charArray[0];
                if (!this.field_177666_c.containsKey(c)) {
                    this.field_177666_c.put(c, null);
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        this.field_177665_b.add(array);
        return this;
    }
    
    public static FactoryBlockPattern start() {
        return new FactoryBlockPattern();
    }
    
    public FactoryBlockPattern where(final char c, final Predicate predicate) {
        this.field_177666_c.put(c, predicate);
        return this;
    }
    
    public BlockPattern build() {
        return new BlockPattern(this.func_177658_c());
    }
    
    private Predicate[][][] func_177658_c() {
        this.func_177657_d();
        final Predicate[][][] array = (Predicate[][][])Array.newInstance(Predicate.class, this.field_177665_b.size(), this.field_177663_d, this.field_177664_e);
        while (0 < this.field_177665_b.size()) {
            while (0 < this.field_177663_d) {
                while (0 < this.field_177664_e) {
                    array[0][0][0] = this.field_177666_c.get(((String[])this.field_177665_b.get(0))[0].charAt(0));
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return array;
    }
    
    private void func_177657_d() {
        final ArrayList arrayList = Lists.newArrayList();
        for (final Map.Entry<Object, V> entry : this.field_177666_c.entrySet()) {
            if (entry.getValue() == null) {
                arrayList.add(entry.getKey());
            }
        }
        if (!arrayList.isEmpty()) {
            throw new IllegalStateException("Predicates for character(s) " + FactoryBlockPattern.field_177667_a.join(arrayList) + " are missing");
        }
    }
}
