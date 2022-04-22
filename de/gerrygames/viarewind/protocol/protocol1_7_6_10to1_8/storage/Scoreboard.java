package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import de.gerrygames.viarewind.utils.*;
import java.util.*;

public class Scoreboard extends StoredObject
{
    private HashMap teams;
    private HashSet objectives;
    private HashMap scoreTeams;
    private HashMap teamColors;
    private HashSet scoreTeamNames;
    private String colorIndependentSidebar;
    private HashMap colorDependentSidebar;
    
    public Scoreboard(final UserConnection userConnection) {
        super(userConnection);
        this.teams = new HashMap();
        this.objectives = new HashSet();
        this.scoreTeams = new HashMap();
        this.teamColors = new HashMap();
        this.scoreTeamNames = new HashSet();
        this.colorDependentSidebar = new HashMap();
    }
    
    public void addPlayerToTeam(final String s, final String s2) {
        this.teams.computeIfAbsent(s2, Scoreboard::lambda$addPlayerToTeam$0).add(s);
    }
    
    public void setTeamColor(final String s, final Byte b) {
        this.teamColors.put(s, b);
    }
    
    public Optional getTeamColor(final String s) {
        return Optional.ofNullable(this.teamColors.get(s));
    }
    
    public void addTeam(final String s) {
        this.teams.computeIfAbsent(s, Scoreboard::lambda$addTeam$1);
    }
    
    public void removeTeam(final String s) {
        this.teams.remove(s);
        this.scoreTeams.remove(s);
        this.teamColors.remove(s);
    }
    
    public boolean teamExists(final String s) {
        return this.teams.containsKey(s);
    }
    
    public void removePlayerFromTeam(final String s, final String s2) {
        final List list = this.teams.get(s2);
        if (list != null) {
            list.remove(s);
        }
    }
    
    public boolean isPlayerInTeam(final String s, final String s2) {
        final List list = this.teams.get(s2);
        return list != null && list.contains(s);
    }
    
    public boolean isPlayerInTeam(final String s) {
        final Iterator<List> iterator = this.teams.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().contains(s)) {
                return true;
            }
        }
        return false;
    }
    
    public Optional getPlayerTeamColor(final String s) {
        final Optional team = this.getTeam(s);
        return team.isPresent() ? this.getTeamColor(team.get()) : Optional.empty();
    }
    
    public Optional getTeam(final String s) {
        for (final Map.Entry<K, List> entry : this.teams.entrySet()) {
            if (entry.getValue().contains(s)) {
                return Optional.of((String)entry.getKey());
            }
        }
        return Optional.empty();
    }
    
    public void addObjective(final String s) {
        this.objectives.add(s);
    }
    
    public void removeObjective(final String s) {
        this.objectives.remove(s);
        this.colorDependentSidebar.values().remove(s);
        if (s.equals(this.colorIndependentSidebar)) {
            this.colorIndependentSidebar = null;
        }
    }
    
    public boolean objectiveExists(final String s) {
        return this.objectives.contains(s);
    }
    
    public String sendTeamForScore(final String s) {
        if (s.length() <= 16) {
            return s;
        }
        if (this.scoreTeams.containsKey(s)) {
            return ScoreTeam.access$000(this.scoreTeams.get(s));
        }
        int n;
        String s2;
        int n2 = 0;
        for (n = Math.min(16, s.length() - 16), s2 = s.substring(n, n + 16); this.scoreTeamNames.contains(s2) || this.teams.containsKey(s2); s2 = s.substring(n, n + 16)) {
            --n;
            while (s.length() - 16 - n > 16) {
                --n2;
                n = Math.min(16, s.length() - 16);
            }
        }
        final String substring = s.substring(0, n);
        final String s3 = (n + 16 >= s.length()) ? "" : s.substring(n + 16, s.length());
        this.scoreTeams.put(s, new ScoreTeam(s2, substring, s3));
        this.scoreTeamNames.add(s2);
        final PacketWrapper create = PacketWrapper.create(62, null, this.getUser());
        create.write(Type.STRING, s2);
        create.write(Type.BYTE, 0);
        create.write(Type.STRING, "ViaRewind");
        create.write(Type.STRING, substring);
        create.write(Type.STRING, s3);
        create.write(Type.BYTE, 0);
        create.write(Type.SHORT, 1);
        create.write(Type.STRING, s2);
        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
        return s2;
    }
    
    public String removeTeamForScore(final String s) {
        final ScoreTeam scoreTeam = this.scoreTeams.remove(s);
        if (scoreTeam == null) {
            return s;
        }
        this.scoreTeamNames.remove(ScoreTeam.access$000(scoreTeam));
        final PacketWrapper create = PacketWrapper.create(62, null, this.getUser());
        create.write(Type.STRING, ScoreTeam.access$000(scoreTeam));
        create.write(Type.BYTE, 1);
        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
        return ScoreTeam.access$000(scoreTeam);
    }
    
    public String getColorIndependentSidebar() {
        return this.colorIndependentSidebar;
    }
    
    public HashMap getColorDependentSidebar() {
        return this.colorDependentSidebar;
    }
    
    public void setColorIndependentSidebar(final String colorIndependentSidebar) {
        this.colorIndependentSidebar = colorIndependentSidebar;
    }
    
    private static List lambda$addTeam$1(final String s) {
        return new ArrayList();
    }
    
    private static List lambda$addPlayerToTeam$0(final String s) {
        return new ArrayList();
    }
    
    private class ScoreTeam
    {
        private String prefix;
        private String suffix;
        private String name;
        final Scoreboard this$0;
        
        public ScoreTeam(final Scoreboard this$0, final String name, final String prefix, final String suffix) {
            this.this$0 = this$0;
            this.prefix = prefix;
            this.suffix = suffix;
            this.name = name;
        }
        
        static String access$000(final ScoreTeam scoreTeam) {
            return scoreTeam.name;
        }
    }
}
