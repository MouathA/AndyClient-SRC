package joptsimple;

public interface ValueConverter
{
    Object convert(final String p0);
    
    Class valueType();
    
    String valuePattern();
}
