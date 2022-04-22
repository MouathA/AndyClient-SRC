package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import org.jetbrains.annotations.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.function.*;

public final class HoverEvent implements Examinable, HoverEventSource, StyleBuilderApplicable
{
    private final Action action;
    private final Object value;
    
    @NotNull
    public static HoverEvent showText(@NotNull final ComponentLike text) {
        return showText(text.asComponent());
    }
    
    @NotNull
    public static HoverEvent showText(@NotNull final Component text) {
        return new HoverEvent(Action.SHOW_TEXT, text);
    }
    
    @NotNull
    public static HoverEvent showItem(@NotNull final Key item, final int count) {
        return showItem(item, count, null);
    }
    
    @NotNull
    public static HoverEvent showItem(@NotNull final Keyed item, final int count) {
        return showItem(item, count, null);
    }
    
    @NotNull
    public static HoverEvent showItem(@NotNull final Key item, final int count, @Nullable final BinaryTagHolder nbt) {
        return showItem(ShowItem.of(item, count, nbt));
    }
    
    @NotNull
    public static HoverEvent showItem(@NotNull final Keyed item, final int count, @Nullable final BinaryTagHolder nbt) {
        return showItem(ShowItem.of(item, count, nbt));
    }
    
    @NotNull
    public static HoverEvent showItem(@NotNull final ShowItem item) {
        return new HoverEvent(Action.SHOW_ITEM, item);
    }
    
    @NotNull
    public static HoverEvent showEntity(@NotNull final Key type, @NotNull final UUID id) {
        return showEntity(type, id, null);
    }
    
    @NotNull
    public static HoverEvent showEntity(@NotNull final Keyed type, @NotNull final UUID id) {
        return showEntity(type, id, null);
    }
    
    @NotNull
    public static HoverEvent showEntity(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
        return showEntity(ShowEntity.of(type, id, name));
    }
    
    @NotNull
    public static HoverEvent showEntity(@NotNull final Keyed type, @NotNull final UUID id, @Nullable final Component name) {
        return showEntity(ShowEntity.of(type, id, name));
    }
    
    @NotNull
    public static HoverEvent showEntity(@NotNull final ShowEntity entity) {
        return new HoverEvent(Action.SHOW_ENTITY, entity);
    }
    
    @NotNull
    public static HoverEvent hoverEvent(@NotNull final Action action, @NotNull final Object value) {
        return new HoverEvent(action, value);
    }
    
    private HoverEvent(@NotNull final Action action, @NotNull final Object value) {
        this.action = Objects.requireNonNull(action, "action");
        this.value = Objects.requireNonNull(value, "value");
    }
    
    @NotNull
    public Action action() {
        return this.action;
    }
    
    @NotNull
    public Object value() {
        return this.value;
    }
    
    @NotNull
    public HoverEvent value(@NotNull final Object value) {
        return new HoverEvent(this.action, value);
    }
    
    @NotNull
    public HoverEvent withRenderedValue(@NotNull final ComponentRenderer renderer, @NotNull final Object context) {
        final Object value = this.value;
        final Object render = Action.access$000(this.action).render(renderer, context, value);
        if (render != value) {
            return new HoverEvent(this.action, render);
        }
        return this;
    }
    
    @NotNull
    @Override
    public HoverEvent asHoverEvent() {
        return this;
    }
    
    @NotNull
    @Override
    public HoverEvent asHoverEvent(@NotNull final UnaryOperator op) {
        if (op == UnaryOperator.identity()) {
            return this;
        }
        return new HoverEvent(this.action, op.apply(this.value));
    }
    
    @Override
    public void styleApply(final Style.Builder style) {
        style.hoverEvent(this);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final HoverEvent hoverEvent = (HoverEvent)other;
        return this.action == hoverEvent.action && this.value.equals(hoverEvent.value);
    }
    
    @Override
    public int hashCode() {
        return 31 * this.action.hashCode() + this.value.hashCode();
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("action", this.action), ExaminableProperty.of("value", this.value) });
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    public static final class Action
    {
        public static final Action SHOW_TEXT;
        public static final Action SHOW_ITEM;
        public static final Action SHOW_ENTITY;
        public static final Index NAMES;
        private final String name;
        private final Class type;
        private final boolean readable;
        private final Renderer renderer;
        
        Action(final String name, final Class type, final boolean readable, final Renderer renderer) {
            this.name = name;
            this.type = type;
            this.readable = readable;
            this.renderer = renderer;
        }
        
        @NotNull
        public Class type() {
            return this.type;
        }
        
        public boolean readable() {
            return this.readable;
        }
        
        @NotNull
        @Override
        public String toString() {
            return this.name;
        }
        
        private static String lambda$static$0(final Action action) {
            return action.name;
        }
        
        static Renderer access$000(final Action action) {
            return action.renderer;
        }
        
        static {
            SHOW_TEXT = new Action("show_text", Component.class, true, new Renderer() {
                @NotNull
                public Component render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final Component value) {
                    return renderer.render(value, context);
                }
                
                @NotNull
                @Override
                public Object render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final Object value) {
                    return this.render(renderer, context, (Component)value);
                }
            });
            SHOW_ITEM = new Action("show_item", ShowItem.class, true, new Renderer() {
                @NotNull
                public ShowItem render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final ShowItem value) {
                    return value;
                }
                
                @NotNull
                @Override
                public Object render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final Object value) {
                    return this.render(renderer, context, (ShowItem)value);
                }
            });
            SHOW_ENTITY = new Action("show_entity", ShowEntity.class, true, new Renderer() {
                @NotNull
                public ShowEntity render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final ShowEntity value) {
                    if (ShowEntity.access$100(value) == null) {
                        return value;
                    }
                    return value.name(renderer.render(ShowEntity.access$100(value), context));
                }
                
                @NotNull
                @Override
                public Object render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final Object value) {
                    return this.render(renderer, context, (ShowEntity)value);
                }
            });
            NAMES = Index.create(Action::lambda$static$0, Action.SHOW_TEXT, Action.SHOW_ITEM, Action.SHOW_ENTITY);
        }
        
        @FunctionalInterface
        interface Renderer
        {
            @NotNull
            Object render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final Object value);
        }
    }
    
    public static final class ShowItem implements Examinable
    {
        private final Key item;
        private final int count;
        @Nullable
        private final BinaryTagHolder nbt;
        
        @NotNull
        public static ShowItem of(@NotNull final Key item, final int count) {
            return of(item, count, null);
        }
        
        @NotNull
        public static ShowItem of(@NotNull final Keyed item, final int count) {
            return of(item, count, null);
        }
        
        @NotNull
        public static ShowItem of(@NotNull final Key item, final int count, @Nullable final BinaryTagHolder nbt) {
            return new ShowItem(Objects.requireNonNull(item, "item"), count, nbt);
        }
        
        @NotNull
        public static ShowItem of(@NotNull final Keyed item, final int count, @Nullable final BinaryTagHolder nbt) {
            return new ShowItem(Objects.requireNonNull(item, "item").key(), count, nbt);
        }
        
        private ShowItem(@NotNull final Key item, final int count, @Nullable final BinaryTagHolder nbt) {
            this.item = item;
            this.count = count;
            this.nbt = nbt;
        }
        
        @NotNull
        public Key item() {
            return this.item;
        }
        
        @NotNull
        public ShowItem item(@NotNull final Key item) {
            if (Objects.requireNonNull(item, "item").equals(this.item)) {
                return this;
            }
            return new ShowItem(item, this.count, this.nbt);
        }
        
        public int count() {
            return this.count;
        }
        
        @NotNull
        public ShowItem count(final int count) {
            if (count == this.count) {
                return this;
            }
            return new ShowItem(this.item, count, this.nbt);
        }
        
        @Nullable
        public BinaryTagHolder nbt() {
            return this.nbt;
        }
        
        @NotNull
        public ShowItem nbt(@Nullable final BinaryTagHolder nbt) {
            if (Objects.equals(nbt, this.nbt)) {
                return this;
            }
            return new ShowItem(this.item, this.count, nbt);
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || this.getClass() != other.getClass()) {
                return false;
            }
            final ShowItem showItem = (ShowItem)other;
            return this.item.equals(showItem.item) && this.count == showItem.count && Objects.equals(this.nbt, showItem.nbt);
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * this.item.hashCode() + Integer.hashCode(this.count)) + Objects.hashCode(this.nbt);
        }
        
        @NotNull
        @Override
        public Stream examinableProperties() {
            return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("item", this.item), ExaminableProperty.of("count", this.count), ExaminableProperty.of("nbt", this.nbt) });
        }
        
        @Override
        public String toString() {
            return (String)this.examine(StringExaminer.simpleEscaping());
        }
    }
    
    public static final class ShowEntity implements Examinable
    {
        private final Key type;
        private final UUID id;
        private final Component name;
        
        @NotNull
        public static ShowEntity of(@NotNull final Key type, @NotNull final UUID id) {
            return of(type, id, null);
        }
        
        @NotNull
        public static ShowEntity of(@NotNull final Keyed type, @NotNull final UUID id) {
            return of(type, id, null);
        }
        
        @NotNull
        public static ShowEntity of(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
            return new ShowEntity(Objects.requireNonNull(type, "type"), Objects.requireNonNull(id, "id"), name);
        }
        
        @NotNull
        public static ShowEntity of(@NotNull final Keyed type, @NotNull final UUID id, @Nullable final Component name) {
            return new ShowEntity(Objects.requireNonNull(type, "type").key(), Objects.requireNonNull(id, "id"), name);
        }
        
        private ShowEntity(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
            this.type = type;
            this.id = id;
            this.name = name;
        }
        
        @NotNull
        public Key type() {
            return this.type;
        }
        
        @NotNull
        public ShowEntity type(@NotNull final Key type) {
            if (Objects.requireNonNull(type, "type").equals(this.type)) {
                return this;
            }
            return new ShowEntity(type, this.id, this.name);
        }
        
        @NotNull
        public ShowEntity type(@NotNull final Keyed type) {
            return this.type(Objects.requireNonNull(type, "type").key());
        }
        
        @NotNull
        public UUID id() {
            return this.id;
        }
        
        @NotNull
        public ShowEntity id(@NotNull final UUID id) {
            if (Objects.requireNonNull(id).equals(this.id)) {
                return this;
            }
            return new ShowEntity(this.type, id, this.name);
        }
        
        @Nullable
        public Component name() {
            return this.name;
        }
        
        @NotNull
        public ShowEntity name(@Nullable final Component name) {
            if (Objects.equals(name, this.name)) {
                return this;
            }
            return new ShowEntity(this.type, this.id, name);
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || this.getClass() != other.getClass()) {
                return false;
            }
            final ShowEntity showEntity = (ShowEntity)other;
            return this.type.equals(showEntity.type) && this.id.equals(showEntity.id) && Objects.equals(this.name, showEntity.name);
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * this.type.hashCode() + this.id.hashCode()) + Objects.hashCode(this.name);
        }
        
        @NotNull
        @Override
        public Stream examinableProperties() {
            return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("type", this.type), ExaminableProperty.of("id", this.id), ExaminableProperty.of("name", this.name) });
        }
        
        @Override
        public String toString() {
            return (String)this.examine(StringExaminer.simpleEscaping());
        }
        
        static Component access$100(final ShowEntity showEntity) {
            return showEntity.name;
        }
    }
}
