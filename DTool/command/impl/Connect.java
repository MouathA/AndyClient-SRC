package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;
import Mood.*;

public class Connect extends Command
{
    public Connect() {
        super("Connect", "Connect", "Connect", new String[] { "Connect" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 1) {
            final ServerData serverData = new ServerData("", array[0], true);
            if (!Connect.mc.isSingleplayer()) {
                final Minecraft mc = Connect.mc;
                Minecraft.theWorld.sendQuittingDisconnectingPacket();
            }
            Connect.mc.displayGuiScreen(new GuiConnecting(null, Connect.mc, serverData));
        }
        else {
            Segito.msg("Haszn\u00e1lat:§b -connect §8(§6Szerver IP-c\u00edme§8)");
        }
    }
}
