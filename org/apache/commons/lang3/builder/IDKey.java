package org.apache.commons.lang3.builder;

final class IDKey
{
    private final Object value;
    private final int id;
    
    public IDKey(final Object value) {
        this.id = System.identityHashCode(value);
        this.value = value;
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof IDKey)) {
            return false;
        }
        final IDKey idKey = (IDKey)o;
        return this.id == idKey.id && this.value == idKey.value;
    }
}
