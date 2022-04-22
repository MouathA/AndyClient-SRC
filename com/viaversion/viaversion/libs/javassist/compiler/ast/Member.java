package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;

public class Member extends Symbol
{
    private static final long serialVersionUID = 1L;
    private CtField field;
    
    public Member(final String s) {
        super(s);
        this.field = null;
    }
    
    public void setField(final CtField field) {
        this.field = field;
    }
    
    public CtField getField() {
        return this.field;
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atMember(this);
    }
}
