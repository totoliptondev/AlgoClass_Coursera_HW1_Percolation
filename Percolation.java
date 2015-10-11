
public class Percolation {
   private int n;
   private int top;
   private WeightedQuickUnionUF uf;
   private boolean[] site;
   private boolean[] connectedToBottom;
   private boolean canPercolate;

   public Percolation(int N)   // create N-by-N grid, with all sites blocked
   {
     if (N <= 0) throw new IllegalArgumentException();
     n = N;
     uf = new WeightedQuickUnionUF(n*n+1);
     // the 1 additional are virtual cells, top
     site = new boolean[n*n];
     connectedToBottom = new boolean[n*n+1];
     top = n*n;
     canPercolate = false;
   }

   private void isValidIndex(int i, int j)
   {
     if (i < 1 || i > n || j < 1 || j > n)
     {
       throw new IndexOutOfBoundsException();
     }
   }
   // open site (row i, column j) if it is not open already
   public void open(int i, int j)
   {
     isValidIndex(i, j);
     if (isOpen(i, j)) return;

     int currentSite = convert2dTo1dCoord(i, j);
     site[currentSite] = true;
     // union top row with the top virtual cell
     if (i == 1)
     {
       connect(currentSite, top);
     }

     if (i > 1 && isOpen(i-1, j))
     {
       connect(currentSite, convert2dTo1dCoord(i-1, j));
     }
     if (j > 1 && isOpen(i, j-1))
     {
       connect(currentSite, convert2dTo1dCoord(i, j-1));
     }
     if (j < n && isOpen(i, j+1))
     {
       connect(currentSite, convert2dTo1dCoord(i, j+1));
     }
     if (i < n && isOpen(i+1, j))
     {
       connect(currentSite, convert2dTo1dCoord(i+1, j));
     }
     // if connected to bottom, mark the root to be connect to the bottom.
     // this is basically a way to use the boolean array connectedToBottom
     // to replace a second board.
     int root = uf.find(currentSite);
     if (i == n)
     {
       connectedToBottom[root] = true;
     }
     // isFull indicates connection to the top.
     if (isFull(i, j) && connectedToBottom[root])
     {
       canPercolate = true;
     }

   }

   private void connect(int id1, int id2)
   {
     int rootp = uf.find(id1);
     int rootq = uf.find(id2);

     if (connectedToBottom[rootp] || connectedToBottom[rootq]){
       connectedToBottom[rootp] = true;
       connectedToBottom[rootq] = true;
     }
     uf.union(id1, id2);
   }

   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
     isValidIndex(i, j);
     return site[convert2dTo1dCoord(i, j)];
   }

   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
     isValidIndex(i, j);
     if (!isOpen(i, j))                                return false;
     if (uf.connected(convert2dTo1dCoord(i, j), top))  return true;

     return false;
   }

   public boolean percolates()             // does the system percolate?
   {
     return canPercolate;
   }

   // helper function to convert 2d index to 1d.
   private int convert2dTo1dCoord(int i, int j)
   {
     return n*(i - 1) + j - 1;
   }

   public static void main(String[] args)   // test client (optional)
   {

   }
}
