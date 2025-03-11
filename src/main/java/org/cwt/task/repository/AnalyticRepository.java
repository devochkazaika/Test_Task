package org.cwt.task.repository;

import org.cwt.task.model.UserAnalytic;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AnalyticRepository {
    UserAnalytic getUserAnalytic(UUID userId);
    UserAnalytic getUserAnalytic(UUID userId, LocalDateTime startTime);
    UserAnalytic getUserAnalytic(UUID userId, LocalDateTime startTime, LocalDateTime endTime);

}
