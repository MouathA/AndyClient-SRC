package com.mojang.authlib.minecraft;

import com.mojang.authlib.*;

public abstract class HttpMinecraftSessionService extends BaseMinecraftSessionService
{
    protected HttpMinecraftSessionService(final HttpAuthenticationService httpAuthenticationService) {
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
