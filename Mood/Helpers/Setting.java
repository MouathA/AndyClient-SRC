package Mood.Helpers;

import java.lang.reflect.*;
import com.google.gson.annotations.*;

public class Setting
{
    private final String name;
    private final String desc;
    private final Type type;
    private final double inc;
    private String[] options;
    private String selectedOption;
    private final double min;
    private final double max;
    @Expose
    private Object value;
    
    public String getDesc() {
        return this.desc;
    }
    
    public Setting(final String name, final Object value, final String desc) {
        this.name = name;
        this.value = value;
        this.type = value.getClass().getGenericSuperclass();
        this.desc = desc;
        if (value instanceof Number) {
            this.inc = 0.5;
            this.min = 1.0;
            this.max = 20.0;
        }
        else {
            this.inc = 0.0;
            this.min = 0.0;
            this.max = 0.0;
        }
    }
    
    public Setting(final String name, final Object value, final String desc, final double inc, final double min, final double max) {
        this.name = name;
        this.value = value;
        this.type = value.getClass().getGenericSuperclass();
        this.desc = desc;
        this.inc = inc;
        this.min = min;
        this.max = max;
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public double getInc() {
        return this.inc;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public void update(final Setting setting) {
        this.value = setting.value;
    }
}
