package com.viaversion.viaversion.dump;

import java.util.*;

public class VersionInfo
{
    private final String javaVersion;
    private final String operatingSystem;
    private final int serverProtocol;
    private final Set enabledProtocols;
    private final String platformName;
    private final String platformVersion;
    private final String pluginVersion;
    private final String implementationVersion;
    private final Set subPlatforms;
    
    public VersionInfo(final String javaVersion, final String operatingSystem, final int serverProtocol, final Set enabledProtocols, final String platformName, final String platformVersion, final String pluginVersion, final String implementationVersion, final Set subPlatforms) {
        this.javaVersion = javaVersion;
        this.operatingSystem = operatingSystem;
        this.serverProtocol = serverProtocol;
        this.enabledProtocols = enabledProtocols;
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.pluginVersion = pluginVersion;
        this.implementationVersion = implementationVersion;
        this.subPlatforms = subPlatforms;
    }
    
    public String getJavaVersion() {
        return this.javaVersion;
    }
    
    public String getOperatingSystem() {
        return this.operatingSystem;
    }
    
    public int getServerProtocol() {
        return this.serverProtocol;
    }
    
    public Set getEnabledProtocols() {
        return this.enabledProtocols;
    }
    
    public String getPlatformName() {
        return this.platformName;
    }
    
    public String getPlatformVersion() {
        return this.platformVersion;
    }
    
    public String getPluginVersion() {
        return this.pluginVersion;
    }
    
    public String getImplementationVersion() {
        return this.implementationVersion;
    }
    
    public Set getSubPlatforms() {
        return this.subPlatforms;
    }
}
