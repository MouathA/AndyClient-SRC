package org.yaml.snakeyaml.error;

public class YAMLException extends RuntimeException
{
    private static final long serialVersionUID = -4738336175050337570L;
    
    public YAMLException(final String s) {
        super(s);
    }
    
    public YAMLException(final Throwable t) {
        super(t);
    }
    
    public YAMLException(final String s, final Throwable t) {
        super(s, t);
    }
}
