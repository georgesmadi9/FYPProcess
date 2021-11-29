package com.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


 // This script gets the choices that the student group made
 // and save their choices in the GROUPS database
public class ChoiceSaver implements JavaDelegate {

     // creates the GROUPS Database if it does not exist
    private void createDBGroups() throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        String query = "create table if not exists GROUPS (CHOICEID int not null primary key auto_increment, PROJECTID int not null, STUDENTS varchar(255) not null);";
        Statement st = conn.createStatement();
        st.execute(query);

        dbc.closeConnection(conn);
    }

    // checks if the database GROUPS is empty
    private boolean isGroupsEmpty() throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        String query = "select count(*) from GROUPS";
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(query);

        int count = 0;
        while (res.next()) {
            count = res.getInt("count");
        }

        dbc.closeConnection(conn);

        return count == 0;
    }

     // default values initializer for the database (can be disabled)
    private void initDbGroups() throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        String query = "insert into GROUPS (projectid, students) values ( ?, ? )";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setInt(1,5);
        pst.setString(2, "\"Jim Gaftet, Sarah Farah, Lechauna Mafwakandi\"");
        pst.addBatch();
        pst.setInt(1,8);
        pst.setString(2, "\"Jim Gaftet, Sarah Farah, Lechauna Mafwakandi, Patricia Lagauffre\"");
        pst.addBatch();
        pst.setInt(1,10);
        pst.setString(2, "\"Peter Harfouche, Yves Daaboul\"");
        pst.addBatch();
        pst.setInt(1,13);
        pst.setString(2, "\"Peter Harfouche, Yves Daaboul, Mikhaela Charif, Theoura Tawahi\"");
        pst.addBatch();
        pst.setInt(1,8);
        pst.setString(2, "\"Namadia Afwasahi, Laurent Mestret, Claude Dartoit, Koumba Baraji\"");
        pst.addBatch();
        pst.setInt(1,12);
        pst.setString(2, "\"Namadia Afwasahi, Laurent Mestret, Claude Dartoit\"");
        pst.addBatch();
        pst.setInt(1,1);
        pst.setString(2, "\"Vincent Delaprairie, Clemence Castel\"");
        pst.addBatch();
        pst.setInt(1,6);
        pst.setString(2, "\"Vincent Delaprairie, Clemence Castel\"");
        pst.addBatch();
        pst.setInt(1,2);
        pst.setString(2, "\"Alexandra Pronet, Clemntine Larage\"");
        pst.addBatch();
        pst.setInt(1,3);
        pst.setString(2, "\"Alexandra Pronet, Clemntine Larage\"");
        pst.addBatch();
        pst.setInt(1,3);
        pst.setString(2, "\"Raphael Sassine, Taylor Swift\"");
        pst.addBatch();
        pst.setInt(1,15);
        pst.setString(2, "\"Raphael Sassine, Taylor Swift\"");
        pst.addBatch();
        pst.setInt(1,3);
        pst.setString(2, "\"Raphael Sassine, Taylor Swift\"");
        pst.addBatch();
        pst.setInt(1,15);
        pst.setString(2, "\"Raphael Sassine, Taylor Swift\"");
        pst.addBatch();
        pst.setInt(1,10);
        pst.setString(2, "\"Magic Systeme, Eminem\"");
        pst.addBatch();
        pst.setInt(1,15);
        pst.setString(2, "\"Magic Systeme, Eminem\"");
        pst.addBatch();
        pst.setInt(1,4);
        pst.setString(2, "\"San Tahiti, Loic Frenel\"");
        pst.addBatch();
        pst.setInt(1,2);
        pst.setString(2, "\"San Tahiti, Loic Frenel\"");
        pst.addBatch();
        pst.setInt(1,5);
        pst.setString(2, "\"Mouafac Ghattas, Anas Daou, Karine Ismail\"");
        pst.addBatch();
        pst.setInt(1,8);
        pst.setString(2, "\"Mouafac Ghattas, Anas Daou, Karine Ismail, Rola Hadi\"");
        pst.addBatch();
        pst.setInt(1,6);
        pst.setString(2, "\"Maxime Ouisant, Maxine Bleze\"");
        pst.addBatch();
        pst.setInt(1,4);
        pst.setString(2, "\"Maxime Ouisant, Maxine Bleze\"");
        pst.addBatch();
        pst.setInt(1,6);
        pst.setString(2, "\"Robert De la Fistiniere, Sebastian Castellianos, Justine De Karakalla, Paola Sharabaty\"");
        pst.addBatch();
        pst.setInt(1,4);
        pst.setString(2, "\"Robert De la Fistiniere, Sebastian Castellianos, Justine De Karakalla, Paola Sharabaty\"");
        pst.addBatch();
        pst.setInt(1,8);
        pst.setString(2, "\"Carole De La Musique, Charlotte Ines, Chanis Mondi, Candice Boisson\"");
        pst.addBatch();
        pst.setInt(1,13);
        pst.setString(2, "\"Carole De La Musique, Charlotte Ines, Chanis Mondi\"");
        pst.addBatch();
        pst.setInt(1,7);
        pst.setString(2, "\"Christina Cordula, Stephane Plaza, Jasmine Baracuda\"");
        pst.addBatch();
        pst.setInt(1,9);
        pst.setString(2, "\"Christina Cordula, Stephane Plaza, Jasmine Baracuda\"");
        pst.addBatch();

        pst.executeBatch();
        conn.commit();
        dbc.closeConnection(conn);

    }

    // gets lists of students in string format (name, name, ...) depending on the number of students for a project
    private String getOnly(List<String> names, int nbr) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < nbr; i++) {
            if (i == nbr - 1) {
                s.append(names.get(i));
            } else {
                s.append(names.get(i)).append(", ");
            }
        }
        return s.toString();
    }

    private String makeQuery(List<String> names, int grpc) throws SQLException {
        // if database GROUPS does not exist it will be created
        createDBGroups();

        // to initialize the database values (disable if you want it to be empty)
        if (isGroupsEmpty()) {
            initDbGroups();
        }

        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        String query = "SELECT STUDENTSNBR FROM PROJECTS WHERE PROJECTID = " + grpc;
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(query);

        String list = "";

        if (res.next()) {
            list = getOnly(names, res.getInt(1));
        }

        dbc.closeConnection(conn);

        return list;
    }

    private void saveChoices(String list, int grpc) throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        String query = "INSERT INTO GROUPS (PROJECTID, STUDENTS) " + "VALUES ( ? , ? )";
        PreparedStatement insertst = conn.prepareStatement(query);

        insertst.setInt(1, grpc);
        insertst.setString(2, list);
        insertst.execute();

        dbc.closeConnection(conn);
    }

    private void choiceDefinition(List<String> names, int grpc1, int grpc2) throws SQLException {
        String list1 = makeQuery(names, grpc1);
        String list2 = makeQuery(names, grpc2);

        saveChoices(list1, grpc1);
        saveChoices(list2, grpc2);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int grpc1 = (int) delegateExecution.getVariable("studentchoice1");
        int grpc2 = (int) delegateExecution.getVariable("studentchoice2");
        String name1 = (String) delegateExecution.getVariable("name1");
        String name2 = (String) delegateExecution.getVariable("name2");
        String name3 = (String) delegateExecution.getVariable("name3");
        String name4 = (String) delegateExecution.getVariable("name4");

        List<String> names = new ArrayList<>();
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);

        choiceDefinition(names, grpc1, grpc2);
    }
}
