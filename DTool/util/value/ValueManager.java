package DTool.util.value;

import java.util.*;

public class ValueManager
{
    private ArrayList values;
    public static ValueManager INSTANCE;
    
    static {
        ValueManager.INSTANCE = new ValueManager();
    }
    
    public ValueManager() {
        this.values = new ArrayList();
    }
    
    public void register(final Value value) {
        this.values.add(value);
    }
    
    public ArrayList getValues(final Object o) {
        final ArrayList<Value> list = new ArrayList<Value>();
        for (final Value value : this.values) {
            if (value.getParent() == o) {
                list.add(value);
            }
        }
        return list;
    }
}
