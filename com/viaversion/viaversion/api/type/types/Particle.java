package com.viaversion.viaversion.api.type.types;

import java.util.*;
import com.viaversion.viaversion.api.type.*;

public class Particle
{
    private List arguments;
    private int id;
    
    public Particle(final int id) {
        this.arguments = new ArrayList(4);
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public List getArguments() {
        return this.arguments;
    }
    
    @Deprecated
    public void setArguments(final List arguments) {
        this.arguments = arguments;
    }
    
    public void add(final Type type, final Object o) {
        this.arguments.add(new ParticleData(type, o));
    }
    
    public static class ParticleData
    {
        private Type type;
        private Object value;
        
        public ParticleData(final Type type, final Object value) {
            this.type = type;
            this.value = value;
        }
        
        public Type getType() {
            return this.type;
        }
        
        public void setType(final Type type) {
            this.type = type;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public Object get() {
            return this.value;
        }
        
        public void setValue(final Object value) {
            this.value = value;
        }
    }
}
