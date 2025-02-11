package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Public APIs")
public class PublicController {
    @GetMapping("/health")
    @Operation(summary = "Health check endpoint", description = "Check the health of the application.")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        log.info("Health check endpoint called");
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Application is healthy", "OK"));
    }
}
