package com.ibm.icu.text;

import java.text.*;

public class StringPrepParseException extends ParseException
{
    static final long serialVersionUID = 7160264827701651255L;
    public static final int INVALID_CHAR_FOUND = 0;
    public static final int ILLEGAL_CHAR_FOUND = 1;
    public static final int PROHIBITED_ERROR = 2;
    public static final int UNASSIGNED_ERROR = 3;
    public static final int CHECK_BIDI_ERROR = 4;
    public static final int STD3_ASCII_RULES_ERROR = 5;
    public static final int ACE_PREFIX_ERROR = 6;
    public static final int VERIFICATION_ERROR = 7;
    public static final int LABEL_TOO_LONG_ERROR = 8;
    public static final int BUFFER_OVERFLOW_ERROR = 9;
    public static final int ZERO_LENGTH_LABEL = 10;
    public static final int DOMAIN_NAME_TOO_LONG_ERROR = 11;
    private int error;
    private int line;
    private StringBuffer preContext;
    private StringBuffer postContext;
    private static final int PARSE_CONTEXT_LEN = 16;
    static final boolean $assertionsDisabled;
    
    public StringPrepParseException(final String s, final int error) {
        super(s, -1);
        this.preContext = new StringBuffer();
        this.postContext = new StringBuffer();
        this.error = error;
        this.line = 0;
    }
    
    public StringPrepParseException(final String s, final int error, final String s2, final int n) {
        super(s, -1);
        this.preContext = new StringBuffer();
        this.postContext = new StringBuffer();
        this.error = error;
        this.setContext(s2, n);
        this.line = 0;
    }
    
    public StringPrepParseException(final String s, final int error, final String s2, final int n, final int line) {
        super(s, -1);
        this.preContext = new StringBuffer();
        this.postContext = new StringBuffer();
        this.error = error;
        this.setContext(s2, n);
        this.line = line;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof StringPrepParseException && ((StringPrepParseException)o).error == this.error;
    }
    
    @Override
    @Deprecated
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.getMessage());
        sb.append(". line:  ");
        sb.append(this.line);
        sb.append(". preContext:  ");
        sb.append(this.preContext);
        sb.append(". postContext: ");
        sb.append(this.postContext);
        sb.append("\n");
        return sb.toString();
    }
    
    private void setPreContext(final String s, final int n) {
        this.setPreContext(s.toCharArray(), n);
    }
    
    private void setPreContext(final char[] array, final int n) {
        final int n2 = (n <= 16) ? 0 : (n - 15);
        this.preContext.append(array, n2, (n2 <= 16) ? n2 : 16);
    }
    
    private void setPostContext(final String s, final int n) {
        this.setPostContext(s.toCharArray(), n);
    }
    
    private void setPostContext(final char[] array, final int n) {
        this.postContext.append(array, n, array.length - n);
    }
    
    private void setContext(final String s, final int n) {
        this.setPreContext(s, n);
        this.setPostContext(s, n);
    }
    
    public int getError() {
        return this.error;
    }
    
    static {
        $assertionsDisabled = !StringPrepParseException.class.desiredAssertionStatus();
    }
}
