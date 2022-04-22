package org.lwjgl.util.mapped;

import java.lang.reflect.*;
import java.util.*;
import java.nio.*;
import org.lwjgl.*;
import java.io.*;
import org.objectweb.asm.util.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

public class MappedObjectTransformer
{
    static final boolean PRINT_ACTIVITY;
    static final boolean PRINT_TIMING;
    static final boolean PRINT_BYTECODE;
    static final Map className_to_subtype;
    static final String MAPPED_OBJECT_JVM;
    static final String MAPPED_HELPER_JVM;
    static final String MAPPEDSET_PREFIX;
    static final String MAPPED_SET2_JVM;
    static final String MAPPED_SET3_JVM;
    static final String MAPPED_SET4_JVM;
    static final String CACHE_LINE_PAD_JVM;
    static final String VIEWADDRESS_METHOD_NAME = "getViewAddress";
    static final String NEXT_METHOD_NAME = "next";
    static final String ALIGN_METHOD_NAME = "getAlign";
    static final String SIZEOF_METHOD_NAME = "getSizeof";
    static final String CAPACITY_METHOD_NAME = "capacity";
    static final String VIEW_CONSTRUCTOR_NAME = "constructView$LWJGL";
    static final Map OPCODE_TO_NAME;
    static final Map INSNTYPE_TO_NAME;
    static boolean is_currently_computing_frames;
    static final boolean $assertionsDisabled;
    
    public static void register(final Class clazz) {
        if (MappedObjectClassLoader.FORKED) {
            return;
        }
        final MappedType mappedType = clazz.getAnnotation(MappedType.class);
        if (mappedType != null && mappedType.padding() < 0) {
            throw new ClassFormatError("Invalid mapped type padding: " + mappedType.padding());
        }
        if (clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers())) {
            throw new InternalError("only top-level or static inner classes are allowed");
        }
        final String jvmClassName = jvmClassName(clazz);
        final HashMap<String, FieldInfo> hashMap = new HashMap<String, FieldInfo>();
        long max = 0L;
        final Field[] declaredFields = clazz.getDeclaredFields();
        final int length = declaredFields.length;
        while (1 < 0) {
            final Field field = declaredFields[1];
            final FieldInfo registerField = registerField(mappedType == null || mappedType.autoGenerateOffsets(), jvmClassName, max, field);
            if (registerField != null) {
                hashMap.put(field.getName(), registerField);
                max = Math.max(max, registerField.offset + registerField.lengthPadded);
            }
            int n = 0;
            ++n;
        }
        if (mappedType != null) {
            mappedType.align();
            if (mappedType.cacheLinePadding()) {
                if (mappedType.padding() != 0) {
                    throw new ClassFormatError("Mapped type padding cannot be specified together with cacheLinePadding.");
                }
                final int n2 = (int)(max % CacheUtil.getCacheLineSize());
                if (n2 != 0) {
                    final int n3 = CacheUtil.getCacheLineSize() - n2;
                }
            }
            else {
                mappedType.padding();
            }
        }
        final MappedSubtypeInfo mappedSubtypeInfo = new MappedSubtypeInfo(jvmClassName, hashMap, (int)(max + 0), 4, 0, true);
        if (MappedObjectTransformer.className_to_subtype.put(jvmClassName, mappedSubtypeInfo) != null) {
            throw new InternalError("duplicate mapped type: " + mappedSubtypeInfo.className);
        }
    }
    
    private static FieldInfo registerField(final boolean b, final String s, final long n, final Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            return null;
        }
        if (!field.getType().isPrimitive() && field.getType() != ByteBuffer.class) {
            throw new ClassFormatError("field '" + s + "." + field.getName() + "' not supported: " + field.getType());
        }
        final MappedField mappedField = field.getAnnotation(MappedField.class);
        if (mappedField == null && !b) {
            throw new ClassFormatError("field '" + s + "." + field.getName() + "' missing annotation " + MappedField.class.getName() + ": " + s);
        }
        final Pointer pointer = field.getAnnotation(Pointer.class);
        if (pointer != null && field.getType() != Long.TYPE) {
            throw new ClassFormatError("The @Pointer annotation can only be used on long fields. @Pointer field found: " + s + "." + field.getName() + ": " + field.getType());
        }
        if (Modifier.isVolatile(field.getModifiers()) && (pointer != null || field.getType() == ByteBuffer.class)) {
            throw new ClassFormatError("The volatile keyword is not supported for @Pointer or ByteBuffer fields. Volatile field found: " + s + "." + field.getName() + ": " + field.getType());
        }
        long byteLength;
        if (field.getType() == Long.TYPE || field.getType() == Double.TYPE) {
            if (pointer == null) {
                byteLength = 8L;
            }
            else {
                byteLength = MappedObjectUnsafe.INSTANCE.addressSize();
            }
        }
        else if (field.getType() == Double.TYPE) {
            byteLength = 8L;
        }
        else if (field.getType() == Integer.TYPE || field.getType() == Float.TYPE) {
            byteLength = 4L;
        }
        else if (field.getType() == Character.TYPE || field.getType() == Short.TYPE) {
            byteLength = 2L;
        }
        else if (field.getType() == Byte.TYPE) {
            byteLength = 1L;
        }
        else {
            if (field.getType() != ByteBuffer.class) {
                throw new ClassFormatError(field.getType().getName());
            }
            byteLength = mappedField.byteLength();
            if (byteLength < 0L) {
                throw new IllegalStateException("invalid byte length for mapped ByteBuffer field: " + s + "." + field.getName() + " [length=" + byteLength + "]");
            }
        }
        if (field.getType() != ByteBuffer.class && n % byteLength != 0L) {
            throw new IllegalStateException("misaligned mapped type: " + s + "." + field.getName());
        }
        final CacheLinePad cacheLinePad = field.getAnnotation(CacheLinePad.class);
        long byteOffset = n;
        if (mappedField != null && mappedField.byteOffset() != -1L) {
            if (mappedField.byteOffset() < 0L) {
                throw new ClassFormatError("Invalid field byte offset: " + s + "." + field.getName() + " [byteOffset=" + mappedField.byteOffset() + "]");
            }
            if (cacheLinePad != null) {
                throw new ClassFormatError("A field byte offset cannot be specified together with cache-line padding: " + s + "." + field.getName());
            }
            byteOffset = mappedField.byteOffset();
        }
        long n2 = byteLength;
        if (cacheLinePad != null) {
            if (cacheLinePad.before() && byteOffset % CacheUtil.getCacheLineSize() != 0L) {
                byteOffset += CacheUtil.getCacheLineSize() - (byteOffset & (long)(CacheUtil.getCacheLineSize() - 1));
            }
            if (cacheLinePad.after() && (byteOffset + byteLength) % CacheUtil.getCacheLineSize() != 0L) {
                n2 += CacheUtil.getCacheLineSize() - (byteOffset + byteLength) % CacheUtil.getCacheLineSize();
            }
            assert byteOffset % CacheUtil.getCacheLineSize() == 0L;
            assert (byteOffset + n2) % CacheUtil.getCacheLineSize() == 0L;
        }
        if (MappedObjectTransformer.PRINT_ACTIVITY) {
            LWJGLUtil.log(MappedObjectTransformer.class.getSimpleName() + ": " + s + "." + field.getName() + " [type=" + field.getType().getSimpleName() + ", offset=" + byteOffset + "]");
        }
        return new FieldInfo(byteOffset, byteLength, n2, Type.getType((Class)field.getType()), Modifier.isVolatile(field.getModifiers()), pointer != null);
    }
    
    static byte[] transformMappedObject(final byte[] array) {
        final ClassWriter classWriter = new ClassWriter(0);
        new ClassReader(array).accept((ClassVisitor)new ClassAdapter((ClassVisitor)classWriter) {
            private final String[] DEFINALIZE_LIST = { "getViewAddress", "next", "getAlign", "getSizeof", "capacity" };
            
            public MethodVisitor visitMethod(int n, final String s, final String s2, final String s3, final String[] array) {
                final String[] definalize_LIST = this.DEFINALIZE_LIST;
                while (0 < definalize_LIST.length) {
                    if (s.equals(definalize_LIST[0])) {
                        n &= 0xFFFFFFEF;
                        break;
                    }
                    int n2 = 0;
                    ++n2;
                }
                return super.visitMethod(n, s, s2, s3, array);
            }
        }, 0);
        return classWriter.toByteArray();
    }
    
    static byte[] transformMappedAPI(final String s, byte[] byteArray) {
        final ClassWriter classWriter = new ClassWriter(2) {
            protected String getCommonSuperClass(final String s, final String s2) {
                if ((MappedObjectTransformer.is_currently_computing_frames && !s.startsWith("java/")) || !s2.startsWith("java/")) {
                    return "java/lang/Object";
                }
                return super.getCommonSuperClass(s, s2);
            }
        };
        ClassAdapter methodGenAdapter;
        final TransformationAdapter transformationAdapter = (TransformationAdapter)(methodGenAdapter = new TransformationAdapter((ClassVisitor)classWriter, s));
        if (MappedObjectTransformer.className_to_subtype.containsKey(s)) {
            methodGenAdapter = getMethodGenAdapter(s, (ClassVisitor)methodGenAdapter);
        }
        new ClassReader(byteArray).accept((ClassVisitor)methodGenAdapter, 4);
        if (!transformationAdapter.transformed) {
            return byteArray;
        }
        byteArray = classWriter.toByteArray();
        if (MappedObjectTransformer.PRINT_BYTECODE) {
            printBytecode(byteArray);
        }
        return byteArray;
    }
    
    private static ClassAdapter getMethodGenAdapter(final String s, final ClassVisitor classVisitor) {
        return new ClassAdapter(classVisitor, s) {
            final String val$className;
            
            public void visitEnd() {
                final MappedSubtypeInfo mappedSubtypeInfo = MappedObjectTransformer.className_to_subtype.get(this.val$className);
                this.generateViewAddressGetter();
                this.generateCapacity();
                this.generateAlignGetter(mappedSubtypeInfo);
                this.generateSizeofGetter();
                this.generateNext();
                for (final String s : mappedSubtypeInfo.fields.keySet()) {
                    final FieldInfo fieldInfo = mappedSubtypeInfo.fields.get(s);
                    if (fieldInfo.type.getDescriptor().length() > 1) {
                        this.generateByteBufferGetter(s, fieldInfo);
                    }
                    else {
                        this.generateFieldGetter(s, fieldInfo);
                        this.generateFieldSetter(s, fieldInfo);
                    }
                }
                super.visitEnd();
            }
            
            private void generateViewAddressGetter() {
                final MethodVisitor visitMethod = super.visitMethod(1, "getViewAddress", "(I)J", (String)null, (String[])null);
                visitMethod.visitCode();
                visitMethod.visitVarInsn(25, 0);
                visitMethod.visitFieldInsn(180, MappedObjectTransformer.MAPPED_OBJECT_JVM, "baseAddress", "J");
                visitMethod.visitVarInsn(21, 1);
                visitMethod.visitFieldInsn(178, this.val$className, "SIZEOF", "I");
                visitMethod.visitInsn(104);
                visitMethod.visitInsn(133);
                visitMethod.visitInsn(97);
                if (MappedObject.CHECKS) {
                    visitMethod.visitInsn(92);
                    visitMethod.visitVarInsn(25, 0);
                    visitMethod.visitMethodInsn(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "checkAddress", "(JL" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";)V");
                }
                visitMethod.visitInsn(173);
                visitMethod.visitMaxs(3, 2);
                visitMethod.visitEnd();
            }
            
            private void generateCapacity() {
                final MethodVisitor visitMethod = super.visitMethod(1, "capacity", "()I", (String)null, (String[])null);
                visitMethod.visitCode();
                visitMethod.visitVarInsn(25, 0);
                visitMethod.visitMethodInsn(182, MappedObjectTransformer.MAPPED_OBJECT_JVM, "backingByteBuffer", "()L" + MappedObjectTransformer.jvmClassName(ByteBuffer.class) + ";");
                visitMethod.visitInsn(89);
                visitMethod.visitMethodInsn(182, MappedObjectTransformer.jvmClassName(ByteBuffer.class), "capacity", "()I");
                visitMethod.visitInsn(95);
                visitMethod.visitMethodInsn(184, MappedObjectTransformer.jvmClassName(MemoryUtil.class), "getAddress0", "(L" + MappedObjectTransformer.jvmClassName(Buffer.class) + ";)J");
                visitMethod.visitVarInsn(25, 0);
                visitMethod.visitFieldInsn(180, MappedObjectTransformer.MAPPED_OBJECT_JVM, "baseAddress", "J");
                visitMethod.visitInsn(101);
                visitMethod.visitInsn(136);
                visitMethod.visitInsn(96);
                visitMethod.visitFieldInsn(178, this.val$className, "SIZEOF", "I");
                visitMethod.visitInsn(108);
                visitMethod.visitInsn(172);
                visitMethod.visitMaxs(3, 1);
                visitMethod.visitEnd();
            }
            
            private void generateAlignGetter(final MappedSubtypeInfo mappedSubtypeInfo) {
                final MethodVisitor visitMethod = super.visitMethod(1, "getAlign", "()I", (String)null, (String[])null);
                visitMethod.visitCode();
                MappedObjectTransformer.visitIntNode(visitMethod, mappedSubtypeInfo.sizeof);
                visitMethod.visitInsn(172);
                visitMethod.visitMaxs(1, 1);
                visitMethod.visitEnd();
            }
            
            private void generateSizeofGetter() {
                final MethodVisitor visitMethod = super.visitMethod(1, "getSizeof", "()I", (String)null, (String[])null);
                visitMethod.visitCode();
                visitMethod.visitFieldInsn(178, this.val$className, "SIZEOF", "I");
                visitMethod.visitInsn(172);
                visitMethod.visitMaxs(1, 1);
                visitMethod.visitEnd();
            }
            
            private void generateNext() {
                final MethodVisitor visitMethod = super.visitMethod(1, "next", "()V", (String)null, (String[])null);
                visitMethod.visitCode();
                visitMethod.visitVarInsn(25, 0);
                visitMethod.visitInsn(89);
                visitMethod.visitFieldInsn(180, MappedObjectTransformer.MAPPED_OBJECT_JVM, "viewAddress", "J");
                visitMethod.visitFieldInsn(178, this.val$className, "SIZEOF", "I");
                visitMethod.visitInsn(133);
                visitMethod.visitInsn(97);
                visitMethod.visitMethodInsn(182, this.val$className, "setViewAddress", "(J)V");
                visitMethod.visitInsn(177);
                visitMethod.visitMaxs(3, 1);
                visitMethod.visitEnd();
            }
            
            private void generateByteBufferGetter(final String s, final FieldInfo fieldInfo) {
                final MethodVisitor visitMethod = super.visitMethod(9, MappedObjectTransformer.getterName(s), "(L" + this.val$className + ";I)" + fieldInfo.type.getDescriptor(), (String)null, (String[])null);
                visitMethod.visitCode();
                visitMethod.visitVarInsn(25, 0);
                visitMethod.visitVarInsn(21, 1);
                visitMethod.visitMethodInsn(182, this.val$className, "getViewAddress", "(I)J");
                MappedObjectTransformer.visitIntNode(visitMethod, (int)fieldInfo.offset);
                visitMethod.visitInsn(133);
                visitMethod.visitInsn(97);
                MappedObjectTransformer.visitIntNode(visitMethod, (int)fieldInfo.length);
                visitMethod.visitMethodInsn(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "newBuffer", "(JI)L" + MappedObjectTransformer.jvmClassName(ByteBuffer.class) + ";");
                visitMethod.visitInsn(176);
                visitMethod.visitMaxs(3, 2);
                visitMethod.visitEnd();
            }
            
            private void generateFieldGetter(final String s, final FieldInfo fieldInfo) {
                final MethodVisitor visitMethod = super.visitMethod(9, MappedObjectTransformer.getterName(s), "(L" + this.val$className + ";I)" + fieldInfo.type.getDescriptor(), (String)null, (String[])null);
                visitMethod.visitCode();
                visitMethod.visitVarInsn(25, 0);
                visitMethod.visitVarInsn(21, 1);
                visitMethod.visitMethodInsn(182, this.val$className, "getViewAddress", "(I)J");
                MappedObjectTransformer.visitIntNode(visitMethod, (int)fieldInfo.offset);
                visitMethod.visitInsn(133);
                visitMethod.visitInsn(97);
                visitMethod.visitMethodInsn(184, MappedObjectTransformer.MAPPED_HELPER_JVM, fieldInfo.getAccessType() + "get", "(J)" + fieldInfo.type.getDescriptor());
                visitMethod.visitInsn(fieldInfo.type.getOpcode(172));
                visitMethod.visitMaxs(3, 2);
                visitMethod.visitEnd();
            }
            
            private void generateFieldSetter(final String s, final FieldInfo fieldInfo) {
                final MethodVisitor visitMethod = super.visitMethod(9, MappedObjectTransformer.setterName(s), "(L" + this.val$className + ";I" + fieldInfo.type.getDescriptor() + ")V", (String)null, (String[])null);
                visitMethod.visitCode();
                switch (fieldInfo.type.getSort()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5: {}
                    case 6: {}
                }
                visitMethod.visitVarInsn(24, 2);
                visitMethod.visitVarInsn(25, 0);
                visitMethod.visitVarInsn(21, 1);
                visitMethod.visitMethodInsn(182, this.val$className, "getViewAddress", "(I)J");
                MappedObjectTransformer.visitIntNode(visitMethod, (int)fieldInfo.offset);
                visitMethod.visitInsn(133);
                visitMethod.visitInsn(97);
                visitMethod.visitMethodInsn(184, MappedObjectTransformer.MAPPED_HELPER_JVM, fieldInfo.getAccessType() + "put", "(" + fieldInfo.type.getDescriptor() + "J)V");
                visitMethod.visitInsn(177);
                visitMethod.visitMaxs(4, 4);
                visitMethod.visitEnd();
            }
        };
    }
    
    static int transformMethodCall(final InsnList list, int n, final Map map, final MethodInsnNode methodInsnNode, final MappedSubtypeInfo mappedSubtypeInfo, final Map map2) {
        switch (methodInsnNode.getOpcode()) {
            case 182: {
                if ("asArray".equals(methodInsnNode.name) && methodInsnNode.desc.equals("()[L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";")) {
                    final AbstractInsnNode next;
                    checkInsnAfterIsArray(next = methodInsnNode.getNext(), 192);
                    final AbstractInsnNode next2;
                    checkInsnAfterIsArray(next2 = next.getNext(), 58);
                    final Frame frame = map.get(next2);
                    final String internalName = ((BasicValue)frame.getStack(frame.getStackSize() - 1)).getType().getElementType().getInternalName();
                    if (!methodInsnNode.owner.equals(internalName)) {
                        throw new ClassCastException("Source: " + methodInsnNode.owner + " - Target: " + internalName);
                    }
                    map2.put(((VarInsnNode)next2).var, mappedSubtypeInfo);
                    list.remove(methodInsnNode.getNext());
                    list.remove((AbstractInsnNode)methodInsnNode);
                }
                if ("dup".equals(methodInsnNode.name) && methodInsnNode.desc.equals("()L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";")) {
                    n = replace(list, n, (AbstractInsnNode)methodInsnNode, generateDupInstructions(methodInsnNode));
                    break;
                }
                if ("slice".equals(methodInsnNode.name) && methodInsnNode.desc.equals("()L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";")) {
                    n = replace(list, n, (AbstractInsnNode)methodInsnNode, generateSliceInstructions(methodInsnNode));
                    break;
                }
                if ("runViewConstructor".equals(methodInsnNode.name) && "()V".equals(methodInsnNode.desc)) {
                    n = replace(list, n, (AbstractInsnNode)methodInsnNode, generateRunViewConstructorInstructions(methodInsnNode));
                    break;
                }
                if ("copyTo".equals(methodInsnNode.name) && methodInsnNode.desc.equals("(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";)V")) {
                    n = replace(list, n, (AbstractInsnNode)methodInsnNode, generateCopyToInstructions(mappedSubtypeInfo));
                    break;
                }
                if ("copyRange".equals(methodInsnNode.name) && methodInsnNode.desc.equals("(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";I)V")) {
                    n = replace(list, n, (AbstractInsnNode)methodInsnNode, generateCopyRangeInstructions(mappedSubtypeInfo));
                    break;
                }
                break;
            }
            case 183: {
                if (methodInsnNode.owner.equals(MappedObjectTransformer.MAPPED_OBJECT_JVM) && "<init>".equals(methodInsnNode.name) && "()V".equals(methodInsnNode.desc)) {
                    list.remove(methodInsnNode.getPrevious());
                    list.remove((AbstractInsnNode)methodInsnNode);
                    n -= 2;
                    break;
                }
                break;
            }
            case 184: {
                final boolean b = "map".equals(methodInsnNode.name) && methodInsnNode.desc.equals("(JI)L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";");
                final boolean b2 = "map".equals(methodInsnNode.name) && methodInsnNode.desc.equals("(Ljava/nio/ByteBuffer;)L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";");
                final boolean b3 = "malloc".equals(methodInsnNode.name) && methodInsnNode.desc.equals("(I)L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";");
                if (b || b2 || b3) {
                    n = replace(list, n, (AbstractInsnNode)methodInsnNode, generateMapInstructions(mappedSubtypeInfo, methodInsnNode.owner, b, b3));
                    break;
                }
                break;
            }
        }
        return n;
    }
    
    private static InsnList generateCopyRangeInstructions(final MappedSubtypeInfo mappedSubtypeInfo) {
        final InsnList list = new InsnList();
        list.add(getIntNode(mappedSubtypeInfo.sizeof));
        list.add((AbstractInsnNode)new InsnNode(104));
        list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "copy", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";I)V"));
        return list;
    }
    
    private static InsnList generateCopyToInstructions(final MappedSubtypeInfo mappedSubtypeInfo) {
        final InsnList list = new InsnList();
        list.add(getIntNode(mappedSubtypeInfo.sizeof - mappedSubtypeInfo.padding));
        list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "copy", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";I)V"));
        return list;
    }
    
    private static InsnList generateRunViewConstructorInstructions(final MethodInsnNode methodInsnNode) {
        final InsnList list = new InsnList();
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(182, methodInsnNode.owner, "constructView$LWJGL", "()V"));
        return list;
    }
    
    private static InsnList generateSliceInstructions(final MethodInsnNode methodInsnNode) {
        final InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, methodInsnNode.owner));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, methodInsnNode.owner, "<init>", "()V"));
        list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "slice", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";)L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";"));
        return list;
    }
    
    private static InsnList generateDupInstructions(final MethodInsnNode methodInsnNode) {
        final InsnList list = new InsnList();
        list.add((AbstractInsnNode)new TypeInsnNode(187, methodInsnNode.owner));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, methodInsnNode.owner, "<init>", "()V"));
        list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "dup", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";)L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";"));
        return list;
    }
    
    private static InsnList generateMapInstructions(final MappedSubtypeInfo mappedSubtypeInfo, final String s, final boolean b, final boolean b2) {
        final InsnList list = new InsnList();
        if (b2) {
            list.add(getIntNode(mappedSubtypeInfo.sizeof));
            list.add((AbstractInsnNode)new InsnNode(104));
            list.add((AbstractInsnNode)new MethodInsnNode(184, mappedSubtypeInfo.cacheLinePadded ? jvmClassName(CacheUtil.class) : jvmClassName(BufferUtils.class), "createByteBuffer", "(I)L" + jvmClassName(ByteBuffer.class) + ";"));
        }
        else if (b) {
            list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "newBuffer", "(JI)L" + jvmClassName(ByteBuffer.class) + ";"));
        }
        list.add((AbstractInsnNode)new TypeInsnNode(187, s));
        list.add((AbstractInsnNode)new InsnNode(89));
        list.add((AbstractInsnNode)new MethodInsnNode(183, s, "<init>", "()V"));
        list.add((AbstractInsnNode)new InsnNode(90));
        list.add((AbstractInsnNode)new InsnNode(95));
        list.add(getIntNode(mappedSubtypeInfo.align));
        list.add(getIntNode(mappedSubtypeInfo.sizeof));
        list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "setup", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";Ljava/nio/ByteBuffer;II)V"));
        return list;
    }
    
    static InsnList transformFieldAccess(final FieldInsnNode fieldInsnNode) {
        final MappedSubtypeInfo mappedSubtypeInfo = MappedObjectTransformer.className_to_subtype.get(fieldInsnNode.owner);
        if (mappedSubtypeInfo == null) {
            if ("view".equals(fieldInsnNode.name) && fieldInsnNode.owner.startsWith(MappedObjectTransformer.MAPPEDSET_PREFIX)) {
                return generateSetViewInstructions(fieldInsnNode);
            }
            return null;
        }
        else {
            if ("SIZEOF".equals(fieldInsnNode.name)) {
                return generateSIZEOFInstructions(fieldInsnNode, mappedSubtypeInfo);
            }
            if ("view".equals(fieldInsnNode.name)) {
                return generateViewInstructions(fieldInsnNode, mappedSubtypeInfo);
            }
            if ("baseAddress".equals(fieldInsnNode.name) || "viewAddress".equals(fieldInsnNode.name)) {
                return generateAddressInstructions(fieldInsnNode);
            }
            final FieldInfo fieldInfo = mappedSubtypeInfo.fields.get(fieldInsnNode.name);
            if (fieldInfo == null) {
                return null;
            }
            if (fieldInsnNode.desc.equals("L" + jvmClassName(ByteBuffer.class) + ";")) {
                return generateByteBufferInstructions(fieldInsnNode, mappedSubtypeInfo, fieldInfo.offset);
            }
            return generateFieldInstructions(fieldInsnNode, fieldInfo);
        }
    }
    
    private static InsnList generateSetViewInstructions(final FieldInsnNode fieldInsnNode) {
        if (fieldInsnNode.getOpcode() == 180) {
            throwAccessErrorOnReadOnlyField(fieldInsnNode.owner, fieldInsnNode.name);
        }
        if (fieldInsnNode.getOpcode() != 181) {
            throw new InternalError();
        }
        final InsnList list = new InsnList();
        if (MappedObjectTransformer.MAPPED_SET2_JVM.equals(fieldInsnNode.owner)) {
            list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "put_views", "(L" + MappedObjectTransformer.MAPPED_SET2_JVM + ";I)V"));
        }
        else if (MappedObjectTransformer.MAPPED_SET3_JVM.equals(fieldInsnNode.owner)) {
            list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "put_views", "(L" + MappedObjectTransformer.MAPPED_SET3_JVM + ";I)V"));
        }
        else {
            if (!MappedObjectTransformer.MAPPED_SET4_JVM.equals(fieldInsnNode.owner)) {
                throw new InternalError();
            }
            list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "put_views", "(L" + MappedObjectTransformer.MAPPED_SET4_JVM + ";I)V"));
        }
        return list;
    }
    
    private static InsnList generateSIZEOFInstructions(final FieldInsnNode fieldInsnNode, final MappedSubtypeInfo mappedSubtypeInfo) {
        if (!"I".equals(fieldInsnNode.desc)) {
            throw new InternalError();
        }
        final InsnList list = new InsnList();
        if (fieldInsnNode.getOpcode() == 178) {
            list.add(getIntNode(mappedSubtypeInfo.sizeof));
            return list;
        }
        if (fieldInsnNode.getOpcode() == 179) {
            throwAccessErrorOnReadOnlyField(fieldInsnNode.owner, fieldInsnNode.name);
        }
        throw new InternalError();
    }
    
    private static InsnList generateViewInstructions(final FieldInsnNode fieldInsnNode, final MappedSubtypeInfo mappedSubtypeInfo) {
        if (!"I".equals(fieldInsnNode.desc)) {
            throw new InternalError();
        }
        final InsnList list = new InsnList();
        if (fieldInsnNode.getOpcode() == 180) {
            if (mappedSubtypeInfo.sizeof_shift != 0) {
                list.add(getIntNode(mappedSubtypeInfo.sizeof_shift));
                list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "get_view_shift", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";I)I"));
            }
            else {
                list.add(getIntNode(mappedSubtypeInfo.sizeof));
                list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "get_view", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";I)I"));
            }
            return list;
        }
        if (fieldInsnNode.getOpcode() == 181) {
            if (mappedSubtypeInfo.sizeof_shift != 0) {
                list.add(getIntNode(mappedSubtypeInfo.sizeof_shift));
                list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "put_view_shift", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";II)V"));
            }
            else {
                list.add(getIntNode(mappedSubtypeInfo.sizeof));
                list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "put_view", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";II)V"));
            }
            return list;
        }
        throw new InternalError();
    }
    
    private static InsnList generateAddressInstructions(final FieldInsnNode fieldInsnNode) {
        if (!"J".equals(fieldInsnNode.desc)) {
            throw new IllegalStateException();
        }
        if (fieldInsnNode.getOpcode() == 180) {
            return null;
        }
        if (fieldInsnNode.getOpcode() == 181) {
            throwAccessErrorOnReadOnlyField(fieldInsnNode.owner, fieldInsnNode.name);
        }
        throw new InternalError();
    }
    
    private static InsnList generateByteBufferInstructions(final FieldInsnNode fieldInsnNode, final MappedSubtypeInfo mappedSubtypeInfo, final long n) {
        if (fieldInsnNode.getOpcode() == 181) {
            throwAccessErrorOnReadOnlyField(fieldInsnNode.owner, fieldInsnNode.name);
        }
        if (fieldInsnNode.getOpcode() == 180) {
            final InsnList list = new InsnList();
            list.add((AbstractInsnNode)new FieldInsnNode(180, mappedSubtypeInfo.className, "viewAddress", "J"));
            list.add((AbstractInsnNode)new LdcInsnNode((Object)n));
            list.add((AbstractInsnNode)new InsnNode(97));
            list.add((AbstractInsnNode)new LdcInsnNode((Object)mappedSubtypeInfo.fields.get(fieldInsnNode.name).length));
            list.add((AbstractInsnNode)new InsnNode(136));
            list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "newBuffer", "(JI)L" + jvmClassName(ByteBuffer.class) + ";"));
            return list;
        }
        throw new InternalError();
    }
    
    private static InsnList generateFieldInstructions(final FieldInsnNode fieldInsnNode, final FieldInfo fieldInfo) {
        final InsnList list = new InsnList();
        if (fieldInsnNode.getOpcode() == 181) {
            list.add(getIntNode((int)fieldInfo.offset));
            list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, fieldInfo.getAccessType() + "put", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";" + fieldInsnNode.desc + "I)V"));
            return list;
        }
        if (fieldInsnNode.getOpcode() == 180) {
            list.add(getIntNode((int)fieldInfo.offset));
            list.add((AbstractInsnNode)new MethodInsnNode(184, MappedObjectTransformer.MAPPED_HELPER_JVM, fieldInfo.getAccessType() + "get", "(L" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";I)" + fieldInsnNode.desc));
            return list;
        }
        throw new InternalError();
    }
    
    static int transformArrayAccess(final InsnList list, final int n, final Map map, final VarInsnNode varInsnNode, final MappedSubtypeInfo mappedSubtypeInfo, final int var) {
        final int n2 = map.get(varInsnNode).getStackSize() + 1;
        Object o = varInsnNode;
        while (true) {
            o = ((AbstractInsnNode)o).getNext();
            if (o == null) {
                throw new InternalError();
            }
            final Frame frame = map.get(o);
            if (frame == null) {
                continue;
            }
            final int stackSize = frame.getStackSize();
            if (stackSize == n2 + 1 && ((AbstractInsnNode)o).getOpcode() == 50) {
                final AbstractInsnNode abstractInsnNode = (AbstractInsnNode)o;
                while (true) {
                    o = ((AbstractInsnNode)o).getNext();
                    if (o == null) {
                        break;
                    }
                    final Frame frame2 = map.get(o);
                    if (frame2 == null) {
                        continue;
                    }
                    final int stackSize2 = frame2.getStackSize();
                    if (stackSize2 == n2 + 1 && ((AbstractInsnNode)o).getOpcode() == 181) {
                        final FieldInsnNode fieldInsnNode = (FieldInsnNode)o;
                        list.insert((AbstractInsnNode)o, (AbstractInsnNode)new MethodInsnNode(184, mappedSubtypeInfo.className, setterName(fieldInsnNode.name), "(L" + mappedSubtypeInfo.className + ";I" + fieldInsnNode.desc + ")V"));
                        list.remove((AbstractInsnNode)o);
                        break;
                    }
                    if (stackSize2 == n2 && ((AbstractInsnNode)o).getOpcode() == 180) {
                        final FieldInsnNode fieldInsnNode2 = (FieldInsnNode)o;
                        list.insert((AbstractInsnNode)o, (AbstractInsnNode)new MethodInsnNode(184, mappedSubtypeInfo.className, getterName(fieldInsnNode2.name), "(L" + mappedSubtypeInfo.className + ";I)" + fieldInsnNode2.desc));
                        list.remove((AbstractInsnNode)o);
                        break;
                    }
                    if (stackSize2 == n2 && ((AbstractInsnNode)o).getOpcode() == 89 && ((AbstractInsnNode)o).getNext().getOpcode() == 180) {
                        final FieldInsnNode fieldInsnNode3 = (FieldInsnNode)((AbstractInsnNode)o).getNext();
                        final MethodInsnNode methodInsnNode = new MethodInsnNode(184, mappedSubtypeInfo.className, getterName(fieldInsnNode3.name), "(L" + mappedSubtypeInfo.className + ";I)" + fieldInsnNode3.desc);
                        list.insert((AbstractInsnNode)o, (AbstractInsnNode)new InsnNode(92));
                        list.insert(((AbstractInsnNode)o).getNext(), (AbstractInsnNode)methodInsnNode);
                        list.remove((AbstractInsnNode)o);
                        list.remove((AbstractInsnNode)fieldInsnNode3);
                        o = methodInsnNode;
                    }
                    else {
                        if (stackSize2 < n2) {
                            throw new ClassFormatError("Invalid " + mappedSubtypeInfo.className + " view array usage detected: " + getOpcodeName((AbstractInsnNode)o));
                        }
                        continue;
                    }
                }
                list.remove(abstractInsnNode);
                return n;
            }
            if (stackSize == n2 && ((AbstractInsnNode)o).getOpcode() == 190) {
                if (LWJGLUtil.DEBUG && varInsnNode.getNext() != o) {
                    throw new InternalError();
                }
                list.remove((AbstractInsnNode)o);
                varInsnNode.var = var;
                list.insert((AbstractInsnNode)varInsnNode, (AbstractInsnNode)new MethodInsnNode(182, mappedSubtypeInfo.className, "capacity", "()I"));
                return n + 1;
            }
            else {
                if (stackSize < n2) {
                    throw new ClassFormatError("Invalid " + mappedSubtypeInfo.className + " view array usage detected: " + getOpcodeName((AbstractInsnNode)o));
                }
                continue;
            }
        }
    }
    
    private static void getClassEnums(final Class clazz, final Map map, final String... array) {
        final Field[] fields = clazz.getFields();
        while (0 < fields.length) {
            final Field field = fields[0];
            Label_0118: {
                if (Modifier.isStatic(field.getModifiers())) {
                    if (field.getType() == Integer.TYPE) {
                        while (0 < array.length) {
                            if (field.getName().startsWith(array[0])) {
                                break Label_0118;
                            }
                            int n = 0;
                            ++n;
                        }
                        if (map.put(field.get(null), field.getName()) != null) {
                            throw new IllegalStateException();
                        }
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    static String getOpcodeName(final AbstractInsnNode abstractInsnNode) {
        return MappedObjectTransformer.INSNTYPE_TO_NAME.get(abstractInsnNode.getType()) + ": " + abstractInsnNode.getOpcode() + ((MappedObjectTransformer.OPCODE_TO_NAME.get(abstractInsnNode.getOpcode()) == null) ? "" : (" [" + MappedObjectTransformer.OPCODE_TO_NAME.get(abstractInsnNode.getOpcode()) + "]"));
    }
    
    static String jvmClassName(final Class clazz) {
        return clazz.getName().replace('.', '/');
    }
    
    static String getterName(final String s) {
        return "get$" + Character.toUpperCase(s.charAt(0)) + s.substring(1) + "$LWJGL";
    }
    
    static String setterName(final String s) {
        return "set$" + Character.toUpperCase(s.charAt(0)) + s.substring(1) + "$LWJGL";
    }
    
    private static void checkInsnAfterIsArray(final AbstractInsnNode abstractInsnNode, final int n) {
        if (abstractInsnNode == null) {
            throw new ClassFormatError("Unexpected end of instructions after .asArray() method.");
        }
        if (abstractInsnNode.getOpcode() != n) {
            throw new ClassFormatError("The result of .asArray() must be stored to a local variable. Found: " + getOpcodeName(abstractInsnNode));
        }
    }
    
    static AbstractInsnNode getIntNode(final int n) {
        if (n <= 5 && -1 <= n) {
            return (AbstractInsnNode)new InsnNode(2 + n + 1);
        }
        if (n >= -128 && n <= 127) {
            return (AbstractInsnNode)new IntInsnNode(16, n);
        }
        if (n >= -32768 && n <= 32767) {
            return (AbstractInsnNode)new IntInsnNode(17, n);
        }
        return (AbstractInsnNode)new LdcInsnNode((Object)n);
    }
    
    static void visitIntNode(final MethodVisitor methodVisitor, final int n) {
        if (n <= 5 && -1 <= n) {
            methodVisitor.visitInsn(2 + n + 1);
        }
        else if (n >= -128 && n <= 127) {
            methodVisitor.visitIntInsn(16, n);
        }
        else if (n >= -32768 && n <= 32767) {
            methodVisitor.visitIntInsn(17, n);
        }
        else {
            methodVisitor.visitLdcInsn((Object)n);
        }
    }
    
    static int replace(final InsnList list, final int n, final AbstractInsnNode abstractInsnNode, final InsnList list2) {
        final int size = list2.size();
        list.insert(abstractInsnNode, list2);
        list.remove(abstractInsnNode);
        return n + (size - 1);
    }
    
    private static void throwAccessErrorOnReadOnlyField(final String s, final String s2) {
        throw new IllegalAccessError("The " + s + "." + s2 + " field is final.");
    }
    
    private static void printBytecode(final byte[] array) {
        final StringWriter stringWriter = new StringWriter();
        new ClassReader(array).accept((ClassVisitor)new TraceClassVisitor((ClassVisitor)new ClassWriter(0), new PrintWriter(stringWriter)), 0);
        LWJGLUtil.log(stringWriter.toString());
    }
    
    static {
        $assertionsDisabled = !MappedObjectTransformer.class.desiredAssertionStatus();
        PRINT_ACTIVITY = (LWJGLUtil.DEBUG && LWJGLUtil.getPrivilegedBoolean("org.lwjgl.util.mapped.PrintActivity"));
        PRINT_TIMING = (MappedObjectTransformer.PRINT_ACTIVITY && LWJGLUtil.getPrivilegedBoolean("org.lwjgl.util.mapped.PrintTiming"));
        PRINT_BYTECODE = (LWJGLUtil.DEBUG && LWJGLUtil.getPrivilegedBoolean("org.lwjgl.util.mapped.PrintBytecode"));
        MAPPED_OBJECT_JVM = jvmClassName(MappedObject.class);
        MAPPED_HELPER_JVM = jvmClassName(MappedHelper.class);
        MAPPEDSET_PREFIX = jvmClassName(MappedSet.class);
        MAPPED_SET2_JVM = jvmClassName(MappedSet2.class);
        MAPPED_SET3_JVM = jvmClassName(MappedSet3.class);
        MAPPED_SET4_JVM = jvmClassName(MappedSet4.class);
        CACHE_LINE_PAD_JVM = "L" + jvmClassName(CacheLinePad.class) + ";";
        OPCODE_TO_NAME = new HashMap();
        INSNTYPE_TO_NAME = new HashMap();
        getClassEnums(Opcodes.class, MappedObjectTransformer.OPCODE_TO_NAME, "V1_", "ACC_", "T_", "F_", "MH_");
        getClassEnums(AbstractInsnNode.class, MappedObjectTransformer.INSNTYPE_TO_NAME, new String[0]);
        (className_to_subtype = new HashMap()).put(MappedObjectTransformer.MAPPED_OBJECT_JVM, new MappedSubtypeInfo(MappedObjectTransformer.MAPPED_OBJECT_JVM, null, -1, -1, -1, false));
        final String property = System.getProperty("java.vm.name");
        if (property != null && !property.contains("Server")) {
            System.err.println("Warning: " + MappedObject.class.getSimpleName() + "s have inferiour performance on Client VMs, please consider switching to a Server VM.");
        }
    }
    
    private static class MappedSubtypeInfo
    {
        final String className;
        final int sizeof;
        final int sizeof_shift;
        final int align;
        final int padding;
        final boolean cacheLinePadded;
        final Map fields;
        
        MappedSubtypeInfo(final String className, final Map fields, final int sizeof, final int align, final int padding, final boolean cacheLinePadded) {
            this.className = className;
            this.sizeof = sizeof;
            if ((sizeof - 1 & sizeof) == 0x0) {
                this.sizeof_shift = getPoT(sizeof);
            }
            else {
                this.sizeof_shift = 0;
            }
            this.align = align;
            this.padding = padding;
            this.cacheLinePadded = cacheLinePadded;
            this.fields = fields;
        }
        
        private static int getPoT(int i) {
            while (i > 0) {
                int n = 0;
                ++n;
                i >>= 1;
            }
            return -1;
        }
    }
    
    private static class FieldInfo
    {
        final long offset;
        final long length;
        final long lengthPadded;
        final Type type;
        final boolean isVolatile;
        final boolean isPointer;
        
        FieldInfo(final long offset, final long length, final long lengthPadded, final Type type, final boolean isVolatile, final boolean isPointer) {
            this.offset = offset;
            this.length = length;
            this.lengthPadded = lengthPadded;
            this.type = type;
            this.isVolatile = isVolatile;
            this.isPointer = isPointer;
        }
        
        String getAccessType() {
            return this.isPointer ? "a" : (this.type.getDescriptor().toLowerCase() + (this.isVolatile ? "v" : ""));
        }
    }
    
    private static class TransformationAdapter extends ClassAdapter
    {
        final String className;
        boolean transformed;
        
        TransformationAdapter(final ClassVisitor classVisitor, final String className) {
            super(classVisitor);
            this.className = className;
        }
        
        public FieldVisitor visitField(final int n, final String s, final String s2, final String s3, final Object o) {
            final MappedSubtypeInfo mappedSubtypeInfo = MappedObjectTransformer.className_to_subtype.get(this.className);
            if (mappedSubtypeInfo != null && mappedSubtypeInfo.fields.containsKey(s)) {
                if (MappedObjectTransformer.PRINT_ACTIVITY) {
                    LWJGLUtil.log(MappedObjectTransformer.class.getSimpleName() + ": discarding field: " + this.className + "." + s + ":" + s2);
                }
                return null;
            }
            if ((n & 0x8) == 0x0) {
                return (FieldVisitor)new FieldNode(n, s, s2, s3, o) {
                    final TransformationAdapter this$0;
                    
                    public void visitEnd() {
                        if (this.visibleAnnotations == null) {
                            this.accept(TransformationAdapter.access$000(this.this$0));
                            return;
                        }
                        for (final AnnotationNode annotationNode : this.visibleAnnotations) {
                            if (MappedObjectTransformer.CACHE_LINE_PAD_JVM.equals(annotationNode.desc)) {
                                if (!"J".equals(this.desc) && !"D".equals(this.desc)) {
                                    if (!"I".equals(this.desc) && !"F".equals(this.desc)) {
                                        if (!"S".equals(this.desc) && !"C".equals(this.desc)) {
                                            if (!"B".equals(this.desc) && !"Z".equals(this.desc)) {
                                                throw new ClassFormatError("The @CacheLinePad annotation cannot be used on non-primitive fields: " + this.this$0.className + "." + this.name);
                                            }
                                        }
                                    }
                                }
                                this.this$0.transformed = true;
                                if (annotationNode.values != null) {
                                    while (0 < annotationNode.values.size()) {
                                        annotationNode.values.get(1).equals(Boolean.TRUE);
                                        if ("before".equals(annotationNode.values.get(0))) {}
                                        final int n;
                                        n += 2;
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                        int n2 = 0;
                        if (false) {
                            n2 = CacheUtil.getCacheLineSize() / 1 - 1;
                            while (1 >= 1) {
                                TransformationAdapter.access$100(this.this$0).visitField(this.access | 0x1 | 0x1000, this.name + "$PAD_" + 1, this.desc, this.signature, (Object)null);
                                --n2;
                            }
                        }
                        this.accept(TransformationAdapter.access$200(this.this$0));
                        if (true) {
                            while (1 <= CacheUtil.getCacheLineSize() / 1 - 1) {
                                TransformationAdapter.access$300(this.this$0).visitField(this.access | 0x1 | 0x1000, this.name + "$PAD" + 1, this.desc, this.signature, (Object)null);
                                ++n2;
                            }
                        }
                    }
                };
            }
            return super.visitField(n, s, s2, s3, o);
        }
        
        public MethodVisitor visitMethod(final int n, String s, final String s2, final String s3, final String[] array) {
            if ("<init>".equals(s) && MappedObjectTransformer.className_to_subtype.get(this.className) != null) {
                if (!"()V".equals(s2)) {
                    throw new ClassFormatError(this.className + " can only have a default constructor, found: " + s2);
                }
                final MethodVisitor visitMethod = super.visitMethod(n, s, s2, s3, array);
                visitMethod.visitVarInsn(25, 0);
                visitMethod.visitMethodInsn(183, MappedObjectTransformer.MAPPED_OBJECT_JVM, "<init>", "()V");
                visitMethod.visitInsn(177);
                visitMethod.visitMaxs(0, 0);
                s = "constructView$LWJGL";
            }
            return (MethodVisitor)new MethodNode(n, s, s2, s3, array, super.visitMethod(n, s, s2, s3, array)) {
                boolean needsTransformation;
                final MethodVisitor val$mv;
                final TransformationAdapter this$0;
                
                public void visitMaxs(final int n, final int n2) {
                    MappedObjectTransformer.is_currently_computing_frames = true;
                    super.visitMaxs(n, n2);
                    MappedObjectTransformer.is_currently_computing_frames = false;
                }
                
                public void visitFieldInsn(final int n, final String s, final String s2, final String s3) {
                    if (MappedObjectTransformer.className_to_subtype.containsKey(s) || s.startsWith(MappedObjectTransformer.MAPPEDSET_PREFIX)) {
                        this.needsTransformation = true;
                    }
                    super.visitFieldInsn(n, s, s2, s3);
                }
                
                public void visitMethodInsn(final int n, final String s, final String s2, final String s3) {
                    if (MappedObjectTransformer.className_to_subtype.containsKey(s)) {
                        this.needsTransformation = true;
                    }
                    super.visitMethodInsn(n, s, s2, s3);
                }
                
                public void visitEnd() {
                    if (this.needsTransformation) {
                        this.this$0.transformed = true;
                        this.transformMethod(this.analyse());
                    }
                    this.accept(this.val$mv);
                }
                
                private Frame[] analyse() throws AnalyzerException {
                    final Analyzer analyzer = new Analyzer((Interpreter)new SimpleVerifier());
                    analyzer.analyze(this.this$0.className, (MethodNode)this);
                    return analyzer.getFrames();
                }
                
                private void transformMethod(final Frame[] p0) {
                    // 
                    // This method could not be decompiled.
                    // 
                    // Original Bytecode:
                    // 
                    //     1: getfield        org/lwjgl/util/mapped/MappedObjectTransformer$TransformationAdapter$2.instructions:Lorg/objectweb/asm/tree/InsnList;
                    //     4: astore_2       
                    //     5: new             Ljava/util/HashMap;
                    //     8: dup            
                    //     9: invokespecial   java/util/HashMap.<init>:()V
                    //    12: astore_3       
                    //    13: new             Ljava/util/HashMap;
                    //    16: dup            
                    //    17: invokespecial   java/util/HashMap.<init>:()V
                    //    20: astore          4
                    //    22: iconst_0       
                    //    23: aload_1        
                    //    24: arraylength    
                    //    25: if_icmpge       50
                    //    28: aload           4
                    //    30: aload_2        
                    //    31: iconst_0       
                    //    32: invokevirtual   org/objectweb/asm/tree/InsnList.get:(I)Lorg/objectweb/asm/tree/AbstractInsnNode;
                    //    35: aload_1        
                    //    36: iconst_0       
                    //    37: aaload         
                    //    38: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
                    //    43: pop            
                    //    44: iinc            5, 1
                    //    47: goto            22
                    //    50: iconst_0       
                    //    51: aload_2        
                    //    52: invokevirtual   org/objectweb/asm/tree/InsnList.size:()I
                    //    55: if_icmpge       245
                    //    58: aload_2        
                    //    59: iconst_0       
                    //    60: invokevirtual   org/objectweb/asm/tree/InsnList.get:(I)Lorg/objectweb/asm/tree/AbstractInsnNode;
                    //    63: astore          6
                    //    65: aload           6
                    //    67: invokevirtual   org/objectweb/asm/tree/AbstractInsnNode.getType:()I
                    //    70: tableswitch {
                    //                4: 100
                    //                5: 239
                    //                6: 162
                    //                7: 195
                    //          default: 239
                    //        }
                    //   100: aload           6
                    //   102: invokevirtual   org/objectweb/asm/tree/AbstractInsnNode.getOpcode:()I
                    //   105: bipush          25
                    //   107: if_icmpne       239
                    //   110: aload           6
                    //   112: checkcast       Lorg/objectweb/asm/tree/VarInsnNode;
                    //   115: astore          7
                    //   117: aload_3        
                    //   118: aload           7
                    //   120: getfield        org/objectweb/asm/tree/VarInsnNode.var:I
                    //   123: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                    //   126: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
                    //   131: checkcast       Lorg/lwjgl/util/mapped/MappedObjectTransformer$MappedSubtypeInfo;
                    //   134: astore          8
                    //   136: aload           8
                    //   138: ifnull          159
                    //   141: aload_2        
                    //   142: iconst_0       
                    //   143: aload           4
                    //   145: aload           7
                    //   147: aload           8
                    //   149: aload           7
                    //   151: getfield        org/objectweb/asm/tree/VarInsnNode.var:I
                    //   154: invokestatic    org/lwjgl/util/mapped/MappedObjectTransformer.transformArrayAccess:(Lorg/objectweb/asm/tree/InsnList;ILjava/util/Map;Lorg/objectweb/asm/tree/VarInsnNode;Lorg/lwjgl/util/mapped/MappedObjectTransformer$MappedSubtypeInfo;I)I
                    //   157: istore          5
                    //   159: goto            239
                    //   162: aload           6
                    //   164: checkcast       Lorg/objectweb/asm/tree/FieldInsnNode;
                    //   167: astore          7
                    //   169: aload           7
                    //   171: invokestatic    org/lwjgl/util/mapped/MappedObjectTransformer.transformFieldAccess:(Lorg/objectweb/asm/tree/FieldInsnNode;)Lorg/objectweb/asm/tree/InsnList;
                    //   174: astore          8
                    //   176: aload           8
                    //   178: ifnull          239
                    //   181: aload_2        
                    //   182: iconst_0       
                    //   183: aload           6
                    //   185: aload           8
                    //   187: invokestatic    org/lwjgl/util/mapped/MappedObjectTransformer.replace:(Lorg/objectweb/asm/tree/InsnList;ILorg/objectweb/asm/tree/AbstractInsnNode;Lorg/objectweb/asm/tree/InsnList;)I
                    //   190: istore          5
                    //   192: goto            239
                    //   195: aload           6
                    //   197: checkcast       Lorg/objectweb/asm/tree/MethodInsnNode;
                    //   200: astore          9
                    //   202: getstatic       org/lwjgl/util/mapped/MappedObjectTransformer.className_to_subtype:Ljava/util/Map;
                    //   205: aload           9
                    //   207: getfield        org/objectweb/asm/tree/MethodInsnNode.owner:Ljava/lang/String;
                    //   210: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
                    //   215: checkcast       Lorg/lwjgl/util/mapped/MappedObjectTransformer$MappedSubtypeInfo;
                    //   218: astore          10
                    //   220: aload           10
                    //   222: ifnull          239
                    //   225: aload_2        
                    //   226: iconst_0       
                    //   227: aload           4
                    //   229: aload           9
                    //   231: aload           10
                    //   233: aload_3        
                    //   234: invokestatic    org/lwjgl/util/mapped/MappedObjectTransformer.transformMethodCall:(Lorg/objectweb/asm/tree/InsnList;ILjava/util/Map;Lorg/objectweb/asm/tree/MethodInsnNode;Lorg/lwjgl/util/mapped/MappedObjectTransformer$MappedSubtypeInfo;Ljava/util/Map;)I
                    //   237: istore          5
                    //   239: iinc            5, 1
                    //   242: goto            50
                    //   245: return         
                    // 
                    // The error that occurred was:
                    // 
                    // java.lang.UnsupportedOperationException
                    //     at java.util.Collections$1.remove(Unknown Source)
                    //     at java.util.AbstractCollection.removeAll(Unknown Source)
                    //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2968)
                    //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
                    //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                    //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                    //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                    //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
                    //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
                    //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
                    //     at java.lang.Thread.run(Unknown Source)
                    // 
                    throw new IllegalStateException("An error occurred while decompiling this method.");
                }
            };
        }
        
        static ClassVisitor access$000(final TransformationAdapter transformationAdapter) {
            return transformationAdapter.cv;
        }
        
        static ClassVisitor access$100(final TransformationAdapter transformationAdapter) {
            return transformationAdapter.cv;
        }
        
        static ClassVisitor access$200(final TransformationAdapter transformationAdapter) {
            return transformationAdapter.cv;
        }
        
        static ClassVisitor access$300(final TransformationAdapter transformationAdapter) {
            return transformationAdapter.cv;
        }
    }
}
