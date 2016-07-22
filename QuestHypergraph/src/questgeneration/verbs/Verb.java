package questgeneration.verbs;
import questgeneration.nouns.Noun;

public abstract class Verb
{
    private final String name;
    
    public Verb(String theName)
    {
        name = theName;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean equals(Verb thatVerb)
    {
        if(name.equals(thatVerb.getName())) return true;
        
        else return false;
    }
    
    public abstract boolean isCompatible(Noun theNoun);
}
