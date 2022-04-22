package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.io.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

public interface LegacyHoverEventSerializer
{
    HoverEvent.ShowItem deserializeShowItem(@NotNull final Component input) throws IOException;
    
    HoverEvent.ShowEntity deserializeShowEntity(@NotNull final Component input, final Codec.Decoder componentDecoder) throws IOException;
    
    @NotNull
    Component serializeShowItem(final HoverEvent.ShowItem input) throws IOException;
    
    @NotNull
    Component serializeShowEntity(final HoverEvent.ShowEntity input, final Codec.Encoder componentEncoder) throws IOException;
}
