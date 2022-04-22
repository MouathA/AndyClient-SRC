package net.minecraft.client.stream;

import tv.twitch.*;
import com.google.common.collect.*;
import tv.twitch.broadcast.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class IngestServerTester
{
    protected IngestTestListener field_153044_b;
    protected Stream field_153045_c;
    protected IngestList field_153046_d;
    protected IngestTestState field_153047_e;
    protected long field_153048_f;
    protected long field_153049_g;
    protected long field_153050_h;
    protected RTMPState field_153051_i;
    protected VideoParams field_153052_j;
    protected AudioParams field_153053_k;
    protected long field_153054_l;
    protected List field_153055_m;
    protected boolean field_153056_n;
    protected IStreamCallbacks field_153057_o;
    protected IStatCallbacks field_153058_p;
    protected IngestServer field_153059_q;
    protected boolean field_153060_r;
    protected boolean field_153061_s;
    protected int field_153062_t;
    protected int field_153063_u;
    protected long field_153064_v;
    protected float field_153065_w;
    protected float field_153066_x;
    protected boolean field_176009_x;
    protected boolean field_176008_y;
    protected boolean field_176007_z;
    protected IStreamCallbacks field_176005_A;
    protected IStatCallbacks field_176006_B;
    private static final String __OBFID;
    
    public void func_153042_a(final IngestTestListener field_153044_b) {
        this.field_153044_b = field_153044_b;
    }
    
    public IngestServer func_153040_c() {
        return this.field_153059_q;
    }
    
    public int func_153028_p() {
        return this.field_153062_t;
    }
    
    public boolean func_153032_e() {
        return this.field_153047_e == IngestTestState.Finished || this.field_153047_e == IngestTestState.Cancelled || this.field_153047_e == IngestTestState.Failed;
    }
    
    public float func_153030_h() {
        return this.field_153066_x;
    }
    
    public IngestServerTester(final Stream field_153045_c, final IngestList field_153046_d) {
        this.field_153044_b = null;
        this.field_153045_c = null;
        this.field_153046_d = null;
        this.field_153047_e = IngestTestState.Uninitalized;
        this.field_153048_f = 8000L;
        this.field_153049_g = 2000L;
        this.field_153050_h = 0L;
        this.field_153051_i = RTMPState.Invalid;
        this.field_153052_j = null;
        this.field_153053_k = null;
        this.field_153054_l = 0L;
        this.field_153055_m = null;
        this.field_153056_n = false;
        this.field_153057_o = null;
        this.field_153058_p = null;
        this.field_153059_q = null;
        this.field_153060_r = false;
        this.field_153061_s = false;
        this.field_153062_t = -1;
        this.field_153063_u = 0;
        this.field_153064_v = 0L;
        this.field_153065_w = 0.0f;
        this.field_153066_x = 0.0f;
        this.field_176009_x = false;
        this.field_176008_y = false;
        this.field_176007_z = false;
        this.field_176005_A = new IStreamCallbacks() {
            private static final String __OBFID;
            final IngestServerTester this$0;
            
            @Override
            public void requestAuthTokenCallback(final ErrorCode errorCode, final AuthToken authToken) {
            }
            
            @Override
            public void loginCallback(final ErrorCode errorCode, final ChannelInfo channelInfo) {
            }
            
            @Override
            public void getIngestServersCallback(final ErrorCode errorCode, final IngestList list) {
            }
            
            @Override
            public void getUserInfoCallback(final ErrorCode errorCode, final UserInfo userInfo) {
            }
            
            @Override
            public void getStreamInfoCallback(final ErrorCode errorCode, final StreamInfo streamInfo) {
            }
            
            @Override
            public void getArchivingStateCallback(final ErrorCode errorCode, final ArchivingState archivingState) {
            }
            
            @Override
            public void runCommercialCallback(final ErrorCode errorCode) {
            }
            
            @Override
            public void setStreamInfoCallback(final ErrorCode errorCode) {
            }
            
            @Override
            public void getGameNameListCallback(final ErrorCode errorCode, final GameInfoList list) {
            }
            
            @Override
            public void bufferUnlockCallback(final long n) {
            }
            
            @Override
            public void startCallback(final ErrorCode errorCode) {
                this.this$0.field_176008_y = false;
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_176009_x = true;
                    this.this$0.field_153054_l = System.currentTimeMillis();
                    this.this$0.func_153034_a(IngestTestState.ConnectingToServer);
                }
                else {
                    this.this$0.field_153056_n = false;
                    this.this$0.func_153034_a(IngestTestState.DoneTestingServer);
                }
            }
            
            @Override
            public void stopCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    System.out.println("IngestTester.stopCallback failed to stop - " + this.this$0.field_153059_q.serverName + ": " + errorCode.toString());
                }
                this.this$0.field_176007_z = false;
                this.this$0.field_176009_x = false;
                this.this$0.func_153034_a(IngestTestState.DoneTestingServer);
                this.this$0.field_153059_q = null;
                if (this.this$0.field_153060_r) {
                    this.this$0.func_153034_a(IngestTestState.Cancelling);
                }
            }
            
            @Override
            public void sendActionMetaDataCallback(final ErrorCode errorCode) {
            }
            
            @Override
            public void sendStartSpanMetaDataCallback(final ErrorCode errorCode) {
            }
            
            @Override
            public void sendEndSpanMetaDataCallback(final ErrorCode errorCode) {
            }
            
            static {
                __OBFID = "CL_00002368";
            }
        };
        this.field_176006_B = new IStatCallbacks() {
            private static final String __OBFID;
            final IngestServerTester this$0;
            
            @Override
            public void statCallback(final StatType statType, final long field_153050_h) {
                switch (SwitchStatType.field_176003_a[statType.ordinal()]) {
                    case 1: {
                        this.this$0.field_153051_i = RTMPState.lookupValue((int)field_153050_h);
                        break;
                    }
                    case 2: {
                        this.this$0.field_153050_h = field_153050_h;
                        break;
                    }
                }
            }
            
            static {
                __OBFID = "CL_00002367";
            }
        };
        this.field_153045_c = field_153045_c;
        this.field_153046_d = field_153046_d;
    }
    
    public void func_176004_j() {
        if (this.field_153047_e == IngestTestState.Uninitalized) {
            this.field_153062_t = 0;
            this.field_153060_r = false;
            this.field_153061_s = false;
            this.field_176009_x = false;
            this.field_176008_y = false;
            this.field_176007_z = false;
            this.field_153058_p = this.field_153045_c.getStatCallbacks();
            this.field_153045_c.setStatCallbacks(this.field_176006_B);
            this.field_153057_o = this.field_153045_c.getStreamCallbacks();
            this.field_153045_c.setStreamCallbacks(this.field_176005_A);
            this.field_153052_j = new VideoParams();
            this.field_153052_j.targetFps = 60;
            this.field_153052_j.maxKbps = 3500;
            this.field_153052_j.outputWidth = 1280;
            this.field_153052_j.outputHeight = 720;
            this.field_153052_j.pixelFormat = PixelFormat.TTV_PF_BGRA;
            this.field_153052_j.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
            this.field_153052_j.disableAdaptiveBitrate = true;
            this.field_153052_j.verticalFlip = false;
            this.field_153045_c.getDefaultParams(this.field_153052_j);
            this.field_153053_k = new AudioParams();
            this.field_153053_k.audioEnabled = false;
            this.field_153053_k.enableMicCapture = false;
            this.field_153053_k.enablePlaybackCapture = false;
            this.field_153053_k.enablePassthroughAudio = false;
            this.field_153055_m = Lists.newArrayList();
            while (0 < 3) {
                final FrameBuffer allocateFrameBuffer = this.field_153045_c.allocateFrameBuffer(this.field_153052_j.outputWidth * this.field_153052_j.outputHeight * 4);
                if (!allocateFrameBuffer.getIsValid()) {
                    this.func_153031_o();
                    this.func_153034_a(IngestTestState.Failed);
                    return;
                }
                this.field_153055_m.add(allocateFrameBuffer);
                this.field_153045_c.randomizeFrameBuffer(allocateFrameBuffer);
                int n = 0;
                ++n;
            }
            this.func_153034_a(IngestTestState.Starting);
            this.field_153054_l = System.currentTimeMillis();
        }
    }
    
    public void func_153041_j() {
        if (!this.func_153032_e() && this.field_153047_e != IngestTestState.Uninitalized && !this.field_176008_y && !this.field_176007_z) {
            switch (SwitchStatType.field_176002_b[this.field_153047_e.ordinal()]) {
                case 1:
                case 2: {
                    if (this.field_153059_q != null) {
                        if (this.field_153061_s || !this.field_153056_n) {
                            this.field_153059_q.bitrateKbps = 0.0f;
                        }
                        this.func_153035_b(this.field_153059_q);
                        break;
                    }
                    this.field_153054_l = 0L;
                    this.field_153061_s = false;
                    this.field_153056_n = true;
                    if (this.field_153047_e != IngestTestState.Starting) {
                        ++this.field_153062_t;
                    }
                    if (this.field_153062_t < this.field_153046_d.getServers().length) {
                        this.func_153036_a(this.field_153059_q = this.field_153046_d.getServers()[this.field_153062_t]);
                        break;
                    }
                    this.func_153034_a(IngestTestState.Finished);
                    break;
                }
                case 3:
                case 4: {
                    this.func_153029_c(this.field_153059_q);
                    break;
                }
                case 5: {
                    this.func_153034_a(IngestTestState.Cancelled);
                    break;
                }
            }
            this.func_153038_n();
            if (this.field_153047_e == IngestTestState.Cancelled || this.field_153047_e == IngestTestState.Finished) {
                this.func_153031_o();
            }
        }
    }
    
    public void func_153039_l() {
        if (!this.func_153032_e() && !this.field_153060_r) {
            this.field_153060_r = true;
            if (this.field_153059_q != null) {
                this.field_153059_q.bitrateKbps = 0.0f;
            }
        }
    }
    
    protected boolean func_153036_a(final IngestServer field_153059_q) {
        this.field_153056_n = true;
        this.field_153050_h = 0L;
        this.field_153051_i = RTMPState.Idle;
        this.field_153059_q = field_153059_q;
        this.field_176008_y = true;
        this.func_153034_a(IngestTestState.ConnectingToServer);
        if (ErrorCode.failed(this.field_153045_c.start(this.field_153052_j, this.field_153053_k, field_153059_q, StartFlags.TTV_Start_BandwidthTest, true))) {
            this.field_176008_y = false;
            this.field_153056_n = false;
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return false;
        }
        this.field_153064_v = this.field_153050_h;
        field_153059_q.bitrateKbps = 0.0f;
        this.field_153063_u = 0;
        return true;
    }
    
    protected void func_153035_b(final IngestServer ingestServer) {
        if (this.field_176008_y) {
            this.field_153061_s = true;
        }
        else if (this.field_176009_x) {
            this.field_176007_z = true;
            final ErrorCode stop = this.field_153045_c.stop(true);
            if (ErrorCode.failed(stop)) {
                this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
                System.out.println("Stop failed: " + stop.toString());
            }
            this.field_153045_c.pollStats();
        }
        else {
            this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
        }
    }
    
    protected long func_153037_m() {
        return System.currentTimeMillis() - this.field_153054_l;
    }
    
    protected void func_153038_n() {
        final float n = (float)this.func_153037_m();
        switch (SwitchStatType.field_176002_b[this.field_153047_e.ordinal()]) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9: {
                this.field_153066_x = 0.0f;
                break;
            }
            case 2: {
                this.field_153066_x = 1.0f;
                break;
            }
            default: {
                this.field_153066_x = n / this.field_153048_f;
                break;
            }
        }
        switch (SwitchStatType.field_176002_b[this.field_153047_e.ordinal()]) {
            case 7:
            case 8:
            case 9: {
                this.field_153065_w = 1.0f;
                break;
            }
            default: {
                this.field_153065_w = this.field_153062_t / (float)this.field_153046_d.getServers().length;
                this.field_153065_w += this.field_153066_x / this.field_153046_d.getServers().length;
                break;
            }
        }
    }
    
    protected boolean func_153029_c(final IngestServer ingestServer) {
        if (this.field_153061_s || this.field_153060_r || this.func_153037_m() >= this.field_153048_f) {
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return true;
        }
        if (this.field_176008_y || this.field_176007_z) {
            return true;
        }
        if (ErrorCode.failed(this.field_153045_c.submitVideoFrame(this.field_153055_m.get(this.field_153063_u)))) {
            this.field_153056_n = false;
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return false;
        }
        this.field_153063_u = (this.field_153063_u + 1) % this.field_153055_m.size();
        this.field_153045_c.pollStats();
        if (this.field_153051_i == RTMPState.SendVideo) {
            this.func_153034_a(IngestTestState.TestingServer);
            if (this.func_153037_m() > 0L && this.field_153050_h > this.field_153064_v) {
                ingestServer.bitrateKbps = this.field_153050_h * 8L / (float)this.func_153037_m();
                this.field_153064_v = this.field_153050_h;
            }
        }
        return true;
    }
    
    protected void func_153031_o() {
        this.field_153059_q = null;
        if (this.field_153055_m != null) {
            while (0 < this.field_153055_m.size()) {
                this.field_153055_m.get(0).free();
                int n = 0;
                ++n;
            }
            this.field_153055_m = null;
        }
        if (this.field_153045_c.getStatCallbacks() == this.field_176006_B) {
            this.field_153045_c.setStatCallbacks(this.field_153058_p);
            this.field_153058_p = null;
        }
        if (this.field_153045_c.getStreamCallbacks() == this.field_176005_A) {
            this.field_153045_c.setStreamCallbacks(this.field_153057_o);
            this.field_153057_o = null;
        }
    }
    
    protected void func_153034_a(final IngestTestState field_153047_e) {
        if (field_153047_e != this.field_153047_e) {
            this.field_153047_e = field_153047_e;
            if (this.field_153044_b != null) {
                this.field_153044_b.func_152907_a(this, field_153047_e);
            }
        }
    }
    
    static {
        __OBFID = "CL_00001816";
    }
    
    public interface IngestTestListener
    {
        void func_152907_a(final IngestServerTester p0, final IngestTestState p1);
    }
    
    public enum IngestTestState
    {
        Uninitalized("Uninitalized", 0, "Uninitalized", 0), 
        Starting("Starting", 1, "Starting", 1), 
        ConnectingToServer("ConnectingToServer", 2, "ConnectingToServer", 2), 
        TestingServer("TestingServer", 3, "TestingServer", 3), 
        DoneTestingServer("DoneTestingServer", 4, "DoneTestingServer", 4), 
        Finished("Finished", 5, "Finished", 5), 
        Cancelling("Cancelling", 6, "Cancelling", 6), 
        Cancelled("Cancelled", 7, "Cancelled", 7), 
        Failed("Failed", 8, "Failed", 8);
        
        private static final IngestTestState[] $VALUES;
        private static final String __OBFID;
        private static final IngestTestState[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001814";
            ENUM$VALUES = new IngestTestState[] { IngestTestState.Uninitalized, IngestTestState.Starting, IngestTestState.ConnectingToServer, IngestTestState.TestingServer, IngestTestState.DoneTestingServer, IngestTestState.Finished, IngestTestState.Cancelling, IngestTestState.Cancelled, IngestTestState.Failed };
            $VALUES = new IngestTestState[] { IngestTestState.Uninitalized, IngestTestState.Starting, IngestTestState.ConnectingToServer, IngestTestState.TestingServer, IngestTestState.DoneTestingServer, IngestTestState.Finished, IngestTestState.Cancelling, IngestTestState.Cancelled, IngestTestState.Failed };
        }
        
        private IngestTestState(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    static final class SwitchStatType
    {
        static final int[] field_176003_a;
        static final int[] field_176002_b;
        private static final String __OBFID;
        private static final String[] llIIIlllIlIIlIl;
        private static String[] llIIIlllIlIIllI;
        
        static {
            lIIlIIlIllIIllII();
            lIIlIIlIllIIlIll();
            __OBFID = SwitchStatType.llIIIlllIlIIlIl[0];
            field_176002_b = new int[IngestTestState.values().length];
            try {
                SwitchStatType.field_176002_b[IngestTestState.Starting.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchStatType.field_176002_b[IngestTestState.DoneTestingServer.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchStatType.field_176002_b[IngestTestState.ConnectingToServer.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchStatType.field_176002_b[IngestTestState.TestingServer.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchStatType.field_176002_b[IngestTestState.Cancelling.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchStatType.field_176002_b[IngestTestState.Uninitalized.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchStatType.field_176002_b[IngestTestState.Finished.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchStatType.field_176002_b[IngestTestState.Cancelled.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchStatType.field_176002_b[IngestTestState.Failed.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            field_176003_a = new int[StatType.values().length];
            try {
                SwitchStatType.field_176003_a[StatType.TTV_ST_RTMPSTATE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                SwitchStatType.field_176003_a[StatType.TTV_ST_RTMPDATASENT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
        }
        
        private static void lIIlIIlIllIIlIll() {
            (llIIIlllIlIIlIl = new String[1])[0] = lIIlIIlIllIIlIlI(SwitchStatType.llIIIlllIlIIllI[0], SwitchStatType.llIIIlllIlIIllI[1]);
            SwitchStatType.llIIIlllIlIIllI = null;
        }
        
        private static void lIIlIIlIllIIllII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchStatType.llIIIlllIlIIllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIlIIlIllIIlIlI(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                final Cipher instance = Cipher.getInstance("Blowfish");
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
