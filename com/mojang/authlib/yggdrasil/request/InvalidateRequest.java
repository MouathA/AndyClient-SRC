package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.yggdrasil.*;

public class InvalidateRequest
{
    private String accessToken;
    private String clientToken;
    
    public InvalidateRequest(final YggdrasilUserAuthentication yggdrasilUserAuthentication) {
        this.accessToken = yggdrasilUserAuthentication.getAuthenticatedToken();
        this.clientToken = yggdrasilUserAuthentication.getAuthenticationService().getClientToken();
    }
}
