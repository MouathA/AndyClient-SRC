package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.*;

public class ParserException extends MarkedYAMLException
{
    private static final long serialVersionUID = -2349253802798398038L;
    
    public ParserException(final String s, final Mark mark, final String s2, final Mark mark2) {
        super(s, mark, s2, mark2, null, null);
    }
}
