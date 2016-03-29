package is.pedro;

import java.security.PrivateKey;
import java.util.Comparator;

/**
 * Created by asgeirthor on 3/29/16.
 */
public class Diagnosis {

    private Case c;
    private double similarityIndex;

    public Diagnosis (Case oldCase, double similarityIndex) {
        this.c = oldCase;
        this.similarityIndex = similarityIndex;
    }

    public double getSimilarityIndex() { return this.similarityIndex; }
    public String getDiagnosisName() { return this.c.diagnosis; }
    public Case getCase() { return this.c; }
}
