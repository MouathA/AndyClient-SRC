package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata;

import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viaversion.util.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_7_6_10.metadata.*;

public class MetaIndex1_7_6_10to1_8
{
    private static final HashMap metadataRewrites;
    
    private static Optional getIndex(final Entity1_10Types.EntityType entityType, final int n) {
        final Pair pair = new Pair(entityType, n);
        if (MetaIndex1_7_6_10to1_8.metadataRewrites.containsKey(pair)) {
            return Optional.of(MetaIndex1_7_6_10to1_8.metadataRewrites.get(pair));
        }
        return Optional.empty();
    }
    
    public static MetaIndex1_8to1_7_6_10 searchIndex(final Entity1_10Types.EntityType entityType, final int n) {
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
        final MetaIndex1_8to1_7_6_10[] values = MetaIndex1_8to1_7_6_10.values();
        while (0 < values.length) {
            final MetaIndex1_8to1_7_6_10 metaIndex1_8to1_7_6_10 = values[0];
            MetaIndex1_7_6_10to1_8.metadataRewrites.put(new Pair(metaIndex1_8to1_7_6_10.getClazz(), metaIndex1_8to1_7_6_10.getNewIndex()), metaIndex1_8to1_7_6_10);
            int n = 0;
            ++n;
        }
    }
}
