package org.apache.http.conn.routing;

import org.apache.http.*;
import java.net.*;

public interface RouteInfo
{
    HttpHost getTargetHost();
    
    InetAddress getLocalAddress();
    
    int getHopCount();
    
    HttpHost getHopTarget(final int p0);
    
    HttpHost getProxyHost();
    
    TunnelType getTunnelType();
    
    boolean isTunnelled();
    
    LayerType getLayerType();
    
    boolean isLayered();
    
    boolean isSecure();
    
    public enum LayerType
    {
        PLAIN("PLAIN", 0), 
        LAYERED("LAYERED", 1);
        
        private static final LayerType[] $VALUES;
        
        private LayerType(final String s, final int n) {
        }
        
        static {
            $VALUES = new LayerType[] { LayerType.PLAIN, LayerType.LAYERED };
        }
    }
    
    public enum TunnelType
    {
        PLAIN("PLAIN", 0), 
        TUNNELLED("TUNNELLED", 1);
        
        private static final TunnelType[] $VALUES;
        
        private TunnelType(final String s, final int n) {
        }
        
        static {
            $VALUES = new TunnelType[] { TunnelType.PLAIN, TunnelType.TUNNELLED };
        }
    }
}
