package optifine;

import java.util.*;

public class PropertiesOrdered extends Properties
{
    private Set keysOrdered;
    
    public PropertiesOrdered() {
        this.keysOrdered = new LinkedHashSet();
    }
    
    @Override
    public synchronized Object put(final Object o, final Object o2) {
        this.keysOrdered.add(o);
        return super.put(o, o2);
    }
    
    @Override
    public Set keySet() {
        this.keysOrdered.retainAll(super.keySet());
        return Collections.unmodifiableSet((Set<?>)this.keysOrdered);
    }
    
    @Override
    public synchronized Enumeration keys() {
        return Collections.enumeration((Collection<Object>)this.keySet());
    }
}
