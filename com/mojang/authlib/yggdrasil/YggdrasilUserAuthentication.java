package com.mojang.authlib.yggdrasil;

import java.net.*;
import com.mojang.authlib.exceptions.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import com.mojang.authlib.yggdrasil.request.*;
import com.mojang.authlib.yggdrasil.response.*;
import java.util.*;
import com.mojang.authlib.*;
import org.apache.logging.log4j.*;

public class YggdrasilUserAuthentication extends HttpUserAuthentication
{
    private static final Logger LOGGER;
    private static final String BASE_URL = "https://authserver.mojang.com/";
    private static final URL ROUTE_AUTHENTICATE;
    private static final URL ROUTE_REFRESH;
    private static final URL ROUTE_VALIDATE;
    private static final URL ROUTE_INVALIDATE;
    private static final URL ROUTE_SIGNOUT;
    private static final String STORAGE_KEY_ACCESS_TOKEN = "accessToken";
    private final Agent agent;
    private GameProfile[] profiles;
    private String accessToken;
    private boolean isOnline;
    
    public YggdrasilUserAuthentication(final YggdrasilAuthenticationService yggdrasilAuthenticationService, final Agent agent) {
        super(yggdrasilAuthenticationService);
        this.agent = agent;
    }
    
    @Override
    public boolean canLogIn() {
        return !this.canPlayOnline() && StringUtils.isNotBlank(this.getUsername()) && (StringUtils.isNotBlank(this.getPassword()) || StringUtils.isNotBlank(this.getAuthenticatedToken()));
    }
    
    @Override
    public void logIn() throws AuthenticationException {
        if (StringUtils.isBlank(this.getUsername())) {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isNotBlank(this.getAuthenticatedToken())) {
            this.logInWithToken();
        }
        else {
            if (!StringUtils.isNotBlank(this.getPassword())) {
                throw new InvalidCredentialsException("Invalid password");
            }
            this.logInWithPassword();
        }
    }
    
    protected void logInWithPassword() throws AuthenticationException {
        if (StringUtils.isBlank(this.getUsername())) {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isBlank(this.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        YggdrasilUserAuthentication.LOGGER.info("Logging in with username & password");
        final AuthenticationResponse authenticationResponse = (AuthenticationResponse)this.getAuthenticationService().makeRequest(YggdrasilUserAuthentication.ROUTE_AUTHENTICATE, new AuthenticationRequest(this, this.getUsername(), this.getPassword()), AuthenticationResponse.class);
        if (!authenticationResponse.getClientToken().equals(this.getAuthenticationService().getClientToken())) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        if (authenticationResponse.getSelectedProfile() != null) {
            this.setUserType(authenticationResponse.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        else if (ArrayUtils.isNotEmpty(authenticationResponse.getAvailableProfiles())) {
            this.setUserType(authenticationResponse.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        final User user = authenticationResponse.getUser();
        if (user != null && user.getId() != null) {
            this.setUserid(user.getId());
        }
        else {
            this.setUserid(this.getUsername());
        }
        this.isOnline = true;
        this.accessToken = authenticationResponse.getAccessToken();
        this.profiles = authenticationResponse.getAvailableProfiles();
        this.setSelectedProfile(authenticationResponse.getSelectedProfile());
        this.getModifiableUserProperties().clear();
        this.updateUserProperties(user);
    }
    
    protected void updateUserProperties(final User user) {
        if (user == null) {
            return;
        }
        if (user.getProperties() != null) {
            this.getModifiableUserProperties().putAll(user.getProperties());
        }
    }
    
    protected void logInWithToken() throws AuthenticationException {
        if (StringUtils.isBlank(this.getUserID())) {
            if (!StringUtils.isBlank(this.getUsername())) {
                throw new InvalidCredentialsException("Invalid uuid & username");
            }
            this.setUserid(this.getUsername());
        }
        if (StringUtils.isBlank(this.getAuthenticatedToken())) {
            throw new InvalidCredentialsException("Invalid access token");
        }
        YggdrasilUserAuthentication.LOGGER.info("Logging in with access token");
        if (this.checkTokenValidity()) {
            YggdrasilUserAuthentication.LOGGER.debug("Skipping refresh call as we're safely logged in.");
            this.isOnline = true;
            return;
        }
        final RefreshResponse refreshResponse = (RefreshResponse)this.getAuthenticationService().makeRequest(YggdrasilUserAuthentication.ROUTE_REFRESH, new RefreshRequest(this), RefreshResponse.class);
        if (!refreshResponse.getClientToken().equals(this.getAuthenticationService().getClientToken())) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        if (refreshResponse.getSelectedProfile() != null) {
            this.setUserType(refreshResponse.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        else if (ArrayUtils.isNotEmpty(refreshResponse.getAvailableProfiles())) {
            this.setUserType(refreshResponse.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        if (refreshResponse.getUser() != null && refreshResponse.getUser().getId() != null) {
            this.setUserid(refreshResponse.getUser().getId());
        }
        else {
            this.setUserid(this.getUsername());
        }
        this.isOnline = true;
        this.accessToken = refreshResponse.getAccessToken();
        this.profiles = refreshResponse.getAvailableProfiles();
        this.setSelectedProfile(refreshResponse.getSelectedProfile());
        this.getModifiableUserProperties().clear();
        this.updateUserProperties(refreshResponse.getUser());
    }
    
    protected boolean checkTokenValidity() throws AuthenticationException {
        this.getAuthenticationService().makeRequest(YggdrasilUserAuthentication.ROUTE_VALIDATE, new ValidateRequest(this), Response.class);
        return true;
    }
    
    @Override
    public void logOut() {
        super.logOut();
        this.accessToken = null;
        this.profiles = null;
        this.isOnline = false;
    }
    
    @Override
    public GameProfile[] getAvailableProfiles() {
        return this.profiles;
    }
    
    @Override
    public boolean isLoggedIn() {
        return StringUtils.isNotBlank(this.accessToken);
    }
    
    @Override
    public boolean canPlayOnline() {
        return this.isLoggedIn() && this.getSelectedProfile() != null && this.isOnline;
    }
    
    @Override
    public void selectGameProfile(final GameProfile gameProfile) throws AuthenticationException {
        if (!this.isLoggedIn()) {
            throw new AuthenticationException("Cannot change game profile whilst not logged in");
        }
        if (this.getSelectedProfile() != null) {
            throw new AuthenticationException("Cannot change game profile. You must log out and back in.");
        }
        if (gameProfile == null || !ArrayUtils.contains(this.profiles, gameProfile)) {
            throw new IllegalArgumentException("Invalid profile '" + gameProfile + "'");
        }
        final RefreshResponse refreshResponse = (RefreshResponse)this.getAuthenticationService().makeRequest(YggdrasilUserAuthentication.ROUTE_REFRESH, new RefreshRequest(this, gameProfile), RefreshResponse.class);
        if (!refreshResponse.getClientToken().equals(this.getAuthenticationService().getClientToken())) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        this.isOnline = true;
        this.accessToken = refreshResponse.getAccessToken();
        this.setSelectedProfile(refreshResponse.getSelectedProfile());
    }
    
    @Override
    public void loadFromStorage(final Map map) {
        super.loadFromStorage(map);
        this.accessToken = String.valueOf(map.get("accessToken"));
    }
    
    @Override
    public Map saveForStorage() {
        final Map saveForStorage = super.saveForStorage();
        if (StringUtils.isNotBlank(this.getAuthenticatedToken())) {
            saveForStorage.put("accessToken", this.getAuthenticatedToken());
        }
        return saveForStorage;
    }
    
    @Deprecated
    public String getSessionToken() {
        if (this.isLoggedIn() && this.getSelectedProfile() != null && this.canPlayOnline()) {
            return String.format("token:%s:%s", this.getAuthenticatedToken(), this.getSelectedProfile().getId());
        }
        return null;
    }
    
    @Override
    public String getAuthenticatedToken() {
        return this.accessToken;
    }
    
    public Agent getAgent() {
        return this.agent;
    }
    
    @Override
    public String toString() {
        return "YggdrasilAuthenticationService{agent=" + this.agent + ", profiles=" + Arrays.toString(this.profiles) + ", selectedProfile=" + this.getSelectedProfile() + ", username='" + this.getUsername() + '\'' + ", isLoggedIn=" + this.isLoggedIn() + ", userType=" + this.getUserType() + ", canPlayOnline=" + this.canPlayOnline() + ", accessToken='" + this.accessToken + '\'' + ", clientToken='" + this.getAuthenticationService().getClientToken() + '\'' + '}';
    }
    
    @Override
    public YggdrasilAuthenticationService getAuthenticationService() {
        return (YggdrasilAuthenticationService)super.getAuthenticationService();
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
        LOGGER = LogManager.getLogger();
        ROUTE_AUTHENTICATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/authenticate");
        ROUTE_REFRESH = HttpAuthenticationService.constantURL("https://authserver.mojang.com/refresh");
        ROUTE_VALIDATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/validate");
        ROUTE_INVALIDATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/invalidate");
        ROUTE_SIGNOUT = HttpAuthenticationService.constantURL("https://authserver.mojang.com/signout");
    }
}
