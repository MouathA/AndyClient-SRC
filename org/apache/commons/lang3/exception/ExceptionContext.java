package org.apache.commons.lang3.exception;

import java.util.*;

public interface ExceptionContext
{
    ExceptionContext addContextValue(final String p0, final Object p1);
    
    ExceptionContext setContextValue(final String p0, final Object p1);
    
    List getContextValues(final String p0);
    
    Object getFirstContextValue(final String p0);
    
    Set getContextLabels();
    
    List getContextEntries();
    
    String getFormattedExceptionMessage(final String p0);
}
