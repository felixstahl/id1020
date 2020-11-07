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
 */
public class Spelling {

    private static final String COMMON_ERRORS = "C:\\Users\\mr_fe\\Desktop\\Algo\\Algo2\\nya\\komplettering\\src\\q4\\words.txt";
    private static final int MISSPELLINGS_COUNT = 4285;
    private static String[] misspellings = new String[MISSPELLINGS_COUNT];
    private static int misspellingsCount = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        if(args.length == 1 && args[0].equals("test")){ // run test cases
            runTest();
        } else {

            File words = new File(COMMON_ERRORS);

            System.out.println("Enter the name of the file with errors (including .txt)");
            String fileName = "C:\\Users\\mr_fe\\Desktop\\Algo\\Algo2\\nya\\komplettering\\src\\q4\\" + scan.nextLine();
            File file = new File(fileName);

            try {
                Util.readWordsFromFile(words, (word) -> {
                    if (isGoodWord(word)) {
                        misspellings[misspellingsCount++] = word;
                    }
                });
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                //String[] lineWords;
                File fixedFile = new File(makeFixedFileName(fileName));     // create a new file for corrected words
                fixedFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(fixedFile));
                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    String[] characters = line.split("");
                    for (int i = 0; i < characters.length; i++) {

                        if (Util.isLetterOrDigit(characters[i])) {
                            sb.append(characters[i]);
                            try {
                                if (!Util.isLetterOrDigit(characters[i + 1])) {
                                    writeCorrection(writer, sb);
                                }
                            } catch (IndexOutOfBoundsException ignored) {
                                writeCorrection(writer, sb);
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

    // returns a string containing old file name + '_corrected' to it. corrected words will be stored in this file
    private static String makeFixedFileName(String fileName) {
        String originalName = fileName.replaceAll("\\.txt", "");
        return originalName + "_corrected.txt";
    }

    private static void runTest() {
        testMakeFixedFileName();
        testGetOptionalCorrection();
        testTrimMisspelling();
    }

    private static void testMakeFixedFileName() {
        String before = "before.txt";
        String expected = "before_corrected.txt";
        String actual = makeFixedFileName(before);

        Util.assertTrue(expected.equals(actual), "incorrect file name");
    }

    private static void testGetOptionalCorrection() {
        File commonErrors = new File(COMMON_ERRORS);
        try {
            Util.readWordsFromFile(commonErrors, (word) -> {
                if (isGoodWord(word)) {
                    misspellings[misspellingsCount++] = word;
                }
                //System.out.println(misspellingsCount);
            });
        } catch (IOException e) {

        }
        String misspelledWord = "currenly";
        String corrected = "currently";
        Optional<String> actual = getOptionalCorrection(misspelledWord);

        Util.assertTrue(actual.isPresent(), "word correction should have been present");
        Util.assertTrue(actual.get().equals(corrected), actual + " should have equaled " + corrected);

        String capitalisedMisspelledWord = "CuRrEnLy";
        actual = getOptionalCorrection(capitalisedMisspelledWord);

        Util.assertTrue(actual.isPresent(), "word correction should have been present");
        Util.assertTrue(actual.get().equals(corrected), actual + " should have equaled " + corrected);
    }

    private static void testTrimMisspelling() {
        String before = "elephant->tests";
        String expected = "elephant";
        String actual = trimMisspelling(before);

        Util.assertTrue(actual.equals(expected), "misspelled string should be trimmed");
    }
}
