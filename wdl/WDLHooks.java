package wdl;

import net.minecraft.profiler.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import com.google.common.collect.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.client.network.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import net.minecraft.network.play.server.*;
import wdl.api.*;
import net.minecraft.block.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;
import wdl.chan.*;

public class WDLHooks
{
    private static final Profiler profiler;
    
    static {
        profiler = Minecraft.getInstance().mcProfiler;
    }
    
    public static void onWorldClientTick(final WorldClient worldClient) {
        WDLHooks.profiler.startSection("wdl");
        final ImmutableList copy = ImmutableList.copyOf(worldClient.playerEntities);
        if (worldClient != WDL.worldClient) {
            WDLHooks.profiler.startSection("onWorldLoad");
            if (WDL.worldLoadingDeferred) {
                return;
            }
            WDLEvents.onWorldLoad(worldClient);
            WDLHooks.profiler.endSection();
        }
        else {
            WDLHooks.profiler.startSection("inventoryCheck");
            if (WDL.downloading && WDL.thePlayer != null && WDL.thePlayer.openContainer != WDL.windowContainer) {
                if (WDL.thePlayer.openContainer == WDL.thePlayer.inventoryContainer) {
                    WDLHooks.profiler.startSection("onItemGuiClosed");
                    WDLHooks.profiler.startSection("Core");
                    boolean b = WDLEvents.onItemGuiClosed();
                    WDLHooks.profiler.endSection();
                    final Container openContainer = WDL.thePlayer.openContainer;
                    if (WDL.lastEntity != null) {
                        final Entity lastEntity = WDL.lastEntity;
                        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IGuiHooksListener.class)) {
                            if (b) {
                                break;
                            }
                            WDLHooks.profiler.startSection(modInfo.id);
                            b = ((IGuiHooksListener)modInfo.mod).onEntityGuiClosed(worldClient, lastEntity, openContainer);
                            WDLHooks.profiler.endSection();
                        }
                        if (!b) {
                            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_WARNING, "wdl.messages.onGuiClosedWarning.unhandledEntity", lastEntity);
                        }
                    }
                    else {
                        final BlockPos lastClickedBlock = WDL.lastClickedBlock;
                        for (final WDLApi.ModInfo modInfo2 : WDLApi.getImplementingExtensions(IGuiHooksListener.class)) {
                            if (b) {
                                break;
                            }
                            WDLHooks.profiler.startSection(modInfo2.id);
                            b = ((IGuiHooksListener)modInfo2.mod).onBlockGuiClosed(worldClient, lastClickedBlock, openContainer);
                            WDLHooks.profiler.endSection();
                        }
                        if (!b) {
                            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_WARNING, "wdl.messages.onGuiClosedWarning.unhandledTileEntity", lastClickedBlock, worldClient.getTileEntity(lastClickedBlock));
                        }
                    }
                    WDLHooks.profiler.endSection();
                }
                else {
                    WDLHooks.profiler.startSection("onItemGuiOpened");
                    WDLHooks.profiler.startSection("Core");
                    WDLHooks.profiler.endSection();
                    WDLHooks.profiler.endSection();
                }
                WDL.windowContainer = WDL.thePlayer.openContainer;
            }
            WDLHooks.profiler.endSection();
        }
        WDLHooks.profiler.startSection("capes");
        CapeHandler.onWorldTick(copy);
        WDLHooks.profiler.endSection();
        WDLHooks.profiler.endSection();
    }
    
    public static void onWorldClientDoPreChunk(final WorldClient worldClient, final int n, final int n2, final boolean b) {
        if (!WDL.downloading) {
            return;
        }
        WDLHooks.profiler.startSection("wdl");
        if (!b) {
            WDLHooks.profiler.startSection("onChunkNoLongerNeeded");
            final Chunk chunkFromChunkCoords = worldClient.getChunkFromChunkCoords(n, n2);
            WDLHooks.profiler.startSection("Core");
            WDLEvents.onChunkNoLongerNeeded(chunkFromChunkCoords);
            WDLHooks.profiler.endSection();
            WDLHooks.profiler.endSection();
        }
        WDLHooks.profiler.endSection();
    }
    
    public static void onWorldClientRemoveEntityFromWorld(final WorldClient worldClient, final int n) {
        if (!WDL.downloading) {
            return;
        }
        WDLHooks.profiler.startSection("wdl.onRemoveEntityFromWorld");
        final Entity entityByID = worldClient.getEntityByID(n);
        WDLHooks.profiler.startSection("Core");
        WDLEvents.onRemoveEntityFromWorld(entityByID);
        WDLHooks.profiler.endSection();
        WDLHooks.profiler.endSection();
    }
    
    public static void onNHPCHandleChat(final NetHandlerPlayClient netHandlerPlayClient, final S02PacketChat s02PacketChat) {
        if (!Minecraft.getInstance().isCallingFromMinecraftThread()) {
            return;
        }
        if (!WDL.downloading) {
            return;
        }
        WDLHooks.profiler.startSection("wdl.onChatMessage");
        final String unformattedText = s02PacketChat.func_148915_c().getUnformattedText();
        WDLHooks.profiler.startSection("Core");
        WDLEvents.onChatMessage(unformattedText);
        WDLHooks.profiler.endSection();
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IChatMessageListener.class)) {
            WDLHooks.profiler.startSection(modInfo.id);
            ((IChatMessageListener)modInfo.mod).onChat(WDL.worldClient, unformattedText);
            WDLHooks.profiler.endSection();
        }
        WDLHooks.profiler.endSection();
    }
    
    public static void onNHPCHandleMaps(final NetHandlerPlayClient netHandlerPlayClient, final S34PacketMaps s34PacketMaps) {
        if (!Minecraft.getInstance().isCallingFromMinecraftThread()) {
            return;
        }
        if (!WDL.downloading) {
            return;
        }
        WDLHooks.profiler.startSection("wdl.onMapDataLoaded");
        final int mapId = s34PacketMaps.getMapId();
        final MapData loadMapData = ItemMap.loadMapData(s34PacketMaps.getMapId(), WDL.worldClient);
        WDLHooks.profiler.startSection("Core");
        WDLEvents.onMapDataLoaded(mapId, loadMapData);
        WDLHooks.profiler.endSection();
        WDLHooks.profiler.endSection();
    }
    
    public static void onNHPCHandleCustomPayload(final NetHandlerPlayClient netHandlerPlayClient, final S3FPacketCustomPayload s3FPacketCustomPayload) {
        if (!Minecraft.getInstance().isCallingFromMinecraftThread()) {
            return;
        }
        if (!s3FPacketCustomPayload.getBufferData().isReadable()) {
            return;
        }
        final String channelName = s3FPacketCustomPayload.getChannelName();
        final byte[] array = s3FPacketCustomPayload.getBufferData().array();
        WDLHooks.profiler.startSection("wdl.onPluginMessage");
        WDLHooks.profiler.startSection("Core");
        WDLEvents.onPluginChannelPacket(channelName, array);
        WDLHooks.profiler.endSection();
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IPluginChannelListener.class)) {
            WDLHooks.profiler.startSection(modInfo.id);
            ((IPluginChannelListener)modInfo.mod).onPluginChannelPacket(WDL.worldClient, channelName, array);
            WDLHooks.profiler.endSection();
        }
        WDLHooks.profiler.endSection();
    }
    
    public static void onNHPCHandleBlockAction(final NetHandlerPlayClient netHandlerPlayClient, final S24PacketBlockAction s24PacketBlockAction) {
        if (!Minecraft.getInstance().isCallingFromMinecraftThread()) {
            return;
        }
        if (!WDL.downloading) {
            return;
        }
        WDLHooks.profiler.startSection("wdl.onBlockEvent");
        final BlockPos func_179825_a = s24PacketBlockAction.func_179825_a();
        final Block blockType = s24PacketBlockAction.getBlockType();
        final int data1 = s24PacketBlockAction.getData1();
        final int data2 = s24PacketBlockAction.getData2();
        WDLHooks.profiler.startSection("Core");
        WDLEvents.onBlockEvent(func_179825_a, blockType, data1, data2);
        WDLHooks.profiler.endSection();
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IBlockEventListener.class)) {
            WDLHooks.profiler.startSection(modInfo.id);
            ((IBlockEventListener)modInfo.mod).onBlockEvent(WDL.worldClient, func_179825_a, blockType, data1, data2);
            WDLHooks.profiler.endSection();
        }
        WDLHooks.profiler.endSection();
    }
    
    public static void onCrashReportPopulateEnvironment(final CrashReport crashReport) {
        crashReport.makeCategory("World Downloader Mod").addCrashSectionCallable("Info", new Callable() {
            @Override
            public String call() {
                return WDL.getDebugInfo();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
    }
    
    public static void injectWDLButtons(final GuiIngameMenu guiIngameMenu, final List list) {
        for (final GuiButton guiButton : list) {
            if (guiButton.id == 5) {
                final int n = guiButton.yPosition + 24;
                break;
            }
        }
        for (final GuiButton guiButton2 : list) {
            if (guiButton2.yPosition >= 0) {
                final GuiButton guiButton3 = guiButton2;
                guiButton3.yPosition += 24;
            }
        }
        final GuiButton guiButton4 = new GuiButton(1464093811, GuiIngameMenu.width / 2 - 100, 0, 168, 20, null);
        final GuiButton guiButton5 = new GuiButton(1464093807, GuiIngameMenu.width / 2 + 72, 0, 28, 20, I18n.format("wdl.gui.ingameMenu.settings", new Object[0]));
        if (WDL.minecraft.isIntegratedServerRunning()) {
            guiButton4.displayString = I18n.format("           Can't download in single player", new Object[0]);
            guiButton4.enabled = false;
        }
        else if (!WDLPluginChannels.canDownloadAtAll()) {
            if (WDLPluginChannels.canRequestPermissions()) {
                guiButton4.displayString = I18n.format("wdl.gui.ingameMenu.downloadStatus.request", new Object[0]);
            }
            else {
                guiButton4.displayString = I18n.format("        Download disabled by server", new Object[0]);
                guiButton4.enabled = false;
            }
        }
        else if (WDL.saving) {
            guiButton4.displayString = I18n.format("wdl.gui.ingameMenu.downloadStatus.saving", new Object[0]);
            guiButton4.enabled = false;
            guiButton5.enabled = false;
        }
        else if (WDL.downloading) {
            guiButton4.displayString = I18n.format("wdl.gui.ingameMenu.downloadStatus.stop", new Object[0]);
        }
        else {
            guiButton4.displayString = I18n.format("Download this World", new Object[0]);
        }
        list.add(guiButton4);
        list.add(guiButton5);
    }
    
    public static void handleWDLButtonClick(final GuiIngameMenu p0, final GuiButton p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/gui/GuiButton.enabled:Z
        //     4: ifne            8
        //     7: return         
        //     8: aload_1        
        //     9: getfield        net/minecraft/client/gui/GuiButton.id:I
        //    12: ldc_w           1464093811
        //    15: if_icmpne       104
        //    18: getstatic       wdl/WDL.minecraft:Lnet/minecraft/client/Minecraft;
        //    21: invokevirtual   net/minecraft/client/Minecraft.isIntegratedServerRunning:()Z
        //    24: ifeq            28
        //    27: return         
        //    28: getstatic       wdl/WDL.downloading:Z
        //    31: ifeq            37
        //    34: goto            162
        //    37: invokestatic    wdl/chan/WDLPluginChannels.canDownloadAtAll:()Z
        //    40: ifne            72
        //    43: invokestatic    wdl/chan/WDLPluginChannels.canRequestPermissions:()Z
        //    46: ifeq            66
        //    49: getstatic       wdl/WDL.minecraft:Lnet/minecraft/client/Minecraft;
        //    52: new             Lwdl/gui/GuiWDLPermissions;
        //    55: dup            
        //    56: aload_0        
        //    57: invokespecial   wdl/gui/GuiWDLPermissions.<init>:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    60: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    63: goto            71
        //    66: aload_1        
        //    67: iconst_0       
        //    68: putfield        net/minecraft/client/gui/GuiButton.enabled:Z
        //    71: return         
        //    72: invokestatic    wdl/chan/WDLPluginChannels.hasChunkOverrides:()Z
        //    75: ifeq            101
        //    78: invokestatic    wdl/chan/WDLPluginChannels.canDownloadInGeneral:()Z
        //    81: ifne            101
        //    84: getstatic       wdl/WDL.minecraft:Lnet/minecraft/client/Minecraft;
        //    87: new             Lwdl/gui/GuiWDLChunkOverrides;
        //    90: dup            
        //    91: aload_0        
        //    92: invokespecial   wdl/gui/GuiWDLChunkOverrides.<init>:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    95: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    98: goto            162
        //   101: goto            162
        //   104: aload_1        
        //   105: getfield        net/minecraft/client/gui/GuiButton.id:I
        //   108: ldc_w           1464093807
        //   111: if_icmpne       157
        //   114: getstatic       wdl/WDL.minecraft:Lnet/minecraft/client/Minecraft;
        //   117: invokevirtual   net/minecraft/client/Minecraft.isIntegratedServerRunning:()Z
        //   120: ifeq            140
        //   123: getstatic       wdl/WDL.minecraft:Lnet/minecraft/client/Minecraft;
        //   126: new             Lwdl/gui/GuiWDLAbout;
        //   129: dup            
        //   130: aload_0        
        //   131: invokespecial   wdl/gui/GuiWDLAbout.<init>:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   134: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   137: goto            162
        //   140: getstatic       wdl/WDL.minecraft:Lnet/minecraft/client/Minecraft;
        //   143: new             Lwdl/gui/GuiWDL;
        //   146: dup            
        //   147: aload_0        
        //   148: invokespecial   wdl/gui/GuiWDL.<init>:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   151: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   154: goto            162
        //   157: aload_1        
        //   158: getfield        net/minecraft/client/gui/GuiButton.id:I
        //   161: iconst_1       
        //   162: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0162 (coming from #0154).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
