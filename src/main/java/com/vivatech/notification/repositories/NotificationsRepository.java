package com.vivatech.notification.repositories;

import com.vivatech.notification.notificationmodel.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
}
