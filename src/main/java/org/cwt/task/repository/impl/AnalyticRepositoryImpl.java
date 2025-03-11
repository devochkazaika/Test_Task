package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.cwt.task.model.UserAnalytic;
import org.cwt.task.repository.AnalyticRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AnalyticRepositoryImpl implements AnalyticRepository {
    @Inject
    private EntityManager em;

    private Set<String> getBooksByStatus(UUID userId, 
                                         String status, 
                                         LocalDateTime startTime, 
                                         LocalDateTime endTime) {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT DISTINCT b.name FROM BookRent r " +
                "JOIN r.book b " +
                "WHERE r.user.id = :userId AND r.rentStatus = :status";
        stringBuilder.append(query);
        if (startTime != null) {
            stringBuilder.append(" AND (r.startTime > :startTime)");
        }
        if (endTime != null) {
            stringBuilder.append(" AND (r.endTime < :endTime)");
        }
        List<String> books = em.createQuery(stringBuilder.toString(), String.class)
                .setParameter("userId", userId)
                .setParameter("status", status)
                .getResultList();

        return new HashSet<>(books);
    }

    @Override
    public UserAnalytic getUserAnalytic(UUID userId, LocalDateTime startTime, LocalDateTime endTime) {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT u.firstName, u.lastName, " +
                "COUNT(r), " +
                "SUM(CASE WHEN r.rentStatus = 'OPEN' THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN r.rentStatus = 'CLOSED' THEN 1 ELSE 0 END) " +
                "FROM User u " +
                "LEFT JOIN BookRent r ON u.id = r.user.id " +
                "WHERE u.id = :userId";
        stringBuilder.append(query);
        if (startTime != null) {
            stringBuilder.append(" AND (r.startTime > :startTime)");
        }
        if (endTime != null) {
            stringBuilder.append(" AND (r.endTime < :endTime)");
        }
        stringBuilder.append(" GROUP BY u.id");

        Object[] result = (Object[]) em.createQuery(stringBuilder.toString())
                .setParameter("userId", userId)
                .getSingleResult();

        return UserAnalytic.builder()
                .firstName((String) result[0])
                .lastName((String) result[1])
                .countRent((Integer) result[2])
                .countOpenRent((Integer) result[3])
                .countCloseRent((Integer) result[4])
                .openBook(getBooksByStatus(userId, "OPEN", startTime, endTime))
                .closeBook(getBooksByStatus(userId, "CLOSED", startTime, endTime))
                .build();
    }

    @Override
    public UserAnalytic getUserAnalytic(UUID userId, LocalDateTime startTime) {
        return getUserAnalytic(userId, startTime, null);
    }

    @Override
    public UserAnalytic getUserAnalytic(UUID userId) {
        return getUserAnalytic(userId, null, null);
    }
}
