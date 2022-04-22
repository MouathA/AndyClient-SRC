package org.apache.logging.log4j.message;

class BasicThreadInformation implements ThreadInformation
{
    private static final int HASH_SHIFT = 32;
    private static final int HASH_MULTIPLIER = 31;
    private final long id;
    private final String name;
    private final String longName;
    private final Thread.State state;
    private final int priority;
    private final boolean isAlive;
    private final boolean isDaemon;
    private final String threadGroupName;
    
    public BasicThreadInformation(final Thread thread) {
        this.id = thread.getId();
        this.name = thread.getName();
        this.longName = thread.toString();
        this.state = thread.getState();
        this.priority = thread.getPriority();
        this.isAlive = thread.isAlive();
        this.isDaemon = thread.isDaemon();
        final ThreadGroup threadGroup = thread.getThreadGroup();
        this.threadGroupName = ((threadGroup == null) ? null : threadGroup.getName());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BasicThreadInformation basicThreadInformation = (BasicThreadInformation)o;
        if (this.id != basicThreadInformation.id) {
            return false;
        }
        if (this.name != null) {
            if (this.name.equals(basicThreadInformation.name)) {
                return true;
            }
        }
        else if (basicThreadInformation.name == null) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * (int)(this.id ^ this.id >>> 32) + ((this.name != null) ? this.name.hashCode() : 0);
    }
    
    @Override
    public void printThreadInfo(final StringBuilder sb) {
        sb.append("\"").append(this.name).append("\" ");
        if (this.isDaemon) {
            sb.append("daemon ");
        }
        sb.append("prio=").append(this.priority).append(" tid=").append(this.id).append(" ");
        if (this.threadGroupName != null) {
            sb.append("group=\"").append(this.threadGroupName).append("\"");
        }
        sb.append("\n");
        sb.append("\tThread state: ").append(this.state.name()).append("\n");
    }
    
    @Override
    public void printStack(final StringBuilder sb, final StackTraceElement[] array) {
        while (0 < array.length) {
            sb.append("\tat ").append(array[0]).append("\n");
            int n = 0;
            ++n;
        }
    }
}
