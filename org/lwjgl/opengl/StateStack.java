package org.lwjgl.opengl;

class StateStack
{
    private int[] state_stack;
    private int stack_pos;
    
    public int getState() {
        return this.state_stack[this.stack_pos];
    }
    
    public void pushState(final int n) {
        final int n2 = ++this.stack_pos;
        if (n2 == this.state_stack.length) {
            this.growState();
        }
        this.state_stack[n2] = n;
    }
    
    public int popState() {
        return this.state_stack[this.stack_pos--];
    }
    
    public void growState() {
        final int[] state_stack = new int[this.state_stack.length + 1];
        System.arraycopy(this.state_stack, 0, state_stack, 0, this.state_stack.length);
        this.state_stack = state_stack;
    }
    
    StateStack(final int n) {
        this.state_stack = new int[1];
        this.stack_pos = 0;
        this.state_stack[this.stack_pos] = n;
    }
}
