package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.analysis.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public final class TransformAccessArrayField extends Transformer
{
    private final String methodClassname;
    private final CodeConverter.ArrayAccessReplacementMethodNames names;
    private Frame[] frames;
    private int offset;
    
    public TransformAccessArrayField(final Transformer transformer, final String methodClassname, final CodeConverter.ArrayAccessReplacementMethodNames names) throws NotFoundException {
        super(transformer);
        this.methodClassname = methodClassname;
        this.names = names;
    }
    
    @Override
    public void initialize(final ConstPool constPool, final CtClass ctClass, final MethodInfo methodInfo) throws CannotCompileException {
        final CodeIterator iterator = methodInfo.getCodeAttribute().iterator();
        while (iterator.hasNext()) {
            final int next = iterator.next();
            final int byte1 = iterator.byteAt(next);
            if (byte1 == 50) {
                this.initFrames(ctClass, methodInfo);
            }
            if (byte1 == 50 || byte1 == 51 || byte1 == 52 || byte1 == 49 || byte1 == 48 || byte1 == 46 || byte1 == 47 || byte1 == 53) {
                this.replace(constPool, iterator, next, byte1, this.getLoadReplacementSignature(byte1));
            }
            else {
                if (byte1 != 83 && byte1 != 84 && byte1 != 85 && byte1 != 82 && byte1 != 81 && byte1 != 79 && byte1 != 80 && byte1 != 86) {
                    continue;
                }
                this.replace(constPool, iterator, next, byte1, this.getStoreReplacementSignature(byte1));
            }
        }
    }
    
    @Override
    public void clean() {
        this.frames = null;
        this.offset = -1;
    }
    
    @Override
    public int transform(final CtClass ctClass, final int n, final CodeIterator codeIterator, final ConstPool constPool) throws BadBytecode {
        return n;
    }
    
    private Frame getFrame(final int n) throws BadBytecode {
        return this.frames[n - this.offset];
    }
    
    private void initFrames(final CtClass ctClass, final MethodInfo methodInfo) throws BadBytecode {
        if (this.frames == null) {
            this.frames = new Analyzer().analyze(ctClass, methodInfo);
            this.offset = 0;
        }
    }
    
    private int updatePos(final int n, final int n2) {
        if (this.offset > -1) {
            this.offset += n2;
        }
        return n + n2;
    }
    
    private String getTopType(final int n) throws BadBytecode {
        final Frame frame = this.getFrame(n);
        if (frame == null) {
            return null;
        }
        final CtClass ctClass = frame.peek().getCtClass();
        return (ctClass != null) ? Descriptor.toJvmName(ctClass) : null;
    }
    
    private int replace(final ConstPool constPool, final CodeIterator codeIterator, int n, final int n2, final String s) throws BadBytecode {
        String topType = null;
        final String methodName = this.getMethodName(n2);
        if (methodName != null) {
            if (n2 == 50) {
                topType = this.getTopType(codeIterator.lookAhead());
                if (topType == null) {
                    return n;
                }
                if ("java/lang/Object".equals(topType)) {
                    topType = null;
                }
            }
            codeIterator.writeByte(0, n);
            final CodeIterator.Gap insertGap = codeIterator.insertGapAt(n, (topType != null) ? 5 : 2, false);
            n = insertGap.position;
            final int addMethodrefInfo = constPool.addMethodrefInfo(constPool.addClassInfo(this.methodClassname), methodName, s);
            codeIterator.writeByte(184, n);
            codeIterator.write16bit(addMethodrefInfo, n + 1);
            if (topType != null) {
                final int addClassInfo = constPool.addClassInfo(topType);
                codeIterator.writeByte(192, n + 3);
                codeIterator.write16bit(addClassInfo, n + 4);
            }
            n = this.updatePos(n, insertGap.length);
        }
        return n;
    }
    
    private String getMethodName(final int n) {
        String s = null;
        switch (n) {
            case 50: {
                s = this.names.objectRead();
                break;
            }
            case 51: {
                s = this.names.byteOrBooleanRead();
                break;
            }
            case 52: {
                s = this.names.charRead();
                break;
            }
            case 49: {
                s = this.names.doubleRead();
                break;
            }
            case 48: {
                s = this.names.floatRead();
                break;
            }
            case 46: {
                s = this.names.intRead();
                break;
            }
            case 53: {
                s = this.names.shortRead();
                break;
            }
            case 47: {
                s = this.names.longRead();
                break;
            }
            case 83: {
                s = this.names.objectWrite();
                break;
            }
            case 84: {
                s = this.names.byteOrBooleanWrite();
                break;
            }
            case 85: {
                s = this.names.charWrite();
                break;
            }
            case 82: {
                s = this.names.doubleWrite();
                break;
            }
            case 81: {
                s = this.names.floatWrite();
                break;
            }
            case 79: {
                s = this.names.intWrite();
                break;
            }
            case 86: {
                s = this.names.shortWrite();
                break;
            }
            case 80: {
                s = this.names.longWrite();
                break;
            }
        }
        if (s.equals("")) {
            s = null;
        }
        return s;
    }
    
    private String getLoadReplacementSignature(final int n) throws BadBytecode {
        switch (n) {
            case 50: {
                return "(Ljava/lang/Object;I)Ljava/lang/Object;";
            }
            case 51: {
                return "(Ljava/lang/Object;I)B";
            }
            case 52: {
                return "(Ljava/lang/Object;I)C";
            }
            case 49: {
                return "(Ljava/lang/Object;I)D";
            }
            case 48: {
                return "(Ljava/lang/Object;I)F";
            }
            case 46: {
                return "(Ljava/lang/Object;I)I";
            }
            case 53: {
                return "(Ljava/lang/Object;I)S";
            }
            case 47: {
                return "(Ljava/lang/Object;I)J";
            }
            default: {
                throw new BadBytecode(n);
            }
        }
    }
    
    private String getStoreReplacementSignature(final int n) throws BadBytecode {
        switch (n) {
            case 83: {
                return "(Ljava/lang/Object;ILjava/lang/Object;)V";
            }
            case 84: {
                return "(Ljava/lang/Object;IB)V";
            }
            case 85: {
                return "(Ljava/lang/Object;IC)V";
            }
            case 82: {
                return "(Ljava/lang/Object;ID)V";
            }
            case 81: {
                return "(Ljava/lang/Object;IF)V";
            }
            case 79: {
                return "(Ljava/lang/Object;II)V";
            }
            case 86: {
                return "(Ljava/lang/Object;IS)V";
            }
            case 80: {
                return "(Ljava/lang/Object;IJ)V";
            }
            default: {
                throw new BadBytecode(n);
            }
        }
    }
}
