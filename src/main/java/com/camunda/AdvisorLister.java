package com.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvisorLister implements JavaDelegate {

    private Map<String, ArrayList<String>> getLists(String advisorname) throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        // Query on both databases that get this data stucture:
        // Students Names (students) - Project Names (projectname) - Advisor Names (advisor)
        // This query will only get the projects that have been chosen
        String query = "select GROUPS.students, PROJECTS.projectname, PROJECTS.advisor from GROUPS " +
                "INNER JOIN PROJECTS on GROUPS.PROJECTID = PROJECTS.PROJECTID;";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet res = ps.executeQuery();

        // Hash map that contains the students that chose a project
        Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

        // ArrayLists to store the results of the query
        // Not optimized /!\
        List<String> namelist = new ArrayList<String>();
        List<String> pnames = new ArrayList<String>();

        // Fill the ArrayLists
        while (res.next()) {
            String advisor = res.getString(3);
            if (advisor.equals(advisorname)) {
                namelist.add(res.getString(1));
                pnames.add(res.getString(2));
            }
        }

        // close db connection
        dbc.closeConnection(conn);

        // Fill the HashMap
        for (int i = 0; i < pnames.size(); i++) {
            String pname = pnames.get(i);
            String nameSet = namelist.get(i);
            if (!map.containsKey(pname)) {
                map.put(pname, new ArrayList<String>());
            }
            map.get(pname).add(nameSet);
        }

        return map;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // get the name of the advisor from the login form
        String advisorname = (String) delegateExecution.getVariable("AdvisorID");

        // make the hashmap
        Map<String, ArrayList<String>> map = getLists(advisorname);

        // define the projects variable (the hashmap)
        delegateExecution.setVariable("ADVISOR_PROJECTS", Spin.JSON(map));
    }
}
