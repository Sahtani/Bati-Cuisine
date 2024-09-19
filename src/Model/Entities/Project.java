package Model.Entities;

import Model.Enums.ProjectStatus;

public class Project {

    private int id;
    private String projectName;
    private double profitMargin;
    private double totalCost;
    private ProjectStatus status;
    private Client client;
//    private List<Component> components;
//    private Estimate estimate ;


    // Constructor
    public Project(String projectName, double profitMargin, double totalCost, ProjectStatus status, Client client) {
        this.projectName = projectName;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.status = status;
        this.client = client;
//        this.components = new ArrayList<>();
    }

    public Project() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                ", status=" + status +
                ", client=" + client +
                '}';
    }
//    public List<Component> getComponents() {
//        return components;
//    }
//
//    public void setComponents(List<Component> components) {
//        this.components = components;
//    }

}
