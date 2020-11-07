package q5;
import java.io.*;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - All questions
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 * Implementation of a utility class to, for example, read from files and test classes

 * consumer is an interface which implements so that the operations will be performed on the consumer that is sent to
 * the function.
 *
 */
public class Util {
    public static <U extends Comparable> void swap(U[] list, int i, int j) {
        U x = list[i];
        list[i] = list[j];
        list[j] = x;
    }

    public static <U extends Consumer<String>> void readLinesFromFile(File file, U consumer) throws IOException {
        readFromFile(file, consumer::accept);
    }

    private static void readFromFile(File file, Consumer<String> doSomethingWithLine) {
        String line;
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(" +");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((line = reader.readLine()) != null) {
                doSomethingWithLine.accept(line );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void assertTrue(boolean isTrue, String message) {
        System.out.println(isTrue ? "Pass" : "Fail: " + message);
    }

}