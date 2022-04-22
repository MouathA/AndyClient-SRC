package net.minecraft.util;

import java.util.*;

public class ChatComponentText extends ChatComponentStyle
{
    private final String text;
    private static final String __OBFID;
    
    public ChatComponentText(final String text) {
        this.text = text;
    }
    
    public String getChatComponentText_TextValue() {
        return this.text;
    }
    
    @Override
    public String getUnformattedTextForChat() {
        return this.text;
    }
    
    @Override
    public ChatComponentText createCopy() {
        final ChatComponentText chatComponentText = new ChatComponentText(this.text);
        chatComponentText.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        while (iterator.hasNext()) {
            chatComponentText.appendSibling(iterator.next().createCopy());
        }
        return chatComponentText;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ChatComponentText && this.text.equals(((ChatComponentText)o).getChatComponentText_TextValue()) && super.equals(o));
    }
    
    @Override
    public String toString() {
        return "TextComponent{text='" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.createCopy();
    }
    
    static {
        __OBFID = "CL_00001269";
    }
}
