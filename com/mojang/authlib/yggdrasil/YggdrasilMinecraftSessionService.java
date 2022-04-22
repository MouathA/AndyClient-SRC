package com.mojang.authlib.yggdrasil;

import com.google.gson.*;
import com.mojang.util.*;
import java.lang.reflect.*;
import java.util.concurrent.*;
import com.google.common.cache.*;
import java.security.*;
import org.apache.commons.io.*;
import java.security.spec.*;
import com.mojang.authlib.yggdrasil.request.*;
import com.mojang.authlib.exceptions.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;
import org.apache.commons.codec.binary.*;
import org.apache.commons.codec.*;
import com.mojang.authlib.minecraft.*;
import java.util.*;
import com.mojang.authlib.yggdrasil.response.*;
import java.net.*;
import com.mojang.authlib.*;
import org.apache.logging.log4j.*;

public class YggdrasilMinecraftSessionService extends HttpMinecraftSessionService
{
    private static final Logger LOGGER;
    private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL JOIN_URL;
    private static final URL CHECK_URL;
    private final PublicKey publicKey;
    private final Gson gson;
    private final LoadingCache insecureProfiles;
    
    protected YggdrasilMinecraftSessionService(final YggdrasilAuthenticationService yggdrasilAuthenticationService) {
        super(yggdrasilAuthenticationService);
        this.gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
        this.insecureProfiles = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build(new CacheLoader() {
            final YggdrasilMinecraftSessionService this$0;
            
            public GameProfile load(final GameProfile gameProfile) throws Exception {
                return this.this$0.fillGameProfile(gameProfile, false);
            }
            
            @Override
            public Object load(final Object o) throws Exception {
                return this.load((GameProfile)o);
            }
        });
        this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(IOUtils.toByteArray(YggdrasilMinecraftSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der"))));
    }
    
    @Override
    public void joinServer(final GameProfile gameProfile, final String accessToken, final String serverId) throws AuthenticationException {
        final JoinMinecraftServerRequest joinMinecraftServerRequest = new JoinMinecraftServerRequest();
        joinMinecraftServerRequest.accessToken = accessToken;
        joinMinecraftServerRequest.selectedProfile = gameProfile.getId();
        joinMinecraftServerRequest.serverId = serverId;
        this.getAuthenticationService().makeRequest(YggdrasilMinecraftSessionService.JOIN_URL, joinMinecraftServerRequest, Response.class);
    }
    
    @Override
    public GameProfile hasJoinedServer(final GameProfile gameProfile, final String s) throws AuthenticationUnavailableException {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("username", gameProfile.getName());
        hashMap.put("serverId", s);
        final HasJoinedMinecraftServerResponse hasJoinedMinecraftServerResponse = (HasJoinedMinecraftServerResponse)this.getAuthenticationService().makeRequest(HttpAuthenticationService.concatenateURL(YggdrasilMinecraftSessionService.CHECK_URL, HttpAuthenticationService.buildQuery(hashMap)), null, HasJoinedMinecraftServerResponse.class);
        if (hasJoinedMinecraftServerResponse != null && hasJoinedMinecraftServerResponse.getId() != null) {
            final GameProfile gameProfile2 = new GameProfile(hasJoinedMinecraftServerResponse.getId(), gameProfile.getName());
            if (hasJoinedMinecraftServerResponse.getProperties() != null) {
                gameProfile2.getProperties().putAll(hasJoinedMinecraftServerResponse.getProperties());
            }
            return gameProfile2;
        }
        return null;
    }
    
    @Override
    public Map getTextures(final GameProfile gameProfile, final boolean b) {
        final Property property = (Property)Iterables.getFirst(gameProfile.getProperties().get("textures"), null);
        if (property == null) {
            return new HashMap();
        }
        if (b) {
            if (!property.hasSignature()) {
                YggdrasilMinecraftSessionService.LOGGER.error("Signature is missing from textures payload");
                throw new InsecureTextureException("Signature is missing from textures payload");
            }
            if (!property.isSignatureValid(this.publicKey)) {
                YggdrasilMinecraftSessionService.LOGGER.error("Textures payload has been tampered with (signature invalid)");
                throw new InsecureTextureException("Textures payload has been tampered with (signature invalid)");
            }
        }
        final MinecraftTexturesPayload minecraftTexturesPayload = (MinecraftTexturesPayload)this.gson.fromJson(new String(Base64.decodeBase64(property.getValue()), Charsets.UTF_8), MinecraftTexturesPayload.class);
        if (minecraftTexturesPayload.getTextures() == null) {
            return new HashMap();
        }
        final Iterator<Map.Entry<K, MinecraftProfileTexture>> iterator = minecraftTexturesPayload.getTextures().entrySet().iterator();
        while (iterator.hasNext()) {
            if (!isWhitelistedDomain(iterator.next().getValue().getUrl())) {
                YggdrasilMinecraftSessionService.LOGGER.error("Textures payload has been tampered with (non-whitelisted domain)");
                return new HashMap();
            }
        }
        return minecraftTexturesPayload.getTextures();
    }
    
    @Override
    public GameProfile fillProfileProperties(final GameProfile gameProfile, final boolean b) {
        if (gameProfile.getId() == null) {
            return gameProfile;
        }
        if (!b) {
            return (GameProfile)this.insecureProfiles.getUnchecked(gameProfile);
        }
        return this.fillGameProfile(gameProfile, true);
    }
    
    protected GameProfile fillGameProfile(final GameProfile gameProfile, final boolean b) {
        final MinecraftProfilePropertiesResponse minecraftProfilePropertiesResponse = (MinecraftProfilePropertiesResponse)this.getAuthenticationService().makeRequest(HttpAuthenticationService.concatenateURL(HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUIDTypeAdapter.fromUUID(gameProfile.getId())), "unsigned=" + !b), null, MinecraftProfilePropertiesResponse.class);
        if (minecraftProfilePropertiesResponse == null) {
            YggdrasilMinecraftSessionService.LOGGER.debug("Couldn't fetch profile properties for " + gameProfile + " as the profile does not exist");
            return gameProfile;
        }
        final GameProfile gameProfile2 = new GameProfile(minecraftProfilePropertiesResponse.getId(), minecraftProfilePropertiesResponse.getName());
        gameProfile2.getProperties().putAll(minecraftProfilePropertiesResponse.getProperties());
        gameProfile.getProperties().putAll(minecraftProfilePropertiesResponse.getProperties());
        YggdrasilMinecraftSessionService.LOGGER.debug("Successfully fetched profile properties for " + gameProfile);
        return gameProfile2;
    }
    
    @Override
    public YggdrasilAuthenticationService getAuthenticationService() {
        return (YggdrasilAuthenticationService)super.getAuthenticationService();
    }
    
    private static boolean isWhitelistedDomain(final String s) {
        final String host = new URI(s).getHost();
        while (0 < YggdrasilMinecraftSessionService.WHITELISTED_DOMAINS.length) {
            if (host.endsWith(YggdrasilMinecraftSessionService.WHITELISTED_DOMAINS[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
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
        YggdrasilMinecraftSessionService.WHITELISTED_DOMAINS = new String[] { ".minecraft.net", ".mojang.com" };
        LOGGER = LogManager.getLogger();
        JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
        CHECK_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/hasJoined");
    }
}
