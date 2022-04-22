package net.minecraft.scoreboard;

public class ScoreObjective
{
    private final Scoreboard theScoreboard;
    private final String name;
    private final IScoreObjectiveCriteria objectiveCriteria;
    private IScoreObjectiveCriteria.EnumRenderType field_178768_d;
    private String displayName;
    private static final String __OBFID;
    
    public ScoreObjective(final Scoreboard theScoreboard, final String s, final IScoreObjectiveCriteria objectiveCriteria) {
        this.theScoreboard = theScoreboard;
        this.name = s;
        this.objectiveCriteria = objectiveCriteria;
        this.displayName = s;
        this.field_178768_d = objectiveCriteria.func_178790_c();
    }
    
    public Scoreboard getScoreboard() {
        return this.theScoreboard;
    }
    
    public String getName() {
        return this.name;
    }
    
    public IScoreObjectiveCriteria getCriteria() {
        return this.objectiveCriteria;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
        this.theScoreboard.func_96532_b(this);
    }
    
    public IScoreObjectiveCriteria.EnumRenderType func_178766_e() {
        return this.field_178768_d;
    }
    
    public void func_178767_a(final IScoreObjectiveCriteria.EnumRenderType field_178768_d) {
        this.field_178768_d = field_178768_d;
        this.theScoreboard.func_96532_b(this);
    }
    
    static {
        __OBFID = "CL_00000614";
    }
}
