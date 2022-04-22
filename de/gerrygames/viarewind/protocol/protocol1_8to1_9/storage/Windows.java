package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class Windows extends StoredObject
{
    private HashMap types;
    private HashMap brewingItems;
    
    public Windows(final UserConnection userConnection) {
        super(userConnection);
        this.types = new HashMap();
        this.brewingItems = new HashMap();
    }
    
    public String get(final short n) {
        return this.types.get(n);
    }
    
    public void put(final short n, final String s) {
        this.types.put(n, s);
    }
    
    public void remove(final short n) {
        this.types.remove(n);
        this.brewingItems.remove(n);
    }
    
    public Item[] getBrewingItems(final short n) {
        return this.brewingItems.computeIfAbsent(n, Windows::lambda$getBrewingItems$0);
    }
    
    public static void updateBrewingStand(final UserConnection userConnection, final Item item, final short n) {
        if (item != null && item.identifier() != 377) {
            return;
        }
        final int n2 = (item == null) ? 0 : item.amount();
        final PacketWrapper create = PacketWrapper.create(45, null, userConnection);
        create.write(Type.UNSIGNED_BYTE, n);
        create.write(Type.STRING, "minecraft:brewing_stand");
        create.write(Type.STRING, "[{\"translate\":\"container.brewing\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"text\":\"§4" + n2 + " \",\"color\":\"dark_red\"},{\"translate\":\"item.blazePowder.name\",\"color\":\"dark_red\"}]");
        create.write(Type.UNSIGNED_BYTE, 420);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
        final Item[] brewingItems = ((Windows)userConnection.get(Windows.class)).getBrewingItems(n);
        while (0 < brewingItems.length) {
            final PacketWrapper create2 = PacketWrapper.create(47, null, userConnection);
            create2.write(Type.BYTE, (byte)n);
            create2.write(Type.SHORT, 0);
            create2.write(Type.ITEM, brewingItems[0]);
            PacketUtil.sendPacket(create2, Protocol1_8TO1_9.class);
            int n3 = 0;
            ++n3;
        }
    }
    
    private static Item[] lambda$getBrewingItems$0(final Short n) {
        return new Item[] { new DataItem(), new DataItem(), new DataItem(), new DataItem() };
    }
}
