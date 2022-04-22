package net.minecraft.command.server;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandScoreboard extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "scoreboard";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.scoreboard.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (!this.func_175780_b(commandSender, array)) {
            if (array.length < 1) {
                throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
            }
            if (array[0].equalsIgnoreCase("objectives")) {
                if (array.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                }
                if (array[1].equalsIgnoreCase("list")) {
                    this.listObjectives(commandSender);
                }
                else if (array[1].equalsIgnoreCase("add")) {
                    if (array.length < 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                    }
                    this.addObjective(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("remove")) {
                    if (array.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                    }
                    this.removeObjective(commandSender, array[2]);
                }
                else {
                    if (!array[1].equalsIgnoreCase("setdisplay")) {
                        throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                    }
                    if (array.length != 3 && array.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                    }
                    this.setObjectiveDisplay(commandSender, array, 2);
                }
            }
            else if (array[0].equalsIgnoreCase("players")) {
                if (array.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                }
                if (array[1].equalsIgnoreCase("list")) {
                    if (array.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                    }
                    this.listPlayers(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("add")) {
                    if (array.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                    }
                    this.setPlayer(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("remove")) {
                    if (array.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                    }
                    this.setPlayer(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("set")) {
                    if (array.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                    }
                    this.setPlayer(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("reset")) {
                    if (array.length != 3 && array.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                    }
                    this.resetPlayers(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("enable")) {
                    if (array.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
                    }
                    this.func_175779_n(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("test")) {
                    if (array.length != 5 && array.length != 6) {
                        throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
                    }
                    this.func_175781_o(commandSender, array, 2);
                }
                else {
                    if (!array[1].equalsIgnoreCase("operation")) {
                        throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                    }
                    if (array.length != 7) {
                        throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
                    }
                    this.func_175778_p(commandSender, array, 2);
                }
            }
            else if (array[0].equalsIgnoreCase("teams")) {
                if (array.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                }
                if (array[1].equalsIgnoreCase("list")) {
                    if (array.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                    }
                    this.listTeams(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("add")) {
                    if (array.length < 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                    }
                    this.addTeam(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("remove")) {
                    if (array.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                    }
                    this.removeTeam(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("empty")) {
                    if (array.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                    }
                    this.emptyTeam(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("join")) {
                    if (array.length < 4 && (array.length != 3 || !(commandSender instanceof EntityPlayer))) {
                        throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                    }
                    this.joinTeam(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("leave")) {
                    if (array.length < 3 && !(commandSender instanceof EntityPlayer)) {
                        throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                    }
                    this.leaveTeam(commandSender, array, 2);
                }
                else {
                    if (!array[1].equalsIgnoreCase("option")) {
                        throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                    }
                    if (array.length != 4 && array.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                    }
                    this.setTeamOption(commandSender, array, 2);
                }
            }
        }
    }
    
    private boolean func_175780_b(final ICommandSender commandSender, final String[] array) throws CommandException {
        while (0 < array.length) {
            if (this.isUsernameIndex(array, 0) && "*".equals(array[0]) && -1 >= 0) {
                throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
            }
            int n = 0;
            ++n;
        }
        if (-1 < 0) {
            return false;
        }
        final ArrayList arrayList = Lists.newArrayList(this.getScoreboard().getObjectiveNames());
        final String s = array[-1];
        final ArrayList arrayList2 = Lists.newArrayList();
        for (final String s2 : arrayList) {
            array[-1] = s2;
            this.processCommand(commandSender, array);
            arrayList2.add(s2);
        }
        array[-1] = s;
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, arrayList2.size());
        if (arrayList2.size() == 0) {
            throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
        }
        return true;
    }
    
    protected Scoreboard getScoreboard() {
        return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
    }
    
    protected ScoreObjective func_147189_a(final String s, final boolean b) throws CommandException {
        final ScoreObjective objective = this.getScoreboard().getObjective(s);
        if (objective == null) {
            throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { s });
        }
        if (b && objective.getCriteria().isReadOnly()) {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { s });
        }
        return objective;
    }
    
    protected ScorePlayerTeam func_147183_a(final String s) throws CommandException {
        final ScorePlayerTeam team = this.getScoreboard().getTeam(s);
        if (team == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { s });
        }
        return team;
    }
    
    protected void addObjective(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n++];
        final String s2 = array[n++];
        final Scoreboard scoreboard = this.getScoreboard();
        final IScoreObjectiveCriteria scoreObjectiveCriteria = IScoreObjectiveCriteria.INSTANCES.get(s2);
        if (scoreObjectiveCriteria == null) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { s2 });
        }
        if (scoreboard.getObjective(s) != null) {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { s });
        }
        if (s.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { s, 16 });
        }
        if (s.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
        }
        if (array.length > n) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, n).getUnformattedText();
            if (unformattedText.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { unformattedText, 32 });
            }
            if (unformattedText.length() > 0) {
                scoreboard.addScoreObjective(s, scoreObjectiveCriteria).setDisplayName(unformattedText);
            }
            else {
                scoreboard.addScoreObjective(s, scoreObjectiveCriteria);
            }
        }
        else {
            scoreboard.addScoreObjective(s, scoreObjectiveCriteria);
        }
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.objectives.add.success", s);
    }
    
    protected void addTeam(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n++];
        final Scoreboard scoreboard = this.getScoreboard();
        if (scoreboard.getTeam(s) != null) {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { s });
        }
        if (s.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { s, 16 });
        }
        if (s.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
        }
        if (array.length > n) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, n).getUnformattedText();
            if (unformattedText.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { unformattedText, 32 });
            }
            if (unformattedText.length() > 0) {
                scoreboard.createTeam(s).setTeamName(unformattedText);
            }
            else {
                scoreboard.createTeam(s);
            }
        }
        else {
            scoreboard.createTeam(s);
        }
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.add.success", s);
    }
    
    protected void setTeamOption(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final ScorePlayerTeam func_147183_a = this.func_147183_a(array[n++]);
        if (func_147183_a != null) {
            final String lowerCase = array[n++].toLowerCase();
            if (!lowerCase.equalsIgnoreCase("color") && !lowerCase.equalsIgnoreCase("friendlyfire") && !lowerCase.equalsIgnoreCase("seeFriendlyInvisibles") && !lowerCase.equalsIgnoreCase("nametagVisibility") && !lowerCase.equalsIgnoreCase("deathMessageVisibility")) {
                throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
            }
            if (array.length == 4) {
                if (lowerCase.equalsIgnoreCase("color")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
                }
                if (lowerCase.equalsIgnoreCase("friendlyfire") || lowerCase.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
                }
                if (!lowerCase.equalsIgnoreCase("nametagVisibility") && !lowerCase.equalsIgnoreCase("deathMessageVisibility")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceString(Team.EnumVisible.func_178825_a()) });
            }
            else {
                final String s = array[n];
                if (lowerCase.equalsIgnoreCase("color")) {
                    final EnumChatFormatting valueByName = EnumChatFormatting.getValueByName(s);
                    if (valueByName == null || valueByName.isFancyStyling()) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
                    }
                    func_147183_a.func_178774_a(valueByName);
                    func_147183_a.setNamePrefix(valueByName.toString());
                    func_147183_a.setNameSuffix(EnumChatFormatting.RESET.toString());
                }
                else if (lowerCase.equalsIgnoreCase("friendlyfire")) {
                    if (!s.equalsIgnoreCase("true") && !s.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
                    }
                    func_147183_a.setAllowFriendlyFire(s.equalsIgnoreCase("true"));
                }
                else if (lowerCase.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!s.equalsIgnoreCase("true") && !s.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
                    }
                    func_147183_a.setSeeFriendlyInvisiblesEnabled(s.equalsIgnoreCase("true"));
                }
                else if (lowerCase.equalsIgnoreCase("nametagVisibility")) {
                    final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(s);
                    if (func_178824_a == null) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceString(Team.EnumVisible.func_178825_a()) });
                    }
                    func_147183_a.func_178772_a(func_178824_a);
                }
                else if (lowerCase.equalsIgnoreCase("deathMessageVisibility")) {
                    final Team.EnumVisible func_178824_a2 = Team.EnumVisible.func_178824_a(s);
                    if (func_178824_a2 == null) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceString(Team.EnumVisible.func_178825_a()) });
                    }
                    func_147183_a.func_178773_b(func_178824_a2);
                }
                CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.option.success", lowerCase, func_147183_a.getRegisteredName(), s);
            }
        }
    }
    
    protected void removeTeam(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final ScorePlayerTeam func_147183_a = this.func_147183_a(array[n]);
        if (func_147183_a != null) {
            scoreboard.removeTeam(func_147183_a);
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.remove.success", func_147183_a.getRegisteredName());
        }
    }
    
    protected void listTeams(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        if (array.length > n) {
            final ScorePlayerTeam func_147183_a = this.func_147183_a(array[n]);
            if (func_147183_a == null) {
                return;
            }
            final Collection membershipCollection = func_147183_a.getMembershipCollection();
            commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, membershipCollection.size());
            if (membershipCollection.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { func_147183_a.getRegisteredName() });
            }
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { membershipCollection.size(), func_147183_a.getRegisteredName() });
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation);
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(membershipCollection.toArray())));
        }
        else {
            final Collection teams = scoreboard.getTeams();
            commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, teams.size());
            if (teams.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { teams.size() });
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation2);
            for (final ScorePlayerTeam scorePlayerTeam : teams) {
                commandSender.addChatMessage(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { scorePlayerTeam.getRegisteredName(), scorePlayerTeam.func_96669_c(), scorePlayerTeam.getMembershipCollection().size() }));
            }
        }
    }
    
    protected void joinTeam(final ICommandSender commandSender, final String[] array, int i) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String s = array[i++];
        final HashSet hashSet = Sets.newHashSet();
        final HashSet hashSet2 = Sets.newHashSet();
        if (commandSender instanceof EntityPlayer && i == array.length) {
            final String name = CommandBase.getCommandSenderAsPlayer(commandSender).getName();
            if (scoreboard.func_151392_a(name, s)) {
                hashSet.add(name);
            }
            else {
                hashSet2.add(name);
            }
        }
        else {
            while (i < array.length) {
                final String s2 = array[i++];
                if (s2.startsWith("@")) {
                    final Iterator iterator = CommandBase.func_175763_c(commandSender, s2).iterator();
                    while (iterator.hasNext()) {
                        final String func_175758_e = CommandBase.func_175758_e(commandSender, iterator.next().getUniqueID().toString());
                        if (scoreboard.func_151392_a(func_175758_e, s)) {
                            hashSet.add(func_175758_e);
                        }
                        else {
                            hashSet2.add(func_175758_e);
                        }
                    }
                }
                else {
                    final String func_175758_e2 = CommandBase.func_175758_e(commandSender, s2);
                    if (scoreboard.func_151392_a(func_175758_e2, s)) {
                        hashSet.add(func_175758_e2);
                    }
                    else {
                        hashSet2.add(func_175758_e2);
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, hashSet.size());
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.join.success", hashSet.size(), s, CommandBase.joinNiceString(hashSet.toArray(new String[0])));
        }
        if (!hashSet2.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { hashSet2.size(), s, CommandBase.joinNiceString(hashSet2.toArray(new String[0])) });
        }
    }
    
    protected void leaveTeam(final ICommandSender commandSender, final String[] array, int i) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final HashSet hashSet = Sets.newHashSet();
        final HashSet hashSet2 = Sets.newHashSet();
        if (commandSender instanceof EntityPlayer && i == array.length) {
            final String name = CommandBase.getCommandSenderAsPlayer(commandSender).getName();
            if (scoreboard.removePlayerFromTeams(name)) {
                hashSet.add(name);
            }
            else {
                hashSet2.add(name);
            }
        }
        else {
            while (i < array.length) {
                final String s = array[i++];
                if (s.startsWith("@")) {
                    final Iterator iterator = CommandBase.func_175763_c(commandSender, s).iterator();
                    while (iterator.hasNext()) {
                        final String func_175758_e = CommandBase.func_175758_e(commandSender, iterator.next().getUniqueID().toString());
                        if (scoreboard.removePlayerFromTeams(func_175758_e)) {
                            hashSet.add(func_175758_e);
                        }
                        else {
                            hashSet2.add(func_175758_e);
                        }
                    }
                }
                else {
                    final String func_175758_e2 = CommandBase.func_175758_e(commandSender, s);
                    if (scoreboard.removePlayerFromTeams(func_175758_e2)) {
                        hashSet.add(func_175758_e2);
                    }
                    else {
                        hashSet2.add(func_175758_e2);
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, hashSet.size());
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.leave.success", hashSet.size(), CommandBase.joinNiceString(hashSet.toArray(new String[0])));
        }
        if (!hashSet2.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { hashSet2.size(), CommandBase.joinNiceString(hashSet2.toArray(new String[0])) });
        }
    }
    
    protected void emptyTeam(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final ScorePlayerTeam func_147183_a = this.func_147183_a(array[n]);
        if (func_147183_a != null) {
            final ArrayList arrayList = Lists.newArrayList(func_147183_a.getMembershipCollection());
            commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, arrayList.size());
            if (arrayList.isEmpty()) {
                throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { func_147183_a.getRegisteredName() });
            }
            final Iterator<String> iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                scoreboard.removePlayerFromTeam(iterator.next(), func_147183_a);
            }
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.empty.success", arrayList.size(), func_147183_a.getRegisteredName());
        }
    }
    
    protected void removeObjective(final ICommandSender commandSender, final String s) throws CommandException {
        this.getScoreboard().func_96519_k(this.func_147189_a(s, false));
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.objectives.remove.success", s);
    }
    
    protected void listObjectives(final ICommandSender commandSender) throws CommandException {
        final Collection scoreObjectives = this.getScoreboard().getScoreObjectives();
        if (scoreObjectives.size() <= 0) {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { scoreObjectives.size() });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslation);
        for (final ScoreObjective scoreObjective : scoreObjectives) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { scoreObjective.getName(), scoreObjective.getDisplayName(), scoreObjective.getCriteria().getName() }));
        }
    }
    
    protected void setObjectiveDisplay(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String s = array[n++];
        final int objectiveDisplaySlotNumber = Scoreboard.getObjectiveDisplaySlotNumber(s);
        ScoreObjective func_147189_a = null;
        if (array.length == 4) {
            func_147189_a = this.func_147189_a(array[n], false);
        }
        if (objectiveDisplaySlotNumber < 0) {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { s });
        }
        scoreboard.setObjectiveInDisplaySlot(objectiveDisplaySlotNumber, func_147189_a);
        if (func_147189_a != null) {
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.objectives.setdisplay.successSet", Scoreboard.getObjectiveDisplaySlot(objectiveDisplaySlotNumber), func_147189_a.getName());
        }
        else {
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.objectives.setdisplay.successCleared", Scoreboard.getObjectiveDisplaySlot(objectiveDisplaySlotNumber));
        }
    }
    
    protected void listPlayers(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        if (array.length > n) {
            final String func_175758_e = CommandBase.func_175758_e(commandSender, array[n]);
            final Map func_96510_d = scoreboard.func_96510_d(func_175758_e);
            commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, func_96510_d.size());
            if (func_96510_d.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { func_175758_e });
            }
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { func_96510_d.size(), func_175758_e });
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation);
            for (final Score score : func_96510_d.values()) {
                commandSender.addChatMessage(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { score.getScorePoints(), score.getObjective().getDisplayName(), score.getObjective().getName() }));
            }
        }
        else {
            final Collection objectiveNames = scoreboard.getObjectiveNames();
            commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, objectiveNames.size());
            if (objectiveNames.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { objectiveNames.size() });
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation2);
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(objectiveNames.toArray())));
        }
    }
    
    protected void setPlayer(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n - 1];
        final int n2 = n;
        final String func_175758_e = CommandBase.func_175758_e(commandSender, array[n++]);
        final ScoreObjective func_147189_a = this.func_147189_a(array[n++], true);
        final int scorePoints = s.equalsIgnoreCase("set") ? CommandBase.parseInt(array[n++]) : CommandBase.parseInt(array[n++], 0);
        if (array.length > n) {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[n2]);
            final NBTTagCompound func_180713_a = JsonToNBT.func_180713_a(CommandBase.func_180529_a(array, n));
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            func_175768_b.writeToNBT(nbtTagCompound);
            if (!CommandTestForBlock.func_175775_a(func_180713_a, nbtTagCompound, true)) {
                throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { func_175758_e });
            }
        }
        final Score valueFromObjective = this.getScoreboard().getValueFromObjective(func_175758_e, func_147189_a);
        if (s.equalsIgnoreCase("set")) {
            valueFromObjective.setScorePoints(scorePoints);
        }
        else if (s.equalsIgnoreCase("add")) {
            valueFromObjective.increseScore(scorePoints);
        }
        else {
            valueFromObjective.decreaseScore(scorePoints);
        }
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.set.success", func_147189_a.getName(), func_175758_e, valueFromObjective.getScorePoints());
    }
    
    protected void resetPlayers(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String func_175758_e = CommandBase.func_175758_e(commandSender, array[n++]);
        if (array.length > n) {
            final ScoreObjective func_147189_a = this.func_147189_a(array[n++], false);
            scoreboard.func_178822_d(func_175758_e, func_147189_a);
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.resetscore.success", func_147189_a.getName(), func_175758_e);
        }
        else {
            scoreboard.func_178822_d(func_175758_e, null);
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.reset.success", func_175758_e);
        }
    }
    
    protected void func_175779_n(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String playerName = CommandBase.getPlayerName(commandSender, array[n++]);
        final ScoreObjective func_147189_a = this.func_147189_a(array[n], false);
        if (func_147189_a.getCriteria() != IScoreObjectiveCriteria.field_178791_c) {
            throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { func_147189_a.getName() });
        }
        scoreboard.getValueFromObjective(playerName, func_147189_a).func_178815_a(false);
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.enable.success", func_147189_a.getName(), playerName);
    }
    
    protected void func_175781_o(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String func_175758_e = CommandBase.func_175758_e(commandSender, array[n++]);
        final ScoreObjective func_147189_a = this.func_147189_a(array[n++], false);
        if (!scoreboard.func_178819_b(func_175758_e, func_147189_a)) {
            throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { func_147189_a.getName(), func_175758_e });
        }
        final int n2 = array[n].equals("*") ? Integer.MIN_VALUE : CommandBase.parseInt(array[n]);
        final int n3 = (++n < array.length && !array[n].equals("*")) ? CommandBase.parseInt(array[n], n2) : Integer.MAX_VALUE;
        final Score valueFromObjective = scoreboard.getValueFromObjective(func_175758_e, func_147189_a);
        if (valueFromObjective.getScorePoints() >= n2 && valueFromObjective.getScorePoints() <= n3) {
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.test.success", valueFromObjective.getScorePoints(), n2, n3);
            return;
        }
        throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { valueFromObjective.getScorePoints(), n2, n3 });
    }
    
    protected void func_175778_p(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String func_175758_e = CommandBase.func_175758_e(commandSender, array[n++]);
        final ScoreObjective func_147189_a = this.func_147189_a(array[n++], true);
        final String s = array[n++];
        final String func_175758_e2 = CommandBase.func_175758_e(commandSender, array[n++]);
        final ScoreObjective func_147189_a2 = this.func_147189_a(array[n], false);
        final Score valueFromObjective = scoreboard.getValueFromObjective(func_175758_e, func_147189_a);
        if (!scoreboard.func_178819_b(func_175758_e2, func_147189_a2)) {
            throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { func_147189_a2.getName(), func_175758_e2 });
        }
        final Score valueFromObjective2 = scoreboard.getValueFromObjective(func_175758_e2, func_147189_a2);
        if (s.equals("+=")) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() + valueFromObjective2.getScorePoints());
        }
        else if (s.equals("-=")) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() - valueFromObjective2.getScorePoints());
        }
        else if (s.equals("*=")) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() * valueFromObjective2.getScorePoints());
        }
        else if (s.equals("/=")) {
            if (valueFromObjective2.getScorePoints() != 0) {
                valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() / valueFromObjective2.getScorePoints());
            }
        }
        else if (s.equals("%=")) {
            if (valueFromObjective2.getScorePoints() != 0) {
                valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() % valueFromObjective2.getScorePoints());
            }
        }
        else if (s.equals("=")) {
            valueFromObjective.setScorePoints(valueFromObjective2.getScorePoints());
        }
        else if (s.equals("<")) {
            valueFromObjective.setScorePoints(Math.min(valueFromObjective.getScorePoints(), valueFromObjective2.getScorePoints()));
        }
        else if (s.equals(">")) {
            valueFromObjective.setScorePoints(Math.max(valueFromObjective.getScorePoints(), valueFromObjective2.getScorePoints()));
        }
        else {
            if (!s.equals("><")) {
                throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { s });
            }
            final int scorePoints = valueFromObjective.getScorePoints();
            valueFromObjective.setScorePoints(valueFromObjective2.getScorePoints());
            valueFromObjective2.setScorePoints(scorePoints);
        }
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.operation.success", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(array, "objectives", "players", "teams");
        }
        if (array[0].equalsIgnoreCase("objectives")) {
            if (array.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(array, "list", "add", "remove", "setdisplay");
            }
            if (array[1].equalsIgnoreCase("add")) {
                if (array.length == 4) {
                    return CommandBase.func_175762_a(array, IScoreObjectiveCriteria.INSTANCES.keySet());
                }
            }
            else if (array[1].equalsIgnoreCase("remove")) {
                if (array.length == 3) {
                    return CommandBase.func_175762_a(array, this.func_147184_a(false));
                }
            }
            else if (array[1].equalsIgnoreCase("setdisplay")) {
                if (array.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, Scoreboard.func_178821_h());
                }
                if (array.length == 4) {
                    return CommandBase.func_175762_a(array, this.func_147184_a(false));
                }
            }
        }
        else if (array[0].equalsIgnoreCase("players")) {
            if (array.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(array, "set", "add", "remove", "reset", "list", "enable", "test", "operation");
            }
            if (!array[1].equalsIgnoreCase("set") && !array[1].equalsIgnoreCase("add") && !array[1].equalsIgnoreCase("remove") && !array[1].equalsIgnoreCase("reset")) {
                if (array[1].equalsIgnoreCase("enable")) {
                    if (array.length == 3) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                    }
                    if (array.length == 4) {
                        return CommandBase.func_175762_a(array, this.func_175782_e());
                    }
                }
                else if (!array[1].equalsIgnoreCase("list") && !array[1].equalsIgnoreCase("test")) {
                    if (array[1].equalsIgnoreCase("operation")) {
                        if (array.length == 3) {
                            return CommandBase.func_175762_a(array, this.getScoreboard().getObjectiveNames());
                        }
                        if (array.length == 4) {
                            return CommandBase.func_175762_a(array, this.func_147184_a(true));
                        }
                        if (array.length == 5) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><");
                        }
                        if (array.length == 6) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                        }
                        if (array.length == 7) {
                            return CommandBase.func_175762_a(array, this.func_147184_a(false));
                        }
                    }
                }
                else {
                    if (array.length == 3) {
                        return CommandBase.func_175762_a(array, this.getScoreboard().getObjectiveNames());
                    }
                    if (array.length == 4 && array[1].equalsIgnoreCase("test")) {
                        return CommandBase.func_175762_a(array, this.func_147184_a(false));
                    }
                }
            }
            else {
                if (array.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
                if (array.length == 4) {
                    return CommandBase.func_175762_a(array, this.func_147184_a(true));
                }
            }
        }
        else if (array[0].equalsIgnoreCase("teams")) {
            if (array.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(array, "add", "remove", "join", "leave", "empty", "list", "option");
            }
            if (array[1].equalsIgnoreCase("join")) {
                if (array.length == 3) {
                    return CommandBase.func_175762_a(array, this.getScoreboard().getTeamNames());
                }
                if (array.length >= 4) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
            }
            else {
                if (array[1].equalsIgnoreCase("leave")) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
                if (!array[1].equalsIgnoreCase("empty") && !array[1].equalsIgnoreCase("list") && !array[1].equalsIgnoreCase("remove")) {
                    if (array[1].equalsIgnoreCase("option")) {
                        if (array.length == 3) {
                            return CommandBase.func_175762_a(array, this.getScoreboard().getTeamNames());
                        }
                        if (array.length == 4) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility");
                        }
                        if (array.length == 5) {
                            if (array[3].equalsIgnoreCase("color")) {
                                return CommandBase.func_175762_a(array, EnumChatFormatting.getValidValues(true, false));
                            }
                            if (array[3].equalsIgnoreCase("nametagVisibility") || array[3].equalsIgnoreCase("deathMessageVisibility")) {
                                return CommandBase.getListOfStringsMatchingLastWord(array, Team.EnumVisible.func_178825_a());
                            }
                            if (array[3].equalsIgnoreCase("friendlyfire") || array[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                return CommandBase.getListOfStringsMatchingLastWord(array, "true", "false");
                            }
                        }
                    }
                }
                else if (array.length == 3) {
                    return CommandBase.func_175762_a(array, this.getScoreboard().getTeamNames());
                }
            }
        }
        return null;
    }
    
    protected List func_147184_a(final boolean b) {
        final Collection scoreObjectives = this.getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        for (final ScoreObjective scoreObjective : scoreObjectives) {
            if (!b || !scoreObjective.getCriteria().isReadOnly()) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return arrayList;
    }
    
    protected List func_175782_e() {
        final Collection scoreObjectives = this.getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        for (final ScoreObjective scoreObjective : scoreObjectives) {
            if (scoreObjective.getCriteria() == IScoreObjectiveCriteria.field_178791_c) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return arrayList;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return array[0].equalsIgnoreCase("players") ? ((array.length > 1 && array[1].equalsIgnoreCase("operation")) ? (n == 2 || n == 5) : (n == 2)) : (array[0].equalsIgnoreCase("teams") && n == 2);
    }
    
    static {
        __OBFID = "CL_00000896";
    }
}
