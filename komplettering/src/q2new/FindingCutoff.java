package q2new;

import q5.Stack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.function.Consumer;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * 1. Implement a program to test and determine the best CUTOFF value to Insertionsort for Quicksort and Mergesort
 * respectively. Assume the input consists of uniformly distributed integer values in the interval [0,K].
 * The program should take the number of integers N in the input and the upper limit K as parameters by the
 * command line arguments (argv). Assume K <= 2^32
 *
 * 2. You should be able to show (think graphs) and explain your results.
 * For this experiment you need to use a random number generator and run experiments with different seeds.
 *
 * 2^32 = 4294967296
 */
class Metric {
    public int n;
    public long ms;

    public Metric(int x, long m) {
        n  = x;
        ms = m;
    }
}

// compare for metric class
class MetComp implements Comparator<Metric>{
    // Overriding compare() method of Comparator. for descending order
    public int compare(Metric s1, Metric s2) {
        if (s1.ms < s2.ms)
            return 1;
        else if (s1.ms > s2.ms)
            return -1;
        return 0;
    }
}
public class FindingCutoff {

    private static final int DEFAULT_SEED = Instant.now().getNano();

    public static void main(String[] args) throws IOException {
        //int N = Integer.valueOf(args[0]);
        //int K = Integer.valueOf(args[1]);

        File result = new File("C:\\Users\\mr_fe\\Desktop\\Algo\\Algo2\\nya\\komplettering\\src\\q2new\\result.txt");
        result.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(result));

        Integer[] intArray1 = makeRandomIntArray(1000000,400000, 100);
        PriorityQueue<Metric> pq = new PriorityQueue<Metric>(100, new MetComp());
        Stack<Metric> meta = new Stack<Metric>();
        int l = 0;  // low
        int h = 100; // high
        long baseline = GetSamples(h, intArray1.clone());
        pq.add(new Metric(h, baseline));
        meta.push(new Metric(h, baseline));
        while (h > l) {
            int mid = (h + l) / 2;
            long sample = GetSamples(mid, intArray1);
            pq.add(new Metric(mid, sample));
            meta.push(new Metric(mid, sample));
            if (sample < baseline) {
                baseline = sample;
                h = mid;
            } else {
                l = mid + 1;
            }
        }

        for (Metric m : meta) {
            System.out.println("milliseconds: " + m.ms + " cutoff: " + m.n);
        }
        System.out.println("----");
        while (!pq.isEmpty()) {
            Metric o = pq.poll();
            System.out.println("milliseconds: " + o.ms + " cutoff: " + o.n);
            StringBuilder sb = new StringBuilder();
            sb.append(o.ms).append(", ").append(o.n);
            writer.write(sb.toString());
            writer.write(System.lineSeparator());
        }
    writer.close();
    }
    private static long GetSamples(int c, Integer a[]) {
        long arr[] = new long[100];
        long avg = 0;
        for (int i = 0; i < arr.length; i++) {
            QuickSort q = new QuickSort(c);
            Integer b[] = a.clone();
            Instant timeBefore = Instant.now();
            q.sort(b);
            Instant timeAfter = Instant.now();
            long dur = Duration.between(timeBefore, timeAfter).toMillis();
            avg = ((avg * i) + dur) / (i + 1);
        }
        return avg;
    }

    private static <U, V extends Consumer<U[]>> long getTimeForSortingAlgorithm(U[] array, V algorithm, String algoName) {
        Instant timeBefore = Instant.now();
        algorithm.accept(array);
        Instant timeAfter = Instant.now();
        long duration = Duration.between(timeBefore, timeAfter).toMillis();
        System.out.println(algoName + " took " + duration + " milliseconds");
        return duration;
    }

    // create a random integer array from user input
    private static Integer[] makeRandomIntArray(int count, int max, int seed) {
        Integer[] array = new Integer[count];
        Random random = new Random(seed);
        for (int i = 0; i < count; i++) {
            array[i] = random.nextInt(max + 1);
        }
        return array;
    }
}
