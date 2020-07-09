package io.task.dailytask.services;

import io.task.dailytask.domain.Backlog;
import io.task.dailytask.domain.Project;
import io.task.dailytask.exceptions.ProjectIdException;
import io.task.dailytask.repositories.BacklogRepository;
import io.task.dailytask.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
        String projectId = project.getProjectIdentifier().toUpperCase();
        try {
            project.setProjectIdentifier(projectId);

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectId);
            }

            if (project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectId));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + projectId + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
        }

        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectid) {
        Project project = projectRepository.findByProjectIdentifier(projectid);

        if (project == null) {
            throw new ProjectIdException("Cannot delete with ID: " + projectid + " . It doesn't exist");
        }

        projectRepository.delete(project);
    }
}
