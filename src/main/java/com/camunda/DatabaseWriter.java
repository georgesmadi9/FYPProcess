package com.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseWriter implements JavaDelegate {

    DatabaseConnector dbc = new DatabaseConnector();

    private void writer(String projectname, int studentsnbr, String advisor) throws SQLException {
        Connection conn = dbc.createConnection();

        String query = "insert into PROJECTS (name, grpnbr, encadrant)" + " values ( ?, ?, ? )";

        PreparedStatement insertst = conn.prepareStatement(query);
        insertst.setString(1, projectname);
        insertst.setInt(2, studentsnbr);
        insertst.setString(3, advisor);
        //insertst.setString(4, document);

        insertst.execute();

        dbc.closeConnection(conn);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String projectname = (String) delegateExecution.getVariable("projectname");
        int studentsnbr = (int) delegateExecution.getVariable("studentsnbr");
        String advisor = (String) delegateExecution.getVariable("advisor");
        //String document = (String) delegateExecution.getVariable("document");
        writer(projectname, studentsnbr, advisor);
    }
}
