package questgeneration.constraints;

import java.util.ArrayList;
import utilities.Utilities;

public class HypergraphFilter<T> implements Filter
{
    double upper_bound;
    public ArrayList<String> _hypergraphs;
    public enum _function {EDIT_DISTANCE, HAMMING, KENDALL_TAU};
    private final double[] upperBounds = {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                                            1000, 500, 350, 200, 150,
                                            100, 90, 80, 70, 60,
                                            55, 50, 45, 40, 35,
                                            30, 100, 20, 18, 16,      //21 nodes is second from left in this row
                                            14, 12, 10, 8, 6, 4};
    
    public HypergraphFilter(ArrayList<String> hypergraphList, int numNodes)
    {
        _hypergraphs = hypergraphList;
        //upper_bound = hypergraphList.size() / 2;
        upper_bound = upperBounds[numNodes];
    }
    
    @Override
    public void filter()
    {
        int count = 0;
        
        ArrayList<ArrayList<Integer>> distanceMatrix = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> Sums = new ArrayList<Integer>();
        
        for(String currOrder: _hypergraphs)
        {
            ArrayList<Integer> currOrderDistances = new ArrayList<Integer>();
            int n = currOrder.length();
            
            for(String otherOrder: _hypergraphs)
            {
                double distance = 0;
                // CHOOSE WHICH DISTANCE FUNCTION TO USE
                LinearizationFilter._function FUNC_TYPE = LinearizationFilter._function.HAMMING;
                switch(FUNC_TYPE)
                {
                    case EDIT_DISTANCE:
                        distance = Utilities.editDistance(currOrder, otherOrder) / n;
                    break;
                    case HAMMING:
                        distance = Utilities.hamming(currOrder, otherOrder);
                    break;
                    case KENDALL_TAU:
                        int[] currOrderAry = new int[currOrder.length()];
                        int[] otherOrderAry = new int[otherOrder.length()];
                        for(int index = 0; index < currOrder.length(); index++)
                        {
                            currOrderAry[index] = Character.getNumericValue(currOrder.charAt(index));
                            otherOrderAry[index] = Character.getNumericValue(otherOrder.charAt(index));
                        }
                        distance = Utilities.kendallTau(currOrderAry, otherOrderAry) / (double) (n * (n - 1) / 2);
                    break;
                }
                currOrderDistances.add((int)distance);
            }
            distanceMatrix.add(currOrderDistances);
        }
        
        for(ArrayList<Integer> distances: distanceMatrix)
        {
            int sum = 0;
            for(Integer distance: distances)
            {
                sum += distance;
            }
            Sums.add(sum);
        }
        
        if(Utilities.DEBUG)
        {
            if(count == 0) System.out.println("Sums: " + Sums);
        }
        
        while(_hypergraphs.size() > upper_bound)
        {
            int minimumIndex = 0;
            for(Integer sum: Sums)
            {
                if(sum < Sums.get(minimumIndex)) minimumIndex = Sums.indexOf(sum);
            }
            
            distanceMatrix.remove(minimumIndex);
            Sums.remove(minimumIndex);
            for(ArrayList<Integer> distances: distanceMatrix)
            {
                int subtractIndex = distanceMatrix.indexOf(distances);
                Sums.set(subtractIndex, Sums.get(subtractIndex) - distances.get(minimumIndex));
                distances.remove(minimumIndex);
            }
            
            _hypergraphs.remove(minimumIndex);
            System.out.println("Hypergraphs: " + _hypergraphs.size());
            System.out.println("Sums: " + Sums);
        }
    }
}
