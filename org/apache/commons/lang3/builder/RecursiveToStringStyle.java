package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.*;
import java.util.*;

public class RecursiveToStringStyle extends ToStringStyle
{
    private static final long serialVersionUID = 1L;
    
    public void appendDetail(final StringBuffer sb, final String s, final Object o) {
        if (!ClassUtils.isPrimitiveWrapper(o.getClass()) && !String.class.equals(o.getClass()) && this.accept(o.getClass())) {
            sb.append(ReflectionToStringBuilder.toString(o, this));
        }
        else {
            super.appendDetail(sb, s, o);
        }
    }
    
    @Override
    protected void appendDetail(final StringBuffer sb, final String s, final Collection collection) {
        this.appendClassName(sb, collection);
        this.appendIdentityHashCode(sb, collection);
        this.appendDetail(sb, s, collection.toArray());
    }
    
    protected boolean accept(final Class clazz) {
        return true;
    }
}
