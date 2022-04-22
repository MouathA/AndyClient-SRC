package com.viaversion.viaversion.libs.kyori.adventure.inventory;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.*;
import org.jetbrains.annotations.*;

@ApiStatus.NonExtendable
public interface Book extends Buildable, Examinable
{
    @NotNull
    default Book book(@NotNull final Component title, @NotNull final Component author, @NotNull final Collection pages) {
        return new BookImpl(title, author, new ArrayList(pages));
    }
    
    @NotNull
    default Book book(@NotNull final Component title, @NotNull final Component author, @NotNull final Component... pages) {
        return book(title, author, Arrays.asList(pages));
    }
    
    @NotNull
    default Builder builder() {
        return new BookImpl.BuilderImpl();
    }
    
    @NotNull
    Component title();
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book title(@NotNull final Component title);
    
    @NotNull
    Component author();
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book author(@NotNull final Component author);
    
    @NotNull
    List pages();
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default Book pages(@NotNull final Component... pages) {
        return this.pages(Arrays.asList(pages));
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book pages(@NotNull final List pages);
    
    @NotNull
    default Builder toBuilder() {
        return builder().title(this.title()).author(this.author()).pages(this.pages());
    }
    
    @NotNull
    default Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    public interface Builder extends Buildable.Builder
    {
        @Contract("_ -> this")
        @NotNull
        Builder title(@NotNull final Component title);
        
        @Contract("_ -> this")
        @NotNull
        Builder author(@NotNull final Component author);
        
        @Contract("_ -> this")
        @NotNull
        Builder addPage(@NotNull final Component page);
        
        @Contract("_ -> this")
        @NotNull
        Builder pages(@NotNull final Component... pages);
        
        @Contract("_ -> this")
        @NotNull
        Builder pages(@NotNull final Collection pages);
        
        @NotNull
        Book build();
        
        @NotNull
        default Object build() {
            return this.build();
        }
    }
}
