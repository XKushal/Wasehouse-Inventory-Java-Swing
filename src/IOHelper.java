
/**
 *
 * @author edorphy
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class IOHelper {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static final int EXIT = 0;
    public static final int HELP = 20;

    public static int readInt() {
        int returnValue = 0;
        Scanner inputReader = new Scanner(System.in);

        try {
            returnValue = inputReader.nextInt();
        } catch (InputMismatchException ime) {
            //ime.printStackTrace();
            // netbeans doesn't like this... hum
        }

        return returnValue;
    }

    public static int readInt(String prompt) {
        Println(prompt);
        return readInt();
    }

    public static String readString() {
        String input;

        Scanner inputReader = new Scanner(System.in);
        input = inputReader.nextLine();

        return input;
    }

    public static boolean yesOrNo(String str) {
        Println(str);
        String input;
        Scanner inputReader = new Scanner(System.in);

        while (true) {
            input = inputReader.nextLine().toUpperCase();
            switch (input) {
                case "YES":
                case "Y":
                    return true;
                case "NO":
                case "N":
                    return false;
                default:
                    System.out
                            .print("Invalid Input! Options are [Yes] or [No].\nPlease try again: ");
                    break;
            }
        }
    }

    public static String GetString(String prompt) {
        Println(prompt);
        String input;

        Scanner inputReader = new Scanner(System.in);
        input = inputReader.nextLine();

        return input;
    }

    public static void Println(String str) {
        System.out.println(str);
    }

    public static String getToken(String prompt) {
        do {
            try {
                Println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    public static int GetCmd() {
        do {
            try {
                int value = readInt("Enter command:" + HELP + " for help");
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    public static Calendar getDate(String prompt) {
        do {
            try {
                Calendar date = new GregorianCalendar();
                String item = getToken(prompt);
                DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
                date.setTime(df.parse(item));
                return date;
            } catch (Exception fe) {
                System.out.println("Please input a date as mm/dd/yy");
            }
        } while (true);
    }
}
