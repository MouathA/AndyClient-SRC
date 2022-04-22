package org.yaml.snakeyaml.introspector;

import org.yaml.snakeyaml.error.*;
import java.util.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.logging.*;

public class PropertySubstitute extends Property
{
    private static final Logger log;
    protected Class targetType;
    private final String readMethod;
    private final String writeMethod;
    private transient Method read;
    private transient Method write;
    private Field field;
    protected Class[] parameters;
    private Property delegate;
    private boolean filler;
    
    public PropertySubstitute(final String s, final Class clazz, final String readMethod, final String writeMethod, final Class... actualTypeArguments) {
        super(s, clazz);
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
        this.setActualTypeArguments(actualTypeArguments);
        this.filler = false;
    }
    
    public PropertySubstitute(final String s, final Class clazz, final Class... array) {
        this(s, clazz, null, (String)null, array);
    }
    
    @Override
    public Class[] getActualTypeArguments() {
        if (this.parameters == null && this.delegate != null) {
            return this.delegate.getActualTypeArguments();
        }
        return this.parameters;
    }
    
    public void setActualTypeArguments(final Class... parameters) {
        if (parameters != null && parameters.length > 0) {
            this.parameters = parameters;
        }
        else {
            this.parameters = null;
        }
    }
    
    @Override
    public void set(final Object o, final Object o2) throws Exception {
        if (this.write != null) {
            if (!this.filler) {
                this.write.invoke(o, o2);
            }
            else if (o2 != null) {
                if (o2 instanceof Collection) {
                    final Iterator<Object> iterator = ((Collection)o2).iterator();
                    while (iterator.hasNext()) {
                        this.write.invoke(o, iterator.next());
                    }
                }
                else if (o2 instanceof Map) {
                    for (final Map.Entry<Object, V> entry : ((Map)o2).entrySet()) {
                        this.write.invoke(o, entry.getKey(), entry.getValue());
                    }
                }
                else if (o2.getClass().isArray()) {
                    while (0 < Array.getLength(o2)) {
                        this.write.invoke(o, Array.get(o2, 0));
                        int n = 0;
                        ++n;
                    }
                }
            }
        }
        else if (this.field != null) {
            this.field.set(o, o2);
        }
        else if (this.delegate != null) {
            this.delegate.set(o, o2);
        }
        else {
            PropertySubstitute.log.warning("No setter/delegate for '" + this.getName() + "' on object " + o);
        }
    }
    
    @Override
    public Object get(final Object o) {
        if (this.read != null) {
            return this.read.invoke(o, new Object[0]);
        }
        if (this.field != null) {
            return this.field.get(o);
        }
        if (this.delegate != null) {
            return this.delegate.get(o);
        }
        throw new YAMLException("No getter or delegate for property '" + this.getName() + "' on object " + o);
    }
    
    @Override
    public List getAnnotations() {
        Annotation[] array = null;
        if (this.read != null) {
            array = this.read.getAnnotations();
        }
        else if (this.field != null) {
            array = this.field.getAnnotations();
        }
        return (array != null) ? Arrays.asList(array) : this.delegate.getAnnotations();
    }
    
    @Override
    public Annotation getAnnotation(final Class clazz) {
        Annotation annotation;
        if (this.read != null) {
            annotation = this.read.getAnnotation((Class<T>)clazz);
        }
        else if (this.field != null) {
            annotation = this.field.getAnnotation((Class<T>)clazz);
        }
        else {
            annotation = this.delegate.getAnnotation(clazz);
        }
        return annotation;
    }
    
    public void setTargetType(final Class targetType) {
        if (this.targetType != targetType) {
            this.targetType = targetType;
            final String name = this.getName();
            for (Class superclass = targetType; superclass != null; superclass = superclass.getSuperclass()) {
                final Field[] declaredFields = superclass.getDeclaredFields();
                while (0 < declaredFields.length) {
                    final Field field = declaredFields[0];
                    if (field.getName().equals(name)) {
                        final int modifiers = field.getModifiers();
                        if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                            field.setAccessible(true);
                            this.field = field;
                            break;
                        }
                        break;
                    }
                    else {
                        int n = 0;
                        ++n;
                    }
                }
            }
            if (this.field == null && PropertySubstitute.log.isLoggable(Level.FINE)) {
                PropertySubstitute.log.fine(String.format("Failed to find field for %s.%s", targetType.getName(), this.getName()));
            }
            if (this.readMethod != null) {
                this.read = this.discoverMethod(targetType, this.readMethod, new Class[0]);
            }
            if (this.writeMethod != null) {
                this.filler = false;
                this.write = this.discoverMethod(targetType, this.writeMethod, this.getType());
                if (this.write == null && this.parameters != null) {
                    this.filler = true;
                    this.write = this.discoverMethod(targetType, this.writeMethod, this.parameters);
                }
            }
        }
    }
    
    private Method discoverMethod(final Class clazz, final String s, final Class... array) {
        for (Class superclass = clazz; superclass != null; superclass = superclass.getSuperclass()) {
            final Method[] declaredMethods = superclass.getDeclaredMethods();
            while (0 < declaredMethods.length) {
                final Method method = declaredMethods[0];
                if (s.equals(method.getName())) {
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == array.length) {
                        while (0 < parameterTypes.length) {
                            if (!parameterTypes[0].isAssignableFrom(array[0])) {}
                            int n = 0;
                            ++n;
                        }
                    }
                }
                int n2 = 0;
                ++n2;
            }
        }
        if (PropertySubstitute.log.isLoggable(Level.FINE)) {
            PropertySubstitute.log.fine(String.format("Failed to find [%s(%d args)] for %s.%s", s, array.length, this.targetType.getName(), this.getName()));
        }
        return null;
    }
    
    @Override
    public String getName() {
        final String name = super.getName();
        if (name != null) {
            return name;
        }
        return (this.delegate != null) ? this.delegate.getName() : null;
    }
    
    @Override
    public Class getType() {
        final Class type = super.getType();
        if (type != null) {
            return type;
        }
        return (this.delegate != null) ? this.delegate.getType() : null;
    }
    
    @Override
    public boolean isReadable() {
        return this.read != null || this.field != null || (this.delegate != null && this.delegate.isReadable());
    }
    
    @Override
    public boolean isWritable() {
        return this.write != null || this.field != null || (this.delegate != null && this.delegate.isWritable());
    }
    
    public void setDelegate(final Property delegate) {
        this.delegate = delegate;
        if (this.writeMethod != null && this.write == null && !this.filler) {
            this.filler = true;
            this.write = this.discoverMethod(this.targetType, this.writeMethod, this.getActualTypeArguments());
        }
    }
    
    static {
        log = Logger.getLogger(PropertySubstitute.class.getPackage().getName());
    }
}
