package is.pedro;

import sun.tools.tree.ThisExpression;

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
    private ArrayList<Case> ddxList = new ArrayList<Case>();

    double majorRating = 5.0;
    double minorRating = 2.0;
    double painRating = 3.0;
    double posRating = 5.0;
    double rPosRating = 3.0;

    private int similarityLimit = 4;

    public CBR(Repo repo) {
        this.repo = repo;
    }

    public ArrayList<String> findDiagnosis(Case currCase) {
        this.baseCases = repo.getBaseCases(currCase);
        this.currCase = currCase;

        Comparator<Diagnosis> compare = new DiagnosisComparator();
        PriorityQueue<Diagnosis> ddxQueue = new PriorityQueue<>(10, compare);

        for (Case oldCase : baseCases) {
            double similarityIndex = compareCases(oldCase);

            if (similarityIndex >= similarityLimit) {
                ddxQueue.add(new Diagnosis(oldCase, similarityIndex));
            }
        }

        ArrayList<String> ddxDiagnoses = new ArrayList<String>();

        for (Diagnosis ddx : ddxQueue) {
            if (!ddxDiagnoses.contains(ddx.getDiagnosisName())){
                ddxList.add(ddx.getCase());
                ddxDiagnoses.add(ddx.getDiagnosisName());
            }
        }
        return ddxDiagnoses;
    }

    public Case reviseCase(Case currentCase) {
        Case similiarCase = null;
        for (Case c : ddxList) {
            if (currentCase.diagnosis.equals(c.diagnosis)) {
                similiarCase = c;
            }
        }
        if (similiarCase == null) {
            return currentCase;
        }

        Case revisedCase = new Case();
        revisedCase.setDiagnosis(currentCase.diagnosis);
        revisedCase.setGender(currentCase.gender);
        revisedCase.setAge(currentCase.age);
        revisedCase.pain = currentCase.pain;
        for (Integer symptom : currentCase.hasMajorSx) {
            if (similiarCase.hasMajorSx.contains(symptom)) {
                revisedCase.hasMajorSx.add(symptom);
            }
            else {
                revisedCase.hasMinorSx.add(symptom);
            }
        }
        for (Integer symptom : currentCase.hasNotMajorSx) {
            if (similiarCase.hasNotMajorSx.contains(symptom)) {
                revisedCase.hasNotMajorSx.add(symptom);
            }
            else {
                revisedCase.hasNotMinorSx.add(symptom);
            }
        }
        return revisedCase;
    }

    private double compareCases(Case oldCase) {
        HashSet major = (HashSet)this.currCase.hasMajorSx.clone();
        // Remember that all sx in currCase are major until doctor stratifies
        HashSet minor = (HashSet)this.currCase.hasMajorSx.clone();
        HashSet pain = (HashSet)this.currCase.pain.painInfo.clone();
        HashSet pos = (HashSet)this.currCase.pain.position.clone();
        HashSet rPos = (HashSet)this.currCase.pain.rPosition.clone();

        major.retainAll(oldCase.hasMajorSx);
        minor.retainAll(oldCase.hasMinorSx);
        pain.retainAll(oldCase.pain.painInfo);
        pos.retainAll(oldCase.pain.position);
        rPos.retainAll(oldCase.pain.rPosition);

        double similarityIndex = 0.0;

        if (this.currCase.hasMajorSx.size() != 0) {
            similarityIndex += majorRating * (((double)major.size()) / (double)this.currCase.hasMajorSx.size());
        }
        if (this.currCase.hasMajorSx.size() != 0) {
            similarityIndex += minorRating * (((double)minor.size()) / (double)this.currCase.hasMajorSx.size());
        }
        if (this.currCase.pain.painInfo.size() != 0) {
            similarityIndex += painRating * (((double)pain.size()) / (double)this.currCase.pain.painInfo.size());
        }
        if (this.currCase.pain.position.size() != 0) {
            similarityIndex += posRating * (((double)pos.size()) / (double)this.currCase.pain.position.size());
        }
        if (this.currCase.pain.rPosition.size() != 0) {
            similarityIndex += rPosRating * (((double)rPos.size()) / (double)this.currCase.pain.rPosition.size());
        }
        // Add age and gender rating....

        return similarityIndex;
    }
}
