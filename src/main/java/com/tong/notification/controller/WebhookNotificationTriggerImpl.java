package com.tong.notification.controller;

import com.tong.notification.api.webhook.WebhookNotificationTrigger;
import com.tong.notification.model.webhook.WebhookMessageDto;
import com.tong.notification.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebhookNotificationTriggerImpl implements WebhookNotificationTrigger {

    private final WebhookService webhookService;

    @Override
    public ResponseEntity<String> dispatchSlackWebhookMessage(WebhookMessageDto webhookMessageDto) {
        webhookService.sendWebhookMessage(webhookMessageDto.getUrl(), webhookMessageDto.getText());
        return ResponseEntity.ok("OK");
    }
}
