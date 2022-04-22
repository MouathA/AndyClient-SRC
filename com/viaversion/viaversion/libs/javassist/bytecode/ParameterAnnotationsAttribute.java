package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.bytecode.annotation.*;
import java.io.*;
import java.util.*;

public class ParameterAnnotationsAttribute extends AttributeInfo
{
    public static final String visibleTag = "RuntimeVisibleParameterAnnotations";
    public static final String invisibleTag = "RuntimeInvisibleParameterAnnotations";
    
    public ParameterAnnotationsAttribute(final ConstPool constPool, final String s, final byte[] array) {
        super(constPool, s, array);
    }
    
    public ParameterAnnotationsAttribute(final ConstPool constPool, final String s) {
        this(constPool, s, new byte[] { 0 });
    }
    
    ParameterAnnotationsAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public int numParameters() {
        return this.info[0] & 0xFF;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, constPool, map);
        copier.parameters();
        return new ParameterAnnotationsAttribute(constPool, this.getName(), copier.close());
    }
    
    public Annotation[][] getAnnotations() {
        return new AnnotationsAttribute.Parser(this.info, this.constPool).parseParameters();
    }
    
    public void setAnnotations(final Annotation[][] array) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final AnnotationsWriter annotationsWriter = new AnnotationsWriter(byteArrayOutputStream, this.constPool);
        annotationsWriter.numParameters(array.length);
        while (0 < array.length) {
            final Annotation[] array2 = array[0];
            annotationsWriter.numAnnotations(array2.length);
            while (0 < array2.length) {
                array2[0].write(annotationsWriter);
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        annotationsWriter.close();
        this.set(byteArrayOutputStream.toByteArray());
    }
    
    @Override
    void renameClass(final String s, final String s2) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(s, s2);
        this.renameClass(hashMap);
    }
    
    @Override
    void renameClass(final Map map) {
        new AnnotationsAttribute.Renamer(this.info, this.getConstPool(), map).parameters();
    }
    
    @Override
    void getRefClasses(final Map map) {
        this.renameClass(map);
    }
    
    @Override
    public String toString() {
        final Annotation[][] annotations = this.getAnnotations();
        final StringBuilder sb = new StringBuilder();
        final Annotation[][] array = annotations;
        while (0 < array.length) {
            final Annotation[] array2 = array[0];
            while (0 < array2.length) {
                sb.append(array2[0].toString()).append(" ");
                int n = 0;
                ++n;
            }
            sb.append(", ");
            int n2 = 0;
            ++n2;
        }
        return sb.toString().replaceAll(" (?=,)|, $", "");
    }
}
