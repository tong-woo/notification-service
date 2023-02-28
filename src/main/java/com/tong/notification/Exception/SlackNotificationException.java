package com.tong.notification.Exception;

import lombok.Getter;

@Getter
public class SlackNotificationException extends RuntimeException {

    private final String slackErrorMessage;

    public SlackNotificationException(String slackErrorMessage) {
        super();
        this.slackErrorMessage = slackErrorMessage;
    }
}
