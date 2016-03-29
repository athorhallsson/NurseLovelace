package is.pedro;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by andri on 19/03/16.
 */
public class QuestionSearch {
    private Repo repo;
    private int falseCount = 0;

    private ArrayList<QuestionNode> questionArray = new ArrayList<QuestionNode>();

    public QuestionSearch(Repo repo) {
        this.repo = repo;
    }

    public void initSearch(Case currCase) {

        ArrayList<Case> pCases = repo.getInitCases(currCase);
        // System.out.println(pCases.size());
        for (int i = 0; i < repo.numberOfSymptoms(); i++) {
            questionArray.add(new QuestionNode(i));
        }

        for (Case c : pCases) {
            //System.out.println(c);
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
        if (falseCount > 5) {
            return -1;
        }
        // Make a dummy question node for comparison
        QuestionNode nextQuestion = new QuestionNode(-1);
        for(QuestionNode qNode : questionArray) {
            if (!qNode.asked && qNode.rating >= nextQuestion.rating) {
                nextQuestion = qNode;
            }
        }
        nextQuestion.asked = true;
        return nextQuestion.number;
    }

    public void update(int question, boolean answer) {
        // Count the number of false answer in a row
        if (!answer) {
            falseCount++;
        }
        else {
            falseCount = 0;
        }

        ArrayList<Case> relevantCases = repo.getCasesWithSymtom(question, answer);
        for (Case c : relevantCases) {
            //System.out.println(c);
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
