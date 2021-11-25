package com.camunda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class to create a connection to the database and able to close it
// Made mostly for the purpose of removing code duplication since
// we're making a lot of queries to the database
public class DatabaseConnector {

    Connection createConnection() throws SQLException {
        String myDriver = "org.h2.Driver";
        String Url = "jdbc:h2:file:~/src/main/resources/data/camunda-db";
        try {
            Class.forName(myDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(Url, "sa", "sa");
        return conn;
    }

    void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
