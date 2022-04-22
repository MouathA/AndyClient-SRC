package com.mojang.realmsclient.gui;

import com.google.common.collect.*;
import java.util.concurrent.*;
import com.mojang.realmsclient.dto.*;
import net.minecraft.realms.*;
import java.util.*;
import org.lwjgl.opengl.*;
import org.apache.logging.log4j.*;
import com.mojang.realmsclient.client.*;

public class RealmsDataFetcher
{
    private static final Logger LOGGER;
    private final ScheduledExecutorService scheduler;
    private static final int SERVER_UPDATE_INTERVAL = 60;
    private static final int PENDING_INVITES_INTERVAL = 10;
    private static final int TRIAL_UPDATE_INTERVAL = 60;
    private boolean stopped;
    private ServerListUpdateTask serverListUpdateTask;
    private PendingInviteUpdateTask pendingInviteUpdateTask;
    private TrialAvailabilityTask trialAvailabilityTask;
    private Set removedServers;
    private List servers;
    private int pendingInvitesCount;
    private boolean trialAvailable;
    private ScheduledFuture serverListScheduledFuture;
    private ScheduledFuture pendingInviteScheduledFuture;
    private ScheduledFuture trialAvailableScheduledFuture;
    private Map fetchStatus;
    
    public RealmsDataFetcher() {
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.stopped = true;
        this.serverListUpdateTask = new ServerListUpdateTask(null);
        this.pendingInviteUpdateTask = new PendingInviteUpdateTask(null);
        this.trialAvailabilityTask = new TrialAvailabilityTask(null);
        this.removedServers = Sets.newHashSet();
        this.servers = Lists.newArrayList();
        this.trialAvailable = false;
        this.fetchStatus = new ConcurrentHashMap(Task.values().length);
        this.scheduleTasks();
    }
    
    public synchronized void init() {
        if (this.stopped) {
            this.stopped = false;
            this.cancelTasks();
            this.scheduleTasks();
        }
    }
    
    public synchronized boolean isFetchedSinceLastTry(final Task task) {
        final Boolean b = this.fetchStatus.get(task.toString());
        return b != null && b;
    }
    
    public synchronized void markClean() {
        final Iterator<String> iterator = this.fetchStatus.keySet().iterator();
        while (iterator.hasNext()) {
            this.fetchStatus.put(iterator.next(), false);
        }
    }
    
    public synchronized void forceUpdate() {
        this.stop();
        this.init();
    }
    
    public synchronized List getServers() {
        return Lists.newArrayList(this.servers);
    }
    
    public int getPendingInvitesCount() {
        return this.pendingInvitesCount;
    }
    
    public boolean isTrialAvailable() {
        return this.trialAvailable;
    }
    
    public synchronized void stop() {
        this.stopped = true;
        this.cancelTasks();
    }
    
    private void scheduleTasks() {
        final Task[] values = Task.values();
        while (0 < values.length) {
            this.fetchStatus.put(values[0].toString(), false);
            int n = 0;
            ++n;
        }
        this.serverListScheduledFuture = this.scheduler.scheduleAtFixedRate(this.serverListUpdateTask, 0L, 60L, TimeUnit.SECONDS);
        this.pendingInviteScheduledFuture = this.scheduler.scheduleAtFixedRate(this.pendingInviteUpdateTask, 0L, 10L, TimeUnit.SECONDS);
        this.trialAvailableScheduledFuture = this.scheduler.scheduleAtFixedRate(this.trialAvailabilityTask, 0L, 60L, TimeUnit.SECONDS);
    }
    
    private void cancelTasks() {
        this.serverListScheduledFuture.cancel(false);
        this.pendingInviteScheduledFuture.cancel(false);
        this.trialAvailableScheduledFuture.cancel(false);
    }
    
    private synchronized void setServers(final List servers) {
        final Iterator<RealmsServer> iterator = this.removedServers.iterator();
        while (iterator.hasNext()) {
            if (servers.remove(iterator.next())) {
                int n = 0;
                ++n;
            }
        }
        this.removedServers.clear();
        this.servers = servers;
    }
    
    private synchronized void setTrialAvailabile(final boolean trialAvailable) {
        this.trialAvailable = trialAvailable;
    }
    
    public synchronized void removeItem(final RealmsServer realmsServer) {
        this.servers.remove(realmsServer);
        this.removedServers.add(realmsServer);
    }
    
    private void sort(final List list) {
        Collections.sort((List<Object>)list, new RealmsServer.McoServerComparator(Realms.getName()));
    }
    
    private boolean isActive() {
        return !this.stopped && Display.isActive();
    }
    
    static boolean access$300(final RealmsDataFetcher realmsDataFetcher) {
        return realmsDataFetcher.isActive();
    }
    
    static void access$400(final RealmsDataFetcher realmsDataFetcher, final List list) {
        realmsDataFetcher.sort(list);
    }
    
    static void access$500(final RealmsDataFetcher realmsDataFetcher, final List servers) {
        realmsDataFetcher.setServers(servers);
    }
    
    static Map access$600(final RealmsDataFetcher realmsDataFetcher) {
        return realmsDataFetcher.fetchStatus;
    }
    
    static Logger access$700() {
        return RealmsDataFetcher.LOGGER;
    }
    
    static int access$802(final RealmsDataFetcher realmsDataFetcher, final int pendingInvitesCount) {
        return realmsDataFetcher.pendingInvitesCount = pendingInvitesCount;
    }
    
    static boolean access$902(final RealmsDataFetcher realmsDataFetcher, final boolean trialAvailable) {
        return realmsDataFetcher.trialAvailable = trialAvailable;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public enum Task
    {
        SERVER_LIST("SERVER_LIST", 0), 
        PENDING_INVITE("PENDING_INVITE", 1), 
        TRIAL_AVAILABLE("TRIAL_AVAILABLE", 2);
        
        private static final Task[] $VALUES;
        
        private Task(final String s, final int n) {
        }
        
        static {
            $VALUES = new Task[] { Task.SERVER_LIST, Task.PENDING_INVITE, Task.TRIAL_AVAILABLE };
        }
    }
    
    private class TrialAvailabilityTask implements Runnable
    {
        final RealmsDataFetcher this$0;
        
        private TrialAvailabilityTask(final RealmsDataFetcher this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void run() {
            if (RealmsDataFetcher.access$300(this.this$0)) {
                this.getTrialAvailable();
            }
        }
        
        private void getTrialAvailable() {
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            if (realmsClient != null) {
                RealmsDataFetcher.access$902(this.this$0, realmsClient.trialAvailable());
                RealmsDataFetcher.access$600(this.this$0).put(Task.TRIAL_AVAILABLE.toString(), true);
            }
        }
        
        TrialAvailabilityTask(final RealmsDataFetcher realmsDataFetcher, final RealmsDataFetcher$1 object) {
            this(realmsDataFetcher);
        }
    }
    
    private class PendingInviteUpdateTask implements Runnable
    {
        final RealmsDataFetcher this$0;
        
        private PendingInviteUpdateTask(final RealmsDataFetcher this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void run() {
            if (RealmsDataFetcher.access$300(this.this$0)) {
                this.updatePendingInvites();
            }
        }
        
        private void updatePendingInvites() {
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            if (realmsClient != null) {
                RealmsDataFetcher.access$802(this.this$0, realmsClient.pendingInvitesCount());
                RealmsDataFetcher.access$600(this.this$0).put(Task.PENDING_INVITE.toString(), true);
            }
        }
        
        PendingInviteUpdateTask(final RealmsDataFetcher realmsDataFetcher, final RealmsDataFetcher$1 object) {
            this(realmsDataFetcher);
        }
    }
    
    private class ServerListUpdateTask implements Runnable
    {
        final RealmsDataFetcher this$0;
        
        private ServerListUpdateTask(final RealmsDataFetcher this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void run() {
            if (RealmsDataFetcher.access$300(this.this$0)) {
                this.updateServersList();
            }
        }
        
        private void updateServersList() {
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            if (realmsClient != null) {
                final List servers = realmsClient.listWorlds().servers;
                if (servers != null) {
                    RealmsDataFetcher.access$400(this.this$0, servers);
                    RealmsDataFetcher.access$500(this.this$0, servers);
                    RealmsDataFetcher.access$600(this.this$0).put(Task.SERVER_LIST.toString(), true);
                }
                else {
                    RealmsDataFetcher.access$700().warn("Realms server list was null or empty");
                }
            }
        }
        
        ServerListUpdateTask(final RealmsDataFetcher realmsDataFetcher, final RealmsDataFetcher$1 object) {
            this(realmsDataFetcher);
        }
    }
}
