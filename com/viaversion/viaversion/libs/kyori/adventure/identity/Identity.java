package com.viaversion.viaversion.libs.kyori.adventure.identity;

import com.viaversion.viaversion.libs.kyori.adventure.pointer.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.*;

public interface Identity extends Examinable
{
    public static final Pointer NAME = Pointer.pointer(String.class, Key.key("adventure", "name"));
    public static final Pointer UUID = Pointer.pointer(UUID.class, Key.key("adventure", "uuid"));
    public static final Pointer DISPLAY_NAME = Pointer.pointer(Component.class, Key.key("adventure", "display_name"));
    public static final Pointer LOCALE = Pointer.pointer(Locale.class, Key.key("adventure", "locale"));
    
    @NotNull
    default Identity nil() {
        return Identities.NIL;
    }
    
    @NotNull
    default Identity identity(@NotNull final UUID uuid) {
        if (uuid.equals(Identities.NIL.uuid())) {
            return Identities.NIL;
        }
        return new IdentityImpl(uuid);
    }
    
    @NotNull
    UUID uuid();
    
    @NotNull
    default Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("uuid", this.uuid()));
    }
}
