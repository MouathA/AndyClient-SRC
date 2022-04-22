package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import java.net.*;
import java.util.*;

@NotThreadSafe
public class RedirectLocations extends AbstractList
{
    private final Set unique;
    private final List all;
    
    public RedirectLocations() {
        this.unique = new HashSet();
        this.all = new ArrayList();
    }
    
    public boolean contains(final URI uri) {
        return this.unique.contains(uri);
    }
    
    public void add(final URI uri) {
        this.unique.add(uri);
        this.all.add(uri);
    }
    
    public boolean remove(final URI uri) {
        final boolean remove = this.unique.remove(uri);
        if (remove) {
            final Iterator iterator = this.all.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(uri)) {
                    iterator.remove();
                }
            }
        }
        return remove;
    }
    
    public List getAll() {
        return new ArrayList(this.all);
    }
    
    @Override
    public URI get(final int n) {
        return this.all.get(n);
    }
    
    @Override
    public int size() {
        return this.all.size();
    }
    
    @Override
    public Object set(final int n, final Object o) {
        final URI uri = this.all.set(n, o);
        this.unique.remove(uri);
        this.unique.add(o);
        if (this.all.size() != this.unique.size()) {
            this.unique.addAll(this.all);
        }
        return uri;
    }
    
    @Override
    public void add(final int n, final Object o) {
        this.all.add(n, o);
        this.unique.add(o);
    }
    
    @Override
    public URI remove(final int n) {
        final URI uri = this.all.remove(n);
        this.unique.remove(uri);
        if (this.all.size() != this.unique.size()) {
            this.unique.addAll(this.all);
        }
        return uri;
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.unique.contains(o);
    }
    
    @Override
    public Object remove(final int n) {
        return this.remove(n);
    }
    
    @Override
    public Object get(final int n) {
        return this.get(n);
    }
}
