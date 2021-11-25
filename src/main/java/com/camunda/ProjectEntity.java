package com.camunda;

// Special object made to parse the project into JSON format
// Is reference as an entity since it represents a database schema
public class ProjectEntity {

    // Attributes
    private int projectid;
    private String projectname;
    private int studentsnumber;
    private String advisor;

    // Constructor
    public ProjectEntity(int projectid, String projectname, int studentsnumber, String advisor) {
        this.projectid = projectid;
        this.projectname = projectname;
        this.studentsnumber = studentsnumber;
        this.advisor = advisor;
    }
}
