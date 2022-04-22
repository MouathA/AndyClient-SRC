package com.viaversion.viaversion.dump;

import java.util.*;

public class PluginInfo
{
    private final boolean enabled;
    private final String name;
    private final String version;
    private final String main;
    private final List authors;
    
    public PluginInfo(final boolean enabled, final String name, final String version, final String main, final List authors) {
        this.enabled = enabled;
        this.name = name;
        this.version = version;
        this.main = main;
        this.authors = authors;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getMain() {
        return this.main;
    }
    
    public List getAuthors() {
        return this.authors;
    }
}
