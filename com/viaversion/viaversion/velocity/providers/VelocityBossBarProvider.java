package com.viaversion.viaversion.velocity.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import com.viaversion.viaversion.velocity.storage.*;

public class VelocityBossBarProvider extends BossBarProvider
{
    @Override
    public void handleAdd(final UserConnection userConnection, final UUID uuid) {
        if (userConnection.has(VelocityStorage.class)) {
            final VelocityStorage velocityStorage = (VelocityStorage)userConnection.get(VelocityStorage.class);
            if (velocityStorage.getBossbar() != null) {
                velocityStorage.getBossbar().add(uuid);
            }
        }
    }
    
    @Override
    public void handleRemove(final UserConnection userConnection, final UUID uuid) {
        if (userConnection.has(VelocityStorage.class)) {
            final VelocityStorage velocityStorage = (VelocityStorage)userConnection.get(VelocityStorage.class);
            if (velocityStorage.getBossbar() != null) {
                velocityStorage.getBossbar().remove(uuid);
            }
        }
    }
}
