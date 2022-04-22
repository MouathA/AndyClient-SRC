package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.*;
import org.apache.logging.log4j.core.helpers.*;
import java.util.*;
import java.lang.reflect.*;

@Converter(autoApply = false)
public class ThrowableAttributeConverter implements AttributeConverter
{
    private static final int CAUSED_BY_STRING_LENGTH = 10;
    private static final Field THROWABLE_CAUSE;
    private static final Field THROWABLE_MESSAGE;
    
    public String convertToDatabaseColumn(final Throwable t) {
        if (t == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        this.convertThrowable(sb, t);
        return sb.toString();
    }
    
    private void convertThrowable(final StringBuilder sb, final Throwable t) {
        sb.append(t.toString()).append('\n');
        final StackTraceElement[] stackTrace = t.getStackTrace();
        while (0 < stackTrace.length) {
            sb.append("\tat ").append(stackTrace[0]).append('\n');
            int n = 0;
            ++n;
        }
        if (t.getCause() != null) {
            sb.append("Caused by ");
            this.convertThrowable(sb, t.getCause());
        }
    }
    
    public Throwable convertToEntityAttribute(final String s) {
        if (Strings.isEmpty(s)) {
            return null;
        }
        return this.convertString(Arrays.asList(s.split("(\n|\r\n)")).listIterator(), false);
    }
    
    private Throwable convertString(final ListIterator listIterator, final boolean b) {
        String substring = listIterator.next();
        if (b) {
            substring = substring.substring(10);
        }
        final int index = substring.indexOf(":");
        String trim = null;
        String substring2;
        if (index > 1) {
            substring2 = substring.substring(0, index);
            if (substring.length() > index + 1) {
                trim = substring.substring(index + 1).trim();
            }
        }
        else {
            substring2 = substring;
        }
        final ArrayList<StackTraceElement> list = new ArrayList<StackTraceElement>();
        Throwable convertString = null;
        while (listIterator.hasNext()) {
            final String s = listIterator.next();
            if (s.startsWith("Caused by ")) {
                listIterator.previous();
                convertString = this.convertString(listIterator, true);
                break;
            }
            list.add(StackTraceElementAttributeConverter.convertString(s.trim().substring(3).trim()));
        }
        return this.getThrowable(substring2, trim, convertString, list.toArray(new StackTraceElement[list.size()]));
    }
    
    private Throwable getThrowable(final String s, final String s2, final Throwable t, final StackTraceElement[] stackTrace) {
        final Class<?> forName = Class.forName(s);
        if (!Throwable.class.isAssignableFrom(forName)) {
            return null;
        }
        Throwable t2;
        if (s2 != null && t != null) {
            t2 = this.getThrowable(forName, s2, t);
            if (t2 == null) {
                t2 = this.getThrowable(forName, t);
                if (t2 == null) {
                    t2 = this.getThrowable(forName, s2);
                    if (t2 == null) {
                        t2 = this.getThrowable(forName);
                        if (t2 != null) {
                            ThrowableAttributeConverter.THROWABLE_MESSAGE.set(t2, s2);
                            ThrowableAttributeConverter.THROWABLE_CAUSE.set(t2, t);
                        }
                    }
                    else {
                        ThrowableAttributeConverter.THROWABLE_CAUSE.set(t2, t);
                    }
                }
                else {
                    ThrowableAttributeConverter.THROWABLE_MESSAGE.set(t2, s2);
                }
            }
        }
        else if (t != null) {
            t2 = this.getThrowable(forName, t);
            if (t2 == null) {
                t2 = this.getThrowable(forName);
                if (t2 != null) {
                    ThrowableAttributeConverter.THROWABLE_CAUSE.set(t2, t);
                }
            }
        }
        else if (s2 != null) {
            t2 = this.getThrowable(forName, s2);
            if (t2 == null) {
                t2 = this.getThrowable(forName);
                if (t2 != null) {
                    ThrowableAttributeConverter.THROWABLE_MESSAGE.set(t2, t);
                }
            }
        }
        else {
            t2 = this.getThrowable(forName);
        }
        if (t2 == null) {
            return null;
        }
        t2.setStackTrace(stackTrace);
        return t2;
    }
    
    private Throwable getThrowable(final Class clazz, final String s, final Throwable t) {
        final Constructor[] array = clazz.getConstructors();
        while (0 < array.length) {
            final Constructor constructor = array[0];
            final Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 2) {
                if (String.class == parameterTypes[0] && Throwable.class.isAssignableFrom(parameterTypes[1])) {
                    return constructor.newInstance(s, t);
                }
                if (String.class == parameterTypes[1] && Throwable.class.isAssignableFrom(parameterTypes[0])) {
                    return constructor.newInstance(t, s);
                }
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    private Throwable getThrowable(final Class clazz, final Throwable t) {
        final Constructor[] array = clazz.getConstructors();
        while (0 < array.length) {
            final Constructor constructor = array[0];
            final Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 1 && Throwable.class.isAssignableFrom(parameterTypes[0])) {
                return constructor.newInstance(t);
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    private Throwable getThrowable(final Class clazz, final String s) {
        return clazz.getConstructor(String.class).newInstance(s);
    }
    
    private Throwable getThrowable(final Class clazz) {
        return clazz.newInstance();
    }
    
    public Object convertToEntityAttribute(final Object o) {
        return this.convertToEntityAttribute((String)o);
    }
    
    public Object convertToDatabaseColumn(final Object o) {
        return this.convertToDatabaseColumn((Throwable)o);
    }
    
    static {
        (THROWABLE_CAUSE = Throwable.class.getDeclaredField("cause")).setAccessible(true);
        (THROWABLE_MESSAGE = Throwable.class.getDeclaredField("detailMessage")).setAccessible(true);
    }
}
