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

    private int majorStart = 9;
    private int majorEnd = 70;
    private int minorStart = 72;
    private int minorEnd = 133;
    private int painStart = 132;
    private int painEnd = 162;
    private int posStart = 165;
    private int posEnd = 215;
    private int refstart = 217;
    private int refEnd = 267;

    private String joinQuery = "SELECT * FROM CASES c "
        + "JOIN SYMPTOMS major ON c.majorsx = major.sxid "
        + "JOIN SYMPTOMS minor ON c.minorsx = minor.sxid "
        + "JOIN Pain p ON p.pId = c.painid "
        + "JOIN diagnosis d on c.diagnosisId = d.did "
        + "JOIN Positions pos ON pos.posid = p.positionid "
        + "JOIN Positions rpos ON rpos.posid = p.referred_pain_position ";


    private ArrayList<String> symptoms = new ArrayList<String>();
    private ArrayList<String> painSx = new ArrayList<String>();
    private ArrayList<String> painPos = new ArrayList<String>();

    private Connection c;

    public Repo() {
        connectToDb();
        ArrayList<String> temp = this.getSymptoms();
        for (int i = 0; i < temp.size(); i++) {
            this.symptoms.add(temp.get(i).replace(' ', '_'));
        }
        temp = this.getPainPos();
        for (int i = 0; i < temp.size(); i++) {
            this.painPos.add(temp.get(i).replace(' ', '_'));
        }
        temp = this.getPainSx();
        for (int i = 0; i < temp.size(); i++) {
            this.painSx.add(temp.get(i).replace(' ', '_'));
        }
    }

    public int numberOfSymptoms() { return symptoms.size(); }

    private void connectToDb() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/diagnosisdb", "postgres", "nopassword");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    private String makePositionQuery(HashSet<Integer> hs) {
        StringBuilder query = new StringBuilder("INSERT INTO Positions (");
        for (Iterator<Integer> i = hs.iterator(); i.hasNext();) {
            Integer posIndex = i.next();
            query.append(painPos.get(posIndex));
            if (i.hasNext()) {
                query.append(", ");
            }
        }
        query.append(") VALUES (");

        for (int i = 0; i < hs.size(); i++) {
            query.append("true");
            if (i != hs.size() - 1) {
                query.append(", ");
            }
        }
        query.append(")");
        return query.toString();
    }

    private String makePainQuery(HashSet<Integer> hs, Integer posId, Integer rPosId) {
        StringBuilder query = new StringBuilder("INSERT INTO Pain (positionId, referred_pain_position, ");
        for (Iterator<Integer> i = hs.iterator(); i.hasNext();) {
            Integer posIndex = i.next();
            query.append(painSx.get(posIndex));
            if (i.hasNext()) {
                query.append(", ");
            }
        }
        query.append(") VALUES (" + posId + ", " + rPosId + ", ");

        for (int i = 0; i < hs.size(); i++) {
            query.append("true");
            if (i != hs.size() - 1) {
                query.append(", ");
            }
        }
        query.append(")");
        return query.toString();
    }

    private String makeSymptomsQuery(HashSet<Integer> tSet, HashSet<Integer> fSet) {
        StringBuilder query = new StringBuilder("INSERT INTO Symptoms (");
        for (Iterator<Integer> i = tSet.iterator(); i.hasNext();) {
            Integer posIndex = i.next();
            query.append(symptoms.get(posIndex));
            if (i.hasNext() && fSet.size() != 0) {
                query.append(", ");
            }
        }
        for (Iterator<Integer> i = fSet.iterator(); i.hasNext();) {
            Integer posIndex = i.next();
            query.append(symptoms.get(posIndex));
            if (i.hasNext()) {
                query.append(", ");
            }
        }
        query.append(") VALUES (");

        for (int i = 0; i < tSet.size(); i++) {
            query.append("true");
            if (i != tSet.size() - 1 && fSet.size() != 0) {
                query.append(", ");
            }
        }
        for (int i = 0; i < fSet.size(); i++) {
            query.append("false");
            if (i != fSet.size() - 1) {
                query.append(", ");
            }
        }
        query.append(")");
        return query.toString();
    }

    public void addCase(Case newCase) {
        Statement s1;
        Statement s2;
        Statement s3;
        Statement s4;
        Statement s5;
        Statement s6;
        Statement s7;
        try {
            int dId;
            s1 = c.createStatement();
            s2 = c.createStatement();
            s3 = c.createStatement();
            s4 = c.createStatement();
            s5 = c.createStatement();
            s6 = c.createStatement();
            s7 = c.createStatement();

            String diagnosisQuery = "SELECT d.dId FROM Diagnosis d WHERE d.dname='" + newCase.diagnosis + "';";
            System.out.println(diagnosisQuery);
            ResultSet rs1 = s1.executeQuery(diagnosisQuery);
            // Check if there is an existing diagnosis
            if (rs1.next()) {
                dId = rs1.getInt("dId");
            }
            else {
                dId = s1.executeUpdate("INSERT INTO Diagnosis (dname) VALUES ('" + newCase.diagnosis + "');", Statement.RETURN_GENERATED_KEYS);
            }

            Integer posId = s2.executeUpdate(makePositionQuery(newCase.pain.position), Statement.RETURN_GENERATED_KEYS);
            Integer rPosId = s3.executeUpdate(makePositionQuery(newCase.pain.rPosition), Statement.RETURN_GENERATED_KEYS);

            String painQuery = makePainQuery(newCase.pain.painInfo, posId, rPosId);
            System.out.println(painQuery);
            Integer painId = s4.executeUpdate(painQuery, Statement.RETURN_GENERATED_KEYS);

            System.out.println("posId: " + posId + " rPosId: " + rPosId + " painid: " + painId);

            int majorSxId = s5.executeUpdate(makeSymptomsQuery(newCase.hasMajorSx, newCase.hasNotMajorSx));
            int minorSxId = s6.executeUpdate(makeSymptomsQuery(newCase.hasMinorSx, newCase.hasNotMinorSx));


            String query = "INSERT INTO Cases (diagnosisId, age, gender, majorSx, minorSx, painId) VALUES (";
            query += dId + ", " + newCase.age + ", '" + newCase.gender + "', " + majorSxId + ", " + minorSxId + ", " + painId + ");";
            System.out.println(query);
            s7.executeUpdate(query);

            s1.close();
            s2.close();
            s3.close();
            s4.close();
            s5.close();
            s6.close();
            s7.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public ArrayList<Case> getBaseCases(Case currCase) {
        int ageMin = currCase.age - 10;
        int ageMax = currCase.age + 10;
        String gender = "'" + currCase.gender + "'";

        String query = joinQuery;
        query += "WHERE c.gender = " + gender;
        query += " AND c.age BETWEEN " + ageMin + " AND " + ageMax;
        System.out.println(query);
        return getFromCases(query);
    }

    public ArrayList<Case> getInitCases(Case currCase) {
        Iterator i = currCase.hasMajorSx.iterator();
        boolean value = true;
        int symptom = 0;

        if (i.hasNext()) {
             symptom = (int)i.next();
        }

        String query = joinQuery;
        query += "WHERE major." + symptoms.get(symptom) + "=" + value + " OR minor." + symptoms.get(symptom) + "=" + value;

        for (Integer pSx : currCase.pain.painInfo) {
            query += " AND p." + painSx.get(pSx) + "=" + value;
        }
/*
        for (Integer pPos : currCase.pain.position) {
            query += " AND pos." + painPos.get(pPos) + "=" + value;
        }

        for (Integer rPos : currCase.pain.rPosition) {
            query += " AND rpos." + painPos.get(rPos) + "=" + value;
        }
*/
  //      System.out.println(query);
        return getFromCases(query);
    }

    public ArrayList<Case> getCasesWithSymtom(int symptom, boolean value) {
        String query = joinQuery + "WHERE major." + symptoms.get(symptom) + "=" + value + " OR minor." + symptoms.get(symptom) + "=" + value;
       // System.out.println(query);
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
                for (int i = majorStart; i <= majorEnd; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            hasMajorSx.add(i - majorStart);
                        }
                        else {
                            hasNotMajorSx.add(i - majorStart);
                        }
                    }
                }

                HashSet<Integer> hasMinorSx = new HashSet<Integer>();
                HashSet<Integer> hasNotMinorSx = new HashSet<Integer>();

                for (int i = minorStart; i <= minorEnd; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            hasMinorSx.add(i - minorStart);
                        }
                        else {
                            hasNotMinorSx.add(i - minorStart);
                        }
                    }
                }


                HashSet<Integer> painInfo = new HashSet<Integer>();
                HashSet<Integer> position = new HashSet<Integer>();
                HashSet<Integer> rPosition = new HashSet<Integer>();


                for (int i = painStart; i <= painEnd; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            painInfo.add(i - painStart);
                        }
                    }
                }

                for (int i = posStart; i <= posEnd; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            position.add(i - posStart);
                        }
                    }
                }

                for (int i = refstart; i <= refEnd; i++) {
                    Boolean bool = rs.getBoolean(i);
                    if (!rs.wasNull()) {
                        if (bool) {
                            rPosition.add(i - refstart);
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

    public ArrayList<String> getPainSx() {
        ArrayList<String> painSx = new ArrayList<String>();
        Statement s;
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM PainSx ps ORDER BY ps.psId;");

            while (rs.next()) {
                painSx.add(rs.getString("psName"));
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return painSx;
    }

    public ArrayList<String> getPainPos() {
        ArrayList<String> painPos = new ArrayList<String>();
        Statement s;
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM PainPosition pp ORDER BY pp.ppId;");

            while (rs.next()) {
                painPos.add(rs.getString("ppName"));
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return painPos;
    }

}
