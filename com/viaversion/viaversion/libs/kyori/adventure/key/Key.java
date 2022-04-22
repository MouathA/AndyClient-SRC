package com.viaversion.viaversion.libs.kyori.adventure.key;

import org.jetbrains.annotations.*;
import org.intellij.lang.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

public interface Key extends Comparable, Examinable
{
    public static final String MINECRAFT_NAMESPACE = "minecraft";
    
    @NotNull
    default Key key(@NotNull @Pattern("([a-z0-9_\\-.]+:)?[a-z0-9_\\-./]+") final String string) {
        return key(string, ':');
    }
    
    @NotNull
    default Key key(@NotNull final String string, final char character) {
        final int index = string.indexOf(character);
        return key((index >= 1) ? string.substring(0, index) : "minecraft", (index >= 0) ? string.substring(index + 1) : string);
    }
    
    @NotNull
    default Key key(@NotNull final Namespaced namespaced, @NotNull @Pattern("[a-z0-9_\\-./]+") final String value) {
        return key(namespaced.namespace(), value);
    }
    
    @NotNull
    default Key key(@NotNull @Pattern("[a-z0-9_\\-.]+") final String namespace, @NotNull @Pattern("[a-z0-9_\\-./]+") final String value) {
        return new KeyImpl(namespace, value);
    }
    
    @NotNull
    String namespace();
    
    @NotNull
    String value();
    
    @NotNull
    String asString();
    
    @NotNull
    default Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("namespace", this.namespace()), ExaminableProperty.of("value", this.value()) });
    }
    
    default int compareTo(@NotNull final Key that) {
        final int compareTo = this.value().compareTo(that.value());
        if (compareTo != 0) {
            return KeyImpl.clampCompare(compareTo);
        }
        return KeyImpl.clampCompare(this.namespace().compareTo(that.namespace()));
    }
    
    default int compareTo(@NotNull final Object that) {
        return this.compareTo((Key)that);
    }
}
