package is.pedro;

/**
 * Created by andri on 19/03/16.
 */
public class QuestionNode {
    public Integer number = 0;
    public Integer rating = 0;
    public boolean asked = false;

    public QuestionNode(Integer symptom) {
        this.number = symptom;
    }
}
