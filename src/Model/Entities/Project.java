package Model.Entities;

import Model.Enums.ProjectStatus;
import Model.Interfaces.Identifiable;

import java.util.ArrayList;
import java.util.List;

public class Project implements Identifiable {

    private int id;
    private String projectName;
    private double profitMargin;
    private double totalCost;
    private ProjectStatus status;
    private Client client;
    private List<Component> components;
    private List<Material> materials;
    private List<Labor> labors;
//    private Estimate estimate ;


    // Constructor
    public Project( String projectName, double profitMargin, double totalCost, ProjectStatus status, Client client,List<Component> components) {
        this.projectName = projectName;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.status = status;
        this.client = client;
        if (components != null) {
            this.components = new ArrayList<>(components);
        }
    }

    public Project() {
        this.components = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.labors = new ArrayList<>();
    }


    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
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

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Labor> getLabors() {
        return labors;
    }

    public void setLabors(List<Labor> labors) {
        this.labors = labors;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                ", status=" + status +
                ", client=" + client +
                ", components=" + components +
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
