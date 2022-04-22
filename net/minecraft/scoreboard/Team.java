package net.minecraft.scoreboard;

import java.util.*;
import com.google.common.collect.*;

public abstract class Team
{
    private static final String __OBFID;
    
    public boolean isSameTeam(final Team team) {
        return team != null && this == team;
    }
    
    public abstract String getRegisteredName();
    
    public abstract String formatString(final String p0);
    
    public abstract boolean func_98297_h();
    
    public abstract boolean getAllowFriendlyFire();
    
    public abstract EnumVisible func_178770_i();
    
    public abstract Collection getMembershipCollection();
    
    public abstract EnumVisible func_178771_j();
    
    static {
        __OBFID = "CL_00000621";
    }
    
    public enum EnumVisible
    {
        ALWAYS("ALWAYS", 0, "ALWAYS", 0, "always", 0), 
        NEVER("NEVER", 1, "NEVER", 1, "never", 1), 
        HIDE_FOR_OTHER_TEAMS("HIDE_FOR_OTHER_TEAMS", 2, "HIDE_FOR_OTHER_TEAMS", 2, "hideForOtherTeams", 2), 
        HIDE_FOR_OWN_TEAM("HIDE_FOR_OWN_TEAM", 3, "HIDE_FOR_OWN_TEAM", 3, "hideForOwnTeam", 3);
        
        private static Map field_178828_g;
        public final String field_178830_e;
        public final int field_178827_f;
        private static final EnumVisible[] $VALUES;
        private static final String __OBFID;
        private static final EnumVisible[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001962";
            ENUM$VALUES = new EnumVisible[] { EnumVisible.ALWAYS, EnumVisible.NEVER, EnumVisible.HIDE_FOR_OTHER_TEAMS, EnumVisible.HIDE_FOR_OWN_TEAM };
            EnumVisible.field_178828_g = Maps.newHashMap();
            $VALUES = new EnumVisible[] { EnumVisible.ALWAYS, EnumVisible.NEVER, EnumVisible.HIDE_FOR_OTHER_TEAMS, EnumVisible.HIDE_FOR_OWN_TEAM };
            final EnumVisible[] values = values();
            while (0 < values.length) {
                final EnumVisible enumVisible = values[0];
                EnumVisible.field_178828_g.put(enumVisible.field_178830_e, enumVisible);
                int n = 0;
                ++n;
            }
        }
        
        public static String[] func_178825_a() {
            return (String[])EnumVisible.field_178828_g.keySet().toArray(new String[EnumVisible.field_178828_g.size()]);
        }
        
        public static EnumVisible func_178824_a(final String s) {
            return EnumVisible.field_178828_g.get(s);
        }
        
        private EnumVisible(final String s, final int n, final String s2, final int n2, final String field_178830_e, final int field_178827_f) {
            this.field_178830_e = field_178830_e;
            this.field_178827_f = field_178827_f;
        }
    }
}
