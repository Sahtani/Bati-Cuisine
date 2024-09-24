package Repository.Implementations;

import Model.Entities.Estimate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EstimateRepository {

    private Connection connection;

    public EstimateRepository(Connection connection) {
        this.connection = connection;
    }

    public void create(Estimate estimate) throws SQLException {
        String query = "INSERT INTO estimates (project_id, estimatedamount, issuedate, validitydate, accepted) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, estimate.getProject().getId());
            statement.setDouble(2, estimate.getEstimatedAmount());
            statement.setDate(3, java.sql.Date.valueOf(estimate.getIssueDate()));
            statement.setDate(4, java.sql.Date.valueOf(estimate.getValidityDate()));
            statement.setBoolean(5, estimate.isAccepted());
            statement.executeUpdate();
        }
    }
}
