package io.netty.util.internal.logging;

class FormattingTuple
{
    static final FormattingTuple NULL;
    private final String message;
    private final Throwable throwable;
    private final Object[] argArray;
    
    FormattingTuple(final String s) {
        this(s, null, null);
    }
    
    FormattingTuple(final String message, final Object[] argArray, final Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
        if (throwable == null) {
            this.argArray = argArray;
        }
        else {
            this.argArray = trimmedCopy(argArray);
        }
    }
    
    static Object[] trimmedCopy(final Object[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        final int n = array.length - 1;
        final Object[] array2 = new Object[n];
        System.arraycopy(array, 0, array2, 0, n);
        return array2;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public Object[] getArgArray() {
        return this.argArray;
    }
    
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    static {
        NULL = new FormattingTuple(null);
    }
}
