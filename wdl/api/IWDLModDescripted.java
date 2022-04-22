package wdl.api;

public interface IWDLModDescripted extends IWDLMod
{
    String getDisplayName();
    
    String getMainAuthor();
    
    String[] getAuthors();
    
    String getURL();
    
    String getDescription();
}
