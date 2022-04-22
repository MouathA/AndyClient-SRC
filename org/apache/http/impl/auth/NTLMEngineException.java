package org.apache.http.impl.auth;

import org.apache.http.auth.*;
import org.apache.http.annotation.*;

@Immutable
public class NTLMEngineException extends AuthenticationException
{
    private static final long serialVersionUID = 6027981323731768824L;
    
    public NTLMEngineException() {
    }
    
    public NTLMEngineException(final String s) {
        super(s);
    }
    
    public NTLMEngineException(final String s, final Throwable t) {
        super(s, t);
    }
}
