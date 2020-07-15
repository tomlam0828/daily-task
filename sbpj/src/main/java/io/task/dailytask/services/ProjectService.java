package io.task.dailytask.services;

import io.task.dailytask.domain.Backlog;
import io.task.dailytask.domain.Project;
import io.task.dailytask.domain.User;
import io.task.dailytask.exceptions.ProjectIdException;
import io.task.dailytask.repositories.BacklogRepository;
import io.task.dailytask.repositories.ProjectRepository;
import io.task.dailytask.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        String projectId = project.getProjectIdentifier().toUpperCase();
        try {
            User user = userRepository.findByUsername(username);

            project.setUser(user);

            project.setProjectLeader(user.getUsername());

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

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectid) {
        Project project = projectRepository.findByProjectIdentifier(projectid);

        if (project == null) {
            throw new ProjectIdException("Cannot delete with ID: " + projectid + " . It doesn't exist");
        }

        projectRepository.delete(project);
    }
}
