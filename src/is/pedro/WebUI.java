package is.pedro;

/**
 * Created by andri on 16/03/16.
 */

import static spark.Spark.*;
import com.google.gson.Gson;
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
        Repo repo = new Repo();

        QuestionSearch qSearch = new QuestionSearch(repo);

        CBR cbr = new CBR(repo);

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

            if (pos >= 0) {
                currentCase.pain.position.add(pos);
            }
            if (rpos >= 0) {
                currentCase.pain.rPosition.add(rpos);
            }

            response.status(200);

            qSearch.initSearch(currentCase);
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
            qSearch.setToAsked(symptom);
            Integer nextIndex = qSearch.nextQuestion();
            return "{ \"symptom\":\""+ nextIndex +"\" }";
        });

        post("/confirm", (request, response) -> {
            String answer = request.queryParams("answer");
            System.out.println(answer);
            if (answer == null) {
                return response;
            }
            currentCase.setDiagnosis(answer);
            Case revisedCase = cbr.reviseCase(currentCase);
            repo.addCase(revisedCase);
            response.status(200);
            return response;
        });

        post("/addNewDiagnosis", (request, response) -> {
            Case newCase = new Case();
            String sxList = request.queryParams("majorSx");
            String newDx = request.queryParams("newDx");

            String[] arr = sxList.split(",");

            for (String sx : arr) {
                if (!sx.equals("")) {
                    int sxInt = Integer.parseInt(sx);
                    newCase.addToHas(sxInt);
                }
            }

            for (Integer sx : currentCase.hasMajorSx) {
                if (!newCase.hasMajorSx.contains(sx)) {
                    newCase.addToHasMinor(sx);
                }
            }

            newCase.age = currentCase.age;
            newCase.gender = currentCase.gender;
            newCase.hasNotMajorSx = currentCase.hasNotMajorSx;
            newCase.hasNotMinorSx = currentCase.hasNotMinorSx;
            newCase.pain = currentCase.pain;
            newCase.diagnosis = newDx;
            repo.addCase(newCase);
            response.status(200);
            return response;
        });

        // GET

        get("/done", (request, response) -> {
            response.status(200);
            ArrayList<String> ddxList = cbr.findDiagnosis(currentCase);
            return new Gson().toJson(ddxList);
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

        get("/getSymptomsForCase", (request, response) -> {
            ArrayList<Integer> sxList = new ArrayList<>();
            for (Integer sx : currentCase.hasMajorSx) {
                sxList.add(sx);
            }
            return new Gson().toJson(sxList);
        });

        get("/reset", (request, response) -> {
            init();
            return response;
        });

    }

}