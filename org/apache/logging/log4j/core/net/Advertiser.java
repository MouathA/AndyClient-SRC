package org.apache.logging.log4j.core.net;

import java.util.*;

public interface Advertiser
{
    Object advertise(final Map p0);
    
    void unadvertise(final Object p0);
}
