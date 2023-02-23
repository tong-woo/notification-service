package com.tong.notification.service;

import com.slack.api.methods.response.chat.ChatPostMessageResponse;

public interface SlackService {
    ChatPostMessageResponse sendMessage();

    ChatPostMessageResponse trySendMessage();
}
