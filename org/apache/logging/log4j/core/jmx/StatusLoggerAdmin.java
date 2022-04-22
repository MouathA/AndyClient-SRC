package org.apache.logging.log4j.core.jmx;

import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import java.util.concurrent.*;
import javax.management.*;
import org.apache.logging.log4j.status.*;
import java.util.*;

public class StatusLoggerAdmin extends NotificationBroadcasterSupport implements StatusListener, StatusLoggerAdminMBean
{
    private final AtomicLong sequenceNo;
    private final ObjectName objectName;
    private Level level;
    
    public StatusLoggerAdmin(final Executor executor) {
        super(executor, new MBeanNotificationInfo[] { createNotificationInfo() });
        this.sequenceNo = new AtomicLong();
        this.level = Level.WARN;
        this.objectName = new ObjectName("org.apache.logging.log4j2:type=StatusLogger");
        StatusLogger.getLogger().registerListener(this);
    }
    
    private static MBeanNotificationInfo createNotificationInfo() {
        return new MBeanNotificationInfo(new String[] { "com.apache.logging.log4j.core.jmx.statuslogger.data", "com.apache.logging.log4j.core.jmx.statuslogger.message" }, Notification.class.getName(), "StatusLogger has logged an event");
    }
    
    @Override
    public String[] getStatusDataHistory() {
        final List statusData = this.getStatusData();
        final String[] array = new String[statusData.size()];
        while (0 < array.length) {
            array[0] = statusData.get(0).getFormattedStatus();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    @Override
    public List getStatusData() {
        return StatusLogger.getLogger().getStatusData();
    }
    
    @Override
    public String getLevel() {
        return this.level.name();
    }
    
    @Override
    public Level getStatusLevel() {
        return this.level;
    }
    
    @Override
    public void setLevel(final String s) {
        this.level = Level.toLevel(s, Level.ERROR);
    }
    
    @Override
    public void log(final StatusData userData) {
        this.sendNotification(new Notification("com.apache.logging.log4j.core.jmx.statuslogger.message", this.getObjectName(), this.nextSeqNo(), this.now(), userData.getFormattedStatus()));
        final Notification notification = new Notification("com.apache.logging.log4j.core.jmx.statuslogger.data", this.getObjectName(), this.nextSeqNo(), this.now());
        notification.setUserData(userData);
        this.sendNotification(notification);
    }
    
    public ObjectName getObjectName() {
        return this.objectName;
    }
    
    private long nextSeqNo() {
        return this.sequenceNo.getAndIncrement();
    }
    
    private long now() {
        return System.currentTimeMillis();
    }
}
