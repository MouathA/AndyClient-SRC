package com.mojang.authlib.legacy;

import java.net.*;
import org.apache.commons.lang3.*;
import java.util.*;
import com.mojang.authlib.exceptions.*;
import com.mojang.util.*;
import com.mojang.authlib.*;

public class LegacyUserAuthentication extends HttpUserAuthentication
{
    private static final URL AUTHENTICATION_URL;
    private static final int AUTHENTICATION_VERSION = 14;
    private static final int RESPONSE_PART_PROFILE_NAME = 2;
    private static final int RESPONSE_PART_SESSION_TOKEN = 3;
    private static final int RESPONSE_PART_PROFILE_ID = 4;
    private String sessionToken;
    
    protected LegacyUserAuthentication(final LegacyAuthenticationService legacyAuthenticationService) {
        super(legacyAuthenticationService);
    }
    
    @Override
    public void logIn() throws AuthenticationException {
        if (StringUtils.isBlank(this.getUsername())) {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isBlank(this.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("user", this.getUsername());
        hashMap.put("password", this.getPassword());
        hashMap.put("version", (String)14);
        final String trim = this.getAuthenticationService().performPostRequest(LegacyUserAuthentication.AUTHENTICATION_URL, HttpAuthenticationService.buildQuery(hashMap), "application/x-www-form-urlencoded").trim();
        final String[] split = trim.split(":");
        if (split.length != 5) {
            throw new InvalidCredentialsException(trim);
        }
        final String s = split[4];
        final String s2 = split[2];
        final String sessionToken = split[3];
        if (StringUtils.isBlank(s) || StringUtils.isBlank(s2) || StringUtils.isBlank(sessionToken)) {
            throw new AuthenticationException("Unknown response from authentication server: " + trim);
        }
        this.setSelectedProfile(new GameProfile(UUIDTypeAdapter.fromString(s), s2));
        this.sessionToken = sessionToken;
        this.setUserType(UserType.LEGACY);
    }
    
    @Override
    public void logOut() {
        super.logOut();
        this.sessionToken = null;
    }
    
    @Override
    public boolean canPlayOnline() {
        return this.isLoggedIn() && this.getSelectedProfile() != null && this.getAuthenticatedToken() != null;
    }
    
    @Override
    public GameProfile[] getAvailableProfiles() {
        if (this.getSelectedProfile() != null) {
            return new GameProfile[] { this.getSelectedProfile() };
        }
        return new GameProfile[0];
    }
    
    @Override
    public void selectGameProfile(final GameProfile gameProfile) throws AuthenticationException {
        throw new UnsupportedOperationException("Game profiles cannot be changed in the legacy authentication service");
    }
    
    @Override
    public String getAuthenticatedToken() {
        return this.sessionToken;
    }
    
    @Override
    public String getUserID() {
        return this.getUsername();
    }
    
    @Override
    public LegacyAuthenticationService getAuthenticationService() {
        return (LegacyAuthenticationService)super.getAuthenticationService();
    }
    
    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return this.getAuthenticationService();
    }
    
    @Override
    public AuthenticationService getAuthenticationService() {
        return this.getAuthenticationService();
    }
    
    static {
        AUTHENTICATION_URL = HttpAuthenticationService.constantURL("https://login.minecraft.net");
    }
}
