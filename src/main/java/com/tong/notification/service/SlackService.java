package com.tong.notification.service;

import com.slack.api.methods.response.chat.ChatPostMessageResponse;

public interface SlackService {

    ChatPostMessageResponse sendThreadMessage(String key, String channel, String message);

    ChatPostMessageResponse trySendMessage(String channel, String message, String threadTs);
}
