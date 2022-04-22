package com.mojang.authlib;

import java.util.*;
import com.mojang.authlib.properties.*;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.builder.*;

public class GameProfile
{
    private final UUID id;
    private final String name;
    private final PropertyMap properties;
    private boolean legacy;
    
    public GameProfile(final UUID id, final String name) {
        this.properties = new PropertyMap();
        if (id == null && StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name and ID cannot both be blank");
        }
        this.id = id;
        this.name = name;
    }
    
    public UUID getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public PropertyMap getProperties() {
        return this.properties;
    }
    
    public boolean isComplete() {
        return this.id != null && StringUtils.isNotBlank(this.getName());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final GameProfile gameProfile = (GameProfile)o;
        Label_0062: {
            if (this.id != null) {
                if (this.id.equals(gameProfile.id)) {
                    break Label_0062;
                }
            }
            else if (gameProfile.id == null) {
                break Label_0062;
            }
            return false;
        }
        if (this.name != null) {
            if (this.name.equals(gameProfile.name)) {
                return true;
            }
        }
        else if (gameProfile.name == null) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * ((this.id != null) ? this.id.hashCode() : 0) + ((this.name != null) ? this.name.hashCode() : 0);
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("properties", this.properties).append("legacy", this.legacy).toString();
    }
    
    public boolean isLegacy() {
        return this.legacy;
    }
}
