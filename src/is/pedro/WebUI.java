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
        ArrayList<Case> pCases = repo.getPreviousCases();
        final CBR cbr = new CBR(pCases);
        Case currentCase = new Case(null, null, "Bla");

        post("/initinfo", (req, res) -> {
            currentCase.setAge(req.queryParams("age"));
            currentCase.setGender(req.queryParams("gender"));
            res.status(200);
            return res;
        });

        post("/answer", (req, res) -> {
            if (req.queryParams("answer")) {
                currentCase.addToHas(req.queryParams("symptom"));
            }
            else {
                currentCase.addToNotHas(req.queryParams("symptom"));
            }
            res.status(200);
            return res;
        });

        post("/confirm", (req, res) -> {
            if (req.queryParams("answer")) {
                currentCase.addToHas(req.queryParams("symptom"));
            }
            else {
                currentCase.addToNotHas(req.queryParams("symptom"));
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
            res.status(200);
            return res;
        });


    }

}