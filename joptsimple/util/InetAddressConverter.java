package joptsimple.util;

import joptsimple.*;
import java.net.*;

public class InetAddressConverter implements ValueConverter
{
    public InetAddress convert(final String s) {
        return InetAddress.getByName(s);
    }
    
    public Class valueType() {
        return InetAddress.class;
    }
    
    public String valuePattern() {
        return null;
    }
    
    public Object convert(final String s) {
        return this.convert(s);
    }
}
