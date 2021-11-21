package com.camunda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    Connection createConnection() throws SQLException {
        String myDriver = "org.h2.Driver";
        String Url = "jdbc:h2:file:~/src/main/resources/data/camunda-db";
        try {
            Class.forName(myDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection( Url , "sa", "sa");
        return conn;
    }

    void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
