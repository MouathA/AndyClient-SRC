package optifine;

import net.minecraft.util.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;

public class NextTickHashSet extends TreeSet
{
    private LongHashMap longHashMap;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    
    public NextTickHashSet(final Set set) {
        this.longHashMap = new LongHashMap();
        this.minX = Integer.MIN_VALUE;
        this.minZ = Integer.MIN_VALUE;
        this.maxX = Integer.MIN_VALUE;
        this.maxZ = Integer.MIN_VALUE;
        final Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }
    
    @Override
    public boolean contains(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        final Set subSet = this.getSubSet(nextTickListEntry, false);
        return subSet != null && subSet.contains(nextTickListEntry);
    }
    
    @Override
    public boolean add(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        if (nextTickListEntry == null) {
            return false;
        }
        final boolean add = this.getSubSet(nextTickListEntry, true).add(nextTickListEntry);
        final boolean add2 = super.add(o);
        if (add != add2) {
            throw new IllegalStateException("Added: " + add + ", addedParent: " + add2);
        }
        return add2;
    }
    
    @Override
    public boolean remove(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        final Set subSet = this.getSubSet(nextTickListEntry, false);
        if (subSet == null) {
            return false;
        }
        final boolean remove = subSet.remove(nextTickListEntry);
        final boolean remove2 = super.remove(nextTickListEntry);
        if (remove != remove2) {
            throw new IllegalStateException("Added: " + remove + ", addedParent: " + remove2);
        }
        return remove2;
    }
    
    private Set getSubSet(final NextTickListEntry nextTickListEntry, final boolean b) {
        if (nextTickListEntry == null) {
            return null;
        }
        final BlockPos field_180282_a = nextTickListEntry.field_180282_a;
        return this.getSubSet(field_180282_a.getX() >> 4, field_180282_a.getZ() >> 4, b);
    }
    
    private Set getSubSet(final int n, final int n2, final boolean b) {
        final long chunkXZ2Int = ChunkCoordIntPair.chunkXZ2Int(n, n2);
        HashSet set = (HashSet)this.longHashMap.getValueByKey(chunkXZ2Int);
        if (set == null && b) {
            set = new HashSet();
            this.longHashMap.add(chunkXZ2Int, set);
        }
        return set;
    }
    
    @Override
    public Iterator iterator() {
        if (this.minX == Integer.MIN_VALUE) {
            return super.iterator();
        }
        if (this.size() <= 0) {
            return Iterators.emptyIterator();
        }
        final int n = this.minX >> 4;
        final int n2 = this.minZ >> 4;
        final int n3 = this.maxX >> 4;
        final int n4 = this.maxZ >> 4;
        final ArrayList<Iterator> list = new ArrayList<Iterator>();
        for (int i = n; i <= n3; ++i) {
            for (int j = n2; j <= n4; ++j) {
                final Set subSet = this.getSubSet(i, j, false);
                if (subSet != null) {
                    list.add(subSet.iterator());
                }
            }
        }
        if (list.size() <= 0) {
            return Iterators.emptyIterator();
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        return Iterators.concat(list.iterator());
    }
    
    public void setIteratorLimits(final int n, final int n2, final int n3, final int n4) {
        this.minX = Math.min(n, n3);
        this.minZ = Math.min(n2, n4);
        this.maxX = Math.max(n, n3);
        this.maxZ = Math.max(n2, n4);
    }
    
    public void clearIteratorLimits() {
        this.minX = Integer.MIN_VALUE;
        this.minZ = Integer.MIN_VALUE;
        this.maxX = Integer.MIN_VALUE;
        this.maxZ = Integer.MIN_VALUE;
    }
}
