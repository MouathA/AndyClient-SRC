package DTool.util;

public enum EnumColor
{
    Black("Black", 0, "Black", 0, "Black", 0), 
    DarkBlue("DarkBlue", 1, "DarkBlue", 1, "DarkBlue", 1), 
    DarkGreen("DarkGreen", 2, "DarkGreen", 2, "DarkGreen", 2), 
    DarkAqua("DarkAqua", 3, "DarkAqua", 3, "DarkAqua", 3), 
    DarkRed("DarkRed", 4, "DarkRed", 4, "DarkRed", 4), 
    DarkPurple("DarkPurple", 5, "DarkPurple", 5, "DarkPurple", 5), 
    Gold("Gold", 6, "Gold", 6, "Gold", 6), 
    Gray("Gray", 7, "Gray", 7, "Gray", 7), 
    DarkGray("DarkGray", 8, "DarkGray", 8, "DarkGray", 8), 
    Blue("Blue", 9, "Blue", 9, "Blue", 9), 
    Green("Green", 10, "Green", 10, "Green", 10), 
    Aqua("Aqua", 11, "Aqua", 11, "Aqua", 11), 
    Red("Red", 12, "Red", 12, "Red", 12), 
    Purple("Purple", 13, "Purple", 13, "Purple", 13), 
    Yellow("Yellow", 14, "Yellow", 14, "Yellow", 14), 
    White("White", 15, "White", 15, "White", 15);
    
    private static EnumColor[] ENUM$VALUES;
    private static final EnumColor[] ENUM$VALUES_0;
    
    static {
        ENUM$VALUES_0 = new EnumColor[] { EnumColor.Black, EnumColor.DarkBlue, EnumColor.DarkGreen, EnumColor.DarkAqua, EnumColor.DarkRed, EnumColor.DarkPurple, EnumColor.Gold, EnumColor.Gray, EnumColor.DarkGray, EnumColor.Blue, EnumColor.Green, EnumColor.Aqua, EnumColor.Red, EnumColor.Purple, EnumColor.Yellow, EnumColor.White };
        EnumColor.ENUM$VALUES = new EnumColor[] { EnumColor.Black, EnumColor.DarkBlue, EnumColor.DarkGreen, EnumColor.DarkAqua, EnumColor.DarkRed, EnumColor.DarkPurple, EnumColor.Gold, EnumColor.Gray, EnumColor.DarkGray, EnumColor.Blue, EnumColor.Green, EnumColor.Aqua, EnumColor.Red, EnumColor.Purple, EnumColor.Yellow, EnumColor.White };
    }
    
    private EnumColor(final String s, final int n, final String s2, final int n2, final String s3, final int n3) {
    }
    
    public static EnumColor nextColor(EnumColor black) {
        final EnumColor[] values;
        final int length = (values = values()).length;
        while (0 < 1 && values[0] != black) {
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
        if (black == EnumColor.White) {
            black = EnumColor.Black;
            return black;
        }
        if (1 > values().length) {}
        black = values()[1];
        return black;
    }
}
