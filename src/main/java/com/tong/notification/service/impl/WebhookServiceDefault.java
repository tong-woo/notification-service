package com.tong.notification.service.impl;

import com.tong.notification.client.WebhookFeignClient;
import com.tong.notification.model.slack.SlackWebhookRequestDto;
import com.tong.notification.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;

@RequiredArgsConstructor
@Slf4j
@Service
public class WebhookServiceDefault implements WebhookService {

    private final WebhookFeignClient webhookFeignClient;

    @Override
    public void sendWebhookMessage(String url, String text) {
        try {
            URI uri = URI.create(url);
            webhookFeignClient.sendWebhookMessage(uri, new SlackWebhookRequestDto(text));
        } catch (Exception e) {
            log.error("error: {}", e.getMessage(), e);
            throw e;
        }
    }
}
