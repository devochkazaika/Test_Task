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
                "JOIN r.book b WHERE r.user.id = :userId");

        if (status.equals(BookRent.RentStatus.OPENED)) {
            queryBuilder.append(" AND r.rentDate >= :startTime");
            queryBuilder.append(" AND (r.rentDate <= :endTime) AND (r.returnDate IS NULL OR r.returnDate > :endTime)");
        }

        if (status.equals(BookRent.RentStatus.CLOSED)) {
            queryBuilder.append(" AND r.returnDate >= :startTime");
            queryBuilder.append(" AND (r.returnDate <= :endTime) ");
        }

        LocalDateTime safeEndTime = (endTime != null) ? endTime : LocalDateTime.of(3000, 12, 31, 23, 59, 59);
        LocalDateTime safeStartTime = (startTime != null) ? startTime : LocalDateTime.of(1000, 12, 31, 23, 59, 59);

        Query query = em.createQuery(queryBuilder.toString(), String.class)
                .setParameter("userId", userId)
                .setParameter("startTime", safeStartTime)
                .setParameter("endTime", safeEndTime);
        return query.getResultList();
    }


    @Override
    public UserAnalytic getUserAnalytic(UUID userId, LocalDateTime startTime, LocalDateTime endTime) {
        StringBuilder queryBuilder = new StringBuilder("SELECT " +
                "COUNT(r), " +
                "SUM(CASE WHEN r.rentDate >= :startTime AND (r.returnDate IS NULL OR r.returnDate > :endTime) THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN r.returnDate >= :startTime AND r.returnDate <= :endTime THEN 1 ELSE 0 END) " +
                "FROM User u " +
                "LEFT JOIN BookRent r ON u.id = r.user.id " +
                "WHERE u.id = :userId");

        queryBuilder.append(" GROUP BY u.id");

        User user = userRepository.findById(userId);

        Query query = em.createQuery(queryBuilder.toString())
                .setParameter("userId", userId)
                .setParameter("startTime", (startTime != null) ?
                        startTime : LocalDateTime.of(1000, 12, 31, 23, 59, 59))
                .setParameter("endTime", endTime != null ?
                        endTime : LocalDateTime.of(3000, 12, 31, 23, 59, 59)); // если endTime = null, ставим LocalDateTime.MAX

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
                .countRent(countOpenRent + countCloseRent)
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
