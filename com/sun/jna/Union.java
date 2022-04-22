package com.sun.jna;

import java.util.*;

public abstract class Union extends Structure
{
    private StructField activeField;
    StructField biggestField;
    static Class class$com$sun$jna$Structure;
    static Class class$java$lang$String;
    static Class class$com$sun$jna$WString;
    
    protected Union() {
    }
    
    protected Union(final Pointer pointer) {
        super(pointer);
    }
    
    protected Union(final Pointer pointer, final int n) {
        super(pointer, n);
    }
    
    protected Union(final TypeMapper typeMapper) {
        super(typeMapper);
    }
    
    protected Union(final Pointer pointer, final int n, final TypeMapper typeMapper) {
        super(pointer, n, typeMapper);
    }
    
    public void setType(final Class clazz) {
        this.ensureAllocated();
        for (final StructField activeField : this.fields().values()) {
            if (activeField.type == clazz) {
                this.activeField = activeField;
                return;
            }
        }
        throw new IllegalArgumentException("No field of type " + clazz + " in " + this);
    }
    
    public void setType(final String s) {
        this.ensureAllocated();
        final StructField activeField = this.fields().get(s);
        if (activeField != null) {
            this.activeField = activeField;
            return;
        }
        throw new IllegalArgumentException("No field named " + s + " in " + this);
    }
    
    public Object readField(final String type) {
        this.ensureAllocated();
        this.setType(type);
        return super.readField(type);
    }
    
    public void writeField(final String type) {
        this.ensureAllocated();
        this.setType(type);
        super.writeField(type);
    }
    
    public void writeField(final String type, final Object o) {
        this.ensureAllocated();
        this.setType(type);
        super.writeField(type, o);
    }
    
    public Object getTypedValue(final Class clazz) {
        this.ensureAllocated();
        for (final StructField activeField : this.fields().values()) {
            if (activeField.type == clazz) {
                this.activeField = activeField;
                this.read();
                return this.getField(this.activeField);
            }
        }
        throw new IllegalArgumentException("No field of type " + clazz + " in " + this);
    }
    
    public Object setTypedValue(final Object o) {
        final StructField field = this.findField(o.getClass());
        if (field != null) {
            this.setField(this.activeField = field, o);
            return this;
        }
        throw new IllegalArgumentException("No field of type " + o.getClass() + " in " + this);
    }
    
    private StructField findField(final Class clazz) {
        this.ensureAllocated();
        for (final StructField structField : this.fields().values()) {
            if (structField.type.isAssignableFrom(clazz)) {
                return structField;
            }
        }
        return null;
    }
    
    void writeField(final StructField structField) {
        if (structField == this.activeField) {
            super.writeField(structField);
        }
    }
    
    Object readField(final StructField structField) {
        if (structField == this.activeField || (!((Union.class$com$sun$jna$Structure == null) ? (Union.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Union.class$com$sun$jna$Structure).isAssignableFrom(structField.type) && !((Union.class$java$lang$String == null) ? (Union.class$java$lang$String = class$("java.lang.String")) : Union.class$java$lang$String).isAssignableFrom(structField.type) && !((Union.class$com$sun$jna$WString == null) ? (Union.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Union.class$com$sun$jna$WString).isAssignableFrom(structField.type))) {
            return super.readField(structField);
        }
        return null;
    }
    
    int calculateSize(final boolean b, final boolean b2) {
        int n = super.calculateSize(b, b2);
        if (n != -1) {
            for (final StructField biggestField : this.fields().values()) {
                biggestField.offset = 0;
                if (biggestField.size > 0 || (biggestField.size == 0 && ((Union.class$com$sun$jna$Structure == null) ? (Union.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Union.class$com$sun$jna$Structure).isAssignableFrom(biggestField.type))) {
                    final int size = biggestField.size;
                    this.biggestField = biggestField;
                }
            }
            n = this.calculateAlignedSize(0);
            if (n > 0 && this instanceof ByValue && !b2) {
                this.getTypeInfo();
            }
        }
        return n;
    }
    
    protected int getNativeAlignment(final Class clazz, final Object o, final boolean b) {
        return super.getNativeAlignment(clazz, o, true);
    }
    
    Pointer getTypeInfo() {
        if (this.biggestField == null) {
            return null;
        }
        return super.getTypeInfo();
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
}
