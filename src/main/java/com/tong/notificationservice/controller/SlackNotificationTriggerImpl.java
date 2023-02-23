package com.tong.notificationservice.controller;

import com.tong.notificationservice.api.slack.SlackNotificationTrigger;
import com.tong.notificationservice.model.slack.SlackMessageRequestDto;
import com.tong.notificationservice.model.slack.SlackThreadMessageRequestDto;
import org.springframework.http.ResponseEntity;

public class SlackNotificationTriggerImpl implements SlackNotificationTrigger {
    @Override
    public ResponseEntity<String> dispatchThreadSlackMessage(SlackThreadMessageRequestDto slackThreadMessageRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<String> dispatchNewSlackMessage(SlackMessageRequestDto slackMessageRequestDto) {
        return null;
    }
}
