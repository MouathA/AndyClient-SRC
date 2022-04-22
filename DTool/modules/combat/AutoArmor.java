package DTool.modules.combat;

import DTool.modules.*;
import net.minecraft.client.*;
import DTool.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.item.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class AutoArmor extends Module
{
    private int[] chestplate;
    private int[] leggings;
    private int[] boots;
    private int[] helmet;
    int delay;
    public boolean bestarmor;
    
    public AutoArmor() {
        super("AutoArmor", 0, Category.Combat);
        this.chestplate = new int[] { 311, 307, 315, 303, 299 };
        this.leggings = new int[] { 312, 308, 316, 304, 300 };
        this.boots = new int[] { 313, 309, 317, 305, 301 };
        this.helmet = new int[] { 310, 306, 314, 302, 298 };
        this.delay = 0;
        this.bestarmor = true;
    }
    
    public void AutoArmor() {
        if (this.bestarmor) {
            return;
        }
        ++this.delay;
        if (this.delay >= 10) {
            final Minecraft mc = AutoArmor.mc;
            int n2 = 0;
            if (Minecraft.thePlayer.inventory.armorInventory[0] == null) {
                int[] boots;
                while (0 < (boots = this.boots).length) {
                    final int n = boots[0];
                    if (ArmorUtils.getItem(n) != -1) {
                        ArmorUtils.getItem(n);
                        break;
                    }
                    ++n2;
                }
            }
            final Minecraft mc2 = AutoArmor.mc;
            if (Minecraft.thePlayer.inventory.armorInventory[1] == null) {
                int[] leggings;
                while (0 < (leggings = this.leggings).length) {
                    final int n3 = leggings[0];
                    if (ArmorUtils.getItem(n3) != -1) {
                        ArmorUtils.getItem(n3);
                        break;
                    }
                    ++n2;
                }
            }
            final Minecraft mc3 = AutoArmor.mc;
            if (Minecraft.thePlayer.inventory.armorInventory[2] == null) {
                int[] chestplate;
                while (0 < (chestplate = this.chestplate).length) {
                    final int n4 = chestplate[0];
                    if (ArmorUtils.getItem(n4) != -1) {
                        ArmorUtils.getItem(n4);
                        break;
                    }
                    ++n2;
                }
            }
            final Minecraft mc4 = AutoArmor.mc;
            if (Minecraft.thePlayer.inventory.armorInventory[3] == null) {
                int[] helmet;
                while (0 < (helmet = this.helmet).length) {
                    final int n5 = helmet[0];
                    if (ArmorUtils.getItem(n5) != -1) {
                        ArmorUtils.getItem(n5);
                        break;
                    }
                    ++n2;
                }
            }
            if (-1 != -1) {
                final Minecraft mc5 = AutoArmor.mc;
                final PlayerControllerMP playerController = Minecraft.playerController;
                final int n6 = 0;
                final int n7 = -1;
                final int n8 = 0;
                final int n9 = 1;
                final Minecraft mc6 = AutoArmor.mc;
                playerController.windowClick(n6, n7, n8, n9, Minecraft.thePlayer);
                this.delay = 0;
            }
        }
    }
    
    public void SwitchToBetterArmor() {
        if (!this.bestarmor) {
            return;
        }
        ++this.delay;
        if (this.delay >= 10) {
            final Minecraft mc = AutoArmor.mc;
            if (Minecraft.thePlayer.openContainer != null) {
                final Minecraft mc2 = AutoArmor.mc;
                if (Minecraft.thePlayer.openContainer.windowId != 0) {
                    return;
                }
            }
            final Minecraft mc3 = AutoArmor.mc;
            int n = 0;
            int n3 = 0;
            if (Minecraft.thePlayer.inventory.armorInventory[0] == null) {
                final int[] boots;
                n = (boots = this.boots).length;
                while (0 < 0) {
                    final int n2 = boots[0];
                    if (ArmorUtils.getItem(1) != -1) {
                        ArmorUtils.getItem(1);
                        break;
                    }
                    ++n3;
                }
            }
            if (ArmorUtils.IsBetterArmor(0, this.boots)) {}
            final Minecraft mc4 = AutoArmor.mc;
            if (Minecraft.thePlayer.inventory.armorInventory[3] == null) {
                final int[] helmet;
                n = (helmet = this.helmet).length;
                while (0 < 0) {
                    final int n4 = helmet[0];
                    if (ArmorUtils.getItem(1) != -1) {
                        ArmorUtils.getItem(1);
                        break;
                    }
                    ++n3;
                }
            }
            if (ArmorUtils.IsBetterArmor(3, this.helmet)) {}
            final Minecraft mc5 = AutoArmor.mc;
            if (Minecraft.thePlayer.inventory.armorInventory[1] == null) {
                final int[] leggings;
                n = (leggings = this.leggings).length;
                while (0 < 0) {
                    final int n5 = leggings[0];
                    if (ArmorUtils.getItem(1) != -1) {
                        ArmorUtils.getItem(1);
                        break;
                    }
                    ++n3;
                }
            }
            if (ArmorUtils.IsBetterArmor(1, this.leggings)) {}
            final Minecraft mc6 = AutoArmor.mc;
            if (Minecraft.thePlayer.inventory.armorInventory[2] == null) {
                final int[] chestplate;
                n = (chestplate = this.chestplate).length;
                while (0 < 0) {
                    final int n6 = chestplate[0];
                    if (ArmorUtils.getItem(1) != -1) {
                        ArmorUtils.getItem(1);
                        break;
                    }
                    ++n3;
                }
            }
            if (ArmorUtils.IsBetterArmor(2, this.chestplate)) {}
            final Minecraft mc7 = AutoArmor.mc;
            ItemStack[] mainInventory;
            while (0 < (mainInventory = Minecraft.thePlayer.inventory.mainInventory).length && mainInventory[0] != null) {
                ++n;
            }
            final boolean b = true && !true;
            if (6 != -1) {
                final Minecraft mc8 = AutoArmor.mc;
                final PlayerControllerMP playerController = Minecraft.playerController;
                final int n7 = 0;
                final int n8 = 6;
                final int n9 = 0;
                final int n10 = true ? 4 : 1;
                final Minecraft mc9 = AutoArmor.mc;
                playerController.windowClick(n7, n8, n9, n10, Minecraft.thePlayer);
                this.delay = 0;
            }
        }
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            this.AutoArmor();
            this.SwitchToBetterArmor();
        }
    }
}
