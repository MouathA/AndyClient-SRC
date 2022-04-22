package org.apache.logging.log4j.message;

import java.lang.management.*;

class ExtendedThreadInformation implements ThreadInformation
{
    private final ThreadInfo info;
    
    public ExtendedThreadInformation(final ThreadInfo info) {
        this.info = info;
    }
    
    @Override
    public void printThreadInfo(final StringBuilder sb) {
        sb.append("\"").append(this.info.getThreadName()).append("\"");
        sb.append(" Id=").append(this.info.getThreadId()).append(" ");
        this.formatState(sb, this.info);
        if (this.info.isSuspended()) {
            sb.append(" (suspended)");
        }
        if (this.info.isInNative()) {
            sb.append(" (in native)");
        }
        sb.append('\n');
    }
    
    @Override
    public void printStack(final StringBuilder p0, final StackTraceElement[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore          4
        //     3: aload           4
        //     5: arraylength    
        //     6: istore          5
        //     8: iconst_0       
        //     9: iload           5
        //    11: if_icmpge       255
        //    14: aload           4
        //    16: iconst_0       
        //    17: aaload         
        //    18: astore          7
        //    20: aload_1        
        //    21: ldc             "\tat "
        //    23: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    26: aload           7
        //    28: invokevirtual   java/lang/StackTraceElement.toString:()Ljava/lang/String;
        //    31: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    34: pop            
        //    35: aload_1        
        //    36: bipush          10
        //    38: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //    41: pop            
        //    42: iconst_0       
        //    43: ifne            184
        //    46: aload_0        
        //    47: getfield        org/apache/logging/log4j/message/ExtendedThreadInformation.info:Ljava/lang/management/ThreadInfo;
        //    50: invokevirtual   java/lang/management/ThreadInfo.getLockInfo:()Ljava/lang/management/LockInfo;
        //    53: ifnull          184
        //    56: aload_0        
        //    57: getfield        org/apache/logging/log4j/message/ExtendedThreadInformation.info:Ljava/lang/management/ThreadInfo;
        //    60: invokevirtual   java/lang/management/ThreadInfo.getThreadState:()Ljava/lang/Thread$State;
        //    63: astore          8
        //    65: getstatic       org/apache/logging/log4j/message/ExtendedThreadInformation$1.$SwitchMap$java$lang$Thread$State:[I
        //    68: aload           8
        //    70: invokevirtual   java/lang/Thread$State.ordinal:()I
        //    73: iaload         
        //    74: tableswitch {
        //                2: 100
        //                3: 129
        //                4: 158
        //          default: 184
        //        }
        //   100: aload_1        
        //   101: ldc             "\t-  blocked on "
        //   103: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   106: pop            
        //   107: aload_0        
        //   108: aload_1        
        //   109: aload_0        
        //   110: getfield        org/apache/logging/log4j/message/ExtendedThreadInformation.info:Ljava/lang/management/ThreadInfo;
        //   113: invokevirtual   java/lang/management/ThreadInfo.getLockInfo:()Ljava/lang/management/LockInfo;
        //   116: invokespecial   org/apache/logging/log4j/message/ExtendedThreadInformation.formatLock:(Ljava/lang/StringBuilder;Ljava/lang/management/LockInfo;)V
        //   119: aload_1        
        //   120: bipush          10
        //   122: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   125: pop            
        //   126: goto            184
        //   129: aload_1        
        //   130: ldc             "\t-  waiting on "
        //   132: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   135: pop            
        //   136: aload_0        
        //   137: aload_1        
        //   138: aload_0        
        //   139: getfield        org/apache/logging/log4j/message/ExtendedThreadInformation.info:Ljava/lang/management/ThreadInfo;
        //   142: invokevirtual   java/lang/management/ThreadInfo.getLockInfo:()Ljava/lang/management/LockInfo;
        //   145: invokespecial   org/apache/logging/log4j/message/ExtendedThreadInformation.formatLock:(Ljava/lang/StringBuilder;Ljava/lang/management/LockInfo;)V
        //   148: aload_1        
        //   149: bipush          10
        //   151: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   154: pop            
        //   155: goto            184
        //   158: aload_1        
        //   159: ldc             "\t-  waiting on "
        //   161: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   164: pop            
        //   165: aload_0        
        //   166: aload_1        
        //   167: aload_0        
        //   168: getfield        org/apache/logging/log4j/message/ExtendedThreadInformation.info:Ljava/lang/management/ThreadInfo;
        //   171: invokevirtual   java/lang/management/ThreadInfo.getLockInfo:()Ljava/lang/management/LockInfo;
        //   174: invokespecial   org/apache/logging/log4j/message/ExtendedThreadInformation.formatLock:(Ljava/lang/StringBuilder;Ljava/lang/management/LockInfo;)V
        //   177: aload_1        
        //   178: bipush          10
        //   180: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   183: pop            
        //   184: aload_0        
        //   185: getfield        org/apache/logging/log4j/message/ExtendedThreadInformation.info:Ljava/lang/management/ThreadInfo;
        //   188: invokevirtual   java/lang/management/ThreadInfo.getLockedMonitors:()[Ljava/lang/management/MonitorInfo;
        //   191: astore          8
        //   193: aload           8
        //   195: arraylength    
        //   196: istore          9
        //   198: iconst_0       
        //   199: iload           9
        //   201: if_icmpge       246
        //   204: aload           8
        //   206: iconst_0       
        //   207: aaload         
        //   208: astore          11
        //   210: aload           11
        //   212: invokevirtual   java/lang/management/MonitorInfo.getLockedStackDepth:()I
        //   215: iconst_0       
        //   216: if_icmpne       240
        //   219: aload_1        
        //   220: ldc             "\t-  locked "
        //   222: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   225: pop            
        //   226: aload_0        
        //   227: aload_1        
        //   228: aload           11
        //   230: invokespecial   org/apache/logging/log4j/message/ExtendedThreadInformation.formatLock:(Ljava/lang/StringBuilder;Ljava/lang/management/LockInfo;)V
        //   233: aload_1        
        //   234: bipush          10
        //   236: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   239: pop            
        //   240: iinc            10, 1
        //   243: goto            198
        //   246: iinc            3, 1
        //   249: iinc            6, 1
        //   252: goto            8
        //   255: aload_0        
        //   256: getfield        org/apache/logging/log4j/message/ExtendedThreadInformation.info:Ljava/lang/management/ThreadInfo;
        //   259: invokevirtual   java/lang/management/ThreadInfo.getLockedSynchronizers:()[Ljava/lang/management/LockInfo;
        //   262: astore          4
        //   264: aload           4
        //   266: arraylength    
        //   267: ifle            335
        //   270: aload_1        
        //   271: ldc             "\n\tNumber of locked synchronizers = "
        //   273: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   276: aload           4
        //   278: arraylength    
        //   279: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   282: bipush          10
        //   284: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   287: pop            
        //   288: aload           4
        //   290: astore          5
        //   292: aload           5
        //   294: arraylength    
        //   295: istore          6
        //   297: iconst_0       
        //   298: iconst_0       
        //   299: if_icmpge       335
        //   302: aload           5
        //   304: iconst_0       
        //   305: aaload         
        //   306: astore          8
        //   308: aload_1        
        //   309: ldc             "\t- "
        //   311: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   314: pop            
        //   315: aload_0        
        //   316: aload_1        
        //   317: aload           8
        //   319: invokespecial   org/apache/logging/log4j/message/ExtendedThreadInformation.formatLock:(Ljava/lang/StringBuilder;Ljava/lang/management/LockInfo;)V
        //   322: aload_1        
        //   323: bipush          10
        //   325: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   328: pop            
        //   329: iinc            7, 1
        //   332: goto            297
        //   335: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void formatLock(final StringBuilder sb, final LockInfo lockInfo) {
        sb.append("<").append(lockInfo.getIdentityHashCode()).append("> (a ");
        sb.append(lockInfo.getClassName()).append(")");
    }
    
    private void formatState(final StringBuilder sb, final ThreadInfo threadInfo) {
        final Thread.State threadState = threadInfo.getThreadState();
        sb.append(threadState);
        switch (threadState) {
            case BLOCKED: {
                sb.append(" (on object monitor owned by \"");
                sb.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId()).append(")");
                break;
            }
            case WAITING: {
                final StackTraceElement stackTraceElement = threadInfo.getStackTrace()[0];
                final String className = stackTraceElement.getClassName();
                final String methodName = stackTraceElement.getMethodName();
                if (className.equals("java.lang.Object") && methodName.equals("wait")) {
                    sb.append(" (on object monitor");
                    if (threadInfo.getLockOwnerName() != null) {
                        sb.append(" owned by \"");
                        sb.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
                    }
                    sb.append(")");
                    break;
                }
                if (className.equals("java.lang.Thread") && methodName.equals("join")) {
                    sb.append(" (on completion of thread ").append(threadInfo.getLockOwnerId()).append(")");
                    break;
                }
                sb.append(" (parking for lock");
                if (threadInfo.getLockOwnerName() != null) {
                    sb.append(" owned by \"");
                    sb.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
                }
                sb.append(")");
                break;
            }
            case TIMED_WAITING: {
                final StackTraceElement stackTraceElement2 = threadInfo.getStackTrace()[0];
                final String className2 = stackTraceElement2.getClassName();
                final String methodName2 = stackTraceElement2.getMethodName();
                if (className2.equals("java.lang.Object") && methodName2.equals("wait")) {
                    sb.append(" (on object monitor");
                    if (threadInfo.getLockOwnerName() != null) {
                        sb.append(" owned by \"");
                        sb.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
                    }
                    sb.append(")");
                    break;
                }
                if (className2.equals("java.lang.Thread") && methodName2.equals("sleep")) {
                    sb.append(" (sleeping)");
                    break;
                }
                if (className2.equals("java.lang.Thread") && methodName2.equals("join")) {
                    sb.append(" (on completion of thread ").append(threadInfo.getLockOwnerId()).append(")");
                    break;
                }
                sb.append(" (parking for lock");
                if (threadInfo.getLockOwnerName() != null) {
                    sb.append(" owned by \"");
                    sb.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
                }
                sb.append(")");
                break;
            }
        }
    }
}
