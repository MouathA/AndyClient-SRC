import net.minecraft.client.main.*;
import java.util.*;

public class Start
{
    public static void main(final String[] array) {
        Main.main((String[])concat(new String[] { "--version", "mcp", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}" }, array));
    }
    
    public static Object[] concat(final Object[] array, final Object[] array2) {
        final Object[] copy = Arrays.copyOf(array, array.length + array2.length);
        System.arraycopy(array2, 0, copy, array.length, array2.length);
        return copy;
    }
}
