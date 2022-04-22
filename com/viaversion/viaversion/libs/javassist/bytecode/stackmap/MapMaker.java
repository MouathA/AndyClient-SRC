package com.viaversion.viaversion.libs.javassist.bytecode.stackmap;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class MapMaker extends Tracer
{
    public static StackMapTable make(final ClassPool classPool, final MethodInfo methodInfo) throws BadBytecode {
        final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            return null;
        }
        final TypedBlock[] blocks = TypedBlock.makeBlocks(methodInfo, codeAttribute, true);
        if (blocks == null) {
            return null;
        }
        final MapMaker mapMaker = new MapMaker(classPool, methodInfo, codeAttribute);
        mapMaker.make(blocks, codeAttribute.getCode());
        return mapMaker.toStackMap(blocks);
    }
    
    public static StackMap make2(final ClassPool classPool, final MethodInfo methodInfo) throws BadBytecode {
        final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            return null;
        }
        final TypedBlock[] blocks = TypedBlock.makeBlocks(methodInfo, codeAttribute, true);
        if (blocks == null) {
            return null;
        }
        final MapMaker mapMaker = new MapMaker(classPool, methodInfo, codeAttribute);
        mapMaker.make(blocks, codeAttribute.getCode());
        return mapMaker.toStackMap2(methodInfo.getConstPool(), blocks);
    }
    
    public MapMaker(final ClassPool classPool, final MethodInfo methodInfo, final CodeAttribute codeAttribute) {
        super(classPool, methodInfo.getConstPool(), codeAttribute.getMaxStack(), codeAttribute.getMaxLocals(), TypedBlock.getRetType(methodInfo.getDescriptor()));
    }
    
    protected MapMaker(final MapMaker mapMaker) {
        super(mapMaker);
    }
    
    void make(final TypedBlock[] array, final byte[] array2) throws BadBytecode {
        this.make(array2, array[0]);
        this.findDeadCatchers(array2, array);
        this.fixTypes(array2, array);
    }
    
    private void make(final byte[] array, final TypedBlock typedBlock) throws BadBytecode {
        copyTypeData(typedBlock.stackTop, typedBlock.stackTypes, this.stackTypes);
        this.stackTop = typedBlock.stackTop;
        copyTypeData(typedBlock.localsTypes.length, typedBlock.localsTypes, this.localsTypes);
        this.traceException(array, typedBlock.toCatch);
        int i = typedBlock.position;
        while (i < i + typedBlock.length) {
            i += this.doOpcode(i, array);
            this.traceException(array, typedBlock.toCatch);
        }
        if (typedBlock.exit != null) {
            while (0 < typedBlock.exit.length) {
                final TypedBlock typedBlock2 = (TypedBlock)typedBlock.exit[0];
                if (typedBlock2.alreadySet()) {
                    this.mergeMap(typedBlock2, true);
                }
                else {
                    this.recordStackMap(typedBlock2);
                    new MapMaker(this).make(array, typedBlock2);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    private void traceException(final byte[] array, BasicBlock.Catch next) throws BadBytecode {
        while (next != null) {
            final TypedBlock typedBlock = (TypedBlock)next.body;
            if (typedBlock.alreadySet()) {
                this.mergeMap(typedBlock, false);
                if (typedBlock.stackTop < 1) {
                    throw new BadBytecode("bad catch clause: " + next.typeIndex);
                }
                typedBlock.stackTypes[0] = this.merge(this.toExceptionType(next.typeIndex), typedBlock.stackTypes[0]);
            }
            else {
                this.recordStackMap(typedBlock, next.typeIndex);
                new MapMaker(this).make(array, typedBlock);
            }
            next = next.next;
        }
    }
    
    private void mergeMap(final TypedBlock typedBlock, final boolean b) throws BadBytecode {
        final int length = this.localsTypes.length;
        int n = 0;
        while (0 < length) {
            typedBlock.localsTypes[0] = this.merge(validateTypeData(this.localsTypes, length, 0), typedBlock.localsTypes[0]);
            ++n;
        }
        if (b) {
            while (0 < this.stackTop) {
                typedBlock.stackTypes[0] = this.merge(this.stackTypes[0], typedBlock.stackTypes[0]);
                ++n;
            }
        }
    }
    
    private TypeData merge(final TypeData typeData, final TypeData typeData2) throws BadBytecode {
        if (typeData == typeData2) {
            return typeData2;
        }
        if (typeData2 instanceof TypeData.ClassName || typeData2 instanceof TypeData.BasicType) {
            return typeData2;
        }
        if (typeData2 instanceof TypeData.AbsTypeVar) {
            ((TypeData.AbsTypeVar)typeData2).merge(typeData);
            return typeData2;
        }
        throw new RuntimeException("fatal: this should never happen");
    }
    
    private void recordStackMap(final TypedBlock typedBlock) throws BadBytecode {
        final TypeData[] make = TypeData.make(this.stackTypes.length);
        final int stackTop = this.stackTop;
        recordTypeData(stackTop, this.stackTypes, make);
        this.recordStackMap0(typedBlock, stackTop, make);
    }
    
    private void recordStackMap(final TypedBlock typedBlock, final int n) throws BadBytecode {
        final TypeData[] make = TypeData.make(this.stackTypes.length);
        make[0] = this.toExceptionType(n).join();
        this.recordStackMap0(typedBlock, 1, make);
    }
    
    private TypeData.ClassName toExceptionType(final int n) {
        String classInfo;
        if (n == 0) {
            classInfo = "java.lang.Throwable";
        }
        else {
            classInfo = this.cpool.getClassInfo(n);
        }
        return new TypeData.ClassName(classInfo);
    }
    
    private void recordStackMap0(final TypedBlock typedBlock, final int n, final TypeData[] array) throws BadBytecode {
        final int length = this.localsTypes.length;
        final TypeData[] make = TypeData.make(length);
        typedBlock.setStackMap(n, array, recordTypeData(length, this.localsTypes, make), make);
    }
    
    protected static int recordTypeData(final int n, final TypeData[] array, final TypeData[] array2) {
        while (0 < n) {
            final TypeData validateTypeData = validateTypeData(array, n, 0);
            array2[0] = validateTypeData.join();
            if (validateTypeData != MapMaker.TOP) {}
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    protected static void copyTypeData(final int n, final TypeData[] array, final TypeData[] array2) {
        while (0 < n) {
            array2[0] = array[0];
            int n2 = 0;
            ++n2;
        }
    }
    
    private static TypeData validateTypeData(final TypeData[] array, final int n, final int n2) {
        final TypeData typeData = array[n2];
        if (typeData.is2WordType() && n2 + 1 < n && array[n2 + 1] != MapMaker.TOP) {
            return MapMaker.TOP;
        }
        return typeData;
    }
    
    private void findDeadCatchers(final byte[] array, final TypedBlock[] array2) throws BadBytecode {
        while (0 < array2.length) {
            final TypedBlock typedBlock = array2[0];
            if (!typedBlock.alreadySet()) {
                this.fixDeadcode(array, typedBlock);
                final BasicBlock.Catch toCatch = typedBlock.toCatch;
                if (toCatch != null) {
                    final TypedBlock typedBlock2 = (TypedBlock)toCatch.body;
                    if (!typedBlock2.alreadySet()) {
                        this.recordStackMap(typedBlock2, toCatch.typeIndex);
                        this.fixDeadcode(array, typedBlock2);
                        typedBlock2.incoming = 1;
                    }
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    private void fixDeadcode(final byte[] array, final TypedBlock typedBlock) throws BadBytecode {
        final int position = typedBlock.position;
        final int n = typedBlock.length - 3;
        if (n < 0) {
            if (n == -1) {
                array[position] = 0;
            }
            array[position + typedBlock.length - 1] = -65;
            typedBlock.incoming = 1;
            this.recordStackMap(typedBlock, 0);
            return;
        }
        typedBlock.incoming = 0;
        while (0 < n) {
            array[position + 0] = 0;
            int n2 = 0;
            ++n2;
        }
        array[position + n] = -89;
        ByteArray.write16bit(-n, array, position + n + 1);
    }
    
    private void fixTypes(final byte[] array, final TypedBlock[] array2) throws NotFoundException, BadBytecode {
        final ArrayList list = new ArrayList();
        while (0 < array2.length) {
            final TypedBlock typedBlock = array2[0];
            if (typedBlock.alreadySet()) {
                int n = 0;
                while (0 < typedBlock.localsTypes.length) {
                    typedBlock.localsTypes[0].dfs(list, 0, this.classPool);
                    ++n;
                }
                while (0 < typedBlock.stackTop) {
                    typedBlock.stackTypes[0].dfs(list, 0, this.classPool);
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public StackMapTable toStackMap(final TypedBlock[] array) {
        final StackMapTable.Writer writer = new StackMapTable.Writer(32);
        final int length = array.length;
        TypedBlock typedBlock = array[0];
        int length2 = typedBlock.length;
        if (typedBlock.incoming > 0) {
            writer.sameFrame(0);
            --length2;
        }
        while (1 < length) {
            final TypedBlock typedBlock2 = array[1];
            if (this.isTarget(typedBlock2, array[0])) {
                typedBlock2.resetNumLocals();
                this.toStackMapBody(writer, typedBlock2, stackMapDiff(typedBlock.numLocals, typedBlock.localsTypes, typedBlock2.numLocals, typedBlock2.localsTypes), length2, typedBlock);
                length2 = typedBlock2.length - 1;
                typedBlock = typedBlock2;
            }
            else if (typedBlock2.incoming == 0) {
                writer.sameFrame(length2);
                length2 = typedBlock2.length - 1;
            }
            else {
                length2 += typedBlock2.length;
            }
            int n = 0;
            ++n;
        }
        return writer.toStackMapTable(this.cpool);
    }
    
    private boolean isTarget(final TypedBlock typedBlock, final TypedBlock typedBlock2) {
        final int incoming = typedBlock.incoming;
        return incoming > 1 || (incoming >= 1 && typedBlock2.stop);
    }
    
    private void toStackMapBody(final StackMapTable.Writer writer, final TypedBlock typedBlock, final int n, final int n2, final TypedBlock typedBlock2) {
        final int stackTop = typedBlock.stackTop;
        if (stackTop == 0) {
            if (n == 0) {
                writer.sameFrame(n2);
                return;
            }
            if (0 > n && n >= -3) {
                writer.chopFrame(n2, -n);
                return;
            }
            if (0 < n && n <= 3) {
                final int[] array = new int[n];
                writer.appendFrame(n2, this.fillStackMap(typedBlock.numLocals - typedBlock2.numLocals, typedBlock2.numLocals, array, typedBlock.localsTypes), array);
                return;
            }
        }
        else {
            if (stackTop == 1 && n == 0) {
                final TypeData typeData = typedBlock.stackTypes[0];
                writer.sameLocals(n2, typeData.getTypeTag(), typeData.getTypeData(this.cpool));
                return;
            }
            if (stackTop == 2 && n == 0) {
                final TypeData typeData2 = typedBlock.stackTypes[0];
                if (typeData2.is2WordType()) {
                    writer.sameLocals(n2, typeData2.getTypeTag(), typeData2.getTypeData(this.cpool));
                    return;
                }
            }
        }
        final int[] array2 = new int[stackTop];
        final int[] fillStackMap = this.fillStackMap(stackTop, 0, array2, typedBlock.stackTypes);
        final int[] array3 = new int[typedBlock.numLocals];
        writer.fullFrame(n2, this.fillStackMap(typedBlock.numLocals, 0, array3, typedBlock.localsTypes), array3, fillStackMap, array2);
    }
    
    private int[] fillStackMap(final int n, final int n2, final int[] array, final TypeData[] array2) {
        final int diffSize = diffSize(array2, n2, n2 + n);
        final ConstPool cpool = this.cpool;
        final int[] array3 = new int[diffSize];
        while (0 < n) {
            final TypeData typeData = array2[n2 + 0];
            array3[0] = typeData.getTypeTag();
            array[0] = typeData.getTypeData(cpool);
            int n3 = 0;
            if (typeData.is2WordType()) {
                ++n3;
            }
            int n4 = 0;
            ++n4;
            ++n3;
        }
        return array3;
    }
    
    private static int stackMapDiff(final int n, final TypeData[] array, final int n2, final TypeData[] array2) {
        final int n3 = n2 - n;
        int n4;
        if (n3 > 0) {
            n4 = n;
        }
        else {
            n4 = n2;
        }
        if (!stackMapEq(array, array2, n4)) {
            return -100;
        }
        if (n3 > 0) {
            return diffSize(array2, n4, n2);
        }
        return -diffSize(array, n4, n);
    }
    
    private static boolean stackMapEq(final TypeData[] array, final TypeData[] array2, final int n) {
        while (0 < n) {
            if (!array[0].eq(array2[0])) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    private static int diffSize(final TypeData[] array, int i, final int n) {
        while (i < n) {
            final TypeData typeData = array[i++];
            int n2 = 0;
            ++n2;
            if (typeData.is2WordType()) {
                ++i;
            }
        }
        return 0;
    }
    
    public StackMap toStackMap2(final ConstPool constPool, final TypedBlock[] array) {
        final StackMap.Writer writer = new StackMap.Writer();
        final int length = array.length;
        final boolean[] array2 = new boolean[length];
        array2[0] = (array[0].incoming > 0);
        int n = array2[0] ? 1 : 0;
        int n3 = 0;
        while (0 < length) {
            final TypedBlock typedBlock = array[0];
            final boolean[] array3 = array2;
            final int n2 = 0;
            final boolean target = this.isTarget(typedBlock, array[-1]);
            array3[n2] = target;
            if (target) {
                typedBlock.resetNumLocals();
                ++n;
            }
            ++n3;
        }
        if (n == 0) {
            return null;
        }
        writer.write16bit(n);
        while (0 < length) {
            if (array2[0]) {
                this.writeStackFrame(writer, constPool, array[0].position, array[0]);
            }
            ++n3;
        }
        return writer.toStackMap(constPool);
    }
    
    private void writeStackFrame(final StackMap.Writer writer, final ConstPool constPool, final int n, final TypedBlock typedBlock) {
        writer.write16bit(n);
        this.writeVerifyTypeInfo(writer, constPool, typedBlock.localsTypes, typedBlock.numLocals);
        this.writeVerifyTypeInfo(writer, constPool, typedBlock.stackTypes, typedBlock.stackTop);
    }
    
    private void writeVerifyTypeInfo(final StackMap.Writer writer, final ConstPool constPool, final TypeData[] array, final int n) {
        int n3 = 0;
        while (0 < n) {
            final TypeData typeData = array[0];
            if (typeData != null && typeData.is2WordType()) {
                int n2 = 0;
                ++n2;
                ++n3;
            }
            ++n3;
        }
        writer.write16bit(n - 0);
        while (0 < n) {
            final TypeData typeData2 = array[0];
            writer.writeVerifyTypeInfo(typeData2.getTypeTag(), typeData2.getTypeData(constPool));
            if (typeData2.is2WordType()) {
                ++n3;
            }
            ++n3;
        }
    }
}
