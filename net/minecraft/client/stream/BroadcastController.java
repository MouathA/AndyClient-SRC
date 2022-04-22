package net.minecraft.client.stream;

import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import tv.twitch.*;
import tv.twitch.broadcast.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BroadcastController
{
    private static final Logger logger;
    protected final int field_152865_a = 30;
    protected final int field_152866_b = 3;
    private static final ThreadSafeBoundList field_152862_C;
    private String field_152863_D;
    protected BroadcastListener field_152867_c;
    protected String field_152868_d;
    protected String field_152869_e;
    protected String field_152870_f;
    protected boolean field_152871_g;
    protected Core field_152872_h;
    protected Stream field_152873_i;
    protected List field_152874_j;
    protected List field_152875_k;
    protected boolean field_152876_l;
    protected boolean field_152877_m;
    protected boolean field_152878_n;
    protected BroadcastState broadcastState;
    protected String field_152880_p;
    protected VideoParams field_152881_q;
    protected AudioParams field_152882_r;
    protected IngestList field_152883_s;
    protected IngestServer field_152884_t;
    protected AuthToken field_152885_u;
    protected ChannelInfo channelInfo;
    protected UserInfo field_152887_w;
    protected StreamInfo field_152888_x;
    protected ArchivingState field_152889_y;
    protected long field_152890_z;
    protected IngestServerTester field_152860_A;
    private ErrorCode field_152864_E;
    protected IStreamCallbacks field_177948_B;
    protected IStatCallbacks field_177949_C;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001822";
        logger = LogManager.getLogger();
        field_152862_C = new ThreadSafeBoundList(String.class, 50);
    }
    
    public void func_152841_a(final BroadcastListener field_152867_c) {
        this.field_152867_c = field_152867_c;
    }
    
    public boolean func_152858_b() {
        return this.field_152876_l;
    }
    
    public void func_152842_a(final String field_152868_d) {
        this.field_152868_d = field_152868_d;
    }
    
    public StreamInfo func_152816_j() {
        return this.field_152888_x;
    }
    
    public ChannelInfo func_152843_l() {
        return this.channelInfo;
    }
    
    public boolean isBroadcasting() {
        return this.broadcastState == BroadcastState.Broadcasting || this.broadcastState == BroadcastState.Paused;
    }
    
    public boolean func_152857_n() {
        return this.broadcastState == BroadcastState.ReadyToBroadcast;
    }
    
    public boolean isIngestTesting() {
        return this.broadcastState == BroadcastState.IngestTesting;
    }
    
    public boolean isBroadcastPaused() {
        return this.broadcastState == BroadcastState.Paused;
    }
    
    public boolean func_152849_q() {
        return this.field_152877_m;
    }
    
    public IngestServer func_152833_s() {
        return this.field_152884_t;
    }
    
    public void func_152824_a(final IngestServer field_152884_t) {
        this.field_152884_t = field_152884_t;
    }
    
    public IngestList func_152855_t() {
        return this.field_152883_s;
    }
    
    public void func_152829_a(final float n) {
        this.field_152873_i.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, n);
    }
    
    public void func_152837_b(final float n) {
        this.field_152873_i.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, n);
    }
    
    public IngestServerTester isReady() {
        return this.field_152860_A;
    }
    
    public long func_152844_x() {
        return this.field_152873_i.getStreamTime();
    }
    
    protected boolean func_152848_y() {
        return true;
    }
    
    public ErrorCode func_152852_P() {
        return this.field_152864_E;
    }
    
    public BroadcastController() {
        this.field_152863_D = null;
        this.field_152867_c = null;
        this.field_152868_d = "";
        this.field_152869_e = "";
        this.field_152870_f = "";
        this.field_152871_g = true;
        this.field_152872_h = null;
        this.field_152873_i = null;
        this.field_152874_j = Lists.newArrayList();
        this.field_152875_k = Lists.newArrayList();
        this.field_152876_l = false;
        this.field_152877_m = false;
        this.field_152878_n = false;
        this.broadcastState = BroadcastState.Uninitialized;
        this.field_152880_p = null;
        this.field_152881_q = null;
        this.field_152882_r = null;
        this.field_152883_s = new IngestList(new IngestServer[0]);
        this.field_152884_t = null;
        this.field_152885_u = new AuthToken();
        this.channelInfo = new ChannelInfo();
        this.field_152887_w = new UserInfo();
        this.field_152888_x = new StreamInfo();
        this.field_152889_y = new ArchivingState();
        this.field_152890_z = 0L;
        this.field_152860_A = null;
        this.field_177948_B = new IStreamCallbacks() {
            private static final String __OBFID;
            final BroadcastController this$0;
            
            @Override
            public void requestAuthTokenCallback(final ErrorCode errorCode, final AuthToken field_152885_u) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_152885_u = field_152885_u;
                    this.this$0.func_152827_a(BroadcastState.Authenticated);
                }
                else {
                    this.this$0.field_152885_u.data = "";
                    this.this$0.func_152827_a(BroadcastState.Initialized);
                    this.this$0.func_152820_d(String.format("RequestAuthTokenDoneCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
                if (this.this$0.field_152867_c != null) {
                    this.this$0.field_152867_c.func_152900_a(errorCode, field_152885_u);
                }
            }
            
            @Override
            public void loginCallback(final ErrorCode errorCode, final ChannelInfo channelInfo) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.channelInfo = channelInfo;
                    this.this$0.func_152827_a(BroadcastState.LoggedIn);
                    this.this$0.field_152877_m = true;
                }
                else {
                    this.this$0.func_152827_a(BroadcastState.Initialized);
                    this.this$0.field_152877_m = false;
                    this.this$0.func_152820_d(String.format("LoginCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
                if (this.this$0.field_152867_c != null) {
                    this.this$0.field_152867_c.func_152897_a(errorCode);
                }
            }
            
            @Override
            public void getIngestServersCallback(final ErrorCode errorCode, final IngestList field_152883_s) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_152883_s = field_152883_s;
                    this.this$0.field_152884_t = this.this$0.field_152883_s.getDefaultServer();
                    this.this$0.func_152827_a(BroadcastState.ReceivedIngestServers);
                    if (this.this$0.field_152867_c != null) {
                        this.this$0.field_152867_c.func_152896_a(field_152883_s);
                    }
                }
                else {
                    this.this$0.func_152820_d(String.format("IngestListCallback got failure: %s", ErrorCode.getString(errorCode)));
                    this.this$0.func_152827_a(BroadcastState.LoggingIn);
                }
            }
            
            @Override
            public void getUserInfoCallback(final ErrorCode errorCode, final UserInfo field_152887_w) {
                this.this$0.field_152887_w = field_152887_w;
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("UserInfoDoneCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            @Override
            public void getStreamInfoCallback(final ErrorCode errorCode, final StreamInfo field_152888_x) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_152888_x = field_152888_x;
                    if (this.this$0.field_152867_c != null) {
                        this.this$0.field_152867_c.func_152894_a(field_152888_x);
                    }
                }
                else {
                    this.this$0.func_152832_e(String.format("StreamInfoDoneCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            @Override
            public void getArchivingStateCallback(final ErrorCode errorCode, final ArchivingState field_152889_y) {
                this.this$0.field_152889_y = field_152889_y;
                ErrorCode.failed(errorCode);
            }
            
            @Override
            public void runCommercialCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152832_e(String.format("RunCommercialCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            @Override
            public void setStreamInfoCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152832_e(String.format("SetStreamInfoCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            @Override
            public void getGameNameListCallback(final ErrorCode errorCode, final GameInfoList list) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("GameNameListCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
                if (this.this$0.field_152867_c != null) {
                    this.this$0.field_152867_c.func_152898_a(errorCode, (list == null) ? new GameInfo[0] : list.list);
                }
            }
            
            @Override
            public void bufferUnlockCallback(final long n) {
                this.this$0.field_152875_k.add(FrameBuffer.lookupBuffer(n));
            }
            
            @Override
            public void startCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    if (this.this$0.field_152867_c != null) {
                        this.this$0.field_152867_c.func_152899_b();
                    }
                    this.this$0.func_152827_a(BroadcastState.Broadcasting);
                }
                else {
                    this.this$0.field_152881_q = null;
                    this.this$0.field_152882_r = null;
                    this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                    if (this.this$0.field_152867_c != null) {
                        this.this$0.field_152867_c.func_152892_c(errorCode);
                    }
                    this.this$0.func_152820_d(String.format("startCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            @Override
            public void stopCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_152881_q = null;
                    this.this$0.field_152882_r = null;
                    this.this$0.func_152831_M();
                    if (this.this$0.field_152867_c != null) {
                        this.this$0.field_152867_c.func_152901_c();
                    }
                    if (this.this$0.field_152877_m) {
                        this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                    }
                    else {
                        this.this$0.func_152827_a(BroadcastState.Initialized);
                    }
                }
                else {
                    this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                    this.this$0.func_152820_d(String.format("stopCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            @Override
            public void sendActionMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("sendActionMetaDataCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            @Override
            public void sendStartSpanMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("sendStartSpanMetaDataCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            @Override
            public void sendEndSpanMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("sendEndSpanMetaDataCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            static {
                __OBFID = "CL_00002375";
            }
        };
        this.field_177949_C = new IStatCallbacks() {
            private static final String __OBFID;
            final BroadcastController this$0;
            
            @Override
            public void statCallback(final StatType statType, final long n) {
            }
            
            static {
                __OBFID = "CL_00002374";
            }
        };
        this.field_152872_h = Core.getInstance();
        if (Core.getInstance() == null) {
            this.field_152872_h = new Core(new StandardCoreAPI());
        }
        this.field_152873_i = new Stream(new DesktopStreamAPI());
    }
    
    protected PixelFormat func_152826_z() {
        return PixelFormat.TTV_PF_RGBA;
    }
    
    public boolean func_152817_A() {
        if (this.field_152876_l) {
            return false;
        }
        this.field_152873_i.setStreamCallbacks(this.field_177948_B);
        final ErrorCode initialize = this.field_152872_h.initialize(this.field_152868_d, System.getProperty("java.library.path"));
        if (!this.func_152853_a(initialize)) {
            this.field_152873_i.setStreamCallbacks(null);
            this.field_152864_E = initialize;
            return false;
        }
        final ErrorCode setTraceLevel = this.field_152872_h.setTraceLevel(MessageLevel.TTV_ML_ERROR);
        if (!this.func_152853_a(setTraceLevel)) {
            this.field_152873_i.setStreamCallbacks(null);
            this.field_152872_h.shutdown();
            this.field_152864_E = setTraceLevel;
            return false;
        }
        if (ErrorCode.succeeded(setTraceLevel)) {
            this.field_152876_l = true;
            this.func_152827_a(BroadcastState.Initialized);
            return true;
        }
        this.field_152864_E = setTraceLevel;
        this.field_152872_h.shutdown();
        return false;
    }
    
    public boolean func_152851_B() {
        if (!this.field_152876_l) {
            return true;
        }
        if (this.isIngestTesting()) {
            return false;
        }
        this.field_152878_n = true;
        this.func_152845_C();
        this.field_152873_i.setStreamCallbacks(null);
        this.field_152873_i.setStatCallbacks(null);
        this.func_152853_a(this.field_152872_h.shutdown());
        this.field_152876_l = false;
        this.field_152878_n = false;
        this.func_152827_a(BroadcastState.Uninitialized);
        return true;
    }
    
    public void statCallback() {
        if (this.broadcastState != BroadcastState.Uninitialized) {
            if (this.field_152860_A != null) {
                this.field_152860_A.func_153039_l();
            }
            while (this.field_152860_A != null) {
                Thread.sleep(200L);
                this.func_152821_H();
            }
            this.func_152851_B();
        }
    }
    
    public boolean func_152818_a(final String field_152880_p, final AuthToken field_152885_u) {
        if (this.isIngestTesting()) {
            return false;
        }
        this.func_152845_C();
        if (field_152880_p == null || field_152880_p.isEmpty()) {
            this.func_152820_d("Username must be valid");
            return false;
        }
        if (field_152885_u != null && field_152885_u.data != null && !field_152885_u.data.isEmpty()) {
            this.field_152880_p = field_152880_p;
            this.field_152885_u = field_152885_u;
            if (this.func_152858_b()) {
                this.func_152827_a(BroadcastState.Authenticated);
            }
            return true;
        }
        this.func_152820_d("Auth token must be valid");
        return false;
    }
    
    public boolean func_152845_C() {
        if (this.isIngestTesting()) {
            return false;
        }
        if (this.isBroadcasting()) {
            this.field_152873_i.stop(false);
        }
        this.field_152880_p = "";
        this.field_152885_u = new AuthToken();
        if (!this.field_152877_m) {
            return false;
        }
        this.field_152877_m = false;
        if (!this.field_152878_n && this.field_152867_c != null) {
            this.field_152867_c.func_152895_a();
        }
        this.func_152827_a(BroadcastState.Initialized);
        return true;
    }
    
    public boolean func_152828_a(String field_152880_p, String gameName, String streamTitle) {
        if (!this.field_152877_m) {
            return false;
        }
        if (field_152880_p == null || field_152880_p.equals("")) {
            field_152880_p = this.field_152880_p;
        }
        if (gameName == null) {
            gameName = "";
        }
        if (streamTitle == null) {
            streamTitle = "";
        }
        final StreamInfoForSetting streamInfoForSetting = new StreamInfoForSetting();
        streamInfoForSetting.streamTitle = streamTitle;
        streamInfoForSetting.gameName = gameName;
        final ErrorCode setStreamInfo = this.field_152873_i.setStreamInfo(this.field_152885_u, field_152880_p, streamInfoForSetting);
        this.func_152853_a(setStreamInfo);
        return ErrorCode.succeeded(setStreamInfo);
    }
    
    public boolean func_152830_D() {
        if (!this.isBroadcasting()) {
            return false;
        }
        final ErrorCode runCommercial = this.field_152873_i.runCommercial(this.field_152885_u);
        this.func_152853_a(runCommercial);
        return ErrorCode.succeeded(runCommercial);
    }
    
    public VideoParams func_152834_a(final int maxKbps, final int targetFps, final float n, final float n2) {
        final int[] maxResolution = this.field_152873_i.getMaxResolution(maxKbps, targetFps, n, n2);
        final VideoParams videoParams = new VideoParams();
        videoParams.maxKbps = maxKbps;
        videoParams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
        videoParams.pixelFormat = this.func_152826_z();
        videoParams.targetFps = targetFps;
        videoParams.outputWidth = maxResolution[0];
        videoParams.outputHeight = maxResolution[1];
        videoParams.disableAdaptiveBitrate = false;
        videoParams.verticalFlip = false;
        return videoParams;
    }
    
    public boolean func_152836_a(final VideoParams videoParams) {
        if (videoParams == null || !this.func_152857_n()) {
            return false;
        }
        this.field_152881_q = videoParams.clone();
        this.field_152882_r = new AudioParams();
        this.field_152882_r.audioEnabled = (this.field_152871_g && this.func_152848_y());
        this.field_152882_r.enableMicCapture = this.field_152882_r.audioEnabled;
        this.field_152882_r.enablePlaybackCapture = this.field_152882_r.audioEnabled;
        this.field_152882_r.enablePassthroughAudio = false;
        if (!this.func_152823_L()) {
            this.field_152881_q = null;
            this.field_152882_r = null;
            return false;
        }
        final ErrorCode start = this.field_152873_i.start(videoParams, this.field_152882_r, this.field_152884_t, StartFlags.None, true);
        if (ErrorCode.failed(start)) {
            this.func_152831_M();
            this.func_152820_d(String.format("Error while starting to broadcast: %s", ErrorCode.getString(start)));
            this.field_152881_q = null;
            this.field_152882_r = null;
            return false;
        }
        this.func_152827_a(BroadcastState.Starting);
        return true;
    }
    
    public boolean func_152819_E() {
        if (!this.isBroadcasting()) {
            return false;
        }
        final ErrorCode stop = this.field_152873_i.stop(true);
        if (ErrorCode.failed(stop)) {
            this.func_152820_d(String.format("Error while stopping the broadcast: %s", ErrorCode.getString(stop)));
            return false;
        }
        this.func_152827_a(BroadcastState.Stopping);
        return ErrorCode.succeeded(stop);
    }
    
    public boolean func_152847_F() {
        if (!this.isBroadcasting()) {
            return false;
        }
        final ErrorCode pauseVideo = this.field_152873_i.pauseVideo();
        if (ErrorCode.failed(pauseVideo)) {
            this.func_152819_E();
            this.func_152820_d(String.format("Error pausing stream: %s\n", ErrorCode.getString(pauseVideo)));
        }
        else {
            this.func_152827_a(BroadcastState.Paused);
        }
        return ErrorCode.succeeded(pauseVideo);
    }
    
    public boolean func_152854_G() {
        if (!this.isBroadcastPaused()) {
            return false;
        }
        this.func_152827_a(BroadcastState.Broadcasting);
        return true;
    }
    
    public boolean func_152840_a(final String s, final long n, final String s2, final String s3) {
        final ErrorCode sendActionMetaData = this.field_152873_i.sendActionMetaData(this.field_152885_u, s, n, s2, s3);
        if (ErrorCode.failed(sendActionMetaData)) {
            this.func_152820_d(String.format("Error while sending meta data: %s\n", ErrorCode.getString(sendActionMetaData)));
            return false;
        }
        return true;
    }
    
    public long func_177946_b(final String s, final long n, final String s2, final String s3) {
        final long sendStartSpanMetaData = this.field_152873_i.sendStartSpanMetaData(this.field_152885_u, s, n, s2, s3);
        if (sendStartSpanMetaData == -1L) {
            this.func_152820_d(String.format("Error in SendStartSpanMetaData\n", new Object[0]));
        }
        return sendStartSpanMetaData;
    }
    
    public boolean func_177947_a(final String s, final long n, final long n2, final String s2, final String s3) {
        if (n2 == -1L) {
            this.func_152820_d(String.format("Invalid sequence id: %d\n", n2));
            return false;
        }
        final ErrorCode sendEndSpanMetaData = this.field_152873_i.sendEndSpanMetaData(this.field_152885_u, s, n, n2, s2, s3);
        if (ErrorCode.failed(sendEndSpanMetaData)) {
            this.func_152820_d(String.format("Error in SendStopSpanMetaData: %s\n", ErrorCode.getString(sendEndSpanMetaData)));
            return false;
        }
        return true;
    }
    
    protected void func_152827_a(final BroadcastState broadcastState) {
        if (broadcastState != this.broadcastState) {
            this.broadcastState = broadcastState;
            if (this.field_152867_c != null) {
                this.field_152867_c.func_152891_a(broadcastState);
            }
        }
    }
    
    public void func_152821_H() {
        if (this.field_152873_i != null && this.field_152876_l) {
            this.func_152853_a(this.field_152873_i.pollTasks());
            if (this.isIngestTesting()) {
                this.field_152860_A.func_153041_j();
                if (this.field_152860_A.func_153032_e()) {
                    this.field_152860_A = null;
                    this.func_152827_a(BroadcastState.ReadyToBroadcast);
                }
            }
            switch (SwitchBroadcastState.field_177773_a[this.broadcastState.ordinal()]) {
                case 1: {
                    this.func_152827_a(BroadcastState.LoggingIn);
                    final ErrorCode login = this.field_152873_i.login(this.field_152885_u);
                    if (ErrorCode.failed(login)) {
                        this.func_152820_d(String.format("Error in TTV_Login: %s\n", ErrorCode.getString(login)));
                        break;
                    }
                    break;
                }
                case 2: {
                    this.func_152827_a(BroadcastState.FindingIngestServer);
                    final ErrorCode ingestServers = this.field_152873_i.getIngestServers(this.field_152885_u);
                    if (ErrorCode.failed(ingestServers)) {
                        this.func_152827_a(BroadcastState.LoggedIn);
                        this.func_152820_d(String.format("Error in TTV_GetIngestServers: %s\n", ErrorCode.getString(ingestServers)));
                        break;
                    }
                    break;
                }
                case 3: {
                    this.func_152827_a(BroadcastState.ReadyToBroadcast);
                    final ErrorCode userInfo = this.field_152873_i.getUserInfo(this.field_152885_u);
                    if (ErrorCode.failed(userInfo)) {
                        this.func_152820_d(String.format("Error in TTV_GetUserInfo: %s\n", ErrorCode.getString(userInfo)));
                    }
                    this.func_152835_I();
                    final ErrorCode archivingState = this.field_152873_i.getArchivingState(this.field_152885_u);
                    if (ErrorCode.failed(archivingState)) {
                        this.func_152820_d(String.format("Error in TTV_GetArchivingState: %s\n", ErrorCode.getString(archivingState)));
                        break;
                    }
                    break;
                }
                case 11:
                case 12: {
                    this.func_152835_I();
                    break;
                }
            }
        }
    }
    
    protected void func_152835_I() {
        final long nanoTime = System.nanoTime();
        if ((nanoTime - this.field_152890_z) / 1000000000L >= 30L) {
            this.field_152890_z = nanoTime;
            final ErrorCode streamInfo = this.field_152873_i.getStreamInfo(this.field_152885_u, this.field_152880_p);
            if (ErrorCode.failed(streamInfo)) {
                this.func_152820_d(String.format("Error in TTV_GetStreamInfo: %s", ErrorCode.getString(streamInfo)));
            }
        }
    }
    
    public IngestServerTester func_152838_J() {
        if (!this.func_152857_n() || this.field_152883_s == null) {
            return null;
        }
        if (this.isIngestTesting()) {
            return null;
        }
        (this.field_152860_A = new IngestServerTester(this.field_152873_i, this.field_152883_s)).func_176004_j();
        this.func_152827_a(BroadcastState.IngestTesting);
        return this.field_152860_A;
    }
    
    protected boolean func_152823_L() {
        while (0 < 3) {
            final FrameBuffer allocateFrameBuffer = this.field_152873_i.allocateFrameBuffer(this.field_152881_q.outputWidth * this.field_152881_q.outputHeight * 4);
            if (!allocateFrameBuffer.getIsValid()) {
                this.func_152820_d(String.format("Error while allocating frame buffer", new Object[0]));
                return false;
            }
            this.field_152874_j.add(allocateFrameBuffer);
            this.field_152875_k.add(allocateFrameBuffer);
            int n = 0;
            ++n;
        }
        return true;
    }
    
    protected void func_152831_M() {
        while (0 < this.field_152874_j.size()) {
            this.field_152874_j.get(0).free();
            int n = 0;
            ++n;
        }
        this.field_152875_k.clear();
        this.field_152874_j.clear();
    }
    
    public FrameBuffer func_152822_N() {
        if (this.field_152875_k.size() == 0) {
            this.func_152820_d(String.format("Out of free buffers, this should never happen", new Object[0]));
            return null;
        }
        final FrameBuffer frameBuffer = this.field_152875_k.get(this.field_152875_k.size() - 1);
        this.field_152875_k.remove(this.field_152875_k.size() - 1);
        return frameBuffer;
    }
    
    public void func_152846_a(final FrameBuffer frameBuffer) {
        this.field_152873_i.captureFrameBuffer_ReadPixels(frameBuffer);
    }
    
    public ErrorCode func_152859_b(final FrameBuffer frameBuffer) {
        if (this.isBroadcastPaused()) {
            this.func_152854_G();
        }
        else if (!this.isBroadcasting()) {
            return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
        }
        final ErrorCode submitVideoFrame = this.field_152873_i.submitVideoFrame(frameBuffer);
        if (submitVideoFrame != ErrorCode.TTV_EC_SUCCESS) {
            final String string = ErrorCode.getString(submitVideoFrame);
            if (ErrorCode.succeeded(submitVideoFrame)) {
                this.func_152832_e(String.format("Warning in SubmitTexturePointer: %s\n", string));
            }
            else {
                this.func_152820_d(String.format("Error in SubmitTexturePointer: %s\n", string));
                this.func_152819_E();
            }
            if (this.field_152867_c != null) {
                this.field_152867_c.func_152893_b(submitVideoFrame);
            }
        }
        return submitVideoFrame;
    }
    
    protected boolean func_152853_a(final ErrorCode errorCode) {
        if (ErrorCode.failed(errorCode)) {
            this.func_152820_d(ErrorCode.getString(errorCode));
            return false;
        }
        return true;
    }
    
    protected void func_152820_d(final String field_152863_D) {
        this.field_152863_D = field_152863_D;
        BroadcastController.field_152862_C.func_152757_a("<Error> " + field_152863_D);
        BroadcastController.logger.error(TwitchStream.field_152949_a, "[Broadcast controller] {}", field_152863_D);
    }
    
    protected void func_152832_e(final String s) {
        BroadcastController.field_152862_C.func_152757_a("<Warning> " + s);
        BroadcastController.logger.warn(TwitchStream.field_152949_a, "[Broadcast controller] {}", s);
    }
    
    public interface BroadcastListener
    {
        void func_152900_a(final ErrorCode p0, final AuthToken p1);
        
        void func_152897_a(final ErrorCode p0);
        
        void func_152898_a(final ErrorCode p0, final GameInfo[] p1);
        
        void func_152891_a(final BroadcastState p0);
        
        void func_152895_a();
        
        void func_152894_a(final StreamInfo p0);
        
        void func_152896_a(final IngestList p0);
        
        void func_152893_b(final ErrorCode p0);
        
        void func_152899_b();
        
        void func_152901_c();
        
        void func_152892_c(final ErrorCode p0);
    }
    
    public enum BroadcastState
    {
        Uninitialized("Uninitialized", 0, "Uninitialized", 0), 
        Initialized("Initialized", 1, "Initialized", 1), 
        Authenticating("Authenticating", 2, "Authenticating", 2), 
        Authenticated("Authenticated", 3, "Authenticated", 3), 
        LoggingIn("LoggingIn", 4, "LoggingIn", 4), 
        LoggedIn("LoggedIn", 5, "LoggedIn", 5), 
        FindingIngestServer("FindingIngestServer", 6, "FindingIngestServer", 6), 
        ReceivedIngestServers("ReceivedIngestServers", 7, "ReceivedIngestServers", 7), 
        ReadyToBroadcast("ReadyToBroadcast", 8, "ReadyToBroadcast", 8), 
        Starting("Starting", 9, "Starting", 9), 
        Broadcasting("Broadcasting", 10, "Broadcasting", 10), 
        Stopping("Stopping", 11, "Stopping", 11), 
        Paused("Paused", 12, "Paused", 12), 
        IngestTesting("IngestTesting", 13, "IngestTesting", 13);
        
        private static final BroadcastState[] $VALUES;
        private static final String __OBFID;
        private static final BroadcastState[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001820";
            ENUM$VALUES = new BroadcastState[] { BroadcastState.Uninitialized, BroadcastState.Initialized, BroadcastState.Authenticating, BroadcastState.Authenticated, BroadcastState.LoggingIn, BroadcastState.LoggedIn, BroadcastState.FindingIngestServer, BroadcastState.ReceivedIngestServers, BroadcastState.ReadyToBroadcast, BroadcastState.Starting, BroadcastState.Broadcasting, BroadcastState.Stopping, BroadcastState.Paused, BroadcastState.IngestTesting };
            $VALUES = new BroadcastState[] { BroadcastState.Uninitialized, BroadcastState.Initialized, BroadcastState.Authenticating, BroadcastState.Authenticated, BroadcastState.LoggingIn, BroadcastState.LoggedIn, BroadcastState.FindingIngestServer, BroadcastState.ReceivedIngestServers, BroadcastState.ReadyToBroadcast, BroadcastState.Starting, BroadcastState.Broadcasting, BroadcastState.Stopping, BroadcastState.Paused, BroadcastState.IngestTesting };
        }
        
        private BroadcastState(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    static final class SwitchBroadcastState
    {
        static final int[] field_177773_a;
        private static final String __OBFID;
        private static final String[] lIlIllIIlIlIlIll;
        private static String[] lIlIllIIlIlIllII;
        
        static {
            lllllllIIIllllll();
            lllllllIIIlllllI();
            __OBFID = SwitchBroadcastState.lIlIllIIlIlIlIll[0];
            field_177773_a = new int[BroadcastState.values().length];
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.Authenticated.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.LoggedIn.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.ReceivedIngestServers.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.Starting.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.Stopping.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.FindingIngestServer.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.Authenticating.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.Initialized.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.Uninitialized.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.IngestTesting.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.Paused.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                SwitchBroadcastState.field_177773_a[BroadcastState.Broadcasting.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
        }
        
        private static void lllllllIIIlllllI() {
            (lIlIllIIlIlIlIll = new String[1])[0] = lllllllIIIllllIl(SwitchBroadcastState.lIlIllIIlIlIllII[0], SwitchBroadcastState.lIlIllIIlIlIllII[1]);
            SwitchBroadcastState.lIlIllIIlIlIllII = null;
        }
        
        private static void lllllllIIIllllll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchBroadcastState.lIlIllIIlIlIllII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lllllllIIIllllIl(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
                final Cipher instance = Cipher.getInstance("AES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
