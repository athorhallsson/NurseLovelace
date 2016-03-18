package is.pedro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
                            "postgres", "nopassword");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public void addCase(Case newCase) {
        Statement s;
        try {
            s = c.createStatement();
            StringBuilder hasQuery = new StringBuilder("INSERT INTO Cases (");
            hasQuery.append("diagnosis, age, gender, ");
            for (Integer sx : newCase.hasSx) {
                hasQuery.append(sx + 4 + ", ");
            }
            for (Iterator<Integer> i = newCase.hasNotSx.iterator(); i.hasNext();) {
                Integer notSx = i.next();
                hasQuery.append(notSx + 4);
                if (i.hasNext()) {
                    hasQuery.append(", ");
                }
                else {
                    hasQuery.append(") VALUES (");
                }
            }
            hasQuery.append(newCase.diagnosis + ", ");
            hasQuery.append(newCase.age + ", ");
            hasQuery.append(newCase.gender + ", ");
            for (Integer sx : newCase.hasSx) {
                hasQuery.append("true, ");
            }
            for (int i = 0; i < newCase.hasNotSx.size(); i++) {
                hasQuery.append("false");
                if (i != newCase.hasNotSx.size()) {
                    hasQuery.append(", ");
                }
                else {
                    hasQuery.append(");");
                }
            }
            s.executeUpdate(hasQuery.toString());
            s.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    public ArrayList<Case> getPreviousCases(int age, char gender) {
        ArrayList<Case> cases = new ArrayList<Case>();
        Statement s;

        try {
            s = c.createStatement();
            //ResultSet rs = s.executeQuery( "SELECT * FROM Cases c WHERE c.age = " + age + " AND c.gender = " + gender + ";");
            ResultSet rs = s.executeQuery( "SELECT * FROM Cases c JOIN diagnosis d on c.diagnosisId = d.did;");

            // Account for the two joined diagnosis columns
            int size = rs.getMetaData().getColumnCount() - 2;

            while (rs.next()) {
                HashSet<Integer> hasSx = new HashSet<Integer>();
                HashSet<Integer> hasNotSx = new HashSet<Integer>();

                // Account for cid, did, age and gender columns
                for (int i = 5; i <= size; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            hasSx.add(i);
                        } else {
                            hasNotSx.add(i);
                        }
                    }
                }
                cases.add(new Case(hasSx, hasNotSx, rs.getInt("age"), rs.getString("gender").charAt(0), rs.getString("dname")));
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return cases;
    }

    public ArrayList<String> getSymptoms() {
        ArrayList<String> symptoms = new ArrayList<String>();
        Statement s;

        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery( "SELECT * FROM Symptoms s ORDER BY s.sid;");

            while (rs.next()) {
                symptoms.add(rs.getString("sname"));
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return symptoms;
    }

}
