package net.minecraft.client.gui;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.network.*;
import net.minecraft.client.renderer.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.world.*;

public class GuiPlayerTabOverlay extends Gui
{
    private static final Ordering field_175252_a;
    private final Minecraft field_175250_f;
    private final GuiIngame field_175251_g;
    private IChatComponent footer;
    private IChatComponent header;
    private long field_175253_j;
    private boolean field_175254_k;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001943";
        field_175252_a = Ordering.from(new PlayerComparator(null));
    }
    
    public GuiPlayerTabOverlay(final Minecraft field_175250_f, final GuiIngame field_175251_g) {
        this.field_175250_f = field_175250_f;
        this.field_175251_g = field_175251_g;
    }
    
    public String func_175243_a(final NetworkPlayerInfo networkPlayerInfo) {
        return (networkPlayerInfo.func_178854_k() != null) ? networkPlayerInfo.func_178854_k().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfo.func_178850_i(), networkPlayerInfo.func_178845_a().getName());
    }
    
    public void func_175246_a(final boolean field_175254_k) {
        if (field_175254_k && !this.field_175254_k) {
            this.field_175253_j = Minecraft.getSystemTime();
        }
        this.field_175254_k = field_175254_k;
    }
    
    public void func_175249_a(final int p0, final Scoreboard p1, final ScoreObjective p2) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/minecraft/client/gui/GuiPlayerTabOverlay.func_175249_a:(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V'.
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
        //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
        //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 983.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void func_175245_a(final int n, final int n2, final int n3, final NetworkPlayerInfo networkPlayerInfo) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_175250_f.getTextureManager().bindTexture(GuiPlayerTabOverlay.icons);
        if (networkPlayerInfo.getResponseTime() >= 0) {
            if (networkPlayerInfo.getResponseTime() >= 150) {
                if (networkPlayerInfo.getResponseTime() >= 300) {
                    if (networkPlayerInfo.getResponseTime() >= 600) {
                        if (networkPlayerInfo.getResponseTime() < 1000) {}
                    }
                }
            }
        }
        this.zLevel += 100.0f;
        this.drawTexturedModalRect(n2 + n - 11, n3, 0, 208, 10, 8);
        this.zLevel -= 100.0f;
    }
    
    private void func_175247_a(final ScoreObjective scoreObjective, final int n, final String s, final int n2, final int n3, final NetworkPlayerInfo networkPlayerInfo) {
        final int scorePoints = scoreObjective.getScoreboard().getValueFromObjective(s, scoreObjective).getScorePoints();
        if (scoreObjective.func_178766_e() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            this.field_175250_f.getTextureManager().bindTexture(GuiPlayerTabOverlay.icons);
            if (this.field_175253_j == networkPlayerInfo.func_178855_p()) {
                if (scorePoints < networkPlayerInfo.func_178835_l()) {
                    networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
                    networkPlayerInfo.func_178844_b(this.field_175251_g.getUpdateCounter() + 20);
                }
                else if (scorePoints > networkPlayerInfo.func_178835_l()) {
                    networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
                    networkPlayerInfo.func_178844_b(this.field_175251_g.getUpdateCounter() + 10);
                }
            }
            if (Minecraft.getSystemTime() - networkPlayerInfo.func_178847_n() > 1000L || this.field_175253_j != networkPlayerInfo.func_178855_p()) {
                networkPlayerInfo.func_178836_b(scorePoints);
                networkPlayerInfo.func_178857_c(scorePoints);
                networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
            }
            networkPlayerInfo.func_178843_c(this.field_175253_j);
            networkPlayerInfo.func_178836_b(scorePoints);
            final int ceiling_float_int = MathHelper.ceiling_float_int(Math.max(scorePoints, networkPlayerInfo.func_178860_m()) / 2.0f);
            final int max = Math.max(MathHelper.ceiling_float_int((float)(scorePoints / 2)), Math.max(MathHelper.ceiling_float_int((float)(networkPlayerInfo.func_178860_m() / 2)), 10));
            final boolean b = networkPlayerInfo.func_178858_o() > this.field_175251_g.getUpdateCounter() && (networkPlayerInfo.func_178858_o() - this.field_175251_g.getUpdateCounter()) / 3L % 2L == 1L;
            if (ceiling_float_int > 0) {
                final float min = Math.min((n3 - n2 - 4) / (float)max, 9.0f);
                if (min > 3.0f) {
                    int n4 = ceiling_float_int;
                    while (0 < max) {
                        this.func_175174_a(n2 + 0 * min, (float)n, b ? 25 : 16, 0, 9, 9);
                        ++n4;
                    }
                    while (0 < ceiling_float_int) {
                        this.func_175174_a(n2 + 0 * min, (float)n, b ? 25 : 16, 0, 9, 9);
                        if (b) {
                            if (1 < networkPlayerInfo.func_178860_m()) {
                                this.func_175174_a(n2 + 0 * min, (float)n, 70, 0, 9, 9);
                            }
                            if (1 == networkPlayerInfo.func_178860_m()) {
                                this.func_175174_a(n2 + 0 * min, (float)n, 79, 0, 9, 9);
                            }
                        }
                        if (1 < scorePoints) {
                            this.func_175174_a(n2 + 0 * min, (float)n, (0 >= 10) ? 160 : 52, 0, 9, 9);
                        }
                        if (scorePoints != 0) {
                            this.func_175174_a(n2 + 0 * min, (float)n, (0 >= 10) ? 169 : 61, 0, 9, 9);
                        }
                        ++n4;
                    }
                }
                else {
                    final float clamp_float = MathHelper.clamp_float(scorePoints / 20.0f, 0.0f, 1.0f);
                    final int n5 = (int)((1.0f - clamp_float) * 255.0f) << 16 | (int)(clamp_float * 255.0f) << 8;
                    String s2 = new StringBuilder().append(scorePoints / 2.0f).toString();
                    if (n3 - Minecraft.fontRendererObj.getStringWidth(String.valueOf(s2) + "hp") >= n2) {
                        s2 = String.valueOf(s2) + "hp";
                    }
                    Minecraft.fontRendererObj.func_175063_a(s2, (float)((n3 + n2) / 2 - Minecraft.fontRendererObj.getStringWidth(s2) / 2), (float)n, n5);
                }
            }
        }
        else {
            final String string = new StringBuilder().append(EnumChatFormatting.YELLOW).append(scorePoints).toString();
            Minecraft.fontRendererObj.func_175063_a(string, (float)(n3 - Minecraft.fontRendererObj.getStringWidth(string)), (float)n, 16777215);
        }
    }
    
    public void setFooter(final IChatComponent footer) {
        this.footer = footer;
    }
    
    public void setHeader(final IChatComponent header) {
        this.header = header;
    }
    
    static class PlayerComparator implements Comparator
    {
        private static final String __OBFID;
        
        private PlayerComparator() {
        }
        
        public int func_178952_a(final NetworkPlayerInfo networkPlayerInfo, final NetworkPlayerInfo networkPlayerInfo2) {
            final ScorePlayerTeam func_178850_i = networkPlayerInfo.func_178850_i();
            final ScorePlayerTeam func_178850_i2 = networkPlayerInfo2.func_178850_i();
            return ComparisonChain.start().compareTrueFirst(networkPlayerInfo.getGameType() != WorldSettings.GameType.SPECTATOR, networkPlayerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR).compare((func_178850_i != null) ? func_178850_i.getRegisteredName() : "", (func_178850_i2 != null) ? func_178850_i2.getRegisteredName() : "").compare(networkPlayerInfo.func_178845_a().getName(), networkPlayerInfo2.func_178845_a().getName()).result();
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.func_178952_a((NetworkPlayerInfo)o, (NetworkPlayerInfo)o2);
        }
        
        PlayerComparator(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00001941";
        }
    }
}
