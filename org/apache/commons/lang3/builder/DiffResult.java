package org.apache.commons.lang3.builder;

import java.util.*;

public class DiffResult implements Iterable
{
    public static final String OBJECTS_SAME_STRING = "";
    private static final String DIFFERS_STRING = "differs from";
    private final List diffs;
    private final Object lhs;
    private final Object rhs;
    private final ToStringStyle style;
    
    DiffResult(final Object lhs, final Object rhs, final List diffs, final ToStringStyle style) {
        if (lhs == null) {
            throw new IllegalArgumentException("Left hand object cannot be null");
        }
        if (rhs == null) {
            throw new IllegalArgumentException("Right hand object cannot be null");
        }
        if (diffs == null) {
            throw new IllegalArgumentException("List of differences cannot be null");
        }
        this.diffs = diffs;
        this.lhs = lhs;
        this.rhs = rhs;
        if (style == null) {
            this.style = ToStringStyle.DEFAULT_STYLE;
        }
        else {
            this.style = style;
        }
    }
    
    public List getDiffs() {
        return Collections.unmodifiableList((List<?>)this.diffs);
    }
    
    public int getNumberOfDiffs() {
        return this.diffs.size();
    }
    
    public ToStringStyle getToStringStyle() {
        return this.style;
    }
    
    @Override
    public String toString() {
        return this.toString(this.style);
    }
    
    public String toString(final ToStringStyle toStringStyle) {
        if (this.diffs.size() == 0) {
            return "";
        }
        final ToStringBuilder toStringBuilder = new ToStringBuilder(this.lhs, toStringStyle);
        final ToStringBuilder toStringBuilder2 = new ToStringBuilder(this.rhs, toStringStyle);
        for (final Diff diff : this.diffs) {
            toStringBuilder.append(diff.getFieldName(), diff.getLeft());
            toStringBuilder2.append(diff.getFieldName(), diff.getRight());
        }
        return String.format("%s %s %s", toStringBuilder.build(), "differs from", toStringBuilder2.build());
    }
    
    @Override
    public Iterator iterator() {
        return this.diffs.iterator();
    }
}
