package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

@Debug.Renderer(text = "\"ListBinaryTag[type=\" + this.type.toString() + \"]\"", childrenArray = "this.tags.toArray()", hasChildren = "!this.tags.isEmpty()")
final class ListBinaryTagImpl extends AbstractBinaryTag implements ListBinaryTag
{
    static final ListBinaryTag EMPTY;
    private final List tags;
    private final BinaryTagType elementType;
    private final int hashCode;
    
    ListBinaryTagImpl(final BinaryTagType elementType, final List tags) {
        this.tags = Collections.unmodifiableList((List<?>)tags);
        this.elementType = elementType;
        this.hashCode = tags.hashCode();
    }
    
    @NotNull
    @Override
    public BinaryTagType elementType() {
        return this.elementType;
    }
    
    @Override
    public int size() {
        return this.tags.size();
    }
    
    @NotNull
    @Override
    public BinaryTag get(final int index) {
        return this.tags.get(index);
    }
    
    @NotNull
    @Override
    public ListBinaryTag set(final int index, @NotNull final BinaryTag newTag, @Nullable final Consumer removed) {
        return this.edit(ListBinaryTagImpl::lambda$set$0, newTag.type());
    }
    
    @NotNull
    @Override
    public ListBinaryTag remove(final int index, @Nullable final Consumer removed) {
        return this.edit(ListBinaryTagImpl::lambda$remove$1, null);
    }
    
    @NotNull
    @Override
    public ListBinaryTag add(final BinaryTag tag) {
        noAddEnd(tag);
        if (this.elementType != BinaryTagTypes.END) {
            mustBeSameType(tag, this.elementType);
        }
        return this.edit(ListBinaryTagImpl::lambda$add$2, tag.type());
    }
    
    @NotNull
    @Override
    public ListBinaryTag add(final Iterable tagsToAdd) {
        if (tagsToAdd instanceof Collection && ((Collection)tagsToAdd).isEmpty()) {
            return this;
        }
        return this.edit(ListBinaryTagImpl::lambda$add$3, mustBeSameType(tagsToAdd));
    }
    
    static void noAddEnd(final BinaryTag tag) {
        if (tag.type() == BinaryTagTypes.END) {
            throw new IllegalArgumentException(String.format("Cannot add a %s to a %s", BinaryTagTypes.END, BinaryTagTypes.LIST));
        }
    }
    
    static BinaryTagType mustBeSameType(final Iterable tags) {
        BinaryTagType type = null;
        for (final BinaryTag tag : tags) {
            if (type == null) {
                type = tag.type();
            }
            else {
                mustBeSameType(tag, type);
            }
        }
        return type;
    }
    
    static void mustBeSameType(final BinaryTag tag, final BinaryTagType type) {
        if (tag.type() != type) {
            throw new IllegalArgumentException(String.format("Trying to add tag of type %s to list of %s", tag.type(), type));
        }
    }
    
    private ListBinaryTag edit(final Consumer consumer, @Nullable final BinaryTagType maybeElementType) {
        final ArrayList tags = new ArrayList(this.tags);
        consumer.accept(tags);
        BinaryTagType elementType = this.elementType;
        if (maybeElementType != null && elementType == BinaryTagTypes.END) {
            elementType = maybeElementType;
        }
        return new ListBinaryTagImpl(elementType, tags);
    }
    
    @NotNull
    @Override
    public Stream stream() {
        return this.tags.stream();
    }
    
    @Override
    public Iterator iterator() {
        return new Iterator() {
            final Iterator val$iterator;
            final ListBinaryTagImpl this$0;
            
            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }
            
            @Override
            public BinaryTag next() {
                return this.val$iterator.next();
            }
            
            @Override
            public void forEachRemaining(final Consumer action) {
                this.val$iterator.forEachRemaining(action);
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        };
    }
    
    @Override
    public void forEach(final Consumer action) {
        this.tags.forEach(action);
    }
    
    @Override
    public Spliterator spliterator() {
        return Spliterators.spliterator((Collection<?>)this.tags, 1040);
    }
    
    @Override
    public boolean equals(final Object that) {
        return this == that || (that instanceof ListBinaryTagImpl && this.tags.equals(((ListBinaryTagImpl)that).tags));
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("tags", this.tags), ExaminableProperty.of("type", this.elementType) });
    }
    
    @NotNull
    @Override
    public Object add(final Iterable tagsToAdd) {
        return this.add(tagsToAdd);
    }
    
    @NotNull
    @Override
    public Object add(final BinaryTag tag) {
        return this.add(tag);
    }
    
    private static void lambda$add$3(final Iterable iterable, final List list) {
        final Iterator<BinaryTag> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
    }
    
    private static void lambda$add$2(final BinaryTag binaryTag, final List list) {
        list.add(binaryTag);
    }
    
    private static void lambda$remove$1(final int n, final Consumer consumer, final List list) {
        final BinaryTag binaryTag = list.remove(n);
        if (consumer != null) {
            consumer.accept(binaryTag);
        }
    }
    
    private static void lambda$set$0(final int n, final BinaryTag binaryTag, final Consumer consumer, final List list) {
        final BinaryTag binaryTag2 = list.set(n, binaryTag);
        if (consumer != null) {
            consumer.accept(binaryTag2);
        }
    }
    
    static {
        EMPTY = new ListBinaryTagImpl(BinaryTagTypes.END, Collections.emptyList());
    }
}
