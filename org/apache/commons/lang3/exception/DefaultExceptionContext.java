package org.apache.commons.lang3.exception;

import java.io.*;
import org.apache.commons.lang3.tuple.*;
import org.apache.commons.lang3.*;
import java.util.*;

public class DefaultExceptionContext implements ExceptionContext, Serializable
{
    private static final long serialVersionUID = 20110706L;
    private final List contextValues;
    
    public DefaultExceptionContext() {
        this.contextValues = new ArrayList();
    }
    
    @Override
    public DefaultExceptionContext addContextValue(final String s, final Object o) {
        this.contextValues.add(new ImmutablePair(s, o));
        return this;
    }
    
    @Override
    public DefaultExceptionContext setContextValue(final String s, final Object o) {
        final Iterator<Pair> iterator = (Iterator<Pair>)this.contextValues.iterator();
        while (iterator.hasNext()) {
            if (StringUtils.equals(s, (CharSequence)iterator.next().getKey())) {
                iterator.remove();
            }
        }
        this.addContextValue(s, o);
        return this;
    }
    
    @Override
    public List getContextValues(final String s) {
        final ArrayList<Object> list = new ArrayList<Object>();
        for (final Pair pair : this.contextValues) {
            if (StringUtils.equals(s, (CharSequence)pair.getKey())) {
                list.add(pair.getValue());
            }
        }
        return list;
    }
    
    @Override
    public Object getFirstContextValue(final String s) {
        for (final Pair pair : this.contextValues) {
            if (StringUtils.equals(s, (CharSequence)pair.getKey())) {
                return pair.getValue();
            }
        }
        return null;
    }
    
    @Override
    public Set getContextLabels() {
        final HashSet<Object> set = new HashSet<Object>();
        final Iterator<Pair> iterator = this.contextValues.iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next().getKey());
        }
        return set;
    }
    
    @Override
    public List getContextEntries() {
        return this.contextValues;
    }
    
    @Override
    public String getFormattedExceptionMessage(final String s) {
        final StringBuilder sb = new StringBuilder(256);
        if (s != null) {
            sb.append(s);
        }
        if (this.contextValues.size() > 0) {
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append("Exception Context:\n");
            for (final Pair pair : this.contextValues) {
                sb.append("\t[");
                final StringBuilder sb2 = sb;
                int n = 0;
                ++n;
                sb2.append(0);
                sb.append(':');
                sb.append((String)pair.getKey());
                sb.append("=");
                final Object value = pair.getValue();
                if (value == null) {
                    sb.append("null");
                }
                else {
                    sb.append(value.toString());
                }
                sb.append("]\n");
            }
            sb.append("---------------------------------");
        }
        return sb.toString();
    }
    
    @Override
    public ExceptionContext setContextValue(final String s, final Object o) {
        return this.setContextValue(s, o);
    }
    
    @Override
    public ExceptionContext addContextValue(final String s, final Object o) {
        return this.addContextValue(s, o);
    }
}
