package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "ThreadContextMapFilter", category = "Core", elementType = "filter", printObject = true)
public class ThreadContextMapFilter extends MapFilter
{
    private final String key;
    private final String value;
    private final boolean useMap;
    
    public ThreadContextMapFilter(final Map map, final boolean b, final Filter.Result result, final Filter.Result result2) {
        super(map, b, result, result2);
        if (map.size() == 1) {
            final Map.Entry<K, List> entry = map.entrySet().iterator().next();
            if (entry.getValue().size() == 1) {
                this.key = (String)entry.getKey();
                this.value = entry.getValue().get(0);
                this.useMap = false;
            }
            else {
                this.key = null;
                this.value = null;
                this.useMap = true;
            }
        }
        else {
            this.key = null;
            this.value = null;
            this.useMap = true;
        }
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final String s, final Object... array) {
        return this.filter();
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Object o, final Throwable t) {
        return this.filter();
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Message message, final Throwable t) {
        return this.filter();
    }
    
    private Filter.Result filter() {
        if (this.useMap) {
            for (final Map.Entry<String, V> entry : this.getMap().entrySet()) {
                final String value = ThreadContext.get(entry.getKey());
                if (value != null) {
                    ((List)entry.getValue()).contains(value);
                }
                if (!this.isAnd()) {}
                if (this.isAnd()) {
                    break;
                }
            }
        }
        else {
            this.value.equals(ThreadContext.get(this.key));
        }
        return this.onMismatch;
    }
    
    @Override
    public Filter.Result filter(final LogEvent logEvent) {
        return super.filter(logEvent.getContextMap()) ? this.onMatch : this.onMismatch;
    }
    
    @PluginFactory
    public static ThreadContextMapFilter createFilter(@PluginElement("Pairs") final KeyValuePair[] array, @PluginAttribute("operator") final String s, @PluginAttribute("onMatch") final String s2, @PluginAttribute("onMismatch") final String s3) {
        if (array == null || array.length == 0) {
            ThreadContextMapFilter.LOGGER.error("key and value pairs must be specified for the ThreadContextMapFilter");
            return null;
        }
        final HashMap<String, ArrayList<String>> hashMap = (HashMap<String, ArrayList<String>>)new HashMap<Object, List<String>>();
        while (0 < array.length) {
            final KeyValuePair keyValuePair = array[0];
            final String key = keyValuePair.getKey();
            if (key == null) {
                ThreadContextMapFilter.LOGGER.error("A null key is not valid in MapFilter");
            }
            else {
                final String value = keyValuePair.getValue();
                if (value == null) {
                    ThreadContextMapFilter.LOGGER.error("A null value for key " + key + " is not allowed in MapFilter");
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
            ThreadContextMapFilter.LOGGER.error("ThreadContextMapFilter is not configured with any valid key value pairs");
            return null;
        }
        return new ThreadContextMapFilter(hashMap, s == null || !s.equalsIgnoreCase("or"), Filter.Result.toResult(s2), Filter.Result.toResult(s3));
    }
}
