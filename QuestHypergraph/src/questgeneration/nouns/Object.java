package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public class Object extends StaticNoun
{
    public Object(String theType, ArrayList<Verb> negatedVerbs)
    {
        super(theType, negatedVerbs);
    }   
}
