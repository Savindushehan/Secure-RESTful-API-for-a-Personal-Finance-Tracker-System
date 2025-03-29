package com.IT22354938.Notification;

import org.springframework.web.bind.annotation.*;
import com.IT22354938.Notification.NotificationHandler;

import java.io.IOException;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationHandler notificationHandler;

    public NotificationController(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @PostMapping("/send")
    public String sendNotification(@RequestParam String message) throws IOException {
        notificationHandler.sendNotification(message);
        return "Notification Sent!";
    }
}
