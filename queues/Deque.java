/****************************************************************************
 *  Author:        Daniel Yuan
 *  Written:       2/15/2014
 *  Last updated:  2/15/2014
 *  
 *  
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque < tobe.txt
 *  Output:  simulate a normal queue
 *  
 *  A double-ended queue or deque (pronounced "deck") is a 
 *  generalization of a stack and a queue that supports adding 
 *  and removing items from either the front or the back of the 
 *  data structure.
 *  The implementation must support each deque operation in constant
 *  worst-case time and use space proportional to the number of items
 *  currently in the deque. Additionally, your iterator implementation 
 *  must support each operation (including construction) in constant 
 *  worst-case time.
 *  
 *
 ****************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
	
	private DNode first;
	private DNode last;
	private int N;
	
	private class DNode{
		Item item;
		DNode previous;
		DNode next;		
	}
	
	
    // construct an empty deque
	public Deque(){
		first = null;
		last = null;
		N = 0;		
	}  
	
	// is the deque empty?
   public boolean isEmpty(){	   
	   return (first == null)||(last==null);
   }
   
 // return the number of items on the deque
   public int size(){	   
	   return N;	   
   }  
   
 // add the item to the front
   public void addFirst(Item item){
	   
	   checkitem(item);	   
	   if(isEmpty()){
		   first = new DNode();
		   first.item = item;
		   first.previous=null;
		   first.next = null;
		   last = first;
		   
	   }
	   else{	   
	   DNode oldfirst = first;
	   first = new DNode();
	   first.item = item;
	   first.previous = null;
	   first.next = oldfirst;
	   oldfirst.previous = first;	   
	   }
	   N++;
   }
   
// add the item to the end
   public void addLast(Item item){
	   
	   checkitem(item);	   
	   if(isEmpty()){
		   
		   last = new DNode();
		   last.item = item;
		   last.previous = null;
		   last.next = null;
		   first = last;
	   }
	   else{

		   DNode oldlast = last;
		   last = new DNode();
		   last.item = item;
		   last.next = null;
		   last.previous = oldlast;
		   oldlast.next = last;
	   }
	   N++;	   
   } 
   
// remove and return the item from the front
   public Item removeFirst(){
	   
	   if(isEmpty()){
		   throw new NoSuchElementException("the client attempts to remove an item from an empty deque");
	   }
	   Item item = first.item;
	   first = first.next;
	   if(isEmpty()){
		   last = null;
	   }
	   else{
		   first.previous = null;
	   }
	   N--;
	   return item;	   
   }
   
// remove and return the item from the end
   public Item removeLast(){
	   
	   if(isEmpty()){
		   throw new NoSuchElementException("the client attempts to remove an item from an empty deque");
	   }
	   
	   Item item = last.item;
	   last = last.previous;
	   if(isEmpty()){
		   first = null;		   
	   }
	   else{
		   last.next = null;		   
	   } 
	   N--;
	   return item;	   
   }  
   
// return an iterator over items in order from front to end
   public Iterator<Item> iterator() {
	   
	   return new ListIterator();
	   
   }
   
   private class ListIterator implements Iterator<Item>{
		
		private DNode current = first;
		public boolean hasNext(){
			
			return current != null;
		}
		public void remove(){
			throw new UnsupportedOperationException("the remove() method in the iterator is not supported");
		}
		
		public Item next(){
			
			if(!hasNext()){
				throw new NoSuchElementException("there are no more items to return");
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}	
   
   
   private void checkitem(Item item){
	   if(item == null){
		   throw new NullPointerException("the client attempts to add a null item");
	   }
	   
   }
   
// unit testing

   public static void main(String[] args){	   

	    Deque<String> d;
		d = new Deque<String>();
		
		while(!StdIn.isEmpty())
		{
			String item = StdIn.readString();
			if(!item.equals("-"))
				d.addLast(item);
			else if(!d.isEmpty()) StdOut.print(d.removeFirst()+ " ");			
		}
		
		StdOut.println("(" + d.size() + " left on stack)");		
	}	
	   
	   
}
   
   
   
