package Mood.Helpers;

import java.util.*;

public class RandomObjectArraylist extends ArrayList
{
    public RandomObjectArraylist(final Object... array) {
        while (0 < array.length) {
            this.add(array[0]);
            final byte b = 1;
        }
    }
    
    public Object getRandomObject() {
        if (this.size() == 0) {
            return null;
        }
        return this.get(new Random().nextInt(this.size() - 1));
    }
}
