package wdl;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.client.network.*;
import com.mojang.authlib.*;
import java.lang.reflect.*;

public class CapeHandler
{
    private static final Logger logger;
    private static final Map capes;
    private static final Set handledPlayers;
    private static final Map playerFailures;
    
    static {
        logger = LogManager.getLogger();
        capes = new HashMap();
        handledPlayers = new HashSet();
        playerFailures = new HashMap();
        CapeHandler.capes.put(UUID.fromString("6c8976e3-99a9-4d8b-a98e-d4c0c09b305b"), new ResourceLocation("wdl", "textures/cape_dev.png"));
        CapeHandler.capes.put(UUID.fromString("f6c068f1-0738-4b41-bdb2-69d81d2b0f1c"), new ResourceLocation("wdl", "textures/cape_dev.png"));
    }
    
    public static void onWorldTick(final List list) {
        if (0 > 40) {
            return;
        }
        CapeHandler.handledPlayers.retainAll(list);
        for (final EntityPlayer entityPlayer : list) {
            if (CapeHandler.handledPlayers.contains(entityPlayer)) {
                continue;
            }
            if (!(entityPlayer instanceof AbstractClientPlayer)) {
                continue;
            }
            setupPlayer((AbstractClientPlayer)entityPlayer);
        }
    }
    
    private static void setupPlayer(final AbstractClientPlayer abstractClientPlayer) {
        final NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)ReflectionUtils.stealAndGetField(abstractClientPlayer, AbstractClientPlayer.class, NetworkPlayerInfo.class);
        if (networkPlayerInfo == null) {
            incrementFailure(abstractClientPlayer);
            return;
        }
        final GameProfile func_178845_a = networkPlayerInfo.func_178845_a();
        if (CapeHandler.capes.containsKey(func_178845_a.getId())) {
            setPlayerCape(networkPlayerInfo, (ResourceLocation)CapeHandler.capes.get(func_178845_a.getId()));
        }
        CapeHandler.handledPlayers.add(abstractClientPlayer);
    }
    
    private static void setPlayerCape(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation resourceLocation) throws Exception {
        Field field = null;
        Field[] declaredFields;
        while (0 < (declaredFields = networkPlayerInfo.getClass().getDeclaredFields()).length) {
            final Field field2 = declaredFields[0];
            if (field2.getType().equals(ResourceLocation.class) && true) {
                field = field2;
            }
            int n = 0;
            ++n;
        }
        if (field != null) {
            field.setAccessible(true);
            field.set(networkPlayerInfo, resourceLocation);
        }
    }
    
    private static void incrementFailure(final EntityPlayer entityPlayer) {
        if (CapeHandler.playerFailures.containsKey(entityPlayer)) {
            final int n = CapeHandler.playerFailures.get(entityPlayer) + 1;
            CapeHandler.playerFailures.put(entityPlayer, n);
            if (n > 40) {
                CapeHandler.handledPlayers.add(entityPlayer);
                CapeHandler.playerFailures.remove(entityPlayer);
                CapeHandler.logger.warn("[WDL] Failed to set up cape for " + entityPlayer + " too many times (" + n + "); skipping them");
            }
        }
        else {
            CapeHandler.playerFailures.put(entityPlayer, 1);
        }
    }
}
