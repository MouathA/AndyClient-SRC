package optifine;

import com.google.gson.*;

public class PlayerConfigurationReceiver implements IFileDownloadListener
{
    private String player;
    
    public PlayerConfigurationReceiver(final String player) {
        this.player = null;
        this.player = player;
    }
    
    @Override
    public void fileDownloadFinished(final String s, final byte[] array, final Throwable t) {
        if (array != null) {
            final PlayerConfiguration playerConfiguration = new PlayerConfigurationParser(this.player).parsePlayerConfiguration(new JsonParser().parse(new String(array, "ASCII")));
            if (playerConfiguration != null) {
                playerConfiguration.setInitialized(true);
                PlayerConfigurations.setPlayerConfiguration(this.player, playerConfiguration);
            }
        }
    }
}
