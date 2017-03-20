package questgeneration.constraints;

import java.util.ArrayList;
import digraph.Linearization;
import utilities.Utilities;

public class LinearizationFilter<T> implements Filter
{
    double upper_bound;
    public ArrayList<Linearization<T>> _linearizations;
    public enum _function {EDIT_DISTANCE, HAMMING, KENDALL_TAU};
    private final double[] upperBounds = {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                                            1000, 500, 350, 200, 150,
                                            100, 90, 80, 70, 60,
                                            55, 50, 45, 40, 35,
                                            30, 25, 20, 18, 16,      //21 nodes is second from left in this row (sample Skyrim quest length)
                                            14, 12, 10, 8, 6, 4};
    
    public LinearizationFilter(ArrayList<Linearization<T>> linearizationList, int numNodes)
    {
        _linearizations = linearizationList;
        //upper_bound = linearizationList.size() / 2;
        upper_bound = upperBounds[numNodes];
    }
    
//    @Override
//    public void filter()
//    {
//        int count = 0;
//        
//        ArrayList<ArrayList<Integer>> distanceMatrix = new ArrayList<ArrayList<Integer>>();
//        ArrayList<Integer> Sums = new ArrayList<Integer>();
//        
//        for(Linearization currOrder: _linearizations)
//        {
//            ArrayList<Integer> currOrderDistances = new ArrayList<Integer>();
//            String currOrderString = currOrder.toASCIIString();
//            int n = currOrderString.length();
//            
//            for(Linearization otherOrder: _linearizations)
//            {
//                String otherOrderString = otherOrder.toASCIIString();
//                double distance = 0;
//                // CHOOSE WHICH DISTANCE FUNCTION TO USE
//                _function FUNC_TYPE = _function.HAMMING;
//                switch(FUNC_TYPE)
//                {
//                    case EDIT_DISTANCE:
//                        distance = Utilities.editDistance(currOrderString, otherOrderString) / n;
//                    break;
//                    case HAMMING:
//                        distance = Utilities.hamming(currOrderString, otherOrderString);
//                    break;
//                    case KENDALL_TAU:
//                        distance = Utilities.kendallTau(currOrder.toArray(), otherOrder.toArray()) / (double) (n * (n - 1) / 2);
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
////        if(Utilities.DEBUG)
////        {
////            if(count == 0) System.out.println("Sums: " + Sums);
////        }
//        
//        while(_linearizations.size() > upper_bound)
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
//            _linearizations.remove(minimumIndex);
////            System.out.println("Linearizations: " + _linearizations.size());
////            System.out.println("Sums: " + Sums);
//        }
//    }
    
    @Override
    public void filter()
    {
        if(_linearizations.isEmpty()) return;
        
        //if the amount of linearizations is above the bound, removes least unique linearization
        while (_linearizations.size() == upper_bound)
        {
            int leastUniqueIndex = 0;
            for(int index = 0; index < _linearizations.size(); index++)
            {
                for(int subIndex = index; subIndex < _linearizations.size(); subIndex++)
                {
                    //if an element with an edit distance less than the edit distance of the previous least unique element,
                    //it will be assigned as the new least unique element
                    if(Utilities.editDistance(_linearizations.get(index).toASCIIString(), 
                            _linearizations.get(subIndex).toASCIIString()) < 
                            Utilities.editDistance(_linearizations.get(index).toASCIIString(), 
                                    _linearizations.get(leastUniqueIndex).toASCIIString())) leastUniqueIndex = subIndex;
                }
            }
            _linearizations.remove(leastUniqueIndex);
        }
    }
}
