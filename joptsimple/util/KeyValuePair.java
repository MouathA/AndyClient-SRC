package joptsimple.util;

public final class KeyValuePair
{
    public final String key;
    public final String value;
    
    private KeyValuePair(final String key, final String value) {
        this.key = key;
        this.value = value;
    }
    
    public static KeyValuePair valueOf(final String s) {
        final int index = s.indexOf(61);
        if (index == -1) {
            return new KeyValuePair(s, "");
        }
        return new KeyValuePair(s.substring(0, index), (index == s.length() - 1) ? "" : s.substring(index + 1));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof KeyValuePair)) {
            return false;
        }
        final KeyValuePair keyValuePair = (KeyValuePair)o;
        return this.key.equals(keyValuePair.key) && this.value.equals(keyValuePair.value);
    }
    
    @Override
    public int hashCode() {
        return this.key.hashCode() ^ this.value.hashCode();
    }
    
    @Override
    public String toString() {
        return this.key + '=' + this.value;
    }
}
