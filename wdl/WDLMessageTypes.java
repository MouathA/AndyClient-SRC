package wdl;

import wdl.api.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;

public enum WDLMessageTypes implements IWDLMessageType
{
    INFO("INFO", 0, "wdl.messages.message.info", EnumChatFormatting.RED, EnumChatFormatting.GOLD, true, MessageTypeCategory.CORE_RECOMMENDED), 
    ERROR("ERROR", 1, "wdl.messages.message.error", EnumChatFormatting.DARK_GREEN, EnumChatFormatting.DARK_RED, true, MessageTypeCategory.CORE_RECOMMENDED), 
    UPDATES("UPDATES", 2, "wdl.messages.message.updates", EnumChatFormatting.RED, EnumChatFormatting.GOLD, true, MessageTypeCategory.CORE_RECOMMENDED), 
    LOAD_TILE_ENTITY("LOAD_TILE_ENTITY", 3, "wdl.messages.message.loadingTileEntity", false), 
    ON_WORLD_LOAD("ON_WORLD_LOAD", 4, "wdl.messages.message.onWorldLoad", false), 
    ON_BLOCK_EVENT("ON_BLOCK_EVENT", 5, "wdl.messages.message.blockEvent", true), 
    ON_MAP_SAVED("ON_MAP_SAVED", 6, "wdl.messages.message.mapDataSaved", false), 
    ON_CHUNK_NO_LONGER_NEEDED("ON_CHUNK_NO_LONGER_NEEDED", 7, "wdl.messages.message.chunkUnloaded", false), 
    ON_GUI_CLOSED_INFO("ON_GUI_CLOSED_INFO", 8, "wdl.messages.message.guiClosedInfo", true), 
    ON_GUI_CLOSED_WARNING("ON_GUI_CLOSED_WARNING", 9, "wdl.messages.message.guiClosedWarning", true), 
    SAVING("SAVING", 10, "wdl.messages.message.saving", true), 
    REMOVE_ENTITY("REMOVE_ENTITY", 11, "wdl.messages.message.removeEntity", false), 
    PLUGIN_CHANNEL_MESSAGE("PLUGIN_CHANNEL_MESSAGE", 12, "wdl.messages.message.pluginChannel", false), 
    UPDATE_DEBUG("UPDATE_DEBUG", 13, "wdl.messages.message.updateDebug", false);
    
    private final String displayTextKey;
    private final EnumChatFormatting titleColor;
    private final EnumChatFormatting textColor;
    private final String descriptionKey;
    private final boolean enabledByDefault;
    private static final WDLMessageTypes[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new WDLMessageTypes[] { WDLMessageTypes.INFO, WDLMessageTypes.ERROR, WDLMessageTypes.UPDATES, WDLMessageTypes.LOAD_TILE_ENTITY, WDLMessageTypes.ON_WORLD_LOAD, WDLMessageTypes.ON_BLOCK_EVENT, WDLMessageTypes.ON_MAP_SAVED, WDLMessageTypes.ON_CHUNK_NO_LONGER_NEEDED, WDLMessageTypes.ON_GUI_CLOSED_INFO, WDLMessageTypes.ON_GUI_CLOSED_WARNING, WDLMessageTypes.SAVING, WDLMessageTypes.REMOVE_ENTITY, WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, WDLMessageTypes.UPDATE_DEBUG };
    }
    
    private WDLMessageTypes(final String s, final int n, final String s2, final boolean b) {
        this(s, n, s2, EnumChatFormatting.DARK_GREEN, EnumChatFormatting.GOLD, b, MessageTypeCategory.CORE_DEBUG);
    }
    
    private WDLMessageTypes(final String s, final int n, final String s2, final EnumChatFormatting titleColor, final EnumChatFormatting textColor, final boolean enabledByDefault, final MessageTypeCategory messageTypeCategory) {
        this.displayTextKey = String.valueOf(s2) + ".text";
        this.titleColor = titleColor;
        this.textColor = textColor;
        this.descriptionKey = String.valueOf(s2) + ".description";
        this.enabledByDefault = enabledByDefault;
        WDLMessages.registerMessage(this.name(), this, messageTypeCategory);
    }
    
    @Override
    public String getDisplayName() {
        return I18n.format(this.displayTextKey, new Object[0]);
    }
    
    @Override
    public EnumChatFormatting getTitleColor() {
        return this.titleColor;
    }
    
    @Override
    public EnumChatFormatting getTextColor() {
        return this.textColor;
    }
    
    @Override
    public String getDescription() {
        return I18n.format(this.descriptionKey, new Object[0]);
    }
    
    @Override
    public boolean isEnabledByDefault() {
        return this.enabledByDefault;
    }
}
