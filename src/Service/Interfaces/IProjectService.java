package Service.Interfaces;

import Model.Entities.Client;
import Model.Entities.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IProjectService {

    void addProject(Project project) throws SQLException;

    void updateProject(int projectId, Project project) throws SQLException;

    void deleteProject(int projectId) throws SQLException;

    List<Project> getAllProject() throws SQLException;

    Project getProjectById(int projectId) throws SQLException;

}
