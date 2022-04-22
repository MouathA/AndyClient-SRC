package org.yaml.snakeyaml;

import java.util.logging.*;
import org.yaml.snakeyaml.introspector.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;
import java.lang.reflect.*;

public class TypeDescription
{
    private static final Logger log;
    private final Class type;
    private Class impl;
    private Tag tag;
    private transient Set dumpProperties;
    private transient PropertyUtils propertyUtils;
    private transient boolean delegatesChecked;
    private Map properties;
    protected Set excludes;
    protected String[] includes;
    protected BeanAccess beanAccess;
    
    public TypeDescription(final Class clazz, final Tag tag) {
        this(clazz, tag, null);
    }
    
    public TypeDescription(final Class type, final Tag tag, final Class impl) {
        this.properties = Collections.emptyMap();
        this.excludes = Collections.emptySet();
        this.includes = null;
        this.type = type;
        this.tag = tag;
        this.impl = impl;
        this.beanAccess = null;
    }
    
    public TypeDescription(final Class clazz, final String s) {
        this(clazz, new Tag(s), null);
    }
    
    public TypeDescription(final Class clazz) {
        this(clazz, null, null);
    }
    
    public TypeDescription(final Class clazz, final Class clazz2) {
        this(clazz, null, clazz2);
    }
    
    public Tag getTag() {
        return this.tag;
    }
    
    @Deprecated
    public void setTag(final Tag tag) {
        this.tag = tag;
    }
    
    @Deprecated
    public void setTag(final String s) {
        this.setTag(new Tag(s));
    }
    
    public Class getType() {
        return this.type;
    }
    
    @Deprecated
    public void putListPropertyType(final String s, final Class clazz) {
        this.addPropertyParameters(s, clazz);
    }
    
    @Deprecated
    public Class getListPropertyType(final String s) {
        if (this.properties.containsKey(s)) {
            final Class[] actualTypeArguments = this.properties.get(s).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                return actualTypeArguments[0];
            }
        }
        return null;
    }
    
    @Deprecated
    public void putMapPropertyType(final String s, final Class clazz, final Class clazz2) {
        this.addPropertyParameters(s, clazz, clazz2);
    }
    
    @Deprecated
    public Class getMapKeyType(final String s) {
        if (this.properties.containsKey(s)) {
            final Class[] actualTypeArguments = this.properties.get(s).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                return actualTypeArguments[0];
            }
        }
        return null;
    }
    
    @Deprecated
    public Class getMapValueType(final String s) {
        if (this.properties.containsKey(s)) {
            final Class[] actualTypeArguments = this.properties.get(s).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 1) {
                return actualTypeArguments[1];
            }
        }
        return null;
    }
    
    public void addPropertyParameters(final String s, final Class... actualTypeArguments) {
        if (!this.properties.containsKey(s)) {
            this.substituteProperty(s, null, null, null, actualTypeArguments);
        }
        else {
            this.properties.get(s).setActualTypeArguments(actualTypeArguments);
        }
    }
    
    @Override
    public String toString() {
        return "TypeDescription for " + this.getType() + " (tag='" + this.getTag() + "')";
    }
    
    private void checkDelegates() {
        for (final PropertySubstitute propertySubstitute : this.properties.values()) {
            propertySubstitute.setDelegate(this.discoverProperty(propertySubstitute.getName()));
        }
        this.delegatesChecked = true;
    }
    
    private Property discoverProperty(final String s) {
        if (this.propertyUtils == null) {
            return null;
        }
        if (this.beanAccess == null) {
            return this.propertyUtils.getProperty(this.type, s);
        }
        return this.propertyUtils.getProperty(this.type, s, this.beanAccess);
    }
    
    public Property getProperty(final String s) {
        if (!this.delegatesChecked) {
            this.checkDelegates();
        }
        return this.properties.containsKey(s) ? this.properties.get(s) : this.discoverProperty(s);
    }
    
    public void substituteProperty(final String s, final Class clazz, final String s2, final String s3, final Class... array) {
        this.substituteProperty(new PropertySubstitute(s, clazz, s2, s3, array));
    }
    
    public void substituteProperty(final PropertySubstitute propertySubstitute) {
        if (Collections.EMPTY_MAP == this.properties) {
            this.properties = new LinkedHashMap();
        }
        propertySubstitute.setTargetType(this.type);
        this.properties.put(propertySubstitute.getName(), propertySubstitute);
    }
    
    public void setPropertyUtils(final PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
    }
    
    public void setIncludes(final String... array) {
        this.includes = (String[])((array != null && array.length > 0) ? array : null);
    }
    
    public void setExcludes(final String... array) {
        if (array != null && array.length > 0) {
            this.excludes = new HashSet();
            while (0 < array.length) {
                this.excludes.add(array[0]);
                int n = 0;
                ++n;
            }
        }
        else {
            this.excludes = Collections.emptySet();
        }
    }
    
    public Set getProperties() {
        if (this.dumpProperties != null) {
            return this.dumpProperties;
        }
        if (this.propertyUtils == null) {
            return null;
        }
        if (this.includes != null) {
            this.dumpProperties = new LinkedHashSet();
            final String[] includes = this.includes;
            while (0 < includes.length) {
                final String s = includes[0];
                if (!this.excludes.contains(s)) {
                    this.dumpProperties.add(this.getProperty(s));
                }
                int n = 0;
                ++n;
            }
            return this.dumpProperties;
        }
        final Set dumpProperties = (this.beanAccess == null) ? this.propertyUtils.getProperties(this.type) : this.propertyUtils.getProperties(this.type, this.beanAccess);
        if (!this.properties.isEmpty()) {
            if (!this.delegatesChecked) {
                this.checkDelegates();
            }
            this.dumpProperties = new LinkedHashSet();
            for (final Property property : this.properties.values()) {
                if (!this.excludes.contains(property.getName()) && property.isReadable()) {
                    this.dumpProperties.add(property);
                }
            }
            for (final Property property2 : dumpProperties) {
                if (!this.excludes.contains(property2.getName())) {
                    this.dumpProperties.add(property2);
                }
            }
            return this.dumpProperties;
        }
        if (this.excludes.isEmpty()) {
            return this.dumpProperties = dumpProperties;
        }
        this.dumpProperties = new LinkedHashSet();
        for (final Property property3 : dumpProperties) {
            if (!this.excludes.contains(property3.getName())) {
                this.dumpProperties.add(property3);
            }
        }
        return this.dumpProperties;
    }
    
    public boolean setupPropertyType(final String s, final Node node) {
        return false;
    }
    
    public boolean setProperty(final Object o, final String s, final Object o2) throws Exception {
        return false;
    }
    
    public Object newInstance(final Node node) {
        if (this.impl != null) {
            final Constructor<Object> declaredConstructor = this.impl.getDeclaredConstructor((Class<?>[])new Class[0]);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(new Object[0]);
        }
        return null;
    }
    
    public Object newInstance(final String s, final Node node) {
        return null;
    }
    
    public Object finalizeConstruction(final Object o) {
        return o;
    }
    
    static {
        log = Logger.getLogger(TypeDescription.class.getPackage().getName());
    }
}
