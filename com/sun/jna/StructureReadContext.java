package com.sun.jna;

import java.lang.reflect.*;

public class StructureReadContext extends FromNativeContext
{
    private Structure structure;
    private Field field;
    
    StructureReadContext(final Structure structure, final Field field) {
        super(field.getType());
        this.structure = structure;
        this.field = field;
    }
    
    public Structure getStructure() {
        return this.structure;
    }
    
    public Field getField() {
        return this.field;
    }
}
