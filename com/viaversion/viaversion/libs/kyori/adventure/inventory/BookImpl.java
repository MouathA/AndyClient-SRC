package com.viaversion.viaversion.libs.kyori.adventure.inventory;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

final class BookImpl implements Book
{
    private final Component title;
    private final Component author;
    private final List pages;
    
    BookImpl(@NotNull final Component title, @NotNull final Component author, @NotNull final List pages) {
        this.title = Objects.requireNonNull(title, "title");
        this.author = Objects.requireNonNull(author, "author");
        this.pages = Collections.unmodifiableList((List<?>)Objects.requireNonNull(pages, "pages"));
    }
    
    @NotNull
    @Override
    public Component title() {
        return this.title;
    }
    
    @NotNull
    @Override
    public Book title(@NotNull final Component title) {
        return new BookImpl(Objects.requireNonNull(title, "title"), this.author, this.pages);
    }
    
    @NotNull
    @Override
    public Component author() {
        return this.author;
    }
    
    @NotNull
    @Override
    public Book author(@NotNull final Component author) {
        return new BookImpl(this.title, Objects.requireNonNull(author, "author"), this.pages);
    }
    
    @NotNull
    @Override
    public List pages() {
        return this.pages;
    }
    
    @NotNull
    @Override
    public Book pages(@NotNull final List pages) {
        return new BookImpl(this.title, this.author, new ArrayList(Objects.requireNonNull(pages, "pages")));
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("title", this.title), ExaminableProperty.of("author", this.author), ExaminableProperty.of("pages", this.pages) });
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookImpl)) {
            return false;
        }
        final BookImpl bookImpl = (BookImpl)o;
        return this.title.equals(bookImpl.title) && this.author.equals(bookImpl.author) && this.pages.equals(bookImpl.pages);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * this.title.hashCode() + this.author.hashCode()) + this.pages.hashCode();
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    static final class BuilderImpl implements Builder
    {
        private Component title;
        private Component author;
        private final List pages;
        
        BuilderImpl() {
            this.title = Component.empty();
            this.author = Component.empty();
            this.pages = new ArrayList();
        }
        
        @NotNull
        @Override
        public Builder title(@NotNull final Component title) {
            this.title = Objects.requireNonNull(title, "title");
            return this;
        }
        
        @NotNull
        @Override
        public Builder author(@NotNull final Component author) {
            this.author = Objects.requireNonNull(author, "author");
            return this;
        }
        
        @NotNull
        @Override
        public Builder addPage(@NotNull final Component page) {
            this.pages.add(Objects.requireNonNull(page, "page"));
            return this;
        }
        
        @NotNull
        @Override
        public Builder pages(@NotNull final Collection pages) {
            this.pages.addAll(Objects.requireNonNull(pages, "pages"));
            return this;
        }
        
        @NotNull
        @Override
        public Builder pages(@NotNull final Component... pages) {
            Collections.addAll(this.pages, pages);
            return this;
        }
        
        @NotNull
        @Override
        public Book build() {
            return new BookImpl(this.title, this.author, new ArrayList(this.pages));
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
    }
}
