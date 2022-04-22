package optifine;

import net.minecraft.util.*;
import java.awt.image.*;
import net.minecraft.client.*;
import javax.imageio.*;
import java.io.*;
import com.google.gson.*;

public class PlayerConfigurationParser
{
    private String player;
    public static final String CONFIG_ITEMS;
    public static final String ITEM_TYPE;
    public static final String ITEM_ACTIVE;
    
    public PlayerConfigurationParser(final String player) {
        this.player = null;
        this.player = player;
    }
    
    public PlayerConfiguration parsePlayerConfiguration(final JsonElement jsonElement) {
        if (jsonElement == null) {
            throw new JsonParseException("JSON object is null, player: " + this.player);
        }
        final JsonObject jsonObject = (JsonObject)jsonElement;
        final PlayerConfiguration playerConfiguration = new PlayerConfiguration();
        final JsonArray jsonArray = (JsonArray)jsonObject.get("items");
        if (jsonArray != null) {
            while (0 < jsonArray.size()) {
                final JsonObject jsonObject2 = (JsonObject)jsonArray.get(0);
                Label_0286: {
                    if (Json.getBoolean(jsonObject2, "active", true)) {
                        final String string = Json.getString(jsonObject2, "type");
                        if (string == null) {
                            Config.warn("Item type is null, player: " + this.player);
                        }
                        else {
                            String s = Json.getString(jsonObject2, "model");
                            if (s == null) {
                                s = "items/" + string + "/model.cfg";
                            }
                            final PlayerItemModel downloadModel = this.downloadModel(s);
                            if (downloadModel != null) {
                                if (!downloadModel.isUsePlayerTexture()) {
                                    String s2 = Json.getString(jsonObject2, "texture");
                                    if (s2 == null) {
                                        s2 = "items/" + string + "/users/" + this.player + ".png";
                                    }
                                    final BufferedImage downloadTextureImage = this.downloadTextureImage(s2);
                                    if (downloadTextureImage == null) {
                                        break Label_0286;
                                    }
                                    downloadModel.setTextureImage(downloadTextureImage);
                                    downloadModel.setTextureLocation(new ResourceLocation("optifine.net", s2));
                                }
                                playerConfiguration.addPlayerItemModel(downloadModel);
                            }
                        }
                    }
                }
                int n = 0;
                ++n;
            }
        }
        return playerConfiguration;
    }
    
    private BufferedImage downloadTextureImage(final String s) {
        return ImageIO.read(new ByteArrayInputStream(HttpPipeline.get("http://s.optifine.net/" + s, Minecraft.getMinecraft().getProxy())));
    }
    
    private PlayerItemModel downloadModel(final String s) {
        final JsonObject jsonObject = (JsonObject)new JsonParser().parse(new String(HttpPipeline.get("http://s.optifine.net/" + s, Minecraft.getMinecraft().getProxy()), "ASCII"));
        final PlayerItemParser playerItemParser = new PlayerItemParser();
        return PlayerItemParser.parseItemModel(jsonObject);
    }
    
    static {
        ITEM_TYPE = "type";
        ITEM_ACTIVE = "active";
        CONFIG_ITEMS = "items";
    }
}
