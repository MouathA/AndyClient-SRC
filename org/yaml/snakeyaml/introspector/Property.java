package org.yaml.snakeyaml.introspector;

import java.util.*;
import java.lang.annotation.*;

public abstract class Property implements Comparable
{
    private final String name;
    private final Class type;
    
    public Property(final String name, final Class type) {
        this.name = name;
        this.type = type;
    }
    
    public Class getType() {
        return this.type;
    }
    
    public abstract Class[] getActualTypeArguments();
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.getName() + " of " + this.getType();
    }
    
    public int compareTo(final Property property) {
        return this.getName().compareTo(property.getName());
    }
    
    public boolean isWritable() {
        return true;
    }
    
    public boolean isReadable() {
        return true;
    }
    
    public abstract void set(final Object p0, final Object p1) throws Exception;
    
    public abstract Object get(final Object p0);
    
    public abstract List getAnnotations();
    
    public abstract Annotation getAnnotation(final Class p0);
    
    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getType().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Property) {
            final Property property = (Property)o;
            return this.getName().equals(property.getName()) && this.getType().equals(property.getType());
        }
        return false;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Property)o);
    }
}
