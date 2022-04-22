package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.*;
import com.mojang.authlib.yggdrasil.*;

public class RefreshRequest
{
    private String clientToken;
    private String accessToken;
    private GameProfile selectedProfile;
    private boolean requestUser;
    
    public RefreshRequest(final YggdrasilUserAuthentication yggdrasilUserAuthentication) {
        this(yggdrasilUserAuthentication, null);
    }
    
    public RefreshRequest(final YggdrasilUserAuthentication yggdrasilUserAuthentication, final GameProfile selectedProfile) {
        this.requestUser = true;
        this.clientToken = yggdrasilUserAuthentication.getAuthenticationService().getClientToken();
        this.accessToken = yggdrasilUserAuthentication.getAuthenticatedToken();
        this.selectedProfile = selectedProfile;
    }
}
