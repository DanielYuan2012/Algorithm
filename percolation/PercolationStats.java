/****************************************************************************
 *  Author:        Daniel Yuan
 *  Written:       2/3/2014
 *  Last updated:  2/3/2014
 *  
 *  
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats 200 100
 *  Output:  the mean, standard deviation, 
 *           and the 95% confidence interval for the percolation threshold
 *  
 *
 * It takes two command-line arguments N and T, performs T independent computational experiments 
 *(discussed above) on an N-by-N grid, and prints out the mean, standard deviation, 
 *and the 95% confidence interval for the percolation threshold. 
 *Use standard random from our standard libraries to generate random numbers; 
 *use standard statistics to compute the sample mean and standard deviation.
 *
 ****************************************************************************/

public class PercolationStats {
	private int experients_num;
	//private Percolation per;
	private double[] thresholds;
	

	/**
	 * perform T independent experiments on an N-by-N grid 
	 *   
	 */
	
	public PercolationStats(int N, int T) {
		if(N <= 0 || T <= 0){
			throw new IllegalArgumentException("Make sure N>0 and T >0 ");
		}
		experients_num = T;
		thresholds = new double[experients_num];
		for(int i =0; i < experients_num; i++){
			Percolation per = new Percolation(N);
			int openedSites = 0;
			while(!per.percolates()){
				int r = StdRandom.uniform(1, N+1);
				int c = StdRandom.uniform(1, N +1);
				if(!per.isOpen(r, c)){
					per.open(r, c);
					openedSites ++;					
				}				
			}
			double threshold = (double) openedSites /(N * N) ;
			thresholds[i] = threshold;			
		}		
	} 
	
	/**
	 *sample mean of percolation threshold
	 * 
	 */
	public double mean(){
		return StdStats.mean(thresholds);		
	}
	
	
	/**
	 *  sample standard deviation of percolation threshold
	 * 
	 */
	public double stddev(){
		return StdStats.stddev(thresholds);		
	}
	
	/**
	 * low endpoint of 95% confidence interval
	 * 
	 */	
	public double confidenceLo(){
		return mean() - (1.96 * stddev())/Math.sqrt(experients_num);
	}
	
	/**
	 * high endpoint of 95% confidence interval
	 * 
	 */
	public double confidenceHi(){
		return mean() + (1.96 * stddev())/Math.sqrt(experients_num);
	}
	
	
	/**
	 * test program
	 * 
	 */
	public static void main(String[] args){
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(N,T);
		
		String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
		StdOut.println("mean             =" + ps.mean());
		StdOut.println("stddev           =" + ps.stddev());
		StdOut.println("95% confidence interval = "+ confidence);
		
	}	

}
