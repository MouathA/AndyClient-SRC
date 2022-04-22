package net.minecraft.util;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.command.*;

public class ChatComponentProcessor
{
    private static final String __OBFID;
    
    public static IChatComponent func_179985_a(final ICommandSender commandSender, final IChatComponent chatComponent, final Entity entity) throws CommandException {
        IChatComponent func_150869_b;
        if (chatComponent instanceof ChatComponentScore) {
            final ChatComponentScore chatComponentScore = (ChatComponentScore)chatComponent;
            String s = chatComponentScore.func_179995_g();
            if (PlayerSelector.hasArguments(s)) {
                final List func_179656_b = PlayerSelector.func_179656_b(commandSender, s, Entity.class);
                if (func_179656_b.size() != 1) {
                    throw new EntityNotFoundException();
                }
                s = func_179656_b.get(0).getName();
            }
            func_150869_b = ((entity != null && s.equals("*")) ? new ChatComponentScore(entity.getName(), chatComponentScore.func_179994_h()) : new ChatComponentScore(s, chatComponentScore.func_179994_h()));
            ((ChatComponentScore)func_150869_b).func_179997_b(chatComponentScore.getUnformattedTextForChat());
        }
        else if (chatComponent instanceof ChatComponentSelector) {
            func_150869_b = PlayerSelector.func_150869_b(commandSender, ((ChatComponentSelector)chatComponent).func_179992_g());
            if (func_150869_b == null) {
                func_150869_b = new ChatComponentText("");
            }
        }
        else if (chatComponent instanceof ChatComponentText) {
            func_150869_b = new ChatComponentText(((ChatComponentText)chatComponent).getChatComponentText_TextValue());
        }
        else {
            if (!(chatComponent instanceof ChatComponentTranslation)) {
                return chatComponent;
            }
            final Object[] formatArgs = ((ChatComponentTranslation)chatComponent).getFormatArgs();
            while (0 < formatArgs.length) {
                final Object o = formatArgs[0];
                if (o instanceof IChatComponent) {
                    formatArgs[0] = func_179985_a(commandSender, (IChatComponent)o, entity);
                }
                int n = 0;
                ++n;
            }
            func_150869_b = new ChatComponentTranslation(((ChatComponentTranslation)chatComponent).getKey(), formatArgs);
        }
        final ChatStyle chatStyle = chatComponent.getChatStyle();
        if (chatStyle != null) {
            ((ChatComponentScore)func_150869_b).setChatStyle(chatStyle.createShallowCopy());
        }
        final Iterator<IChatComponent> iterator = (Iterator<IChatComponent>)chatComponent.getSiblings().iterator();
        while (iterator.hasNext()) {
            ((ChatComponentScore)func_150869_b).appendSibling(func_179985_a(commandSender, iterator.next(), entity));
        }
        return func_150869_b;
    }
    
    static {
        __OBFID = "CL_00002310";
    }
}
