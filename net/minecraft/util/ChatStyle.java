package net.minecraft.util;

import net.minecraft.event.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class ChatStyle
{
    private ChatStyle parentStyle;
    private EnumChatFormatting color;
    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Boolean strikethrough;
    private Boolean obfuscated;
    private ClickEvent chatClickEvent;
    private HoverEvent chatHoverEvent;
    private String insertion;
    private static final ChatStyle rootStyle;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001266";
        rootStyle = new ChatStyle() {
            private static final String __OBFID;
            
            @Override
            public EnumChatFormatting getColor() {
                return null;
            }
            
            @Override
            public boolean getBold() {
                return false;
            }
            
            @Override
            public boolean getItalic() {
                return false;
            }
            
            @Override
            public boolean getStrikethrough() {
                return false;
            }
            
            @Override
            public boolean getUnderlined() {
                return false;
            }
            
            @Override
            public boolean getObfuscated() {
                return false;
            }
            
            @Override
            public ClickEvent getChatClickEvent() {
                return null;
            }
            
            @Override
            public HoverEvent getChatHoverEvent() {
                return null;
            }
            
            @Override
            public String getInsertion() {
                return null;
            }
            
            @Override
            public ChatStyle setColor(final EnumChatFormatting enumChatFormatting) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setBold(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setItalic(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setStrikethrough(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setUnderlined(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setObfuscated(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setChatClickEvent(final ClickEvent clickEvent) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setChatHoverEvent(final HoverEvent hoverEvent) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setParentStyle(final ChatStyle chatStyle) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public String toString() {
                return "Style.ROOT";
            }
            
            @Override
            public ChatStyle createShallowCopy() {
                return this;
            }
            
            @Override
            public ChatStyle createDeepCopy() {
                return this;
            }
            
            @Override
            public String getFormattingCode() {
                return "";
            }
            
            static {
                __OBFID = "CL_00001267";
            }
        };
    }
    
    public EnumChatFormatting getColor() {
        return (this.color == null) ? this.getParent().getColor() : this.color;
    }
    
    public boolean getBold() {
        return (this.bold == null) ? this.getParent().getBold() : this.bold;
    }
    
    public boolean getItalic() {
        return (this.italic == null) ? this.getParent().getItalic() : this.italic;
    }
    
    public boolean getStrikethrough() {
        return (this.strikethrough == null) ? this.getParent().getStrikethrough() : this.strikethrough;
    }
    
    public boolean getUnderlined() {
        return (this.underlined == null) ? this.getParent().getUnderlined() : this.underlined;
    }
    
    public boolean getObfuscated() {
        return (this.obfuscated == null) ? this.getParent().getObfuscated() : this.obfuscated;
    }
    
    public boolean isEmpty() {
        return this.bold == null && this.italic == null && this.strikethrough == null && this.underlined == null && this.obfuscated == null && this.color == null && this.chatClickEvent == null && this.chatHoverEvent == null;
    }
    
    public ClickEvent getChatClickEvent() {
        return (this.chatClickEvent == null) ? this.getParent().getChatClickEvent() : this.chatClickEvent;
    }
    
    public HoverEvent getChatHoverEvent() {
        return (this.chatHoverEvent == null) ? this.getParent().getChatHoverEvent() : this.chatHoverEvent;
    }
    
    public String getInsertion() {
        return (this.insertion == null) ? this.getParent().getInsertion() : this.insertion;
    }
    
    public ChatStyle setColor(final EnumChatFormatting color) {
        this.color = color;
        return this;
    }
    
    public ChatStyle setBold(final Boolean bold) {
        this.bold = bold;
        return this;
    }
    
    public ChatStyle setItalic(final Boolean italic) {
        this.italic = italic;
        return this;
    }
    
    public ChatStyle setStrikethrough(final Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }
    
    public ChatStyle setUnderlined(final Boolean underlined) {
        this.underlined = underlined;
        return this;
    }
    
    public ChatStyle setObfuscated(final Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }
    
    public ChatStyle setChatClickEvent(final ClickEvent chatClickEvent) {
        this.chatClickEvent = chatClickEvent;
        return this;
    }
    
    public ChatStyle setChatHoverEvent(final HoverEvent chatHoverEvent) {
        this.chatHoverEvent = chatHoverEvent;
        return this;
    }
    
    public ChatStyle setInsertion(final String insertion) {
        this.insertion = insertion;
        return this;
    }
    
    public ChatStyle setParentStyle(final ChatStyle parentStyle) {
        this.parentStyle = parentStyle;
        return this;
    }
    
    public String getFormattingCode() {
        if (this.isEmpty()) {
            return (this.parentStyle != null) ? this.parentStyle.getFormattingCode() : "";
        }
        final StringBuilder sb = new StringBuilder();
        if (this.getColor() != null) {
            sb.append(this.getColor());
        }
        if (this.getBold()) {
            sb.append(EnumChatFormatting.BOLD);
        }
        if (this.getItalic()) {
            sb.append(EnumChatFormatting.ITALIC);
        }
        if (this.getUnderlined()) {
            sb.append(EnumChatFormatting.UNDERLINE);
        }
        if (this.getObfuscated()) {
            sb.append(EnumChatFormatting.OBFUSCATED);
        }
        if (this.getStrikethrough()) {
            sb.append(EnumChatFormatting.STRIKETHROUGH);
        }
        return sb.toString();
    }
    
    private ChatStyle getParent() {
        return (this.parentStyle == null) ? ChatStyle.rootStyle : this.parentStyle;
    }
    
    @Override
    public String toString() {
        return "Style{hasParent=" + (this.parentStyle != null) + ", color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", obfuscated=" + this.obfuscated + ", clickEvent=" + this.getChatClickEvent() + ", hoverEvent=" + this.getChatHoverEvent() + ", insertion=" + this.getInsertion() + '}';
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatStyle)) {
            return false;
        }
        final ChatStyle chatStyle = (ChatStyle)o;
        if (this.getBold() == chatStyle.getBold() && this.getColor() == chatStyle.getColor() && this.getItalic() == chatStyle.getItalic() && this.getObfuscated() == chatStyle.getObfuscated() && this.getStrikethrough() == chatStyle.getStrikethrough() && this.getUnderlined() == chatStyle.getUnderlined()) {
            if (this.getChatClickEvent() != null) {
                if (!this.getChatClickEvent().equals(chatStyle.getChatClickEvent())) {
                    return false;
                }
            }
            else if (chatStyle.getChatClickEvent() != null) {
                return false;
            }
            if (this.getChatHoverEvent() != null) {
                if (!this.getChatHoverEvent().equals(chatStyle.getChatHoverEvent())) {
                    return false;
                }
            }
            else if (chatStyle.getChatHoverEvent() != null) {
                return false;
            }
            if (this.getInsertion() != null) {
                if (!this.getInsertion().equals(chatStyle.getInsertion())) {
                    return false;
                }
            }
            else if (chatStyle.getInsertion() != null) {
                return false;
            }
            return false;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * this.color.hashCode() + this.bold.hashCode()) + this.italic.hashCode()) + this.underlined.hashCode()) + this.strikethrough.hashCode()) + this.obfuscated.hashCode()) + this.chatClickEvent.hashCode()) + this.chatHoverEvent.hashCode()) + this.insertion.hashCode();
    }
    
    public ChatStyle createShallowCopy() {
        final ChatStyle chatStyle = new ChatStyle();
        chatStyle.bold = this.bold;
        chatStyle.italic = this.italic;
        chatStyle.strikethrough = this.strikethrough;
        chatStyle.underlined = this.underlined;
        chatStyle.obfuscated = this.obfuscated;
        chatStyle.color = this.color;
        chatStyle.chatClickEvent = this.chatClickEvent;
        chatStyle.chatHoverEvent = this.chatHoverEvent;
        chatStyle.parentStyle = this.parentStyle;
        chatStyle.insertion = this.insertion;
        return chatStyle;
    }
    
    public ChatStyle createDeepCopy() {
        final ChatStyle chatStyle = new ChatStyle();
        chatStyle.setBold(this.getBold());
        chatStyle.setItalic(this.getItalic());
        chatStyle.setStrikethrough(this.getStrikethrough());
        chatStyle.setUnderlined(this.getUnderlined());
        chatStyle.setObfuscated(this.getObfuscated());
        chatStyle.setColor(this.getColor());
        chatStyle.setChatClickEvent(this.getChatClickEvent());
        chatStyle.setChatHoverEvent(this.getChatHoverEvent());
        chatStyle.setInsertion(this.getInsertion());
        return chatStyle;
    }
    
    public ChatStyle setClickEvent(final ClickEvent chatClickEvent) {
        this.chatClickEvent = chatClickEvent;
        return this;
    }
    
    static void access$0(final ChatStyle chatStyle, final Boolean bold) {
        chatStyle.bold = bold;
    }
    
    static void access$1(final ChatStyle chatStyle, final Boolean italic) {
        chatStyle.italic = italic;
    }
    
    static void access$2(final ChatStyle chatStyle, final Boolean underlined) {
        chatStyle.underlined = underlined;
    }
    
    static void access$3(final ChatStyle chatStyle, final Boolean strikethrough) {
        chatStyle.strikethrough = strikethrough;
    }
    
    static void access$4(final ChatStyle chatStyle, final Boolean obfuscated) {
        chatStyle.obfuscated = obfuscated;
    }
    
    static void access$5(final ChatStyle chatStyle, final EnumChatFormatting color) {
        chatStyle.color = color;
    }
    
    static void access$6(final ChatStyle chatStyle, final String insertion) {
        chatStyle.insertion = insertion;
    }
    
    static void access$7(final ChatStyle chatStyle, final ClickEvent chatClickEvent) {
        chatStyle.chatClickEvent = chatClickEvent;
    }
    
    static void access$8(final ChatStyle chatStyle, final HoverEvent chatHoverEvent) {
        chatStyle.chatHoverEvent = chatHoverEvent;
    }
    
    static Boolean access$9(final ChatStyle chatStyle) {
        return chatStyle.bold;
    }
    
    static Boolean access$10(final ChatStyle chatStyle) {
        return chatStyle.italic;
    }
    
    static Boolean access$11(final ChatStyle chatStyle) {
        return chatStyle.underlined;
    }
    
    static Boolean access$12(final ChatStyle chatStyle) {
        return chatStyle.strikethrough;
    }
    
    static Boolean access$13(final ChatStyle chatStyle) {
        return chatStyle.obfuscated;
    }
    
    static EnumChatFormatting access$14(final ChatStyle chatStyle) {
        return chatStyle.color;
    }
    
    static String access$15(final ChatStyle chatStyle) {
        return chatStyle.insertion;
    }
    
    static ClickEvent access$16(final ChatStyle chatStyle) {
        return chatStyle.chatClickEvent;
    }
    
    static HoverEvent access$17(final ChatStyle chatStyle) {
        return chatStyle.chatHoverEvent;
    }
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final String __OBFID;
        
        @Override
        public ChatStyle deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            final ChatStyle chatStyle = new ChatStyle();
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            if (asJsonObject == null) {
                return null;
            }
            if (asJsonObject.has("bold")) {
                ChatStyle.access$0(chatStyle, asJsonObject.get("bold").getAsBoolean());
            }
            if (asJsonObject.has("italic")) {
                ChatStyle.access$1(chatStyle, asJsonObject.get("italic").getAsBoolean());
            }
            if (asJsonObject.has("underlined")) {
                ChatStyle.access$2(chatStyle, asJsonObject.get("underlined").getAsBoolean());
            }
            if (asJsonObject.has("strikethrough")) {
                ChatStyle.access$3(chatStyle, asJsonObject.get("strikethrough").getAsBoolean());
            }
            if (asJsonObject.has("obfuscated")) {
                ChatStyle.access$4(chatStyle, asJsonObject.get("obfuscated").getAsBoolean());
            }
            if (asJsonObject.has("color")) {
                ChatStyle.access$5(chatStyle, (EnumChatFormatting)jsonDeserializationContext.deserialize(asJsonObject.get("color"), EnumChatFormatting.class));
            }
            if (asJsonObject.has("insertion")) {
                ChatStyle.access$6(chatStyle, asJsonObject.get("insertion").getAsString());
            }
            if (asJsonObject.has("clickEvent")) {
                final JsonObject asJsonObject2 = asJsonObject.getAsJsonObject("clickEvent");
                if (asJsonObject2 != null) {
                    final JsonPrimitive asJsonPrimitive = asJsonObject2.getAsJsonPrimitive("action");
                    final ClickEvent.Action action = (asJsonPrimitive == null) ? null : ClickEvent.Action.getValueByCanonicalName(asJsonPrimitive.getAsString());
                    final JsonPrimitive asJsonPrimitive2 = asJsonObject2.getAsJsonPrimitive("value");
                    final String s = (asJsonPrimitive2 == null) ? null : asJsonPrimitive2.getAsString();
                    if (action != null && s != null && action.shouldAllowInChat()) {
                        ChatStyle.access$7(chatStyle, new ClickEvent(action, s));
                    }
                }
            }
            if (asJsonObject.has("hoverEvent")) {
                final JsonObject asJsonObject3 = asJsonObject.getAsJsonObject("hoverEvent");
                if (asJsonObject3 != null) {
                    final JsonPrimitive asJsonPrimitive3 = asJsonObject3.getAsJsonPrimitive("action");
                    final HoverEvent.Action action2 = (asJsonPrimitive3 == null) ? null : HoverEvent.Action.getValueByCanonicalName(asJsonPrimitive3.getAsString());
                    final IChatComponent chatComponent = (IChatComponent)jsonDeserializationContext.deserialize(asJsonObject3.get("value"), IChatComponent.class);
                    if (action2 != null && chatComponent != null && action2.shouldAllowInChat()) {
                        ChatStyle.access$8(chatStyle, new HoverEvent(action2, chatComponent));
                    }
                }
            }
            return chatStyle;
        }
        
        public JsonElement serialize(final ChatStyle chatStyle, final Type type, final JsonSerializationContext jsonSerializationContext) {
            if (chatStyle.isEmpty()) {
                return null;
            }
            final JsonObject jsonObject = new JsonObject();
            if (ChatStyle.access$9(chatStyle) != null) {
                jsonObject.addProperty("bold", ChatStyle.access$9(chatStyle));
            }
            if (ChatStyle.access$10(chatStyle) != null) {
                jsonObject.addProperty("italic", ChatStyle.access$10(chatStyle));
            }
            if (ChatStyle.access$11(chatStyle) != null) {
                jsonObject.addProperty("underlined", ChatStyle.access$11(chatStyle));
            }
            if (ChatStyle.access$12(chatStyle) != null) {
                jsonObject.addProperty("strikethrough", ChatStyle.access$12(chatStyle));
            }
            if (ChatStyle.access$13(chatStyle) != null) {
                jsonObject.addProperty("obfuscated", ChatStyle.access$13(chatStyle));
            }
            if (ChatStyle.access$14(chatStyle) != null) {
                jsonObject.add("color", jsonSerializationContext.serialize(ChatStyle.access$14(chatStyle)));
            }
            if (ChatStyle.access$15(chatStyle) != null) {
                jsonObject.add("insertion", jsonSerializationContext.serialize(ChatStyle.access$15(chatStyle)));
            }
            if (ChatStyle.access$16(chatStyle) != null) {
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("action", ChatStyle.access$16(chatStyle).getAction().getCanonicalName());
                jsonObject2.addProperty("value", ChatStyle.access$16(chatStyle).getValue());
                jsonObject.add("clickEvent", jsonObject2);
            }
            if (ChatStyle.access$17(chatStyle) != null) {
                final JsonObject jsonObject3 = new JsonObject();
                jsonObject3.addProperty("action", ChatStyle.access$17(chatStyle).getAction().getCanonicalName());
                jsonObject3.add("value", jsonSerializationContext.serialize(ChatStyle.access$17(chatStyle).getValue()));
                jsonObject.add("hoverEvent", jsonObject3);
            }
            return jsonObject;
        }
        
        @Override
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ChatStyle)o, type, jsonSerializationContext);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            __OBFID = "CL_00001268";
        }
    }
}
