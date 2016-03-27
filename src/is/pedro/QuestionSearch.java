package is.pedro;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by andri on 19/03/16.
 */
public class QuestionSearch {
    private Repo repo;

    private ArrayList<QuestionNode> questionArray = new ArrayList<QuestionNode>();

    public QuestionSearch(Repo repo) {
        this.repo = repo;
    }

    public void initSearch(Case currCase) {
        ArrayList<Case> pCases = repo.getInitCases(currCase);

        for (int i = 0; i < repo.numberOfSymptoms(); i++) {
            questionArray.add(new QuestionNode(i));
        }

        for (Case c : pCases) {
            for (Integer sx : c.hasMajorSx) {
                questionArray.get(sx).rating += 2;
            }
            for (Integer sx : c.hasNotMajorSx) {
                questionArray.get(sx).rating += 2;
            }
            for (Integer sx : c.hasMinorSx) {
                questionArray.get(sx).rating++;
            }
            for (Integer sx : c.hasNotMinorSx) {
                questionArray.get(sx).rating++;
            }
        }
    }

    public int nextQuestion() {
        QuestionNode nextQuestion = new QuestionNode(0);
        for(QuestionNode qNode : questionArray) {
            if (!qNode.asked && qNode.rating > nextQuestion.rating) {
                nextQuestion = qNode;
            }
        }
        nextQuestion.asked = true;
        return nextQuestion.number;
    }

    public void update(int question, boolean answer) {
        ArrayList<Case> relevantCases = repo.getCasesWithSymtom(question, answer);
        for (Case c : relevantCases) {
            for (Integer sx : c.hasMajorSx) {
                questionArray.get(sx).rating += 2;
            }
            for (Integer sx : c.hasNotMajorSx) {
                questionArray.get(sx).rating += 2;
            }
            for (Integer sx : c.hasMinorSx) {
                questionArray.get(sx).rating++;
            }
            for (Integer sx : c.hasNotMinorSx) {
                questionArray.get(sx).rating++;
            }
        }
    }

    public void setToAsked(int question) {
        questionArray.get(question).asked = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (QuestionNode qn : questionArray) {
            sb.append(qn.toString() + "\n");
        }
        return sb.toString();
    }
}
