package Mood.AndyConnection.integracio.webhookspam;

import net.minecraft.client.*;
import Mood.*;
import java.net.*;
import java.io.*;
import Mood.AndyConnection.*;

public class DiscordWebhookSpammer
{
    Minecraft mc;
    public static String l;
    public static String k;
    
    static {
        DiscordWebhookSpammer.l = "";
        DiscordWebhookSpammer.k = "";
    }
    
    public static void setL(final String l) {
        DiscordWebhookSpammer.l = l;
    }
    
    public static void setK(final String k) {
        DiscordWebhookSpammer.k = k;
    }
    
    public DiscordWebhookSpammer() {
        final String string = new StringBuilder().append(HackedItemUtils.getRandomName()).toString();
        final String string2 = new StringBuilder().append(HackedItemUtils.getRandomProfilPicture()).toString();
        final String l = getL();
        final String k = getK();
        final UTILS utils = new UTILS(l);
        Minecraft.getMinecraft();
        Minecraft.getSession().getUsername();
        System.getProperty("os.name");
        new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream())).readLine();
        System.getProperty("user.name");
        utils.sendMessage(new PlayerBuilder.Builder().withUsername(string).withContent(new StringBuilder().append(k).toString()).withAvatarURL(string2).withDev(false).build());
    }
    
    public static String getL() {
        return DiscordWebhookSpammer.l;
    }
    
    public static String getK() {
        return DiscordWebhookSpammer.k;
    }
}
