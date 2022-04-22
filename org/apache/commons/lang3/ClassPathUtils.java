package org.apache.commons.lang3;

public class ClassPathUtils
{
    public static String toFullyQualifiedName(final Class clazz, final String s) {
        Validate.notNull(clazz, "Parameter '%s' must not be null!", "context");
        Validate.notNull(s, "Parameter '%s' must not be null!", "resourceName");
        return toFullyQualifiedName(clazz.getPackage(), s);
    }
    
    public static String toFullyQualifiedName(final Package package1, final String s) {
        Validate.notNull(package1, "Parameter '%s' must not be null!", "context");
        Validate.notNull(s, "Parameter '%s' must not be null!", "resourceName");
        final StringBuilder sb = new StringBuilder();
        sb.append(package1.getName());
        sb.append(".");
        sb.append(s);
        return sb.toString();
    }
    
    public static String toFullyQualifiedPath(final Class clazz, final String s) {
        Validate.notNull(clazz, "Parameter '%s' must not be null!", "context");
        Validate.notNull(s, "Parameter '%s' must not be null!", "resourceName");
        return toFullyQualifiedPath(clazz.getPackage(), s);
    }
    
    public static String toFullyQualifiedPath(final Package package1, final String s) {
        Validate.notNull(package1, "Parameter '%s' must not be null!", "context");
        Validate.notNull(s, "Parameter '%s' must not be null!", "resourceName");
        final StringBuilder sb = new StringBuilder();
        sb.append(package1.getName().replace('.', '/'));
        sb.append("/");
        sb.append(s);
        return sb.toString();
    }
}
