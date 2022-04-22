package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.bytecode.annotation.*;
import java.io.*;

public class TypeAnnotationsAttribute extends AttributeInfo
{
    public static final String visibleTag = "RuntimeVisibleTypeAnnotations";
    public static final String invisibleTag = "RuntimeInvisibleTypeAnnotations";
    
    public TypeAnnotationsAttribute(final ConstPool constPool, final String s, final byte[] array) {
        super(constPool, s, array);
    }
    
    TypeAnnotationsAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public int numAnnotations() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final Copier copier = new Copier(this.info, this.constPool, constPool, map);
        copier.annotationArray();
        return new TypeAnnotationsAttribute(constPool, this.getName(), copier.close());
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
    
    static class SubCopier extends SubWalker
    {
        ConstPool srcPool;
        ConstPool destPool;
        Map classnames;
        TypeAnnotationsWriter writer;
        
        SubCopier(final byte[] array, final ConstPool srcPool, final ConstPool destPool, final Map classnames, final TypeAnnotationsWriter writer) {
            super(array);
            this.srcPool = srcPool;
            this.destPool = destPool;
            this.classnames = classnames;
            this.writer = writer;
        }
        
        @Override
        void typeParameterTarget(final int n, final int n2, final int n3) throws Exception {
            this.writer.typeParameterTarget(n2, n3);
        }
        
        @Override
        void supertypeTarget(final int n, final int n2) throws Exception {
            this.writer.supertypeTarget(n2);
        }
        
        @Override
        void typeParameterBoundTarget(final int n, final int n2, final int n3, final int n4) throws Exception {
            this.writer.typeParameterBoundTarget(n2, n3, n4);
        }
        
        @Override
        void emptyTarget(final int n, final int n2) throws Exception {
            this.writer.emptyTarget(n2);
        }
        
        @Override
        void formalParameterTarget(final int n, final int n2) throws Exception {
            this.writer.formalParameterTarget(n2);
        }
        
        @Override
        void throwsTarget(final int n, final int n2) throws Exception {
            this.writer.throwsTarget(n2);
        }
        
        @Override
        int localvarTarget(final int n, final int n2, final int n3) throws Exception {
            this.writer.localVarTarget(n2, n3);
            return super.localvarTarget(n, n2, n3);
        }
        
        @Override
        void localvarTarget(final int n, final int n2, final int n3, final int n4, final int n5) throws Exception {
            this.writer.localVarTargetTable(n3, n4, n5);
        }
        
        @Override
        void catchTarget(final int n, final int n2) throws Exception {
            this.writer.catchTarget(n2);
        }
        
        @Override
        void offsetTarget(final int n, final int n2, final int n3) throws Exception {
            this.writer.offsetTarget(n2, n3);
        }
        
        @Override
        void typeArgumentTarget(final int n, final int n2, final int n3, final int n4) throws Exception {
            this.writer.typeArgumentTarget(n2, n3, n4);
        }
        
        @Override
        int typePath(final int n, final int n2) throws Exception {
            this.writer.typePath(n2);
            return super.typePath(n, n2);
        }
        
        @Override
        void typePath(final int n, final int n2, final int n3) throws Exception {
            this.writer.typePathPath(n2, n3);
        }
    }
    
    static class SubWalker
    {
        byte[] info;
        
        SubWalker(final byte[] info) {
            this.info = info;
        }
        
        final int targetInfo(final int n, final int n2) throws Exception {
            switch (n2) {
                case 0:
                case 1: {
                    this.typeParameterTarget(n, n2, this.info[n] & 0xFF);
                    return n + 1;
                }
                case 16: {
                    this.supertypeTarget(n, ByteArray.readU16bit(this.info, n));
                    return n + 2;
                }
                case 17:
                case 18: {
                    this.typeParameterBoundTarget(n, n2, this.info[n] & 0xFF, this.info[n + 1] & 0xFF);
                    return n + 2;
                }
                case 19:
                case 20:
                case 21: {
                    this.emptyTarget(n, n2);
                    return n;
                }
                case 22: {
                    this.formalParameterTarget(n, this.info[n] & 0xFF);
                    return n + 1;
                }
                case 23: {
                    this.throwsTarget(n, ByteArray.readU16bit(this.info, n));
                    return n + 2;
                }
                case 64:
                case 65: {
                    return this.localvarTarget(n + 2, n2, ByteArray.readU16bit(this.info, n));
                }
                case 66: {
                    this.catchTarget(n, ByteArray.readU16bit(this.info, n));
                    return n + 2;
                }
                case 67:
                case 68:
                case 69:
                case 70: {
                    this.offsetTarget(n, n2, ByteArray.readU16bit(this.info, n));
                    return n + 2;
                }
                case 71:
                case 72:
                case 73:
                case 74:
                case 75: {
                    this.typeArgumentTarget(n, n2, ByteArray.readU16bit(this.info, n), this.info[n + 2] & 0xFF);
                    return n + 3;
                }
                default: {
                    throw new RuntimeException("invalid target type: " + n2);
                }
            }
        }
        
        void typeParameterTarget(final int n, final int n2, final int n3) throws Exception {
        }
        
        void supertypeTarget(final int n, final int n2) throws Exception {
        }
        
        void typeParameterBoundTarget(final int n, final int n2, final int n3, final int n4) throws Exception {
        }
        
        void emptyTarget(final int n, final int n2) throws Exception {
        }
        
        void formalParameterTarget(final int n, final int n2) throws Exception {
        }
        
        void throwsTarget(final int n, final int n2) throws Exception {
        }
        
        int localvarTarget(int n, final int n2, final int n3) throws Exception {
            while (0 < n3) {
                this.localvarTarget(n, n2, ByteArray.readU16bit(this.info, n), ByteArray.readU16bit(this.info, n + 2), ByteArray.readU16bit(this.info, n + 4));
                n += 6;
                int n4 = 0;
                ++n4;
            }
            return n;
        }
        
        void localvarTarget(final int n, final int n2, final int n3, final int n4, final int n5) throws Exception {
        }
        
        void catchTarget(final int n, final int n2) throws Exception {
        }
        
        void offsetTarget(final int n, final int n2, final int n3) throws Exception {
        }
        
        void typeArgumentTarget(final int n, final int n2, final int n3, final int n4) throws Exception {
        }
        
        final int typePath(int n) throws Exception {
            return this.typePath(n, this.info[n++] & 0xFF);
        }
        
        int typePath(int n, final int n2) throws Exception {
            while (0 < n2) {
                this.typePath(n, this.info[n] & 0xFF, this.info[n + 1] & 0xFF);
                n += 2;
                int n3 = 0;
                ++n3;
            }
            return n;
        }
        
        void typePath(final int n, final int n2, final int n3) throws Exception {
        }
    }
    
    static class Copier extends AnnotationsAttribute.Copier
    {
        SubCopier sub;
        
        Copier(final byte[] array, final ConstPool constPool, final ConstPool constPool2, final Map map) {
            super(array, constPool, constPool2, map, false);
            final TypeAnnotationsWriter writer = new TypeAnnotationsWriter(this.output, constPool2);
            this.writer = writer;
            this.sub = new SubCopier(array, constPool, constPool2, map, writer);
        }
        
        @Override
        int annotationArray(int n, final int n2) throws Exception {
            this.writer.numAnnotations(n2);
            while (0 < n2) {
                n = this.sub.targetInfo(n + 1, this.info[n] & 0xFF);
                n = this.sub.typePath(n);
                n = this.annotation(n);
                int n3 = 0;
                ++n3;
            }
            return n;
        }
    }
    
    static class Renamer extends AnnotationsAttribute.Renamer
    {
        SubWalker sub;
        
        Renamer(final byte[] array, final ConstPool constPool, final Map map) {
            super(array, constPool, map);
            this.sub = new SubWalker(array);
        }
        
        @Override
        int annotationArray(int n, final int n2) throws Exception {
            while (0 < n2) {
                n = this.sub.targetInfo(n + 1, this.info[n] & 0xFF);
                n = this.sub.typePath(n);
                n = this.annotation(n);
                int n3 = 0;
                ++n3;
            }
            return n;
        }
    }
    
    static class TAWalker extends AnnotationsAttribute.Walker
    {
        SubWalker subWalker;
        
        TAWalker(final byte[] array) {
            super(array);
            this.subWalker = new SubWalker(array);
        }
        
        @Override
        int annotationArray(int n, final int n2) throws Exception {
            while (0 < n2) {
                n = this.subWalker.targetInfo(n + 1, this.info[n] & 0xFF);
                n = this.subWalker.typePath(n);
                n = this.annotation(n);
                int n3 = 0;
                ++n3;
            }
            return n;
        }
    }
}
