package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.io.*;
import java.util.*;

public final class TagStringIO
{
    private static final TagStringIO INSTANCE;
    private final boolean acceptLegacy;
    private final boolean emitLegacy;
    private final String indent;
    
    @NotNull
    public static TagStringIO get() {
        return TagStringIO.INSTANCE;
    }
    
    @NotNull
    public static Builder builder() {
        return new Builder();
    }
    
    private TagStringIO(@NotNull final Builder builder) {
        this.acceptLegacy = Builder.access$000(builder);
        this.emitLegacy = Builder.access$100(builder);
        this.indent = Builder.access$200(builder);
    }
    
    public CompoundBinaryTag asCompound(final String input) throws IOException {
        final CharBuffer buffer = new CharBuffer(input);
        final TagStringReader tagStringReader = new TagStringReader(buffer);
        tagStringReader.legacy(this.acceptLegacy);
        final CompoundBinaryTag compound = tagStringReader.compound();
        if (buffer.skipWhitespace().hasMore()) {
            throw new IOException("Document had trailing content after first CompoundTag");
        }
        return compound;
    }
    
    public String asString(final CompoundBinaryTag input) throws IOException {
        final StringBuilder out = new StringBuilder();
        final TagStringWriter tagStringWriter = new TagStringWriter(out, this.indent);
        tagStringWriter.legacy(this.emitLegacy);
        tagStringWriter.writeTag(input);
        tagStringWriter.close();
        return out.toString();
    }
    
    public void toWriter(final CompoundBinaryTag input, final Writer dest) throws IOException {
        final TagStringWriter tagStringWriter = new TagStringWriter(dest, this.indent);
        tagStringWriter.legacy(this.emitLegacy);
        tagStringWriter.writeTag(input);
        tagStringWriter.close();
    }
    
    TagStringIO(final Builder builder, final TagStringIO$1 object) {
        this(builder);
    }
    
    static {
        INSTANCE = new TagStringIO(new Builder());
    }
    
    public static class Builder
    {
        private boolean acceptLegacy;
        private boolean emitLegacy;
        private String indent;
        
        Builder() {
            this.acceptLegacy = true;
            this.emitLegacy = false;
            this.indent = "";
        }
        
        @NotNull
        public Builder indent(final int spaces) {
            if (spaces == 0) {
                this.indent = "";
            }
            else if ((this.indent.length() > 0 && this.indent.charAt(0) != ' ') || spaces != this.indent.length()) {
                final char[] array = new char[spaces];
                Arrays.fill(array, ' ');
                this.indent = String.copyValueOf(array);
            }
            return this;
        }
        
        @NotNull
        public Builder indentTab(final int tabs) {
            if (tabs == 0) {
                this.indent = "";
            }
            else if ((this.indent.length() > 0 && this.indent.charAt(0) != '\t') || tabs != this.indent.length()) {
                final char[] array = new char[tabs];
                Arrays.fill(array, '\t');
                this.indent = String.copyValueOf(array);
            }
            return this;
        }
        
        @NotNull
        public Builder acceptLegacy(final boolean legacy) {
            this.acceptLegacy = legacy;
            return this;
        }
        
        @NotNull
        public Builder emitLegacy(final boolean legacy) {
            this.emitLegacy = legacy;
            return this;
        }
        
        @NotNull
        public TagStringIO build() {
            return new TagStringIO(this, null);
        }
        
        static boolean access$000(final Builder builder) {
            return builder.acceptLegacy;
        }
        
        static boolean access$100(final Builder builder) {
            return builder.emitLegacy;
        }
        
        static String access$200(final Builder builder) {
            return builder.indent;
        }
    }
}
