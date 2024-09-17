package Model.Entities;

import java.time.LocalDate;

public class Estimate {

    private double estimatedAmount;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private boolean accepted;
    private Project project ;

    // Constructor
    public Estimate(double estimatedAmount, LocalDate issueDate, LocalDate validityDate, boolean accepted, Project project) {
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.accepted = accepted;
        this.project = project ;
    }

    // Getters and Setters
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

}
