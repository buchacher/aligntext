import java.util.ArrayList;

/**
* AlignText class.
* @version 1
*/
public class AlignText {
    // #constantsagainstmagicnumbers

    // Bubble: Correct for longest word defining bubble width
    static final int CORR_WORD = 4;

    // Bubble: Correct for line_length defining bubble width
    static final int CORR_LEN = 3;

    /**
    * The main method is divided into two sections:
    * In section 1 it reads from a file into the program, and
    * in section 2 it formats and outputs the text.
    * @param args Command line arguments
    */
    public static void main(String[] args) {
        try {
            // Catch invalid line_length (must be positve non-zero integer)
            if (Integer.parseInt(args[1]) < 1) {
                System.out.println("usage: java AlignText file_name line_length [align_mode]");
                System.exit(0);
            }

            // Section 1
            // Read text from file into program

            String[] paragraphs = FileUtil.readFile(args[0]);
            ArrayList<String> listOfLines = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i <= paragraphs.length - 1; i++) {
                // Split paragraph into an array of its words, where the elements are all words,
                // in order and without whitespace.
                String[] arrayOfWords = paragraphs[i].split(" ");

                // For align_mode: B, line_length refers not to the length of the lines of text,
                // but to the width of the speech bubble.
                // Accordingly, B will require shorter lines of text to account for the leading
                // "| " and trailing " |", which amount to 4 text characters.
                // So correctForBubble (4) is substracted from line_length,
                // represented as Integer.parseInt(args[1]).
                if (args.length > 2 && args[2].equals("B")) {
                    for (int j = 0; j <= arrayOfWords.length - 1; j++) {
                        if ((sb + arrayOfWords[j]).length() <= Integer.parseInt(args[1]) - CORR_WORD) {
                            sb.append(arrayOfWords[j]).append(" ");
                        }
                        else {
                            listOfLines.add(sb.toString().strip());
                            sb = new StringBuilder();
                            sb.append(arrayOfWords[j]).append(" ");
                        }
                    }
                }
                else {
                    for (int j = 0; j <= arrayOfWords.length - 1; j++) {
                        if ((sb + arrayOfWords[j]).length() <= Integer.parseInt(args[1])) {
                            sb.append(arrayOfWords[j]).append(" ");
                        }
                        else {
                            listOfLines.add(sb.toString().strip());
                            sb = new StringBuilder();
                            sb.append(arrayOfWords[j]).append(" ");
                        }
                    }
                }
                // Catch last line of paragraph
                listOfLines.add(sb.toString().strip());
                // Clear sb of last line of paragraph
                sb = new StringBuilder();
            }

            // Re: Speech Bubble
            // An empty string will be found at beginning of listOfLines when line_length < 11
            listOfLines.remove("");

            // Section 2
            // Format according to align_mode and output text

            // align_mode: R - Right-align text from the file
            if (args.length > 2 && args[2].equals("R")) {
                for (int i = 0; i <= listOfLines.size() - 1; i++) {
                    int lenThisline = listOfLines.get(i).length();
                    int noOfSpaces = Integer.parseInt(args[1]) - lenThisline;
                    for (int j = 0; j <= noOfSpaces - 1; j++) {
                        System.out.print(" ");
                    }
                    System.out.println(listOfLines.get(i));
                }
            }

            // align_mode: C - Centre-align text from the file
            else if (args.length > 2 && args[2].equals("C")) {
                for (int i = 0; i <= listOfLines.size() - 1; i++) {
                    int lenThisline = listOfLines.get(i).length();
                    // Divide by 2.0, round up to nearest whole number
                    // Must be 2.0 not 2, because latter will round down
                    int noOfSpaces = (int) Math.ceil((Integer.parseInt(args[1]) - lenThisline) / 2.0);
                    for (int j = 0; j <= noOfSpaces - 1; j++) {
                        System.out.print(" ");
                    }
                    System.out.println(listOfLines.get(i));
                }
            }

            // align_mode: B - Put text in a speech bubble
            else if (args.length > 2 && args[2].equals("B")) {
                // Find and get length of longest word (equals longest line)
                int lenLongestLine = listOfLines.get(0).length();
                for (int g = 0; g <= listOfLines.size() - 1; g++) {
                    if (listOfLines.get(g).length() > lenLongestLine) {
                        lenLongestLine = listOfLines.get(g).length();
                    }
                }

                // Print top border
                System.out.print(" ");
                if (lenLongestLine > Integer.parseInt(args[1]) - CORR_WORD) {
                    for (int h = 0; h <= lenLongestLine + 1; h++) {
                        System.out.print("_");
                    }
                }
                else {
                    for (int h = 0; h <= Integer.parseInt(args[1]) - CORR_LEN; h++) {
                        System.out.print("_");
                    }
                }
                System.out.println(" ");

                // Print left border, text and right border
                int lenThisline = 0;
                int noOfSpaces = 0;

                for (int i = 0; i <= listOfLines.size() - 1; i++) {
                    System.out.print("| ");
                    System.out.print(listOfLines.get(i));
                    lenThisline = listOfLines.get(i).length();
                    noOfSpaces = 0;
                    if (lenLongestLine > Integer.parseInt(args[1]) - CORR_WORD) {
                        noOfSpaces = lenLongestLine + 1 - lenThisline;
                        for (int j = 0; j <= noOfSpaces - 1; j++) {
                            System.out.print(" ");
                        }
                    }
                    else {
                        noOfSpaces = Integer.parseInt(args[1]) - CORR_LEN - lenThisline;
                        for (int j = 0; j <= noOfSpaces - 1; j++) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println("|");
                }

                // Print bottom border
                System.out.print(" ");
                if (lenLongestLine > Integer.parseInt(args[1]) - CORR_WORD) {
                    for (int k = 0; k <= lenLongestLine + 1; k++) {
                        System.out.print("-");
                    }
                }
                else {
                    for (int k = 0; k <= Integer.parseInt(args[1]) - CORR_LEN; k++) {
                        System.out.print("-");
                    }
                }
                System.out.println(" ");

                // Print tail
                System.out.println("        \\");
                System.out.println("         \\");
            }

            // align_mode: L - Left-align text from the file
            // Default when align_mode is omitted
            else {
                for (int i = 0; i <= listOfLines.size() - 1; i++) {
                    System.out.println(listOfLines.get(i));
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("usage: java AlignText file_name line_length [align_mode]");
        } catch (NumberFormatException e) {
            System.out.println("usage: java AlignText file_name line_length [align_mode]");
        }
    }
}
