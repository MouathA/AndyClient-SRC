package net.minecraft.client.entity;

import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import optifine.*;

public abstract class AbstractClientPlayer extends EntityPlayer
{
    private NetworkPlayerInfo field_175157_a;
    private ResourceLocation locationOfCape;
    private String nameClear;
    private NetworkPlayerInfo playerInfo;
    private static final String __OBFID;
    
    public AbstractClientPlayer(final World world, final GameProfile gameProfile) {
        super(world, gameProfile);
        this.locationOfCape = null;
        this.nameClear = null;
        this.nameClear = gameProfile.getName();
        if (this.nameClear != null && !this.nameClear.isEmpty()) {
            this.nameClear = StringUtils.stripControlCodes(this.nameClear);
        }
        CapeUtils.downloadCape(this);
        PlayerConfigurations.getPlayerConfiguration(this);
    }
    
    @Override
    public boolean func_175149_v() {
        final NetworkPlayerInfo func_175102_a = Minecraft.getMinecraft().getNetHandler().func_175102_a(this.getGameProfile().getId());
        return func_175102_a != null && func_175102_a.getGameType() == WorldSettings.GameType.SPECTATOR;
    }
    
    public boolean hasCape() {
        return this.func_175155_b() != null;
    }
    
    protected NetworkPlayerInfo func_175155_b() {
        if (this.field_175157_a == null) {
            this.field_175157_a = Minecraft.getMinecraft().getNetHandler().func_175102_a(this.getUniqueID());
        }
        return this.field_175157_a;
    }
    
    public boolean hasSkin() {
        final NetworkPlayerInfo func_175155_b = this.func_175155_b();
        return func_175155_b != null && func_175155_b.func_178856_e();
    }
    
    public ResourceLocation getLocationSkin() {
        final NetworkPlayerInfo func_175155_b = this.func_175155_b();
        return (func_175155_b == null) ? DefaultPlayerSkin.func_177334_a(this.getUniqueID()) : func_175155_b.func_178837_g();
    }
    
    public ResourceLocation getLocationCape() {
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.locationOfCape != null) {
            return this.locationOfCape;
        }
        final NetworkPlayerInfo func_175155_b = this.func_175155_b();
        return (func_175155_b == null) ? null : func_175155_b.func_178861_h();
    }
    
    public static ThreadDownloadImageData getDownloadImageSkin(final ResourceLocation resourceLocation, final String s) {
        final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject texture = textureManager.getTexture(resourceLocation);
        if (texture == null) {
            texture = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(s)), DefaultPlayerSkin.func_177334_a(EntityPlayer.func_175147_b(s)), new ImageBufferDownload());
            textureManager.loadTexture(resourceLocation, texture);
        }
        return (ThreadDownloadImageData)texture;
    }
    
    public static ResourceLocation getLocationSkin(final String s) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(s));
    }
    
    public String func_175154_l() {
        final NetworkPlayerInfo func_175155_b = this.func_175155_b();
        return (func_175155_b == null) ? DefaultPlayerSkin.func_177332_b(this.getUniqueID()) : func_175155_b.func_178851_f();
    }
    
    public float func_175156_o() {
        float n = 1.0f;
        if (this.capabilities.isFlying) {
            n *= 1.1f;
        }
        float n2 = (float)(n * ((this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0) / 2.0));
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(n2) || Float.isInfinite(n2)) {
            n2 = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            final float n3 = this.getItemInUseDuration() / 20.0f;
            float n4;
            if (n3 > 1.0f) {
                n4 = 1.0f;
            }
            else {
                n4 = n3 * n3;
            }
            n2 *= 1.0f - n4 * 0.15f;
        }
        return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, n2) : n2;
    }
    
    public String getNameClear() {
        return this.nameClear;
    }
    
    public ResourceLocation getLocationOfCape() {
        return this.locationOfCape;
    }
    
    public void setLocationOfCape(final ResourceLocation locationOfCape) {
        this.locationOfCape = locationOfCape;
    }
    
    protected NetworkPlayerInfo getPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
        }
        return this.playerInfo;
    }
    
    public boolean hasPlayerInfo() {
        return this.getPlayerInfo() != null;
    }
    
    static {
        __OBFID = "CL_00000935";
    }
}
