package com.mojang.authlib;

public abstract class HttpUserAuthentication extends BaseUserAuthentication
{
    protected HttpUserAuthentication(final HttpAuthenticationService httpAuthenticationService) {
        super(httpAuthenticationService);
    }
    
    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return (HttpAuthenticationService)super.getAuthenticationService();
    }
    
    @Override
    public AuthenticationService getAuthenticationService() {
        return this.getAuthenticationService();
    }
}
