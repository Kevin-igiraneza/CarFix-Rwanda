package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientIdAndReadFalseOrderByCreatedAtDesc(Long recipientId);

    long countByRecipientIdAndReadFalse(Long recipientId);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.read = true WHERE n.recipient.id = :recipientId AND n.read = false")
    void markAllReadForUser(@Param("recipientId") Long recipientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.recipient.id = :recipientId AND n.type = :type AND n.link = :link")
    void deleteByRecipientAndTypeAndLink(@Param("recipientId") Long recipientId, @Param("type") com.carfix.carfixrwanda.enums.NotificationType type, @Param("link") String link);
}
