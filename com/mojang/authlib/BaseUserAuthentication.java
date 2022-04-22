package com.mojang.authlib;

import org.apache.commons.lang3.*;
import com.mojang.authlib.properties.*;
import com.mojang.util.*;
import java.util.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;

public abstract class BaseUserAuthentication implements UserAuthentication
{
    private static final Logger LOGGER;
    protected static final String STORAGE_KEY_PROFILE_NAME = "displayName";
    protected static final String STORAGE_KEY_PROFILE_ID = "uuid";
    protected static final String STORAGE_KEY_PROFILE_PROPERTIES = "profileProperties";
    protected static final String STORAGE_KEY_USER_NAME = "username";
    protected static final String STORAGE_KEY_USER_ID = "userid";
    protected static final String STORAGE_KEY_USER_PROPERTIES = "userProperties";
    private final AuthenticationService authenticationService;
    private final PropertyMap userProperties;
    private String userid;
    private String username;
    private String password;
    private GameProfile selectedProfile;
    private UserType userType;
    
    protected BaseUserAuthentication(final AuthenticationService authenticationService) {
        this.userProperties = new PropertyMap();
        Validate.notNull(authenticationService);
        this.authenticationService = authenticationService;
    }
    
    @Override
    public boolean canLogIn() {
        return !this.canPlayOnline() && StringUtils.isNotBlank(this.getUsername()) && StringUtils.isNotBlank(this.getPassword());
    }
    
    @Override
    public void logOut() {
        this.password = null;
        this.userid = null;
        this.setSelectedProfile(null);
        this.getModifiableUserProperties().clear();
        this.setUserType(null);
    }
    
    @Override
    public void setUsername(final String username) {
        if (this != null && this.canPlayOnline()) {
            throw new IllegalStateException("Cannot change username whilst logged in & online");
        }
        this.username = username;
    }
    
    @Override
    public void setPassword(final String password) {
        if (this != null && this.canPlayOnline() && StringUtils.isNotBlank(password)) {
            throw new IllegalStateException("Cannot set password whilst logged in & online");
        }
        this.password = password;
    }
    
    protected String getUsername() {
        return this.username;
    }
    
    protected String getPassword() {
        return this.password;
    }
    
    @Override
    public void loadFromStorage(final Map map) {
        this.logOut();
        this.setUsername(String.valueOf(map.get("username")));
        if (map.containsKey("userid")) {
            this.userid = String.valueOf(map.get("userid"));
        }
        else {
            this.userid = this.username;
        }
        if (map.containsKey("userProperties")) {
            for (final Map<K, String> map2 : map.get("userProperties")) {
                final String s = map2.get("name");
                final String s2 = map2.get("value");
                final String s3 = map2.get("signature");
                if (s3 == null) {
                    this.getModifiableUserProperties().put(s, new Property(s, s2));
                }
                else {
                    this.getModifiableUserProperties().put(s, new Property(s, s2, s3));
                }
            }
        }
        if (map.containsKey("displayName") && map.containsKey("uuid")) {
            final GameProfile selectedProfile = new GameProfile(UUIDTypeAdapter.fromString(String.valueOf(map.get("uuid"))), String.valueOf(map.get("displayName")));
            if (map.containsKey("profileProperties")) {
                for (final Map<K, String> map3 : map.get("profileProperties")) {
                    final String s4 = map3.get("name");
                    final String s5 = map3.get("value");
                    final String s6 = map3.get("signature");
                    if (s6 == null) {
                        selectedProfile.getProperties().put(s4, new Property(s4, s5));
                    }
                    else {
                        selectedProfile.getProperties().put(s4, new Property(s4, s5, s6));
                    }
                }
            }
            this.setSelectedProfile(selectedProfile);
        }
    }
    
    @Override
    public Map saveForStorage() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        if (this.getUsername() != null) {
            hashMap.put("username", this.getUsername());
        }
        if (this.getUserID() != null) {
            hashMap.put("userid", this.getUserID());
        }
        else if (this.getUsername() != null) {
            hashMap.put("username", this.getUsername());
        }
        if (!this.getUserProperties().isEmpty()) {
            final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            for (final Property property : this.getUserProperties().values()) {
                final HashMap<String, String> hashMap2 = new HashMap<String, String>();
                hashMap2.put("name", property.getName());
                hashMap2.put("value", property.getValue());
                hashMap2.put("signature", property.getSignature());
                list.add(hashMap2);
            }
            hashMap.put("userProperties", (String)list);
        }
        final GameProfile selectedProfile = this.getSelectedProfile();
        if (selectedProfile != null) {
            hashMap.put("displayName", selectedProfile.getName());
            hashMap.put("uuid", (String)selectedProfile.getId());
            final ArrayList list2 = new ArrayList<HashMap<String, String>>();
            for (final Property property2 : selectedProfile.getProperties().values()) {
                final HashMap<String, String> hashMap3 = new HashMap<String, String>();
                hashMap3.put("name", property2.getName());
                hashMap3.put("value", property2.getValue());
                hashMap3.put("signature", property2.getSignature());
                list2.add(hashMap3);
            }
            if (!list2.isEmpty()) {
                hashMap.put("profileProperties", (String)list2);
            }
        }
        return hashMap;
    }
    
    protected void setSelectedProfile(final GameProfile selectedProfile) {
        this.selectedProfile = selectedProfile;
    }
    
    @Override
    public GameProfile getSelectedProfile() {
        return this.selectedProfile;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append("{");
        if (this != null) {
            sb.append("Logged in as ");
            sb.append(this.getUsername());
            if (this.getSelectedProfile() != null) {
                sb.append(" / ");
                sb.append(this.getSelectedProfile());
                sb.append(" - ");
                if (this.canPlayOnline()) {
                    sb.append("Online");
                }
                else {
                    sb.append("Offline");
                }
            }
        }
        else {
            sb.append("Not logged in");
        }
        sb.append("}");
        return sb.toString();
    }
    
    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }
    
    @Override
    public String getUserID() {
        return this.userid;
    }
    
    @Override
    public PropertyMap getUserProperties() {
        if (this != null) {
            final PropertyMap propertyMap = new PropertyMap();
            propertyMap.putAll(this.getModifiableUserProperties());
            return propertyMap;
        }
        return new PropertyMap();
    }
    
    protected PropertyMap getModifiableUserProperties() {
        return this.userProperties;
    }
    
    @Override
    public UserType getUserType() {
        if (this != null) {
            return (this.userType == null) ? UserType.LEGACY : this.userType;
        }
        return null;
    }
    
    protected void setUserType(final UserType userType) {
        this.userType = userType;
    }
    
    protected void setUserid(final String userid) {
        this.userid = userid;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
