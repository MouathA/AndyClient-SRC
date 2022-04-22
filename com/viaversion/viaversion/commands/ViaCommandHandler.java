package com.viaversion.viaversion.commands;

import com.google.common.base.*;
import com.viaversion.viaversion.api.command.*;
import java.util.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.commands.defaultsubs.*;

public abstract class ViaCommandHandler implements ViaVersionCommand
{
    private final Map commandMap;
    
    protected ViaCommandHandler() {
        this.commandMap = new HashMap();
        this.registerDefaults();
    }
    
    @Override
    public void registerSubCommand(final ViaSubCommand viaSubCommand) {
        Preconditions.checkArgument(viaSubCommand.name().matches("^[a-z0-9_-]{3,15}$"), (Object)(viaSubCommand.name() + " is not a valid sub-command name."));
        Preconditions.checkArgument(!this.hasSubCommand(viaSubCommand.name()), (Object)("ViaSubCommand " + viaSubCommand.name() + " does already exists!"));
        this.commandMap.put(viaSubCommand.name().toLowerCase(Locale.ROOT), viaSubCommand);
    }
    
    @Override
    public boolean hasSubCommand(final String s) {
        return this.commandMap.containsKey(s.toLowerCase(Locale.ROOT));
    }
    
    @Override
    public ViaSubCommand getSubCommand(final String s) {
        return this.commandMap.get(s.toLowerCase(Locale.ROOT));
    }
    
    @Override
    public boolean onCommand(final ViaCommandSender viaCommandSender, final String[] array) {
        if (array.length == 0) {
            this.showHelp(viaCommandSender);
            return false;
        }
        if (!this.hasSubCommand(array[0])) {
            viaCommandSender.sendMessage("\ufd5e\ufd1b\ufd2c\ufd10\ufd11\ufd0b\ufd58\ufd1b\ufd17\ufd15\ufd15\ufd19\ufd16\ufd1c\ufd58\ufd1c\ufd17\ufd1d\ufd0b\ufd58\ufd16\ufd17\ufd0c\ufd58\ufd1d\ufd00\ufd11\ufd0b\ufd0c\ufd56");
            this.showHelp(viaCommandSender);
            return false;
        }
        final ViaSubCommand subCommand = this.getSubCommand(array[0]);
        if (subCommand.permission() != null) {
            viaCommandSender.sendMessage("\ufd5e\ufd1b\ufd21\ufd17\ufd0d\ufd58\ufd19\ufd0a\ufd1d\ufd58\ufd16\ufd17\ufd0c\ufd58\ufd19\ufd14\ufd14\ufd17\ufd0f\ufd1d\ufd1c\ufd58\ufd0c\ufd17\ufd58\ufd0d\ufd0b\ufd1d\ufd58\ufd0c\ufd10\ufd11\ufd0b\ufd58\ufd1b\ufd17\ufd15\ufd15\ufd19\ufd16\ufd1c\ufd59");
            return false;
        }
        final boolean execute = subCommand.execute(viaCommandSender, Arrays.copyOfRange(array, 1, array.length));
        if (!execute) {
            viaCommandSender.sendMessage("Usage: /viaversion " + subCommand.usage());
        }
        return execute;
    }
    
    @Override
    public List onTabComplete(final ViaCommandSender viaCommandSender, final String[] array) {
        final Set calculateAllowedCommands = this.calculateAllowedCommands(viaCommandSender);
        final ArrayList<String> list = new ArrayList<String>();
        if (array.length == 1) {
            if (!array[0].isEmpty()) {
                for (final ViaSubCommand viaSubCommand : calculateAllowedCommands) {
                    if (viaSubCommand.name().toLowerCase().startsWith(array[0].toLowerCase(Locale.ROOT))) {
                        list.add(viaSubCommand.name());
                    }
                }
            }
            else {
                final Iterator<ViaSubCommand> iterator2 = calculateAllowedCommands.iterator();
                while (iterator2.hasNext()) {
                    list.add(iterator2.next().name());
                }
            }
        }
        else if (array.length >= 2 && this.getSubCommand(array[0]) != null) {
            final ViaSubCommand subCommand = this.getSubCommand(array[0]);
            if (!calculateAllowedCommands.contains(subCommand)) {
                return list;
            }
            final List onTabComplete = subCommand.onTabComplete(viaCommandSender, Arrays.copyOfRange(array, 1, array.length));
            Collections.sort((List<Comparable>)onTabComplete);
            return onTabComplete;
        }
        return list;
    }
    
    @Override
    public void showHelp(final ViaCommandSender viaCommandSender) {
        final Set calculateAllowedCommands = this.calculateAllowedCommands(viaCommandSender);
        if (calculateAllowedCommands.isEmpty()) {
            viaCommandSender.sendMessage("\ufd5e\ufd1b\ufd21\ufd17\ufd0d\ufd58\ufd19\ufd0a\ufd1d\ufd58\ufd16\ufd17\ufd0c\ufd58\ufd19\ufd14\ufd14\ufd17\ufd0f\ufd1d\ufd1c\ufd58\ufd0c\ufd17\ufd58\ufd0d\ufd0b\ufd1d\ufd58\ufd0c\ufd10\ufd1d\ufd0b\ufd1d\ufd58\ufd1b\ufd17\ufd15\ufd15\ufd19\ufd16\ufd1c\ufd0b\ufd59");
            return;
        }
        viaCommandSender.sendMessage(ViaSubCommand.color("&aViaVersion &c" + Via.getPlatform().getPluginVersion()));
        viaCommandSender.sendMessage("\ufd5e\ufd4e\ufd3b\ufd17\ufd15\ufd15\ufd19\ufd16\ufd1c\ufd0b\ufd42");
        for (final ViaSubCommand viaSubCommand : calculateAllowedCommands) {
            viaCommandSender.sendMessage(ViaSubCommand.color(String.format("&2/viaversion %s &7- &6%s", viaSubCommand.usage(), viaSubCommand.description())));
        }
        calculateAllowedCommands.clear();
    }
    
    private Set calculateAllowedCommands(final ViaCommandSender p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/HashSet.<init>:()V
        //     7: astore_2       
        //     8: aload_0        
        //     9: getfield        com/viaversion/viaversion/commands/ViaCommandHandler.commandMap:Ljava/util/Map;
        //    12: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //    17: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    22: astore_3       
        //    23: aload_3        
        //    24: invokeinterface java/util/Iterator.hasNext:()Z
        //    29: ifeq            65
        //    32: aload_3        
        //    33: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    38: checkcast       Lcom/viaversion/viaversion/api/command/ViaSubCommand;
        //    41: astore          4
        //    43: aload_0        
        //    44: aload_1        
        //    45: aload           4
        //    47: invokevirtual   com/viaversion/viaversion/api/command/ViaSubCommand.permission:()Ljava/lang/String;
        //    50: ifnull          62
        //    53: aload_2        
        //    54: aload           4
        //    56: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //    61: pop            
        //    62: goto            23
        //    65: aload_2        
        //    66: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0023 (coming from #0062).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void registerDefaults() {
        this.registerSubCommand(new ListSubCmd());
        this.registerSubCommand(new PPSSubCmd());
        this.registerSubCommand(new DebugSubCmd());
        this.registerSubCommand(new DumpSubCmd());
        this.registerSubCommand(new DisplayLeaksSubCmd());
        this.registerSubCommand(new DontBugMeSubCmd());
        this.registerSubCommand(new AutoTeamSubCmd());
        this.registerSubCommand(new HelpSubCmd());
        this.registerSubCommand(new ReloadSubCmd());
    }
}
