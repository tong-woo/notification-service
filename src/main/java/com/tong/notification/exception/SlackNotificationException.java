package com.tong.notification.exception;

import lombok.Getter;

@Getter
public class SlackNotificationException extends RuntimeException {

    private final String slackErrorMessage;

    public SlackNotificationException(String slackErrorMessage) {
        super();
        this.slackErrorMessage = slackErrorMessage;
    }
}
