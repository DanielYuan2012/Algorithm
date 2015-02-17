/****************************************************************************
 *  Author:        Daniel Yuan
 *  Written:       2/15/2014
 *  Last updated:  2/15/2014
 *  
 *  
 *  Compilation:  javac Subset.java
 *  Execution:    java Subset
 *  
 *  
 *  Write a client program Subset.java that takes a command-line 
 *  integer k; reads in a sequence of N strings from standard input 
 *  using StdIn.readString(); and prints out exactly k of them, uniformly 
 *  at random. Each item from the sequence can be printed out at most once. 
 *  You may assume that 0 <= k <= N, where N is the number of string on standard input.
 *   
 *       % echo A B C D E F G H I | java Subset 3       % echo AA BB BB BB BB BB CC CC | java Subset 8
 *       C                                              BB
 *       G                                              AA
 *       A                                              BB
 *                                                      CC
 *       % echo A B C D E F G H I | java Subset 3       BB
 *       E                                              BB
 *       F                                              CC
 *       G                                              BB
 *  
 *
 ****************************************************************************/

public class Subset {
	
   public static void main(String[] args){
	   
	     RandomizedQueue<String> rq;
		 rq = new RandomizedQueue<String>();
		 
		 int k = Integer.parseInt(args[0]);
		 if(k < 0){
				throw new IllegalArgumentException("Make sure k >= 0 ");			
				}
		
		while(!StdIn.isEmpty())
		{
			String item = StdIn.readString();			
			rq.enqueue(item);					
		}
		
		if(k>rq.size()){
			throw new IllegalArgumentException("Make sure k <= the queue size");
			
		}
		
		
		while(k>0){
			StdOut.println(rq.dequeue());
			k--;			
		}		
	   
   }
   
}