package org.apache.logging.log4j.core.config.plugins;

import org.apache.logging.log4j.core.config.*;
import java.util.*;
import org.apache.logging.log4j.core.lookup.*;

@Plugin(name = "properties", category = "Core", printObject = true)
public final class PropertiesPlugin
{
    private PropertiesPlugin() {
    }
    
    @PluginFactory
    public static StrLookup configureSubstitutor(@PluginElement("Properties") final Property[] array, @PluginConfiguration final Configuration configuration) {
        if (array == null) {
            return new Interpolator(null);
        }
        final HashMap<String, String> hashMap = new HashMap<String, String>(configuration.getProperties());
        while (0 < array.length) {
            final Property property = array[0];
            hashMap.put(property.getName(), property.getValue());
            int n = 0;
            ++n;
        }
        return new Interpolator(new MapLookup(hashMap));
    }
}
