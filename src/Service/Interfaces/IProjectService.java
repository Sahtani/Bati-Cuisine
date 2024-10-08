package Service.Interfaces;

import Model.Entities.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IProjectService {

    Project addProject(Project project) throws SQLException;

    void updateProject(int projectId, Project project) throws SQLException;

    Optional<Project> deleteProject(int projectId) throws SQLException;

    List<Project> getAllProject() throws SQLException;

    Optional<Project> getProjectById(int projectId) throws SQLException;

    void updateTotalCost(Project project, double newTotalCost) throws SQLException;

    void updateProfitMargin(Project project, double newProfitMargin) throws SQLException;

}
