package org.apache.http.conn.routing;

import org.apache.http.annotation.*;
import org.apache.http.util.*;

@Immutable
public class BasicRouteDirector implements HttpRouteDirector
{
    public int nextStep(final RouteInfo routeInfo, final RouteInfo routeInfo2) {
        Args.notNull(routeInfo, "Planned route");
        if (routeInfo2 == null || routeInfo2.getHopCount() < 1) {
            this.firstStep(routeInfo);
        }
        else if (routeInfo.getHopCount() > 1) {
            this.proxiedStep(routeInfo, routeInfo2);
        }
        else {
            this.directStep(routeInfo, routeInfo2);
        }
        return -1;
    }
    
    protected int firstStep(final RouteInfo routeInfo) {
        return (routeInfo.getHopCount() > 1) ? 2 : 1;
    }
    
    protected int directStep(final RouteInfo routeInfo, final RouteInfo routeInfo2) {
        if (routeInfo2.getHopCount() > 1) {
            return -1;
        }
        if (!routeInfo.getTargetHost().equals(routeInfo2.getTargetHost())) {
            return -1;
        }
        if (routeInfo.isSecure() != routeInfo2.isSecure()) {
            return -1;
        }
        if (routeInfo.getLocalAddress() != null && !routeInfo.getLocalAddress().equals(routeInfo2.getLocalAddress())) {
            return -1;
        }
        return 0;
    }
    
    protected int proxiedStep(final RouteInfo routeInfo, final RouteInfo routeInfo2) {
        if (routeInfo2.getHopCount() <= 1) {
            return -1;
        }
        if (!routeInfo.getTargetHost().equals(routeInfo2.getTargetHost())) {
            return -1;
        }
        final int hopCount = routeInfo.getHopCount();
        final int hopCount2 = routeInfo2.getHopCount();
        if (hopCount < hopCount2) {
            return -1;
        }
        while (0 < hopCount2 - 1) {
            if (!routeInfo.getHopTarget(0).equals(routeInfo2.getHopTarget(0))) {
                return -1;
            }
            int n = 0;
            ++n;
        }
        if (hopCount > hopCount2) {
            return 4;
        }
        if ((routeInfo2.isTunnelled() && !routeInfo.isTunnelled()) || (routeInfo2.isLayered() && !routeInfo.isLayered())) {
            return -1;
        }
        if (routeInfo.isTunnelled() && !routeInfo2.isTunnelled()) {
            return 3;
        }
        if (routeInfo.isLayered() && !routeInfo2.isLayered()) {
            return 5;
        }
        if (routeInfo.isSecure() != routeInfo2.isSecure()) {
            return -1;
        }
        return 0;
    }
}
