package questgeneration;
import questgeneration.nouns.Noun;
import questgeneration.verbs.Verb;

public class Action
{
    private Verb _verb;
    private Noun _noun;
    public boolean requiresSequencing;
    
    public Action(Verb theVerb, Noun theNoun)
    {
        _verb = theVerb;
        _noun = theNoun;
        requiresSequencing = true;
    }
    
    public Verb getVerb()
    {
        return _verb;
    }
    
    public Noun getNoun()
    {
        return _noun;
    }
    
    public boolean equals(Action thatAction)
    {
        if(this.getVerb().equals(thatAction.getVerb()) && this.getNoun().equals(thatAction.getNoun())) return true;
        else return false;
    }
    
    @Override
    public String toString()
    {
        String actionString;
        actionString = this.getVerb().getName() + " " + this.getNoun().getType();
        return actionString;
    }
}
