package net.minecraft.scoreboard;

import net.minecraft.server.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class ServerScoreboard extends Scoreboard
{
    private final MinecraftServer scoreboardMCServer;
    private final Set field_96553_b;
    private ScoreboardSaveData field_96554_c;
    private static final String __OBFID;
    
    public ServerScoreboard(final MinecraftServer scoreboardMCServer) {
        this.field_96553_b = Sets.newHashSet();
        this.scoreboardMCServer = scoreboardMCServer;
    }
    
    @Override
    public void func_96536_a(final Score score) {
        super.func_96536_a(score);
        if (this.field_96553_b.contains(score.getObjective())) {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(score));
        }
        this.func_96551_b();
    }
    
    @Override
    public void func_96516_a(final String s) {
        super.func_96516_a(s);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(s));
        this.func_96551_b();
    }
    
    @Override
    public void func_178820_a(final String s, final ScoreObjective scoreObjective) {
        super.func_178820_a(s, scoreObjective);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(s, scoreObjective));
        this.func_96551_b();
    }
    
    @Override
    public void setObjectiveInDisplaySlot(final int n, final ScoreObjective scoreObjective) {
        final ScoreObjective objectiveInDisplaySlot = this.getObjectiveInDisplaySlot(n);
        super.setObjectiveInDisplaySlot(n, scoreObjective);
        if (objectiveInDisplaySlot != scoreObjective && objectiveInDisplaySlot != null) {
            if (this.func_96552_h(objectiveInDisplaySlot) > 0) {
                this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(n, scoreObjective));
            }
            else {
                this.func_96546_g(objectiveInDisplaySlot);
            }
        }
        if (scoreObjective != null) {
            if (this.field_96553_b.contains(scoreObjective)) {
                this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(n, scoreObjective));
            }
            else {
                this.func_96549_e(scoreObjective);
            }
        }
        this.func_96551_b();
    }
    
    @Override
    public boolean func_151392_a(final String s, final String s2) {
        if (super.func_151392_a(s, s2)) {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(this.getTeam(s2), Arrays.asList(s), 3));
            this.func_96551_b();
            return true;
        }
        return false;
    }
    
    @Override
    public void removePlayerFromTeam(final String s, final ScorePlayerTeam scorePlayerTeam) {
        super.removePlayerFromTeam(s, scorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, Arrays.asList(s), 4));
        this.func_96551_b();
    }
    
    @Override
    public void func_96522_a(final ScoreObjective scoreObjective) {
        super.func_96522_a(scoreObjective);
        this.func_96551_b();
    }
    
    @Override
    public void func_96532_b(final ScoreObjective scoreObjective) {
        super.func_96532_b(scoreObjective);
        if (this.field_96553_b.contains(scoreObjective)) {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3BPacketScoreboardObjective(scoreObjective, 2));
        }
        this.func_96551_b();
    }
    
    @Override
    public void func_96533_c(final ScoreObjective scoreObjective) {
        super.func_96533_c(scoreObjective);
        if (this.field_96553_b.contains(scoreObjective)) {
            this.func_96546_g(scoreObjective);
        }
        this.func_96551_b();
    }
    
    @Override
    public void broadcastTeamCreated(final ScorePlayerTeam scorePlayerTeam) {
        super.broadcastTeamCreated(scorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 0));
        this.func_96551_b();
    }
    
    @Override
    public void broadcastTeamRemoved(final ScorePlayerTeam scorePlayerTeam) {
        super.broadcastTeamRemoved(scorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 2));
        this.func_96551_b();
    }
    
    @Override
    public void func_96513_c(final ScorePlayerTeam scorePlayerTeam) {
        super.func_96513_c(scorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 1));
        this.func_96551_b();
    }
    
    public void func_96547_a(final ScoreboardSaveData field_96554_c) {
        this.field_96554_c = field_96554_c;
    }
    
    protected void func_96551_b() {
        if (this.field_96554_c != null) {
            this.field_96554_c.markDirty();
        }
    }
    
    public List func_96550_d(final ScoreObjective scoreObjective) {
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new S3BPacketScoreboardObjective(scoreObjective, 0));
        while (0 < 19) {
            if (this.getObjectiveInDisplaySlot(0) == scoreObjective) {
                arrayList.add(new S3DPacketDisplayScoreboard(0, scoreObjective));
            }
            int n = 0;
            ++n;
        }
        final Iterator<Score> iterator = (Iterator<Score>)this.getSortedScores(scoreObjective).iterator();
        while (iterator.hasNext()) {
            arrayList.add(new S3CPacketUpdateScore(iterator.next()));
        }
        return arrayList;
    }
    
    public void func_96549_e(final ScoreObjective scoreObjective) {
        final List func_96550_d = this.func_96550_d(scoreObjective);
        for (final EntityPlayerMP entityPlayerMP : this.scoreboardMCServer.getConfigurationManager().playerEntityList) {
            final Iterator<Packet> iterator2 = func_96550_d.iterator();
            while (iterator2.hasNext()) {
                entityPlayerMP.playerNetServerHandler.sendPacket(iterator2.next());
            }
        }
        this.field_96553_b.add(scoreObjective);
    }
    
    public List func_96548_f(final ScoreObjective scoreObjective) {
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new S3BPacketScoreboardObjective(scoreObjective, 1));
        while (0 < 19) {
            if (this.getObjectiveInDisplaySlot(0) == scoreObjective) {
                arrayList.add(new S3DPacketDisplayScoreboard(0, scoreObjective));
            }
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    public void func_96546_g(final ScoreObjective scoreObjective) {
        final List func_96548_f = this.func_96548_f(scoreObjective);
        for (final EntityPlayerMP entityPlayerMP : this.scoreboardMCServer.getConfigurationManager().playerEntityList) {
            final Iterator<Packet> iterator2 = func_96548_f.iterator();
            while (iterator2.hasNext()) {
                entityPlayerMP.playerNetServerHandler.sendPacket(iterator2.next());
            }
        }
        this.field_96553_b.remove(scoreObjective);
    }
    
    public int func_96552_h(final ScoreObjective scoreObjective) {
        while (0 < 19) {
            if (this.getObjectiveInDisplaySlot(0) == scoreObjective) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    static {
        __OBFID = "CL_00001424";
    }
}
