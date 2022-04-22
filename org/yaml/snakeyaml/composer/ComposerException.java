package org.yaml.snakeyaml.composer;

import org.yaml.snakeyaml.error.*;

public class ComposerException extends MarkedYAMLException
{
    private static final long serialVersionUID = 2146314636913113935L;
    
    protected ComposerException(final String s, final Mark mark, final String s2, final Mark mark2) {
        super(s, mark, s2, mark2);
    }
}
