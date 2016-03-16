package is.pedro;

import java.util.HashSet;
import java.util.InputMismatchException;

/**
 * Created by andri on 13/03/16.
 */
public class Case {
    HashSet<Integer> hasSx = new HashSet<>();
    HashSet<Integer> hasNotSx = new HashSet<>();
    int age;
    char gender;
    String diagnosis = "";

    public Case(HashSet<Integer> hasSx, HashSet<Integer> hasNotSx, int age, char gender, String diagnosis) {
        this.hasSx = hasSx;
        this.hasNotSx = hasNotSx;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setAge(Integer age) { this.age = age; }

    public void setGender(Character gender) { this.gender = gender; }

    public void addToHas(Integer symptom) { hasSx.add(symptom); }

    public void addToHasNot (Integer symptom) { hasNotSx.add(symptom); }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append(this.age + " " + this.gender + " ");
        for(Integer sx : hasSx) {
            stb.append(sx + " ");
        }
        stb.append(diagnosis);
        return stb.toString();
    }
}
