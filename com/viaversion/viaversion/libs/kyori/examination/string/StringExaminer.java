package com.viaversion.viaversion.libs.kyori.examination.string;

import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.*;
import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;

public class StringExaminer extends AbstractExaminer
{
    private static final Function DEFAULT_ESCAPER;
    private static final Collector COMMA_CURLY;
    private static final Collector COMMA_SQUARE;
    private final Function escaper;
    
    @NotNull
    public static StringExaminer simpleEscaping() {
        return Instances.SIMPLE_ESCAPING;
    }
    
    public StringExaminer(@NotNull final Function escaper) {
        this.escaper = escaper;
    }
    
    @NotNull
    @Override
    protected String array(final Object[] array, @NotNull final Stream elements) {
        return elements.collect(StringExaminer.COMMA_SQUARE);
    }
    
    @NotNull
    @Override
    protected String collection(@NotNull final Collection collection, @NotNull final Stream elements) {
        return elements.collect(StringExaminer.COMMA_SQUARE);
    }
    
    @NotNull
    @Override
    protected String examinable(@NotNull final String name, @NotNull final Stream properties) {
        return name + properties.map(StringExaminer::lambda$examinable$1).collect((Collector<? super Object, Object, String>)StringExaminer.COMMA_CURLY);
    }
    
    @NotNull
    @Override
    protected String map(@NotNull final Map map, @NotNull final Stream entries) {
        return entries.map(StringExaminer::lambda$map$2).collect((Collector<? super Object, Object, String>)StringExaminer.COMMA_CURLY);
    }
    
    @NotNull
    @Override
    protected String nil() {
        return "null";
    }
    
    @NotNull
    @Override
    protected String scalar(@NotNull final Object value) {
        return String.valueOf(value);
    }
    
    @NotNull
    @Override
    public String examine(final boolean value) {
        return String.valueOf(value);
    }
    
    @NotNull
    @Override
    public String examine(final byte value) {
        return String.valueOf(value);
    }
    
    @NotNull
    @Override
    public String examine(final char value) {
        return Strings.wrapIn(this.escaper.apply(String.valueOf(value)), '\'');
    }
    
    @NotNull
    @Override
    public String examine(final double value) {
        return Strings.withSuffix(String.valueOf(value), 'd');
    }
    
    @NotNull
    @Override
    public String examine(final float value) {
        return Strings.withSuffix(String.valueOf(value), 'f');
    }
    
    @NotNull
    @Override
    public String examine(final int value) {
        return String.valueOf(value);
    }
    
    @NotNull
    @Override
    public String examine(final long value) {
        return String.valueOf(value);
    }
    
    @NotNull
    @Override
    public String examine(final short value) {
        return String.valueOf(value);
    }
    
    @NotNull
    @Override
    protected String stream(@NotNull final Stream stream) {
        return stream.map(this::examine).collect((Collector<? super Object, Object, String>)StringExaminer.COMMA_SQUARE);
    }
    
    @NotNull
    @Override
    protected String stream(@NotNull final DoubleStream stream) {
        return stream.mapToObj((DoubleFunction<?>)this::examine).collect((Collector<? super Object, Object, String>)StringExaminer.COMMA_SQUARE);
    }
    
    @NotNull
    @Override
    protected String stream(@NotNull final IntStream stream) {
        return stream.mapToObj((IntFunction<?>)this::examine).collect((Collector<? super Object, Object, String>)StringExaminer.COMMA_SQUARE);
    }
    
    @NotNull
    @Override
    protected String stream(@NotNull final LongStream stream) {
        return stream.mapToObj((LongFunction<?>)this::examine).collect((Collector<? super Object, Object, String>)StringExaminer.COMMA_SQUARE);
    }
    
    @NotNull
    @Override
    public String examine(@Nullable final String value) {
        if (value == null) {
            return this.nil();
        }
        return Strings.wrapIn(this.escaper.apply(value), '\"');
    }
    
    @NotNull
    @Override
    protected String array(final int length, final IntFunction value) {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        while (0 < length) {
            sb.append(value.apply(0));
            if (1 < length) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
        return sb.toString();
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
    
    private static String lambda$map$2(final Map.Entry entry) {
        return entry.getKey() + '=' + (String)entry.getValue();
    }
    
    private static String lambda$examinable$1(final Map.Entry entry) {
        return entry.getKey() + '=' + (String)entry.getValue();
    }
    
    private static String lambda$static$0(final String s) {
        return s.replace("\"", "\\\"").replace("\\", "\\\\").replace("\b", "\\b").replace("\f", "\\f").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
    
    static Function access$000() {
        return StringExaminer.DEFAULT_ESCAPER;
    }
    
    static {
        DEFAULT_ESCAPER = StringExaminer::lambda$static$0;
        COMMA_CURLY = Collectors.joining(", ", "{", "}");
        COMMA_SQUARE = Collectors.joining(", ", "[", "]");
    }
    
    private static final class Instances
    {
        static final StringExaminer SIMPLE_ESCAPING;
        
        static {
            SIMPLE_ESCAPING = new StringExaminer(StringExaminer.access$000());
        }
    }
}
