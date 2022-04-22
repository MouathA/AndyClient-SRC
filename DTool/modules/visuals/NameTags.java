package DTool.modules.visuals;

import DTool.modules.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.entity.*;
import DTool.util.value.*;
import org.lwjgl.opengl.*;
import DTool.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;

public class NameTags extends Module
{
    public static boolean formatting;
    public static boolean armour;
    
    static {
        NameTags.formatting = true;
        NameTags.armour = true;
    }
    
    public NameTags() {
        super("NameTags", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onRender() {
        if (!this.isEnable()) {
            return;
        }
        for (final EntityPlayer entityPlayer : Minecraft.theWorld.playerEntities) {
            if (entityPlayer != NameTags.mc.func_175606_aa() && entityPlayer.isEntityAlive()) {
                NameTags.mc.getRenderManager();
                final double n = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * NameTags.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                NameTags.mc.getRenderManager();
                final double n2 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * NameTags.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                NameTags.mc.getRenderManager();
                this.renderNameTag(entityPlayer, NameTags.formatting ? entityPlayer.getDisplayName().getFormattedText() : entityPlayer.getName(), n, n2, entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * NameTags.mc.timer.renderPartialTicks - RenderManager.renderPosZ);
            }
        }
    }
    
    public void renderNameTag(final EntityPlayer entityPlayer, String stripColor, final double n, double n2, final double n3) {
        final Minecraft mc = NameTags.mc;
        final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
        n2 += (entityPlayer.isSneaking() ? 0.5 : 0.7);
        float n4 = Minecraft.thePlayer.getDistanceToEntity(entityPlayer) / 4.0f;
        if (n4 < 1.6f) {
            n4 = 1.6f;
        }
        if (!NameTags.formatting) {
            stripColor = ChatColor.stripColor(stripColor);
        }
        final double n5 = Math.ceil(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount()) / 2.0;
        String s;
        if (Math.floor(n5) == n5) {
            s = String.valueOf((int)Math.floor(n5));
        }
        else {
            s = String.valueOf(n5);
        }
        if (entityPlayer.getAbsorptionAmount() <= 0.0f || entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() < entityPlayer.getMaxHealth()) {
            switch ((int)Math.floor(n5 * 2.0)) {
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20: {}
                case 8:
                case 9:
                case 10:
                case 12: {}
            }
        }
        NameTags.mc.getRenderManager();
        final float n6 = n4 / 80.0f;
        GL11.glTranslatef((float)n, (float)n2 + 1.4f, (float)n3);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-n6, -n6, n6);
        GLUtil.setGLCap(2896, false);
        GLUtil.setGLCap(2929, false);
        Tessellator.getInstance().getWorldRenderer();
        final Minecraft mc2 = NameTags.mc;
        final int n7 = Minecraft.fontRendererObj.getStringWidth(String.valueOf(String.valueOf(stripColor)) + " " + s) / 2;
        GLUtil.setGLCap(3042, true);
        GL11.glBlendFunc(770, 771);
        final float n8 = (float)(-n7 - 2);
        final Minecraft mc3 = NameTags.mc;
        RenderHelper.drawBorderedRect(n8, (float)(-(Minecraft.fontRendererObj.FONT_HEIGHT + 1)), (float)(n7 + 2), 2.0f, 1.3f, -14496513, -2013265920);
        final FontRenderer fontRenderer = fontRendererObj;
        final String s2 = stripColor;
        final float n9 = (float)(-n7);
        final Minecraft mc4 = NameTags.mc;
        fontRenderer.func_175065_a(s2, n9, (float)(-(Minecraft.fontRendererObj.FONT_HEIGHT - 1)), 16777215, true);
        final FontRenderer fontRenderer2 = fontRendererObj;
        final String s3 = s;
        final int n10 = -n7;
        final Minecraft mc5 = NameTags.mc;
        final float n11 = (float)(n10 + Minecraft.fontRendererObj.getStringWidth(String.valueOf(String.valueOf(stripColor)) + " "));
        final Minecraft mc6 = NameTags.mc;
        fontRenderer2.func_175065_a(s3, n11, (float)(-(Minecraft.fontRendererObj.FONT_HEIGHT - 1)), 5635925, true);
        if (NameTags.armour && Minecraft.thePlayer.getDistanceSqToEntity(entityPlayer) <= 900.0) {
            final ItemStack[] armorInventory;
            int i = (armorInventory = entityPlayer.inventory.armorInventory).length;
            int n12 = 0;
            while (0 < i) {
                if (armorInventory[0] != null) {
                    n12 -= 8;
                }
                final byte b = 1;
            }
            if (entityPlayer.getHeldItem() != null) {
                n12 -= 8;
                final ItemStack copy = entityPlayer.getHeldItem().copy();
                if (copy.hasEffect() && (copy.getItem() instanceof ItemTool || copy.getItem() instanceof ItemArmor)) {
                    copy.stackSize = 1;
                }
                this.renderItemStack(copy, 0, -28);
                n12 += 16;
            }
            ItemStack[] armorInventory2;
            while (i < (armorInventory2 = entityPlayer.inventory.armorInventory).length) {
                final ItemStack itemStack = armorInventory2[i];
                if (itemStack != null) {
                    final ItemStack copy2 = itemStack.copy();
                    if (copy2.hasEffect() && (copy2.getItem() instanceof ItemTool || copy2.getItem() instanceof ItemArmor)) {
                        copy2.stackSize = 1;
                    }
                    this.renderItemStack(copy2, 0, -28);
                    n12 += 16;
                }
                ++i;
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void renderItemStack(final ItemStack itemStack, final int n, final int n2) {
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        NameTags.mc.getRenderItem().zLevel = -150.0f;
        this.whatTheFuckOpenGLThisFixesItemGlint();
        NameTags.mc.getRenderItem().func_180450_b(itemStack, n, n2);
        NameTags.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(0.5, 0.5, 0.5);
        this.renderEnchantText(itemStack, n, n2);
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    public void renderEnchantText(final ItemStack itemStack, final int n, final int n2) {
        int n3 = n2 - 24;
        if (itemStack.getItem() instanceof ItemArmor) {
            final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, itemStack);
            final int enchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, itemStack);
            final int enchantmentLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            if (enchantmentLevel > 0) {
                final Minecraft mc = NameTags.mc;
                Minecraft.fontRendererObj.drawString("p" + enchantmentLevel, n * 2, n3, 16777215);
                n3 += 7;
            }
            if (enchantmentLevel2 > 0) {
                final Minecraft mc2 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("t" + enchantmentLevel2, n * 2, n3, 16777215);
                n3 += 7;
            }
            if (enchantmentLevel3 > 0) {
                final Minecraft mc3 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("u" + enchantmentLevel3, n * 2, n3, 16777215);
                n3 += 7;
            }
        }
        if (itemStack.getItem() instanceof ItemBow) {
            final int enchantmentLevel4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
            final int enchantmentLevel5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
            final int enchantmentLevel6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack);
            final int enchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            if (enchantmentLevel4 > 0) {
                final Minecraft mc4 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("d" + enchantmentLevel4, n * 2, n3, 16777215);
                n3 += 7;
            }
            if (enchantmentLevel5 > 0) {
                final Minecraft mc5 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("k" + enchantmentLevel5, n * 2, n3, 16777215);
                n3 += 7;
            }
            if (enchantmentLevel6 > 0) {
                final Minecraft mc6 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("f" + enchantmentLevel6, n * 2, n3, 16777215);
                n3 += 7;
            }
            if (enchantmentLevel7 > 0) {
                final Minecraft mc7 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("u" + enchantmentLevel7, n * 2, n3, 16777215);
                n3 += 7;
            }
        }
        if (itemStack.getItem() instanceof ItemSword) {
            final int enchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, itemStack);
            final int enchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180313_o.effectId, itemStack);
            final int enchantmentLevel10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);
            final int enchantmentLevel11 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            if (enchantmentLevel8 > 0) {
                final Minecraft mc8 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("s" + enchantmentLevel8, n * 2, n3, 16777215);
                n3 += 7;
            }
            if (enchantmentLevel9 > 0) {
                final Minecraft mc9 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("k" + enchantmentLevel9, n * 2, n3, 16777215);
                n3 += 7;
            }
            if (enchantmentLevel10 > 0) {
                final Minecraft mc10 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("f" + enchantmentLevel10, n * 2, n3, 16777215);
                n3 += 7;
            }
            if (enchantmentLevel11 > 0) {
                final Minecraft mc11 = NameTags.mc;
                Minecraft.fontRendererObj.drawString("u" + enchantmentLevel11, n * 2, n3, 16777215);
            }
        }
    }
    
    public void whatTheFuckOpenGLThisFixesItemGlint() {
    }
}
