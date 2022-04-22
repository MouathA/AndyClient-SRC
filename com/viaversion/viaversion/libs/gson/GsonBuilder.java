package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.internal.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import com.viaversion.viaversion.libs.gson.internal.bind.*;
import java.util.*;
import java.sql.*;

public final class GsonBuilder
{
    private Excluder excluder;
    private LongSerializationPolicy longSerializationPolicy;
    private FieldNamingStrategy fieldNamingPolicy;
    private final Map instanceCreators;
    private final List factories;
    private final List hierarchyFactories;
    private boolean serializeNulls;
    private String datePattern;
    private int dateStyle;
    private int timeStyle;
    private boolean complexMapKeySerialization;
    private boolean serializeSpecialFloatingPointValues;
    private boolean escapeHtmlChars;
    private boolean prettyPrinting;
    private boolean generateNonExecutableJson;
    private boolean lenient;
    
    public GsonBuilder() {
        this.excluder = Excluder.DEFAULT;
        this.longSerializationPolicy = LongSerializationPolicy.DEFAULT;
        this.fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
        this.instanceCreators = new HashMap();
        this.factories = new ArrayList();
        this.hierarchyFactories = new ArrayList();
        this.serializeNulls = false;
        this.dateStyle = 2;
        this.timeStyle = 2;
        this.complexMapKeySerialization = false;
        this.serializeSpecialFloatingPointValues = false;
        this.escapeHtmlChars = true;
        this.prettyPrinting = false;
        this.generateNonExecutableJson = false;
        this.lenient = false;
    }
    
    GsonBuilder(final Gson gson) {
        this.excluder = Excluder.DEFAULT;
        this.longSerializationPolicy = LongSerializationPolicy.DEFAULT;
        this.fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
        this.instanceCreators = new HashMap();
        this.factories = new ArrayList();
        this.hierarchyFactories = new ArrayList();
        this.serializeNulls = false;
        this.dateStyle = 2;
        this.timeStyle = 2;
        this.complexMapKeySerialization = false;
        this.serializeSpecialFloatingPointValues = false;
        this.escapeHtmlChars = true;
        this.prettyPrinting = false;
        this.generateNonExecutableJson = false;
        this.lenient = false;
        this.excluder = gson.excluder;
        this.fieldNamingPolicy = gson.fieldNamingStrategy;
        this.instanceCreators.putAll(gson.instanceCreators);
        this.serializeNulls = gson.serializeNulls;
        this.complexMapKeySerialization = gson.complexMapKeySerialization;
        this.generateNonExecutableJson = gson.generateNonExecutableJson;
        this.escapeHtmlChars = gson.htmlSafe;
        this.prettyPrinting = gson.prettyPrinting;
        this.lenient = gson.lenient;
        this.serializeSpecialFloatingPointValues = gson.serializeSpecialFloatingPointValues;
        this.longSerializationPolicy = gson.longSerializationPolicy;
        this.datePattern = gson.datePattern;
        this.dateStyle = gson.dateStyle;
        this.timeStyle = gson.timeStyle;
        this.factories.addAll(gson.builderFactories);
        this.hierarchyFactories.addAll(gson.builderHierarchyFactories);
    }
    
    public GsonBuilder setVersion(final double n) {
        this.excluder = this.excluder.withVersion(n);
        return this;
    }
    
    public GsonBuilder excludeFieldsWithModifiers(final int... array) {
        this.excluder = this.excluder.withModifiers(array);
        return this;
    }
    
    public GsonBuilder generateNonExecutableJson() {
        this.generateNonExecutableJson = true;
        return this;
    }
    
    public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
        this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
        return this;
    }
    
    public GsonBuilder serializeNulls() {
        this.serializeNulls = true;
        return this;
    }
    
    public GsonBuilder enableComplexMapKeySerialization() {
        this.complexMapKeySerialization = true;
        return this;
    }
    
    public GsonBuilder disableInnerClassSerialization() {
        this.excluder = this.excluder.disableInnerClassSerialization();
        return this;
    }
    
    public GsonBuilder setLongSerializationPolicy(final LongSerializationPolicy longSerializationPolicy) {
        this.longSerializationPolicy = longSerializationPolicy;
        return this;
    }
    
    public GsonBuilder setFieldNamingPolicy(final FieldNamingPolicy fieldNamingPolicy) {
        this.fieldNamingPolicy = fieldNamingPolicy;
        return this;
    }
    
    public GsonBuilder setFieldNamingStrategy(final FieldNamingStrategy fieldNamingPolicy) {
        this.fieldNamingPolicy = fieldNamingPolicy;
        return this;
    }
    
    public GsonBuilder setExclusionStrategies(final ExclusionStrategy... array) {
        while (0 < array.length) {
            this.excluder = this.excluder.withExclusionStrategy(array[0], true, true);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public GsonBuilder addSerializationExclusionStrategy(final ExclusionStrategy exclusionStrategy) {
        this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy, true, false);
        return this;
    }
    
    public GsonBuilder addDeserializationExclusionStrategy(final ExclusionStrategy exclusionStrategy) {
        this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy, false, true);
        return this;
    }
    
    public GsonBuilder setPrettyPrinting() {
        this.prettyPrinting = true;
        return this;
    }
    
    public GsonBuilder setLenient() {
        this.lenient = true;
        return this;
    }
    
    public GsonBuilder disableHtmlEscaping() {
        this.escapeHtmlChars = false;
        return this;
    }
    
    public GsonBuilder setDateFormat(final String datePattern) {
        this.datePattern = datePattern;
        return this;
    }
    
    public GsonBuilder setDateFormat(final int dateStyle) {
        this.dateStyle = dateStyle;
        this.datePattern = null;
        return this;
    }
    
    public GsonBuilder setDateFormat(final int dateStyle, final int timeStyle) {
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
        this.datePattern = null;
        return this;
    }
    
    public GsonBuilder registerTypeAdapter(final Type type, final Object o) {
        $Gson$Preconditions.checkArgument(o instanceof JsonSerializer || o instanceof JsonDeserializer || o instanceof InstanceCreator || o instanceof TypeAdapter);
        if (o instanceof InstanceCreator) {
            this.instanceCreators.put(type, o);
        }
        if (o instanceof JsonSerializer || o instanceof JsonDeserializer) {
            this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(TypeToken.get(type), o));
        }
        if (o instanceof TypeAdapter) {
            this.factories.add(TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter)o));
        }
        return this;
    }
    
    public GsonBuilder registerTypeAdapterFactory(final TypeAdapterFactory typeAdapterFactory) {
        this.factories.add(typeAdapterFactory);
        return this;
    }
    
    public GsonBuilder registerTypeHierarchyAdapter(final Class clazz, final Object o) {
        $Gson$Preconditions.checkArgument(o instanceof JsonSerializer || o instanceof JsonDeserializer || o instanceof TypeAdapter);
        if (o instanceof JsonDeserializer || o instanceof JsonSerializer) {
            this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(clazz, o));
        }
        if (o instanceof TypeAdapter) {
            this.factories.add(TypeAdapters.newTypeHierarchyFactory(clazz, (TypeAdapter)o));
        }
        return this;
    }
    
    public GsonBuilder serializeSpecialFloatingPointValues() {
        this.serializeSpecialFloatingPointValues = true;
        return this;
    }
    
    public Gson create() {
        final ArrayList<Object> list = new ArrayList<Object>(this.factories.size() + this.hierarchyFactories.size() + 3);
        list.addAll(this.factories);
        Collections.reverse(list);
        final ArrayList<Object> list2 = new ArrayList<Object>(this.hierarchyFactories);
        Collections.reverse(list2);
        list.addAll(list2);
        this.addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, list);
        return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.lenient, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, this.datePattern, this.dateStyle, this.timeStyle, this.factories, this.hierarchyFactories, list);
    }
    
    private void addTypeAdaptersForDate(final String s, final int n, final int n2, final List list) {
        DefaultDateTypeAdapter defaultDateTypeAdapter;
        DefaultDateTypeAdapter defaultDateTypeAdapter2;
        DefaultDateTypeAdapter defaultDateTypeAdapter3;
        if (s != null && !"".equals(s.trim())) {
            defaultDateTypeAdapter = new DefaultDateTypeAdapter(Date.class, s);
            defaultDateTypeAdapter2 = new DefaultDateTypeAdapter(Timestamp.class, s);
            defaultDateTypeAdapter3 = new DefaultDateTypeAdapter(java.sql.Date.class, s);
        }
        else {
            if (n == 2 || n2 == 2) {
                return;
            }
            defaultDateTypeAdapter = new DefaultDateTypeAdapter(Date.class, n, n2);
            defaultDateTypeAdapter2 = new DefaultDateTypeAdapter(Timestamp.class, n, n2);
            defaultDateTypeAdapter3 = new DefaultDateTypeAdapter(java.sql.Date.class, n, n2);
        }
        list.add(TypeAdapters.newFactory(Date.class, defaultDateTypeAdapter));
        list.add(TypeAdapters.newFactory(Timestamp.class, defaultDateTypeAdapter2));
        list.add(TypeAdapters.newFactory(java.sql.Date.class, defaultDateTypeAdapter3));
    }
}
