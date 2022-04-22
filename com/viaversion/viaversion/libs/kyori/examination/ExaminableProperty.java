package com.viaversion.viaversion.libs.kyori.examination;

import org.jetbrains.annotations.*;

public abstract class ExaminableProperty
{
    private ExaminableProperty() {
    }
    
    @NotNull
    public abstract String name();
    
    @NotNull
    public abstract Object examine(@NotNull final Examiner examiner);
    
    @Override
    public String toString() {
        return "ExaminableProperty{" + this.name() + "}";
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, @Nullable final Object value) {
        return new ExaminableProperty() {
            final String val$name;
            final Object val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, @Nullable final String value) {
        return new ExaminableProperty() {
            final String val$name;
            final String val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final boolean value) {
        return new ExaminableProperty() {
            final String val$name;
            final boolean val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final boolean[] value) {
        return new ExaminableProperty() {
            final String val$name;
            final boolean[] val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final byte value) {
        return new ExaminableProperty() {
            final String val$name;
            final byte val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final byte[] value) {
        return new ExaminableProperty() {
            final String val$name;
            final byte[] val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final char value) {
        return new ExaminableProperty() {
            final String val$name;
            final char val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final char[] value) {
        return new ExaminableProperty() {
            final String val$name;
            final char[] val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final double value) {
        return new ExaminableProperty() {
            final String val$name;
            final double val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final double[] value) {
        return new ExaminableProperty() {
            final String val$name;
            final double[] val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final float value) {
        return new ExaminableProperty() {
            final String val$name;
            final float val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final float[] value) {
        return new ExaminableProperty() {
            final String val$name;
            final float[] val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final int value) {
        return new ExaminableProperty() {
            final String val$name;
            final int val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final int[] value) {
        return new ExaminableProperty() {
            final String val$name;
            final int[] val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final long value) {
        return new ExaminableProperty() {
            final String val$name;
            final long val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final long[] value) {
        return new ExaminableProperty() {
            final String val$name;
            final long[] val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final short value) {
        return new ExaminableProperty() {
            final String val$name;
            final short val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    @NotNull
    public static ExaminableProperty of(@NotNull final String name, final short[] value) {
        return new ExaminableProperty() {
            final String val$name;
            final short[] val$value;
            
            @NotNull
            @Override
            public String name() {
                return this.val$name;
            }
            
            @NotNull
            @Override
            public Object examine(@NotNull final Examiner examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }
    
    ExaminableProperty(final ExaminableProperty$1 examinableProperty) {
        this();
    }
}
