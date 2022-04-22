package org.yaml.snakeyaml.emitter;

import org.yaml.snakeyaml.error.*;

public class EmitterException extends YAMLException
{
    private static final long serialVersionUID = -8280070025452995908L;
    
    public EmitterException(final String s) {
        super(s);
    }
}
