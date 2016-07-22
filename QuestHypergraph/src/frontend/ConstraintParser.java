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
                StaticVerb sVerb = new StaticVerb(theName);
                StaticVerb_List.add(sVerb);
                Verb_List.add(sVerb);
                if(Options.DEBUG) System.out.println("    " + sVerb.getName());
            }
        }
    }
    
    public void parseLivingVerbs()
    {
        Element root = doc.getDocumentElement();
        NodeList LivingVerb_NodeList = doc.getElementsByTagName("LivingVerb");
        if(Options.DEBUG) System.out.println("Living Verbs:");
        for (int temp = 0; temp < LivingVerb_NodeList.getLength(); temp++) 
        {
            Node LivingVerbNode = LivingVerb_NodeList.item(temp);
                
            if (LivingVerbNode.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element LivingVerbElement = (Element) LivingVerbNode;
                String theName = LivingVerbElement.getElementsByTagName("name").item(0).getTextContent();
                LivingVerb sVerb = new LivingVerb(theName);
                LivingVerb_List.add(sVerb);
                Verb_List.add(sVerb);
                if(Options.DEBUG) System.out.println("    " + sVerb.getName());
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
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currTool.getType());
                    System.out.println("        Negated Verbs:");
                    for(Verb negated: currTool.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
    }
    
    public void parseIngredients()
    {
        NodeList Ingredient_List = doc.getElementsByTagName("Ingredient");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Ingredients:");
        }
        
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
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currIngredient.getType());
                    System.out.println("        Negated Verbs:");
                    for(Verb negated: currIngredient.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
    }
    
    public void parseRelics()
    {
        NodeList Relic_List = doc.getElementsByTagName("Relic");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Relics:");
        }
        
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
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currRelic.getType());
                    System.out.println("        Negated Verbs:");
                    for(Verb negated: currRelic.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
    }
    
    public void parseBooks()
    {
        NodeList Book_List = doc.getElementsByTagName("Book");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Books:");
        }
        
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
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currBook.getType());
                    System.out.println("        Negated Verbs:");
                    for(Verb negated: currBook.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
    }
    
    public void parseKeys()
    {
        NodeList Key_List = doc.getElementsByTagName("Key");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Keys:");
        }
        
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
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currKey.getType());
                    System.out.println("        Negated Verbs:");
                    for(Verb negated: currKey.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
    }
    
    public void parsePlaces()
    {
        NodeList Place_List = doc.getElementsByTagName("Place");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Places:");
        }
        
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
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currPlace.getType());
                    System.out.println("        Negated Verbs:");
                    for(Verb negated: currPlace.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
    }
    
    public void parseAllies()
    {
        NodeList Ally_List = doc.getElementsByTagName("Ally");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Allies:");
        }
        
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
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currAlly.getType());
                    System.out.println("        Negated Verbs:");
                    for(Verb negated: currAlly.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
    }
    
    public void parseEnemies()
    {
        NodeList Enemy_List = doc.getElementsByTagName("Enemy");
        
        if(Options.DEBUG)
        {
            System.out.println();
            System.out.println("Enemys:");
        }
        
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
                
                if(Options.DEBUG)
                {
                    System.out.println("    " + currEnemy.getType());
                    System.out.println("        Negated Verbs:");
                    for(Verb negated: currEnemy.getNegatedVerbs())
                    {
                        System.out.println("        " + negated.getName());
                    }
                }
            }
        }
    }
}
