package com.mojang.realmsclient.client;

import java.util.*;

public class QueryBuilder
{
    private Map queryParams;
    
    public QueryBuilder() {
        this.queryParams = new HashMap();
    }
    
    public static QueryBuilder of(final String s, final String s2) {
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.queryParams.put(s, s2);
        return queryBuilder;
    }
    
    public static QueryBuilder empty() {
        return new QueryBuilder();
    }
    
    public QueryBuilder with(final String s, final String s2) {
        this.queryParams.put(s, s2);
        return this;
    }
    
    public QueryBuilder with(final Object o, final Object o2) {
        this.queryParams.put(String.valueOf(o), String.valueOf(o2));
        return this;
    }
    
    public String toQueryString() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<String> iterator = this.queryParams.keySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        final String s = iterator.next();
        sb.append(s).append("=").append((String)this.queryParams.get(s));
        while (iterator.hasNext()) {
            final String s2 = iterator.next();
            sb.append("&").append(s2).append("=").append((String)this.queryParams.get(s2));
        }
        return sb.toString();
    }
}
