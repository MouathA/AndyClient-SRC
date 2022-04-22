package net.minecraft.scoreboard;

import java.util.*;

public class Score
{
    public static final Comparator scoreComparator;
    private final Scoreboard theScoreboard;
    private final ScoreObjective theScoreObjective;
    private final String scorePlayerName;
    private int scorePoints;
    private boolean field_178817_f;
    private boolean field_178818_g;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000617";
        scoreComparator = new Comparator() {
            private static final String __OBFID;
            
            public int compare(final Score score, final Score score2) {
                return (score.getScorePoints() > score2.getScorePoints()) ? 1 : ((score.getScorePoints() < score2.getScorePoints()) ? -1 : score2.getPlayerName().compareToIgnoreCase(score.getPlayerName()));
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((Score)o, (Score)o2);
            }
            
            static {
                __OBFID = "CL_00000618";
            }
        };
    }
    
    public Score(final Scoreboard theScoreboard, final ScoreObjective theScoreObjective, final String scorePlayerName) {
        this.theScoreboard = theScoreboard;
        this.theScoreObjective = theScoreObjective;
        this.scorePlayerName = scorePlayerName;
        this.field_178818_g = true;
    }
    
    public void increseScore(final int n) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.setScorePoints(this.getScorePoints() + n);
    }
    
    public void decreaseScore(final int n) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.setScorePoints(this.getScorePoints() - n);
    }
    
    public void func_96648_a() {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.increseScore(1);
    }
    
    public int getScorePoints() {
        return this.scorePoints;
    }
    
    public void setScorePoints(final int scorePoints) {
        final int scorePoints2 = this.scorePoints;
        this.scorePoints = scorePoints;
        if (scorePoints2 != scorePoints || this.field_178818_g) {
            this.field_178818_g = false;
            this.getScoreScoreboard().func_96536_a(this);
        }
    }
    
    public ScoreObjective getObjective() {
        return this.theScoreObjective;
    }
    
    public String getPlayerName() {
        return this.scorePlayerName;
    }
    
    public Scoreboard getScoreScoreboard() {
        return this.theScoreboard;
    }
    
    public boolean func_178816_g() {
        return this.field_178817_f;
    }
    
    public void func_178815_a(final boolean field_178817_f) {
        this.field_178817_f = field_178817_f;
    }
    
    public void func_96651_a(final List list) {
        this.setScorePoints(this.theScoreObjective.getCriteria().func_96635_a(list));
    }
}
