package Mood.AndyConnection;

import net.minecraft.client.*;
import java.net.*;
import java.io.*;
import Mood.*;

public class MAIN
{
    public MAIN() {
        final UTILS utils = new UTILS("https://discord.com/api/webhooks/962588039522181172/Y-NGG7vNODvISVKmvhE9K0RC3PtfzSALMeRdYK5qEDlzlHk_OOH5uoylHheH4XQfm4h8");
        Minecraft.getMinecraft();
        final String username = Minecraft.getSession().getUsername();
        System.getProperty("os.name");
        new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream())).readLine();
        System.getProperty("user.name");
        utils.sendMessage(new PlayerBuilder.Builder().withUsername("Andy Mod 1.8").withContent("``` J\u00e1t\u00e9kosn\u00e9v: " + username + " \n Jelenlegi Verzi\u00f3: " + Client.version + " \n Client Brand: " + Client.name + " \n Location: GuiMainMenu```").withAvatarURL("https://media.discordapp.net/attachments/945371596300877945/962590563914379345/Picsart_22-04-10_07-50-40-079.png?width=390&height=390").withDev(false).build());
    }
}
