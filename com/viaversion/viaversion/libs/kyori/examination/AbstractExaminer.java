package com.viaversion.viaversion.libs.kyori.examination;

import java.util.stream.*;
import org.jetbrains.annotations.*;
import java.util.function.*;
import java.util.*;

public abstract class AbstractExaminer implements Examiner
{
    @NotNull
    @Override
    public Object examine(@Nullable final Object value) {
        if (value == null) {
            return this.nil();
        }
        if (value instanceof String) {
            return this.examine((String)value);
        }
        if (value instanceof Examinable) {
            return this.examine((Examinable)value);
        }
        if (value instanceof Collection) {
            return this.collection((Collection)value);
        }
        if (value instanceof Map) {
            return this.map((Map)value);
        }
        if (value.getClass().isArray()) {
            final Class<?> componentType = value.getClass().getComponentType();
            if (componentType.isPrimitive()) {
                if (componentType == Boolean.TYPE) {
                    return this.examine((boolean[])value);
                }
                if (componentType == Byte.TYPE) {
                    return this.examine((byte[])value);
                }
                if (componentType == Character.TYPE) {
                    return this.examine((char[])value);
                }
                if (componentType == Double.TYPE) {
                    return this.examine((double[])value);
                }
                if (componentType == Float.TYPE) {
                    return this.examine((float[])value);
                }
                if (componentType == Integer.TYPE) {
                    return this.examine((int[])value);
                }
                if (componentType == Long.TYPE) {
                    return this.examine((long[])value);
                }
                if (componentType == Short.TYPE) {
                    return this.examine((short[])value);
                }
            }
            return this.array((Object[])value);
        }
        if (value instanceof Boolean) {
            return this.examine((boolean)value);
        }
        if (value instanceof Character) {
            return this.examine((char)value);
        }
        if (value instanceof Number) {
            if (value instanceof Byte) {
                return this.examine((byte)value);
            }
            if (value instanceof Double) {
                return this.examine((double)value);
            }
            if (value instanceof Float) {
                return this.examine((float)value);
            }
            if (value instanceof Integer) {
                return this.examine((int)value);
            }
            if (value instanceof Long) {
                return this.examine((long)value);
            }
            if (value instanceof Short) {
                return this.examine((short)value);
            }
        }
        else if (value instanceof BaseStream) {
            if (value instanceof Stream) {
                return this.stream((Stream)value);
            }
            if (value instanceof DoubleStream) {
                return this.stream((DoubleStream)value);
            }
            if (value instanceof IntStream) {
                return this.stream((IntStream)value);
            }
            if (value instanceof LongStream) {
                return this.stream((LongStream)value);
            }
        }
        return this.scalar(value);
    }
    
    @NotNull
    private Object array(final Object[] array) {
        return this.array(array, Arrays.stream(array).map((Function<? super Object, ?>)this::examine));
    }
    
    @NotNull
    protected abstract Object array(final Object[] array, @NotNull final Stream elements);
    
    @NotNull
    private Object collection(@NotNull final Collection collection) {
        return this.collection(collection, collection.stream().map(this::examine));
    }
    
    @NotNull
    protected abstract Object collection(@NotNull final Collection collection, @NotNull final Stream elements);
    
    @NotNull
    @Override
    public Object examine(@NotNull final String name, @NotNull final Stream properties) {
        return this.examinable(name, properties.map(this::lambda$examine$0));
    }
    
    @NotNull
    protected abstract Object examinable(@NotNull final String name, @NotNull final Stream properties);
    
    @NotNull
    private Object map(@NotNull final Map map) {
        return this.map(map, map.entrySet().stream().map(this::lambda$map$1));
    }
    
    @NotNull
    protected abstract Object map(@NotNull final Map map, @NotNull final Stream entries);
    
    @NotNull
    protected abstract Object nil();
    
    @NotNull
    protected abstract Object scalar(@NotNull final Object value);
    
    @NotNull
    protected abstract Object stream(@NotNull final Stream stream);
    
    @NotNull
    protected abstract Object stream(@NotNull final DoubleStream stream);
    
    @NotNull
    protected abstract Object stream(@NotNull final IntStream stream);
    
    @NotNull
    protected abstract Object stream(@NotNull final LongStream stream);
    
    @NotNull
    @Override
    public Object examine(final boolean[] values) {
        if (values == null) {
            return this.nil();
        }
        return this.array(values.length, this::lambda$examine$2);
    }
    
    @NotNull
    @Override
    public Object examine(final byte[] values) {
        if (values == null) {
            return this.nil();
        }
        return this.array(values.length, this::lambda$examine$3);
    }
    
    @NotNull
    @Override
    public Object examine(final char[] values) {
        if (values == null) {
            return this.nil();
        }
        return this.array(values.length, this::lambda$examine$4);
    }
    
    @NotNull
    @Override
    public Object examine(final double[] values) {
        if (values == null) {
            return this.nil();
        }
        return this.array(values.length, this::lambda$examine$5);
    }
    
    @NotNull
    @Override
    public Object examine(final float[] values) {
        if (values == null) {
            return this.nil();
        }
        return this.array(values.length, this::lambda$examine$6);
    }
    
    @NotNull
    @Override
    public Object examine(final int[] values) {
        if (values == null) {
            return this.nil();
        }
        return this.array(values.length, this::lambda$examine$7);
    }
    
    @NotNull
    @Override
    public Object examine(final long[] values) {
        if (values == null) {
            return this.nil();
        }
        return this.array(values.length, this::lambda$examine$8);
    }
    
    @NotNull
    @Override
    public Object examine(final short[] values) {
        if (values == null) {
            return this.nil();
        }
        return this.array(values.length, this::lambda$examine$9);
    }
    
    @NotNull
    protected abstract Object array(final int length, final IntFunction value);
    
    private Object lambda$examine$9(final short[] array, final int n) {
        return this.examine(array[n]);
    }
    
    private Object lambda$examine$8(final long[] array, final int n) {
        return this.examine(array[n]);
    }
    
    private Object lambda$examine$7(final int[] array, final int n) {
        return this.examine(array[n]);
    }
    
    private Object lambda$examine$6(final float[] array, final int n) {
        return this.examine(array[n]);
    }
    
    private Object lambda$examine$5(final double[] array, final int n) {
        return this.examine(array[n]);
    }
    
    private Object lambda$examine$4(final char[] array, final int n) {
        return this.examine(array[n]);
    }
    
    private Object lambda$examine$3(final byte[] array, final int n) {
        return this.examine(array[n]);
    }
    
    private Object lambda$examine$2(final boolean[] array, final int n) {
        return this.examine(array[n]);
    }
    
    private Map.Entry lambda$map$1(final Map.Entry entry) {
        return new AbstractMap.SimpleImmutableEntry(this.examine(entry.getKey()), this.examine(entry.getValue()));
    }
    
    private Map.Entry lambda$examine$0(final ExaminableProperty examinableProperty) {
        return new AbstractMap.SimpleImmutableEntry(examinableProperty.name(), examinableProperty.examine(this));
    }
}
