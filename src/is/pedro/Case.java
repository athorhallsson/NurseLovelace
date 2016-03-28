package is.pedro;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by andri on 13/03/16.
 */
public class Case {
    // Use major if dealing with current case
    HashSet<Integer> hasMajorSx = new HashSet<>();
    HashSet<Integer> hasNotMajorSx = new HashSet<>();
    HashSet<Integer> hasNotMinorSx = new HashSet<>();
    HashSet<Integer> hasMinorSx = new HashSet<>();

    int age;
    char gender;
    String diagnosis = "";
    Pain pain = new Pain();

    public Case() {}

    public Case(HashSet<Integer> hasMajorSx, HashSet<Integer> hasNotMajorSx, Pain pain, int age, char gender, String diagnosis) {
        this.hasMajorSx = hasMajorSx;
        this.hasNotMajorSx = hasNotMajorSx;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.pain = pain;
    }

    public Case(HashSet<Integer> hasMajorSx, HashSet<Integer> hasNotMajorSx, HashSet<Integer> hasMinorSx, HashSet<Integer> hasNotMinorSx, Pain pain, int age, char gender, String diagnosis) {
        this.hasMajorSx = hasMajorSx;
        this.hasNotMajorSx = hasNotMajorSx;
        this.hasMinorSx = hasMinorSx;
        this.hasNotMinorSx = hasNotMinorSx;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.pain = pain;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setAge(Integer age) { this.age = age; }

    public void setGender(Character gender) { this.gender = gender; }

    public void addToHas(Integer symptom) { hasMajorSx.add(symptom); }

    public void addToHasNot (Integer symptom) { hasNotMajorSx.add(symptom); }

    public String caseToString(ArrayList<String> symptoms, ArrayList<String> painSx, ArrayList<String> pos) {
        StringBuilder stb = new StringBuilder();
        stb.append(this.age + " " + this.gender + " ");
        for (Integer majorSx : hasMajorSx) {
            stb.append(symptoms.get(majorSx) + " ");
        }
        for (Integer minorSx : hasMinorSx) {
            stb.append(symptoms.get(minorSx) + " ");
        }
        for (Integer pSx : pain.painInfo) {
            stb.append(painSx.get(pSx) + " ");
        }
        for (Integer p : pain.position) {
            stb.append(pos.get(p) + " " + p + " ");
        }
        for (Integer rp : pain.rPosition) {
            stb.append(pos.get(rp) + " ");
        }
        stb.append(diagnosis);
        return stb.toString();
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append(this.age + " " + this.gender + " ");
        for(Integer sx : hasMajorSx) {
            stb.append(sx + " ");
        }
        stb.append(diagnosis);
        return stb.toString();
    }
}
