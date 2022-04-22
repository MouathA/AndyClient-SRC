package DTool.util.value;

import java.util.*;

public class Value
{
    private String name;
    private Object defaultValue;
    public Object value;
    private Object parent;
    private ArrayList values;
    
    public Value(final String name, final Object o) {
        this.values = new ArrayList();
        this.name = name;
        this.defaultValue = o;
        this.value = o;
        ValueManager.INSTANCE.register(this);
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public Object setValue(final Object value) {
        return this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Object getDefaultValue() {
        return this.defaultValue;
    }
    
    public Class getGenericClass() {
        return this.defaultValue.getClass();
    }
    
    public Object getParent() {
        return this.parent;
    }
    
    public ArrayList getValues(final Object o) {
        final ArrayList<Value> list = new ArrayList<Value>();
        for (final Value value : list) {
            if (value.getParent() == o) {
                list.add(value);
            }
        }
        return list;
    }
}
