package is.pedro;

import java.security.PrivateKey;
import java.util.Comparator;

/**
 * Created by asgeirthor on 3/29/16.
 */
public class Diagnosis {

    private String diagnosisName;
    private double similarityIndex;

    public Diagnosis (String name, double similarityIndex) {
        this.diagnosisName = name;
        this.similarityIndex = similarityIndex;
    }

    public double getSimilarityIndex() { return this.similarityIndex; }
    public String getDiagnosisName() { return this.diagnosisName; }
}
