package com.sak.ppmtool.services;

import com.sak.ppmtool.exceptions.ProjectNotFoundException;
import com.sak.ppmtool.model.Backlog;
import com.sak.ppmtool.model.Project;
import com.sak.ppmtool.model.ProjectTask;
import com.sak.ppmtool.repositories.BacklogRepository;
import com.sak.ppmtool.repositories.ProjectRepository;
import com.sak.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    ProjectRepository projectRepository;


    public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask){
        /*
         * {
         *  ProjectNotFound: "Project Not Found"
         * }
         * */
        try {
            // PTs to be added to specific project, project != null , BL exists
            //System.out.println(">>>>>>>>>>>>> " +projectIdentifier);
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

            // set he BL to project task
            projectTask.setBacklog(backlog);
            //projectTask.setProjectIdentifier(projectIdentifier);
            // project sequence IDPRO-1 IDPRO-2
            Integer BacklogSequence = backlog.getPTSequence();
            BacklogSequence++;


            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            // UPDATE BL SEQUENCE
            backlog.setPTSequence(BacklogSequence);

            // initial priority when priority is null
            //if(projectTask.getPriority() == 0 | projectTask.getPriority() == null) {
            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            // status = done or on progress , initial status when  status is null
            if (projectTask.getStatus() == "" | projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        }catch (Exception e){
            throw new ProjectNotFoundException("Project Not Found");
        }
    }

    public List<ProjectTask> findBacklogById(String id){
        Project project = projectRepository.findByProjectIdentifier(id);

        if(project == null)
            throw new ProjectNotFoundException("Project with ID: '"+id+"' does not exist");

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id){
        // make sure we are searching on an existing backlog
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null)
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");

        //make sure that our task exist
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null)
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
            // make sure we are searching on the right backlog
        if(!projectTask.getProjectIdentifier().equals(backlog_id))
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id);

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);

        projectTask = updatedTask;

        return  projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence( String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);
        System.out.println(projectTask.getProjectIdentifier());
        projectTaskRepository.delete(projectTask);

    }
}










