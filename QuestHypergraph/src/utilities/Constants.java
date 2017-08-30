package utilities;


public class Constants
{
    public static final int source_bound = 3;
    public static final int main_quest_nodes_parallelism = 5;
    public static final int parallelism_instance_limit = 2;
    public static final int parallelism_combination_limit = 250;
    
    public enum Method {BRUTALITY, STEALTH, WEALTH, DIPLOMACY, SORCERY};
    
    private Constants()
    {
        throw new AssertionError();
    }
}
