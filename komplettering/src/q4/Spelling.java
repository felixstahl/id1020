package q4;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;
/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - Question 1
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 * Implementation of a double linked (circular if wanted) list with a sentinel node
 * The execution time complexity should be near linear compared to the time it takes to read the input file.
 * That is you need to show that your algorithm meets this constraint.
 *
 * To read from command line, dont open CMD, just
 *
 * comment out parts of:
 * line 26, like this      String fileName; //= "C:\\Users\\mr_...
 *
 * and UNcomment
 *  line 29, like this     fileName = scan.nextLine();
 */
public class Spelling {

    private static final int MISSPELLINGS_COUNT = 4285;
    private static String[] misspellings = new String[MISSPELLINGS_COUNT];

    private static int misspellingsCount = 0;

    public static void main(String[] args) {
        File words = new File("C:\\Users\\mr_fe\\Desktop\\Algo\\Algo2\\nya\\komplettering\\src\\q4\\words.txt");

        Scanner scan = new Scanner(System.in);
        String fileName; //= "C:\\Users\\mr_fe\\Desktop\\Algo\\Algo2\\nya\\komplettering\\src\\q4\\misspelled.txt";
        fileName = scan.nextLine();
        File file = new File(fileName);

        try {
            Util.readWordsFromFile(words, (word) -> {
                if (isGoodWord(word)) {
                    misspellings[misspellingsCount++] = word;
                }
            });
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            String[] lineWords;
            File fixedFile = new File(makeFixedFileName(fileName));
            fixedFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(fixedFile));
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                String[] characters = line.split("");
                for (int i = 0; i < characters.length; i++) {
                    if (Util.isLetterOrDigit(characters[i])) {
                        builder.append(characters[i]);
                        try {
                            if (!Util.isLetterOrDigit(characters[i + 1])) {
                                writeCorrection(writer, builder);
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            writeCorrection(writer, builder);
                        }
                    } else {
                        writer.write(characters[i]);
                    }
                }
                writer.write(System.lineSeparator());
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeCorrection(BufferedWriter writer, StringBuilder builder) throws IOException {
        writer.write(getCorrection(builder.toString()));
        builder.setLength(0);
    }

    private static String getCorrection(String s) {
        return getOptionalCorrection(s).orElse(s);

    }

    private static boolean isGoodWord(String word) {
        return word.contains("-");
    }

    private static Optional<String> getOptionalCorrection(String word) {
        String maybeCorrection = null;
        String misspelling;
        int low = 0, high = misspellings.length;
        while (low <= high) {
            int mid = (low + high) / 2;
            misspelling = trimMisspelling(misspellings[mid]);
            if (less(misspelling, word)) {
                low = mid + 1;
            } else if (less(word, misspelling)) {
                high = mid - 1;
            } else if (word.compareToIgnoreCase(misspelling) == 0) {
                maybeCorrection = getCorrectionFromIndex(mid);
                break;
            }
        }
        return Optional.ofNullable(maybeCorrection);
    }

    private static String trimMisspelling(String misspelling) {
        int arrowIndex = misspelling.indexOf("-");
        return misspelling.substring(0, arrowIndex);
    }

    private static String getCorrectionFromIndex(int index) {
        int arrowIndex = misspellings[index].indexOf("-");
        return misspellings[index].substring(arrowIndex + 2);
    }

    private static boolean less(String misspellingWithArrow, String word) {
        return misspellingWithArrow.compareToIgnoreCase(word) < 0;
    }

    private static String makeFixedFileName(String fileName) {
        String originalPrefix = fileName.replaceAll("\\.txt", "");
        return originalPrefix + "_corrected.txt";
    }

    /*private static void runTestSuite() {
        testMakeFixedFileName();
        testGetOptionalCorrection();
        testTrimMisspelling();
    }

    private static void testMakeFixedFileName() {
        String before = "before.txt",
                expected = "before_fixed.txt",
                actual = makeFixedFileName(before);

        Util.assertTrue(expected.equals(actual), "makeFixedFileName");
    }

    private static void testGetOptionalCorrection() {
        File misspellingsFile = new File(MISSPELLINGS_FILE_NAME);
        try {
            Util.readWordsFromFile(misspellingsFile, (word) -> {
                if (isGoodWord(word)) {
                    misspellings[misspellingsCount++] = word;
                }
            });
        } catch (IOException e) {

        }
        String misspeltWord = "currenly",
                corrected = "currently";
        Optional<String> actual = getOptionalCorrection(misspeltWord);

        Util.assertTrue(actual.isPresent(), "word correction should have been present");
        Util.assertTrue(actual.get().equals(corrected), actual + " should have equaled " + corrected);

        String capitalisedMisspeltWord = "CuRrEnLy";
        actual = getOptionalCorrection(capitalisedMisspeltWord);

        Util.assertTrue(actual.isPresent(), "word correction should have been present");
        Util.assertTrue(actual.get().equals(corrected), actual + " should have equaled " + corrected);
    }

    private static void testTrimMisspelling() {
        String before = "derp->herp",
                expected = "derp",
                actual = trimMisspelling(before);

        Util.assertTrue(actual.equals(expected), "misspelling string should be trimmed");
    }*/
}
