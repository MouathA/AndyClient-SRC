package org.apache.http.auth;

import org.apache.http.*;
import org.apache.http.annotation.*;

@Immutable
public class MalformedChallengeException extends ProtocolException
{
    private static final long serialVersionUID = 814586927989932284L;
    
    public MalformedChallengeException() {
    }
    
    public MalformedChallengeException(final String s) {
        super(s);
    }
    
    public MalformedChallengeException(final String s, final Throwable t) {
        super(s, t);
    }
}
