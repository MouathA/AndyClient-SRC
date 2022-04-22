package com.viaversion.viaversion.libs.javassist.bytecode;

class ExceptionTableEntry
{
    int startPc;
    int endPc;
    int handlerPc;
    int catchType;
    
    ExceptionTableEntry(final int startPc, final int endPc, final int handlerPc, final int catchType) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchType = catchType;
    }
}
