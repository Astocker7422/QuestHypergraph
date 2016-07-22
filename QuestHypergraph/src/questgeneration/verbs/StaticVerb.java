package questgeneration.verbs;
import questgeneration.nouns.Noun;
import questgeneration.nouns.StaticNoun;

public class StaticVerb extends Verb
{
    public StaticVerb(String theName)
    {
        super(theName);
    }
    
    public boolean isCompatible(Noun theNoun)
    {
        //check if the noun is static
        if (!(theNoun instanceof StaticNoun)) return false;
        
        //check if the verb is in theNoun's negated verbs list
        for(Verb currVerb: theNoun.getNegatedVerbs())
        {
            if(this.equals(currVerb)) return false;
        }
        
        return true;
    }
}
