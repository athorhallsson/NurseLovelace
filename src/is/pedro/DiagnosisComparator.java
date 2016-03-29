package is.pedro;

import java.util.Comparator;

/**
 * Created by asgeirthor on 3/29/16.
 */
public class DiagnosisComparator implements Comparator<Diagnosis> {
    @Override
    public int compare (Diagnosis d1, Diagnosis d2) {
        if (d1.getSimilarityIndex() > d2.getSimilarityIndex()) {
            return  1;
        }
        if (d1.getSimilarityIndex() < d2.getSimilarityIndex()) {
            return -1;
        }
        return 0;
    }
}
