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

    double majorRating = 5.0;
    double minorRating = 2.0;
    double painRating = 3.0;
    double posRating = 5.0;
    double rPosRating = 3.0;

    private int similarityLimit = 3;

    public CBR(Case currCase, Repo repo) {
        this.repo = repo;
        this.baseCases = repo.getBaseCases(currCase);
        this.currCase = currCase;
    }

    public ArrayList<String> findDiagnosis() {
        Comparator<Diagnosis> compare = new DiagnosisComparator();
        PriorityQueue<Diagnosis> ddxQueue = new PriorityQueue<>(10, compare);

        for (Case oldCase : baseCases) {
            double similarityIndex = compareCases(oldCase);

            if (similarityIndex >= similarityLimit) {
                ddxQueue.add(new Diagnosis(oldCase.diagnosis, similarityIndex));
            }
        }

        ArrayList<String> ddxStrings = new ArrayList<>();

        for (Diagnosis ddx : ddxQueue) {
            if (!ddxStrings.contains(ddx.getDiagnosisName())){
                ddxStrings.add(ddx.getDiagnosisName());
            }
        }
        return ddxStrings;
    }

    private double compareCases(Case oldCase) {
        HashSet major = this.currCase.hasMajorSx;
        // All symptoms are regarded major until doctor has confirmed diagnosis
        HashSet minor = this.currCase.hasMajorSx;
        HashSet pain = this.currCase.pain.painInfo;
        HashSet pos = this.currCase.pain.position;
        HashSet rPos = this.currCase.pain.rPosition;

        System.out.println(oldCase.caseToString(repo.symptoms, repo.painSx, repo.painPos));
        System.out.println(this.currCase.caseToString(repo.symptoms, repo.painSx, repo.painPos));

        major.retainAll(oldCase.hasMajorSx);
        minor.retainAll(oldCase.hasMinorSx);
        pain.retainAll(oldCase.pain.painInfo);
        pos.retainAll(oldCase.pain.position);
        rPos.retainAll(oldCase.pain.rPosition);

        System.out.println(oldCase.caseToString(repo.symptoms, repo.painSx, repo.painPos));
        System.out.println(this.currCase.caseToString(repo.symptoms, repo.painSx, repo.painPos));

        double similarityIndex = 0.0;

        if (this.currCase.hasMajorSx.size() != 0) {
            similarityIndex += majorRating * (((double)major.size()) / (double)this.currCase.hasMajorSx.size());
            System.out.println(similarityIndex);
        }
        if (this.currCase.hasMajorSx.size() != 0) {
            similarityIndex += minorRating * (((double)minor.size()) / (double)this.currCase.hasMajorSx.size());
            System.out.println(similarityIndex);

        }
        if (this.currCase.pain.painInfo.size() != 0) {
            similarityIndex += painRating * (((double)pain.size()) / (double)this.currCase.pain.painInfo.size());
            System.out.println(similarityIndex);

        }
        if (this.currCase.pain.position.size() != 0) {
            similarityIndex += posRating * (((double)pos.size()) / (double)this.currCase.pain.position.size());
            System.out.println(similarityIndex);

        }
        if (this.currCase.pain.rPosition.size() != 0) {
            similarityIndex += rPosRating * (((double)rPos.size()) / (double)this.currCase.pain.rPosition.size());
        }

        //System.out.println(oldCase.caseToString(repo.symptoms, repo.painSx, repo.painPos) + " " + similarityIndex);
        // Add age and gender rating....

        return similarityIndex;
    }
}
