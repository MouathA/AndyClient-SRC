package de.gerrygames.viarewind.api;

public interface ViaRewindConfig
{
    CooldownIndicator getCooldownIndicator();
    
    boolean isReplaceAdventureMode();
    
    boolean isReplaceParticles();
    
    int getMaxBookPages();
    
    int getMaxBookPageSize();
    
    public enum CooldownIndicator
    {
        TITLE("TITLE", 0), 
        ACTION_BAR("ACTION_BAR", 1), 
        BOSS_BAR("BOSS_BAR", 2), 
        DISABLED("DISABLED", 3);
        
        private static final CooldownIndicator[] $VALUES;
        
        private CooldownIndicator(final String s, final int n) {
        }
        
        static {
            $VALUES = new CooldownIndicator[] { CooldownIndicator.TITLE, CooldownIndicator.ACTION_BAR, CooldownIndicator.BOSS_BAR, CooldownIndicator.DISABLED };
        }
    }
}
