package com.viaversion.viaversion.api.configuration;

import java.util.*;

public interface ConfigurationProvider
{
    void set(final String p0, final Object p1);
    
    void saveConfig();
    
    void reloadConfig();
    
    Map getValues();
}
