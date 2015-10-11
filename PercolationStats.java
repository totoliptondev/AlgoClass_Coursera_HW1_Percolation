//import java.util.Arrays;
public class PercolationStats {
   private double[] percolationThresholdArr;
   private int simulationRuns;
   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
     if (N < 0 || T <= 0) throw new IllegalArgumentException();
     simulationRuns = T;
     int randi;
     int randj;
     int percolationThresholdCount;
     percolationThresholdArr = new double[T];
     for (int i = 0; i < T; i++)
     {
         Percolation perc = new Percolation(N);
         percolationThresholdCount = 0;
         while (true)
         {
           randi = StdRandom.uniform(N)+1;
           randj = StdRandom.uniform(N)+1;
           if (perc.isOpen(randi, randj))
              continue;

           perc.open(randi, randj);
           percolationThresholdCount++;
           if (perc.percolates())
           {
               percolationThresholdArr[i] = (double) percolationThresholdCount/ (N*N);
               break;
           }
         }


     }

   }
   public double mean()                      // sample mean of percolation threshold
   {
     int T = simulationRuns;
     double sum = 0.0;
     for (int i = 0; i < T; i++)
     {
       sum += percolationThresholdArr[i];
     }
     return sum / (double) T;

   }
   public double stddev()                    // sample standard deviation of percolation threshold
   {
     int T = percolationThresholdArr.length;
     double u = mean();
     double ss = 0.0;
     for (int i = 0; i < T; i++)
     {
       double tmp =  percolationThresholdArr[i] - u;
       ss += tmp*tmp;
     }
     return Math.sqrt(ss / (T-1));

   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
     double u = mean();
     double sigma = stddev();
     int T = percolationThresholdArr.length;
     return u - 1.96*sigma/Math.sqrt(T);
   }
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
     double u = mean();
     double sigma = stddev();
     int T = percolationThresholdArr.length;
     return u + 1.96*sigma/Math.sqrt(T);
   }

   public static void main(String[] args)    // test client (described below)
   {

     PercolationStats test = new PercolationStats(200, 1000);
     System.out.println(test.mean());
     System.out.println(test.stddev());
     System.out.println(test.confidenceLo());
     System.out.println(test.confidenceHi());


   }

}
