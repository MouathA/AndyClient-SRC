package wdl.update;

import wdl.*;
import java.util.*;

public class WDLUpdateChecker extends Thread
{
    private static boolean started;
    private static boolean finished;
    private static boolean failed;
    private static String failReason;
    private static List releases;
    private static Release runningRelease;
    private static final String FORUMS_THREAD_USAGE_LINK;
    private static final String WIKI_LINK;
    private static final String GITHUB_LINK;
    private static final String REDISTRIBUTION_LINK;
    private static final String SMR_LINK;
    
    static {
        FORUMS_THREAD_USAGE_LINK = "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465#Usage";
        GITHUB_LINK = "https://github.com/pokechu22/WorldDownloader";
        WIKI_LINK = "https://github.com/pokechu22/WorldDownloader/wiki";
        SMR_LINK = "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/mods-discussion/2314237";
        REDISTRIBUTION_LINK = "http://pokechu22.github.io/WorldDownloader/redistribution";
        WDLUpdateChecker.started = false;
        WDLUpdateChecker.finished = false;
        WDLUpdateChecker.failed = false;
        WDLUpdateChecker.failReason = null;
    }
    
    public static List getReleases() {
        return WDLUpdateChecker.releases;
    }
    
    public static Release getRunningRelease() {
        return WDLUpdateChecker.runningRelease;
    }
    
    public static Release getRecomendedRelease() {
        if (WDLUpdateChecker.releases == null) {
            return null;
        }
        if (WDLUpdateChecker.runningRelease == null) {
            return null;
        }
        final String minecraftVersion = WDL.getMinecraftVersion();
        final boolean equals = WDL.globalProps.getProperty("UpdateAllowBetas").equals("true");
        final boolean equals2 = WDL.globalProps.getProperty("UpdateMinecraftVersion").equals("client");
        final boolean equals3 = WDL.globalProps.getProperty("UpdateMinecraftVersion").equals("server");
        for (final Release release : WDLUpdateChecker.releases) {
            if (release.hiddenInfo != null) {
                if (release.prerelease && !equals) {
                    continue;
                }
                if (equals2) {
                    if (!release.hiddenInfo.mainMinecraftVersion.equals(minecraftVersion)) {
                        continue;
                    }
                }
                else if (equals3) {
                    String[] supportedMinecraftVersions;
                    while (0 < (supportedMinecraftVersions = release.hiddenInfo.supportedMinecraftVersions).length && !supportedMinecraftVersions[0].equals(minecraftVersion)) {
                        int n = 0;
                        ++n;
                    }
                    if (!true) {
                        continue;
                    }
                }
                if (WDLUpdateChecker.releases.indexOf(release) > WDLUpdateChecker.releases.indexOf(WDLUpdateChecker.runningRelease)) {
                    continue;
                }
                return release;
            }
        }
        return null;
    }
    
    public static boolean hasNewVersion() {
        if (WDLUpdateChecker.runningRelease == null) {
            return false;
        }
        final Release recomendedRelease = getRecomendedRelease();
        return recomendedRelease != null && WDLUpdateChecker.runningRelease != recomendedRelease;
    }
    
    public static void startIfNeeded() {
        if (!WDLUpdateChecker.started) {
            WDLUpdateChecker.started = true;
            new WDLUpdateChecker().start();
        }
    }
    
    public static boolean hasFinishedUpdateCheck() {
        return WDLUpdateChecker.finished;
    }
    
    public static boolean hasUpdateCheckFailed() {
        return WDLUpdateChecker.failed;
    }
    
    public static String getUpdateCheckFailReason() {
        return WDLUpdateChecker.failReason;
    }
    
    private WDLUpdateChecker() {
        super("WorldDownloader update check thread");
    }
    
    @Override
    public void run() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ldc             "TutorialShown"
        //     5: invokevirtual   java/util/Properties.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //     8: ldc             "true"
        //    10: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    13: ifne            488
        //    16: ldc2_w          5000
        //    19: invokestatic    wdl/update/WDLUpdateChecker.sleep:(J)V
        //    22: new             Lnet/minecraft/util/ChatComponentTranslation;
        //    25: dup            
        //    26: ldc             "wdl.intro.success"
        //    28: iconst_0       
        //    29: anewarray       Ljava/lang/Object;
        //    32: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //    35: astore_1       
        //    36: new             Lnet/minecraft/util/ChatComponentTranslation;
        //    39: dup            
        //    40: ldc             "wdl.intro.forumsLink"
        //    42: iconst_0       
        //    43: anewarray       Ljava/lang/Object;
        //    46: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //    49: astore_2       
        //    50: aload_2        
        //    51: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //    54: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //    57: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //    60: iconst_1       
        //    61: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    64: invokevirtual   net/minecraft/util/ChatStyle.setUnderlined:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //    67: new             Lnet/minecraft/event/ClickEvent;
        //    70: dup            
        //    71: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_URL:Lnet/minecraft/event/ClickEvent$Action;
        //    74: ldc             "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465#Usage"
        //    76: invokespecial   net/minecraft/event/ClickEvent.<init>:(Lnet/minecraft/event/ClickEvent$Action;Ljava/lang/String;)V
        //    79: invokevirtual   net/minecraft/util/ChatStyle.setChatClickEvent:(Lnet/minecraft/event/ClickEvent;)Lnet/minecraft/util/ChatStyle;
        //    82: pop            
        //    83: new             Lnet/minecraft/util/ChatComponentTranslation;
        //    86: dup            
        //    87: ldc             "wdl.intro.wikiLink"
        //    89: iconst_0       
        //    90: anewarray       Ljava/lang/Object;
        //    93: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //    96: astore_3       
        //    97: aload_3        
        //    98: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   101: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //   104: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //   107: iconst_1       
        //   108: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   111: invokevirtual   net/minecraft/util/ChatStyle.setUnderlined:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //   114: new             Lnet/minecraft/event/ClickEvent;
        //   117: dup            
        //   118: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_URL:Lnet/minecraft/event/ClickEvent$Action;
        //   121: ldc             "https://github.com/pokechu22/WorldDownloader/wiki"
        //   123: invokespecial   net/minecraft/event/ClickEvent.<init>:(Lnet/minecraft/event/ClickEvent$Action;Ljava/lang/String;)V
        //   126: invokevirtual   net/minecraft/util/ChatStyle.setChatClickEvent:(Lnet/minecraft/event/ClickEvent;)Lnet/minecraft/util/ChatStyle;
        //   129: pop            
        //   130: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   133: dup            
        //   134: ldc             "wdl.intro.usage"
        //   136: iconst_2       
        //   137: anewarray       Ljava/lang/Object;
        //   140: dup            
        //   141: iconst_0       
        //   142: aload_2        
        //   143: aastore        
        //   144: dup            
        //   145: iconst_1       
        //   146: aload_3        
        //   147: aastore        
        //   148: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   151: astore          4
        //   153: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   156: dup            
        //   157: ldc             "wdl.intro.githubRepo"
        //   159: iconst_0       
        //   160: anewarray       Ljava/lang/Object;
        //   163: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   166: astore          5
        //   168: aload           5
        //   170: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   173: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //   176: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //   179: iconst_1       
        //   180: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   183: invokevirtual   net/minecraft/util/ChatStyle.setUnderlined:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //   186: new             Lnet/minecraft/event/ClickEvent;
        //   189: dup            
        //   190: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_URL:Lnet/minecraft/event/ClickEvent$Action;
        //   193: ldc             "https://github.com/pokechu22/WorldDownloader"
        //   195: invokespecial   net/minecraft/event/ClickEvent.<init>:(Lnet/minecraft/event/ClickEvent$Action;Ljava/lang/String;)V
        //   198: invokevirtual   net/minecraft/util/ChatStyle.setChatClickEvent:(Lnet/minecraft/event/ClickEvent;)Lnet/minecraft/util/ChatStyle;
        //   201: pop            
        //   202: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   205: dup            
        //   206: ldc             "wdl.intro.contribute"
        //   208: iconst_1       
        //   209: anewarray       Ljava/lang/Object;
        //   212: dup            
        //   213: iconst_0       
        //   214: aload           5
        //   216: aastore        
        //   217: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   220: astore          6
        //   222: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   225: dup            
        //   226: ldc             "wdl.intro.redistributionList"
        //   228: iconst_0       
        //   229: anewarray       Ljava/lang/Object;
        //   232: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   235: astore          7
        //   237: aload           7
        //   239: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   242: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //   245: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //   248: iconst_1       
        //   249: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   252: invokevirtual   net/minecraft/util/ChatStyle.setUnderlined:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //   255: new             Lnet/minecraft/event/ClickEvent;
        //   258: dup            
        //   259: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_URL:Lnet/minecraft/event/ClickEvent$Action;
        //   262: ldc             "http://pokechu22.github.io/WorldDownloader/redistribution"
        //   264: invokespecial   net/minecraft/event/ClickEvent.<init>:(Lnet/minecraft/event/ClickEvent$Action;Ljava/lang/String;)V
        //   267: invokevirtual   net/minecraft/util/ChatStyle.setChatClickEvent:(Lnet/minecraft/event/ClickEvent;)Lnet/minecraft/util/ChatStyle;
        //   270: pop            
        //   271: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   274: dup            
        //   275: ldc             "wdl.intro.warning"
        //   277: iconst_0       
        //   278: anewarray       Ljava/lang/Object;
        //   281: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   284: astore          8
        //   286: aload           8
        //   288: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   291: getstatic       net/minecraft/util/EnumChatFormatting.DARK_RED:Lnet/minecraft/util/EnumChatFormatting;
        //   294: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //   297: iconst_1       
        //   298: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   301: invokevirtual   net/minecraft/util/ChatStyle.setBold:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //   304: pop            
        //   305: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   308: dup            
        //   309: ldc             "wdl.intro.illegally"
        //   311: iconst_0       
        //   312: anewarray       Ljava/lang/Object;
        //   315: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   318: astore          9
        //   320: aload           9
        //   322: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   325: getstatic       net/minecraft/util/EnumChatFormatting.DARK_RED:Lnet/minecraft/util/EnumChatFormatting;
        //   328: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //   331: iconst_1       
        //   332: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   335: invokevirtual   net/minecraft/util/ChatStyle.setBold:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //   338: pop            
        //   339: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   342: dup            
        //   343: ldc             "wdl.intro.stolen"
        //   345: iconst_3       
        //   346: anewarray       Ljava/lang/Object;
        //   349: dup            
        //   350: iconst_0       
        //   351: aload           8
        //   353: aastore        
        //   354: dup            
        //   355: iconst_1       
        //   356: aload           7
        //   358: aastore        
        //   359: dup            
        //   360: iconst_2       
        //   361: aload           9
        //   363: aastore        
        //   364: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   367: astore          10
        //   369: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   372: dup            
        //   373: ldc             "wdl.intro.stopModReposts"
        //   375: iconst_0       
        //   376: anewarray       Ljava/lang/Object;
        //   379: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   382: astore          11
        //   384: aload           11
        //   386: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   389: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //   392: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //   395: iconst_1       
        //   396: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   399: invokevirtual   net/minecraft/util/ChatStyle.setUnderlined:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //   402: new             Lnet/minecraft/event/ClickEvent;
        //   405: dup            
        //   406: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_URL:Lnet/minecraft/event/ClickEvent$Action;
        //   409: ldc             "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/mods-discussion/2314237"
        //   411: invokespecial   net/minecraft/event/ClickEvent.<init>:(Lnet/minecraft/event/ClickEvent$Action;Ljava/lang/String;)V
        //   414: invokevirtual   net/minecraft/util/ChatStyle.setChatClickEvent:(Lnet/minecraft/event/ClickEvent;)Lnet/minecraft/util/ChatStyle;
        //   417: pop            
        //   418: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   421: dup            
        //   422: ldc             "wdl.intro.stolenBeware"
        //   424: iconst_1       
        //   425: anewarray       Ljava/lang/Object;
        //   428: dup            
        //   429: iconst_0       
        //   430: aload           11
        //   432: aastore        
        //   433: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   436: astore          12
        //   438: getstatic       wdl/WDLMessageTypes.UPDATES:Lwdl/WDLMessageTypes;
        //   441: aload_1        
        //   442: invokestatic    wdl/WDLMessages.chatMessage:(Lwdl/api/IWDLMessageType;Lnet/minecraft/util/IChatComponent;)V
        //   445: getstatic       wdl/WDLMessageTypes.UPDATES:Lwdl/WDLMessageTypes;
        //   448: aload           4
        //   450: invokestatic    wdl/WDLMessages.chatMessage:(Lwdl/api/IWDLMessageType;Lnet/minecraft/util/IChatComponent;)V
        //   453: getstatic       wdl/WDLMessageTypes.UPDATES:Lwdl/WDLMessageTypes;
        //   456: aload           6
        //   458: invokestatic    wdl/WDLMessages.chatMessage:(Lwdl/api/IWDLMessageType;Lnet/minecraft/util/IChatComponent;)V
        //   461: getstatic       wdl/WDLMessageTypes.UPDATES:Lwdl/WDLMessageTypes;
        //   464: aload           10
        //   466: invokestatic    wdl/WDLMessages.chatMessage:(Lwdl/api/IWDLMessageType;Lnet/minecraft/util/IChatComponent;)V
        //   469: getstatic       wdl/WDLMessageTypes.UPDATES:Lwdl/WDLMessageTypes;
        //   472: aload           12
        //   474: invokestatic    wdl/WDLMessages.chatMessage:(Lwdl/api/IWDLMessageType;Lnet/minecraft/util/IChatComponent;)V
        //   477: getstatic       wdl/WDL.globalProps:Ljava/util/Properties;
        //   480: ldc             "TutorialShown"
        //   482: ldc             "true"
        //   484: invokevirtual   java/util/Properties.setProperty:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
        //   487: pop            
        //   488: ldc2_w          5000
        //   491: invokestatic    wdl/update/WDLUpdateChecker.sleep:(J)V
        //   494: invokestatic    wdl/update/GithubInfoGrabber.getReleases:()Ljava/util/List;
        //   497: putstatic       wdl/update/WDLUpdateChecker.releases:Ljava/util/List;
        //   500: getstatic       wdl/WDLMessageTypes.UPDATE_DEBUG:Lwdl/WDLMessageTypes;
        //   503: ldc_w           "wdl.messages.updates.releaseCount"
        //   506: iconst_1       
        //   507: anewarray       Ljava/lang/Object;
        //   510: dup            
        //   511: iconst_0       
        //   512: getstatic       wdl/update/WDLUpdateChecker.releases:Ljava/util/List;
        //   515: invokeinterface java/util/List.size:()I
        //   520: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   523: aastore        
        //   524: invokestatic    wdl/WDLMessages.chatMessageTranslated:(Lwdl/api/IWDLMessageType;Ljava/lang/String;[Ljava/lang/Object;)V
        //   527: getstatic       wdl/update/WDLUpdateChecker.releases:Ljava/util/List;
        //   530: invokeinterface java/util/List.isEmpty:()Z
        //   535: ifeq            553
        //   538: iconst_1       
        //   539: putstatic       wdl/update/WDLUpdateChecker.failed:Z
        //   542: ldc_w           "No releases found."
        //   545: putstatic       wdl/update/WDLUpdateChecker.failReason:Ljava/lang/String;
        //   548: iconst_1       
        //   549: putstatic       wdl/update/WDLUpdateChecker.finished:Z
        //   552: return         
        //   553: goto            589
        //   556: getstatic       wdl/update/WDLUpdateChecker.releases:Ljava/util/List;
        //   559: iconst_0       
        //   560: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   565: checkcast       Lwdl/update/Release;
        //   568: astore_2       
        //   569: aload_2        
        //   570: getfield        wdl/update/Release.tag:Ljava/lang/String;
        //   573: ldc_w           "1.8.9a-beta2"
        //   576: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   579: ifeq            586
        //   582: aload_2        
        //   583: putstatic       wdl/update/WDLUpdateChecker.runningRelease:Lwdl/update/Release;
        //   586: iinc            1, 1
        //   589: iconst_0       
        //   590: getstatic       wdl/update/WDLUpdateChecker.releases:Ljava/util/List;
        //   593: invokeinterface java/util/List.size:()I
        //   598: if_icmplt       556
        //   601: getstatic       wdl/update/WDLUpdateChecker.runningRelease:Lwdl/update/Release;
        //   604: ifnonnull       631
        //   607: getstatic       wdl/WDLMessageTypes.UPDATE_DEBUG:Lwdl/WDLMessageTypes;
        //   610: ldc_w           "wdl.messages.updates.failedToFindMatchingRelease"
        //   613: iconst_1       
        //   614: anewarray       Ljava/lang/Object;
        //   617: dup            
        //   618: iconst_0       
        //   619: ldc_w           "1.8.9a-beta2"
        //   622: aastore        
        //   623: invokestatic    wdl/WDLMessages.chatMessageTranslated:(Lwdl/api/IWDLMessageType;Ljava/lang/String;[Ljava/lang/Object;)V
        //   626: iconst_1       
        //   627: putstatic       wdl/update/WDLUpdateChecker.finished:Z
        //   630: return         
        //   631: invokestatic    wdl/update/WDLUpdateChecker.hasNewVersion:()Z
        //   634: ifeq            724
        //   637: invokestatic    wdl/update/WDLUpdateChecker.getRecomendedRelease:()Lwdl/update/Release;
        //   640: astore_1       
        //   641: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   644: dup            
        //   645: ldc_w           "wdl.messages.updates.newRelease.updateLink"
        //   648: iconst_0       
        //   649: anewarray       Ljava/lang/Object;
        //   652: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   655: astore_2       
        //   656: aload_2        
        //   657: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   660: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //   663: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //   666: iconst_1       
        //   667: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   670: invokevirtual   net/minecraft/util/ChatStyle.setUnderlined:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //   673: new             Lnet/minecraft/event/ClickEvent;
        //   676: dup            
        //   677: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_URL:Lnet/minecraft/event/ClickEvent$Action;
        //   680: aload_1        
        //   681: getfield        wdl/update/Release.URL:Ljava/lang/String;
        //   684: invokespecial   net/minecraft/event/ClickEvent.<init>:(Lnet/minecraft/event/ClickEvent$Action;Ljava/lang/String;)V
        //   687: invokevirtual   net/minecraft/util/ChatStyle.setChatClickEvent:(Lnet/minecraft/event/ClickEvent;)Lnet/minecraft/util/ChatStyle;
        //   690: pop            
        //   691: getstatic       wdl/WDLMessageTypes.UPDATES:Lwdl/WDLMessageTypes;
        //   694: ldc_w           "wdl.messages.updates.newRelease"
        //   697: iconst_3       
        //   698: anewarray       Ljava/lang/Object;
        //   701: dup            
        //   702: iconst_0       
        //   703: getstatic       wdl/update/WDLUpdateChecker.runningRelease:Lwdl/update/Release;
        //   706: getfield        wdl/update/Release.tag:Ljava/lang/String;
        //   709: aastore        
        //   710: dup            
        //   711: iconst_1       
        //   712: aload_1        
        //   713: getfield        wdl/update/Release.tag:Ljava/lang/String;
        //   716: aastore        
        //   717: dup            
        //   718: iconst_2       
        //   719: aload_2        
        //   720: aastore        
        //   721: invokestatic    wdl/WDLMessages.chatMessageTranslated:(Lwdl/api/IWDLMessageType;Ljava/lang/String;[Ljava/lang/Object;)V
        //   724: getstatic       wdl/update/WDLUpdateChecker.runningRelease:Lwdl/update/Release;
        //   727: getfield        wdl/update/Release.hiddenInfo:Lwdl/update/Release$HiddenInfo;
        //   730: ifnonnull       757
        //   733: getstatic       wdl/WDLMessageTypes.UPDATE_DEBUG:Lwdl/WDLMessageTypes;
        //   736: ldc_w           "wdl.messages.updates.failedToFindMetadata"
        //   739: iconst_1       
        //   740: anewarray       Ljava/lang/Object;
        //   743: dup            
        //   744: iconst_0       
        //   745: ldc_w           "1.8.9a-beta2"
        //   748: aastore        
        //   749: invokestatic    wdl/WDLMessages.chatMessageTranslated:(Lwdl/api/IWDLMessageType;Ljava/lang/String;[Ljava/lang/Object;)V
        //   752: iconst_1       
        //   753: putstatic       wdl/update/WDLUpdateChecker.finished:Z
        //   756: return         
        //   757: new             Ljava/util/HashMap;
        //   760: dup            
        //   761: invokespecial   java/util/HashMap.<init>:()V
        //   764: astore_1       
        //   765: getstatic       wdl/update/WDLUpdateChecker.runningRelease:Lwdl/update/Release;
        //   768: getfield        wdl/update/Release.hiddenInfo:Lwdl/update/Release$HiddenInfo;
        //   771: getfield        wdl/update/Release$HiddenInfo.hashes:[Lwdl/update/Release$HashData;
        //   774: dup            
        //   775: astore          5
        //   777: arraylength    
        //   778: istore          4
        //   780: goto            954
        //   783: aload           5
        //   785: iconst_0       
        //   786: aaload         
        //   787: astore_2       
        //   788: aload_2        
        //   789: getfield        wdl/update/Release$HashData.relativeTo:Ljava/lang/String;
        //   792: aload_2        
        //   793: getfield        wdl/update/Release$HashData.file:Ljava/lang/String;
        //   796: invokestatic    wdl/update/ClassHasher.hash:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   799: astore          6
        //   801: aload_2        
        //   802: getfield        wdl/update/Release$HashData.validHashes:[Ljava/lang/String;
        //   805: dup            
        //   806: astore          10
        //   808: arraylength    
        //   809: istore          9
        //   811: goto            836
        //   814: aload           10
        //   816: iconst_0       
        //   817: aaload         
        //   818: astore          7
        //   820: aload           7
        //   822: aload           6
        //   824: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   827: ifeq            833
        //   830: goto            951
        //   833: iinc            8, 1
        //   836: iconst_0       
        //   837: iload           9
        //   839: if_icmplt       814
        //   842: getstatic       wdl/WDLMessageTypes.UPDATE_DEBUG:Lwdl/WDLMessageTypes;
        //   845: ldc_w           "wdl.messages.updates.incorrectHash"
        //   848: iconst_4       
        //   849: anewarray       Ljava/lang/Object;
        //   852: dup            
        //   853: iconst_0       
        //   854: aload_2        
        //   855: getfield        wdl/update/Release$HashData.file:Ljava/lang/String;
        //   858: aastore        
        //   859: dup            
        //   860: iconst_1       
        //   861: aload_2        
        //   862: getfield        wdl/update/Release$HashData.relativeTo:Ljava/lang/String;
        //   865: aastore        
        //   866: dup            
        //   867: iconst_2       
        //   868: aload_2        
        //   869: getfield        wdl/update/Release$HashData.validHashes:[Ljava/lang/String;
        //   872: invokestatic    java/util/Arrays.toString:([Ljava/lang/Object;)Ljava/lang/String;
        //   875: aastore        
        //   876: dup            
        //   877: iconst_3       
        //   878: aload           6
        //   880: aastore        
        //   881: invokestatic    wdl/WDLMessages.chatMessageTranslated:(Lwdl/api/IWDLMessageType;Ljava/lang/String;[Ljava/lang/Object;)V
        //   884: aload_1        
        //   885: aload_2        
        //   886: aload           6
        //   888: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   893: pop            
        //   894: goto            951
        //   897: astore          6
        //   899: getstatic       wdl/WDLMessageTypes.UPDATE_DEBUG:Lwdl/WDLMessageTypes;
        //   902: ldc_w           "wdl.messages.updates.hashException"
        //   905: iconst_4       
        //   906: anewarray       Ljava/lang/Object;
        //   909: dup            
        //   910: iconst_0       
        //   911: aload_2        
        //   912: getfield        wdl/update/Release$HashData.file:Ljava/lang/String;
        //   915: aastore        
        //   916: dup            
        //   917: iconst_1       
        //   918: aload_2        
        //   919: getfield        wdl/update/Release$HashData.relativeTo:Ljava/lang/String;
        //   922: aastore        
        //   923: dup            
        //   924: iconst_2       
        //   925: aload_2        
        //   926: getfield        wdl/update/Release$HashData.validHashes:[Ljava/lang/String;
        //   929: invokestatic    java/util/Arrays.toString:([Ljava/lang/Object;)Ljava/lang/String;
        //   932: aastore        
        //   933: dup            
        //   934: iconst_3       
        //   935: aload           6
        //   937: aastore        
        //   938: invokestatic    wdl/WDLMessages.chatMessageTranslated:(Lwdl/api/IWDLMessageType;Ljava/lang/String;[Ljava/lang/Object;)V
        //   941: aload_1        
        //   942: aload_2        
        //   943: aload           6
        //   945: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   950: pop            
        //   951: iinc            3, 1
        //   954: iconst_0       
        //   955: iload           4
        //   957: if_icmplt       783
        //   960: aload_1        
        //   961: invokeinterface java/util/Map.size:()I
        //   966: ifle            1081
        //   969: new             Lnet/minecraft/util/ChatComponentTranslation;
        //   972: dup            
        //   973: ldc             "wdl.intro.forumsLink"
        //   975: iconst_0       
        //   976: anewarray       Ljava/lang/Object;
        //   979: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   982: astore_2       
        //   983: aload_2        
        //   984: invokevirtual   net/minecraft/util/ChatComponentTranslation.getChatStyle:()Lnet/minecraft/util/ChatStyle;
        //   987: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //   990: invokevirtual   net/minecraft/util/ChatStyle.setColor:(Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;
        //   993: iconst_1       
        //   994: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   997: invokevirtual   net/minecraft/util/ChatStyle.setUnderlined:(Ljava/lang/Boolean;)Lnet/minecraft/util/ChatStyle;
        //  1000: new             Lnet/minecraft/event/ClickEvent;
        //  1003: dup            
        //  1004: getstatic       net/minecraft/event/ClickEvent$Action.OPEN_URL:Lnet/minecraft/event/ClickEvent$Action;
        //  1007: ldc             "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465#Usage"
        //  1009: invokespecial   net/minecraft/event/ClickEvent.<init>:(Lnet/minecraft/event/ClickEvent$Action;Ljava/lang/String;)V
        //  1012: invokevirtual   net/minecraft/util/ChatStyle.setChatClickEvent:(Lnet/minecraft/event/ClickEvent;)Lnet/minecraft/util/ChatStyle;
        //  1015: pop            
        //  1016: getstatic       wdl/WDLMessageTypes.UPDATES:Lwdl/WDLMessageTypes;
        //  1019: ldc_w           "wdl.messages.updates.badHashesFound"
        //  1022: iconst_1       
        //  1023: anewarray       Ljava/lang/Object;
        //  1026: dup            
        //  1027: iconst_0       
        //  1028: aload_2        
        //  1029: aastore        
        //  1030: invokestatic    wdl/WDLMessages.chatMessageTranslated:(Lwdl/api/IWDLMessageType;Ljava/lang/String;[Ljava/lang/Object;)V
        //  1033: goto            1081
        //  1036: astore_1       
        //  1037: getstatic       wdl/WDLMessageTypes.UPDATE_DEBUG:Lwdl/WDLMessageTypes;
        //  1040: ldc_w           "wdl.messages.updates.updateCheckError"
        //  1043: iconst_1       
        //  1044: anewarray       Ljava/lang/Object;
        //  1047: dup            
        //  1048: iconst_0       
        //  1049: aload_1        
        //  1050: aastore        
        //  1051: invokestatic    wdl/WDLMessages.chatMessageTranslated:(Lwdl/api/IWDLMessageType;Ljava/lang/String;[Ljava/lang/Object;)V
        //  1054: iconst_1       
        //  1055: putstatic       wdl/update/WDLUpdateChecker.failed:Z
        //  1058: aload_1        
        //  1059: invokevirtual   java/lang/Exception.toString:()Ljava/lang/String;
        //  1062: putstatic       wdl/update/WDLUpdateChecker.failReason:Ljava/lang/String;
        //  1065: iconst_1       
        //  1066: putstatic       wdl/update/WDLUpdateChecker.finished:Z
        //  1069: goto            1085
        //  1072: astore          13
        //  1074: iconst_1       
        //  1075: putstatic       wdl/update/WDLUpdateChecker.finished:Z
        //  1078: aload           13
        //  1080: athrow         
        //  1081: iconst_1       
        //  1082: putstatic       wdl/update/WDLUpdateChecker.finished:Z
        //  1085: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
