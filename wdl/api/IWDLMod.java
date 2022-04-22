package wdl.api;

public interface IWDLMod
{
    boolean isValidEnvironment(final String p0);
    
    String getEnvironmentErrorMessage(final String p0);
}
