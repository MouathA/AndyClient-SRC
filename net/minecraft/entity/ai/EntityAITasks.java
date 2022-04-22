package net.minecraft.entity.ai;

import net.minecraft.profiler.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.util.*;

public class EntityAITasks
{
    private static final Logger logger;
    private List taskEntries;
    private List executingTaskEntries;
    private final Profiler theProfiler;
    private int tickCount;
    private int tickRate;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001588";
        logger = LogManager.getLogger();
    }
    
    public EntityAITasks(final Profiler theProfiler) {
        this.taskEntries = Lists.newArrayList();
        this.executingTaskEntries = Lists.newArrayList();
        this.tickRate = 3;
        this.theProfiler = theProfiler;
    }
    
    public void addTask(final int n, final EntityAIBase entityAIBase) {
        this.taskEntries.add(new EntityAITaskEntry(n, entityAIBase));
    }
    
    public void removeTask(final EntityAIBase entityAIBase) {
        final Iterator<EntityAITaskEntry> iterator = (Iterator<EntityAITaskEntry>)this.taskEntries.iterator();
        while (iterator.hasNext()) {
            final EntityAITaskEntry entityAITaskEntry = iterator.next();
            final EntityAIBase action = entityAITaskEntry.action;
            if (action == entityAIBase) {
                if (this.executingTaskEntries.contains(entityAITaskEntry)) {
                    action.resetTask();
                    this.executingTaskEntries.remove(entityAITaskEntry);
                }
                iterator.remove();
            }
        }
    }
    
    public void onUpdateTasks() {
        this.theProfiler.startSection("goalSetup");
        if (this.tickCount++ % this.tickRate == 0) {
            for (final EntityAITaskEntry entityAITaskEntry : this.taskEntries) {
                if (this.executingTaskEntries.contains(entityAITaskEntry)) {
                    if (this != entityAITaskEntry && this.canContinue(entityAITaskEntry)) {
                        continue;
                    }
                    entityAITaskEntry.action.resetTask();
                    this.executingTaskEntries.remove(entityAITaskEntry);
                }
                if (this != entityAITaskEntry && entityAITaskEntry.action.shouldExecute()) {
                    entityAITaskEntry.action.startExecuting();
                    this.executingTaskEntries.add(entityAITaskEntry);
                }
            }
        }
        else {
            final Iterator<EntityAITaskEntry> iterator2 = (Iterator<EntityAITaskEntry>)this.executingTaskEntries.iterator();
            while (iterator2.hasNext()) {
                final EntityAITaskEntry entityAITaskEntry2 = iterator2.next();
                if (!this.canContinue(entityAITaskEntry2)) {
                    entityAITaskEntry2.action.resetTask();
                    iterator2.remove();
                }
            }
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("goalTick");
        final Iterator<EntityAITaskEntry> iterator3 = this.executingTaskEntries.iterator();
        while (iterator3.hasNext()) {
            iterator3.next().action.updateTask();
        }
        this.theProfiler.endSection();
    }
    
    private boolean canContinue(final EntityAITaskEntry entityAITaskEntry) {
        return entityAITaskEntry.action.continueExecuting();
    }
    
    class EntityAITaskEntry
    {
        public EntityAIBase action;
        public int priority;
        private static final String __OBFID;
        final EntityAITasks this$0;
        
        public EntityAITaskEntry(final EntityAITasks this$0, final int priority, final EntityAIBase action) {
            this.this$0 = this$0;
            this.priority = priority;
            this.action = action;
        }
        
        static {
            __OBFID = "CL_00001589";
        }
    }
}
