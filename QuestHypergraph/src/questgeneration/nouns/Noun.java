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

package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public abstract class Noun
{
    private String type;
    private ArrayList<Verb> negated_verb_list;
    
    public Noun(String theType, ArrayList<Verb> negatedVerbs)
    {
        type = theType;
        negated_verb_list = negatedVerbs;
    }
    
    public String getType()
    {
        return type;
    }
    
    public ArrayList<Verb> getNegatedVerbs()
    {
        return negated_verb_list;
    }
    
    public boolean equals(Noun thatNoun)
    {
        if(this.getType().equals(thatNoun.getType())) return true;
        else return false;
    }
}
