package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.*;
import com.mojang.authlib.yggdrasil.*;

public class AuthenticationRequest
{
    private Agent agent;
    private String username;
    private String password;
    private String clientToken;
    private boolean requestUser;
    
    public AuthenticationRequest(final YggdrasilUserAuthentication yggdrasilUserAuthentication, final String username, final String password) {
        this.requestUser = true;
        this.agent = yggdrasilUserAuthentication.getAgent();
        this.username = username;
        this.clientToken = yggdrasilUserAuthentication.getAuthenticationService().getClientToken();
        this.password = password;
    }
}
