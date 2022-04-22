package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class TransformAfter extends TransformBefore
{
    public TransformAfter(final Transformer transformer, final CtMethod ctMethod, final CtMethod ctMethod2) throws NotFoundException {
        super(transformer, ctMethod, ctMethod2);
    }
    
    @Override
    protected int match2(int next, final CodeIterator codeIterator) throws BadBytecode {
        codeIterator.move(next);
        codeIterator.insert(this.saveCode);
        codeIterator.insert(this.loadCode);
        codeIterator.setMark(codeIterator.insertGap(3));
        codeIterator.insert(this.loadCode);
        next = codeIterator.next();
        final int mark = codeIterator.getMark();
        codeIterator.writeByte(codeIterator.byteAt(next), mark);
        codeIterator.write16bit(codeIterator.u16bitAt(next + 1), mark + 1);
        codeIterator.writeByte(184, next);
        codeIterator.write16bit(this.newIndex, next + 1);
        codeIterator.move(mark);
        return codeIterator.next();
    }
}
