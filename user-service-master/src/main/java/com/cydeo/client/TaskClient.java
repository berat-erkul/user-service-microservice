package com.cydeo.client;

import com.cydeo.dto.responses.TaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "task-service", url = "/api/v1/task")
public interface TaskClient {

    @GetMapping("/count/employee/{assignedEmployee}")
    ResponseEntity<TaskResponse> getNonCompletedCountByAssignedEmployeeByAssignedEmployee (@PathVariable ("assignedEmployee") String assignedEmployee);

}
