package com.viaversion.viaversion.legacy;

import com.viaversion.viaversion.api.legacy.*;
import com.viaversion.viaversion.api.legacy.bossbar.*;
import com.viaversion.viaversion.legacy.bossbar.*;

public final class LegacyAPI implements LegacyViaAPI
{
    @Override
    public BossBar createLegacyBossBar(final String s, final float n, final BossColor bossColor, final BossStyle bossStyle) {
        return new CommonBoss(s, n, bossColor, bossStyle);
    }
}
