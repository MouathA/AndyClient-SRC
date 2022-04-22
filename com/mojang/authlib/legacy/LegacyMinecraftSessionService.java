package com.mojang.authlib.legacy;

import com.mojang.authlib.minecraft.*;
import java.net.*;
import java.util.*;
import com.mojang.authlib.exceptions.*;
import com.mojang.authlib.*;

public class LegacyMinecraftSessionService extends HttpMinecraftSessionService
{
    private static final String BASE_URL = "http://session.minecraft.net/game/";
    private static final URL JOIN_URL;
    private static final URL CHECK_URL;
    
    protected LegacyMinecraftSessionService(final LegacyAuthenticationService legacyAuthenticationService) {
        super(legacyAuthenticationService);
    }
    
    @Override
    public void joinServer(final GameProfile gameProfile, final String s, final String s2) throws AuthenticationException {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("user", gameProfile.getName());
        hashMap.put("sessionId", s);
        hashMap.put("serverId", s2);
        final String performGetRequest = this.getAuthenticationService().performGetRequest(HttpAuthenticationService.concatenateURL(LegacyMinecraftSessionService.JOIN_URL, HttpAuthenticationService.buildQuery(hashMap)));
        if (!performGetRequest.equals("OK")) {
            throw new AuthenticationException(performGetRequest);
        }
    }
    
    @Override
    public GameProfile hasJoinedServer(final GameProfile gameProfile, final String s) throws AuthenticationUnavailableException {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("user", gameProfile.getName());
        hashMap.put("serverId", s);
        return this.getAuthenticationService().performGetRequest(HttpAuthenticationService.concatenateURL(LegacyMinecraftSessionService.CHECK_URL, HttpAuthenticationService.buildQuery(hashMap))).equals("YES") ? gameProfile : null;
    }
    
    @Override
    public Map getTextures(final GameProfile gameProfile, final boolean b) {
        return new HashMap();
    }
    
    @Override
    public GameProfile fillProfileProperties(final GameProfile gameProfile, final boolean b) {
        return gameProfile;
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
        JOIN_URL = HttpAuthenticationService.constantURL("http://session.minecraft.net/game/joinserver.jsp");
        CHECK_URL = HttpAuthenticationService.constantURL("http://session.minecraft.net/game/checkserver.jsp");
    }
}
