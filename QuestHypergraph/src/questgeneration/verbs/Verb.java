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

import questgeneration.nouns.Noun;
import utilities.Constants;
import java.util.ArrayList;

public abstract class Verb
{
    private final String name;
    public final ArrayList<Constants.Method> methods;
    private ArrayList<Verb> predecessor_verbs;
    
    public Verb(String theName, ArrayList<String> theMethods)
    {
        name = theName;
        
        methods = new ArrayList<Constants.Method>();
        for(String method : theMethods)
        {
            methods.add(Constants.Method.valueOf(method));
        }
        
        predecessor_verbs = new ArrayList<Verb>();
    }
    
    public Verb(String theName)
    {
        name = theName;
        
        methods = new ArrayList<Constants.Method>();
        
        predecessor_verbs = new ArrayList<Verb>();
    }
    
    public void setPredecessorVerbs(ArrayList<Verb> predecessors)
    {
        predecessor_verbs = predecessors;
    }
    
    public ArrayList<Verb> getPredecessorVerbs()
    {
        return predecessor_verbs;
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
    
    public String toString()
    {
        return this.name;
    }
}
