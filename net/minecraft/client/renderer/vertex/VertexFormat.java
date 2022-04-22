package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.util.*;

public class VertexFormat
{
    private static final Logger field_177357_a;
    private final List elements;
    private final List offsets;
    private final List field_177355_b;
    private final List field_177356_c;
    private int field_177353_d;
    private int field_177354_e;
    private List field_177351_f;
    private int nextOffset;
    private int field_177352_g;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002401";
        field_177357_a = LogManager.getLogger();
    }
    
    public VertexFormat(final VertexFormat vertexFormat) {
        this();
        while (0 < vertexFormat.func_177345_h()) {
            this.func_177349_a(vertexFormat.func_177348_c(0));
            int n = 0;
            ++n;
        }
        this.field_177353_d = vertexFormat.func_177338_f();
    }
    
    public VertexFormat() {
        this.elements = Lists.newArrayList();
        this.offsets = Lists.newArrayList();
        this.nextOffset = 0;
        this.field_177355_b = Lists.newArrayList();
        this.field_177356_c = Lists.newArrayList();
        this.field_177353_d = 0;
        this.field_177354_e = -1;
        this.field_177351_f = Lists.newArrayList();
        this.field_177352_g = -1;
    }
    
    public void clear() {
        this.field_177355_b.clear();
        this.field_177356_c.clear();
        this.field_177354_e = -1;
        this.field_177351_f.clear();
        this.field_177352_g = -1;
        this.field_177353_d = 0;
    }
    
    public void func_177349_a(final VertexFormatElement vertexFormatElement) {
        if (vertexFormatElement.func_177374_g() && this.func_177341_i()) {
            VertexFormat.field_177357_a.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
        }
        else {
            this.field_177355_b.add(vertexFormatElement);
            this.field_177356_c.add(this.field_177353_d);
            vertexFormatElement.func_177371_a(this.field_177353_d);
            this.field_177353_d += vertexFormatElement.func_177368_f();
            switch (SwitchEnumUseage.field_177382_a[vertexFormatElement.func_177375_c().ordinal()]) {
                case 1: {
                    this.field_177352_g = vertexFormatElement.func_177373_a();
                    break;
                }
                case 2: {
                    this.field_177354_e = vertexFormatElement.func_177373_a();
                    break;
                }
                case 3: {
                    this.field_177351_f.add(vertexFormatElement.func_177369_e(), vertexFormatElement.func_177373_a());
                    break;
                }
            }
        }
    }
    
    public boolean func_177350_b() {
        return this.field_177352_g >= 0;
    }
    
    public int func_177342_c() {
        return this.field_177352_g;
    }
    
    public boolean func_177346_d() {
        return this.field_177354_e >= 0;
    }
    
    public int func_177340_e() {
        return this.field_177354_e;
    }
    
    public boolean func_177347_a(final int n) {
        return this.field_177351_f.size() - 1 >= n;
    }
    
    public int func_177344_b(final int n) {
        return this.field_177351_f.get(n);
    }
    
    @Override
    public String toString() {
        String s = "format: " + this.field_177355_b.size() + " elements: ";
        while (0 < this.field_177355_b.size()) {
            s = String.valueOf(s) + this.field_177355_b.get(0).toString();
            if (0 != this.field_177355_b.size() - 1) {
                s = String.valueOf(s) + " ";
            }
            int n = 0;
            ++n;
        }
        return s;
    }
    
    private boolean func_177341_i() {
        final Iterator<VertexFormatElement> iterator = this.field_177355_b.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().func_177374_g()) {
                return true;
            }
        }
        return false;
    }
    
    public int func_177338_f() {
        return this.field_177353_d;
    }
    
    public List func_177343_g() {
        return this.field_177355_b;
    }
    
    public int func_177345_h() {
        return this.field_177355_b.size();
    }
    
    public VertexFormatElement func_177348_c(final int n) {
        return this.field_177355_b.get(n);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final VertexFormat vertexFormat = (VertexFormat)o;
            return this.field_177353_d == vertexFormat.field_177353_d && this.field_177355_b.equals(vertexFormat.field_177355_b) && this.field_177356_c.equals(vertexFormat.field_177356_c);
        }
        return false;
    }
    
    public int func_181719_f() {
        return this.getNextOffset() / 4;
    }
    
    public int getNextOffset() {
        return this.nextOffset;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * this.field_177355_b.hashCode() + this.field_177356_c.hashCode()) + this.field_177353_d;
    }
    
    public VertexFormatElement getElement(final int n) {
        return this.elements.get(n);
    }
    
    public int func_181720_d(final int n) {
        return this.offsets.get(n);
    }
    
    static final class SwitchEnumUseage
    {
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00002400";
            (SwitchEnumUseage.field_177382_a = new int[VertexFormatElement.EnumUseage.values().length])[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 1;
            SwitchEnumUseage.field_177382_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
            SwitchEnumUseage.field_177382_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
        }
    }
}
