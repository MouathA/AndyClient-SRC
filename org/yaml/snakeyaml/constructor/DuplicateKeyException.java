package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.*;

public class DuplicateKeyException extends ConstructorException
{
    protected DuplicateKeyException(final Mark mark, final Object o, final Mark mark2) {
        super("while constructing a mapping", mark, "found duplicate key " + String.valueOf(o), mark2);
    }
}
