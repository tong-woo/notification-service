package com.tong.notification.client;

import com.tong.notification.model.slack.SlackWebhookRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;

@FeignClient(name = "SlackWebhookClient", url = "${webhook.default.url}")
public interface WebhookFeignClient {

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    ResponseEntity<String> sendWebhookMessage(
            URI url, @RequestBody SlackWebhookRequestDto slackWebhookRequestDto);
}