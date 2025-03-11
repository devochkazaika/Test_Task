package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.cwt.task.model.UserAnalytic;
import org.cwt.task.model.entity.BookRent;
import org.cwt.task.model.entity.User;
import org.cwt.task.repository.AnalyticRepository;
import org.cwt.task.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Singleton
public class AnalyticRepositoryImpl implements AnalyticRepository {
    @Inject
    private EntityManager em;

    @Inject
    private UserRepository userRepository;

    private List<String> getBooksByStatus(UUID userId,
                                         BookRent.RentStatus status,
                                         LocalDateTime startTime, 
                                         LocalDateTime endTime) {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT DISTINCT b.name FROM BookRent r " +
                "JOIN r.book b " +
                "WHERE r.user.id = :userId AND r.rentStatus = :status";
        stringBuilder.append(query);
        if (startTime != null) {
            stringBuilder.append(" AND (r.rentDate >= :startTime)");
        }
        if (endTime != null) {
            stringBuilder.append(" AND (r.returnDate <= :endTime)");
        }

        return em.createQuery(stringBuilder.toString(), String.class)
                .setParameter("userId", userId)
                .setParameter("status", status)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .getResultList();
    }

    @Override
    public UserAnalytic getUserAnalytic(UUID userId, LocalDateTime startTime, LocalDateTime endTime) {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT " +
                "COUNT(r), " +
                "SUM(CASE WHEN r.rentStatus = 'OPENED' THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN r.rentStatus = 'CLOSED' THEN 1 ELSE 0 END) " +
                "FROM User u " +
                "LEFT JOIN BookRent r ON u.id = r.user.id " +
                "WHERE u.id = :userId";
        stringBuilder.append(query);
        if (startTime != null) {
            stringBuilder.append(" AND (r.rentDate >= :startTime)");
        }
        if (endTime != null) {
            stringBuilder.append(" AND (r.returnDate <= :endTime)");
        }
        stringBuilder.append(" GROUP BY u.id");

        User user = userRepository.findById(userId);
        try {
            Object[] result = (Object[]) em.createQuery(stringBuilder.toString())
                    .setParameter("userId", userId)
                    .setParameter("startTime", startTime)
                    .setParameter("endTime", endTime)
                    .getSingleResult();
            return UserAnalytic.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .countRent(((Number) result[0]).intValue())
                    .countOpenRent(((Number) result[1]).intValue())
                    .countCloseRent(((Number) result[2]).intValue())
                    .openBook(getBooksByStatus(userId, BookRent.RentStatus.OPENED, startTime, endTime))
                    .closeBook(getBooksByStatus(userId, BookRent.RentStatus.CLOSED, startTime, endTime))
                    .build();
        }
        catch (NoResultException e){
            return UserAnalytic.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .countRent(0)
                    .countOpenRent(0)
                    .countCloseRent(0)
                    .openBook(getBooksByStatus(userId, BookRent.RentStatus.OPENED, startTime, endTime))
                    .closeBook(getBooksByStatus(userId, BookRent.RentStatus.CLOSED, startTime, endTime))
                    .build();
        }
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
