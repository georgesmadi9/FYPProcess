package com.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectsParser implements JavaDelegate {

    DatabaseConnector dbc = new DatabaseConnector();

    public List<ProjectEntity> getElements() throws SQLException {
        Connection conn = dbc.createConnection();

        String query = "SELECT * FROM PROJECTS";
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(query);

        List<ProjectEntity> projectslist = new ArrayList<ProjectEntity>();

        while (res.next()) {
            String projectname = res.getString(2);
            //int studentsnumber = res.getInt(2);
            //String supervisor = res.getString(3);
            //String filepath = res.getString(4);

            ProjectEntity project = new ProjectEntity(projectname); /*, studentsnumber, supervisor, filepath*/

            projectslist.add(project);
        }

        conn.close();
        return projectslist;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<ProjectEntity> prjl = getElements();

        //System.out.println(projects.values());

        delegateExecution.setVariable("AVAILABLE_PROJECTS",  Spin.JSON(prjl));
    }
}
