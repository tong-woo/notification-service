package com.tong.notification.service;

public interface WebhookService {
    void sendWebhookMessage(String url, String text);
}
