package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import de.gerrygames.viarewind.api.*;
import com.viaversion.viaversion.api.connection.*;
import de.gerrygames.viarewind.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;
import java.util.*;
import com.viaversion.viaversion.util.*;

public class Cooldown extends StoredObject implements Tickable
{
    private double attackSpeed;
    private long lastHit;
    private final ViaRewindConfig.CooldownIndicator cooldownIndicator;
    private UUID bossUUID;
    private boolean lastSend;
    
    public Cooldown(final UserConnection userConnection) {
        super(userConnection);
        this.attackSpeed = 4.0;
        this.lastHit = 0L;
        this.cooldownIndicator = ViaRewind.getConfig().getCooldownIndicator();
    }
    
    @Override
    public void tick() {
        if (!this.hasCooldown()) {
            if (this.lastSend) {
                this.hide();
                this.lastSend = false;
            }
            return;
        }
        if (((BlockPlaceDestroyTracker)this.getUser().get(BlockPlaceDestroyTracker.class)).isMining()) {
            this.lastHit = 0L;
            if (this.lastSend) {
                this.hide();
                this.lastSend = false;
            }
            return;
        }
        this.showCooldown();
        this.lastSend = true;
    }
    
    private void showCooldown() {
        if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
            this.sendTitle("", this.getTitle(), 0, 2, 5);
        }
        else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
            this.sendActionBar(this.getTitle());
        }
        else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
            this.sendBossBar((float)this.getCooldown());
        }
    }
    
    private void hide() {
        if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
            this.sendActionBar("§r");
        }
        else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
            this.hideTitle();
        }
        else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
            this.hideBossBar();
        }
    }
    
    private void hideBossBar() {
        if (this.bossUUID == null) {
            return;
        }
        final PacketWrapper create = PacketWrapper.create(12, null, this.getUser());
        create.write(Type.UUID, this.bossUUID);
        create.write(Type.VAR_INT, 1);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, false, true);
        this.bossUUID = null;
    }
    
    private void sendBossBar(final float n) {
        final PacketWrapper create = PacketWrapper.create(12, null, this.getUser());
        if (this.bossUUID == null) {
            this.bossUUID = UUID.randomUUID();
            create.write(Type.UUID, this.bossUUID);
            create.write(Type.VAR_INT, 0);
            create.write(Type.STRING, "{\"text\":\"  \"}");
            create.write(Type.FLOAT, n);
            create.write(Type.VAR_INT, 0);
            create.write(Type.VAR_INT, 0);
            create.write(Type.UNSIGNED_BYTE, 0);
        }
        else {
            create.write(Type.UUID, this.bossUUID);
            create.write(Type.VAR_INT, 2);
            create.write(Type.FLOAT, n);
        }
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, false, true);
    }
    
    private void hideTitle() {
        final PacketWrapper create = PacketWrapper.create(69, null, this.getUser());
        create.write(Type.VAR_INT, 3);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
    }
    
    private void sendTitle(final String s, final String s2, final int n, final int n2, final int n3) {
        final PacketWrapper create = PacketWrapper.create(69, null, this.getUser());
        create.write(Type.VAR_INT, 2);
        create.write(Type.INT, n);
        create.write(Type.INT, n2);
        create.write(Type.INT, n3);
        final PacketWrapper create2 = PacketWrapper.create(69, null, this.getUser());
        create2.write(Type.VAR_INT, 0);
        create2.write(Type.STRING, s);
        final PacketWrapper create3 = PacketWrapper.create(69, null, this.getUser());
        create3.write(Type.VAR_INT, 1);
        create3.write(Type.STRING, s2);
        PacketUtil.sendPacket(create2, Protocol1_8TO1_9.class);
        PacketUtil.sendPacket(create3, Protocol1_8TO1_9.class);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
    }
    
    private void sendActionBar(final String s) {
        final PacketWrapper create = PacketWrapper.create(2, null, this.getUser());
        create.write(Type.STRING, s);
        create.write(Type.BYTE, 2);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
    }
    
    public boolean hasCooldown() {
        final double restrain = this.restrain((System.currentTimeMillis() - this.lastHit) * this.attackSpeed / 1000.0, 0.0, 1.5);
        return restrain > 0.1 && restrain < 1.1;
    }
    
    public double getCooldown() {
        return this.restrain((System.currentTimeMillis() - this.lastHit) * this.attackSpeed / 1000.0, 0.0, 1.0);
    }
    
    private double restrain(final double n, final double n2, final double n3) {
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }
    
    private String getTitle() {
        final String s = (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) ? "\u25a0" : "\u02d9";
        int n = (int)Math.floor(10.0 * this.getCooldown());
        int n2 = 10 - n;
        final StringBuilder sb = new StringBuilder("§8");
        while (n-- > 0) {
            sb.append(s);
        }
        sb.append("§7");
        while (n2-- > 0) {
            sb.append(s);
        }
        return sb.toString();
    }
    
    public double getAttackSpeed() {
        return this.attackSpeed;
    }
    
    public void setAttackSpeed(final double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }
    
    public void setAttackSpeed(final double attackSpeed, final ArrayList list) {
        this.attackSpeed = attackSpeed;
        int n2 = 0;
        while (0 < list.size()) {
            if ((byte)list.get(0).getKey() == 0) {
                this.attackSpeed += (double)list.get(0).getValue();
                final int n = 0;
                --n2;
                list.remove(n);
            }
            ++n2;
        }
        while (0 < list.size()) {
            if ((byte)list.get(0).getKey() == 1) {
                this.attackSpeed += attackSpeed * (double)list.get(0).getValue();
                final int n3 = 0;
                --n2;
                list.remove(n3);
            }
            ++n2;
        }
        while (0 < list.size()) {
            if ((byte)list.get(0).getKey() == 2) {
                this.attackSpeed *= 1.0 + (double)list.get(0).getValue();
                final int n4 = 0;
                --n2;
                list.remove(n4);
            }
            ++n2;
        }
    }
    
    public void hit() {
        this.lastHit = System.currentTimeMillis();
    }
    
    public void setLastHit(final long lastHit) {
        this.lastHit = lastHit;
    }
}
