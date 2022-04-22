package org.yaml.snakeyaml.introspector;

import java.lang.reflect.*;
import java.util.*;
import org.yaml.snakeyaml.util.*;
import java.lang.annotation.*;

public class FieldProperty extends GenericProperty
{
    private final Field field;
    
    public FieldProperty(final Field field) {
        super(field.getName(), field.getType(), field.getGenericType());
        (this.field = field).setAccessible(true);
    }
    
    @Override
    public void set(final Object o, final Object o2) throws Exception {
        this.field.set(o, o2);
    }
    
    @Override
    public Object get(final Object o) {
        return this.field.get(o);
    }
    
    @Override
    public List getAnnotations() {
        return ArrayUtils.toUnmodifiableList(this.field.getAnnotations());
    }
    
    @Override
    public Annotation getAnnotation(final Class clazz) {
        return this.field.getAnnotation((Class<Annotation>)clazz);
    }
}
