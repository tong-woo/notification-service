package com.tong.notificationservice.api.slack;

import com.tong.notificationservice.model.slack.SlackMessageRequestDto;
import com.tong.notificationservice.model.slack.SlackThreadMessageRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/slack")
public interface SlackNotificationTrigger {

    @PostMapping(value = "/sendInThread")
    ResponseEntity<String> dispatchThreadSlackMessage(
            @RequestBody SlackThreadMessageRequestDto slackThreadMessageRequestDto
    );

    @PostMapping(value = "/sendNewMessage")
    ResponseEntity<String> dispatchNewSlackMessage(
            @RequestBody SlackMessageRequestDto slackMessageRequestDto
    );
}
