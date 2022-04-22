package org.yaml.snakeyaml.util;

public class PlatformFeatureDetector
{
    private Boolean isRunningOnAndroid;
    
    public PlatformFeatureDetector() {
        this.isRunningOnAndroid = null;
    }
    
    public boolean isRunningOnAndroid() {
        if (this.isRunningOnAndroid == null) {
            final String property = System.getProperty("java.runtime.name");
            this.isRunningOnAndroid = (property != null && property.startsWith("Android Runtime"));
        }
        return this.isRunningOnAndroid;
    }
}
