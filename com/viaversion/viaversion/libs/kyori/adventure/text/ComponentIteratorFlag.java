package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;

@ApiStatus.NonExtendable
public enum ComponentIteratorFlag
{
    INCLUDE_HOVER_SHOW_ENTITY_NAME, 
    INCLUDE_HOVER_SHOW_TEXT_COMPONENT, 
    INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS;
    
    private static final ComponentIteratorFlag[] $VALUES;
    
    static {
        $VALUES = new ComponentIteratorFlag[] { ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME, ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT, ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS };
    }
}
