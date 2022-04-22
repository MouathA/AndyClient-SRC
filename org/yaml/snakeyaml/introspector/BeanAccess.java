package org.yaml.snakeyaml.introspector;

public enum BeanAccess
{
    DEFAULT("DEFAULT", 0), 
    FIELD("FIELD", 1), 
    PROPERTY("PROPERTY", 2);
    
    private static final BeanAccess[] $VALUES;
    
    private BeanAccess(final String s, final int n) {
    }
    
    static {
        $VALUES = new BeanAccess[] { BeanAccess.DEFAULT, BeanAccess.FIELD, BeanAccess.PROPERTY };
    }
}
