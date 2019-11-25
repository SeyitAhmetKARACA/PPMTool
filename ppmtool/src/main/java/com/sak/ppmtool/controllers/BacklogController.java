package com.sak.ppmtool.controllers;

import com.sak.ppmtool.model.ProjectTask;
import com.sak.ppmtool.services.MapValidationErrorService;
import com.sak.ppmtool.services.ProjectService;
import com.sak.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @RequestMapping(value = "/{backlog_id}",method = RequestMethod.POST)
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result , @PathVariable String backlog_id){
        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);

        if(errMap != null){
            return errMap;
        }
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id , projectTask);
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @RequestMapping( value = "/{backlog_id}",method = RequestMethod.GET)
    public ResponseEntity<List<ProjectTask>> getProjectBacklog(@PathVariable String backlog_id ){
        return new ResponseEntity<List<ProjectTask>>( projectTaskService.findBacklogById(backlog_id),HttpStatus.OK);
    }

    @RequestMapping( value = "/{backlog_id}/{pt_id}",method = RequestMethod.GET)
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id,pt_id);
        return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }

    @RequestMapping(value = "/{backlog_id}/{pt_id}",method = RequestMethod.PATCH)
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
                                               @PathVariable String backlog_id , @PathVariable String pt_id,
                                               BindingResult result){
        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);

        if(errMap != null)
            return errMap;

        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask,backlog_id,pt_id);

        return new ResponseEntity<ProjectTask>(updatedTask,HttpStatus.OK);
    }


    @RequestMapping(value = "/{backlog_id}/{pt_id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> updateProjectTask(@PathVariable String backlog_id , @PathVariable String pt_id){
       /* ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);

        if(errMap != null)
            return errMap;*/

        projectTaskService.deletePTByProjectSequence(backlog_id,pt_id);
        return new ResponseEntity<String>("Project deleted",HttpStatus.OK);
    }
}






