package questgeneration.constraints;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ArrayList;
import digraph.Linearization;
import utilities.Utilities;

public class LinearizationFilter<T> implements Filter
{
    int upper_bound;
    public ArrayList<Linearization<T>> _linearizations;
    public enum _function {EDIT_DISTANCE, HAMMING, KENDALL_TAU}
    
    public LinearizationFilter(ArrayList<Linearization<T>> linearizationList, int numNodes)
    {
        _linearizations = linearizationList;
        upper_bound = numNodes / 2;
    }
    
    @Override
    public void filter()
    {
        int count = 0;
        
        ArrayList<ArrayList<Integer>> distanceMatrix = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> Sums = new ArrayList<Integer>();
        
        for(Linearization currOrder: _linearizations)
        {
            ArrayList<Integer> currOrderDistances = new ArrayList<Integer>();
            String currOrderString = currOrder.toASCIIString();
            int n = currOrderString.length();
            
            for(Linearization otherOrder: _linearizations)
            {
                String otherOrderString = otherOrder.toASCIIString();
                int distance = 0;
                // CHOOSE WHICH DISTANCE FUNCTION TO USE
                // DISTANCE CODE IN PROGRESS
                _function FUNC_TYPE = _function.KENDALL_TAU;
                switch(FUNC_TYPE)
                {
                    case EDIT_DISTANCE:
                        distance = Utilities.editDistance(currOrderString, otherOrderString) / n;
                    break;
                    case HAMMING:
                        distance = Utilities.hamming(currOrderString, otherOrderString);
                    break;
                    case KENDALL_TAU:
                        distance = Utilities.kendallTau(currOrder.toArray(), otherOrder.toArray()) / (n * (n - 1) / 2);
                    break;
                }
                currOrderDistances.add(distance);
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
        
        while(_linearizations.size() > upper_bound)
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
            
            _linearizations.remove(minimumIndex);
            System.out.println("Linearizations: " + _linearizations.size());
            System.out.println("Sums: " + Sums);
        }
    }
}
