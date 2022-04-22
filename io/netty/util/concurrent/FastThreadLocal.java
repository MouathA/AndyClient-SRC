package io.netty.util.concurrent;

import io.netty.util.internal.*;
import java.util.*;

public class FastThreadLocal
{
    private static final int variablesToRemoveIndex;
    private final int index;
    
    public static void removeAll() {
        final InternalThreadLocalMap ifSet = InternalThreadLocalMap.getIfSet();
        if (ifSet == null) {
            return;
        }
        final Object indexedVariable = ifSet.indexedVariable(FastThreadLocal.variablesToRemoveIndex);
        if (indexedVariable != null && indexedVariable != InternalThreadLocalMap.UNSET) {
            final Set set = (Set)indexedVariable;
            final FastThreadLocal[] array = set.toArray(new FastThreadLocal[set.size()]);
            while (0 < array.length) {
                array[0].remove(ifSet);
                int n = 0;
                ++n;
            }
        }
    }
    
    public static int size() {
        final InternalThreadLocalMap ifSet = InternalThreadLocalMap.getIfSet();
        if (ifSet == null) {
            return 0;
        }
        return ifSet.size();
    }
    
    public static void destroy() {
    }
    
    private static void addToVariablesToRemove(final InternalThreadLocalMap internalThreadLocalMap, final FastThreadLocal fastThreadLocal) {
        final Object indexedVariable = internalThreadLocalMap.indexedVariable(FastThreadLocal.variablesToRemoveIndex);
        Set<FastThreadLocal> setFromMap;
        if (indexedVariable == InternalThreadLocalMap.UNSET || indexedVariable == null) {
            setFromMap = Collections.newSetFromMap(new IdentityHashMap<FastThreadLocal, Boolean>());
            internalThreadLocalMap.setIndexedVariable(FastThreadLocal.variablesToRemoveIndex, setFromMap);
        }
        else {
            setFromMap = (Set<FastThreadLocal>)indexedVariable;
        }
        setFromMap.add(fastThreadLocal);
    }
    
    private static void removeFromVariablesToRemove(final InternalThreadLocalMap internalThreadLocalMap, final FastThreadLocal fastThreadLocal) {
        final Object indexedVariable = internalThreadLocalMap.indexedVariable(FastThreadLocal.variablesToRemoveIndex);
        if (indexedVariable == InternalThreadLocalMap.UNSET || indexedVariable == null) {
            return;
        }
        ((Set)indexedVariable).remove(fastThreadLocal);
    }
    
    public FastThreadLocal() {
        this.index = InternalThreadLocalMap.nextVariableIndex();
    }
    
    public final Object get() {
        return this.get(InternalThreadLocalMap.get());
    }
    
    public final Object get(final InternalThreadLocalMap internalThreadLocalMap) {
        final Object indexedVariable = internalThreadLocalMap.indexedVariable(this.index);
        if (indexedVariable != InternalThreadLocalMap.UNSET) {
            return indexedVariable;
        }
        return this.initialize(internalThreadLocalMap);
    }
    
    private Object initialize(final InternalThreadLocalMap internalThreadLocalMap) {
        final Object initialValue = this.initialValue();
        internalThreadLocalMap.setIndexedVariable(this.index, initialValue);
        addToVariablesToRemove(internalThreadLocalMap, this);
        return initialValue;
    }
    
    public final void set(final Object o) {
        if (o != InternalThreadLocalMap.UNSET) {
            this.set(InternalThreadLocalMap.get(), o);
        }
        else {
            this.remove();
        }
    }
    
    public final void set(final InternalThreadLocalMap internalThreadLocalMap, final Object o) {
        if (o != InternalThreadLocalMap.UNSET) {
            if (internalThreadLocalMap.setIndexedVariable(this.index, o)) {
                addToVariablesToRemove(internalThreadLocalMap, this);
            }
        }
        else {
            this.remove(internalThreadLocalMap);
        }
    }
    
    public final boolean isSet() {
        return this.isSet(InternalThreadLocalMap.getIfSet());
    }
    
    public final boolean isSet(final InternalThreadLocalMap internalThreadLocalMap) {
        return internalThreadLocalMap != null && internalThreadLocalMap.isIndexedVariableSet(this.index);
    }
    
    public final void remove() {
        this.remove(InternalThreadLocalMap.getIfSet());
    }
    
    public final void remove(final InternalThreadLocalMap internalThreadLocalMap) {
        if (internalThreadLocalMap == null) {
            return;
        }
        final Object removeIndexedVariable = internalThreadLocalMap.removeIndexedVariable(this.index);
        removeFromVariablesToRemove(internalThreadLocalMap, this);
        if (removeIndexedVariable != InternalThreadLocalMap.UNSET) {
            this.onRemoval(removeIndexedVariable);
        }
    }
    
    protected Object initialValue() throws Exception {
        return null;
    }
    
    protected void onRemoval(final Object o) throws Exception {
    }
    
    static {
        variablesToRemoveIndex = InternalThreadLocalMap.nextVariableIndex();
    }
}
