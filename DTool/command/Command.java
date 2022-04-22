package DTool.command;

import net.minecraft.client.*;
import java.util.*;

public abstract class Command
{
    public static Minecraft mc;
    public String name;
    public String description;
    public String syntax;
    public List aliases;
    
    static {
        Command.mc = Minecraft.getMinecraft();
    }
    
    public Command(final String name, final String description, final String syntax, final String... array) {
        this.aliases = new ArrayList();
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(array);
    }
    
    public abstract void onCommand(final String[] p0, final String p1);
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getSyntax() {
        return this.syntax;
    }
    
    public void setSyntax(final String syntax) {
        this.syntax = syntax;
    }
    
    public List getAliases() {
        return this.aliases;
    }
    
    public void setAliases(final List aliases) {
        this.aliases = aliases;
    }
}
