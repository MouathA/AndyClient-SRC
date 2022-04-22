package net.minecraft.entity.monster;

import net.minecraft.entity.passive.*;
import com.google.common.base.*;
import net.minecraft.entity.*;

public interface IMob extends IAnimals
{
    public static final Predicate mobSelector = new Predicate() {
        private static final String __OBFID;
        
        public boolean func_179983_a(final Entity entity) {
            return entity instanceof IMob;
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.func_179983_a((Entity)o);
        }
        
        static {
            __OBFID = "CL_00001688";
        }
    };
    public static final Predicate field_175450_e = new Predicate() {
        private static final String __OBFID;
        
        public boolean func_179982_a(final Entity entity) {
            return entity instanceof IMob && !entity.isInvisible();
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.func_179982_a((Entity)o);
        }
        
        static {
            __OBFID = "CL_00002218";
        }
    };
}
