package com.viaversion.viaversion.api.protocol;

import java.util.*;

public interface ProtocolPipeline extends SimpleProtocol
{
    void add(final Protocol p0);
    
    void add(final Collection p0);
    
    boolean contains(final Class p0);
    
    Protocol getProtocol(final Class p0);
    
    List pipes();
    
    boolean hasNonBaseProtocols();
    
    void cleanPipes();
}
