package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "MapFilter", category = "Core", elementType = "filter", printObject = true)
public class MapFilter extends AbstractFilter
{
    private final Map map;
    private final boolean isAnd;
    
    protected MapFilter(final Map map, final boolean isAnd, final Filter.Result result, final Filter.Result result2) {
        super(result, result2);
        if (map == null) {
            throw new NullPointerException("key cannot be null");
        }
        this.isAnd = isAnd;
        this.map = map;
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Message message, final Throwable t) {
        if (message instanceof MapMessage) {
            return this.filter(((MapMessage)message).getData()) ? this.onMatch : this.onMismatch;
        }
        return Filter.Result.NEUTRAL;
    }
    
    @Override
    public Filter.Result filter(final LogEvent logEvent) {
        final Message message = logEvent.getMessage();
        if (message instanceof MapMessage) {
            return this.filter(((MapMessage)message).getData()) ? this.onMatch : this.onMismatch;
        }
        return Filter.Result.NEUTRAL;
    }
    
    protected boolean filter(final Map map) {
        for (final Map.Entry<Object, V> entry : this.map.entrySet()) {
            final String s = map.get(entry.getKey());
            if (s != null) {
                ((List)entry.getValue()).contains(s);
            }
            if (!this.isAnd && false) {
                break;
            }
            if (this.isAnd && !false) {
                break;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("isAnd=").append(this.isAnd);
        if (this.map.size() > 0) {
            sb.append(", {");
            for (final Map.Entry<K, List> entry : this.map.entrySet()) {
                if (!false) {
                    sb.append(", ");
                }
                final List<String> list = entry.getValue();
                sb.append((String)entry.getKey()).append("=").append((list.size() > 1) ? list.get(0) : list.toString());
            }
            sb.append("}");
        }
        return sb.toString();
    }
    
    protected boolean isAnd() {
        return this.isAnd;
    }
    
    protected Map getMap() {
        return this.map;
    }
    
    @PluginFactory
    public static MapFilter createFilter(@PluginElement("Pairs") final KeyValuePair[] array, @PluginAttribute("operator") final String s, @PluginAttribute("onMatch") final String s2, @PluginAttribute("onMismatch") final String s3) {
        if (array == null || array.length == 0) {
            MapFilter.LOGGER.error("keys and values must be specified for the MapFilter");
            return null;
        }
        final HashMap<String, ArrayList<String>> hashMap = (HashMap<String, ArrayList<String>>)new HashMap<Object, List<String>>();
        while (0 < array.length) {
            final KeyValuePair keyValuePair = array[0];
            final String key = keyValuePair.getKey();
            if (key == null) {
                MapFilter.LOGGER.error("A null key is not valid in MapFilter");
            }
            else {
                final String value = keyValuePair.getValue();
                if (value == null) {
                    MapFilter.LOGGER.error("A null value for key " + key + " is not allowed in MapFilter");
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
            MapFilter.LOGGER.error("MapFilter is not configured with any valid key value pairs");
            return null;
        }
        return new MapFilter(hashMap, s == null || !s.equalsIgnoreCase("or"), Filter.Result.toResult(s2), Filter.Result.toResult(s3));
    }
}
