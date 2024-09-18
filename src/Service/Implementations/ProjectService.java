package Service.Implementations;

import Model.Entities.Client;
import Model.Entities.Project;
import Repository.Implementations.ClientRepository;
import Repository.Implementations.ProjectRepository;
import Service.Interfaces.IProjectService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProjectService implements IProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ClientRepository clientRepository) {
    }

    @Override
    public void addProject(Project project) throws SQLException {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        if (project.getClient() == null || project.getClient().getId() <= 0) {
            throw new IllegalArgumentException("Invalid client associated with the project");
        }
        if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
        if (project.getProfitMargin() < 0 || project.getTotalCost() < 0) {
            throw new IllegalArgumentException("Profit margin and total cost must be non-negative");
        }

        projectRepository.create(project);
    }

    @Override
    public void updateProject(int projectId, Project project) throws SQLException {
        projectRepository.update(project);
    }


    @Override
    public void deleteProject(int projectId) throws SQLException {
        Optional<Project> existingClient = getProjectById(projectId);


        if (existingClient.isPresent()) {
            projectRepository.delete(projectId);
        } else {
            System.out.println("Client with ID " + projectId + " not found.");
        }

    }

    @Override
    public List<Project> getAllProject() throws SQLException {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(int projectId) throws SQLException {
        Optional<Project> client = projectRepository.findById(projectId);
        return Optional.ofNullable(client.orElse(null));
    }
}
