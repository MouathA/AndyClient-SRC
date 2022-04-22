package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.yggdrasil.*;

public class ValidateRequest
{
    private String clientToken;
    private String accessToken;
    
    public ValidateRequest(final YggdrasilUserAuthentication yggdrasilUserAuthentication) {
        this.clientToken = yggdrasilUserAuthentication.getAuthenticationService().getClientToken();
        this.accessToken = yggdrasilUserAuthentication.getAuthenticatedToken();
    }
}
