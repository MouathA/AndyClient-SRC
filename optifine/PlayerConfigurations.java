package optifine;

import net.minecraft.client.model.*;
import net.minecraft.client.entity.*;
import java.util.*;

public class PlayerConfigurations
{
    private static Map mapConfigurations;
    
    static {
        PlayerConfigurations.mapConfigurations = null;
    }
    
    public static void renderPlayerItems(final ModelBiped modelBiped, final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        final PlayerConfiguration playerConfiguration = getPlayerConfiguration(abstractClientPlayer);
        if (playerConfiguration != null) {
            playerConfiguration.renderPlayerItems(modelBiped, abstractClientPlayer, n, n2);
        }
    }
    
    public static synchronized PlayerConfiguration getPlayerConfiguration(final AbstractClientPlayer abstractClientPlayer) {
        final String nameClear = abstractClientPlayer.getNameClear();
        if (nameClear == null) {
            return null;
        }
        PlayerConfiguration playerConfiguration = getMapConfigurations().get(nameClear);
        if (playerConfiguration == null) {
            playerConfiguration = new PlayerConfiguration();
            getMapConfigurations().put(nameClear, playerConfiguration);
            new FileDownloadThread("http://s.optifine.net/users/" + nameClear + ".cfg", new PlayerConfigurationReceiver(nameClear)).start();
        }
        return playerConfiguration;
    }
    
    public static synchronized void setPlayerConfiguration(final String s, final PlayerConfiguration playerConfiguration) {
        getMapConfigurations().put(s, playerConfiguration);
    }
    
    private static Map getMapConfigurations() {
        if (PlayerConfigurations.mapConfigurations == null) {
            PlayerConfigurations.mapConfigurations = new HashMap();
        }
        return PlayerConfigurations.mapConfigurations;
    }
}
