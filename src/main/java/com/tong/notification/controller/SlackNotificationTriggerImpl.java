package com.tong.notification.controller;

import com.tong.notification.api.slack.SlackNotificationTrigger;
import com.tong.notification.model.slack.SlackMessageRequestDto;
import com.tong.notification.model.slack.SlackThreadMessageRequestDto;
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
