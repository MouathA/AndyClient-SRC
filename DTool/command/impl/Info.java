package DTool.command.impl;

import DTool.command.*;
import Mood.*;

public class Info extends Command
{
    public Info() {
        super("Info", "Le\u00edrja aamit tudni kell a kliens-r\u0151l XD", "info", new String[] { "i" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        Client.addChatMessage(" [ DTool Info ] ");
        Client.addChatMessage(String.valueOf(Client.name) + Client.version + " by Dennyel.");
        Client.addChatMessage("Az\u00e9rt k\u00e9sz\u00edtettem a klient, mert mi\u00e9rt nem.");
    }
}
