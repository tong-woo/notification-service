package com.tong.notification.service.impl;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.tong.notification.Exception.SlackNotificationException;
import com.tong.notification.service.CachedThreadService;
import com.tong.notification.service.SlackService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class SlackServiceDefault implements SlackService {

    private final MethodsClient slackMethodsClient;
    private final CachedThreadService cachedThreadService;

    @Override
    public ChatPostMessageResponse sendThreadMessage(String key, String channel, String message) {
        if (Objects.isNull(key)) throw new IllegalArgumentException("the key is null");
        String threadTs = cachedThreadService.findByKey(key).orElse("");
        ChatPostMessageResponse response = this.trySendMessage(channel, message, threadTs);
        if (!cachedThreadService.containsKey(key)) {
            cachedThreadService.cacheStore(key, response.getTs());
        }
        return response;
    }

    @Override
    @SneakyThrows
    public ChatPostMessageResponse trySendMessage(String channelName, String message, String threadTs) {
        try {
            ChatPostMessageResponse result = slackMethodsClient.chatPostMessage(r -> r
                    .channel(channelName)
                    .threadTs(threadTs)
                    .text(message));
            if (Objects.nonNull(result.getError())) {
                throw new SlackNotificationException(result.getError());
            }
            log.info("result {}", result);
            return result;
        } catch (IOException e) {
            log.error("error: {}", e.getMessage(), e);
            throw e;
        } catch (SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
            throw new SlackNotificationException(e.getError().getError());
        }
    }
}
