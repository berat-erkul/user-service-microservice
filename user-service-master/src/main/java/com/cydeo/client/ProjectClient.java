package com.cydeo.client;

import com.cydeo.dto.responses.ProjectResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "project-service") //normally we use the fully URL, but here we have DiscoveryService in eureka server to find the project-service (IP and PORT)
public interface ProjectClient {

    //I need a wrapper to take fields of Json file. And I'll use Header that why I use ResponseEntity<...>

    @GetMapping("/api/v1/project/count/manager/{assignedManager}")
    @CircuitBreaker(name = "project-service")
    ResponseEntity<ProjectResponse> getNonCompletedCountByAssignedManager(@PathVariable("assignedManager") String assignedManager);




}
