package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;

public class ClearChat extends Command
{
    public ClearChat() {
        super("cc", "cc", "cc", new String[] { "cc" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        Minecraft.ingameGUI.getChatGUI().clearChatMessages();
    }
}
