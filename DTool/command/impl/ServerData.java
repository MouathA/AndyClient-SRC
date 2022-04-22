package DTool.command.impl;

import DTool.command.*;
import java.util.concurrent.*;
import net.minecraft.client.*;
import java.net.*;
import java.io.*;
import Mood.*;

public class ServerData extends Command
{
    public ServerData() {
        super("ServerData", "ServerData", "ServerData", new String[] { "ServerData" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        CompletableFuture.runAsync(ServerData::lambda$0);
    }
    
    private static void lambda$0() {
        final String line = new BufferedReader(new InputStreamReader(new URL("http://ip-api.com/json/" + Minecraft.getMinecraft().getCurrentServerData().serverIP + "?fields=status,message,continent,continentCode,country,countryCode,region,regionName,city,district,zgeodata,lat,lon,timezone,currency,isp,org,as,asname,reverse,mobile,proxy,query").openStream())).readLine();
        Segito.msg(new StringBuilder().append(line.split(",")[0]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[1]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[2]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[3]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[4]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[5]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[6]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[7]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[8]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[9]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[10]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[11]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[12]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[13]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[14]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[15]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[16]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[17]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[18]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[19]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[20]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[21]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[22]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[23]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[24]).toString());
        Segito.msg(new StringBuilder().append(line.split(",")[25]).toString());
    }
}
