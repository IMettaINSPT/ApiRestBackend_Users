package com.tp.backend.dashboard.application;

import com.tp.backend.dashboard.dto.DashboardSummaryResponse;

public interface DashboardUseCase {
    DashboardSummaryResponse getSummary();
}