package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.config.plugins.*;
import java.io.*;

@Plugin(name = "JSONConfigurationFactory", category = "ConfigurationFactory")
@Order(6)
public class JSONConfigurationFactory extends ConfigurationFactory
{
    private static String[] dependencies;
    private final File configFile;
    private boolean isActive;
    
    public JSONConfigurationFactory() {
        this.configFile = null;
        final String[] dependencies = JSONConfigurationFactory.dependencies;
        while (0 < dependencies.length) {
            Class.forName(dependencies[0]);
            int n = 0;
            ++n;
        }
        this.isActive = true;
    }
    
    @Override
    protected boolean isActive() {
        return this.isActive;
    }
    
    @Override
    public Configuration getConfiguration(final ConfigurationSource configurationSource) {
        if (!this.isActive) {
            return null;
        }
        return new JSONConfiguration(configurationSource);
    }
    
    public String[] getSupportedTypes() {
        return JSONConfigurationFactory.SUFFIXES;
    }
    
    static {
        JSONConfigurationFactory.SUFFIXES = new String[] { ".json", ".jsn" };
        JSONConfigurationFactory.dependencies = new String[] { "com.fasterxml.jackson.databind.ObjectMapper", "com.fasterxml.jackson.databind.JsonNode", "com.fasterxml.jackson.core.JsonParser" };
    }
}
