package com.camunda;

public class ProjectEntity {

    private String projectname;
    private int studentsnumber;
    private String advisor;
    private String filepath;

    public ProjectEntity(String projectname, int studentsnumber, String advisor, String filepath) {
        this.projectname = projectname;
        this.studentsnumber = studentsnumber;
        this.advisor = advisor;
        this.filepath = filepath;
    }

    public ProjectEntity(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectname() {
        return projectname;
    }
}
