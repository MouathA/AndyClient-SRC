package Mood.discord;

import net.arikia.dev.drpc.callbacks.*;
import net.arikia.dev.drpc.*;

public class DiscordRP
{
    public boolean running;
    private long created;
    
    public DiscordRP() {
        this.running = true;
        this.created = 0L;
    }
    
    public void start() {
        this.created = System.currentTimeMillis();
        DiscordRPC.discordInitialize("952970509715398716", new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            final DiscordRP this$0;
            
            @Override
            public void apply(final DiscordUser discordUser) {
                System.out.println("Websome " + discordUser.username + "#" + discordUser.discriminator + ".");
                this.this$0.update("Loading...", "");
            }
        }).build(), true);
        new Thread("Discord RPC Callback") {
            final DiscordRP this$0;
            
            @Override
            public void run() {
                while (this.this$0.running) {}
            }
        }.start();
    }
    
    public void shutdown() {
        this.running = false;
    }
    
    public void update(final String details, final String s) {
        final DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(s);
        builder.setBigImage("large", "Utility Mod");
        builder.setSmallImage("small", "by: iTzMatthew1337");
        builder.setDetails(details);
        builder.setStartTimestamps(this.created);
        DiscordRPC.discordUpdatePresence(builder.build());
    }
}
