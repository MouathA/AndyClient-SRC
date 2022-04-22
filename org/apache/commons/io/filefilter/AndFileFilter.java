package org.apache.commons.io.filefilter;

import java.io.*;
import java.util.*;

public class AndFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable
{
    private final List fileFilters;
    
    public AndFileFilter() {
        this.fileFilters = new ArrayList();
    }
    
    public AndFileFilter(final List list) {
        if (list == null) {
            this.fileFilters = new ArrayList();
        }
        else {
            this.fileFilters = new ArrayList(list);
        }
    }
    
    public AndFileFilter(final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        if (ioFileFilter == null || ioFileFilter2 == null) {
            throw new IllegalArgumentException("The filters must not be null");
        }
        this.fileFilters = new ArrayList(2);
        this.addFileFilter(ioFileFilter);
        this.addFileFilter(ioFileFilter2);
    }
    
    @Override
    public void addFileFilter(final IOFileFilter ioFileFilter) {
        this.fileFilters.add(ioFileFilter);
    }
    
    @Override
    public List getFileFilters() {
        return Collections.unmodifiableList((List<?>)this.fileFilters);
    }
    
    @Override
    public boolean removeFileFilter(final IOFileFilter ioFileFilter) {
        return this.fileFilters.remove(ioFileFilter);
    }
    
    @Override
    public void setFileFilters(final List list) {
        this.fileFilters.clear();
        this.fileFilters.addAll(list);
    }
    
    @Override
    public boolean accept(final File file) {
        if (this.fileFilters.isEmpty()) {
            return false;
        }
        final Iterator<IOFileFilter> iterator = this.fileFilters.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().accept(file)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        if (this.fileFilters.isEmpty()) {
            return false;
        }
        final Iterator<IOFileFilter> iterator = this.fileFilters.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().accept(file, s)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        if (this.fileFilters != null) {
            while (0 < this.fileFilters.size()) {
                if (0 > 0) {
                    sb.append(",");
                }
                final Object value = this.fileFilters.get(0);
                sb.append((value == null) ? "null" : value.toString());
                int n = 0;
                ++n;
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
