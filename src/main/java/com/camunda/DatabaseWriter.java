package com.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Saves the inputted project into the database PROJECTS
public class DatabaseWriter implements JavaDelegate {

    private void writer(String projectname, int studentsnbr, String advisor) throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        // Query to save
        String query = "insert into PROJECTS (PROJECTNAME, STUDENTSNBR, ADVISOR)" + " values ( ?, ?, ? )";

        PreparedStatement insertst = conn.prepareStatement(query);
        // Setting variables correctly (the 3 ?)
        insertst.setString(1, projectname);
        insertst.setInt(2, studentsnbr);
        insertst.setString(3, advisor);

        insertst.execute();

        dbc.closeConnection(conn);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // get the camunda variables
        String projectname = (String) delegateExecution.getVariable("projectname");
        int studentsnbr = (int) delegateExecution.getVariable("studentsnbr");
        String advisor = (String) delegateExecution.getVariable("advisor");

        // executes the function to save
        writer(projectname, studentsnbr, advisor);
    }
}
