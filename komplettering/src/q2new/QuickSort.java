package q2new;


import java.util.Scanner;

/**
 *  The QuickX class provides static methods for sorting an array
 *  using an optimized version of quicksort (using Hoare's 2-way partitioning
 *  algorithm, median-of-3 to choose the partitioning element, and cutoff
 *  to insertion sort).

 */
public class QuickSort {

    // cutoff to insertion sort, must be >= 1
    private static final int CUTOFF = 8;

    // This class should not be instantiated.
    private QuickSort() { }

    //Rearranges the array in ascending order, using the natural order.
    public static void sort(Comparable[] a) {
        // StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;

        // cutoff to insertion sort (Insertion.sort() uses half-open intervals)
        int n = hi - lo + 1;
        if (n <= CUTOFF) {
            //Insertion.sort(a, lo, hi + 1);
            return;
        }

        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        int m = median3(a, lo, lo + n/2, hi);
        exch(a, m, lo);

        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];

        // a[lo] is unique largest element
        while (less(a[++i], v)) {
            if (i == hi) { exch(a, lo, hi); return hi; }
        }

        // a[lo] is unique smallest element
        while (less(v, a[--j])) {
            if (j == lo + 1) return lo;
        }

        // the main loop
        while (i < j) {
            exch(a, i, j);
            while (less(a[++i], v)) ;
            while (less(v, a[--j])) ;
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    // return the index of the median element among a[i], a[j], and a[k]
    private static int median3(Comparable[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
                (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
                (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }

    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
           // StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] a = new String[scan.nextInt()];

        for(int i = 0; i < a.length; i++) {
            a[i] = scan.nextLine();
        }
        QuickSort.sort(a);
        assert isSorted(a);
        show(a);
    }

}