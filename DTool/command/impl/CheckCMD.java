package DTool.command.impl;

import DTool.command.*;
import io.netty.buffer.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class CheckCMD extends Command
{
    public CheckCMD() {
        super("CheckCMD", "CheckCMD", "CheckCMD", new String[] { "CheckCMD" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        CheckCMD.mc.isSingleplayer();
        if (array.length == 0) {
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeString("Csekkol\u00e1s");
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", packetBuffer));
        }
    }
}
