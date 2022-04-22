package DTool.values;

import java.util.*;

public class ValueManager
{
    private static final ArrayList values;
    public static final ArrayList apiValues;
    
    static {
        values = new ArrayList();
        apiValues = new ArrayList();
    }
    
    public static void addValue(final Value value) {
        ValueManager.values.add(value);
    }
    
    public static void removeValue(final Value value) {
        ValueManager.values.remove(value);
    }
    
    public static ArrayList getValues() {
        final ArrayList list = new ArrayList(ValueManager.values);
        list.addAll(ValueManager.apiValues);
        return list;
    }
}
