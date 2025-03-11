package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.cwt.task.model.UserAnalytic;
import org.cwt.task.repository.AnalyticRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Singleton
public class AnalyticRepositoryImpl implements AnalyticRepository {
    @Inject
    private EntityManager em;

    private List<String> getBooksByStatus(UUID userId,
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

        return em.createQuery(stringBuilder.toString(), String.class)
                .setParameter("userId", userId)
                .setParameter("status", status)
                .getResultList();
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
                .countRent(((Number) result[2]).intValue()) // ✅ Приведение типов
                .countOpenRent(((Number) result[3]).intValue())
                .countCloseRent(((Number) result[4]).intValue())
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
