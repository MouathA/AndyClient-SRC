package net.minecraft.scoreboard;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class Scoreboard
{
    private final Map scoreObjectives;
    private final Map scoreObjectiveCriterias;
    private final Map field_96544_c;
    private final ScoreObjective[] objectiveDisplaySlots;
    private final Map teams;
    private final Map teamMemberships;
    private static String[] field_178823_g;
    private static final String __OBFID;
    private static final String[] lIlIIlIlIlllIlll;
    private static String[] lIlIIlIlIlllllII;
    
    static {
        llllIIllllIlIlII();
        llllIIllllIlIIll();
        __OBFID = Scoreboard.lIlIIlIlIlllIlll[0];
        Scoreboard.field_178823_g = null;
    }
    
    public Scoreboard() {
        this.scoreObjectives = Maps.newHashMap();
        this.scoreObjectiveCriterias = Maps.newHashMap();
        this.field_96544_c = Maps.newHashMap();
        this.objectiveDisplaySlots = new ScoreObjective[19];
        this.teams = Maps.newHashMap();
        this.teamMemberships = Maps.newHashMap();
    }
    
    public ScoreObjective getObjective(final String s) {
        return this.scoreObjectives.get(s);
    }
    
    public ScoreObjective addScoreObjective(final String s, final IScoreObjectiveCriteria scoreObjectiveCriteria) {
        if (this.getObjective(s) != null) {
            throw new IllegalArgumentException(Scoreboard.lIlIIlIlIlllIlll[1] + s + Scoreboard.lIlIIlIlIlllIlll[2]);
        }
        final ScoreObjective scoreObjective = new ScoreObjective(this, s, scoreObjectiveCriteria);
        List<ScoreObjective> arrayList = this.scoreObjectiveCriterias.get(scoreObjectiveCriteria);
        if (arrayList == null) {
            arrayList = (List<ScoreObjective>)Lists.newArrayList();
            this.scoreObjectiveCriterias.put(scoreObjectiveCriteria, arrayList);
        }
        arrayList.add(scoreObjective);
        this.scoreObjectives.put(s, scoreObjective);
        this.func_96522_a(scoreObjective);
        return scoreObjective;
    }
    
    public Collection func_96520_a(final IScoreObjectiveCriteria scoreObjectiveCriteria) {
        final Collection collection = this.scoreObjectiveCriterias.get(scoreObjectiveCriteria);
        return (collection == null) ? Lists.newArrayList() : Lists.newArrayList(collection);
    }
    
    public boolean func_178819_b(final String s, final ScoreObjective scoreObjective) {
        final Map<Object, Score> map = this.field_96544_c.get(s);
        return map != null && map.get(scoreObjective) != null;
    }
    
    public Score getValueFromObjective(final String s, final ScoreObjective scoreObjective) {
        Map<Object, Score> hashMap = this.field_96544_c.get(s);
        if (hashMap == null) {
            hashMap = (Map<Object, Score>)Maps.newHashMap();
            this.field_96544_c.put(s, hashMap);
        }
        Score score = ((Map<String, Score>)hashMap).get(scoreObjective);
        if (score == null) {
            score = new Score(this, scoreObjective, s);
            ((Map<String, Score>)hashMap).put((String)scoreObjective, score);
        }
        return score;
    }
    
    public Collection getSortedScores(final ScoreObjective scoreObjective) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Map<Object, Score>> iterator = this.field_96544_c.values().iterator();
        while (iterator.hasNext()) {
            final Score score = iterator.next().get(scoreObjective);
            if (score != null) {
                arrayList.add(score);
            }
        }
        Collections.sort((List<Object>)arrayList, Score.scoreComparator);
        return arrayList;
    }
    
    public Collection getScoreObjectives() {
        return this.scoreObjectives.values();
    }
    
    public Collection getObjectiveNames() {
        return this.field_96544_c.keySet();
    }
    
    public void func_178822_d(final String s, final ScoreObjective scoreObjective) {
        if (scoreObjective == null) {
            if (this.field_96544_c.remove(s) != null) {
                this.func_96516_a(s);
            }
        }
        else {
            final Map<Object, Score> map = this.field_96544_c.get(s);
            if (map != null) {
                final Score score = map.remove(scoreObjective);
                if (map.size() < 1) {
                    if (this.field_96544_c.remove(s) != null) {
                        this.func_96516_a(s);
                    }
                }
                else if (score != null) {
                    this.func_178820_a(s, scoreObjective);
                }
            }
        }
    }
    
    public Collection func_96528_e() {
        final Collection<Map<Object, ? extends E>> values = this.field_96544_c.values();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Map<Object, ? extends E>> iterator = values.iterator();
        while (iterator.hasNext()) {
            arrayList.addAll(iterator.next().values());
        }
        return arrayList;
    }
    
    public Map func_96510_d(final String s) {
        Map hashMap = this.field_96544_c.get(s);
        if (hashMap == null) {
            hashMap = Maps.newHashMap();
        }
        return hashMap;
    }
    
    public void func_96519_k(final ScoreObjective scoreObjective) {
        this.scoreObjectives.remove(scoreObjective.getName());
        for (int i = 0; i < 19; ++i) {
            if (this.getObjectiveInDisplaySlot(i) == scoreObjective) {
                this.setObjectiveInDisplaySlot(i, null);
            }
        }
        final List list = this.scoreObjectiveCriterias.get(scoreObjective.getCriteria());
        if (list != null) {
            list.remove(scoreObjective);
        }
        final Iterator<Map> iterator = this.field_96544_c.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().remove(scoreObjective);
        }
        this.func_96533_c(scoreObjective);
    }
    
    public void setObjectiveInDisplaySlot(final int n, final ScoreObjective scoreObjective) {
        this.objectiveDisplaySlots[n] = scoreObjective;
    }
    
    public ScoreObjective getObjectiveInDisplaySlot(final int n) {
        return this.objectiveDisplaySlots[n];
    }
    
    public ScorePlayerTeam getTeam(final String s) {
        return this.teams.get(s);
    }
    
    public ScorePlayerTeam createTeam(final String s) {
        if (this.getTeam(s) != null) {
            throw new IllegalArgumentException(Scoreboard.lIlIIlIlIlllIlll[3] + s + Scoreboard.lIlIIlIlIlllIlll[4]);
        }
        final ScorePlayerTeam scorePlayerTeam = new ScorePlayerTeam(this, s);
        this.teams.put(s, scorePlayerTeam);
        this.broadcastTeamCreated(scorePlayerTeam);
        return scorePlayerTeam;
    }
    
    public void removeTeam(final ScorePlayerTeam scorePlayerTeam) {
        this.teams.remove(scorePlayerTeam.getRegisteredName());
        final Iterator<String> iterator = scorePlayerTeam.getMembershipCollection().iterator();
        while (iterator.hasNext()) {
            this.teamMemberships.remove(iterator.next());
        }
        this.func_96513_c(scorePlayerTeam);
    }
    
    public boolean func_151392_a(final String s, final String s2) {
        if (!this.teams.containsKey(s2)) {
            return false;
        }
        final ScorePlayerTeam team = this.getTeam(s2);
        if (this.getPlayersTeam(s) != null) {
            this.removePlayerFromTeams(s);
        }
        this.teamMemberships.put(s, team);
        team.getMembershipCollection().add(s);
        return true;
    }
    
    public boolean removePlayerFromTeams(final String s) {
        final ScorePlayerTeam playersTeam = this.getPlayersTeam(s);
        if (playersTeam != null) {
            this.removePlayerFromTeam(s, playersTeam);
            return true;
        }
        return false;
    }
    
    public void removePlayerFromTeam(final String s, final ScorePlayerTeam scorePlayerTeam) {
        if (this.getPlayersTeam(s) != scorePlayerTeam) {
            throw new IllegalStateException(Scoreboard.lIlIIlIlIlllIlll[5] + scorePlayerTeam.getRegisteredName() + Scoreboard.lIlIIlIlIlllIlll[6]);
        }
        this.teamMemberships.remove(s);
        scorePlayerTeam.getMembershipCollection().remove(s);
    }
    
    public Collection getTeamNames() {
        return this.teams.keySet();
    }
    
    public Collection getTeams() {
        return this.teams.values();
    }
    
    public ScorePlayerTeam getPlayersTeam(final String s) {
        return this.teamMemberships.get(s);
    }
    
    public void func_96522_a(final ScoreObjective scoreObjective) {
    }
    
    public void func_96532_b(final ScoreObjective scoreObjective) {
    }
    
    public void func_96533_c(final ScoreObjective scoreObjective) {
    }
    
    public void func_96536_a(final Score score) {
    }
    
    public void func_96516_a(final String s) {
    }
    
    public void func_178820_a(final String s, final ScoreObjective scoreObjective) {
    }
    
    public void broadcastTeamCreated(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public void broadcastTeamRemoved(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public void func_96513_c(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public static String getObjectiveDisplaySlot(final int n) {
        switch (n) {
            case 0: {
                return Scoreboard.lIlIIlIlIlllIlll[7];
            }
            case 1: {
                return Scoreboard.lIlIIlIlIlllIlll[8];
            }
            case 2: {
                return Scoreboard.lIlIIlIlIlllIlll[9];
            }
            default: {
                if (n >= 3 && n <= 18) {
                    final EnumChatFormatting func_175744_a = EnumChatFormatting.func_175744_a(n - 3);
                    if (func_175744_a != null && func_175744_a != EnumChatFormatting.RESET) {
                        return Scoreboard.lIlIIlIlIlllIlll[10] + func_175744_a.getFriendlyName();
                    }
                }
                return null;
            }
        }
    }
    
    public static int getObjectiveDisplaySlotNumber(final String s) {
        if (s.equalsIgnoreCase(Scoreboard.lIlIIlIlIlllIlll[11])) {
            return 0;
        }
        if (s.equalsIgnoreCase(Scoreboard.lIlIIlIlIlllIlll[12])) {
            return 1;
        }
        if (s.equalsIgnoreCase(Scoreboard.lIlIIlIlIlllIlll[13])) {
            return 2;
        }
        if (s.startsWith(Scoreboard.lIlIIlIlIlllIlll[14])) {
            final EnumChatFormatting valueByName = EnumChatFormatting.getValueByName(s.substring(Scoreboard.lIlIIlIlIlllIlll[15].length()));
            if (valueByName != null && valueByName.func_175746_b() >= 0) {
                return valueByName.func_175746_b() + 3;
            }
        }
        return -1;
    }
    
    public static String[] func_178821_h() {
        if (Scoreboard.field_178823_g == null) {
            Scoreboard.field_178823_g = new String[19];
            for (int i = 0; i < 19; ++i) {
                Scoreboard.field_178823_g[i] = getObjectiveDisplaySlot(i);
            }
        }
        return Scoreboard.field_178823_g;
    }
    
    private static void llllIIllllIlIIll() {
        (lIlIIlIlIlllIlll = new String[16])[0] = llllIIllllIIIlll(Scoreboard.lIlIIlIlIlllllII[0], Scoreboard.lIlIIlIlIlllllII[1]);
        Scoreboard.lIlIIlIlIlllIlll[1] = llllIIllllIIllIl(Scoreboard.lIlIIlIlIlllllII[2], Scoreboard.lIlIIlIlIlllllII[3]);
        Scoreboard.lIlIIlIlIlllIlll[2] = llllIIllllIIIlll(Scoreboard.lIlIIlIlIlllllII[4], Scoreboard.lIlIIlIlIlllllII[5]);
        Scoreboard.lIlIIlIlIlllIlll[3] = llllIIllllIIIlll(Scoreboard.lIlIIlIlIlllllII[6], Scoreboard.lIlIIlIlIlllllII[7]);
        Scoreboard.lIlIIlIlIlllIlll[4] = llllIIllllIIIlll(Scoreboard.lIlIIlIlIlllllII[8], Scoreboard.lIlIIlIlIlllllII[9]);
        Scoreboard.lIlIIlIlIlllIlll[5] = llllIIllllIIllll(Scoreboard.lIlIIlIlIlllllII[10], Scoreboard.lIlIIlIlIlllllII[11]);
        Scoreboard.lIlIIlIlIlllIlll[6] = llllIIllllIIIlll(Scoreboard.lIlIIlIlIlllllII[12], Scoreboard.lIlIIlIlIlllllII[13]);
        Scoreboard.lIlIIlIlIlllIlll[7] = llllIIllllIIIlll(Scoreboard.lIlIIlIlIlllllII[14], Scoreboard.lIlIIlIlIlllllII[15]);
        Scoreboard.lIlIIlIlIlllIlll[8] = llllIIllllIlIIlI(Scoreboard.lIlIIlIlIlllllII[16], Scoreboard.lIlIIlIlIlllllII[17]);
        Scoreboard.lIlIIlIlIlllIlll[9] = llllIIllllIlIIlI(Scoreboard.lIlIIlIlIlllllII[18], Scoreboard.lIlIIlIlIlllllII[19]);
        Scoreboard.lIlIIlIlIlllIlll[10] = llllIIllllIIllll(Scoreboard.lIlIIlIlIlllllII[20], Scoreboard.lIlIIlIlIlllllII[21]);
        Scoreboard.lIlIIlIlIlllIlll[11] = llllIIllllIIllIl(Scoreboard.lIlIIlIlIlllllII[22], Scoreboard.lIlIIlIlIlllllII[23]);
        Scoreboard.lIlIIlIlIlllIlll[12] = llllIIllllIIllIl(Scoreboard.lIlIIlIlIlllllII[24], Scoreboard.lIlIIlIlIlllllII[25]);
        Scoreboard.lIlIIlIlIlllIlll[13] = llllIIllllIIllll("W8eUo6ty3GJZEh4mCEooCg==", "vFqUD");
        Scoreboard.lIlIIlIlIlllIlll[14] = llllIIllllIlIIlI("wqI5CY4X8F9EcIm2U9scoQ==", "wrSxp");
        Scoreboard.lIlIIlIlIlllIlll[15] = llllIIllllIIllIl("GAURPSwKHlssKwoBWw==", "kluXN");
        Scoreboard.lIlIIlIlIlllllII = null;
    }
    
    private static void llllIIllllIlIlII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        Scoreboard.lIlIIlIlIlllllII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String llllIIllllIIllll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llllIIllllIlIIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llllIIllllIIIlll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llllIIllllIIllIl(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
}
