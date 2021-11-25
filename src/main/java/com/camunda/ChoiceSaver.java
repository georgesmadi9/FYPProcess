package com.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/************************************************************
 * This script gets the choices that the student group made
 * and save their choices in the GROUPS database
 *
 * /!\ The way this operation was handled is somewhat
 * inefficient, but it's best to keep it this way
 * for simplicity's sake /!\
 ************************************************************/


public class ChoiceSaver implements JavaDelegate {

    private String getOnly(List<String> names, int nbr) {
        String s = "";
        for (int i = 0; i < nbr; i++) {
            if (i == nbr - 1) {
                s += names.get(i);
            } else {
                s += names.get(i) + ", ";
            }
        }
        return s;
    }

    private String makeQuery(List<String> names, int grpc) throws SQLException {
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

        List<String> names = new ArrayList<String>();
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);

        choiceDefinition(names, grpc1, grpc2);
    }
}
