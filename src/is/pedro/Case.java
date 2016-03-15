package is.pedro;

/**
 * Created by andri on 13/03/16.
 */
public class Case {
    boolean[] symptoms = null;
    String diagnosis = "";

    public Case(boolean[] symptoms, String diagnosis) {
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for(int i = 0; i < symptoms.length; i++) {
            stb.append(symptoms[i] + " ");
        }
        stb.append(diagnosis);
        return stb.toString();
    }
}
