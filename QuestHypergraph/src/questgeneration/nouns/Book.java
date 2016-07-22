package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public class Book extends Object
{
    public Book(String theType, ArrayList<Verb> negatedVerbs)
    {
        super(theType, negatedVerbs);
    }   
}
