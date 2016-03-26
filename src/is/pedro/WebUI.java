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

        QuestionSearch qSearch = new QuestionSearch(pCases, repo);

        Case currentCase = new Case();

        // POST

        post("/initinfo", (request, response) -> {
            int age = Integer.parseInt(request.queryParams("age"));
            char gender = request.queryParams("gender").charAt(0);
            int mainSymptom = Integer.parseInt(request.queryParams("mainsymptom"));
            String painString = request.queryParams("pain");

            int pos = Integer.parseInt(request.queryParams("pos"));
            int rpos = Integer.parseInt(request.queryParams("rpos"));

            // Add to painInfo
            String[] painIndex = painString.split("_");
            for (int i = 0; i < painIndex.length; i++) {
                int index = Integer.parseInt(painIndex[i]);
                if (index >= 0) {
                    currentCase.pain.painInfo.add(index);
                }
            }

            currentCase.setAge(age);
            currentCase.setGender(gender);
            currentCase.addToHas(mainSymptom);

            response.status(200);

            qSearch.update(mainSymptom, true);
            qSearch.setToAsked(mainSymptom);
            Integer nextIndex = qSearch.nextQuestion();

            return "{ \"symptom\":\""+ nextIndex +"\" }";
        });

        post("/answer", (request, response) -> {
            Integer symptom = Integer.parseInt(request.queryParams("symptom"));
            String answer = request.queryParams("answer");
            boolean hasSymptom = answer.equals("true");

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
            String answer = request.queryParams("answer");
            if (answer == null) {
                return response;
            }
            else if (answer.equals("true")) {
                repo.addCase(currentCase);
            }
            else {
                currentCase.setDiagnosis(answer);
                repo.addCase(currentCase);
            }
            response.status(200);
            return response;
        });

        // GET

        get("/done", (request, response) -> {
            CBR cbr = new CBR(pCases);
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