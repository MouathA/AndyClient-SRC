package de.gerrygames.viarewind.replacement;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class ReplacementRegistry
{
    private final Int2ObjectMap itemReplacements;
    private final Int2ObjectMap blockReplacements;
    
    public ReplacementRegistry() {
        this.itemReplacements = new Int2ObjectOpenHashMap();
        this.blockReplacements = new Int2ObjectOpenHashMap();
    }
    
    public void registerItem(final int n, final Replacement replacement) {
        this.registerItem(n, -1, replacement);
    }
    
    public void registerBlock(final int n, final Replacement replacement) {
        this.registerBlock(n, -1, replacement);
    }
    
    public void registerItemBlock(final int n, final Replacement replacement) {
        this.registerItemBlock(n, -1, replacement);
    }
    
    public void registerItem(final int n, final int n2, final Replacement replacement) {
        this.itemReplacements.put(combine(n, n2), replacement);
    }
    
    public void registerBlock(final int n, final int n2, final Replacement replacement) {
        this.blockReplacements.put(combine(n, n2), replacement);
    }
    
    public void registerItemBlock(final int n, final int n2, final Replacement replacement) {
        this.registerItem(n, n2, replacement);
        this.registerBlock(n, n2, replacement);
    }
    
    public Item replace(final Item item) {
        Replacement replacement = (Replacement)this.itemReplacements.get(combine(item.identifier(), item.data()));
        if (replacement == null) {
            replacement = (Replacement)this.itemReplacements.get(combine(item.identifier(), -1));
        }
        return (replacement == null) ? item : replacement.replace(item);
    }
    
    public Replacement replace(final int n, final int n2) {
        Replacement replacement = (Replacement)this.blockReplacements.get(combine(n, n2));
        if (replacement == null) {
            replacement = (Replacement)this.blockReplacements.get(combine(n, -1));
        }
        return replacement;
    }
    
    public static int combine(final int n, final int n2) {
        return n << 16 | (n2 & 0xFFFF);
    }
}
