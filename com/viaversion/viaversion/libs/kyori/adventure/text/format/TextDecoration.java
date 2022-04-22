package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.function.*;
import org.jetbrains.annotations.*;

public enum TextDecoration implements StyleBuilderApplicable, TextFormat
{
    OBFUSCATED("obfuscated"), 
    BOLD("bold"), 
    STRIKETHROUGH("strikethrough"), 
    UNDERLINED("underlined"), 
    ITALIC("italic");
    
    public static final Index NAMES;
    private final String name;
    private static final TextDecoration[] $VALUES;
    
    private TextDecoration(final String name) {
        this.name = name;
    }
    
    @NotNull
    public final TextDecorationAndState as(final boolean state) {
        return this.as(State.byBoolean(state));
    }
    
    @NotNull
    public final TextDecorationAndState as(@NotNull final State state) {
        return new TextDecorationAndStateImpl(this, state);
    }
    
    @Override
    public void styleApply(final Style.Builder style) {
        style.decorate(this);
    }
    
    @NotNull
    @Override
    public String toString() {
        return this.name;
    }
    
    private static String lambda$static$0(final TextDecoration textDecoration) {
        return textDecoration.name;
    }
    
    static {
        $VALUES = new TextDecoration[] { TextDecoration.OBFUSCATED, TextDecoration.BOLD, TextDecoration.STRIKETHROUGH, TextDecoration.UNDERLINED, TextDecoration.ITALIC };
        NAMES = Index.create(TextDecoration.class, TextDecoration::lambda$static$0);
    }
    
    public enum State
    {
        NOT_SET("not_set"), 
        FALSE("false"), 
        TRUE("true");
        
        private final String name;
        private static final State[] $VALUES;
        
        private State(final String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @NotNull
        public static State byBoolean(final boolean flag) {
            return flag ? State.TRUE : State.FALSE;
        }
        
        @NotNull
        public static State byBoolean(@Nullable final Boolean flag) {
            return (flag == null) ? State.NOT_SET : byBoolean((boolean)flag);
        }
        
        static {
            $VALUES = new State[] { State.NOT_SET, State.FALSE, State.TRUE };
        }
    }
}
