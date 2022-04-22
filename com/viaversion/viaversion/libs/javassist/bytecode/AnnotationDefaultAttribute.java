package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.bytecode.annotation.*;
import java.io.*;

public class AnnotationDefaultAttribute extends AttributeInfo
{
    public static final String tag = "AnnotationDefault";
    
    public AnnotationDefaultAttribute(final ConstPool constPool, final byte[] array) {
        super(constPool, "AnnotationDefault", array);
    }
    
    public AnnotationDefaultAttribute(final ConstPool constPool) {
        this(constPool, new byte[] { 0, 0 });
    }
    
    AnnotationDefaultAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, constPool, map);
        copier.memberValue(0);
        return new AnnotationDefaultAttribute(constPool, copier.close());
    }
    
    public MemberValue getDefaultValue() {
        return new AnnotationsAttribute.Parser(this.info, this.constPool).parseMemberValue();
    }
    
    public void setDefaultValue(final MemberValue memberValue) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final AnnotationsWriter annotationsWriter = new AnnotationsWriter(byteArrayOutputStream, this.constPool);
        memberValue.write(annotationsWriter);
        annotationsWriter.close();
        this.set(byteArrayOutputStream.toByteArray());
    }
    
    @Override
    public String toString() {
        return this.getDefaultValue().toString();
    }
}
