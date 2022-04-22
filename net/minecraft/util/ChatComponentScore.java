package net.minecraft.util;

import net.minecraft.server.*;
import net.minecraft.scoreboard.*;
import java.util.*;

public class ChatComponentScore extends ChatComponentStyle
{
    private final String field_179999_b;
    private final String field_180000_c;
    private String field_179998_d;
    private static final String __OBFID;
    
    public ChatComponentScore(final String field_179999_b, final String field_180000_c) {
        this.field_179998_d = "";
        this.field_179999_b = field_179999_b;
        this.field_180000_c = field_180000_c;
    }
    
    public String func_179995_g() {
        return this.field_179999_b;
    }
    
    public String func_179994_h() {
        return this.field_180000_c;
    }
    
    public void func_179997_b(final String field_179998_d) {
        this.field_179998_d = field_179998_d;
    }
    
    @Override
    public String getUnformattedTextForChat() {
        final MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.func_175578_N() && StringUtils.isNullOrEmpty(this.field_179998_d)) {
            final Scoreboard scoreboard = server.worldServerForDimension(0).getScoreboard();
            final ScoreObjective objective = scoreboard.getObjective(this.field_180000_c);
            if (scoreboard.func_178819_b(this.field_179999_b, objective)) {
                this.func_179997_b(String.format("%d", scoreboard.getValueFromObjective(this.field_179999_b, objective).getScorePoints()));
            }
            else {
                this.field_179998_d = "";
            }
        }
        return this.field_179998_d;
    }
    
    public ChatComponentScore func_179996_i() {
        final ChatComponentScore chatComponentScore = new ChatComponentScore(this.field_179999_b, this.field_180000_c);
        chatComponentScore.func_179997_b(this.field_179998_d);
        chatComponentScore.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        while (iterator.hasNext()) {
            chatComponentScore.appendSibling(iterator.next().createCopy());
        }
        return chatComponentScore;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatComponentScore)) {
            return false;
        }
        final ChatComponentScore chatComponentScore = (ChatComponentScore)o;
        return this.field_179999_b.equals(chatComponentScore.field_179999_b) && this.field_180000_c.equals(chatComponentScore.field_180000_c) && super.equals(o);
    }
    
    @Override
    public String toString() {
        return "ScoreComponent{name='" + this.field_179999_b + '\'' + "objective='" + this.field_180000_c + '\'' + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.func_179996_i();
    }
    
    static {
        __OBFID = "CL_00002309";
    }
}
