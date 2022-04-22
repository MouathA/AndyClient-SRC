package wdl;

import org.apache.logging.log4j.*;
import wdl.api.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.event.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import java.util.*;

public class WDLMessages
{
    private static Logger logger;
    public static boolean enableAllMessages;
    private static List registrations;
    
    static {
        WDLMessages.logger = LogManager.getLogger();
        WDLMessages.enableAllMessages = true;
        WDLMessages.registrations = new ArrayList();
    }
    
    private static MessageRegistration getRegistration(final String s) {
        for (final MessageRegistration messageRegistration : WDLMessages.registrations) {
            if (messageRegistration.name.equals(s)) {
                return messageRegistration;
            }
        }
        return null;
    }
    
    private static MessageRegistration getRegistration(final IWDLMessageType iwdlMessageType) {
        for (final MessageRegistration messageRegistration : WDLMessages.registrations) {
            if (messageRegistration.type.equals(iwdlMessageType)) {
                return messageRegistration;
            }
        }
        return null;
    }
    
    public static void registerMessage(final String s, final IWDLMessageType iwdlMessageType, final MessageTypeCategory messageTypeCategory) {
        WDLMessages.registrations.add(new MessageRegistration(s, iwdlMessageType, messageTypeCategory));
        WDL.defaultProps.setProperty("Messages." + s, Boolean.toString(iwdlMessageType.isEnabledByDefault()));
        WDL.defaultProps.setProperty("MessageGroup." + messageTypeCategory.internalName, "true");
    }
    
    public static void toggleEnabled(final IWDLMessageType iwdlMessageType) {
        final MessageRegistration registration = getRegistration(iwdlMessageType);
        if (registration != null) {
            WDL.baseProps.setProperty("Messages." + registration.name, Boolean.toString(iwdlMessageType != null));
        }
    }
    
    public static void toggleGroupEnabled(final MessageTypeCategory messageTypeCategory) {
        WDL.baseProps.setProperty("MessageGroup." + messageTypeCategory.internalName, Boolean.toString(messageTypeCategory != 0));
    }
    
    public static ListMultimap getTypes() {
        final LinkedListMultimap create = LinkedListMultimap.create();
        for (final MessageRegistration messageRegistration : WDLMessages.registrations) {
            create.put(messageRegistration.category, messageRegistration.type);
        }
        return ImmutableListMultimap.copyOf(create);
    }
    
    public static void resetEnabledToDefaults() {
        WDL.baseProps.setProperty("Messages.enableAll", "true");
        WDLMessages.enableAllMessages = WDL.globalProps.getProperty("Messages.enableAll", "true").equals("true");
        for (final MessageRegistration messageRegistration : WDLMessages.registrations) {
            WDL.baseProps.setProperty("MessageGroup." + messageRegistration.category.internalName, WDL.globalProps.getProperty("MessageGroup." + messageRegistration.category.internalName, "true"));
            WDL.baseProps.setProperty("Messages." + messageRegistration.name, WDL.globalProps.getProperty("Messages." + messageRegistration.name));
        }
    }
    
    public static void onNewServer() {
        if (!WDL.baseProps.containsKey("Messages.enableAll")) {
            if (WDL.baseProps.containsKey("Debug.globalDebugEnabled")) {
                ((Hashtable<String, Object>)WDL.baseProps).put("Messages.enableAll", ((Hashtable<K, Object>)WDL.baseProps).remove("Debug.globalDebugEnabled"));
            }
            else {
                WDL.baseProps.setProperty("Messages.enableAll", WDL.globalProps.getProperty("Messages.enableAll", "true"));
            }
        }
        WDLMessages.enableAllMessages = WDL.baseProps.getProperty("Messages.enableAll").equals("true");
    }
    
    public static void chatMessage(final IWDLMessageType iwdlMessageType, final String s) {
        chatMessage(iwdlMessageType, new ChatComponentText(s));
    }
    
    public static void chatMessageTranslated(final IWDLMessageType iwdlMessageType, final String s, final Object... array) {
        final ArrayList<Throwable> list = new ArrayList<Throwable>();
        int n = 0;
        while (0 < array.length) {
            if (array[0] instanceof Entity) {
                final Entity entity = (Entity)array[0];
                final String entityType = EntityUtils.getEntityType(entity);
                Object customNameTag = null;
                final HoverEvent chatHoverEvent = entity.getDisplayName().getChatStyle().getChatHoverEvent();
                if (entity.hasCustomName()) {
                    customNameTag = entity.getCustomNameTag();
                }
                ChatComponentStyle chatComponentStyle;
                if (customNameTag != null) {
                    chatComponentStyle = new ChatComponentTranslation("wdl.messages.entityTypeAndCustomName", new Object[] { entityType, customNameTag });
                }
                else {
                    chatComponentStyle = new ChatComponentText(entityType);
                }
                chatComponentStyle.setChatStyle(chatComponentStyle.getChatStyle().setChatHoverEvent(chatHoverEvent));
                array[0] = chatComponentStyle;
            }
            else if (array[0] instanceof Throwable) {
                final Throwable t = (Throwable)array[0];
                final ChatComponentText chatComponentText = new ChatComponentText(t.toString());
                final StringWriter stringWriter = new StringWriter();
                t.printStackTrace(new PrintWriter(stringWriter));
                chatComponentText.setChatStyle(chatComponentText.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(stringWriter.toString().replace("\r", "").replace("\t", "    ")))));
                WDLMessages.logger.warn(t);
                array[0] = chatComponentText;
                list.add(t);
            }
            ++n;
        }
        chatMessage(iwdlMessageType, new ChatComponentTranslation(s, array));
        while (0 < list.size()) {
            WDLMessages.logger.warn("Exception #" + 1 + ": ", (Throwable)list.get(0));
            ++n;
        }
    }
    
    public static void chatMessage(final IWDLMessageType iwdlMessageType, final IChatComponent chatComponent) {
        final ChatComponentText chatComponentText = new ChatComponentText(I18n.format("wdl.messages.tooltip", iwdlMessageType.getDisplayName()).replace("\r", ""));
        final ChatComponentText chatComponentText2 = new ChatComponentText("");
        final ChatComponentText chatComponentText3 = new ChatComponentText("[WorldDL]");
        chatComponentText3.getChatStyle().setColor(iwdlMessageType.getTitleColor());
        chatComponentText3.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, chatComponentText));
        final ChatComponentText chatComponentText4 = new ChatComponentText(" ");
        chatComponentText4.getChatStyle().setColor(iwdlMessageType.getTextColor());
        chatComponentText4.appendSibling(chatComponent);
        chatComponentText2.appendSibling(chatComponentText3);
        chatComponentText2.appendSibling(chatComponentText4);
        if (iwdlMessageType == null) {
            Minecraft.getMinecraft();
            Minecraft.ingameGUI.getChatGUI().printChatMessage(chatComponentText2);
        }
        else {
            WDLMessages.logger.info(chatComponentText2.getUnformattedText());
        }
    }
    
    private static class MessageRegistration
    {
        public final String name;
        public final IWDLMessageType type;
        public final MessageTypeCategory category;
        
        public MessageRegistration(final String name, final IWDLMessageType type, final MessageTypeCategory category) {
            this.name = name;
            this.type = type;
            this.category = category;
        }
        
        @Override
        public String toString() {
            return "MessageRegistration [name=" + this.name + ", type=" + this.type + ", category=" + this.category + "]";
        }
        
        @Override
        public int hashCode() {
            final int n = 31 + ((this.name == null) ? 0 : this.name.hashCode());
            final int n2 = 31 + ((this.category == null) ? 0 : this.category.hashCode());
            final int n3 = 31 + ((this.type == null) ? 0 : this.type.hashCode());
            return 1;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (!(o instanceof MessageRegistration)) {
                return false;
            }
            final MessageRegistration messageRegistration = (MessageRegistration)o;
            if (this.name == null) {
                if (messageRegistration.name != null) {
                    return false;
                }
            }
            else if (!this.name.equals(messageRegistration.name)) {
                return false;
            }
            if (this.category == null) {
                if (messageRegistration.category != null) {
                    return false;
                }
            }
            else if (!this.category.equals(messageRegistration.category)) {
                return false;
            }
            if (this.type == null) {
                if (messageRegistration.type != null) {
                    return false;
                }
            }
            else if (!this.type.equals(messageRegistration.type)) {
                return false;
            }
            return true;
        }
    }
}
