package utilities;

import java.util.ArrayList;
import java.util.Random;

public class Utilities 
{
    public static final boolean DEBUG = true;
    public static final boolean QUEST = true;
    
    public static ArrayList<Integer> genSubset(Random gen, int size, int low, int high)
    {
        ArrayList<Integer> ary = new ArrayList<>();
        int count = 0;
        
        while(count < size)
        {
            int num = gen.nextInt(high - low) + low;
            
            if(!ary.contains(num))
            {
                ary.add(num);
                count++;
            }
        }
        return ary;
    }
    
    // Is smaller \subseteq larger
    public static boolean subset(ArrayList<Integer> larger, ArrayList<Integer> smaller)
    {
        for (Integer o : smaller)
        {
            if (!larger.contains(o)) return false;
        }

        return true;
    }

    // Is set1 \equals set2
    public static boolean equalSets(ArrayList<Integer> set1, ArrayList<Integer> set2)
    {
        if (set1.size() != set2.size()) return false;

        return Utilities.subset(set1, set2);
    }
    
    public static boolean addUnique(ArrayList<Integer> list, Integer obj)
    {
        if (list.contains(obj)) return false;

        list.add(obj);

        return true;
    }
    
    public static void addUniqueList(ArrayList<Integer> list, ArrayList<Integer> objList)
    {
        for (Integer o : objList)
        {
            addUnique(list, o);
        }
    }
    
    //
    // Keep n and sz small since (n-1)^sz is large
    //
    public static ArrayList<String> construct(int n, int sz)
    {
	ArrayList<String> strings = new ArrayList<String>();

	//
	// Prime the pump
	//
        if(QUEST)
        {
            Integer start = 1;
            strings.add(start.toString());
        }
        else
        {
            for (int count = 1; count <= n; count++)
            {
                strings.add(Integer.toString(count));
            }
        }
		
	//
	// General construction
        //
        int index = 2;
        for (int count = 1; count < sz; count++)
        {
            strings = construct(strings, n, index);
            index++;
        }
		
	return strings;
    }
	
    //
    // Add one row, switch value every X occurrence
    //
    public static ArrayList<String> construct(ArrayList<String> original, int n, int index)
    {
	ArrayList<String> newer = new ArrayList<String>();
		
        for (int subCount = 1; subCount <= n; subCount++)
        {
            ArrayList<String> copy = new ArrayList<String>(original);
            
            for (String string : copy)
            {
                if(subCount > index) continue;
    		newer.add(string + Integer.toString(subCount));
            }
        }
        return newer;
    }
    
    public static int editDistance(String word1, String word2)
    {
	int len1 = word1.length();
	int len2 = word2.length();
 
	// len1+1, len2+1, because finally return dp[len1][len2]
	int[][] dp = new int[len1 + 1][len2 + 1];
 
	for (int i = 0; i <= len1; i++) {
		dp[i][0] = i;
	}
 
	for (int j = 0; j <= len2; j++) {
		dp[0][j] = j;
	}
 
	//iterate though, and check last char
	for (int i = 0; i < len1; i++) {
		char c1 = word1.charAt(i);
		for (int j = 0; j < len2; j++) {
			char c2 = word2.charAt(j);
 
			//if last two chars equal
			if (c1 == c2) {
				//update dp value for +1 length
				dp[i + 1][j + 1] = dp[i][j];
			} else {
				int replace = dp[i][j] + 1;
				int insert = dp[i][j + 1] + 1;
				int delete = dp[i + 1][j] + 1;
 
				int min = replace > insert ? insert : replace;
				min = delete > min ? min : delete;
				dp[i + 1][j + 1] = min;
			}
		}
	}
 
	return dp[len1][len2];
    }
    
    public static Integer hamming(String left, String right)
    {
        if (left == null || right == null) {
        throw new IllegalArgumentException("Strings must not be null");
        }

        if (left.length() != right.length()) {
            throw new IllegalArgumentException("Strings must have the same length");
        }

        int distance = 0;

        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                distance++;
            }
        }

        return distance;
    }
    
    public static long kendallTau(int[] a, int[] b)
    {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int n = a.length;

        int[] ainv = new int[n];
        for (int i = 0; i < n; i++)
            ainv[a[i]] = i;

        Integer[] bnew = new Integer[n];
        for (int i = 0; i < n; i++)
            bnew[i] = ainv[b[i]];

        return (int) Inversions.count(bnew);
    }
}