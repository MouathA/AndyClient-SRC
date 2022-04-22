package tv.twitch.chat;

import java.util.*;

public class ChatUserInfo
{
    public String displayName;
    public HashSet modes;
    public HashSet subscriptions;
    public int nameColorARGB;
    public boolean ignore;
    
    public ChatUserInfo() {
        this.displayName = null;
        this.modes = new HashSet();
        this.subscriptions = new HashSet();
        this.nameColorARGB = 0;
        this.ignore = false;
    }
}
