package com.cydeo.client;

import com.cydeo.dto.responses.ProjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "project-service", url = "http://localhost:8082/api/v1/projects")
public interface ProjectClient {

    //I need a wrapper to take fields of Json file. And I'll use Header that why I use ResponseEntity<...>

    @GetMapping("/count/manager/{assignedManager}")
    ResponseEntity<ProjectResponse> getNonCompletedCountByAssignedManager(@PathVariable("assignedManager") String assignedManager);




}
