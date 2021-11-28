package com.camunda;

import com.google.gson.Gson;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// This class gets the information about the projects and parses them into JSON format (from a special entity to JSON)
public class ProjectsParser implements JavaDelegate {

    public List<String> getElements() throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.createConnection();

        // Query to database to get everything from PROJECTS
        String query = "SELECT * FROM PROJECTS";
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(query);

        // Array to store the data transformed later
        List<String> projectslist = new ArrayList<>();

        // loop on the result set to get the data into variables
        while (res.next()) {
            int projectid = res.getInt(1);
            String projectname = res.getString(2);
            int studentsnumber = res.getInt(3);
            String supervisor = res.getString(4);

            // transforms the data collected into a Project Entity (customarily created)
            ProjectEntity project = new ProjectEntity(projectid, projectname, studentsnumber, supervisor);
            // parses the entity into JSON format using the GSON library/addon
            String jsonProject = new Gson().toJson(project);

            projectslist.add(jsonProject);
        }

        conn.close();
        return projectslist;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // gets the data
        List<String> prjl = getElements();
        // defines the variable into the camunda scope
        delegateExecution.setVariable("AVAILABLE_PROJECTS", Spin.JSON(prjl));
    }

}
