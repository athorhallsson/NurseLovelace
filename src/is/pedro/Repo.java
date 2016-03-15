package is.pedro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by andri on 13/03/16.
 */
public class Repo {

    private Connection c;

    public Repo() {
        connectToDb();
    }

    private void connectToDb() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/diagnosisdb",
                            "andri", "nopasswordneeded");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public void addCase(Case newCase) {
        Statement s;
        boolean[] arr = newCase.symptoms;
        try {
            s = c.createStatement();
            StringBuilder sql = new StringBuilder("INSERT INTO Cases (fever, cough, diarrhea, nausea, bloodystool, bloodyvomit, diagnosis) VALUES (");
            for (int i = 0; i < arr.length; i++) {
                sql.append(arr[i]);
                sql.append(", ");
            }
            sql.append("'" + newCase.diagnosis + "');");
            s.executeUpdate(sql.toString());
            s.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    public ArrayList<Case> getPreviousCases() {
        ArrayList<Case> cases = new ArrayList<Case>();
        Statement stmt = null;

        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Cases;" );
            while (rs.next()) {
                boolean[] arr = new boolean[Constants.numberOfSymptoms];
                for (int i = 0; i < Constants.numberOfSymptoms; i++) {
                    arr[i] = rs.getBoolean(i + 1);
                }
                cases.add(new Case(arr, rs.getString("diagnosis")));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return cases;
    }

}
