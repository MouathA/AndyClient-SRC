package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

public class Frame
{
    private Type[] locals;
    private Type[] stack;
    private int top;
    private boolean jsrMerged;
    private boolean retMerged;
    
    public Frame(final int n, final int n2) {
        this.locals = new Type[n];
        this.stack = new Type[n2];
    }
    
    public Type getLocal(final int n) {
        return this.locals[n];
    }
    
    public void setLocal(final int n, final Type type) {
        this.locals[n] = type;
    }
    
    public Type getStack(final int n) {
        return this.stack[n];
    }
    
    public void setStack(final int n, final Type type) {
        this.stack[n] = type;
    }
    
    public void clearStack() {
        this.top = 0;
    }
    
    public int getTopIndex() {
        return this.top - 1;
    }
    
    public int localsLength() {
        return this.locals.length;
    }
    
    public Type peek() {
        if (this.top < 1) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        return this.stack[this.top - 1];
    }
    
    public Type pop() {
        if (this.top < 1) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        final Type[] stack = this.stack;
        final int top = this.top - 1;
        this.top = top;
        return stack[top];
    }
    
    public void push(final Type type) {
        this.stack[this.top++] = type;
    }
    
    public Frame copy() {
        final Frame frame = new Frame(this.locals.length, this.stack.length);
        System.arraycopy(this.locals, 0, frame.locals, 0, this.locals.length);
        System.arraycopy(this.stack, 0, frame.stack, 0, this.stack.length);
        frame.top = this.top;
        return frame;
    }
    
    public Frame copyStack() {
        final Frame frame = new Frame(this.locals.length, this.stack.length);
        System.arraycopy(this.stack, 0, frame.stack, 0, this.stack.length);
        frame.top = this.top;
        return frame;
    }
    
    public boolean mergeStack(final Frame frame) {
        if (this.top != frame.top) {
            throw new RuntimeException("Operand stacks could not be merged, they are different sizes!");
        }
        while (0 < this.top) {
            if (this.stack[0] != null) {
                final Type type = this.stack[0];
                final Type merge = type.merge(frame.stack[0]);
                if (merge == Type.BOGUS) {
                    throw new RuntimeException("Operand stacks could not be merged due to differing primitive types: pos = " + 0);
                }
                this.stack[0] = merge;
                if (!merge.equals(type) || merge.popChanged()) {}
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public boolean merge(final Frame frame) {
        while (0 < this.locals.length) {
            if (this.locals[0] != null) {
                final Type type = this.locals[0];
                final Type merge = type.merge(frame.locals[0]);
                this.locals[0] = merge;
                if (!merge.equals(type) || merge.popChanged()) {}
            }
            else if (frame.locals[0] != null) {
                this.locals[0] = frame.locals[0];
            }
            int n = 0;
            ++n;
        }
        final boolean b = true | this.mergeStack(frame);
        return true;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("locals = [");
        int n = 0;
        while (0 < this.locals.length) {
            sb.append((this.locals[0] == null) ? "empty" : this.locals[0].toString());
            if (0 < this.locals.length - 1) {
                sb.append(", ");
            }
            ++n;
        }
        sb.append("] stack = [");
        while (0 < this.top) {
            sb.append(this.stack[0]);
            if (0 < this.top - 1) {
                sb.append(", ");
            }
            ++n;
        }
        sb.append("]");
        return sb.toString();
    }
    
    boolean isJsrMerged() {
        return this.jsrMerged;
    }
    
    void setJsrMerged(final boolean jsrMerged) {
        this.jsrMerged = jsrMerged;
    }
    
    boolean isRetMerged() {
        return this.retMerged;
    }
    
    void setRetMerged(final boolean retMerged) {
        this.retMerged = retMerged;
    }
}
