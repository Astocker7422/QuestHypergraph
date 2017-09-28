package hypergraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import questgeneration.QuestData;
import pebbler.PebblerHyperedge;
import pebbler.PebblerHypergraph;
import pebbler.PebblerHypernode;
import questgeneration.Action;
import utilities.Constants;
import digraph.DiGraph;

public class Hypergraph<T, A> extends QuestData
{
    public ArrayList<Hypernode<T, A>> vertices;
    
    public Hypergraph()
    {
        vertices = new ArrayList<Hypernode<T, A>>();
        isComplex = true;
    }
    
    public Hypergraph(Hypergraph<T, A> HG)
    {
        vertices = new ArrayList<Hypernode<T, A>>();
        
        for(Hypernode<T, A> currNode : HG.vertices)
	{
            this.addNode(currNode.data, currNode.id);
        }
            
        for(Hypernode currNode : HG.vertices)
        {
            ArrayList<Hyperedge> currNodeOut = currNode.outEdges;
            ArrayList<Hyperedge> currNodeIn = currNode.inEdges;
            
            for(Hyperedge outEdge : currNodeOut)
            {
                ArrayList<Integer> oldFromList = outEdge.sourceNodes;
                ArrayList<Integer> newFromList = new ArrayList<Integer>();
                for(Integer from : oldFromList)
                {
                    newFromList.add(from);
                }
                
                int newTarget = outEdge.targetNode;
                
                this.addEdge(newFromList, newTarget);
            }
            
            for(Hyperedge inEdge : currNodeIn)
            {
                ArrayList<Integer> oldFromList = inEdge.sourceNodes;
                ArrayList<Integer> newFromList = new ArrayList<Integer>();
                for(Integer from : oldFromList)
                {
                    newFromList.add(from);
                }
                
                int newTarget = inEdge.targetNode;
                
                this.addEdge(newFromList, newTarget);
            }
	}
        
        isComplex = true;
    }
    
    public int size() { return vertices.size(); }
 
    //trys to add a vertex to the graph and returns whether it was successful
    //makes the index of the node its id
    public boolean addNode(T data)
    {
        return vertices.add(new Hypernode<T, A>(data, vertices.size()));
    }
    
    //allows to assign id (specifically for Hypergraph made from topologicalSort)
    public boolean addNode(T data, int id)
    {
        return vertices.add(new Hypernode<T, A>(data, id));
    }
    
    //adds new edge and returns whether it was successful
    public boolean addEdge(Hyperedge<A> newEdge)
    {
        //for every vertex check if the edge already exists
        if (hasEdge(newEdge)) return false;
        
        //for every vertex in the edge's sourcenodes list, add the edge to out edges
        for(Integer currNode: newEdge.sourceNodes)
        {
            vertices.get(currNode).outEdges.add(newEdge);
        }
        
        //add the edge to the edge's targetnode's in edge list
        vertices.get(newEdge.targetNode).inEdges.add(newEdge);

        return true;
    }
    
    public void addEdge(ArrayList<Integer> fromList, int to)
    {
        Annotation edgeAnnotation = new Annotation();
        Hyperedge newEdge = new Hyperedge(fromList, to, edgeAnnotation);
        addEdge(newEdge);
    }
    
    //checks and returns if each vertex is incident to the edge
    public boolean hasEdge(Hyperedge<A> e)
    {
        for(Hypernode<T, A> currNode: vertices)
        {
            if(currNode.inEdges.contains(e)) return true;
            if(currNode.outEdges.contains(e)) return true;
        }
        return false;
    }
    
    //returns the node with a specific id
    public Hypernode<T, A> getNode(int id) throws Exception
    {
        for(Hypernode currNode : vertices)
        {
            if(currNode.id == id) return currNode;
        }
        throw new Exception();
    }
    
    public boolean isDisconnected()
    {
        if(size() == 1) return false;
        if(vertices.get(0).outEdges.isEmpty()) return true;
        if(vertices.get(vertices.size() - 1).inEdges.isEmpty()) return true;
        for(int index = 1; index < (vertices.size() - 1); index++)
        {
            if(vertices.get(index).inEdges.isEmpty() && vertices.get(index).outEdges.isEmpty()) return true;
        }
        return false;
    }
    
    //return integer-based representation of hypergraph
    public PebblerHypergraph<T, A> getPebbledHypergraph() throws Exception
    {
        //
        // Create the nodes
        //
        ArrayList<PebblerHypernode<A>> pebbledNodes = new ArrayList<PebblerHypernode<A>>(vertices.size());

        for (int v = 0; v < vertices.size(); v++)
        {
            pebbledNodes.add(v, vertices.get(v).createPebbledNode());
        }

        //
        // Create the hyperedges
        //
        for (int v = 0; v < vertices.size(); v++)
        {
            for(Hyperedge<A>currEdge: vertices.get(v).outEdges)
            {
                //only add if it is the "minimum" source node, so the edge is not added twice to any node
                if(v == Collections.min(currEdge.sourceNodes))
                {
                    PebblerHyperedge<A> newEdge = new PebblerHyperedge<A>(currEdge.sourceNodes, currEdge.targetNode, currEdge.annot);
                    for(int src: currEdge.sourceNodes)
                    {
                        pebbledNodes.get(src).addEdge(newEdge);
                    }
                }
            }
        }

        return new PebblerHypergraph<T, A>(this, pebbledNodes);
    }
    
    public boolean equals(Hypergraph otherHG) throws Exception
    {
        if(this.size() != otherHG.size()) return false;
        
        for(int index = 0; index < this.size(); index++)
        {
            Hypernode thisNode = this.getNode(index);
            Hypernode otherNode = otherHG.getNode(index);
            
            if(!thisNode.equals(otherNode)) return false;
        }
        
        return true;
    }
    
    public int minDepth()
    {
        int sink = -1;
        
        for(Hypernode currNode : vertices)
        {
            if(currNode.outEdges.isEmpty()) sink = vertices.indexOf(currNode);
        }
        
        return minDepthHelper(sink, 0);
    }
    
    public int minDepthHelper(int currNodeInt, int minDepth)
    {
        Hypernode currNode = vertices.get(currNodeInt);
        
        minDepth++;
        
        ArrayList<Hyperedge> inEdges = currNode.inEdges;
        
        if(inEdges.isEmpty()) return minDepth;
        
        if(currNode.isComplex())
        {
            minDepth -= 1;
            
            Hypergraph subHG = (Hypergraph) currNode.data;
            
            int tempDepth = subHG.minDepth();
            
            minDepth += tempDepth;
            
            minDepth -= 2;
        }
        
        int originalDepth = minDepth;
        
        for(Hyperedge inEdge : inEdges)
        {
            ArrayList<Integer> sources = inEdge.sourceNodes;
            
            if(inEdges.indexOf(inEdge) == 0)
            {
                if(sources.size() > 1)
                {
                    originalDepth += sources.size();
                    
                    int sourceNum = 1;
                    for(int source : sources)
                    {
                        Hypernode sourceNode = vertices.get(source);
                        
                        if(sourceNode.isComplex())
                        {
                            minDepth -= 1;
            
                            Hypergraph subHG = (Hypergraph) sourceNode.data;
            
                            originalDepth += subHG.minDepth();
            
                            originalDepth -= 2;
                        }
                        else
                        {
                            int sourceDepth = originalDepth - 1;
                            
                            sourceDepth = minDepthHelper(sources.get(sourceNum - 1), sourceDepth);
                            
                            boolean contains = false;
                                
                            ArrayList<Hyperedge> sourceIn = sourceNode.inEdges;
                                
                            for(Hyperedge currIn : sourceIn)
                            {
                                for(int otherSource : sources)
                                {
                                    if(otherSource == source) continue;
                                        
                                    Hypernode other = vertices.get(otherSource);
                                        
                                    ArrayList<Hyperedge> otherIn = other.inEdges;
                                        
                                    for(Hyperedge otherCurrIn : otherIn)
                                    {
                                        contains = otherCurrIn.contains(currIn);
                                        if(contains) break;
                                    }
                                        
                                    if(contains) break;
                                }
                                    
                                if(contains) break;
                            }
                
                            if(sourceNum == 1 || !contains) minDepth = sourceDepth;
                            else
                            {
                                if(sourceDepth < minDepth && !contains) minDepth = sourceDepth;
                            }
                        }
                        sourceNum++;
                    }
                }
            
                else
                {
                    int tempDepth = minDepthHelper(sources.get(0), originalDepth);
            
                    minDepth = tempDepth;
                }
            }
            else
            {
                if(sources.size() > 1)
                {
                    int tempDepth = originalDepth + sources.size();
                    
                    for(int source : sources)
                    {
                        Hypernode sourceNode = vertices.get(source);
                        
                        if(sourceNode.isComplex())
                        {
                            minDepth -= 1;
            
                            Hypergraph subHG = (Hypergraph) sourceNode.data;
            
                            tempDepth += subHG.minDepth();
            
                            tempDepth -= 2;
                        }
                    }
                
                    tempDepth = minDepthHelper(sources.get(0), tempDepth);
                
                    if(tempDepth < minDepth) minDepth = tempDepth;
                }
            
                else
                {
                    int tempDepth = minDepthHelper(sources.get(0), originalDepth);
            
                    if(tempDepth < minDepth) minDepth = tempDepth;
                }
            }
        }
        
        return minDepth;
    }
    
    public int maxDepth()
    {
        int sink = -1;
        
        for(Hypernode currNode : vertices)
        {
            if(currNode.outEdges.isEmpty()) sink = vertices.indexOf(currNode);
        }
        
        return maxDepthHelper(sink, 0);
    }
    
    public int maxDepthHelper(int currNodeInt, int maxDepth)
    {
        Hypernode currNode = vertices.get(currNodeInt);
        
        maxDepth++;
        
        ArrayList<Hyperedge> inEdges = currNode.inEdges;
        
        if(inEdges.isEmpty()) return maxDepth;
        
        if(currNode.isComplex())
        {
            maxDepth -= 1;
            
            Hypergraph subHG = (Hypergraph) currNode.data;
            
            int tempDepth = subHG.maxDepth();
            
            maxDepth += tempDepth;
            
            maxDepth -= 2;
        }
        
        int originalDepth = maxDepth;
        
        for(Hyperedge inEdge : inEdges)
        {
            ArrayList<Integer> sources = inEdge.sourceNodes;
            
            if(sources.size() > 1)
            {
                originalDepth += sources.size();
                    
                int sourceNum = 1;
                for(int source : sources)
                {
                    Hypernode sourceNode = vertices.get(source);
                        
                    if(sourceNode.isComplex())
                    {
                        maxDepth -= 1;
            
                        Hypergraph subHG = (Hypergraph) sourceNode.data;
            
                        originalDepth += subHG.maxDepth();
            
                        originalDepth -= 2;
                    }
                    else
                    {
                        int sourceDepth = originalDepth - 1;
                            
                        sourceDepth = maxDepthHelper(sources.get(sourceNum - 1), sourceDepth);
                
                        if(sourceNum == 1) maxDepth = sourceDepth;
                        else
                        {
                            if(sourceDepth > maxDepth) maxDepth = sourceDepth;
                        }
                    }
                    sourceNum++;
                }
            }
            
            else
            {
                int tempDepth = maxDepthHelper(sources.get(0), originalDepth);
            
                if(tempDepth > maxDepth) maxDepth = tempDepth;
            }
        }
        
        return maxDepth;
    }
    
    public int concurrencyCount()
    {
        int concurrencyCount = 0;
        
        ArrayList<Hyperedge> counted = new ArrayList<Hyperedge>();
        
        for(Hypernode currNode : vertices)
        {
            ArrayList<Hyperedge> inEdges = currNode.inEdges;
            for(Hyperedge inEdge : inEdges)
            {
                if(!counted.contains(inEdge))
                {
                    if(inEdge.sourceNodes.size() > 1)
                    {
                        concurrencyCount++;
                        counted.add(inEdge);
                    }
                }
            }
            
            ArrayList<Hyperedge> outEdges = currNode.outEdges;
            for(Hyperedge outEdge : outEdges)
            {
                if(!counted.contains(outEdge))
                {
                    if(outEdge.sourceNodes.size() > 1)
                    {
                        concurrencyCount++;
                        counted.add(outEdge);
                    }
                }
            }
            
            if(currNode.isComplex())
            {
                Hypergraph subHG = (Hypergraph) currNode.data;
                concurrencyCount += subHG.concurrencyCount();
            }
        }
        
        return concurrencyCount;
    }
    
    public double concurrencyFactor(double concurrencyCount)
    {
        double numActions = 0;
        
        for(Hypernode currNode : vertices)
        {
            if(currNode.isComplex())
            {
                Hypergraph subHG = (Hypergraph) currNode.data;
                numActions += subHG.size();
                continue;
            }
            
            numActions++;
        }
        
        return concurrencyCount / numActions;
    }
    
    public int pathComplexity() throws Exception
    {
        int pathComplexity = 1;
        
        for(Hypernode currNode : vertices)
        {
            if(currNode.isComplex())
            {
                Hypergraph subHG = (Hypergraph) currNode.data;
                int methods = subHG.getNode(0).outEdges.size();
                pathComplexity *= methods;
            }
        }
        
        return pathComplexity;
    }
    
    @Override
    public String toString()
    {
        String graphS = "";
        
        int subquestCount = 0;
        
        for(Hypernode<T, A> currNode: vertices)
        {
            if(vertices.indexOf(currNode) != 0) graphS += ", [Vertex " + vertices.indexOf(currNode) + "]: ";
            else graphS += "[Vertex " + vertices.indexOf(currNode) + "]: ";
            
            if(!currNode.isComplex()) graphS += "(data: " + currNode.data + " / ";
            else
            {
                subquestCount++;
                graphS += "(data: SUB-QUEST " + subquestCount + " / ";
            }
            
            graphS += "out edges: ";
            if(currNode.outEdges.isEmpty()) graphS += "none ";
            for(Hyperedge<A> currEdge: currNode.outEdges)
            {
                int lastIndex = currNode.outEdges.size() - 1;
                if((currNode.outEdges.indexOf(currEdge)) != (lastIndex))
                {
                    graphS += currEdge.toString() + ", ";
                }
                else graphS += currEdge.toString();
            }
            
            graphS += " / in edges: ";
            if(currNode.inEdges.isEmpty()) graphS += "none ";
            for(Hyperedge<A> currEdge: currNode.inEdges)
            {
                int lastIndex = currNode.inEdges.size() - 1;
                if((currNode.inEdges.indexOf(currEdge)) != (lastIndex))
                {
                    graphS += currEdge.toString() + ", ";
                }
                else graphS += currEdge.toString();
            }
            
            graphS += ")";
        }
        
        if(subquestCount > 0)
        {
            subquestCount = 1;
            for(Hypernode<T, A> currNode: vertices)
            {
                if(currNode.isComplex())
                {
                    if(subquestCount == 1) graphS += "\n" + "Sub-Quests: ";
                    graphS += "\n" + "Sub-Quest " + subquestCount + ": " + currNode.data;
                    subquestCount++;
                }
            }
        }
        
        return graphS;
    }
}