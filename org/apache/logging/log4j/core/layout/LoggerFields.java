package org.apache.logging.log4j.core.layout;

import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.message.*;

@Plugin(name = "LoggerFields", category = "Core", printObject = true)
public final class LoggerFields
{
    private final Map map;
    private final String sdId;
    private final String enterpriseId;
    private final boolean discardIfAllFieldsAreEmpty;
    
    private LoggerFields(final Map map, final String sdId, final String enterpriseId, final boolean discardIfAllFieldsAreEmpty) {
        this.sdId = sdId;
        this.enterpriseId = enterpriseId;
        this.map = Collections.unmodifiableMap((Map<?, ?>)map);
        this.discardIfAllFieldsAreEmpty = discardIfAllFieldsAreEmpty;
    }
    
    public Map getMap() {
        return this.map;
    }
    
    @Override
    public String toString() {
        return this.map.toString();
    }
    
    @PluginFactory
    public static LoggerFields createLoggerFields(@PluginElement("LoggerFields") final KeyValuePair[] array, @PluginAttribute("sdId") final String s, @PluginAttribute("enterpriseId") final String s2, @PluginAttribute("discardIfAllFieldsAreEmpty") final String s3) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        while (0 < array.length) {
            final KeyValuePair keyValuePair = array[0];
            hashMap.put(keyValuePair.getKey(), keyValuePair.getValue());
            int n = 0;
            ++n;
        }
        return new LoggerFields(hashMap, s, s2, Booleans.parseBoolean(s3, false));
    }
    
    public StructuredDataId getSdId() {
        if (this.enterpriseId == null || this.sdId == null) {
            return null;
        }
        return new StructuredDataId(this.sdId, Integer.parseInt(this.enterpriseId), null, null);
    }
    
    public boolean getDiscardIfAllFieldsAreEmpty() {
        return this.discardIfAllFieldsAreEmpty;
    }
}
