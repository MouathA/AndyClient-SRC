package us.myles.ViaVersion.api.boss;

import java.util.*;

@Deprecated
public class BossBar
{
    private final com.viaversion.viaversion.api.legacy.bossbar.BossBar bossBar;
    
    public BossBar(final com.viaversion.viaversion.api.legacy.bossbar.BossBar bossBar) {
        this.bossBar = bossBar;
    }
    
    public String getTitle() {
        return this.bossBar.getTitle();
    }
    
    public BossBar setTitle(final String title) {
        this.bossBar.setTitle(title);
        return this;
    }
    
    public float getHealth() {
        return this.bossBar.getHealth();
    }
    
    public BossBar setHealth(final float health) {
        this.bossBar.setHealth(health);
        return this;
    }
    
    public BossColor getColor() {
        return BossColor.values()[this.bossBar.getColor().ordinal()];
    }
    
    public BossBar setColor(final BossColor bossColor) {
        this.bossBar.setColor(com.viaversion.viaversion.api.legacy.bossbar.BossColor.values()[bossColor.ordinal()]);
        return this;
    }
    
    public BossStyle getStyle() {
        return BossStyle.values()[this.bossBar.getStyle().ordinal()];
    }
    
    public BossBar setStyle(final BossStyle bossStyle) {
        this.bossBar.setStyle(com.viaversion.viaversion.api.legacy.bossbar.BossStyle.values()[bossStyle.ordinal()]);
        return this;
    }
    
    @Deprecated
    public BossBar addPlayer(final Object o) {
        return this;
    }
    
    public BossBar addPlayer(final UUID uuid) {
        this.bossBar.addPlayer(uuid);
        return this;
    }
    
    @Deprecated
    public BossBar addPlayers(final Object... array) {
        return this;
    }
    
    @Deprecated
    public BossBar removePlayer(final Object o) {
        return this;
    }
    
    public BossBar removePlayer(final UUID uuid) {
        this.bossBar.removePlayer(uuid);
        return this;
    }
    
    public BossBar addFlag(final BossFlag bossFlag) {
        this.bossBar.addFlag(com.viaversion.viaversion.api.legacy.bossbar.BossFlag.values()[bossFlag.ordinal()]);
        return this;
    }
    
    public BossBar removeFlag(final BossFlag bossFlag) {
        this.bossBar.removeFlag(com.viaversion.viaversion.api.legacy.bossbar.BossFlag.values()[bossFlag.ordinal()]);
        return this;
    }
    
    public boolean hasFlag(final BossFlag bossFlag) {
        return this.bossBar.hasFlag(com.viaversion.viaversion.api.legacy.bossbar.BossFlag.values()[bossFlag.ordinal()]);
    }
    
    public Set getPlayers() {
        return this.bossBar.getPlayers();
    }
    
    public BossBar show() {
        this.bossBar.show();
        return this;
    }
    
    public BossBar hide() {
        this.bossBar.hide();
        return this;
    }
    
    public boolean isVisible() {
        return this.bossBar.isVisible();
    }
    
    public UUID getId() {
        return this.bossBar.getId();
    }
}
