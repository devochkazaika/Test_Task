package org.cwt.task.service;

import org.cwt.task.model.UserAnalytic;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AnalyticService {
    UserAnalytic userAnalytics(UUID username,
                               LocalDateTime start,
                               LocalDateTime end);

}
