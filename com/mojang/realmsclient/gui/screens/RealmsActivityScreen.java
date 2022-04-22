package com.mojang.realmsclient.gui.screens;

import org.lwjgl.input.*;
import com.mojang.realmsclient.client.*;
import java.text.*;
import com.google.common.collect.*;
import com.mojang.realmsclient.dto.*;
import java.util.*;
import org.lwjgl.opengl.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import com.google.common.cache.*;
import net.minecraft.realms.*;

public class RealmsActivityScreen extends RealmsScreen
{
    private static final Logger LOGGER;
    private final RealmsScreen lastScreen;
    private final RealmsServer serverData;
    private List activityMap;
    private DetailsList list;
    private int matrixWidth;
    private String toolTip;
    private List dayList;
    private List colors;
    private int colorIndex;
    private long periodInMillis;
    private int maxKeyWidth;
    private Boolean noActivity;
    private int activityPoint;
    private int dayWidth;
    private double hourWidth;
    private double minuteWidth;
    private int BUTTON_BACK_ID;
    private static LoadingCache activitiesNameCache;
    
    public RealmsActivityScreen(final RealmsScreen lastScreen, final RealmsServer serverData) {
        this.activityMap = new ArrayList();
        this.dayList = new ArrayList();
        this.colors = Arrays.asList(new Color(79, 243, 29), new Color(243, 175, 29), new Color(243, 29, 190), new Color(29, 165, 243), new Color(29, 243, 130), new Color(243, 29, 64), new Color(29, 74, 243));
        this.colorIndex = 0;
        this.maxKeyWidth = 0;
        this.noActivity = false;
        this.activityPoint = 0;
        this.dayWidth = 0;
        this.hourWidth = 0.0;
        this.minuteWidth = 0.0;
        this.BUTTON_BACK_ID = 0;
        this.lastScreen = lastScreen;
        this.serverData = serverData;
        this.getActivities();
    }
    
    @Override
    public void mouseEvent() {
        super.mouseEvent();
        this.list.mouseEvent();
    }
    
    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttonsClear();
        this.matrixWidth = this.width();
        this.list = new DetailsList();
        this.buttonsAdd(RealmsScreen.newButton(this.BUTTON_BACK_ID, this.width() / 2 - 100, this.height() - 30, 200, 20, RealmsScreen.getLocalizedString("gui.back")));
    }
    
    private Color getColor() {
        if (this.colorIndex > this.colors.size() - 1) {
            this.colorIndex = 0;
        }
        return this.colors.get(this.colorIndex++);
    }
    
    private void getActivities() {
        new Thread() {
            final RealmsActivityScreen this$0;
            
            @Override
            public void run() {
                RealmsActivityScreen.access$102(this.this$0, RealmsActivityScreen.access$200(this.this$0, RealmsClient.createRealmsClient().getActivity(RealmsActivityScreen.access$000(this.this$0).id)));
                final ArrayList<Day> list = (ArrayList<Day>)new ArrayList<Comparable>();
                final Iterator<ActivityRow> iterator = RealmsActivityScreen.access$100(this.this$0).iterator();
                while (iterator.hasNext()) {
                    for (final Activity activity : iterator.next().activities) {
                        final Day day = new Day(new SimpleDateFormat("dd/MM").format(new Date(activity.start)), activity.start);
                        if (!list.contains(day)) {
                            list.add(day);
                        }
                    }
                }
                Collections.sort((List<Comparable>)list);
                final Iterator<ActivityRow> iterator3 = RealmsActivityScreen.access$100(this.this$0).iterator();
                while (iterator3.hasNext()) {
                    for (final Activity activity2 : iterator3.next().activities) {
                        activity2.dayIndex = list.indexOf(new Day(new SimpleDateFormat("dd/MM").format(new Date(activity2.start)), activity2.start)) + 1;
                    }
                }
                RealmsActivityScreen.access$302(this.this$0, list);
            }
        }.start();
    }
    
    private List convertToActivityMatrix(final ServerActivityList list) {
        final ArrayList arrayList = Lists.newArrayList();
        this.periodInMillis = list.periodInMillis;
        final long n = System.currentTimeMillis() - list.periodInMillis;
        for (final ServerActivity serverActivity : list.serverActivities) {
            final ActivityRow find = this.find(serverActivity.profileUuid, arrayList);
            final Calendar instance = Calendar.getInstance(TimeZone.getDefault());
            instance.setTimeInMillis(serverActivity.joinTime);
            final Calendar instance2 = Calendar.getInstance(TimeZone.getDefault());
            instance2.setTimeInMillis(serverActivity.leaveTime);
            final Activity activity = new Activity(n, instance.getTimeInMillis(), instance2.getTimeInMillis(), null);
            if (find == null) {
                final ActivityRow activityRow = new ActivityRow(serverActivity.profileUuid, new ArrayList(), (String)RealmsActivityScreen.activitiesNameCache.get(serverActivity.profileUuid), serverActivity.profileUuid);
                activityRow.activities.add(activity);
                arrayList.add(activityRow);
            }
            else {
                find.activities.add(activity);
            }
        }
        Collections.sort(arrayList);
        for (final ActivityRow activityRow2 : arrayList) {
            activityRow2.color = this.getColor();
            Collections.sort((List<Comparable>)activityRow2.activities);
        }
        this.noActivity = (arrayList.size() == 0);
        return arrayList;
    }
    
    private ActivityRow find(final String s, final List list) {
        for (final ActivityRow activityRow : list) {
            if (activityRow.key.equals(s)) {
                return activityRow;
            }
        }
        return null;
    }
    
    @Override
    public void tick() {
        super.tick();
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (realmsButton.id() == this.BUTTON_BACK_ID) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.lastScreen);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.toolTip = null;
        this.renderBackground();
        final Iterator<ActivityRow> iterator = this.activityMap.iterator();
        while (iterator.hasNext()) {
            final int fontWidth = this.fontWidth(iterator.next().name);
            if (fontWidth > this.maxKeyWidth) {
                this.maxKeyWidth = fontWidth + 10;
            }
        }
        this.activityPoint = this.maxKeyWidth + 25;
        this.dayWidth = (this.matrixWidth - this.activityPoint - 10) / ((this.dayList.size() < 1) ? 1 : this.dayList.size());
        this.hourWidth = this.dayWidth / 24.0;
        this.minuteWidth = this.hourWidth / 60.0;
        this.list.render(n, n2, n3);
        if (this.activityMap != null && this.activityMap.size() > 0) {
            final Tezzelator instance = Tezzelator.instance;
            GL11.glDisable(3553);
            instance.begin(7, RealmsDefaultVertexFormat.POSITION_COLOR);
            instance.vertex((double)this.activityPoint, (double)(this.height() - 40), 0.0).color(128, 128, 128, 255).endVertex();
            instance.vertex((double)(this.activityPoint + 1), (double)(this.height() - 40), 0.0).color(128, 128, 128, 255).endVertex();
            instance.vertex((double)(this.activityPoint + 1), 30.0, 0.0).color(128, 128, 128, 255).endVertex();
            instance.vertex((double)this.activityPoint, 30.0, 0.0).color(128, 128, 128, 255).endVertex();
            instance.end();
            GL11.glEnable(3553);
            for (final Day day : this.dayList) {
                final int n4 = this.dayList.indexOf(day) + 1;
                this.drawString(day.day, this.activityPoint + (n4 - 1) * this.dayWidth + (this.dayWidth - this.fontWidth(day.day)) / 2 + 2, this.height() - 52, 16777215);
                GL11.glDisable(3553);
                instance.begin(7, RealmsDefaultVertexFormat.POSITION_COLOR);
                instance.vertex((double)(this.activityPoint + n4 * this.dayWidth), (double)(this.height() - 40), 0.0).color(128, 128, 128, 255).endVertex();
                instance.vertex((double)(this.activityPoint + n4 * this.dayWidth + 1), (double)(this.height() - 40), 0.0).color(128, 128, 128, 255).endVertex();
                instance.vertex((double)(this.activityPoint + n4 * this.dayWidth + 1), 30.0, 0.0).color(128, 128, 128, 255).endVertex();
                instance.vertex((double)(this.activityPoint + n4 * this.dayWidth), 30.0, 0.0).color(128, 128, 128, 255).endVertex();
                instance.end();
                GL11.glEnable(3553);
            }
        }
        super.render(n, n2, n3);
        this.drawCenteredString(RealmsScreen.getLocalizedString("mco.activity.title"), this.width() / 2, 10, 16777215);
        if (this.toolTip != null) {
            this.renderMousehoverTooltip(this.toolTip, n, n2);
        }
        if (this.noActivity) {
            this.drawCenteredString(RealmsScreen.getLocalizedString("mco.activity.noactivity", TimeUnit.DAYS.convert(this.periodInMillis, TimeUnit.MILLISECONDS)), this.width() / 2, this.height() / 2 - 20, 16777215);
        }
    }
    
    protected void renderMousehoverTooltip(final String p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       5
        //     4: return         
        //     5: aload_1        
        //     6: ldc_w           "\n"
        //     9: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    12: astore          6
        //    14: aload           6
        //    16: arraylength    
        //    17: istore          7
        //    19: iconst_0       
        //    20: iload           7
        //    22: if_icmpge       50
        //    25: aload           6
        //    27: iconst_0       
        //    28: aaload         
        //    29: astore          9
        //    31: aload_0        
        //    32: aload           9
        //    34: invokevirtual   com/mojang/realmsclient/gui/screens/RealmsActivityScreen.fontWidth:(Ljava/lang/String;)I
        //    37: istore          10
        //    39: iconst_0       
        //    40: iconst_0       
        //    41: if_icmple       44
        //    44: iinc            8, 1
        //    47: goto            19
        //    50: iload_2        
        //    51: iconst_0       
        //    52: isub           
        //    53: iconst_5       
        //    54: isub           
        //    55: istore          6
        //    57: iload_3        
        //    58: istore          7
        //    60: iload           6
        //    62: ifge            71
        //    65: iload_2        
        //    66: bipush          12
        //    68: iadd           
        //    69: istore          6
        //    71: aload_1        
        //    72: ldc_w           "\n"
        //    75: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    78: astore          8
        //    80: aload           8
        //    82: arraylength    
        //    83: istore          9
        //    85: iconst_0       
        //    86: iload           9
        //    88: if_icmpge       160
        //    91: aload           8
        //    93: iconst_0       
        //    94: aaload         
        //    95: astore          11
        //    97: aload_0        
        //    98: iload           6
        //   100: iconst_3       
        //   101: isub           
        //   102: iload           7
        //   104: iconst_0       
        //   105: ifne            112
        //   108: iconst_3       
        //   109: goto            108
        //   112: iconst_0       
        //   113: iadd           
        //   114: iload           6
        //   116: iconst_0       
        //   117: iadd           
        //   118: iconst_3       
        //   119: iadd           
        //   120: iload           7
        //   122: bipush          8
        //   124: iadd           
        //   125: iconst_3       
        //   126: iadd           
        //   127: iconst_0       
        //   128: iadd           
        //   129: ldc_w           -1073741824
        //   132: ldc_w           -1073741824
        //   135: invokevirtual   com/mojang/realmsclient/gui/screens/RealmsActivityScreen.fillGradient:(IIIIII)V
        //   138: aload_0        
        //   139: aload           11
        //   141: iload           6
        //   143: iload           7
        //   145: iconst_0       
        //   146: iadd           
        //   147: iconst_m1      
        //   148: invokevirtual   com/mojang/realmsclient/gui/screens/RealmsActivityScreen.fontDrawShadow:(Ljava/lang/String;III)V
        //   151: iinc            4, 10
        //   154: iinc            10, 1
        //   157: goto            85
        //   160: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0108 (coming from #0109).
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
    
    static RealmsServer access$000(final RealmsActivityScreen realmsActivityScreen) {
        return realmsActivityScreen.serverData;
    }
    
    static List access$102(final RealmsActivityScreen realmsActivityScreen, final List activityMap) {
        return realmsActivityScreen.activityMap = activityMap;
    }
    
    static List access$200(final RealmsActivityScreen realmsActivityScreen, final ServerActivityList list) {
        return realmsActivityScreen.convertToActivityMatrix(list);
    }
    
    static List access$100(final RealmsActivityScreen realmsActivityScreen) {
        return realmsActivityScreen.activityMap;
    }
    
    static List access$302(final RealmsActivityScreen realmsActivityScreen, final List dayList) {
        return realmsActivityScreen.dayList = dayList;
    }
    
    static int access$500(final RealmsActivityScreen realmsActivityScreen) {
        return realmsActivityScreen.activityPoint;
    }
    
    static double access$600(final RealmsActivityScreen realmsActivityScreen) {
        return realmsActivityScreen.minuteWidth;
    }
    
    static int access$700(final RealmsActivityScreen realmsActivityScreen) {
        return realmsActivityScreen.dayWidth;
    }
    
    static double access$800(final RealmsActivityScreen realmsActivityScreen) {
        return realmsActivityScreen.hourWidth;
    }
    
    static String access$1002(final RealmsActivityScreen realmsActivityScreen, final String toolTip) {
        return realmsActivityScreen.toolTip = toolTip;
    }
    
    static {
        LOGGER = LogManager.getLogger();
        RealmsActivityScreen.activitiesNameCache = CacheBuilder.newBuilder().build(new CacheLoader() {
            public String load(final String s) throws Exception {
                final String uuidToName = Realms.uuidToName(s);
                if (uuidToName == null) {
                    throw new Exception("Couldn't get username");
                }
                return uuidToName;
            }
            
            @Override
            public Object load(final Object o) throws Exception {
                return this.load((String)o);
            }
        });
    }
    
    class DetailsList extends RealmsScrolledSelectionList
    {
        final RealmsActivityScreen this$0;
        
        public DetailsList(final RealmsActivityScreen this$0) {
            this.this$0 = this$0;
            super(this$0.width(), this$0.height(), 30, this$0.height() - 40, this$0.fontLineHeight() + 1);
        }
        
        @Override
        public int getItemCount() {
            return RealmsActivityScreen.access$100(this.this$0).size();
        }
        
        @Override
        public void selectItem(final int n, final boolean b, final int n2, final int n3) {
        }
        
        @Override
        public boolean isSelectedItem(final int n) {
            return false;
        }
        
        @Override
        public int getMaxPosition() {
            return this.getItemCount() * (this.this$0.fontLineHeight() + 1) + 15;
        }
        
        @Override
        protected void renderItem(final int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
            if (RealmsActivityScreen.access$100(this.this$0) != null && RealmsActivityScreen.access$100(this.this$0).size() > n) {
                final ActivityRow activityRow = RealmsActivityScreen.access$100(this.this$0).get(n);
                this.this$0.drawString(activityRow.name, 20, n3, RealmsActivityScreen.access$100(this.this$0).get(n).uuid.equals(Realms.getUUID()) ? 8388479 : 16777215);
                final int r = activityRow.color.r;
                final int g = activityRow.color.g;
                final int b = activityRow.color.b;
                GL11.glDisable(3553);
                tezzelator.begin(7, RealmsDefaultVertexFormat.POSITION_COLOR);
                tezzelator.vertex((double)(RealmsActivityScreen.access$500(this.this$0) - 8), n3 + 6.5, 0.0).color(r, g, b, 255).endVertex();
                tezzelator.vertex((double)(RealmsActivityScreen.access$500(this.this$0) - 3), n3 + 6.5, 0.0).color(r, g, b, 255).endVertex();
                tezzelator.vertex((double)(RealmsActivityScreen.access$500(this.this$0) - 3), n3 + 1.5, 0.0).color(r, g, b, 255).endVertex();
                tezzelator.vertex((double)(RealmsActivityScreen.access$500(this.this$0) - 8), n3 + 1.5, 0.0).color(r, g, b, 255).endVertex();
                tezzelator.end();
                GL11.glEnable(3553);
                RealmsScreen.bindFace(RealmsActivityScreen.access$100(this.this$0).get(n).uuid, RealmsActivityScreen.access$100(this.this$0).get(n).name);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                RealmsScreen.blit(10, n3, 8.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
                RealmsScreen.blit(10, n3, 40.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
                final ArrayList<ActivityRender> list = new ArrayList<ActivityRender>();
                for (final Activity activity : activityRow.activities) {
                    final int minuteIndice = activity.minuteIndice();
                    final int hourIndice = activity.hourIndice();
                    double n7 = RealmsActivityScreen.access$600(this.this$0) * TimeUnit.MINUTES.convert(activity.end - activity.start, TimeUnit.MILLISECONDS);
                    if (n7 < 3.0) {
                        n7 = 3.0;
                    }
                    final double n8 = RealmsActivityScreen.access$500(this.this$0) + (RealmsActivityScreen.access$700(this.this$0) * activity.dayIndex - RealmsActivityScreen.access$700(this.this$0)) + hourIndice * RealmsActivityScreen.access$800(this.this$0) + minuteIndice * RealmsActivityScreen.access$600(this.this$0);
                    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    final Date date = new Date(activity.start);
                    final Date date2 = new Date(activity.end);
                    final int n9 = (int)Math.ceil(TimeUnit.SECONDS.convert(activity.end - activity.start, TimeUnit.MILLISECONDS) / 60.0);
                    if (1 < 1) {}
                    final String string = "[" + simpleDateFormat.format(date) + " - " + simpleDateFormat.format(date2) + "] " + 1 + ((1 > 1) ? " minutes" : " minute");
                    for (final ActivityRender activityRender : list) {
                        if (activityRender.start + activityRender.width >= n8 - 0.5) {
                            activityRender.width = activityRender.width - Math.max(0.0, activityRender.start + activityRender.width - n8) + n7 + Math.max(0.0, n8 - (activityRender.start + activityRender.width));
                            final StringBuilder sb = new StringBuilder();
                            final ActivityRender activityRender2 = activityRender;
                            activityRender2.tooltip = sb.append(activityRender2.tooltip).append("\n").append(string).toString();
                            break;
                        }
                    }
                    if (!true) {
                        list.add(new ActivityRender(n8, n7, string, null));
                    }
                }
                for (final ActivityRender activityRender3 : list) {
                    GL11.glDisable(3553);
                    tezzelator.begin(7, RealmsDefaultVertexFormat.POSITION_COLOR);
                    tezzelator.vertex(activityRender3.start, n3 + 6.5, 0.0).color(r, g, b, 255).endVertex();
                    tezzelator.vertex(activityRender3.start + activityRender3.width, n3 + 6.5, 0.0).color(r, g, b, 255).endVertex();
                    tezzelator.vertex(activityRender3.start + activityRender3.width, n3 + 1.5, 0.0).color(r, g, b, 255).endVertex();
                    tezzelator.vertex(activityRender3.start, n3 + 1.5, 0.0).color(r, g, b, 255).endVertex();
                    tezzelator.end();
                    GL11.glEnable(3553);
                    if (this.xm() >= activityRender3.start && this.xm() <= activityRender3.start + activityRender3.width && this.ym() >= n3 + 1.5 && this.ym() <= n3 + 6.5) {
                        RealmsActivityScreen.access$1002(this.this$0, activityRender3.tooltip.trim());
                    }
                }
            }
        }
        
        @Override
        public int getScrollbarPosition() {
            return this.width() - 7;
        }
    }
    
    static class ActivityRender
    {
        double start;
        double width;
        String tooltip;
        
        private ActivityRender(final double start, final double width, final String tooltip) {
            this.start = start;
            this.width = width;
            this.tooltip = tooltip;
        }
        
        ActivityRender(final double n, final double n2, final String s, final RealmsActivityScreen$1 cacheLoader) {
            this(n, n2, s);
        }
    }
    
    static class Activity implements Comparable
    {
        long base;
        long start;
        long end;
        int dayIndex;
        
        private Activity(final long base, final long start, final long end) {
            this.base = base;
            this.start = start;
            this.end = end;
        }
        
        public int compareTo(final Activity activity) {
            return (int)(this.start - activity.start);
        }
        
        public int hourIndice() {
            return Integer.parseInt(new SimpleDateFormat("HH").format(new Date(this.start)));
        }
        
        public int minuteIndice() {
            return Integer.parseInt(new SimpleDateFormat("mm").format(new Date(this.start)));
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Activity)o);
        }
        
        Activity(final long n, final long n2, final long n3, final RealmsActivityScreen$1 cacheLoader) {
            this(n, n2, n3);
        }
    }
    
    static class ActivityRow implements Comparable
    {
        String key;
        List activities;
        Color color;
        String name;
        String uuid;
        
        public int compareTo(final ActivityRow activityRow) {
            return this.name.compareTo(activityRow.name);
        }
        
        ActivityRow(final String key, final List activities, final String name, final String uuid) {
            this.key = key;
            this.activities = activities;
            this.name = name;
            this.uuid = uuid;
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((ActivityRow)o);
        }
    }
    
    static class Color
    {
        int r;
        int g;
        int b;
        
        Color(final int r, final int g, final int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }
    
    static class Day implements Comparable
    {
        String day;
        Long timestamp;
        
        public int compareTo(final Day day) {
            return this.timestamp.compareTo(day.timestamp);
        }
        
        Day(final String day, final Long timestamp) {
            this.day = day;
            this.timestamp = timestamp;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Day && this.day.equals(((Day)o).day);
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Day)o);
        }
    }
}
