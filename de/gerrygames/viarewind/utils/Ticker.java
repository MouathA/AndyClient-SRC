package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Ticker
{
    private static boolean init;
    
    public static void init() {
        if (Ticker.init) {
            return;
        }
        final Class<Ticker> clazz = Ticker.class;
        final Class<Ticker> clazz2 = Ticker.class;
        // monitorenter(clazz)
        if (Ticker.init) {
            // monitorexit(clazz2)
            return;
        }
        Ticker.init = true;
        // monitorexit(clazz2)
        Via.getPlatform().runRepeatingSync(Ticker::lambda$init$1, 1L);
    }
    
    private static void lambda$init$1() {
        Via.getManager().getConnectionManager().getConnections().forEach(Ticker::lambda$init$0);
    }
    
    private static void lambda$init$0(final UserConnection userConnection) {
        final Stream stream = userConnection.getStoredObjects().values().stream();
        final Class<Tickable> clazz = Tickable.class;
        Objects.requireNonNull(Tickable.class);
        final Stream filter = stream.filter(clazz::isInstance);
        final Class<Tickable> clazz2 = Tickable.class;
        Objects.requireNonNull(Tickable.class);
        filter.map(clazz2::cast).forEach(Tickable::tick);
    }
    
    static {
        Ticker.init = false;
    }
}
