package org.apache.logging.log4j.core.jmx;

import javax.management.*;
import org.apache.logging.log4j.core.selector.*;
import org.apache.logging.log4j.core.helpers.*;

public class ContextSelectorAdmin implements ContextSelectorAdminMBean
{
    private final ObjectName objectName;
    private final ContextSelector selector;
    
    public ContextSelectorAdmin(final ContextSelector contextSelector) {
        this.selector = (ContextSelector)Assert.isNotNull(contextSelector, "ContextSelector");
        this.objectName = new ObjectName("org.apache.logging.log4j2:type=ContextSelector");
    }
    
    public ObjectName getObjectName() {
        return this.objectName;
    }
    
    @Override
    public String getImplementationClassName() {
        return this.selector.getClass().getName();
    }
}
