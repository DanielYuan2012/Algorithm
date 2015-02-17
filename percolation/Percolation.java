/****************************************************************************
 *  Author:        Daniel Yuan
 *  Written:       2/3/2014
 *  Last updated:  2/3/2014
 *  
 *  
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation < input.txt
 *  Output: if the system percolates
 *  
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size N of the percolation system.
 *    - Creates an N-by-N grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it will check if the system can percolate.
 *  
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ****************************************************************************/

public class Percolation {
	
	private boolean[][] sites_grid; // define N-by-N grid of sites,0-open,1-blocked
	private int top=0;              // define virtual top and bottom
	private int bottom;
	private int size;
	
	// define 2 WeightedQuickUnion object, one for remove the backwash issue
	private WeightedQuickUnionUF wqu,auxwqu; 
	
	/**
	 * initialize the percloation data object,
	 * create N-by-N grid, with all sites blocked. 
	 *   
	 */
	
	public Percolation(int N){
		if(N <= 0){
			throw new IllegalArgumentException("Make sure N>0  ");			
			}
		size = N;
		bottom = size * size + 1;
		wqu = new WeightedQuickUnionUF(size * size +2);
		auxwqu = new WeightedQuickUnionUF(size * size + 1);
		sites_grid = new boolean[size][size];    //the default value is false
	}
	
	/**
	 * open site (row i, column j) if it is not open already
	 * if the site(i,j) is in 1st row,union it with top
	 * if the site(i,j) is in last row,union it with bottom
	 * if the adjacent site of site(i,j) is in open state, then 
	 * union it with site(i,j), 	 *   
	 */	
	public void open(int i, int j){
		checkRange(i,j);
		if(isOpen(i,j)) return;
		sites_grid[i-1][j-1] = true;
		
		
		if(i==1){
			wqu.union(to1DIndex(i,j), top);
			auxwqu.union(to1DIndex(i,j), top);
		}
		if(i==size){
			wqu.union(to1DIndex(i,j), bottom);
		}
		
		if(j > 1 && isOpen(i, j - 1)){			
			wqu.union(to1DIndex(i,j), to1DIndex(i,j-1));
			auxwqu.union(to1DIndex(i,j), to1DIndex(i,j-1));
		}
		if(j < size && isOpen(i, j+1)){			
			wqu.union(to1DIndex(i,j), to1DIndex(i,j+1));
			auxwqu.union(to1DIndex(i,j), to1DIndex(i,j+1));
		}
		
		if(i > 1 && isOpen(i-1,j)){
			wqu.union(to1DIndex(i,j), to1DIndex(i-1,j));
			auxwqu.union(to1DIndex(i,j), to1DIndex(i-1,j));
		}
		
		if(i < size && isOpen(i+1,j)){
			wqu.union(to1DIndex(i,j), to1DIndex(i+1,j));
			auxwqu.union(to1DIndex(i,j), to1DIndex(i+1,j));
		}		
		
	}
	
	/**
	 *is site (row i, column j) open?
	 */
	public boolean isOpen(int i, int j){
		checkRange(i,j);
		return sites_grid[i-1][j-1];
	}
	
	/**
	 *  is site (row i, column j) full?
	 * 
	 */
	public boolean isFull(int i, int j){
		checkRange(i,j);
		return wqu.connected(top, to1DIndex(i,j)) && auxwqu.connected(top, to1DIndex(i,j));		
		
	}	
	
	/**
	 * 
	 * does the system percolate?
	 */
	 
	public boolean percolates(){
		return wqu.connected(top, bottom);		
	}
	
	/**
	 *  check the range of row and column
	 */
	private void checkRange(int i, int j){
	       if (i<=0||j<=0||i>size||j>size){
	    	   throw new IndexOutOfBoundsException("row or column index are out of bounds");
	       }
	     }
	
	/**
	 *  Convert 2D index to 1D index
	 */
	private int to1DIndex(int row, int column){
		checkRange(row,column);
		return size * (row-1) + column;
		
	}
	
	/**
	 *  Test program
	 */
	public static void main(String[] args){
		int N = StdIn.readInt();
		Percolation per = new Percolation(N);
		while(!StdIn.isEmpty()){
			int i = StdIn.readInt();
			int j = StdIn.readInt();
			per.open(i, j);			
		}
		if (per.percolates()){
			StdOut.println( "The system percolates.");			
		}
		else{
			StdOut.println( "The system doen not percolate.");
			
		}
		
	}

}
