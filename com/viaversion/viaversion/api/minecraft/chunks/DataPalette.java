package com.viaversion.viaversion.api.minecraft.chunks;

public interface DataPalette
{
    int index(final int p0, final int p1, final int p2);
    
    int idAt(final int p0);
    
    default int idAt(final int n, final int n2, final int n3) {
        return this.idAt(this.index(n, n2, n3));
    }
    
    void setIdAt(final int p0, final int p1);
    
    default void setIdAt(final int n, final int n2, final int n3, final int n4) {
        this.setIdAt(this.index(n, n2, n3), n4);
    }
    
    int idByIndex(final int p0);
    
    void setIdByIndex(final int p0, final int p1);
    
    int paletteIndexAt(final int p0);
    
    void setPaletteIndexAt(final int p0, final int p1);
    
    void addId(final int p0);
    
    void replaceId(final int p0, final int p1);
    
    int size();
    
    void clear();
}
