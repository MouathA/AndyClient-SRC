package org.apache.commons.lang3.exception;

import java.util.*;

public class ContextedException extends Exception implements ExceptionContext
{
    private static final long serialVersionUID = 20110706L;
    private final ExceptionContext exceptionContext;
    
    public ContextedException() {
        this.exceptionContext = new DefaultExceptionContext();
    }
    
    public ContextedException(final String s) {
        super(s);
        this.exceptionContext = new DefaultExceptionContext();
    }
    
    public ContextedException(final Throwable t) {
        super(t);
        this.exceptionContext = new DefaultExceptionContext();
    }
    
    public ContextedException(final String s, final Throwable t) {
        super(s, t);
        this.exceptionContext = new DefaultExceptionContext();
    }
    
    public ContextedException(final String s, final Throwable t, ExceptionContext exceptionContext) {
        super(s, t);
        if (exceptionContext == null) {
            exceptionContext = new DefaultExceptionContext();
        }
        this.exceptionContext = exceptionContext;
    }
    
    @Override
    public ContextedException addContextValue(final String s, final Object o) {
        this.exceptionContext.addContextValue(s, o);
        return this;
    }
    
    @Override
    public ContextedException setContextValue(final String s, final Object o) {
        this.exceptionContext.setContextValue(s, o);
        return this;
    }
    
    @Override
    public List getContextValues(final String s) {
        return this.exceptionContext.getContextValues(s);
    }
    
    @Override
    public Object getFirstContextValue(final String s) {
        return this.exceptionContext.getFirstContextValue(s);
    }
    
    @Override
    public List getContextEntries() {
        return this.exceptionContext.getContextEntries();
    }
    
    @Override
    public Set getContextLabels() {
        return this.exceptionContext.getContextLabels();
    }
    
    @Override
    public String getMessage() {
        return this.getFormattedExceptionMessage(super.getMessage());
    }
    
    public String getRawMessage() {
        return super.getMessage();
    }
    
    @Override
    public String getFormattedExceptionMessage(final String s) {
        return this.exceptionContext.getFormattedExceptionMessage(s);
    }
    
    @Override
    public ExceptionContext setContextValue(final String s, final Object o) {
        return this.setContextValue(s, o);
    }
    
    @Override
    public ExceptionContext addContextValue(final String s, final Object o) {
        return this.addContextValue(s, o);
    }
}
