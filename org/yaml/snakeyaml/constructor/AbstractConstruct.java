package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.error.*;

public abstract class AbstractConstruct implements Construct
{
    @Override
    public void construct2ndStep(final Node node, final Object o) {
        if (node.isTwoStepsConstruction()) {
            throw new IllegalStateException("Not Implemented in " + this.getClass().getName());
        }
        throw new YAMLException("Unexpected recursive structure for Node: " + node);
    }
}
