package net.arikia.dev.drpc;

import java.io.*;
import com.sun.jna.*;

public final class DiscordRPC
{
    private static final String DLL_VERSION;
    private static final String LIB_VERSION;
    
    public static void discordInitialize(final String s, final DiscordEventHandlers discordEventHandlers, final boolean b) {
        DLL.INSTANCE.Discord_Initialize(s, discordEventHandlers, b ? 1 : 0, null);
    }
    
    public static void discordRegister(final String s, final String s2) {
        DLL.INSTANCE.Discord_Register(s, s2);
    }
    
    public static void discordInitialize(final String s, final DiscordEventHandlers discordEventHandlers, final boolean b, final String s2) {
        DLL.INSTANCE.Discord_Initialize(s, discordEventHandlers, b ? 1 : 0, s2);
    }
    
    public static void discordRegisterSteam(final String s, final String s2) {
        DLL.INSTANCE.Discord_RegisterSteamGame(s, s2);
    }
    
    public static void discordUpdateEventHandlers(final DiscordEventHandlers discordEventHandlers) {
        DLL.INSTANCE.Discord_UpdateHandlers(discordEventHandlers);
    }
    
    public static void discordShutdown() {
        DLL.INSTANCE.Discord_Shutdown();
    }
    
    public static void discordRunCallbacks() {
        DLL.INSTANCE.Discord_RunCallbacks();
    }
    
    public static void discordUpdatePresence(final DiscordRichPresence discordRichPresence) {
        DLL.INSTANCE.Discord_UpdatePresence(discordRichPresence);
    }
    
    public static void discordClearPresence() {
        DLL.INSTANCE.Discord_ClearPresence();
    }
    
    public static void discordRespond(final String s, final DiscordReply discordReply) {
        DLL.INSTANCE.Discord_Respond(s, discordReply.reply);
    }
    
    private static void loadDLL() {
        final String mapLibraryName = System.mapLibraryName("discord-rpc");
        final OSUtil osUtil = new OSUtil();
        String s;
        String s2;
        if (osUtil.isMac()) {
            final File file = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator);
            s = "darwin";
            s2 = file + File.separator + "discord-rpc" + File.separator + mapLibraryName;
        }
        else if (osUtil.isWindows()) {
            final File file2 = new File(System.getenv("TEMP"));
            s = (System.getProperty("sun.arch.data.model").equals("64") ? "win-x64" : "win-x86");
            s2 = file2 + File.separator + "discord-rpc" + File.separator + mapLibraryName;
        }
        else {
            final File file3 = new File(System.getProperty("user.home"), ".discord-rpc");
            s = "linux";
            s2 = file3 + File.separator + mapLibraryName;
        }
        final String string = "/" + s + "/" + mapLibraryName;
        final File file4 = new File(s2);
        final InputStream resourceAsStream = DiscordRPC.class.getResourceAsStream(string);
        final FileOutputStream openOutputStream = openOutputStream(file4);
        copyFile(resourceAsStream, openOutputStream);
        file4.deleteOnExit();
        if (openOutputStream != null) {
            openOutputStream.close();
        }
        if (resourceAsStream != null) {
            resourceAsStream.close();
        }
        System.load(file4.getAbsolutePath());
    }
    
    private static void copyFile(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final byte[] array = new byte[4096];
        int read;
        while (-1 != (read = inputStream.read(array))) {
            outputStream.write(array, 0, read);
        }
    }
    
    private static FileOutputStream openOutputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        }
        else {
            final File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Directory '" + parentFile + "' could not be created");
            }
        }
        return new FileOutputStream(file);
    }
    
    static {
        DLL_VERSION = "3.4.0";
        LIB_VERSION = "1.6.2";
    }
    
    private interface DLL extends Library
    {
        public static final DLL INSTANCE = (DLL)Native.loadLibrary("discord-rpc", DLL.class);
        
        void Discord_Initialize(final String p0, final DiscordEventHandlers p1, final int p2, final String p3);
        
        void Discord_Register(final String p0, final String p1);
        
        void Discord_RegisterSteamGame(final String p0, final String p1);
        
        void Discord_UpdateHandlers(final DiscordEventHandlers p0);
        
        void Discord_Shutdown();
        
        void Discord_RunCallbacks();
        
        void Discord_UpdatePresence(final DiscordRichPresence p0);
        
        void Discord_ClearPresence();
        
        void Discord_Respond(final String p0, final int p1);
    }
    
    public enum DiscordReply
    {
        NO("NO", 0, 0), 
        YES("YES", 1, 1), 
        IGNORE("IGNORE", 2, 2);
        
        public final int reply;
        private static final DiscordReply[] $VALUES;
        
        private DiscordReply(final String s, final int n, final int reply) {
            this.reply = reply;
        }
        
        static {
            $VALUES = new DiscordReply[] { DiscordReply.NO, DiscordReply.YES, DiscordReply.IGNORE };
        }
    }
}
