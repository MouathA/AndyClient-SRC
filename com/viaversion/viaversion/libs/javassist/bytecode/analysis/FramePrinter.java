package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import java.io.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public final class FramePrinter
{
    private final PrintStream stream;
    
    public FramePrinter(final PrintStream stream) {
        this.stream = stream;
    }
    
    public static void print(final CtClass ctClass, final PrintStream printStream) {
        new FramePrinter(printStream).print(ctClass);
    }
    
    public void print(final CtClass ctClass) {
        final CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        while (0 < declaredMethods.length) {
            this.print(declaredMethods[0]);
            int n = 0;
            ++n;
        }
    }
    
    private String getMethodString(final CtMethod ctMethod) {
        return Modifier.toString(ctMethod.getModifiers()) + " " + ctMethod.getReturnType().getName() + " " + ctMethod.getName() + Descriptor.toString(ctMethod.getSignature()) + ";";
    }
    
    public void print(final CtMethod ctMethod) {
        this.stream.println("\n" + this.getMethodString(ctMethod));
        final MethodInfo methodInfo2 = ctMethod.getMethodInfo2();
        final ConstPool constPool = methodInfo2.getConstPool();
        final CodeAttribute codeAttribute = methodInfo2.getCodeAttribute();
        if (codeAttribute == null) {
            return;
        }
        final Frame[] analyze = new Analyzer().analyze(ctMethod.getDeclaringClass(), methodInfo2);
        final int length = String.valueOf(codeAttribute.getCodeLength()).length();
        final CodeIterator iterator = codeAttribute.iterator();
        while (iterator.hasNext()) {
            final int next = iterator.next();
            this.stream.println(next + ": " + InstructionPrinter.instructionString(iterator, next, constPool));
            this.addSpacing(length + 3);
            final Frame frame = analyze[next];
            if (frame == null) {
                this.stream.println("--DEAD CODE--");
            }
            else {
                this.printStack(frame);
                this.addSpacing(length + 3);
                this.printLocals(frame);
            }
        }
    }
    
    private void printStack(final Frame frame) {
        this.stream.print("stack [");
        while (0 <= frame.getTopIndex()) {
            if (0 > 0) {
                this.stream.print(", ");
            }
            this.stream.print(frame.getStack(0));
            int n = 0;
            ++n;
        }
        this.stream.println("]");
    }
    
    private void printLocals(final Frame frame) {
        this.stream.print("locals [");
        while (0 < frame.localsLength()) {
            if (0 > 0) {
                this.stream.print(", ");
            }
            final Type local = frame.getLocal(0);
            this.stream.print((local == null) ? "empty" : local.toString());
            int n = 0;
            ++n;
        }
        this.stream.println("]");
    }
    
    private void addSpacing(int n) {
        while (n-- > 0) {
            this.stream.print(' ');
        }
    }
}
