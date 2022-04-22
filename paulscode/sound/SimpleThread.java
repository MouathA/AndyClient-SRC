package paulscode.sound;

public class SimpleThread extends Thread
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private boolean alive;
    private boolean kill;
    
    public SimpleThread() {
        this.alive = true;
        this.kill = false;
    }
    
    protected void cleanup() {
        this.kill(true, true);
        this.alive(true, false);
    }
    
    @Override
    public void run() {
        this.cleanup();
    }
    
    public void restart() {
        new Thread() {
            final SimpleThread this$0;
            
            @Override
            public void run() {
                SimpleThread.access$000(this.this$0);
            }
        }.start();
    }
    
    private void rerun() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_1       
        //     2: iconst_1       
        //     3: invokespecial   paulscode/sound/SimpleThread.kill:(ZZ)Z
        //     6: pop            
        //     7: aload_0        
        //     8: aload_0        
        //     9: ldc2_w          100
        //    12: invokevirtual   paulscode/sound/SimpleThread.snooze:(J)V
        //    15: goto            7
        //    18: aload_0        
        //    19: iconst_1       
        //    20: iconst_1       
        //    21: invokespecial   paulscode/sound/SimpleThread.alive:(ZZ)Z
        //    24: pop            
        //    25: aload_0        
        //    26: iconst_1       
        //    27: iconst_0       
        //    28: invokespecial   paulscode/sound/SimpleThread.kill:(ZZ)Z
        //    31: pop            
        //    32: aload_0        
        //    33: invokevirtual   paulscode/sound/SimpleThread.run:()V
        //    36: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0007 (coming from #0015).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean alive() {
        return this.alive(false, false);
    }
    
    public void kill() {
        this.kill(true, true);
    }
    
    protected boolean dying() {
        return this.kill(false, false);
    }
    
    private synchronized boolean kill(final boolean b, final boolean kill) {
        if (b) {
            this.kill = kill;
        }
        return this.kill;
    }
    
    protected void snooze(final long n) {
        Thread.sleep(n);
    }
    
    static void access$000(final SimpleThread simpleThread) {
        simpleThread.rerun();
    }
}
