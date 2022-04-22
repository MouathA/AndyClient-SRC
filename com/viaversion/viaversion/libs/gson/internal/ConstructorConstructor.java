package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.internal.reflect.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.concurrent.*;
import java.util.*;

public final class ConstructorConstructor
{
    private final Map instanceCreators;
    private final ReflectionAccessor accessor;
    
    public ConstructorConstructor(final Map instanceCreators) {
        this.accessor = ReflectionAccessor.getInstance();
        this.instanceCreators = instanceCreators;
    }
    
    public ObjectConstructor get(final TypeToken typeToken) {
        final Type type = typeToken.getType();
        final Class rawType = typeToken.getRawType();
        final InstanceCreator instanceCreator = this.instanceCreators.get(type);
        if (instanceCreator != null) {
            return new ObjectConstructor(instanceCreator, type) {
                final InstanceCreator val$typeCreator;
                final Type val$type;
                final ConstructorConstructor this$0;
                
                @Override
                public Object construct() {
                    return this.val$typeCreator.createInstance(this.val$type);
                }
            };
        }
        final InstanceCreator instanceCreator2 = this.instanceCreators.get(rawType);
        if (instanceCreator2 != null) {
            return new ObjectConstructor(instanceCreator2, type) {
                final InstanceCreator val$rawTypeCreator;
                final Type val$type;
                final ConstructorConstructor this$0;
                
                @Override
                public Object construct() {
                    return this.val$rawTypeCreator.createInstance(this.val$type);
                }
            };
        }
        final ObjectConstructor defaultConstructor = this.newDefaultConstructor(rawType);
        if (defaultConstructor != null) {
            return defaultConstructor;
        }
        final ObjectConstructor defaultImplementationConstructor = this.newDefaultImplementationConstructor(type, rawType);
        if (defaultImplementationConstructor != null) {
            return defaultImplementationConstructor;
        }
        return this.newUnsafeAllocator(type, rawType);
    }
    
    private ObjectConstructor newDefaultConstructor(final Class clazz) {
        final Constructor declaredConstructor = clazz.getDeclaredConstructor((Class[])new Class[0]);
        if (!declaredConstructor.isAccessible()) {
            this.accessor.makeAccessible(declaredConstructor);
        }
        return new ObjectConstructor((Constructor)declaredConstructor) {
            final Constructor val$constructor;
            final ConstructorConstructor this$0;
            
            @Override
            public Object construct() {
                return this.val$constructor.newInstance((Object[])null);
            }
        };
    }
    
    private ObjectConstructor newDefaultImplementationConstructor(final Type type, final Class clazz) {
        if (Collection.class.isAssignableFrom(clazz)) {
            if (SortedSet.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor() {
                    final ConstructorConstructor this$0;
                    
                    @Override
                    public Object construct() {
                        return new TreeSet();
                    }
                };
            }
            if (EnumSet.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor(type) {
                    final Type val$type;
                    final ConstructorConstructor this$0;
                    
                    @Override
                    public Object construct() {
                        if (!(this.val$type instanceof ParameterizedType)) {
                            throw new JsonIOException("Invalid EnumSet type: " + this.val$type.toString());
                        }
                        final Type type = ((ParameterizedType)this.val$type).getActualTypeArguments()[0];
                        if (type instanceof Class) {
                            return EnumSet.noneOf((Class<Enum>)type);
                        }
                        throw new JsonIOException("Invalid EnumSet type: " + this.val$type.toString());
                    }
                };
            }
            if (Set.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor() {
                    final ConstructorConstructor this$0;
                    
                    @Override
                    public Object construct() {
                        return new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor() {
                    final ConstructorConstructor this$0;
                    
                    @Override
                    public Object construct() {
                        return new ArrayDeque();
                    }
                };
            }
            return new ObjectConstructor() {
                final ConstructorConstructor this$0;
                
                @Override
                public Object construct() {
                    return new ArrayList();
                }
            };
        }
        else {
            if (!Map.class.isAssignableFrom(clazz)) {
                return null;
            }
            if (ConcurrentNavigableMap.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor() {
                    final ConstructorConstructor this$0;
                    
                    @Override
                    public Object construct() {
                        return new ConcurrentSkipListMap();
                    }
                };
            }
            if (ConcurrentMap.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor() {
                    final ConstructorConstructor this$0;
                    
                    @Override
                    public Object construct() {
                        return new ConcurrentHashMap();
                    }
                };
            }
            if (SortedMap.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor() {
                    final ConstructorConstructor this$0;
                    
                    @Override
                    public Object construct() {
                        return new TreeMap();
                    }
                };
            }
            if (type instanceof ParameterizedType && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)type).getActualTypeArguments()[0]).getRawType())) {
                return new ObjectConstructor() {
                    final ConstructorConstructor this$0;
                    
                    @Override
                    public Object construct() {
                        return new LinkedHashMap();
                    }
                };
            }
            return new ObjectConstructor() {
                final ConstructorConstructor this$0;
                
                @Override
                public Object construct() {
                    return new LinkedTreeMap();
                }
            };
        }
    }
    
    private ObjectConstructor newUnsafeAllocator(final Type type, final Class clazz) {
        return new ObjectConstructor(clazz, type) {
            private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();
            final Class val$rawType;
            final Type val$type;
            final ConstructorConstructor this$0;
            
            @Override
            public Object construct() {
                return this.unsafeAllocator.newInstance(this.val$rawType);
            }
        };
    }
    
    @Override
    public String toString() {
        return this.instanceCreators.toString();
    }
}
