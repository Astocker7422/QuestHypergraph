package questgeneration;
import questgeneration.nouns.Noun;
import questgeneration.verbs.Verb;

public class Action extends QuestData
{
    private Verb _verb;
    private Noun _noun;
    public boolean requiresSequencing;
    
    public Action()
    {
        _verb = null;
        _noun = null;
        requiresSequencing = false;
        isComplex = false;
    }
    
    public Action(Verb theVerb, Noun theNoun)
    {
        _verb = theVerb;
        _noun = theNoun;
        if(theVerb.getPredecessorVerbs().isEmpty()) requiresSequencing = false;
        else requiresSequencing = true;
        isComplex = false;
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
