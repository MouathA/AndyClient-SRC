package net.minecraft.util;

import org.apache.commons.lang3.*;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced
{
    private final Object field_148760_d;
    private Object field_148761_e;
    private static final String __OBFID;
    
    public RegistryNamespacedDefaultedByKey(final Object field_148760_d) {
        this.field_148760_d = field_148760_d;
    }
    
    @Override
    public void register(final int n, final Object o, final Object field_148761_e) {
        if (this.field_148760_d.equals(o)) {
            this.field_148761_e = field_148761_e;
        }
        super.register(n, o, field_148761_e);
    }
    
    public void validateKey() {
        Validate.notNull(this.field_148760_d);
    }
    
    @Override
    public Object getObject(final Object o) {
        final Object object = super.getObject(o);
        return (object == null) ? this.field_148761_e : object;
    }
    
    @Override
    public Object getObjectById(final int n) {
        final Object objectById = super.getObjectById(n);
        return (objectById == null) ? this.field_148761_e : objectById;
    }
    
    static {
        __OBFID = "CL_00001196";
    }
}
