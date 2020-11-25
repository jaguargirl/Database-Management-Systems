package proiect_sgbd;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Workspace {

    private static volatile Workspace w = new Workspace();
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private String column_name, sequence_name, pk_tab, new_pk_column, new_fk_column, URL, schema, password, query, pk_constraint_name;
    private String add = "";
    private String pk_column_name;
    private int e;
    private Boolean b = true;
    private int lines;
    private String[][] ret = new String[50][5];
    private ArrayList<String> rsp = new ArrayList<String>();
    private ArrayList<String> temp = new ArrayList<String>();
    private ArrayList<String> allTables = new ArrayList<String>();
    private ArrayList<String> flexible = new ArrayList<String>();
    private ArrayList<String> badTables = new ArrayList<String>();
    private ArrayList<String> refA = new ArrayList<String>();
    private HashSet<String> duplicate = new HashSet<String>();
    private HashSet<String> notNumber = new HashSet<String>();
    private Iterator<String> it = duplicate.iterator();
    private Iterator<String> itr = notNumber.iterator();

    private Workspace() {
    }

    public static Workspace getInstance() {
        return w;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.err.println("Indisponible driver: " + e);
        }
        try {
            conn = DriverManager.getConnection(URL, schema, password);
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int verifyTablesWithoutPK() {
        query = "SELECT TABLE_NAME FROM USER_TABLES MINUS SELECT TABLE_NAME FROM USER_CONSTRAINTS WHERE CONSTRAINT_TYPE='P'";//selecteaza tabelele fara chei primare
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                rsp.add(rs.getString("TABLE_NAME"));//adauga numele tabelelor intr-o lista
            }
            if (rsp.isEmpty()) {//verifica daca lista e goala=>toate tebelele au chei primare
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    public int createSQ() {
        sequence_name = "my_sq_";
        String nr;
        for (int i = 0;; i++) {
            nr = Integer.toString(i);
            try {
                sequence_name += nr;//atribuim numele de secventa
                query = "CREATE SEQUENCE " + sequence_name;//cream secventa
                st = conn.createStatement();
                rs = st.executeQuery(query);
                return 0;
            } catch (SQLException e) {
                e.getErrorCode();//preluam codul erorii
                if (e.getErrorCode() == 955) {//daca acest nume de secventa exita
                    sequence_name = "my_sq_";//atribuim numele initial, se incrementeaza i, si la urmatoare iteratie, numele secventei va fi my_sq_i, pana cand nu se gaseste un nume liber
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            } catch (Exception se) {
                JOptionPane.showMessageDialog(null, se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        }
    }

    public int addSurrogatePK() {
        for (int i = 0; i < rsp.size(); i++) {//parcurgem lista cu numele tabelelor fara chei primare
            String constraint_name;
            int e = 0;
            String add = "";
            Boolean b = true;
            while (b) {
                try {
                    column_name = "SPK" + add;//cream un nume de coloana pentru cheia primara surogat, dupa principiul numelui de secventa
                    query = "ALTER TABLE " + rsp.get(i) + " ADD " + column_name + " NUMBER(7)";
                    rs = st.executeQuery(query);//adaugam o coloana noua tabelei
                    query = "UPDATE " + rsp.get(i) + " SET " + column_name + "=" + sequence_name + ".NEXTVAL";//atribuim valori coloanei, utilizand sequence creat anterior
                    rs = st.executeQuery(query);
                    b = false;
                } catch (SQLException ex) {
                    if (ex.getErrorCode() == 1430) {
                        e++;
                        add = Integer.toString(e);
                    } else {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    }
                } catch (Exception se) {
                    JOptionPane.showMessageDialog(null, se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            }
            e = 0;
            add = "";
            b = true;
            while (b) {
                try {
                    constraint_name = "MY_SPK" + add;//cream nume de constrangere exact ca la sequence si nume de coloana
                    query = "ALTER TABLE " + rsp.get(i) + " ADD CONSTRAINT " + constraint_name + " PRIMARY KEY(" + column_name + ")";
                    rs = st.executeQuery(query);//adaugam constrangerea de cheie primara, coloanei pe care am creat-o anterion
                    b = false;
                } catch (SQLException sqle) {
                    if (sqle.getErrorCode() == 2264) {//daca acest nume de constrangere exista, schimbam partea pe care o adaugam => add
                        e++;
                        add = Integer.toString(e);
                    } else {
                        JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    }
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            }
        }
        return 0;
    }

    private int getTablesPK() {
        query = "SELECT ucc.TABLE_NAME,ucc.COLUMN_NAME, uc.CONSTRAINT_NAME,utc.DATA_TYPE FROM (USER_CONSTRAINTS uc JOIN USER_CONS_COLUMNS ucc ON uc.CONSTRAINT_NAME=ucc.CONSTRAINT_NAME) JOIN USER_TAB_COLUMNS utc ON utc.TABLE_NAME=uc.TABLE_NAME AND utc.COLUMN_NAME=ucc.COLUMN_NAME WHERE uc.CONSTRAINT_TYPE='P'";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            int e = 0;
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {//Parcurge fiecare tuplu(linie) a rezultatului
                for (int i = 1; i < columnsNumber + 1; i++) {//Parcurge fiecare coloana a unui tuplu, numerotate de la 1 la columnsNumber
                    String columnValue = rs.getString(i);
                    ret[e][i - 1] = columnValue.toString();
                }
                e++;
            }
            lines = e;
            return 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    private int checkDuplicates() {
        for (int i = 0; i < lines; i++) {
            for (int j = i + 1; j < lines; j++) {
                if (ret[i][0].equals(ret[j][0])) {//verifica daca exista nume de tabel care se repeta=> cheile primare pe mai multe atribute
                    duplicate.add(ret[i][0]);
                }
            }
        }
        if (duplicate.isEmpty()) {
            return 0;
        }
        return 1;
    }

    private int checkNotNumber() {
        int i = 0;
        while (i < lines) {
            if (!ret[i][3].equals("NUMBER") && !duplicate.contains(ret[i][0])) {//Verifica daca PK nu este de tip number si nu se afla in lista duplicates
                notNumber.add(ret[i][0]);
            }
            i++;
        }
        if (notNumber.isEmpty()) {
            return 0;
        }
        return 1;
    }

    public int verifyExistentPK() {
        int r = this.getTablesPK();
        if (r == -1) {
            return -1;
        }
        r = this.checkDuplicates();
        int e = this.checkNotNumber();
        if (r == 0 && e == 0) {
            return 0;
        }
        if (r == 1 && e == 0) {
            return 1;
        }
        if (r == 0 && e == 1) {
            return 2;
        }
        return 3;
        //jdbc:oracle:thin:@127.0.0.1:1521:xe
    }

    private int addColumnPK() {
        b = true;
        e = 0;
        add = "";
        while (b) {
            try {
                new_pk_column = "SPK" + add;
                query = "ALTER TABLE " + pk_tab + " ADD " + new_pk_column + " NUMBER(7)";
                st = conn.createStatement();
                rs = st.executeQuery(query);
                query = "UPDATE " + pk_tab + " SET " + new_pk_column + "=" + sequence_name + ".NEXTVAL";
                rs = st.executeQuery(query);
                b = false;
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1430) {
                    e++;
                    add = Integer.toString(e);
                } else {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            } catch (Exception se) {
                JOptionPane.showMessageDialog(null, se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        }
        return 1;
    }

    private int getRefferedTables() {
        query = "SELECT uc.TABLE_NAME, ucc.COLUMN_NAME, uc.CONSTRAINT_NAME FROM (USER_CONSTRAINTS uc JOIN USER_CONS_COLUMNS ucc ON uc.CONSTRAINT_NAME=ucc.CONSTRAINT_NAME) JOIN USER_TAB_COLUMNS utc ON uc.TABLE_NAME=utc.TABLE_NAME AND ucc.COLUMN_NAME=utc.COLUMN_NAME WHERE R_CONSTRAINT_NAME='" + pk_constraint_name + "'";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                add = "";
                for (int j = 1; j < columnsNumber + 1; j++) {
                    String columnValue = rs.getString(j);
                    add += columnValue.toString() + ",";
                }
                refA.add(add);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        if (refA.isEmpty()) {
            return 0;
        }
        return 1;
    }

    private int insertNewPKValToNewFKColMultiple() {
        for (int t = 0; t < refA.size(); t++) {
            String[] currentRow = refA.get(t).split(",");
            String fk_tab = currentRow[0];
            if (!flexible.contains(fk_tab)) {
                flexible.add(fk_tab);
                b = true;
                e = 0;
                add = "";
                String col = "SFK_"+pk_tab;
                while (b) {
                    try {
                        new_fk_column = col + add;
                        query = "ALTER TABLE " + fk_tab + " ADD " + new_fk_column + " NUMBER(7)";
                        st = conn.createStatement();
                        rs = st.executeQuery(query);
                        b = false;
                    } catch (SQLException sqle) {
                        if (sqle.getErrorCode() == 1430) {
                            e++;
                            add = Integer.toString(e);
                        } else {
                            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            return -1;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    }
                }
                String pk_coloane = "";
                for (e = 0; e < lines; e++) {
                    if (ret[e][0].equals(pk_tab)) {
                        pk_coloane += ret[e][1] + ",";
                    }
                }
                String[] c = pk_coloane.split(",");
                String equal = "";
                for (e = 0; e < c.length; e++) {
                    if (e != c.length - 1) {
                        equal += refA.get(e).split(",")[1] + "=my_rec." + c[e] + " AND ";
                    } else {
                        equal += refA.get(e).split(",")[1] + "=my_rec." + c[e];
                    }
                }
                try {
                    query = "DECLARE CURSOR my_cursor IS SELECT " + pk_coloane + new_pk_column + " FROM " + pk_tab + "; my_rec my_cursor%ROWTYPE; BEGIN FOR my_rec IN my_cursor LOOP UPDATE " + fk_tab + " SET " + new_fk_column + "=my_rec." + new_pk_column + " WHERE " + equal + "; END LOOP; END;";
                    st = conn.createStatement();
                    rs = st.executeQuery(query);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            }
        }
        flexible.clear();
        return 1;
    }

    private int insertNewPKValToNewFKColSingle() {
        for (int t = 0; t < refA.size(); t++) {
            String[] currentRow = refA.get(t).split(",");
            if (!flexible.contains(currentRow[0])) {
                flexible.add(currentRow[0]);
                b = true;
                e = 0;
                add = "";
                String col = "SFK_" + currentRow[1];
                String fk_tab = currentRow[0];
                while (b) {
                    try {
                        new_fk_column = col + add;
                        query = "ALTER TABLE " + fk_tab + " ADD " + new_fk_column + " NUMBER(7)";
                        st = conn.createStatement();
                        rs = st.executeQuery(query);
                        b = false;
                    } catch (SQLException sqle) {
                        if (sqle.getErrorCode() == 1430) {
                            e++;
                            add = Integer.toString(e);
                        } else {
                            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            return -1;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    }
                }
                try {
                    query = "DECLARE CURSOR my_cursor IS SELECT " + pk_column_name + "," + new_pk_column + " FROM " + pk_tab + "; my_rec my_cursor%ROWTYPE; BEGIN FOR my_rec IN my_cursor LOOP UPDATE " + fk_tab + " SET " + new_fk_column + "=my_rec." + new_pk_column + " WHERE " + currentRow[1] + "=" + "my_rec." + pk_column_name + "; END LOOP; END;";
                    st = conn.createStatement();
                    rs = st.executeQuery(query);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            }
        }
        flexible.clear();
        return 1;
    }

    private int dropFKCons() {
        for (int t = 0; t < refA.size(); t++) {
            String fk_cons = refA.get(t).split(",")[2];
            String fk_tab = refA.get(t).split(",")[0];
            if (!flexible.contains(fk_tab)) {
                flexible.add(fk_tab);
                query = "ALTER TABLE " + fk_tab + " DROP CONSTRAINT " + fk_cons;
                try {
                    st = conn.createStatement();
                    rs = st.executeQuery(query);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            }
        }
        flexible.clear();
        return 1;
    }

    private int dropOldPKaddNewPK() {
        String constraint_name;
        query = "ALTER TABLE " + pk_tab + " DROP CONSTRAINT " + pk_constraint_name;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        b = true;
        add = "";
        while (b) {
            try {
                constraint_name = "MY_SPK" + add;
                query = "ALTER TABLE " + pk_tab + " ADD CONSTRAINT " + constraint_name + " PRIMARY KEY(" + new_pk_column + ")";
                rs = st.executeQuery(query);
                b = false;
            } catch (SQLException sqle) {
                if (sqle.getErrorCode() == 2264) {
                    e++;
                    add = Integer.toString(e);
                } else {
                    JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        }
        return 1;
    }

    private int addFKCons() {
        for (int t = 0; t < refA.size(); t++) {
            String fk_tab = refA.get(t).split(",")[0];
            if (flexible.contains(fk_tab)) {
                flexible.add(fk_tab);
                b = true;
                e = 0;
                add = "";
                String fk_cons = "";
                while (b) {
                    fk_cons = pk_tab + "_FK" + add;
                    try {
                        query = "ALTER TABLE" + fk_tab + " ADD CONSTRAINT " + fk_cons + " FOREIGN KEY(" + new_fk_column + ") REFERENCES " + pk_tab + " (" + new_pk_column + ")";
                        rs = st.executeQuery(query);
                        flexible.clear();
                    } catch (SQLException sqle) {
                        if (sqle.getErrorCode() == 2264) {
                            e++;
                            add = Integer.toString(e);
                        } else {
                            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            return -1;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    }
                }
            }
        }
        flexible.clear();
        return 1;
    }

    private int changeRefferencesN() {
        for (int i = 0; i < lines; i++) {//parcurgem fiecare tabel cu cheie primara gestionata gresit
            pk_constraint_name = ret[i][2];
            pk_column_name = ret[i][1];
            new_pk_column = "";
            new_fk_column = "";
            pk_tab = ret[i][0];
            if ((notNumber.contains(pk_tab) || duplicate.contains(pk_tab)) && !temp.contains(pk_tab)) {//verificam daca nu am tratat deja tabelul respectiv
                temp.add(pk_tab);
                e = addColumnPK();//adaugam o noua coloana care va inlocui PK tabelului
                if (e == 1) {
                    e = getRefferedTables();//cautam daca exista tabele referite
                    if (e == 1) {//exista tabele referite
                        if (duplicate.contains(pk_tab)) {//verificam daca tabelul se afla in duplicate=> cheie primara pe m.m. coloane
                            e = insertNewPKValToNewFKColMultiple();//adaugam fiecarei tabele referite coloana nou si inseram valori corespunzator legaturilor anterioare
                            if (e == -1) {
                                return -1;
                            }
                        } else {
                            e = insertNewPKValToNewFKColSingle();//daca are pk pe o singura coloana, inseram valori corespunzator legaturilor anterioare
                            if (e == -1) {
                                return -1;
                            }
                        }
                        e = dropFKCons();//stergem constrangerile tabelelor referite
                        if (e == 1) {
                            e = dropOldPKaddNewPK();//stergem cheia primara veche, adaugam cheia primara noua
                            if (e == 1) {
                                e = addFKCons();//refacem constrangerile tabelelor referite
                                if (e == -1) {
                                    return -1;
                                }
                            }
                        } else {
                            return -1;
                        }
                    } else if (e == 0) {//nu exista tabele referite
                        e = dropOldPKaddNewPK();
                        if (e == -1) {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                } else {
                    return -1;
                }
            }
            refA.clear();
        }
        return 1;
    }

    public int addPK2() {
        int r;
        if (sequence_name == null) {
            r = this.createSQ();
            if (r == -1) {
                return -1;
            }
        }
        r = this.changeRefferencesN();
        if (r == -1) {
            return -1;
        }
        return 1;
    }

    public int checkTablesNOTNULL() {
        query = "SELECT TABLE_NAME FROM USER_TABLES";//preia toate denumirile de tabele din bd
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                allTables.add(rs.getString("TABLE_NAME"));//adauga denumirile intr-o lista
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        query = "SELECT TABLE_NAME, COLUMN_NAME FROM USER_TAB_COLUMNS WHERE NULLABLE='N' MINUS SELECT ucc.TABLE_NAME,ucc.COLUMN_NAME FROM (USER_CONSTRAINTS uc JOIN USER_CONS_COLUMNS ucc ON uc.CONSTRAINT_NAME=ucc.CONSTRAINT_NAME) JOIN USER_TAB_COLUMNS utc ON utc.TABLE_NAME=uc.TABLE_NAME AND utc.COLUMN_NAME=ucc.COLUMN_NAME WHERE uc.CONSTRAINT_TYPE='P'";
        try {//selecteaza numele de tabel si coloanele care nu sunt pk, dar sunt not null
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                flexible.add(rs.getString("TABLE_NAME"));//adaugam numele tabelelor respective in lista
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        for (int i = 0; i < allTables.size(); i++) {//parcurgem lista tuturor tabelelor
            if (!flexible.contains(allTables.get(i))) {//daca numele tabelului nu se afla in flexible, adica, nu are coloane not null in afara de pk
                badTables.add(allTables.get(i));//adaugam numele de tabel in lista tabelelor proiectate gresit
            }
        }
        flexible.clear();
        if (badTables.isEmpty()) {//daca lista tabelelor proiectate gresit este goala=> toate tabelele sunt proiectate corect
            return 0;
        } else {
            return 1;
        }
    }

    public void showBadTables() {
        new F5(badTables).setVisible(true);//afiseaza fereastra 5
    }

    public ArrayList<String> getColumns(String tableName) {
        flexible.clear();
        ArrayList<String> column = new ArrayList<String>();
        query = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME='" + tableName + "' MINUS SELECT ucc.COLUMN_NAME FROM (USER_CONSTRAINTS uc JOIN USER_CONS_COLUMNS ucc ON uc.CONSTRAINT_NAME=ucc.CONSTRAINT_NAME) JOIN USER_TAB_COLUMNS utc ON  utc.TABLE_NAME=uc.TABLE_NAME AND  utc.COLUMN_NAME=ucc.COLUMN_NAME WHERE uc.CONSTRAINT_TYPE='P' AND utc.TABLE_NAME='" + tableName + "'";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                flexible.add(rs.getString("COLUMN_NAME"));
            }
            query = "SELECT COUNT(*) FROM " + tableName;
            rs = st.executeQuery(query);
            rs.next();
            int total = rs.getInt(1);
            for (int j = 0; j < flexible.size(); j++) {
                query = "SELECT COUNT(" + flexible.get(j) + ") FROM " + tableName;
                rs = st.executeQuery(query);
                rs.next();
                int nr = rs.getInt(1);
                if (total - nr == 0) {
                    column.add(flexible.get(j));
                }
            }
            if (!column.isEmpty()) {
                column.add("G");
                return column;
            } else {
                flexible.add("B");
                return flexible;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    public int addNOTNULL(String tabN, String colN) {
        query = "ALTER TABLE " + tabN + " MODIFY( " + colN + " NOT NULL)";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return 1;
    }

    public int completeNULLvalues(String tabN, String colN) {
        query = "SELECT DATA_TYPE, DATA_LENGTH, DATA_PRECISION FROM USER_TAB_COLUMNS WHERE TABLE_NAME='" + tabN + "' AND COLUMN_NAME='" + colN + "'";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            String dType = rs.getString("DATA_TYPE");
            String dLength = rs.getString("DATA_LENGTH");
            String dPrecision = rs.getString("DATA_PRECISION");
            String value = null;
            boolean b;
            if (dPrecision != null) {
                b = true;
                String mesaj = "Introduce a value of type " + dType + "(" + dPrecision + ") to fill null fields from the column " + colN;
                while (b) {
                    value = JOptionPane.showInputDialog(null, mesaj, "Attention", JOptionPane.OK_OPTION);
                    query = "UPDATE " + tabN + " SET " + colN + "=" + value + " WHERE " + colN + " IS NULL";
                    try {
                        rs = st.executeQuery(query);
                        b = false;
                    } catch (SQLException sql) {
                        int code = sql.getErrorCode();
                        if (code == 936) {
                            mesaj = "You did not introduce anything. Introduce a value of type " + dType + "(" + dPrecision + ") to fill null fields from the column " + colN;
                        } else if (code == 904) {
                            mesaj = "The introduced value is not numerical. Introduce a value of type " + dType + "(" + dPrecision + ") to fill null fields from the column " + colN;
                        } else if (code == 1438) {
                            mesaj = "The introduced value is too big. Introduce a value of type " + dType + "(" + dPrecision + ") to fill null fields from the column " + colN;
                        } else {
                            JOptionPane.showMessageDialog(null, sql.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            return -1;
                        }
                    }
                }
            } else {
                b = true;
                if (dType.equals("DATE")) {
                    String format = "";
                    query = "SELECT value FROM V$NLS_PARAMETERS WHERE parameter = 'NLS_DATE_FORMAT'";
                    try {
                        rs = st.executeQuery(query);
                        rs.next();
                        format = rs.getString(1);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    }
                    String mesaj = "Introduce a value of type " + dType + "(" + format + ") to fill null fields from the column " + colN;
                    while (b) {
                        value = JOptionPane.showInputDialog(null, mesaj, "Attention", JOptionPane.OK_OPTION);
                        query = "UPDATE " + tabN + " SET " + colN + "=TO_DATE('" + value + "','" + format + "') WHERE " + colN + " IS NULL";
                        try {
                            rs = st.executeQuery(query);
                            b = false;
                        } catch (SQLException sq) {
                            int code = sq.getErrorCode();
                            if (code == 1843 || code == 1858 || code == 1841 || code == 1830 || code == 1861) {
                                mesaj = "Eronate value. Introduce a value of type " + dType + "(" + format + ") to fill null fields from the column " + colN;
                                JOptionPane.showMessageDialog(null, sq.getMessage() + " " + code, "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, sq.getMessage() + " " + code, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                            return -1;
                        }
                    }
                } else {
                    String mesaj = "Introduce a value of type " + dType + "(" + dLength + ") Introduce a value of type " + colN;
                    while (b) {
                        value = JOptionPane.showInputDialog(null, mesaj, "Attention", JOptionPane.OK_OPTION);
                        if (value.length() == 0) {
                            mesaj = "You did not introduce anything. Introduce a value of type " + dType + "(" + dLength + ") to fill null fields from the column " + colN;
                        } else if (value.length() > Integer.parseInt(dLength)) {
                            mesaj = "Introduced value is too big. Introduce a value of type " + dType + "(" + dLength + ") to fill null fields from the column " + colN;
                        } else {
                            b = false;
                        }
                    }
                    query = "UPDATE " + tabN + " SET " + colN + "='" + value + "' WHERE " + colN + " IS NULL";
                    rs = st.executeQuery(query);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        int rs = this.addNOTNULL(tabN, colN);
        if (rs == 1) {
            return 1;
        } else {
            return -1;
        }
    }
}
