package org.lwjgl.opengl;

class ReferencesStack
{
    private References[] references_stack;
    private int stack_pos;
    
    public References getReferences() {
        return this.references_stack[this.stack_pos];
    }
    
    public void pushState() {
        final int n = ++this.stack_pos;
        if (n == this.references_stack.length) {
            this.growStack();
        }
        this.references_stack[n].copy(this.references_stack[n - 1], -1);
    }
    
    public References popState(final int n) {
        final References references = this.references_stack[this.stack_pos--];
        this.references_stack[this.stack_pos].copy(references, ~n);
        references.clear();
        return references;
    }
    
    private void growStack() {
        final References[] references_stack = new References[this.references_stack.length + 1];
        System.arraycopy(this.references_stack, 0, references_stack, 0, this.references_stack.length);
        (this.references_stack = references_stack)[this.references_stack.length - 1] = new References(GLContext.getCapabilities());
    }
    
    ReferencesStack() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        this.references_stack = new References[1];
        this.stack_pos = 0;
        while (0 < this.references_stack.length) {
            this.references_stack[0] = new References(capabilities);
            int n = 0;
            ++n;
        }
    }
}
