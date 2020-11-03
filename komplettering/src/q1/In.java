package q1;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 *              README
 *       Author: Felix Ståhl
 *       Lab4 - Question 1 & 2
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 *  This class provides methods for reading inputs
 *
 */

public final class In {
    // assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";
    // assume language = English, country = US for consistency with System.out.
    private static final Locale LOCALE = Locale.US;
    // initialized in constructors
    private Scanner scanner;

    //Initializes an input stream from a filename
    public In(String name) {
        if (name == null) throw new IllegalArgumentException("argument is null");
        try {
            File file = new File(name); // first try to read file from local file system
            if (file.exists()) {
                // for consistency with StdIn, wrap with BufferedInputStream instead of use
                // file as argument to Scanner
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + name, ioe);
        }
    }

    //Returns true if input stream is empty (except possibly whitespace).
    public boolean isEmpty() {
        return !scanner.hasNext();
    }

    //Returns true if this input stream has a next line. This method is functionally equivalent to hasNextChar().
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    //Reads and returns the next line in this input stream.
    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        }
        catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    //Reads the next token from this input stream, parses it as a int, and returns the int.
    public int readInt() {
        try {
            return scanner.nextInt();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from the input stream, "
                                           + "but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read an 'int' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }
}
