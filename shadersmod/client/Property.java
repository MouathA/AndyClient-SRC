package shadersmod.client;

import org.apache.commons.lang3.*;
import java.util.*;
import optifine.*;

public class Property
{
    private int[] values;
    private int defaultValue;
    private String propertyName;
    private String[] propertyValues;
    private String userName;
    private String[] userValues;
    private int value;
    
    public Property(final String propertyName, final String[] propertyValues, final String userName, final String[] userValues, final int n) {
        this.values = null;
        this.defaultValue = 0;
        this.propertyName = null;
        this.propertyValues = null;
        this.userName = null;
        this.userValues = null;
        this.value = 0;
        this.propertyName = propertyName;
        this.propertyValues = propertyValues;
        this.userName = userName;
        this.userValues = userValues;
        this.defaultValue = n;
        if (propertyValues.length != userValues.length) {
            throw new IllegalArgumentException("Property and user values have different lengths: " + propertyValues.length + " != " + userValues.length);
        }
        if (n >= 0 && n < propertyValues.length) {
            this.value = n;
            return;
        }
        throw new IllegalArgumentException("Invalid default value: " + n);
    }
    
    public boolean setPropertyValue(final String s) {
        if (s == null) {
            this.value = this.defaultValue;
            return false;
        }
        this.value = ArrayUtils.indexOf(this.propertyValues, s);
        if (this.value >= 0 && this.value < this.propertyValues.length) {
            return true;
        }
        this.value = this.defaultValue;
        return false;
    }
    
    public void nextValue() {
        ++this.value;
        if (this.value < 0 || this.value >= this.propertyValues.length) {
            this.value = 0;
        }
    }
    
    public void setValue(final int value) {
        this.value = value;
        if (this.value < 0 || this.value >= this.propertyValues.length) {
            this.value = this.defaultValue;
        }
    }
    
    public int getValue() {
        return this.value;
    }
    
    public String getUserValue() {
        return this.userValues[this.value];
    }
    
    public String getPropertyValue() {
        return this.propertyValues[this.value];
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public void resetValue() {
        this.value = this.defaultValue;
    }
    
    public boolean loadFrom(final Properties properties) {
        this.resetValue();
        if (properties == null) {
            return false;
        }
        final String property = properties.getProperty(this.propertyName);
        return property != null && this.setPropertyValue(property);
    }
    
    public void saveTo(final Properties properties) {
        if (properties != null) {
            properties.setProperty(this.getPropertyName(), this.getPropertyValue());
        }
    }
    
    @Override
    public String toString() {
        return this.propertyName + "=" + this.getPropertyValue() + " [" + Config.arrayToString(this.propertyValues) + "], value: " + this.value;
    }
}
