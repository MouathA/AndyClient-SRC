package tv.twitch;

public class StandardCoreAPI extends CoreAPI
{
    public StandardCoreAPI() {
        System.loadLibrary("twitchsdk");
    }
    
    @Override
    protected void finalize() {
    }
    
    private static native ErrorCode TTV_Java_Init(final String p0, final String p1);
    
    private static native ErrorCode TTV_Java_Shutdown();
    
    private static native ErrorCode TTV_Java_SetTraceLevel(final int p0);
    
    private static native ErrorCode TTV_Java_SetTraceOutput(final String p0);
    
    private static native String TTV_Java_ErrorToString(final ErrorCode p0);
    
    @Override
    public ErrorCode init(final String s, final String s2) {
        return TTV_Java_Init(s, s2);
    }
    
    @Override
    public ErrorCode shutdown() {
        return TTV_Java_Shutdown();
    }
    
    @Override
    public ErrorCode setTraceLevel(MessageLevel ttv_ML_NONE) {
        if (ttv_ML_NONE == null) {
            ttv_ML_NONE = MessageLevel.TTV_ML_NONE;
        }
        return TTV_Java_SetTraceLevel(ttv_ML_NONE.getValue());
    }
    
    @Override
    public ErrorCode setTraceOutput(final String s) {
        return TTV_Java_SetTraceOutput(s);
    }
    
    @Override
    public String errorToString(final ErrorCode errorCode) {
        if (errorCode == null) {
            return null;
        }
        return TTV_Java_ErrorToString(errorCode);
    }
}
