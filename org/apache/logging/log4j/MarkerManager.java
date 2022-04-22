package org.apache.logging.log4j;

import java.util.concurrent.*;

public final class MarkerManager
{
    private static ConcurrentMap markerMap;
    
    private MarkerManager() {
    }
    
    public static Marker getMarker(final String s) {
        MarkerManager.markerMap.putIfAbsent(s, new Log4jMarker(s));
        return (Marker)MarkerManager.markerMap.get(s);
    }
    
    public static Marker getMarker(final String s, final String s2) {
        final Marker marker = (Marker)MarkerManager.markerMap.get(s2);
        if (marker == null) {
            throw new IllegalArgumentException("Parent Marker " + s2 + " has not been defined");
        }
        return getMarker(s, marker);
    }
    
    public static Marker getMarker(final String s, final Marker marker) {
        MarkerManager.markerMap.putIfAbsent(s, new Log4jMarker(s, marker));
        return (Marker)MarkerManager.markerMap.get(s);
    }
    
    static {
        MarkerManager.markerMap = new ConcurrentHashMap();
    }
    
    private static class Log4jMarker implements Marker
    {
        private static final long serialVersionUID = 100L;
        private final String name;
        private final Marker parent;
        
        public Log4jMarker(final String name) {
            this.name = name;
            this.parent = null;
        }
        
        public Log4jMarker(final String name, final Marker parent) {
            this.name = name;
            this.parent = parent;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public Marker getParent() {
            return this.parent;
        }
        
        @Override
        public boolean isInstanceOf(final Marker marker) {
            if (marker == null) {
                throw new IllegalArgumentException("A marker parameter is required");
            }
            Marker parent = this;
            while (parent != marker) {
                parent = parent.getParent();
                if (parent == null) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean isInstanceOf(final String s) {
            if (s == null) {
                throw new IllegalArgumentException("A marker name is required");
            }
            Marker parent = this;
            while (!s.equals(parent.getName())) {
                parent = parent.getParent();
                if (parent == null) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof Marker)) {
                return false;
            }
            final Marker marker = (Marker)o;
            if (this.name != null) {
                if (this.name.equals(marker.getName())) {
                    return true;
                }
            }
            else if (marker.getName() == null) {
                return true;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return (this.name != null) ? this.name.hashCode() : 0;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(this.name);
            if (this.parent != null) {
                Marker marker = this.parent;
                sb.append("[ ");
                while (marker != null) {
                    if (!false) {
                        sb.append(", ");
                    }
                    sb.append(marker.getName());
                    marker = marker.getParent();
                }
                sb.append(" ]");
            }
            return sb.toString();
        }
    }
}
