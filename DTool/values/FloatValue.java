package DTool.values;

import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;

public class FloatValue extends Value
{
    private float min;
    private float max;
    private float increment;
    private String unit;
    public boolean anotherShit;
    
    public FloatValue(final String group, final String key, final float n, final float min, final float max, final float increment, final boolean b) {
        this.group = group;
        this.key = key;
        this.value = n;
        this.min = min;
        this.max = max;
        this.increment = increment;
        this.anotherShit = false;
        if (!b) {
            ValueManager.addValue(this);
        }
    }
    
    public FloatValue(final String s, final String s2, final float n, final float n2, final float n3, final float n4) {
        this(s, s2, n, n2, n3, n4, false);
    }
    
    public FloatValue(final String s, final String s2, final float n, final float n2, final float n3, final float n4, final String unit) {
        this(s, s2, n, n2, n3, n4);
        this.unit = unit;
    }
    
    @Override
    public Float getValue() {
        return (Float)this.value;
    }
    
    public void setValue(float n) {
        if (n < this.min) {
            n = this.min;
        }
        if (n > this.max) {
            n = this.max;
        }
        EventManager.call(new EventChangeValue(this.group, this.key, this.value, n));
        this.value = n;
    }
    
    public float getValueState() {
        return this.getValue();
    }
    
    public void setValueState(final float value) {
        this.setValue(value);
    }
    
    public float getDMin() {
        return this.min;
    }
    
    public float getDMax() {
        return this.max;
    }
    
    public float getDIncrement() {
        return this.increment;
    }
    
    @Override
    public Object getValue() {
        return this.getValue();
    }
}
