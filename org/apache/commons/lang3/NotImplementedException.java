package org.apache.commons.lang3;

public class NotImplementedException extends UnsupportedOperationException
{
    private static final long serialVersionUID = 20131021L;
    private final String code;
    
    public NotImplementedException(final String s) {
        this(s, (String)null);
    }
    
    public NotImplementedException(final Throwable t) {
        this(t, null);
    }
    
    public NotImplementedException(final String s, final Throwable t) {
        this(s, t, null);
    }
    
    public NotImplementedException(final String s, final String code) {
        super(s);
        this.code = code;
    }
    
    public NotImplementedException(final Throwable t, final String code) {
        super(t);
        this.code = code;
    }
    
    public NotImplementedException(final String s, final Throwable t, final String code) {
        super(s, t);
        this.code = code;
    }
    
    public String getCode() {
        return this.code;
    }
}
