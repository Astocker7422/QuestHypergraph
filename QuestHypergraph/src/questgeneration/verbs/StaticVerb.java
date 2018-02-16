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

package questgeneration.verbs;
import java.util.ArrayList;
import questgeneration.nouns.Noun;
import questgeneration.nouns.StaticNoun;

public class StaticVerb extends Verb
{
    public StaticVerb(String theName, ArrayList<String> theMethods)
    {
        super(theName, theMethods);
    }
    
    public StaticVerb(String theName)
    {
        super(theName);
    }
    
    @Override
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
