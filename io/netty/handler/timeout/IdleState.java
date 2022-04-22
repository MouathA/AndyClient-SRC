package io.netty.handler.timeout;

public enum IdleState
{
    READER_IDLE("READER_IDLE", 0), 
    WRITER_IDLE("WRITER_IDLE", 1), 
    ALL_IDLE("ALL_IDLE", 2);
    
    private static final IdleState[] $VALUES;
    
    private IdleState(final String s, final int n) {
    }
    
    static {
        $VALUES = new IdleState[] { IdleState.READER_IDLE, IdleState.WRITER_IDLE, IdleState.ALL_IDLE };
    }
}
