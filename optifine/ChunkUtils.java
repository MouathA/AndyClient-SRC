package optifine;

import java.lang.reflect.*;
import net.minecraft.world.chunk.*;
import java.util.*;

public class ChunkUtils
{
    private static Field fieldHasEntities;
    private static boolean fieldHasEntitiesMissing;
    
    static {
        ChunkUtils.fieldHasEntities = null;
        ChunkUtils.fieldHasEntitiesMissing = false;
    }
    
    public static boolean hasEntities(final Chunk chunk) {
        if (ChunkUtils.fieldHasEntities == null) {
            if (ChunkUtils.fieldHasEntitiesMissing) {
                return true;
            }
            ChunkUtils.fieldHasEntities = findFieldHasEntities(chunk);
            if (ChunkUtils.fieldHasEntities == null) {
                return ChunkUtils.fieldHasEntitiesMissing = true;
            }
        }
        return ChunkUtils.fieldHasEntities.getBoolean(chunk);
    }
    
    private static Field findFieldHasEntities(final Chunk chunk) {
        final ArrayList<Field> list = new ArrayList<Field>();
        final ArrayList<Boolean> list2 = new ArrayList<Boolean>();
        final Field[] declaredFields = Chunk.class.getDeclaredFields();
        while (0 < declaredFields.length) {
            final Field field = declaredFields[0];
            if (field.getType() == Boolean.TYPE) {
                field.setAccessible(true);
                list.add(field);
                list2.add((Boolean)field.get(chunk));
            }
            int n = 0;
            ++n;
        }
        chunk.setHasEntities(false);
        final ArrayList<Boolean> list3 = new ArrayList<Boolean>();
        final Iterator<Field> iterator = list.iterator();
        while (iterator.hasNext()) {
            list3.add((Boolean)iterator.next().get(chunk));
        }
        chunk.setHasEntities(true);
        final ArrayList<Boolean> list4 = new ArrayList<Boolean>();
        final Iterator<Field> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            list4.add((Boolean)iterator2.next().get(chunk));
        }
        final ArrayList<Field> list5 = new ArrayList<Field>();
        while (0 < list.size()) {
            final Field field2 = list.get(0);
            final Boolean b = list3.get(0);
            final Boolean b2 = list4.get(0);
            if (!b && b2) {
                list5.add(field2);
                field2.set(chunk, list2.get(0));
            }
            int n2 = 0;
            ++n2;
        }
        if (list5.size() == 1) {
            return list5.get(0);
        }
        Config.warn("Error finding Chunk.hasEntities");
        return null;
    }
}
