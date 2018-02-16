//Copyright (c) 2018 Alex R. Stocker
//
//Permission is hereby granted, free of charge, to any person obtaining a copy of 
//this software and associated documentation files (the "Software"), to deal in 
//the Software without restriction, including without limitation the rights to use, 
//copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
//Software, and to permit persons to whom the Software is furnished to do so, 
//subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all 
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
//FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
//COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
//IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
//CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package questgeneration;
import java.util.ArrayList;
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
        if(_verb == null || _noun == null)
        {
            actionString = "DUMMY";
            return actionString;
        }
        actionString = this.getVerb().getName() + " " + this.getNoun().getType();
        return actionString;
    }
}
