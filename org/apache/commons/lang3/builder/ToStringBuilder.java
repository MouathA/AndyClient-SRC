package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.*;

public class ToStringBuilder implements Builder
{
    private static ToStringStyle defaultStyle;
    private final StringBuffer buffer;
    private final Object object;
    private final ToStringStyle style;
    
    public static ToStringStyle getDefaultStyle() {
        return ToStringBuilder.defaultStyle;
    }
    
    public static void setDefaultStyle(final ToStringStyle defaultStyle) {
        if (defaultStyle == null) {
            throw new IllegalArgumentException("The style must not be null");
        }
        ToStringBuilder.defaultStyle = defaultStyle;
    }
    
    public static String reflectionToString(final Object o) {
        return ReflectionToStringBuilder.toString(o);
    }
    
    public static String reflectionToString(final Object o, final ToStringStyle toStringStyle) {
        return ReflectionToStringBuilder.toString(o, toStringStyle);
    }
    
    public static String reflectionToString(final Object o, final ToStringStyle toStringStyle, final boolean b) {
        return ReflectionToStringBuilder.toString(o, toStringStyle, b, false, null);
    }
    
    public static String reflectionToString(final Object o, final ToStringStyle toStringStyle, final boolean b, final Class clazz) {
        return ReflectionToStringBuilder.toString(o, toStringStyle, b, false, clazz);
    }
    
    public ToStringBuilder(final Object o) {
        this(o, null, null);
    }
    
    public ToStringBuilder(final Object o, final ToStringStyle toStringStyle) {
        this(o, toStringStyle, null);
    }
    
    public ToStringBuilder(final Object object, ToStringStyle defaultStyle, StringBuffer buffer) {
        if (defaultStyle == null) {
            defaultStyle = getDefaultStyle();
        }
        if (buffer == null) {
            buffer = new StringBuffer(512);
        }
        this.buffer = buffer;
        (this.style = defaultStyle).appendStart(buffer, this.object = object);
    }
    
    public ToStringBuilder append(final boolean b) {
        this.style.append(this.buffer, null, b);
        return this;
    }
    
    public ToStringBuilder append(final boolean[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final byte b) {
        this.style.append(this.buffer, null, b);
        return this;
    }
    
    public ToStringBuilder append(final byte[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final char c) {
        this.style.append(this.buffer, null, c);
        return this;
    }
    
    public ToStringBuilder append(final char[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final double n) {
        this.style.append(this.buffer, null, n);
        return this;
    }
    
    public ToStringBuilder append(final double[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final float n) {
        this.style.append(this.buffer, null, n);
        return this;
    }
    
    public ToStringBuilder append(final float[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final int n) {
        this.style.append(this.buffer, null, n);
        return this;
    }
    
    public ToStringBuilder append(final int[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final long n) {
        this.style.append(this.buffer, null, n);
        return this;
    }
    
    public ToStringBuilder append(final long[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final Object o) {
        this.style.append(this.buffer, null, o, null);
        return this;
    }
    
    public ToStringBuilder append(final Object[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final short n) {
        this.style.append(this.buffer, null, n);
        return this;
    }
    
    public ToStringBuilder append(final short[] array) {
        this.style.append(this.buffer, null, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final boolean b) {
        this.style.append(this.buffer, s, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final boolean[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final boolean[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final byte b) {
        this.style.append(this.buffer, s, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final byte[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final byte[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final char c) {
        this.style.append(this.buffer, s, c);
        return this;
    }
    
    public ToStringBuilder append(final String s, final char[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final char[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final double n) {
        this.style.append(this.buffer, s, n);
        return this;
    }
    
    public ToStringBuilder append(final String s, final double[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final double[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final float n) {
        this.style.append(this.buffer, s, n);
        return this;
    }
    
    public ToStringBuilder append(final String s, final float[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final float[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final int n) {
        this.style.append(this.buffer, s, n);
        return this;
    }
    
    public ToStringBuilder append(final String s, final int[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final int[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final long n) {
        this.style.append(this.buffer, s, n);
        return this;
    }
    
    public ToStringBuilder append(final String s, final long[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final long[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final Object o) {
        this.style.append(this.buffer, s, o, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final Object o, final boolean b) {
        this.style.append(this.buffer, s, o, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final Object[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final Object[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder append(final String s, final short n) {
        this.style.append(this.buffer, s, n);
        return this;
    }
    
    public ToStringBuilder append(final String s, final short[] array) {
        this.style.append(this.buffer, s, array, null);
        return this;
    }
    
    public ToStringBuilder append(final String s, final short[] array, final boolean b) {
        this.style.append(this.buffer, s, array, b);
        return this;
    }
    
    public ToStringBuilder appendAsObjectToString(final Object o) {
        ObjectUtils.identityToString(this.getStringBuffer(), o);
        return this;
    }
    
    public ToStringBuilder appendSuper(final String s) {
        if (s != null) {
            this.style.appendSuper(this.buffer, s);
        }
        return this;
    }
    
    public ToStringBuilder appendToString(final String s) {
        if (s != null) {
            this.style.appendToString(this.buffer, s);
        }
        return this;
    }
    
    public Object getObject() {
        return this.object;
    }
    
    public StringBuffer getStringBuffer() {
        return this.buffer;
    }
    
    public ToStringStyle getStyle() {
        return this.style;
    }
    
    @Override
    public String toString() {
        if (this.getObject() == null) {
            this.getStringBuffer().append(this.getStyle().getNullText());
        }
        else {
            this.style.appendEnd(this.getStringBuffer(), this.getObject());
        }
        return this.getStringBuffer().toString();
    }
    
    @Override
    public String build() {
        return this.toString();
    }
    
    @Override
    public Object build() {
        return this.build();
    }
    
    static {
        ToStringBuilder.defaultStyle = ToStringStyle.DEFAULT_STYLE;
    }
}
