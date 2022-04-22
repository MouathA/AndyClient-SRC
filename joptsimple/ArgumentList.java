package joptsimple;

class ArgumentList
{
    private final String[] arguments;
    private int currentIndex;
    
    ArgumentList(final String... array) {
        this.arguments = array.clone();
    }
    
    boolean hasMore() {
        return this.currentIndex < this.arguments.length;
    }
    
    String next() {
        return this.arguments[this.currentIndex++];
    }
    
    String peek() {
        return this.arguments[this.currentIndex];
    }
    
    void treatNextAsLongOption() {
        if ('-' != this.arguments[this.currentIndex].charAt(0)) {
            this.arguments[this.currentIndex] = "--" + this.arguments[this.currentIndex];
        }
    }
}
