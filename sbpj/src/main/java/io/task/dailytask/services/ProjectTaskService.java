package io.task.dailytask.services;

import io.task.dailytask.domain.Backlog;
import io.task.dailytask.domain.ProjectTask;
import io.task.dailytask.repositories.BacklogRepository;
import io.task.dailytask.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        // pts to added to a specific project, project != null
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        // set the bl to the pt
        projectTask.setBacklog(backlog);

        // we want our project sequence to be like this: idpro-1, idpro-2
        Integer BacklogSequence = backlog.getPTSequence();

        // update the bl sequence
        BacklogSequence++;

        // add sequence to project task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        // initial priority when priority null
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }
        // initial status when status is null;
        if (projectTask.getPriority() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("Todo");
        }

        return projectTaskRepository.save(projectTask);
    }
}
