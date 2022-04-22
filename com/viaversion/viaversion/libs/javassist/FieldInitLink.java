package com.viaversion.viaversion.libs.javassist;

class FieldInitLink
{
    FieldInitLink next;
    CtField field;
    CtField.Initializer init;
    
    FieldInitLink(final CtField field, final CtField.Initializer init) {
        this.next = null;
        this.field = field;
        this.init = init;
    }
}
