package org.apache.commons.lang3.builder;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import org.apache.commons.lang3.*;

public abstract class ToStringStyle implements Serializable
{
    private static final long serialVersionUID = -2587890625525655916L;
    public static final ToStringStyle DEFAULT_STYLE;
    public static final ToStringStyle MULTI_LINE_STYLE;
    public static final ToStringStyle NO_FIELD_NAMES_STYLE;
    public static final ToStringStyle SHORT_PREFIX_STYLE;
    public static final ToStringStyle SIMPLE_STYLE;
    private static final ThreadLocal REGISTRY;
    private boolean useFieldNames;
    private boolean useClassName;
    private boolean useShortClassName;
    private boolean useIdentityHashCode;
    private String contentStart;
    private String contentEnd;
    private String fieldNameValueSeparator;
    private boolean fieldSeparatorAtStart;
    private boolean fieldSeparatorAtEnd;
    private String fieldSeparator;
    private String arrayStart;
    private String arraySeparator;
    private boolean arrayContentDetail;
    private String arrayEnd;
    private boolean defaultFullDetail;
    private String nullText;
    private String sizeStartText;
    private String sizeEndText;
    private String summaryObjectStartText;
    private String summaryObjectEndText;
    
    static Map getRegistry() {
        return ToStringStyle.REGISTRY.get();
    }
    
    static boolean isRegistered(final Object o) {
        final Map registry = getRegistry();
        return registry != null && registry.containsKey(o);
    }
    
    static void register(final Object o) {
        if (o != null) {
            if (getRegistry() == null) {
                ToStringStyle.REGISTRY.set(new WeakHashMap());
            }
            getRegistry().put(o, null);
        }
    }
    
    static void unregister(final Object o) {
        if (o != null) {
            final Map registry = getRegistry();
            if (registry != null) {
                registry.remove(o);
                if (registry.isEmpty()) {
                    ToStringStyle.REGISTRY.remove();
                }
            }
        }
    }
    
    protected ToStringStyle() {
        this.useFieldNames = true;
        this.useClassName = true;
        this.useShortClassName = false;
        this.useIdentityHashCode = true;
        this.contentStart = "[";
        this.contentEnd = "]";
        this.fieldNameValueSeparator = "=";
        this.fieldSeparatorAtStart = false;
        this.fieldSeparatorAtEnd = false;
        this.fieldSeparator = ",";
        this.arrayStart = "{";
        this.arraySeparator = ",";
        this.arrayContentDetail = true;
        this.arrayEnd = "}";
        this.defaultFullDetail = true;
        this.nullText = "<null>";
        this.sizeStartText = "<size=";
        this.sizeEndText = ">";
        this.summaryObjectStartText = "<";
        this.summaryObjectEndText = ">";
    }
    
    public void appendSuper(final StringBuffer sb, final String s) {
        this.appendToString(sb, s);
    }
    
    public void appendToString(final StringBuffer sb, final String s) {
        if (s != null) {
            final int n = s.indexOf(this.contentStart) + this.contentStart.length();
            final int lastIndex = s.lastIndexOf(this.contentEnd);
            if (n != lastIndex && n >= 0 && lastIndex >= 0) {
                final String substring = s.substring(n, lastIndex);
                if (this.fieldSeparatorAtStart) {
                    this.removeLastFieldSeparator(sb);
                }
                sb.append(substring);
                this.appendFieldSeparator(sb);
            }
        }
    }
    
    public void appendStart(final StringBuffer sb, final Object o) {
        if (o != null) {
            this.appendClassName(sb, o);
            this.appendIdentityHashCode(sb, o);
            this.appendContentStart(sb);
            if (this.fieldSeparatorAtStart) {
                this.appendFieldSeparator(sb);
            }
        }
    }
    
    public void appendEnd(final StringBuffer sb, final Object o) {
        if (!this.fieldSeparatorAtEnd) {
            this.removeLastFieldSeparator(sb);
        }
        this.appendContentEnd(sb);
        unregister(o);
    }
    
    protected void removeLastFieldSeparator(final StringBuffer sb) {
        final int length = sb.length();
        final int length2 = this.fieldSeparator.length();
        if (length > 0 && length2 > 0 && length >= length2) {
            while (0 < length2 && sb.charAt(length - 1 - 0) == this.fieldSeparator.charAt(length2 - 1 - 0)) {
                int n = 0;
                ++n;
            }
            if (false) {
                sb.setLength(length - length2);
            }
        }
    }
    
    public void append(final StringBuffer sb, final String s, final Object o, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (o == null) {
            this.appendNullText(sb, s);
        }
        else {
            this.appendInternal(sb, s, o, this.isFullDetail(b));
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendInternal(final StringBuffer sb, final String s, final Object o, final boolean b) {
        if (isRegistered(o) && !(o instanceof Number) && !(o instanceof Boolean) && !(o instanceof Character)) {
            this.appendCyclicObject(sb, s, o);
            return;
        }
        register(o);
        if (o instanceof Collection) {
            if (b) {
                this.appendDetail(sb, s, (Collection)o);
            }
            else {
                this.appendSummarySize(sb, s, ((Collection)o).size());
            }
        }
        else if (o instanceof Map) {
            if (b) {
                this.appendDetail(sb, s, (Map)o);
            }
            else {
                this.appendSummarySize(sb, s, ((Map)o).size());
            }
        }
        else if (o instanceof long[]) {
            if (b) {
                this.appendDetail(sb, s, (long[])o);
            }
            else {
                this.appendSummary(sb, s, (long[])o);
            }
        }
        else if (o instanceof int[]) {
            if (b) {
                this.appendDetail(sb, s, (int[])o);
            }
            else {
                this.appendSummary(sb, s, (int[])o);
            }
        }
        else if (o instanceof short[]) {
            if (b) {
                this.appendDetail(sb, s, (short[])o);
            }
            else {
                this.appendSummary(sb, s, (short[])o);
            }
        }
        else if (o instanceof byte[]) {
            if (b) {
                this.appendDetail(sb, s, (byte[])o);
            }
            else {
                this.appendSummary(sb, s, (byte[])o);
            }
        }
        else if (o instanceof char[]) {
            if (b) {
                this.appendDetail(sb, s, (char[])o);
            }
            else {
                this.appendSummary(sb, s, (char[])o);
            }
        }
        else if (o instanceof double[]) {
            if (b) {
                this.appendDetail(sb, s, (double[])o);
            }
            else {
                this.appendSummary(sb, s, (double[])o);
            }
        }
        else if (o instanceof float[]) {
            if (b) {
                this.appendDetail(sb, s, (float[])o);
            }
            else {
                this.appendSummary(sb, s, (float[])o);
            }
        }
        else if (o instanceof boolean[]) {
            if (b) {
                this.appendDetail(sb, s, (boolean[])o);
            }
            else {
                this.appendSummary(sb, s, (boolean[])o);
            }
        }
        else if (o.getClass().isArray()) {
            if (b) {
                this.appendDetail(sb, s, (Object[])o);
            }
            else {
                this.appendSummary(sb, s, (Object[])o);
            }
        }
        else if (b) {
            this.appendDetail(sb, s, o);
        }
        else {
            this.appendSummary(sb, s, o);
        }
        unregister(o);
    }
    
    protected void appendCyclicObject(final StringBuffer sb, final String s, final Object o) {
        ObjectUtils.identityToString(sb, o);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final Object o) {
        sb.append(o);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final Collection collection) {
        sb.append(collection);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final Map map) {
        sb.append(map);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final Object o) {
        sb.append(this.summaryObjectStartText);
        sb.append(this.getShortClassName(o.getClass()));
        sb.append(this.summaryObjectEndText);
    }
    
    public void append(final StringBuffer sb, final String s, final long n) {
        this.appendFieldStart(sb, s);
        this.appendDetail(sb, s, n);
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final long n) {
        sb.append(n);
    }
    
    public void append(final StringBuffer sb, final String s, final int n) {
        this.appendFieldStart(sb, s);
        this.appendDetail(sb, s, n);
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final int n) {
        sb.append(n);
    }
    
    public void append(final StringBuffer sb, final String s, final short n) {
        this.appendFieldStart(sb, s);
        this.appendDetail(sb, s, n);
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final short n) {
        sb.append(n);
    }
    
    public void append(final StringBuffer sb, final String s, final byte b) {
        this.appendFieldStart(sb, s);
        this.appendDetail(sb, s, b);
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final byte b) {
        sb.append(b);
    }
    
    public void append(final StringBuffer sb, final String s, final char c) {
        this.appendFieldStart(sb, s);
        this.appendDetail(sb, s, c);
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final char c) {
        sb.append(c);
    }
    
    public void append(final StringBuffer sb, final String s, final double n) {
        this.appendFieldStart(sb, s);
        this.appendDetail(sb, s, n);
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final double n) {
        sb.append(n);
    }
    
    public void append(final StringBuffer sb, final String s, final float n) {
        this.appendFieldStart(sb, s);
        this.appendDetail(sb, s, n);
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final float n) {
        sb.append(n);
    }
    
    public void append(final StringBuffer sb, final String s, final boolean b) {
        this.appendFieldStart(sb, s);
        this.appendDetail(sb, s, b);
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final boolean b) {
        sb.append(b);
    }
    
    public void append(final StringBuffer sb, final String s, final Object[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final Object[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            final Object o = array[0];
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            if (o == null) {
                this.appendNullText(sb, s);
            }
            else {
                this.appendInternal(sb, s, o, this.arrayContentDetail);
            }
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void reflectionAppendArrayDetail(final StringBuffer sb, final String s, final Object o) {
        sb.append(this.arrayStart);
        while (0 < Array.getLength(o)) {
            final Object value = Array.get(o, 0);
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            if (value == null) {
                this.appendNullText(sb, s);
            }
            else {
                this.appendInternal(sb, s, value, this.arrayContentDetail);
            }
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final Object[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    public void append(final StringBuffer sb, final String s, final long[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final long[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            this.appendDetail(sb, s, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final long[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    public void append(final StringBuffer sb, final String s, final int[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final int[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            this.appendDetail(sb, s, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final int[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    public void append(final StringBuffer sb, final String s, final short[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final short[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            this.appendDetail(sb, s, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final short[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    public void append(final StringBuffer sb, final String s, final byte[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final byte[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            this.appendDetail(sb, s, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final byte[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    public void append(final StringBuffer sb, final String s, final char[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final char[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            this.appendDetail(sb, s, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final char[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    public void append(final StringBuffer sb, final String s, final double[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final double[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            this.appendDetail(sb, s, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final double[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    public void append(final StringBuffer sb, final String s, final float[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final float[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            this.appendDetail(sb, s, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final float[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    public void append(final StringBuffer sb, final String s, final boolean[] array, final Boolean b) {
        this.appendFieldStart(sb, s);
        if (array == null) {
            this.appendNullText(sb, s);
        }
        else if (this.isFullDetail(b)) {
            this.appendDetail(sb, s, array);
        }
        else {
            this.appendSummary(sb, s, array);
        }
        this.appendFieldEnd(sb, s);
    }
    
    protected void appendDetail(final StringBuffer sb, final String s, final boolean[] array) {
        sb.append(this.arrayStart);
        while (0 < array.length) {
            if (0 > 0) {
                sb.append(this.arraySeparator);
            }
            this.appendDetail(sb, s, array[0]);
            int n = 0;
            ++n;
        }
        sb.append(this.arrayEnd);
    }
    
    protected void appendSummary(final StringBuffer sb, final String s, final boolean[] array) {
        this.appendSummarySize(sb, s, array.length);
    }
    
    protected void appendClassName(final StringBuffer sb, final Object o) {
        if (this.useClassName && o != null) {
            register(o);
            if (this.useShortClassName) {
                sb.append(this.getShortClassName(o.getClass()));
            }
            else {
                sb.append(o.getClass().getName());
            }
        }
    }
    
    protected void appendIdentityHashCode(final StringBuffer sb, final Object o) {
        if (this.isUseIdentityHashCode() && o != null) {
            register(o);
            sb.append('@');
            sb.append(Integer.toHexString(System.identityHashCode(o)));
        }
    }
    
    protected void appendContentStart(final StringBuffer sb) {
        sb.append(this.contentStart);
    }
    
    protected void appendContentEnd(final StringBuffer sb) {
        sb.append(this.contentEnd);
    }
    
    protected void appendNullText(final StringBuffer sb, final String s) {
        sb.append(this.nullText);
    }
    
    protected void appendFieldSeparator(final StringBuffer sb) {
        sb.append(this.fieldSeparator);
    }
    
    protected void appendFieldStart(final StringBuffer sb, final String s) {
        if (this.useFieldNames && s != null) {
            sb.append(s);
            sb.append(this.fieldNameValueSeparator);
        }
    }
    
    protected void appendFieldEnd(final StringBuffer sb, final String s) {
        this.appendFieldSeparator(sb);
    }
    
    protected void appendSummarySize(final StringBuffer sb, final String s, final int n) {
        sb.append(this.sizeStartText);
        sb.append(n);
        sb.append(this.sizeEndText);
    }
    
    protected boolean isFullDetail(final Boolean b) {
        if (b == null) {
            return this.defaultFullDetail;
        }
        return b;
    }
    
    protected String getShortClassName(final Class clazz) {
        return ClassUtils.getShortClassName(clazz);
    }
    
    protected boolean isUseClassName() {
        return this.useClassName;
    }
    
    protected void setUseClassName(final boolean useClassName) {
        this.useClassName = useClassName;
    }
    
    protected boolean isUseShortClassName() {
        return this.useShortClassName;
    }
    
    protected void setUseShortClassName(final boolean useShortClassName) {
        this.useShortClassName = useShortClassName;
    }
    
    protected boolean isUseIdentityHashCode() {
        return this.useIdentityHashCode;
    }
    
    protected void setUseIdentityHashCode(final boolean useIdentityHashCode) {
        this.useIdentityHashCode = useIdentityHashCode;
    }
    
    protected boolean isUseFieldNames() {
        return this.useFieldNames;
    }
    
    protected void setUseFieldNames(final boolean useFieldNames) {
        this.useFieldNames = useFieldNames;
    }
    
    protected boolean isDefaultFullDetail() {
        return this.defaultFullDetail;
    }
    
    protected void setDefaultFullDetail(final boolean defaultFullDetail) {
        this.defaultFullDetail = defaultFullDetail;
    }
    
    protected boolean isArrayContentDetail() {
        return this.arrayContentDetail;
    }
    
    protected void setArrayContentDetail(final boolean arrayContentDetail) {
        this.arrayContentDetail = arrayContentDetail;
    }
    
    protected String getArrayStart() {
        return this.arrayStart;
    }
    
    protected void setArrayStart(String arrayStart) {
        if (arrayStart == null) {
            arrayStart = "";
        }
        this.arrayStart = arrayStart;
    }
    
    protected String getArrayEnd() {
        return this.arrayEnd;
    }
    
    protected void setArrayEnd(String arrayEnd) {
        if (arrayEnd == null) {
            arrayEnd = "";
        }
        this.arrayEnd = arrayEnd;
    }
    
    protected String getArraySeparator() {
        return this.arraySeparator;
    }
    
    protected void setArraySeparator(String arraySeparator) {
        if (arraySeparator == null) {
            arraySeparator = "";
        }
        this.arraySeparator = arraySeparator;
    }
    
    protected String getContentStart() {
        return this.contentStart;
    }
    
    protected void setContentStart(String contentStart) {
        if (contentStart == null) {
            contentStart = "";
        }
        this.contentStart = contentStart;
    }
    
    protected String getContentEnd() {
        return this.contentEnd;
    }
    
    protected void setContentEnd(String contentEnd) {
        if (contentEnd == null) {
            contentEnd = "";
        }
        this.contentEnd = contentEnd;
    }
    
    protected String getFieldNameValueSeparator() {
        return this.fieldNameValueSeparator;
    }
    
    protected void setFieldNameValueSeparator(String fieldNameValueSeparator) {
        if (fieldNameValueSeparator == null) {
            fieldNameValueSeparator = "";
        }
        this.fieldNameValueSeparator = fieldNameValueSeparator;
    }
    
    protected String getFieldSeparator() {
        return this.fieldSeparator;
    }
    
    protected void setFieldSeparator(String fieldSeparator) {
        if (fieldSeparator == null) {
            fieldSeparator = "";
        }
        this.fieldSeparator = fieldSeparator;
    }
    
    protected boolean isFieldSeparatorAtStart() {
        return this.fieldSeparatorAtStart;
    }
    
    protected void setFieldSeparatorAtStart(final boolean fieldSeparatorAtStart) {
        this.fieldSeparatorAtStart = fieldSeparatorAtStart;
    }
    
    protected boolean isFieldSeparatorAtEnd() {
        return this.fieldSeparatorAtEnd;
    }
    
    protected void setFieldSeparatorAtEnd(final boolean fieldSeparatorAtEnd) {
        this.fieldSeparatorAtEnd = fieldSeparatorAtEnd;
    }
    
    protected String getNullText() {
        return this.nullText;
    }
    
    protected void setNullText(String nullText) {
        if (nullText == null) {
            nullText = "";
        }
        this.nullText = nullText;
    }
    
    protected String getSizeStartText() {
        return this.sizeStartText;
    }
    
    protected void setSizeStartText(String sizeStartText) {
        if (sizeStartText == null) {
            sizeStartText = "";
        }
        this.sizeStartText = sizeStartText;
    }
    
    protected String getSizeEndText() {
        return this.sizeEndText;
    }
    
    protected void setSizeEndText(String sizeEndText) {
        if (sizeEndText == null) {
            sizeEndText = "";
        }
        this.sizeEndText = sizeEndText;
    }
    
    protected String getSummaryObjectStartText() {
        return this.summaryObjectStartText;
    }
    
    protected void setSummaryObjectStartText(String summaryObjectStartText) {
        if (summaryObjectStartText == null) {
            summaryObjectStartText = "";
        }
        this.summaryObjectStartText = summaryObjectStartText;
    }
    
    protected String getSummaryObjectEndText() {
        return this.summaryObjectEndText;
    }
    
    protected void setSummaryObjectEndText(String summaryObjectEndText) {
        if (summaryObjectEndText == null) {
            summaryObjectEndText = "";
        }
        this.summaryObjectEndText = summaryObjectEndText;
    }
    
    static {
        DEFAULT_STYLE = new DefaultToStringStyle();
        MULTI_LINE_STYLE = new MultiLineToStringStyle();
        NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();
        SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();
        SIMPLE_STYLE = new SimpleToStringStyle();
        REGISTRY = new ThreadLocal();
    }
    
    private static final class MultiLineToStringStyle extends ToStringStyle
    {
        private static final long serialVersionUID = 1L;
        
        MultiLineToStringStyle() {
            this.setContentStart("[");
            this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
            this.setFieldSeparatorAtStart(true);
            this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
        }
        
        private Object readResolve() {
            return ToStringStyle.MULTI_LINE_STYLE;
        }
    }
    
    private static final class SimpleToStringStyle extends ToStringStyle
    {
        private static final long serialVersionUID = 1L;
        
        SimpleToStringStyle() {
            this.setUseClassName(false);
            this.setUseIdentityHashCode(false);
            this.setUseFieldNames(false);
            this.setContentStart("");
            this.setContentEnd("");
        }
        
        private Object readResolve() {
            return ToStringStyle.SIMPLE_STYLE;
        }
    }
    
    private static final class ShortPrefixToStringStyle extends ToStringStyle
    {
        private static final long serialVersionUID = 1L;
        
        ShortPrefixToStringStyle() {
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
        }
        
        private Object readResolve() {
            return ToStringStyle.SHORT_PREFIX_STYLE;
        }
    }
    
    private static final class NoFieldNameToStringStyle extends ToStringStyle
    {
        private static final long serialVersionUID = 1L;
        
        NoFieldNameToStringStyle() {
            this.setUseFieldNames(false);
        }
        
        private Object readResolve() {
            return ToStringStyle.NO_FIELD_NAMES_STYLE;
        }
    }
    
    private static final class DefaultToStringStyle extends ToStringStyle
    {
        private static final long serialVersionUID = 1L;
        
        DefaultToStringStyle() {
        }
        
        private Object readResolve() {
            return ToStringStyle.DEFAULT_STYLE;
        }
    }
}
