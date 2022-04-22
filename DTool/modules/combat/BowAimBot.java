package DTool.modules.combat;

import DTool.modules.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.item.*;

public class BowAimBot extends Module
{
    private Entity target;
    private float velocity;
    
    public BowAimBot() {
        super("BowAimBot", 0, Category.Combat);
    }
    
    public static boolean isBot(final Entity entity) {
        return entity.getName() == null || (entity.getDisplayName() == null && Minecraft.thePlayer.getDisplayName() != null) || entity.getName().contains("§");
    }
    
    public static Entity closestEntity(final double n) {
        Entity entity = null;
        for (final Entity next : Minecraft.theWorld.loadedEntityList) {
            if (next instanceof EntityLivingBase && !(next instanceof EntityPlayerSP)) {
                final Entity entity2 = next;
                if (entity2.isDead || Minecraft.thePlayer.getDistanceToEntity(entity2) >= n || entity2.getName().contains("§")) {
                    continue;
                }
                if (entity2 instanceof EntityPlayer) {
                    if (isBot(entity2) || (entity != null && Minecraft.thePlayer.getDistanceToEntity(entity2) >= Minecraft.thePlayer.getDistanceToEntity(Objects.requireNonNull(entity)))) {
                        continue;
                    }
                    entity = entity2;
                }
                else {
                    if (entity2.getName().contains("Shop") || entity2.getDisplayName().toString().contains("Shop") || (entity != null && Minecraft.thePlayer.getDistanceToEntity(entity2) >= Minecraft.thePlayer.getDistanceToEntity(entity)) || entity2 instanceof EntityArmorStand || entity2.getDisplayName().toString().contains("§")) {
                        continue;
                    }
                    entity = entity2;
                }
            }
        }
        return entity;
    }
    
    private void aimAtTarget() {
        if (this.target == null) {
            return;
        }
        Minecraft.getMinecraft();
        this.velocity = (float)(Minecraft.thePlayer.getItemInUseDuration() / 20);
        this.velocity = (this.velocity * this.velocity + this.velocity * 2.0f) / 3.0f;
        this.velocity = 1.0f;
        if (this.velocity < 0.1 && this.target instanceof EntityLivingBase) {
            return;
        }
        if (this.velocity > 1.0f) {
            this.velocity = 1.0f;
        }
        final double posX = this.target.posX;
        Minecraft.getMinecraft();
        final double n = posX - Minecraft.thePlayer.posX;
        final double n2 = this.target.posY + this.target.getEyeHeight() - 0.15;
        Minecraft.getMinecraft();
        final double n3 = n2 - Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        final double n4 = n3 - Minecraft.thePlayer.getEyeHeight();
        final double posZ = this.target.posZ;
        Minecraft.getMinecraft();
        final double n5 = posZ - Minecraft.thePlayer.posZ;
        final float n6 = (float)(Math.atan2(n5, n) * 180.0 / 3.141592653589793) - 90.0f;
        final double sqrt = Math.sqrt(n * n + n5 * n5);
        final float n7 = 0.006f;
        final float n8 = (float)(-Math.toDegrees(Math.atan((this.velocity * this.velocity - Math.sqrt((float)(this.velocity * this.velocity * this.velocity * this.velocity - n7 * (n7 * sqrt * sqrt + 2.0 * n4 * (this.velocity * this.velocity))))) / n7 * sqrt)));
        final Minecraft mc = BowAimBot.mc;
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(n6, n8, true));
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.inventory.getCurrentItem() != null) {
                Minecraft.getMinecraft();
                if (Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed) {
                    this.target = closestEntity(Double.MAX_VALUE);
                    this.aimAtTarget();
                }
            }
        }
    }
}
