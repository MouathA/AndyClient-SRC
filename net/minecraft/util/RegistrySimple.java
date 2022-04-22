package net.minecraft.util;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.*;
import java.util.*;

public class RegistrySimple implements IRegistry
{
    private static final Logger logger;
    protected final Map registryObjects;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001210";
        logger = LogManager.getLogger();
    }
    
    public RegistrySimple() {
        this.registryObjects = this.createUnderlyingMap();
    }
    
    protected Map createUnderlyingMap() {
        return Maps.newHashMap();
    }
    
    @Override
    public Object getObject(final Object o) {
        return this.registryObjects.get(o);
    }
    
    @Override
    public void putObject(final Object o, final Object o2) {
        Validate.notNull(o);
        Validate.notNull(o2);
        if (this.registryObjects.containsKey(o)) {
            RegistrySimple.logger.debug("Adding duplicate key '" + o + "' to registry");
        }
        this.registryObjects.put(o, o2);
    }
    
    public Set getKeys() {
        return Collections.unmodifiableSet(this.registryObjects.keySet());
    }
    
    public boolean containsKey(final Object o) {
        return this.registryObjects.containsKey(o);
    }
    
    @Override
    public Iterator iterator() {
        return this.registryObjects.values().iterator();
    }
}
