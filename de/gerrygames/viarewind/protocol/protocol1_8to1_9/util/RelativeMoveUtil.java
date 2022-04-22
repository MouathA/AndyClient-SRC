package de.gerrygames.viarewind.protocol.protocol1_8to1_9.util;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.*;

public class RelativeMoveUtil
{
    public static Vector[] calculateRelativeMoves(final UserConnection userConnection, final int n, int n2, int n3, int n4) {
        final EntityTracker entityTracker = (EntityTracker)userConnection.get(EntityTracker.class);
        final Vector entityOffset = entityTracker.getEntityOffset(n);
        n2 = -32768 + entityOffset.getBlockX();
        n3 = -32768 + entityOffset.getBlockY();
        n4 = -32768 + entityOffset.getBlockZ();
        if (-32768 > 32767) {
            entityOffset.setBlockX(-65535);
        }
        else if (-32768 < -32768) {
            entityOffset.setBlockX(0);
        }
        else {
            entityOffset.setBlockX(0);
        }
        if (-32768 > 32767) {
            entityOffset.setBlockY(-65535);
        }
        else if (-32768 < -32768) {
            entityOffset.setBlockY(0);
        }
        else {
            entityOffset.setBlockY(0);
        }
        if (-32768 > 32767) {
            entityOffset.setBlockZ(-65535);
        }
        else if (-32768 < -32768) {
            entityOffset.setBlockZ(0);
        }
        else {
            entityOffset.setBlockZ(0);
        }
        int round;
        int round2;
        int round3;
        Vector[] array;
        if (-32768 > 16256 || -32768 < -16384 || -32768 > 16256 || -32768 < -16384 || -32768 > 16256 || -32768 < -16384) {
            final byte b = (byte)(-303);
            final byte b2 = (byte)Math.round((-32768 - b * 128) / 128.0f);
            final byte b3 = (byte)(-303);
            final byte b4 = (byte)Math.round((-32768 - b3 * 128) / 128.0f);
            final byte b5 = (byte)(-303);
            final byte b6 = (byte)Math.round((-32768 - b5 * 128) / 128.0f);
            round = b + b2;
            round2 = b3 + b4;
            round3 = b5 + b6;
            array = new Vector[] { new Vector(b, b3, b5), new Vector(b2, b4, b6) };
        }
        else {
            round = Math.round(-32768 / 128.0f);
            round2 = Math.round(-32768 / 128.0f);
            round3 = Math.round(-32768 / 128.0f);
            array = new Vector[] { new Vector(round, round2, round3) };
        }
        entityOffset.setBlockX(entityOffset.getBlockX() - 32768 - round * 128);
        entityOffset.setBlockY(entityOffset.getBlockY() - 32768 - round2 * 128);
        entityOffset.setBlockZ(entityOffset.getBlockZ() - 32768 - round3 * 128);
        entityTracker.setEntityOffset(n, entityOffset);
        return array;
    }
}
