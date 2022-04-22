package org.apache.commons.lang3;

public enum JavaVersion
{
    JAVA_0_9("JAVA_0_9", 0, 1.5f, "0.9"), 
    JAVA_1_1("JAVA_1_1", 1, 1.1f, "1.1"), 
    JAVA_1_2("JAVA_1_2", 2, 1.2f, "1.2"), 
    JAVA_1_3("JAVA_1_3", 3, 1.3f, "1.3"), 
    JAVA_1_4("JAVA_1_4", 4, 1.4f, "1.4"), 
    JAVA_1_5("JAVA_1_5", 5, 1.5f, "1.5"), 
    JAVA_1_6("JAVA_1_6", 6, 1.6f, "1.6"), 
    JAVA_1_7("JAVA_1_7", 7, 1.7f, "1.7"), 
    JAVA_1_8("JAVA_1_8", 8, 1.8f, "1.8");
    
    private final float value;
    private final String name;
    private static final JavaVersion[] $VALUES;
    
    private JavaVersion(final String s, final int n, final float value, final String name) {
        this.value = value;
        this.name = name;
    }
    
    public boolean atLeast(final JavaVersion javaVersion) {
        return this.value >= javaVersion.value;
    }
    
    static JavaVersion getJavaVersion(final String s) {
        return get(s);
    }
    
    static JavaVersion get(final String s) {
        if ("0.9".equals(s)) {
            return JavaVersion.JAVA_0_9;
        }
        if ("1.1".equals(s)) {
            return JavaVersion.JAVA_1_1;
        }
        if ("1.2".equals(s)) {
            return JavaVersion.JAVA_1_2;
        }
        if ("1.3".equals(s)) {
            return JavaVersion.JAVA_1_3;
        }
        if ("1.4".equals(s)) {
            return JavaVersion.JAVA_1_4;
        }
        if ("1.5".equals(s)) {
            return JavaVersion.JAVA_1_5;
        }
        if ("1.6".equals(s)) {
            return JavaVersion.JAVA_1_6;
        }
        if ("1.7".equals(s)) {
            return JavaVersion.JAVA_1_7;
        }
        if ("1.8".equals(s)) {
            return JavaVersion.JAVA_1_8;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        $VALUES = new JavaVersion[] { JavaVersion.JAVA_0_9, JavaVersion.JAVA_1_1, JavaVersion.JAVA_1_2, JavaVersion.JAVA_1_3, JavaVersion.JAVA_1_4, JavaVersion.JAVA_1_5, JavaVersion.JAVA_1_6, JavaVersion.JAVA_1_7, JavaVersion.JAVA_1_8 };
    }
}
