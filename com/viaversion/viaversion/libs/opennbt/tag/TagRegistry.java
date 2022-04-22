package com.viaversion.viaversion.libs.opennbt.tag;

import java.util.function.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class TagRegistry
{
    private static final Int2ObjectMap idToTag;
    private static final Object2IntMap tagToId;
    private static final Int2ObjectMap instanceSuppliers;
    
    public static void register(final int n, final Class clazz, final Supplier supplier) throws TagRegisterException {
        if (TagRegistry.idToTag.containsKey(n)) {
            throw new TagRegisterException("Tag ID \"" + n + "\" is already in use.");
        }
        if (TagRegistry.tagToId.containsKey(clazz)) {
            throw new TagRegisterException("Tag \"" + clazz.getSimpleName() + "\" is already registered.");
        }
        TagRegistry.instanceSuppliers.put(n, supplier);
        TagRegistry.idToTag.put(n, clazz);
        TagRegistry.tagToId.put(clazz, n);
    }
    
    public static void unregister(final int n) {
        TagRegistry.tagToId.removeInt(getClassFor(n));
        TagRegistry.idToTag.remove(n);
    }
    
    @Nullable
    public static Class getClassFor(final int n) {
        return (Class)TagRegistry.idToTag.get(n);
    }
    
    public static int getIdFor(final Class clazz) {
        return TagRegistry.tagToId.getInt(clazz);
    }
    
    public static Tag createInstance(final int n) throws TagCreateException {
        final Supplier supplier = (Supplier)TagRegistry.instanceSuppliers.get(n);
        if (supplier == null) {
            throw new TagCreateException("Could not find tag with ID \"" + n + "\".");
        }
        return supplier.get();
    }
    
    static {
        idToTag = new Int2ObjectOpenHashMap();
        tagToId = new Object2IntOpenHashMap();
        instanceSuppliers = new Int2ObjectOpenHashMap();
        TagRegistry.tagToId.defaultReturnValue(-1);
        register(1, ByteTag.class, ByteTag::new);
        register(2, ShortTag.class, ShortTag::new);
        register(3, IntTag.class, IntTag::new);
        register(4, LongTag.class, LongTag::new);
        register(5, FloatTag.class, FloatTag::new);
        register(6, DoubleTag.class, DoubleTag::new);
        register(7, ByteArrayTag.class, ByteArrayTag::new);
        register(8, StringTag.class, StringTag::new);
        register(9, ListTag.class, ListTag::new);
        register(10, CompoundTag.class, CompoundTag::new);
        register(11, IntArrayTag.class, IntArrayTag::new);
        register(12, LongArrayTag.class, LongArrayTag::new);
    }
}
