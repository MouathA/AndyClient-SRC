package net.minecraft.util;

import java.util.*;

public class ChatComponentSelector extends ChatComponentStyle
{
    private final String field_179993_b;
    private static final String __OBFID;
    
    public ChatComponentSelector(final String field_179993_b) {
        this.field_179993_b = field_179993_b;
    }
    
    public String func_179992_g() {
        return this.field_179993_b;
    }
    
    @Override
    public String getUnformattedTextForChat() {
        return this.field_179993_b;
    }
    
    public ChatComponentSelector func_179991_h() {
        final ChatComponentSelector chatComponentSelector = new ChatComponentSelector(this.field_179993_b);
        chatComponentSelector.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        while (iterator.hasNext()) {
            chatComponentSelector.appendSibling(iterator.next().createCopy());
        }
        return chatComponentSelector;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ChatComponentSelector && this.field_179993_b.equals(((ChatComponentSelector)o).field_179993_b) && super.equals(o));
    }
    
    @Override
    public String toString() {
        return "SelectorComponent{pattern='" + this.field_179993_b + '\'' + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.func_179991_h();
    }
    
    static {
        __OBFID = "CL_00002308";
    }
}
