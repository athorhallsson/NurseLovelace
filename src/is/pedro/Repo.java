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

    private ArrayList<String> symptoms = new ArrayList<String>();

    private Connection c;

    public Repo() {
        connectToDb();
        ArrayList<String> tempSymptoms = this.getSymptoms();
        for (int i = 0; i < tempSymptoms.size(); i++) {
            symptoms.add(tempSymptoms.get(i).replace(' ', '_'));
        }
    }

    public int numberOfSymptoms() { return symptoms.size(); }

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
            for (Iterator<Integer> i = newCase.hasMajorSx.iterator(); i.hasNext();) {
                Integer sx = i.next();
                query.append(symptoms.get(sx));
                if (i.hasNext() || newCase.hasNotMajorSx.size() != 0) {
                    query.append(", ");
                }
            }
            for (Iterator<Integer> i = newCase.hasNotMajorSx.iterator(); i.hasNext();) {
                Integer notSx = i.next();
                query.append(symptoms.get(notSx));
                if (i.hasNext()) {
                    query.append(", ");
                }
            }
            query.append(") VALUES (");

            s2 = c.createStatement();
            String diagnosisQuery = "SELECT d.dId FROM Diagnosis d WHERE d.dname='" + newCase.diagnosis + "';";
            System.out.println(diagnosisQuery);

            ResultSet rs2 = s2.executeQuery(diagnosisQuery);
            // Check if there is an existing diagnosis
            if (rs2.next()) {
                int did = rs2.getInt("dId");
                query.append(did);
            }
            else {
                s2.executeUpdate("INSERT INTO Diagnosis (dname) VALUES ('" + newCase.diagnosis + "');");
                rs2 = s2.executeQuery(diagnosisQuery);
                rs2.next();
                int did = rs2.getInt("dId");
                query.append(did);
            }
            query.append(", ");
            query.append(newCase.age);
            query.append(", '");
            query.append(newCase.gender);
            query.append("', ");

            for (int i = 0; i < newCase.hasMajorSx.size(); i++) {
                query.append("true");
                if (i == newCase.hasMajorSx.size() - 1) {
                    if (newCase.hasNotMajorSx.size() != 0) {
                        query.append(", ");
                    }
                }
                else {
                    query.append(", ");
                }
            }
            for (int i = 0; i < newCase.hasNotMajorSx.size(); i++) {
                query.append("false");
                if (i != newCase.hasNotMajorSx.size() - 1) {
                    query.append(", ");
                }
            }
            query.append(");");
            System.out.println(query.toString());
            s.executeUpdate(query.toString());
            s.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    public ArrayList<Case> getPreviousCases(int age, char gender) {
        //ResultSet rs = s.executeQuery( "SELECT * FROM Cases c WHERE c.age = " + age + " AND c.gender = " + gender + ";");
        //return getFromCases("SELECT * FROM Cases c JOIN diagnosis d on c.diagnosisId = d.did;");
        String query = "SELECT * FROM CASES c JOIN SYMPTOMS major ON c.majorsx = major.sxid JOIN SYMPTOMS minor ON c.minorsx = minor.sxid JOIN Pain p ON p.pId = c.painid JOIN diagnosis d on c.diagnosisId = d.did JOIN Positions pos ON pos.posid = p.positionid JOIN Positions rpos ON rpos.posid = p.referred_pain_position ";

        return getFromCases(query);
    }

    public ArrayList<Case> getCasesWithSymtom(int symptom, boolean value) {
        // return getFromCases("SELECT * FROM Cases c JOIN diagnosis d on c.diagnosisId = d.did WHERE " + symptoms.get(symptom) + "=" + value + ";");
        //String query = "SELECT * FROM CASES c JOIN SYMPTOMS major ON c.majorsx = major.sxid JOIN SYMPTOMS minor ON c.minorsx = minor.sxid JOIN Pain p ON p.pId = c.painid JOIN diagnosis d on c.diagnosisId = d.did ";
        String query = "SELECT * FROM CASES c JOIN SYMPTOMS major ON c.majorsx = major.sxid JOIN SYMPTOMS minor ON c.minorsx = minor.sxid JOIN Pain p ON p.pId = c.painid JOIN diagnosis d on c.diagnosisId = d.did JOIN Positions pos ON pos.posid = p.positionid JOIN Positions rpos ON rpos.posid = p.referred_pain_position";
        query += " WHERE major." + symptoms.get(symptom) + "=" + value + " OR minor." + symptoms.get(symptom) + "=" + value;
        System.out.println(query);
        return getFromCases(query);
    }

    private ArrayList<Case> getFromCases(String query) {
        ArrayList<Case> cases = new ArrayList<Case>();
        Statement s;
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            //int size = rs.getMetaData().getColumnCount() - 2;

            while (rs.next()) {
                HashSet<Integer> hasMajorSx = new HashSet<Integer>();
                HashSet<Integer> hasNotMajorSx = new HashSet<Integer>();

                // Account for cid, did, age and gender columns
                for (int i = 9; i <= 59; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            hasMajorSx.add(i - 9);
                        }
                        else {
                            hasNotMajorSx.add(i - 9);
                        }
                    }
                }

                HashSet<Integer> hasMinorSx = new HashSet<Integer>();
                HashSet<Integer> hasNotMinorSx = new HashSet<Integer>();

                for (int i = 61; i <= 101; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            hasMinorSx.add(i - 61);
                        }
                        else {
                            hasNotMinorSx.add(i - 61);
                        }
                    }
                }


                HashSet<Integer> painInfo = new HashSet<Integer>();
                HashSet<Integer> position = new HashSet<Integer>();
                HashSet<Integer> rPosition = new HashSet<Integer>();


                for (int i = 105; i <= 130; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            painInfo.add(i - 105);
                        }
                        else {
                            painInfo.add(i - 105);
                        }
                    }
                }
                Pain pain = new Pain(painInfo, position, rPosition);
                cases.add(new Case(hasMajorSx, hasNotMajorSx, hasMinorSx, hasNotMinorSx, pain, rs.getInt("age"), rs.getString("gender").charAt(0), rs.getString("dname")));
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
        return cases;
    }

    public ArrayList<String> getSymptoms() {
        ArrayList<String> symptoms = new ArrayList<String>();
        Statement s;
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM SymptomNames s ORDER BY s.sid;");

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
