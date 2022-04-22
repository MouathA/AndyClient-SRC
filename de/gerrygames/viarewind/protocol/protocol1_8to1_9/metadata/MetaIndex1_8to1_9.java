package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;

import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.*;

public class MetaIndex1_8to1_9
{
    private static final HashMap metadataRewrites;
    
    private static Optional getIndex(final Entity1_10Types.EntityType entityType, final int n) {
        final Pair pair = new Pair(entityType, n);
        if (MetaIndex1_8to1_9.metadataRewrites.containsKey(pair)) {
            return Optional.of(MetaIndex1_8to1_9.metadataRewrites.get(pair));
        }
        return Optional.empty();
    }
    
    public static MetaIndex searchIndex(final Entity1_10Types.EntityType entityType, final int n) {
        Entity1_10Types.EntityType parent = entityType;
        do {
            final Optional index = getIndex(parent, n);
            if (index.isPresent()) {
                return index.get();
            }
            parent = parent.getParent();
        } while (parent != null);
        return null;
    }
    
    static {
        metadataRewrites = new HashMap();
        final MetaIndex[] values = MetaIndex.values();
        while (0 < values.length) {
            final MetaIndex metaIndex = values[0];
            MetaIndex1_8to1_9.metadataRewrites.put(new Pair(metaIndex.getClazz(), metaIndex.getNewIndex()), metaIndex);
            int n = 0;
            ++n;
        }
    }
}
