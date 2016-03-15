package is.pedro;

import java.sql.Connection;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Repo repo = new Repo();
        ConsoleUI ui = new ConsoleUI();

        ArrayList<Case> pCases = repo.getPreviousCases();

        CBR cbr = new CBR(pCases);

        Case currentCase = ui.getNewCase();

        String diagnosis = cbr.findDiagnosis(currentCase);

        if (ui.confirmDiagnosis(diagnosis)) {
            currentCase.setDiagnosis(diagnosis);
            repo.addCase(currentCase);
            System.out.println("Thank you, I'll remember that.");
        }
        else {
            System.out.println("Sorry, I suck at my job.");
        }

        System.out.println();
        System.out.println("Here are my previous cases:");
        for(Case c : pCases) {
            System.out.println(c);
        }

    }
}
