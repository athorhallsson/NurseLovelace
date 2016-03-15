package is.pedro;

import java.util.ArrayList;

/**
 * Created by andri on 13/03/16.
 */
public class CBR {
    ArrayList<Case> prevCases = null;

    // private int simularityLimit = 0;

    public CBR(ArrayList<Case> cases) {
        this.prevCases = cases;
    }

    public String findDiagnosis(Case c) {
        Case closestMatch = null;
        int smallestDifference = Integer.MAX_VALUE;

        for (Case oldCase : prevCases) {
            int difference = compareCases(oldCase, c);

            if (difference < smallestDifference) {
                closestMatch = oldCase;
                smallestDifference = difference;
            }

        }
        return closestMatch.diagnosis;
    }

    private int compareCases(Case c1, Case c2) {
        int difference = 0;
        for (int i = 0; i < c1.symptoms.length; i++) {
            if (c1.symptoms[i] != c2.symptoms[i]) {
                difference += 1;
            }
        }
        return difference;
    }
}
