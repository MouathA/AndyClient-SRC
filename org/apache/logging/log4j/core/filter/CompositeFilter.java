package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "filters", category = "Core", printObject = true)
public final class CompositeFilter implements Iterable, Filter, LifeCycle
{
    private final List filters;
    private final boolean hasFilters;
    private boolean isStarted;
    
    private CompositeFilter() {
        this.filters = new ArrayList();
        this.hasFilters = false;
    }
    
    private CompositeFilter(final List list) {
        if (list == null) {
            this.filters = Collections.unmodifiableList((List<?>)new ArrayList<Object>());
            this.hasFilters = false;
            return;
        }
        this.filters = Collections.unmodifiableList((List<?>)list);
        this.hasFilters = (this.filters.size() > 0);
    }
    
    public CompositeFilter addFilter(final Filter filter) {
        final ArrayList<Filter> list = new ArrayList<Filter>(this.filters);
        list.add(filter);
        return new CompositeFilter(Collections.unmodifiableList((List<?>)list));
    }
    
    public CompositeFilter removeFilter(final Filter filter) {
        final ArrayList<Object> list = new ArrayList<Object>(this.filters);
        list.remove(filter);
        return new CompositeFilter(Collections.unmodifiableList((List<?>)list));
    }
    
    @Override
    public Iterator iterator() {
        return this.filters.iterator();
    }
    
    public List getFilters() {
        return this.filters;
    }
    
    public boolean hasFilters() {
        return this.hasFilters;
    }
    
    public int size() {
        return this.filters.size();
    }
    
    @Override
    public void start() {
        for (final Filter filter : this.filters) {
            if (filter instanceof LifeCycle) {
                ((LifeCycle)filter).start();
            }
        }
        this.isStarted = true;
    }
    
    @Override
    public void stop() {
        for (final Filter filter : this.filters) {
            if (filter instanceof LifeCycle) {
                ((LifeCycle)filter).stop();
            }
        }
        this.isStarted = false;
    }
    
    @Override
    public boolean isStarted() {
        return this.isStarted;
    }
    
    @Override
    public Result getOnMismatch() {
        return Result.NEUTRAL;
    }
    
    @Override
    public Result getOnMatch() {
        return Result.NEUTRAL;
    }
    
    @Override
    public Result filter(final Logger logger, final Level level, final Marker marker, final String s, final Object... array) {
        Result result = Result.NEUTRAL;
        final Iterator<Filter> iterator = this.filters.iterator();
        while (iterator.hasNext()) {
            result = iterator.next().filter(logger, level, marker, s, array);
            if (result == Result.ACCEPT || result == Result.DENY) {
                return result;
            }
        }
        return result;
    }
    
    @Override
    public Result filter(final Logger logger, final Level level, final Marker marker, final Object o, final Throwable t) {
        Result result = Result.NEUTRAL;
        final Iterator<Filter> iterator = this.filters.iterator();
        while (iterator.hasNext()) {
            result = iterator.next().filter(logger, level, marker, o, t);
            if (result == Result.ACCEPT || result == Result.DENY) {
                return result;
            }
        }
        return result;
    }
    
    @Override
    public Result filter(final Logger logger, final Level level, final Marker marker, final Message message, final Throwable t) {
        Result result = Result.NEUTRAL;
        final Iterator<Filter> iterator = this.filters.iterator();
        while (iterator.hasNext()) {
            result = iterator.next().filter(logger, level, marker, message, t);
            if (result == Result.ACCEPT || result == Result.DENY) {
                return result;
            }
        }
        return result;
    }
    
    @Override
    public Result filter(final LogEvent logEvent) {
        Result result = Result.NEUTRAL;
        final Iterator<Filter> iterator = this.filters.iterator();
        while (iterator.hasNext()) {
            result = iterator.next().filter(logEvent);
            if (result == Result.ACCEPT || result == Result.DENY) {
                return result;
            }
        }
        return result;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Filter filter : this.filters) {
            if (sb.length() == 0) {
                sb.append("{");
            }
            else {
                sb.append(", ");
            }
            sb.append(filter.toString());
        }
        if (sb.length() > 0) {
            sb.append("}");
        }
        return sb.toString();
    }
    
    @PluginFactory
    public static CompositeFilter createFilters(@PluginElement("Filters") final Filter[] array) {
        return new CompositeFilter((array == null || array.length == 0) ? new ArrayList<Filter>() : Arrays.asList(array));
    }
}
