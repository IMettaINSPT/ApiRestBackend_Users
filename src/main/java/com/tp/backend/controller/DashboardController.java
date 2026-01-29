package com.tp.backend.controller;

import com.tp.backend.dto.dashboard.DashboardSummaryResponse;
import com.tp.backend.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse summary() {
        return service.getSummary();
    }
}
