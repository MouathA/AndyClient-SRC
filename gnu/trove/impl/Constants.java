package gnu.trove.impl;

public class Constants
{
    private static final boolean VERBOSE;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    public static final byte DEFAULT_BYTE_NO_ENTRY_VALUE;
    public static final short DEFAULT_SHORT_NO_ENTRY_VALUE;
    public static final char DEFAULT_CHAR_NO_ENTRY_VALUE;
    public static final int DEFAULT_INT_NO_ENTRY_VALUE;
    public static final long DEFAULT_LONG_NO_ENTRY_VALUE;
    public static final float DEFAULT_FLOAT_NO_ENTRY_VALUE;
    public static final double DEFAULT_DOUBLE_NO_ENTRY_VALUE;
    
    static {
        VERBOSE = (System.getProperty("gnu.trove.verbose", null) != null);
        final String property = System.getProperty("gnu.trove.no_entry.byte", "0");
        if (!"MAX_VALUE".equalsIgnoreCase(property)) {
            if (!"MIN_VALUE".equalsIgnoreCase(property)) {
                Byte.valueOf(property);
            }
        }
        if (Integer.MIN_VALUE <= 127) {
            if (Integer.MIN_VALUE < -128) {}
        }
        DEFAULT_BYTE_NO_ENTRY_VALUE = (byte)Integer.MIN_VALUE;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_BYTE_NO_ENTRY_VALUE: " + Constants.DEFAULT_BYTE_NO_ENTRY_VALUE);
        }
        final String property2 = System.getProperty("gnu.trove.no_entry.short", "0");
        if (!"MAX_VALUE".equalsIgnoreCase(property2)) {
            if (!"MIN_VALUE".equalsIgnoreCase(property2)) {
                Short.valueOf(property2);
            }
        }
        if (Integer.MIN_VALUE <= 32767) {
            if (Integer.MIN_VALUE < -32768) {}
        }
        DEFAULT_SHORT_NO_ENTRY_VALUE = (short)Integer.MIN_VALUE;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_SHORT_NO_ENTRY_VALUE: " + Constants.DEFAULT_SHORT_NO_ENTRY_VALUE);
        }
        final String property3 = System.getProperty("gnu.trove.no_entry.char", "\u0000");
        if (!"MAX_VALUE".equalsIgnoreCase(property3)) {
            if (!"MIN_VALUE".equalsIgnoreCase(property3)) {
                final char c = property3.toCharArray()[0];
            }
        }
        if (Integer.MIN_VALUE <= 65535) {
            if (Integer.MIN_VALUE < 0) {}
        }
        DEFAULT_CHAR_NO_ENTRY_VALUE = (char)Integer.MIN_VALUE;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_CHAR_NO_ENTRY_VALUE: " + (Object)Integer.MIN_VALUE);
        }
        final String property4 = System.getProperty("gnu.trove.no_entry.int", "0");
        if (!"MAX_VALUE".equalsIgnoreCase(property4)) {
            if (!"MIN_VALUE".equalsIgnoreCase(property4)) {
                Integer.valueOf(property4);
            }
        }
        DEFAULT_INT_NO_ENTRY_VALUE = Integer.MIN_VALUE;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_INT_NO_ENTRY_VALUE: " + Constants.DEFAULT_INT_NO_ENTRY_VALUE);
        }
        final String property5 = System.getProperty("gnu.trove.no_entry.long", "0");
        long longValue;
        if ("MAX_VALUE".equalsIgnoreCase(property5)) {
            longValue = Long.MAX_VALUE;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property5)) {
            longValue = Long.MIN_VALUE;
        }
        else {
            longValue = Long.valueOf(property5);
        }
        DEFAULT_LONG_NO_ENTRY_VALUE = longValue;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_LONG_NO_ENTRY_VALUE: " + Constants.DEFAULT_LONG_NO_ENTRY_VALUE);
        }
        final String property6 = System.getProperty("gnu.trove.no_entry.float", "0");
        float floatValue;
        if ("MAX_VALUE".equalsIgnoreCase(property6)) {
            floatValue = Float.MAX_VALUE;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property6)) {
            floatValue = Float.MIN_VALUE;
        }
        else if ("MIN_NORMAL".equalsIgnoreCase(property6)) {
            floatValue = Float.MIN_NORMAL;
        }
        else if ("NEGATIVE_INFINITY".equalsIgnoreCase(property6)) {
            floatValue = Float.NEGATIVE_INFINITY;
        }
        else if ("POSITIVE_INFINITY".equalsIgnoreCase(property6)) {
            floatValue = Float.POSITIVE_INFINITY;
        }
        else {
            floatValue = Float.valueOf(property6);
        }
        DEFAULT_FLOAT_NO_ENTRY_VALUE = floatValue;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_FLOAT_NO_ENTRY_VALUE: " + Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE);
        }
        final String property7 = System.getProperty("gnu.trove.no_entry.double", "0");
        double doubleValue;
        if ("MAX_VALUE".equalsIgnoreCase(property7)) {
            doubleValue = Double.MAX_VALUE;
        }
        else if ("MIN_VALUE".equalsIgnoreCase(property7)) {
            doubleValue = Double.MIN_VALUE;
        }
        else if ("MIN_NORMAL".equalsIgnoreCase(property7)) {
            doubleValue = Double.MIN_NORMAL;
        }
        else if ("NEGATIVE_INFINITY".equalsIgnoreCase(property7)) {
            doubleValue = Double.NEGATIVE_INFINITY;
        }
        else if ("POSITIVE_INFINITY".equalsIgnoreCase(property7)) {
            doubleValue = Double.POSITIVE_INFINITY;
        }
        else {
            doubleValue = Double.valueOf(property7);
        }
        DEFAULT_DOUBLE_NO_ENTRY_VALUE = doubleValue;
        if (Constants.VERBOSE) {
            System.out.println("DEFAULT_DOUBLE_NO_ENTRY_VALUE: " + Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE);
        }
    }
}
