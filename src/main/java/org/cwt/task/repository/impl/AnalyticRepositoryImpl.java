package org.cwt.task.repository.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
    private final EntityManager em;
    private final UserRepository userRepository;

    @Inject
    public AnalyticRepositoryImpl(EntityManager entityManager, UserRepository userRepository) {
        this.em = entityManager;
        this.userRepository = userRepository;
    }

    public List<String> getBooksByStatus(UUID userId, BookRent.RentStatus status, LocalDateTime startTime, LocalDateTime endTime) {
        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT b.name FROM BookRent r " +
                "JOIN r.book b WHERE r.user.id = :userId AND r.rentStatus = :status");

        if (startTime != null && status.equals(BookRent.RentStatus.OPENED)) {
            queryBuilder.append(" AND r.rentDate >= :startTime");
        }
        if (endTime != null && status.equals(BookRent.RentStatus.CLOSED)) {
            queryBuilder.append(" AND r.returnDate <= :endTime");
        }

        Query query = em.createQuery(queryBuilder.toString(), String.class)
                .setParameter("userId", userId)
                .setParameter("status", status);

        if (startTime != null && status.equals(BookRent.RentStatus.OPENED)) {
            query.setParameter("startTime", startTime);
        }
        if (endTime != null && status.equals(BookRent.RentStatus.CLOSED)) {
            query.setParameter("endTime", endTime);
        }

        return query.getResultList();
    }

    @Override
    public UserAnalytic getUserAnalytic(UUID userId, LocalDateTime startTime, LocalDateTime endTime) {
        StringBuilder queryBuilder = new StringBuilder("SELECT " +
                "COUNT(r), " +
                "SUM(CASE WHEN r.rentStatus = 'OPENED' THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN r.rentStatus = 'CLOSED' THEN 1 ELSE 0 END) " +
                "FROM User u " +
                "LEFT JOIN BookRent r ON u.id = r.user.id " +
                "WHERE u.id = :userId");

        if (startTime != null) {
            queryBuilder.append(" AND r.rentDate >= :startTime");
        }
        if (endTime != null) {
            queryBuilder.append(" AND r.returnDate <= :endTime");
        }

        queryBuilder.append(" GROUP BY u.id");

        User user = userRepository.findById(userId);

        Query query = em.createQuery(queryBuilder.toString())
                .setParameter("userId", userId);

        if (startTime != null) {
            query.setParameter("startTime", startTime);
        }
        if (endTime != null) {
            query.setParameter("endTime", endTime);
        }

        List<Object[]> resultList = query.getResultList();
        Object[] result = resultList.isEmpty() ? null : resultList.get(0);

        return buildUserAnalytic(user, result, userId, startTime, endTime);
    }

    private UserAnalytic buildUserAnalytic(User user, Object[] result, UUID userId, LocalDateTime startTime, LocalDateTime endTime) {
        int countRent = (result != null && result[0] != null) ? ((Number) result[0]).intValue() : 0;
        int countOpenRent = (result != null && result[1] != null) ? ((Number) result[1]).intValue() : 0;
        int countCloseRent = (result != null && result[2] != null) ? ((Number) result[2]).intValue() : 0;

        List<String> openBooks = getBooksByStatus(userId, BookRent.RentStatus.OPENED, startTime, endTime);
        List<String> closeBooks = getBooksByStatus(userId, BookRent.RentStatus.CLOSED, startTime, endTime);

        return UserAnalytic.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .countRent(countRent)
                .countOpenRent(countOpenRent)
                .countCloseRent(countCloseRent)
                .openBook(openBooks)
                .closeBook(closeBooks)
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
