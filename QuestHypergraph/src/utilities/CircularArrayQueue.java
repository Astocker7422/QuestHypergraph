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

package utilities;

public class CircularArrayQueue<T>
{	
	private T[] queueArray;
	private int front;
	private int rear;
	private int n;
	
	@SuppressWarnings("unchecked")
    public CircularArrayQueue()
	{
		n = 5;
		queueArray = (T[])new Object[n];
		front = rear = 0;
	}
	
	public void enqueue(T element)
    {
		int s = size();
		if (s == n - 1) resize();

		queueArray[rear++] = (T)element;
		if(rear == n) rear = 0;
	}
	
	@SuppressWarnings("unchecked")
    private void resize()
	{
		n *= 2;
		int s = size();
		int lastIndex = s + 1;

		T[]array = (T[])new Object[n];
		
		int i = 0;
		while(s > 0)
		{
			s--;
			array[i++] = queueArray[front++];
			if(front == lastIndex){
				front = 0;
			}
		}
		rear = i++;
		front = 0;
		queueArray = array;
	}
	
	public T dequeue()
    {
		if (isEmpty()) return null;

		T x = queueArray[front];
		
		queueArray[front++] = null;
		
		if (front == n) front = 0;

		return x;
	}

	public T first() { return queueArray[front]; }
	public boolean isEmpty() { return front == rear; }
	public int size() { return (n - front + rear) % n; }
	
	public String toString()
	{
		String retString = "";

		for (int i = 0; i < n; i++ )
		{
			retString += "Index " + i + ": " +  queueArray[i] + "\n";
		}
		
		return retString;
	}
}
