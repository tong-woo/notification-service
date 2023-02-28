package com.tong.notification.api.slack;

import com.tong.notification.model.slack.SlackNewMessageRequestDto;
import com.tong.notification.model.slack.SlackThreadMessageRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/slack")
public interface SlackNotificationTrigger {

    @PostMapping(value = "/sendThreadMessage")
    ResponseEntity<Object> dispatchThreadSlackMessage(
            @RequestBody SlackThreadMessageRequestDto slackThreadMessageRequestDto
    );

    @PostMapping(value = "/sendNewMessage")
    ResponseEntity<Object> dispatchNewSlackMessage(
            @RequestBody SlackNewMessageRequestDto slackNewMessageRequestDto
    );
}
