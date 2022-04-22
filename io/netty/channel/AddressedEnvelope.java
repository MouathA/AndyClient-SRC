package io.netty.channel;

import io.netty.util.*;
import java.net.*;

public interface AddressedEnvelope extends ReferenceCounted
{
    Object content();
    
    SocketAddress sender();
    
    SocketAddress recipient();
}
