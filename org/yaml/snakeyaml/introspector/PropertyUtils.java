package org.yaml.snakeyaml.introspector;

import org.yaml.snakeyaml.util.*;
import org.yaml.snakeyaml.error.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

public class PropertyUtils
{
    private final Map propertiesCache;
    private final Map readableProperties;
    private BeanAccess beanAccess;
    private boolean allowReadOnlyProperties;
    private boolean skipMissingProperties;
    private PlatformFeatureDetector platformFeatureDetector;
    private static final String TRANSIENT = "transient";
    
    public PropertyUtils() {
        this(new PlatformFeatureDetector());
    }
    
    PropertyUtils(final PlatformFeatureDetector platformFeatureDetector) {
        this.propertiesCache = new HashMap();
        this.readableProperties = new HashMap();
        this.beanAccess = BeanAccess.DEFAULT;
        this.allowReadOnlyProperties = false;
        this.skipMissingProperties = false;
        this.platformFeatureDetector = platformFeatureDetector;
        if (platformFeatureDetector.isRunningOnAndroid()) {
            this.beanAccess = BeanAccess.FIELD;
        }
    }
    
    protected Map getPropertiesMap(final Class clazz, final BeanAccess beanAccess) {
        if (this.propertiesCache.containsKey(clazz)) {
            return (Map)this.propertiesCache.get(clazz);
        }
        final LinkedHashMap<String, MethodProperty> linkedHashMap = new LinkedHashMap<String, MethodProperty>();
        switch (beanAccess) {
            case FIELD: {
                for (Class<?> superclass = (Class<?>)clazz; superclass != null; superclass = superclass.getSuperclass()) {
                    final int n = superclass.getDeclaredFields().length;
                }
                break;
            }
            default: {
                final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
                while (0 < propertyDescriptors.length) {
                    final PropertyDescriptor propertyDescriptor = propertyDescriptors[0];
                    final Method readMethod = propertyDescriptor.getReadMethod();
                    if ((readMethod == null || !readMethod.getName().equals("getClass")) && !this.isTransient(propertyDescriptor)) {
                        linkedHashMap.put(propertyDescriptor.getName(), new MethodProperty(propertyDescriptor));
                    }
                    int n = 0;
                    ++n;
                }
                for (Class<?> superclass2 = (Class<?>)clazz; superclass2 != null; superclass2 = superclass2.getSuperclass()) {
                    final int n = superclass2.getDeclaredFields().length;
                }
                break;
            }
        }
        if (linkedHashMap.isEmpty()) {
            throw new YAMLException("No JavaBean properties found in " + clazz.getName());
        }
        this.propertiesCache.put(clazz, linkedHashMap);
        return linkedHashMap;
    }
    
    private boolean isTransient(final FeatureDescriptor featureDescriptor) {
        return Boolean.TRUE.equals(featureDescriptor.getValue("transient"));
    }
    
    public Set getProperties(final Class clazz) {
        return this.getProperties(clazz, this.beanAccess);
    }
    
    public Set getProperties(final Class clazz, final BeanAccess beanAccess) {
        if (this.readableProperties.containsKey(clazz)) {
            return this.readableProperties.get(clazz);
        }
        final Set propertySet = this.createPropertySet(clazz, beanAccess);
        this.readableProperties.put(clazz, propertySet);
        return propertySet;
    }
    
    protected Set createPropertySet(final Class clazz, final BeanAccess beanAccess) {
        final TreeSet<Property> set = new TreeSet<Property>();
        for (final Property property : this.getPropertiesMap(clazz, beanAccess).values()) {
            if (property.isReadable() && (this.allowReadOnlyProperties || property.isWritable())) {
                set.add(property);
            }
        }
        return set;
    }
    
    public Property getProperty(final Class clazz, final String s) {
        return this.getProperty(clazz, s, this.beanAccess);
    }
    
    public Property getProperty(final Class clazz, final String s, final BeanAccess beanAccess) {
        Property property = this.getPropertiesMap(clazz, beanAccess).get(s);
        if (property == null && this.skipMissingProperties) {
            property = new MissingProperty(s);
        }
        if (property == null) {
            throw new YAMLException("Unable to find property '" + s + "' on class: " + clazz.getName());
        }
        return property;
    }
    
    public void setBeanAccess(final BeanAccess beanAccess) {
        if (this.platformFeatureDetector.isRunningOnAndroid() && beanAccess != BeanAccess.FIELD) {
            throw new IllegalArgumentException("JVM is Android - only BeanAccess.FIELD is available");
        }
        if (this.beanAccess != beanAccess) {
            this.beanAccess = beanAccess;
            this.propertiesCache.clear();
            this.readableProperties.clear();
        }
    }
    
    public void setAllowReadOnlyProperties(final boolean allowReadOnlyProperties) {
        if (this.allowReadOnlyProperties != allowReadOnlyProperties) {
            this.allowReadOnlyProperties = allowReadOnlyProperties;
            this.readableProperties.clear();
        }
    }
    
    public boolean isAllowReadOnlyProperties() {
        return this.allowReadOnlyProperties;
    }
    
    public void setSkipMissingProperties(final boolean skipMissingProperties) {
        if (this.skipMissingProperties != skipMissingProperties) {
            this.skipMissingProperties = skipMissingProperties;
            this.readableProperties.clear();
        }
    }
    
    public boolean isSkipMissingProperties() {
        return this.skipMissingProperties;
    }
}
