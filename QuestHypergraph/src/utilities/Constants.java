package utilities;


public class Constants
{
    public static final int source_bound = 3;
    
    public enum Method {BRUTALITY, STEALTH, WEALTH, DIPLOMACY, SORCERY};
    
    private Constants()
    {
        throw new AssertionError();
    }
}
