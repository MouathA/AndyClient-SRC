package com.viaversion.viaversion.libs.kyori.examination.string;

import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.*;

public class MultiLineStringExaminer extends AbstractExaminer
{
    private static final String INDENT_2 = "  ";
    private final StringExaminer examiner;
    
    @NotNull
    public static MultiLineStringExaminer simpleEscaping() {
        return Instances.SIMPLE_ESCAPING;
    }
    
    public MultiLineStringExaminer(@NotNull final StringExaminer examiner) {
        this.examiner = examiner;
    }
    
    @NotNull
    @Override
    protected Stream array(final Object[] array, @NotNull final Stream elements) {
        return this.arrayLike(elements);
    }
    
    @NotNull
    @Override
    protected Stream collection(@NotNull final Collection collection, @NotNull final Stream elements) {
        return this.arrayLike(elements);
    }
    
    @NotNull
    @Override
    protected Stream examinable(@NotNull final String name, @NotNull final Stream properties) {
        return enclose(indent(flatten(",", properties.map(this::lambda$examinable$0))), name + "{", "}");
    }
    
    @NotNull
    @Override
    protected Stream map(@NotNull final Map map, @NotNull final Stream entries) {
        return enclose(indent(flatten(",", entries.map(MultiLineStringExaminer::lambda$map$1))), "{", "}");
    }
    
    @NotNull
    @Override
    protected Stream nil() {
        return Stream.of(this.examiner.nil());
    }
    
    @NotNull
    @Override
    protected Stream scalar(@NotNull final Object value) {
        return Stream.of(this.examiner.scalar(value));
    }
    
    @NotNull
    @Override
    public Stream examine(final boolean value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    @NotNull
    @Override
    public Stream examine(final byte value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    @NotNull
    @Override
    public Stream examine(final char value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    @NotNull
    @Override
    public Stream examine(final double value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    @NotNull
    @Override
    public Stream examine(final float value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    @NotNull
    @Override
    public Stream examine(final int value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    @NotNull
    @Override
    public Stream examine(final long value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    @NotNull
    @Override
    public Stream examine(final short value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    @NotNull
    @Override
    protected Stream array(final int length, final IntFunction value) {
        return this.arrayLike((length == 0) ? Stream.empty() : IntStream.range(0, length).mapToObj((IntFunction<?>)value));
    }
    
    @NotNull
    @Override
    protected Stream stream(@NotNull final Stream stream) {
        return this.arrayLike(stream.map(this::examine));
    }
    
    @NotNull
    @Override
    protected Stream stream(@NotNull final DoubleStream stream) {
        return this.arrayLike(stream.mapToObj((DoubleFunction<?>)this::examine));
    }
    
    @NotNull
    @Override
    protected Stream stream(@NotNull final IntStream stream) {
        return this.arrayLike(stream.mapToObj((IntFunction<?>)this::examine));
    }
    
    @NotNull
    @Override
    protected Stream stream(@NotNull final LongStream stream) {
        return this.arrayLike(stream.mapToObj((LongFunction<?>)this::examine));
    }
    
    @NotNull
    @Override
    public Stream examine(@Nullable final String value) {
        return Stream.of(this.examiner.examine(value));
    }
    
    private Stream arrayLike(final Stream streams) {
        return enclose(indent(flatten(",", streams)), "[", "]");
    }
    
    private static Stream enclose(final Stream lines, final String open, final String close) {
        return enclose(lines.collect(Collectors.toList()), open, close);
    }
    
    private static Stream enclose(final List lines, final String open, final String close) {
        if (lines.isEmpty()) {
            return Stream.of(open + close);
        }
        return Stream.of(Stream.of(open), indent(lines.stream()), Stream.of(close)).reduce(Stream.empty(), Stream::concat);
    }
    
    private static Stream flatten(final String delimiter, final Stream bumpy) {
        final ArrayList list = new ArrayList();
        bumpy.forEachOrdered(MultiLineStringExaminer::lambda$flatten$2);
        return list.stream();
    }
    
    private static Stream association(final Stream left, final String middle, final Stream right) {
        return association(left.collect(Collectors.toList()), middle, right.collect(Collectors.toList()));
    }
    
    private static Stream association(final List left, final String middle, final List right) {
        final int size = left.size();
        final int size2 = right.size();
        final int max = Math.max(size, size2);
        final int maxLength = Strings.maxLength(left.stream());
        final String s = (size < 2) ? "" : Strings.repeat(" ", maxLength);
        final String s2 = (size < 2) ? "" : Strings.repeat(" ", middle.length());
        final ArrayList list = new ArrayList<Object>(max);
        while (0 < max) {
            list.add(((0 < size) ? Strings.padEnd(left.get(0), maxLength, ' ') : s) + middle + ((0 < size2) ? right.get(0) : ""));
            int n = 0;
            ++n;
        }
        return list.stream();
    }
    
    private static Stream indent(final Stream lines) {
        return lines.map(MultiLineStringExaminer::lambda$indent$3);
    }
    
    @NotNull
    @Override
    protected Object array(final int length, final IntFunction value) {
        return this.array(length, value);
    }
    
    @NotNull
    @Override
    protected Object stream(@NotNull final LongStream stream) {
        return this.stream(stream);
    }
    
    @NotNull
    @Override
    protected Object stream(@NotNull final IntStream stream) {
        return this.stream(stream);
    }
    
    @NotNull
    @Override
    protected Object stream(@NotNull final DoubleStream stream) {
        return this.stream(stream);
    }
    
    @NotNull
    @Override
    protected Object stream(@NotNull final Stream stream) {
        return this.stream(stream);
    }
    
    @NotNull
    @Override
    protected Object scalar(@NotNull final Object value) {
        return this.scalar(value);
    }
    
    @NotNull
    @Override
    protected Object nil() {
        return this.nil();
    }
    
    @NotNull
    @Override
    protected Object map(@NotNull final Map map, @NotNull final Stream entries) {
        return this.map(map, entries);
    }
    
    @NotNull
    @Override
    protected Object examinable(@NotNull final String name, @NotNull final Stream properties) {
        return this.examinable(name, properties);
    }
    
    @NotNull
    @Override
    protected Object collection(@NotNull final Collection collection, @NotNull final Stream elements) {
        return this.collection(collection, elements);
    }
    
    @NotNull
    @Override
    protected Object array(final Object[] array, @NotNull final Stream elements) {
        return this.array(array, elements);
    }
    
    @NotNull
    @Override
    public Object examine(@Nullable final String value) {
        return this.examine(value);
    }
    
    @NotNull
    @Override
    public Object examine(final short value) {
        return this.examine(value);
    }
    
    @NotNull
    @Override
    public Object examine(final long value) {
        return this.examine(value);
    }
    
    @NotNull
    @Override
    public Object examine(final int value) {
        return this.examine(value);
    }
    
    @NotNull
    @Override
    public Object examine(final float value) {
        return this.examine(value);
    }
    
    @NotNull
    @Override
    public Object examine(final double value) {
        return this.examine(value);
    }
    
    @NotNull
    @Override
    public Object examine(final char value) {
        return this.examine(value);
    }
    
    @NotNull
    @Override
    public Object examine(final byte value) {
        return this.examine(value);
    }
    
    @NotNull
    @Override
    public Object examine(final boolean value) {
        return this.examine(value);
    }
    
    private static String lambda$indent$3(final String s) {
        return "  " + s;
    }
    
    private static void lambda$flatten$2(final List list, final String s, final Stream stream) {
        if (!list.isEmpty()) {
            final int n = list.size() - 1;
            list.set(n, list.get(n) + s);
        }
        Objects.requireNonNull((Object)list);
        stream.forEachOrdered(list::add);
    }
    
    private static Stream lambda$map$1(final Map.Entry entry) {
        return association(entry.getKey(), " = ", (Stream)entry.getValue());
    }
    
    private Stream lambda$examinable$0(final Map.Entry entry) {
        return association(this.examine((String)entry.getKey()), " = ", (Stream)entry.getValue());
    }
    
    private static final class Instances
    {
        static final MultiLineStringExaminer SIMPLE_ESCAPING;
        
        static {
            SIMPLE_ESCAPING = new MultiLineStringExaminer(StringExaminer.simpleEscaping());
        }
    }
}
