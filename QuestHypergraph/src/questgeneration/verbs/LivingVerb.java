package questgeneration.verbs;
import questgeneration.nouns.LivingNoun;
import questgeneration.nouns.Noun;

public class LivingVerb extends Verb
{
    public LivingVerb(String theName)
    {
        super(theName);
    }
    
    public boolean isCompatible(Noun theNoun)
    {
        //check if noun is living
        if (!(theNoun instanceof LivingNoun)) return false;
        
        //check if the verb is in theNoun's negated verbs list
        for(Verb currVerb: theNoun.getNegatedVerbs())
        {
            if(this.equals(currVerb)) return false;
        }
        
        return true;
    }
}
