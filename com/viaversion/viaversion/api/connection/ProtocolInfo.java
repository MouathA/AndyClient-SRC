package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.*;

public interface ProtocolInfo
{
    State getState();
    
    void setState(final State p0);
    
    int getProtocolVersion();
    
    void setProtocolVersion(final int p0);
    
    int getServerProtocolVersion();
    
    void setServerProtocolVersion(final int p0);
    
    String getUsername();
    
    void setUsername(final String p0);
    
    UUID getUuid();
    
    void setUuid(final UUID p0);
    
    ProtocolPipeline getPipeline();
    
    void setPipeline(final ProtocolPipeline p0);
    
    UserConnection getUser();
}
