package DTool.util.value;

import java.math.*;

public class RestrictedValue extends Value
{
    private Number min;
    public Number max;
    
    public RestrictedValue(final String s, final Number n, final Number min, final Number max) {
        super(s, n);
        this.min = min;
        this.max = max;
    }
    
    public Number getMin() {
        return this.min;
    }
    
    public Number getMax() {
        return this.max;
    }
    
    public static void increase(final RestrictedValue restrictedValue) {
        final Class genericClass = restrictedValue.getGenericClass();
        if (genericClass.getName().toLowerCase().contains("double")) {
            final Double value = (double)restrictedValue.getValue() + 0.1;
            if (value <= (double)restrictedValue.getMax()) {
                restrictedValue.setValue(round(value, 1));
            }
        }
        else if (genericClass.getName().toLowerCase().contains("float")) {
            final Float value2 = (float)restrictedValue.getValue() + 0.1f;
            if (value2 <= (float)restrictedValue.getMax()) {
                restrictedValue.setValue(round(value2, 1));
            }
        }
        else if (genericClass.getName().toLowerCase().contains("int")) {
            final Integer value3 = (int)restrictedValue.getValue() + 1;
            if (value3 <= (int)restrictedValue.getMax()) {
                restrictedValue.setValue(value3);
            }
        }
    }
    
    public static void descrease(final RestrictedValue restrictedValue) {
        final Class genericClass = restrictedValue.getGenericClass();
        if (genericClass.getName().toLowerCase().contains("double")) {
            final Double value = (double)restrictedValue.getValue() - 0.1;
            if (value >= (double)restrictedValue.getMin()) {
                restrictedValue.setValue(round(value, 1));
            }
        }
        else if (genericClass.getName().toLowerCase().contains("float")) {
            final Float value2 = (float)restrictedValue.getValue() - 0.1f;
            if (value2 >= (float)restrictedValue.getMin()) {
                restrictedValue.setValue(round(value2, 1));
            }
        }
        else if (genericClass.getName().toLowerCase().contains("int")) {
            final Integer value3 = (int)restrictedValue.getValue() - 1;
            if (value3 >= (int)restrictedValue.getMin()) {
                restrictedValue.setValue(value3);
            }
        }
    }
    
    public static void fromNumber(final Number n, final RestrictedValue restrictedValue) {
        final Class genericClass = restrictedValue.getGenericClass();
        if (genericClass.getName().toLowerCase().contains("double")) {
            final Double value = n.doubleValue();
            if (value >= (double)restrictedValue.getMin()) {
                restrictedValue.setValue(round(value, 1));
            }
        }
        else if (genericClass.getName().toLowerCase().contains("float")) {
            final Float value2 = n.floatValue() - 0.1f;
            if (value2 >= (float)restrictedValue.getMin()) {
                restrictedValue.setValue(round(value2, 1));
            }
        }
        else if (genericClass.getName().toLowerCase().contains("int")) {
            final Integer value3 = (int)n.doubleValue() - 1;
            if (value3 >= (int)restrictedValue.getMin()) {
                restrictedValue.setValue(value3);
            }
        }
    }
    
    public static Number fromString(final String s, final RestrictedValue restrictedValue) {
        final Class genericClass = restrictedValue.getGenericClass();
        if (genericClass.getName().toLowerCase().contains("double")) {
            return Double.parseDouble(s);
        }
        if (genericClass.getName().toLowerCase().contains("float")) {
            return Float.parseFloat(s);
        }
        if (genericClass.getName().toLowerCase().contains("int")) {
            return Integer.parseInt(s);
        }
        return null;
    }
    
    private static double round(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    private static float round(final float n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).floatValue();
    }
}
