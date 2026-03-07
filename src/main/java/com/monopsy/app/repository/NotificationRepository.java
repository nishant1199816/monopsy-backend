package com.monopsy.app.repository;

import com.monopsy.app.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientUserIdOrderByCreatedAtDesc(Long userId);
    List<Notification> findByRecipientUserIdAndReadFalseOrderByCreatedAtDesc(Long userId);
    long countByRecipientUserIdAndReadFalse(Long userId);
}
