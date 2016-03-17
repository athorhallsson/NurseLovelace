package is.pedro;

/**
 * Created by andri on 16/03/16.
 */

import static spark.Spark.*;
import spark.servlet.SparkApplication;

import java.util.ArrayList;
import java.util.HashSet;

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
        final CBR cbr = new CBR(pCases);
        Case currentCase = new Case(new HashSet<Integer>(), new HashSet<Integer>(), 9, 'M', "Bla");

        post("/initinfo", (request, response) -> {
            currentCase.setAge(Integer.parseInt(request.queryParams("age")));
            currentCase.setGender(request.queryParams("gender").charAt(0));
            response.status(200);
            return response;
        });

        post("/answer", (request, response) -> {
            System.out.println(request.queryParams("answer"));
            System.out.println(request.queryParams("symptom"));

            if (request.queryParams("answer").equals("true")) {
                currentCase.addToHas(Integer.parseInt(request.queryParams("symptom")));
            }
            else {
                currentCase.addToHasNot(Integer.parseInt(request.queryParams("symptom")));
            }

            Integer newSymptomId = Integer.parseInt(request.queryParams("symptom")) + 1;
            response.status(200);
            return "{ \"symptom\":\""+ newSymptomId +"\" }";
        });

        post("/confirm", (req, res) -> {
            if (req.queryParams("answer").equals("true")) {
                repo.addCase(currentCase);
            }
            res.status(200);
            return res;
        });

        get("/question", (req, res) -> {
            res.status(200);
            return res;
        });

        get("/done", (req, res) -> {
            //String diagnosis = cbr.findDiagnosis(currentCase);
            String diagnosis = "Fake diagnosis";
            res.status(200);
            return "{ \"diagnosis\":\""+ diagnosis +"\" }";
        });

    }

}