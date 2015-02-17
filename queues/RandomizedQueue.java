/****************************************************************************
 *  Author:        Daniel Yuan
 *  Written:       2/15/2014
 *  Last updated:  2/15/2014
 *  
 *  
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue < tobe.txt
 *  A randomized queue is similar to a stack or queue, 
 *  except that the item removed is chosen uniformly at 
 *  random from items in the data structure. Create a 
 *  generic data type RandomizedQueue that implements the following API:
 *  
 *  The queue implementation must support each randomized queue operation 
 *  (besides creating an iterator) in constant amortized time and use space 
 *  proportional to the number of items currently in the queue. That is, any 
 *  sequence of M randomized queue operations (starting from an empty queue) 
 *  should take at most cM steps in the worst case, for some constant c. 
 *  Additionally, your iterator implementation must support operations next() 
 *  and hasNext() in constant worst-case time; and construction in linear time; 
 *  you may use a linear amount of extra memory per iterator.
 *  
 *  
 *  
 *
 ****************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] a = (Item[]) new Object[1];
	private int N;	
	
// construct an empty randomized queue
   public RandomizedQueue(){
	   a[0] = null;
	   N = 0;	   
   }
   
// is the queue empty?  
   public boolean isEmpty(){
	   return N == 0;
   } 
   
// return the number of items on the queue
   public int size(){
	   return N;
   }
   
   private void resize(int max){
	   Item[] temp = (Item[]) new Object[max];
	   for(int i=0; i < N; i++ ){
		   temp[i] = a[i];
	   }
	   
	   a = temp;	   
   }
 
// add the item
   public void enqueue(Item item){
	   checkitem(item);
	   if(N == a.length) resize(2*a.length);
	   a[N++] = item;
   }
   
// remove and return a random item
   public Item dequeue(){
	   if(isEmpty()){
		   throw new NoSuchElementException("the client attempts to remove an item from an empty deque");
	   }
	   RandomExchange(a, N);
	   Item item = a[--N];
	   a[N]=null;
	   if(N >0 && N==a.length/4) resize(a.length/2);
	   return item;
   }
   
// return (but do not remove) a random item
   public Item sample(){
	   if(isEmpty()){
		   throw new NoSuchElementException("the client attempts to sample an item from an empty deque");
	   }
	   int r = StdRandom.uniform(0,N);
	   return a[r];
   }

// return an independent iterator over items in random order   
   public Iterator<Item> iterator(){
	   return new RandomArrayIterator();
	   
   }
   
   
   /*
   private class RandomArrayIterator implements Iterator<Item>{
	   private int i = N;
	   public boolean hasNext(){
		   return i > 0;
	   }
	   public Item next(){
		   
		   if(!hasNext()){
				throw new NoSuchElementException("there are no more items to return");
			}
		   RandomExchange(i);
		   return a[--i];
	   }
	   
	   public void remove(){
		   throw new UnsupportedOperationException("the remove() method in the iterator is not supported");
	   }	   
   }
   
   */
   
   private class RandomArrayIterator implements Iterator<Item>{
	   private Item[] b= (Item[]) new Object[a.length];
	   private int iN = N;
	   public boolean hasNext(){
		   return iN > 0;
	   }
	   
	   public RandomArrayIterator(){
		   for(int i=0; i < a.length; i++){
			   b[i] = a[i];
		   }
			   
		   
	   }
	   public Item next(){
		   
		   if(!hasNext()){
				throw new NoSuchElementException("there are no more items to return");
			}
		   RandomExchange(b,iN);
		   Item item = b[--iN];
		   b[iN] = null;
		   return item;
	   }
	   
	   public void remove(){
		   throw new UnsupportedOperationException("the remove() method in the iterator is not supported");
	   }	   
   }
   
   
   private void checkitem(Item item){
	   if(item == null){
		   throw new NullPointerException("the client attempts to add a null item");
	   }
   }
   
   private void RandomExchange(Item[] a,int i){
	   
	   int r = StdRandom.uniform(0, i);
	   Item temp = a[r];
	   a[r] = a[i-1];
	   a[i-1] = temp;
	   
   }   
   // unit testing  
   public static void main(String[] args){
	     RandomizedQueue<String> rq;
		 rq = new RandomizedQueue<String>();
		
		while(!StdIn.isEmpty())
		{
			String item = StdIn.readString();
			if(!item.equals("-"))
				rq.enqueue(item);
			else if(!rq.isEmpty()) StdOut.print(rq.dequeue()+ " ");			
		}
		
		StdOut.println("(" + rq.size() + " left on stack)");		
	}   
}