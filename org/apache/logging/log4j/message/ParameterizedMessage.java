package org.apache.logging.log4j.message;

import java.text.*;
import java.util.*;

public class ParameterizedMessage implements Message
{
    public static final String RECURSION_PREFIX = "[...";
    public static final String RECURSION_SUFFIX = "...]";
    public static final String ERROR_PREFIX = "[!!!";
    public static final String ERROR_SEPARATOR = "=>";
    public static final String ERROR_MSG_SEPARATOR = ":";
    public static final String ERROR_SUFFIX = "!!!]";
    private static final long serialVersionUID = -665975803997290697L;
    private static final int HASHVAL = 31;
    private static final char DELIM_START = '{';
    private static final char DELIM_STOP = '}';
    private static final char ESCAPE_CHAR = '\\';
    private final String messagePattern;
    private final String[] stringArgs;
    private transient Object[] argArray;
    private transient String formattedMessage;
    private transient Throwable throwable;
    
    public ParameterizedMessage(final String messagePattern, final String[] stringArgs, final Throwable throwable) {
        this.messagePattern = messagePattern;
        this.stringArgs = stringArgs;
        this.throwable = throwable;
    }
    
    public ParameterizedMessage(final String messagePattern, final Object[] array, final Throwable throwable) {
        this.messagePattern = messagePattern;
        this.throwable = throwable;
        this.stringArgs = this.parseArguments(array);
    }
    
    public ParameterizedMessage(final String messagePattern, final Object[] array) {
        this.messagePattern = messagePattern;
        this.stringArgs = this.parseArguments(array);
    }
    
    public ParameterizedMessage(final String s, final Object o) {
        this(s, new Object[] { o });
    }
    
    public ParameterizedMessage(final String s, final Object o, final Object o2) {
        this(s, new Object[] { o, o2 });
    }
    
    private String[] parseArguments(final Object[] array) {
        if (array == null) {
            return null;
        }
        final int countArgumentPlaceholders = countArgumentPlaceholders(this.messagePattern);
        int length = array.length;
        if (countArgumentPlaceholders < array.length && this.throwable == null && array[array.length - 1] instanceof Throwable) {
            this.throwable = (Throwable)array[array.length - 1];
            --length;
        }
        this.argArray = new Object[length];
        while (0 < length) {
            this.argArray[0] = array[0];
            int n = 0;
            ++n;
        }
        String[] array2;
        if (countArgumentPlaceholders == 1 && this.throwable == null && array.length > 1) {
            array2 = new String[] { deepToString(array) };
        }
        else {
            array2 = new String[length];
            while (0 < array2.length) {
                array2[0] = deepToString(array[0]);
                int n2 = 0;
                ++n2;
            }
        }
        return array2;
    }
    
    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            this.formattedMessage = this.formatMessage(this.messagePattern, this.stringArgs);
        }
        return this.formattedMessage;
    }
    
    @Override
    public String getFormat() {
        return this.messagePattern;
    }
    
    @Override
    public Object[] getParameters() {
        if (this.argArray != null) {
            return this.argArray;
        }
        return this.stringArgs;
    }
    
    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    protected String formatMessage(final String s, final String[] array) {
        return format(s, array);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ParameterizedMessage parameterizedMessage = (ParameterizedMessage)o;
        if (this.messagePattern != null) {
            if (this.messagePattern.equals(parameterizedMessage.messagePattern)) {
                return Arrays.equals(this.stringArgs, parameterizedMessage.stringArgs);
            }
        }
        else if (parameterizedMessage.messagePattern == null) {
            return Arrays.equals(this.stringArgs, parameterizedMessage.stringArgs);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * ((this.messagePattern != null) ? this.messagePattern.hashCode() : 0) + ((this.stringArgs != null) ? Arrays.hashCode(this.stringArgs) : 0);
    }
    
    public static String format(final String s, final Object[] array) {
        if (s == null || array == null || array.length == 0) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            int n4 = 0;
            if (char1 == '\\') {
                int n = 0;
                ++n;
            }
            else if (char1 == '{' && 0 < s.length() - 1 && s.charAt(1) == '}') {
                while (0 < 0) {
                    sb.append('\\');
                    int n2 = 0;
                    ++n2;
                }
                if (false == true) {
                    sb.append('{');
                    sb.append('}');
                }
                else {
                    if (0 < array.length) {
                        sb.append(array[0]);
                    }
                    else {
                        sb.append('{').append('}');
                    }
                    int n3 = 0;
                    ++n3;
                }
                ++n4;
            }
            else {
                if (0 > 0) {
                    while (0 < 0) {
                        sb.append('\\');
                        int n5 = 0;
                        ++n5;
                    }
                }
                sb.append(char1);
            }
            ++n4;
        }
        return sb.toString();
    }
    
    public static int countArgumentPlaceholders(final String s) {
        if (s == null) {
            return 0;
        }
        if (s.indexOf(123) == -1) {
            return 0;
        }
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            int n2 = 0;
            if (char1 == '\\') {
                final boolean b = !false;
            }
            else if (char1 == '{') {
                if (!false && 0 < s.length() - 1 && s.charAt(1) == '}') {
                    int n = 0;
                    ++n;
                    ++n2;
                }
            }
            ++n2;
        }
        return 0;
    }
    
    public static String deepToString(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return (String)o;
        }
        final StringBuilder sb = new StringBuilder();
        recursiveDeepToString(o, sb, new HashSet());
        return sb.toString();
    }
    
    private static void recursiveDeepToString(final Object o, final StringBuilder sb, final Set set) {
        if (o == null) {
            sb.append("null");
            return;
        }
        if (o instanceof String) {
            sb.append(o);
            return;
        }
        final Class<?> class1 = o.getClass();
        if (class1.isArray()) {
            if (class1 == byte[].class) {
                sb.append(Arrays.toString((byte[])o));
            }
            else if (class1 == short[].class) {
                sb.append(Arrays.toString((short[])o));
            }
            else if (class1 == int[].class) {
                sb.append(Arrays.toString((int[])o));
            }
            else if (class1 == long[].class) {
                sb.append(Arrays.toString((long[])o));
            }
            else if (class1 == float[].class) {
                sb.append(Arrays.toString((float[])o));
            }
            else if (class1 == double[].class) {
                sb.append(Arrays.toString((double[])o));
            }
            else if (class1 == boolean[].class) {
                sb.append(Arrays.toString((boolean[])o));
            }
            else if (class1 == char[].class) {
                sb.append(Arrays.toString((char[])o));
            }
            else {
                final String identityToString = identityToString(o);
                if (set.contains(identityToString)) {
                    sb.append("[...").append(identityToString).append("...]");
                }
                else {
                    set.add(identityToString);
                    final Object[] array = (Object[])o;
                    sb.append("[");
                    final Object[] array2 = array;
                    while (0 < array2.length) {
                        final Object o2 = array2[0];
                        if (!false) {
                            sb.append(", ");
                        }
                        recursiveDeepToString(o2, sb, new HashSet(set));
                        int n = 0;
                        ++n;
                    }
                    sb.append("]");
                }
            }
        }
        else if (o instanceof Map) {
            final String identityToString2 = identityToString(o);
            if (set.contains(identityToString2)) {
                sb.append("[...").append(identityToString2).append("...]");
            }
            else {
                set.add(identityToString2);
                final Map map = (Map)o;
                sb.append("{");
                for (final Map.Entry<Object, V> entry : map.entrySet()) {
                    if (!false) {
                        sb.append(", ");
                    }
                    final Object key = entry.getKey();
                    final V value = entry.getValue();
                    recursiveDeepToString(key, sb, new HashSet(set));
                    sb.append("=");
                    recursiveDeepToString(value, sb, new HashSet(set));
                }
                sb.append("}");
            }
        }
        else if (o instanceof Collection) {
            final String identityToString3 = identityToString(o);
            if (set.contains(identityToString3)) {
                sb.append("[...").append(identityToString3).append("...]");
            }
            else {
                set.add(identityToString3);
                final Collection collection = (Collection)o;
                sb.append("[");
                for (final Object next : collection) {
                    if (!false) {
                        sb.append(", ");
                    }
                    recursiveDeepToString(next, sb, new HashSet(set));
                }
                sb.append("]");
            }
        }
        else if (o instanceof Date) {
            sb.append(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format((Date)o));
        }
        else {
            sb.append(o.toString());
        }
    }
    
    public static String identityToString(final Object o) {
        if (o == null) {
            return null;
        }
        return o.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(o));
    }
    
    @Override
    public String toString() {
        return "ParameterizedMessage[messagePattern=" + this.messagePattern + ", stringArgs=" + Arrays.toString(this.stringArgs) + ", throwable=" + this.throwable + "]";
    }
}
