package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.util.*;
import java.net.*;
import org.yaml.snakeyaml.error.*;
import java.math.*;
import java.util.*;

public final class Tag
{
    public static final String PREFIX = "tag:yaml.org,2002:";
    public static final Tag YAML;
    public static final Tag MERGE;
    public static final Tag SET;
    public static final Tag PAIRS;
    public static final Tag OMAP;
    public static final Tag BINARY;
    public static final Tag INT;
    public static final Tag FLOAT;
    public static final Tag TIMESTAMP;
    public static final Tag BOOL;
    public static final Tag NULL;
    public static final Tag STR;
    public static final Tag SEQ;
    public static final Tag MAP;
    protected static final Map COMPATIBILITY_MAP;
    private final String value;
    private boolean secondary;
    
    public Tag(final String s) {
        this.secondary = false;
        if (s == null) {
            throw new NullPointerException("Tag must be provided.");
        }
        if (s.length() == 0) {
            throw new IllegalArgumentException("Tag must not be empty.");
        }
        if (s.trim().length() != s.length()) {
            throw new IllegalArgumentException("Tag must not contain leading or trailing spaces.");
        }
        this.value = UriEncoder.encode(s);
        this.secondary = !s.startsWith("tag:yaml.org,2002:");
    }
    
    public Tag(final Class clazz) {
        this.secondary = false;
        if (clazz == null) {
            throw new NullPointerException("Class for tag must be provided.");
        }
        this.value = "tag:yaml.org,2002:" + UriEncoder.encode(clazz.getName());
    }
    
    @Deprecated
    public Tag(final URI uri) {
        this.secondary = false;
        if (uri == null) {
            throw new NullPointerException("URI for tag must be provided.");
        }
        this.value = uri.toASCIIString();
    }
    
    public boolean isSecondary() {
        return this.secondary;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public boolean startsWith(final String s) {
        return this.value.startsWith(s);
    }
    
    public String getClassName() {
        if (!this.value.startsWith("tag:yaml.org,2002:")) {
            throw new YAMLException("Invalid tag: " + this.value);
        }
        return UriEncoder.decode(this.value.substring(18));
    }
    
    @Override
    public String toString() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Tag && this.value.equals(((Tag)o).getValue());
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    public boolean isCompatible(final Class clazz) {
        final Set set = Tag.COMPATIBILITY_MAP.get(this);
        return set != null && set.contains(clazz);
    }
    
    public boolean matches(final Class clazz) {
        return this.value.equals("tag:yaml.org,2002:" + clazz.getName());
    }
    
    static {
        YAML = new Tag("tag:yaml.org,2002:yaml");
        MERGE = new Tag("tag:yaml.org,2002:merge");
        SET = new Tag("tag:yaml.org,2002:set");
        PAIRS = new Tag("tag:yaml.org,2002:pairs");
        OMAP = new Tag("tag:yaml.org,2002:omap");
        BINARY = new Tag("tag:yaml.org,2002:binary");
        INT = new Tag("tag:yaml.org,2002:int");
        FLOAT = new Tag("tag:yaml.org,2002:float");
        TIMESTAMP = new Tag("tag:yaml.org,2002:timestamp");
        BOOL = new Tag("tag:yaml.org,2002:bool");
        NULL = new Tag("tag:yaml.org,2002:null");
        STR = new Tag("tag:yaml.org,2002:str");
        SEQ = new Tag("tag:yaml.org,2002:seq");
        MAP = new Tag("tag:yaml.org,2002:map");
        COMPATIBILITY_MAP = new HashMap();
        final HashSet<Class<Double>> set = new HashSet<Class<Double>>();
        set.add(Double.class);
        set.add((Class<Double>)Float.class);
        set.add((Class<Double>)BigDecimal.class);
        Tag.COMPATIBILITY_MAP.put(Tag.FLOAT, set);
        final HashSet<Class<Integer>> set2 = new HashSet<Class<Integer>>();
        set2.add(Integer.class);
        set2.add((Class<Integer>)Long.class);
        set2.add((Class<Integer>)BigInteger.class);
        Tag.COMPATIBILITY_MAP.put(Tag.INT, set2);
        final HashSet<Class<Date>> set3 = new HashSet<Class<Date>>();
        set3.add(Date.class);
        set3.add((Class<Date>)Class.forName("java.sql.Date"));
        set3.add((Class<Date>)Class.forName("java.sql.Timestamp"));
        Tag.COMPATIBILITY_MAP.put(Tag.TIMESTAMP, set3);
    }
}
