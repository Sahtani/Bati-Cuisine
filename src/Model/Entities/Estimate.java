package Model.Entities;

import Model.Interfaces.Identifiable;

import java.time.LocalDate;

public class Estimate implements Identifiable {
    private int id;
    private Project project;
    private double estimatedAmount;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private boolean accepted;


    public Estimate(int id, Project project, double estimatedAmount, LocalDate issueDate, LocalDate validityDate) {
        this.id = id;
        this.project = project;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;

    }
    public Estimate(){

    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Estimate{" +
                "id=" + id +
                ", projectId=" + project +
                ", estimatedAmount=" + estimatedAmount +
                ", issueDate=" + issueDate +
                ", validityDate=" + validityDate +
                ", accepted=" + accepted +
                '}';
    }
}

