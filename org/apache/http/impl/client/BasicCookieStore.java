package org.apache.http.impl.client;

import org.apache.http.client.*;
import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.cookie.*;
import java.util.*;

@ThreadSafe
public class BasicCookieStore implements CookieStore, Serializable
{
    private static final long serialVersionUID = -7581093305228232025L;
    @GuardedBy("this")
    private final TreeSet cookies;
    
    public BasicCookieStore() {
        this.cookies = new TreeSet(new CookieIdentityComparator());
    }
    
    public synchronized void addCookie(final Cookie cookie) {
        if (cookie != null) {
            this.cookies.remove(cookie);
            if (!cookie.isExpired(new Date())) {
                this.cookies.add(cookie);
            }
        }
    }
    
    public synchronized void addCookies(final Cookie[] array) {
        if (array != null) {
            while (0 < array.length) {
                this.addCookie(array[0]);
                int n = 0;
                ++n;
            }
        }
    }
    
    public synchronized List getCookies() {
        return new ArrayList(this.cookies);
    }
    
    public synchronized boolean clearExpired(final Date date) {
        if (date == null) {
            return false;
        }
        final Iterator<Cookie> iterator = (Iterator<Cookie>)this.cookies.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isExpired(date)) {
                iterator.remove();
            }
        }
        return true;
    }
    
    public synchronized void clear() {
        this.cookies.clear();
    }
    
    @Override
    public synchronized String toString() {
        return this.cookies.toString();
    }
}
