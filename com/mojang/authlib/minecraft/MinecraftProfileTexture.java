package com.mojang.authlib.minecraft;

import java.util.*;
import javax.annotation.*;
import org.apache.commons.io.*;
import org.apache.commons.lang3.builder.*;

public class MinecraftProfileTexture
{
    private final String url;
    private final Map metadata;
    
    public MinecraftProfileTexture(final String url, final Map metadata) {
        this.url = url;
        this.metadata = metadata;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    @Nullable
    public String getMetadata(final String s) {
        if (this.metadata == null) {
            return null;
        }
        return this.metadata.get(s);
    }
    
    public String getHash() {
        return FilenameUtils.getBaseName(this.url);
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("url", this.url).append("hash", this.getHash()).toString();
    }
    
    public enum Type
    {
        SKIN("SKIN", 0), 
        CAPE("CAPE", 1);
        
        private static final Type[] $VALUES;
        
        private Type(final String s, final int n) {
        }
        
        static {
            $VALUES = new Type[] { Type.SKIN, Type.CAPE };
        }
    }
}
