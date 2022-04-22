package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;
import com.sun.jna.*;

public interface ShellAPI extends StdCallLibrary
{
    public static final int FO_MOVE = 1;
    public static final int FO_COPY = 2;
    public static final int FO_DELETE = 3;
    public static final int FO_RENAME = 4;
    public static final int FOF_MULTIDESTFILES = 1;
    public static final int FOF_CONFIRMMOUSE = 2;
    public static final int FOF_SILENT = 4;
    public static final int FOF_RENAMEONCOLLISION = 8;
    public static final int FOF_NOCONFIRMATION = 16;
    public static final int FOF_WANTMAPPINGHANDLE = 32;
    public static final int FOF_ALLOWUNDO = 64;
    public static final int FOF_FILESONLY = 128;
    public static final int FOF_SIMPLEPROGRESS = 256;
    public static final int FOF_NOCONFIRMMKDIR = 512;
    public static final int FOF_NOERRORUI = 1024;
    public static final int FOF_NOCOPYSECURITYATTRIBS = 2048;
    public static final int FOF_NORECURSION = 4096;
    public static final int FOF_NO_CONNECTED_ELEMENTS = 8192;
    public static final int FOF_WANTNUKEWARNING = 16384;
    public static final int FOF_NORECURSEREPARSE = 32768;
    public static final int FOF_NO_UI = 1556;
    public static final int PO_DELETE = 19;
    public static final int PO_RENAME = 20;
    public static final int PO_PORTCHANGE = 32;
    public static final int PO_REN_PORT = 52;
    
    default static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            10
        //     6: iconst_0       
        //     7: goto            10
        //    10: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0010 (coming from #0007).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static class SHFILEOPSTRUCT extends Structure
    {
        public WinNT.HANDLE hwnd;
        public int wFunc;
        public WString pFrom;
        public WString pTo;
        public short fFlags;
        public boolean fAnyOperationsAborted;
        public Pointer pNameMappings;
        public WString lpszProgressTitle;
        
        public String encodePaths(final String[] array) {
            String string = "";
            while (0 < array.length) {
                string = string + array[0] + "\u0000";
                int n = 0;
                ++n;
            }
            return string + "\u0000";
        }
    }
}
