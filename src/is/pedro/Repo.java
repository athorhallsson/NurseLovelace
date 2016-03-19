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

    private ArrayList<String> symptoms;

    private Connection c;

    public Repo() {
        connectToDb();
        symptoms = this.getSymptoms();
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
        Statement s2;
        try {
            s = c.createStatement();
            StringBuilder query = new StringBuilder("INSERT INTO Cases (");
            query.append("diagnosisId, age, gender, ");
            for (Integer sx : newCase.hasSx) {
                query.append(symptoms.get(sx).replace(' ', '_'));
                query.append(", ");
            }
            for (Iterator<Integer> i = newCase.hasNotSx.iterator(); i.hasNext();) {
                Integer notSx = i.next();
                query.append(symptoms.get(notSx).replace(' ', '_'));
                if (i.hasNext()) {
                    query.append(", ");
                }
                else {
                    query.append(") VALUES (");
                }
            }

            s2 = c.createStatement();
            String bla = "SELECT d.dId FROM Diagnosis d WHERE d.dname='" + newCase.diagnosis + "';";
            System.out.println(bla);
            ResultSet rs2 = s2.executeQuery(bla);

            if (rs2.next()) {
                query.append(rs2.getInt("dId"));
            }
            query.append(", ");
            query.append(newCase.age);
            query.append(", '");
            query.append(newCase.gender);
            query.append("', ");

            for (int i = 0; i < newCase.hasSx.size(); i++) {
                query.append("true");
                if (i == newCase.hasSx.size() - 1) {
                    if (newCase.hasNotSx.size() != 0) {
                        query.append(", ");
                    }
                }
                else {
                    query.append(", ");
                }
            }
            for (int i = 0; i < newCase.hasNotSx.size(); i++) {
                query.append("false");
                if (i != newCase.hasNotSx.size() - 1) {
                    query.append(", ");
                }
                else {
                    query.append(");");
                }
            }
            System.out.println(query.toString());
            s.executeUpdate(query.toString());
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
