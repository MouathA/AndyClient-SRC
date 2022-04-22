package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "StructuredDataFilter", category = "Core", elementType = "filter", printObject = true)
public final class StructuredDataFilter extends MapFilter
{
    private StructuredDataFilter(final Map map, final boolean b, final Filter.Result result, final Filter.Result result2) {
        super(map, b, result, result2);
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Message message, final Throwable t) {
        if (message instanceof StructuredDataMessage) {
            return this.filter((StructuredDataMessage)message);
        }
        return Filter.Result.NEUTRAL;
    }
    
    @Override
    public Filter.Result filter(final LogEvent logEvent) {
        final Message message = logEvent.getMessage();
        if (message instanceof StructuredDataMessage) {
            return this.filter((StructuredDataMessage)message);
        }
        return super.filter(logEvent);
    }
    
    protected Filter.Result filter(final StructuredDataMessage structuredDataMessage) {
        for (final Map.Entry<String, V> entry : this.getMap().entrySet()) {
            final String value = this.getValue(structuredDataMessage, entry.getKey());
            if (value != null) {
                ((List)entry.getValue()).contains(value);
            }
            if (!this.isAnd()) {}
            if (this.isAnd()) {
                break;
            }
        }
        return this.onMismatch;
    }
    
    private String getValue(final StructuredDataMessage structuredDataMessage, final String s) {
        if (s.equalsIgnoreCase("id")) {
            return structuredDataMessage.getId().toString();
        }
        if (s.equalsIgnoreCase("id.name")) {
            return structuredDataMessage.getId().getName();
        }
        if (s.equalsIgnoreCase("type")) {
            return structuredDataMessage.getType();
        }
        if (s.equalsIgnoreCase("message")) {
            return structuredDataMessage.getFormattedMessage();
        }
        return structuredDataMessage.getData().get(s);
    }
    
    @PluginFactory
    public static StructuredDataFilter createFilter(@PluginElement("Pairs") final KeyValuePair[] array, @PluginAttribute("operator") final String s, @PluginAttribute("onMatch") final String s2, @PluginAttribute("onMismatch") final String s3) {
        if (array == null || array.length == 0) {
            StructuredDataFilter.LOGGER.error("keys and values must be specified for the StructuredDataFilter");
            return null;
        }
        final HashMap<String, ArrayList<String>> hashMap = (HashMap<String, ArrayList<String>>)new HashMap<Object, List<String>>();
        while (0 < array.length) {
            final KeyValuePair keyValuePair = array[0];
            final String key = keyValuePair.getKey();
            if (key == null) {
                StructuredDataFilter.LOGGER.error("A null key is not valid in MapFilter");
            }
            else {
                final String value = keyValuePair.getValue();
                if (value == null) {
                    StructuredDataFilter.LOGGER.error("A null value for key " + key + " is not allowed in MapFilter");
                }
                else {
                    final List<String> list = hashMap.get(keyValuePair.getKey());
                    if (list != null) {
                        list.add(value);
                    }
                    else {
                        final ArrayList<String> list2 = new ArrayList<String>();
                        list2.add(value);
                        hashMap.put(keyValuePair.getKey(), list2);
                    }
                }
            }
            int n = 0;
            ++n;
        }
        if (hashMap.size() == 0) {
            StructuredDataFilter.LOGGER.error("StructuredDataFilter is not configured with any valid key value pairs");
            return null;
        }
        return new StructuredDataFilter(hashMap, s == null || !s.equalsIgnoreCase("or"), Filter.Result.toResult(s2), Filter.Result.toResult(s3));
    }
}
