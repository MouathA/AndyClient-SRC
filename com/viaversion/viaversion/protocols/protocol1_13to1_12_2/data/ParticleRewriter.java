package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.type.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.*;
import java.util.*;

public class ParticleRewriter
{
    private static final List particles;
    
    public static Particle rewriteParticle(final int n, final Integer[] array) {
        if (n >= ParticleRewriter.particles.size()) {
            Via.getPlatform().getLogger().severe("Failed to transform particles with id " + n + " and data " + Arrays.toString(array));
            return null;
        }
        final NewParticle newParticle = ParticleRewriter.particles.get(n);
        return newParticle.handle(new Particle(newParticle.getId()), array);
    }
    
    private static void add(final int n) {
        ParticleRewriter.particles.add(new NewParticle(n, null));
    }
    
    private static void add(final int n, final ParticleDataHandler particleDataHandler) {
        ParticleRewriter.particles.add(new NewParticle(n, particleDataHandler));
    }
    
    private static ParticleDataHandler reddustHandler() {
        return new ParticleDataHandler() {
            @Override
            public Particle handler(final Particle particle, final Integer[] array) {
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, ParticleRewriter.access$000() ? 1.0f : 0.0f));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, 0.0f));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, ParticleRewriter.access$000() ? 1.0f : 0.0f));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, 1.0f));
                return particle;
            }
        };
    }
    
    private static boolean randomBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }
    
    private static ParticleDataHandler iconcrackHandler() {
        return new ParticleDataHandler() {
            @Override
            public Particle handler(final Particle particle, final Integer[] array) {
                DataItem dataItem;
                if (array.length == 1) {
                    dataItem = new DataItem(array[0], (byte)1, (short)0, null);
                }
                else {
                    if (array.length != 2) {
                        return particle;
                    }
                    dataItem = new DataItem(array[0], (byte)1, array[1].shortValue(), null);
                }
                ((Protocol1_13To1_12_2)Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class)).getItemRewriter().handleItemToClient(dataItem);
                particle.getArguments().add(new Particle.ParticleData(Type.FLAT_ITEM, dataItem));
                return particle;
            }
        };
    }
    
    private static ParticleDataHandler blockHandler() {
        return new ParticleDataHandler() {
            @Override
            public Particle handler(final Particle particle, final Integer[] array) {
                final int intValue = array[0];
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, WorldPackets.toNewId((intValue & 0xFFF) << 4 | (intValue >> 12 & 0xF))));
                return particle;
            }
        };
    }
    
    static boolean access$000() {
        return randomBool();
    }
    
    static {
        particles = new ArrayList();
        add(34);
        add(19);
        add(18);
        add(21);
        add(4);
        add(43);
        add(22);
        add(42);
        add(42);
        add(6);
        add(14);
        add(37);
        add(30);
        add(12);
        add(26);
        add(17);
        add(0);
        add(44);
        add(10);
        add(9);
        add(1);
        add(24);
        add(32);
        add(33);
        add(35);
        add(15);
        add(23);
        add(31);
        add(-1);
        add(5);
        add(11, reddustHandler());
        add(29);
        add(34);
        add(28);
        add(25);
        add(2);
        add(27, iconcrackHandler());
        add(3, blockHandler());
        add(3, blockHandler());
        add(36);
        add(-1);
        add(13);
        add(8);
        add(16);
        add(7);
        add(40);
        add(20, blockHandler());
        add(41);
        add(38);
    }
    
    private static class NewParticle
    {
        private final int id;
        private final ParticleDataHandler handler;
        
        public NewParticle(final int id, final ParticleDataHandler handler) {
            this.id = id;
            this.handler = handler;
        }
        
        public Particle handle(final Particle particle, final Integer[] array) {
            if (this.handler != null) {
                return this.handler.handler(particle, array);
            }
            return particle;
        }
        
        public int getId() {
            return this.id;
        }
        
        public ParticleDataHandler getHandler() {
            return this.handler;
        }
    }
    
    interface ParticleDataHandler
    {
        Particle handler(final Particle p0, final Integer[] p1);
    }
}
