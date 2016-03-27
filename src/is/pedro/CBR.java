package is.pedro;

import java.util.ArrayList;

/**
 * Created by andri on 13/03/16.
 */
public class CBR {
    private ArrayList<Case> baseCases = null;
    private Repo repo;

    // private int simularityLimit = 0;

    public CBR(Case currCase, Repo repo) {
        this.repo = repo;
        this.baseCases = repo.getBaseCases(currCase);
    }

    public String findDiagnosis(Case c) {
        Case closestMatch = null;
        int smallestDifference = Integer.MAX_VALUE;

        for (Case oldCase : baseCases) {
            int difference = compareCases(c, oldCase);

            if (difference < smallestDifference) {
                closestMatch = oldCase;
                smallestDifference = difference;
            }

        }
        return closestMatch.diagnosis;
    }

    private int compareCases(Case c1, Case c2) {
        int difference = 0;
        for (Integer sx : c1.hasMajorSx) {
            if (c2.hasNotMajorSx.contains(sx)) {
                difference += 5;
            }
            else if (!c2.hasMajorSx.contains(sx)) {
                difference += 1;
            }
        }
        for (Integer sx : c1.hasNotMajorSx) {
            if (c2.hasMajorSx.contains(sx)) {
                difference += 5;
            }
            else if (!c2.hasNotMajorSx.contains(sx)) {
                difference += 1;
            }
        }
        return difference;
    }
}
