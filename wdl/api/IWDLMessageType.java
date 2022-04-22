package wdl.api;

import net.minecraft.util.*;

public interface IWDLMessageType
{
    EnumChatFormatting getTitleColor();
    
    EnumChatFormatting getTextColor();
    
    String getDisplayName();
    
    String getDescription();
    
    boolean isEnabledByDefault();
}
