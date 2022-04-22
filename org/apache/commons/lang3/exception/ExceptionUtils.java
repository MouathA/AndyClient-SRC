package org.apache.commons.lang3.exception;

import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.*;

public class ExceptionUtils
{
    static final String WRAPPED_MARKER = " [wrapped] ";
    
    @Deprecated
    public static String[] getDefaultCauseMethodNames() {
        return (String[])ArrayUtils.clone(ExceptionUtils.CAUSE_METHOD_NAMES);
    }
    
    @Deprecated
    public static Throwable getCause(final Throwable t) {
        return getCause(t, ExceptionUtils.CAUSE_METHOD_NAMES);
    }
    
    @Deprecated
    public static Throwable getCause(final Throwable t, String[] cause_METHOD_NAMES) {
        if (t == null) {
            return null;
        }
        if (cause_METHOD_NAMES == null) {
            cause_METHOD_NAMES = ExceptionUtils.CAUSE_METHOD_NAMES;
        }
        final String[] array = cause_METHOD_NAMES;
        while (0 < array.length) {
            final String s = array[0];
            if (s != null) {
                final Throwable causeUsingMethodName = getCauseUsingMethodName(t, s);
                if (causeUsingMethodName != null) {
                    return causeUsingMethodName;
                }
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static Throwable getRootCause(final Throwable t) {
        final List throwableList = getThrowableList(t);
        return (throwableList.size() < 2) ? null : throwableList.get(throwableList.size() - 1);
    }
    
    private static Throwable getCauseUsingMethodName(final Throwable t, final String s) {
        final Method method = t.getClass().getMethod(s, (Class<?>[])new Class[0]);
        if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
            return (Throwable)method.invoke(t, new Object[0]);
        }
        return null;
    }
    
    public static int getThrowableCount(final Throwable t) {
        return getThrowableList(t).size();
    }
    
    public static Throwable[] getThrowables(final Throwable t) {
        final List throwableList = getThrowableList(t);
        return throwableList.toArray(new Throwable[throwableList.size()]);
    }
    
    public static List getThrowableList(Throwable cause) {
        ArrayList<Throwable> list;
        for (list = new ArrayList<Throwable>(); cause != null && !list.contains(cause); cause = getCause(cause)) {
            list.add(cause);
        }
        return list;
    }
    
    public static int indexOfThrowable(final Throwable t, final Class clazz) {
        return indexOf(t, clazz, 0, false);
    }
    
    public static int indexOfThrowable(final Throwable t, final Class clazz, final int n) {
        return indexOf(t, clazz, n, false);
    }
    
    public static int indexOfType(final Throwable t, final Class clazz) {
        return indexOf(t, clazz, 0, true);
    }
    
    public static int indexOfType(final Throwable t, final Class clazz, final int n) {
        return indexOf(t, clazz, n, true);
    }
    
    private static int indexOf(final Throwable t, final Class clazz, final int n, final boolean b) {
        if (t == null || clazz == null) {
            return -1;
        }
        if (0 < 0) {}
        final Throwable[] throwables = getThrowables(t);
        if (0 >= throwables.length) {
            return -1;
        }
        if (b) {
            while (0 < throwables.length) {
                if (clazz.isAssignableFrom(throwables[0].getClass())) {
                    return 0;
                }
                int n2 = 0;
                ++n2;
            }
        }
        else {
            while (0 < throwables.length) {
                if (clazz.equals(throwables[0].getClass())) {
                    return 0;
                }
                int n2 = 0;
                ++n2;
            }
        }
        return -1;
    }
    
    public static void printRootCauseStackTrace(final Throwable t) {
        printRootCauseStackTrace(t, System.err);
    }
    
    public static void printRootCauseStackTrace(final Throwable t, final PrintStream printStream) {
        if (t == null) {
            return;
        }
        if (printStream == null) {
            throw new IllegalArgumentException("The PrintStream must not be null");
        }
        final String[] rootCauseStackTrace = getRootCauseStackTrace(t);
        while (0 < rootCauseStackTrace.length) {
            printStream.println(rootCauseStackTrace[0]);
            int n = 0;
            ++n;
        }
        printStream.flush();
    }
    
    public static void printRootCauseStackTrace(final Throwable t, final PrintWriter printWriter) {
        if (t == null) {
            return;
        }
        if (printWriter == null) {
            throw new IllegalArgumentException("The PrintWriter must not be null");
        }
        final String[] rootCauseStackTrace = getRootCauseStackTrace(t);
        while (0 < rootCauseStackTrace.length) {
            printWriter.println(rootCauseStackTrace[0]);
            int n = 0;
            ++n;
        }
        printWriter.flush();
    }
    
    public static String[] getRootCauseStackTrace(final Throwable t) {
        if (t == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final Throwable[] throwables = getThrowables(t);
        final int length = throwables.length;
        final ArrayList<String> list = new ArrayList<String>();
        List list2 = getStackFrameList(throwables[length - 1]);
        int n = length;
        while (--n >= 0) {
            final List list3 = list2;
            if (n != 0) {
                list2 = getStackFrameList(throwables[n - 1]);
                removeCommonFrames(list3, list2);
            }
            if (n == length - 1) {
                list.add(throwables[n].toString());
            }
            else {
                list.add(" [wrapped] " + throwables[n].toString());
            }
            while (0 < list3.size()) {
                list.add(list3.get(0));
                int n2 = 0;
                ++n2;
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static void removeCommonFrames(final List list, final List list2) {
        if (list == null || list2 == null) {
            throw new IllegalArgumentException("The List must not be null");
        }
        for (int n = list.size() - 1, n2 = list2.size() - 1; n >= 0 && n2 >= 0; --n, --n2) {
            if (list.get(n).equals(list2.get(n2))) {
                list.remove(n);
            }
        }
    }
    
    public static String getStackTrace(final Throwable t) {
        final StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.getBuffer().toString();
    }
    
    public static String[] getStackFrames(final Throwable t) {
        if (t == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return getStackFrames(getStackTrace(t));
    }
    
    static String[] getStackFrames(final String s) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, SystemUtils.LINE_SEPARATOR);
        final ArrayList<String> list = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list.toArray(new String[list.size()]);
    }
    
    static List getStackFrameList(final Throwable t) {
        final StringTokenizer stringTokenizer = new StringTokenizer(getStackTrace(t), SystemUtils.LINE_SEPARATOR);
        final ArrayList<String> list = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            final int index = nextToken.indexOf("at");
            if (index != -1 && nextToken.substring(0, index).trim().isEmpty()) {
                list.add(nextToken);
            }
            else {
                if (true) {
                    break;
                }
                continue;
            }
        }
        return list;
    }
    
    public static String getMessage(final Throwable t) {
        if (t == null) {
            return "";
        }
        return ClassUtils.getShortClassName(t, null) + ": " + StringUtils.defaultString(t.getMessage());
    }
    
    public static String getRootCauseMessage(final Throwable t) {
        final Throwable rootCause = getRootCause(t);
        return getMessage((rootCause == null) ? t : rootCause);
    }
    
    static {
        ExceptionUtils.CAUSE_METHOD_NAMES = new String[] { "getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable" };
    }
}
