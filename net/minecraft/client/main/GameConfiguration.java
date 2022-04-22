package net.minecraft.client.main;

import java.io.*;
import net.minecraft.util.*;
import com.mojang.authlib.properties.*;
import java.net.*;

public class GameConfiguration
{
    public final UserInformation field_178745_a;
    public final DisplayInformation field_178743_b;
    public final FolderInformation field_178744_c;
    public final GameInformation field_178741_d;
    public final ServerInformation field_178742_e;
    private static final String __OBFID;
    
    public GameConfiguration(final UserInformation field_178745_a, final DisplayInformation field_178743_b, final FolderInformation field_178744_c, final GameInformation field_178741_d, final ServerInformation field_178742_e) {
        this.field_178745_a = field_178745_a;
        this.field_178743_b = field_178743_b;
        this.field_178744_c = field_178744_c;
        this.field_178741_d = field_178741_d;
        this.field_178742_e = field_178742_e;
    }
    
    static {
        __OBFID = "CL_00001918";
    }
    
    public static class DisplayInformation
    {
        public final int field_178764_a;
        public final int field_178762_b;
        public final boolean field_178763_c;
        public final boolean field_178761_d;
        private static final String __OBFID;
        
        public DisplayInformation(final int field_178764_a, final int field_178762_b, final boolean field_178763_c, final boolean field_178761_d) {
            this.field_178764_a = field_178764_a;
            this.field_178762_b = field_178762_b;
            this.field_178763_c = field_178763_c;
            this.field_178761_d = field_178761_d;
        }
        
        static {
            __OBFID = "CL_00001917";
        }
    }
    
    public static class FolderInformation
    {
        public final File field_178760_a;
        public final File field_178758_b;
        public final File field_178759_c;
        public final String field_178757_d;
        private static final String __OBFID;
        
        public FolderInformation(final File field_178760_a, final File field_178758_b, final File field_178759_c, final String field_178757_d) {
            this.field_178760_a = field_178760_a;
            this.field_178758_b = field_178758_b;
            this.field_178759_c = field_178759_c;
            this.field_178757_d = field_178757_d;
        }
        
        static {
            __OBFID = "CL_00001916";
        }
    }
    
    public static class GameInformation
    {
        public final boolean field_178756_a;
        public final String field_178755_b;
        private static final String __OBFID;
        
        public GameInformation(final boolean field_178756_a, final String field_178755_b) {
            this.field_178756_a = field_178756_a;
            this.field_178755_b = field_178755_b;
        }
        
        static {
            __OBFID = "CL_00001915";
        }
    }
    
    public static class ServerInformation
    {
        public final String field_178754_a;
        public final int field_178753_b;
        private static final String __OBFID;
        
        public ServerInformation(final String field_178754_a, final int field_178753_b) {
            this.field_178754_a = field_178754_a;
            this.field_178753_b = field_178753_b;
        }
        
        static {
            __OBFID = "CL_00001914";
        }
    }
    
    public static class UserInformation
    {
        public final Session field_178752_a;
        public final PropertyMap field_178750_b;
        public final Proxy field_178751_c;
        private static final String __OBFID;
        
        public UserInformation(final Session field_178752_a, final PropertyMap field_178750_b, final Proxy field_178751_c) {
            this.field_178752_a = field_178752_a;
            this.field_178750_b = field_178750_b;
            this.field_178751_c = field_178751_c;
        }
        
        static {
            __OBFID = "CL_00001913";
        }
    }
}
