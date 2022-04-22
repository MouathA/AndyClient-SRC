package org.apache.http.client;

import org.apache.http.*;
import org.apache.http.protocol.*;
import java.util.*;
import org.apache.http.auth.*;

@Deprecated
public interface AuthenticationHandler
{
    boolean isAuthenticationRequested(final HttpResponse p0, final HttpContext p1);
    
    Map getChallenges(final HttpResponse p0, final HttpContext p1) throws MalformedChallengeException;
    
    AuthScheme selectScheme(final Map p0, final HttpResponse p1, final HttpContext p2) throws AuthenticationException;
}
