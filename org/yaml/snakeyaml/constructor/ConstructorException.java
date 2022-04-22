package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.*;

public class ConstructorException extends MarkedYAMLException
{
    private static final long serialVersionUID = -8816339931365239910L;
    
    protected ConstructorException(final String s, final Mark mark, final String s2, final Mark mark2, final Throwable t) {
        super(s, mark, s2, mark2, t);
    }
    
    protected ConstructorException(final String s, final Mark mark, final String s2, final Mark mark2) {
        this(s, mark, s2, mark2, (Throwable)null);
    }
}
