package com.tp.backend.dashboard.controller;

import com.tp.backend.dashboard.application.DashboardUseCase;
import com.tp.backend.dashboard.dto.DashboardSummaryResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardUseCase service;

    public DashboardController(DashboardUseCase service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse summary() {
        return service.getSummary();
    }
}