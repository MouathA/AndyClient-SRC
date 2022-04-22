package net.minecraft.world;

public enum EnumDifficulty
{
    PEACEFUL("PEACEFUL", 0, "PEACEFUL", 0, 0, "options.difficulty.peaceful"), 
    EASY("EASY", 1, "EASY", 1, 1, "options.difficulty.easy"), 
    NORMAL("NORMAL", 2, "NORMAL", 2, 2, "options.difficulty.normal"), 
    HARD("HARD", 3, "HARD", 3, 3, "options.difficulty.hard");
    
    private static final EnumDifficulty[] difficultyEnums;
    private final int difficultyId;
    private final String difficultyResourceKey;
    private static final EnumDifficulty[] $VALUES;
    private static final String __OBFID;
    private static final EnumDifficulty[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00001510";
        ENUM$VALUES = new EnumDifficulty[] { EnumDifficulty.PEACEFUL, EnumDifficulty.EASY, EnumDifficulty.NORMAL, EnumDifficulty.HARD };
        difficultyEnums = new EnumDifficulty[values().length];
        $VALUES = new EnumDifficulty[] { EnumDifficulty.PEACEFUL, EnumDifficulty.EASY, EnumDifficulty.NORMAL, EnumDifficulty.HARD };
        final EnumDifficulty[] values = values();
        while (0 < values.length) {
            final EnumDifficulty enumDifficulty = values[0];
            EnumDifficulty.difficultyEnums[enumDifficulty.difficultyId] = enumDifficulty;
            int n = 0;
            ++n;
        }
    }
    
    private EnumDifficulty(final String s, final int n, final String s2, final int n2, final int difficultyId, final String difficultyResourceKey) {
        this.difficultyId = difficultyId;
        this.difficultyResourceKey = difficultyResourceKey;
    }
    
    public int getDifficultyId() {
        return this.difficultyId;
    }
    
    public static EnumDifficulty getDifficultyEnum(final int n) {
        return EnumDifficulty.difficultyEnums[n % EnumDifficulty.difficultyEnums.length];
    }
    
    public String getDifficultyResourceKey() {
        return this.difficultyResourceKey;
    }
}
