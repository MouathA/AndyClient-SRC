package net.minecraft.client.stream;

import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import org.apache.logging.log4j.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.net.*;
import com.google.gson.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.settings.*;
import tv.twitch.*;
import tv.twitch.broadcast.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.event.*;
import java.util.*;
import tv.twitch.chat.*;

public class TwitchStream implements BroadcastController.BroadcastListener, ChatController.ChatListener, IngestServerTester.IngestTestListener, IStream
{
    private static final Logger field_152950_b;
    public static final Marker field_152949_a;
    private final BroadcastController broadcastController;
    private final ChatController field_152952_d;
    private String field_176029_e;
    private final Minecraft field_152953_e;
    private final IChatComponent field_152954_f;
    private final Map field_152955_g;
    private Framebuffer field_152956_h;
    private boolean field_152957_i;
    private int field_152958_j;
    private long field_152959_k;
    private boolean field_152960_l;
    private boolean field_152961_m;
    private boolean field_152962_n;
    private boolean field_152963_o;
    private AuthFailureReason field_152964_p;
    private static boolean field_152965_q;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001812";
        field_152950_b = LogManager.getLogger();
        field_152949_a = MarkerManager.getMarker("STREAM");
        if (Util.getOSType() == Util.EnumOS.WINDOWS) {
            System.loadLibrary("avutil-ttv-51");
            System.loadLibrary("swresample-ttv-0");
            System.loadLibrary("libmp3lame-ttv");
            if (System.getProperty("os.arch").contains("64")) {
                System.loadLibrary("libmfxsw64");
            }
            else {
                System.loadLibrary("libmfxsw32");
            }
        }
        TwitchStream.field_152965_q = true;
    }
    
    public TwitchStream(final Minecraft field_152953_e, final Property property) {
        this.field_152954_f = new ChatComponentText("Twitch");
        this.field_152955_g = Maps.newHashMap();
        this.field_152958_j = 30;
        this.field_152959_k = 0L;
        this.field_152960_l = false;
        this.field_152964_p = AuthFailureReason.ERROR;
        this.field_152953_e = field_152953_e;
        this.broadcastController = new BroadcastController();
        this.field_152952_d = new ChatController();
        this.broadcastController.func_152841_a(this);
        this.field_152952_d.func_152990_a(this);
        this.broadcastController.func_152842_a("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.field_152952_d.func_152984_a("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.field_152954_f.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
        if (property != null && !Strings.isNullOrEmpty(property.getValue()) && OpenGlHelper.framebufferSupported) {
            final Thread thread = new Thread("Twitch authenticator", property) {
                private static final String __OBFID;
                final TwitchStream this$0;
                private final Property val$p_i46057_2_;
                
                @Override
                public void run() {
                    final JsonObject jsonObject = JsonUtils.getJsonObject(JsonUtils.getElementAsJsonObject(new JsonParser().parse(HttpUtil.get(new URL("https://api.twitch.tv/kraken?oauth_token=" + URLEncoder.encode(this.val$p_i46057_2_.getValue(), "UTF-8")))), "Response"), "token");
                    if (JsonUtils.getJsonObjectBooleanFieldValue(jsonObject, "valid")) {
                        final String jsonObjectStringFieldValue = JsonUtils.getJsonObjectStringFieldValue(jsonObject, "user_name");
                        TwitchStream.access$0().debug(TwitchStream.field_152949_a, "Authenticated with twitch; username is {}", jsonObjectStringFieldValue);
                        final AuthToken authToken = new AuthToken();
                        authToken.data = this.val$p_i46057_2_.getValue();
                        TwitchStream.access$1(this.this$0).func_152818_a(jsonObjectStringFieldValue, authToken);
                        TwitchStream.access$2(this.this$0).func_152998_c(jsonObjectStringFieldValue);
                        TwitchStream.access$2(this.this$0).func_152994_a(authToken);
                        Runtime.getRuntime().addShutdownHook(new Thread("Twitch shutdown hook") {
                            private static final String __OBFID;
                            final TwitchStream$1 this$1;
                            
                            @Override
                            public void run() {
                                TwitchStream$1.access$0(this.this$1).shutdownStream();
                            }
                            
                            static {
                                __OBFID = "CL_00001810";
                            }
                        });
                        TwitchStream.access$1(this.this$0).func_152817_A();
                        TwitchStream.access$2(this.this$0).func_175984_n();
                    }
                    else {
                        TwitchStream.access$3(this.this$0, AuthFailureReason.INVALID_TOKEN);
                        TwitchStream.access$0().error(TwitchStream.field_152949_a, "Given twitch access token is invalid");
                    }
                }
                
                static TwitchStream access$0(final TwitchStream$1 thread) {
                    return thread.this$0;
                }
                
                static {
                    __OBFID = "CL_00001811";
                }
            };
            thread.setDaemon(true);
            thread.start();
        }
    }
    
    @Override
    public void shutdownStream() {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Shutdown streaming");
        this.broadcastController.statCallback();
        this.field_152952_d.func_175988_p();
    }
    
    @Override
    public void func_152935_j() {
        final int streamChatEnabled = this.field_152953_e.gameSettings.streamChatEnabled;
        final boolean b = this.field_176029_e != null && this.field_152952_d.func_175990_d(this.field_176029_e);
        final boolean b2 = this.field_152952_d.func_153000_j() == ChatController.ChatState.Initialized && (this.field_176029_e == null || this.field_152952_d.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected);
        if (streamChatEnabled == 2) {
            if (b) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Disconnecting from twitch chat per user options");
                this.field_152952_d.func_175991_l(this.field_176029_e);
            }
        }
        else if (streamChatEnabled == 1) {
            if (b2 && this.broadcastController.func_152849_q()) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Connecting to twitch chat per user options");
                this.func_152942_I();
            }
        }
        else if (streamChatEnabled == 0) {
            if (b && !this.func_152934_n()) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Disconnecting from twitch chat as user is no longer streaming");
                this.field_152952_d.func_175991_l(this.field_176029_e);
            }
            else if (b2 && this.func_152934_n()) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Connecting to twitch chat as user is streaming");
                this.func_152942_I();
            }
        }
        this.broadcastController.func_152821_H();
        this.field_152952_d.func_152997_n();
    }
    
    protected void func_152942_I() {
        final ChatController.ChatState func_153000_j = this.field_152952_d.func_153000_j();
        final String name = this.broadcastController.func_152843_l().name;
        this.field_176029_e = name;
        if (func_153000_j != ChatController.ChatState.Initialized) {
            TwitchStream.field_152950_b.warn("Invalid twitch chat state {}", func_153000_j);
        }
        else if (this.field_152952_d.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected) {
            this.field_152952_d.func_152986_d(name);
        }
        else {
            TwitchStream.field_152950_b.warn("Invalid twitch chat state {}", func_153000_j);
        }
    }
    
    @Override
    public void func_152922_k() {
        if (this.broadcastController.isBroadcasting() && !this.broadcastController.isBroadcastPaused()) {
            final long nanoTime = System.nanoTime();
            if (nanoTime - this.field_152959_k >= 1000000000 / this.field_152958_j) {
                final FrameBuffer func_152822_N = this.broadcastController.func_152822_N();
                final Framebuffer framebuffer = this.field_152953_e.getFramebuffer();
                this.field_152956_h.bindFramebuffer(true);
                GlStateManager.matrixMode(5889);
                GlStateManager.ortho(0.0, this.field_152956_h.framebufferWidth, this.field_152956_h.framebufferHeight, 0.0, 1000.0, 3000.0);
                GlStateManager.matrixMode(5888);
                GlStateManager.translate(0.0f, 0.0f, -2000.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.viewport(0, 0, this.field_152956_h.framebufferWidth, this.field_152956_h.framebufferHeight);
                final float n = (float)this.field_152956_h.framebufferWidth;
                final float n2 = (float)this.field_152956_h.framebufferHeight;
                final float n3 = framebuffer.framebufferWidth / (float)framebuffer.framebufferTextureWidth;
                final float n4 = framebuffer.framebufferHeight / (float)framebuffer.framebufferTextureHeight;
                framebuffer.bindFramebufferTexture();
                GL11.glTexParameterf(3553, 10241, 9729.0f);
                GL11.glTexParameterf(3553, 10240, 9729.0f);
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.startDrawingQuads();
                worldRenderer.addVertexWithUV(0.0, n2, 0.0, 0.0, n4);
                worldRenderer.addVertexWithUV(n, n2, 0.0, n3, n4);
                worldRenderer.addVertexWithUV(n, 0.0, 0.0, n3, 0.0);
                worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                instance.draw();
                framebuffer.unbindFramebufferTexture();
                GlStateManager.matrixMode(5889);
                GlStateManager.matrixMode(5888);
                this.broadcastController.func_152846_a(func_152822_N);
                this.field_152956_h.unbindFramebuffer();
                this.broadcastController.func_152859_b(func_152822_N);
                this.field_152959_k = nanoTime;
            }
        }
    }
    
    @Override
    public boolean func_152936_l() {
        return this.broadcastController.func_152849_q();
    }
    
    @Override
    public boolean func_152924_m() {
        return this.broadcastController.func_152857_n();
    }
    
    @Override
    public boolean func_152934_n() {
        return this.broadcastController.isBroadcasting();
    }
    
    @Override
    public void func_152911_a(final Metadata metadata, final long n) {
        if (this.func_152934_n() && this.field_152957_i) {
            final long func_152844_x = this.broadcastController.func_152844_x();
            if (!this.broadcastController.func_152840_a(metadata.func_152810_c(), func_152844_x + n, metadata.func_152809_a(), metadata.func_152806_b())) {
                TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Couldn't send stream metadata action at {}: {}", func_152844_x + n, metadata);
            }
            else {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Sent stream metadata action at {}: {}", func_152844_x + n, metadata);
            }
        }
    }
    
    @Override
    public void func_176026_a(final Metadata metadata, final long n, final long n2) {
        if (this.func_152934_n() && this.field_152957_i) {
            final long func_152844_x = this.broadcastController.func_152844_x();
            final String func_152809_a = metadata.func_152809_a();
            final String func_152806_b = metadata.func_152806_b();
            final long func_177946_b = this.broadcastController.func_177946_b(metadata.func_152810_c(), func_152844_x + n, func_152809_a, func_152806_b);
            if (func_177946_b < 0L) {
                TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Could not send stream metadata sequence from {} to {}: {}", func_152844_x + n, func_152844_x + n2, metadata);
            }
            else if (this.broadcastController.func_177947_a(metadata.func_152810_c(), func_152844_x + n2, func_177946_b, func_152809_a, func_152806_b)) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Sent stream metadata sequence from {} to {}: {}", func_152844_x + n, func_152844_x + n2, metadata);
            }
            else {
                TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Half-sent stream metadata sequence from {} to {}: {}", func_152844_x + n, func_152844_x + n2, metadata);
            }
        }
    }
    
    @Override
    public boolean isPaused() {
        return this.broadcastController.isBroadcastPaused();
    }
    
    @Override
    public void func_152931_p() {
        if (this.broadcastController.func_152830_D()) {
            TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Requested commercial from Twitch");
        }
        else {
            TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Could not request commercial from Twitch");
        }
    }
    
    @Override
    public void func_152916_q() {
        this.broadcastController.func_152847_F();
        this.field_152962_n = true;
        this.func_152915_s();
    }
    
    @Override
    public void func_152933_r() {
        this.broadcastController.func_152854_G();
        this.field_152962_n = false;
        this.func_152915_s();
    }
    
    @Override
    public void func_152915_s() {
        if (this.func_152934_n()) {
            final float streamGameVolume = this.field_152953_e.gameSettings.streamGameVolume;
            this.broadcastController.func_152837_b((this.field_152962_n || streamGameVolume <= 0.0f) ? 0.0f : streamGameVolume);
            this.broadcastController.func_152829_a(this.func_152929_G() ? 0.0f : this.field_152953_e.gameSettings.streamMicVolume);
        }
    }
    
    @Override
    public void func_152930_t() {
        final GameSettings gameSettings = this.field_152953_e.gameSettings;
        final VideoParams func_152834_a = this.broadcastController.func_152834_a(func_152946_b(gameSettings.streamKbps), func_152948_a(gameSettings.streamFps), func_152947_c(gameSettings.streamBytesPerPixel), this.field_152953_e.displayWidth / (float)this.field_152953_e.displayHeight);
        switch (gameSettings.streamCompression) {
            case 0: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
                break;
            }
            case 1: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
                break;
            }
            case 2: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
                break;
            }
        }
        if (this.field_152956_h == null) {
            this.field_152956_h = new Framebuffer(func_152834_a.outputWidth, func_152834_a.outputHeight, false);
        }
        else {
            this.field_152956_h.createBindFramebuffer(func_152834_a.outputWidth, func_152834_a.outputHeight);
        }
        if (gameSettings.streamPreferredServer != null && gameSettings.streamPreferredServer.length() > 0) {
            final IngestServer[] func_152925_v = this.func_152925_v();
            while (0 < func_152925_v.length) {
                final IngestServer ingestServer = func_152925_v[0];
                if (ingestServer.serverUrl.equals(gameSettings.streamPreferredServer)) {
                    this.broadcastController.func_152824_a(ingestServer);
                    break;
                }
                int n = 0;
                ++n;
            }
        }
        this.field_152958_j = func_152834_a.targetFps;
        this.field_152957_i = gameSettings.streamSendMetadata;
        this.broadcastController.func_152836_a(func_152834_a);
        TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Streaming at {}/{} at {} kbps to {}", func_152834_a.outputWidth, func_152834_a.outputHeight, func_152834_a.maxKbps, this.broadcastController.func_152833_s().serverUrl);
        this.broadcastController.func_152828_a(null, "Minecraft", null);
    }
    
    @Override
    public void func_152914_u() {
        if (this.broadcastController.func_152819_E()) {
            TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Stopped streaming to Twitch");
        }
        else {
            TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Could not stop streaming to Twitch");
        }
    }
    
    @Override
    public void func_152900_a(final ErrorCode errorCode, final AuthToken authToken) {
    }
    
    @Override
    public void func_152897_a(final ErrorCode errorCode) {
        if (ErrorCode.succeeded(errorCode)) {
            TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Login attempt successful");
            this.field_152961_m = true;
        }
        else {
            TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Login attempt unsuccessful: {} (error code {})", ErrorCode.getString(errorCode), errorCode.getValue());
            this.field_152961_m = false;
        }
    }
    
    @Override
    public void func_152898_a(final ErrorCode errorCode, final GameInfo[] array) {
    }
    
    @Override
    public void func_152891_a(final BroadcastController.BroadcastState broadcastState) {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Broadcast state changed to {}", broadcastState);
        if (broadcastState == BroadcastController.BroadcastState.Initialized) {
            this.broadcastController.func_152827_a(BroadcastController.BroadcastState.Authenticated);
        }
    }
    
    @Override
    public void func_152895_a() {
        TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Logged out of twitch");
    }
    
    @Override
    public void func_152894_a(final StreamInfo streamInfo) {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Stream info updated; {} viewers on stream ID {}", streamInfo.viewers, streamInfo.streamId);
    }
    
    @Override
    public void func_152896_a(final IngestList list) {
    }
    
    @Override
    public void func_152893_b(final ErrorCode errorCode) {
        TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Issue submitting frame: {} (Error code {})", ErrorCode.getString(errorCode), errorCode.getValue());
        Minecraft.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText("Issue streaming frame: " + errorCode + " (" + ErrorCode.getString(errorCode) + ")"), 2);
    }
    
    @Override
    public void func_152899_b() {
        this.func_152915_s();
        TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Broadcast to Twitch has started");
    }
    
    @Override
    public void func_152901_c() {
        TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Broadcast to Twitch has stopped");
    }
    
    @Override
    public void func_152892_c(final ErrorCode errorCode) {
        if (errorCode == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stream.unavailable.soundflower.chat.link", new Object[0]);
            chatComponentTranslation.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"));
            chatComponentTranslation.getChatStyle().setUnderlined(true);
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("stream.unavailable.soundflower.chat", new Object[] { chatComponentTranslation });
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            Minecraft.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation2);
        }
        else {
            final ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation("stream.unavailable.unknown.chat", new Object[] { ErrorCode.getString(errorCode) });
            chatComponentTranslation3.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            Minecraft.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation3);
        }
    }
    
    @Override
    public void func_152907_a(final IngestServerTester ingestServerTester, final IngestServerTester.IngestTestState ingestTestState) {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Ingest test state changed to {}", ingestTestState);
        if (ingestTestState == IngestServerTester.IngestTestState.Finished) {
            this.field_152960_l = true;
        }
    }
    
    public static int func_152948_a(final float n) {
        return MathHelper.floor_float(10.0f + n * 50.0f);
    }
    
    public static int func_152946_b(final float n) {
        return MathHelper.floor_float(230.0f + n * 3270.0f);
    }
    
    public static float func_152947_c(final float n) {
        return 0.1f + n * 0.1f;
    }
    
    @Override
    public IngestServer[] func_152925_v() {
        return this.broadcastController.func_152855_t().getServers();
    }
    
    @Override
    public void func_152909_x() {
        final IngestServerTester func_152838_J = this.broadcastController.func_152838_J();
        if (func_152838_J != null) {
            func_152838_J.func_153042_a(this);
        }
    }
    
    @Override
    public IngestServerTester func_152932_y() {
        return this.broadcastController.isReady();
    }
    
    @Override
    public boolean func_152908_z() {
        return this.broadcastController.isIngestTesting();
    }
    
    @Override
    public int func_152920_A() {
        return this.func_152934_n() ? this.broadcastController.func_152816_j().viewers : 0;
    }
    
    @Override
    public void func_176023_d(final ErrorCode errorCode) {
        if (ErrorCode.failed(errorCode)) {
            TwitchStream.field_152950_b.error(TwitchStream.field_152949_a, "Chat failed to initialize");
        }
    }
    
    @Override
    public void func_176022_e(final ErrorCode errorCode) {
        if (ErrorCode.failed(errorCode)) {
            TwitchStream.field_152950_b.error(TwitchStream.field_152949_a, "Chat failed to shutdown");
        }
    }
    
    @Override
    public void func_176017_a(final ChatController.ChatState chatState) {
    }
    
    @Override
    public void func_180605_a(final String s, final ChatRawMessage[] array) {
        while (0 < array.length) {
            final ChatRawMessage chatRawMessage = array[0];
            this.func_176027_a(chatRawMessage.userName, chatRawMessage);
            if (this.func_176028_a(chatRawMessage.modes, chatRawMessage.subscriptions, this.field_152953_e.gameSettings.streamChatUserFilter)) {
                final ChatComponentText chatComponentText = new ChatComponentText(chatRawMessage.userName);
                final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.stream." + (chatRawMessage.action ? "emote" : "text"), new Object[] { this.field_152954_f, chatComponentText, EnumChatFormatting.getTextWithoutFormattingCodes(chatRawMessage.message) });
                if (chatRawMessage.action) {
                    chatComponentTranslation.getChatStyle().setItalic(true);
                }
                final ChatComponentText chatComponentText2 = new ChatComponentText("");
                chatComponentText2.appendSibling(new ChatComponentTranslation("stream.userinfo.chatTooltip", new Object[0]));
                for (final IChatComponent chatComponent : GuiTwitchUserMode.func_152328_a(chatRawMessage.modes, chatRawMessage.subscriptions, null)) {
                    chatComponentText2.appendText("\n");
                    chatComponentText2.appendSibling(chatComponent);
                }
                chatComponentText.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, chatComponentText2));
                chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.TWITCH_USER_INFO, chatRawMessage.userName));
                Minecraft.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void func_176025_a(final String s, final ChatTokenizedMessage[] array) {
    }
    
    private void func_176027_a(final String displayName, final ChatRawMessage chatRawMessage) {
        ChatUserInfo chatUserInfo = this.field_152955_g.get(displayName);
        if (chatUserInfo == null) {
            chatUserInfo = new ChatUserInfo();
            chatUserInfo.displayName = displayName;
            this.field_152955_g.put(displayName, chatUserInfo);
        }
        chatUserInfo.subscriptions = chatRawMessage.subscriptions;
        chatUserInfo.modes = chatRawMessage.modes;
        chatUserInfo.nameColorARGB = chatRawMessage.nameColorARGB;
    }
    
    private boolean func_176028_a(final Set set, final Set set2, final int n) {
        return !set.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED) && (set.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) || set.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) || set.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF) || n == 0 || (n == 1 && set2.contains(ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER)));
    }
    
    @Override
    public void func_176018_a(final String s, final ChatUserInfo[] array, final ChatUserInfo[] array2, final ChatUserInfo[] array3) {
        int n = 0;
        while (0 < array2.length) {
            this.field_152955_g.remove(array2[0].displayName);
            ++n;
        }
        while (0 < array3.length) {
            final ChatUserInfo chatUserInfo = array3[0];
            this.field_152955_g.put(chatUserInfo.displayName, chatUserInfo);
            ++n;
        }
        while (0 < array.length) {
            final ChatUserInfo chatUserInfo2 = array[0];
            this.field_152955_g.put(chatUserInfo2.displayName, chatUserInfo2);
            ++n;
        }
    }
    
    @Override
    public void func_180606_a(final String s) {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Chat connected");
    }
    
    @Override
    public void func_180607_b(final String s) {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Chat disconnected");
        this.field_152955_g.clear();
    }
    
    @Override
    public void func_176019_a(final String s, final String s2) {
    }
    
    @Override
    public void func_176021_d() {
    }
    
    @Override
    public void func_176024_e() {
    }
    
    @Override
    public void func_176016_c(final String s) {
    }
    
    @Override
    public void func_176020_d(final String s) {
    }
    
    @Override
    public boolean func_152927_B() {
        return this.field_176029_e != null && this.field_176029_e.equals(this.broadcastController.func_152843_l().name);
    }
    
    @Override
    public String func_152921_C() {
        return this.field_176029_e;
    }
    
    @Override
    public ChatUserInfo func_152926_a(final String s) {
        return this.field_152955_g.get(s);
    }
    
    @Override
    public void func_152917_b(final String s) {
        this.field_152952_d.func_175986_a(this.field_176029_e, s);
    }
    
    @Override
    public boolean func_152928_D() {
        return TwitchStream.field_152965_q && this.broadcastController.func_152858_b();
    }
    
    @Override
    public ErrorCode func_152912_E() {
        return TwitchStream.field_152965_q ? this.broadcastController.func_152852_P() : ErrorCode.TTV_EC_OS_TOO_OLD;
    }
    
    @Override
    public boolean func_152913_F() {
        return this.field_152961_m;
    }
    
    @Override
    public void func_152910_a(final boolean field_152963_o) {
        this.field_152963_o = field_152963_o;
        this.func_152915_s();
    }
    
    @Override
    public boolean func_152929_G() {
        final boolean b = this.field_152953_e.gameSettings.streamMicToggleBehavior == 1;
        return this.field_152962_n || this.field_152953_e.gameSettings.streamMicVolume <= 0.0f || b != this.field_152963_o;
    }
    
    @Override
    public AuthFailureReason func_152918_H() {
        return this.field_152964_p;
    }
    
    static Logger access$0() {
        return TwitchStream.field_152950_b;
    }
    
    static BroadcastController access$1(final TwitchStream twitchStream) {
        return twitchStream.broadcastController;
    }
    
    static ChatController access$2(final TwitchStream twitchStream) {
        return twitchStream.field_152952_d;
    }
    
    static void access$3(final TwitchStream twitchStream, final AuthFailureReason field_152964_p) {
        twitchStream.field_152964_p = field_152964_p;
    }
}
