package org.cwt.task.service.impl;

import jakarta.inject.Inject;
import org.cwt.task.model.UserAnalytic;
import org.cwt.task.repository.AnalyticRepository;
import org.cwt.task.service.AnalyticService;

import java.time.LocalDateTime;
import java.util.UUID;

public class AnalyticServiceImpl implements AnalyticService {
    @Inject
    private AnalyticRepository analyticRepository;

    @Override
    public UserAnalytic userAnalytics(UUID userId,
                                      LocalDateTime start,
                                      LocalDateTime end) {
        return analyticRepository.getUserAnalytic(userId, start, end);
    }
}
