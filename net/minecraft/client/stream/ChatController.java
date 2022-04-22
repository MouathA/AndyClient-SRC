package net.minecraft.client.stream;

import org.apache.logging.log4j.*;
import tv.twitch.*;
import com.google.common.collect.*;
import tv.twitch.chat.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ChatController
{
    private static final Logger LOGGER;
    protected ChatListener field_153003_a;
    protected String field_153004_b;
    protected String field_153006_d;
    protected String field_153007_e;
    protected Core field_175992_e;
    protected Chat field_153008_f;
    protected ChatState field_153011_i;
    protected AuthToken field_153012_j;
    protected HashMap field_175998_i;
    protected int field_153015_m;
    protected EnumEmoticonMode field_175997_k;
    protected EnumEmoticonMode field_175995_l;
    protected ChatEmoticonData field_175996_m;
    protected int field_175993_n;
    protected int field_175994_o;
    protected IChatAPIListener field_175999_p;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001819";
        LOGGER = LogManager.getLogger();
    }
    
    public void func_152990_a(final ChatListener field_153003_a) {
        this.field_153003_a = field_153003_a;
    }
    
    public void func_152994_a(final AuthToken field_153012_j) {
        this.field_153012_j = field_153012_j;
    }
    
    public void func_152984_a(final String field_153006_d) {
        this.field_153006_d = field_153006_d;
    }
    
    public void func_152998_c(final String field_153004_b) {
        this.field_153004_b = field_153004_b;
    }
    
    public ChatState func_153000_j() {
        return this.field_153011_i;
    }
    
    public boolean func_175990_d(final String s) {
        return this.field_175998_i.containsKey(s) && this.field_175998_i.get(s).func_176040_a() == EnumChannelState.Connected;
    }
    
    public EnumChannelState func_175989_e(final String s) {
        if (!this.field_175998_i.containsKey(s)) {
            return EnumChannelState.Disconnected;
        }
        return this.field_175998_i.get(s).func_176040_a();
    }
    
    public ChatController() {
        this.field_153003_a = null;
        this.field_153004_b = "";
        this.field_153006_d = "";
        this.field_153007_e = "";
        this.field_175992_e = null;
        this.field_153008_f = null;
        this.field_153011_i = ChatState.Uninitialized;
        this.field_153012_j = new AuthToken();
        this.field_175998_i = new HashMap();
        this.field_153015_m = 128;
        this.field_175997_k = EnumEmoticonMode.None;
        this.field_175995_l = EnumEmoticonMode.None;
        this.field_175996_m = null;
        this.field_175993_n = 500;
        this.field_175994_o = 2000;
        this.field_175999_p = new IChatAPIListener() {
            private static final String __OBFID;
            final ChatController this$0;
            
            @Override
            public void chatInitializationCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_153008_f.setMessageFlushInterval(this.this$0.field_175993_n);
                    this.this$0.field_153008_f.setUserChangeEventInterval(this.this$0.field_175994_o);
                    this.this$0.func_153001_r();
                    this.this$0.func_175985_a(ChatState.Initialized);
                }
                else {
                    this.this$0.func_175985_a(ChatState.Uninitialized);
                }
                if (this.this$0.field_153003_a != null) {
                    this.this$0.field_153003_a.func_176023_d(errorCode);
                }
            }
            
            @Override
            public void chatShutdownCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    final ErrorCode shutdown = this.this$0.field_175992_e.shutdown();
                    if (ErrorCode.failed(shutdown)) {
                        this.this$0.func_152995_h(String.format("Error shutting down the Twitch sdk: %s", ErrorCode.getString(shutdown)));
                    }
                    this.this$0.func_175985_a(ChatState.Uninitialized);
                }
                else {
                    this.this$0.func_175985_a(ChatState.Initialized);
                    this.this$0.func_152995_h(String.format("Error shutting down Twith chat: %s", errorCode));
                }
                if (this.this$0.field_153003_a != null) {
                    this.this$0.field_153003_a.func_176022_e(errorCode);
                }
            }
            
            @Override
            public void chatEmoticonDataDownloadCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.func_152988_s();
                }
            }
            
            static {
                __OBFID = "CL_00002373";
            }
        };
        this.field_175992_e = Core.getInstance();
        if (this.field_175992_e == null) {
            this.field_175992_e = new Core(new StandardCoreAPI());
        }
        this.field_153008_f = new Chat(new StandardChatAPI());
    }
    
    public boolean func_175984_n() {
        if (this.field_153011_i != ChatState.Uninitialized) {
            return false;
        }
        this.func_175985_a(ChatState.Initializing);
        final ErrorCode initialize = this.field_175992_e.initialize(this.field_153006_d, null);
        if (ErrorCode.failed(initialize)) {
            this.func_175985_a(ChatState.Uninitialized);
            this.func_152995_h(String.format("Error initializing Twitch sdk: %s", ErrorCode.getString(initialize)));
            return false;
        }
        this.field_175995_l = this.field_175997_k;
        final HashSet<ChatTokenizationOption> set = new HashSet<ChatTokenizationOption>();
        switch (SwitchEnumEmoticonMode.field_175975_c[this.field_175997_k.ordinal()]) {
            case 1: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
                break;
            }
            case 2: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
                break;
            }
            case 3: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
                break;
            }
        }
        final ErrorCode initialize2 = this.field_153008_f.initialize(set, this.field_175999_p);
        if (ErrorCode.failed(initialize2)) {
            this.field_175992_e.shutdown();
            this.func_175985_a(ChatState.Uninitialized);
            this.func_152995_h(String.format("Error initializing Twitch chat: %s", ErrorCode.getString(initialize2)));
            return false;
        }
        this.func_175985_a(ChatState.Initialized);
        return true;
    }
    
    public boolean func_152986_d(final String s) {
        return this.func_175987_a(s, false);
    }
    
    protected boolean func_175987_a(final String s, final boolean b) {
        if (this.field_153011_i != ChatState.Initialized) {
            return false;
        }
        if (this.field_175998_i.containsKey(s)) {
            this.func_152995_h("Already in channel: " + s);
            return false;
        }
        if (s != null && !s.equals("")) {
            final ChatChannelListener chatChannelListener = new ChatChannelListener(s);
            this.field_175998_i.put(s, chatChannelListener);
            final boolean func_176038_a = chatChannelListener.func_176038_a(b);
            if (!func_176038_a) {
                this.field_175998_i.remove(s);
            }
            return func_176038_a;
        }
        return false;
    }
    
    public boolean func_175991_l(final String s) {
        if (this.field_153011_i != ChatState.Initialized) {
            return false;
        }
        if (!this.field_175998_i.containsKey(s)) {
            this.func_152995_h("Not in channel: " + s);
            return false;
        }
        return this.field_175998_i.get(s).func_176034_g();
    }
    
    public boolean func_152993_m() {
        if (this.field_153011_i != ChatState.Initialized) {
            return false;
        }
        final ErrorCode shutdown = this.field_153008_f.shutdown();
        if (ErrorCode.failed(shutdown)) {
            this.func_152995_h(String.format("Error shutting down chat: %s", ErrorCode.getString(shutdown)));
            return false;
        }
        this.func_152996_t();
        this.func_175985_a(ChatState.ShuttingDown);
        return true;
    }
    
    public void func_175988_p() {
        if (this.func_153000_j() != ChatState.Uninitialized) {
            this.func_152993_m();
            if (this.func_153000_j() == ChatState.ShuttingDown) {
                while (this.func_153000_j() != ChatState.Uninitialized) {
                    Thread.sleep(200L);
                    this.func_152997_n();
                }
            }
        }
    }
    
    public void func_152997_n() {
        if (this.field_153011_i != ChatState.Uninitialized) {
            final ErrorCode flushEvents = this.field_153008_f.flushEvents();
            if (ErrorCode.failed(flushEvents)) {
                this.func_152995_h(String.format("Error flushing chat events: %s", ErrorCode.getString(flushEvents)));
            }
        }
    }
    
    public boolean func_175986_a(final String s, final String s2) {
        if (this.field_153011_i != ChatState.Initialized) {
            return false;
        }
        if (!this.field_175998_i.containsKey(s)) {
            this.func_152995_h("Not in channel: " + s);
            return false;
        }
        return this.field_175998_i.get(s).func_176037_b(s2);
    }
    
    protected void func_175985_a(final ChatState field_153011_i) {
        if (field_153011_i != this.field_153011_i) {
            this.field_153011_i = field_153011_i;
            if (this.field_153003_a != null) {
                this.field_153003_a.func_176017_a(field_153011_i);
            }
        }
    }
    
    protected void func_153001_r() {
        if (this.field_175995_l != EnumEmoticonMode.None && this.field_175996_m == null) {
            final ErrorCode downloadEmoticonData = this.field_153008_f.downloadEmoticonData();
            if (ErrorCode.failed(downloadEmoticonData)) {
                this.func_152995_h(String.format("Error trying to download emoticon data: %s", ErrorCode.getString(downloadEmoticonData)));
            }
        }
    }
    
    protected void func_152988_s() {
        if (this.field_175996_m == null) {
            this.field_175996_m = new ChatEmoticonData();
            final ErrorCode emoticonData = this.field_153008_f.getEmoticonData(this.field_175996_m);
            if (ErrorCode.succeeded(emoticonData)) {
                if (this.field_153003_a != null) {
                    this.field_153003_a.func_176021_d();
                }
            }
            else {
                this.func_152995_h("Error preparing emoticon data: " + ErrorCode.getString(emoticonData));
            }
        }
    }
    
    protected void func_152996_t() {
        if (this.field_175996_m != null) {
            final ErrorCode clearEmoticonData = this.field_153008_f.clearEmoticonData();
            if (ErrorCode.succeeded(clearEmoticonData)) {
                this.field_175996_m = null;
                if (this.field_153003_a != null) {
                    this.field_153003_a.func_176024_e();
                }
            }
            else {
                this.func_152995_h("Error clearing emoticon data: " + ErrorCode.getString(clearEmoticonData));
            }
        }
    }
    
    protected void func_152995_h(final String s) {
        ChatController.LOGGER.error(TwitchStream.field_152949_a, "[Chat controller] {}", s);
    }
    
    public class ChatChannelListener implements IChatChannelListener
    {
        protected String field_176048_a;
        protected boolean field_176046_b;
        protected EnumChannelState field_176047_c;
        protected List field_176044_d;
        protected LinkedList field_176045_e;
        protected LinkedList field_176042_f;
        protected ChatBadgeData field_176043_g;
        private static final String __OBFID;
        final ChatController this$0;
        
        public ChatChannelListener(final ChatController this$0, final String field_176048_a) {
            this.this$0 = this$0;
            this.field_176048_a = null;
            this.field_176046_b = false;
            this.field_176047_c = EnumChannelState.Created;
            this.field_176044_d = Lists.newArrayList();
            this.field_176045_e = new LinkedList();
            this.field_176042_f = new LinkedList();
            this.field_176043_g = null;
            this.field_176048_a = field_176048_a;
        }
        
        public EnumChannelState func_176040_a() {
            return this.field_176047_c;
        }
        
        public boolean func_176038_a(final boolean field_176046_b) {
            this.field_176046_b = field_176046_b;
            final ErrorCode ttv_EC_SUCCESS = ErrorCode.TTV_EC_SUCCESS;
            ErrorCode errorCode;
            if (field_176046_b) {
                errorCode = this.this$0.field_153008_f.connectAnonymous(this.field_176048_a, this);
            }
            else {
                errorCode = this.this$0.field_153008_f.connect(this.field_176048_a, this.this$0.field_153004_b, this.this$0.field_153012_j.data, this);
            }
            if (ErrorCode.failed(errorCode)) {
                this.this$0.func_152995_h(String.format("Error connecting: %s", ErrorCode.getString(errorCode)));
                this.func_176036_d(this.field_176048_a);
                return false;
            }
            this.func_176035_a(EnumChannelState.Connecting);
            this.func_176041_h();
            return true;
        }
        
        public boolean func_176034_g() {
            switch (SwitchEnumEmoticonMode.field_175976_a[this.field_176047_c.ordinal()]) {
                case 1:
                case 2: {
                    final ErrorCode disconnect = this.this$0.field_153008_f.disconnect(this.field_176048_a);
                    if (ErrorCode.failed(disconnect)) {
                        this.this$0.func_152995_h(String.format("Error disconnecting: %s", ErrorCode.getString(disconnect)));
                        return false;
                    }
                    this.func_176035_a(EnumChannelState.Disconnecting);
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        protected void func_176035_a(final EnumChannelState field_176047_c) {
            if (field_176047_c != this.field_176047_c) {
                this.field_176047_c = field_176047_c;
            }
        }
        
        public void func_176032_a(final String s) {
            if (this.this$0.field_175995_l == EnumEmoticonMode.None) {
                this.field_176045_e.clear();
                this.field_176042_f.clear();
            }
            else {
                if (this.field_176045_e.size() > 0) {
                    final ListIterator listIterator = this.field_176045_e.listIterator();
                    while (listIterator.hasNext()) {
                        if (listIterator.next().userName.equals(s)) {
                            listIterator.remove();
                        }
                    }
                }
                if (this.field_176042_f.size() > 0) {
                    final ListIterator listIterator2 = this.field_176042_f.listIterator();
                    while (listIterator2.hasNext()) {
                        if (listIterator2.next().displayName.equals(s)) {
                            listIterator2.remove();
                        }
                    }
                }
            }
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_176019_a(this.field_176048_a, s);
            }
        }
        
        public boolean func_176037_b(final String s) {
            if (this.field_176047_c != EnumChannelState.Connected) {
                return false;
            }
            final ErrorCode sendMessage = this.this$0.field_153008_f.sendMessage(this.field_176048_a, s);
            if (ErrorCode.failed(sendMessage)) {
                this.this$0.func_152995_h(String.format("Error sending chat message: %s", ErrorCode.getString(sendMessage)));
                return false;
            }
            return true;
        }
        
        protected void func_176041_h() {
            if (this.this$0.field_175995_l != EnumEmoticonMode.None && this.field_176043_g == null) {
                final ErrorCode downloadBadgeData = this.this$0.field_153008_f.downloadBadgeData(this.field_176048_a);
                if (ErrorCode.failed(downloadBadgeData)) {
                    this.this$0.func_152995_h(String.format("Error trying to download badge data: %s", ErrorCode.getString(downloadBadgeData)));
                }
            }
        }
        
        protected void func_176039_i() {
            if (this.field_176043_g == null) {
                this.field_176043_g = new ChatBadgeData();
                final ErrorCode badgeData = this.this$0.field_153008_f.getBadgeData(this.field_176048_a, this.field_176043_g);
                if (ErrorCode.succeeded(badgeData)) {
                    if (this.this$0.field_153003_a != null) {
                        this.this$0.field_153003_a.func_176016_c(this.field_176048_a);
                    }
                }
                else {
                    this.this$0.func_152995_h("Error preparing badge data: " + ErrorCode.getString(badgeData));
                }
            }
        }
        
        protected void func_176033_j() {
            if (this.field_176043_g != null) {
                final ErrorCode clearBadgeData = this.this$0.field_153008_f.clearBadgeData(this.field_176048_a);
                if (ErrorCode.succeeded(clearBadgeData)) {
                    this.field_176043_g = null;
                    if (this.this$0.field_153003_a != null) {
                        this.this$0.field_153003_a.func_176020_d(this.field_176048_a);
                    }
                }
                else {
                    this.this$0.func_152995_h("Error releasing badge data: " + ErrorCode.getString(clearBadgeData));
                }
            }
        }
        
        protected void func_176031_c(final String s) {
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_180606_a(s);
            }
        }
        
        protected void func_176036_d(final String s) {
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_180607_b(s);
            }
        }
        
        private void func_176030_k() {
            if (this.field_176047_c != EnumChannelState.Disconnected) {
                this.func_176035_a(EnumChannelState.Disconnected);
                this.func_176036_d(this.field_176048_a);
                this.func_176033_j();
            }
        }
        
        @Override
        public void chatStatusCallback(final String s, final ErrorCode errorCode) {
            if (!ErrorCode.succeeded(errorCode)) {
                this.this$0.field_175998_i.remove(s);
                this.func_176030_k();
            }
        }
        
        @Override
        public void chatChannelMembershipCallback(final String s, final ChatEvent chatEvent, final ChatChannelInfo chatChannelInfo) {
            switch (SwitchEnumEmoticonMode.field_175974_b[chatEvent.ordinal()]) {
                case 1: {
                    this.func_176035_a(EnumChannelState.Connected);
                    this.func_176031_c(s);
                    break;
                }
                case 2: {
                    this.func_176030_k();
                    break;
                }
            }
        }
        
        @Override
        public void chatChannelUserChangeCallback(final String s, final ChatUserInfo[] array, final ChatUserInfo[] array2, final ChatUserInfo[] array3) {
            int n = 0;
            while (0 < array2.length) {
                final int index = this.field_176044_d.indexOf(array2[0]);
                if (index >= 0) {
                    this.field_176044_d.remove(index);
                }
                ++n;
            }
            while (0 < array3.length) {
                final int index2 = this.field_176044_d.indexOf(array3[0]);
                if (index2 >= 0) {
                    this.field_176044_d.remove(index2);
                }
                this.field_176044_d.add(array3[0]);
                ++n;
            }
            while (0 < array.length) {
                this.field_176044_d.add(array[0]);
                ++n;
            }
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_176018_a(this.field_176048_a, array, array2, array3);
            }
        }
        
        @Override
        public void chatChannelRawMessageCallback(final String s, final ChatRawMessage[] array) {
            while (0 < array.length) {
                this.field_176045_e.addLast(array[0]);
                int n = 0;
                ++n;
            }
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_180605_a(this.field_176048_a, array);
            }
            while (this.field_176045_e.size() > this.this$0.field_153015_m) {
                this.field_176045_e.removeFirst();
            }
        }
        
        @Override
        public void chatChannelTokenizedMessageCallback(final String s, final ChatTokenizedMessage[] array) {
            while (0 < array.length) {
                this.field_176042_f.addLast(array[0]);
                int n = 0;
                ++n;
            }
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_176025_a(this.field_176048_a, array);
            }
            while (this.field_176042_f.size() > this.this$0.field_153015_m) {
                this.field_176042_f.removeFirst();
            }
        }
        
        @Override
        public void chatClearCallback(final String s, final String s2) {
            this.func_176032_a(s2);
        }
        
        @Override
        public void chatBadgeDataDownloadCallback(final String s, final ErrorCode errorCode) {
            if (ErrorCode.succeeded(errorCode)) {
                this.func_176039_i();
            }
        }
        
        static {
            __OBFID = "CL_00002370";
        }
    }
    
    public interface ChatListener
    {
        void func_176023_d(final ErrorCode p0);
        
        void func_176022_e(final ErrorCode p0);
        
        void func_176021_d();
        
        void func_176024_e();
        
        void func_176017_a(final ChatState p0);
        
        void func_176025_a(final String p0, final ChatTokenizedMessage[] p1);
        
        void func_180605_a(final String p0, final ChatRawMessage[] p1);
        
        void func_176018_a(final String p0, final ChatUserInfo[] p1, final ChatUserInfo[] p2, final ChatUserInfo[] p3);
        
        void func_180606_a(final String p0);
        
        void func_180607_b(final String p0);
        
        void func_176019_a(final String p0, final String p1);
        
        void func_176016_c(final String p0);
        
        void func_176020_d(final String p0);
    }
    
    public enum ChatState
    {
        Uninitialized("Uninitialized", 0, "Uninitialized", 0), 
        Initializing("Initializing", 1, "Initializing", 1), 
        Initialized("Initialized", 2, "Initialized", 2), 
        ShuttingDown("ShuttingDown", 3, "ShuttingDown", 3);
        
        private static final ChatState[] $VALUES;
        private static final String __OBFID;
        private static final ChatState[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001817";
            ENUM$VALUES = new ChatState[] { ChatState.Uninitialized, ChatState.Initializing, ChatState.Initialized, ChatState.ShuttingDown };
            $VALUES = new ChatState[] { ChatState.Uninitialized, ChatState.Initializing, ChatState.Initialized, ChatState.ShuttingDown };
        }
        
        private ChatState(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    public enum EnumChannelState
    {
        Created("Created", 0, "Created", 0), 
        Connecting("Connecting", 1, "Connecting", 1), 
        Connected("Connected", 2, "Connected", 2), 
        Disconnecting("Disconnecting", 3, "Disconnecting", 3), 
        Disconnected("Disconnected", 4, "Disconnected", 4);
        
        private static final EnumChannelState[] $VALUES;
        private static final String __OBFID;
        private static final EnumChannelState[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002371";
            ENUM$VALUES = new EnumChannelState[] { EnumChannelState.Created, EnumChannelState.Connecting, EnumChannelState.Connected, EnumChannelState.Disconnecting, EnumChannelState.Disconnected };
            $VALUES = new EnumChannelState[] { EnumChannelState.Created, EnumChannelState.Connecting, EnumChannelState.Connected, EnumChannelState.Disconnecting, EnumChannelState.Disconnected };
        }
        
        private EnumChannelState(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    public enum EnumEmoticonMode
    {
        None("None", 0, "None", 0), 
        Url("Url", 1, "Url", 1), 
        TextureAtlas("TextureAtlas", 2, "TextureAtlas", 2);
        
        private static final EnumEmoticonMode[] $VALUES;
        private static final String __OBFID;
        private static final EnumEmoticonMode[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002369";
            ENUM$VALUES = new EnumEmoticonMode[] { EnumEmoticonMode.None, EnumEmoticonMode.Url, EnumEmoticonMode.TextureAtlas };
            $VALUES = new EnumEmoticonMode[] { EnumEmoticonMode.None, EnumEmoticonMode.Url, EnumEmoticonMode.TextureAtlas };
        }
        
        private EnumEmoticonMode(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    static final class SwitchEnumEmoticonMode
    {
        static final int[] field_175976_a;
        static final int[] field_175974_b;
        static final int[] field_175975_c;
        private static final String __OBFID;
        private static final String[] lIlIIllllIllllll;
        private static String[] lIlIIlllllIIIIII;
        
        static {
            llllIlllIIIIIlIl();
            llllIlllIIIIIlII();
            __OBFID = SwitchEnumEmoticonMode.lIlIIllllIllllll[0];
            field_175975_c = new int[EnumEmoticonMode.values().length];
            try {
                SwitchEnumEmoticonMode.field_175975_c[EnumEmoticonMode.None.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumEmoticonMode.field_175975_c[EnumEmoticonMode.Url.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumEmoticonMode.field_175975_c[EnumEmoticonMode.TextureAtlas.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            field_175974_b = new int[ChatEvent.values().length];
            try {
                SwitchEnumEmoticonMode.field_175974_b[ChatEvent.TTV_CHAT_JOINED_CHANNEL.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumEmoticonMode.field_175974_b[ChatEvent.TTV_CHAT_LEFT_CHANNEL.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            field_175976_a = new int[EnumChannelState.values().length];
            try {
                SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Connected.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Connecting.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Created.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Disconnected.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Disconnecting.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
        }
        
        private static void llllIlllIIIIIlII() {
            (lIlIIllllIllllll = new String[1])[0] = llllIlllIIIIIIll(SwitchEnumEmoticonMode.lIlIIlllllIIIIII[0], SwitchEnumEmoticonMode.lIlIIlllllIIIIII[1]);
            SwitchEnumEmoticonMode.lIlIIlllllIIIIII = null;
        }
        
        private static void llllIlllIIIIIlIl() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumEmoticonMode.lIlIIlllllIIIIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llllIlllIIIIIIll(final String s, final String s2) {
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
