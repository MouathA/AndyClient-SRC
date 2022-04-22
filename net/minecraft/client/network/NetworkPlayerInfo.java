package net.minecraft.client.network;

import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.network.play.server.*;
import com.google.common.base.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import com.mojang.authlib.minecraft.*;

public class NetworkPlayerInfo
{
    private final GameProfile field_178867_a;
    private WorldSettings.GameType gameType;
    private int responseTime;
    private boolean field_178864_d;
    private ResourceLocation field_178865_e;
    private ResourceLocation field_178862_f;
    private String field_178863_g;
    private IChatComponent field_178872_h;
    private int field_178873_i;
    private int field_178870_j;
    private long field_178871_k;
    private long field_178868_l;
    private long field_178869_m;
    private static final String __OBFID;
    
    public NetworkPlayerInfo(final GameProfile field_178867_a) {
        this.field_178864_d = false;
        this.field_178873_i = 0;
        this.field_178870_j = 0;
        this.field_178871_k = 0L;
        this.field_178868_l = 0L;
        this.field_178869_m = 0L;
        this.field_178867_a = field_178867_a;
    }
    
    public NetworkPlayerInfo(final S38PacketPlayerListItem.AddPlayerData addPlayerData) {
        this.field_178864_d = false;
        this.field_178873_i = 0;
        this.field_178870_j = 0;
        this.field_178871_k = 0L;
        this.field_178868_l = 0L;
        this.field_178869_m = 0L;
        this.field_178867_a = addPlayerData.func_179962_a();
        this.gameType = addPlayerData.func_179960_c();
        this.responseTime = addPlayerData.func_179963_b();
    }
    
    public GameProfile func_178845_a() {
        return this.field_178867_a;
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
    
    public int getResponseTime() {
        return this.responseTime;
    }
    
    protected void func_178839_a(final WorldSettings.GameType gameType) {
        this.gameType = gameType;
    }
    
    protected void func_178838_a(final int responseTime) {
        this.responseTime = responseTime;
    }
    
    public boolean func_178856_e() {
        return this.field_178865_e != null;
    }
    
    public String func_178851_f() {
        return (this.field_178863_g == null) ? DefaultPlayerSkin.func_177332_b(this.field_178867_a.getId()) : this.field_178863_g;
    }
    
    public ResourceLocation func_178837_g() {
        if (this.field_178865_e == null) {
            this.func_178841_j();
        }
        return (ResourceLocation)Objects.firstNonNull(this.field_178865_e, DefaultPlayerSkin.func_177334_a(this.field_178867_a.getId()));
    }
    
    public ResourceLocation func_178861_h() {
        if (this.field_178862_f == null) {
            this.func_178841_j();
        }
        return this.field_178862_f;
    }
    
    public ScorePlayerTeam func_178850_i() {
        Minecraft.getMinecraft();
        return Minecraft.theWorld.getScoreboard().getPlayersTeam(this.func_178845_a().getName());
    }
    
    protected void func_178841_j() {
        // monitorenter(this)
        if (!this.field_178864_d) {
            this.field_178864_d = true;
            Minecraft.getMinecraft().getSkinManager().func_152790_a(this.field_178867_a, new SkinManager.SkinAvailableCallback() {
                private static final String __OBFID;
                final NetworkPlayerInfo this$0;
                
                @Override
                public void func_180521_a(final MinecraftProfileTexture.Type type, final ResourceLocation resourceLocation, final MinecraftProfileTexture minecraftProfileTexture) {
                    switch (SwitchType.field_178875_a[type.ordinal()]) {
                        case 1: {
                            NetworkPlayerInfo.access$0(this.this$0, resourceLocation);
                            NetworkPlayerInfo.access$1(this.this$0, minecraftProfileTexture.getMetadata("model"));
                            if (NetworkPlayerInfo.access$2(this.this$0) == null) {
                                NetworkPlayerInfo.access$1(this.this$0, "default");
                                break;
                            }
                            break;
                        }
                        case 2: {
                            NetworkPlayerInfo.access$3(this.this$0, resourceLocation);
                            break;
                        }
                    }
                }
                
                static {
                    __OBFID = "CL_00002619";
                }
            }, true);
        }
    }
    // monitorexit(this)
    
    public void func_178859_a(final IChatComponent field_178872_h) {
        this.field_178872_h = field_178872_h;
    }
    
    public IChatComponent func_178854_k() {
        return this.field_178872_h;
    }
    
    public int func_178835_l() {
        return this.field_178873_i;
    }
    
    public void func_178836_b(final int field_178873_i) {
        this.field_178873_i = field_178873_i;
    }
    
    public int func_178860_m() {
        return this.field_178870_j;
    }
    
    public void func_178857_c(final int field_178870_j) {
        this.field_178870_j = field_178870_j;
    }
    
    public long func_178847_n() {
        return this.field_178871_k;
    }
    
    public void func_178846_a(final long field_178871_k) {
        this.field_178871_k = field_178871_k;
    }
    
    public long func_178858_o() {
        return this.field_178868_l;
    }
    
    public void func_178844_b(final long field_178868_l) {
        this.field_178868_l = field_178868_l;
    }
    
    public long func_178855_p() {
        return this.field_178869_m;
    }
    
    public void func_178843_c(final long field_178869_m) {
        this.field_178869_m = field_178869_m;
    }
    
    static void access$0(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation field_178865_e) {
        networkPlayerInfo.field_178865_e = field_178865_e;
    }
    
    static void access$1(final NetworkPlayerInfo networkPlayerInfo, final String field_178863_g) {
        networkPlayerInfo.field_178863_g = field_178863_g;
    }
    
    static String access$2(final NetworkPlayerInfo networkPlayerInfo) {
        return networkPlayerInfo.field_178863_g;
    }
    
    static void access$3(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation field_178862_f) {
        networkPlayerInfo.field_178862_f = field_178862_f;
    }
    
    static {
        __OBFID = "CL_00000888";
    }
    
    static final class SwitchType
    {
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00002618";
            (SwitchType.field_178875_a = new int[MinecraftProfileTexture.Type.values().length])[MinecraftProfileTexture.Type.SKIN.ordinal()] = 1;
            SwitchType.field_178875_a[MinecraftProfileTexture.Type.CAPE.ordinal()] = 2;
        }
    }
}
