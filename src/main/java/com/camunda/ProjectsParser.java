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


public class ProjectsParser implements JavaDelegate {

    DatabaseConnector dbc = new DatabaseConnector();

    public List<String> getElements() throws SQLException {
        Connection conn = dbc.createConnection();

        String query = "SELECT * FROM PROJECTS";
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(query);

        List<String> projectslist = new ArrayList<String>();

        while (res.next()) {
            String projectname = res.getString(2);
            int studentsnumber = res.getInt(3);
            String supervisor = res.getString(4);
            String filepath = res.getString(5);

            ProjectEntity project = new ProjectEntity(projectname, studentsnumber, supervisor, filepath); /*, studentsnumber, supervisor, filepath*/
            String jsonProject = new Gson().toJson(project);
            System.out.println(jsonProject);
            projectslist.add(jsonProject);
        }

        conn.close();
        return projectslist;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> prjl = getElements();

        delegateExecution.setVariable("AVAILABLE_PROJECTS", Spin.JSON(prjl));
    }

}
