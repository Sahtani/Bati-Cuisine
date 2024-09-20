package Repository.Implementations;

import Model.Entities.Client;
import Model.Entities.Project;
import Service.Implementations.ClientService;
import Service.Interfaces.IClientService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProjectRepository extends BaseRepository<Project> {
     private IClientService clientService  ;
    public ProjectRepository(Connection connection, IClientService clientService ) {
        super(connection);
        this.clientService = clientService ;
    }

    @Override
    public Project mapResultSetToEntity(ResultSet resultSet) throws SQLException {

        Project project = new Project();
        int clientId = resultSet.getInt("client_id");
        Client client = clientService.getClientById(clientId);

        project.setClient(client);

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
        statement.setInt(1, project.getClient().getId());
        statement.setString(2, project.getProjectName());
        statement.setDouble(3, project.getProfitMargin());
        statement.setDouble(4, project.getTotalCost());
        statement.setString(5, String.valueOf(project.getStatus().ONGOING));
    }


    @Override
    public String getInsertQuery() {
        return "INSERT INTO projects (client_id, projectname, profitmargin, totalcost, projectstatus) VALUES (?, ?, ?, ?, CAST(? AS projectstatus))";
    }



    @Override
    public String getUpdateQuery() {
        return "UPDATE projects SET client_id = ?, projectname = ?, profitmargin = ?, totalcost = ?, projectstatus = ? WHERE id = ?";
    }


}
