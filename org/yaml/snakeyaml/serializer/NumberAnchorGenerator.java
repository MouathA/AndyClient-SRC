package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.nodes.*;
import java.text.*;

public class NumberAnchorGenerator implements AnchorGenerator
{
    private int lastAnchorId;
    
    public NumberAnchorGenerator(final int lastAnchorId) {
        this.lastAnchorId = 0;
        this.lastAnchorId = lastAnchorId;
    }
    
    @Override
    public String nextAnchor(final Node node) {
        ++this.lastAnchorId;
        final NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumIntegerDigits(3);
        numberInstance.setMaximumFractionDigits(0);
        numberInstance.setGroupingUsed(false);
        return "id" + numberInstance.format(this.lastAnchorId);
    }
}
