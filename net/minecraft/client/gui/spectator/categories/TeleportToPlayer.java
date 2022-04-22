package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.spectator.*;
import net.minecraft.client.gui.*;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject
{
    private static final Ordering field_178674_a;
    private final List field_178673_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001922";
        field_178674_a = Ordering.from(new Comparator() {
            private static final String __OBFID;
            
            public int func_178746_a(final NetworkPlayerInfo networkPlayerInfo, final NetworkPlayerInfo networkPlayerInfo2) {
                return ComparisonChain.start().compare(networkPlayerInfo.func_178845_a().getId(), networkPlayerInfo2.func_178845_a().getId()).result();
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.func_178746_a((NetworkPlayerInfo)o, (NetworkPlayerInfo)o2);
            }
            
            static {
                __OBFID = "CL_00001921";
            }
        });
    }
    
    public TeleportToPlayer() {
        this(TeleportToPlayer.field_178674_a.sortedCopy(Minecraft.getMinecraft().getNetHandler().func_175106_d()));
    }
    
    public TeleportToPlayer(final Collection collection) {
        this.field_178673_b = Lists.newArrayList();
        for (final NetworkPlayerInfo networkPlayerInfo : TeleportToPlayer.field_178674_a.sortedCopy(collection)) {
            if (networkPlayerInfo.getGameType() != WorldSettings.GameType.SPECTATOR) {
                this.field_178673_b.add(new PlayerMenuObject(networkPlayerInfo.func_178845_a()));
            }
        }
    }
    
    @Override
    public List func_178669_a() {
        return this.field_178673_b;
    }
    
    @Override
    public IChatComponent func_178670_b() {
        return new ChatComponentText("Select a player to teleport to");
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu spectatorMenu) {
        spectatorMenu.func_178647_a(this);
    }
    
    @Override
    public IChatComponent func_178664_z_() {
        return new ChatComponentText("Teleport to player");
    }
    
    @Override
    public void func_178663_a(final float n, final int n2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 16, 16, 256.0f, 256.0f);
    }
    
    @Override
    public boolean func_178662_A_() {
        return !this.field_178673_b.isEmpty();
    }
}
