package org.apache.logging.log4j.core.jmx;

public interface ContextSelectorAdminMBean
{
    public static final String NAME = "org.apache.logging.log4j2:type=ContextSelector";
    
    String getImplementationClassName();
}
