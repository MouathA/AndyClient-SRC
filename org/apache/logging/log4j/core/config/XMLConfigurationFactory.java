package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "XMLConfigurationFactory", category = "ConfigurationFactory")
@Order(5)
public class XMLConfigurationFactory extends ConfigurationFactory
{
    @Override
    public Configuration getConfiguration(final ConfigurationSource configurationSource) {
        return new XMLConfiguration(configurationSource);
    }
    
    public String[] getSupportedTypes() {
        return XMLConfigurationFactory.SUFFIXES;
    }
    
    static {
        XMLConfigurationFactory.SUFFIXES = new String[] { ".xml", "*" };
    }
}
