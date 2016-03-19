package is.pedro;

/**
 * Created by andri on 16/03/16.
 */

import static spark.Spark.*;
import spark.servlet.SparkApplication;

import java.util.ArrayList;

public class WebUI implements SparkApplication {

    public static void main(String[] args) {
        staticFileLocation("/public");

        SparkApplication webUI = new WebUI();
        webUI.init();
    }

    @Override
    public void init() {
        final Repo repo = new Repo();
        ArrayList<Case> pCases = repo.getPreviousCases(9, 'M');
        CBR cbr = new CBR(pCases);
        QuestionSearch qSearch = new QuestionSearch(pCases, repo);

        Case currentCase = new Case();

        // POST

        post("/initinfo", (request, response) -> {
            int age = Integer.parseInt(request.queryParams("age"));
            char gender = request.queryParams("gender").charAt(0);
            int mainSymptom = Integer.parseInt(request.queryParams("mainsymptom"));

            currentCase.setAge(age);
            currentCase.setGender(gender);
            currentCase.addToHas(mainSymptom);

            response.status(200);

            qSearch.update(mainSymptom, true);
            Integer nextIndex = qSearch.nextQuestion();

            return "{ \"symptom\":\""+ nextIndex +"\" }";
        });

        post("/answer", (request, response) -> {
            Integer symptom = Integer.parseInt(request.queryParams("symptom"));
            String answer = request.queryParams("answer");
            boolean hasSymptom = answer.equals("true");

            System.out.println(answer);
            System.out.println(symptom);

            if (hasSymptom) {
                currentCase.addToHas(symptom);
            }
            else {
                currentCase.addToHasNot(symptom);
            }

            response.status(200);

            qSearch.update(symptom, hasSymptom);
            Integer nextIndex = qSearch.nextQuestion();

            return "{ \"symptom\":\""+ nextIndex +"\" }";
        });

        post("/confirm", (request, response) -> {
            if (request.queryParams("answer").equals("true")) {
                repo.addCase(currentCase);
            }
            response.status(200);
            return response;
        });

        // GET

        get("/question", (request, response) -> {
            response.status(200);
            return response;
        });

        get("/done", (request, response) -> {
            String diagnosis = cbr.findDiagnosis(currentCase);
            currentCase.setDiagnosis(diagnosis);
            response.status(200);
            return "{ \"diagnosis\":\""+ diagnosis +"\" }";
        });

        get("/symptoms", (request, response) -> {
            ArrayList<String> symptoms = repo.getSymptoms();
            StringBuilder arr = new StringBuilder("{ \"symptoms\": [");
            for (int i = 0; i < symptoms.size(); i++) {
                if (i != symptoms.size() - 1) {
                    arr.append("\"" + symptoms.get(i) + "\", ");
                }
                else {
                    arr.append("\"" + symptoms.get(i) + "\" ] }");
                }
            }
            return arr.toString();
        });

    }

}