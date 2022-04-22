package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

final class TextDecorationAndStateImpl implements TextDecorationAndState
{
    private final TextDecoration decoration;
    private final TextDecoration.State state;
    
    TextDecorationAndStateImpl(final TextDecoration decoration, final TextDecoration.State state) {
        this.decoration = decoration;
        this.state = state;
    }
    
    @NotNull
    @Override
    public TextDecoration decoration() {
        return this.decoration;
    }
    
    @Override
    public TextDecoration.State state() {
        return this.state;
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final TextDecorationAndStateImpl textDecorationAndStateImpl = (TextDecorationAndStateImpl)other;
        return this.decoration == textDecorationAndStateImpl.decoration && this.state == textDecorationAndStateImpl.state;
    }
    
    @Override
    public int hashCode() {
        return 31 * this.decoration.hashCode() + this.state.hashCode();
    }
}
