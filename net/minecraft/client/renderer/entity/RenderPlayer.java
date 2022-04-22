package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import Mood.Cosmetics.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import Mood.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;

public class RenderPlayer extends RendererLivingEntity
{
    private boolean field_177140_a;
    private static final String __OBFID;
    
    public RenderPlayer(final RenderManager renderManager) {
        this(renderManager, false);
    }
    
    public RenderPlayer(final RenderManager renderManager, final boolean field_177140_a) {
        super(renderManager, new ModelPlayer(0.0f, field_177140_a), 0.5f);
        this.field_177140_a = field_177140_a;
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerDeadmau5Head(this));
        this.addLayer(new LayerCape(this));
        this.addLayer(new LayerCustomHead(this.func_177136_g().bipedHead));
        this.addLayer(new CosmeticWings(this));
        this.addLayer(new ILikeCommandBlocks(this));
        this.addLayer(new CosmeticSusanoo(this));
    }
    
    public ModelPlayer func_177136_g() {
        return (ModelPlayer)super.getMainModel();
    }
    
    public void func_180596_a(final AbstractClientPlayer abstractClientPlayer, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (!abstractClientPlayer.func_175144_cb() || this.renderManager.livingPlayer == abstractClientPlayer) {
            double n6 = n2;
            if (abstractClientPlayer.isSneaking() && !(abstractClientPlayer instanceof EntityPlayerSP)) {
                n6 = n2 - 0.125;
            }
            this.func_177137_d(abstractClientPlayer);
            super.doRender(abstractClientPlayer, n, n6, n3, n4, n5);
        }
    }
    
    private void func_177137_d(final AbstractClientPlayer abstractClientPlayer) {
        final ModelPlayer func_177136_g = this.func_177136_g();
        if (abstractClientPlayer.func_175149_v()) {
            func_177136_g.func_178719_a(false);
            func_177136_g.bipedHead.showModel = true;
            func_177136_g.bipedHeadwear.showModel = true;
        }
        else {
            final ItemStack currentItem = abstractClientPlayer.inventory.getCurrentItem();
            func_177136_g.func_178719_a(true);
            func_177136_g.bipedHeadwear.showModel = abstractClientPlayer.func_175148_a(EnumPlayerModelParts.HAT);
            func_177136_g.field_178730_v.showModel = abstractClientPlayer.func_175148_a(EnumPlayerModelParts.JACKET);
            func_177136_g.field_178733_c.showModel = abstractClientPlayer.func_175148_a(EnumPlayerModelParts.LEFT_PANTS_LEG);
            func_177136_g.field_178731_d.showModel = abstractClientPlayer.func_175148_a(EnumPlayerModelParts.RIGHT_PANTS_LEG);
            func_177136_g.field_178734_a.showModel = abstractClientPlayer.func_175148_a(EnumPlayerModelParts.LEFT_SLEEVE);
            func_177136_g.field_178732_b.showModel = abstractClientPlayer.func_175148_a(EnumPlayerModelParts.RIGHT_SLEEVE);
            func_177136_g.heldItemLeft = 0;
            func_177136_g.aimedBow = false;
            func_177136_g.isSneak = abstractClientPlayer.isSneaking();
            if (currentItem == null) {
                func_177136_g.heldItemRight = 0;
            }
            else {
                func_177136_g.heldItemRight = 1;
                if (abstractClientPlayer.getItemInUseCount() > 0) {
                    final EnumAction itemUseAction = currentItem.getItemUseAction();
                    if (itemUseAction == EnumAction.BLOCK) {
                        func_177136_g.heldItemRight = 3;
                    }
                    else if (itemUseAction == EnumAction.BOW) {
                        func_177136_g.aimedBow = true;
                    }
                }
            }
        }
    }
    
    protected ResourceLocation func_180594_a(final AbstractClientPlayer abstractClientPlayer) {
        final Client instance = Client.INSTANCE;
        final boolean toggled = Client.getModuleByName("CustomModel").toggled;
        if (!(abstractClientPlayer instanceof EntityPlayerSP)) {
            return abstractClientPlayer.getLocationSkin();
        }
        if (toggled) {
            return new ResourceLocation("MooDTool/Jeff.png");
        }
        return abstractClientPlayer.getLocationSkin();
    }
    
    @Override
    public void func_82422_c() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    protected void preRenderCallback(final AbstractClientPlayer abstractClientPlayer, final float n) {
        final float n2 = 0.9375f;
        GlStateManager.scale(n2, n2, n2);
        final Client instance = Client.INSTANCE;
        if (Client.getModuleByName("IamFuckingSanic").toggled) {
            final String name = abstractClientPlayer.getName();
            Minecraft.getMinecraft();
            if (name.equals(Minecraft.getSession().getUsername())) {
                final float n3 = 0.0f;
                GlStateManager.scale(n3, n3, n3);
            }
        }
    }
    
    protected void renderOffsetLivingLabel(final AbstractClientPlayer abstractClientPlayer, final double n, double n2, final double n3, final String s, final float n4, final double n5) {
        if (n5 < 100.0) {
            final Scoreboard worldScoreboard = abstractClientPlayer.getWorldScoreboard();
            final ScoreObjective objectiveInDisplaySlot = worldScoreboard.getObjectiveInDisplaySlot(2);
            if (objectiveInDisplaySlot != null) {
                this.renderLivingLabel(abstractClientPlayer, String.valueOf(worldScoreboard.getValueFromObjective(abstractClientPlayer.getName(), objectiveInDisplaySlot).getScorePoints()) + " " + objectiveInDisplaySlot.getDisplayName(), n, n2, n3, 64);
                n2 += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15f * n4;
            }
        }
        super.func_177069_a(abstractClientPlayer, n, n2, n3, s, n4, n5);
    }
    
    public void func_177138_b(final AbstractClientPlayer abstractClientPlayer) {
        final float n = 1.0f;
        GlStateManager.color(n, n, n);
        final ModelPlayer func_177136_g = this.func_177136_g();
        this.func_177137_d(abstractClientPlayer);
        func_177136_g.swingProgress = 0.0f;
        func_177136_g.isSneak = false;
        func_177136_g.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, abstractClientPlayer);
        func_177136_g.func_178725_a();
    }
    
    public void func_177139_c(final AbstractClientPlayer abstractClientPlayer) {
        final float n = 1.0f;
        GlStateManager.color(n, n, n);
        final ModelPlayer func_177136_g = this.func_177136_g();
        this.func_177137_d(abstractClientPlayer);
        func_177136_g.isSneak = false;
        func_177136_g.setRotationAngles(func_177136_g.swingProgress = 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, abstractClientPlayer);
        func_177136_g.func_178726_b();
    }
    
    protected void renderLivingAt(final AbstractClientPlayer abstractClientPlayer, final double n, final double n2, final double n3) {
        if (abstractClientPlayer.isEntityAlive() && abstractClientPlayer.isPlayerSleeping()) {
            super.renderLivingAt(abstractClientPlayer, n + abstractClientPlayer.field_71079_bU, n2 + abstractClientPlayer.field_71082_cx, n3 + abstractClientPlayer.field_71089_bV);
        }
        else {
            super.renderLivingAt(abstractClientPlayer, n, n2, n3);
        }
    }
    
    protected void func_180595_a(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3) {
        if (abstractClientPlayer.isEntityAlive() && abstractClientPlayer.isPlayerSleeping()) {
            GlStateManager.rotate(abstractClientPlayer.getBedOrientationInDegrees(), 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(this.getDeathMaxRotation(abstractClientPlayer), 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(270.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            super.rotateCorpse(abstractClientPlayer, n, n2, n3);
        }
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((AbstractClientPlayer)entityLivingBase, n);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.func_180595_a((AbstractClientPlayer)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected void renderLivingAt(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        this.renderLivingAt((AbstractClientPlayer)entityLivingBase, n, n2, n3);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180596_a((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.func_177136_g();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180594_a((AbstractClientPlayer)entity);
    }
    
    @Override
    protected void func_177069_a(final Entity entity, final double n, final double n2, final double n3, final String s, final float n4, final double n5) {
        this.renderOffsetLivingLabel((AbstractClientPlayer)entity, n, n2, n3, s, n4, n5);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180596_a((AbstractClientPlayer)entity, n, n2, n3, n4, n5);
    }
    
    static {
        __OBFID = "CL_00001020";
    }
}
