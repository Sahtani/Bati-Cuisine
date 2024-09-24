package Repository.Implementations;

import Model.Entities.*;
import Model.Enums.ProjectStatus;
import Service.Implementations.ClientService;
import Service.Interfaces.IClientService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepository extends BaseRepository<Project> {

     private final IClientService clientService  ;
     private final MaterialRepository materialRepository;
     private final LaborRepository laborRepository;

    public ProjectRepository(Connection connection, IClientService clientService ) {
        super(connection);
        this.clientService = clientService ;
        this.materialRepository = new MaterialRepository(connection);
        this.laborRepository = new LaborRepository(connection);
    }

    @Override
    public Project mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("id"));
        int clientId = resultSet.getInt("client_id");
        Client client = clientService.getClientById(clientId);
        project.setClient(client);
        project.setProjectName(resultSet.getString("projectname"));
        project.setProfitMargin(resultSet.getDouble("profitmargin"));
        project.setTotalCost(resultSet.getDouble("totalcost"));


        List<Material> materials = materialRepository.getMaterialsByProjectId(project.getId());
        List<Labor> labors = laborRepository.getLaborsByProjectId(project.getId());

        List<Component> components = new ArrayList<>();
        components.addAll(materials);
        components.addAll(labors);

        project.setComponents(components);



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
        statement.setString(5, String.valueOf(ProjectStatus.ONGOING));
    }


    @Override
    public String getInsertQuery() {
        return "INSERT INTO projects (client_id, projectname, profitmargin, totalcost, projectstatus) VALUES (?, ?, ?, ?, CAST(? AS projectstatus))";
    }



    @Override
    public String getUpdateQuery() {
        return "UPDATE projects SET client_id = ?, projectname = ?, profitmargin = ?, totalcost = ?, projectstatus = ? WHERE id = ?";
    }

    public void updateTotalCost(int projectId, double newTotalCost) throws SQLException {
        String updateTotalCostQuery = "UPDATE projects SET totalcost = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateTotalCostQuery)) {
            statement.setDouble(1, newTotalCost);
            statement.setInt(2, projectId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Total cost updated successfully for project ID: " + projectId);
            } else {
                System.out.println("Project not found with ID: " + projectId);
            }
        }
    }
    public boolean updateProfitMargin(Project project, double newProfitMargin) throws SQLException {

        project.setProfitMargin(newProfitMargin);
        String updateProfitMarginQuery = "UPDATE projects SET profitmargin = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateProfitMarginQuery)) {
            statement.setDouble(1, newProfitMargin);
            statement.setInt(2, project.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTotalCost(Project project, double newTotalCost) throws SQLException {

        project.setTotalCost(newTotalCost);
        String updateTotalCostQuery = "UPDATE projects SET totalcost = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateTotalCostQuery)) {
            statement.setDouble(1, newTotalCost);
            statement.setInt(2, project.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }







}
