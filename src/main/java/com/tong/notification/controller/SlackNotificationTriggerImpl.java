package com.tong.notification.controller;

import com.tong.notification.api.slack.SlackNotificationTrigger;
import com.tong.notification.model.slack.SlackNewMessageRequestDto;
import com.tong.notification.model.slack.SlackThreadMessageRequestDto;
import com.tong.notification.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackNotificationTriggerImpl implements SlackNotificationTrigger {

    private final SlackService slackService;

    @Override
    public ResponseEntity<Object> dispatchThreadSlackMessage(SlackThreadMessageRequestDto slackThreadMessageRequestDto) {
        var response = slackService.sendThreadMessage(slackThreadMessageRequestDto.getKey(),
                slackThreadMessageRequestDto.getChannel(),
                slackThreadMessageRequestDto.getPayload());
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<Object> dispatchNewSlackMessage(SlackNewMessageRequestDto slackNewMessageRequestDto) {
        var response = slackService.trySendMessage(slackNewMessageRequestDto.getChannel(),
                slackNewMessageRequestDto.getText(),
                slackNewMessageRequestDto.getThreadId());
        return ResponseEntity.ok(response);
    }
}
