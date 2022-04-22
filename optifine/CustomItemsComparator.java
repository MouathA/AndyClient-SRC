package optifine;

import java.util.*;

public class CustomItemsComparator implements Comparator
{
    @Override
    public int compare(final Object o, final Object o2) {
        final CustomItemProperties customItemProperties = (CustomItemProperties)o;
        final CustomItemProperties customItemProperties2 = (CustomItemProperties)o2;
        return (customItemProperties.weight != customItemProperties2.weight) ? (customItemProperties2.weight - customItemProperties.weight) : (Config.equals(customItemProperties.basePath, customItemProperties2.basePath) ? customItemProperties.name.compareTo(customItemProperties2.name) : customItemProperties.basePath.compareTo(customItemProperties2.basePath));
    }
}
