package com.tong.notification.api.webhook;

import com.tong.notification.model.webhook.WebhookMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1", consumes = "application/json")
public interface WebhookNotificationTrigger {

    @PostMapping(value = "/webhook")
    ResponseEntity<String> dispatchSlackWebhookMessage(
            @RequestBody WebhookMessageDto webhookMessageDto
    );
}
