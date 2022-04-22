package org.apache.logging.log4j.core.config.plugins;

import java.io.*;

public class PluginType implements Serializable
{
    private static final long serialVersionUID = 4743255148794846612L;
    private final Class pluginClass;
    private final String elementName;
    private final boolean printObject;
    private final boolean deferChildren;
    
    public PluginType(final Class pluginClass, final String elementName, final boolean printObject, final boolean deferChildren) {
        this.pluginClass = pluginClass;
        this.elementName = elementName;
        this.printObject = printObject;
        this.deferChildren = deferChildren;
    }
    
    public Class getPluginClass() {
        return this.pluginClass;
    }
    
    public String getElementName() {
        return this.elementName;
    }
    
    public boolean isObjectPrintable() {
        return this.printObject;
    }
    
    public boolean isDeferChildren() {
        return this.deferChildren;
    }
}
