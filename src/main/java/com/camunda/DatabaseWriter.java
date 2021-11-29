package com.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;

// Saves the inputted project into the database PROJECTS
public class DatabaseWriter implements JavaDelegate {

    // creates the PROJECTS Database if it does not exist
    private void createDBProjects() throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        String query = "create table if not exists PROJECTS (PROJECTID int not null primary key auto_increment, PROJECTNAME varchar(255) not null, STUDENTSNBR int not null, ADVISOR varchar(255) not null);";
        Statement st = conn.createStatement();
        st.execute(query);
    }

    // checks if the database PROJECTS is empty
    private boolean isProjectsEmpty() throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        String query = "select count(*) from PROJECTS";
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
    private void initDbProjects() throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        String query = "insert into PROJECTS (PROJECTNAME, STUDENTSNBR, ADVISOR) values ( ?, ?, ? )";
        PreparedStatement pst = conn.prepareStatement(query);

        pst.setString(1, "T'asCapté? An online transcriber using Deep Learning");
        pst.setInt(2, 2);
        pst.setString(3, "Youssef Bakouny");
        pst.addBatch();

        pst.setString(1, "SmartShip - The discovery of the Milky Way using CubeSat");
        pst.setInt(2, 2);
        pst.setString(3, "Elias Rachid");
        pst.addBatch();

        pst.setString(1, "TravelDream - An extended reality journey simulation");
        pst.setInt(2, 2);
        pst.setString(3, "Melhem Helou (El)");
        pst.addBatch();

        pst.setString(1, "AI Application for elderly people");
        pst.setInt(2, 2);
        pst.setString(3, "Rima Kilany Chamoun");
        pst.addBatch();

        pst.setString(1, "International Call - An application sharing worldwide issues");
        pst.setInt(2, 3);
        pst.setString(3, "Maroun Chamoun");
        pst.addBatch();

        pst.setString(1, "Meteo Prediction using Machine Learning");
        pst.setInt(2, 2);
        pst.setString(3, "Youssef Bakouny");
        pst.addBatch();

        pst.setString(1, "Station Spot - The placement of bike sharing systems");
        pst.setInt(2, 3);
        pst.setString(3, "Rima Kilany Chamoun");
        pst.addBatch();

        pst.setString(1, "Des ailes d'élite - IoT Network combining Middle Eastern airports");
        pst.setInt(2, 4);
        pst.setString(3, "Flavia Khatounian Rajji (El)");
        pst.addBatch();

        pst.setString(1, "Speed Tourism - Mobile App facilitating travel for people in a hurry");
        pst.setInt(2, 3);
        pst.setString(3, "Rima Kilany Chamoun");
        pst.addBatch();

        pst.setString(1, "An internal app for automation of HR rejection mails at Murex");
        pst.setInt(2, 2);
        pst.setString(3, "Youssef Bakouny");
        pst.addBatch();

        pst.setString(1, "Computer Vision and AI applied to Locksmith Industry");
        pst.setInt(2, 3);
        pst.setString(3, "Georges Sakr");
        pst.addBatch();

        pst.setString(1, "Shipping specific SAP ERP");
        pst.setInt(2, 3);
        pst.setString(3, "Marc Ibrahim");
        pst.addBatch();

        pst.setString(1, "Protecting C Applications using Blockchain Technology");
        pst.setInt(2, 4);
        pst.setString(3, "Maroun Chamoun");
        pst.addBatch();

        pst.setString(1, "How to deliver faster shipments internationally");
        pst.setInt(2, 4);
        pst.setString(3, "Maroun Chamoun");
        pst.addBatch();

        pst.setString(1, "White Music applied to Music Therapy");
        pst.setInt(2, 2);
        pst.setString(3, "Hadi Sawaya");
        pst.addBatch();

        pst.executeBatch();
        conn.commit();
        dbc.closeConnection(conn);

    }

    private void writer(String projectname, int studentsnbr, String advisor) throws SQLException {
        // if database GROUPS does not exist it will be created
        createDBProjects();

        // to initialize the database values (disable if you want it to be empty)
        if (isProjectsEmpty()) {
            initDbProjects();
        }

        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        // Query to save
        String query = "insert into PROJECTS (PROJECTNAME, STUDENTSNBR, ADVISOR)" + " values ( ?, ?, ? )";

        PreparedStatement insertst = conn.prepareStatement(query);
        // Setting variables correctly
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
