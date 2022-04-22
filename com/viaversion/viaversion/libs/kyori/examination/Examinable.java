package com.viaversion.viaversion.libs.kyori.examination;

import org.jetbrains.annotations.*;
import java.util.stream.*;

public interface Examinable
{
    @NotNull
    default String examinableName() {
        return this.getClass().getSimpleName();
    }
    
    @NotNull
    default Stream examinableProperties() {
        return Stream.empty();
    }
    
    @NotNull
    default Object examine(@NotNull final Examiner examiner) {
        return examiner.examine(this);
    }
}
