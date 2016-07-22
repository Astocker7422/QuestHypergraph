package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public class Place extends StaticNoun
{
    public Place(String theType, ArrayList<Verb> negatedVerbs)
    {
        super(theType, negatedVerbs);
    }   
}
