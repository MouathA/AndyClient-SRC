package net.minecraft.util;

import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import java.nio.charset.*;

public enum EnumFacing implements IStringSerializable
{
    DOWN("DOWN", 0, "DOWN", 0, 0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)), 
    UP("UP", 1, "UP", 1, 1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0)), 
    NORTH("NORTH", 2, "NORTH", 2, 2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1)), 
    SOUTH("SOUTH", 3, "SOUTH", 3, 3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1)), 
    WEST("WEST", 4, "WEST", 4, 4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0)), 
    EAST("EAST", 5, "EAST", 5, 5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));
    
    private final int index;
    private final int opposite;
    private final int horizontalIndex;
    private final String name;
    private final Axis axis;
    private final AxisDirection axisDirection;
    private final Vec3i directionVec;
    public static final EnumFacing[] VALUES;
    private static final EnumFacing[] HORIZONTALS;
    private static final Map NAME_LOOKUP;
    private static final String __OBFID;
    private static final EnumFacing[] $VALUES;
    private static final EnumFacing[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00001201";
        ENUM$VALUES = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST };
        VALUES = new EnumFacing[6];
        HORIZONTALS = new EnumFacing[4];
        NAME_LOOKUP = Maps.newHashMap();
        $VALUES = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST };
        final EnumFacing[] values = values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            EnumFacing.VALUES[enumFacing.index] = enumFacing;
            if (enumFacing.getAxis().isHorizontal()) {
                EnumFacing.HORIZONTALS[enumFacing.horizontalIndex] = enumFacing;
            }
            EnumFacing.NAME_LOOKUP.put(enumFacing.getName2().toLowerCase(), enumFacing);
            int n = 0;
            ++n;
        }
    }
    
    private EnumFacing(final String s, final int n, final String s2, final int n2, final int index, final int opposite, final int horizontalIndex, final String name, final AxisDirection axisDirection, final Axis axis, final Vec3i directionVec) {
        this.index = index;
        this.horizontalIndex = horizontalIndex;
        this.opposite = opposite;
        this.name = name;
        this.axis = axis;
        this.axisDirection = axisDirection;
        this.directionVec = directionVec;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }
    
    public AxisDirection getAxisDirection() {
        return this.axisDirection;
    }
    
    public EnumFacing getOpposite() {
        return EnumFacing.VALUES[this.opposite];
    }
    
    public EnumFacing rotateAround(final Axis axis) {
        switch (SwitchPlane.AXIS_LOOKUP[axis.ordinal()]) {
            case 1: {
                if (this != EnumFacing.WEST && this != EnumFacing.EAST) {
                    return this.rotateX();
                }
                return this;
            }
            case 2: {
                if (this != EnumFacing.UP && this != EnumFacing.DOWN) {
                    return this.rotateY();
                }
                return this;
            }
            case 3: {
                if (this != EnumFacing.NORTH && this != EnumFacing.SOUTH) {
                    return this.rotateZ();
                }
                return this;
            }
            default: {
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
            }
        }
    }
    
    public EnumFacing rotateY() {
        switch (SwitchPlane.FACING_LOOKUP[this.ordinal()]) {
            case 1: {
                return EnumFacing.EAST;
            }
            case 2: {
                return EnumFacing.SOUTH;
            }
            case 3: {
                return EnumFacing.WEST;
            }
            case 4: {
                return EnumFacing.NORTH;
            }
            default: {
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
            }
        }
    }
    
    private EnumFacing rotateX() {
        switch (SwitchPlane.FACING_LOOKUP[this.ordinal()]) {
            case 1: {
                return EnumFacing.DOWN;
            }
            default: {
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
            }
            case 3: {
                return EnumFacing.UP;
            }
            case 5: {
                return EnumFacing.NORTH;
            }
            case 6: {
                return EnumFacing.SOUTH;
            }
        }
    }
    
    private EnumFacing rotateZ() {
        switch (SwitchPlane.FACING_LOOKUP[this.ordinal()]) {
            case 2: {
                return EnumFacing.DOWN;
            }
            default: {
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            }
            case 4: {
                return EnumFacing.UP;
            }
            case 5: {
                return EnumFacing.EAST;
            }
            case 6: {
                return EnumFacing.WEST;
            }
        }
    }
    
    public EnumFacing rotateYCCW() {
        switch (SwitchPlane.FACING_LOOKUP[this.ordinal()]) {
            case 1: {
                return EnumFacing.WEST;
            }
            case 2: {
                return EnumFacing.NORTH;
            }
            case 3: {
                return EnumFacing.EAST;
            }
            case 4: {
                return EnumFacing.SOUTH;
            }
            default: {
                throw new IllegalStateException("Unable to get CCW facing of " + this);
            }
        }
    }
    
    public int getFrontOffsetX() {
        return (this.axis == Axis.X) ? this.axisDirection.getOffset() : 0;
    }
    
    public int getFrontOffsetY() {
        return (this.axis == Axis.Y) ? this.axisDirection.getOffset() : 0;
    }
    
    public int getFrontOffsetZ() {
        return (this.axis == Axis.Z) ? this.axisDirection.getOffset() : 0;
    }
    
    public String getName2() {
        return this.name;
    }
    
    public Axis getAxis() {
        return this.axis;
    }
    
    public static EnumFacing byName(final String s) {
        return (s == null) ? null : EnumFacing.NAME_LOOKUP.get(s.toLowerCase());
    }
    
    public static EnumFacing getFront(final int n) {
        return EnumFacing.VALUES[MathHelper.abs_int(n % EnumFacing.VALUES.length)];
    }
    
    public static EnumFacing getHorizontal(final int n) {
        return EnumFacing.HORIZONTALS[MathHelper.abs_int(n % EnumFacing.HORIZONTALS.length)];
    }
    
    public static EnumFacing fromAngle(final double n) {
        return getHorizontal(MathHelper.floor_double(n / 90.0 + 0.5) & 0x3);
    }
    
    public static EnumFacing random(final Random random) {
        return values()[random.nextInt(values().length)];
    }
    
    public static EnumFacing func_176737_a(final float n, final float n2, final float n3) {
        EnumFacing north = EnumFacing.NORTH;
        float n4 = Float.MIN_VALUE;
        final EnumFacing[] values = values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            final float n5 = n * enumFacing.directionVec.getX() + n2 * enumFacing.directionVec.getY() + n3 * enumFacing.directionVec.getZ();
            if (n5 > n4) {
                n4 = n5;
                north = enumFacing;
            }
            int n6 = 0;
            ++n6;
        }
        return north;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public Vec3i getDirectionVec() {
        return this.directionVec;
    }
    
    public enum Axis implements Predicate, IStringSerializable
    {
        X("X", 0, "X", 0, "X", 0, "x", Plane.HORIZONTAL), 
        Y("Y", 1, "Y", 1, "Y", 1, "y", Plane.VERTICAL), 
        Z("Z", 2, "Z", 2, "Z", 2, "z", Plane.HORIZONTAL);
        
        private static final Map NAME_LOOKUP;
        private final String name;
        private final Plane plane;
        private static final Axis[] $VALUES;
        private static final String __OBFID;
        private static final Axis[] $VALUES$;
        private static final Axis[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002321";
            ENUM$VALUES = new Axis[] { Axis.X, Axis.Y, Axis.Z };
            NAME_LOOKUP = Maps.newHashMap();
            $VALUES = new Axis[] { Axis.X, Axis.Y, Axis.Z };
            $VALUES$ = new Axis[] { Axis.X, Axis.Y, Axis.Z };
            final Axis[] values = values();
            while (0 < values.length) {
                final Axis axis = values[0];
                Axis.NAME_LOOKUP.put(axis.getName2().toLowerCase(), axis);
                int n = 0;
                ++n;
            }
        }
        
        private Axis(final String s, final int n, final String s2, final int n2, final String s3, final int n3, final String name, final Plane plane) {
            this.name = name;
            this.plane = plane;
        }
        
        public static Axis byName(final String s) {
            return (s == null) ? null : Axis.NAME_LOOKUP.get(s.toLowerCase());
        }
        
        public String getName2() {
            return this.name;
        }
        
        public boolean isVertical() {
            return this.plane == Plane.VERTICAL;
        }
        
        public boolean isHorizontal() {
            return this.plane == Plane.HORIZONTAL;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public boolean apply(final EnumFacing enumFacing) {
            return enumFacing != null && enumFacing.getAxis() == this;
        }
        
        public Plane getPlane() {
            return this.plane;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.apply((EnumFacing)o);
        }
    }
    
    public enum Plane implements Predicate, Iterable
    {
        HORIZONTAL("HORIZONTAL", 0, "HORIZONTAL", 0, "HORIZONTAL", 0), 
        VERTICAL("VERTICAL", 1, "VERTICAL", 1, "VERTICAL", 1);
        
        private static final Plane[] $VALUES;
        private static final String __OBFID;
        private static final Plane[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002319";
            ENUM$VALUES = new Plane[] { Plane.HORIZONTAL, Plane.VERTICAL };
            $VALUES = new Plane[] { Plane.HORIZONTAL, Plane.VERTICAL };
        }
        
        private Plane(final String s, final int n, final String s2, final int n2, final String s3, final int n3) {
        }
        
        public EnumFacing[] facings() {
            switch (SwitchPlane.PLANE_LOOKUP[this.ordinal()]) {
                case 1: {
                    return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
                }
                case 2: {
                    return new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
                }
                default: {
                    throw new Error("Someone's been tampering with the universe!");
                }
            }
        }
        
        public EnumFacing random(final Random random) {
            final EnumFacing[] facings = this.facings();
            return facings[random.nextInt(facings.length)];
        }
        
        public boolean apply(final EnumFacing enumFacing) {
            return enumFacing != null && enumFacing.getAxis().getPlane() == this;
        }
        
        @Override
        public Iterator iterator() {
            return Iterators.forArray((Object[])this.facings());
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.apply((EnumFacing)o);
        }
    }
    
    static final class SwitchPlane
    {
        static final int[] AXIS_LOOKUP;
        static final int[] FACING_LOOKUP;
        static final int[] PLANE_LOOKUP;
        private static final String __OBFID;
        private static final String[] lIllIIIIlIIIlIll;
        private static String[] lIllIIIIlIIIllII;
        
        static {
            lIIIIIIlllIllIlll();
            lIIIIIIlllIllIllI();
            __OBFID = SwitchPlane.lIllIIIIlIIIlIll[0];
            PLANE_LOOKUP = new int[Plane.values().length];
            try {
                SwitchPlane.PLANE_LOOKUP[Plane.HORIZONTAL.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchPlane.PLANE_LOOKUP[Plane.VERTICAL.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            FACING_LOOKUP = new int[EnumFacing.values().length];
            try {
                SwitchPlane.FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchPlane.FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchPlane.FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchPlane.FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchPlane.FACING_LOOKUP[EnumFacing.UP.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchPlane.FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            AXIS_LOOKUP = new int[Axis.values().length];
            try {
                SwitchPlane.AXIS_LOOKUP[Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchPlane.AXIS_LOOKUP[Axis.Y.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                SwitchPlane.AXIS_LOOKUP[Axis.Z.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
        }
        
        private static void lIIIIIIlllIllIllI() {
            (lIllIIIIlIIIlIll = new String[1])[0] = lIIIIIIlllIllIlIl(SwitchPlane.lIllIIIIlIIIllII[0], SwitchPlane.lIllIIIIlIIIllII[1]);
            SwitchPlane.lIllIIIIlIIIllII = null;
        }
        
        private static void lIIIIIIlllIllIlll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchPlane.lIllIIIIlIIIllII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIIIIIlllIllIlIl(String s, final String s2) {
            s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int n = 0;
            final char[] charArray2 = s.toCharArray();
            for (int length = charArray2.length, i = 0; i < length; ++i) {
                sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
                ++n;
            }
            return sb.toString();
        }
    }
    
    public enum AxisDirection
    {
        POSITIVE("POSITIVE", 0, "POSITIVE", 0, "POSITIVE", 0, 1, "Towards positive"), 
        NEGATIVE("NEGATIVE", 1, "NEGATIVE", 1, "NEGATIVE", 1, -1, "Towards negative");
        
        private final int offset;
        private final String description;
        private static final AxisDirection[] $VALUES;
        private static final String __OBFID;
        private static final AxisDirection[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002320";
            ENUM$VALUES = new AxisDirection[] { AxisDirection.POSITIVE, AxisDirection.NEGATIVE };
            $VALUES = new AxisDirection[] { AxisDirection.POSITIVE, AxisDirection.NEGATIVE };
        }
        
        private AxisDirection(final String s, final int n, final String s2, final int n2, final String s3, final int n3, final int offset, final String description) {
            this.offset = offset;
            this.description = description;
        }
        
        public int getOffset() {
            return this.offset;
        }
        
        @Override
        public String toString() {
            return this.description;
        }
    }
}
