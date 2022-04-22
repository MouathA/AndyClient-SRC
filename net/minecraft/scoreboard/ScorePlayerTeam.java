package net.minecraft.scoreboard;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class ScorePlayerTeam extends Team
{
    private final Scoreboard theScoreboard;
    private final String field_96675_b;
    private final Set membershipSet;
    private String teamNameSPT;
    private String namePrefixSPT;
    private String colorSuffix;
    private boolean allowFriendlyFire;
    private boolean canSeeFriendlyInvisibles;
    private EnumVisible field_178778_i;
    private EnumVisible field_178776_j;
    private EnumChatFormatting field_178777_k;
    private static final String __OBFID;
    
    public ScorePlayerTeam(final Scoreboard theScoreboard, final String s) {
        this.membershipSet = Sets.newHashSet();
        this.namePrefixSPT = "";
        this.colorSuffix = "";
        this.allowFriendlyFire = true;
        this.canSeeFriendlyInvisibles = true;
        this.field_178778_i = EnumVisible.ALWAYS;
        this.field_178776_j = EnumVisible.ALWAYS;
        this.field_178777_k = EnumChatFormatting.RESET;
        this.theScoreboard = theScoreboard;
        this.field_96675_b = s;
        this.teamNameSPT = s;
    }
    
    @Override
    public String getRegisteredName() {
        return this.field_96675_b;
    }
    
    public String func_96669_c() {
        return this.teamNameSPT;
    }
    
    public void setTeamName(final String teamNameSPT) {
        if (teamNameSPT == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.teamNameSPT = teamNameSPT;
        this.theScoreboard.broadcastTeamRemoved(this);
    }
    
    @Override
    public Collection getMembershipCollection() {
        return this.membershipSet;
    }
    
    public String getColorPrefix() {
        return this.namePrefixSPT;
    }
    
    public void setNamePrefix(final String namePrefixSPT) {
        if (namePrefixSPT == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        this.namePrefixSPT = namePrefixSPT;
        this.theScoreboard.broadcastTeamRemoved(this);
    }
    
    public String getColorSuffix() {
        return this.colorSuffix;
    }
    
    public void setNameSuffix(final String colorSuffix) {
        this.colorSuffix = colorSuffix;
        this.theScoreboard.broadcastTeamRemoved(this);
    }
    
    @Override
    public String formatString(final String s) {
        return String.valueOf(this.getColorPrefix()) + s + this.getColorSuffix();
    }
    
    public static String formatPlayerName(final Team team, final String s) {
        return (team == null) ? s : team.formatString(s);
    }
    
    @Override
    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }
    
    public void setAllowFriendlyFire(final boolean allowFriendlyFire) {
        this.allowFriendlyFire = allowFriendlyFire;
        this.theScoreboard.broadcastTeamRemoved(this);
    }
    
    @Override
    public boolean func_98297_h() {
        return this.canSeeFriendlyInvisibles;
    }
    
    public void setSeeFriendlyInvisiblesEnabled(final boolean canSeeFriendlyInvisibles) {
        this.canSeeFriendlyInvisibles = canSeeFriendlyInvisibles;
        this.theScoreboard.broadcastTeamRemoved(this);
    }
    
    @Override
    public EnumVisible func_178770_i() {
        return this.field_178778_i;
    }
    
    @Override
    public EnumVisible func_178771_j() {
        return this.field_178776_j;
    }
    
    public void func_178772_a(final EnumVisible field_178778_i) {
        this.field_178778_i = field_178778_i;
        this.theScoreboard.broadcastTeamRemoved(this);
    }
    
    public void func_178773_b(final EnumVisible field_178776_j) {
        this.field_178776_j = field_178776_j;
        this.theScoreboard.broadcastTeamRemoved(this);
    }
    
    public int func_98299_i() {
        if (this.getAllowFriendlyFire()) {}
        if (this.func_98297_h()) {}
        return 0;
    }
    
    public void func_98298_a(final int n) {
        this.setAllowFriendlyFire((n & 0x1) > 0);
        this.setSeeFriendlyInvisiblesEnabled((n & 0x2) > 0);
    }
    
    public void func_178774_a(final EnumChatFormatting field_178777_k) {
        this.field_178777_k = field_178777_k;
    }
    
    public EnumChatFormatting func_178775_l() {
        return this.field_178777_k;
    }
    
    static {
        __OBFID = "CL_00000616";
    }
}
