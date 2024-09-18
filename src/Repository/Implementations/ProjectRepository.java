package Repository.Implementations;

import Model.Entities.Client;
import Model.Entities.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProjectRepository extends BaseRepository<Project> {

    public ProjectRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Project mapResultSetToEntity(ResultSet resultSet) throws SQLException {

        Project project = new Project();
        project.setId(resultSet.getInt("id"));
        project.setClient(resultSet.getInt("client_id"));
        project.setProjectName(resultSet.getString("projectname"));
        project.setProfitMargin(resultSet.getDouble("profitmargin"));
        project.setTotalCost(resultSet.getDouble("totalcost"));

        return project;

    }


    @Override
    public String getTableName() {
        return "projects";
    }

    @Override
    public void setParameters(PreparedStatement statement, Project project) throws SQLException {
        statement.setInt(1, project.getId());
        statement.setInt(2, project.getClient().getId());
        statement.setString(3, project.getProjectName());
        statement.setDouble(4, project.getProfitMargin());
        statement.setDouble(5, project.getTotalCost());
        statement.setString(6, project.getStatus().toString());
    }


    @Override
    public String getInsertQuery() {
        return "INSERT INTO projects (id, client_id, projectname, profitmargin, totalcost, projectstatus) VALUES (?, ?, ?, ?, ?, ?)";
    }


    @Override
    public String getUpdateQuery() {
        return "UPDATE projects SET client_id = ?, projectname = ?, profitmargin = ?, totalcost = ?, projectstatus = ? WHERE id = ?";
    }


}
