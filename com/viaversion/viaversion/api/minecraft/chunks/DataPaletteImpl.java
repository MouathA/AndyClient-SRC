package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.libs.fastutil.ints.*;

public final class DataPaletteImpl implements DataPalette
{
    private final IntList palette;
    private final Int2IntMap inversePalette;
    private final int[] values;
    private final int sizeBits;
    
    public DataPaletteImpl(final int n) {
        this(n, 8);
    }
    
    public DataPaletteImpl(final int n, final int n2) {
        this.values = new int[n];
        this.sizeBits = Integer.numberOfTrailingZeros(n) / 3;
        this.palette = new IntArrayList(n2);
        (this.inversePalette = new Int2IntOpenHashMap(n2)).defaultReturnValue(-1);
    }
    
    @Override
    public int index(final int n, final int n2, final int n3) {
        return (n2 << this.sizeBits | n3) << this.sizeBits | n;
    }
    
    @Override
    public int idAt(final int n) {
        return this.palette.getInt(this.values[n]);
    }
    
    @Override
    public void setIdAt(final int n, final int n2) {
        int n3 = this.inversePalette.get(n2);
        if (n3 == -1) {
            n3 = this.palette.size();
            this.palette.add(n2);
            this.inversePalette.put(n2, n3);
        }
        this.values[n] = n3;
    }
    
    @Override
    public int paletteIndexAt(final int n) {
        return this.values[n];
    }
    
    @Override
    public void setPaletteIndexAt(final int n, final int n2) {
        this.values[n] = n2;
    }
    
    @Override
    public int size() {
        return this.palette.size();
    }
    
    @Override
    public int idByIndex(final int n) {
        return this.palette.getInt(n);
    }
    
    @Override
    public void setIdByIndex(final int n, final int n2) {
        final int set = this.palette.set(n, n2);
        if (set == n2) {
            return;
        }
        this.inversePalette.put(n2, n);
        if (this.inversePalette.get(set) == n) {
            this.inversePalette.remove(set);
            while (0 < this.palette.size()) {
                if (this.palette.getInt(0) == set) {
                    this.inversePalette.put(set, 0);
                    break;
                }
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    @Override
    public void replaceId(final int n, final int n2) {
        final int remove = this.inversePalette.remove(n);
        if (remove == -1) {
            return;
        }
        this.inversePalette.put(n2, remove);
        while (0 < this.palette.size()) {
            if (this.palette.getInt(0) == n) {
                this.palette.set(0, n2);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public void addId(final int n) {
        this.inversePalette.put(n, this.palette.size());
        this.palette.add(n);
    }
    
    @Override
    public void clear() {
        this.palette.clear();
        this.inversePalette.clear();
    }
}
