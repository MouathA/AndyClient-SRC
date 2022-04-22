package tv.twitch.chat;

import java.util.*;

public class ChatRawMessage
{
    public String userName;
    public String message;
    public HashSet modes;
    public HashSet subscriptions;
    public int nameColorARGB;
    public boolean action;
    
    public ChatRawMessage() {
        this.userName = null;
        this.message = null;
        this.modes = new HashSet();
        this.subscriptions = new HashSet();
        this.nameColorARGB = 0;
        this.action = false;
    }
}
