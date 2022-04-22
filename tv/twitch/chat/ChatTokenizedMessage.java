package tv.twitch.chat;

import java.util.*;

public class ChatTokenizedMessage
{
    public String displayName;
    public HashSet modes;
    public HashSet subscriptions;
    public int nameColorARGB;
    public ChatMessageToken[] tokenList;
    public boolean action;
    
    public ChatTokenizedMessage() {
        this.modes = new HashSet();
        this.subscriptions = new HashSet();
    }
}
