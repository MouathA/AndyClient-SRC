package org.apache.http.util;

import java.lang.reflect.*;

@Deprecated
public final class ExceptionUtils
{
    private static final Method INIT_CAUSE_METHOD;
    
    private static Method getInitCauseMethod() {
        return Throwable.class.getMethod("initCause", Throwable.class);
    }
    
    public static void initCause(final Throwable t, final Throwable t2) {
        if (ExceptionUtils.INIT_CAUSE_METHOD != null) {
            ExceptionUtils.INIT_CAUSE_METHOD.invoke(t, t2);
        }
    }
    
    private ExceptionUtils() {
    }
    
    static {
        INIT_CAUSE_METHOD = getInitCauseMethod();
    }
}
