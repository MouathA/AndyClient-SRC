package com.google.common.base;

import com.google.common.annotations.*;

@Beta
@GwtIncompatible("java.lang.System#getProperty")
public enum StandardSystemProperty
{
    JAVA_VERSION("JAVA_VERSION", 0, "java.version"), 
    JAVA_VENDOR("JAVA_VENDOR", 1, "java.vendor"), 
    JAVA_VENDOR_URL("JAVA_VENDOR_URL", 2, "java.vendor.url"), 
    JAVA_HOME("JAVA_HOME", 3, "java.home"), 
    JAVA_VM_SPECIFICATION_VERSION("JAVA_VM_SPECIFICATION_VERSION", 4, "java.vm.specification.version"), 
    JAVA_VM_SPECIFICATION_VENDOR("JAVA_VM_SPECIFICATION_VENDOR", 5, "java.vm.specification.vendor"), 
    JAVA_VM_SPECIFICATION_NAME("JAVA_VM_SPECIFICATION_NAME", 6, "java.vm.specification.name"), 
    JAVA_VM_VERSION("JAVA_VM_VERSION", 7, "java.vm.version"), 
    JAVA_VM_VENDOR("JAVA_VM_VENDOR", 8, "java.vm.vendor"), 
    JAVA_VM_NAME("JAVA_VM_NAME", 9, "java.vm.name"), 
    JAVA_SPECIFICATION_VERSION("JAVA_SPECIFICATION_VERSION", 10, "java.specification.version"), 
    JAVA_SPECIFICATION_VENDOR("JAVA_SPECIFICATION_VENDOR", 11, "java.specification.vendor"), 
    JAVA_SPECIFICATION_NAME("JAVA_SPECIFICATION_NAME", 12, "java.specification.name"), 
    JAVA_CLASS_VERSION("JAVA_CLASS_VERSION", 13, "java.class.version"), 
    JAVA_CLASS_PATH("JAVA_CLASS_PATH", 14, "java.class.path"), 
    JAVA_LIBRARY_PATH("JAVA_LIBRARY_PATH", 15, "java.library.path"), 
    JAVA_IO_TMPDIR("JAVA_IO_TMPDIR", 16, "java.io.tmpdir"), 
    JAVA_COMPILER("JAVA_COMPILER", 17, "java.compiler"), 
    JAVA_EXT_DIRS("JAVA_EXT_DIRS", 18, "java.ext.dirs"), 
    OS_NAME("OS_NAME", 19, "os.name"), 
    OS_ARCH("OS_ARCH", 20, "os.arch"), 
    OS_VERSION("OS_VERSION", 21, "os.version"), 
    FILE_SEPARATOR("FILE_SEPARATOR", 22, "file.separator"), 
    PATH_SEPARATOR("PATH_SEPARATOR", 23, "path.separator"), 
    LINE_SEPARATOR("LINE_SEPARATOR", 24, "line.separator"), 
    USER_NAME("USER_NAME", 25, "user.name"), 
    USER_HOME("USER_HOME", 26, "user.home"), 
    USER_DIR("USER_DIR", 27, "user.dir");
    
    private final String key;
    private static final StandardSystemProperty[] $VALUES;
    
    private StandardSystemProperty(final String s, final int n, final String key) {
        this.key = key;
    }
    
    public String key() {
        return this.key;
    }
    
    public String value() {
        return System.getProperty(this.key);
    }
    
    @Override
    public String toString() {
        return this.key() + "=" + this.value();
    }
    
    static {
        $VALUES = new StandardSystemProperty[] { StandardSystemProperty.JAVA_VERSION, StandardSystemProperty.JAVA_VENDOR, StandardSystemProperty.JAVA_VENDOR_URL, StandardSystemProperty.JAVA_HOME, StandardSystemProperty.JAVA_VM_SPECIFICATION_VERSION, StandardSystemProperty.JAVA_VM_SPECIFICATION_VENDOR, StandardSystemProperty.JAVA_VM_SPECIFICATION_NAME, StandardSystemProperty.JAVA_VM_VERSION, StandardSystemProperty.JAVA_VM_VENDOR, StandardSystemProperty.JAVA_VM_NAME, StandardSystemProperty.JAVA_SPECIFICATION_VERSION, StandardSystemProperty.JAVA_SPECIFICATION_VENDOR, StandardSystemProperty.JAVA_SPECIFICATION_NAME, StandardSystemProperty.JAVA_CLASS_VERSION, StandardSystemProperty.JAVA_CLASS_PATH, StandardSystemProperty.JAVA_LIBRARY_PATH, StandardSystemProperty.JAVA_IO_TMPDIR, StandardSystemProperty.JAVA_COMPILER, StandardSystemProperty.JAVA_EXT_DIRS, StandardSystemProperty.OS_NAME, StandardSystemProperty.OS_ARCH, StandardSystemProperty.OS_VERSION, StandardSystemProperty.FILE_SEPARATOR, StandardSystemProperty.PATH_SEPARATOR, StandardSystemProperty.LINE_SEPARATOR, StandardSystemProperty.USER_NAME, StandardSystemProperty.USER_HOME, StandardSystemProperty.USER_DIR };
    }
}
