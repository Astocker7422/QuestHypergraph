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

package questgeneration.constraints;

import java.util.ArrayList;
import utilities.Utilities;

public class HypergraphFilter<T> implements Filter
{
    double upper_bound;
    public ArrayList<String> _hypergraphs;
    public enum _function {EDIT_DISTANCE, HAMMING, KENDALL_TAU};
    //number of hypergraphs allowed PER LINEARIZATION by number of nodes (0 - 30 nodes)
    //21 nodes is second from left fifth row (sample Skyrim quest length)
    //7 nodes is third from left second row (generic parallelism quest length [holds main quest actions and sub-quests])
    private final double[] upperBounds = {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                                            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                                            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                                            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 40, 35,  
                                            30, 25, 20, 18, 16,      
                                            14, 12, 10, 8, 6, 4};
    
    public HypergraphFilter(ArrayList<String> hypergraphList, int numNodes)
    {
        _hypergraphs = hypergraphList;
        upper_bound = upperBounds[numNodes];
    }
    
    //filter full list of source node number orders
    //put in line 116 of HypergraphGenerator
//    @Override
//    public void filter()
//    {
//        int count = 0;
//        
//        ArrayList<ArrayList<Integer>> distanceMatrix = new ArrayList<ArrayList<Integer>>();
//        ArrayList<Integer> Sums = new ArrayList<Integer>();
//        
//        for(String currOrder: _hypergraphs)
//        {
//            ArrayList<Integer> currOrderDistances = new ArrayList<Integer>();
//            int n = currOrder.length();
//            
//            for(String otherOrder: _hypergraphs)
//            {
//                double distance = 0;
//                // CHOOSE WHICH DISTANCE FUNCTION TO USE
//                LinearizationFilter._function FUNC_TYPE = LinearizationFilter._function.HAMMING;
//                switch(FUNC_TYPE)
//                {
//                    case EDIT_DISTANCE:
//                        distance = Utilities.editDistance(currOrder, otherOrder) / n;
//                    break;
//                    case HAMMING:
//                        distance = Utilities.hamming(currOrder, otherOrder);
//                    break;
//                    case KENDALL_TAU:
//                        int[] currOrderAry = new int[currOrder.length()];
//                        int[] otherOrderAry = new int[otherOrder.length()];
//                        for(int index = 0; index < currOrder.length(); index++)
//                        {
//                            currOrderAry[index] = Character.getNumericValue(currOrder.charAt(index));
//                            otherOrderAry[index] = Character.getNumericValue(otherOrder.charAt(index));
//                        }
//                        distance = Utilities.kendallTau(currOrderAry, otherOrderAry) / (double) (n * (n - 1) / 2);
//                    break;
//                }
//                currOrderDistances.add((int)distance);
//            }
//            distanceMatrix.add(currOrderDistances);
//        }
//        
//        for(ArrayList<Integer> distances: distanceMatrix)
//        {
//            int sum = 0;
//            for(Integer distance: distances)
//            {
//                sum += distance;
//            }
//            Sums.add(sum);
//        }
//        
//        if(Utilities.DEBUG)
//        {
//            if(count == 0) System.out.println("Sums: " + Sums);
//        }
//        
//        while(_hypergraphs.size() > upper_bound)
//        {
//            int minimumIndex = 0;
//            for(Integer sum: Sums)
//            {
//                if(sum < Sums.get(minimumIndex)) minimumIndex = Sums.indexOf(sum);
//            }
//            
//            distanceMatrix.remove(minimumIndex);
//            Sums.remove(minimumIndex);
//            for(ArrayList<Integer> distances: distanceMatrix)
//            {
//                int subtractIndex = distanceMatrix.indexOf(distances);
//                Sums.set(subtractIndex, Sums.get(subtractIndex) - distances.get(minimumIndex));
//                distances.remove(minimumIndex);
//            }
//            
//            _hypergraphs.remove(minimumIndex);
//            System.out.println("Hypergraphs: " + _hypergraphs.size());
//            System.out.println("Sums: " + Sums);
//        }
//    }
    
    //filter while generating source node number orders
    @Override
    public void filter()
    {
        //if the amount of source node orders is above the bound, removes orders based on distance from the first
        while (_hypergraphs.size() > upper_bound)
        {
            int numEdges = _hypergraphs.get(0).length();
            
            for(int index = 0; index < _hypergraphs.size(); index++)
            {
                for(int subIndex = index; subIndex < _hypergraphs.size(); subIndex++)
                {
                    if(Utilities.editDistance(_hypergraphs.get(index), _hypergraphs.get(subIndex)) < (numEdges / 2)) _hypergraphs.remove(subIndex);
                    if(_hypergraphs.size() <= upper_bound) return;
                }
            }
        }
    }
}
