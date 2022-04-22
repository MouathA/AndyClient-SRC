package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.reflect.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.annotations.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public final class Excluder implements TypeAdapterFactory, Cloneable
{
    private static final double IGNORE_VERSIONS = -1.0;
    public static final Excluder DEFAULT;
    private double version;
    private int modifiers;
    private boolean serializeInnerClasses;
    private boolean requireExpose;
    private List serializationStrategies;
    private List deserializationStrategies;
    
    public Excluder() {
        this.version = -1.0;
        this.modifiers = 136;
        this.serializeInnerClasses = true;
        this.serializationStrategies = Collections.emptyList();
        this.deserializationStrategies = Collections.emptyList();
    }
    
    @Override
    protected Excluder clone() {
        return (Excluder)super.clone();
    }
    
    public Excluder withVersion(final double version) {
        final Excluder clone = this.clone();
        clone.version = version;
        return clone;
    }
    
    public Excluder withModifiers(final int... array) {
        final Excluder clone = this.clone();
        clone.modifiers = 0;
        while (0 < array.length) {
            final int n = array[0];
            final Excluder excluder = clone;
            excluder.modifiers |= n;
            int n2 = 0;
            ++n2;
        }
        return clone;
    }
    
    public Excluder disableInnerClassSerialization() {
        final Excluder clone = this.clone();
        clone.serializeInnerClasses = false;
        return clone;
    }
    
    public Excluder excludeFieldsWithoutExposeAnnotation() {
        final Excluder clone = this.clone();
        clone.requireExpose = true;
        return clone;
    }
    
    public Excluder withExclusionStrategy(final ExclusionStrategy exclusionStrategy, final boolean b, final boolean b2) {
        final Excluder clone = this.clone();
        if (b) {
            (clone.serializationStrategies = new ArrayList(this.serializationStrategies)).add(exclusionStrategy);
        }
        if (b2) {
            (clone.deserializationStrategies = new ArrayList(this.deserializationStrategies)).add(exclusionStrategy);
        }
        return clone;
    }
    
    @Override
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final Class rawType = typeToken.getRawType();
        final boolean excludeClassChecks = this.excludeClassChecks(rawType);
        final boolean b = excludeClassChecks || this.excludeClassInStrategy(rawType, true);
        final boolean b2 = excludeClassChecks || this.excludeClassInStrategy(rawType, false);
        if (!b && !b2) {
            return null;
        }
        return new TypeAdapter(b2, b, gson, typeToken) {
            private TypeAdapter delegate;
            final boolean val$skipDeserialize;
            final boolean val$skipSerialize;
            final Gson val$gson;
            final TypeToken val$type;
            final Excluder this$0;
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                if (this.val$skipDeserialize) {
                    jsonReader.skipValue();
                    return null;
                }
                return this.delegate().read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                if (this.val$skipSerialize) {
                    jsonWriter.nullValue();
                    return;
                }
                this.delegate().write(jsonWriter, o);
            }
            
            private TypeAdapter delegate() {
                final TypeAdapter delegate = this.delegate;
                return (delegate != null) ? delegate : (this.delegate = this.val$gson.getDelegateAdapter(this.this$0, this.val$type));
            }
        };
    }
    
    public boolean excludeField(final Field field, final boolean b) {
        if ((this.modifiers & field.getModifiers()) != 0x0) {
            return true;
        }
        if (this.version != -1.0 && !this.isValidVersion(field.getAnnotation(Since.class), field.getAnnotation(Until.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        Label_0109: {
            if (this.requireExpose) {
                final Expose expose = field.getAnnotation(Expose.class);
                if (expose != null) {
                    if (b) {
                        if (expose.serialize()) {
                            break Label_0109;
                        }
                    }
                    else if (expose.deserialize()) {
                        break Label_0109;
                    }
                }
                return true;
            }
        }
        if (!this.serializeInnerClasses && this.isInnerClass(field.getType())) {
            return true;
        }
        if (this.isAnonymousOrLocal(field.getType())) {
            return true;
        }
        final List list = b ? this.serializationStrategies : this.deserializationStrategies;
        if (!list.isEmpty()) {
            final FieldAttributes fieldAttributes = new FieldAttributes(field);
            final Iterator<ExclusionStrategy> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().shouldSkipField(fieldAttributes)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean excludeClassChecks(final Class clazz) {
        return (this.version != -1.0 && !this.isValidVersion(clazz.getAnnotation(Since.class), clazz.getAnnotation(Until.class))) || (!this.serializeInnerClasses && this.isInnerClass(clazz)) || this.isAnonymousOrLocal(clazz);
    }
    
    public boolean excludeClass(final Class clazz, final boolean b) {
        return this.excludeClassChecks(clazz) || this.excludeClassInStrategy(clazz, b);
    }
    
    private boolean excludeClassInStrategy(final Class clazz, final boolean b) {
        final Iterator<ExclusionStrategy> iterator = (b ? this.serializationStrategies : this.deserializationStrategies).iterator();
        while (iterator.hasNext()) {
            if (iterator.next().shouldSkipClass(clazz)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isAnonymousOrLocal(final Class clazz) {
        return !Enum.class.isAssignableFrom(clazz) && (clazz.isAnonymousClass() || clazz.isLocalClass());
    }
    
    private boolean isInnerClass(final Class clazz) {
        return clazz.isMemberClass() && !this.isStatic(clazz);
    }
    
    private boolean isStatic(final Class clazz) {
        return (clazz.getModifiers() & 0x8) != 0x0;
    }
    
    private boolean isValidVersion(final Since since, final Until until) {
        return this.isValidSince(since) && this.isValidUntil(until);
    }
    
    private boolean isValidSince(final Since since) {
        return since == null || since.value() <= this.version;
    }
    
    private boolean isValidUntil(final Until until) {
        return until == null || until.value() > this.version;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static {
        DEFAULT = new Excluder();
    }
}
