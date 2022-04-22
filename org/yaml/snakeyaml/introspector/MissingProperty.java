package org.yaml.snakeyaml.introspector;

import java.util.*;
import java.lang.annotation.*;

public class MissingProperty extends Property
{
    public MissingProperty(final String s) {
        super(s, Object.class);
    }
    
    @Override
    public Class[] getActualTypeArguments() {
        return new Class[0];
    }
    
    @Override
    public void set(final Object o, final Object o2) throws Exception {
    }
    
    @Override
    public Object get(final Object o) {
        return o;
    }
    
    @Override
    public List getAnnotations() {
        return Collections.emptyList();
    }
    
    @Override
    public Annotation getAnnotation(final Class clazz) {
        return null;
    }
}
