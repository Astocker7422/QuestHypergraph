package utilities;

import java.util.Vector;

public class PrimeGenerator
{
	protected int _currentPrimeIndex;
	protected int _leastUnanalyzed; // This value has not been analyzed

	protected Vector<Integer> _primes;


	public PrimeGenerator()
	{
		_currentPrimeIndex = 0;
		_leastUnanalyzed = 3; // We note that this is an odd value; may increment by 2

		_primes = new Vector<Integer>();
		_primes.add(2);
	}

	//
	// Reset the current prime information (only)
	//
	public void reset()
	{
		_currentPrimeIndex = 0;
	}

	//
	// Acquire the next prime in sequence (generate if needed)
	//
	public int nextPrime()
	{
		// If we need to go get more primes, generate more
		if (_currentPrimeIndex >= _primes.size()) generateNextPrime();

		int prime = _primes.get(_currentPrimeIndex);

		_currentPrimeIndex++;

		return prime;
	}

	//
	// generate the next prime number >= start
	//     (a) analyze from _leastUnanalyzed to start
	//     (b) generate the next prime
	//
	public int generateNextFrom(int start)
	{
		//
		// Analyze all values from _leastUnanalyzed to start
		//
        analyzeTo(start);

		//
		// Generate the next prime
		//	
		generateNextPrime();

		// Set the current marker to the last prime generated
		_currentPrimeIndex = _primes.size() - 1;

		return nextPrime();
	}

	//
	// Analyze and update history from [_leastAnalyzed, end)
	//
	private void analyzeTo(int end)
	{
		// For all values, determine primality and add to the list as needed.
		for (int current = _leastUnanalyzed; current < end; current += 2)
		{
			if (isHistoricallyPrime(current))
			{
				// Add to the history
				_primes.add(current);
			}
		}

		// We've analyzed up to this point successfully
		_leastUnanalyzed = end;
	}

	//
	// generate the next prime (minimally)
	//
	private void generateNextPrime()
	{
		generateNext(_leastUnanalyzed);
	}

	//
	// generate the next prime number beginning at start
	//    * elevate the leastAnalyzed number to at least to start + 1
	//
	private void generateNext(int start)
	{
		boolean found = false;
		int currentValueUnderAnalysis = start % 2 == 0 ? start + 1 : start; // Make sure we're consider odd values

		for ( ; !found; currentValueUnderAnalysis += 2)
		{
			if (isHistoricallyPrime(currentValueUnderAnalysis))
			{
				// Add to the history
				_primes.add(currentValueUnderAnalysis);

				// Indicate success
				found = true;
			}
		}

		// We've analyzed up to this point successfully
		_leastUnanalyzed = currentValueUnderAnalysis;
	}

	//
	// Determine if this number is prime (with history not up to date)
	//   (a) Bring history up to date
	//   (b) Check historical primality
	//
	public boolean isPrime(int num)
	{
		// Quick check
		if (num % 2 == 0) return false;
		
		// Analyze up to the square root
		analyzeTo((int)Math.sqrt(num));

		// Use history to verify primality
		return isHistoricallyPrime(num);
	}

	//
	// Determine if this number is prime (assumes history is up to date)
	//
	private boolean isHistoricallyPrime(int num)
	{
		for (int prime : _primes)
		{
			if (num % prime == 0) return false;

			// Stop analyzing if sqrt(num) has been reached; we have a prime
			if (prime * prime > num) break;
		}

		return true;
	}
}