package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.javassist.bytecode.annotation.*;

public class AnnotationsAttribute extends AttributeInfo
{
    public static final String visibleTag = "RuntimeVisibleAnnotations";
    public static final String invisibleTag = "RuntimeInvisibleAnnotations";
    
    public AnnotationsAttribute(final ConstPool constPool, final String s, final byte[] array) {
        super(constPool, s, array);
    }
    
    public AnnotationsAttribute(final ConstPool constPool, final String s) {
        this(constPool, s, new byte[] { 0, 0 });
    }
    
    AnnotationsAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public int numAnnotations() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final Copier copier = new Copier(this.info, this.constPool, constPool, map);
        copier.annotationArray();
        return new AnnotationsAttribute(constPool, this.getName(), copier.close());
    }
    
    public Annotation getAnnotation(final String s) {
        final Annotation[] annotations = this.getAnnotations();
        while (0 < annotations.length) {
            if (annotations[0].getTypeName().equals(s)) {
                return annotations[0];
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public void addAnnotation(final Annotation annotation) {
        final String typeName = annotation.getTypeName();
        final Annotation[] annotations = this.getAnnotations();
        while (0 < annotations.length) {
            if (annotations[0].getTypeName().equals(typeName)) {
                annotations[0] = annotation;
                this.setAnnotations(annotations);
                return;
            }
            int n = 0;
            ++n;
        }
        final Annotation[] annotations2 = new Annotation[annotations.length + 1];
        System.arraycopy(annotations, 0, annotations2, 0, annotations.length);
        annotations2[annotations.length] = annotation;
        this.setAnnotations(annotations2);
    }
    
    public boolean removeAnnotation(final String s) {
        final Annotation[] annotations = this.getAnnotations();
        while (0 < annotations.length) {
            if (annotations[0].getTypeName().equals(s)) {
                final Annotation[] annotations2 = new Annotation[annotations.length - 1];
                System.arraycopy(annotations, 0, annotations2, 0, 0);
                if (0 < annotations.length - 1) {
                    System.arraycopy(annotations, 1, annotations2, 0, annotations.length - 0 - 1);
                }
                this.setAnnotations(annotations2);
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public Annotation[] getAnnotations() {
        return new Parser(this.info, this.constPool).parseAnnotations();
    }
    
    public void setAnnotations(final Annotation[] array) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final AnnotationsWriter annotationsWriter = new AnnotationsWriter(byteArrayOutputStream, this.constPool);
        final int length = array.length;
        annotationsWriter.numAnnotations(length);
        while (0 < length) {
            array[0].write(annotationsWriter);
            int n = 0;
            ++n;
        }
        annotationsWriter.close();
        this.set(byteArrayOutputStream.toByteArray());
    }
    
    public void setAnnotation(final Annotation annotation) {
        this.setAnnotations(new Annotation[] { annotation });
    }
    
    @Override
    void renameClass(final String s, final String s2) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(s, s2);
        this.renameClass(hashMap);
    }
    
    @Override
    void renameClass(final Map map) {
        new Renamer(this.info, this.getConstPool(), map).annotationArray();
    }
    
    @Override
    void getRefClasses(final Map map) {
        this.renameClass(map);
    }
    
    @Override
    public String toString() {
        final Annotation[] annotations = this.getAnnotations();
        final StringBuilder sb = new StringBuilder();
        while (0 < annotations.length) {
            final StringBuilder sb2 = sb;
            final Annotation[] array = annotations;
            final int n = 0;
            int n2 = 0;
            ++n2;
            sb2.append(array[n].toString());
            if (0 != annotations.length) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    static class Parser extends Walker
    {
        ConstPool pool;
        Annotation[][] allParams;
        Annotation[] allAnno;
        Annotation currentAnno;
        MemberValue currentMember;
        
        Parser(final byte[] array, final ConstPool pool) {
            super(array);
            this.pool = pool;
        }
        
        Annotation[][] parseParameters() throws Exception {
            this.parameters();
            return this.allParams;
        }
        
        Annotation[] parseAnnotations() throws Exception {
            this.annotationArray();
            return this.allAnno;
        }
        
        MemberValue parseMemberValue() throws Exception {
            this.memberValue(0);
            return this.currentMember;
        }
        
        @Override
        void parameters(final int n, int annotationArray) throws Exception {
            final Annotation[][] allParams = new Annotation[n][];
            while (0 < n) {
                annotationArray = this.annotationArray(annotationArray);
                allParams[0] = this.allAnno;
                int n2 = 0;
                ++n2;
            }
            this.allParams = allParams;
        }
        
        @Override
        int annotationArray(int annotation, final int n) throws Exception {
            final Annotation[] allAnno = new Annotation[n];
            while (0 < n) {
                annotation = this.annotation(annotation);
                allAnno[0] = this.currentAnno;
                int n2 = 0;
                ++n2;
            }
            this.allAnno = allAnno;
            return annotation;
        }
        
        @Override
        int annotation(final int n, final int n2, final int n3) throws Exception {
            this.currentAnno = new Annotation(n2, this.pool);
            return super.annotation(n, n2, n3);
        }
        
        @Override
        int memberValuePair(int memberValuePair, final int n) throws Exception {
            memberValuePair = super.memberValuePair(memberValuePair, n);
            this.currentAnno.addMemberValue(n, this.currentMember);
            return memberValuePair;
        }
        
        @Override
        void constValueMember(final int n, final int n2) throws Exception {
            final ConstPool pool = this.pool;
            MemberValue currentMember = null;
            switch (n) {
                case 66: {
                    currentMember = new ByteMemberValue(n2, pool);
                    break;
                }
                case 67: {
                    currentMember = new CharMemberValue(n2, pool);
                    break;
                }
                case 68: {
                    currentMember = new DoubleMemberValue(n2, pool);
                    break;
                }
                case 70: {
                    currentMember = new FloatMemberValue(n2, pool);
                    break;
                }
                case 73: {
                    currentMember = new IntegerMemberValue(n2, pool);
                    break;
                }
                case 74: {
                    currentMember = new LongMemberValue(n2, pool);
                    break;
                }
                case 83: {
                    currentMember = new ShortMemberValue(n2, pool);
                    break;
                }
                case 90: {
                    currentMember = new BooleanMemberValue(n2, pool);
                    break;
                }
                case 115: {
                    currentMember = new StringMemberValue(n2, pool);
                    break;
                }
                default: {
                    throw new RuntimeException("unknown tag:" + n);
                }
            }
            this.currentMember = currentMember;
            super.constValueMember(n, n2);
        }
        
        @Override
        void enumMemberValue(final int n, final int n2, final int n3) throws Exception {
            this.currentMember = new EnumMemberValue(n2, n3, this.pool);
            super.enumMemberValue(n, n2, n3);
        }
        
        @Override
        void classMemberValue(final int n, final int n2) throws Exception {
            this.currentMember = new ClassMemberValue(n2, this.pool);
            super.classMemberValue(n, n2);
        }
        
        @Override
        int annotationMemberValue(int annotationMemberValue) throws Exception {
            final Annotation currentAnno = this.currentAnno;
            annotationMemberValue = super.annotationMemberValue(annotationMemberValue);
            this.currentMember = new AnnotationMemberValue(this.currentAnno, this.pool);
            this.currentAnno = currentAnno;
            return annotationMemberValue;
        }
        
        @Override
        int arrayMemberValue(int memberValue, final int n) throws Exception {
            final ArrayMemberValue currentMember = new ArrayMemberValue(this.pool);
            final MemberValue[] value = new MemberValue[n];
            while (0 < n) {
                memberValue = this.memberValue(memberValue);
                value[0] = this.currentMember;
                int n2 = 0;
                ++n2;
            }
            currentMember.setValue(value);
            this.currentMember = currentMember;
            return memberValue;
        }
    }
    
    static class Walker
    {
        byte[] info;
        
        Walker(final byte[] info) {
            this.info = info;
        }
        
        final void parameters() throws Exception {
            this.parameters(this.info[0] & 0xFF, 1);
        }
        
        void parameters(final int n, int annotationArray) throws Exception {
            while (0 < n) {
                annotationArray = this.annotationArray(annotationArray);
                int n2 = 0;
                ++n2;
            }
        }
        
        final void annotationArray() throws Exception {
            this.annotationArray(0);
        }
        
        final int annotationArray(final int n) throws Exception {
            return this.annotationArray(n + 2, ByteArray.readU16bit(this.info, n));
        }
        
        int annotationArray(int annotation, final int n) throws Exception {
            while (0 < n) {
                annotation = this.annotation(annotation);
                int n2 = 0;
                ++n2;
            }
            return annotation;
        }
        
        final int annotation(final int n) throws Exception {
            return this.annotation(n + 4, ByteArray.readU16bit(this.info, n), ByteArray.readU16bit(this.info, n + 2));
        }
        
        int annotation(int memberValuePair, final int n, final int n2) throws Exception {
            while (0 < n2) {
                memberValuePair = this.memberValuePair(memberValuePair);
                int n3 = 0;
                ++n3;
            }
            return memberValuePair;
        }
        
        final int memberValuePair(final int n) throws Exception {
            return this.memberValuePair(n + 2, ByteArray.readU16bit(this.info, n));
        }
        
        int memberValuePair(final int n, final int n2) throws Exception {
            return this.memberValue(n);
        }
        
        final int memberValue(final int n) throws Exception {
            final int n2 = this.info[n] & 0xFF;
            if (n2 == 101) {
                this.enumMemberValue(n, ByteArray.readU16bit(this.info, n + 1), ByteArray.readU16bit(this.info, n + 3));
                return n + 5;
            }
            if (n2 == 99) {
                this.classMemberValue(n, ByteArray.readU16bit(this.info, n + 1));
                return n + 3;
            }
            if (n2 == 64) {
                return this.annotationMemberValue(n + 1);
            }
            if (n2 == 91) {
                return this.arrayMemberValue(n + 3, ByteArray.readU16bit(this.info, n + 1));
            }
            this.constValueMember(n2, ByteArray.readU16bit(this.info, n + 1));
            return n + 3;
        }
        
        void constValueMember(final int n, final int n2) throws Exception {
        }
        
        void enumMemberValue(final int n, final int n2, final int n3) throws Exception {
        }
        
        void classMemberValue(final int n, final int n2) throws Exception {
        }
        
        int annotationMemberValue(final int n) throws Exception {
            return this.annotation(n);
        }
        
        int arrayMemberValue(int memberValue, final int n) throws Exception {
            while (0 < n) {
                memberValue = this.memberValue(memberValue);
                int n2 = 0;
                ++n2;
            }
            return memberValue;
        }
    }
    
    static class Copier extends Walker
    {
        ByteArrayOutputStream output;
        AnnotationsWriter writer;
        ConstPool srcPool;
        ConstPool destPool;
        Map classnames;
        
        Copier(final byte[] array, final ConstPool constPool, final ConstPool constPool2, final Map map) {
            this(array, constPool, constPool2, map, true);
        }
        
        Copier(final byte[] array, final ConstPool srcPool, final ConstPool destPool, final Map classnames, final boolean b) {
            super(array);
            this.output = new ByteArrayOutputStream();
            if (b) {
                this.writer = new AnnotationsWriter(this.output, destPool);
            }
            this.srcPool = srcPool;
            this.destPool = destPool;
            this.classnames = classnames;
        }
        
        byte[] close() throws IOException {
            this.writer.close();
            return this.output.toByteArray();
        }
        
        @Override
        void parameters(final int n, final int n2) throws Exception {
            this.writer.numParameters(n);
            super.parameters(n, n2);
        }
        
        @Override
        int annotationArray(final int n, final int n2) throws Exception {
            this.writer.numAnnotations(n2);
            return super.annotationArray(n, n2);
        }
        
        @Override
        int annotation(final int n, final int n2, final int n3) throws Exception {
            this.writer.annotation(this.copyType(n2), n3);
            return super.annotation(n, n2, n3);
        }
        
        @Override
        int memberValuePair(final int n, final int n2) throws Exception {
            this.writer.memberValuePair(this.copy(n2));
            return super.memberValuePair(n, n2);
        }
        
        @Override
        void constValueMember(final int n, final int n2) throws Exception {
            this.writer.constValueIndex(n, this.copy(n2));
            super.constValueMember(n, n2);
        }
        
        @Override
        void enumMemberValue(final int n, final int n2, final int n3) throws Exception {
            this.writer.enumConstValue(this.copyType(n2), this.copy(n3));
            super.enumMemberValue(n, n2, n3);
        }
        
        @Override
        void classMemberValue(final int n, final int n2) throws Exception {
            this.writer.classInfoIndex(this.copyType(n2));
            super.classMemberValue(n, n2);
        }
        
        @Override
        int annotationMemberValue(final int n) throws Exception {
            this.writer.annotationValue();
            return super.annotationMemberValue(n);
        }
        
        @Override
        int arrayMemberValue(final int n, final int n2) throws Exception {
            this.writer.arrayValue(n2);
            return super.arrayMemberValue(n, n2);
        }
        
        int copy(final int n) {
            return this.srcPool.copy(n, this.destPool, this.classnames);
        }
        
        int copyType(final int n) {
            return this.destPool.addUtf8Info(Descriptor.rename(this.srcPool.getUtf8Info(n), this.classnames));
        }
    }
    
    static class Renamer extends Walker
    {
        ConstPool cpool;
        Map classnames;
        
        Renamer(final byte[] array, final ConstPool cpool, final Map classnames) {
            super(array);
            this.cpool = cpool;
            this.classnames = classnames;
        }
        
        @Override
        int annotation(final int n, final int n2, final int n3) throws Exception {
            this.renameType(n - 4, n2);
            return super.annotation(n, n2, n3);
        }
        
        @Override
        void enumMemberValue(final int n, final int n2, final int n3) throws Exception {
            this.renameType(n + 1, n2);
            super.enumMemberValue(n, n2, n3);
        }
        
        @Override
        void classMemberValue(final int n, final int n2) throws Exception {
            this.renameType(n + 1, n2);
            super.classMemberValue(n, n2);
        }
        
        private void renameType(final int n, final int n2) {
            final String utf8Info = this.cpool.getUtf8Info(n2);
            final String rename = Descriptor.rename(utf8Info, this.classnames);
            if (!utf8Info.equals(rename)) {
                ByteArray.write16bit(this.cpool.addUtf8Info(rename), this.info, n);
            }
        }
    }
}
