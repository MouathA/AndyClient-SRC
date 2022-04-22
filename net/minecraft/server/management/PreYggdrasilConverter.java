package net.minecraft.server.management;

import java.io.*;
import org.apache.logging.log4j.*;
import net.minecraft.server.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import java.util.*;

public class PreYggdrasilConverter
{
    private static final Logger LOGGER;
    public static final File OLD_IPBAN_FILE;
    public static final File OLD_PLAYERBAN_FILE;
    public static final File OLD_OPS_FILE;
    public static final File OLD_WHITELIST_FILE;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001882";
        LOGGER = LogManager.getLogger();
        OLD_IPBAN_FILE = new File("banned-ips.txt");
        OLD_PLAYERBAN_FILE = new File("banned-players.txt");
        OLD_OPS_FILE = new File("ops.txt");
        OLD_WHITELIST_FILE = new File("white-list.txt");
    }
    
    private static void lookupNames(final MinecraftServer minecraftServer, final Collection collection, final ProfileLookupCallback profileLookupCallback) {
        final String[] array = (String[])Iterators.toArray(Iterators.filter(collection.iterator(), new Predicate() {
            private static final String __OBFID;
            
            public boolean func_152733_a(final String s) {
                return !StringUtils.isNullOrEmpty(s);
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_152733_a((String)o);
            }
            
            static {
                __OBFID = "CL_00001881";
            }
        }), String.class);
        if (minecraftServer.isServerInOnlineMode()) {
            minecraftServer.getGameProfileRepository().findProfilesByNames(array, Agent.MINECRAFT, profileLookupCallback);
        }
        else {
            final String[] array2 = array;
            while (0 < array.length) {
                final String s = array2[0];
                profileLookupCallback.onProfileLookupSucceeded(new GameProfile(EntityPlayer.getUUID(new GameProfile(null, s)), s));
                int n = 0;
                ++n;
            }
        }
    }
    
    public static String func_152719_a(final String s) {
        if (StringUtils.isNullOrEmpty(s) || s.length() > 16) {
            return s;
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(s);
        if (gameProfileForUsername != null && gameProfileForUsername.getId() != null) {
            return gameProfileForUsername.getId().toString();
        }
        if (!server.isSinglePlayer() && server.isServerInOnlineMode()) {
            final ArrayList arrayList = Lists.newArrayList();
            lookupNames(server, Lists.newArrayList(s), new ProfileLookupCallback(arrayList) {
                private static final String __OBFID;
                private final MinecraftServer val$var1;
                private final ArrayList val$var3;
                
                @Override
                public void onProfileLookupSucceeded(final GameProfile gameProfile) {
                    this.val$var1.getPlayerProfileCache().func_152649_a(gameProfile);
                    this.val$var3.add(gameProfile);
                }
                
                @Override
                public void onProfileLookupFailed(final GameProfile gameProfile, final Exception ex) {
                    PreYggdrasilConverter.access$0().warn("Could not lookup user whitelist entry for " + gameProfile.getName(), ex);
                }
                
                static {
                    __OBFID = "CL_00001880";
                }
            });
            return (arrayList.size() > 0 && arrayList.get(0).getId() != null) ? arrayList.get(0).getId().toString() : "";
        }
        return EntityPlayer.getUUID(new GameProfile(null, s)).toString();
    }
    
    static Logger access$0() {
        return PreYggdrasilConverter.LOGGER;
    }
}
