package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.bossbar.*;
import java.util.function.*;

public class BossBarStorage extends StoredObject
{
    private Map bossBars;
    
    public BossBarStorage(final UserConnection userConnection) {
        super(userConnection);
        this.bossBars = new HashMap();
    }
    
    public void add(final UUID uuid, final String s, final float n) {
        final WitherBossBar witherBossBar = new WitherBossBar(this.getUser(), uuid, s, n);
        final PlayerPosition playerPosition = (PlayerPosition)this.getUser().get(PlayerPosition.class);
        witherBossBar.setPlayerLocation(playerPosition.getPosX(), playerPosition.getPosY(), playerPosition.getPosZ(), playerPosition.getYaw(), playerPosition.getPitch());
        witherBossBar.show();
        this.bossBars.put(uuid, witherBossBar);
    }
    
    public void remove(final UUID uuid) {
        final WitherBossBar witherBossBar = this.bossBars.remove(uuid);
        if (witherBossBar == null) {
            return;
        }
        witherBossBar.hide();
    }
    
    public void updateLocation() {
        this.bossBars.values().forEach(BossBarStorage::lambda$updateLocation$0);
    }
    
    public void changeWorld() {
        this.bossBars.values().forEach(BossBarStorage::lambda$changeWorld$1);
    }
    
    public void updateHealth(final UUID uuid, final float health) {
        final WitherBossBar witherBossBar = this.bossBars.get(uuid);
        if (witherBossBar == null) {
            return;
        }
        witherBossBar.setHealth(health);
    }
    
    public void updateTitle(final UUID uuid, final String title) {
        final WitherBossBar witherBossBar = this.bossBars.get(uuid);
        if (witherBossBar == null) {
            return;
        }
        witherBossBar.setTitle(title);
    }
    
    private static void lambda$changeWorld$1(final WitherBossBar witherBossBar) {
        witherBossBar.hide();
        witherBossBar.show();
    }
    
    private static void lambda$updateLocation$0(final PlayerPosition playerPosition, final WitherBossBar witherBossBar) {
        witherBossBar.setPlayerLocation(playerPosition.getPosX(), playerPosition.getPosY(), playerPosition.getPosZ(), playerPosition.getYaw(), playerPosition.getPitch());
    }
}
