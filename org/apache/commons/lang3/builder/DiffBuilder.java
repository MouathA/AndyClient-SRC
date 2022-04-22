package org.apache.commons.lang3.builder;

import java.util.*;
import org.apache.commons.lang3.*;

public class DiffBuilder implements Builder
{
    private final List diffs;
    private final boolean objectsTriviallyEqual;
    private final Object left;
    private final Object right;
    private final ToStringStyle style;
    
    public DiffBuilder(final Object left, final Object right, final ToStringStyle style) {
        if (left == null) {
            throw new IllegalArgumentException("lhs cannot be null");
        }
        if (right == null) {
            throw new IllegalArgumentException("rhs cannot be null");
        }
        this.diffs = new ArrayList();
        this.left = left;
        this.right = right;
        this.style = style;
        this.objectsTriviallyEqual = (left == right || left.equals(right));
    }
    
    public DiffBuilder append(final String s, final boolean b, final boolean b2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (b != b2) {
            this.diffs.add(new Diff(s, b, b2) {
                private static final long serialVersionUID = 1L;
                final boolean val$lhs;
                final boolean val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Boolean getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Boolean getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final boolean[] array, final boolean[] array2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final boolean[] val$lhs;
                final boolean[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Boolean[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }
                
                @Override
                public Boolean[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final byte b, final byte b2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (b != b2) {
            this.diffs.add(new Diff(s, b, b2) {
                private static final long serialVersionUID = 1L;
                final byte val$lhs;
                final byte val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Byte getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Byte getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final byte[] array, final byte[] array2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final byte[] val$lhs;
                final byte[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Byte[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }
                
                @Override
                public Byte[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final char c, final char c2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (c != c2) {
            this.diffs.add(new Diff(s, c, c2) {
                private static final long serialVersionUID = 1L;
                final char val$lhs;
                final char val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Character getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Character getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final char[] array, final char[] array2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final char[] val$lhs;
                final char[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Character[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }
                
                @Override
                public Character[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final double n, final double n2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (Double.doubleToLongBits(n) != Double.doubleToLongBits(n2)) {
            this.diffs.add(new Diff(s, n, n2) {
                private static final long serialVersionUID = 1L;
                final double val$lhs;
                final double val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Double getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Double getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final double[] array, final double[] array2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final double[] val$lhs;
                final double[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Double[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }
                
                @Override
                public Double[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final float n, final float n2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (Float.floatToIntBits(n) != Float.floatToIntBits(n2)) {
            this.diffs.add(new Diff(s, n, n2) {
                private static final long serialVersionUID = 1L;
                final float val$lhs;
                final float val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Float getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Float getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final float[] array, final float[] array2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final float[] val$lhs;
                final float[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Float[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }
                
                @Override
                public Float[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final int n, final int n2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (n != n2) {
            this.diffs.add(new Diff(s, n, n2) {
                private static final long serialVersionUID = 1L;
                final int val$lhs;
                final int val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Integer getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Integer getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final int[] array, final int[] array2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final int[] val$lhs;
                final int[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Integer[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }
                
                @Override
                public Integer[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final long n, final long n2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (n != n2) {
            this.diffs.add(new Diff(s, n, n2) {
                private static final long serialVersionUID = 1L;
                final long val$lhs;
                final long val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Long getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Long getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final long[] array, final long[] array2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final long[] val$lhs;
                final long[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Long[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }
                
                @Override
                public Long[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final short n, final short n2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (n != n2) {
            this.diffs.add(new Diff(s, n, n2) {
                private static final long serialVersionUID = 1L;
                final short val$lhs;
                final short val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Short getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Short getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final short[] array, final short[] array2) {
        if (s == null) {
            throw new IllegalArgumentException("Field name cannot be null");
        }
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final short[] val$lhs;
                final short[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Short[] getLeft() {
                    return ArrayUtils.toObject(this.val$lhs);
                }
                
                @Override
                public Short[] getRight() {
                    return ArrayUtils.toObject(this.val$rhs);
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    public DiffBuilder append(final String s, final Object o, final Object o2) {
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (o == o2) {
            return this;
        }
        Object o3;
        if (o != null) {
            o3 = o;
        }
        else {
            o3 = o2;
        }
        if (!o3.getClass().isArray()) {
            this.diffs.add(new Diff(s, o, o2) {
                private static final long serialVersionUID = 1L;
                final Object val$lhs;
                final Object val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Object getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Object getRight() {
                    return this.val$rhs;
                }
            });
            return this;
        }
        if (o3 instanceof boolean[]) {
            return this.append(s, (boolean[])o, (boolean[])o2);
        }
        if (o3 instanceof byte[]) {
            return this.append(s, (byte[])o, (byte[])o2);
        }
        if (o3 instanceof char[]) {
            return this.append(s, (char[])o, (char[])o2);
        }
        if (o3 instanceof double[]) {
            return this.append(s, (double[])o, (double[])o2);
        }
        if (o3 instanceof float[]) {
            return this.append(s, (float[])o, (float[])o2);
        }
        if (o3 instanceof int[]) {
            return this.append(s, (int[])o, (int[])o2);
        }
        if (o3 instanceof long[]) {
            return this.append(s, (long[])o, (long[])o2);
        }
        if (o3 instanceof short[]) {
            return this.append(s, (short[])o, (short[])o2);
        }
        return this.append(s, (Object[])o, (Object[])o2);
    }
    
    public DiffBuilder append(final String s, final Object[] array, final Object[] array2) {
        if (this.objectsTriviallyEqual) {
            return this;
        }
        if (!Arrays.equals(array, array2)) {
            this.diffs.add(new Diff(s, array, array2) {
                private static final long serialVersionUID = 1L;
                final Object[] val$lhs;
                final Object[] val$rhs;
                final DiffBuilder this$0;
                
                @Override
                public Object[] getLeft() {
                    return this.val$lhs;
                }
                
                @Override
                public Object[] getRight() {
                    return this.val$rhs;
                }
                
                @Override
                public Object getRight() {
                    return this.getRight();
                }
                
                @Override
                public Object getLeft() {
                    return this.getLeft();
                }
            });
        }
        return this;
    }
    
    @Override
    public DiffResult build() {
        return new DiffResult(this.left, this.right, this.diffs, this.style);
    }
    
    @Override
    public Object build() {
        return this.build();
    }
}
