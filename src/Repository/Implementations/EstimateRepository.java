package Repository.Implementations;

import Model.Entities.Client;
import Model.Entities.Estimate;
import Model.Entities.Project;
import Service.Interfaces.IEstimateService;
import Service.Interfaces.IProjectService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EstimateRepository extends BaseRepository<Estimate> {


    private IProjectService projectService;

    public EstimateRepository(Connection connection, IProjectService projectService) {
        super(connection);
        this.projectService = projectService;
    }


    @Override
    protected Estimate mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Estimate estimate = new Estimate();
        estimate.setId(resultSet.getInt("id"));

        int projectId = resultSet.getInt("project_id");
        Optional<Project> project = projectService.getProjectById(projectId);

        estimate.setProject(project.get());

        estimate.setEstimatedAmount(resultSet.getDouble("estimatedamount"));
        estimate.setIssueDate(resultSet.getDate("issuedate").toLocalDate());
        estimate.setValidityDate(resultSet.getDate("validitydate").toLocalDate());
        estimate.setAccepted(resultSet.getBoolean("accepted"));

        return estimate;


    }

    @Override
    protected String getTableName() {
        return "estimates";
    }

    @Override
    protected void setParameters(PreparedStatement statement, Estimate estimate) throws SQLException {

        statement.setInt(1, estimate.getProject().getId());
        statement.setDouble(2, estimate.getEstimatedAmount());
        statement.setDate(3, java.sql.Date.valueOf(estimate.getIssueDate()));
        statement.setDate(4, java.sql.Date.valueOf(estimate.getValidityDate()));

    }


    @Override
    protected String getInsertQuery() {
        return "INSERT INTO estimates (project_id, estimatedamount, issuedate, validitydate) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "";
    }

    public void updateStatus(Estimate estimate, boolean newStatus) throws SQLException {
        estimate.setAccepted(newStatus);
        String query = "UPDATE estimates SET accepted = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, estimate.isAccepted());
            statement.setInt(2, estimate.getId());
            statement.executeUpdate();
        }
    }
}
