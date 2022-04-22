package net.minecraft.client.main;

import java.io.*;
import net.minecraft.client.*;
import java.net.*;
import com.mojang.authlib.properties.*;
import com.google.gson.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import joptsimple.*;
import java.util.*;

public class Main
{
    private static final String __OBFID;
    
    public static void main(final String[] array) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        final OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("demo");
        optionParser.accepts("fullscreen");
        optionParser.accepts("checkGlErrors");
        final ArgumentAcceptingOptionSpec withRequiredArg = optionParser.accepts("server").withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec defaultsTo2 = optionParser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), (Object[])new File[0]);
        final ArgumentAcceptingOptionSpec ofType = optionParser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        final ArgumentAcceptingOptionSpec ofType2 = optionParser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        final ArgumentAcceptingOptionSpec withRequiredArg2 = optionParser.accepts("proxyHost").withRequiredArg();
        final ArgumentAcceptingOptionSpec ofType3 = optionParser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", (Object[])new String[0]).ofType(Integer.class);
        final ArgumentAcceptingOptionSpec withRequiredArg3 = optionParser.accepts("proxyUser").withRequiredArg();
        final ArgumentAcceptingOptionSpec withRequiredArg4 = optionParser.accepts("proxyPass").withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo3 = optionParser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L, (Object[])new String[0]);
        final ArgumentAcceptingOptionSpec withRequiredArg5 = optionParser.accepts("uuid").withRequiredArg();
        final ArgumentAcceptingOptionSpec required = optionParser.accepts("accessToken").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec required2 = optionParser.accepts("version").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec defaultsTo4 = optionParser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec defaultsTo5 = optionParser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec required3 = optionParser.accepts("userProperties").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec withRequiredArg6 = optionParser.accepts("assetIndex").withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo6 = optionParser.accepts("userType").withRequiredArg().defaultsTo("legacy", (Object[])new String[0]);
        final NonOptionArgumentSpec nonOptions = optionParser.nonOptions();
        final OptionSet parse = optionParser.parse(array);
        final List values = parse.valuesOf(nonOptions);
        if (!values.isEmpty()) {
            System.out.println("Completely ignored arguments: " + values);
        }
        final String s = (String)parse.valueOf(withRequiredArg2);
        Proxy no_PROXY = Proxy.NO_PROXY;
        if (s != null) {
            no_PROXY = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(s, (int)parse.valueOf(ofType3)));
        }
        final String s2 = (String)parse.valueOf(withRequiredArg3);
        final String s3 = (String)parse.valueOf(withRequiredArg4);
        if (!no_PROXY.equals(Proxy.NO_PROXY) && func_110121_a(s2) && func_110121_a(s3)) {
            Authenticator.setDefault(new Authenticator(s3) {
                private static final String __OBFID;
                private final String val$var25;
                private final String val$var26;
                
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(this.val$var25, this.val$var26.toCharArray());
                }
                
                static {
                    __OBFID = "CL_00000828";
                }
            });
        }
        final int intValue = (int)parse.valueOf(defaultsTo4);
        final int intValue2 = (int)parse.valueOf(defaultsTo5);
        final boolean has = parse.has("fullscreen");
        final boolean has2 = parse.has("checkGlErrors");
        final boolean has3 = parse.has("demo");
        final String s4 = (String)parse.valueOf(required2);
        final PropertyMap propertyMap = (PropertyMap)new GsonBuilder().registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create().fromJson((String)parse.valueOf(required3), PropertyMap.class);
        final File file = (File)parse.valueOf(defaultsTo2);
        final GameConfiguration gameConfiguration = new GameConfiguration(new GameConfiguration.UserInformation(new Session((String)defaultsTo3.value(parse), parse.has(withRequiredArg5) ? ((String)withRequiredArg5.value(parse)) : ((String)defaultsTo3.value(parse)), (String)required.value(parse), (String)defaultsTo6.value(parse)), propertyMap, no_PROXY), new GameConfiguration.DisplayInformation(intValue, intValue2, has, has2), new GameConfiguration.FolderInformation(file, parse.has(ofType2) ? ((File)parse.valueOf(ofType2)) : new File(file, "resourcepacks/"), parse.has(ofType) ? ((File)parse.valueOf(ofType)) : new File(file, "assets/"), parse.has(withRequiredArg6) ? ((String)withRequiredArg6.value(parse)) : null), new GameConfiguration.GameInformation(has3, s4), new GameConfiguration.ServerInformation((String)parse.valueOf(withRequiredArg), (int)parse.valueOf(defaultsTo)));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            private static final String __OBFID;
            
            @Override
            public void run() {
            }
            
            static {
                __OBFID = "CL_00000829";
            }
        });
        Thread.currentThread().setName("Client thread");
        new Minecraft(gameConfiguration).run();
    }
    
    private static boolean func_110121_a(final String s) {
        return s != null && !s.isEmpty();
    }
    
    static {
        __OBFID = "CL_00001461";
    }
}
