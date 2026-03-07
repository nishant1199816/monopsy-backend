package com.monopsy.app.repository;

import com.monopsy.app.model.WorkerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerProfileRepository extends JpaRepository<WorkerProfile, Long> {
    Optional<WorkerProfile> findByUserId(Long userId);
    Optional<WorkerProfile> findByUser_Email(String email);
    List<WorkerProfile> findBySkillCategoryIgnoreCaseContaining(String skill);
    List<WorkerProfile> findByLocationIgnoreCaseContaining(String location);
    List<WorkerProfile> findBySkillCategoryIgnoreCaseContainingAndLocationIgnoreCaseContaining(String skill, String location);
}
