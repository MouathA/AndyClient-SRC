package DTool.values;

public class Value
{
    protected String group;
    protected String key;
    protected Object value;
    
    public Object getValue() {
        return this.value;
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
}
