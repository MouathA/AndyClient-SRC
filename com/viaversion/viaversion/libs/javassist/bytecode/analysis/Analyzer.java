package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;

public class Analyzer implements Opcode
{
    private final SubroutineScanner scanner;
    private CtClass clazz;
    private ExceptionInfo[] exceptions;
    private Frame[] frames;
    private Subroutine[] subroutines;
    
    public Analyzer() {
        this.scanner = new SubroutineScanner();
    }
    
    public Frame[] analyze(final CtClass clazz, final MethodInfo methodInfo) throws BadBytecode {
        this.clazz = clazz;
        final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            return null;
        }
        final int maxLocals = codeAttribute.getMaxLocals();
        final int maxStack = codeAttribute.getMaxStack();
        final int codeLength = codeAttribute.getCodeLength();
        final CodeIterator iterator = codeAttribute.iterator();
        final IntQueue intQueue = new IntQueue();
        this.exceptions = this.buildExceptionInfo(methodInfo);
        this.subroutines = this.scanner.scan(methodInfo);
        final Executor executor = new Executor(clazz.getClassPool(), methodInfo.getConstPool());
        (this.frames = new Frame[codeLength])[iterator.lookAhead()] = this.firstFrame(methodInfo, maxLocals, maxStack);
        intQueue.add(iterator.next());
        while (!intQueue.isEmpty()) {
            this.analyzeNextEntry(methodInfo, iterator, intQueue, executor);
        }
        return this.frames;
    }
    
    public Frame[] analyze(final CtMethod ctMethod) throws BadBytecode {
        return this.analyze(ctMethod.getDeclaringClass(), ctMethod.getMethodInfo2());
    }
    
    private void analyzeNextEntry(final MethodInfo methodInfo, final CodeIterator codeIterator, final IntQueue intQueue, final Executor executor) throws BadBytecode {
        final int take = intQueue.take();
        codeIterator.move(take);
        codeIterator.next();
        final Frame copy = this.frames[take].copy();
        final Subroutine subroutine = this.subroutines[take];
        executor.execute(methodInfo, take, codeIterator, copy, subroutine);
        final int byte1 = codeIterator.byteAt(take);
        if (byte1 == 170) {
            this.mergeTableSwitch(intQueue, take, codeIterator, copy);
        }
        else if (byte1 == 171) {
            this.mergeLookupSwitch(intQueue, take, codeIterator, copy);
        }
        else if (byte1 == 169) {
            this.mergeRet(intQueue, codeIterator, take, copy, subroutine);
        }
        else if (Util.isJumpInstruction(byte1)) {
            final int jumpTarget = Util.getJumpTarget(take, codeIterator);
            if (Util.isJsr(byte1)) {
                this.mergeJsr(intQueue, this.frames[take], this.subroutines[jumpTarget], take, this.lookAhead(codeIterator, take));
            }
            else if (!Util.isGoto(byte1)) {
                this.merge(intQueue, copy, this.lookAhead(codeIterator, take));
            }
            this.merge(intQueue, copy, jumpTarget);
        }
        else if (byte1 != 191 && !Util.isReturn(byte1)) {
            this.merge(intQueue, copy, this.lookAhead(codeIterator, take));
        }
        this.mergeExceptionHandlers(intQueue, methodInfo, take, copy);
    }
    
    private ExceptionInfo[] buildExceptionInfo(final MethodInfo methodInfo) {
        final ConstPool constPool = methodInfo.getConstPool();
        final ClassPool classPool = this.clazz.getClassPool();
        final ExceptionTable exceptionTable = methodInfo.getCodeAttribute().getExceptionTable();
        final ExceptionInfo[] array = new ExceptionInfo[exceptionTable.size()];
        while (0 < exceptionTable.size()) {
            final int catchType = exceptionTable.catchType(0);
            array[0] = new ExceptionInfo(exceptionTable.startPc(0), exceptionTable.endPc(0), exceptionTable.handlerPc(0), (catchType == 0) ? Type.THROWABLE : Type.get(classPool.get(constPool.getClassInfo(catchType))), null);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private Frame firstFrame(final MethodInfo methodInfo, final int n, final int n2) {
        final Frame frame = new Frame(n, n2);
        int n4 = 0;
        if ((methodInfo.getAccessFlags() & 0x8) == 0x0) {
            final Frame frame2 = frame;
            final int n3 = 0;
            ++n4;
            frame2.setLocal(n3, Type.get(this.clazz));
        }
        final CtClass[] parameterTypes = Descriptor.getParameterTypes(methodInfo.getDescriptor(), this.clazz.getClassPool());
        while (0 < parameterTypes.length) {
            final Type zeroExtend = this.zeroExtend(Type.get(parameterTypes[0]));
            final Frame frame3 = frame;
            final int n5 = 0;
            ++n4;
            frame3.setLocal(n5, zeroExtend);
            if (zeroExtend.getSize() == 2) {
                final Frame frame4 = frame;
                final int n6 = 0;
                ++n4;
                frame4.setLocal(n6, Type.TOP);
            }
            int n7 = 0;
            ++n7;
        }
        return frame;
    }
    
    private int getNext(final CodeIterator codeIterator, final int n, final int n2) throws BadBytecode {
        codeIterator.move(n);
        codeIterator.next();
        final int lookAhead = codeIterator.lookAhead();
        codeIterator.move(n2);
        codeIterator.next();
        return lookAhead;
    }
    
    private int lookAhead(final CodeIterator codeIterator, final int n) throws BadBytecode {
        if (!codeIterator.hasNext()) {
            throw new BadBytecode("Execution falls off end! [pos = " + n + "]");
        }
        return codeIterator.lookAhead();
    }
    
    private void merge(final IntQueue intQueue, final Frame frame, final int n) {
        final Frame frame2 = this.frames[n];
        if (frame2 == null) {
            this.frames[n] = frame.copy();
        }
        else {
            frame2.merge(frame);
        }
        if (true) {
            intQueue.add(n);
        }
    }
    
    private void mergeExceptionHandlers(final IntQueue intQueue, final MethodInfo methodInfo, final int n, final Frame frame) {
        while (0 < this.exceptions.length) {
            final ExceptionInfo exceptionInfo = this.exceptions[0];
            if (n >= ExceptionInfo.access$100(exceptionInfo) && n < ExceptionInfo.access$200(exceptionInfo)) {
                final Frame copy = frame.copy();
                copy.clearStack();
                copy.push(ExceptionInfo.access$300(exceptionInfo));
                this.merge(intQueue, copy, ExceptionInfo.access$400(exceptionInfo));
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    private void mergeJsr(final IntQueue intQueue, final Frame frame, final Subroutine subroutine, final int n, final int n2) throws BadBytecode {
        if (subroutine == null) {
            throw new BadBytecode("No subroutine at jsr target! [pos = " + n + "]");
        }
        Frame frame2 = this.frames[n2];
        if (frame2 == null) {
            final Frame[] frames = this.frames;
            final Frame copy = frame.copy();
            frames[n2] = copy;
            frame2 = copy;
        }
        else {
            while (0 < frame.localsLength()) {
                if (!subroutine.isAccessed(0)) {
                    final Type local = frame2.getLocal(0);
                    final Type local2 = frame.getLocal(0);
                    if (local == null) {
                        frame2.setLocal(0, local2);
                    }
                    else {
                        final Type merge = local.merge(local2);
                        frame2.setLocal(0, merge);
                        if (!merge.equals(local) || merge.popChanged()) {}
                    }
                }
                int n3 = 0;
                ++n3;
            }
        }
        if (!frame2.isJsrMerged()) {
            frame2.setJsrMerged(true);
        }
        if (true && frame2.isRetMerged()) {
            intQueue.add(n2);
        }
    }
    
    private void mergeLookupSwitch(final IntQueue intQueue, final int n, final CodeIterator codeIterator, final Frame frame) throws BadBytecode {
        int i = (n & 0xFFFFFFFC) + 4;
        this.merge(intQueue, frame, n + codeIterator.s32bitAt(i));
        i += 4;
        final int n2 = codeIterator.s32bitAt(i) * 8;
        for (i += 4, final int n3 = n2 + i, i += 4; i < n3; i += 8) {
            this.merge(intQueue, frame, codeIterator.s32bitAt(i) + n);
        }
    }
    
    private void mergeRet(final IntQueue intQueue, final CodeIterator codeIterator, final int n, final Frame frame, final Subroutine subroutine) throws BadBytecode {
        if (subroutine == null) {
            throw new BadBytecode("Ret on no subroutine! [pos = " + n + "]");
        }
        final Iterator iterator = subroutine.callers().iterator();
        while (iterator.hasNext()) {
            final int next = this.getNext(codeIterator, iterator.next(), n);
            Frame frame2 = this.frames[next];
            if (frame2 == null) {
                final Frame[] frames = this.frames;
                final int n2 = next;
                final Frame copyStack = frame.copyStack();
                frames[n2] = copyStack;
                frame2 = copyStack;
            }
            else {
                frame2.mergeStack(frame);
            }
            for (final int intValue : subroutine.accessed()) {
                final Type local = frame2.getLocal(intValue);
                final Type local2 = frame.getLocal(intValue);
                if (local != local2) {
                    frame2.setLocal(intValue, local2);
                }
            }
            if (!frame2.isRetMerged()) {
                frame2.setRetMerged(true);
            }
            if (true && frame2.isJsrMerged()) {
                intQueue.add(next);
            }
        }
    }
    
    private void mergeTableSwitch(final IntQueue intQueue, final int n, final CodeIterator codeIterator, final Frame frame) throws BadBytecode {
        int i = (n & 0xFFFFFFFC) + 4;
        this.merge(intQueue, frame, n + codeIterator.s32bitAt(i));
        i += 4;
        final int s32bit = codeIterator.s32bitAt(i);
        i += 4;
        final int n2 = (codeIterator.s32bitAt(i) - s32bit + 1) * 4;
        for (i += 4; i < n2 + i; i += 4) {
            this.merge(intQueue, frame, codeIterator.s32bitAt(i) + n);
        }
    }
    
    private Type zeroExtend(final Type type) {
        if (type == Type.SHORT || type == Type.BYTE || type == Type.CHAR || type == Type.BOOLEAN) {
            return Type.INTEGER;
        }
        return type;
    }
    
    private static class ExceptionInfo
    {
        private int end;
        private int handler;
        private int start;
        private Type type;
        
        private ExceptionInfo(final int start, final int end, final int handler, final Type type) {
            this.start = start;
            this.end = end;
            this.handler = handler;
            this.type = type;
        }
        
        ExceptionInfo(final int n, final int n2, final int n3, final Type type, final Analyzer$1 object) {
            this(n, n2, n3, type);
        }
        
        static int access$100(final ExceptionInfo exceptionInfo) {
            return exceptionInfo.start;
        }
        
        static int access$200(final ExceptionInfo exceptionInfo) {
            return exceptionInfo.end;
        }
        
        static Type access$300(final ExceptionInfo exceptionInfo) {
            return exceptionInfo.type;
        }
        
        static int access$400(final ExceptionInfo exceptionInfo) {
            return exceptionInfo.handler;
        }
    }
}
