package is.pedro;

import java.util.Scanner;

/**
 * Created by andri on 13/03/16.
 */
public class ConsoleUI {

    Scanner scanner = null;

    public ConsoleUI() {
        try {
            scanner = new Scanner(System.in);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }
    public Case getNewCase() {
        boolean[] arr = new boolean[Constants.numberOfSymptoms];
        for (int i = 0; i < arr.length; i++) {
            System.out.print("Do you have " + Constants.symtoms[i] + "? ");
            String answer = scanner.next();

            if (answer.equals("Y") || answer.equals("y") || answer.equals("Yes") || answer.equals("yes")) {
                arr[i] = true;
            } else {
                arr[i] = false;
            }
        }
        return new Case(arr, "Unknown");
    }

    public boolean confirmDiagnosis(String diagnosis) {
        System.out.print("I think it's " + diagnosis + "! Is that correct? ");
        String answer = scanner.next();

        if (answer.equals("Y") || answer.equals("y") || answer.equals("Yes") || answer.equals("yes")) {
            return true;
        }
        return false;
    }

}
