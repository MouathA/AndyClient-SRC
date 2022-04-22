package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.*;

public final class ForwardingIterator implements Iterable
{
    private final Supplier iterator;
    private final Supplier spliterator;
    
    public ForwardingIterator(@NotNull final Supplier iterator, @NotNull final Supplier spliterator) {
        this.iterator = Objects.requireNonNull(iterator, "iterator");
        this.spliterator = Objects.requireNonNull(spliterator, "spliterator");
    }
    
    @NotNull
    @Override
    public Iterator iterator() {
        return this.iterator.get();
    }
    
    @NotNull
    @Override
    public Spliterator spliterator() {
        return this.spliterator.get();
    }
}
