package net.minecraft.event;

import java.util.*;
import com.google.common.collect.*;

public class ClickEvent
{
    private final Action action;
    private final String value;
    private static final String __OBFID;
    
    public ClickEvent(final Action action, final String value) {
        this.action = action;
        this.value = value;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ClickEvent clickEvent = (ClickEvent)o;
        if (this.action != clickEvent.action) {
            return false;
        }
        if (this.value != null) {
            if (!this.value.equals(clickEvent.value)) {
                return false;
            }
        }
        else if (clickEvent.value != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
    }
    
    @Override
    public int hashCode() {
        return 31 * this.action.hashCode() + ((this.value != null) ? this.value.hashCode() : 0);
    }
    
    static {
        __OBFID = "CL_00001260";
    }
    
    public enum Action
    {
        OPEN_URL("OPEN_URL", 0, "OPEN_URL", 0, "open_url", true), 
        OPEN_FILE("OPEN_FILE", 1, "OPEN_FILE", 1, "open_file", false), 
        RUN_COMMAND("RUN_COMMAND", 2, "RUN_COMMAND", 2, "run_command", true), 
        TWITCH_USER_INFO("TWITCH_USER_INFO", 3, "TWITCH_USER_INFO", 3, "twitch_user_info", false), 
        SUGGEST_COMMAND("SUGGEST_COMMAND", 4, "SUGGEST_COMMAND", 4, "suggest_command", true), 
        CHANGE_PAGE("CHANGE_PAGE", 5, "CHANGE_PAGE", 5, "change_page", true);
        
        private static final Map nameMapping;
        private final boolean allowedInChat;
        private final String canonicalName;
        private static final Action[] $VALUES;
        private static final String __OBFID;
        private static final Action[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001261";
            ENUM$VALUES = new Action[] { Action.OPEN_URL, Action.OPEN_FILE, Action.RUN_COMMAND, Action.TWITCH_USER_INFO, Action.SUGGEST_COMMAND, Action.CHANGE_PAGE };
            nameMapping = Maps.newHashMap();
            $VALUES = new Action[] { Action.OPEN_URL, Action.OPEN_FILE, Action.RUN_COMMAND, Action.TWITCH_USER_INFO, Action.SUGGEST_COMMAND, Action.CHANGE_PAGE };
            final Action[] values = values();
            while (0 < values.length) {
                final Action action = values[0];
                Action.nameMapping.put(action.getCanonicalName(), action);
                int n = 0;
                ++n;
            }
        }
        
        private Action(final String s, final int n, final String s2, final int n2, final String canonicalName, final boolean allowedInChat) {
            this.canonicalName = canonicalName;
            this.allowedInChat = allowedInChat;
        }
        
        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }
        
        public String getCanonicalName() {
            return this.canonicalName;
        }
        
        public static Action getValueByCanonicalName(final String s) {
            return Action.nameMapping.get(s);
        }
    }
}
