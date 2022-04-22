package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.libs.gson.*;

public class TranslatableRewriter1_16 extends TranslatableRewriter
{
    private static final ChatColor[] COLORS;
    
    public TranslatableRewriter1_16(final BackwardsProtocol backwardsProtocol) {
        super(backwardsProtocol);
    }
    
    @Override
    public void processText(final JsonElement jsonElement) {
        super.processText(jsonElement);
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return;
        }
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final JsonPrimitive asJsonPrimitive = asJsonObject.getAsJsonPrimitive("color");
        if (asJsonPrimitive != null) {
            final String asString = asJsonPrimitive.getAsString();
            if (!asString.isEmpty() && asString.charAt(0) == '#') {
                asJsonObject.addProperty("color", this.getClosestChatColor(Integer.parseInt(asString.substring(1), 16)));
            }
        }
        if (asJsonObject.getAsJsonObject("hoverEvent") != null) {
            final JsonObject asJsonObject2 = ((JsonObject)ChatRewriter.HOVER_GSON_SERIALIZER.serializeToTree(ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree(asJsonObject))).getAsJsonObject("hoverEvent");
            asJsonObject2.remove("contents");
            asJsonObject.add("hoverEvent", asJsonObject2);
        }
    }
    
    private String getClosestChatColor(final int n) {
        final int n2 = n >> 16 & 0xFF;
        final int n3 = n >> 8 & 0xFF;
        final int n4 = n & 0xFF;
        ChatColor chatColor = null;
        final ChatColor[] colors = TranslatableRewriter1_16.COLORS;
        while (0 < colors.length) {
            final ChatColor chatColor2 = colors[0];
            if (ChatColor.access$000(chatColor2) == n) {
                return ChatColor.access$100(chatColor2);
            }
            final int n5 = (ChatColor.access$200(chatColor2) + n2) / 2;
            final int n6 = ChatColor.access$200(chatColor2) - n2;
            final int n7 = ChatColor.access$300(chatColor2) - n3;
            final int n8 = ChatColor.access$400(chatColor2) - n4;
            final int n9 = (2 + (n5 >> 8)) * n6 * n6 + 4 * n7 * n7 + (2 + (255 - n5 >> 8)) * n8 * n8;
            if (chatColor == null || n9 < 0) {
                chatColor = chatColor2;
            }
            int n10 = 0;
            ++n10;
        }
        return ChatColor.access$100(chatColor);
    }
    
    static {
        COLORS = new ChatColor[] { new ChatColor("black", 0), new ChatColor("dark_blue", 170), new ChatColor("dark_green", 43520), new ChatColor("dark_aqua", 43690), new ChatColor("dark_red", 11141120), new ChatColor("dark_purple", 11141290), new ChatColor("gold", 16755200), new ChatColor("gray", 11184810), new ChatColor("dark_gray", 5592405), new ChatColor("blue", 5592575), new ChatColor("green", 5635925), new ChatColor("aqua", 5636095), new ChatColor("red", 16733525), new ChatColor("light_purple", 16733695), new ChatColor("yellow", 16777045), new ChatColor("white", 16777215) };
    }
    
    private static final class ChatColor
    {
        private final String colorName;
        private final int rgb;
        private final int r;
        private final int g;
        private final int b;
        
        ChatColor(final String colorName, final int rgb) {
            this.colorName = colorName;
            this.rgb = rgb;
            this.r = (rgb >> 16 & 0xFF);
            this.g = (rgb >> 8 & 0xFF);
            this.b = (rgb & 0xFF);
        }
        
        static int access$000(final ChatColor chatColor) {
            return chatColor.rgb;
        }
        
        static String access$100(final ChatColor chatColor) {
            return chatColor.colorName;
        }
        
        static int access$200(final ChatColor chatColor) {
            return chatColor.r;
        }
        
        static int access$300(final ChatColor chatColor) {
            return chatColor.g;
        }
        
        static int access$400(final ChatColor chatColor) {
            return chatColor.b;
        }
    }
}
