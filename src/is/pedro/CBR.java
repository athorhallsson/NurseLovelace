package is.pedro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by andri on 13/03/16.
 */
public class CBR {
    private ArrayList<Case> baseCases = null;
    private Repo repo;
    private Case currCase;

    int majorRating = 5;
    int minorRating = 2;
    int painRating = 3;
    int posRating = 5;
    int rPosRating = 3;

    // private int simularityLimit = 0;

    public CBR(Case currCase, Repo repo) {
        this.repo = repo;
        this.baseCases = repo.getBaseCases(currCase);
        this.currCase = currCase;
    }

    public ArrayList<Case> findDiagnosis() {
        ArrayList<Case> dDxList = new ArrayList<>();

        for (Case oldCase : baseCases) {
            double difference = compareCases(oldCase);

            if (difference >= 3) {
                dDxList.add(oldCase);
            }
        }

        for (Case ddx : dDxList) {
            //System.out.println(ddx.caseToString(this.repo.symptoms, this.repo.painSx, this.repo.painPos));
        }
        return dDxList;
    }

    private double compareCases(Case oldCase) {
        HashSet major = this.currCase.hasMajorSx;
        HashSet minor = this.currCase.hasMinorSx;
        HashSet pain = this.currCase.pain.painInfo;
        HashSet pos = this.currCase.pain.position;
        HashSet rPos = this.currCase.pain.rPosition;

        major.retainAll(oldCase.hasMajorSx);
        minor.retainAll(oldCase.hasMinorSx);
        pain.retainAll(oldCase.pain.painInfo);
        pos.retainAll(oldCase.pain.position);
        rPos.retainAll(oldCase.pain.rPosition);

        double similarityIndex = 0.0;

        if (this.currCase.hasMajorSx.size() != 0) {
            similarityIndex += majorRating * ((double)major.size() / (double)this.currCase.hasMajorSx.size());
        }
        if (this.currCase.hasMinorSx.size() != 0) {
            similarityIndex += minorRating * ((double)minor.size() / (double)this.currCase.hasMinorSx.size());

        }
        if (this.currCase.pain.painInfo.size() != 0) {
            similarityIndex += painRating * ((double)pain.size() / (double)this.currCase.pain.painInfo.size());

        }
        if (this.currCase.pain.position.size() != 0) {
            similarityIndex += posRating * ((double)pos.size() / (double)this.currCase.pain.position.size());

        }
        if (this.currCase.pain.rPosition.size() != 0) {
            similarityIndex += rPosRating * ((double)rPos.size() / (double)this.currCase.pain.rPosition.size());

        }
        // Add age and gender rating....

        return similarityIndex;
    }
}
