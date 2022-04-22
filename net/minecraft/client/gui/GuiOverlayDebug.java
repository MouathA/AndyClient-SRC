package net.minecraft.client.gui;

import com.google.common.base.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import optifine.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class GuiOverlayDebug extends Gui
{
    private final Minecraft mc;
    private final FontRenderer fontRenderer;
    private static final String __OBFID;
    
    public GuiOverlayDebug(final Minecraft mc) {
        this.mc = mc;
        this.fontRenderer = Minecraft.fontRendererObj;
    }
    
    public void func_175237_a(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection("debug");
        this.func_180798_a();
        this.func_175239_b(scaledResolution);
        this.mc.mcProfiler.endSection();
    }
    
    private boolean func_175236_d() {
        return Minecraft.thePlayer.func_175140_cp() || this.mc.gameSettings.field_178879_v;
    }
    
    protected void func_180798_a() {
        final List call = this.call();
        while (0 < call.size()) {
            final String s = call.get(0);
            if (!Strings.isNullOrEmpty(s)) {
                final int font_HEIGHT = this.fontRenderer.FONT_HEIGHT;
                final int stringWidth = this.fontRenderer.getStringWidth(s);
                final int n = 2 + font_HEIGHT * 0;
                Gui.drawRect(1, n - 1, 2 + stringWidth + 1, n + font_HEIGHT - 1, -1873784752);
                this.fontRenderer.drawString(s, 2, n, 14737632);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    protected void func_175239_b(final ScaledResolution scaledResolution) {
        final List func_175238_c = this.func_175238_c();
        while (0 < func_175238_c.size()) {
            final String s = func_175238_c.get(0);
            if (!Strings.isNullOrEmpty(s)) {
                final int font_HEIGHT = this.fontRenderer.FONT_HEIGHT;
                final int stringWidth = this.fontRenderer.getStringWidth(s);
                final int n = ScaledResolution.getScaledWidth() - 2 - stringWidth;
                final int n2 = 2 + font_HEIGHT * 0;
                Gui.drawRect(n - 1, n2 - 1, n + stringWidth + 1, n2 + font_HEIGHT - 1, -1873784752);
                this.fontRenderer.drawString(s, n, n2, 14737632);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    protected List call() {
        final BlockPos blockPos = new BlockPos(this.mc.func_175606_aa().posX, this.mc.func_175606_aa().getEntityBoundingBox().minY, this.mc.func_175606_aa().posZ);
        if (this.func_175236_d()) {
            return Lists.newArrayList("Minecraft 1.8 (" + this.mc.func_175600_c() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + Minecraft.theWorld.getDebugLoadedEntities(), Minecraft.theWorld.getProviderName(), "", String.format("Chunk-relative: %d %d %d", blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF));
        }
        final Entity func_175606_aa = this.mc.func_175606_aa();
        final EnumFacing func_174811_aO = func_175606_aa.func_174811_aO();
        String s = "Invalid";
        switch (SwitchEnumFacing.field_178907_a[func_174811_aO.ordinal()]) {
            case 1: {
                s = "Towards negative Z";
                break;
            }
            case 2: {
                s = "Towards positive Z";
                break;
            }
            case 3: {
                s = "Towards negative X";
                break;
            }
            case 4: {
                s = "Towards positive X";
                break;
            }
        }
        final ArrayList arrayList = Lists.newArrayList("Minecraft 1.8 (" + this.mc.func_175600_c() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + Minecraft.theWorld.getDebugLoadedEntities(), Minecraft.theWorld.getProviderName(), "", String.format("XYZ: %.3f / %.5f / %.3f", this.mc.func_175606_aa().posX, this.mc.func_175606_aa().getEntityBoundingBox().minY, this.mc.func_175606_aa().posZ), String.format("Block: %d %d %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()), String.format("Chunk: %d %d %d in %d %d %d", blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF, blockPos.getX() >> 4, blockPos.getY() >> 4, blockPos.getZ() >> 4), String.format("Facing: %s (%s) (%.1f / %.1f)", func_174811_aO, s, MathHelper.wrapAngleTo180_float(func_175606_aa.rotationYaw), MathHelper.wrapAngleTo180_float(func_175606_aa.rotationPitch)));
        if (Minecraft.theWorld != null && Minecraft.theWorld.isBlockLoaded(blockPos)) {
            final Chunk chunkFromBlockCoords = Minecraft.theWorld.getChunkFromBlockCoords(blockPos);
            arrayList.add("Biome: " + chunkFromBlockCoords.getBiome(blockPos, Minecraft.theWorld.getWorldChunkManager()).biomeName);
            arrayList.add("Light: " + chunkFromBlockCoords.setLight(blockPos, 0) + " (" + chunkFromBlockCoords.getLightFor(EnumSkyBlock.SKY, blockPos) + " sky, " + chunkFromBlockCoords.getLightFor(EnumSkyBlock.BLOCK, blockPos) + " block)");
            DifficultyInstance difficultyInstance = Minecraft.theWorld.getDifficultyForLocation(blockPos);
            if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
                final EntityPlayerMP func_177451_a = this.mc.getIntegratedServer().getConfigurationManager().func_177451_a(Minecraft.thePlayer.getUniqueID());
                if (func_177451_a != null) {
                    difficultyInstance = func_177451_a.worldObj.getDifficultyForLocation(new BlockPos(func_177451_a));
                }
            }
            arrayList.add(String.format("Local Difficulty: %.2f (Day %d)", difficultyInstance.func_180168_b(), Minecraft.theWorld.getWorldTime() / 24000L));
        }
        if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
            arrayList.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
        }
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.func_178782_a() != null) {
            final BlockPos func_178782_a = this.mc.objectMouseOver.func_178782_a();
            arrayList.add(String.format("Looking at: %d %d %d", func_178782_a.getX(), func_178782_a.getY(), func_178782_a.getZ()));
        }
        return arrayList;
    }
    
    protected List func_175238_c() {
        final long maxMemory = Runtime.getRuntime().maxMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long n = totalMemory - Runtime.getRuntime().freeMemory();
        final ArrayList arrayList = Lists.newArrayList(String.format("Java: %s %dbit", System.getProperty("java.version"), this.mc.isJava64bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", n * 100L / maxMemory, func_175240_a(n), func_175240_a(maxMemory)), String.format("Allocated: % 2d%% %03dMB", totalMemory * 100L / maxMemory, func_175240_a(totalMemory)), "", String.format("Display: %dx%d (%s)", Display.getWidth(), Display.getHeight(), GL11.glGetString(7936)), GL11.glGetString(7937), GL11.glGetString(7938));
        if (Reflector.FMLCommonHandler_getBrandings.exists()) {
            final Object call = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            arrayList.add("");
            arrayList.addAll((Collection<? extends String>)Reflector.call(call, Reflector.FMLCommonHandler_getBrandings, false));
        }
        if (this.func_175236_d()) {
            return arrayList;
        }
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.func_178782_a() != null) {
            final BlockPos func_178782_a = this.mc.objectMouseOver.func_178782_a();
            IBlockState blockState = Minecraft.theWorld.getBlockState(func_178782_a);
            if (Minecraft.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
                blockState = blockState.getBlock().getActualState(blockState, Minecraft.theWorld, func_178782_a);
            }
            arrayList.add("");
            arrayList.add(String.valueOf(Block.blockRegistry.getNameForObject(blockState.getBlock())));
            for (final Map.Entry<K, Comparable<?>> entry : blockState.getProperties().entrySet()) {
                String s = entry.getValue().toString();
                if (entry.getValue() == Boolean.TRUE) {
                    s = EnumChatFormatting.GREEN + s;
                }
                else if (entry.getValue() == Boolean.FALSE) {
                    s = EnumChatFormatting.RED + s;
                }
                arrayList.add(String.valueOf(entry.getKey().getName()) + ": " + s);
            }
        }
        return arrayList;
    }
    
    private static long func_175240_a(final long n) {
        return n / 1024L / 1024L;
    }
    
    static {
        __OBFID = "CL_00001956";
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_178907_a;
        private static final String __OBFID;
        private static final String[] lIlllIIlIIlllIII;
        private static String[] lIlllIIlIIlllIIl;
        
        static {
            lIIIlIIIIIlIlIllI();
            lIIIlIIIIIlIlIlIl();
            __OBFID = SwitchEnumFacing.lIlllIIlIIlllIII[0];
            field_178907_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_178907_a[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_178907_a[EnumFacing.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_178907_a[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_178907_a[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void lIIIlIIIIIlIlIlIl() {
            (lIlllIIlIIlllIII = new String[1])[0] = lIIIlIIIIIlIlIlII(SwitchEnumFacing.lIlllIIlIIlllIIl[0], SwitchEnumFacing.lIlllIIlIIlllIIl[1]);
            SwitchEnumFacing.lIlllIIlIIlllIIl = null;
        }
        
        private static void lIIIlIIIIIlIlIllI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIlllIIlIIlllIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIIlIIIIIlIlIlII(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
                final Cipher instance = Cipher.getInstance("DES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
