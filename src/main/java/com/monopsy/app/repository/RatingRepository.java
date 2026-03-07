package com.monopsy.app.repository;

import com.monopsy.app.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByTargetUserId(Long userId);
    boolean existsByJobIdAndRaterUserId(Long jobId, Long raterUserId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.targetUserId = :userId")
    Double findAverageScoreByTargetUserId(Long userId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.targetUserId = :userId")
    Long countByTargetUserId(Long userId);
}
