package frontend;
import org.w3c.dom.*;

import questgeneration.nouns.Ally;
import questgeneration.nouns.Book;
import questgeneration.nouns.Enemy;
import questgeneration.nouns.Ingredient;
import questgeneration.nouns.Key;
import questgeneration.nouns.Noun;
import questgeneration.nouns.Place;
import questgeneration.nouns.Relic;
import questgeneration.nouns.Tool;
import questgeneration.verbs.LivingVerb;
import questgeneration.verbs.StaticVerb;
import questgeneration.verbs.Verb;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

public class ConstraintParser
{
    //XML parsing
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
    
    private ArrayList<Verb> Verb_List = new ArrayList<Verb>();
    private ArrayList<Noun> Noun_List = new ArrayList<Noun>();
    
    //ArrayLists of Verbs to store them until adds to negated_verb_lists of nouns
    private ArrayList<StaticVerb> StaticVerb_List = new ArrayList<StaticVerb>();
    private ArrayList<LivingVerb> LivingVerb_List = new ArrayList<LivingVerb>();
    
    public ConstraintParser(String fileName)
    {
        try
        {
            File inputFile = new File(fileName);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Verb> getVerbs()
    {
        return Verb_List;
    }
    
    public ArrayList<StaticVerb> getStaticVerbs()
    {
        return StaticVerb_List;
    }
    
    public ArrayList<LivingVerb> getLivingVerbs()
    {
        return LivingVerb_List;
    }
    
    public ArrayList<Noun> getNouns()
    {
        return Noun_List;
    }
    
    public void parse()
    {
        parseStaticVerbs();
        parseLivingVerbs();
        parsePredecessorLists();
        parseTools();
        parseIngredients();
        parseRelics();
        parseBooks();
        parseKeys();
        parsePlaces();
        parseAllies();
        parseEnemies();
    }
    
    public void parseStaticVerbs()
    {
        Element root = doc.getDocumentElement();
        NodeList StaticVerb_NodeList = doc.getElementsByTagName("StaticVerb");
        if(Options.DEBUG) System.out.println("Static Verbs:");
        for (int temp = 0; temp < StaticVerb_NodeList.getLength(); temp++) 
        {
            Node StaticVerbNode = StaticVerb_NodeList.item(temp);
                
            if (StaticVerbNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element StaticVerbElement = (Element) StaticVerbNode;
                String theName = StaticVerbElement.getElementsByTagName("name").item(0).getTextContent();
                ArrayList<String> theMethods = new ArrayList<String>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < StaticVerbElement.getElementsByTagName("Method").getLength(); VerbNodeNumber++)
                {
                    theMethods.add(StaticVerbElement.getElementsByTagName("Method").item(VerbNodeNumber).getTextContent());
                }
                StaticVerb sVerb = new StaticVerb(theName, theMethods);
                StaticVerb_List.add(sVerb);
                Verb_List.add(sVerb);
                if(Options.DEBUG)
                {
                    System.out.println("    " + sVerb.getName());
                    System.out.println("        Methods: " + sVerb.methods);
                }
            }
        }
        if(StaticVerb_List.isEmpty() || Options.DEBUG) System.out.println("    None");
    }
    
    public void parseLivingVerbs()
    {
        Element root = doc.getDocumentElement();
        NodeList LivingVerb_NodeList = doc.getElementsByTagName("LivingVerb");
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Living Verbs:");
        }
        for (int temp = 0; temp < LivingVerb_NodeList.getLength(); temp++) 
        {
            Node LivingVerbNode = LivingVerb_NodeList.item(temp);
                
            if (LivingVerbNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element LivingVerbElement = (Element) LivingVerbNode;
                String theName = LivingVerbElement.getElementsByTagName("name").item(0).getTextContent();
                ArrayList<String> theMethods = new ArrayList<String>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < LivingVerbElement.getElementsByTagName("Method").getLength(); VerbNodeNumber++)
                {
                    theMethods.add(LivingVerbElement.getElementsByTagName("Method").item(VerbNodeNumber).getTextContent());
                }
                LivingVerb lVerb = new LivingVerb(theName, theMethods);
                LivingVerb_List.add(lVerb);
                Verb_List.add(lVerb);
                if(Options.DEBUG)
                {
                    System.out.println("    " + lVerb.getName());
                    System.out.println("        Methods: " + lVerb.methods);
                }
            }
        }
        if(LivingVerb_List.isEmpty() || Options.DEBUG) System.out.println("    None");
    }
    
    public void parsePredecessorLists()
    {
        Element root = doc.getDocumentElement();
        NodeList PredecessorList_NodeList = doc.getElementsByTagName("PredecessorList");
        for (int temp = 0; temp < PredecessorList_NodeList.getLength(); temp++)
        {
            Node PredecessorListNode = PredecessorList_NodeList.item(temp);
                
            if (PredecessorListNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element PredecessorListElement = (Element) PredecessorListNode;
                String theVerbName = PredecessorListElement.getElementsByTagName("Verb").item(0).getTextContent();
                Verb theVerb = null;
                for(Verb currVerb: Verb_List)
                {
                    if(currVerb.getName().equalsIgnoreCase(theVerbName))
                    {
                        theVerb = currVerb;
                    }
                }
                ArrayList<Verb> PredecessorVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < PredecessorListElement.getElementsByTagName("PredecessorVerb").getLength(); VerbNodeNumber++)
                {
                    for(Verb currVerb: Verb_List)
                    {
                        if(PredecessorListElement.getElementsByTagName("PredecessorVerb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            PredecessorVerbs.add(currVerb);
                        }
                    }
                }
                theVerb.setPredecessorVerbs(PredecessorVerbs);
            }
        }
    }
    
    public void parseTools()
    {
        NodeList Tool_List = doc.getElementsByTagName("Tool");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Tools:");
        }
        
        int toolCount = 0;
        for (int temp = 0; temp < Tool_List.getLength(); temp++) 
        {
            Node ToolNode = Tool_List.item(temp);
                
            if (ToolNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element ToolElement = (Element) ToolNode;
                String theType = ToolElement.getElementsByTagName("type").item(0).getTextContent();
                ArrayList<Verb> negatedVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < ToolElement.getElementsByTagName("Verb").getLength(); VerbNodeNumber++)
                {
                    for(StaticVerb currVerb: StaticVerb_List)
                    {
                        if(ToolElement.getElementsByTagName("Verb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            negatedVerbs.add(currVerb);
                        }
                    }
                }
                Tool currTool = new Tool(theType, negatedVerbs);
                Noun_List.add(currTool);
                toolCount++;
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currTool.getType());
                    System.out.println("        Negated Verbs:");
                    if(negatedVerbs.isEmpty()) System.out.println("        None");
                    for(Verb negated: currTool.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
        if(toolCount == 0 || Options.DEBUG) System.out.println("    None");
    }
    
    public void parseIngredients()
    {
        NodeList Ingredient_List = doc.getElementsByTagName("Ingredient");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Ingredients:");
        }
        
        int ingredientCount = 0;
        for (int temp = 0; temp < Ingredient_List.getLength(); temp++) 
        {
            Node IngredientNode = Ingredient_List.item(temp);
                
            if (IngredientNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element IngredientElement = (Element) IngredientNode;
                String theType = IngredientElement.getElementsByTagName("type").item(0).getTextContent();
                ArrayList<Verb> negatedVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < IngredientElement.getElementsByTagName("Verb").getLength(); VerbNodeNumber++)
                {
                    for(StaticVerb currVerb: StaticVerb_List)
                    {
                        if(IngredientElement.getElementsByTagName("Verb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            negatedVerbs.add(currVerb);
                        }
                    }
                }
                Ingredient currIngredient = new Ingredient(theType, negatedVerbs);
                Noun_List.add(currIngredient);
                ingredientCount++;
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currIngredient.getType());
                    System.out.println("        Negated Verbs:");
                    if(negatedVerbs.isEmpty()) System.out.println("        None");
                    for(Verb negated: currIngredient.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
        if(ingredientCount == 0 || Options.DEBUG) System.out.println("    None");
    }
    
    public void parseRelics()
    {
        NodeList Relic_List = doc.getElementsByTagName("Relic");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Relics:");
        }
        
        int relicCount = 0;
        for (int temp = 0; temp < Relic_List.getLength(); temp++) 
        {
            Node RelicNode = Relic_List.item(temp);
                
            if (RelicNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element RelicElement = (Element) RelicNode;
                String theType = RelicElement.getElementsByTagName("type").item(0).getTextContent();
                ArrayList<Verb> negatedVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < RelicElement.getElementsByTagName("Verb").getLength(); VerbNodeNumber++)
                {
                    for(StaticVerb currVerb: StaticVerb_List)
                    {
                        if(RelicElement.getElementsByTagName("Verb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            negatedVerbs.add(currVerb);
                        }
                    }
                }
                Relic currRelic = new Relic(theType, negatedVerbs);
                Noun_List.add(currRelic);
                relicCount++;
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currRelic.getType());
                    System.out.println("        Negated Verbs:");
                    if(negatedVerbs.isEmpty()) System.out.println("        None");
                    for(Verb negated: currRelic.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
        if(relicCount == 0 || Options.DEBUG) System.out.println("    None");
    }
    
    public void parseBooks()
    {
        NodeList Book_List = doc.getElementsByTagName("Book");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Books:");
        }
        
        int bookCount = 0;
        for (int temp = 0; temp < Book_List.getLength(); temp++) 
        {
            Node BookNode = Book_List.item(temp);
                
            if (BookNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element BookElement = (Element) BookNode;
                String theType = BookElement.getElementsByTagName("type").item(0).getTextContent();
                ArrayList<Verb> negatedVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < BookElement.getElementsByTagName("Verb").getLength(); VerbNodeNumber++)
                {
                    for(StaticVerb currVerb: StaticVerb_List)
                    {
                        if(BookElement.getElementsByTagName("Verb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            negatedVerbs.add(currVerb);
                        }
                    }
                }
                Book currBook = new Book(theType, negatedVerbs);
                Noun_List.add(currBook);
                bookCount++;
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currBook.getType());
                    System.out.println("        Negated Verbs:");
                    if(negatedVerbs.isEmpty()) System.out.println("        None");
                    for(Verb negated: currBook.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
        if(bookCount == 0 || Options.DEBUG) System.out.println("    None");
    }
    
    public void parseKeys()
    {
        NodeList Key_List = doc.getElementsByTagName("Key");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Keys:");
        }
        
        int keyCount = 0;
        for (int temp = 0; temp < Key_List.getLength(); temp++) 
        {
            Node KeyNode = Key_List.item(temp);
                
            if (KeyNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element KeyElement = (Element) KeyNode;
                String theType = KeyElement.getElementsByTagName("type").item(0).getTextContent();
                ArrayList<Verb> negatedVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < KeyElement.getElementsByTagName("Verb").getLength(); VerbNodeNumber++)
                {
                    for(StaticVerb currVerb: StaticVerb_List)
                    {
                        if(KeyElement.getElementsByTagName("Verb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            negatedVerbs.add(currVerb);
                        }
                    }
                }
                Key currKey = new Key(theType, negatedVerbs);
                Noun_List.add(currKey);
                keyCount++;
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currKey.getType());
                    System.out.println("        Negated Verbs:");
                    if(negatedVerbs.isEmpty()) System.out.println("        None");
                    for(Verb negated: currKey.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
        if(keyCount == 0 || Options.DEBUG) System.out.println("    None");
    }
    
    public void parsePlaces()
    {
        NodeList Place_List = doc.getElementsByTagName("Place");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Places:");
        }
        
        int placeCount = 0;
        for (int temp = 0; temp < Place_List.getLength(); temp++) 
        {
            Node PlaceNode = Place_List.item(temp);
                
            if (PlaceNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element PlaceElement = (Element) PlaceNode;
                String theType = PlaceElement.getElementsByTagName("type").item(0).getTextContent();
                ArrayList<Verb> negatedVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < PlaceElement.getElementsByTagName("Verb").getLength(); VerbNodeNumber++)
                {
                    for(StaticVerb currVerb: StaticVerb_List)
                    {
                        if(PlaceElement.getElementsByTagName("Verb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            negatedVerbs.add(currVerb);
                        }
                    }
                }
                Place currPlace = new Place(theType, negatedVerbs);
                Noun_List.add(currPlace);
                placeCount++;
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currPlace.getType());
                    System.out.println("        Negated Verbs:");
                    if(negatedVerbs.isEmpty()) System.out.println("        None");
                    for(Verb negated: currPlace.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
        if(placeCount == 0 || Options.DEBUG) System.out.println("    None");
    }
    
    public void parseAllies()
    {
        NodeList Ally_List = doc.getElementsByTagName("Ally");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Allies:");
        }
        
        int allyCount = 0;
        for (int temp = 0; temp < Ally_List.getLength(); temp++) 
        {
            Node AllyNode = Ally_List.item(temp);
                
            if (AllyNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element AllyElement = (Element) AllyNode;
                String theType = AllyElement.getElementsByTagName("type").item(0).getTextContent();
                ArrayList<Verb> negatedVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < AllyElement.getElementsByTagName("Verb").getLength(); VerbNodeNumber++)
                {
                    for(LivingVerb currVerb: LivingVerb_List)
                    {
                        if(AllyElement.getElementsByTagName("Verb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            negatedVerbs.add(currVerb);
                        }
                    }
                }
                Ally currAlly = new Ally(theType, negatedVerbs);
                Noun_List.add(currAlly);
                allyCount++;
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currAlly.getType());
                    System.out.println("        Negated Verbs:");
                    if(negatedVerbs.isEmpty()) System.out.println("        None");
                    for(Verb negated: currAlly.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
        if(allyCount == 0 || Options.DEBUG) System.out.println("    None");
    }
    
    public void parseEnemies()
    {
        NodeList Enemy_List = doc.getElementsByTagName("Enemy");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Enemys:");
        }
        
        int enemyCount = 0;
        for (int temp = 0; temp < Enemy_List.getLength(); temp++) 
        {
            Node EnemyNode = Enemy_List.item(temp);
                
            if (EnemyNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element EnemyElement = (Element) EnemyNode;
                String theType = EnemyElement.getElementsByTagName("type").item(0).getTextContent();
                ArrayList<Verb> negatedVerbs = new ArrayList<Verb>();
                for(int VerbNodeNumber = 0; VerbNodeNumber < EnemyElement.getElementsByTagName("Verb").getLength(); VerbNodeNumber++)
                {
                    for(LivingVerb currVerb: LivingVerb_List)
                    {
                        if(EnemyElement.getElementsByTagName("Verb").item(VerbNodeNumber).getTextContent().equals(currVerb.getName()))
                        {
                            negatedVerbs.add(currVerb);
                        }
                    }
                }
                Enemy currEnemy = new Enemy(theType, negatedVerbs);
                Noun_List.add(currEnemy);
                enemyCount++;
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currEnemy.getType());
                    System.out.println("        Negated Verbs:");
                    if(negatedVerbs.isEmpty()) System.out.println("        None");
                    for(Verb negated: currEnemy.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
        if(enemyCount == 0 || Options.DEBUG) System.out.println("    None");
    }
}
