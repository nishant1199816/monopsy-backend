package com.monopsy.app.service;

import com.monopsy.app.model.Notification;
import com.monopsy.app.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public Notification create(Long recipientId, String type, String message, Long jobId, Long actorId) {
        Notification n = new Notification();
        n.setRecipientUserId(recipientId);
        n.setType(type);
        n.setMessage(message);
        n.setJobId(jobId);
        n.setActorUserId(actorId);
        return repo.save(n);
    }

    public List<Notification> getForUser(Long userId) {
        return repo.findByRecipientUserIdOrderByCreatedAtDesc(userId);
    }

    public long countUnread(Long userId) {
        return repo.countByRecipientUserIdAndReadFalse(userId);
    }

    public Notification markRead(Long id) {
        Notification n = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        n.setRead(true);
        n.setReadAt(Instant.now());
        return repo.save(n);
    }

    public int markAllRead(Long userId) {
        List<Notification> unread =
                repo.findByRecipientUserIdAndReadFalseOrderByCreatedAtDesc(userId);
        unread.forEach(n -> { n.setRead(true); n.setReadAt(Instant.now()); });
        repo.saveAll(unread);
        return unread.size();
    }
}
