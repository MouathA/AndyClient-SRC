package org.apache.http.client;

import org.apache.http.*;
import org.apache.http.protocol.*;
import java.util.*;
import org.apache.http.auth.*;

public interface AuthenticationStrategy
{
    boolean isAuthenticationRequested(final HttpHost p0, final HttpResponse p1, final HttpContext p2);
    
    Map getChallenges(final HttpHost p0, final HttpResponse p1, final HttpContext p2) throws MalformedChallengeException;
    
    Queue select(final Map p0, final HttpHost p1, final HttpResponse p2, final HttpContext p3) throws MalformedChallengeException;
    
    void authSucceeded(final HttpHost p0, final AuthScheme p1, final HttpContext p2);
    
    void authFailed(final HttpHost p0, final AuthScheme p1, final HttpContext p2);
}
