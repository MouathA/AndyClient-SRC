package com.viaversion.viaversion.api.legacy;

import com.viaversion.viaversion.api.legacy.bossbar.*;

public interface LegacyViaAPI
{
    BossBar createLegacyBossBar(final String p0, final float p1, final BossColor p2, final BossStyle p3);
    
    default BossBar createLegacyBossBar(final String s, final BossColor bossColor, final BossStyle bossStyle) {
        return this.createLegacyBossBar(s, 1.0f, bossColor, bossStyle);
    }
}
