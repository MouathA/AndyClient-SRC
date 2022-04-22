package DTool.values;

public class BooleanValue extends Value
{
    public BooleanValue(final String group, final String key, final Boolean value, final boolean b) {
        this.group = group;
        this.key = key;
        this.value = value;
        if (!b) {
            ValueManager.addValue(this);
        }
    }
    
    public BooleanValue(final String s, final String s2, final Boolean b) {
        this(s, s2, b, false);
    }
    
    @Override
    public Boolean getValue() {
        return (Boolean)this.value;
    }
    
    @Override
    public void setValue(final Object value) {
        super.setValue(value);
    }
    
    public boolean getValueState() {
        return this.getValue();
    }
    
    @Override
    public Object getValue() {
        return this.getValue();
    }
}
