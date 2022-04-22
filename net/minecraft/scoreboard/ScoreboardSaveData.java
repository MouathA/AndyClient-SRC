package net.minecraft.scoreboard;

import net.minecraft.world.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.util.*;

public class ScoreboardSaveData extends WorldSavedData
{
    private static final Logger logger;
    private Scoreboard theScoreboard;
    private NBTTagCompound field_96506_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000620";
        logger = LogManager.getLogger();
    }
    
    public ScoreboardSaveData() {
        this("scoreboard");
    }
    
    public ScoreboardSaveData(final String s) {
        super(s);
    }
    
    public void func_96499_a(final Scoreboard theScoreboard) {
        this.theScoreboard = theScoreboard;
        if (this.field_96506_b != null) {
            this.readFromNBT(this.field_96506_b);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound field_96506_b) {
        if (this.theScoreboard == null) {
            this.field_96506_b = field_96506_b;
        }
        else {
            this.func_96501_b(field_96506_b.getTagList("Objectives", 10));
            this.func_96500_c(field_96506_b.getTagList("PlayerScores", 10));
            if (field_96506_b.hasKey("DisplaySlots", 10)) {
                this.func_96504_c(field_96506_b.getCompoundTag("DisplaySlots"));
            }
            if (field_96506_b.hasKey("Teams", 9)) {
                this.func_96498_a(field_96506_b.getTagList("Teams", 10));
            }
        }
    }
    
    protected void func_96498_a(final NBTTagList list) {
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final ScorePlayerTeam team = this.theScoreboard.createTeam(compoundTag.getString("Name"));
            team.setTeamName(compoundTag.getString("DisplayName"));
            if (compoundTag.hasKey("TeamColor", 8)) {
                team.func_178774_a(EnumChatFormatting.getValueByName(compoundTag.getString("TeamColor")));
            }
            team.setNamePrefix(compoundTag.getString("Prefix"));
            team.setNameSuffix(compoundTag.getString("Suffix"));
            if (compoundTag.hasKey("AllowFriendlyFire", 99)) {
                team.setAllowFriendlyFire(compoundTag.getBoolean("AllowFriendlyFire"));
            }
            if (compoundTag.hasKey("SeeFriendlyInvisibles", 99)) {
                team.setSeeFriendlyInvisiblesEnabled(compoundTag.getBoolean("SeeFriendlyInvisibles"));
            }
            if (compoundTag.hasKey("NameTagVisibility", 8)) {
                final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(compoundTag.getString("NameTagVisibility"));
                if (func_178824_a != null) {
                    team.func_178772_a(func_178824_a);
                }
            }
            if (compoundTag.hasKey("DeathMessageVisibility", 8)) {
                final Team.EnumVisible func_178824_a2 = Team.EnumVisible.func_178824_a(compoundTag.getString("DeathMessageVisibility"));
                if (func_178824_a2 != null) {
                    team.func_178773_b(func_178824_a2);
                }
            }
            this.func_96502_a(team, compoundTag.getTagList("Players", 8));
            int n = 0;
            ++n;
        }
    }
    
    protected void func_96502_a(final ScorePlayerTeam scorePlayerTeam, final NBTTagList list) {
        while (0 < list.tagCount()) {
            this.theScoreboard.func_151392_a(list.getStringTagAt(0), scorePlayerTeam.getRegisteredName());
            int n = 0;
            ++n;
        }
    }
    
    protected void func_96504_c(final NBTTagCompound nbtTagCompound) {
    }
    
    protected void func_96501_b(final NBTTagList list) {
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final IScoreObjectiveCriteria scoreObjectiveCriteria = IScoreObjectiveCriteria.INSTANCES.get(compoundTag.getString("CriteriaName"));
            if (scoreObjectiveCriteria != null) {
                final ScoreObjective addScoreObjective = this.theScoreboard.addScoreObjective(compoundTag.getString("Name"), scoreObjectiveCriteria);
                addScoreObjective.setDisplayName(compoundTag.getString("DisplayName"));
                addScoreObjective.func_178767_a(IScoreObjectiveCriteria.EnumRenderType.func_178795_a(compoundTag.getString("RenderType")));
            }
            int n = 0;
            ++n;
        }
    }
    
    protected void func_96500_c(final NBTTagList list) {
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final Score valueFromObjective = this.theScoreboard.getValueFromObjective(compoundTag.getString("Name"), this.theScoreboard.getObjective(compoundTag.getString("Objective")));
            valueFromObjective.setScorePoints(compoundTag.getInteger("Score"));
            if (compoundTag.hasKey("Locked")) {
                valueFromObjective.func_178815_a(compoundTag.getBoolean("Locked"));
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        if (this.theScoreboard == null) {
            ScoreboardSaveData.logger.warn("Tried to save scoreboard without having a scoreboard...");
        }
        else {
            nbtTagCompound.setTag("Objectives", this.func_96505_b());
            nbtTagCompound.setTag("PlayerScores", this.func_96503_e());
            nbtTagCompound.setTag("Teams", this.func_96496_a());
            this.func_96497_d(nbtTagCompound);
        }
    }
    
    protected NBTTagList func_96496_a() {
        final NBTTagList list = new NBTTagList();
        for (final ScorePlayerTeam scorePlayerTeam : this.theScoreboard.getTeams()) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setString("Name", scorePlayerTeam.getRegisteredName());
            nbtTagCompound.setString("DisplayName", scorePlayerTeam.func_96669_c());
            if (scorePlayerTeam.func_178775_l().func_175746_b() >= 0) {
                nbtTagCompound.setString("TeamColor", scorePlayerTeam.func_178775_l().getFriendlyName());
            }
            nbtTagCompound.setString("Prefix", scorePlayerTeam.getColorPrefix());
            nbtTagCompound.setString("Suffix", scorePlayerTeam.getColorSuffix());
            nbtTagCompound.setBoolean("AllowFriendlyFire", scorePlayerTeam.getAllowFriendlyFire());
            nbtTagCompound.setBoolean("SeeFriendlyInvisibles", scorePlayerTeam.func_98297_h());
            nbtTagCompound.setString("NameTagVisibility", scorePlayerTeam.func_178770_i().field_178830_e);
            nbtTagCompound.setString("DeathMessageVisibility", scorePlayerTeam.func_178771_j().field_178830_e);
            final NBTTagList list2 = new NBTTagList();
            final Iterator iterator2 = scorePlayerTeam.getMembershipCollection().iterator();
            while (iterator2.hasNext()) {
                list2.appendTag(new NBTTagString(iterator2.next()));
            }
            nbtTagCompound.setTag("Players", list2);
            list.appendTag(nbtTagCompound);
        }
        return list;
    }
    
    protected void func_96497_d(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setTag("DisplaySlots", new NBTTagCompound());
    }
    
    protected NBTTagList func_96505_b() {
        final NBTTagList list = new NBTTagList();
        for (final ScoreObjective scoreObjective : this.theScoreboard.getScoreObjectives()) {
            if (scoreObjective.getCriteria() != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString("Name", scoreObjective.getName());
                nbtTagCompound.setString("CriteriaName", scoreObjective.getCriteria().getName());
                nbtTagCompound.setString("DisplayName", scoreObjective.getDisplayName());
                nbtTagCompound.setString("RenderType", scoreObjective.func_178766_e().func_178796_a());
                list.appendTag(nbtTagCompound);
            }
        }
        return list;
    }
    
    protected NBTTagList func_96503_e() {
        final NBTTagList list = new NBTTagList();
        for (final Score score : this.theScoreboard.func_96528_e()) {
            if (score.getObjective() != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString("Name", score.getPlayerName());
                nbtTagCompound.setString("Objective", score.getObjective().getName());
                nbtTagCompound.setInteger("Score", score.getScorePoints());
                nbtTagCompound.setBoolean("Locked", score.func_178816_g());
                list.appendTag(nbtTagCompound);
            }
        }
        return list;
    }
}
