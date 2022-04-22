package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.status.*;

public abstract class AbstractFilterable implements Filterable
{
    protected static final Logger LOGGER;
    private Filter filter;
    
    protected AbstractFilterable(final Filter filter) {
        this.filter = filter;
    }
    
    protected AbstractFilterable() {
    }
    
    @Override
    public Filter getFilter() {
        return this.filter;
    }
    
    @Override
    public synchronized void addFilter(final Filter filter) {
        if (this.filter == null) {
            this.filter = filter;
        }
        else if (filter instanceof CompositeFilter) {
            this.filter = ((CompositeFilter)this.filter).addFilter(filter);
        }
        else {
            this.filter = CompositeFilter.createFilters(new Filter[] { this.filter, filter });
        }
    }
    
    @Override
    public synchronized void removeFilter(final Filter filter) {
        if (this.filter == filter) {
            this.filter = null;
        }
        else if (filter instanceof CompositeFilter) {
            final CompositeFilter removeFilter = ((CompositeFilter)filter).removeFilter(filter);
            if (removeFilter.size() > 1) {
                this.filter = removeFilter;
            }
            else if (removeFilter.size() == 1) {
                this.filter = (Filter)removeFilter.iterator().next();
            }
            else {
                this.filter = null;
            }
        }
    }
    
    @Override
    public boolean hasFilter() {
        return this.filter != null;
    }
    
    public void startFilter() {
        if (this.filter != null && this.filter instanceof LifeCycle) {
            ((LifeCycle)this.filter).start();
        }
    }
    
    public void stopFilter() {
        if (this.filter != null && this.filter instanceof LifeCycle) {
            ((LifeCycle)this.filter).stop();
        }
    }
    
    @Override
    public boolean isFiltered(final LogEvent logEvent) {
        return this.filter != null && this.filter.filter(logEvent) == Filter.Result.DENY;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
